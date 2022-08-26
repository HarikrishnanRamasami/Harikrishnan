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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.FeedBackInfoBO;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.AdminDAO;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.dao.FeedBackDAO;
import qa.com.qic.common.dao.LeadDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.CrmCallLog;
import qa.com.qic.common.vo.CrmHolidays;
import qa.com.qic.common.vo.CrmUser;
import qa.com.qic.common.vo.DataBean;
import qa.com.qic.common.vo.MAppCodes;
import qa.com.qic.common.vo.MCrmAgentsTaskAlloc;
import qa.com.qic.common.vo.TMailCampaignImages;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;
import qa.com.qic.utility.helpers.Validatory;

/**
 *
 * @author ravindar.singh
 */
public class AdminAction extends ActionSupport implements SessionAware {

    private static final Logger LOGGER = LogUtil.getLogger(AdminAction.class);
    private transient Map<String, Object> session;
    private transient AdminDAO adminDAO;
    private transient LeadDAO leadDao;
    private transient List<? extends Object> aaData;

    private transient UserInfo userInfo;
    private transient UserInfo user;
    private transient CrmUser crmUser;
    private transient CrmHolidays crmHolidays;

    private final static String DUMMY_STRING = "1";

    private String utype;
    private String urole;

    /**
     * messageType - S: Success, E: Error message - details description about
     * success/error
     */
    private transient String message;
    private transient String messageType;

    /**
     * operation - add, edit, view, delete
     */
    private transient String operation;

    /**
     * Flexible fields
     */
    private transient String flex1;
    private transient String flex2;
    private transient String flex3;
    private transient List<KeyValue> keyValueList;
    private transient List<KeyValue> userList;
    private transient MAppCodes appcodes;
    private transient TMailCampaignImages campaignImages;
    private transient MCrmAgentsTaskAlloc mCrmAgentsTask;
    private File[] attachments;
    private String[] attachmentsFileName;
    private String[] attachmentsContentType;
    private String[] attachmentsFile;
    private transient List<KeyValue> taskList;
    private transient List<KeyValue> subtaskList;
    private transient List<KeyValue> categoryList;
    private transient List<KeyValue> assignList;
    private transient List<KeyValue> priorityList;
    private transient List<KeyValue> filterlist;
    private transient List<KeyValue> operatorlist;
    private transient List<KeyValue> valuelist;
    public String[] catAgentTempId;
    public String[] catAllocTempRatio;
    private transient String flex4;
    private transient String flex5;
    private transient String company;
    private transient CrmCallLog callLog;
    private transient String agentId;
    private transient AdminDAO.YearRange yearRange;
    private transient List<KeyValue> yearRangeList;
    private transient String month;
    private transient String year;
    private transient String userAdministratorYn;
    private transient List<KeyValue> userTypeList;
    private transient List<KeyValue> userRoleList;
    private List<String> yearList = new ArrayList<>();
    private Map<Integer, String> callLogMap;
    private static final Map<Integer, String> monthRangeList = new HashMap<Integer, String>() {
        {
            put(1, "January");
            put(2, "Feburary");
            put(3, "March");
            put(4, "April");
            put(5, "May");
            put(6, "June");
            put(7, "July");
            put(8, "August");
            put(9, "September");
            put(10, "October");
            put(11, "November");
            put(12, "December");
        }
    };

    private Map<Integer, Integer> holidayYearList;
    private Map<Integer, Integer> yearsList;
    private transient List<KeyValue> userTeamlist;
    private transient List<KeyValue> userRoleSeqlist;
    private transient List<KeyValue> userAgentTypelist;
    private transient List<FeedBackInfoBO> feedBackList;

    public String openTaskAttendeePage() {
        if (1 != userInfo.getUserAdminYn()) {
            return "unAuthroized";
        }
        return "allocate_task";
    }

