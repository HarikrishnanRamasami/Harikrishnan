/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.Customer;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.AppointmentDAO;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.dao.DashboardDAO.DateRange;
import qa.com.qic.common.dao.DealsDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.Appointment;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author karthik.j
 */
public class AppointmentAction extends ActionSupport implements SessionAware, ServletRequestAware {

    private static final Logger LOGGER = LogUtil.getLogger(AppointmentAction.class);

    private transient HttpServletRequest request;
    private transient Map<String, Object> session;

    private transient AppointmentDAO dao;
    private transient CommonDAO commonDao;
    private transient AnoudDAO anoudDao;
    private transient DealsDAO dealsDao;

    private List<? extends Object> aaData;
    private transient List<KeyValue> dateRangeList;
    private transient List<KeyValue> caContactTypList;
    private transient List<KeyValue> caContactList;
    private transient List<KeyValue> userList;
	private List<KeyValue> agentList;

    private transient DateRange dateRange;
    private transient UserInfo userInfo;
    private String message;
    private String messageType;
    private String operation;
    private String flex1;
    private String flex2;
    private String flex3;
    private String company;
    private transient Customer customer;
    private transient Activity activity;
    private transient Appointment appointment;
    private String type;  // Contact Type


    public static final String MAIL_HOST = "smtp.qichoappl.com";
    public static final String MAIL_PORT = "587";
    public static final String MAIL_USER_ID = "rioadmin";
    public static final String MAIL_PASSWORD = "8htdQxS4DCZBz78flDnz";
    public static final String MAIL_FROM = "AnoudAdmin<anoudadmin@qicgroup.com.qa>";




    public String openAppointmentDetails() {
        return SUCCESS;

    }

    public String openAppointmentForm() {
    	dao = new AppointmentDAO(getCompany());
        anoudDao = new AnoudDAO(getCompany());
        caContactTypList =  anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_CONTACT_TYPE);

