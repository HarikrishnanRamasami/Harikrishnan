/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.mc.vo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import qa.com.qic.anoud.reports.ReportsBeanVO;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.mc.model.MMktgCampaign;
import qa.com.qic.mc.model.MMktgCampaignDataMap;
import qa.com.qic.mc.model.MMktgCampaignDataParam;
import qa.com.qic.mc.model.MMktgCampaignFilter;
import qa.com.qic.mc.model.MMktgCampaignFilterParm;
import qa.com.qic.mc.model.MMktgCampaignFormField;
import qa.com.qic.mc.model.MMktgCampaignForms;
import qa.com.qic.mc.model.MMktgCampaignPath;
import qa.com.qic.mc.model.MMktgCampaignPathFlow;
import qa.com.qic.mc.model.MMktgCampaignTemplate;
import qa.com.qic.mc.model.MMktgCampaignTxn;
import qa.com.qic.mc.model.MMktgCampaignTxnData;
import qa.com.qic.mc.model.TMktgCampaignTxnForm;

/**
 *
 * @author sutharsan.g
 */
public class CampaignVO {

    private transient String message;
    private transient String messageType;

    private String oper;

    private transient List<? extends Object> aaData;

    private List<KeyValue> campTypeList;
    private List<KeyValue> dataSourceList;
    private List<KeyValue> scheduleModeList;
    private List<KeyValue> campStatusList;
    private MMktgCampaign campaign;
    private List<KeyValue> reportsViewList;
    private List<MMktgCampaignDataParam> dataParam;
    private List<String> repColumns;
    private List<KeyValue> filterColumns;
    private List<KeyValue> actualColumns;
    private List<String> repFilters;
    private Long mcCampId;
    private Long campaignId;
    private String campaignTab;
    private Map<String, String> condTypes;
    private Map<String, String> dtFilterTypes;
    private List<MMktgCampaignDataMap> dataMap;
    private MMktgCampaignFilter campaignFilter;
    private MMktgCampaignFilterParm campaignFilterParam;
    private List<MMktgCampaignFilterParm> campaignFilterParm;
    private List<KeyValue> dataTypes;
    private List<MMktgCampaignDataMap> dataMapList = null;
    private List<MMktgCampaignFormField> formFieldList = null;
    private MMktgCampaignTemplate campTemplate;
    private MMktgCampaignForms campForm;
    private MMktgCampaignFormField formField;
    private List<KeyValue> fieldTypes;
    private TMktgCampaignTxnForm txnForm;
    private MMktgCampaignTxn campaignTxn;
    private MMktgCampaignTxnData campaignTxnData;
    private List<MMktgCampaignTxnData> dataTxnList;
    private int rowsCount;
    private List<KeyValue> filterList;
    private List<KeyValue> actionList;
    private List<KeyValue> waitFreqList;
    private List<KeyValue> tempList;
    private List<KeyValue> tempUrlList;
    private List<KeyValue> startTimeList;
    private List<KeyValue> senderAndReplyList;
    private List<KeyValue> campaignUrlList;
     private List<KeyValue> actionTypeList;

    private MMktgCampaignPath campPath;
    private MMktgCampaignPathFlow campPathFlow;
    private MMktgCampaignDataParam campaignDataParam;

    private List<KeyValue> colorList;
    private List<KeyValue> campActionList;
    private List<KeyValue> campaignList;
    private List<ReportsBeanVO> userReportsList;
    private List<KeyValue> campaignDetList;
    public String flex1;
    public String flex2;
    public Integer month;
    public Integer year;

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

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public List<? extends Object> getAaData() {
        return aaData;
    }

    public void setAaData(List<? extends Object> aaData) {
        this.aaData = aaData;
    }

    public List<KeyValue> getCampTypeList() {
        return campTypeList;
    }

    public void setCampTypeList(List<KeyValue> campTypeList) {
        this.campTypeList = campTypeList;
    }

    public List<KeyValue> getDataSourceList() {
        return dataSourceList;
    }

    public void setDataSourceList(List<KeyValue> dataSourceList) {
        this.dataSourceList = dataSourceList;
    }

    public List<KeyValue> getScheduleModeList() {
        return scheduleModeList;
    }

    public void setScheduleModeList(List<KeyValue> scheduleModeList) {
        this.scheduleModeList = scheduleModeList;
    }

    public MMktgCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(MMktgCampaign campaign) {
        this.campaign = campaign;
    }

    public List<MMktgCampaignDataParam> getDataParam() {
        return dataParam;
    }

