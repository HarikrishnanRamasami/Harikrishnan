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
public class MMktgUnsubscribeList implements java.io.Serializable {

    private Long mulId;
    private String mulContactId;
    private Long mulCampId;
    private Long mulTemplateId;
    private String mulReasonType;
    private String mulReason;
    private Date mulDate;

    public MMktgUnsubscribeList() {
    }

    public Long getMulId() {
        return mulId;
    }

    public void setMulId(Long mulId) {
        this.mulId = mulId;
    }

    public String getMulContactId() {
        return mulContactId;
    }

    public void setMulContactId(String mulContactId) {
        this.mulContactId = mulContactId;
    }

    public Long getMulCampId() {
        return mulCampId;
    }

    public void setMulCampId(Long mulCampId) {
        this.mulCampId = mulCampId;
    }

    public Long getMulTemplateId() {
        return mulTemplateId;
    }

    public void setMulTemplateId(Long mulTemplateId) {
        this.mulTemplateId = mulTemplateId;
    }

    public String getMulReasonType() {
        return mulReasonType;
    }

    public void setMulReasonType(String mulReasonType) {
        this.mulReasonType = mulReasonType;
    }

    public String getMulReason() {
        return mulReason;
    }

    public void setMulReason(String mulReason) {
        this.mulReason = mulReason;
    }

    public Date getMulDate() {
        return mulDate;
    }

    public void setMulDate(Date mulDate) {
        this.mulDate = mulDate;
    }

}
