/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.vo.BulkSMSEmail;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.EmailMarketingDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.vo.MailCampaign;
import qa.com.qic.common.vo.MailTxn;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.email.core.MailBodyBuilder;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class EmailMarketingAction extends ActionSupport implements SessionAware {

    private static final Logger LOGGER = LogUtil.getLogger(EmailMarketingAction.class);
    private transient Map<String, Object> session;

    private transient List<? extends Object> aaData;
    private EmailMarketingDAO dao;
    private transient UserInfo userInfo;
    private Map<String, String> sendOptionList = null;
    private Map<String, String> sendToList = null;
    private List<KeyValue> customizeList = null;
    private Map<String, String> templateList = null;
    private List<BulkSMSEmail> bulksmsDetails = null;
    private BigDecimal mcId;

    private transient BulkSMSEmail bulkSMSEmail = new BulkSMSEmail();

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

    private transient MailCampaign campaign;
    private transient MailTxn mailTxn;
    private transient List<KeyValue> statusEmailList;

    public String openCampaignPage() {
        return SUCCESS;
    }

    public String loadCampaignTxnData() {
        dao = new EmailMarketingDAO(getUserInfo().getCompanyCode());
        setAaData(dao.loadCampaignTxnData(getMailTxn()));
        return "data";
    }

    public String loadBulkEmailedit() throws Exception {
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            dao = new EmailMarketingDAO(userInfo.getCompanyCode());
            if (StringUtils.isNoneBlank(getCampaign().getMcCampName())) {
                //dao.loadEmailtxt(getCampaign());
                setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            } else {
                setMessage("Not successfuly added");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "data";
    }

    public String loadCampaignData() {
        dao = new EmailMarketingDAO(getUserInfo().getCompanyCode());
        setAaData(dao.loadCampaignList(getCampaign()));//getCampaign()
        return "data";
    }

    public String loadCampaignSummaryData() {
        dao = new EmailMarketingDAO(getUserInfo().getCompanyCode());
        setAaData(dao.loadCampaignChartData(getCampaign()));
        return "data";
    }

    public String openCampaignReport() {
        dao = new EmailMarketingDAO(getUserInfo().getCompanyCode());
        statusEmailList = dao.loadStatusEmailList();
        setAaData(dao.loadCampaignList(getCampaign()));
        if(getAaData() != null && !getAaData().isEmpty()) {
            setCampaign((MailCampaign) getAaData().get(0));
            campaign = dao.loadCampaignBody(campaign);
        }
        return "report";
    }

    public String loadCampaignForm() {
        bulkSMSEmail.setSendOption("E");
        bulkSMSEmail.setSendTo("C");
        return SUCCESS;
    }

    public String sendCampaign() {
        dao = new EmailMarketingDAO(getUserInfo().getCompanyCode());
        getBulkSMSEmail().setMcCrUid(getUserInfo().getUserId());
        String status = dao.sendBulkSMSEmail(getBulkSMSEmail());
        if (status == null) {
            setMessageType("S");
        } else {
            setMessageType("E");
        }
        return "data";
    }

    public String previewCampaignTemplate() {
        dao = new EmailMarketingDAO(getUserInfo().getCompanyCode());
        mailTxn = dao.loadCampaignTxnData(getCampaign().getMcFrom());
        if(mailTxn != null) {
            mailTxn.setBody(getCampaign().getMcBody());
            MailBodyBuilder mbb = new MailBodyBuilder(mailTxn);
            mbb.setBody(mailTxn.getBody());
            mbb.buildBody();
            setMessage(mbb.getBody().toString());
        }
        return "preview";
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

    public MailCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(MailCampaign campaign) {
        this.campaign = campaign;
    }

    public Map<String, String> getSendOptionList() {
        sendOptionList = new LinkedHashMap<>();
        sendOptionList.put("E", "&nbsp;Email&nbsp;");
        sendOptionList.put("S", "&nbsp;SMS&nbsp;");
        return sendOptionList;
    }

    public void setSendOptionList(Map<String, String> sendOptionList) {
        this.sendOptionList = sendOptionList;
    }

    public Map<String, String> getSendToList() {
        sendToList = new LinkedHashMap<>();
        sendToList.put("E", "Excel File");
        sendToList.put("C", "From CRM");
        return sendToList;
    }

    public void setSendToList(Map<String, String> sendToList) {
        this.sendToList = sendToList;
    }

    public List<KeyValue> getCustomizeList() {
        customizeList = new ArrayList<>();
        dao = new EmailMarketingDAO(userInfo.getCompanyCode());
        customizeList = dao.getKeyValue("BULK_MAILS", null, null, userInfo.getCompanyCode());
        return customizeList;
    }

    public void setCustomizeList(List<KeyValue> customizeList) {
        this.customizeList = customizeList;
    }

    public Map<String, String> getTemplateList() {
        dao = new EmailMarketingDAO(userInfo.getCompanyCode());
        List<KeyValue> keyValues = dao.getTemplateList(userInfo.getCompanyCode());
        templateList = new LinkedHashMap<>();
        templateList.put("", "--Select--");
        if (keyValues != null) {
            for (KeyValue keyValue : keyValues) {
                templateList.put(keyValue.getKey(), keyValue.getValue());
            }
        }
        return templateList;
    }

    public void setTemplateList(Map<String, String> templateList) {
        this.templateList = templateList;
    }

    public EmailMarketingDAO getDao() {
        return dao;
    }

    public void setDao(EmailMarketingDAO dao) {
        this.dao = dao;
    }

    public List<BulkSMSEmail> getBulksmsDetails() {
        return bulksmsDetails;
    }

    public void setBulksmsDetails(List<BulkSMSEmail> bulksmsDetails) {
        this.bulksmsDetails = bulksmsDetails;
    }

    public BulkSMSEmail getBulkSMSEmail() {
        return bulkSMSEmail;
    }

    public void setBulkSMSEmail(BulkSMSEmail bulkSMSEmail) {
        this.bulkSMSEmail = bulkSMSEmail;
    }

    public MailTxn getMailTxn() {
        return mailTxn;
    }

    public void setMailTxn(MailTxn mailTxn) {
        this.mailTxn = mailTxn;
    }

    public List<KeyValue> getStatusEmailList() {
        return statusEmailList;
    }

    public void setStatusEmailList(List<KeyValue> statusEmailList) {
        this.statusEmailList = statusEmailList;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
        this.userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
    }
}
