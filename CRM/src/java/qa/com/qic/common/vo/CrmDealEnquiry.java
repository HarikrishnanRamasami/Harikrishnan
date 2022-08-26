
package qa.com.qic.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author karthik.j
 */
public class CrmDealEnquiry implements Serializable {

	private static final long serialVersionUID = 1L;


	private String  crmId;
	private Long  dealId;
    private Long enqNo;
    private Long enqDocNo;
    private String quoteNo;
    private Date quoteDate;
    private String quoteMode;
    private String quoteLOB;
    private String quoteType;
    private String quoteTypeDesc;
    private String quoteLOBDesc;
    private String custName;
    private String assrName;
    private String brkName;
	private String refNo;
    private String remarks;
    private Long transId;
    private Integer tranSrNo;
    private String companyCode;
    private String divisionCode;
	private String deptCode;
    private String quoteEnqRemarks;
    private String openPolicyYesNo;
    private String receivedBy;
	private String emailId;
	private String contactNo;
    private Date subDt;
    private Date expDt;
    private int expdays;
    private String facPool;
    private String handledBy;
    private String status;
    private String statusDesc;
    private String agentId;
    private Date statusUpdatedDate;
    private String createdBy;
    private Date createdDate;
    private String successResp;
    private String errorResp;


	public String getCrmId() {
		return crmId;
	}

	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}




	public Long getDealId() {
		return dealId;
	}

	public void setDealId(Long dealId) {
		this.dealId = dealId;
	}

	public Long getEnqNo() {
		return enqNo;
	}

	public void setEnqNo(Long enqNo) {
		this.enqNo = enqNo;
	}

	public Long getEnqDocNo() {
		return enqDocNo;
	}

	public void setEnqDocNo(Long enqDocNo) {
		this.enqDocNo = enqDocNo;
	}

	public String getQuoteNo() {
		return quoteNo;
	}

	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}
    public Integer getTranSrNo() {
		return tranSrNo;
	}

	public void setTranSrNo(Integer tranSrNo) {
		this.tranSrNo = tranSrNo;
	}

	public Date getQuoteDate() {
		return quoteDate;
	}

	public void setQuoteDate(Date quoteDate) {
		this.quoteDate = quoteDate;
	}

	public String getQuoteMode() {
		return quoteMode;
	}

	public void setQuoteMode(String quoteMode) {
		this.quoteMode = quoteMode;
	}

	public String getQuoteLOB() {
		return quoteLOB;
	}

	public void setQuoteLOB(String quoteLOB) {
		this.quoteLOB = quoteLOB;
	}

	public String getQuoteType() {
		return quoteType;
	}

	public void setQuoteType(String quoteType) {
		this.quoteType = quoteType;
	}

	public String getQuoteTypeDesc() {
		return quoteTypeDesc;
	}

	public void setQuoteTypeDesc(String quoteTypeDesc) {
		this.quoteTypeDesc = quoteTypeDesc;
	}

	public String getQuoteLOBDesc() {
		return quoteLOBDesc;
	}

	public void setQuoteLOBDesc(String quoteLOBDesc) {
		this.quoteLOBDesc = quoteLOBDesc;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getQuoteEnqRemarks() {
		return quoteEnqRemarks;
	}

	public void setQuoteEnqRemarks(String quoteEnqRemarks) {
		this.quoteEnqRemarks = quoteEnqRemarks;
	}

	public String getOpenPolicyYesNo() {
		return openPolicyYesNo;
	}

	public void setOpenPolicyYesNo(String openPolicyYesNo) {
		this.openPolicyYesNo = openPolicyYesNo;
	}


	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	   public String getCustName() {
			return custName;
		}

		public void setCustName(String custName) {
			this.custName = custName;
		}

		public String getAssrName() {
			return assrName;
		}

		public void setAssrName(String assrName) {
			this.assrName = assrName;
		}

		public String getBrkName() {
			return brkName;
		}

		public void setBrkName(String brkName) {
			this.brkName = brkName;
		}

		public String getRefNo() {
			return refNo;
		}

		public void setRefNo(String refNo) {
			this.refNo = refNo;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public String getEmailId() {
			return emailId;
		}

		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}

		public String getContactNo() {
			return contactNo;
		}

		public void setContactNo(String contactNo) {
			this.contactNo = contactNo;
		}

		public Date getSubDt() {
			return subDt;
		}

		public void setSubDt(Date subDt) {
			this.subDt = subDt;
		}

		public Date getExpDt() {
			return expDt;
		}

		public void setExpDt(Date expDt) {
			this.expDt = expDt;
		}

		public int getExpdays() {
			return expdays;
		}

		public void setExpdays(int expdays) {
			this.expdays = expdays;
		}

		public String getFacPool() {
			return facPool;
		}

		public void setFacPool(String facPool) {
			this.facPool = facPool;
		}

		public String getHandledBy() {
			return handledBy;
		}

		public void setHandledBy(String handledBy) {
			this.handledBy = handledBy;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getStatusDesc() {
			return statusDesc;
		}

		public void setStatusDesc(String statusDesc) {
			this.statusDesc = statusDesc;
		}

		public String getAgentId() {
			return agentId;
		}

		public void setAgentId(String agentId) {
			this.agentId = agentId;
		}

		public Date getStatusUpdatedDate() {
			return statusUpdatedDate;
		}

		public void setStatusUpdatedDate(Date statusUpdatedDate) {
			this.statusUpdatedDate = statusUpdatedDate;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public String getSuccessResp() {
			return successResp;
		}

		public void setSuccessResp(String successResp) {
			this.successResp = successResp;
		}

		public String getErrorResp() {
			return errorResp;
		}

		public void setErrorResp(String errorResp) {
			this.errorResp = errorResp;
		}




}
