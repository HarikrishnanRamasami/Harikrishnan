/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.vo;

import java.io.Serializable;
import java.util.Date;

import qa.com.qic.common.util.TypeConstants.DateRange;

/**
 *
 * @author karthik.j
 */
public class CrmDeal implements Serializable {

	private static final long serialVersionUID = 1L;

    private transient String operation;
    private transient boolean showClosed;

	private Long cdPlId;
	private transient String cdPipelineName;
	private Long cdDealId;
	private transient String cdDealName;
	private Long cdStageNo;
	private transient String cdStageName;
	private Long cdCustCode;
	private transient String cdCustName;
	private transient String cdCustEmail;
	private transient String cdProdCode;
	private transient String cdProdName;
	private transient String cdProdLob;
    private transient String cdContactType;
    private transient String cdContactName;
    private transient Long cdContactNo;
    private transient String cdContactEmail;
    private transient String cdContTypDesc;
	private transient String cdEnqNo;
	private transient String cdQuotNo;
	private transient String cdPolicyNo;
	private transient String cdQuotDeclRes;
	private transient String cdQuotDeclResDesc;
	private transient String cdDealVal;
	private transient String cdDealStatus;
	private transient String cdDealClDt;
    private transient String cdCrUid;
    private transient String cdCrUidDesc;
    private transient Date cdCrDt;
    private transient String cdUpdUid;
    private transient Date cdUpDt;
    private transient String cdCrmId;
    private transient String cdCrmDesc;
    private transient String cdCrmCssClass;
    private transient String cdCustFld1;
    private transient String cdCustFld2;
    private transient String cdCustFld3;
    private transient String cdCustFld4;
    private transient String cdCustFld5;
    private transient String cdCustFld6;
    private transient String cdCustFld7;
    private transient String cdCustFld8;
    private transient String cdCustFld9;
    private transient String cdCustFld10;
	private Long cdDealAge;
	private Long cdActivityCount;
    private transient String cdChartType;
    private transient DateRange cdDateRange;




    public boolean isShowClosed() {
		return showClosed;
	}
	public void setShowClosed(boolean showClosed) {
		this.showClosed = showClosed;
	}
	public Long getCdPlId() {
		return cdPlId;
	}
	public void setCdPlId(Long cdPlId) {
		this.cdPlId = cdPlId;
	}
	public String getCdPipelineName() {
		return cdPipelineName;
	}
	public void setCdPipelineName(String cdPipelineName) {
		this.cdPipelineName = cdPipelineName;
	}
	public Long getCdDealId() {
		return cdDealId;
	}
	public void setCdDealId(Long cdDealId) {
		this.cdDealId = cdDealId;
	}
	public Long getCdStageNo() {
		return cdStageNo;
	}
	public void setCdStageNo(Long cdStageNo) {
		this.cdStageNo = cdStageNo;
	}
	public String getCdStageName() {
		return cdStageName;
	}
	public void setCdStageName(String cdStageName) {
		this.cdStageName = cdStageName;
	}