        if ("add".equals(appointment.getOperation())) {
            appointment.setOperation(appointment.getOperation());
        }else if("edit".equals(appointment.getOperation())){
            appointment = dao.loadAppointmentById(appointment.getCaContactType(), appointment.getCaId());
            appointment.setOperation("edit");
        }
        return "appt_form";
   }

    public String openAppointmentList() {
        commonDao = new CommonDAO(getCompany());
        anoudDao = new AnoudDAO(getCompany());
        userList = commonDao.loadCrmAgentList(getUserInfo().getUserId());
        if (null != userList) {
            userList.add(0, new KeyValue("ALL", "All"));
        }
        setAppointment(new Appointment());
        appointment.setCaCrUid(getUserInfo().getUserId());
        caContactTypList =  anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_CONTACT_TYPE);
        return "appt_list";
    }

    public String openAppointmentDashBoard() {
        commonDao = new CommonDAO(getCompany());
	    setAgentList(commonDao.loadCrmAgentList(null));
        return "appt_dash_board";
    }

    public String openNotesForm() {
    	dao = new AppointmentDAO(getCompany());
        appointment = dao.loadAppointmentById(appointment.getCaContactType(),appointment.getCaId());
        return "notes_form";
    }



    public String saveAppointmentForm() {

        dao = new AppointmentDAO(getCompany());
        anoudDao = new AnoudDAO(getCompany());


        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);

      try {
  	       String startDate = appointment.getCaStartDate() + ' ' + appointment.getCaStartTime();
	       String endDate  =  appointment.getCaStartDate() + ' ' + appointment.getCaEndTime();

	       if(StringUtils.equals("add", appointment.getOperation()))
    	        appointment.setCaCrUid(getUserInfo().getUserId());
	       else if(StringUtils.equals("edit", appointment.getOperation()))
	    	    appointment.setCaUpdUid(getUserInfo().getUserId());

           appointment.setCaStartDate(startDate);
		   appointment.setCaEndDate(endDate);

		   if(StringUtils.isBlank(appointment.getCaRefId()))
			    appointment.setCaRefId(customer.getCivilId());
		   if(StringUtils.isBlank(appointment.getCaCrmId()))
    	         appointment.setCaCrmId(getCustomer().getCrmId());

           int recCount = dao.saveAppointmentDetail(appointment);
           if (recCount == 1) {

        	   if(StringUtils.equals(appointment.getOperation(), "add")){
            	   activity = new Activity();
            	   activity.setCivilId(appointment.getCaRefId());
            	   activity.setDealId(appointment.getCaDealId());
            	   activity.setCrmId(appointment.getCaCrmId());
            	   activity.setMessage(appointment.getCaDescription());
                   activity.setUserId(getUserInfo().getUserId());
                   activity.setTemplate(TypeConstants.CrmLogType.APPOINTMENT.getCode());

                   if(StringUtils.containsIgnoreCase(appointment.getOrigin(), "deal")){
                	   setDealsDao(new DealsDAO(getCompany()));
                	   dealsDao.saveDealActivity(activity);
                   }else {
                  	   anoudDao.callCRMPackage(activity, CommonDAO.ActivityTypes.ACTIVITY);
                   }


        	   }

               setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
           }

           String[] attendees = (String[]) Arrays.asList(appointment.getCaAttendees().split(",")).toArray();

           //Graph.initializeGraphAuth();
          // Graph.createTeamsMeetingInOutlook();

    	//  sendiCalInvite(getOperation(),appointment.getCaTitle() , MAIL_FROM,attendees, appointment.getCaTitle(), appointment.getCaStartDate() ,appointment.getCaEndDate(), "ICS-1",appointment.getCaLocation() , appointment.getCaDescription());

		} catch (Exception e) {
            LOGGER.error("Exception => {}", e);
            setMessage(e.getMessage());
		}

        return "data";
    }


    public String saveNotesForm() {
           dao = new AppointmentDAO(getCompany());
           setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
      try {
    	   int recCount = dao.saveMeetingNotes(appointment, getOperation());
           if (recCount == 1) {
               setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
           }
		} catch (Exception e) {
            LOGGER.error("Exception => {}", e);
            setMessage(e.getMessage());
		}
        return "data";
    }


    public String loadCustomerAppointmentData() {
      dao = new AppointmentDAO(getCompany());
      try {
             setAaData(dao.loadCustomerAppointmentData(appointment.getCaRefId(), appointment.getCaCrmId()));
       } catch (Exception e) {
    	   e.printStackTrace();
       }
     return "data";
   }

   public String loadAppointmentList() {
       dao = new AppointmentDAO(getCompany());
       if ("ALL".equals(getAppointment().getCaCrUid())) {
    	   getAppointment().setCaCrUid("ALL" + getUserInfo().getUserId());
       }
       setAaData(dao.loadAppointmentList(getAppointment()));
       return "data";
   }

   public String loadContactList() {
       dao = new AppointmentDAO(getCompany());
       caContactList = dao.loadContactList(flex1,flex2);
       return SUCCESS;
   }

   public String loadAppointmentSummary() {
       dao = new AppointmentDAO(getCompany());
       aaData = dao.loadAppointmentSummary(getType());
       return "data";
   }

   void sendiCalInvite(String operation,String title, String organizer, String[] to, String subject,
           String start , String end , String invitationId ,
           String location , String description ) throws Exception {

        try {

           String sender = MAIL_FROM;
           String method = null;
          SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy hh:mm aa" );

         Properties props = new Properties();
         props.setProperty("mail.smtp.host", MAIL_HOST);
         // props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         props.setProperty("mail.smtp.socketFactory.fallback", "false");
         props.setProperty("mail.smtp.port", MAIL_PORT);
         props.put("mail.smtp.auth", "true");
         props.put("mail.debug", "true");
         props.put("mail.transport.protocol", "smtp");

         Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        	  @Override
        	  protected PasswordAuthentication getPasswordAuthentication() {
        	      return new PasswordAuthentication(MAIL_USER_ID, MAIL_PASSWORD);
        	  }
        	});
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(sender));

         if( to != null && ( to.length > 0 ) ) {
          InternetAddress[] address = new InternetAddress[ to.length ] ;

         for( int i = 0; i < to.length; i++ ) {
          address[ i ] = new InternetAddress( to[ i ] ) ;
         }

         message.setRecipients( Message.RecipientType.TO, address ) ;
         }

         message.setSubject(subject);
         if("edit".equals(operation))
        	 method = "REQUEST";
        	 else
        	 method =  "PUBLISH";

        	 // Create iCalendar message
        	 StringBuffer messageText = new StringBuffer();
        	 messageText.append("BEGIN:VCALENDAR\n" +
        	               "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
        	               "VERSION:2.0\n" +
        	               "METHOD:"+method+"\n" +
        	               "BEGIN:VEVENT\n" +
        	               "SUMMARY:" + title + "\n" +
        	               "ORGANIZER:MAILTO:" ) ;
        	 messageText.append( organizer ) ;
        	 messageText.append( "\n" +  "DTSTART:");
        	 messageText.append( dateFormat.format(dateFormat.parse(start))) ;
        	 messageText.append( "\n" +  "DTEND:" ) ;
        	 messageText.append(  dateFormat.format(dateFormat.parse(end))) ;
        	 messageText.append( "\n" +  "LOCATION:" ) ;
        	 messageText.append( location ) ;
        	 messageText.append( "\n" + "UID:" ) ;
        	 messageText.append( invitationId ) ;
        	 messageText.append( "\n" +  "DTSTAMP:" ) ;
        	 messageText.append( dateFormat.format( new java.util.Date() ) ) ;
        	 messageText.append( "\n" +  "DESCRIPTION;ALTREP=\"CID:<eventDescriptionHTML>\"" ) ;
        	 messageText.append( "\n" + "BEGIN:VALARM\n" +
        	             "TRIGGER:-PT15M\n" +
        	             "ACTION:DISPLAY\n" +
        	             "DESCRIPTION:Reminder\n" +
        	             "END:VALARM\n" +
        	             "END:VEVENT\n" +
        	             "END:VCALENDAR"
        	    ) ;
        	 Multipart mp = new MimeMultipart();
        	 MimeBodyPart meetingPart = new MimeBodyPart() ;
        	 meetingPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
        	 meetingPart.setHeader("Content-ID", "calendar_message");
        	 meetingPart.setDataHandler( new DataHandler( new StringDataSource( messageText.toString(), "text/calendar;name=\"MeetingInvite.ics\"", "meetingRequest" ) ) ) ;
         	 mp.addBodyPart( meetingPart ) ;

