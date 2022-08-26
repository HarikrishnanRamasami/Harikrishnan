/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import qa.com.qic.anoud.vo.BulkSMSEmail;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.BulkEmailSmsDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;
import qa.com.qic.utility.helpers.Validatory;

/**
 *
 * @author ravindar.singh
 */
public class BulkEmailSmsAction extends ActionSupport implements SessionAware, ServletRequestAware, ModelDriven<BulkSMSEmail> {

    private static final Logger LOGGER = LogUtil.getLogger(BulkEmailSmsAction.class);
    private transient HttpServletRequest request;
    private transient Map<String, Object> session;
    private transient BulkEmailSmsDAO dao;

    private transient BulkSMSEmail bulkSMSEmail = new BulkSMSEmail();

    private transient UserInfo userInfo;

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
    private Map<String, String> sendOptionList = null;
    private Map<String, String> sendToList = null;
    private List<KeyValue> customizeList = null;
    private List<KeyValue> keyValues = null;
    private Map<String, String> templateList = null;
    private Map<String, String> smsFromList = null;
    List<BulkSMSEmail> bulksmsDetails = null;
    private File excelFile;
    private String mobileNoLength;
    private String mobileNoStartsWith;
    private String errorMsg;
    private String respMsg;
    private String company;
  //  private String template_code;

    /**
     * Flexible fields
     */
    private transient String flex1;
    private transient String flex2;
    private transient String flex3;

    //DataTables params
    private String search;
    private Integer start;
    private Integer length;
    private Object[][] order;
    private Integer draw;

    private InputStream inputStream;

