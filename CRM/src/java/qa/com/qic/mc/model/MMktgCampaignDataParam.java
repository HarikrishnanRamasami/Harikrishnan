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
public class MMktgCampaignDataParam implements java.io.Serializable {

    private Long mdpCampId;
    private String mdpType;
    private Byte mdpSrNo;
    private String mdpDataType;
    private String mdpColumn;
    private String mdpOperator;
    private String mdpValueFm;
    private String mdpValueTo;
    private String mdpCondition;
    private String mdpOrder;
    private String mdpCrUid;
    private Date mdpCrDt;
    private String mdpUpdUid;
    private Date mdpUpdDt;

    public MMktgCampaignDataParam() {
    }

    public Long getMdpCampId() {
        return mdpCampId;
    }

    public void setMdpCampId(Long mdpCampId) {
        this.mdpCampId = mdpCampId;
    }

    public String getMdpType() {
        return mdpType;
    }

    public void setMdpType(String mdpType) {
        this.mdpType = mdpType;
    }

    public Byte getMdpSrNo() {
        return mdpSrNo;
    }

    public void setMdpSrNo(Byte mdpSrNo) {
        this.mdpSrNo = mdpSrNo;
    }

    public String getMdpDataType() {
        return mdpDataType;
    }

    public void setMdpDataType(String mdpDataType) {
        this.mdpDataType = mdpDataType;
    }

    public String getMdpColumn() {
        return mdpColumn;
    }

    public void setMdpColumn(String mdpColumn) {
        this.mdpColumn = mdpColumn;
    }

    public String getMdpOperator() {
        return mdpOperator;
    }

    public void setMdpOperator(String mdpOperator) {
        this.mdpOperator = mdpOperator;
    }

    public String getMdpValueFm() {
        return mdpValueFm;
    }

    public void setMdpValueFm(String mdpValueFm) {
        this.mdpValueFm = mdpValueFm;
    }

    public String getMdpValueTo() {
        return mdpValueTo;
    }

    public void setMdpValueTo(String mdpValueTo) {
        this.mdpValueTo = mdpValueTo;
    }

    public String getMdpCondition() {
        return mdpCondition;
    }

    public void setMdpCondition(String mdpCondition) {
        this.mdpCondition = mdpCondition;
    }

    public String getMdpOrder() {
        return mdpOrder;
    }

    public void setMdpOrder(String mdpOrder) {
        this.mdpOrder = mdpOrder;
    }

    public String getMdpCrUid() {
        return mdpCrUid;
    }

    public void setMdpCrUid(String mdpCrUid) {
        this.mdpCrUid = mdpCrUid;
    }

    public Date getMdpCrDt() {
        return mdpCrDt;
    }

    public void setMdpCrDt(Date mdpCrDt) {
        this.mdpCrDt = mdpCrDt;
    }

    public String getMdpUpdUid() {
        return mdpUpdUid;
    }

    public void setMdpUpdUid(String mdpUpdUid) {
        this.mdpUpdUid = mdpUpdUid;
    }

    public Date getMdpUpdDt() {
        return mdpUpdDt;
    }

    public void setMdpUpdDt(Date mdpUpdDt) {
        this.mdpUpdDt = mdpUpdDt;
    }

}