	public Long getCdCustCode() {
		return cdCustCode;
	}
	public void setCdCustCode(Long cdCustCode) {
		this.cdCustCode = cdCustCode;
	}
	public String getCdCustName() {
		return cdCustName;
	}
	public String getCdCustEmail() {
		return cdCustEmail;
	}
	public void setCdCustEmail(String cdCustEmail) {
		this.cdCustEmail = cdCustEmail;
	}
	public String getCdProdCode() {
		return cdProdCode;
	}
	public void setCdProdCode(String cdProdCode) {
		this.cdProdCode = cdProdCode;
	}
	public String getCdProdName() {
		return cdProdName;
	}
	public void setCdProdName(String cdProdName) {
		this.cdProdName = cdProdName;
	}
	public String getCdProdLob() {
		return cdProdLob;
	}
	public void setCdProdLob(String cdProdLob) {
		this.cdProdLob = cdProdLob;
	}
	public void setCdCustName(String cdCustName) {
		this.cdCustName = cdCustName;
	}
	public String getCdContactType() {
		return cdContactType;
	}
	public void setCdContactType(String cdContactType) {
		this.cdContactType = cdContactType;
	}
	public String getCdContactName() {
		return cdContactName;
	}
	public void setCdContactName(String cdContactName) {
		this.cdContactName = cdContactName;
	}
	public Long getCdContactNo() {
		return cdContactNo;
	}
	public void setCdContactNo(Long cdContactNo) {
		this.cdContactNo = cdContactNo;
	}
	public String getCdContactEmail() {
		return cdContactEmail;
	}
	public void setCdContactEmail(String cdContactEmail) {
		this.cdContactEmail = cdContactEmail;
	}
	public String getCdContTypDesc() {
		return cdContTypDesc;
	}
	public void setCdContTypDesc(String cdContTypDesc) {
		this.cdContTypDesc = cdContTypDesc;
	}
	public String getCdDealName() {
		return cdDealName;
	}
	public void setCdDealName(String cdDealName) {
		this.cdDealName = cdDealName;
	}
	public String getCdEnqNo() {
		return cdEnqNo;
	}
	public void setCdEnqNo(String cdEnqNo) {
		this.cdEnqNo = cdEnqNo;
	}
	public String getCdQuotNo() {
		return cdQuotNo;
	}
	public void setCdQuotNo(String cdQuotNo) {
		this.cdQuotNo = cdQuotNo;
	}
	public String getCdPolicyNo() {
		return cdPolicyNo;
	}
	public void setCdPolicyNo(String cdPolicyNo) {
		this.cdPolicyNo = cdPolicyNo;
	}
	public String getCdQuotDeclRes() {
		return cdQuotDeclRes;
	}
	public void setCdQuotDeclRes(String cdQuotDeclRes) {
		this.cdQuotDeclRes = cdQuotDeclRes;
	}
	public String getCdQuotDeclResDesc() {
		return cdQuotDeclResDesc;
	}
	public void setCdQuotDeclResDesc(String cdQuotDeclResDesc) {
		this.cdQuotDeclResDesc = cdQuotDeclResDesc;
	}
	public String getCdDealVal() {
		return cdDealVal;
	}
	public void setCdDealVal(String cdDealVal) {
		this.cdDealVal = cdDealVal;
	}
	public String getCdDealStatus() {
		return cdDealStatus;
	}
	public void setCdDealStatus(String cdDealStatus) {
		this.cdDealStatus = cdDealStatus;
	}
	public String getCdDealClDt() {
		return cdDealClDt;
	}
	public void setCdDealClDt(String cdDealClDt) {
		this.cdDealClDt = cdDealClDt;
	}
	public String getCdCrUid() {
		return cdCrUid;
	}
	public void setCdCrUid(String cdCrUid) {
		this.cdCrUid = cdCrUid;
	}
	public String getCdCrUidDesc() {
		return cdCrUidDesc;
	}
	public void setCdCrUidDesc(String cdCrUidDesc) {
		this.cdCrUidDesc = cdCrUidDesc;
	}
	public Date getCdCrDt() {
		return cdCrDt;
	}
	public void setCdCrDt(Date cdCrDt) {
		this.cdCrDt = cdCrDt;
	}
	public String getCdUpdUid() {
		return cdUpdUid;
	}
	public void setCdUpdUid(String cdUpdUid) {
		this.cdUpdUid = cdUpdUid;
	}
	public Date getCdUpDt() {
		return cdUpDt;
	}
	public void setCdUpDt(Date cdUpDt) {
		this.cdUpDt = cdUpDt;
	}
	public String getCdCrmId() {
		return cdCrmId;
	}
	public void setCdCrmId(String cdCrmId) {
		this.cdCrmId = cdCrmId;
	}
	public String getCdCrmDesc() {
		return cdCrmDesc;
	}
	public void setCdCrmDesc(String cdCrmDesc) {
		this.cdCrmDesc = cdCrmDesc;
	}
	public String getCdCrmCssClass() {
		return cdCrmCssClass;
	}
	public void setCdCrmCssClass(String cdCrmCssClass) {
		this.cdCrmCssClass = cdCrmCssClass;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getCdCustFld1() {
		return cdCustFld1;
	}
	public void setCdCustFld1(String cdCustFld1) {
		this.cdCustFld1 = cdCustFld1;
	}
	public String getCdCustFld2() {
		return cdCustFld2;
	}
	public void setCdCustFld2(String cdCustFld2) {
		this.cdCustFld2 = cdCustFld2;
	}
	public String getCdCustFld3() {
		return cdCustFld3;
	}
	public void setCdCustFld3(String cdCustFld3) {
		this.cdCustFld3 = cdCustFld3;
	}
	public String getCdCustFld4() {
		return cdCustFld4;
	}
	public void setCdCustFld4(String cdCustFld4) {
		this.cdCustFld4 = cdCustFld4;
	}
	public String getCdCustFld5() {
		return cdCustFld5;
	}
	public void setCdCustFld5(String cdCustFld5) {
		this.cdCustFld5 = cdCustFld5;
	}
	public String getCdCustFld6() {
		return cdCustFld6;
	}
	public void setCdCustFld6(String cdCustFld6) {
		this.cdCustFld6 = cdCustFld6;
	}
	public String getCdCustFld7() {
		return cdCustFld7;
	}
	public void setCdCustFld7(String cdCustFld7) {
		this.cdCustFld7 = cdCustFld7;
	}
	public String getCdCustFld8() {
		return cdCustFld8;
	}
	public void setCdCustFld8(String cdCustFld8) {
		this.cdCustFld8 = cdCustFld8;
	}
	public String getCdCustFld9() {
		return cdCustFld9;
	}
	public void setCdCustFld9(String cdCustFld9) {
		this.cdCustFld9 = cdCustFld9;
	}
	public String getCdCustFld10() {
		return cdCustFld10;
	}
	public void setCdCustFld10(String cdCustFld10) {
		this.cdCustFld10 = cdCustFld10;
	}
	public Long getCdDealAge() {
		return cdDealAge;
	}
	public void setCdDealAge(Long cdDealAge) {
		this.cdDealAge = cdDealAge;
	}
	public Long getCdActivityCount() {
		return cdActivityCount;
	}
	public void setCdActivityCount(Long cdActivityCount) {
		this.cdActivityCount = cdActivityCount;
	}
	public String getCdChartType() {
		return cdChartType;
	}
	public void setCdChartType(String cdChartType) {
		this.cdChartType = cdChartType;
	}
	public DateRange getCdDateRange() {
		return cdDateRange;
	}
	public void setCdDateRange(DateRange cdDateRange) {
		this.cdDateRange = cdDateRange;
	}




}