//         	MimeBodyPart attachment = MimeBodyPart();
//         	String filename = "document.docx";
//         	File file = new File("~/docs/document.docx");
//         	FileDataSource source = FileDataSource(file);
//         	attachment.setDataHandler(new DataHandler(source));
//         	attachment.setFileName(filename);
//         	multipart.addBodyPart(attachment);




         	 description = "<html>"
                         + "<body style=\"font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12px; font-style: normal;\">"
                         + "Hi User, <br><br>"
                         + "You having been invited to meet with Anoud CRM!! Please open the attched ics file and save to your to calendar. <br><br>"
                         + "Regards,<br>"
                         + "Anoud Admin<br><br>"
                         + "<b><i> Note : This is a system generated mail. Please do not reply. </i></b>"
                         + "</body>"
                         + "</html>"  ;

        	 MimeBodyPart descriptionPart = new MimeBodyPart() ;
        	 descriptionPart.setDataHandler( new DataHandler( new StringDataSource( description , "text/html", "eventDescription" ) ) ) ;
             mp.addBodyPart( descriptionPart ) ;
        	 message.setContent( mp ) ;
        	 // send message
        	 Transport.send(message);

        	 } catch (MessagingException me) {
        	     LOGGER.info("Messaging Exception during iCal send invite");
        	      me.printStackTrace();
        	 } catch (Exception ex) {
        	     LOGGER.info("Exception during iCal send invite");
        	     ex.printStackTrace();

        	 }
   }


