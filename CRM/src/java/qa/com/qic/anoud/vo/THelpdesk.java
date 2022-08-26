/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ravindar.singh
 */
public class THelpdesk implements java.io.Serializable {

    private Long thReqId;
    private String thReqUid;
    private Date thReqDt;
    private String thReqType;
    private String thPriority;
    private String thCatg;
    private String thSubCatg;
    private String thTo;
    private String thCc;
    private String thSubject;
    private String thMessage;
    private String thTechGrp;
    private String thTechnician;
    private String thRemarks;
    private Date thDueDate;
    private String thStatus;
    private Date thCloseDt;
    private Date thCrDt;
    private String thCrUid;
    private Date thUpdDt;
    private String thUpdUid;
    private String thFlex01;
    private String thFlex02;
    private String thFlex03;
    private String thFlex04;
    private String thFlex05;
    private Set THelpdeskNoteses = new HashSet(0);

    // view-V_HELPDESK_COUNT
    private String thType;
    private BigDecimal thOpen;
    private BigDecimal thClose;
    private BigDecimal thOnHold;
    private BigDecimal thOverDue;
    private BigDecimal thDueToday;

    // DTO
    private String thStatusDesc;
    private Integer thSlaViolated;
    private String thReqUidDesc;
    private String thReqTypeDesc;
    private String thPriorityDesc;
    private String thCatgDesc;
    private String thSubCatgDesc;
    private String thToDesc;

    public THelpdesk() {
    }

    public THelpdesk(Long thReqId) {
        this.thReqId = thReqId;
    }

    public THelpdesk(Long thReqId, String thReqUid, Date thReqDt, String thReqType, String thPriority, String thCatg, String thSubCatg, String thTo, String thCc, String thSubject, String thMessage, String thTechGrp, String thTechnician, String thRemarks, Date thDueDate, String thStatus, Date thCloseDt, Date thCrDt, String thCrUid, Date thUpdDt, String thUpdUid, String thFlex01, String thFlex02, String thFlex03, String thFlex04, String thFlex05, Set THelpdeskNoteses) {
        this.thReqId = thReqId;
        this.thReqUid = thReqUid;
        this.thReqDt = thReqDt;
        this.thReqType = thReqType;
        this.thPriority = thPriority;
        this.thCatg = thCatg;
        this.thSubCatg = thSubCatg;
        this.thTo = thTo;
        this.thCc = thCc;
        this.thSubject = thSubject;
        this.thMessage = thMessage;
        this.thTechGrp = thTechGrp;
        this.thTechnician = thTechnician;
        this.thRemarks = thRemarks;
        this.thDueDate = thDueDate;
        this.thStatus = thStatus;
        this.thCloseDt = thCloseDt;
        this.thCrDt = thCrDt;
        this.thCrUid = thCrUid;
        this.thUpdDt = thUpdDt;
        this.thUpdUid = thUpdUid;
        this.thFlex01 = thFlex01;
        this.thFlex02 = thFlex02;
        this.thFlex03 = thFlex03;
        this.thFlex04 = thFlex04;
        this.thFlex05 = thFlex05;
        this.THelpdeskNoteses = THelpdeskNoteses;
    }

    public Long getThReqId() {
        return this.thReqId;
    }

    public void setThReqId(Long thReqId) {
        this.thReqId = thReqId;
    }

    public String getThReqUid() {
        return this.thReqUid;
    }

    public void setThReqUid(String thReqUid) {
        this.thReqUid = thReqUid;
    }

    public Date getThReqDt() {
        return this.thReqDt;
    }

    public void setThReqDt(Date thReqDt) {
        this.thReqDt = thReqDt;
    }

    public String getThReqType() {
        return this.thReqType;
    }

    public void setThReqType(String thReqType) {
        this.thReqType = thReqType;
    }

    public String getThPriority() {
        return this.thPriority;
    }

    public void setThPriority(String thPriority) {
        this.thPriority = thPriority;
    }

    public String getThCatg() {
        return this.thCatg;
    }

    public void setThCatg(String thCatg) {
        this.thCatg = thCatg;
    }

    public String getThSubCatg() {
        return this.thSubCatg;
    }

    public void setThSubCatg(String thSubCatg) {
        this.thSubCatg = thSubCatg;
    }

    public String getThTo() {
        return this.thTo;
    }

    public void setThTo(String thTo) {
        this.thTo = thTo;
    }

    public String getThCc() {
        return this.thCc;
    }

    public void setThCc(String thCc) {
        this.thCc = thCc;
    }

    public String getThSubject() {
        return this.thSubject;
    }

    public void setThSubject(String thSubject) {
        this.thSubject = thSubject;
    }

    public String getThMessage() {
        return this.thMessage;
    }

    public void setThMessage(String thMessage) {
        this.thMessage = thMessage;
    }

    public String getThTechGrp() {
        return this.thTechGrp;
    }

    public void setThTechGrp(String thTechGrp) {
        this.thTechGrp = thTechGrp;
    }

    public String getThTechnician() {
        return this.thTechnician;
    }

