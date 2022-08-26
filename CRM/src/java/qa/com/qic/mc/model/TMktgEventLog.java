/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ravindar.singh
 */
public class TMktgEventLog implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal melDataId;
    private String melType;
    private String melTo;
    private String melCc;
    private String melBcc;
    private String melFm;
    private String melReplyTo;
    private String melSubject;
    private String melBody;
    private String melUnicode;
    private Date melCrDt;
    private String melStatus;
    private Date melSentDt;

    // DTO
    private Long melKey;
    private String melSentYn;

    public BigDecimal getMelDataId() {
        return melDataId;
    }

    public void setMelDataId(BigDecimal melDataId) {
        this.melDataId = melDataId;
    }

    public String getMelType() {
        return melType;
    }

    public void setMelType(String melType) {
        this.melType = melType;
    }

    public String getMelTo() {
        return melTo;
    }

    public void setMelTo(String melTo) {
        this.melTo = melTo;
    }

    public String getMelCc() {
        return melCc;
    }

    public void setMelCc(String melCc) {
        this.melCc = melCc;
    }

    public String getMelBcc() {
        return melBcc;
    }

    public void setMelBcc(String melBcc) {
        this.melBcc = melBcc;
    }

    public String getMelFm() {
        return melFm;
    }

    public void setMelFm(String melFm) {
        this.melFm = melFm;
    }

    public String getMelReplyTo() {
        return melReplyTo;
    }

    public void setMelReplyTo(String melReplyTo) {
        this.melReplyTo = melReplyTo;
    }

    public String getMelSubject() {
        return melSubject;
    }

    public void setMelSubject(String melSubject) {
        this.melSubject = melSubject;
    }

    public String getMelBody() {
        return melBody;
    }

    public void setMelBody(String melBody) {
        this.melBody = melBody;
    }

    public String getMelUnicode() {
        return melUnicode;
    }

    public void setMelUnicode(String melUnicode) {
        this.melUnicode = melUnicode;
    }

    public Date getMelCrDt() {
        return melCrDt;
    }

    public void setMelCrDt(Date melCrDt) {
        this.melCrDt = melCrDt;
    }

    public String getMelStatus() {
        return melStatus;
    }

    public void setMelStatus(String melStatus) {
        this.melStatus = melStatus;
    }

    public Date getMelSentDt() {
        return melSentDt;
    }

    public void setMelSentDt(Date melSentDt) {
        this.melSentDt = melSentDt;
    }

    public Long getMelKey() {
        return melKey;
    }

    public void setMelKey(Long melKey) {
        this.melKey = melKey;
    }

    public String getMelSentYn() {
        return melSentYn;
    }

    public void setMelSentYn(String melSentYn) {
        this.melSentYn = melSentYn;
    }

}
