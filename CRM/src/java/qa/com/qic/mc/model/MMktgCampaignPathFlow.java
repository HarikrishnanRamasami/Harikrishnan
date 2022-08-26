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
public class MMktgCampaignPathFlow implements java.io.Serializable {

    private Long mcpfPathId;
    private Short mcpfFlowNo;
    private String mcpfAction;
    private String mcpfSplitYn;
    private Byte mcpfSplitPerc;
    private Long mcpfTemplateId;
    private Long mcpfTemplateId2;
    private String mcpfUnicode;
    private String mcpfText;
    private String mcpfWaitFreq;
    private String mcpfClickUrlKey;
    private String mcpfFlex01;
    private String mcpfFlex02;
    private String mcpfFlex03;
    private String mcpfFlex04;
    private String mcpfFlex05;
    private String mcpfCrUid;
    private Date mcpfCrDt;
    private String mcpfUpdUid;
    private Date mcpfUpdDt;

    // DTO
    private String mcpfTemplateName;
    private String mcpfTemplate2Name;
    private String mcpfDelYn;
    private String mcpfCount;

    public MMktgCampaignPathFlow() {
    }

    public Long getMcpfPathId() {
        return mcpfPathId;
    }

    public void setMcpfPathId(Long mcpfPathId) {
        this.mcpfPathId = mcpfPathId;
    }

    public Short getMcpfFlowNo() {
        return mcpfFlowNo;
    }

    public void setMcpfFlowNo(Short mcpfFlowNo) {
        this.mcpfFlowNo = mcpfFlowNo;
    }

    public String getMcpfAction() {
        return mcpfAction;
    }

    public void setMcpfAction(String mcpfAction) {
        this.mcpfAction = mcpfAction;
    }

    public String getMcpfSplitYn() {
        return mcpfSplitYn;
    }

    public void setMcpfSplitYn(String mcpfSplitYn) {
        this.mcpfSplitYn = mcpfSplitYn;
    }

    public Byte getMcpfSplitPerc() {
        return mcpfSplitPerc;
    }

    public void setMcpfSplitPerc(Byte mcpfSplitPerc) {
        this.mcpfSplitPerc = mcpfSplitPerc;
    }

    public Long getMcpfTemplateId() {
        return mcpfTemplateId;
    }

    public void setMcpfTemplateId(Long mcpfTemplateId) {
        this.mcpfTemplateId = mcpfTemplateId;
    }

    public Long getMcpfTemplateId2() {
        return mcpfTemplateId2;
    }

    public void setMcpfTemplateId2(Long mcpfTemplateId2) {
        this.mcpfTemplateId2 = mcpfTemplateId2;
    }

    public String getMcpfUnicode() {
        return mcpfUnicode;
    }

    public void setMcpfUnicode(String mcpfUnicode) {
        this.mcpfUnicode = mcpfUnicode;
    }

    public String getMcpfText() {
        return mcpfText;
    }

    public void setMcpfText(String mcpfText) {
        this.mcpfText = mcpfText;
    }

    public String getMcpfWaitFreq() {
        return mcpfWaitFreq;
    }

    public void setMcpfWaitFreq(String mcpfWaitFreq) {
        this.mcpfWaitFreq = mcpfWaitFreq;
    }

    public String getMcpfClickUrlKey() {
        return mcpfClickUrlKey;
    }

    public void setMcpfClickUrlKey(String mcpfClickUrlKey) {
        this.mcpfClickUrlKey = mcpfClickUrlKey;
    }

    public String getMcpfFlex01() {
        return mcpfFlex01;
    }

    public void setMcpfFlex01(String mcpfFlex01) {
        this.mcpfFlex01 = mcpfFlex01;
    }

    public String getMcpfFlex02() {
        return mcpfFlex02;
    }

    public void setMcpfFlex02(String mcpfFlex02) {
        this.mcpfFlex02 = mcpfFlex02;
    }

    public String getMcpfFlex03() {
        return mcpfFlex03;
    }

    public void setMcpfFlex03(String mcpfFlex03) {
        this.mcpfFlex03 = mcpfFlex03;
    }

    public String getMcpfFlex04() {
        return mcpfFlex04;
    }

    public void setMcpfFlex04(String mcpfFlex04) {
        this.mcpfFlex04 = mcpfFlex04;
    }

    public String getMcpfFlex05() {
        return mcpfFlex05;
    }

    public void setMcpfFlex05(String mcpfFlex05) {
        this.mcpfFlex05 = mcpfFlex05;
    }

    public String getMcpfCrUid() {
        return mcpfCrUid;
    }

    public void setMcpfCrUid(String mcpfCrUid) {
        this.mcpfCrUid = mcpfCrUid;
    }

    public Date getMcpfCrDt() {
        return mcpfCrDt;
    }

    public void setMcpfCrDt(Date mcpfCrDt) {
        this.mcpfCrDt = mcpfCrDt;
    }

    public String getMcpfUpdUid() {
        return mcpfUpdUid;
    }

    public void setMcpfUpdUid(String mcpfUpdUid) {
        this.mcpfUpdUid = mcpfUpdUid;
    }

    public Date getMcpfUpdDt() {
        return mcpfUpdDt;
    }

    public void setMcpfUpdDt(Date mcpfUpdDt) {
        this.mcpfUpdDt = mcpfUpdDt;
    }

    public String getMcpfTemplateName() {
        return mcpfTemplateName;
    }

    public void setMcpfTemplateName(String mcpfTemplateName) {
        this.mcpfTemplateName = mcpfTemplateName;
    }

    public String getMcpfTemplate2Name() {
        return mcpfTemplate2Name;
    }

    public void setMcpfTemplate2Name(String mcpfTemplate2Name) {
        this.mcpfTemplate2Name = mcpfTemplate2Name;
    }

    public String getMcpfDelYn() {
        return mcpfDelYn;
    }

    public void setMcpfDelYn(String mcpfDelYn) {
        this.mcpfDelYn = mcpfDelYn;
    }

    public String getMcpfCount() {
        return mcpfCount;
    }

    public void setMcpfCount(String mcpfCount) {
        this.mcpfCount = mcpfCount;
    }
}