    public void setThTechnician(String thTechnician) {
        this.thTechnician = thTechnician;
    }

    public String getThRemarks() {
        return this.thRemarks;
    }

    public void setThRemarks(String thRemarks) {
        this.thRemarks = thRemarks;
    }

    public Date getThDueDate() {
        return this.thDueDate;
    }

    public void setThDueDate(Date thDueDate) {
        this.thDueDate = thDueDate;
    }

    public String getThStatus() {
        return this.thStatus;
    }

    public void setThStatus(String thStatus) {
        this.thStatus = thStatus;
    }

    public Date getThCloseDt() {
        return this.thCloseDt;
    }

    public void setThCloseDt(Date thCloseDt) {
        this.thCloseDt = thCloseDt;
    }

    public Date getThCrDt() {
        return this.thCrDt;
    }

    public void setThCrDt(Date thCrDt) {
        this.thCrDt = thCrDt;
    }

    public String getThCrUid() {
        return this.thCrUid;
    }

    public void setThCrUid(String thCrUid) {
        this.thCrUid = thCrUid;
    }

    public Date getThUpdDt() {
        return this.thUpdDt;
    }

    public void setThUpdDt(Date thUpdDt) {
        this.thUpdDt = thUpdDt;
    }

    public String getThUpdUid() {
        return this.thUpdUid;
    }

    public void setThUpdUid(String thUpdUid) {
        this.thUpdUid = thUpdUid;
    }

    public String getThFlex01() {
        return this.thFlex01;
    }

    public void setThFlex01(String thFlex01) {
        this.thFlex01 = thFlex01;
    }

    public String getThFlex02() {
        return this.thFlex02;
    }

    public void setThFlex02(String thFlex02) {
        this.thFlex02 = thFlex02;
    }

    public String getThFlex03() {
        return this.thFlex03;
    }

    public void setThFlex03(String thFlex03) {
        this.thFlex03 = thFlex03;
    }

    public String getThFlex04() {
        return this.thFlex04;
    }

    public void setThFlex04(String thFlex04) {
        this.thFlex04 = thFlex04;
    }

    public String getThFlex05() {
        return this.thFlex05;
    }

    public void setThFlex05(String thFlex05) {
        this.thFlex05 = thFlex05;
    }

    public Set getTHelpdeskNoteses() {
        return this.THelpdeskNoteses;
    }

    public void setTHelpdeskNoteses(Set THelpdeskNoteses) {
        this.THelpdeskNoteses = THelpdeskNoteses;
    }

    public BigDecimal getThOpen() {
        return thOpen;
    }

    public void setThOpen(BigDecimal thOpen) {
        this.thOpen = thOpen;
    }

    public BigDecimal getThOnHold() {
        return thOnHold;
    }

    public void setThOnHold(BigDecimal thOnHold) {
        this.thOnHold = thOnHold;
    }

    public BigDecimal getThOverDue() {
        return thOverDue;
    }

    public void setThOverDue(BigDecimal thOverDue) {
        this.thOverDue = thOverDue;
    }

    public BigDecimal getThDueToday() {
        return thDueToday;
    }

    public void setThDueToday(BigDecimal thDueToday) {
        this.thDueToday = thDueToday;
    }

    public String getThType() {
        return thType;
    }

    public void setThType(String thType) {
        this.thType = thType;
    }

    public String getThStatusDesc() {
        return thStatusDesc;
    }

    public void setThStatusDesc(String thStatusDesc) {
        this.thStatusDesc = thStatusDesc;
    }

    public Integer getThSlaViolated() {
        return thSlaViolated;
    }

    public void setThSlaViolated(Integer thSlaViolated) {
        this.thSlaViolated = thSlaViolated;
    }

    public String getThReqUidDesc() {
        return thReqUidDesc;
    }

    public void setThReqUidDesc(String thReqUidDesc) {
        this.thReqUidDesc = thReqUidDesc;
    }

    public String getThReqTypeDesc() {
        return thReqTypeDesc;
    }

    public void setThReqTypeDesc(String thReqTypeDesc) {
        this.thReqTypeDesc = thReqTypeDesc;
    }

    public String getThPriorityDesc() {
        return thPriorityDesc;
    }

    public void setThPriorityDesc(String thPriorityDesc) {
        this.thPriorityDesc = thPriorityDesc;
    }

    public String getThCatgDesc() {
        return thCatgDesc;
    }

    public void setThCatgDesc(String thCatgDesc) {
        this.thCatgDesc = thCatgDesc;
    }

    public String getThSubCatgDesc() {
        return thSubCatgDesc;
    }

    public void setThSubCatgDesc(String thSubCatgDesc) {
        this.thSubCatgDesc = thSubCatgDesc;
    }

    public BigDecimal getThClose() {
        return thClose;
    }

    public void setThClose(BigDecimal thClose) {
        this.thClose = thClose;
    }

    public String getThToDesc() {
        return thToDesc;
    }

    public void setThToDesc(String thToDesc) {
        this.thToDesc = thToDesc;
    }

}
