/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import qa.com.qic.anoud.vo.KeyValue;

/**
 *
 * @author ravindar.singh
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TWhatsappLog implements java.io.Serializable {

    private Long wlLogId;
    private Long wlParentLogId;
    private Date wlLogDate;
    private String wlMobileNo;
    private String wlCrmId;
    private String wlMsgMode;
    private String wlMsgType;
    private String wlMsgId;
    private String wlText;
    private String wlMsgUrl;
    private String wlFilePath;
    private String wlTemplateId;
    private String wlDeliverdYn;
    private Date wlDeliverdDt;
    private String wlReadYn;
    private Date wlReadDt;
    private String wlErrorId;
    private String wlErrorMsg;
    private String wlCrUid;
    private String wlViewUid;
    private Date wlViewDt;
    private String wlFlex01;
    private String wlFlex02;
    private String wlFlex03;
    private String wlFlex04;
    private String wlFlex05;

    private List<KeyValue> templateData;
    private String wlFileExt;

    public TWhatsappLog() {
    }

    public TWhatsappLog(Long wlLogId) {
        this.wlLogId = wlLogId;
    }

    public TWhatsappLog(Long wlLogId, Date wlLogDate, String wlMobileNo, String wlMsgMode, String wlMsgType, String wlText, String wlMsgUrl, String wlTemplateId, String wlDeliverdYn, Date wlDeliverdDt, String wlReadYn, Date wlReadDt, String wlErrorId, String wlErrorMsg, String wlCrUid, String wlViewUid, Date wlViewDt, String wlFlex01, String wlFlex02, String wlFlex03, String wlFlex04, String wlFlex05) {
        this.wlLogId = wlLogId;
        this.wlLogDate = wlLogDate;
        this.wlMobileNo = wlMobileNo;
        this.wlMsgMode = wlMsgMode;
        this.wlMsgType = wlMsgType;
        this.wlText = wlText;
        this.wlMsgUrl = wlMsgUrl;
        this.wlTemplateId = wlTemplateId;
        this.wlDeliverdYn = wlDeliverdYn;
        this.wlDeliverdDt = wlDeliverdDt;
        this.wlReadYn = wlReadYn;
        this.wlReadDt = wlReadDt;
        this.wlErrorId = wlErrorId;
        this.wlErrorMsg = wlErrorMsg;
        this.wlCrUid = wlCrUid;
        this.wlViewUid = wlViewUid;
        this.wlViewDt = wlViewDt;
        this.wlFlex01 = wlFlex01;
        this.wlFlex02 = wlFlex02;
        this.wlFlex03 = wlFlex03;
        this.wlFlex04 = wlFlex04;
        this.wlFlex05 = wlFlex05;
    }

    public Long getWlLogId() {
        return this.wlLogId;
    }

    public void setWlLogId(Long wlLogId) {
        this.wlLogId = wlLogId;
    }

    public Long getWlParentLogId() {
        return wlParentLogId;
    }

    public void setWlParentLogId(Long wlParentLogId) {
        this.wlParentLogId = wlParentLogId;
    }

    public Date getWlLogDate() {
        return this.wlLogDate;
    }

    public void setWlLogDate(Date wlLogDate) {
        this.wlLogDate = wlLogDate;
    }

    public String getWlMobileNo() {
        return this.wlMobileNo;
    }

    public void setWlMobileNo(String wlMobileNo) {
        this.wlMobileNo = wlMobileNo;
    }

    public String getWlCrmId() {
        return wlCrmId;
    }

    public void setWlCrmId(String wlCrmId) {
        this.wlCrmId = wlCrmId;
    }

    public String getWlMsgMode() {
        return this.wlMsgMode;
    }

    public void setWlMsgMode(String wlMsgMode) {
        this.wlMsgMode = wlMsgMode;
    }

    public String getWlMsgType() {
        return this.wlMsgType;
    }

    public void setWlMsgType(String wlMsgType) {
        this.wlMsgType = wlMsgType;
    }

    public String getWlMsgId() {
        return wlMsgId;
    }

    public void setWlMsgId(String wlMsgId) {
        this.wlMsgId = wlMsgId;
    }

    public String getWlText() {
        return this.wlText;
    }

    public void setWlText(String wlText) {
        this.wlText = wlText;
    }

    public String getWlMsgUrl() {
        return this.wlMsgUrl;
    }

    public void setWlMsgUrl(String wlMsgUrl) {
        this.wlMsgUrl = wlMsgUrl;
    }

    public String getWlFilePath() {
        return wlFilePath;
    }

    public void setWlFilePath(String wlMsgPath) {
        this.wlFilePath = wlMsgPath;
    }

    public String getWlTemplateId() {
        return this.wlTemplateId;
    }

    public void setWlTemplateId(String wlTemplateId) {
        this.wlTemplateId = wlTemplateId;
    }

    public String getWlDeliverdYn() {
        return this.wlDeliverdYn;
    }

    public void setWlDeliverdYn(String wlDeliverdYn) {
        this.wlDeliverdYn = wlDeliverdYn;
    }

    public Date getWlDeliverdDt() {
        return this.wlDeliverdDt;
    }

    public void setWlDeliverdDt(Date wlDeliverdDt) {
        this.wlDeliverdDt = wlDeliverdDt;
    }

    public String getWlReadYn() {
        return this.wlReadYn;
    }

    public void setWlReadYn(String wlReadYn) {
        this.wlReadYn = wlReadYn;
    }

    public Date getWlReadDt() {
        return this.wlReadDt;
    }

    public void setWlReadDt(Date wlReadDt) {
        this.wlReadDt = wlReadDt;
    }

    public String getWlErrorId() {
        return this.wlErrorId;
    }

    public void setWlErrorId(String wlErrorId) {
        this.wlErrorId = wlErrorId;
    }

    public String getWlErrorMsg() {
        return this.wlErrorMsg;
    }

    public void setWlErrorMsg(String wlErrorMsg) {
        this.wlErrorMsg = wlErrorMsg;
    }

    public String getWlCrUid() {
        return this.wlCrUid;
    }

    public void setWlCrUid(String wlCrUid) {
        this.wlCrUid = wlCrUid;
    }

    public String getWlViewUid() {
        return this.wlViewUid;
    }

    public void setWlViewUid(String wlViewUid) {
        this.wlViewUid = wlViewUid;
    }

    public Date getWlViewDt() {
        return this.wlViewDt;
    }

    public void setWlViewDt(Date wlViewDt) {
        this.wlViewDt = wlViewDt;
    }

    public String getWlFlex01() {
        return this.wlFlex01;
    }

    public void setWlFlex01(String wlFlex01) {
        this.wlFlex01 = wlFlex01;
    }

    public String getWlFlex02() {
        return this.wlFlex02;
    }

    public void setWlFlex02(String wlFlex02) {
        this.wlFlex02 = wlFlex02;
    }

    public String getWlFlex03() {
        return this.wlFlex03;
    }

    public void setWlFlex03(String wlFlex03) {
        this.wlFlex03 = wlFlex03;
    }

    public String getWlFlex04() {
        return this.wlFlex04;
    }

    public void setWlFlex04(String wlFlex04) {
        this.wlFlex04 = wlFlex04;
    }

    public String getWlFlex05() {
        return this.wlFlex05;
    }

    public void setWlFlex05(String wlFlex05) {
        this.wlFlex05 = wlFlex05;
    }

    public String getType() {
        return "POST";
    }

    public List<KeyValue> getTemplateData() {
        return templateData;
    }

    public void setTemplateData(List<KeyValue> templateData) {
        this.templateData = templateData;
    }

    public String getWlFileExt() {
        return wlFileExt;
    }

    public void setWlFileExt(String wlFileExt) {
        this.wlFileExt = wlFileExt;
    }

}