    public String loadTaskAttendeeData() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        aaData = adminDAO.loadCrmUserList();
        return "data";
    }

    public String loadTaskAttendeeCompany() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        aaData = adminDAO.loadApplCompanyByUser(getUser().getUserId());
        return "data";
    }

    public String saveTaskAttendeeCompany() {
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        getUser().setUserName(getUser().getUserId());
        try {
            int recCent = adminDAO.saveUserApplCompany(getUser(), "add");
            if (recCent == 1) {
                setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            }
        } catch (Exception e) {
        }
        return "data";
    }

    public String deleteTaskAttendeeCompany() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        getUser().setUserName(getUserInfo().getUserId());
        adminDAO.saveUserApplCompany(getUser(), "delete");
        setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        return "data";
    }

    public String saveTaskAttendeeData() {
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        try {
            adminDAO.saveTaskAttendeeData(getFlex1(), getUserInfo().getUserId());
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception ex) {
        }
        return "data";
    }

    /*public String saveTaskAttendeeExtnForm() {
     setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
     adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
     try {
     adminDAO.saveTaskAttendeeExtnForm(getUser(), getUserInfo().getUserId());
     setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
     } catch (Exception ex) {

     }
     return "data";
     }*/
    public String openMissedCallPage() {
        if (!"N".equals(flex1) && 1 != userInfo.getUserAdminYn()) {
            return "unAuthroized";
        }
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        keyValueList = dao.getMAppCodes(FieldConstants.AppCodes.CRM_LOG_TYPE, "CALL", null);
        keyValueList.add(0, new KeyValue("", "All"));
        keyValueList.add(new KeyValue("AC", "Abandoned Call"));
        CommonDAO commonDAO = new CommonDAO(getUserInfo().getCompanyCode());
        userList = commonDAO.loadCrmAgentList(getUserInfo().getUserId());
        userList.add(0, new KeyValue("", "All"));
        callLog = new CrmCallLog();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        callLog.setCclCallDt(sdf.format(new Date()));
        callLog.setCclCrmType(TypeConstants.CallType.MISSED.getCode());
        callLog.setCclCrUid(getUserInfo().getUserId());
        if (!"N".equals(flex1) && 1 == userInfo.getUserAdminYn()) {
            DataBean dataBean = adminDAO.loadCallLogWaitTimeSummaryHead();
            if (dataBean != null) {
                yearList.add("0 - " + dataBean.getInteger1() + "mins");
                yearList.add(dataBean.getInteger1() + "-" + dataBean.getInteger2() + "mins");
                yearList.add(dataBean.getInteger2() + "-" + dataBean.getInteger3() + "mins");
                yearList.add(dataBean.getInteger3() + "-" + dataBean.getInteger4() + "mins");
                yearList.add(dataBean.getInteger4() + "-" + dataBean.getInteger5() + "mins");
                yearList.add(">" + dataBean.getInteger5() + "mins");
                callLog.setCclFlex3(dataBean.getData1());
            }
        }
        return "missed_call";
    }

    public String openMissedCallDetails() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        keyValueList = dao.getMAppCodes(FieldConstants.AppCodes.CRM_CALL_TYPE, "", null);
        aaData = adminDAO.loadCrmCallLogList(callLog);
        if (aaData != null && !aaData.isEmpty()) {
            callLog = (CrmCallLog) aaData.get(0);
        }
        return "call_details";
    }

    public String openCallFeedBack() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        callLogMap = adminDAO.loadCallLogFeedBack(callLog.getCclId());
        FeedBackDAO dao = new FeedBackDAO(company);
        String formId = "CRM_FEEDBACK";
        feedBackList = dao.getFeedbackList(formId, getCompany());
        return "feedback";
    }

    public String saveMissedCallDetails() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            if (StringUtils.isNoneBlank(callLog.getCclRemarks()) && StringUtils.isNoneBlank(callLog.getCclCallCode())) {
                adminDAO.updateCrmCallLog(callLog);
                setMessage("Updated successfuly");
                setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            } else {
                setMessage("Please enter remarks");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "data";
    }

    public String loadMissedCallData() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        if (StringUtils.isBlank(getCallLog().getCclCrUid())) {
            getCallLog().setCclFlex3(getUserInfo().getUserId());
        }
        aaData = adminDAO.loadCrmCallLogList(getCallLog());
        return "data";
    }

    public String loadCallLogSummaryData() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        aaData = adminDAO.loadCallLogSummaryList(getCallLog(), "list");
        return "data";
    }

    public String loadCallLogWaitTimeSummary() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        aaData = adminDAO.loadCallLogWaitTimeSummary(getCallLog());
        return "data";
    }

    public String openHandySmsPage() {
        return "handy_sms";
    }

    public String loadHandySmsData() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        aaData = adminDAO.loadHandySmsList();
        return "data";
    }

    public String openHandySmsForm() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        if ("edit".equals(getOperation())) {
            appcodes = adminDAO.loadHandySmsByCode(getAppcodes().getAcCode());

        }
        return "handy_sms_form";
    }

    public String saveHandySmsForm() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        appcodes.setAcCrUid((String) session.get(ApplicationConstants.SESSION_NAME_USER_ID));
        appcodes.setAcUpdUid((String) session.get(ApplicationConstants.SESSION_NAME_USER_ID));
        try {
            if ("add".equals(getOperation()) || "edit".equals(getOperation())) {
                adminDAO.saveHandySms(appcodes, getOperation());
            }
        } catch (Exception e) {
            LOGGER.error("Exception => {}", e);
            setMessage(e.getMessage());
        }
        return "data";
    }

    //Campaign Images
    public String openCampaignImagesPage() {
        return "campaignImages";
    }

    public String loadCampaignImagesData() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        aaData = adminDAO.loadCampImagesList();
        return "data";
    }

    public String openCampaignImagesForm() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        return "upload_campaign";
    }

    public String uploadCampaignImagesForm() {
        if (attachments[0] != null) {
            String fileName = attachmentsFileName[0];
            String ext = FilenameUtils.getExtension(attachmentsFileName[0]);
            Long size = attachments[0].length() / 1024;
            adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
            try {
                if ("png".equals(ext) || "jpeg".equals(ext) || "jpg".equals(ext) || "gif".equals(ext) || "bmp".equals(ext)) {
                    String filePath = ApplicationConstants.FILE_STORE_LOC(userInfo.getAppType(), userInfo.getCompanyCode()) + ApplicationConstants.CAMPAIGN_IMAGE_PATH;
                    File destFile = new File(filePath);
                    if (!destFile.exists()) {
                        destFile.mkdir();
                    }
                    campaignImages.setMciId(UUID.randomUUID().toString());
                    destFile = new File(filePath, campaignImages.getMciId());
                    FileUtils.copyFile(attachments[0], destFile);
                    campaignImages.setMciCrUid(getUserInfo().getUserId());
                    campaignImages.setMciExt(FilenameUtils.getExtension(attachmentsFileName[0]));
                    campaignImages.setMciName(fileName);
                    campaignImages.setMciMime(attachmentsContentType[0]);
                    campaignImages.setMciDesc(campaignImages.getMciDesc());
                    campaignImages.setMciSize(size);
                    adminDAO.insertUploadedImageDetails(campaignImages);
                    setMessageType("S");
                }
            } catch (Exception e) {
                setMessageType("E");
                setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            }
        }
        return "data";
    }

    public String allocationDetails() {
    	//commented for corporate
        //adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());
    	 adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        utype = "U";
        //commented for corporate
        //String[] allocBy = adminDAO.getAllocationBy(crmAgentsTask.getCatTaskCatg(), crmAgentsTask.getCatTaskSubCatg());
        String[] allocBy = adminDAO.getAllocationBy(mCrmAgentsTask.getCatTaskCatg(), mCrmAgentsTask.getCatTaskSubCatg());
        if (allocBy != null) {
            utype = allocBy[0] == null ? "U" : allocBy[0];
            urole = allocBy[1];
        }
        LOGGER.info(" Allocation By : {} , FOR {}", utype, urole);
        return SUCCESS;
    }

    public String loadTaskAllocateList() {
        LOGGER.debug(" loadTaskAllocateList - Enter ");
        //commented for corporate
        /*adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());
        if ("".equals(crmAgentsTask.getCatTaskCatg())
                || ("".equals(crmAgentsTask.getCatTaskSubCatg()))) {
            setAaData(new ArrayList<KeyValue>());
        } else {
            setAaData(adminDAO.loadTaskAllocateDetails(crmAgentsTask.getCatTaskCatg(), crmAgentsTask.getCatTaskSubCatg()));
        }*/

        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        if ("".equals(mCrmAgentsTask.getCatTaskCatg())
                || ("".equals(mCrmAgentsTask.getCatTaskSubCatg()))) {
            setAaData(new ArrayList<KeyValue>());
        } else {
            setAaData(adminDAO.loadTaskAllocateDetails(mCrmAgentsTask.getCatTaskCatg(), mCrmAgentsTask.getCatTaskSubCatg()));
        }
        LOGGER.debug(" loadTaskAllocateList - Exit ");
        return "data";
    }

    public String openTaskAllocatePage() {
        if (1 != userInfo.getUserAdminYn()) {
            return "unAuthroized";
        }
        LOGGER.debug("openTaskAllocatePage  - Enter ");
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        adminDAO = new AdminDAO((getUserInfo().getCompanyCode()));
        leadDao = new LeadDAO(getUserInfo().getCompanyCode());
        taskList = dao.getMAppCodes(FieldConstants.AppCodes.CRM_CATEGORY);
        subtaskList = dao.getMAppCodes(FieldConstants.AppCodes.CRM_SUB_CATEGORY);
        categoryList = leadDao.loadActiveDataSource(FieldConstants.AppCodes.LEADS_DS);
        userTypeList = FieldConstants.USER_TYPE_LIST;
        userRoleList = adminDAO.getuserRoleList();
        LOGGER.debug("openTaskAllocatePage  - Exit ");
        return "taskAllocation";
    }

    //commented for corporate
    /*public String openTaskAllocatePage() {
        if (1 != userInfo.getUserAdminYn()) {
            return "unAuthroized";
        }
        LOGGER.debug("openTaskAllocatePage  - Enter ");
        //AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        //adminDAO = new AdminDAO((getUserInfo().getCompanyCode()));
        //leadDao = new LeadDAO(getUserInfo().getCompanyCode());
        //taskList = dao.getMAppCodes(FieldConstants.AppCodes.CRM_CATEGORY);
        //subtaskList = dao.getMAppCodes(FieldConstants.AppCodes.CRM_SUB_CATEGORY);
        //categoryList = leadDao.loadActiveDataSource(FieldConstants.AppCodes.LEADS_DS);
        userTypeList = FieldConstants.USER_TYPE_LIST;
        //userRoleList = adminDAO.getuserRoleList();

        taskList = new ArrayList<>();
        subtaskList = new ArrayList<>();
        categoryList = new ArrayList<>();
        userRoleList = new ArrayList<>();
        LOGGER.debug("openTaskAllocatePage  - Exit ");
        return "taskAllocation";
    }*/

    public String openTaskAllocatePageData() {
        if (1 != userInfo.getUserAdminYn()) {
            return "unAuthroized";
        }
        LOGGER.debug("openTaskAllocatePageData  - Enter ");
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        //commented for corporate
        /*adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());
        leadDao = new LeadDAO(getCrmAgentsTask().getCatCrmId());
        taskList = dao.getCrmTaskCategory(getCrmAgentsTask().getCatCrmId(), FieldConstants.AppCodes.CRM_CATEGORY);*/
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        leadDao = new LeadDAO(getUserInfo().getCompanyCode());
        taskList = dao.getCrmTaskCategory(getUserInfo().getCompanyCode(), FieldConstants.AppCodes.CRM_CATEGORY);
        categoryList = leadDao.loadActiveDataSource(FieldConstants.AppCodes.LEADS_DS);
        userRoleList = adminDAO.getuserRoleList();
        LOGGER.debug("openTaskAllocatePage  - Exit ");
        return "taskAllocationData";
    }

    public String openTaskAllocateForm() {
        LOGGER.debug("openTaskAllocateForm  - Enter ");
        //commented for corporate
        /*AnoudDAO dao = new AnoudDAO(getCrmAgentsTask().getCatCrmId());
        CommonDAO commonDAO = new CommonDAO(getCrmAgentsTask().getCatCrmId());*/
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        CommonDAO commonDAO = new CommonDAO(getUserInfo().getCompanyCode());
        taskList = dao.getMAppCodes(FieldConstants.AppCodes.CRM_CATEGORY);
        aaData = dao.getMAppCodes(FieldConstants.AppCodes.CRM_SUB_CATEGORY);
        assignList = commonDAO.loadCrmAgentList(null);
        LOGGER.debug("openTaskAllocateForm  - Exit ");
        return "taskAllocationForm";
    }

    public String saveTaskAllocateForm() {
        LOGGER.debug("Save TaskAllocateForm  - Enter ");
        int recCnt = 0;
        //commented for corporate
        //adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());
        adminDAO = new AdminDAO((getUserInfo().getCompanyCode()));
        if (catAgentTempId != null) {
        	//commented for corporate
            //recCnt = adminDAO.saveTaskAllocate(catAgentTempId, catAllocTempRatio, crmAgentsTask);
        	recCnt = adminDAO.saveTaskAllocate(catAgentTempId, catAllocTempRatio, mCrmAgentsTask);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } else {
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        return "data";
    }

    public String loadTaskAllocateCatList() {
        LOGGER.debug("load TaskAllocateCatList  - Enter ");
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        //commented for corporate
        //aaData = dao.getCrmTaskCategory(getCrmAgentsTask().getCatCrmId(), FieldConstants.AppCodes.CRM_SUB_CATEGORY, crmAgentsTask.getCatTaskCatg());
        aaData = dao.getMAppCodes(FieldConstants.AppCodes.CRM_SUB_CATEGORY, mCrmAgentsTask.getCatTaskCatg(), FieldConstants.AppCodes.CRM_CATEGORY);
        LOGGER.debug("load TaskAllocateCatList  - Exit ");
        return "data";
    }

    public String openTaskDashPage() {
        LOGGER.debug("openTaskAllocatePage  - Enter ");
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        taskList = dao.getMAppCodes(FieldConstants.AppCodes.CRM_CATEGORY);
        subtaskList = dao.getMAppCodes(FieldConstants.AppCodes.CRM_SUB_CATEGORY);
        LOGGER.debug("openTaskAllocatePage  - Exit ");
        return SUCCESS;
    }

    public String openTaskCategoryForm() {
        return SUCCESS;
    }

    public String openUserTeamForm() {
        return SUCCESS;
    }

    public String saveTaskCategoryForm() {
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        adminDAO = new AdminDAO((getUserInfo().getCompanyCode()));
        if (StringUtils.isBlank(flex1)) {
            setMessage("Please Enter Name");
            return SUCCESS;
        }
        try {
        	//commented for corporate
            //flex4 = adminDAO.saveTaskCategory(getCrmAgentsTask().getCatCrmId(), flex1, flex2, flex3, operation, userInfo.getUserId());
            flex4 = adminDAO.saveCategoryDetails(flex1, flex2, flex3, operation, userInfo.getUserId());
            if (flex4 != null) {
                setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                setMessage("Successfully Inserted");
            } else {
                setMessage("UnSuccessfull");
            }
        } catch (Exception e) {
            setMessage("UnSuccessfull");
        }
        return SUCCESS;
    }

    public String saveUserTeamForm() {
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        if (appcodes == null || StringUtils.isBlank(appcodes.getAcDesc())) {
            setMessage("Please Enter Name");
            return SUCCESS;
        }
        try {
        	//commented for corporate
            //AnoudDAO dao = new AnoudDAO(getCrmAgentsTask().getCatCrmId());
            AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
            appcodes.setAcEffFmDt("01/01/1990");
            appcodes.setAcEffToDt("31/12/2099");
            appcodes.setAcCrUid(userInfo.getUserId());
            Long id = dao.saveMAppCodes(appcodes, null);

            if (id != null) {
                flex4 = String.valueOf(id);
                setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            } else {
                setMessage("Unable to create Team");
            }
        } catch (Exception e) {
            setMessage("Exception while creating Team");
        }
        return SUCCESS;
    }

    public String loadTaskAllocateSLAList() {
        LOGGER.debug(" loadTaskAllocateSLAList - Enter ");
      //commented for corporate
        /*adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());
        setAaData(adminDAO.loadTaskSLAList(crmAgentsTask.getCatTaskCatg(), crmAgentsTask.getCatTaskSubCatg()));*/
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        setAaData(adminDAO.loadTaskSLAList(mCrmAgentsTask.getCatTaskCatg(), mCrmAgentsTask.getCatTaskSubCatg()));
        LOGGER.debug(" loadTaskAllocateSLAList - Exit ");
        return "data";
    }

    public String loadTaskAllocateSLAForm() {
    	//commented for corporate
        /*AnoudDAO dao = new AnoudDAO(getCrmAgentsTask().getCatCrmId());
        adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());*/
    	AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        adminDAO = new AdminDAO((getUserInfo().getCompanyCode()));
        if ("edit".equals(operation)) {
            KeyValue slaValues = adminDAO.loadTaskAllocateSLADetails(flex3);
            if (slaValues != null) {
                setFlex3(slaValues.getKey()); // ID
                setFlex2(slaValues.getInfo()); // sla Days
                setFlex1(slaValues.getValue()); // Priority
              //commented for corporate
                /*crmAgentsTask.setCatTaskCatg(slaValues.getInfo1());
                crmAgentsTask.setCatTaskSubCatg(slaValues.getInfo2());*/
                mCrmAgentsTask.setCatTaskCatg(slaValues.getInfo1());
                mCrmAgentsTask.setCatTaskSubCatg(slaValues.getInfo2());
            }
            priorityList = dao.getMAppParameter(FieldConstants.AppParameter.CRM_PRIORITY);
        } else {
        	 //commented for corporate
            //priorityList = adminDAO.getPriorityList(FieldConstants.AppParameter.CRM_PRIORITY, crmAgentsTask);
            priorityList = adminDAO.getPriorityList(FieldConstants.AppParameter.CRM_PRIORITY, mCrmAgentsTask);
        }
        return SUCCESS;
    }

    public String saveTaskSLAForm() {
        String retString = SUCCESS;
      //commented for corporate
        //adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());
        adminDAO = new AdminDAO((getUserInfo().getCompanyCode()));
        if (!"delete".equals(operation)) {
        	//commented for corporate
            //if (StringUtils.isBlank(crmAgentsTask.getCatTaskCatg())) {
        	if (StringUtils.isBlank(mCrmAgentsTask.getCatTaskCatg())) {
                setMessageType("E");
                setMessage("Please select category");
                return retString;
              //commented for corporate
            //} else if (StringUtils.isBlank(crmAgentsTask.getCatTaskSubCatg())) {
        	 } else if (StringUtils.isBlank(mCrmAgentsTask.getCatTaskSubCatg())) {
                setMessageType("E");
                setMessage("Please select sub category");
                return retString;
            } else if (StringUtils.isBlank(flex1)) {
                setMessageType("E");
                setMessage("Please select priority");
                return retString;
            } else if (StringUtils.isBlank(flex2)) {
                setMessageType("E");
                setMessage("Please select SLA Days");
                return retString;
            }
        }
      //commented for corporate
        //String result = adminDAO.saveTaskSLAForm(flex1, flex2, flex3, crmAgentsTask, operation, userInfo.getUserId());
        String result = adminDAO.saveTaskSLAForm(flex1, flex2, flex3, mCrmAgentsTask, operation, userInfo.getUserId());
        if (result != null) {
            setMessageType("E");
            setMessage(result);
        } else {
            setMessageType("S");
            //setAaData(adminDAO.loadTaskSLAList());
        }
        return retString;
    }

    public String loadTaskAllocateRulesList() {
        LOGGER.debug(" loadTaskAllocateSLAList - Enter ");
        //commented for corporate
        /*adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());
        setAaData(adminDAO.loadTaskAllocateRulesList(crmAgentsTask.getCatTaskCatg(), crmAgentsTask.getCatTaskSubCatg()));*/
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        setAaData(adminDAO.loadTaskAllocateRulesList(mCrmAgentsTask.getCatTaskCatg(), mCrmAgentsTask.getCatTaskSubCatg()));
        LOGGER.debug(" loadTaskAllocateSLAList - Exit ");
        return "data";
    }

    public String loadTaskAllocateRulesForm() {
    	//commented for corporate
        /*adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());
        AnoudDAO dao = new AnoudDAO(getCrmAgentsTask().getCatCrmId());*/
    	adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        if ("edit".equals(operation)) {
            KeyValue rulesDetails = adminDAO.loadTaskRulesDetails(flex5);
            if (rulesDetails != null) {
                setFlex5(rulesDetails.getKey()); // ID
                setFlex1(rulesDetails.getInfo1()); // FILTER
                setFlex2(rulesDetails.getInfo2()); // OPERATOR
                if (rulesDetails.getInfo4() != null && "C".equals(rulesDetails.getInfo4())) { // VALUE
                    setFlex3(rulesDetails.getInfo3());
                } else {
                    setFlex4(rulesDetails.getInfo3());
                }
              //commented for corporate
                /*crmAgentsTask.setCatTaskCatg(rulesDetails.getValue());
                crmAgentsTask.setCatTaskSubCatg(rulesDetails.getInfo());*/

                mCrmAgentsTask.setCatTaskCatg(rulesDetails.getValue());
                mCrmAgentsTask.setCatTaskSubCatg(rulesDetails.getInfo());
            }
            filterlist = dao.getMAppParameter(FieldConstants.AppParameter.CRM_FILTER);
            List<KeyValue> list = dao.getMAppParameter(FieldConstants.AppParameter.CRM_FILTER, flex1, null);
            KeyValue kv = new KeyValue();
            if (list != null && !list.isEmpty()) {
                kv = list.get(0);
            }
            operatorlist = dao.getMAppParameter(FieldConstants.AppParameter.CRM_OPERATOR, null, kv.getInfo1());
            valuelist = adminDAO.loadValueList(flex1, getFlex3());
        } else {
            filterlist = dao.getMAppParameter(FieldConstants.AppParameter.CRM_FILTER);
            operatorlist = new ArrayList<>();
            valuelist = new ArrayList<>();
        }
        return SUCCESS;
    }

    public String loadOperatorList() {
    	//commented for corporate
        //AnoudDAO dao = new AnoudDAO(getCrmAgentsTask().getCatCrmId());
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        setAaData(dao.getMAppParameter(FieldConstants.AppParameter.CRM_OPERATOR, null, flex1));
        return SUCCESS;
    }

    public String loadValueList() {
    	//commented for corporate
    	//adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        setAaData(adminDAO.loadValueList(flex1, null));
        return SUCCESS;
    }

    public String saveTaskRulesForm() {
        String retString = SUCCESS;
      //commented for corporate
    	//adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        String taskValue = null;
        if (!"delete".equals(operation)) {
        	//commented for corporate
            //if (StringUtils.isBlank(crmAgentsTask.getCatTaskCatg())) {
        	if (StringUtils.isBlank(mCrmAgentsTask.getCatTaskCatg())) {
                setMessageType("E");
                setMessage("Please select category");
                return retString;
              //commented for corporate
            //} else if (StringUtils.isBlank(crmAgentsTask.getCatTaskSubCatg())) {
        	} else if (StringUtils.isBlank(mCrmAgentsTask.getCatTaskSubCatg())) {
                setMessageType("E");
                setMessage("Please select sub category");
                return retString;
            } else if (StringUtils.isBlank(flex1)) {
                setMessageType("E");
                setMessage("Please select Filter");
                return retString;
            } else if (StringUtils.isBlank(flex2)) {
                setMessageType("E");
                setMessage("Please select operator");
                return retString;
            }

            if (flex3 != null && !"".equals(flex3)) {
                StringBuilder rulesBuff = new StringBuilder();
                if (flex3.contains(",")) {
                    String[] taskRulesArr = flex3.split(",");
                    int i = 0;
                    for (String rulesStr : taskRulesArr) {
                        rulesBuff.append(rulesStr.trim());
                        if (i < taskRulesArr.length - 1) {
                            rulesBuff.append(",");
                        }
                        i++;
                    }
                    taskValue = rulesBuff.toString();
                } else {
                    taskValue = flex3;
                }
            } else {
                taskValue = getFlex4();
            }
        }
      //commented for corporate
        //String result = adminDAO.saveTaskRulesForm(flex1, flex2, taskValue, flex5, crmAgentsTask, operation, userInfo.getUserId());
        String result = adminDAO.saveTaskRulesForm(flex1, flex2, taskValue, flex5, mCrmAgentsTask, operation, userInfo.getUserId());
        if (result != null) {
            setMessageType("E");
            setMessage(result);
        } else {
            setMessageType("S");
        }
        return retString;
    }

    public String loadCustomersList() {
    	//commented for corporate
    	//adminDAO = new AdminDAO(getCrmAgentsTask().getCatCrmId());
    	 adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        setAaData(adminDAO.loadCustomersList(flex1));

        return SUCCESS;
    }

    public String loadAutoCompleteList() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        setAaData(adminDAO.loadAutoCompleteList(flex1, flex3));
        return SUCCESS;
    }

    public String openAgentDashBoardPage() {
        CommonDAO commonDAO = new CommonDAO(getUserInfo().getCompanyCode());
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        Calendar cal = new GregorianCalendar();
        yearList.add("" + (cal.get(Calendar.YEAR) - 1));
        yearList.add("" + cal.get(Calendar.YEAR));
        month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        userList = commonDAO.loadCrmAgentList(getUserInfo().getUserId());
        return SUCCESS;
    }

    public String loadAgentDashBoardcallSummary() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        //commented for corporate
        //setAaData(adminDAO.loadAgentCallSummary(getCrmAgentsTask().getCatCrmId(), getAgentId(), getYear(), getMonth()));
        setAaData(adminDAO.loadAgentCallSummary(getAgentId(), getYear(), getMonth()));
        return "data";
    }

    public String loadAgentDashBoardQuoteSummary() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
      //commented for corporate
        //setAaData(adminDAO.loadAgentQuoteSummary(getCrmAgentsTask().getCatCrmId(), getAgentId(), getYear(), getMonth()));
        setAaData(adminDAO.loadAgentQuoteSummary(getAgentId(), getYear(), getMonth()));
        return "data";
    }

    public String loadAgentDashBoardRenewalSummary() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
      //commented for corporate
        //setAaData(adminDAO.loadAgentRenewalSummary(getCrmAgentsTask().getCatCrmId(), getAgentId(), getYear(), getMonth()));
        setAaData(adminDAO.loadAgentRenewalSummary(getAgentId(), getYear(), getMonth()));
        return "data";
    }

    public String loadAgentDashBoardPayLinkSummary() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
      //commented for corporate
        //setAaData(adminDAO.loadAgentPayLinkSummary(getCrmAgentsTask().getCatCrmId(), getAgentId(), getYear(), getMonth()));
        setAaData(adminDAO.loadAgentPayLinkSummary(getAgentId(), getYear(), getMonth()));
        return "data";
    }

    public String loaduserList() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        userList = adminDAO.loaduserList(flex1);
        return SUCCESS;
    }

    public String openUserDetailForm() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        try {
            if ("add".equals(getOperation()) || "edit".equals(getOperation())) {
                userTeamlist = dao.getMAppCodes(FieldConstants.AppCodes.CRM_TEAM);
                userRoleSeqlist = dao.getMAppCodes(FieldConstants.AppCodes.CRM_LEVEL);
                userAgentTypelist = FieldConstants.AGENT_TYPE_LIST;
                crmUser = adminDAO.openUserDetailForm(flex1, getOperation());
                if (crmUser == null) {
                    setMessage("Record already exist!");
                    return "data";
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception => {}", e);
        }
        return SUCCESS;
    }

   //commented for corporate
   /* public String openUserDetailForm() {

    	String url = null;
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            if ("add".equals(getOperation()) || "edit".equals(getOperation())) {
                userTeamlist = dao.getMAppCodes(FieldConstants.AppCodes.CRM_TEAM);
                userRoleSeqlist = dao.getMAppCodes(FieldConstants.AppCodes.CRM_LEVEL);
                userAgentTypelist = FieldConstants.AGENT_TYPE_LIST;
                crmUser = adminDAO.openUserDetailForm(flex1, getOperation());
                if (crmUser == null) {
                    setMessage("Record already exist!");
                    return "data";
                }else{
                	 setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                }
                crmUser = adminDAO.openUserDetailForm(flex1, getOperation());
                if (crmUser == null) {
                    setMessage("Record already exist!");
                    return "data";
                }
                if ("1".equals(getCrmUser().getUserAdministratorYn())) {
                 getCrmUser().setUserAdministratorYn("true");
                 } else {
                 getCrmUser().setUserAdministratorYn("false");
                 }
                if ("1".equals(getCrmUser().getUserLockYn())) {
                    getCrmUser().setUserLockYn("true");
                } else {
                    getCrmUser().setUserLockYn("false");
                }

            }
        } catch (Exception e) {
            LOGGER.error("Exception => {}", e);
        }
        return SUCCESS;
    }*/

    public String openMenuForm() {
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        try {
            crmUser = dao.getUserMenuByUserId(crmUser.getUserId());
        } catch (Exception e) {
            LOGGER.error("Exception => {}", e);
        }
        return "menu";
    }

    public String saveUserDetailsForm() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            if ("add".equals(getOperation()) || "edit".equals(getOperation())) {
                adminDAO.saveUserDetailsForm(crmUser, getOperation());
                setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            }
        } catch (Exception e) {
            setMessage((e.getMessage() == null ? "Unable to process" : e.getMessage()));
            LOGGER.error("Exception => {}", e);
        }
        return "data";
    }

    public String saveUserMenuForm() {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            adminDAO.saveUserDetailsForm(crmUser, "menu");
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            setMessage((e.getMessage() == null ? "Unable to process" : e.getMessage()));
            LOGGER.error("Exception => {}", e);
        }
        return "data";
    }

    public String saveLeadToTask() {
        try {
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
            adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
            //commented for corporate
            //setMessage(adminDAO.callPkgCrmTasks(crmAgentsTask.getCatTaskSubCatg(), crmAgentsTask.getDataSouceId()));
            setMessage(adminDAO.callPkgCrmTasks(mCrmAgentsTask.getCatTaskSubCatg(), mCrmAgentsTask.getDataSouceId()));
            if (StringUtils.isBlank(getMessage())) {
                setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            }
        } catch (Exception e) {
            setMessage((e.getMessage() == null ? "Unable to process" : e.getMessage()));
            LOGGER.error("Exception => {}", e);
        }
        return "data";
    }

    public String updateAppCodes() throws Exception {
        adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
        if (StringUtils.isNoneBlank(getFlex2())) {
            adminDAO.updateAppcodes(getFlex1(), getFlex2(), getFlex3(), getFlex4());
            setMessageType("S");
            setMessage("Updated SucessFULLY");
            return "data";
        }
        setMessageType("E");
        setMessage("Select Role");
        return "data";
    }

    public String loadHolidaysMasterScreen() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        holidayYearList = new LinkedHashMap<>();
        holidayYearList.put(year + 1, year + 1);
        for (int i = 0; i <= 5; i++) {
            holidayYearList.put(year - i, year - i);
        }
        yearsList = new LinkedHashMap<>();
        yearsList.put(year + 1, year + 1);
        for (int i = 0; i <= 1; i++) {
            yearsList.put(year, year);
        }
        return SUCCESS;
    }

    public String loadHolidaysData() {
        try {
            adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
            aaData = adminDAO.getHolidaysDetailList(crmHolidays.getHolidaysYear(), (String) session.get("COMPANY_CODE"));
        } catch (Exception e) {
            aaData = new ArrayList<>();
        }
        return "success";
    }

    public String saveHolidayForm() {
        try {
            adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
            validateSaveHolidayForm();
            if (StringUtils.isBlank(message)) {
                if ("edit".equals(flex1)) {
                    message = adminDAO.saveEditedHolidayForm(crmHolidays, "edit", getUserInfo().getUserId(), getUserInfo().getCompanyCode());
                } else {
                    message = adminDAO.saveHolidayForm(crmHolidays, getUserInfo().getUserId(), getUserInfo().getCompanyCode());
                }
            }
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage());
        }
        return SUCCESS;
    }

    public String deleteHoliday() {
        try {
            adminDAO = new AdminDAO(getUserInfo().getCompanyCode());
            message = adminDAO.deleteHoliday(crmHolidays.getHolidaysId(), getUserInfo().getCompanyCode());
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage());
        }
        return SUCCESS;
    }

    public void validateSaveHolidayForm() {
        if (StringUtils.isBlank(crmHolidays.getName())) {
            setMessage("Please enter name");
        } else if (StringUtils.isBlank(crmHolidays.getHolidaysYear())) {
            setMessage("Please select year");
        } else if (StringUtils.isBlank(crmHolidays.getHolidaysType())) {
            setMessage("Please select type");
        }
        if ("H".equalsIgnoreCase(crmHolidays.getHolidaysType())) {
            if (StringUtils.isBlank(crmHolidays.getFromDt()) || StringUtils.isBlank(crmHolidays.getToDt())) {
                setMessage("Please enter the From & To dates");
            } else if (Validatory.validateBackDate(crmHolidays.getFromDt(), 0)) {
                setMessage("From date must be greater than or equal to current date");
            } else if (Validatory.getDays(crmHolidays.getFromDt(), crmHolidays.getToDt()) < 0) {
                setMessage("To date must be greater than or equal to From date");
            }
        } else if ("W".equalsIgnoreCase(crmHolidays.getHolidaysType())) {
            if (StringUtils.isBlank(crmHolidays.getFromDt()) && StringUtils.isBlank(crmHolidays.getToDt())) {
                setMessage("Please enter From Day");
            }
        }
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

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
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

    public List<KeyValue> getKeyValueList() {
        return keyValueList;
    }

    public void setKeyValueList(List<KeyValue> keyValueList) {
        this.keyValueList = keyValueList;
    }

    public CrmCallLog getCallLog() {
        return callLog;
    }

    public void setCallLog(CrmCallLog callLog) {
        this.callLog = callLog;
    }

    public List<KeyValue> getUserList() {
        return userList;
    }

    public void setUserList(List<KeyValue> userList) {
        this.userList = userList;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
        setCompany((String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE));
        this.userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
    }

    public MAppCodes getAppcodes() {
        return appcodes;
    }

    public void setAppcodes(MAppCodes appcodes) {
        this.appcodes = appcodes;
    }

    public TMailCampaignImages getCampaignImages() {
        return campaignImages;
    }

    public void setCampaignImages(TMailCampaignImages campaignImages) {
        this.campaignImages = campaignImages;
    }

    public File[] getAttachments() {
        return attachments;
    }

    public void setAttachments(File[] attachments) {
        this.attachments = attachments;
    }

    public String[] getAttachmentsFileName() {
        return attachmentsFileName;
    }

    public void setAttachmentsFileName(String[] attachmentsFileName) {
        this.attachmentsFileName = attachmentsFileName;
    }

    public String[] getAttachmentsContentType() {
        return attachmentsContentType;
    }

    public void setAttachmentsContentType(String[] attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public String[] getAttachmentsFile() {
        return attachmentsFile;
    }

    public void setAttachmentsFile(String[] attachmentsFile) {
        this.attachmentsFile = attachmentsFile;
    }

    public List<KeyValue> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<KeyValue> taskList) {
        this.taskList = taskList;
    }

    public List<KeyValue> getSubtaskList() {
        return subtaskList;
    }

    public void setSubtaskList(List<KeyValue> subtaskList) {
        this.subtaskList = subtaskList;
    }

    public List<KeyValue> getAssignList() {
        return assignList;
    }

    public void setAssignList(List<KeyValue> assignList) {
        this.assignList = assignList;
    }

    //commented for corporate
    /*public MCrmAgentsTaskAlloc getCrmAgentsTask() {
        return crmAgentsTask;
    }

    public void setCrmAgentsTask(MCrmAgentsTaskAlloc crmAgentsTask) {
        this.crmAgentsTask = crmAgentsTask;
    }*/

    public MCrmAgentsTaskAlloc getmCrmAgentsTask() {
        return mCrmAgentsTask;
    }

    public void setmCrmAgentsTask(MCrmAgentsTaskAlloc mCrmAgentsTask) {
        this.mCrmAgentsTask = mCrmAgentsTask;
    }

    public String[] getCatAgentTempId() {
        return catAgentTempId;
    }

    public void setCatAgentTempId(String[] catAgentTempId) {
        this.catAgentTempId = catAgentTempId;
    }

    public String[] getCatAllocTempRatio() {
        return catAllocTempRatio;
    }

    public void setCatAllocTempRatio(String[] catAllocTempRatio) {
        this.catAllocTempRatio = catAllocTempRatio;
    }

    public List<KeyValue> getPriorityList() {
        return priorityList;
    }

    public void setPriorityList(List<KeyValue> priorityList) {
        this.priorityList = priorityList;
    }

    public List<KeyValue> getFilterlist() {
        return filterlist;
    }

    public void setFilterlist(List<KeyValue> filterlist) {
        this.filterlist = filterlist;
    }

    public List<KeyValue> getOperatorlist() {
        return operatorlist;
    }

    public void setOperatorlist(List<KeyValue> operatorlist) {
        this.operatorlist = operatorlist;
    }

    public List<KeyValue> getValuelist() {
        return valuelist;
    }

    public void setValuelist(List<KeyValue> valuelist) {
        this.valuelist = valuelist;
    }

    public String getFlex4() {
        return flex4;
    }

    public void setFlex4(String flex4) {
        this.flex4 = flex4;
    }

    public String getFlex5() {
        return flex5;
    }

    public void setFlex5(String flex5) {
        this.flex5 = flex5;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public CrmUser getCrmUser() {
        return crmUser;
    }

    public void setCrmUser(CrmUser crmUser) {
        this.crmUser = crmUser;
    }

    public List<KeyValue> getUserTeamlist() {
        return userTeamlist;
    }

    public void setUserTeamlist(List<KeyValue> userTeamlist) {
        this.userTeamlist = userTeamlist;
    }

    public List<KeyValue> getUserRoleSeqlist() {
        return userRoleSeqlist;
    }

    public void setUserRoleSeqlist(List<KeyValue> userRoleSeqlist) {
        this.userRoleSeqlist = userRoleSeqlist;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public static Map<Integer, String> getMonthRangeList() {
        return monthRangeList;
    }

    public AdminDAO.YearRange getYearRange() {
        return yearRange;
    }

    public void setYearRange(AdminDAO.YearRange yearRange) {
        this.yearRange = yearRange;
    }

    public List<KeyValue> getYearRangeList() {
        return yearRangeList;
    }

    public void setYearRangeList(List<KeyValue> yearRangeList) {
        this.yearRangeList = yearRangeList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<String> getYearList() {
        return yearList;
    }

    public void setYearList(List<String> yearList) {
        this.yearList = yearList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUserAdministratorYn() {
        return userAdministratorYn;
    }

    public void setUserAdministratorYn(String userAdministratorYn) {
        this.userAdministratorYn = userAdministratorYn;
    }

    public List<KeyValue> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<KeyValue> categoryList) {
        this.categoryList = categoryList;
    }

    public LeadDAO getLeadDao() {
        return leadDao;
    }

    public void setLeadDao(LeadDAO leadDao) {
        this.leadDao = leadDao;
    }

    public List<KeyValue> getUserTypeList() {
        return userTypeList;
    }

    public void setUserTypeList(List<KeyValue> userTypeList) {
        this.userTypeList = userTypeList;
    }

    public List<KeyValue> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<KeyValue> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getUrole() {
        return urole;
    }

    public void setUrole(String urole) {
        this.urole = urole;
    }

    public List<KeyValue> getUserAgentTypelist() {
        return userAgentTypelist;
    }

    public void setUserAgentTypelist(List<KeyValue> userAgentTypelist) {
        this.userAgentTypelist = userAgentTypelist;
    }

    /**
     * @return the feedBackList
     */
    public List<FeedBackInfoBO> getFeedBackList() {
        return feedBackList;
    }

    /**
     * @param feedBackList the feedBackList to set
     */
    public void setFeedBackList(List<FeedBackInfoBO> feedBackList) {
        this.feedBackList = feedBackList;
    }

    public Map<Integer, String> getCallLogMap() {
        return callLogMap;
    }

    public void setCallLogMap(Map<Integer, String> callLogMap) {
        this.callLogMap = callLogMap;
    }

    public Map<Integer, Integer> getHolidayYearList() {
        return holidayYearList;
    }

    public void setHolidayYearList(Map<Integer, Integer> holidayYearList) {
        this.holidayYearList = holidayYearList;
    }

    public CrmHolidays getCrmHolidays() {
        return crmHolidays;
    }

    public void setCrmHolidays(CrmHolidays crmHolidays) {
        this.crmHolidays = crmHolidays;
    }

    public Map<Integer, Integer> getYearsList() {
        return yearsList;
    }

    public void setYearsList(Map<Integer, Integer> yearsList) {
        this.yearsList = yearsList;
    }

}
