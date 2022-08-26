/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.vo;

import java.io.Serializable;

import qa.com.qic.common.dao.DashboardDAO;
import qa.com.qic.common.vo.CrmCallLog;
import qa.com.qic.common.vo.CrmTasks;

/**
 *
 * @author ravindar.singh
 */
public class Activity implements Serializable {

    private transient Long id;
    private transient String civilId;
    private transient String to;
    private transient String language;
    private transient String message;
    private transient String cc;
    private transient String template;
    private transient String subject;
    private transient String userId;
    private transient DashboardDAO.DateRange dateRange;

    private CrmTasks task;
    private CrmCallLog callLog;

    private String coCreateTaskYn;
    private String crmId;
    private String crmDesc;
    private String dealId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCivilId() {
        return civilId;
    }

    public void setCivilId(String civilId) {
        this.civilId = civilId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CrmTasks getTask() {
        return task;
    }

    public void setTask(CrmTasks task) {
        this.task = task;
    }

    public CrmCallLog getCallLog() {
        return callLog;
    }

    public void setCallLog(CrmCallLog callLog) {
        this.callLog = callLog;
    }

    public String getCoCreateTaskYn() {
        return coCreateTaskYn;
    }

    public void setCoCreateTaskYn(String coCreateTaskYn) {
        this.coCreateTaskYn = coCreateTaskYn;
    }

    public DashboardDAO.DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DashboardDAO.DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public String getCrmDesc() {
        return crmDesc;
    }

    public void setCrmDesc(String crmDesc) {
        this.crmDesc = crmDesc;
    }

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

}
