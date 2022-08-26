/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import qa.com.qic.common.dao.DashboardDAO.DateRange;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.vo.DataBean;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class TaskDashboardDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(TaskDashboardDAO.class);

    private final String dataSource;

    public TaskDashboardDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    private static final String QUERY_TASK_SLA = "SELECT NVL(SUM(CT_OPEN), 0) + NVL(SUM(CT_ON_HOLD), 0) count1, NVL(SUM(CT_OVER_DUE), 0) count2 FROM TABLE(FN_GET_CRM_AGENTS(?)), V_CRM_TASK_COUNT WHERE USER_ID = CT_ASSIGNED_TO AND TRUNC (CT_CLOSE_DATE) IS NULL";
    /**
     *
     * @param crmId
     * @param type
     * @param userId
     * @return list of task details
     */
    //commented for corporate
    //public List<DataBean> loadTaskSummary(final String crmId, final String type, final String userId) {
    public List<DataBean> loadTaskSummary(final String type, final String userId) {
        List<DataBean> list = null;
        String query = null;
        String selectClause = "SELECT ";
        String whereClause = "FROM TABLE(FN_GET_CRM_AGENTS(?)), V_CRM_TASK_COUNT WHERE USER_ID = CT_ASSIGNED_TO AND TRUNC(CT_CLOSE_DATE) IS NULL ";
      //commented for corporate
        /*if (StringUtils.isNotBlank(crmId)) {
            whereClause += "AND CT_CRM_ID = '" + crmId + "' ";
        }*/
        String groupByClause = "GROUP BY ";
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            if (null != type) {
                switch (type) {
                    case "TECHNICIAN":
                        selectClause += "PKG_REP_UTILITY.FN_GET_USER_NAME(CT_ASSIGNED_TO) data1, ";
                        groupByClause += "CT_ASSIGNED_TO ";
                        break;
                    case "CATEGORY":
                        selectClause += "PKG_REP_UTILITY.FN_GET_AC_NAME( 'CRM_CATG', CT_CATG) data1, ";
                        groupByClause += "CT_CATG ";
                        break;
                    case "PRIORITY":
                        selectClause += "PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_PRIORITY', CT_PRIORITY) data1, ";
                        groupByClause += "CT_PRIORITY ";
                        break;
                }
            }
            selectClause += "NVL(SUM(CT_OPEN), 0) count1, NVL(SUM(CT_ON_HOLD), 0) count2, NVL(SUM(CT_OVER_DUE), 0) count3, NVL(SUM(CT_DUE_TODAY), 0) count4 ";
            query = selectClause + whereClause + groupByClause;
            Object[] date = new Object[]{userId};
            LOGGER.info("Query :: {} [{}]", new Object[]{query, userId});
            list = executeList(con, query, date, DataBean.class);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return (list == null ? new ArrayList<>() : list);
    }

    /**
     *
     * @param crmId
     * @param userId
     * @return total no of open task and SLA violated
     */
  //commented for corporate
    //public List<DataBean> loadTaskSLA(final String crmId, final String userId) {
    public DataBean loadTaskSLA(final String type, final String userId) {
    	//commented for corporate
       // List<DataBean> list = null;
    	DataBean dataBean = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{userId};
            LOGGER.debug("Query :: {} [{}]", new Object[]{QUERY_TASK_SLA, userId});
          //commented for corporate
            //list = executeList(con, QUERY_TASK_SLA + (StringUtils.isNotBlank(crmId) ? " AND CT_CRM_ID = '" + crmId + "'" : ""), date, DataBean.class);
            dataBean = (DataBean) executeQuery(con, QUERY_TASK_SLA, date, DataBean.class);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            closeDbConn(con);
        }
      //commented for corporate
        //return list;
        return dataBean;
    }

    /**
     *
     * @param crmId
     * @param type
     * @param userId
     * @param dateRange
     * @return list of task details by period
     */
    //commented for corporate
    //public List<DataBean> loadTaskSummaryByPeriod(final String crmId, final String type, final String userId, final DateRange dateRange) {
    public List<DataBean> loadTaskSummaryByPeriod(final String type, final String userId, final DateRange dateRange) {
        List<DataBean> list = null;
        String query = null;
        String selectClause = "SELECT ";
        String whereClause = "FROM TABLE(FN_GET_CRM_AGENTS(?)), V_CRM_TASK_COUNT WHERE USER_ID = CT_ASSIGNED_TO AND TRUNC(CT_ASSIGNED_DT) ";
        String groupByClause = "GROUP BY ";
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            if (null != type) {
                switch (type) {
                    case "TECHNICIAN":
                        selectClause += "CT_ASSIGNED_TO data1, PKG_REP_UTILITY.FN_GET_USER_NAME(CT_ASSIGNED_TO) data2, ";
                        groupByClause += "CT_ASSIGNED_TO ";
                        break;
                    case "CATEGORY":
                        selectClause += "PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CATG', CT_CATG) data1, ";
                        groupByClause += "CT_CATG ";
                        break;
                    case "PRIORITY":
                        selectClause += "PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_PRIORITY', CT_PRIORITY) data1, ";
                        groupByClause += "CT_PRIORITY ";
                        break;
                }
            }
            selectClause += "COUNT(1) count1, SUM((CASE WHEN CT_STATUS = 'C' THEN 1 ELSE 0 END)) count2, SUM((CASE WHEN CT_STATUS = 'C' AND TRUNC(CT_CLOSE_DATE) > TRUNC(CT_DUE_DATE) THEN 1 WHEN CT_STATUS <> 'C' AND TRUNC(SYSDATE) > TRUNC(CT_DUE_DATE) THEN 1 ELSE 0 END)) count3, SUM((CASE WHEN CT_STATUS = 'P' THEN 1 ELSE 0 END)) count4 ";
            whereClause += dateRange.getRange();
          //commented for corporate
/*            if (StringUtils.isNotBlank(crmId)) {
                whereClause += "AND CT_CRM_ID = '" + crmId + "' ";
            }
*/            query = selectClause + whereClause + groupByClause;
            Object[] date = new Object[]{userId};
            LOGGER.info("Query :: {} [{}]", new Object[]{query, userId});
            list = executeList(con, query, date, DataBean.class);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return (list == null ? new ArrayList<>() : list);
    }

    /**
     *
     * @param dateRange
     * @return list of task details by period
     */
  //commented for corporate
    //public List<DataBean> loadTaskSlaSummaryByPeriod(final String crmId, final DateRange dateRange) {
    public List<DataBean> loadTaskSlaSummaryByPeriod(final DateRange dateRange) {
        List<DataBean> list = null;
        String query = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            query = "SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CATG', CT_CATG) data1, "
                    + "COUNT(1) count1, SUM(DECODE(CT_STATUS , 'C', 1, 0)) count2, "
                    + "SUM((CASE WHEN TRUNC(CT_CLOSE_DATE) > TRUNC(CT_DUE_DATE) THEN 1 ELSE (CASE WHEN TRUNC(SYSDATE) > TRUNC(CT_DUE_DATE) AND CT_STATUS <> 'C' THEN 1 ELSE 0 END) END)) count3 "
                    + "FROM  T_CRM_TASKS "
                    + "WHERE TRUNC(CT_CR_DT) " + dateRange.getRange() + " "
                  //commented for corporate
                   // + (StringUtils.isNotBlank(crmId) ? "AND CT_CRM_ID = '" + crmId + "' " : "")
                    + "GROUP BY CT_CATG";
            Object[] date = new Object[]{};
            LOGGER.info("Query :: {} [{}]", new Object[]{query});
            list = executeList(con, query, date, DataBean.class);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return (list == null ? new ArrayList<>() : list);
    }

    public String getDataSource() {
        return dataSource;
    }
}
