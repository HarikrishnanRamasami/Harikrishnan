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
 * @author karthik.j
 */
public class CrmDealPipeline implements Serializable {

	private static final long serialVersionUID = 1L;


	private Long cdpPlId;
	private transient String cdpPlName;
	private Long cdpStageNo;
	private transient String cdpStageTyp;
	private Long cdpStageProb;
	private transient String cdpStageName;
    private transient String cdpCrUid;
    private transient String cdpCrUidDesc;
    private transient Date cdpCrDt;
	private transient String operation;
	private transient String cdpUpUid;
	private transient Date cdpUpDt;

	public Long getCdpPlId() {
		return cdpPlId;
	}
	public void setCdpPlId(Long cdpPlId) {
		this.cdpPlId = cdpPlId;
	}

	public String getCdpPlName() {
		return cdpPlName;
	}
	public void setCdpPlName(String cdpPlName) {
		this.cdpPlName = cdpPlName;
	}
	public Long getCdpStageNo() {
		return cdpStageNo;
	}
	public void setCdpStageNo(Long cdpStageNo) {
		this.cdpStageNo = cdpStageNo;
	}
	public String getCdpStageTyp() {
		return cdpStageTyp;
	}
	public void setCdpStageTyp(String cdpStageTyp) {
		this.cdpStageTyp = cdpStageTyp;
	}
	public Long getCdpStageProb() {
		return cdpStageProb;
	}
	public void setCdpStageProb(Long cdpStageProb) {
		this.cdpStageProb = cdpStageProb;
	}
	public String getCdpStageName() {
		return cdpStageName;
	}
	public void setCdpStageName(String cdpStageName) {
		this.cdpStageName = cdpStageName;
	}
	public String getCdpCrUid() {
		return cdpCrUid;
	}
	public void setCdpCrUid(String cdpCrUid) {
		this.cdpCrUid = cdpCrUid;
	}
	public String getCdpCrUidDesc() {
		return cdpCrUidDesc;
	}
	public void setCdpCrUidDesc(String cdpCrUidDesc) {
		this.cdpCrUidDesc = cdpCrUidDesc;
	}
	public Date getCdpCrDt() {
		return cdpCrDt;
	}
	public void setCdpCrDt(Date cdpCrDt) {
		this.cdpCrDt = cdpCrDt;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getCdpUpUid() {
		return cdpUpUid;
	}
	public void setCdpUpUid(String cdpUpUid) {
		this.cdpUpUid = cdpUpUid;
	}
	public Date getCdpUpDt() {
		return cdpUpDt;
	}
	public void setCdpUpDt(Date cdpUpDt) {
		this.cdpUpDt = cdpUpDt;
	}





}
