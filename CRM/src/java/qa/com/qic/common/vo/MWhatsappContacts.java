/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.vo;

import java.util.Date;

/**
 *
 * @author ravindar.singh
 */
public class MWhatsappContacts implements java.io.Serializable {

    private String wcMobileNo;
    private String wcName;
    private String wcCrmId;
    private String wcDnd;
    private String wcDfltLang;
    private String wcFrzYn;
    private String wcCrUid;
    private Date wcCrDt;
    private String wcUpdUid;
    private Date wcUpdDt;
    private String wcUserKey;

    public MWhatsappContacts() {
    }

    public MWhatsappContacts(String wcMobileNo) {
        this.wcMobileNo = wcMobileNo;
    }

    public MWhatsappContacts(String wcMobileNo, String wcName, String wcDnd, String wcDfltLang, String wcFrzYn, String wcCrUid, Date wcCrDt, String wcUpdUid, Date wcUpdDt) {
        this.wcMobileNo = wcMobileNo;
        this.wcName = wcName;
        this.wcDnd = wcDnd;
        this.wcDfltLang = wcDfltLang;
        this.wcFrzYn = wcFrzYn;
        this.wcCrUid = wcCrUid;
        this.wcCrDt = wcCrDt;
        this.wcUpdUid = wcUpdUid;
        this.wcUpdDt = wcUpdDt;
    }

    public MWhatsappContacts(String wcMobileNo, String wcName, String wcDnd, String wcDfltLang, String wcFrzYn,
			String wcCrUid, Date wcCrDt, String wcUpdUid, Date wcUpdDt, String wcUserKey) {
		super();
		this.wcMobileNo = wcMobileNo;
		this.wcName = wcName;
		this.wcDnd = wcDnd;
		this.wcDfltLang = wcDfltLang;
		this.wcFrzYn = wcFrzYn;
		this.wcCrUid = wcCrUid;
		this.wcCrDt = wcCrDt;
		this.wcUpdUid = wcUpdUid;
		this.wcUpdDt = wcUpdDt;
		this.wcUserKey = wcUserKey;
	}

    public String getWcMobileNo() {
        return this.wcMobileNo;
    }

    public void setWcMobileNo(String wcMobileNo) {
        this.wcMobileNo = wcMobileNo;
    }

    public String getWcName() {
        return this.wcName;
    }

    public void setWcName(String wcName) {
        this.wcName = wcName;
    }

    public String getWcCrmId() {
        return wcCrmId;
    }

    public void setWcCrmId(String wcCrmId) {
        this.wcCrmId = wcCrmId;
    }

    public String getWcDnd() {
        return this.wcDnd;
    }

    public void setWcDnd(String wcDnd) {
        this.wcDnd = wcDnd;
    }

    public String getWcDfltLang() {
        return this.wcDfltLang;
    }

    public void setWcDfltLang(String wcDfltLang) {
        this.wcDfltLang = wcDfltLang;
    }

    public String getWcFrzYn() {
        return this.wcFrzYn;
    }

    public void setWcFrzYn(String wcFrzYn) {
        this.wcFrzYn = wcFrzYn;
    }

    public String getWcCrUid() {
        return this.wcCrUid;
    }

    public void setWcCrUid(String wcCrUid) {
        this.wcCrUid = wcCrUid;
    }

    public Date getWcCrDt() {
        return this.wcCrDt;
    }

    public void setWcCrDt(Date wcCrDt) {
        this.wcCrDt = wcCrDt;
    }

    public String getWcUpdUid() {
        return this.wcUpdUid;
    }

    public void setWcUpdUid(String wcUpdUid) {
        this.wcUpdUid = wcUpdUid;
    }

    public Date getWcUpdDt() {
        return this.wcUpdDt;
    }

    public void setWcUpdDt(Date wcUpdDt) {
        this.wcUpdDt = wcUpdDt;
    }

	public String getWcUserKey() {
		return wcUserKey;
	}

	public void setWcUserKey(String wcUserKey) {
		this.wcUserKey = wcUserKey;
	}

}
