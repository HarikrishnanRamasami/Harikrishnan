/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.action;

import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.Customer;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.anoud.vo.TRiskMedical;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.dao.TaskDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.util.TypeConstants.CallType;
import qa.com.qic.common.vo.CrmCallLog;
import qa.com.qic.common.vo.CrmCallLog.UserAssociations;
import qa.com.qic.common.vo.FlexBean;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.AuthUtil;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author Ravindar Singh T
 * <a href="mailto:ravindar.singh@qicgroup.com.qa">ravindar.singh@qicgroup.com.qa</a>
 */
public class AnoudAction extends ActionSupport implements SessionAware, ServletRequestAware {

    private static final Logger LOGGER = LogUtil.getLogger(AnoudAction.class);
    private transient HttpServletRequest request;
    private transient Map<String, Object> session;
    private transient AnoudDAO dao;
    private List<? extends Object> aaData;
    private transient UserInfo userInfo;
    private transient List<KeyValue> categoryList;
    private transient List<KeyValue> subjectList;
    private transient List<TRiskMedical> policyList;
    private transient List<TRiskMedical> memberList;
    private transient KeyValue keyValue;
    private transient List<KeyValue> customerTypeList;

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
    private String mobile;
    private String civilid;
    private String agentid;
    private String memberId;
    private Integer amount;
    private String qatarId;
    private String diagnosis;
    private String details;
    private String diagCode;
    private String searchValue;

    private transient Customer customer;
    private transient TRiskMedical riskMedical;
    private transient Activity activity;
    private transient CrmCallLog callLog;
    private transient List<KeyValue> keyValueList;
    private List<KeyValue> countryList;
    private List<KeyValue> cityList;
    private List<KeyValue> genderList;
    private List<KeyValue> occupationList;
    private List<KeyValue> nationalityList;
    private List<KeyValue> sourceList;
    private List<KeyValue> custTypeList;
    private transient List<KeyValue> diagnosisList;
    private transient TaskDAO taskDao;
    //DataTables params
    private String search;
    private Integer start;
    private Integer length;
    private Object[][] order;
    private Integer draw;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private String mobileNo;


    private String crmId;
    private String dealId;
    private String origin;

    final static Map<String, String> AMEYO_CALL_TYPE = new HashMap<String, String>() {
        {
            put("inbound.dial.association", CallType.INBOUND.getCode());
            put("manualdial.association", CallType.OUTBOUND.getCode());
            put("transfer.association", CallType.FORWARDED.getCode());//CallType.MISSED.getCode()
        }
    };

    private transient InputStream inputStream;

    /**
     * Ameyo CDR information
     */
    // Physical Extension where call will land.
    private transient Integer extension;
    // CallerID of the Call.
    private transient String callID;
    // Call datetime YYYYMMDD HH:MM:SS
    private transient String datetime;
    // I – Incoming / D - Dial from Dialer / M - Missed call
    private transient String callType;
    // Duration of the call
    private transient Integer duration;

    // Ameyo integration
    private transient String systemDisposition;
    private transient String dispositionCode;
    // Unique Id
    private transient String crtObjectId;
    // Application type. R - Retail, Q - QLM
    private transient String appType;
    // Dialed Number Identification Service
    private transient String dnis;
    // User id's (comma seprated value) of agent who missed the call
    private transient String userAssociations;
    // How long agent didn't pick the call
    private transient Integer callWaitTime;
    // Campaign ID
    private transient String campaignId;

    public Integer getExtension() {
        return extension;
    }

    public void setExtension(Integer extension) {
        this.extension = extension;
    }

    public String getCallID() {
        return callID;
    }

