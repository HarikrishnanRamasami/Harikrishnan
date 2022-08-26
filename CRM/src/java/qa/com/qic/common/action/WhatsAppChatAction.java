/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.dao.DashboardDAO;
import qa.com.qic.common.dao.DashboardDAO.DateRange;
import qa.com.qic.common.dao.WhatsAppDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.FileDescriptor;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.MWhatsappAutoMessage;
import qa.com.qic.common.vo.TWhatsappLog;
import qa.com.qic.common.vo.TWhatsappTxn;
import qa.com.qic.common.vo.TWhatsappTxnHist;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.crm.restapi.resources.services.WhatsAppProperties;
import qa.com.qic.crm.restapi.resources.services.WhatsAppProperty.Vendor;
import qa.com.qic.crm.restapi.resources.services.WhatsAppService;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class WhatsAppChatAction extends ActionSupport implements SessionAware, ServletRequestAware {

    private static final Logger logger = LogUtil.getLogger(WhatsAppChatAction.class);

    private String company;
    private List<TWhatsappTxn> txnList;
    private TWhatsappTxn txn;
    private TWhatsappTxnHist txnHist;
    private List<TWhatsappLog> logList;
    private transient DateRange dateRange;
    private transient List<KeyValue> dateRangeList;
    private transient List<KeyValue> keyValueList;
    private transient List<KeyValue> userList;
    private transient List<KeyValue> countryList;
    private transient CommonDAO commonDao;
    /**
     * messageType - S: Success, E: Error message - details description about
     * success/error
     */
    private String message;
    private String messageType;
    private transient AnoudDAO anoudDao;
    private transient List<KeyValue> chatReasonList;
    private transient HttpServletRequest request;
    private transient Map<String, Object> session;
    private transient WhatsAppDAO dao;
    private transient UserInfo userInfo;
    private TWhatsappLog log;
    private File[] attachment;
    private String[] attachmentFileName;
    private String attachmentContentType;
    private List<? extends Object> aaData;

    private Map<String, Object> chartData;

    private MWhatsappAutoMessage autoMsg;
    private List<? extends Object> msgData;
    private String fetch;

    public String loadWhatsAppMsgPage() throws Exception {
        anoudDao = new AnoudDAO(getCompany());
        getUserInfo().setActiveMenu(TypeConstants.Menu.DASHBOARD);
        chatReasonList = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_CALL_TYPE);
        keyValueList = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_HAND_SMS);
        for (KeyValue kv : keyValueList) {
            if (kv.getInfo2() != null) {
                kv.setInfo2(kv.getInfo2().replaceAll("\r\n|\n\r|\n|\r", "<br>"));
            }
        }
        dao = new WhatsAppDAO(getCompany());
        userList = dao.getWhatsupPermissionUsers();
        countryList = dao.loadCountryList();
        return SUCCESS;
    }

    public String openWhatsAppDetails() throws Exception {
        return SUCCESS;
    }

    public String loadWhatsAppMasterScreen() {
        return SUCCESS;
    }

    public String saveWhatsAppAutoForm() {
        try {
            dao = new WhatsAppDAO(getCompany());
            if ("false".equals(autoMsg.getWamRepeatYn())) {
                autoMsg.setWamRepeatYn("N");
                for (int i = 1; i <= 7; i++) {
                    BeanUtils.setProperty(autoMsg, "wamDay" + i + "Yn", "N");
                }
            } else {
                autoMsg.setWamRepeatYn("Y");
                for (int i = 1; i <= 7; i++) {
                    if ("false".equals(BeanUtils.getProperty(autoMsg, "wamDay" + i + "Yn"))) {
                        BeanUtils.setProperty(autoMsg, "wamDay" + i + "Yn", "N");
                    } else {
                        BeanUtils.setProperty(autoMsg, "wamDay" + i + "Yn", "Y");
                    }
                }
            }
            validateSaveWhatsAppAutoForm();
            if (message == null) {
                dao.saveWhatsAppAutoForm(autoMsg, userInfo.getUserId());
            }
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
        }
        return SUCCESS;
    }

    public void validateSaveWhatsAppAutoForm() {
        if (autoMsg.getWamEffFromDate() == null && autoMsg.getWamEffToDate() == null && (autoMsg.getWamFromTime() == null || "".equalsIgnoreCase(autoMsg.getWamFromTime().trim())) && (autoMsg.getWamToTime() == null || "".equalsIgnoreCase(autoMsg.getWamToTime().trim()))) {
            setMessage("Please enter either Date or Time");
        } else if (autoMsg.getWamEffFromDate() == null && autoMsg.getWamEffToDate() != null) {
            setMessage("Please enter Effective From Date");
        } else if (autoMsg.getWamEffFromDate() != null && autoMsg.getWamEffToDate() == null) {
            setMessage("Please enter Effective To Date");
        } else if (autoMsg.getWamFromTime() != null && (!"".equalsIgnoreCase(autoMsg.getWamFromTime())) && (autoMsg.getWamToTime() == null || "".equalsIgnoreCase(autoMsg.getWamToTime()))) {
            setMessage("Please enter to Time");
        } else if (autoMsg.getWamToTime() != null && (!"".equalsIgnoreCase(autoMsg.getWamToTime())) && (autoMsg.getWamFromTime() == null || "".equalsIgnoreCase(autoMsg.getWamFromTime()))) {
            setMessage("Please enter from Time");
        } else if (autoMsg.getWamMessage() == null || "".equalsIgnoreCase(autoMsg.getWamMessage().trim())) {
            setMessage("Please enter message");
        }
    }

    public String loadAutoMessageData() {
        try {
            dao = new WhatsAppDAO(getCompany());
            msgData = dao.loadAutoMessageData();
        } catch (Exception e) {
            msgData = new ArrayList<>();
        }
        return "success";
    }

    public String openDashBoardPage() {
        dateRangeList = new LinkedList<>();
        KeyValue kv;
        for (DashboardDAO.DateRange d : DateRange.values()) {
            if ("CUSTOM".equals(d.name())) {
                continue;
            }
            kv = new KeyValue(d.name(), d.getDesc());
            dateRangeList.add(kv);
        }
        dateRange = DashboardDAO.DateRange.THIS_MONTH;
        return SUCCESS;
    }

    public String openWhatsAppTxnHistoryPage() {
        try {
            dao = new WhatsAppDAO(getCompany());
            commonDao = new CommonDAO(getCompany());
            txnHist = new TWhatsappTxnHist();
            userList = commonDao.loadCrmAgentList(userInfo.getUserId());
            if (null != userList) {
                userList.add(0, new KeyValue("ALL", "All"));
            }
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -7);
            txnHist.setWhFromDate(c.getTime());
            txnHist.setWhToDate(new Date());
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return SUCCESS;
    }

    public String loadDashSummaryByPeriod() {
        try {
            dao = new WhatsAppDAO(getCompany());
            aaData = dao.loadTaskSummaryByPeriod(getDateRange());
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            if (aaData == null) {
                aaData = new ArrayList<>();
            }
        }
        return SUCCESS;
    }

    public String loadDashOpenSummaryByPeriod() {
        try {
            dao = new WhatsAppDAO(getCompany());
            aaData = dao.loadWhatsAppDashOpenSummaryByPeriod(getDateRange(), getFetch(), userInfo.getUserId());
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            if (aaData == null) {
                aaData = new ArrayList<>();
            }
        }
        return SUCCESS;
    }

    public String loadDashMonthlySummaryByPeriod() {
        try {
            dao = new WhatsAppDAO(getCompany());
            aaData = dao.loadWhatsAppDashMonthlySummaryByPeriod(getDateRange(), userInfo.getUserId());
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            if (aaData == null) {
                aaData = new ArrayList<>();
            }
        }
        return SUCCESS;
    }

     public String loadDashMonthlySummaryByCloseCode() {
        try {
            dao = new WhatsAppDAO(getCompany());
            chartData = dao.loadDashMonthlySummaryByCloseCode();
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
        }
        return SUCCESS;
    }

    public String loadWhatsAppData() {
        try {
            dao = new WhatsAppDAO(getCompany());
            txnList = dao.loadUnreadMessages(getTxn());
        } catch (Exception e) {
        } finally {
            if (txnList == null) {
                txnList = new ArrayList<>();
            }
        }
        return SUCCESS;
    }

    public String loadWhatsAppHistoryData() {
        try {
            dao = new WhatsAppDAO(getCompany());
            if ("ALL".equals(getTxnHist().getWhAssignedTo())) {
                getTxnHist().setWhAssignedTo("");
            }
            aaData = dao.loadWhatsAppHistoryData(getTxnHist());
        } catch (Exception e) {
        } finally {
            if (aaData == null) {
                aaData = new ArrayList<>();
            }
        }
        return SUCCESS;
    }

    public String loadWhatsAppHistoryDataByMessage() {
        try {
            dao = new WhatsAppDAO(getCompany());
            aaData = dao.loadWhatsAppHistoryDataByMessage(getTxnHist());
        } catch (Exception e) {
        } finally {
            if (aaData == null) {
                aaData = new ArrayList<>();
            }
        }
        return SUCCESS;
    }

    public String transferRequest() {
        try {
            dao = new WhatsAppDAO(getCompany());
            if (!txn.getWtAssignedTo().equals(userInfo.getUserId())) {
                txn.setWtAssignedTo(userInfo.getUserId());
                dao.assignNewUser(txn);
            }
            dao.resetUnreadMsgCount(txn.getWtTxnId());
            logList = dao.fetchWhatsAppLog(txn.getWtMobileNo());
            //commented for corporate
            //logList = dao.fetchWhatsAppLog(txn.getWtMobileNo(), txn.getWtCrmId());
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return SUCCESS;
    }

    public String loadWhatsAppLog() {
        try {
            dao = new WhatsAppDAO(getCompany());
            aaData = dao.fetchWhatsAppLog(txn.getWtMobileNo());
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            if (aaData == null) {
                aaData = new ArrayList<>();
            }
        }
        return "data";
    }

    public String loadLatestConversation() {
        try {
            dao = new WhatsAppDAO(getCompany());
            if (getLog() != null && StringUtils.isNotBlank(getLog().getWlMobileNo()) && getLog().getWlMobileNo().length() >= 8) {
                aaData = dao.loadLatestConversation(getLog().getWlMobileNo());
            }
        } catch (Exception e) {
        } finally {
            if (aaData == null) {
                aaData = new ArrayList<>();
            }
        }
        return SUCCESS;
    }

    public void resolveChat() {
        try {
            WhatsAppService service = new WhatsAppService(getCompany());
            dao = new WhatsAppDAO(getCompany());
            txn.setWtCloseUid((userInfo.getUserId()));
            if (StringUtils.isNoneBlank(txn.getWtCloseCode())) {
                Vendor vendor = service.validateVendor();
                if (vendor == Vendor.ENGATI) {
                    boolean response = service.chatResolveFromEngati(txn.getWtMobileNo());
                    if (response) {
                        dao.updateChatReason(txn);
                        setMessage("Updated successfuly");
                    } else {
                        setMessage("Chat could not resloved due unexpected error");
                    }
                } else {
                    dao.updateChatReason(txn);
                    setMessage("Updated successfuly");
                }
            } else {
                setMessage("Please enter data");
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
            logger.error("{}", e);
        }
    }

    public void updateTxnName() {
        try {
            dao = new WhatsAppDAO(getCompany());
            txn.setWtAssignedTo(userInfo.getUserId());
            dao.updateTxnName(txn);
        } catch (Exception e) {
            setMessage(e.getMessage());
            logger.error("{}", e);
        }
    }

    public void updateForwardUser() {
        try {
            dao = new WhatsAppDAO(getCompany());
            dao.assignNewUser(txn);
        } catch (Exception e) {
            setMessage(e.getMessage());
            logger.error("{}", e);
        }
    }

    public TWhatsappLog getLog() {
        return log;
    }

    public void setLog(TWhatsappLog log) {
        this.log = log;
    }

    public File[] getAttachment() {
        return attachment;
    }

    public void setAttachment(File[] attachment) {
        this.attachment = attachment;
    }

    public String[] getAttachmentFileName() {
        return attachmentFileName;
    }

    public void setAttachmentFileName(String[] attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public String sendMessage() {
        WhatsAppService service = new WhatsAppService(getCompany());
        WhatsAppProperties whatsAppProperties = new WhatsAppProperties(getCompany());
        try {
            dao = new WhatsAppDAO(getCompany());
            log.setWlCrUid(userInfo.getUserId());
            List<FileDescriptor> fileList = handleAttachments(whatsAppProperties, null, null);
            if (fileList != null) {
                FileDescriptor fileDesc = fileList.get(0);
                log.setWlMsgUrl(fileDesc.getFilePath() + fileDesc.getFileName());
                log.setWlFilePath(fileDesc.getFilePath() + fileDesc.getFileName());
            }
            Long logId = service.sendMessage(log);
            if (logId != null) {
                log = dao.fetchWhatsAppLog(logId);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return SUCCESS;
    }

    public String sendInviteMessage() {
        WhatsAppService service = new WhatsAppService(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            dao = new WhatsAppDAO(getCompany());
            log.setWlCrUid(userInfo.getUserId());
            Long logId = service.sendInviteMessage(log);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            setMessage("Message sent successfuly");
        } catch (Exception e) {
            setMessage(null == e.getMessage() ? "Unable to process" : e.getMessage());
            logger.error("", e);
        }
        return SUCCESS;
    }

    private List<FileDescriptor> handleAttachments(WhatsAppProperties whatsAppProperties, String destPath, String fileName) throws IOException {
        List<FileDescriptor> fileList = null;
        if (getAttachment() != null && getAttachment().length > 0) {
            fileList = new ArrayList<>();
            destPath = (StringUtils.isBlank(destPath) ? String.valueOf(new Date().getTime()) : destPath);
            if (!destPath.matches(".*(/|\\\\)$")) {
                destPath += File.separator;
            }
            fileName = StringUtils.isBlank(fileName) ? UUID.randomUUID().toString() : fileName;
            int len = getAttachment().length;
            for (int i = 0, j = 1; i < len; i++, j++) {
                File file = getAttachment()[i];
                if (file == null) {
                    continue;
                }
                String orgFileName = getAttachmentFileName()[i];
                String fileExtension = FilenameUtils.getExtension(orgFileName);
                String _fileName = fileName + "_" + j + "." + fileExtension;
                File f = new File(whatsAppProperties.getProp().getBaseFileStorePath() + destPath);
                if (!f.exists()) {
                    f.mkdirs();
                }
                File destFile = new File(f, _fileName);
                FileUtils.copyFile(file, destFile);
                FileDescriptor fileDesc = new FileDescriptor();
                fileDesc.setFilePath(destPath);
                fileDesc.setFileExtn(fileExtension);
                fileDesc.setFileName(_fileName);
                fileDesc.setOrgFile(orgFileName);
                fileList.add(fileDesc);
            }
        }
        return fileList;
    }

    public String loadWhatsDocData() {
        try {
            dao = new WhatsAppDAO(getCompany());
            aaData = dao.fetchWhatsAppDocLog(getTxn().getWtTxnId());
        } catch (Exception e) {
            logger.error("Exception while loading whatsapp Doc Data", e);
        } finally {
            if (aaData == null) {
                aaData = new ArrayList<>();
            }
        }
        return SUCCESS;
    }

    /**
     * @return the userInfo
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
        this.company = (String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE);
        this.userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public List<TWhatsappTxn> getTxnList() {
        return txnList;
    }

    public void setTxnList(List<TWhatsappTxn> txnList) {
        this.txnList = txnList;
    }

    public TWhatsappTxn getTxn() {
        return txn;
    }

    public void setTxn(TWhatsappTxn txn) {
        this.txn = txn;
    }

    public List<TWhatsappLog> getLogList() {
        return logList;
    }

    public void setLogList(List<TWhatsappLog> logList) {
        this.logList = logList;
    }

    public List<KeyValue> getChatReasonList() {
        return chatReasonList;
    }

    public void setChatReasonList(List<KeyValue> chatReasonList) {
        this.chatReasonList = chatReasonList;
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

    public List<KeyValue> getKeyValueList() {
        return keyValueList;
    }

    public void setKeyValueList(List<KeyValue> keyValueList) {
        this.keyValueList = keyValueList;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public List<KeyValue> getDateRangeList() {
        return dateRangeList;
    }

    public void setDateRangeList(List<KeyValue> dateRangeList) {
        this.dateRangeList = dateRangeList;
    }

    public List<? extends Object> getAaData() {
        return aaData;
    }

    public void setAaData(List<? extends Object> aaData) {
        this.aaData = aaData;
    }

    /*   public List<KeyValue> getHourList() {
        return hourList;
    }

    public void setHourList(List<KeyValue> hourList) {
       for(int i=0;i<=24;i++)
       {
           if(i<10)
           {
               hourList.add("0"+i,"0"+i);
           }
           else{
               hourList.add(String.valueOf(i),String.valueOf(i));
           }
       }

        this.hourList = hourList;
    }

    public List<KeyValue> getMinuteList() {
        return minuteList;
    }

    public void setMinuteList(List<KeyValue> minuteList) {
         for(int i=0;i<=60;i++)
       {
           if(i<10)
           {
               minuteList.add("0"+i);
           }
           else{
               minuteList.add(String.valueOf(i));
           }
       }
        this.minuteList = minuteList;
    }*/
    public MWhatsappAutoMessage getAutoMsg() {
        return autoMsg;
    }

    public void setAutoMsg(MWhatsappAutoMessage autoMsg) {
        this.autoMsg = autoMsg;
    }

    public List<? extends Object> getMsgData() {
        return msgData;
    }

    public void setMsgData(List<? extends Object> msgData) {
        this.msgData = msgData;
    }

    public String getFetch() {
        return fetch;
    }

    public void setFetch(String fetch) {
        this.fetch = fetch;
    }

    public TWhatsappTxnHist getTxnHist() {
        return txnHist;
    }

    public void setTxnHist(TWhatsappTxnHist txnHist) {
        this.txnHist = txnHist;
    }

    public List<KeyValue> getUserList() {
        return userList;
    }

    public void setUserList(List<KeyValue> userList) {
        this.userList = userList;
    }

    public List<KeyValue> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<KeyValue> countryList) {
        this.countryList = countryList;
    }

    public Map<String, Object> getChartData() {
        return chartData;
    }

    public void setChartData(Map<String, Object> chartData) {
        this.chartData = chartData;
    }

}
