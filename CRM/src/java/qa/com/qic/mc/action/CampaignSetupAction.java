/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.mc.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.reports.DynamicReportsService;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.email.core.MailBodyBuilder;
import qa.com.qic.email.core.MailBodyDataPropertyMap;
import qa.com.qic.mc.dao.CampaignSetupDAO;
import qa.com.qic.mc.dao.CampaignSetupDAO.DateRange;
import qa.com.qic.mc.model.MMktgCampaign;
import qa.com.qic.mc.model.MMktgCampaignDataMap;
import qa.com.qic.mc.model.MMktgCampaignDataParam;
import qa.com.qic.mc.model.MMktgCampaignPath;
import qa.com.qic.mc.model.MMktgCampaignPathFlow;
import qa.com.qic.mc.model.MMktgCampaignTxnData;
import qa.com.qic.mc.vo.CampaignVO;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class CampaignSetupAction extends ActionSupport implements ModelDriven<CampaignVO>, SessionAware, ServletRequestAware {

    private static final Logger LOGGER = LogUtil.getLogger(CampaignSetupAction.class);
    private transient Map<String, Object> session;
    CampaignVO bean = new CampaignVO();
    private transient UserInfo userInfo;
    private transient CampaignSetupDAO dao;
    private DynamicReportsService service;
    private transient List<KeyValue> dateRangeList;
    private transient DateRange dateRange;
    private InputStream fileInputStream;
    private File file;
    private String filePath;
    private transient HttpServletRequest request;

    public static final String MAIL_HOST = "smtp.qichoappl.com";
    public static final String MAIL_PORT = "587";
    public static final String MAIL_USER_ID = "rioadmin";
    public static final String MAIL_PASSWORD = "8htdQxS4DCZBz78flDnz";
    public static final String MAIL_FROM = "AnoudAdmin<anoudadmin@qicgroup.com.qa>";

    public String openCampaignPage() {
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        getModel().setCampStatusList(dao.getMAppParameter(FieldConstants.AppParameter.CAMP_STATUS));
        return SUCCESS;
    }

    public String campaignList() {
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        getModel().setCampStatusList(dao.getMAppParameter(FieldConstants.AppParameter.CAMP_STATUS));
        return SUCCESS;
    }

    public String loadCampaignData() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        getModel().setAaData(dao.loadCampaignList(getModel().getCampaign()));//getCampaign()
        return "data";
    }

    public String addCampaign() {
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        CampaignSetupDAO campaignDao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        service = new DynamicReportsService(getUserInfo().getCompanyCode());
        getModel().setDataSourceList(dao.getMAppParameter(FieldConstants.AppParameter.DATA_SOURCE));
        getModel().setCampTypeList(dao.getMAppParameter(FieldConstants.AppParameter.CAMP_TYPE));
        getModel().setStartTimeList(FieldConstants.START_TIME_LIST);

        for (KeyValue kv : getModel().getCampTypeList()) {
            if ("P".equals(kv.getKey())) {
                getModel().getCampTypeList().remove(kv);
            }
        }
        if ("add".equals(getModel().getOper())) {
            getModel().setSenderAndReplyList(campaignDao.loadSenderAndReplyList("E"));
            MMktgCampaign campaign = new MMktgCampaign();
            campaign.setMcStatus("P");
            getModel().setCampaign(campaign);
        }
        getModel().setScheduleModeList(dao.getMAppParameter(FieldConstants.AppParameter.SCHEDULE_MODE));
        getModel().setReportsViewList(service.loadReportViews("email"));
        if (getModel().getMcCampId() != null) {
            CampaignSetupDAO campDAO = new CampaignSetupDAO(getUserInfo().getCompanyCode());
            MMktgCampaign mc = campDAO.loadCampaign(getModel().getMcCampId());
            if ("S".equals(mc.getMcStatus()) && "1".equals(mc.getMcRecurringYn()) && "change".equals(getModel().getOper())) {
                getModel().setOper("edit");
                mc.setMcStatus("P");
                mc.setMcUpdUid(userInfo.getUserId());
                campaignDao.updateCampaignStatus(mc);
            }
            getModel().setCampaign(mc);
            getModel().setSenderAndReplyList(campaignDao.loadSenderAndReplyList(bean.getCampaign().getMcCampType()));

            if (getModel().getCampaign().getMcScheduleTime() != null) {
                DecimalFormat df = new DecimalFormat("#0.00");
                String s = df.format(getModel().getCampaign().getMcScheduleTime().doubleValue());
                String[] schTime = s.split("\\.");
                getModel().getCampaign().setMcScheduleTimeHrs(Integer.valueOf(schTime[0]));
                getModel().getCampaign().setMcScheduleTimeMin(Integer.valueOf(schTime[1]));
            }
        }
        getModel().setOper(getModel().getOper());
        return SUCCESS;
    }

    public String saveCampaign() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            validateSaveCampaignForm();
            LOGGER.info("Message is**" + getModel().getMessage());
            if (getModel().getMessage() == null) {
                if (getModel().getCampaign().getMcScheduleTimeHrs() == null) {
                    getModel().getCampaign().setMcScheduleTimeHrs(0);
                }
                if (getModel().getCampaign().getMcScheduleTimeMin() == null) {
                    getModel().getCampaign().setMcScheduleTimeMin(0);
                }
                getModel().getCampaign().setMcScheduleTime(new BigDecimal(getModel().getCampaign().getMcScheduleTimeHrs() + "." + getModel().getCampaign().getMcScheduleTimeMin()));
                dao.saveCampaignData(getModel().getCampaign(), userInfo.getUserId(), getModel().getOper());
                getModel().setMcCampId(getModel().getCampaign().getMcCampId());

            }

        } catch (Exception e) {
            getModel().setMessage(e.getMessage());
            LOGGER.error("Error while saving campaign", e);
        }
        return SUCCESS;
    }

    public void validateSaveCampaignForm() {
    	//commented for corporate
        /*if (getModel().getCampaign().getMcCampName() == null || "".equalsIgnoreCase(getModel().getCampaign().getMcCrmId())) {
            getModel().setMessage("Please select the entity");
        } else */


        if (getModel().getCampaign().getMcCampName() == null || "".equalsIgnoreCase(getModel().getCampaign().getMcCampName().trim())) {
            getModel().setMessage("Please enter campaign name");
        } else if (getModel().getCampaign().getMcCampCode() == null || "".equalsIgnoreCase(getModel().getCampaign().getMcCampCode().trim())) {
            getModel().setMessage("Please enter campaign code");
        } else if (getModel().getCampaign().getMcDataSourceType() == null || "".equalsIgnoreCase(getModel().getCampaign().getMcDataSourceType().trim())) {
            getModel().setMessage("Please enter data source");
        } else if (getModel().getCampaign().getMcCampType() == null || "".equalsIgnoreCase(getModel().getCampaign().getMcCampType().trim())) {
            getModel().setMessage("Please enter campaign type");
        }
    }

    public String openCampaignForms() {
        LOGGER.info("OpenCampaign Form Enter Camp Id: {}", new Object[]{bean.getMcCampId()});
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setCampaign(dao.getCampaignByCampId(bean.getMcCampId()));
        LOGGER.info("OpenCampaign Form Exit");
        return SUCCESS;
    }

    public String loadFormData() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        getModel().setAaData(dao.loadCampaignFormList(bean.getMcCampId()));
        return "data";
    }

    public String addCampaignForms() throws Exception {
        AnoudDAO dao = new AnoudDAO(getUserInfo().getCompanyCode());
        getModel().setColorList(dao.getMAppCodes(FieldConstants.AppCodes.MK_FRM_COLOR));
        if (getModel().getCampForm() != null) {
            CampaignSetupDAO campDAO = new CampaignSetupDAO(getUserInfo().getCompanyCode());
            getModel().setCampForm(campDAO.loadCampaignForm(getModel().getCampForm()));
        }
        // getModel().setOper(getModel().getOper());
        return SUCCESS;
    }

    public String saveCampaignFormData() {
        LOGGER.debug("Save Campaign Form Data - Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            getModel().getCampForm().setMcfCampId(bean.getMcCampId());
            dao.saveCampaignFormData(getModel().getCampForm(), userInfo.getUserId(), getModel().getOper());
            bean.setMessage("Data Saved Successfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.debug("Save Campaign Form Data - Exit");
        return SUCCESS;
    }

    public String loadFormFields() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        getModel().setAaData(dao.loadCampaignFormFieldList(getModel().getFormField().getMcffFormId()));
        return "data";
    }

    public String editFormFields() {

        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setDataTypes(dao.getDataTypes());
        bean.setFieldTypes(dao.getFieldTypes());
        getModel().setFormField(dao.loadCampaignFormField(getModel().getFormField()));
        //   LOGGER.info("in edit field name is**"+getModel().getFormField().getMcffFieldName());
        return SUCCESS;
    }

    public String deleteFormFields() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            dao.deleteCampaignFormFields(getModel().getFormField());
            bean.setMessage("Campaign Form Config Deleted Successfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.info("Delete Campaign Form Config  -Exit");
        return "del_form_config";
    }

    public String saveCampaignFormField() {
        LOGGER.debug("Save Campaign Form Field - Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            dao.saveCampaignFormField(getModel().getFormField(), userInfo.getUserId());
            bean.setMessage("Data Saved Successfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.debug("Save Campaign Form Field - Exit");
        return SUCCESS;
    }

    public String viewConfig() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        getModel().setCampForm(dao.loadCampaignForm(getModel().getCampForm()));
        getModel().setAaData(dao.viewFormFieldList(getModel().getCampForm().getMcfFormId()));
        return SUCCESS;
    }

    public String loadDataParamFilter() throws Exception {
        LOGGER.debug("Load Data Param Filter - Entry" + bean.getMcCampId());
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setCampaign(dao.getCampaignByCampId(bean.getMcCampId()));
        bean.setCondTypes(dao.getCampaignOperators());
        if (bean.getCampaign().getMcDataSourceType() != null && "A".equals(bean.getCampaign().getMcDataSourceType())) {
            String reportName = dao.getCampaignView(bean.getMcCampId());
            if (bean.getCampaign().getMcSelectedCols() != null) {
                bean.setRepColumns(dao.getCampaignStoredColumns(bean.getMcCampId()));
                bean.setDataParam(dao.getCampaignStoredFilters(bean.getMcCampId()));
            } else {
                bean.setRepColumns(new LinkedList<String>());
                bean.setDataParam(new LinkedList<MMktgCampaignDataParam>());
            }
            bean.setActualColumns(dao.loadReportColumnsForTab(reportName));
            KeyValue temp = null;
            for (String s : bean.getRepColumns()) {
                for (KeyValue kv : bean.getActualColumns()) {
                    if (s.equals(kv.getKey())) {
                        temp = kv;
                    }
                }
                if (temp != null) {
                    bean.getActualColumns().remove(temp);
                }
            }
            bean.setFilterColumns(dao.loadReportFilterColumns(reportName));
        } else if (bean.getCampaign().getMcSelectedCols() != null) {
            bean.setRepColumns(dao.getCampaignStoredColumns(bean.getMcCampId()));
            bean.setActualColumns(dao.loadCampaignUploadColumns(bean.getMcCampId()));
            bean.setFilterColumns(dao.loadCampaignUploadColumns(bean.getMcCampId()));
            bean.setDataParam(dao.getCampaignStoredFilters(bean.getMcCampId()));
        } else {
            bean.setDataParam(new LinkedList<MMktgCampaignDataParam>());
            bean.setFilterColumns(new LinkedList<KeyValue>());
            // bean.setRepColumns(dao.getCampaignStoredColumns(bean.getMcCampId()));
            // bean.setActualColumns(dao.loadCampaignUploadColumns(bean.getMcCampId()));
            // bean.setFilterColumns(dao.loadCampaignUploadColumns(bean.getMcCampId()));
        }
        LOGGER.debug("Load Data Param Filter - Exit");
        return INPUT;
    }

    public String saveDataParamFilter() throws Exception {
        LOGGER.debug("Save Data Param Filter - Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        String status;
        try {
            if (bean.getRepColumns() != null && bean.getRepColumns().size() > 0) {
                status = dao.updateSelectedColumns(bean.getRepColumns(), bean.getMcCampId(), getUserInfo().getUserId());
            }
            dao.insertMktgFilterParams(bean, bean.getMcCampId(), getUserInfo().getUserId());
            bean.setMessage("Data Saved Successfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.debug("Save Data Param Filter - Exit");
        return "data_map";
    }

    public String saveUploadCampaign() {
        LOGGER.debug("Save Data Param Filter - Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            String targetPath = ApplicationConstants.FILE_STORE_LOC(ApplicationConstants.APP_TYPE_RETAIL, getUserInfo().getCompanyCode()) + ApplicationConstants.CAMPAIGN_DS_PATH;
            String fileName = dao.getCampaignView(bean.getMcCampId());
            File fileToCreate = new File(targetPath, fileName);
            //dao.insertCampaignData(fileToCreate, bean.getMcCampId(), null);
            bean.setRowsCount(dao.getUploadedCampaignCount(bean.getMcCampId()));
            bean.setMessage("Data Saved Successfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.debug("Save Data Param Filter - Exit");
        return "data";
    }

    public String openDataMappings() {
        LOGGER.info("open Data Mappings - Enter mcCampId: {}", new Object[]{bean.getMcCampId()});
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setCampaign(dao.getCampaignByCampId(bean.getMcCampId()));
        LOGGER.info("open Data Mappings - Exit");
        return SUCCESS;
    }

    public String loadDataMappings() {
        LOGGER.info("Load Data Mappings - Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setDataMap(dao.loadCampaignDataMappings(bean.getMcCampId()));
        bean.setDataTypes(dao.getDataTypes());
        bean.setRepColumns(dao.getCampaignStoredColumns(bean.getMcCampId()));
        if (bean.getDataMap() != null && bean.getDataMap().size() > 0) {
            for (MMktgCampaignDataMap dataMaps : bean.getDataMap()) {
                dataMaps.setDataTypeList(bean.getDataTypes());
            }
        }
        LOGGER.info("Load Data Mappings - Exit");
        return "data";
    }

    public String updateDataMappings() {
        LOGGER.info("Update Data Mappings  -Enter");
        try {
            dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
            dao.updateDataMappings(bean, bean.getMcCampId(), getUserInfo().getUserId());
            bean.setMessage("Data Mappings Updated SUccessfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.info("Update Data Mappings  -Exit");
        return "data";
    }

    public String openCampaignFilter() {
        LOGGER.info("Open Campaign Filter Enter McCampId: {}", new Object[]{bean.getMcCampId()});
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setCampaign(dao.getCampaignByCampId(bean.getMcCampId()));
        LOGGER.info("Open Campaign Filter Exit");
        return SUCCESS;
    }

    public String loadCampaignFilter() {
        LOGGER.info("Load Campaign Filter  -Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.getMktgCampaignFilter(bean.getMcCampId()));
        LOGGER.info("Load Campaign Filter -Exit");
        return "data";
    }

    public String saveCampaignFilter() {
        LOGGER.info("Save Campaign Filter  -Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            if ("add".equals(getModel().getOper())) {
                int count = dao.saveCampaignFilter(bean.getCampaignFilter(), getUserInfo().getUserId());
            } else {
                dao.updateCampaignFilter(bean.getCampaignFilter(), getUserInfo().getUserId());
            }

            bean.setMessage("Data Filters Added Successfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.info("Save Campaign Filter - Exit");
        return "data";
    }

    public String openCampFilterParamForm() {
        LOGGER.info("Open Camp Filter Form  -Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setCampaign(dao.getCampaignByCampId(bean.getMcCampId()));
        if ("edit".equals(getModel().getOper())) {
            bean.setCampaignFilter(dao.getCampaignFilter(bean.getCampaignFilter()));
        }
        LOGGER.info("Open Camp Filter Form  -Exit");
        return "campFilter";
    }

    public String openCampFilterParam() throws Exception {
        LOGGER.info("Open Camp Filter Param  -Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setFilterColumns(dao.loadDataMapColumns(bean.getMcCampId()));
        bean.setCondTypes(dao.getCampaignOperators());
        if (bean.getCampaignFilter().getMcfFilterId() != 0) {
            bean.setCampaignFilterParm(dao.getmktgCampFilterParams(bean.getCampaignFilter().getMcfFilterId()));
        } else {
            bean.setDataParam(new LinkedList<MMktgCampaignDataParam>());
        }
        LOGGER.info("Open Camp Filter Param  -Exit");
        return SUCCESS;
    }

    public String saveCampFilterParam() {
        LOGGER.info("Save Camp Filter Param  -Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            dao.saveMktgFilterParams(bean, getUserInfo().getUserId());
            bean.setMessage("Filters Params Added Successfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.info("Save Camp Filter Param  -Exit");
        return "data";
    }

    public String deleteCampaignFilter() {
        LOGGER.info("Delete Campaign Filter  -Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            dao.deleteCampaignFilter(bean.getCampaignFilter().getMcfFilterId());
            bean.setMessage("Campaign Filters Deleted Successfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.info("Delete Campaign Filter  -Exit");
        return "del_filter";
    }

    public String deleteCampaignFilterParam() {
        LOGGER.info("Delete Campaign Filter Param -Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            dao.deleteCampaignFilterParam(bean.getCampaignFilter().getMcfFilterId());
            bean.setMessage("Campaign Filter Param  Deleted Successfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.info("Delete Campaign Filter Param -Exit");
        return "del_filter";
    }

    public String marketingCampaign() {
        return SUCCESS;
    }

    public String campaignSummary() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setCampaign(dao.getCampaignDetails(bean.getMcCampId()));
        return SUCCESS;
    }

    //TEMPLATE
    public String openCampaignTemplateData() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        if ("edit".equals(getModel().getOper())) {
            bean.setCampTemplate(dao.getCampaignTemplate(bean.getCampTemplate().getMctTemplateId(), bean.getMcCampId()));
        }
        return SUCCESS;
    }

    public String loadKeyElements() throws Exception {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadDataMapColumns(bean.getMcCampId()));
        MMktgCampaign camp = new MMktgCampaign();
        camp.setMcCampType(dao.getCampaignDataType(bean.getMcCampId()));
        getModel().setCampaign(camp);
        return SUCCESS;
    }

    public String loadTemplateData() throws Exception {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadTemplateList(bean.getMcCampId()));//getCampaign()
        return SUCCESS;
    }

    public String loadTemplateUrl() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadUrlList(bean.getCampTemplate().getMctTemplateId()));//getCampaign()
        return SUCCESS;
    }

    public String saveDataTemplate() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setMessage(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            if ("add".equals(getModel().getOper())) {
                bean.getCampTemplate().setMctCrUid(getUserInfo().getUserId());
                bean.getCampTemplate().setMctCampId(bean.getMcCampId());
                dao.saveTemplateData(bean.getCampTemplate(), getUserInfo().getCompanyCode());
            } else {
                bean.getCampTemplate().setMctUpdUid(getUserInfo().getUserId());
                bean.getCampTemplate().setMctCampId(bean.getMcCampId());
                dao.updateTemplateData(bean.getCampTemplate(), getUserInfo().getCompanyCode());
            }
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            bean.setMessage("Saved Successfully");
        } catch (Exception e) {
            bean.setMessage((e.getMessage() == null ? "Something went wrong" : e.getMessage()));
        }
        return SUCCESS;
    }

    private Map<String, String> loadSampleData(final Long campId) {
        LOGGER.info("Load sample data: campId: {}", new Object[]{campId});
        Map<String, String> result = new HashMap<>();
        List<KeyValue> dataMapList = dao.loadDataMapColumns(campId);
        dataMapList.forEach(o -> {
            result.put(o.getKey(), o.getInfo1());
        });
        return result;
    }

    public String previewTemplate() {
        dao = new CampaignSetupDAO(userInfo.getCompanyCode());
        Map<String, String> result = loadSampleData(bean.getMcCampId());
        KeyValue templateVal = prevTemplate(result);
        String status = templateVal.getInfo2();
        LOGGER.info("body  is**" + templateVal.getInfo2());
        if (status == null) {
            bean.setMessage("");
            bean.setMessageType("");
        } else {
            bean.setMessage(status);
            bean.setMessageType(templateVal.getInfo1());
        }
        return "preview";
    }

    public String sendEmailTemplate() {
        dao = new CampaignSetupDAO(userInfo.getCompanyCode());
        try {
            Map<String, String> result = loadSampleData(bean.getMcCampId());
            String senderId = result.get("MCD_CONTACT_ID");
            if (StringUtils.isNotBlank(senderId)) {
                KeyValue templateVal = prevTemplate(result);
                sendMail("", "", senderId, templateVal.getInfo1(), templateVal.getInfo2());
                bean.setMessage("Successfully sent to " + senderId);
            } else {
                bean.setMessage("Sample Contact Id not found");
            }
        } catch (Exception e) {
            bean.setMessage((e.getMessage() == null ? "Something went wrong" : e.getMessage()));
        }
        return "data";
    }

    public String openCampaignTemplate() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setCampaign(dao.getCampaignByCampId(bean.getMcCampId()));
        return SUCCESS;
    }

    //Campaign Transaction
    public String campaignTxn() {
        Calendar cal = Calendar.getInstance();
        bean.setMonth(cal.get(Calendar.MONTH)+1);
        bean.setYear(cal.get(Calendar.YEAR));
        getModel().setCampActionList(FieldConstants.CAMPAIGN_ACTION_LIST);
        return SUCCESS;
    }

    public String loadCampaignTxn() {
        LOGGER.info("Load Campaign Txn ==> Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadCampaignTxn(bean));
        LOGGER.info("Load Campaign Txn ==> Exit");
        return "data";
    }

    public String loadCampaignTxnData() {
        LOGGER.info("Load Campaign Txn Data ==> Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadCampaignTxnData(bean.getCampaignTxnData()));
        LOGGER.info("Load Campaign Txn Data ==> Exit");
        return "data";
    }

    public String loadCampaignTxnCount() {
        LOGGER.info("Load Campaign Txn Count ==> Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        //bean.setCampaignTxnData(dao.loadCampaignTxnCount(bean.getMcCampId(), bean.getCampaignTxn().getMctTxnId()));
        bean.setAaData(dao.loadCampaignPerformance(bean.getMcCampId(), bean.getCampaignTxn().getMctTxnId()));
        LOGGER.info("Load Campaign Txn Count ==> Exit");
        return "data";
    }

    public String loadTxnConvertedData() {
        LOGGER.info("Load Campaign Txn Converted Data ==> Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadTxnConvertedData(bean.getMcCampId(), bean.getCampaignTxn().getMctTxnId()));
        LOGGER.info("Load Campaign Txn Converted Data ==> Exit");
        return "data";
    }

    public String updateCampaignTxn() throws Exception {
        LOGGER.info("Update Campaign Txn ==> Enter Txn Id: {}", new Object[]{bean.getCampaignTxn().getMctTxnId()});
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            dao.updateCampaignTxn(bean.getMcCampId(), bean.getCampaignTxn().getMctTxnId());
            bean.setMessage("Campaign Stopped Successfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.info("Update Campaign Txn ==> Enter");
        return "data_txn";
    }
// Journey path

    public String loadJourneyPathList() {
        LOGGER.info("Load Journey Path List - Enter mcCampId: {}", new Object[]{bean.getMcCampId()});
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadPathList(bean.getMcCampId()));//getCampaign()
        LOGGER.info("Load Journey Path List - Exit");
        return SUCCESS;
    }

    public String openCampaignJourney() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setCampaign(dao.getCampaignByCampId(bean.getMcCampId()));
        bean.setFilterList(dao.loadCampaignFilter(bean.getMcCampId()));
        bean.setActionList(dao.getActionTypes());
        bean.setTempList(dao.getTemplate(bean.getMcCampId()));
        //bean.setTempUrlList(dao.getTemplateUrl(bean.getCampTemplate().getMctTemplateId(),bean.getMcCampId()));
        bean.setWaitFreqList(dao.getWaitFreq());
        return SUCCESS;
    }

    public String openCampaignJouneyForm() {
        LOGGER.info("Open Camp Filter Form  -Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setFilterList(dao.loadCampaignFilter(bean.getMcCampId()));

        if ("edit".equals(getModel().getOper())) {
            bean.setCampPath(dao.getCampaignPath(bean.getCampPath()));
        }
        LOGGER.info("Open Camp Jouney Form  -Exit");
        return "campJourney";
    }

    public String openCampaignJouneyPathFlowForm() {
        LOGGER.info("Open Camp Journey Flow Form  -Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        if ("edit".equals(getModel().getOper())) {
            bean.setCampPathFlow(dao.getCampaignPathFlow(bean.getCampPathFlow()));
        }
        LOGGER.info("Open Camp Jouney Flow Form  -Exit");
        return "campJourneyFlow";
    }

    public String saveJourneyPathData() {
        LOGGER.info("Save Campaign Journey  -Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setMessage(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            if ("add".equals(getModel().getOper())) {
                dao.saveJourneyPath(bean.getCampPath(), bean.getMcCampId(), getUserInfo().getUserId());
            } else if ("edit".equals(getModel().getOper())) {
                dao.updateJourneyPath(bean.getCampPath(), bean.getMcCampId(), getUserInfo().getUserId());
            } else {
                dao.deleteJourneyPath(bean.getCampPath().getMcpPathId());
            }
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            bean.setMessage("Saved Successfully");
        } catch (Exception e) {
            bean.setMessage((e.getMessage() == null ? "Something went wrong" : e.getMessage()));
            LOGGER.info("Exception while saving Campaign Journey");
        }
        return "data";
    }

    //journey path flow
    public String loadJourneyPathFlowList() throws SQLException {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadPathFlowList(bean.getCampPathFlow().getMcpfPathId(), bean.getMcCampId()));//getCampaign()
        return SUCCESS;
    }

    public String saveJourneyPathFlowData() {
        LOGGER.info("Save Campaign Journey  -Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setMessage(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {

            if (null == bean.getCampPathFlow().getMcpfSplitYn()) {
                bean.getCampPathFlow().setMcpfSplitYn("0");
            }
            if ("add".equals(getModel().getOper())) {
                bean.getCampPathFlow().setMcpfCrUid(getUserInfo().getUserId());
                dao.saveJourneyPathFlow(bean.getCampPathFlow());
            } else if ("edit".equals(getModel().getOper())) {
                bean.getCampPathFlow().setMcpfUpdUid(getUserInfo().getUserId());
                dao.updateJourneyPathFlow(bean.getCampPathFlow());
            }
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            bean.setMessage("Saved Successfully");
        } catch (Exception e) {
            bean.setMessage((e.getMessage() == null ? "Something went wrong" : e.getMessage()));
            LOGGER.info("Exception while saving Campaign Journey");
        }
        return "path_data";
    }

    public String loadTemplateUrlList() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.getTemplateUrl(bean.getMcCampId()));
        return "data";
    }

    public String loadSenderAndReplyList() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadSenderAndReplyList(bean.getCampaign().getMcCampType()));
        return "data";
    }

    public String saveUploadExcelCampaign() throws Exception {
        LOGGER.info("save upload excel campaign - Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        File fileToCreate = null;
        try {
            if (null != getModel().getCampaign() && getModel().getCampaign().getExcelFile() != null) {
                String fileName = UUID.randomUUID().toString();
                String fileExt = FilenameUtils.getExtension(getModel().getCampaign().getExcelFileFileName());
                fileName += "." + fileExt;
                String targetPath = ApplicationConstants.FILE_STORE_LOC(ApplicationConstants.APP_TYPE_RETAIL, getUserInfo().getCompanyCode()) + ApplicationConstants.CAMPAIGN_DS_PATH;
                fileToCreate = new File(targetPath, fileName);
                FileUtils.copyFile(getModel().getCampaign().getExcelFile(), fileToCreate);
                getModel().getCampaign().setMcDataSourceView(fileName);
                Workbook workbook = WorkbookFactory.create(new FileInputStream(targetPath + fileName));
                Sheet sheet = workbook.getSheetAt(0);
                Row row = sheet.getRow(0);
                String columns = "";
                for (Cell cell : row) {
                    columns += cell.toString() + "~";
                }
                getModel().getCampaign().setMcSelectedCols(columns);
                dao.updateCampaignColumns(getModel().getCampaign(), bean.getMcCampId(), getUserInfo().getUserId());
                String campType = dao.getCampaignDataType(bean.getMcCampId());
                String regex = ApplicationConstants.getRegex(getUserInfo().getCompanyCode());
                String result = dao.insertCampaignData(fileToCreate, bean.getMcCampId(), campType, regex);
                bean.setRowsCount(dao.getUploadedCampaignCount(bean.getMcCampId()));
                dao.UpdateDataMappings(bean.getMcCampId(), columns);
                bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                bean.setMessage(result);
            } else {
                bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
                bean.setMessage("please Upload a file");
            }

        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        return "data";
    }

    public String loadTxnClickedData() {
        LOGGER.info("Load Txn Clicked Data - Enter McCampId: {}", new Object[]{bean.getMcCampId()});
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadTxnClickedData(bean.getMcCampId()));
        LOGGER.info("Load txn Clicked Data - Exit");
        return "data";
    }

    public String openCampaignPathFlow() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setMessage(ApplicationConstants.MESSAGE_TYPE_ERROR);
        bean.setFilterList(dao.loadCampaignFilter(bean.getMcCampId()));
        bean.setActionList(dao.getActionTypes());
        bean.setTempList(dao.getTemplate(bean.getMcCampId()));
        bean.setWaitFreqList(dao.getWaitFreq());
        bean.setCampaignUrlList(dao.getTemplateUrl(bean.getMcCampId()));
        if ("edit".equals(bean.getOper()) || "view".equals(bean.getOper())) {
            bean.setCampPathFlow(dao.getCampaignPathFlow(bean.getCampPathFlow()));
            bean.setTempUrlList(dao.getTemplateUrl(bean.getCampPathFlow().getMcpfTemplateId()));
        } else {
            bean.setTempUrlList(new ArrayList<>());
        }
        bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        return SUCCESS;
    }

    public String deleteCampaignPathFlow() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            dao.deleteCampaignPathFlow(bean.getCampPathFlow());
            bean.setMessage("Campaign path Flow Deleted Successfully");
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
        LOGGER.info("Delete CampaignPath Flow  -Exit");
        return "del_path_flow";
    }

    public String startABCampaign() {
        LOGGER.info("in startABCampaign**");
        bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            if (getModel().getCampaign().getMcAbDataCount() == null) {
                getModel().setMessage("Please enter count");
            } else if (getModel().getCampaign().getMcAbEndDate() == null) {
                getModel().setMessage("Please enter end date");
            }
            if (getModel().getMessage() == null) {
                dao.updateCampaign(getModel().getCampaign());
                bean.setCampaign(dao.getCampaignByCampId(getModel().getCampaign().getMcCampId()));
                bean.setMcCampId(bean.getCampaign().getMcCampId());
                updateCampaignStatus();
                LOGGER.info("bean.getCampaign() is**" + bean.getCampaign());
                dao.setDataForABTesting(bean.getCampaign(), bean.getMcCampId());
            }
        } catch (Exception e) {
            bean.setMessage((e.getMessage() == null ? "Something went wrong" : e.getMessage()));
        }
        return "data";
    }

    public String startCampaign() {
        LOGGER.info("start Campaign - Enter McCampId: {}", new Object[]{bean.getMcCampId()});
        bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            java.util.Date mcDate = bean.getCampaign().getMcDelayStartDate();
            bean.setCampaign(dao.getCampaignByCampId(bean.getMcCampId()));
            bean.getCampaign().setMcDelayStartDate(mcDate);
            updateCampaignStatus();
        } catch (Exception e) {
            bean.setMessage((e.getMessage() == null ? "Something went wrong" : e.getMessage()));
            LOGGER.info("Exception while start Campaign");
        }
        LOGGER.info("start Campaign - Exit");
        return "data";
    }

    public String stopCampaign() {
        LOGGER.info("stop Campaign -Enter mcCampId: {}", new Object[]{bean.getMcCampId()});
        bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            dao.updateCampaignStatus(bean, "S", getUserInfo().getUserId());
            dao.updateCampaignTxnStatus(bean.getMcCampId());
            bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            bean.setMessage((e.getMessage() == null ? "Something went wrong" : e.getMessage()));
            LOGGER.info("Exception while STOP CAMPAIGN");
        }
        return "data";
    }

    //Campaign Dashboard
    public String campaignDashboard() {
        LOGGER.info("Campaign Dashboard - Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        KeyValue kv;
        dateRangeList = new LinkedList<>();
        for (DateRange d : DateRange.values()) {
            kv = new KeyValue(d.name(), d.getDesc());
            dateRangeList.add(kv);
        }
        dateRange = DateRange.TODAY;
        bean.setCampaignList(dao.loadCampaignList());
        return SUCCESS;
    }

    public String loadActiveCampaignChart() {
        LOGGER.info("Load Active Campaign Chart - Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadActiveCampaignChart());
        LOGGER.info("Load Active Campaign Chart - Exit");
        return "data";
    }

    public String loadCampaignStatusChart() {
        LOGGER.info("Load Campaign Status Charts - Enter DateRange: {}", new Object[]{getDateRange()});
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadCampaignStatusChart(getDateRange()));
        LOGGER.info("Load Campaign Status Charts - Exit ");
        return "data";
    }

    public String loadCampaignTypeChart() {
        LOGGER.info("Load Campaign Type Charts - Enter DateRange: {}", new Object[]{getDateRange()});
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadCampaignTypeChart(getDateRange()));
        LOGGER.info("Load Campaign Type Charts - Exit ");
        return "data";
    }

    public String loadCampaignGraphData() {
        LOGGER.info("Load Campaign Graph Data - Enter campaignId: {}", new Object[]{bean.getMcCampId()});
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadCampaignGraphData(bean.getMcCampId()));
        LOGGER.info("Load Campaign Graph Charts - Exit ");
        return "data";
    }

    public String loadCampaignDataCount() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadCampaignDataCount(bean.getMcCampId()));
        return "data";
    }

    public String loadCampaignBouncedCount() {
        LOGGER.info("Load Campaign Bounced Count - Enter campaignId: {}", new Object[]{bean.getMcCampId()});
        //commented for corporate
        /*if(null == bean.getMcCampId()) {
            dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
            bean.setAaData(dao.loadCampaignBouncedCount(bean.getMcCampId()));
        }
        if(null == bean.getAaData()) {
            bean.setAaData(new ArrayList<>());
        }*/

        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        bean.setAaData(dao.loadCampaignBouncedCount(bean.getMcCampId()));
        LOGGER.info("Load Campaign Bounced Count - Exit ");
        return "data";
    }

    public String loadTxnDashboardPage() {
        return "data";
    }

    public String campaignReports() {
        LOGGER.info("Campaign Reports - Enter");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        service = new DynamicReportsService(getUserInfo().getCompanyCode());
        bean.setReportsViewList(service.loadReportViews("mktg"));
        getUserInfo().setActiveMenu(TypeConstants.Menu.REPORTS);
        bean.setUserReportsList(dao.loadUserReportsList(getUserInfo().getUserId()));
        LOGGER.info("Campaign Reports - Exit");
        return SUCCESS;
    }

    public String checkTemplate() {
        bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        try {
            int data = dao.checkTemplateForPaths(bean.getMcCampId());
            if (data > 0) {
                bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                AnoudDAO anoudDao = new AnoudDAO(getUserInfo().getCompanyCode());
                bean.setActionTypeList(anoudDao.getMAppParameter(FieldConstants.AppParameter.ACTION_TYPE));
                // LOGGER.info("ActionType list is**" + getModel().getActionTypeList().size());
            } else {
                bean.setMessage("Two email templates are not defined for first flow");
            }

        } catch (Exception e) {
            bean.setMessage((e.getMessage() == null ? "Something went wrong" : e.getMessage()));
            LOGGER.info("Exception while checking template");
        }
        return "data";
    }

    public void updateCampaignStatus() {
        List<MMktgCampaignPath> pl = dao.loadPathList(bean.getMcCampId());//getCampaign()
        if (null == pl || pl.isEmpty()) {
            bean.setMessage("Path not defined");
        } else {
            List<MMktgCampaignPathFlow> pfl = dao.loadPathFlowList(pl.get(0).getMcpPathId(), null);
            if (null == pfl || pfl.isEmpty()) {
                bean.setMessage("Path flow not defined");
            } else {
                dao.updateCampaignStatus(bean, "R", getUserInfo().getUserId());
                bean.setMessage("Data Updated Successfully");
                bean.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            }
        }

    }

    //method to preview template & replacing with actual values
    public KeyValue prevTemplate(Map<String, String> dataMapList) {
        KeyValue tempVal = new KeyValue();
        final List<MailBodyDataPropertyMap> PROPERTY_LIST = new LinkedList<MailBodyDataPropertyMap>() {
            {
                add(new MailBodyDataPropertyMap(MailBodyBuilder.PROPERTY_TOKEN, "mctdKey"));
                add(new MailBodyDataPropertyMap(MailBodyBuilder.PROPERTY_CAMPAIGN, "mctdCampCode"));
                add(new MailBodyDataPropertyMap("MCD_CONTACT_ID", "MCD_CONTACT_ID"));
                add(new MailBodyDataPropertyMap("MCD_CONTACT_REF", "MCD_CONTACT_REF"));
                add(new MailBodyDataPropertyMap("MCD_DATA_1", "MCD_DATA_1"));
                add(new MailBodyDataPropertyMap("MCD_DATA_2", "MCD_DATA_2"));
                add(new MailBodyDataPropertyMap("MCD_DATA_3", "MCD_DATA_3"));
                add(new MailBodyDataPropertyMap("MCD_DATA_4", "MCD_DATA_4"));
                add(new MailBodyDataPropertyMap("MCD_DATA_5", "MCD_DATA_5"));
                add(new MailBodyDataPropertyMap("MCD_DATA_6", "MCD_DATA_6"));
                add(new MailBodyDataPropertyMap("MCD_DATA_7", "MCD_DATA_7"));
                add(new MailBodyDataPropertyMap("MCD_DATA_8", "MCD_DATA_8"));
                add(new MailBodyDataPropertyMap("MCD_DATA_9", "MCD_DATA_9"));
                add(new MailBodyDataPropertyMap("MCD_DATA_10", "MCD_DATA_10"));
                add(new MailBodyDataPropertyMap("MCD_DATA_11", "MCD_DATA_11"));
                add(new MailBodyDataPropertyMap("MCD_DATA_12", "MCD_DATA_12"));
                add(new MailBodyDataPropertyMap("MCD_DATA_13", "MCD_DATA_13"));
                add(new MailBodyDataPropertyMap("MCD_DATA_14", "MCD_DATA_14"));
                add(new MailBodyDataPropertyMap("MCD_DATA_15", "MCD_DATA_15"));
                add(new MailBodyDataPropertyMap("MCD_DATA_16", "MCD_DATA_16"));
                add(new MailBodyDataPropertyMap("MCD_DATA_17", "MCD_DATA_17"));
                add(new MailBodyDataPropertyMap("MCD_DATA_18", "MCD_DATA_18"));
                add(new MailBodyDataPropertyMap("MCD_DATA_19", "MCD_DATA_19"));
                add(new MailBodyDataPropertyMap("MCD_DATA_20", "MCD_DATA_20"));
            }
        };
        KeyValue keyValue = dao.previewTemplate(bean.getCampTemplate().getMctTemplateId());
        MailBodyBuilder mbb = new MailBodyBuilder(dataMapList);
        mbb.setPropertyDelimit(ApplicationConstants.CAMPAIGN_PROPERTY_DELIMIT);
        mbb.setBody(new StringBuilder(keyValue.getInfo2()));
        mbb.setSubject(keyValue.getInfo1());
        mbb.buildBody(PROPERTY_LIST);
        tempVal.setInfo1(mbb.getSubject());
        tempVal.setInfo2(mbb.getBody().toString());
        return tempVal;
    }

    public void sendMail(final String from, final String password, String to, String sub, String msg) {
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

    public String downloadCampaignData() {
        LOGGER.debug("***downloadCampaignData() -- Enter ***");
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        List<MMktgCampaignTxnData> txnData = dao.downloadCampaignTxnData(bean.getCampaignTxnData());
        Map<String, String> headerRow = new LinkedHashMap<>();
        headerRow.put("1_STR_L_", "Contact Id");
        headerRow.put("2_STR_L_", "Contact Ref");
        headerRow.put("3_STR_L_", "Sent Yn");
        headerRow.put("4_STR_L_", "Bounced Yn");
        headerRow.put("5_STR_L_", "Replied Yn");
        headerRow.put("6_STR_L_", "Clicked Yn");
        headerRow.put("7_STR_L_", "Converted Yn");
        headerRow.put("8_STR_L_", "Data 1");
        headerRow.put("9_STR_L_", "Data 2");
        headerRow.put("10_STR_L_", "Data 3");
        headerRow.put("11_STR_L_", "Data 4");
        headerRow.put("12_STR_L_", "Data 5");
        headerRow.put("13_STR_L_", "Data 6");
        headerRow.put("14_STR_L_", "Data 7");
        headerRow.put("15_STR_L_", "Data 8");
        headerRow.put("16_STR_L_", "Data 9");
        headerRow.put("17_STR_L_", "Data 10");
        headerRow.put("18_STR_L_", "Data 11");
        headerRow.put("19_STR_L_", "Data 12");
        headerRow.put("20_STR_L_", "Data 13");
        headerRow.put("21_STR_L_", "Data 14");
        headerRow.put("21_STR_L_", "Data 15");
        headerRow.put("23_STR_L_", "Data 16");
        headerRow.put("24_STR_L_", "Data 17");
        headerRow.put("25_STR_L_", "Data 18");
        headerRow.put("26_STR_L_", "Data 19");
        headerRow.put("26_STR_L_", "Data 20");
        List<Map<String, String>> dataList = new ArrayList<>();
        if (txnData != null && txnData.size() > 0) {
            for (MMktgCampaignTxnData info : txnData) {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("1_STR_L_", info.getMctdContactId() == null ? "" : info.getMctdContactId());
                map.put("2_STR_L_", info.getMctdContactRef() == null ? "" : info.getMctdContactRef());
                map.put("3_STR_L_", info.getMctdSentYn() == null ? "" : info.getMctdSentYn());
                map.put("4_STR_L_", info.getMctdBouncedYn() == null ? "" : info.getMctdBouncedYn());
                map.put("5_STR_L_", info.getMctdRepliedYn() == null ? "" : info.getMctdRepliedYn());
                map.put("6_STR_L_", info.getMctdClickedYn() == null ? "" : info.getMctdClickedYn());
                map.put("7_STR_L_", info.getMctdConvertedYn() == null ? "" : info.getMctdConvertedYn());
                map.put("8_STR_L_", info.getMctdData1() == null ? "" : info.getMctdData1());
                map.put("9_STR_L_", info.getMctdData2() == null ? "" : info.getMctdData2());
                map.put("10_STR_L_", info.getMctdData3() == null ? "" : info.getMctdData3());
                map.put("11_STR_L_", info.getMctdData4() == null ? "" : info.getMctdData4());
                map.put("12_STR_L_", info.getMctdData5() == null ? "" : info.getMctdData5());
                map.put("13_STR_L_", info.getMctdData6() == null ? "" : info.getMctdData6());
                map.put("14_STR_L_", info.getMctdData7() == null ? "" : info.getMctdData7());
                map.put("15_STR_L_", info.getMctdData8() == null ? "" : info.getMctdData8());
                map.put("16_STR_L_", info.getMctdData9() == null ? "" : info.getMctdData9());
                map.put("17_STR_L_", info.getMctdData10() == null ? "" : info.getMctdData10());
                map.put("18_STR_L_", info.getMctdData11() == null ? "" : info.getMctdData11());
                map.put("19_STR_L_", info.getMctdData12() == null ? "" : info.getMctdData12());
                map.put("20_STR_L_", info.getMctdData13() == null ? "" : info.getMctdData13());
                map.put("21_STR_L_", info.getMctdData14() == null ? "" : info.getMctdData14());
                map.put("22_STR_L_", info.getMctdData15() == null ? "" : info.getMctdData15());
                map.put("23_STR_L_", info.getMctdData16() == null ? "" : info.getMctdData16());
                map.put("24_STR_L_", info.getMctdData17() == null ? "" : info.getMctdData17());
                map.put("25_STR_L_", info.getMctdData18() == null ? "" : info.getMctdData18());
                map.put("26_STR_L_", info.getMctdData19() == null ? "" : info.getMctdData19());
                map.put("27_STR_L_", info.getMctdData20() == null ? "" : info.getMctdData20());

                dataList.add(map);
            }
        }
        try {
            file = generateExcel(headerRow, dataList, "Campaign Data", "Campaign Transaction Details");
            fileInputStream = new FileInputStream(file);
            filePath = file.getName();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        LOGGER.debug("*** downloadProviderExcel() -- Exit ***");
        return "excel";
    }

    private File generateExcel(Map<String, String> headerRow, List<Map<String, String>> dataBeanList, String sheetName, String fileName) throws IOException {
        LOGGER.debug("***generateExcel() -- Enter ***");
        File file = new File(fileName + ".xlsx");
        FileOutputStream fos = null;
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);
            DataFormat format = workbook.createDataFormat();

            Font font = workbook.createFont();
            font.setColor(IndexedColors.BLACK.index);
            font.setFontHeightInPoints((short) 12);
            //font.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle style = workbook.createCellStyle();
            style.setFont(font);
            style.setAlignment(CellStyle.ALIGN_CENTER);

            CellStyle numberStyle = workbook.createCellStyle();
            numberStyle.setFont(font);
            numberStyle.setAlignment(CellStyle.ALIGN_RIGHT);
            numberStyle.setDataFormat(format.getFormat("0.00"));

            int rowIndex = 0;
            sheet.createRow(rowIndex++); //Empty row

            Row headRow = sheet.createRow(rowIndex++);
            Integer colIndex = 0;

            //Set Row Head in Excel.
            for (Map.Entry<String, String> entry : headerRow.entrySet()) {
                String value = entry.getValue();
                Cell cell = headRow.createCell(colIndex);
                cell.setCellValue(value);
                cell.setCellStyle(style);
                colIndex++;
                // do stuff
            }

            //Set cell data
            colIndex = 0;
            for (Map<String, String> map : dataBeanList) {
                Row row = sheet.createRow(rowIndex++);
                colIndex = 0;
                for (Map.Entry<String, String> entry : headerRow.entrySet()) {
                    String key = entry.getKey();
                    Cell cell = row.createCell(colIndex);
                    cell.setCellValue(map.get(key));
                    if (key.contains("_NUM_")) {
                        if (map.get(key) != null && !map.get(key).equals("")) {
                            cell.setCellValue(new BigDecimal(map.get(key)).doubleValue());
                        } else {
                            cell.setCellValue(new BigDecimal(0).doubleValue());
                        }
                        cell.setCellStyle(numberStyle);
                    }
                    colIndex++;
                }
            }
            fos = new FileOutputStream(file.getAbsoluteFile());
            workbook.write(fos);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (workbook != null) {
                workbook.close();
            }
        }
        LOGGER.debug("*** generateExcel() -- Exit ***");
        return file;
    }

    public String campaignTxnDash() {
        LOGGER.info("campaignTxnDash - Enter");
        return SUCCESS;
    }

    public String campaignFlowChart() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        List<MMktgCampaignPathFlow> flowList = dao.getCampaignFlowData(bean.getMcCampId());
        Map<Long, List<MMktgCampaignPathFlow>> hmap = flowList.stream().collect(Collectors.groupingBy(MMktgCampaignPathFlow::getMcpfPathId));
        request.setAttribute("FLOW_CHART_DATA", hmap);
        return SUCCESS;
    }

    public String openCampaignCopyPage(){
        return SUCCESS;
    }


    public String loadCampaignTypeList() {
        dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
        getModel().setAaData(dao.loadCampaignTypeList(getModel().getFlex1()));
        return SUCCESS;
    }

    public String proceedCopyCampaign() {
        try {
            dao = new CampaignSetupDAO(getUserInfo().getCompanyCode());
            if (getModel().getCampaign().getMcCampName() == null || "".equalsIgnoreCase(getModel().getCampaign().getMcCampName().trim())) {
                getModel().setMessage("Please enter campaign name");
            } else if (getModel().getCampaign().getMcCampCode() == null || "".equalsIgnoreCase(getModel().getCampaign().getMcCampCode().trim())) {
                getModel().setMessage("Please enter campaign code");
            } else if(getModel().getCampaign().getMcCampId() == null){
                getModel().setMessage("Please select campaign type");
            }
            if (getModel().getMessage() == null) {
                dao.copyCampaign(getModel().getCampaign(), userInfo.getUserId());
                getModel().setMcCampId(getModel().getCampaign().getMcCampId());
            }
        } catch (Exception e) {
            bean.setMessage((e.getMessage() == null ? "Something went wrong" : e.getMessage()));
        }
        return SUCCESS;

    }


    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
        this.userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
    }

    @Override
    public CampaignVO getModel() {
        return bean;
    }

    public List<KeyValue> getDateRangeList() {
        return dateRangeList;
    }

    public void setDateRangeList(List<KeyValue> dateRangeList) {
        this.dateRangeList = dateRangeList;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
