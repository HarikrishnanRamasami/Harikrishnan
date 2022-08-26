/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.mc.model;

import java.util.Date;

/**
 *
 * @author ravindar.singh
 */
public class TMktgCampaignTxnLog implements java.io.Serializable {

    private Long mctlId;
    private Long mctlCampId;
    private Long mctlPathId;
    private Long mctlFlowNo;
    private Long mctlTxnId;
    private Long mctlDataId;
    private Long mctlTemplateId;
    private String mctlUrlKey;
    private String mctlLogType;
    private String mctlFlex01;
    private String mctlFlex02;
    private String mctlFlex03;
    private String mctlFlex04;
    private String mctlFlex05;
    private Date mctlCrDt;

    // DTO
    private String mctlUrl;

    public TMktgCampaignTxnLog() {
    }

    public Long getMctlId() {
        return this.mctlId;
    }

    public void setMctlId(Long mctlId) {
        this.mctlId = mctlId;
    }

    public Long getMctlCampId() {
        return this.mctlCampId;
    }

    public void setMctlCampId(Long mctlCampId) {
        this.mctlCampId = mctlCampId;
    }

    public Long getMctlPathId() {
        return this.mctlPathId;
    }

    public void setMctlPathId(Long mctlPathId) {
        this.mctlPathId = mctlPathId;
    }

    public Long getMctlFlowNo() {
        return this.mctlFlowNo;
    }

    public void setMctlFlowNo(Long mctlFlowNo) {
        this.mctlFlowNo = mctlFlowNo;
    }

    public Long getMctlTxnId() {
        return this.mctlTxnId;
    }

    public void setMctlTxnId(Long mctlTxnId) {
        this.mctlTxnId = mctlTxnId;
    }

    public Long getMctlDataId() {
        return this.mctlDataId;
    }

    public void setMctlDataId(Long mctlDataId) {
        this.mctlDataId = mctlDataId;
    }

    public Long getMctlTemplateId() {
        return this.mctlTemplateId;
    }

    public void setMctlTemplateId(Long mctlTemplateId) {
        this.mctlTemplateId = mctlTemplateId;
    }

    public String getMctlUrlKey() {
        return this.mctlUrlKey;
    }

    public void setMctlUrlKey(String mctlUrlKey) {
        this.mctlUrlKey = mctlUrlKey;
    }

    public String getMctlLogType() {
        return this.mctlLogType;
    }

    public void setMctlLogType(String mctlLogType) {
        this.mctlLogType = mctlLogType;
    }

    public String getMctlFlex01() {
        return this.mctlFlex01;
    }

    public void setMctlFlex01(String mctlFlex01) {
        this.mctlFlex01 = mctlFlex01;
    }

    public String getMctlFlex02() {
        return this.mctlFlex02;
    }

    public void setMctlFlex02(String mctlFlex02) {
        this.mctlFlex02 = mctlFlex02;
    }

    public String getMctlFlex03() {
        return this.mctlFlex03;
    }

    public void setMctlFlex03(String mctlFlex03) {
        this.mctlFlex03 = mctlFlex03;
    }

    public String getMctlFlex04() {
        return this.mctlFlex04;
    }

    public void setMctlFlex04(String mctlFlex04) {
        this.mctlFlex04 = mctlFlex04;
    }

    public String getMctlFlex05() {
        return this.mctlFlex05;
    }

    public void setMctlFlex05(String mctlFlex05) {
        this.mctlFlex05 = mctlFlex05;
    }

    public Date getMctlCrDt() {
        return this.mctlCrDt;
    }

    public void setMctlCrDt(Date mctlCrDt) {
        this.mctlCrDt = mctlCrDt;
    }

    public String getMctlUrl() {
        return mctlUrl;
    }

    public void setMctlUrl(String mctlUrl) {
        this.mctlUrl = mctlUrl;
    }

}
