/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.mc.model;

import java.sql.Clob;
import java.util.Date;

/**
 *
 * @author ravindar.singh
 */
public class MMktgCampaignTemplate implements java.io.Serializable {

    private Long mctTemplateId;
    private Long mctCampId;
    private String mctName;
    private String mctType;
    private String mctSubject;
    private StringBuilder mctBody;
    private Clob mctCampBody;
    private String mctCrUid;
    private Date mctCrDt;
    private String mctUpdUid;
    private Date mctUpdDt;

    public MMktgCampaignTemplate() {
    }

    public Long getMctTemplateId() {
        return mctTemplateId;
    }

    public void setMctTemplateId(Long mctTemplateId) {
        this.mctTemplateId = mctTemplateId;
    }

    public Long getMctCampId() {
        return mctCampId;
    }

    public void setMctCampId(Long mctCampId) {
        this.mctCampId = mctCampId;
    }

    public String getMctName() {
        return mctName;
    }

    public void setMctName(String mctName) {
        this.mctName = mctName;
    }

    public String getMctType() {
        return mctType;
    }

    public void setMctType(String mctType) {
        this.mctType = mctType;
    }

    public String getMctSubject() {
        return mctSubject;
    }

    public void setMctSubject(String mctSubject) {
        this.mctSubject = mctSubject;
    }

    public StringBuilder getMctBody() {
        return mctBody;
    }

    public void setMctBody(StringBuilder mctBody) {
        this.mctBody = mctBody;
    }

    public Clob getMctCampBody() {
        return mctCampBody;
    }

    public void setMctCampBody(Clob mctCampBody) {
        this.mctCampBody = mctCampBody;
    }

    public String getMctCrUid() {
        return mctCrUid;
    }

    public void setMctCrUid(String mctCrUid) {
        this.mctCrUid = mctCrUid;
    }

    public Date getMctCrDt() {
        return mctCrDt;
    }

    public void setMctCrDt(Date mctCrDt) {
        this.mctCrDt = mctCrDt;
    }

    public String getMctUpdUid() {
        return mctUpdUid;
    }

    public void setMctUpdUid(String mctUpdUid) {
        this.mctUpdUid = mctUpdUid;
    }

    public Date getMctUpdDt() {
        return mctUpdDt;
    }

    public void setMctUpdDt(Date mctUpdDt) {
        this.mctUpdDt = mctUpdDt;
    }

}
