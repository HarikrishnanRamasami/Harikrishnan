/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.corporate.vo;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author karthik.j
 */
public class CustomerContact implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private transient String custCode;
	private transient String contactName;
	private transient String designation;
	private transient String telNo;
	private transient String mobileNo;
	private transient String faxNo;
	private transient String email;
    private transient String crUid;
    private transient Date crDt;
    private transient String updUid;
    private transient Date upDt;

	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCrUid() {
		return crUid;
	}
	public void setCrUid(String crUid) {
		this.crUid = crUid;
	}
	public Date getCrDt() {
		return crDt;
	}
	public void setCrDt(Date crDt) {
		this.crDt = crDt;
	}
	public String getUpdUid() {
		return updUid;
	}
	public void setUpdUid(String updUid) {
		this.updUid = updUid;
	}
	public Date getUpDt() {
		return upDt;
	}
	public void setUpDt(Date upDt) {
		this.upDt = upDt;
	}


}