    public void setCallID(String callID) {
        this.callID = callID;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getSystemDisposition() {
        return systemDisposition;
    }

    public void setSystemDisposition(String systemDisposition) {
        this.systemDisposition = systemDisposition;
    }

    public String getDispositionCode() {
        return dispositionCode;
    }

    public void setDispositionCode(String dispositionCode) {
        this.dispositionCode = dispositionCode;
    }

    public String getCrtObjectId() {
        return crtObjectId;
    }

    public void setCrtObjectId(String crtObjectId) {
        this.crtObjectId = crtObjectId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    public String getUserAssociations() {
        return userAssociations;
    }

    public void setUserAssociations(String userAssociations) {
        this.userAssociations = userAssociations;
    }

    public Integer getCallWaitTime() {
        return callWaitTime;
    }

    public void setCallWaitTime(Integer callWaitTime) {
        this.callWaitTime = callWaitTime;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String callInfo() {
    	//commented for corporate
        //LOGGER.info("Company: {}, Extension: {}, CallID: {}, Datetime: {}, CallType: {}, Duration: {}, Campaign ID: {}", new Object[]{getCompany(), getExtension(), getCallID(), getDatetime(), getCallType(), getDuration(), getCampaignId()});
        LOGGER.info("Company: {}, Extension: {}, CallID: {}, Datetime: {}, CallType: {}, Duration: {}", new Object[]{getCompany(), getExtension(), getCallID(), getDatetime(), getCallType(), getDuration()});
        LOGGER.info("App Type: {}, Agent Id: {}, Crt Object Id: {}, Disposition Code: {}, System Disposition: {}, Dnis: {}", new Object[]{getAppType(), getAgentid(), getCrtObjectId(), getDispositionCode(), getSystemDisposition(), getDnis()});
        LOGGER.info("UserAssociations: {}, Call Wait Time: {}", new Object[]{getUserAssociations(), getCallWaitTime()});
        if (StringUtils.isBlank(getCompany()) || getExtension() == null || StringUtils.isBlank(getCallID()) || getCallID().length() <= 7) {
            return "bad_request";
        }
        long uptime = getDuration();
        long hours, minutes, seconds;
        if (StringUtils.isNotBlank(getCrtObjectId())) {
            hours = TimeUnit.MILLISECONDS.toHours(uptime);
            uptime -= TimeUnit.HOURS.toMillis(hours);
            minutes = TimeUnit.MILLISECONDS.toMinutes(uptime);
            uptime -= TimeUnit.MINUTES.toMillis(minutes);
            seconds = TimeUnit.MILLISECONDS.toSeconds(uptime);
        } else {
            hours = TimeUnit.SECONDS.toHours(uptime);
            uptime -= TimeUnit.HOURS.toSeconds(hours);
            minutes = TimeUnit.SECONDS.toMinutes(uptime);
            uptime -= TimeUnit.MINUTES.toSeconds(minutes);
            seconds = TimeUnit.SECONDS.toSeconds(uptime);
        }

        DecimalFormat nf = new DecimalFormat("00");
        String time = nf.format(hours) + ":" + nf.format(minutes) + ":" + nf.format(seconds);
        //setCompany(getUserInfo().getCompanyCode());
        dao = new AnoudDAO(getCompany());
        CrmCallLog bean = new CrmCallLog();
        bean.setCclExtId(getExtension());
        bean.setCclCallNo(getCallID());
        bean.setCclCallDt(getDatetime());
        bean.setCclType(getCallType());
        bean.setCclDuration(getDuration());
        bean.setCclDurationDesc(time);
        //commented  for corporate
        //bean.setCclCrmId(ApplicationConstants.getCrmId(getCampaignId()));
        if (ApplicationConstants.APP_TYPE_RETAIL.equalsIgnoreCase(getAppType()) || ApplicationConstants.APP_TYPE_QLM.equalsIgnoreCase(getAppType())) {
            bean.setCclCrUid(getAgentid());
            bean.setCclCallCode(getDispositionCode());
            bean.setCclFilePath(getCrtObjectId());
            bean.setCclFlex1(getSystemDisposition());
            bean.setCclFlex2(getDnis());

            // Call wait time - Converting from milliseconds to readable time
            if (getCallWaitTime() != null) {
                uptime = getCallWaitTime();
                hours = TimeUnit.MILLISECONDS.toHours(uptime);
                uptime -= TimeUnit.HOURS.toMillis(hours);
                minutes = TimeUnit.MILLISECONDS.toMinutes(uptime);
                uptime -= TimeUnit.MINUTES.toMillis(minutes);
                seconds = TimeUnit.MILLISECONDS.toSeconds(uptime);
                time = nf.format(hours) + ":" + nf.format(minutes) + ":" + nf.format(seconds);
                bean.setCclFlex3(time);
            }

            Gson gson = new Gson();
            List<UserAssociations> list = gson.fromJson(getUserAssociations(), new TypeToken<List<UserAssociations>>() {
            }.getType());
            if (list != null && !list.isEmpty()) {
                for (UserAssociations ua : list) {
                    ua.setUserId(ua.getUserId().substring(6));
                    if ("M".equals(getCallType())) {
                        ua.setCrmType(CallType.MISSED.getCode());
                    } else {
                        ua.setCrmType(AMEYO_CALL_TYPE.get(ua.getAssocitionType()));
                    }
                    if ((ua.getDispositionCode() == null || "".equals(ua.getDispositionCode())) && !"O".equals(bean.getCclType())) {
                        LOGGER.info("Disposition code missing for {} call {}", new Object[]{ua.getUserId(), getCallID()});
                        ua.setCrmType(CallType.MISSED.getCode());
                    } else {
                        ua.setDispositionCode(ua.getDispositionCode().substring(0, 3));
                        if ("wra".equals(ua.getDispositionCode())) {
                            ua.setDispositionCode("998");
                        }
                    }
                }
                bean.setUserAssociations(list);
            }
            gson = null;
        } else {
            bean.setCclCrUid(String.valueOf(getExtension()));
        }

        try {
            String format = null;
            if (getDatetime().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
                format = "yyyy-MM-dd HH:mm:ss";
            } else if (getDatetime().matches("\\d{8} \\d{2}:\\d{2}:\\d{2}")) {
                format = "yyyyMMdd HH:mm:ss";
            }
            SimpleDateFormat sdf1 = new SimpleDateFormat(format);
            bean.setCclCallDtTime(sdf1.parse(getDatetime()));
        } catch (Exception e) {
            bean.setCclCallDtTime(new Date());
        }
        if(ApplicationConstants.COMPANY_CODE_DUBAI.equals(getCompany()) ||
                ApplicationConstants.COMPANY_CODE_OMAN.equals(getCompany())) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(bean.getCclCallDtTime());
            cal.add(Calendar.HOUR_OF_DAY, 1);
            bean.setCclCallDtTime(cal.getTime());
        }
        activity = dao.saveCallLog(bean);

        /*dao = new AnoudDAO(getCompany());
         setKeyValueList(dao.getMAppCodes(FieldConstants.AppCodes.CRM_LOG_TYPE));
         setCategoryList(dao.getMAppCodes(FieldConstants.AppCodes.CRM_CATEGORY));
         setSubjectList(dao.getMAppCodes(FieldConstants.AppCodes.CRM_ACT_TYPE));
         aaData = new ArrayList<KeyValue>();
         if (activity.getCivilId() != null) {
         LOGGER.info("Civil id {} found for mobile {}", activity.getCivilId(), getCallID());
         setCivilid(activity.getCivilId());
         activity.getCallLog().setCclCallDt(bean.getCclCallDt());
         } else {
         activity.setCallLog(bean);
         //inputStream = new ByteArrayInputStream("Activity registered".getBytes());
         //return INPUT;
         }
         return SUCCESS;*/
        return "ok_request";
    }

    public String loadHomePage() {
        String result = ERROR;
        // if search equal to plugin then return the customer360 page without header
        getUserInfo().setActiveMenu(TypeConstants.Menu.CUSTOMER360);
        String searchBy = null, searchValue = null;

        dao = new AnoudDAO(getCompany());
        //commented for corporate
        /*String s[] = getCrmId().split("_");
        String at = s[0];
        String comp = s[1];
        if (ApplicationConstants.APP_TYPE_QLM.equals(at)) {
            if (StringUtils.isNotBlank(getCivilid())) {
                dao = new AnoudDAO(getCrmId());
                policyList = dao.loadMemberPolicyDetails(getCivilid());
                if (policyList != null && !policyList.isEmpty()) {
                    memberList = dao.loadMemberList(getPolicyList().get(0).getTrmTransId(), getPolicyList().get(0).getTrmMemSrNo());
                }
            }
        }*/

        if (StringUtils.isNotBlank(getCivilid())) {
            searchBy = "CIVIL_ID";
            searchValue = getCivilid();
        } else if (StringUtils.isNotBlank(getMobile())) {
            if ((getCrtObjectId() == null && !"plugin".equals(getSearch()) && !"view".equals(getSearch())) || (getCrtObjectId() != null && !getCrtObjectId().equals(session.get("CRM_POPUP")))) {
                saveIncomingCall();
            }
            searchBy = "MOBILE_NO";
            searchValue = getMobile();
            if (searchValue.startsWith("9")) {
                searchValue = searchValue.substring(1);
            }
        }
        if (searchBy != null) {
            dao = new AnoudDAO(getCompany());
            //commented for corporate
            //List<Customer> list = dao.loadCutomerDetials(getCrmId(), searchBy, searchValue);
            List<Customer> list = dao.loadCutomerDetials(searchBy, searchValue);
            if (list != null && list.size() > 0) {
                customer = list.get(0);
            }
        }
        if (customer != null) {
        	//commented for corporate
            //if (ApplicationConstants.APP_TYPE_QLM.equals(at)) {
        	if (ApplicationConstants.APP_TYPE.equals(ApplicationConstants.APP_TYPE_QLM)) {
                policyList = dao.loadMemberPolicyDetails(customer.getCivilId());
                if(null == policyList) {
                    policyList = new ArrayList<>();
                }
                if (!policyList.isEmpty()) {
                    memberList = dao.loadMemberList(getPolicyList().get(0).getTrmTransId(), getPolicyList().get(0).getTrmMemSrNo());
                }
                if(null == memberList) {
                    memberList = new ArrayList<>();
                }
                if (getCrtObjectId() != null || "plugin".equals(getSearch())) {
                    result = "medicalPlugin";
                } else if ("modal".equals(getSearch())) {
                    result = "medicalModal";
                } else {
                    result = "medical";
                }
              //commented for corporate
            //} else if (ApplicationConstants.APP_TYPE_RETAIL.equals(at)) {
        	} else if (ApplicationConstants.APP_TYPE.equals(ApplicationConstants.APP_TYPE_RETAIL)) {
                if (getCrtObjectId() != null || "plugin".equals(getSearch())) {
                    result = "retailPlugin";
                } else if ("modal".equals(getSearch())) {
                    result = "retailModal";
                } else {
                    result = "retail";
                }
              //added for corporate
            } else if (ApplicationConstants.APP_TYPE_GI.equals(ApplicationConstants.APP_TYPE)) {
                    result = "corporate";
            }
        }
        if (customer == null && (getCrtObjectId() != null || "plugin".equals(getSearch()))) {
            result = "notfound";
        }
        return result;
    }

    public String linkCustomerNewNumber() {
        LOGGER.debug("Civil id {} adding a new mobile number {}. Replacing {}", new Object[]{getCallLog().getCclCivilId(), getCallLog().getCclCallNo(), getCallLog().getCclType()});
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        dao = new AnoudDAO(getCompany());
        try {
            int recCent = dao.linkCustomerNewNumber(getCallLog());
            if (recCent == 1) {
                setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            } else {
                setMessage("Unable to link the number");
            }
        } catch (Exception e) {
            LOGGER.error("Exception => {}", e);
            setMessage(e.getMessage());
        }
        return "data";
    }

    public String openCustomerForm() {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        sourceList = dao.getMAppParameter(FieldConstants.AppParameter.CRM_CUST_SRC);
        countryList = dao.getMAppCodes(FieldConstants.AppCodes.COUNTRY);
        genderList = dao.getMAppParameter(FieldConstants.AppParameter.GENDER);
        nationalityList = dao.getMAppCodes(FieldConstants.AppCodes.NATIONALITY);
        occupationList = dao.getMAppCodes(FieldConstants.AppCodes.OCCUPATION);
        custTypeList = dao.getMAppCodes(FieldConstants.AppCodes.PROMO_CATG);
        if (("edit".equals(getOperation()) || "view".equals(getOperation())) && StringUtils.isNoneBlank(getCustomer().getCivilId())) {
            customer = dao.loadCustomerDetailByCivilId(getCustomer().getCivilId());
            if(null != customer) {
                if (customer.getCountry() != null && !customer.getCountry().isEmpty()) {
                    cityList = dao.getMAppCodes(FieldConstants.AppCodes.STATE, customer.getCountry(), FieldConstants.AppCodes.COUNTRY);
                }
                //commented for corporate
                /*if(null != customer.getCrmId()) {
                    customer.setCrmIds(customer.getCrmId().split(","));
                }*/
            }
        }
        if (cityList == null) {
            cityList = new ArrayList<>();
        }
        return "form";
    }

    public String loadCityList() {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        cityList = dao.getMAppCodes(FieldConstants.AppCodes.STATE, customer.getCountry(), FieldConstants.AppCodes.COUNTRY);
        return SUCCESS;
    }

    public String saveCustomerForm() {
        dao = new AnoudDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);

        try {
            if ("add".equals(getOperation()) || "edit".equals(getOperation())) {
                customer.setCrUid(getUserInfo().getUserId());
                customer.setUpdUid(getUserInfo().getUserId());
                //commented for corporate
                /*if(null == customer.getCrmIds() || customer.getCrmIds().length < 1) {
                    customer.setCrmId(null);
                } else {
                    customer.setCrmId(String.join(",", customer.getCrmIds()));
                }*/
                int recCent = dao.saveCustomerDetail(customer, getOperation());
                if (recCent == 1) {
                    setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception => {}", e);
            setMessage(e.getMessage());
        }
        return "data";
    }

    public String showPoliciesFigure() {
    	//commented for corporate
        //dao = new AnoudDAO(getCustomer().getCrmId());
        dao = new AnoudDAO(getCompany());
        try {
        	//commented for corporate
            //List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.POLICIES, null);
            List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.POLICIES);
            setAaData(list);
            if (list != null && !list.isEmpty()) {

            }
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    public String openLogPage() {
        dao = new AnoudDAO(getCompany());
        List<KeyValue> list = dao.loadLogActivities(getCustomer().getCivilId());
        setAaData(list);
        return "log_list";
    }

    public String loadLogDetails() {
        dao = new AnoudDAO(getCompany());
        List<KeyValue> list = dao.loadLogActivities(getCustomer().getCivilId());
        setAaData(list);
        return "data";
    }

    public String showQuotesFigure() {
    	//commented for corporate
        //dao = new AnoudDAO(getCustomer().getCrmId());
        dao = new AnoudDAO(getCompany());
        try {
        	//commented for corporate
            //List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.QUOTES, null);
        	List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.QUOTES);
            setAaData(list);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    public String showClaimsFigure() {
    	//commented for corporate
        //dao = new AnoudDAO(getCustomer().getCrmId());
    	dao = new AnoudDAO(getCompany());
        try {
        	//commented for corporate
            //List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.CLAIMS, null);
        	List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.CLAIMS);
            setAaData(list);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    public String showTotalAndLossRatioFigure() {
    	//commented for corporate
        //dao = new AnoudDAO(getCustomer().getCrmId());
        dao = new AnoudDAO(getCompany());
        try {
        	//commented for corporate
            //List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.TOTAL_AND_LOSSRATIO, null);
        	List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.TOTAL_AND_LOSSRATIO);
            setAaData(list);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    public String loadProductivityFigure() {
    	//commented for corporate
        //dao = new AnoudDAO(getCustomer().getCrmId());
    	dao = new AnoudDAO(getCompany());
        try {
        	//commented for corporate
            //List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.PRODUCTIVITY, null);
        	List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.PRODUCTIVITY);
            setAaData(list);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    public String loadLogFigure() {
    	//commented for corporate
        //dao = new AnoudDAO(getCustomer().getCrmId());
        dao = new AnoudDAO(getCompany());
        try {
        	//commented for corporate
            //List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.ACTIVITIES, getCustomer().getCrmId());
            List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), AnoudDAO.DashboardFigures.ACTIVITIES);
            setAaData(list);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    public String loadOffersFigure() {
    	//commented for corporate
        //dao = new AnoudDAO(getCustomer().getCrmId());
        try {
            if (StringUtils.isBlank(getFlex1())) {
                setFlex1("0");
            }
            if (StringUtils.isBlank(getFlex2())) {
                setFlex2("0");
            }
            //commented for corporate
            //List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), new Double(getFlex1()), new Double(getFlex2()), AnoudDAO.DashboardFigures.ELIGIBLE_OFFERS, null);
            List<KeyValue> list = dao.loadDashboardFigures(getCustomer().getCivilId(), new Double(getFlex1()), new Double(getFlex2()), AnoudDAO.DashboardFigures.ELIGIBLE_OFFERS);
            setAaData(list);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    public String anoudAppIntegration() {
        try {
        	//commented for corporate
            /*String s[] = getCrmId().split("_");
            String at = s[0];
            String comp = s[1];
            String url = ApplicationConstants.BASE_URL_ANOUD_APP(at, comp) + "/Login/LoginAction.do?company=" + comp + "&", action = "";*/
        	String url = ApplicationConstants.BASE_URL_ANOUD_APP(ApplicationConstants.APP_TYPE, (String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE)) + "/Login/LoginAction.do?company=" + session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE) + "&", action = "";
            if (userInfo != null && StringUtils.isNotBlank(getFlex1())) {
                boolean isReRouteUrl = false;
                if ("Member".equals(getFlex1())) {
                	//commented for corporate
                    //setFlex1(request.getContextPath() + "/customer360.do?company=" + session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE) + "&" + ("MOBILE_NO".equals(getFlex2()) ? "mobile" : "CIVIL_ID".equals(getFlex2()) ? "civilid" : "") + "=" + ("MOBILE_NO".equals(getFlex2()) ? "9" : "") + getFlex3() + "&search=" + getSearch() + "&crmId=" + getCrmId() + "&randId=" + Math.random());
                	setFlex1(request.getContextPath() + "/customer360.do?company=" + session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE) + "&" + ("MOBILE_NO".equals(getFlex2()) ? "mobile" : "CIVIL_ID".equals(getFlex2()) ? "civilid" : "") + "=" + ("MOBILE_NO".equals(getFlex2()) ? "9" : "") + getFlex3() + "&search=" + getSearch() + "&randId=" + Math.random());
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
                        case "Policy":
                        case "Claim":
                        case "Quote":
                            isReRouteUrl = true;
                            action = "Search.do?method:getSearchResult&searchCatagory=" + getFlex1() + "&searchBy=" + getFlex2() + "&searchValue=" + getFlex3() + "&randId=" + Math.random();
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

    public String saveMemberPreApproval() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
    	dao = new AnoudDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        keyValue = dao.callPharmacyApproval(getMemberId(), getFlex2(), getFlex4(), getFlex3(), getAmount(), getUserInfo().getUserId(), getQatarId());
        if (StringUtils.isBlank(keyValue.getText())) {
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            setFlex1(keyValue.getInfo1());
        } else {
            setMessage(keyValue.getText());
        }
        return SUCCESS;
    }

    //For QLM integration
    public String qlmAppIntegration() {
        try {
        	//commented for corporate
            /*String s[] = getCrmId().split("_");
            String at = s[0];
            String comp = s[1];
            String url = ApplicationConstants.BASE_URL_ANOUD_APP(at, comp) + "/Login/LoginSubmit.do?company=" + comp + "&", action = "";*/
        	String url = ApplicationConstants.BASE_URL_ANOUD_APP(ApplicationConstants.APP_TYPE, (String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE)) + "/Login/LoginSubmit.do?company=" + session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE) + "&", action = "";
            if (userInfo != null && StringUtils.isNotBlank(getFlex1())) {
                boolean isReRouteUrl = false;
                if (null != getFlex1()) {
                    switch (getFlex1()) {
                        case "SSO":
                            break;
                        case "Policy":
                            isReRouteUrl = true;
                            action = "/Life/Search/PolicySearch.do?method:searchPolicy&searchType=POLICY&appFrom=CRM&searchBy=" + getFlex2() + "&searchVal=" + getFlex3() + "&randId=" + Math.random();
                            break;
                        case "Quote":
                            isReRouteUrl = true;
                            action = "/Life/Search/QuoteSearch.do?method:searchQuotation&searchType=QUOTE&appFrom=CRM&searchBy=" + getFlex2() + "&searchVal=" + getFlex3() + "&randId=" + Math.random();
                            break;
                        case "Member":
                            isReRouteUrl = true;
                            action = "/anoundMed/LoadMemberSearchDetail.do?method:loadMemberSearchDetail&searchType=MEMBER&appFrom=CRM&searchBy=" + getFlex2() + "&searchValue=" + getFlex3() + "&policyNo=" + getFlex4() + "&randId=" + Math.random();
                            break;
                        case "Claim":
                            isReRouteUrl = true;
                            action = "/Life/Search/ClaimSearch.do?method:searchClaim&searchType=CLAIM&appFrom=CRM&searchBy=" + getFlex2() + "&searchVal=" + getFlex3() + "&randId=" + Math.random();
                            break;
                        case "MasterBatch":
                            isReRouteUrl = true;
                            action = "/AnoudClaims/LoadClmTrackingDetails.do?method:loadClmTrackingDetails&medClmMasterBantch.mbRefNo=" + getFlex2() + "&appFrom=CRM&medClmMasterBantch.mbTransId=" + Long.valueOf(getFlex3()) + "&medClmMasterBantch.mbStatus=" + getFlex4() + "&medClmMasterBantch.mbType=R&randId=" + Math.random();
                            break;
                        case "PreApproval":
                            isReRouteUrl = true;
                            action = "/anoundMed/loadPACodeDetails.do?method:loadPreApprovalProcessForm&preApproval.id.paTransId=" + Long.valueOf(getFlex2()) + "&appFrom=CRM&preApproval.id.paTranSrNo=" + Long.valueOf(getFlex3()) + "&oper=view&randId=" + Math.random();
                            break;
                        case "Enquiry":
                            isReRouteUrl = true;
                            action = "/MemberEnquiry/EnquirySubmission.do?method:loadEnquirySubmissionForm&enquiries.meRefId=" + Long.valueOf(getFlex2()) + "&appFrom=CRM&oper=view&randId=" + Math.random();
                            break;
                        case "ProviderSearch":
                            isReRouteUrl = true;
                            action = "/AnoudMember/ProviderSearch.do?method:loadProviderSearch&appFrom=CRM&randId=" + Math.random();
                            break;
                        case "AddClaim":
                            isReRouteUrl = true;
                            action = "/AnoudClaims/openMemberClaimInimationPage.do?method:openMemberClaimPage&searchBy=" + getFlex2() + "&searchValue=" + getFlex3() + "&randId=" + Math.random();
                            break;
                        case "ProviderManagement":
                            isReRouteUrl = true;
                            action = "/Masters/ProviderMaster.do?method:loadProvider&appFrom=CRM&searchValue=" + getFlex2() + "&randId=" + Math.random();
                            break;
                        case "ProviderPreApproval":
                            isReRouteUrl = true;
                            if (StringUtils.isNoneBlank(getMemberId())) {
                                flex3 = "TRM_MEM_ID";
                                flex4 = getMemberId();
                                dao = new AnoudDAO(getCompany());
                                keyValue = dao.getGeneralApproval(getMemberId(), flex3);
                            } else {
                                flex3 = "TRM_CIVIL_ID";
                                flex4 = getQatarId();
                                dao = new AnoudDAO(getCompany());
                                keyValue = dao.getGeneralApproval(getQatarId(), flex3);
                            }
                            action = "/anoundMed/loadMemberDetailsPage.do?method:loadMemberDetail&searchBy=" + getFlex3() + "&searchValue=" + getFlex4() + "&policyNo=" + keyValue.getKey() + "&provider=" + getFlex2() + "&randId=" + Math.random();
                            break;
                        case "ProviderPharmacyPreApproval":
                            isReRouteUrl = true;
                            action = "/anoundMed/loadPreApprovalProcessForm.do?method:loadPreApprovalProcessForm&preApproval.id.paTransId=" + Long.valueOf(keyValue.getInfo1()) + "&preApproval.id.paTranSrNo=" + Long.valueOf("0") + "&randId=" + Math.random();
                            break;
                        case "Eclaims":
                            isReRouteUrl = true;
                            action = "/Intimation/BatchTracking.do?method:batchTracking&appFrom=CRM&searchValue=" + getFlex2() + "&randId=" + Math.random();
                            break;
                        case "WhatsAppMemberClaim":
                            isReRouteUrl = true;
                            action = "/AnoudClaims/openMemberClaimInimationPage.do?method:openMemberClaimPage&appFrom=CRM&searchBy=" + getFlex2() + "&searchValue=" + getFlex3() + "&memDocId=" + getDetails() + "&policyNo=" + getFlex4() + "&randId=" + Math.random();
                            break;

                    }
                }
                if (!"Y".equals(getOperation())) {
                    setOperation("N");
                }
                String signData = (isReRouteUrl ? "action=" + action + "&headerYN=" + getOperation() + "&" : "") + "division=90&department=03&userId=" + userInfo.getUserLdapId() + "&accessId=" + UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
                String sign = AuthUtil.sign(signData);
                signData = signData.replace(action, URLEncoder.encode(action, "UTF-8"));
                url = url + signData;
                setFlex1(url + "&token=" + sign);
            }
        } catch (Exception e) {
        }
        return "redirect";
    }

    public String openQuoteDetailsPage() {
        return "quote_page";
    }

    public String openPolicyDetailsPage() {
        return "policy_page";
    }

    public String openClaimDetailsPage() {
        return "claim_page";
    }

    /**
     * Customer's quotation details
     *
     * @return navigation
     * @author Ravindar Singh T
     */
    public String loadQuoteDetailList() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
    	dao = new AnoudDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            aaData = dao.loadQuoteList(getCustomer().getCivilId());
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    /**
     * Customer's active and inactive policy details
     *
     * @return navigation
     * @author Ravindar Singh T
     */
    public String loadPolicyDetailList() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
    	dao = new AnoudDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            aaData = dao.loadPolicyList(getCustomer().getCivilId());
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    /**
     * Customer's claim details
     *
     * @return navigation
     * @author Ravindar Singh T
     */
    public String loadClaimDetailList() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
        dao = new AnoudDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            aaData = dao.loadClaimsList(getCustomer().getCivilId());
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    public String openSmsForm() {
        dao = new AnoudDAO(getCompany());
        setActivity(new Activity());
        getActivity().setCivilId(getCustomer().getCivilId());
        getActivity().setTo(getCustomer().getMobileNo());
        getActivity().setTo(getCustomer().getMobileNo1());
        getActivity().setTo(getCustomer().getMobileNo2());
        getActivity().setLanguage("E");
        keyValueList = dao.getMAppCodes(FieldConstants.AppCodes.CRM_HAND_SMS);
        for (KeyValue kv : keyValueList) {
            if (kv.getInfo2() != null) {
                kv.setInfo2(kv.getInfo2().replaceAll("\r\n|\n\r|\n|\r", "<br>"));
            }
            if (kv.getInfo3() != null) {
                kv.setInfo3(kv.getInfo3().replaceAll("\r\n|\n\r|\n|\r", "<br>"));
            }
        }
        return "sms_form";
    }

    public void validateSaveSmsForm() {

    }

    public String saveSmsForm() {
        dao = new AnoudDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            activity.setUserId(getUserInfo().getUserId());
            dao.callCRMPackage(activity, CommonDAO.ActivityTypes.SMS);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    public String openEmailForm() {
        setActivity(new Activity());
        getActivity().setCivilId(getCustomer().getCivilId());
        getActivity().setTo(getCustomer().getEmailId());
        dao = new AnoudDAO(getCompany());
        keyValueList = dao.loadEmailTemplateList();
        return "email_form";
    }

    public String saveEmailForm() {
        dao = new AnoudDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            activity.setUserId(getUserInfo().getUserId());
            dao.callCRMPackage(activity, CommonDAO.ActivityTypes.EMAIL);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return "data";
    }

    public String openLogForm() {
        dao = new AnoudDAO(getCompany());
        setActivity(new Activity());
        getActivity().setCivilId(getCustomer().getCivilId());
      //commented for corporate
        /*if(StringUtils.isNotBlank(getCrmId()))
            getActivity().setCrmId(getCrmId());
        if(StringUtils.isNotBlank(getDealId()))
            getActivity().setDealId(getDealId());*/

        setKeyValueList(dao.getMAppCodes(FieldConstants.AppCodes.CRM_LOG_TYPE));
        KeyValue temp = null;
        for (KeyValue kv : getKeyValueList()) {
            if (CallType.MISSED.getCode().equals(kv.getKey())) {
                temp = kv;
                break;
            }
        }
        if (temp != null) {
            getKeyValueList().remove(temp);
        }
        setCategoryList(dao.getMAppCodes(FieldConstants.AppCodes.CRM_CATEGORY));
        setSubjectList(dao.getMAppCodes(FieldConstants.AppCodes.CRM_ACT_TYPE));
        aaData = new ArrayList<KeyValue>();
        return "log_form";
    }

    public String saveLogForm() {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            activity.setUserId(getUserInfo().getUserId());
            dao.callCRMPackage(activity, CommonDAO.ActivityTypes.ACTIVITY);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            if (ApplicationConstants.MESSAGE_TYPE_SUCCESS.equals(getMessageType()) && StringUtils.isNoneBlank(activity.getCoCreateTaskYn()) && "1".equals(activity.getCoCreateTaskYn())) {
                createTask();
            }
        } catch (Exception e) {
        }
        return "data";
    }

    public String callCustomer() {
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            if (getCustomer().getMobileNo() != null && getCustomer().getMobileNo().matches("^[\\d]+$")) {
                if (null != getUserInfo().getCompanyCode()) {
                    switch (getUserInfo().getCompanyCode()) {
                        case ApplicationConstants.COMPANY_CODE_BEEMA:
                            /*TeleService ts = new TeleService();
                            ts.getTeleServiceSoap().dialCall(getUserInfo().getUserId(), getUserInfo().getUserTelNo(), "", "", "9" + getCustomer().getMobileNo());
                            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                            break;*/
                        case ApplicationConstants.COMPANY_CODE_DOHA:
                        case ApplicationConstants.COMPANY_CODE_DUBAI:
                        case ApplicationConstants.COMPANY_CODE_KUWAIT:
                        case ApplicationConstants.COMPANY_CODE_OMAN:
                        case ApplicationConstants.COMPANY_CODE_MED_DOHA:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("{}", e);
        }
        return "data";
    }

    private int createTask() {
        int recCnt = 0;
        taskDao = new TaskDAO(getUserInfo().getCompanyCode());
        activity.getTask().setCtRefNo(activity.getCivilId());
        //commented for corporate
        //activity.getTask().setCtRefId(activity.getCivilId());
        activity.getTask().setCtStatus("P");
        activity.getTask().setCtMessage(activity.getMessage());
        activity.getTask().setCtCrUid(getUserInfo().getUserId());
        activity.getTask().setCtModule("000");
        activity.getTask().setCtAssignedTo(getUserInfo().getUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        activity.getTask().setCtAssignedDt(sdf.format(new Date()));
        activity.getTask().setCtPriority("N");

      //commented for corporate
        /*if(StringUtils.isNotBlank(activity.getCrmId()))
            activity.getTask().setCtCrmId(activity.getCrmId());

        if(StringUtils.isNotBlank(activity.getDealId()))
                activity.getTask().setCtDealId(activity.getDealId());*/

        recCnt = taskDao.saveTask(activity.getTask(), "add");
        if (recCnt > 0) {
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            setMessage("Activity and task created successfully");
        } else {
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
            setMessage("Activity created but task not created");
        }
        return recCnt;
    }

    public String saveCallFeedbackForm() {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            activity.setUserId(getUserInfo().getUserId());
            int recCnt = dao.saveCallLog(activity);
            if (recCnt > 0) {
                setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            }
            if (ApplicationConstants.MESSAGE_TYPE_SUCCESS.equals(getMessageType()) && StringUtils.isNoneBlank(activity.getCoCreateTaskYn()) && "1".equals(activity.getCoCreateTaskYn())) {
                createTask();
            }
        } catch (Exception e) {
        }
        return "data";
    }

    public String openInsuredDetailPage() {
        getUserInfo().setActiveMenu(TypeConstants.Menu.CONTACT);
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        customerTypeList = dao.getCustomerTypeList();
        return "ins_page";
    }

    public String openSearchPage() {
        getUserInfo().setActiveMenu(TypeConstants.Menu.SEARCH);
        return SUCCESS;
    }

    public String loadInsuredDetailList() {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        setFlex2(null);
        //commented for corporate
        //if (getCustomer().getCrmId().startsWith(ApplicationConstants.APP_TYPE_QLM)) {
        if (ApplicationConstants.APP_TYPE.equals(ApplicationConstants.APP_TYPE_QLM)) {
            setFlex2(getCustomer().getSource());
        }
        try {
            setSearch(request.getParameter("search[value]"));
            if (getSearch() == null || getSearch().isEmpty()) {
                if (getFlex1() != null && !getFlex1().isEmpty()) {
                    setSearch(getFlex1());
                }
            }
            recordsTotal = recordsFiltered;
            //commented for corporate
            //aaData = dao.loadInsuredList(getCustomer().getCrmId(), start, length, getSearch(), true, getFlex2());
            aaData = dao.loadInsuredList(start, length, getSearch(), true, getFlex2());
            if (aaData != null && !aaData.isEmpty()) {
                recordsFiltered = Integer.parseInt(((Customer) aaData.get(0)).getAge());
            }
            //commented for corporate
            //aaData = dao.loadInsuredList(getCustomer().getCrmId(), start, length, getSearch(), false, getFlex2());
            aaData = dao.loadInsuredList(start, length, getSearch(), false, getFlex2());
        } catch (Exception e) {
        }
        return "data";
    }

    public String loadEmailCampFigure() {
        dao = new AnoudDAO(getCompany());
        try {
            if (StringUtils.isNotBlank(customer.getEmailId())) {
                setAaData(dao.loadMailData(getCustomer().getEmailId()));
            }
        } catch (Exception e) {
        }
        return "data";
    }

    public String loadTaskFigure() {
        dao = new AnoudDAO(getCompany());
        try {
            if (StringUtils.isNotBlank(customer.getCivilId())) {
            	//commented for corporate
                //setAaData(dao.loadTaskData(getCustomer().getCivilId(), getCustomer().getCrmId()));
                setAaData(dao.loadTaskData(getCustomer().getCivilId()));
            }
        } catch (Exception e) {
        }
        return "data";
    }

    public String openMailDetailsFigure() {
        return "mail_list";
    }

    public String loadCampByEmailFigure() {
        dao = new AnoudDAO(getCompany());

        aaData = dao.loadMailDetails(getCustomer().getEmailId());
        return "data";
    }

    //Save Incoming Call
    public String saveIncomingCall() {
        if (StringUtils.isNotBlank(getMobile())) {
            dao = new AnoudDAO(getUserInfo().getCompanyCode());
            callLog = new CrmCallLog();
            callLog.setCclCallNo(getMobile());
            callLog.setCclType("I");
            callLog.setCclCrmType(CallType.INBOUND.getCode());
            callLog.setCclCrUid(getUserInfo().getUserId());
            callLog.setCclFilePath(getCrtObjectId());
            callLog.setCclExtId(getUserInfo().getUserTelNo());
            //commented for corporate
            //callLog.setCclCrmId(getCrmId());
            activity = dao.saveIncomingCall(callLog);
            callLog.setCclId(activity.getId());
            if ((getCrtObjectId() == null && !"plugin".equals(getSearch()) && !"view".equals(getSearch())) || (getCrtObjectId() != null && !getCrtObjectId().equals(session.get("CRM_POPUP")))) {
                session.put("CRM_POPUP", getCrtObjectId());
                session.put("CALL_LOG", callLog);
            }
        }
        return "ok_request";
    }

    public String saveCallFeedBack() {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        try {
            callLog.setCclRemarks(getCallLog().getCclRemarks());
            callLog.setCclId(((CrmCallLog) session.get("CALL_LOG")).getCclId());
            int recCnt = dao.saveCallFeedback(callLog);
            session.remove("CALL_LOG");
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            setMessage("Remarks Updated Successfully successfully");
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return "data";
    }

    //Search Customer
    public String openInsuredDetailForm() {
        return "contact_page";
    }

    public String loadInsuredDetailData() {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        LOGGER.info("Search Name :: {}", getCustomer().getName());
        try {
            if (StringUtils.isNotBlank(customer.getName())) {
                setAaData(dao.loadInsuredListByName(getCustomer().getName()));
            }
        } catch (Exception e) {
        }
        LOGGER.debug("Load Insured Details Data Exit");
        return "data";
    }

    public String loadCallLogFigure() {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        //commented for corporate
        //aaData = dao.loadCallLogDetails(getCustomer().getCivilId(), getCustomer().getCrmId());
        aaData = dao.loadCallLogDetails(getCustomer().getCivilId());
        return "data";
    }

    public String loadMemberClaims() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
        dao = new AnoudDAO(getCompany());
        List<FlexBean> list = null;
        try {
            list = dao.loadMemberFigures(getRiskMedical().getTrmTransId(), getRiskMedical().getTrmMemId(), AnoudDAO.DashboardFigures.MEMBER_CLAIMS);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        if(null == list) {
            list = new ArrayList<>();
        }
        setAaData(list);
        return "data";
    }

    public String loadMemberPreApproval() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
        dao = new AnoudDAO(getCompany());
        List<FlexBean> list = null;
        try {
            list = dao.loadMemberFigures(getRiskMedical().getTrmTransId(), getRiskMedical().getTrmMemId(), AnoudDAO.DashboardFigures.MEMBER_PRE_APPROVALS);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        if(null == list) {
            list = new ArrayList<>();
        }
        setAaData(list);
        return "data";
    }

    public String loadMemberEnquiries() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
        dao = new AnoudDAO(getCompany());
        List<FlexBean> list = null;
        try {
            list = dao.loadMemberFigures(getRiskMedical().getTrmTransId(), getRiskMedical().getTrmMemId(), AnoudDAO.DashboardFigures.MEMBER_ENQUIRIES);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        if(null == list) {
            list = new ArrayList<>();
        }
        setAaData(list);
        return "data";
    }

    public String loadMemberVisits() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
        dao = new AnoudDAO(getCompany());
        List<FlexBean> list = null;
        try {
            list = dao.loadMemberFigures(getRiskMedical().getTrmTransId(), getRiskMedical().getTrmMemSrNo(), AnoudDAO.DashboardFigures.MEMBER_VISITS);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        if(null == list) {
            list = new ArrayList<>();
        }
        setAaData(list);
        return "data";
    }

    public String loadCountMedicalDashBoard() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
        dao = new AnoudDAO(getCompany());
        try {
            keyValue = dao.loadCountDashBoard(getRiskMedical().getTrmTransId(), getRiskMedical().getTrmMemId(), getRiskMedical().getTrmMemSrNo());
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
        }
        return SUCCESS;
    }

    public String loadMemberPolicy() {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        policyList = dao.loadMemberPolicyDetails(getCustomer().getCivilId());
        return "data";
    }

    public String loadMemberData() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
    	dao = new AnoudDAO(getUserInfo().getCompanyCode());
        aaData = dao.loadMemberDetails(getRiskMedical().getTrmTransId(), getRiskMedical().getTrmMemSrNo());
        if (aaData != null && !aaData.isEmpty()) {
            riskMedical = (TRiskMedical) aaData.get(0);
        }
        return "member_data";
    }

    public String loadMemberList() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
    	dao = new AnoudDAO(getUserInfo().getCompanyCode());
        aaData = dao.loadMemberDetails(getRiskMedical().getTrmTransId(), getRiskMedical().getTrmMemSrNo());
        return "data";
    }

    public String loadMemberChat() {
    	//commented for corporate
        //dao = new AnoudDAO(getCustomer().getCrmId());
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        aaData = dao.loadMemberChats(getCustomer().getCivilId());
        return "data";
    }

    //Notification
    public String saveMemberNotification() {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        try {
            setFlex3(userInfo.getUserId());
            //commented for corporate
            //int recCnt = dao.savememberNotification(getCustomer().getCivilId(), getFlex2(), getFlex3(), getCustomer().getCrmId());
            int recCnt = dao.savememberNotification(getCustomer().getCivilId(), getFlex2(), getFlex3());
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            setMessage("Remarks Updated Successfully successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "data";
    }

    //PROVIDER FUNCTION
    public String loadProviderPreApproval() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        aaData = dao.loadProviderPreApprovalDetails(getCustomer().getCivilId());
        return "data";
    }

    public String loadProviderEclaims() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        aaData = dao.loadProviderEclaimDetails(getCustomer().getCivilId());
        return "data";
    }

    public String loadProviderEnquiries() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        aaData = dao.loadProviderEnquiries(getCustomer().getCivilId());
        return "data";
    }

    public String loadProviderDiagnosis() {
    	//commented for corporate
        //dao = new AnoudDAO(getCrmId());
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        diagnosisList = dao.getDiagnosisList(flex1);
        return SUCCESS;
    }

    public String callProviderPharmacy() throws Exception {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        keyValue = dao.callPharmacyApproval(getMemberId(), getCustomer().getCivilId(), getDiagCode(), getDetails(), getAmount(), getUserInfo().getUserId(), getQatarId());
        return "pharmacy_provider";
    }

    public String addPharmacypreApproval() {
        return SUCCESS;
    }

    public String loadProviderList() throws Exception {
        dao = new AnoudDAO(getUserInfo().getCompanyCode());
        //commented for corporate
        //aaData = dao.loadProviderList(getCrmId(), searchValue);
        aaData = dao.loadProviderList(searchValue);
        return "data";
    }

    public String customerNotFound() {
        return "customer_error";
    }

    public String loadFileUpload() {
        return SUCCESS;
    }

    public List<? extends Object> getAaData() {
        return aaData;
    }

    public void setAaData(List<? extends Object> aaData) {
        this.aaData = aaData;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public List<KeyValue> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<KeyValue> countryList) {
        this.countryList = countryList;
    }

    public List<KeyValue> getCityList() {
        return cityList;
    }

    public void setCityList(List<KeyValue> cityList) {
        this.cityList = cityList;
    }

    public List<KeyValue> getGenderList() {
        return genderList;
    }

    public void setGenderList(List<KeyValue> genderList) {
        this.genderList = genderList;
    }

    public List<KeyValue> getOccupationList() {
        return occupationList;
    }

    public void setOccupationList(List<KeyValue> occupationList) {
        this.occupationList = occupationList;
    }

    public List<KeyValue> getNationalityList() {
        return nationalityList;
    }

    public void setNationalityList(List<KeyValue> nationalityList) {
        this.nationalityList = nationalityList;
    }

    public List<KeyValue> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<KeyValue> sourceList) {
        this.sourceList = sourceList;
    }

    public List<KeyValue> getCustTypeList() {
        return custTypeList;
    }

    public void setCustTypeList(List<KeyValue> custTypeList) {
        this.custTypeList = custTypeList;
    }

    public List<KeyValue> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<KeyValue> categoryList) {
        this.categoryList = categoryList;
    }

    public List<KeyValue> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<KeyValue> subjectList) {
        this.subjectList = subjectList;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Object[][] getOrder() {
        return order;
    }

    public void setOrder(Object[][] order) {
        this.order = order;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public CrmCallLog getCallLog() {
        return callLog;
    }

    public void setCallLog(CrmCallLog callLog) {
        this.callLog = callLog;
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

    public List<TRiskMedical> getPolicyList() {
        return policyList;
    }

    public void setPolicyList(List<TRiskMedical> policyList) {
        this.policyList = policyList;
    }

    public List<TRiskMedical> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<TRiskMedical> memberList) {
        this.memberList = memberList;
    }

    public TRiskMedical getRiskMedical() {
        return riskMedical;
    }

    public void setRiskMedical(TRiskMedical riskMedical) {
        this.riskMedical = riskMedical;
    }

    public List<KeyValue> getDiagnosisList() {
        return diagnosisList;
    }

    public void setDiagnosisList(List<KeyValue> diagnosisList) {
        this.diagnosisList = diagnosisList;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getQatarId() {
        return qatarId;
    }

    public void setQatarId(String qatarId) {
        this.qatarId = qatarId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDiagCode() {
        return diagCode;
    }

    public void setDiagCode(String diagCode) {
        this.diagCode = diagCode;
    }

    public List<KeyValue> getCustomerTypeList() {
        return customerTypeList;
    }

    public void setCustomerTypeList(List<KeyValue> customerTypeList) {
        this.customerTypeList = customerTypeList;
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

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

    }
