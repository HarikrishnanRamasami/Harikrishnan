/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.mc.model;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ravindar.singh
 */
public class MMktgCampaign implements java.io.Serializable {

    private Long mcCampId;
    private String mcCampName;
    private String mcCampCode;
    private String mcDataSourceType;
    private String mcDataSourceView;
    private String mcCampType;
    private String mcSenderId;
    private String mcReplyTo;
    private String mcUnsubscribeInclYn;
    private String mcRecurringYn;
    private String mcScheduleMode;
    private Byte mcScheduleValue;
    private BigDecimal mcScheduleTime;
    private Byte mcStartTime;
    private Date mcNextRunDate;
    private String mcAbYn;
    private Short mcAbDataCount;
    private Date mcAbStartDate;
    private Date mcAbEndDate;
    private String mcAbActionType;
    private String mcStatus;
    private String mcSelectedCols;
    private String mcVerifyConversionYn;
    private BigDecimal mcTargetAmt;
    private String mcPreviewYn;
    private String mcAllowUnsubscribeYn;
    private String mcFlex01;
    private String mcFlex02;
    private String mcFlex03;
    private String mcFlex04;
    private String mcFlex05;
    private String mcCrUid;
    private Date mcCrDt;
    private String mcUpdUid;
    private Date mcUpdDt;
    private String mcCrmId;

    private Integer mcScheduleTimeHrs;
    private Integer mcScheduleTimeMin;
    private String mcStatusDesc;
    private Date mcDelayStartDate;
    private String mcCrmDesc;

    private File excelFile;
    private String excelFileContentType;
    private String excelFileFileName;

    public MMktgCampaign() {
    }

    public MMktgCampaign(Long mcCampId) {
        this.mcCampId = mcCampId;
    }

    public Long getMcCampId() {
        return this.mcCampId;
    }

    public void setMcCampId(Long mcCampId) {
        this.mcCampId = mcCampId;
    }

    public String getMcCampName() {
        return this.mcCampName;
    }

    public void setMcCampName(String mcCampName) {
        this.mcCampName = mcCampName;
    }

    public String getMcCampCode() {
        return this.mcCampCode;
    }

    public void setMcCampCode(String mcCampCode) {
        this.mcCampCode = mcCampCode;
    }

    public String getMcDataSourceType() {
        return this.mcDataSourceType;
    }

    public void setMcDataSourceType(String mcDataSourceType) {
        this.mcDataSourceType = mcDataSourceType;
    }

    public String getMcDataSourceView() {
        return this.mcDataSourceView;
    }

    public void setMcDataSourceView(String mcDataSourceView) {
        this.mcDataSourceView = mcDataSourceView;
    }

    public String getMcCampType() {
        return this.mcCampType;
    }

    public void setMcCampType(String mcCampType) {
        this.mcCampType = mcCampType;
    }

    public String getMcSenderId() {
        return this.mcSenderId;
    }

    public void setMcSenderId(String mcSenderId) {
        this.mcSenderId = mcSenderId;
    }

    public String getMcReplyTo() {
        return this.mcReplyTo;
    }

    public void setMcReplyTo(String mcReplyTo) {
        this.mcReplyTo = mcReplyTo;
    }

    public String getMcUnsubscribeInclYn() {
        return this.mcUnsubscribeInclYn;
    }

    public void setMcUnsubscribeInclYn(String mcUnsubscribeInclYn) {
        this.mcUnsubscribeInclYn = mcUnsubscribeInclYn;
    }

    public String getMcRecurringYn() {
        return this.mcRecurringYn;
    }

    public void setMcRecurringYn(String mcRecurringYn) {
        this.mcRecurringYn = mcRecurringYn;
    }

    public String getMcScheduleMode() {
        return this.mcScheduleMode;
    }

    public void setMcScheduleMode(String mcScheduleMode) {
        this.mcScheduleMode = mcScheduleMode;
    }

    public Byte getMcScheduleValue() {
        return this.mcScheduleValue;
    }

    public void setMcScheduleValue(Byte mcScheduleValue) {
        this.mcScheduleValue = mcScheduleValue;
    }

    public BigDecimal getMcScheduleTime() {
        return this.mcScheduleTime;
    }

    public void setMcScheduleTime(BigDecimal mcScheduleTime) {
        this.mcScheduleTime = mcScheduleTime;
    }

    public Byte getMcStartTime() {
        return this.mcStartTime;
    }

    public void setMcStartTime(Byte mcStartTime) {
        this.mcStartTime = mcStartTime;
    }

    public Date getMcNextRunDate() {
        return this.mcNextRunDate;
    }

    public void setMcNextRunDate(Date mcNextRunDate) {
        this.mcNextRunDate = mcNextRunDate;
    }

    public String getMcAbYn() {
        return this.mcAbYn;
    }

    public void setMcAbYn(String mcAbYn) {
        this.mcAbYn = mcAbYn;
    }

    public Short getMcAbDataCount() {
        return this.mcAbDataCount;
    }

    public void setMcAbDataCount(Short mcAbDataCount) {
        this.mcAbDataCount = mcAbDataCount;
    }

    public Date getMcAbStartDate() {
        return this.mcAbStartDate;
    }

