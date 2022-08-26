/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author karthik.j
 */
public class Appointment implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long caId;
	private transient String caTitle;
	private transient String caAttendees;
	private transient String caLocation;
	private transient String caTimezone;
	private transient String caDescription;
	private transient String caStartDate;
	private transient String caEndDate;
	private transient String caStartTime;
	private transient String caEndTime;
	private transient String caNotes;
    private transient String caCrUid;
    private transient String caCrUidDesc;
    private transient Date caCrDt;
    private transient String caUpdUid;
    private transient Date caUpDt;
    private transient String caDealId;
    private transient String caCrmId;
    private transient String caCrmDesc;
    private transient String caRefId;
    private transient String caContactType;
    private transient String caContTypDesc;
    private transient String caCustName;
    private transient String caInsCode;
    private transient String caInsName;
    private transient String caDateRange;
    private transient String operation;
    private String origin;


	public String getCaRefId() {
		return caRefId;
	}
	public void setCaRefId(String caRefId) {
		this.caRefId = caRefId;
	}

	public String getCaContactType() {
		return caContactType;
	}
	public void setCaContactType(String caContactType) {
		this.caContactType = caContactType;
	}
	public String getCaContTypDesc() {
		return caContTypDesc;
	}
	public void setCaContTypDesc(String caContTypDesc) {
		this.caContTypDesc = caContTypDesc;
	}
	public String getCaCustName() {
		return caCustName;
	}
	public void setCaCustName(String caCustName) {
		this.caCustName = caCustName;
	}
	public String getCaCrmId() {
		return caCrmId;
	}
	public void setCaCrmId(String caCrmId) {
		this.caCrmId = caCrmId;
	}
	public String getCaCrmDesc() {
		return caCrmDesc;
	}
	public void setCaCrmDesc(String caCrmDesc) {
		this.caCrmDesc = caCrmDesc;
	}
	public Long getCaId() {
		return caId;
	}
	public void setCaId(Long caId) {
		this.caId = caId;
	}
	public String getCaTitle() {
		return caTitle;
	}
	public void setCaTitle(String caTitle) {
		this.caTitle = caTitle;
	}
	public String getCaAttendees() {
		return caAttendees;
	}
	public void setCaAttendees(String caAttendees) {
		this.caAttendees = caAttendees;
	}
	public String getCaLocation() {
		return caLocation;
	}
	public void setCaLocation(String caLocation) {
		this.caLocation = caLocation;
	}
	public String getCaTimezone() {
		return caTimezone;
	}
	public void setCaTimezone(String caTimezone) {
		this.caTimezone = caTimezone;
	}
	public String getCaDescription() {
		return caDescription;
	}
	public void setCaDescription(String caDescription) {
		this.caDescription = caDescription;
	}
	public String getCaStartDate() {
		return caStartDate;
	}
	public void setCaStartDate(String caStartDate) {
		this.caStartDate = caStartDate;
	}
	public String getCaEndDate() {
		return caEndDate;
	}
	public void setCaEndDate(String caEndDate) {
		this.caEndDate = caEndDate;
	}
	public String getCaStartTime() {
		return caStartTime;
	}
	public void setCaStartTime(String caStartTime) {
		this.caStartTime = caStartTime;
	}
	public String getCaEndTime() {
		return caEndTime;
	}
	public void setCaEndTime(String caEndTime) {
		this.caEndTime = caEndTime;
	}
	public String getCaNotes() {
		return caNotes;
	}
	public void setCaNotes(String caNotes) {
		this.caNotes = caNotes;
	}
	public String getCaCrUid() {
		return caCrUid;
	}
	public void setCaCrUid(String caCrUid) {
		this.caCrUid = caCrUid;
	}
	public String getCaCrUidDesc() {
		return caCrUidDesc;
	}
	public void setCaCrUidDesc(String caCrUidDesc) {
		this.caCrUidDesc = caCrUidDesc;
	}
	public Date getCaCrDt() {
		return caCrDt;
	}
	public void setCaCrDt(Date caCrDt) {
		this.caCrDt = caCrDt;
	}
	public String getCaUpdUid() {
		return caUpdUid;
	}
	public void setCaUpdUid(String caUpdUid) {
		this.caUpdUid = caUpdUid;
	}
	public Date getCaUpDt() {
		return caUpDt;
	}
	public void setCaUpDt(Date caUpDt) {
		this.caUpDt = caUpDt;
	}
	public String getCaDealId() {
		return caDealId;
	}
	public void setCaDealId(String caDealId) {
		this.caDealId = caDealId;
	}
	public String getCaDateRange() {
		return caDateRange;
	}
	public void setCaDateRange(String caDateRange) {
		this.caDateRange = caDateRange;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getCaInsName() {
		return caInsName;
	}
	public void setCaInsName(String caInsName) {
		this.caInsName = caInsName;
	}
	public String getCaInsCode() {
		return caInsCode;
	}
	public void setCaInsCode(String caInsCode) {
		this.caInsCode = caInsCode;
	}



}
