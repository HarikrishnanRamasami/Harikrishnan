/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ravindar.singh
 */
public class TWhatsappTxn implements java.io.Serializable {

    private Long wtTxnId;
    private String wtMobileNo;
    private String wtCrmId;
    private String wtName;
    private Date wtDate;
    private Short wtMsgCount;
    private String wtAssignedTo;
    private Date wtAssignedDt;
    private String wtCloseUid;
    private Date wtCloseDt;
    private String wtCloseCode;
    private String wtCloseRemarks;
    private String wtSaleRefNo;
    private BigDecimal wtSaleValue;
    private Date wtSaleDt;
    private String wtFlex01;
    private String wtFlex02;
    private String wtFlex03;
    private String wtFlex04;
    private String wtFlex05;
    private Date wtUpdDt;
    private String wtAssignedToDesc;

    // DTO
    private String wtCrmDesc;

    public TWhatsappTxn() {
    }

    public TWhatsappTxn(Long wtTxnId) {
        this.wtTxnId = wtTxnId;
    }

    public TWhatsappTxn(Long wtTxnId, String wtMobileNo, String wtName, Date wtDate, Short wtMsgCount, String wtAssignedTo, Date wtAssignedDt, String wtCloseUid, Date wtCloseDt, String wtCloseCode, String wtCloseRemarks, String wtSaleRefNo, BigDecimal wtSaleValue, Date wtSaleDt, String wtFlex01, String wtFlex02, String wtFlex03, String wtFlex04, String wtFlex05) {
        this.wtTxnId = wtTxnId;
        this.wtMobileNo = wtMobileNo;
        this.wtName = wtName;
        this.wtDate = wtDate;
        this.wtMsgCount = wtMsgCount;
        this.wtAssignedTo = wtAssignedTo;
        this.wtAssignedDt = wtAssignedDt;
        this.wtCloseUid = wtCloseUid;
        this.wtCloseDt = wtCloseDt;
        this.wtCloseCode = wtCloseCode;
        this.wtCloseRemarks = wtCloseRemarks;
        this.wtSaleRefNo = wtSaleRefNo;
        this.wtSaleValue = wtSaleValue;
        this.wtSaleDt = wtSaleDt;
        this.wtFlex01 = wtFlex01;
        this.wtFlex02 = wtFlex02;
        this.wtFlex03 = wtFlex03;
        this.wtFlex04 = wtFlex04;
        this.wtFlex05 = wtFlex05;
    }

    public Long getWtTxnId() {
        return this.wtTxnId;
    }

    public void setWtTxnId(Long wtTxnId) {
        this.wtTxnId = wtTxnId;
    }

    public String getWtMobileNo() {
        return this.wtMobileNo;
    }

    public void setWtMobileNo(String wtMobileNo) {
        this.wtMobileNo = wtMobileNo;
    }

    public String getWtCrmId() {
        return wtCrmId;
    }

    public void setWtCrmId(String wtCrmId) {
        this.wtCrmId = wtCrmId;
    }

    public String getWtName() {
        return this.wtName;
    }

    public void setWtName(String wtName) {
        this.wtName = wtName;
    }

    public Date getWtDate() {
        return this.wtDate;
    }

    public void setWtDate(Date wtDate) {
        this.wtDate = wtDate;
    }

    public Short getWtMsgCount() {
        return this.wtMsgCount;
    }

    public void setWtMsgCount(Short wtMsgCount) {
        this.wtMsgCount = wtMsgCount;
    }

    public String getWtAssignedTo() {
        return this.wtAssignedTo;
    }

    public void setWtAssignedTo(String wtAssignedTo) {
        this.wtAssignedTo = wtAssignedTo;
    }

    public Date getWtAssignedDt() {
        return this.wtAssignedDt;
    }

    public void setWtAssignedDt(Date wtAssignedDt) {
        this.wtAssignedDt = wtAssignedDt;
    }

    public String getWtCloseUid() {
        return this.wtCloseUid;
    }

    public void setWtCloseUid(String wtCloseUid) {
        this.wtCloseUid = wtCloseUid;
    }

    public Date getWtCloseDt() {
        return this.wtCloseDt;
    }

    public void setWtCloseDt(Date wtCloseDt) {
        this.wtCloseDt = wtCloseDt;
    }

    public String getWtCloseCode() {
        return this.wtCloseCode;
    }

    public void setWtCloseCode(String wtCloseCode) {
        this.wtCloseCode = wtCloseCode;
    }

    public String getWtCloseRemarks() {
        return this.wtCloseRemarks;
    }

    public void setWtCloseRemarks(String wtCloseRemarks) {
        this.wtCloseRemarks = wtCloseRemarks;
    }

    public String getWtSaleRefNo() {
        return this.wtSaleRefNo;
    }

    public void setWtSaleRefNo(String wtSaleRefNo) {
        this.wtSaleRefNo = wtSaleRefNo;
    }

    public BigDecimal getWtSaleValue() {
        return this.wtSaleValue;
    }

    public void setWtSaleValue(BigDecimal wtSaleValue) {
        this.wtSaleValue = wtSaleValue;
    }

    public Date getWtSaleDt() {
        return this.wtSaleDt;
    }

    public void setWtSaleDt(Date wtSaleDt) {
        this.wtSaleDt = wtSaleDt;
    }

    public String getWtFlex01() {
        return this.wtFlex01;
    }

    public void setWtFlex01(String wtFlex01) {
        this.wtFlex01 = wtFlex01;
    }

    public String getWtFlex02() {
        return this.wtFlex02;
    }

    public void setWtFlex02(String wtFlex02) {
        this.wtFlex02 = wtFlex02;
    }

    public String getWtFlex03() {
        return this.wtFlex03;
    }

    public void setWtFlex03(String wtFlex03) {
        this.wtFlex03 = wtFlex03;
    }

    public String getWtFlex04() {
        return this.wtFlex04;
    }

    public void setWtFlex04(String wtFlex04) {
        this.wtFlex04 = wtFlex04;
    }

    public String getWtFlex05() {
        return this.wtFlex05;
    }

    public void setWtFlex05(String wtFlex05) {
        this.wtFlex05 = wtFlex05;
    }

    public Date getWtUpdDt() {
        return wtUpdDt;
    }

    public void setWtUpdDt(Date wtUpdDt) {
        this.wtUpdDt = wtUpdDt;
    }

    public String getWtAssignedToDesc() {
        return wtAssignedToDesc;
    }

    public void setWtAssignedToDesc(String wtAssignedToDesc) {
        this.wtAssignedToDesc = wtAssignedToDesc;
    }

    public String getWtCrmDesc() {
        return wtCrmDesc;
    }

    public void setWtCrmDesc(String wtCrmDesc) {
        this.wtCrmDesc = wtCrmDesc;
    }

}