    public void setDataParam(List<MMktgCampaignDataParam> dataParam) {
        this.dataParam = dataParam;
    }

    public List<String> getRepColumns() {
        return repColumns;
    }

    public void setRepColumns(List<String> repColumns) {
        this.repColumns = repColumns;
    }

    public List<KeyValue> getFilterColumns() {
        return filterColumns;

    }

    public void setFilterColumns(List<KeyValue> filterColumns) {
        this.filterColumns = filterColumns;
    }

    public List<KeyValue> getActualColumns() {
        return actualColumns;
    }

    public void setActualColumns(List<KeyValue> actualColumns) {
        this.actualColumns = actualColumns;
    }

    public List<String> getRepFilters() {
        return repFilters;
    }

    public void setRepFilters(List<String> repFilters) {
        this.repFilters = repFilters;
    }

    public Long getMcCampId() {
        return mcCampId;
    }

    public void setMcCampId(Long mcCampId) {
        this.mcCampId = mcCampId;
    }

    public Map<String, String> getCondTypes() {
        return condTypes;
    }

    public Map<String, String> getDtFilterTypes() {
        dtFilterTypes = new LinkedHashMap<>();
        dtFilterTypes.put("td", "Today");
        dtFilterTypes.put("yd", "Yesterday");
        dtFilterTypes.put("tw", "This Week");
        dtFilterTypes.put("lw", "Last Week");
        dtFilterTypes.put("tm", "This Month");
        dtFilterTypes.put("lm", "Last Month");
        dtFilterTypes.put("tq", "This Quarter");
        dtFilterTypes.put("lq", "Last Quarter");
        dtFilterTypes.put("ty", "This Year");
        dtFilterTypes.put("ly", "Last Year");
        return dtFilterTypes;
    }

