/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.dao.LeadDAO;
import qa.com.qic.common.dao.TaskDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.CrmLeads;
import qa.com.qic.common.vo.CrmOpportunities;
import qa.com.qic.common.vo.MAppCodes;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;
import qa.com.qic.utility.helpers.Validatory;

/**
 *
 * @author kosuri.rao
 */
public class LeadAction extends ActionSupport implements SessionAware, ServletRequestAware {

    private static final Logger LOGGER = LogUtil.getLogger(LeadAction.class);
    private transient HttpServletRequest request;
    private transient Map<String, Object> session;
    private transient LeadDAO dao;
    private transient AnoudDAO anoudDao;
    private transient CommonDAO commonDao;
    private transient TaskDAO taskDao;
    private List<? extends Object> aaData;
    private transient Object dataBean;

    private transient UserInfo userInfo;

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

    private String company;

    private transient CrmOpportunities oppurtunities;
    private transient CrmLeads leads;
    private transient Activity activity;
    private transient List<KeyValue> moduleList;
    private transient List<KeyValue> priorityList;
    private transient List<KeyValue> categoryList;
    private transient List<KeyValue> userList;
    private transient List<KeyValue> statusList;
    private transient List<KeyValue> assignToList;
    private transient List<KeyValue> countryList;
    private transient List<KeyValue> cityList;
    private transient List<KeyValue> genderList;
    private transient List<KeyValue> occupationList;
    private transient List<KeyValue> nationalityList;
    private transient List<KeyValue> sourceList;
    private transient List<KeyValue> oppTypeList;
    private transient List<KeyValue> currencyList;

    private File attachment;
    private transient MAppCodes appCodes;

    public String openLeadsPage() {
        if(1 != userInfo.getUserAdminYn()) {
            return "unAuthroized";
        }
        getUserInfo().setActiveMenu(TypeConstants.Menu.LEADS);
        dao = new LeadDAO(getCompany());
        commonDao = new CommonDAO(getCompany());
        anoudDao = new AnoudDAO(getCompany());
        statusList = dao.loadStatusList();
        categoryList = anoudDao.getMAppCodes(FieldConstants.AppCodes.LEADS_DS);
        userList = commonDao.loadCrmAgentList(getUserInfo().getUserId());
        leads = new CrmLeads();
        leads.setClStatus("P");
        leads.setClAssignedTo((String) session.get(ApplicationConstants.SESSION_NAME_USER_ID));
        return "leads";
    }

    //added for corporate
    public String loadLeadsDatasourceList() {
        anoudDao = new AnoudDAO(getCompany());
        aaData = anoudDao.getMAppCodes(getLeads().getClCrmId(), FieldConstants.AppCodes.LEADS_DS);
        return "data";
    }

    public String loadLeadsData() {
        dao = new LeadDAO(getCompany());
        aaData = dao.loadLeadList(getLeads().getClAssignedTo(), getLeads().getClRefId(), getLeads().getClRefNo(), getLeads().getClStatus(), getLeads().getClWorkPlace());
        return "data";
    }

    public String loadLeadsUploadedData() {
        dao = new LeadDAO(getCompany());
        aaData = dao.loadLeadUploadedList(getLeads().getClId());
        return "data";
    }

    public String openLeadsUploadedForm() {
        return "uploaded_form";
    }

    public String confirmLeadsUploadedForm() {
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        dao = new LeadDAO(getCompany());
        message = dao.confirmLeadsUploadedForm(getLeads().getClId());
        if (message == null) {
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        }
        return "data";
    }

    public String loadOpportunitiesData() {
        dao = new LeadDAO(getCompany());
        aaData = dao.loadOpportunityList(getLeads().getClAssignedTo(), null, getLeads().getClStatus());
        return "data";
    }