    public void setMcAbStartDate(Date mcAbStartDate) {
        this.mcAbStartDate = mcAbStartDate;
    }

    public Date getMcAbEndDate() {
        return this.mcAbEndDate;
    }

    public void setMcAbEndDate(Date mcAbEndDate) {
        this.mcAbEndDate = mcAbEndDate;
    }

    public String getMcStatus() {
        return this.mcStatus;
    }

    public void setMcStatus(String mcStatus) {
        this.mcStatus = mcStatus;
    }

    public String getMcSelectedCols() {
        return this.mcSelectedCols;
    }

    public void setMcSelectedCols(String mcSelectedCols) {
        this.mcSelectedCols = mcSelectedCols;
    }

    public BigDecimal getMcTargetAmt() {
        return this.mcTargetAmt;
    }

    public void setMcTargetAmt(BigDecimal mcTargetAmt) {
        this.mcTargetAmt = mcTargetAmt;
    }

    public String getMcPreviewYn() {
        return mcPreviewYn;
    }

    public void setMcPreviewYn(String mcPreviewYn) {
        this.mcPreviewYn = mcPreviewYn;
    }

    public String getMcAllowUnsubscribeYn() {
        return mcAllowUnsubscribeYn;
    }

    public void setMcAllowUnsubscribeYn(String mcAllowUnsubscribeYn) {
        this.mcAllowUnsubscribeYn = mcAllowUnsubscribeYn;
    }

    public String getMcFlex01() {
        return this.mcFlex01;
    }

    public void setMcFlex01(String mcFlex01) {
        this.mcFlex01 = mcFlex01;
    }

    public String getMcFlex02() {
        return this.mcFlex02;
    }

    public void setMcFlex02(String mcFlex02) {
        this.mcFlex02 = mcFlex02;
    }

    public String getMcFlex03() {
        return this.mcFlex03;
    }

    public void setMcFlex03(String mcFlex03) {
        this.mcFlex03 = mcFlex03;
    }

    public String getMcFlex04() {
        return this.mcFlex04;
    }

    public void setMcFlex04(String mcFlex04) {
        this.mcFlex04 = mcFlex04;
    }

    public String getMcFlex05() {
        return this.mcFlex05;
    }

    public void setMcFlex05(String mcFlex05) {
        this.mcFlex05 = mcFlex05;
    }

    public String getMcCrUid() {
        return this.mcCrUid;
    }

    public void setMcCrUid(String mcCrUid) {
        this.mcCrUid = mcCrUid;
    }

    public Date getMcCrDt() {
        return this.mcCrDt;
    }

    public void setMcCrDt(Date mcCrDt) {
        this.mcCrDt = mcCrDt;
    }

    public String getMcUpdUid() {
        return this.mcUpdUid;
    }

    public void setMcUpdUid(String mcUpdUid) {
        this.mcUpdUid = mcUpdUid;
    }

    public Date getMcUpdDt() {
        return this.mcUpdDt;
    }

    public void setMcUpdDt(Date mcUpdDt) {
        this.mcUpdDt = mcUpdDt;
    }

    public String getMcCrmId() {
        return mcCrmId;
    }

    public void setMcCrmId(String mcCrmId) {
        this.mcCrmId = mcCrmId;
    }

    public Integer getMcScheduleTimeHrs() {
        return mcScheduleTimeHrs;
    }

    public void setMcScheduleTimeHrs(Integer mcScheduleTimeHrs) {
        this.mcScheduleTimeHrs = mcScheduleTimeHrs;
    }

    public Integer getMcScheduleTimeMin() {
        return mcScheduleTimeMin;
    }

    public void setMcScheduleTimeMin(Integer mcScheduleTimeMin) {
        this.mcScheduleTimeMin = mcScheduleTimeMin;
    }

    public String getMcStatusDesc() {
        return mcStatusDesc;
    }

    public void setMcStatusDesc(String mcStatusDesc) {
        this.mcStatusDesc = mcStatusDesc;
    }

    public Date getMcDelayStartDate() {
        return mcDelayStartDate;
    }

    public void setMcDelayStartDate(Date mcDelayStartDate) {
        this.mcDelayStartDate = mcDelayStartDate;
    }

    public String getMcCrmDesc() {
        return mcCrmDesc;
    }

    public void setMcCrmDesc(String mcCrmDesc) {
        this.mcCrmDesc = mcCrmDesc;
    }

    public File getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(File excelFile) {
        this.excelFile = excelFile;
    }

    public String getExcelFileContentType() {
        return excelFileContentType;
    }

    public void setExcelFileContentType(String excelFileContentType) {
        this.excelFileContentType = excelFileContentType;
    }

    public String getExcelFileFileName() {
        return excelFileFileName;
    }

    public void setExcelFileFileName(String excelFileFileName) {
        this.excelFileFileName = excelFileFileName;
    }

    public String getMcVerifyConversionYn() {
        return mcVerifyConversionYn;
    }

    public void setMcVerifyConversionYn(String mcVerifyConversionYn) {
        this.mcVerifyConversionYn = mcVerifyConversionYn;
    }

    public String getMcAbActionType() {
        return mcAbActionType;
    }

    public void setMcAbActionType(String mcAbActionType) {
        this.mcAbActionType = mcAbActionType;
    }


}