    private static final Map<Integer, String> monthRangeList = new HashMap<Integer, String>() {
        {
            put(0, "ALL");
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

    public static Map<Integer, String> getMonthRangeList() {
        return monthRangeList;
    }

    public void setCondTypes(Map<String, String> condTypes) {
        this.condTypes = condTypes;
    }

    public List<MMktgCampaignDataMap> getDataMap() {
        return dataMap;
    }

    public void setDataMap(List<MMktgCampaignDataMap> dataMap) {
        this.dataMap = dataMap;
    }

    public List<KeyValue> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(List<KeyValue> dataTypes) {
        this.dataTypes = dataTypes;
    }

    public MMktgCampaignFilter getCampaignFilter() {
        return campaignFilter;
    }

    public void setCampaignFilter(MMktgCampaignFilter campaignFilter) {
        this.campaignFilter = campaignFilter;
    }

    public List<MMktgCampaignFilterParm> getCampaignFilterParm() {
        return campaignFilterParm;
    }

    public void setCampaignFilterParm(List<MMktgCampaignFilterParm> campaignFilterParm) {
        this.campaignFilterParm = campaignFilterParm;
    }

    public MMktgCampaignFilterParm getCampaignFilterParam() {
        return campaignFilterParam;
    }

    public void setCampaignFilterParam(MMktgCampaignFilterParm campaignFilterParam) {
        this.campaignFilterParam = campaignFilterParam;
    }

    public List<MMktgCampaignDataMap> getDataMapList() {
        return dataMapList;
    }

    public void setDataMapList(List<MMktgCampaignDataMap> dataMapList) {
        this.dataMapList = dataMapList;
    }

    public MMktgCampaignTemplate getCampTemplate() {
        return campTemplate;
    }

    public void setCampTemplate(MMktgCampaignTemplate campTemplate) {
        this.campTemplate = campTemplate;
    }

    public List<KeyValue> getReportsViewList() {
        return reportsViewList;
    }

    public void setReportsViewList(List<KeyValue> reportsViewList) {
        this.reportsViewList = reportsViewList;
    }

    public List<KeyValue> getCampStatusList() {
        return campStatusList;
    }

    public void setCampStatusList(List<KeyValue> campStatusList) {
        this.campStatusList = campStatusList;
    }

    public MMktgCampaignForms getCampForm() {
        return campForm;
    }

    public void setCampForm(MMktgCampaignForms campForm) {
        this.campForm = campForm;
    }

    public MMktgCampaignFormField getFormField() {
        return formField;
    }

    public MMktgCampaignTxnData getCampaignTxnData() {
        return campaignTxnData;
    }

    public void setFormField(MMktgCampaignFormField formField) {
        this.formField = formField;
    }

    public List<KeyValue> getFieldTypes() {
        return fieldTypes;
    }

    public void setFieldTypes(List<KeyValue> fieldTypes) {
        this.fieldTypes = fieldTypes;
    }

    public TMktgCampaignTxnForm getTxnForm() {
        return txnForm;
    }

    public void setTxnForm(TMktgCampaignTxnForm txnForm) {
        this.txnForm = txnForm;
    }

    public void setCampaignTxnData(MMktgCampaignTxnData campaignTxnData) {
        this.campaignTxnData = campaignTxnData;
    }

    public List<MMktgCampaignTxnData> getDataTxnList() {
        return dataTxnList;
    }

    public void setDataTxnList(List<MMktgCampaignTxnData> dataTxnList) {
        this.dataTxnList = dataTxnList;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public List<KeyValue> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<KeyValue> filterList) {
        this.filterList = filterList;
    }

    public List<KeyValue> getActionList() {
        return actionList;
    }

    public void setActionList(List<KeyValue> actionList) {
        this.actionList = actionList;
    }

    public List<KeyValue> getWaitFreqList() {
        return waitFreqList;
    }

    public void setWaitFreqList(List<KeyValue> waitFreqList) {
        this.waitFreqList = waitFreqList;
    }

    public List<KeyValue> getTempList() {
        return tempList;
    }

    public void setTempList(List<KeyValue> tempList) {
        this.tempList = tempList;
    }

    public List<KeyValue> getTempUrlList() {
        return tempUrlList;
    }

    public void setTempUrlList(List<KeyValue> tempUrlList) {
        this.tempUrlList = tempUrlList;
    }

    public MMktgCampaignPath getCampPath() {
        return campPath;
    }

    public void setCampPath(MMktgCampaignPath campPath) {
        this.campPath = campPath;
    }

    public MMktgCampaignPathFlow getCampPathFlow() {
        return campPathFlow;
    }

    public void setCampPathFlow(MMktgCampaignPathFlow campPathFlow) {
        this.campPathFlow = campPathFlow;
    }

    public MMktgCampaignTxn getCampaignTxn() {
        return campaignTxn;
    }

    public void setCampaignTxn(MMktgCampaignTxn campaignTxn) {
        this.campaignTxn = campaignTxn;
    }

    public MMktgCampaignDataParam getCampaignDataParam() {
        return campaignDataParam;
    }

    public void setCampaignDataParam(MMktgCampaignDataParam campaignDataParam) {
        this.campaignDataParam = campaignDataParam;
    }

    public List<KeyValue> getColorList() {
        return colorList;
    }

    public void setColorList(List<KeyValue> colorList) {
        this.colorList = colorList;
    }

    public List<KeyValue> getStartTimeList() {
        return startTimeList;
    }

    public void setStartTimeList(List<KeyValue> startTimeList) {
        this.startTimeList = startTimeList;
    }

    public List<KeyValue> getSenderAndReplyList() {
        return senderAndReplyList;
    }

    public void setSenderAndReplyList(List<KeyValue> senderAndReplyList) {
        this.senderAndReplyList = senderAndReplyList;
    }

    public List<KeyValue> getCampActionList() {
        return campActionList;
    }

    public void setCampActionList(List<KeyValue> campActionList) {
        this.campActionList = campActionList;
    }

    public List<KeyValue> getCampaignUrlList() {
        return campaignUrlList;
    }

    public void setCampaignUrlList(List<KeyValue> campaignUrlList) {
        this.campaignUrlList = campaignUrlList;
    }

    public List<KeyValue> getActionTypeList() {
        return actionTypeList;
    }

    public void setActionTypeList(List<KeyValue> actionTypeList) {
        this.actionTypeList = actionTypeList;
    }

    public List<KeyValue> getCampaignList() {
        return campaignList;
    }

    public void setCampaignList(List<KeyValue> campaignList) {
        this.campaignList = campaignList;
    }

    public List<ReportsBeanVO> getUserReportsList() {
        return userReportsList;
    }

    public void setUserReportsList(List<ReportsBeanVO> userReportsList) {
        this.userReportsList = userReportsList;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignTab() {
        return campaignTab;
    }

    public void setCampaignTab(String campaignTab) {
        this.campaignTab = campaignTab;
    }

    public List<MMktgCampaignFormField> getFormFieldList() {
        return formFieldList;
    }

    public void setFormFieldList(List<MMktgCampaignFormField> formFieldList) {
        this.formFieldList = formFieldList;
    }

    public List<KeyValue> getCampaignDetList() {
        return campaignDetList;
    }

    public void setCampaignDetList(List<KeyValue> campaignDetList) {
        this.campaignDetList = campaignDetList;
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

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
