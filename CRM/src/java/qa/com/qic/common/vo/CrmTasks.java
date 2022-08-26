/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author ravindar.singh
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrmTasks {

    private Long ctId;
    private String ctRefId;
    private String ctRefNo;
    private String ctPriority;
    private String ctModule;
    private String ctCatg;
    private String ctSubCatg;
    private String ctSubject;
    private String ctMessage;
    private String ctStatus;
    private String ctAssignedTo;
    private String ctAssignedDt;
    private String ctDueDate;
    private Integer ctRemindBefore;
    private String ctCrUid;
    private Date ctCrDt;
    private String ctUpdUid;
    private Date ctUpdDt;
    private String ctFlex01;
    private String ctFlex02;
    private String ctFlex03;
    private String ctFlex04;
    private String ctFlex05;
    private Date ctCloseDate;
    private String ctCloseCode;
    private String ctCloseRemarks;
    private String ctReadYn;
    private String ctCloseYn;
    private String ctCrmId;
    private String ctCrmDesc;
    private String ctDealId;



	//DTO
    private String ctCreateActivityYn;
    private String ctModuleDesc;
    private String ctPriorityDesc;
    private String ctCatgDesc;
    private String ctSubCatgDesc;
    private String ctAffSource;
    private String ctAssignedToDesc;
    private CrmTasksLog crmTasksLog;
    private String ctSlaViolated;
    private String ctReason;
    private String ctMobileNo;
    private String ctEmailId;
    public Long getCtId() {
        return ctId;
    }

    public void setCtId(Long ctId) {
        this.ctId = ctId;
    }

    public String getCtRefId() {
        return ctRefId;
    }

    public void setCtRefId(String ctRefId) {
        this.ctRefId = ctRefId;
    }

    public String getCtRefNo() {
        return ctRefNo;
    }

    public void setCtRefNo(String ctRefNo) {
        this.ctRefNo = ctRefNo;
    }

    public String getCtPriority() {
        return ctPriority;
    }

    public void setCtPriority(String ctPriority) {
        this.ctPriority = ctPriority;
    }

    public String getCtModule() {
        return ctModule;
    }

    public void setCtModule(String ctModule) {
        this.ctModule = ctModule;
    }

    public String getCtCatg() {
        return ctCatg;
    }

    public void setCtCatg(String ctCatg) {
        this.ctCatg = ctCatg;
    }

    public String getCtSubCatg() {
        return ctSubCatg;
    }

    public void setCtSubCatg(String ctSubCatg) {
        this.ctSubCatg = ctSubCatg;
    }

    public String getCtSubject() {
        return ctSubject;
    }

    public void setCtSubject(String ctSubject) {
        this.ctSubject = ctSubject;
    }

    public String getCtMessage() {
        return ctMessage;
    }

    public void setCtMessage(String ctMessage) {
        this.ctMessage = ctMessage;
    }

    public String getCtStatus() {
        return ctStatus;
    }

    public void setCtStatus(String ctStatus) {
        this.ctStatus = ctStatus;
    }

    public String getCtAssignedTo() {
        return ctAssignedTo;
    }

    public void setCtAssignedTo(String ctAssignedTo) {
        this.ctAssignedTo = ctAssignedTo;
    }

    public String getCtAssignedDt() {
        return ctAssignedDt;
    }

    public void setCtAssignedDt(String ctAssignedDt) {
        this.ctAssignedDt = ctAssignedDt;
    }

    public String getCtDueDate() {
        return ctDueDate;
    }

    public void setCtDueDate(String ctDueDate) {
        this.ctDueDate = ctDueDate;
    }

    public Integer getCtRemindBefore() {
        return ctRemindBefore;
    }

    public void setCtRemindBefore(Integer ctRemindBefore) {
        this.ctRemindBefore = ctRemindBefore;
    }

    public String getCtCrUid() {
        return ctCrUid;
    }

    public void setCtCrUid(String ctCrUid) {
        this.ctCrUid = ctCrUid;
    }

    public Date getCtCrDt() {
        return ctCrDt;
    }

    public void setCtCrDt(Date ctCrDt) {
        this.ctCrDt = ctCrDt;
    }

    public String getCtUpdUid() {
        return ctUpdUid;
    }

    public void setCtUpdUid(String ctUpdUid) {
        this.ctUpdUid = ctUpdUid;
    }

    public Date getCtUpdDt() {
        return ctUpdDt;
    }

    public void setCtUpdDt(Date ctUpdDt) {
        this.ctUpdDt = ctUpdDt;
    }

    public String getCtFlex01() {
        return ctFlex01;
    }

    public void setCtFlex01(String ctFlex01) {
        this.ctFlex01 = ctFlex01;
    }

    public String getCtFlex02() {
        return ctFlex02;
    }

    public void setCtFlex02(String ctFlex02) {
        this.ctFlex02 = ctFlex02;
    }

    public String getCtFlex03() {
        return ctFlex03;
    }

    public void setCtFlex03(String ctFlex03) {
        this.ctFlex03 = ctFlex03;
    }

    public String getCtFlex04() {
        return ctFlex04;
    }

    public void setCtFlex04(String ctFlex04) {
        this.ctFlex04 = ctFlex04;
    }

    public String getCtFlex05() {
        return ctFlex05;
    }

    public void setCtFlex05(String ctFlex05) {
        this.ctFlex05 = ctFlex05;
    }

    public String getCtCreateActivityYn() {
        return ctCreateActivityYn;
    }

    public void setCtCreateActivityYn(String ctCreateActivityYn) {
        this.ctCreateActivityYn = ctCreateActivityYn;
    }

    public String getCtModuleDesc() {
        return ctModuleDesc;
    }

    public void setCtModuleDesc(String ctModuleDesc) {
        this.ctModuleDesc = ctModuleDesc;
    }

    public String getCtPriorityDesc() {
        return ctPriorityDesc;
    }

    public void setCtPriorityDesc(String ctPriorityDesc) {
        this.ctPriorityDesc = ctPriorityDesc;
    }

    public String getCtCatgDesc() {
        return ctCatgDesc;
    }

    public void setCtCatgDesc(String ctCatgDesc) {
        this.ctCatgDesc = ctCatgDesc;
    }

    public String getCtSubCatgDesc() {
        return ctSubCatgDesc;
    }

    public void setCtSubCatgDesc(String ctSubCatgDesc) {
        this.ctSubCatgDesc = ctSubCatgDesc;
    }

    public String getCtAssignedToDesc() {
        return ctAssignedToDesc;
    }

    public void setCtAssignedToDesc(String ctAssignedToDesc) {
        this.ctAssignedToDesc = ctAssignedToDesc;
    }

    public Date getCtCloseDate() {
        return ctCloseDate;
    }

    public void setCtCloseDate(Date ctCloseDate) {
        this.ctCloseDate = ctCloseDate;
    }

    public String getCtCloseCode() {
        return ctCloseCode;
    }

    public void setCtCloseCode(String ctCloseCode) {
        this.ctCloseCode = ctCloseCode;
    }

    public String getCtCloseRemarks() {
        return ctCloseRemarks;
    }

    public void setCtCloseRemarks(String ctCloseRemarks) {
        this.ctCloseRemarks = ctCloseRemarks;
    }

    public String getCtReadYn() {
        return ctReadYn;
    }

    public void setCtReadYn(String ctReadYn) {
        this.ctReadYn = ctReadYn;
    }

    public CrmTasksLog getCrmTasksLog() {
        return crmTasksLog;
    }

    public void setCrmTasksLog(CrmTasksLog crmTasksLog) {
        this.crmTasksLog = crmTasksLog;
    }

    public String getCtSlaViolated() {
        return ctSlaViolated;
    }

    public void setCtSlaViolated(String ctSlaViolated) {
        this.ctSlaViolated = ctSlaViolated;
    }

    /**
     * @return the ctAffSource
     */
    public String getCtAffSource() {
        return ctAffSource;
    }

    /**
     * @param ctAffSource the ctAffSource to set
     */
    public void setCtAffSource(String ctAffSource) {
        this.ctAffSource = ctAffSource;
    }

    /**
     * @return the ctReason
     */
    public String getCtReason() {
        return ctReason;
    }

    /**
     * @param ctReason the ctReason to set
     */
    public void setCtReason(String ctReason) {
        this.ctReason = ctReason;
    }

    /**
     * @return the ctMobileNo
     */
    public String getCtMobileNo() {
        return ctMobileNo;
    }

    /**
     * @param ctMobileNo the ctMobileNo to set
     */
    public void setCtMobileNo(String ctMobileNo) {
        this.ctMobileNo = ctMobileNo;
    }

    /**
     * @return the ctEmailId
     */
    public String getCtEmailId() {
        return ctEmailId;
    }

    /**
     * @param ctEmailId the ctEmailId to set
     */
    public void setCtEmailId(String ctEmailId) {
        this.ctEmailId = ctEmailId;
    }

    public String getCtCloseYn() {
        return ctCloseYn;
    }

    public void setCtCloseYn(String ctCloseYn) {
        this.ctCloseYn = ctCloseYn;
    }

    public String getCtCrmId() {
        return ctCrmId;
    }

    public void setCtCrmId(String ctCrmId) {
        this.ctCrmId = ctCrmId;
    }

    public String getCtCrmDesc() {
        return ctCrmDesc;
    }

    public void setCtCrmDesc(String ctCrmDesc) {
        this.ctCrmDesc = ctCrmDesc;
    }

	public String getCtDealId() {
		return ctDealId;
	}

	public void setCtDealId(String ctDealId) {
		this.ctDealId = ctDealId;
	}


}
