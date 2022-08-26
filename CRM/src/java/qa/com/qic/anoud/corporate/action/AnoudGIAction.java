/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.corporate.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
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

import qa.com.qic.anoud.corporate.dao.AnoudGIDAO;
import qa.com.qic.anoud.corporate.vo.CustomerContact;
import qa.com.qic.anoud.corporate.vo.GIPolicyBean;
import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.Customer;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.AuthUtil;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author karthik.j
 *
 */
public class AnoudGIAction extends ActionSupport implements SessionAware, ServletRequestAware {

    private static final Logger LOGGER = LogUtil.getLogger(AnoudGIAction.class);
    private transient HttpServletRequest request;
    private transient Map<String, Object> session;
    private transient AnoudGIDAO dao;
    private List<? extends Object> aaData;
    private List<? extends Object> bbData;
    private List<? extends Object> ccData;
	private transient UserInfo userInfo;
    private transient KeyValue keyValue;
    private transient List<KeyValue> premClaimList;
    private List<KeyValue> keyValueList;
    private transient GIPolicyBean polBean = new GIPolicyBean();


    /**
     * messageType - S: Success, E: Error message - details description about
     * success/error
     */
    private String message;
    private String messageType;

    /**
     * operation - add, edit, view, delete
     */
    private String operation;

    /**
     * Flexible fields
     */
    private String flex1;
    private String flex2;
    private String flex3;
    private String flex4;

    private String company;
    private String civilid;
    private String agentid;
    private String searchValue;
    private String emailTo;
    private transient Customer customer;
    private transient Activity activity;
    private transient CustomerContact contact;
    private String search;
    private transient String crmId;
    private transient String reload;

    public static final String MAIL_HOST = "smtp.qichoappl.com";
    public static final String MAIL_PORT = "587";
    public static final String MAIL_USER_ID = "rioadmin";
    public static final String MAIL_PASSWORD = "8htdQxS4DCZBz78flDnz";
    public static final String MAIL_FROM = "AnoudAdmin<anoudadmin@qicgroup.com.qa>";




    public String openGIPolicyDetailsPage() {
   	  return "corp_policy_page";

    }

    public String openGIClaimDetailsPage() {
	    return "corp_claim_page";
   }

    public String loadGIClaimDetailsList() {

        dao = new AnoudGIDAO(getCrmId());
        aaData = dao.loadGIClaimsList(getCustomer().getCivilId(),polBean.getTransYearFrom(),polBean.getTransYearTo(),polBean.getGiSearchOption());
	    return "corp_data";
    }

    public String loadGIPolicyDetailsList() {
	   dao = new AnoudGIDAO(getCrmId());
	   aaData = dao.loadGIPolicyList(getCustomer().getCivilId(),polBean.getTransYearFrom(),polBean.getTransYearTo(),polBean.getGiSearchOption());
 	   return "corp_data";

    }

   public String openContactForm() {

    	setContact(new CustomerContact());
    	dao = new AnoudGIDAO(getCustomer().getCrmId());
        return "contact_form";

    }

    public String loadHomePage(){

      	    String giSearchBy = null;
    	    String transFromYear = null;
    	    String transToYear = null;
    	    setReload(null);
    	    if(polBean.getGiSearchOption() != null){
    	    	giSearchBy = polBean.getGiSearchOption();
		    }else {
		    	giSearchBy = ApplicationConstants.CUSTOMER360_GI_SEARCH_INSURED;  //Default is 'Insured' search
		    }
    	    if(polBean.getTransYearFrom() != null){
    	    	transFromYear = polBean.getTransYearFrom();
    	    }
    	    if(polBean.getTransYearTo() != null){
    	    	transToYear = polBean.getTransYearTo();
    	    }

    	    polBean = loadGIPolicyClaimInfo(transFromYear,transToYear,giSearchBy);
    	    polBean.setGiSearchOption(giSearchBy);
    	    polBean.setTransYearFrom(transFromYear);
    	    polBean.setTransYearTo(transToYear);

            if ("plugin".equals(getSearch())) {
                  return "corpPlugin";
            } else{
            	  return "corporate";
            }



    }


