/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.mc.model;

import java.util.Date;

/**
 *
 * @author sutharsan.g
 */
public class MMktgCampaignTxn implements java.io.Serializable {

    private Long mctCampId;
    private Long mctDataCount;
    private Date mctStartDate;
    private Date mctEndDate;
    private Date mctNextRunDate;
    private Integer mctPathFlowNo;
    private Long mctPathId;
    private Long mctTxnId;
    private String mctTxnStatus;
    private String mctWaitYn;
    private String mctFlex01;
    private String mctFlex02;
    private String mctFlex03;
    private String mctFlex04;
    private String mctFlex05;
    private String mctStatusDesc;
    private String mctFlowAction;
    private String mctAbYn;

    public Long getMctCampId() {
        return mctCampId;
    }

    public void setMctCampId(Long mctCampId) {
        this.mctCampId = mctCampId;
    }

    public Long getMctDataCount() {
        return mctDataCount;
    }

    public void setMctDataCount(Long mctDataCount) {
        this.mctDataCount = mctDataCount;
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

    public Date getMctNextRunDate() {
        return mctNextRunDate;
    }

    public void setMctNextRunDate(Date mctNextRunDate) {
        this.mctNextRunDate = mctNextRunDate;
    }

    public Integer getMctPathFlowNo() {
        return mctPathFlowNo;
    }

    public void setMctPathFlowNo(Integer mctPathFlowNo) {
        this.mctPathFlowNo = mctPathFlowNo;
    }

    public Long getMctPathId() {
        return mctPathId;
    }

    public void setMctPathId(Long mctPathId) {
        this.mctPathId = mctPathId;
    }

    public Long getMctTxnId() {
        return mctTxnId;
    }

    public void setMctTxnId(Long mctTxnId) {
        this.mctTxnId = mctTxnId;
    }

    public String getMctTxnStatus() {
        return mctTxnStatus;
    }

    public void setMctTxnStatus(String mctTxnStatus) {
        this.mctTxnStatus = mctTxnStatus;
    }

    public String getMctWaitYn() {
        return mctWaitYn;
    }

    public void setMctWaitYn(String mctWaitYn) {
        this.mctWaitYn = mctWaitYn;
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

    public String getMctStatusDesc() {
        return mctStatusDesc;
    }

    public void setMctStatusDesc(String mctStatusDesc) {
        this.mctStatusDesc = mctStatusDesc;
    }

    public String getMctFlowAction() {
        return mctFlowAction;
    }

    public void setMctFlowAction(String mctFlowAction) {
        this.mctFlowAction = mctFlowAction;
    }

    public String getMctAbYn() {
        return mctAbYn;
    }

    public void setMctAbYn(String mctAbYn) {
        this.mctAbYn = mctAbYn;
    }

}
