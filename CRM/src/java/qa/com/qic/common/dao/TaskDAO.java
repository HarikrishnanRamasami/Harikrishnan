/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryExecutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.vo.CrmTasks;
import qa.com.qic.common.vo.CrmTasksLog;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class TaskDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(TaskDAO.class);

    private final String dataSource;

    public TaskDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Task details based on task id
     *
     * @param taskId
     * @return task
     * @author ravindar.singh
     */
    public CrmTasks loadTaskData(final Long taskId) {
    	//commented for corporate
        /*CrmTasks crmTasks = new CrmTasks();
        crmTasks.setCtId(taskId);
        List<CrmTasks> list = loadTaskList(crmTasks);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;*/

    	CrmTasks crmTasks = null;
        List<CrmTasks> list = loadTaskList(null, null, null, taskId, null, null, null, null);
        if (list != null && !list.isEmpty()) {
            crmTasks = list.get(0);
        }
        return crmTasks;
    }

    /**
     * All tasks belongs to user id or civil id or policy no
     *
     * @param task
     * @return list of tasks
     * @author ravindar.singh
     */
    public List<CrmTasks> loadTaskList(final CrmTasks task) {
        return loadTaskList(task.getCtAssignedTo(), task.getCtRefId(), task.getCtRefNo(), null, task.getCtStatus(), task.getCtAssignedDt(), task.getCtCatg(), task.getCtSubCatg());
    }

    /**
     * All tasks belongs to user id or civil id or policy no or task id
     *
     * @param task
     * @return list of tasks
     * @author ravindar.singh
     */
    private List<CrmTasks> loadTaskList(final String userId, final String refId, final String refNo, final Long taskId, final String status, final String dateRange, final String category, final String subCategory) {
        List<CrmTasks> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            List param = new LinkedList();

            query = new StringBuilder("SELECT CT_ID ctId, CT_REF_ID ctRefId, CT_REF_NO ctRefNo, CT_PRIORITY ctPriority, CT_MODULE ctModule, CT_CATG ctCatg, ");
            query.append("CT_SUB_CATG ctSubCatg, CT_SUBJECT ctSubject, CT_MESSAGE ctMessage, CT_STATUS ctStatus, CT_ASSIGNED_TO ctAssignedTo, ");
            query.append("TO_CHAR(CT_ASSIGNED_DT, 'DD/MM/YYYY HH24:MI') ctAssignedDt, TO_CHAR(CT_DUE_DATE, 'DD/MM/YYYY HH24:MI') ctDueDate, CT_REMIND_BEFORE ctRemindBefore, CT_CR_UID ctCrUid, CT_CR_DT ctCrDt, ");
            query.append("CT_UPD_UID ctUpdUid, CT_UPD_DT ctUpdDt, CT_FLEX_01 ctFlex01, CT_FLEX_02 ctFlex02, CT_FLEX_03 ctFlex03, CT_FLEX_04 ctFlex04, CT_FLEX_05 ctFlex05,CT_CLOSE_CODE ctCloseCode, ");
            query.append("PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_MODULE', CT_MODULE) ctModuleDesc, NVL(CT_READ_YN, '0') ctReadYn, ");
            query.append("(CASE WHEN NVL(CT_CLOSE_DATE, SYSDATE) > CT_DUE_DATE THEN 1 ELSE 0 END) ctSlaViolated, ");
            query.append("PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_PRIORITY', CT_PRIORITY) ctPriorityDesc, ");
          //commented for corporate
            /*query.append("PKG_REP_UTILITY.FN_GET_AC_NAME( 'CRM_CATG', CT_CATG) ctCatgDesc, CT_CRM_ID ctCrmId, ");
            query.append("CT_CLOSE_CODE ctReason, CT_CLOSE_REMARKS ctCloseRemarks, PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CT_CRM_ID) as ctCrmDesc, ");*/
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CATG', CT_CATG) ctCatgDesc, ");
            query.append("CT_CLOSE_CODE ctReason, CT_CLOSE_REMARKS ctCloseRemarks, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_SUB_CATG', CT_SUB_CATG) ctSubCatgDesc, ");
            if (ApplicationConstants.APP_TYPE.equals(ApplicationConstants.APP_TYPE_RETAIL)) {
                query.append("(SELECT AF_NAME FROM M_AFFINITY_CODES WHERE AF_CODE = CT_AFF_SRC) ctAffSource, ");
            }
            query.append("(SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_USER_ID = CT_ASSIGNED_TO) ctAssignedToDesc ");
            query.append("FROM T_CRM_TASKS WHERE ");
            if (StringUtils.isNoneBlank(userId)) {
                if (userId.startsWith("ALL")) {
                    query.append("EXISTS(SELECT USER_ID FROM TABLE(FN_GET_CRM_AGENTS(?)) WHERE USER_ID = CT_ASSIGNED_TO) ");
                } else {
                    query.append("CT_ASSIGNED_TO = ? ");
                }
                param.add(userId.replaceFirst("ALL", ""));
            } else if (StringUtils.isNoneBlank(refId)) {
                query.append("CT_REF_ID = ? ");
                param.add(refId);
            } else if (StringUtils.isNoneBlank(refNo)) {
                query.append("CT_REF_NO = ? ");
                param.add(refNo);
            } else if (taskId != null && taskId > 0) {
                query.append("CT_ID = ? ");
                param.add(taskId);
            }
            if (StringUtils.isNoneBlank(status)) {
                if (!"ALL".equals(status)) {
                    if (!param.isEmpty()) {
                        query.append("AND ");
                    }
                    query.append("CT_STATUS = ? ");
                    param.add(status);
                }
            }
            if (StringUtils.isNoneBlank(dateRange)) {
                if (!param.isEmpty()) {
                    query.append("AND ");
                }
                query.append("TRUNC(CT_ASSIGNED_DT) BETWEEN TRUNC(TO_DATE(?, 'DD/MM/YYYY')) AND TRUNC(TO_DATE(?, 'DD/MM/YYYY')) ");
                String s[] = dateRange.split(" - ");
                param.add(s[0]);
                param.add(s[1]);
            }
            if (StringUtils.isNoneBlank(category)) {
                if (!"ALL".equals(category)) {
                    if (!param.isEmpty()) {
                        query.append("AND ");
                    }
                    query.append("CT_CATG = ? ");
                    param.add(category);
                }
            }
            if (StringUtils.isNoneBlank(subCategory)) {
                if (!"ALL".equals(subCategory)) {
                    if (!param.isEmpty()) {
                        query.append("AND ");
                    }
                    query.append("CT_SUB_CATG = ? ");
                    param.add(subCategory);
                }
            }
            query.append("ORDER BY CT_ID DESC");
            Object[] date = param.toArray();
          //commented for corporate
           // LOGGER.info("Query :: {} [{}:{}:{}:{}:{}:{}:{}:Category={}:SubCategory={}]", new Object[]{query, task.getCtAssignedTo(), task.getCtRefId(), task.getCtRefNo(), task.getCtId(), task.getCtStatus(), task.getCtCrmId(), task.getCtAssignedDt(), task.getCtCatg(), task.getCtSubCatg()});
            LOGGER.info("Query :: {} [{}:{}:{}:{}:{}:{}:Category={}:SubCategory={}]", new Object[]{query, userId, refId, refNo, taskId, status, dateRange, category, subCategory});
            list = executeList(con, query.toString(), date, CrmTasks.class);
            for (CrmTasks ct : list) {
                if (ct.getCtAffSource() != null) {
                    ct.setCtSubCatgDesc(ct.getCtSubCatgDesc() + " - " + ct.getCtAffSource());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while retreiving task list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<CrmTasks> loadTaskListForNotify(final String userId) {
        List<CrmTasks> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            List param = new LinkedList();

            query = new StringBuilder("SELECT CT_ID ctId, CT_PRIORITY ctPriority, CT_SUBJECT ctSubject, CT_MESSAGE ctMessage, ");
            query.append("TO_CHAR(CT_ASSIGNED_DT, 'DD/MM/YYYY HH24:MI') ctAssignedDt, CT_FLEX_01 ctFlex01, ");
            query.append("PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_PRIORITY', CT_PRIORITY) ctPriorityDesc, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CATG', CT_CATG) ctCatgDesc, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_SUB_CATG', CT_SUB_CATG) ctSubCatgDesc, ");
            query.append("(SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_USER_ID = CT_ASSIGNED_TO) ctAssignedToDesc ");
            query.append("FROM T_CRM_TASKS WHERE NVL(CT_READ_YN, '0') = '0' AND CT_SUB_CATG IN (").append(ApplicationConstants.TASK_NOTIFY_CATG(getDataSource())).append(") AND CT_STATUS = 'P' AND CT_ASSIGNED_TO = ? AND CT_ASSIGNED_DT > SYSDATE - (1/1440) ");
            query.append("ORDER BY CT_ID DESC");
            param.add(userId);
            Object[] date = param.toArray();
            LOGGER.info("Query :: {} [{}]", new Object[]{query, userId});
            list = executeList(con, query.toString(), date, CrmTasks.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving task list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /**
     * All task logs related to task
     *
     * @param taskId
     * @return list of tasks logs
     * @author ravindar.singh
     */
    public List<CrmTasksLog> loadTaskLogList(final Long taskId) {
        List<CrmTasksLog> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{taskId};

            query = new StringBuilder("SELECT CTL_ID ctlId, CTL_CT_ID ctlCtId, CTL_DETAILS ctlDetails, ");
            query.append("CTL_STATUS ctlStatus, (SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_USER_ID = CTL_CR_UID) ctlCrUid, CTL_CR_DT ctlCrDt ");
            query.append("FROM T_CRM_TASKS_LOG WHERE CTL_CT_ID = ? ");
            query.append("ORDER BY CTL_ID DESC");
            LOGGER.info("Query :: {} [{}]", new Object[]{query, taskId});
            list = executeList(con, query.toString(), date, CrmTasksLog.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving task log list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public int saveTask(final CrmTasks bean, final String operation) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        int taskId = 0;
        String sql = null;
        AnoudDAO anoudDAO = new AnoudDAO(getDataSource());
        try {
            con = getDBConnection(getDataSource());
            if ("add".equalsIgnoreCase(operation)) {
                Long seq_id = anoudDAO.getSeqVal(con, "SELECT S_CT_ID.NEXTVAL FROM DUAL");
              //commented for corporate
                //sql = "INSERT INTO T_CRM_TASKS (CT_ID, CT_CRM_ID, CT_REF_ID, CT_REF_NO, CT_MODULE, CT_PRIORITY, CT_CATG, CT_SUB_CATG, CT_STATUS, "
                sql = "INSERT INTO T_CRM_TASKS (CT_ID, CT_REF_ID, CT_REF_NO, CT_MODULE, CT_PRIORITY, CT_CATG, CT_SUB_CATG, CT_STATUS, "
                        + "CT_FLEX_01, CT_SUBJECT, CT_MESSAGE, CT_DUE_DATE, CT_ASSIGNED_TO, CT_ASSIGNED_DT, "
                        //commented for corporate
                        /*+ "CT_REMIND_BEFORE, CT_CR_UID, CT_CR_DT, CT_DEAL_ID) VALUES "
                        + "(:ctId, :ctCrmId, :ctRefId, :ctRefNo, :ctModule, :ctPriority, :ctCatg, :ctSubCatg, :ctStatus, :ctFlex01, :ctSubject, "
                        + ":ctMessage, TO_DATE(:ctDueDate, 'DD/MM/YYYY HH24:MI'), :ctAssignedTo, SYSDATE, :ctRemindBefore, :ctCrUid, SYSDATE , :ctDealId)";*/
                        + "CT_REMIND_BEFORE, CT_CR_UID, CT_CR_DT) VALUES "
                        + "(:ctId, :ctRefId, :ctRefNo, :ctModule, :ctPriority, :ctCatg, :ctSubCatg, :ctStatus, :ctFlex01, :ctSubject, "
                        + ":ctMessage, TO_DATE(:ctDueDate, 'DD/MM/YYYY HH24:MI'), :ctAssignedTo, SYSDATE, :ctRemindBefore, :ctCrUid, SYSDATE)";
              //commented for corporate
                /*Object[] params = new Object[]{seq_id, bean.getCtCrmId(), bean.getCtRefId(), bean.getCtRefNo(), bean.getCtModule(), bean.getCtPriority(), bean.getCtCatg(), bean.getCtSubCatg(), bean.getCtStatus(),
                    bean.getCtFlex01(), bean.getCtSubject(), bean.getCtMessage(), bean.getCtDueDate(), bean.getCtAssignedTo(), bean.getCtRemindBefore(), bean.getCtCrUid(),  bean.getCtDealId()};*/
                Object[] params = new Object[]{seq_id, bean.getCtRefId(), bean.getCtRefNo(), bean.getCtModule(), bean.getCtPriority(), bean.getCtCatg(), bean.getCtSubCatg(), bean.getCtStatus(),
                        bean.getCtFlex01(), bean.getCtSubject(), bean.getCtMessage(), bean.getCtDueDate(), bean.getCtAssignedTo(), bean.getCtRemindBefore(), bean.getCtCrUid()};
                // recCnt = executeUpdate(con, sql, params);
                QueryExecutor run = new QueryExecutor();
              //commented for corporate
                /*int[] types = new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                    Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR ,Types.VARCHAR};*/
                int[] types = new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                    Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR};
                run.insert(con, sql, params, types);
                /*String sql1 = "INSERT INTO T_CRM_TASKS_LOG(CTL_ID, CTL_CT_ID, CTL_DETAILS, CTL_STATUS, CTL_CR_UID, CTL_CR_DT) VALUES "
                 + "(S_CTL_ID.NEXTVAL, :ctlCtId, :ctlDetails, :ctlStatus, :ctlCrUid, SYSDATE)";
                 Object[] param = new Object[]{seq_id, "Task created" + (" and assigned to " + bean.getCtAssignedTo()), bean.getCtStatus(), bean.getCtCrUid()};
                 recCnt = executeUpdate(con, sql1, param);*/
                taskId = (int) (long) seq_id;

            } else if ("edit".equalsIgnoreCase(operation)) {
                sql = "UPDATE T_CRM_TASKS SET CT_REF_NO = ?, CT_MODULE = ?, CT_PRIORITY = ?, CT_CATG = ?, CT_SUB_CATG = ?, "
                        + "CT_STATUS = ?, CT_FLEX_01 = ?, CT_SUBJECT = ?, CT_MESSAGE = ?, CT_DUE_DATE = TO_DATE(?, 'DD/MM/YYYY HH24:MI'), "
                        + "CT_ASSIGNED_TO = ?, CT_REMIND_BEFORE = ?, CT_UPD_UID = ?, CT_UPD_DT = SYSDATE WHERE CT_ID = ?";
                Object[] params = new Object[]{bean.getCtRefNo(), bean.getCtModule(), bean.getCtPriority(), bean.getCtCatg(), bean.getCtSubCatg(), bean.getCtStatus(),
                    bean.getCtFlex01(), bean.getCtSubject(), bean.getCtMessage(), bean.getCtDueDate(), bean.getCtAssignedTo(), bean.getCtRemindBefore(), bean.getCtUpdUid(), bean.getCtId()};
                recCnt = executeUpdate(con, sql, params);
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while task entry => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return taskId;
    }

    public Long createTask(final CrmTasks bean) {
        Connection con = null;
        PreparedStatement ps = null;
        Long taskId = null;
        AnoudDAO anoudDAO = new AnoudDAO(getDataSource());
        try {
            con = getDBConnection(getDataSource());
            String sql = "SELECT CAT_AGENT_ID key FROM M_CRM_AGENTS_TASK_ALLOC WHERE CAT_TASK_CATG = ? AND CAT_TASK_SUB_CATG = ?";
            KeyValue obj = (KeyValue) executeQuery(con, sql, new Object[]{bean.getCtCatg(), bean.getCtSubCatg()}, KeyValue.class);
            String assignTo = null;
            if (obj != null) {
                assignTo = obj.getKey();
            }
            taskId = anoudDAO.getSeqVal(con, "SELECT S_CT_ID.NEXTVAL FROM DUAL");
            sql = "INSERT INTO T_CRM_TASKS (CT_ID, CT_REF_ID, CT_REF_NO, CT_MODULE, CT_PRIORITY, CT_CATG, CT_SUB_CATG, CT_STATUS, "
                    + "CT_FLEX_01, CT_EMAIL_ID, CT_SUBJECT, CT_MESSAGE, CT_ASSIGNED_TO, CT_ASSIGNED_DT, CT_CR_UID, CT_CR_DT)"
                    + " VALUES (:ctId, :ctRefId, :ctRefNo, :ctModule, :ctPriority, :ctCatg, :ctSubCatg, :ctStatus, :ctMobileNo, :ctEmailId, :ctSubject, "
                    + ":ctMessage, :ctAssignedTo, SYSDATE, :ctCrUid, SYSDATE)";
            Object[] params = new Object[]{taskId, bean.getCtRefId(), bean.getCtRefNo(), bean.getCtModule(), bean.getCtPriority(), bean.getCtCatg(), bean.getCtSubCatg(), bean.getCtStatus(),
                bean.getCtMobileNo(), bean.getCtEmailId(), bean.getCtSubject(), bean.getCtMessage(), assignTo, bean.getCtCrUid()};
            QueryExecutor run = new QueryExecutor();
            int[] types = new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
            run.insert(con, sql, params, types);
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while task entry => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return taskId;
    }

    public int updateTask(final CrmTasks bean) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String assignedTo = null;
        Object[] params = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT CT_ASSIGNED_TO FROM T_CRM_TASKS WHERE CT_ID = ? ";
            PreparedStatement ps1 = con.prepareStatement(query);
            ps1.setLong(1, bean.getCtId());
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                assignedTo = rs.getString(1);
            }
            rs.close();
            ps1.close();
            if ("C".equals(bean.getCtStatus())) {
                String sql;
                if("N".equals(bean.getCtCloseYn())){
                    sql = "UPDATE T_CRM_TASKS SET CT_CLOSE_CODE = ?, CT_CLOSE_REMARKS = ?  WHERE CT_ID = ?";
                    params = new Object[]{bean.getCtCloseCode(), bean.getCtCloseRemarks(), bean.getCtId()};
                }else{
                    sql = "UPDATE T_CRM_TASKS SET CT_STATUS = 'C', CT_CLOSE_CODE = ?, CT_CLOSE_REMARKS = ?, CT_CLOSE_DATE = SYSDATE WHERE CT_ID = ?";
                    params = new Object[]{bean.getCtCloseCode(), bean.getCtCloseRemarks(), bean.getCtId()};
                }

                recCnt = executeUpdate(con, sql, params);
            } else {
                String sql1 = "UPDATE T_CRM_TASKS SET CT_ASSIGNED_TO = NVL(?, CT_ASSIGNED_TO), CT_ASSIGNED_DT = NVL2(?, SYSDATE, CT_ASSIGNED_DT), CT_STATUS = ?, CT_PRIORITY = ? , CT_CLOSE_REMARKS = ?" + ("H".equals(bean.getCtStatus()) ? ", CT_DUE_DATE = TO_DATE(?, 'DD/MM/YYYY HH24:MI'), CT_CLOSE_CODE =?, CT_REMIND_BEFORE = ?" : "") + " WHERE CT_ID = ?";
                if ("H".equals(bean.getCtStatus())) {
                    params = new Object[]{bean.getCtAssignedTo(), bean.getCtAssignedTo(), bean.getCtStatus(), bean.getCtPriority(), bean.getCtCloseRemarks(), bean.getCtDueDate(), bean.getCtReason(), bean.getCtRemindBefore(), bean.getCtId()};
                } else {
                    params = new Object[]{bean.getCtAssignedTo(), bean.getCtAssignedTo(), bean.getCtStatus(), bean.getCtPriority(), bean.getCtCloseRemarks(), bean.getCtId()};
                }
                recCnt = executeUpdate(con, sql1, params);
                if (bean.getCrmTasksLog() != null && bean.getCrmTasksLog().getCtlId() != null) {
                    String sql2 = "UPDATE T_CRM_TASKS_LOG SET CTL_DETAILS = ?, CTL_STATUS = ? WHERE CTL_ID = ? ";
                    params = new Object[]{bean.getCtCloseRemarks(), bean.getCtStatus(), bean.getCrmTasksLog().getCtlId()};
                    recCnt = executeUpdate(con, sql2, params);
                } else {
                    String sql3 = "INSERT INTO T_CRM_TASKS_LOG(CTL_ID, CTL_CT_ID, CTL_DETAILS, CTL_STATUS, CTL_CR_UID, CTL_CR_DT) VALUES "
                            + "(S_CTL_ID.NEXTVAL, :ctlCtId, :ctlDetails, :ctlStatus, :ctlCrUid, SYSDATE)";
                    params = new Object[]{bean.getCtId(), bean.getCtCloseRemarks(), bean.getCtStatus(), bean.getCtCrUid()};
                    recCnt = executeUpdate(con, sql3, params);
                }
                if (bean.getCtAssignedTo() != null && !bean.getCtAssignedTo().equals(assignedTo)) {
                    String sql5 = "INSERT INTO T_CRM_TASKS_LOG(CTL_ID, CTL_CT_ID, CTL_DETAILS, CTL_STATUS, CTL_CR_UID, CTL_CR_DT) VALUES "
                            + "(S_CTL_ID.NEXTVAL, :ctlCtId, :ctlDetails, :ctlStatus, :ctlCrUid, SYSDATE)";
                    params = new Object[]{bean.getCtId(), "Task Forwarded" + (" and assigned to " + bean.getCtAssignedTo()), bean.getCtStatus(), bean.getCtCrUid()};
                    recCnt = executeUpdate(con, sql5, params);
                }
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while update Crm tasks => {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public CrmTasksLog saveTaskLog(final CrmTasksLog bean, final String operation) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equalsIgnoreCase(operation)) {
                sql = "INSERT INTO T_CRM_TASKS_LOG (CTL_ID, CTL_CT_ID, CTL_DETAILS, CTL_STATUS, CTL_CR_UID, CTL_CR_DT, CTL_FLEX_01, CTL_FLEX_02)VALUES "
                        + "(S_CTL_ID.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?, ?)";

                ps = con.prepareStatement(sql, new String[]{"CTL_ID"});
                ps.setLong(++i, bean.getCtlCtId());
                if (bean.getCtlDetails() == null) {
                    ps.setNull(++i, Types.VARCHAR);
                } else {
                    ps.setString(++i, bean.getCtlDetails());
                }
                ps.setString(++i, bean.getCtlStatus());
                ps.setString(++i, bean.getCtlCrUid());
                if (bean.getCtlFlex01() == null) {
                    ps.setNull(++i, Types.VARCHAR);
                } else {
                    ps.setString(++i, bean.getCtlFlex01());
                }
                if (bean.getCtlFlex02() == null) {
                    ps.setNull(++i, Types.VARCHAR);
                } else {
                    ps.setString(++i, bean.getCtlFlex02());
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    bean.setCtlId(rs.getLong(1));
                }
                ps.close();
                if (recCnt > 0 && bean.getTask() != null && bean.getTask().getCtDueDate() != null) {
                    sql = "UPDATE T_CRM_TASKS SET CT_DUE_DATE = TO_DATE(?, 'DD/MM/YYYY'), "
                            + "CT_ASSIGNED_TO = ?, CT_REMIND_BEFORE = ? WHERE CT_ID = ?";
                    Object[] taskParams = new Object[]{bean.getTask().getCtDueDate(), bean.getTask().getCtAssignedTo(), bean.getTask().getCtRemindBefore(), bean.getCtlCtId()};
                    recCnt = executeUpdate(con, sql, taskParams);
                }
            } else if ("edit".equalsIgnoreCase(operation)) {
                sql = "UPDATE T_CRM_TASKS_LOG CTL_DETAILS = ?, CTL_STATUS = ?, CTL_CR_DT = TO_DATE(?, 'DD/MM/YYYY') WHERE CTL_ID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(++i, bean.getCtlDetails());
                ps.setString(++i, bean.getCtlStatus());
                ps.setString(++i, bean.getCtlCrUid());
                ps.setLong(++i, bean.getCtlId());
                recCnt = ps.executeUpdate();
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while taskLog entry => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return bean;
    }

    public List<KeyValue> loadStatusList() {
        List<KeyValue> statusList = new ArrayList<>();
        statusList.add(new KeyValue("P", "Pending"));
        statusList.add(new KeyValue("C", "Closed"));
        statusList.add(new KeyValue("H", "On Hold"));
        return statusList;
    }

    public List<CrmTasks> loadActivityList(final DashboardDAO.DateRange dateRange, final String userId) {
        List<CrmTasks> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{userId};

            query = new StringBuilder("SELECT IL_ID ctId, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', IL_TYPE) ctModuleDesc, IL_CIVIL_ID ctRefId, IL_REF_NO ctRefNo, IL_LOG_DETAILS ctMessage, ");
            query.append("(SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_USER_ID = IL_CR_UID) ctCrUid, IL_CR_DT ctCrDt, IL_TYPE ctModule ");
            query.append("FROM T_INSURED_LOG WHERE IL_CR_UID = ? ");
            if (dateRange != null) {
                query.append("AND TRUNC(IL_CR_DT) ").append(dateRange.getRange());
            }
            query.append("ORDER BY IL_ID DESC ");

            LOGGER.info("Query :: {} [{}]", new Object[]{query, userId});
            list = executeList(con, query.toString(), date, CrmTasks.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving task log list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

   //added for corporate
    public List<CrmTasks> loadActivityList(final Activity activity) {
        List<CrmTasks> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            LinkedList<Object> params = new LinkedList<>();
            params.add(activity.getUserId());

            query = new StringBuilder("SELECT IL_ID ctId, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', IL_TYPE) ctModuleDesc, IL_CIVIL_ID ctRefId, IL_REF_NO ctRefNo, IL_LOG_DETAILS ctMessage, ");
            query.append("(SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_USER_ID = IL_CR_UID) ctCrUid, IL_CR_DT ctCrDt, IL_TYPE ctModule, ");
            query.append("IL_CRM_ID ctCrmId, PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', IL_CRM_ID) ctCrmDesc FROM T_INSURED_LOG WHERE IL_CR_UID = ? ");
            if (StringUtils.isNotBlank(activity.getCrmId())) {
                query.append("AND IL_CRM_ID = ? ");
                params.add(activity.getCrmId());
            }
            if (activity.getDateRange() != null) {
                query.append("AND TRUNC(IL_CR_DT) ").append(activity.getDateRange().getRange());
            }
            query.append("ORDER BY IL_ID DESC ");
            Object[] param = params.toArray();
            LOGGER.info("Query :: {} {}", new Object[]{query, Arrays.toString(param)});
            list = executeList(con, query.toString(), param, CrmTasks.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving task log list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public String getDataSource() {
        return dataSource;
    }

    public String insertUploadedTaskFileDetails(List<CrmTasks> uploadTaskList, CrmTasks task) {
        String result = "";
        String sql;
        Connection con = null;
        CallableStatement cs = null;
        PreparedStatement prs = null;
        con = getDBConnection(dataSource);
        try {
            con.setAutoCommit(true);
            sql = "INSERT INTO T_CRM_TASKS(CT_ID, CT_MODULE, CT_PRIORITY, CT_CATG, CT_SUB_CATG, CT_REF_ID, CT_REF_NO, CT_SUBJECT, CT_MESSAGE, CT_DUE_DATE, CT_ASSIGNED_TO, CT_STATUS, CT_CR_UID, CT_CR_DT, CT_ASSIGNED_DT, CT_FLEX_01) VALUES (S_CT_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, TO_DATE(?,'DD-MM-YYYY'), ?, ?, ?, SYSDATE, SYSDATE, ?)";
            if (uploadTaskList != null && uploadTaskList.size() > 0) {
                prs = con.prepareStatement(sql);
                for (CrmTasks bean : uploadTaskList) {
                    if ("OK".equals(bean.getCtFlex02())) {
                        prs.setString(1, bean.getCtModule());
                        prs.setString(2, bean.getCtPriority());
                        prs.setString(3, bean.getCtCatg());
                        prs.setString(4, bean.getCtSubCatg());
                        prs.setString(5, bean.getCtRefId());
                        prs.setString(6, bean.getCtRefNo());
                        prs.setString(7, bean.getCtSubject());
                        prs.setString(8, bean.getCtMessage());
                        prs.setString(9, bean.getCtDueDate());
                        prs.setString(10, bean.getCtAssignedTo());
                        prs.setString(11, bean.getCtStatus());
                        prs.setString(12, bean.getCtCrUid());
                        prs.setString(13, bean.getCtFlex01());
                        prs.executeUpdate();
                    }
                }
                con.commit();
            }
        } catch (Exception e) {
            result = e.getMessage();
            LOGGER.debug("Error while inserting task file upload", e);
        } finally {
            closeDBCallSt(cs, con);
        }
        return result;
    }

    //mail methods
    public Map<String, String> findMailConnectionParams(String compCode) throws Exception {
        Map<String, String> config = new HashMap<>();

        Connection con = null;
        con = getDBConnection(dataSource);
        PreparedStatement ps = null;
        try {
            String query = "SELECT PARA_REMARK,PARA_NAME_BL,PARA_VALUE FROM M_APP_PARAMETER WHERE PARA_CODE = 'PO_MAIL_CONFIG' AND PARA_NAME = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, compCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                config.put("MAIL_HOST", rs.getString("PARA_REMARK"));
                config.put("MAIL_ID", rs.getString("PARA_NAME_BL"));
                config.put("MAIL_PASSWORD", rs.getString("PARA_VALUE"));
            }

        } catch (Exception he) {
            throw he;
        } finally {
            con.close();
        }
        return config;
    }

    public String retrieveCloudYn() throws Exception {
        String result = null;
        Connection con = null;
        PreparedStatement ps = null;
        con = getDBConnection(dataSource);

        try {
            String query = "SELECT APC_VALUE FROM M_App_Config WHERE apc_Name='CLOUD_YN'";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getString("APC_VALUE");
            }

        } catch (Exception e) {

            throw e;
        } finally {
            con.close();
        }
        return result;
    }

    public void updateTaskFileDetails(long taskId) throws Exception {
        PreparedStatement prs = null;
        int st = 0;
        Connection con = null;
        try {
            con = getDBConnection(dataSource);
            String sql = "UPDATE T_CRM_TASKS SET CT_FLEX_05 = '1' WHERE CT_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setLong(1, taskId);
            st = prs.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error while Updating task Information", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
    }

    public void updateTaskReadStatus(long taskId) throws Exception {
        PreparedStatement prs = null;
        int st = 0;
        Connection con = null;
        try {
            con = getDBConnection(dataSource);
            String sql = "UPDATE T_CRM_TASKS SET CT_READ_YN = '1' WHERE CT_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setLong(1, taskId);
            st = prs.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error while Updating task Information", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
    }

    public void saveTaskBulkAssign(final CrmTasks task, final List<KeyValue> list) throws Exception {
        PreparedStatement prs1 = null, prs2 = null;
        int st = 0;
        Connection con = null;
        try {
            con = getDBConnection(dataSource);
            prs1 = con.prepareStatement("UPDATE T_CRM_TASKS SET CT_ASSIGNED_TO = ?, CT_ASSIGNED_DT = SYSDATE WHERE CT_ID = ?");
            prs2 = con.prepareStatement("INSERT INTO T_CRM_TASKS_LOG(CTL_ID, CTL_CT_ID, CTL_DETAILS, CTL_STATUS, CTL_CR_UID, CTL_CR_DT) VALUES (S_CTL_ID.NEXTVAL, ?, ?, ?, ?, SYSDATE)");
            for (KeyValue kv : list) {
                prs1.setString(1, task.getCtAssignedTo());
                prs1.setString(2, kv.getKey());
                prs1.addBatch();

                prs2.setString(1, kv.getKey());
                prs2.setString(2, "Task Forwarded and assigned to " + task.getCtAssignedTo());
                prs2.setString(3, "P");
                prs2.setString(4, task.getCtCrUid());
                prs2.addBatch();
            }
            prs1.executeBatch();
            prs2.executeBatch();
        } catch (Exception e) {
            LOGGER.error("Error while Updating task Information", e);
            throw e;
        } finally {
            closeDBComp(prs1, null, null);
            closeDBComp(prs2, null, con);
        }
    }

    public List<KeyValue> loadCategoryList(final String userId) {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            if (getDataSource() != null) {
                con = getDBConnection(getDataSource());
            }
            //commented for corporate
            //StringBuilder query = new StringBuilder("SELECT DISTINCT CAT_TASK_CATG key, PKG_REP_UTILITY.FN_GET_AC_NAME(CAT_CRM_ID, 'CRM_CATG', CAT_TASK_CATG) value FROM M_CRM_AGENTS_TASK_ALLOC WHERE CAT_AGENT_ID = ? ");
            StringBuilder query = new StringBuilder("SELECT DISTINCT CAT_TASK_CATG key, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CATG', CAT_TASK_CATG) value FROM M_CRM_AGENTS_TASK_ALLOC WHERE CAT_AGENT_ID = ? ");
            query.append("ORDER BY 1");
            LOGGER.info("Query :: {} [{}]", new Object[]{query, userId});
            list = executeList(con, query.toString(), new Object[]{userId}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving category list => {}", e);
        } finally {
            try {
                if (getDataSource() != null) {
                    if (con != null) {
                        con.close();
                    }
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

    public List<KeyValue> loadSubCategoryList(final String category, final String userId) {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            if (getDataSource() != null) {
                con = getDBConnection(getDataSource());
            }
            StringBuilder query = new StringBuilder("SELECT DISTINCT CAT_TASK_SUB_CATG key, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_SUB_CATG', CAT_TASK_SUB_CATG) value FROM  M_CRM_AGENTS_TASK_ALLOC WHERE CAT_AGENT_ID = ? AND CAT_TASK_CATG = ? ");
            query.append("ORDER BY 1");
            LOGGER.info("Query :: {} [{}:{}]", new Object[]{query, userId, category});
            list = executeList(con, query.toString(), new Object[]{userId, category}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving sub-category list => {}", e);
        } finally {
            try {
                if (getDataSource() != null) {
                    if (con != null) {
                        con.close();
                    }
                }
            } catch (Exception e) {
            }
        }
        return list;
    }
}
