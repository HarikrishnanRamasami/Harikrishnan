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
 * @author ravindar.singh
 */
public class MWhatsappAutoMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer wamId;
    private String wamName;
    private String wamCrmId;
    private Short wamSrNo;
    private Date wamEffFromDate;
    private Date wamEffToDate;
    private String wamFromTime;
    private String wamToTime;
    private String wamRepeatYn;
    private String wamDay1Yn;
    private String wamDay2Yn;
    private String wamDay3Yn;
    private String wamDay4Yn;
    private String wamDay5Yn;
    private String wamDay6Yn;
    private String wamDay7Yn;
    private String wamMessage;
    private String wamMessageImg;
    private String wamCrUid;
    private Date wamCrDt;
    private String wamUpdUid;
    private Date wamUpdDt;
    private Short wamToTimeOn;
    private Short wamFmTimeOn;

    public MWhatsappAutoMessage() {
    }

    public MWhatsappAutoMessage(Integer wamId) {
        this.wamId = wamId;
    }

    public Integer getWamId() {
        return wamId;
    }

    public void setWamId(Integer wamId) {
        this.wamId = wamId;
    }

    public String getWamName() {
        return wamName;
    }

    public void setWamName(String wamName) {
        this.wamName = wamName;
    }

    public String getWamCrmId() {
        return wamCrmId;
    }

    public void setWamCrmId(String wamCrmId) {
        this.wamCrmId = wamCrmId;
    }

    public Short getWamSrNo() {
        return wamSrNo;
    }

    public void setWamSrNo(Short wamSrNo) {
        this.wamSrNo = wamSrNo;
    }

    public Date getWamEffFromDate() {
        return wamEffFromDate;
    }

    public void setWamEffFromDate(Date wamEffFromDate) {
        this.wamEffFromDate = wamEffFromDate;
    }

    public Date getWamEffToDate() {
        return wamEffToDate;
    }

    public void setWamEffToDate(Date wamEffToDate) {
        this.wamEffToDate = wamEffToDate;
    }

    public String getWamFromTime() {
        return wamFromTime;
    }

    public void setWamFromTime(String wamFromTime) {
        this.wamFromTime = wamFromTime;
    }

    public String getWamToTime() {
        return wamToTime;
    }

    public void setWamToTime(String wamToTime) {
        this.wamToTime = wamToTime;
    }

    public String getWamRepeatYn() {
        return wamRepeatYn;
    }

    public void setWamRepeatYn(String wamRepeatYn) {
        this.wamRepeatYn = wamRepeatYn;
    }

    public String getWamDay1Yn() {
        return wamDay1Yn;
    }

    public void setWamDay1Yn(String wamDay1Yn) {
        this.wamDay1Yn = wamDay1Yn;
    }

    public String getWamDay2Yn() {
        return wamDay2Yn;
    }

    public void setWamDay2Yn(String wamDay2Yn) {
        this.wamDay2Yn = wamDay2Yn;
    }

    public String getWamDay3Yn() {
        return wamDay3Yn;
    }

    public void setWamDay3Yn(String wamDay3Yn) {
        this.wamDay3Yn = wamDay3Yn;
    }

    public String getWamDay4Yn() {
        return wamDay4Yn;
    }

    public void setWamDay4Yn(String wamDay4Yn) {
        this.wamDay4Yn = wamDay4Yn;
    }

    public String getWamDay5Yn() {
        return wamDay5Yn;
    }

    public void setWamDay5Yn(String wamDay5Yn) {
        this.wamDay5Yn = wamDay5Yn;
    }

    public String getWamDay6Yn() {
        return wamDay6Yn;
    }

    public void setWamDay6Yn(String wamDay6Yn) {
        this.wamDay6Yn = wamDay6Yn;
    }

    public String getWamDay7Yn() {
        return wamDay7Yn;
    }

    public void setWamDay7Yn(String wamDay7Yn) {
        this.wamDay7Yn = wamDay7Yn;
    }

    public String getWamMessage() {
        return wamMessage;
    }

    public void setWamMessage(String wamMessage) {
        this.wamMessage = wamMessage;
    }

    public String getWamMessageImg() {
        return wamMessageImg;
    }

    public void setWamMessageImg(String wamMessageImg) {
        this.wamMessageImg = wamMessageImg;
    }

    public String getWamCrUid() {
        return wamCrUid;
    }

    public void setWamCrUid(String wamCrUid) {
        this.wamCrUid = wamCrUid;
    }

    public Date getWamCrDt() {
        return wamCrDt;
    }

    public void setWamCrDt(Date wamCrDt) {
        this.wamCrDt = wamCrDt;
    }

    public String getWamUpdUid() {
        return wamUpdUid;
    }

    public void setWamUpdUid(String wamUpdUid) {
        this.wamUpdUid = wamUpdUid;
    }

    public Date getWamUpdDt() {
        return wamUpdDt;
    }

    public void setWamUpdDt(Date wamUpdDt) {
        this.wamUpdDt = wamUpdDt;
    }

    public Short getWamToTimeOn() {
        return wamToTimeOn;
    }

    public void setWamToTimeOn(Short wamToTimeOn) {
        this.wamToTimeOn = wamToTimeOn;
    }

    public Short getWamFmTimeOn() {
        return wamFmTimeOn;
    }

    public void setWamFmTimeOn(Short wamFmTimeOn) {
        this.wamFmTimeOn = wamFmTimeOn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wamId != null ? wamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MWhatsappAutoMessage)) {
            return false;
        }
        MWhatsappAutoMessage other = (MWhatsappAutoMessage) object;
        if ((this.wamId == null && other.wamId != null) || (this.wamId != null && !this.wamId.equals(other.wamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "qa.com.crm.model.MWhatsappAutoMessage[ wamId=" + wamId + " ]";
    }

}
