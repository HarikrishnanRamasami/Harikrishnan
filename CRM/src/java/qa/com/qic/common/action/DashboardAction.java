/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.dao.DashboardDAO;
import qa.com.qic.common.dao.DashboardDAO.DateRange;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class DashboardAction extends ActionSupport implements SessionAware {

    private static final Logger LOGGER = LogUtil.getLogger(DashboardAction.class);

    private transient Map<String, Object> session = null;
    private transient DashboardDAO dao;

    private transient String company;
    private transient String appType;
    private transient String userId;
    private transient DateRange dateRange;
    private transient String dateRangeValue;
    private transient String status;
    private List<? extends Object> aaData;
    private transient UserInfo userInfo;

    /**
     * messageType - S: Success, E: Error message - details description about
     * success/error
     */
    private String message;
    private String messageType;
    private transient List<KeyValue> userList = new ArrayList<>();
    private transient List<KeyValue> dateRangeList;
    private transient List<KeyValue> taskList;
    private transient List<KeyValue> reminderList;
    private transient List<KeyValue> recentActivityList;
    private transient List<KeyValue> opportunityList;
    private transient List<KeyValue> revenueList;
    private transient List<KeyValue> leadsList;
    private transient List<KeyValue> leaderBoardList;

    private String flex1;

    public String loadDashboardPage() {
        if(getUserInfo().getUserAdminYn() == 2) {
            return "blank";
        }
        getUserInfo().setActiveMenu(TypeConstants.Menu.DASHBOARD);
        KeyValue kv;
        dao = new DashboardDAO(getCompany());
      //commented for corporate
        /*taskList = dao.loadMyTask(getAppType(), getUserInfo().getUserId(), DateRange.THIS_MONTH);
        recentActivityList = dao.loadMyRecentActivities(getAppType(), getUserInfo().getUserId(), null);
        opportunityList = dao.loadMyOpportunities(getUserInfo().getUserId(), "O", null);
        revenueList = dao.loadMyRevenue(getUserInfo().getUserId(), DateRange.THIS_MONTH, null);
        leadsList = dao.loadMyLeads(getUserInfo().getUserId(), DateRange.THIS_MONTH, null);
        leaderBoardList = dao.loadMyLeaderBoard(getAppType(), getUserInfo().getUserId(), DateRange.THIS_MONTH, null);*/
        taskList = dao.loadMyTask(getUserInfo().getUserId(), DateRange.THIS_MONTH);
        recentActivityList = dao.loadMyRecentActivities(getUserInfo().getUserId(), null);
        opportunityList = dao.loadMyOpportunities(getUserInfo().getUserId(), "O", null);
        revenueList = dao.loadMyRevenue(getUserInfo().getUserId(), DateRange.THIS_MONTH, null);
        leadsList = dao.loadMyLeads(getUserInfo().getUserId(), DateRange.THIS_MONTH, null);
        leaderBoardList = dao.loadMyLeaderBoard(getUserInfo().getUserId(), DateRange.THIS_MONTH, null);

        CommonDAO commonDao = new CommonDAO(getCompany());
        userList = commonDao.loadCrmAgentList(getUserInfo().getUserId());
        setUserId(getUserInfo().getUserId());

        dateRangeList = new LinkedList<>();
        for (DateRange d : DateRange.values()) {
            kv = new KeyValue(d.name(), d.getDesc());
            dateRangeList.add(kv);
        }
        dateRange = DateRange.THIS_MONTH;
        return SUCCESS;
    }

    public String loadMyTaskFilterData() {
        dao = new DashboardDAO(getCompany());
        fillCustomDateRange();
      //commented for corporate
        //aaData = dao.loadMyTask(getAppType(), getUserId(), getDateRange());
        aaData = dao.loadMyTask(getUserId(), getDateRange());
        return "data";
    }

    public String loadReminderFilterData() {
        dao = new DashboardDAO(getCompany());
      //commented for corporate
        //aaData = dao.loadMyReminders(getAppType(), getUserId(), getFlex1());
        aaData = dao.loadMyReminders(getUserId(), getFlex1());
        return "data";
    }

    public String loadMyRecentFilterData() {
        dao = new DashboardDAO(getCompany());
      //commented for corporate
        //aaData = dao.loadMyRecentActivities(getAppType(), getUserId(), null);
        aaData = dao.loadMyRecentActivities(getUserId(), null);
        return "data";
    }

    public String loadOpportunityFilterData() {
        dao = new DashboardDAO(getCompany());
        aaData = dao.loadMyOpportunities(getUserId(), "O", getFlex1());
        return "data";
    }

    public String loadRevenueFilterData() {
        dao = new DashboardDAO(getCompany());
        fillCustomDateRange();
        aaData = dao.loadMyRevenue(getUserId(), getDateRange(), getFlex1());
        return "data";
    }

    public String loadLeaderBoardFilterData() {
        dao = new DashboardDAO(getCompany());
        fillCustomDateRange();
      //commented for corporate
        //aaData = dao.loadMyLeaderBoard(getAppType(), getUserInfo().getUserId(), getDateRange(), getFlex1());
        aaData = dao.loadMyLeaderBoard(getUserInfo().getUserId(), getDateRange(), getFlex1());
        return "data";
    }

    public String loadLeadsFilterData() {
        dao = new DashboardDAO(getCompany());
        fillCustomDateRange();
        aaData = dao.loadMyLeads(getUserInfo().getUserId(), getDateRange(), getFlex1());
        return "data";
    }

    public String loadActivityChartData() {
        dao = new DashboardDAO(getCompany());
        fillCustomDateRange();
      //commented for corporate
        //aaData = dao.loadActivityChartData(getAppType(), getUserId(), getDateRange());
        aaData = dao.loadActivityChartData(getUserId(), getDateRange());
        return "data";
    }

    public String loadCallLogDetails() {
        dao = new DashboardDAO(getCompany());
        fillCustomDateRange();
        //commented for corporate
       // aaData = dao.loadCallLogDetails(getAppType(), getUserId(), getDateRange());
        aaData = dao.loadCallLogDetails(getUserId(), getDateRange());
        return "data";
    }

    public String openAdminPage() {
        getUserInfo().setActiveMenu(TypeConstants.Menu.ADMIN);
        return SUCCESS;
    }

    public String openMonitorPage() {
        getUserInfo().setActiveMenu(TypeConstants.Menu.MONITOR);
        return SUCCESS;
    }

    public String openCrmPage() {
        getUserInfo().setActiveMenu(TypeConstants.Menu.CRM);
        return SUCCESS;
    }

    public String openDigitalMarketingPage() {
        getUserInfo().setActiveMenu(TypeConstants.Menu.DIGITALMARKETING);
        return SUCCESS;
    }

    public String loadCiscoMonitorPage() {
        if (1 != userInfo.getUserAdminYn()) {
            return "unAuthroized";
        }
        if ("1".equals(getStatus())) {// Agent Status
            setFlex1("http://comstice.beema.com.qa/universal/widget/#/layout8/template/1502856398736/607");
        } else if ("2".equals(getStatus())) {// IVR View
            setFlex1("http://comstice.beema.com.qa/universal/widget/#/layout9/template/1502860259042/607");
        }
        return SUCCESS;
    }

    public String changeCompany() {
        if (null != company) {
            switch (company) {
                case "QA":
                    company = ApplicationConstants.COMPANY_CODE_DOHA;
                    break;
                case "AE":
                    company = ApplicationConstants.COMPANY_CODE_DUBAI;
                    break;
                case "OM":
                    company = ApplicationConstants.COMPANY_CODE_OMAN;
                    break;
                case "KW":
                    company = ApplicationConstants.COMPANY_CODE_KUWAIT;
                    break;
                default:
                    return SUCCESS;
            }
            userInfo.setCompanyCode(company);
            session.put(ApplicationConstants.SESSION_NAME_COMPANY_CODE, company);
        }
        return SUCCESS;
    }

    private void fillCustomDateRange() {
        if (getDateRange() == DashboardDAO.DateRange.CUSTOM) {
            try {
                String fromDate = getDateRangeValue().split(" - ")[0];
                String toDate = getDateRangeValue().split(" - ")[1];
                String s = getDateRange().getRange();
                s = s.replaceFirst(Pattern.quote("?"), fromDate);
                s = s.replace("?", toDate);
                getDateRange().setRange(s);
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public DashboardDAO.DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DashboardDAO.DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public String getDateRangeValue() {
        return dateRangeValue;
    }

    public void setDateRangeValue(String dateRangeValue) {
        this.dateRangeValue = dateRangeValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<? extends Object> getAaData() {
        return aaData;
    }

    public void setAaData(List<? extends Object> aaData) {
        this.aaData = aaData;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public List<KeyValue> getUserList() {
        return userList;
    }

    public void setUserList(List<KeyValue> userList) {
        this.userList = userList;
    }

    public List<KeyValue> getDateRangeList() {
        return dateRangeList;
    }

    public void setDateRangeList(List<KeyValue> dateRangeList) {
        this.dateRangeList = dateRangeList;
    }

    public List<KeyValue> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<KeyValue> taskList) {
        this.taskList = taskList;
    }

    public List<KeyValue> getReminderList() {
        return reminderList;
    }

    public void setReminderList(List<KeyValue> reminderList) {
        this.reminderList = reminderList;
    }

    public List<KeyValue> getRecentActivityList() {
        return recentActivityList;
    }

    public void setRecentActivityList(List<KeyValue> recentActivityList) {
        this.recentActivityList = recentActivityList;
    }

    public List<KeyValue> getOpportunityList() {
        return opportunityList;
    }

    public void setOpportunityList(List<KeyValue> opportunityList) {
        this.opportunityList = opportunityList;
    }

    public List<KeyValue> getRevenueList() {
        return revenueList;
    }

    public void setRevenueList(List<KeyValue> revenueList) {
        this.revenueList = revenueList;
    }

    public List<KeyValue> getLeadsList() {
        return leadsList;
    }

    public void setLeadsList(List<KeyValue> leadsList) {
        this.leadsList = leadsList;
    }

    public List<KeyValue> getLeaderBoardList() {
        return leaderBoardList;
    }

    public void setLeaderBoardList(List<KeyValue> leaderBoardList) {
        this.leaderBoardList = leaderBoardList;
    }

    public String getFlex1() {
        return flex1;
    }

    public void setFlex1(String flex1) {
        this.flex1 = flex1;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
        userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
        setCompany((String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE));
    }
}