//  private void loadOAuthProperties(){
//
//	  final Properties oAuthProperties = new Properties();
//	  try {
//	      oAuthProperties.load(App.class.getResourceAsStream("oAuth.properties"));
//	  } catch (IOException e) {
//	      System.out.println("Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
//	      return;
//	  }
//
//	  final String appId = oAuthProperties.getProperty("app.id");
//	  final List<String> appScopes = Arrays
//	      .asList(oAuthProperties.getProperty("app.scopes").split(","));
//  }



   private static class StringDataSource implements DataSource {
	      private String contents ;
	      private String mimetype ;
	      private String name ;


	      public StringDataSource( String contents
	                             , String mimetype
	                             , String name
	                             ) {
	          this.contents = contents ;
	          this.mimetype = mimetype ;
	          this.name = name ;
	      }

	      public String getContentType() {
	          return( mimetype ) ;
	      }

	      public String getName() {
	          return( name ) ;
	      }

	      @SuppressWarnings("deprecation")
			public InputStream getInputStream() {
	          return( new StringBufferInputStream( contents ) ) ;
	      }

	      public OutputStream getOutputStream() {
	          throw new IllegalAccessError( "This datasource cannot be written to" ) ;
	      }
	  }
	public Customer getCustomer() {
	      return customer;
	}

    public void setCustomer(Customer customer) {
	      this.customer = customer;
	}

    public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Appointment getAppointment() {
  		return appointment;
  	}

  	public void setAppointment(Appointment appointment) {
  		this.appointment = appointment;
  	}

	public List<KeyValue> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<KeyValue> agentList) {
		this.agentList = agentList;
	}

	public List<KeyValue> getUserList() {
		return userList;
	}

	public void setUserList(List<KeyValue> userList) {
		this.userList = userList;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public AppointmentDAO getAppointmentDao() {
		return dao;
	}

	public void setAppointmentDao(AppointmentDAO appointmentDao) {
		this.dao = appointmentDao;
	}

	public CommonDAO getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDAO commonDao) {
		this.commonDao = commonDao;
	}

	public AnoudDAO getAnoudDao() {
		return anoudDao;
	}

	public void setAnoudDao(AnoudDAO anoudDao) {
		this.anoudDao = anoudDao;
	}

	public DealsDAO getDealsDao() {
		return dealsDao;
	}

	public void setDealsDao(DealsDAO dealsDao) {
		this.dealsDao = dealsDao;
	}

	public List<? extends Object> getAaData() {
		return aaData;
	}

	public void setAaData(List<? extends Object> aaData) {
		this.aaData = aaData;
	}

	public List<KeyValue> getDateRangeList() {
		return dateRangeList;
	}

	public void setDateRangeList(List<KeyValue> dateRangeList) {
		this.dateRangeList = dateRangeList;
	}

	public List<KeyValue> getCaContactTypList() {
		return caContactTypList;
	}

	public void setCaContactTypList(List<KeyValue> caContactTypList) {
		this.caContactTypList = caContactTypList;
	}

	public List<KeyValue> getCaContactList() {
		return caContactList;
	}

	public void setCaContactList(List<KeyValue> caContactList) {
		this.caContactList = caContactList;
	}

	public DateRange getDateRange() {
		return dateRange;
	}

	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getFlex1() {
		return flex1;
	}

	public void setFlex1(String flex1) {
		this.flex1 = flex1;
	}

	public String getFlex2() {
		return flex2;
	}

	public void setFlex2(String flex2) {
		this.flex2 = flex2;
	}

	public String getFlex3() {
		return flex3;
	}

	public void setFlex3(String flex3) {
		this.flex3 = flex3;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Override
	public void setSession(Map<String, Object> session) {
	      this.session = session;
	      setCompany((String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE));
	      this.userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
	}

    @Override
	public void setServletRequest(HttpServletRequest request) {
	      this.request = request;
	}


}