    public String showLeadsForm() {
        dao = new LeadDAO(getCompany());
        commonDao = new CommonDAO(getCompany());
        anoudDao = new AnoudDAO(getCompany());
        statusList = dao.loadStatusList();
        assignToList = commonDao.loadCrmAgentList(null);
        sourceList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CUST_SOURCE);
        categoryList = anoudDao.getMAppCodes(FieldConstants.AppCodes.LEADS_DS);
        countryList = anoudDao.getMAppCodes(FieldConstants.AppCodes.COUNTRY);
        genderList = anoudDao.getMAppParameter(FieldConstants.AppParameter.GENDER);
        nationalityList = anoudDao.getMAppCodes(FieldConstants.AppCodes.NATIONALITY);
        occupationList = anoudDao.getMAppCodes(FieldConstants.AppCodes.OCCUPATION);
        if ("edit".equals(getOperation())) {
            leads = dao.loadLeadDetailById(getLeads().getClId());

            if (leads != null && leads.getClCountry() != null && !leads.getClCountry().isEmpty()) {
                cityList = anoudDao.getMAppCodes(FieldConstants.AppCodes.STATE, leads.getClCountry(), FieldConstants.AppCodes.COUNTRY);
            }
        }
        if (cityList == null) {
            cityList = new ArrayList<>();
        }
        if ("add".equals(getOperation())) {
            leads = new CrmLeads();
            leads.setClAssignedTo((String) session.get(ApplicationConstants.SESSION_NAME_USER_ID));
        }
        return "lead_form";
    }

    public String saveLeadsForm() {
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        dao = new LeadDAO(getCompany());
        try {
            if ("add".equals(getOperation()) || "edit".equals(getOperation())) {
                if ("add".equals(getOperation())) {
                    leads.setClStatus("P");
                    leads.setClAssignedTo(getUserInfo().getUserId());
                    leads.setClCrUid((String) session.get(ApplicationConstants.SESSION_NAME_USER_ID));
                }
                int d = dao.saveLeads(getLeads(), getOperation());
                setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                setMessage("Saved successfully");
            }
            aaData = dao.loadLeadList(getLeads().getClAssignedTo(), getLeads().getClRefId(), getLeads().getClRefNo(), getLeads().getClStatus(), getLeads().getClWorkPlace());
        } catch (Exception e) {
            setMessage(e.getMessage());
            LOGGER.error("Exception => {}", e);
        }
        return "data";
    }

    public String openOpportunitiesForm() {
        commonDao = new CommonDAO(getCompany());
        anoudDao = new AnoudDAO(getCompany());
        oppTypeList = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_OPP_TYPE);
        currencyList = commonDao.loadCurrencyList();
        categoryList = anoudDao.getMAppCodes(FieldConstants.AppCodes.LEADS_DS);
        setCategoryList(anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_CATEGORY));
        aaData = new ArrayList<KeyValue>();
        if ("edit".equals(getOperation())) {
            dao = new LeadDAO(getCompany());
            oppurtunities = dao.loadOpportunityData(getOppurtunities().getCoId());
        } else {
            if (getOppurtunities() != null) {
                getOppurtunities().setCoCurrency("001");
            }
        }
        return SUCCESS;
    }

    public String saveOpportunitiesForm() {
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        dao = new LeadDAO(getCompany());
        try {
            if ("add".equals(getOperation())) {
                oppurtunities.setCoCrUid((String) session.get(ApplicationConstants.SESSION_NAME_USER_ID));
                leads = dao.loadLeadDetailById(getLeads().getClId());
                int result = dao.saveOpportunities(getLeads(), getOppurtunities(), getOperation());
                if (result > 0) {
                    setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                    setMessage("Oppurtunity saved successfully");
                }
                if (StringUtils.isNoneBlank(oppurtunities.getCoCreateTaskYn()) && "1".equals(oppurtunities.getCoCreateTaskYn())) {
                    taskDao = new TaskDAO(getCompany());
                    oppurtunities.getTask().setCtRefNo(oppurtunities.getCoRefNo());
                    oppurtunities.getTask().setCtStatus("P");
                    oppurtunities.getTask().setCtMessage(oppurtunities.getCoRemarks());
                    oppurtunities.getTask().setCtModule("000");
                    oppurtunities.getTask().setCtAssignedTo(getUserInfo().getUserId());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    oppurtunities.getTask().setCtAssignedDt(sdf.format(new Date()));
                    oppurtunities.getTask().setCtPriority("N");
                    oppurtunities.getTask().setCtCrUid(getUserInfo().getUserId());
                    int recCnt = taskDao.saveTask(oppurtunities.getTask(), "add");
                    if (recCnt > 0) {
                        setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                        setMessage("Oppurtunity and task created successfully");
                    } else {
                        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
                        setMessage("Oppurtunity created but task not created");
                    }
                }
            } else if ("edit".equals(getOperation())) {
                int result = dao.saveOpportunities(getLeads(), getOppurtunities(), getOperation());
                if (result > 0) {
                    setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                } else {
                    setMessage("Status not updated!");
                }
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
            LOGGER.error("Exception => {}", e);
        }
        return "data";
    }

    public String openUploadLeadsForm() {
        anoudDao = new AnoudDAO(getCompany());
        dao = new LeadDAO(getCompany());
        sourceList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CUST_SOURCE);
        categoryList = anoudDao.getMAppCodes(FieldConstants.AppCodes.LEADS_DS);
        moduleList = anoudDao.getProducts();
        return "upload_form";
    }

    public String saveUploadLeadsForm() {
        LOGGER.info("*** LeadAction -- saveUploadLeadsForm() -- Enter ***");
        LOGGER.info("file name :" + attachment.getName());
        dao = new LeadDAO(getCompany());
        try {
            getLeads().setClId(new Date().getTime());
            getLeads().setClAssignedTo(getUserInfo().getUserId());
            getLeads().setClCrUid(getUserInfo().getUserId());
            getLeads().setClStatus("P");
            setAaData(new ArrayList() {
                {
                    add(getLeads());
                }
            });
            message = dao.insertUploadedFileDetails(attachment, getLeads());
            if (StringUtils.isBlank(message)) {
                setMessageType("S");
            }
        } catch (Exception e) {
            setMessageType("E");
            setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
        }
        LOGGER.info("*** LeadAction -- saveUploadLeadsForm() -- Exit ***");
        return "data";
    }

    public String saveLeadsReportData() {
        LOGGER.info("*** LeadAction -- saveLeadsReportData() -- Enter ***");
        //LOGGER.info("file name :" + attachment.getName());
        dao = new LeadDAO(getCompany());
        try {
            getLeads().setClId(new Date().getTime());
            getLeads().setClAssignedTo(getUserInfo().getUserId());
            getLeads().setClCrUid(getUserInfo().getUserId());
            getLeads().setClStatus("P");
            setAaData(new ArrayList() {
                {
                    add(getLeads());
                }
            });
            message = dao.saveLeadsReportData(getLeads());
            if (StringUtils.isBlank(message)) {
                setMessageType("S");
            }
        } catch (Exception e) {
            setMessageType("E");
            setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
        }
        LOGGER.info("*** LeadAction -- saveLeadsReportData() -- Exit ***");
        return "uploaded_form";
    }

    public String openOpportunitiesPage() {
        getUserInfo().setActiveMenu(TypeConstants.Menu.OPPORTUNITIES);
        dao = new LeadDAO(getCompany());
        commonDao = new CommonDAO(getCompany());
        statusList = dao.loadOppStatusList();
        userList = commonDao.loadCrmAgentList(getUserInfo().getUserId());
        leads = new CrmLeads();
        leads.setClStatus("O");
        leads.setClAssignedTo(getUserInfo().getUserId());
        return "opportunity_page";
    }

    public String saveLeadDataSource() {
        messageType = ApplicationConstants.MESSAGE_TYPE_ERROR;
        /*if (StringUtils.isBlank(appCodes.getAcCode())) {
         message = "Enter Code";
         } else*/ if (StringUtils.isBlank(appCodes.getAcDesc())) {
            message = "Enter description";
        } else if (StringUtils.isBlank(appCodes.getAcType()) || appCodes.getAcType().length() > 12) {
            message = "Invalid type";
        } else if (StringUtils.isBlank(appCodes.getAcFlex01()) || !Validatory.isValidDate(appCodes.getAcFlex01())) {
            message = "Invalid start date";
        } else if (StringUtils.isBlank(appCodes.getAcFlex02()) || !Validatory.isValidDate(appCodes.getAcFlex02())) {
            message = "Invalid end date";
        } else if (!StringUtils.isNumeric(appCodes.getAcFlex03())) {
            message = "Enter valid excepted revenue";
        } else if (StringUtils.isBlank(appCodes.getAcFlex04())) {
            message = "Please select appl. product";
        }
        if (message != null) {
            return "data";
        }
        Date sDt = Validatory.getDate(appCodes.getAcFlex01()), eDt = Validatory.getDate(appCodes.getAcFlex02());
        Calendar currCal = Calendar.getInstance();
        currCal.set(Calendar.HOUR_OF_DAY, 0);
        currCal.set(Calendar.MINUTE, 0);
        currCal.set(Calendar.SECOND, 0);
        currCal.set(Calendar.MILLISECOND, 0);
        if (currCal.getTime().compareTo(sDt) > 0) {
            setMessage("Start date must be greater than or equal to current date");
            return "data";
        } else if (eDt.compareTo(sDt) < 0) {
            setMessage("End date must be greater than or equal to start date");
            return "data";
        }
        anoudDao = new AnoudDAO(getCompany());
        try {
            if ("add".equals(operation)) {
                appCodes.setAcCrUid(getUserInfo().getUserId());
            }
            Long seq_id = anoudDao.saveMAppCodes(appCodes, null);
            messageType = ApplicationConstants.MESSAGE_TYPE_SUCCESS;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return "data";
    }

    public String openDashBoardPage() {
        if(1 != userInfo.getUserAdminYn()) {
            return "unAuthroized";
        }
        dao = new LeadDAO(getCompany());
        sourceList = dao.loadDataSource(FieldConstants.AppCodes.LEADS_DS);
        return "success";
    }

    public String loadDashBoardSummary() {
        dao = new LeadDAO(getCompany());
        dataBean = dao.loadDashBoardSummary(getLeads().getClSource());
        aaData = dao.loadDashBoardSummaryByPeriod(getLeads().getClSource());
        return "data";
    }

    public String loadDashBoardSummaryByPeriod() {
        dao = new LeadDAO(getCompany());
        aaData = dao.loadDashBoardSummaryByPeriod(getLeads().getClSource());
        return "data";
    }

    public Map<String, String> getSendToList() {
        LinkedHashMap<String, String> sendToList = new LinkedHashMap<>();
        sendToList.put("E", "Excel File");
        sendToList.put("C", "From CRM");
        return sendToList;
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

    public List<KeyValue> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<KeyValue> moduleList) {
        this.moduleList = moduleList;
    }

    public List<KeyValue> getPriorityList() {
        return priorityList;
    }

    public void setPriorityList(List<KeyValue> priorityList) {
        this.priorityList = priorityList;
    }

    public List<KeyValue> getAssignToList() {
        return assignToList;
    }

    public void setAssignToList(List<KeyValue> assignToList) {
        this.assignToList = assignToList;
    }

    public List<KeyValue> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<KeyValue> categoryList) {
        this.categoryList = categoryList;
    }

    public List<KeyValue> getUserList() {
        return userList;
    }

    public void setUserList(List<KeyValue> userList) {
        this.userList = userList;
    }

    public List<KeyValue> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<KeyValue> statusList) {
        this.statusList = statusList;
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

    public CrmOpportunities getOppurtunities() {
        return oppurtunities;
    }

    public void setOppurtunities(CrmOpportunities oppurtunities) {
        this.oppurtunities = oppurtunities;
    }

    public CrmLeads getLeads() {
        return leads;
    }

    public void setLeads(CrmLeads leads) {
        this.leads = leads;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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

    public List<KeyValue> getOppTypeList() {
        return oppTypeList;
    }

    public void setOppTypeList(List<KeyValue> oppTypeList) {
        this.oppTypeList = oppTypeList;
    }

    public List<KeyValue> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<KeyValue> currencyList) {
        this.currencyList = currencyList;
    }

    public File getAttachment() {
        return attachment;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

    public MAppCodes getAppCodes() {
        return appCodes;
    }

    public void setAppCodes(MAppCodes appCodes) {
        this.appCodes = appCodes;
    }

    public Object getDataBean() {
        return dataBean;
    }

    public void setDataBean(Object dataBean) {
        this.dataBean = dataBean;
    }
}