    public String loadGIPolicyChart() {

    	   dao = new AnoudGIDAO(getCrmId());
    	   try {
               List<KeyValue> list1 = dao.loadGIPremiumVsClaimsFigures(getCustomer().getCivilId(),polBean.getGiSearchOption().trim(),polBean.getTransYearFrom(),polBean.getTransYearTo());
               List<KeyValue> list2 = dao.loadGILOBWiseCountFigures(getCustomer().getCivilId(),polBean.getGiSearchOption().trim(),polBean.getTransYearFrom(),polBean.getTransYearTo());
               List<KeyValue> list3 = dao.loadGIProductBWiseCountFigures(getCustomer().getCivilId().trim(),polBean.getGiSearchOption().trim(),polBean.getTransYearFrom().trim(),polBean.getTransYearTo().trim());

               setAaData(list1);
               setBbData(list2);
               setCcData(list3);

               setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);

           } catch (Exception e) {
           }

        return "corp_data";
    }

    public GIPolicyBean loadGIPolicyClaimInfo(String transFrom, String transTo, String searchBy) {

 	   dao = new AnoudGIDAO(getCrmId());

 	   try {

 		    polBean = dao.loadGIPolicyDetails(getCustomer().getCivilId(),transFrom,transTo,searchBy, getCompany());

 		    if(Integer.parseInt(polBean.getPolicyCnt()) == 0){

 		    	polBean.setPolicyCnt(ApplicationConstants.VALUE_ZERO);
 		    	polBean.setClaimCnt(ApplicationConstants.VALUE_ZERO);
 		    	polBean.setGrossPrem(ApplicationConstants.VALUE_ZERO);
 		    	polBean.setClaimInc(ApplicationConstants.VALUE_ZERO);
 		    	polBean.setClaimOS(ApplicationConstants.VALUE_ZERO);
 		    	polBean.setClaimInc(ApplicationConstants.VALUE_ZERO);
 		    	polBean.setClaimPaid(ApplicationConstants.VALUE_ZERO);
 		    	polBean.setLossRatio(ApplicationConstants.VALUE_ZERO);
 		    }

            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);

        } catch (Exception e) {
        }

     return polBean;
 }

  public String loadGIPolicyData(){

      setPolBean(loadGIPolicyClaimInfo(polBean.getTransYearFrom(),polBean.getTransYearTo(),polBean.getGiSearchOption()));
      return "corp_data" ;
  }



    public String getAppTypeForCrmId(String crmId){
    	  String appType = ApplicationConstants.APP_TYPE;
    	   if(crmId.contains("_")) {
               String s[] = crmId.split("_");
               appType = s[0];
           }
    	   return appType;
    }

    public String openEmailForm() {
        setActivity(new Activity());
        getActivity().setCivilId(getCustomer().getCivilId());
        getActivity().setTo(getCustomer().getEmailId());
        dao = new AnoudGIDAO(getCompany());
        return "corp_email_form";
    }



    public String sendEmailForm() {
        dao = new AnoudGIDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            activity.setUserId(getUserInfo().getUserId());
            sendEMail("", "", activity.getTo(), activity.getSubject(), activity.getMessage());
            activity.setTemplate("003");
            dao.saveLogOrActivity(activity);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);

        } catch (Exception e) {
        }
        return "data";
    }


    public String saveContactForm() {

        dao = new AnoudGIDAO(getCustomer().getCrmId());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);

    try {
       	   int recCount = dao.saveContactDetail(contact, customer.getCivilId());
           if (recCount == 1) {
               setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
           }

    	} catch (Exception e) {
            LOGGER.error("Exception => {}", e);
            setMessage(e.getMessage());
		}

        return "data";
    }



    public String loadGIPolicyContacts() {
        dao = new AnoudGIDAO(getCrmId());
        try {
        	setAaData(dao.loadContactDetails(getCustomer().getCivilId()));
        } catch (Exception e) {
        	  LOGGER.error("Error in loadGIPolicyContacts", e);
        }
        return "data";
    }

    public String anoudCorpIntegration() {

        try {


            String s[] = getCrmId().split("_");
            String at = s[0];
            String comp = s[1];
           // String url;
           // String action = null;
            String url = ApplicationConstants.BASE_URL_ANOUD_APP(at, comp) + "/Login/LoginAction.do?company=" + comp + "&", action = "";

            if (userInfo != null && StringUtils.isNotBlank(getFlex1())) {


                boolean isReRouteUrl = false;
                if ("Member".equals(getFlex1())) {
                    setFlex1(request.getContextPath() + "/customer360.do?company=" + session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE) + "&" + ("MOBILE_NO".equals(getFlex2()) ? "mobile" : "CIVIL_ID".equals(getFlex2()) ? "civilid" : "") + "=" + ("MOBILE_NO".equals(getFlex2()) ? "9" : "") + getFlex3() + "&search=" + getSearch() + "&crmId=" + getCrmId() + "&randId=" + Math.random());
                    return "redirect";
                }
                if (null != getFlex1()) {

                    switch (getFlex1()) {
                        case "01":
                            isReRouteUrl = true;
                            action = "Menu.do?para1=1&para2=D&para3=01&para4=CC&para5=110&para6=001003&civilId=" + getCustomer().getCivilId() + "&mobileNo=" + getCustomer().getMobileNo() + "&emailId=" + getCustomer().getEmailId() + "&randId=" + Math.random();
                            break;
                        case "04":
                            isReRouteUrl = true;
                            action = "Menu.do?para1=1&para2=D&para3=04&para4=CC&para5=140&para6=001004&civilId=" + getCustomer().getCivilId() + "&mobileNo=" + getCustomer().getMobileNo() + "&email=" + getCustomer().getEmailId() + "&randId=" + Math.random();
                            break;
                        case "08":
                            isReRouteUrl = true;
                            action = "Menu.do?para1=1&para2=D&para3=08&para4=CC&para5=185&para6=001005&civilId=" + getCustomer().getCivilId() + "&mobileNo=" + getCustomer().getMobileNo() + "&emailId=" + getCustomer().getEmailId() + "&randId=" + Math.random();
                            break;
                        case "Enquiry":
                            isReRouteUrl = true;
                            action = "EnquirySearch.do?method:searchPolicy&searchType=" + getFlex1().toUpperCase() + "&searchBy=" + getFlex2() + "&searchVal=" + getFlex3() + "&randId=" + Math.random();
                            break;
                        case "Quote":
                            break;
                        case "Policy":
                            isReRouteUrl = true;
                            action = "PolicySearch.do?method:searchPolicy&searchType=" + getFlex1().toUpperCase() + "&searchBy=" + getFlex2() + "&searchVal=" + getFlex3() + "&randId=" + Math.random();
                            break;
                        case "Claim":
                            isReRouteUrl = true;
                            action = "ClaimSearch.do?method:searchClaims&searchType=" + getFlex1().toUpperCase() + "&searchBy=" + getFlex2() + "&searchVal=" + getFlex3() + "&randId=" + Math.random();
                            break;

                    }
                }
                if (!"Y".equals(getOperation())) {
                    setOperation("N");
                }


                String signData = (isReRouteUrl ? "action=" + action + "&headerYN=" + getOperation() + "&" : "") + "division=09&department=09&userId=" + userInfo.getUserLdapId() + "&accessId=" + UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
                String sign = AuthUtil.sign(signData);
                signData = signData.replace(action, URLEncoder.encode(action, "UTF-8"));
                url = url + signData;
                setFlex1(url + "&token=" + sign);


            }

        } catch (Exception e) {

        }

        return "redirect";
    }

   void sendiCalInvite(String operation,String title, String organizer, String[] to, String subject,
    		                Date start , Date end , String invitationId ,
    		                String location , String description ) throws Exception {

        try {

    	   String sender = MAIL_FROM;
    	   String method = null;

    	   SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyyMMdd'T'HHmm'00'" ) ;
    	  // new SimpleDateFormat( "dd-MM-yyyy HH:mm" );

    	   //Get properties object
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

               // Define message
               MimeMessage message = new MimeMessage(session);
               message.setFrom(new InternetAddress(sender));

              // Set TO
              if( to != null && ( to.length > 0 ) ) {
                  InternetAddress[] address = new InternetAddress[ to.length ] ;

              for( int i = 0; i < to.length; i++ ) {
                  address[ i ] = new InternetAddress( to[ i ] ) ;
               }

              message.setRecipients( Message.RecipientType.TO, address ) ;
            }

             // Set subject
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
            messageText.append( dateFormat.format(start)  ) ;
            messageText.append( "\n" +  "DTEND:" ) ;
            messageText.append(  dateFormat.format(end)  ) ;
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
           meetingPart.setDataHandler( new DataHandler( new StringDataSource( messageText.toString(), "text/calendar;name=\"invite.ics\"", "meetingRequest" ) ) ) ;

           mp.addBodyPart( meetingPart ) ;

           MimeBodyPart descriptionPart = new MimeBodyPart() ;
           descriptionPart.setDataHandler( new DataHandler( new StringDataSource( description, "text/html", "eventDescription" ) ) ) ;
           descriptionPart.setContentID( "<eventDescriptionHTML>") ;
           mp.addBodyPart( descriptionPart ) ;

           message.setContent( mp ) ;

          // send message
          Transport.send(message);

             } catch (MessagingException me) {
            	 LOGGER.info("Messaging exception----------------------------------------");
                  me.printStackTrace();

        } catch (Exception ex) {
        	 LOGGER.info(" exception--------in send invite--------------------------------");
                 ex.printStackTrace();

        }
    }


  public void sendEMail(final String from, final String password, String to, String sub, String msg) {

       //Get properties object
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", MAIL_HOST);
        // props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", MAIL_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.transport.protocol", "smtp");

        //get Session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_USER_ID, MAIL_PASSWORD);
            }
        });
        //compose message
        try {

            MimeMessage message = new MimeMessage(session);

            InternetAddress fromAddress = null;
            try {
                fromAddress = new InternetAddress(MAIL_FROM);
            } catch (AddressException e) {
            }
            message.setFrom(fromAddress);
            String delimit = ";";
            if (to.contains(",")) {
                delimit = ",";
            }
            String tos[] = to.split(delimit);
            List<InternetAddress> toList = new ArrayList<>();
            for (String s : tos) {
                toList.add(new InternetAddress(s));
            }
            message.addRecipients(Message.RecipientType.TO, toList.toArray(new InternetAddress[tos.length]));
            message.setSubject(sub);
            message.setText(msg, "UTF-8", "html");
            //send message
            Transport.send(message);
            LOGGER.info("message sent successfully to ", to);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new RuntimeException(e);
        }
    }
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

  public List<? extends Object> getAaData() {
      return aaData;
  }

  public void setAaData(List<? extends Object> aaData) {
      this.aaData = aaData;
  }

  public List<? extends Object> getBbData() {
		return bbData;
	}

	public void setBbData(List<? extends Object> bbData) {
		this.bbData = bbData;
	}


  public List<? extends Object> getCcData() {
		return ccData;
	}

	public void setCcData(List<? extends Object> ccData) {
		this.ccData = ccData;
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

  public String getFlex4() {
      return flex4;
  }

  public void setFlex4(String flex4) {
      this.flex4 = flex4;
  }

  public String getCompany() {
      return company;
  }

  public void setCompany(String company) {
      this.company = company;
  }



  public String getCivilid() {
      return civilid;
  }

  public void setCivilid(String civilid) {
      this.civilid = civilid;
  }

	public String getAgentid() {
      return agentid;
  }

  public void setAgentid(String agentid) {
      this.agentid = agentid;
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

  public List<KeyValue> getKeyValueList() {
      return keyValueList;
  }

  public void setKeyValueList(List<KeyValue> keyValueList) {
      this.keyValueList = keyValueList;
  }




  public String getSearch() {
      return search;
  }

  public void setSearch(String search) {
      this.search = search;
  }




	public CustomerContact getContact() {
		return contact;
	}

	public void setContact(CustomerContact contact) {
		this.contact = contact;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public KeyValue getKeyValue() {
      return keyValue;
  }

  public void setKeyValue(KeyValue keyValue) {
      this.keyValue = keyValue;
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


  public GIPolicyBean getPolBean() {
		return polBean;
	}

	public void setPolBean(GIPolicyBean polBean) {
		this.polBean = polBean;
	}

	public String getSearchValue() {
      return searchValue;
  }

  public void setSearchValue(String searchValue) {
      this.searchValue = searchValue;
  }

	public String getCrmId() {
      return crmId;
  }

  public void setCrmId(String crmId) {
      this.crmId = crmId;
  }

	public String getReload() {
		return reload;
	}

	public void setReload(String reload) {
		this.reload = reload;
	}


	public List<KeyValue> getPremClaimList() {
		return premClaimList;
	}

	public void setPremClaimList(List<KeyValue> premClaimList) {
		this.premClaimList = premClaimList;
	}

}



