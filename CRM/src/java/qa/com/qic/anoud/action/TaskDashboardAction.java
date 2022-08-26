/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.action;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.dao.TaskDashboardDAO;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.dao.DashboardDAO;
import qa.com.qic.common.dao.DashboardDAO.DateRange;
import qa.com.qic.common.dao.TaskDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.vo.CrmTasks;
import qa.com.qic.common.vo.CrmTasksLog;
import qa.com.qic.common.vo.DataBean;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class TaskDashboardAction extends ActionSupport implements SessionAware {

    private static final Logger logger = LogUtil.getLogger(TaskDashboardAction.class);

    private transient Map<String, Object> session = null;
    private TaskDashboardDAO dao;

    private transient String company;
    private transient DateRange dateRange;
    private transient UserInfo userInfo;
    private transient CrmTasks task;
    private transient CrmTasksLog tasksLog;
    private transient List<KeyValue> keyValueList;
    private transient List<KeyValue> userList;

    /**
     * messageType - S: Success, E: Error message - details description about
     * success/error
     */
    private String message;
    private String messageType;

    private List<? extends Object> aaData;
    private transient List<KeyValue> dateRangeList;
    private transient String type;
    private transient DataBean dataBean;
    private transient List<KeyValue> statusList;
    private transient List<KeyValue> statusRemarksList;

    /**
     * To open the task dashboard page
     *
     * @return navigation
     */
    public String openTaskPage() {
        dateRangeList = new LinkedList<>();
        KeyValue kv;
        for (DashboardDAO.DateRange d : DateRange.values()) {
            kv = new KeyValue(d.name(), d.getDesc());
            dateRangeList.add(kv);
        }
        dateRange = DashboardDAO.DateRange.THIS_MONTH;
        dao = new TaskDashboardDAO(getCompany());
        dataBean = dao.loadTaskSLA(getType(), getUserInfo().getUserId());
        if (dataBean == null) {
            dataBean = new DataBean();
            dataBean.setCount1(0L);
            dataBean.setCount2(0L);
        }
        TaskDAO taskdao = new TaskDAO(getCompany());
        CommonDAO commonDAO = new CommonDAO(getCompany());
        AnoudDAO anoudDao = new AnoudDAO(getCompany());
        statusList = taskdao.loadStatusList();
        statusRemarksList = anoudDao.getMAppCodes(FieldConstants.AppCodes.REN_FOLLOWUP);
        userList = commonDAO.loadCrmAgentList(getUserInfo().getUserId());
        task = new CrmTasks();
        task.setCtAssignedTo(getUserInfo().getUserId());
        tasksLog = new CrmTasksLog();
        tasksLog.setCtlCtId(task.getCtId());
        return SUCCESS;
    }

    /**
     * To load the open task summary data
     *
     * @return navigation
     */
    public String loadTaskSummary() {
        logger.trace("Enter loadTaskSummary()... ");
        dao = new TaskDashboardDAO(getCompany());
        //commented for corporate
        //aaData = dao.loadTaskSummary(getTask().getCtCrmId(), getType(), getUserInfo().getUserId());
        aaData = dao.loadTaskSummary(getType(), getUserInfo().getUserId());
        logger.trace("Exit loadTaskSummary()... ");
        return "data";
    }

    /**
     * To load the SLA violated task data
     *
     * @return navigation
     */
    @Deprecated
    public String loadTaskSLA() {
        logger.trace("Enter loadTaskSLA()... ");
        dao = new TaskDashboardDAO(getCompany());
        dataBean = dao.loadTaskSLA(getType(), getUserInfo().getUserId());
        //commented for corporate
        /*aaData = dao.loadTaskSLA(getTask().getCtCrmId(), getUserInfo().getUserId());
        if (aaData == null) {
            dataBean = new DataBean();
            dataBean.setCount1(0L);
            dataBean.setCount2(0L);
            aaData = Arrays.asList(dataBean);
        }*/
        logger.trace("Exit loadTaskSLA()... ");
        return "data";
    }

    /**
     * To load the task summary data by given period
     *
     * @return navigation
     */
    public String loadTaskSummaryByPeriod() {
        logger.trace("Enter loadTaskSummaryByPeriod()... ");
        dao = new TaskDashboardDAO(getCompany());
        //commented for corporate
        //aaData = dao.loadTaskSummaryByPeriod(getTask().getCtCrmId(), getType(), getUserInfo().getUserId(), getDateRange());
        aaData = dao.loadTaskSummaryByPeriod(getType(), getUserInfo().getUserId(), getDateRange());
        logger.trace("Exit loadTaskSummaryByPeriod()... ");
        return "data";
    }

    public String loadTaskSlaSummaryByPeriod() {
        dao = new TaskDashboardDAO(getCompany());
        //commented for corporate
        //aaData = dao.loadTaskSlaSummaryByPeriod(getTask().getCtCrmId(), getDateRange());
        aaData = dao.loadTaskSlaSummaryByPeriod(getDateRange());
        return "data";
    }

    public String openTaskDetailsPage() {
        return SUCCESS;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public DashboardDAO.DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DashboardDAO.DateRange dateRange) {
        this.dateRange = dateRange;
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

    public List<KeyValue> getDateRangeList() {
        return dateRangeList;
    }

    public void setDateRangeList(List<KeyValue> dateRangeList) {
        this.dateRangeList = dateRangeList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
        userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
        setCompany((String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE));
    }

    public CrmTasks getTask() {
        return task;
    }

    public void setTask(CrmTasks task) {
        this.task = task;
    }

    public CrmTasksLog getTasksLog() {
        return tasksLog;
    }

    public void setTasksLog(CrmTasksLog tasksLog) {
        this.tasksLog = tasksLog;
    }

    public List<KeyValue> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<KeyValue> statusList) {
        this.statusList = statusList;
    }

    public List<KeyValue> getKeyValueList() {
        return keyValueList;
    }

    public void setKeyValueList(List<KeyValue> keyValueList) {
        this.keyValueList = keyValueList;
    }

    public List<KeyValue> getUserList() {
        return userList;
    }

    public void setUserList(List<KeyValue> userList) {
        this.userList = userList;
    }

    public List<KeyValue> getStatusRemarksList() {
        return statusRemarksList;
    }

    public void setStatusRemarksList(List<KeyValue> statusRemarksList) {
        this.statusRemarksList = statusRemarksList;
    }

}