    @Override
    public BulkSMSEmail getModel() {
        return bulkSMSEmail;
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

    /*public List<? extends Object> getAaData() {
     return aaData;
     }

     public void setAaData(List<? extends Object> aaData) {
     this.aaData = aaData;
     }*/
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

    public BulkSMSEmail getBulkSMSEmail() {
        return bulkSMSEmail;
    }

    public void setBulkSMSEmail(BulkSMSEmail bulkSMSEmail) {
        this.bulkSMSEmail = bulkSMSEmail;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
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
        sendToList.put("C", "Customize");
        return sendToList;
    }

    public void setSendToList(Map<String, String> sendToList) {
        this.sendToList = sendToList;
    }

    public List<KeyValue> getCustomizeList() {
        customizeList = new ArrayList<>();
        dao = new BulkEmailSmsDAO(userInfo.getCompanyCode());
        customizeList = dao.getKeyValue("BULK_MAILS", null, null, userInfo.getCompanyCode());
        return customizeList;
    }

    public void setCustomizeList(List<KeyValue> customizeList) {
        this.customizeList = customizeList;
    }

    public List<KeyValue> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(List<KeyValue> keyValues) {
        this.keyValues = keyValues;
    }

    public Map<String, String> getTemplateList() {
        dao = new BulkEmailSmsDAO(userInfo.getCompanyCode());
        keyValues = dao.getTemplateList(userInfo.getCompanyCode());
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

    public Map<String, String> getSmsFromList() {
        return smsFromList;
    }

    public void setSmsFromList(Map<String, String> smsFromList) {
        this.smsFromList = smsFromList;
    }

    public File getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(File excelFile) {
        this.excelFile = excelFile;
    }

    public String getMobileNoLength() {
        return mobileNoLength;
    }

    public void setMobileNoLength(String mobileNoLength) {
        this.mobileNoLength = mobileNoLength;
    }

    public String getMobileNoStartsWith() {
        return mobileNoStartsWith;
    }

    public void setMobileNoStartsWith(String mobileNoStartsWith) {
        this.mobileNoStartsWith = mobileNoStartsWith;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<BulkSMSEmail> getBulksmsDetails() {
        return bulksmsDetails;
    }

    public void setBulksmsDetails(List<BulkSMSEmail> bulksmsDetails) {
        this.bulksmsDetails = bulksmsDetails;
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

    public String loadBulkSMSEmail() {
        request.setAttribute("customizeList", getCustomizeList());
        bulkSMSEmail.setSendOption("E");
        return SUCCESS;
    }

    public String loadExtractDataPage() {
        return SUCCESS;
    }

    public String loadExtractDataList() {
        dao = new BulkEmailSmsDAO(userInfo.getCompanyCode());
        if ("upload".equals(operation)) {
            List<BulkSMSEmail> temp = dao.bulksmsDetails(userInfo.getCompanyCode());
            bulkSMSEmail.setAaData(temp);
        } else {
            String status = null;
            //For email campaign
            if (!"email".equals(operation)) {
                status = dao.loadBulkSMSEmail(bulkSMSEmail.getSendOption(), bulkSMSEmail.getCustomize(), bulkSMSEmail.getIncludePol(), userInfo.getCompanyCode());
            }
            if (status == null) {
                bulksmsDetails = dao.bulksmsDetails(start, length, userInfo.getCompanyCode());
                bulkSMSEmail.setAaData(bulksmsDetails);
                // setAaData(bulksmsDetails);
            }
        }
        return "data";
    }

    public String sendBulkSMSEmail() {
        dao = new BulkEmailSmsDAO(userInfo.getCompanyCode());
        String status = dao.sendBulkSMSEmail(bulkSMSEmail.getSendOption(), bulkSMSEmail.getTemplate(), (String) session.get("companyCode"));
        if (status == null) {
            respMsg = "S";
        } else {
            respMsg = "E";
        }
        return SUCCESS;
    }

    public String previewSMSEmail() {
        dao = new BulkEmailSmsDAO(userInfo.getCompanyCode());
        String status = dao.previewSMSEmail(bulkSMSEmail.getSendOption(), bulkSMSEmail.getTemplate(), bulkSMSEmail.getRowId(), (String) session.get("companyCode"));
        if (status == null) {
            respMsg = "";
        } else {
            respMsg = status;
        }
        return SUCCESS;
    }

    public String loadTempCodeSummary() {
        dao = new BulkEmailSmsDAO(userInfo.getCompanyCode());
        System.out.println("template_code" + bulkSMSEmail.getTemplate_code());
        bulkSMSEmail = dao.getEditSMSEmailTemplate(bulkSMSEmail.getTemplate_code(), userInfo.getCompanyCode());
        return SUCCESS;
    }

    public String upload() {
        LOGGER.info("UploadProcess - Upload() Enter");
        dao = new BulkEmailSmsDAO(userInfo.getCompanyCode());
        String returnResult;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        File csvFile = null;
        if (excelFile != null) {
            csvFile = excelFile;
        }
        if ((errorMsg == null || "".equalsIgnoreCase(errorMsg.trim()))) {
            if (csvFile != null) {
                setMobileNoLength(getText(getText(userInfo.getCompanyCode()) + "_MOBILE_LENGTH"));
                setMobileNoStartsWith(getText(getText(userInfo.getCompanyCode()) + "_MOBILE_NO_STARTS_WITH"));
                errorMsg = dao.insertUploadedDetails(csvFile, userInfo.getCompanyCode(), bulkSMSEmail.getSendOption(), getMobileNoLength(), getMobileNoStartsWith());
            }

        }
        if ((errorMsg == null || "".equalsIgnoreCase(errorMsg.trim()))) {
            loadBulkSMSEmail();
        }
        returnResult = INPUT;
        bulkSMSEmail.setSendTo(bulkSMSEmail.getSendTo());
        bulkSMSEmail.setSendOption(bulkSMSEmail.getSendOption());
        bulkSMSEmail.setRowId(bulkSMSEmail.getRowId());
        bulkSMSEmail.setUploadParam1("S");
        LOGGER.info("UploadProcess - Upload() Exit" + returnResult);
        return returnResult;
    }

    public String displayExtractData() {
        dao = new BulkEmailSmsDAO(userInfo.getCompanyCode());
        List<BulkSMSEmail> temp = dao.bulksmsDetails(userInfo.getCompanyCode());
        request.setAttribute("bulksmsDetails", temp);
        return SUCCESS;
    }

    public String getErrorMessage() {
        HttpServletResponse response = ServletActionContext.getResponse();
        dao = new BulkEmailSmsDAO(userInfo.getCompanyCode());
        response.setContentType("text/html;charset=UTF-8");
        String errorCode = request.getParameter("errorCode") == null ? "" : request.getParameter("errorCode");
        String errMsg = dao.getErrorMsg(errorCode, userInfo.getCompanyCode());
        errMsg = errMsg != null ? errMsg : "";
        inputStream = new ByteArrayInputStream(errMsg.getBytes());
        return SUCCESS;
    }

    public String updateSMSEmailTemplate() throws IOException {
        dao = new BulkEmailSmsDAO(userInfo.getCompanyCode());
        boolean emailid_status = Validatory.isValidEmail(bulkSMSEmail.getEmailfrom());
        if (bulkSMSEmail.getDescription().equalsIgnoreCase("")) {
            setMessage(getText("error.tamplatedesc"));
            setMessageType("E");
        } else if (bulkSMSEmail.getEmailfrom().length() != 0 && (!emailid_status)) {
            setMessage(getText("error.validtamplateemailfrom"));
            setMessageType("E");
        } else if (bulkSMSEmail.getEmailto().length() != 0 && (!emailid_status)) {
            setMessage(getText("error.validtamplateemailfrom"));
            setMessageType("E");
        } else {
            dao.updateSMSEmailTemplateService(bulkSMSEmail, userInfo.getCompanyCode());
            setMessageType("S");
        }
        return "upd_data";

    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

}
