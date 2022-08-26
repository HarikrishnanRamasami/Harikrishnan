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
public class TMktgCampaignTxn implements java.io.Serializable {

    private Long mctTxnId;
    private Long mctCampId;
    private Long mctPathId;
    private Short mctPathFlowNo;
    private Long mctDataCount;
    private String mctWaitYn;
    private Date mctNextRunDate;
    private String mctTxnStatus;
    private Date mctStartDate;
    private Date mctEndDate;
    private String mctFlex01;
    private String mctFlex02;
    private String mctFlex03;
    private String mctFlex04;
    private String mctFlex05;
    private String mctAbYn;

    public TMktgCampaignTxn() {
    }

    public Long getMctTxnId() {
        return mctTxnId;
    }

    public void setMctTxnId(Long mctTxnId) {
        this.mctTxnId = mctTxnId;
    }

    public Long getMctCampId() {
        return mctCampId;
    }

    public void setMctCampId(Long mctCampId) {
        this.mctCampId = mctCampId;
    }

    public Long getMctPathId() {
        return mctPathId;
    }

    public void setMctPathId(Long mctPathId) {
        this.mctPathId = mctPathId;
    }

    public Short getMctPathFlowNo() {
        return mctPathFlowNo;
    }

    public void setMctPathFlowNo(Short mctPathFlowNo) {
        this.mctPathFlowNo = mctPathFlowNo;
    }

    public Long getMctDataCount() {
        return mctDataCount;
    }

    public void setMctDataCount(Long mctDataCount) {
        this.mctDataCount = mctDataCount;
    }

    public String getMctWaitYn() {
        return mctWaitYn;
    }

    public void setMctWaitYn(String mctWaitYn) {
        this.mctWaitYn = mctWaitYn;
    }

    public Date getMctNextRunDate() {
        return mctNextRunDate;
    }

    public void setMctNextRunDate(Date mctNextRunDate) {
        this.mctNextRunDate = mctNextRunDate;
    }

    public String getMctTxnStatus() {
        return mctTxnStatus;
    }

    public void setMctTxnStatus(String mctTxnStatus) {
        this.mctTxnStatus = mctTxnStatus;
    }

    public Date getMctStartDate() {
        return mctStartDate;
    }

    public void setMctStartDate(Date mctStartDate) {
        this.mctStartDate = mctStartDate;
    }

    public Date getMctEndDate() {
        return mctEndDate;
    }

    public void setMctEndDate(Date mctEndDate) {
        this.mctEndDate = mctEndDate;
    }

    public String getMctFlex01() {
        return mctFlex01;
    }

    public void setMctFlex01(String mctFlex01) {
        this.mctFlex01 = mctFlex01;
    }

    public String getMctFlex02() {
        return mctFlex02;
    }

    public void setMctFlex02(String mctFlex02) {
        this.mctFlex02 = mctFlex02;
    }

    public String getMctFlex03() {
        return mctFlex03;
    }

    public void setMctFlex03(String mctFlex03) {
        this.mctFlex03 = mctFlex03;
    }

    public String getMctFlex04() {
        return mctFlex04;
    }

    public void setMctFlex04(String mctFlex04) {
        this.mctFlex04 = mctFlex04;
    }

    public String getMctFlex05() {
        return mctFlex05;
    }

    public void setMctFlex05(String mctFlex05) {
        this.mctFlex05 = mctFlex05;
    }

    public String getMctAbYn() {
        return mctAbYn;
    }

    public void setMctAbYn(String mctAbYn) {
        this.mctAbYn = mctAbYn;
    }

}
