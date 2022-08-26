/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.dao;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.vo.CrmCallLog;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class DashboardDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(DashboardDAO.class);

    private final String dataSource;

    public DashboardDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public enum DateRange {

        TODAY("Today", "= TRUNC(SYSDATE) "),
        YESTERDAY("Yesterday", "= TRUNC(SYSDATE - 1) "),
        THIS_WEEK("This week", "BETWEEN TRUNC(SYSDATE, 'DAY') AND TRUNC(SYSDATE, 'DAY') + 6 "),
        LAST_WEEK("Last week", "BETWEEN TRUNC(SYSDATE - 7, 'DAY') AND TRUNC(SYSDATE - 7, 'DAY') + 6 "),
        THIS_MONTH("This month", "BETWEEN TRUNC(SYSDATE, 'MONTH') AND TRUNC(LAST_DAY(SYSDATE)) "),
        LAST_MONTH("Last month", "BETWEEN TRUNC(ADD_MONTHS(SYSDATE, -1), 'MONTH') AND TRUNC(LAST_DAY(ADD_MONTHS(SYSDATE, -1))) "),
        THIS_QUARTER("This quarter", "BETWEEN TRUNC(SYSDATE, 'Q') AND ADD_MONTHS(TRUNC(SYSDATE, 'Q'), 3) - 1 "),
        LAST_QUARTER("Last quarter", "BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'Q'), -3) AND ADD_MONTHS(ADD_MONTHS(TRUNC(SYSDATE, 'Q'), -3), 3) - 1 "),
        THIS_YEAR("This year", "BETWEEN TRUNC(SYSDATE, 'YEAR') AND ADD_MONTHS(TRUNC(SYSDATE, 'YEAR'), 12) - 1 "),
        LAST_YEAR("Last year", "BETWEEN TRUNC(ADD_MONTHS(SYSDATE, -12), 'YEAR') AND ADD_MONTHS(TRUNC(ADD_MONTHS(SYSDATE, -12), 'YEAR'), 12) - 1 "),
        CUSTOM("Custom", "BETWEEN TRUNC(TO_DATE('?', 'DD/MM/YYYY')) AND TRUNC(TO_DATE('?', 'DD/MM/YYYY'))");

        final String desc;
        String range;

        DateRange(String desc, String range) {
            this.desc = desc;
            this.range = range;
        }

        public String getDesc() {
            return this.desc;
        }

        public String getRange() {
            return this.range;
        }

        public void setRange(String range) {
            this.range = range;
        }
    }

    public List<KeyValue> loadActivityChartData(final String userId, final DateRange dateRange) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', IL_TYPE) key, COUNT(1) value ");
            query.append("FROM T_INSURED_LOG ");
            query.append("WHERE IL_CR_UID = ? ");
            if (dateRange != null) {
                query.append("AND TRUNC(IL_CR_DT) ").append(dateRange.getRange());
            }
            query.append("GROUP BY IL_TYPE");
            LOGGER.info("Query :: {} [{}]", query, userId);
            list = executeList(con, query.toString(), new Object[]{userId}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving activity chart data => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

  //added for corporate
    public List<KeyValue> loadActivityChartData(final String appType, final String userId, final DateRange dateRange) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', IL_TYPE) key, COUNT(1) value ");
            query.append("FROM T_INSURED_LOG ");
            query.append("WHERE IL_CR_UID = ? ");
            List l = new LinkedList();
            l.add(userId);
            if (StringUtils.isNotBlank(appType)) {
                query.append("AND IL_CRM_ID = ? ");
                l.add(appType);
            }
            if (dateRange != null) {
                query.append("AND TRUNC(IL_CR_DT) ").append(dateRange.getRange());
            }
            query.append("GROUP BY IL_TYPE");
            LOGGER.info("Query :: {} [{}:{}]", query, userId, appType);
            list = executeList(con, query.toString(), l.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving activity chart data => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

  //added for corporate
    /*public List<CrmCallLog> loadCallLogDetails(final String appType, final String userId, final DateRange dateRange) {
        List<CrmCallLog> list = null;
        Connection con = null;
        StringBuilder query = null;
        Object[] param = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CCL_CRM_TYPE \"cclCrmType\", PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) \"cclCrmTypeDesc\", ");
            query.append("COUNT(CCL_CRM_TYPE) \"cclCallRefId\", SUM(CASE WHEN CCL_CRM_TYPE = '008' AND CCL_CALL_REF_ID IS NOT NULL THEN 1 ELSE 0 END) \"cclNotAnswered\"  ");
            query.append("FROM T_CRM_CALL_LOG ");
            query.append("WHERE CCL_CR_UID = ? ");
            List l = new LinkedList();
            l.add(userId);
            if (StringUtils.isNotBlank(appType)) {
                query.append("AND CCL_CRM_ID = ? ");
                l.add(appType);
            }
            if (dateRange != null) {
                query.append("AND TRUNC(CCL_CR_DT) ").append(dateRange.getRange());
            }
            query.append("GROUP BY CCL_CRM_TYPE ");
            LOGGER.info("Query :: {} [{}:{}]", new Object[]{query, userId, appType});
            list = (List<CrmCallLog>) executeList(con, query.toString(), l.toArray(), CrmCallLog.class);
        } catch (Exception e) {
            LOGGER.error("Error while call log list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }*/

    public List<CrmCallLog> loadCallLogDetails(final String userId, final DateRange dateRange) {
        List<CrmCallLog> list = null;
        Connection con = null;
        StringBuilder query = null;
        Object[] param = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CCL_CRM_TYPE \"cclCrmType\", PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) \"cclCrmTypeDesc\", ");
            query.append("COUNT(CCL_CRM_TYPE) \"cclCallRefId\", SUM(CASE WHEN CCL_CRM_TYPE = '008' AND CCL_CALL_REF_ID IS NOT NULL THEN 1 ELSE 0 END) \"cclNotAnswered\"  ");
            query.append("FROM T_CRM_CALL_LOG ");
            query.append("WHERE CCL_CR_UID = ? ");
            if (dateRange != null) {
                query.append("AND TRUNC(CCL_CR_DT) ").append(dateRange.getRange());
            }
            query.append("GROUP BY CCL_CRM_TYPE ");
            LOGGER.info("Query :: {} []", new Object[]{query});
            list = executeList(con, query.toString(), new Object[]{userId}, CrmCallLog.class);
        } catch (Exception e) {
            LOGGER.error("Error while call log list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /**
     *
     * @param appType
     * @param userId
     * @param dateRange
     * @return list of tasks
     */
    //commented for corporate
   /* public List<KeyValue> loadMyTask(final String appType, final String userId, final DateRange dateRange) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_SUB_CATG', CT_SUB_CATG) ");
            if (StringUtils.isBlank(appType)) {
                query.append("|| '(' || PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CT_CRM_ID) || ')' ");
            }
            query.append("key, ");
            query.append("COUNT(1) value ");
            query.append("FROM T_CRM_TASKS ");
            query.append("WHERE CT_ASSIGNED_TO = ? AND CT_STATUS = 'P' ");
            List l = new LinkedList();
            l.add(userId);
            if (StringUtils.isNotBlank(appType)) {
                query.append("AND CT_CRM_ID = ? ");
                l.add(appType);
            }
            if (dateRange != null) {
                query.append("AND TRUNC(CT_ASSIGNED_DT) ").append(dateRange.getRange());
            }
            query.append("GROUP BY CT_CRM_ID, CT_SUB_CATG");
            LOGGER.info("Query :: {} [{}:{}]", query, userId, appType);
            list = (List<KeyValue>) executeList(con, query.toString(), l.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving my task => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }*/

    public List<KeyValue> loadMyTask(final String userId, final DateRange dateRange) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_SUB_CATG', CT_SUB_CATG) key, COUNT(1) value ");
            query.append("FROM T_CRM_TASKS ");
            query.append("WHERE CT_ASSIGNED_TO = ? AND CT_STATUS = 'P' ");
            if (dateRange != null) {
                query.append("AND TRUNC(CT_ASSIGNED_DT) ").append(dateRange.getRange());
            }
            query.append("GROUP BY CT_SUB_CATG");
            LOGGER.info("Query :: {} [{}]", query, userId);
            list = executeList(con, query.toString(), new Object[]{userId}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving my task => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /**
     *
     * @param appType
     * @param userId
     * @param fetch
     * @return list of reminders
     */
  //commented for corporate
    /*public List<KeyValue> loadMyReminders(final String appType, final String userId, String fetch) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            float timezone = 0;
            if(ApplicationConstants.COMPANY_CODE_DUBAI.equals(getDataSource()) || ApplicationConstants.COMPANY_CODE_OMAN.equals(getDataSource())) {
                timezone = 60 / 1440;
            }
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CT_ID key, CT_SUBJECT value, TO_CHAR(CT_DUE_DATE, 'DD/MM/YYYY HH24:MI') info1 ");
            query.append("FROM T_CRM_TASKS ");
            query.append("WHERE CT_ASSIGNED_TO = ? ");
            List l = new LinkedList();
            l.add(userId);
            if (StringUtils.isNotBlank(appType)) {
                query.append("AND CT_CRM_ID = ? ");
                l.add(appType);
            }
            query.append("AND CT_STATUS = 'H' AND SYSDATE BETWEEN ((CT_DUE_DATE - ").append(timezone).append(") - (NVL(CT_REMIND_BEFORE, 0) / 24 / 60)) AND ((CT_DUE_DATE - ").append(timezone).append(") + 2 ) ");
            query.append("ORDER BY CT_DUE_DATE ");
            LOGGER.info("Query :: {} [{}:{}]", query, userId, appType);
            list = (List<KeyValue>) executeList(con, query.toString(), l.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving my reminders => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }*/

    public List<KeyValue> loadMyReminders(final String userId, String fetch) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            float timezone = 0;
            if(ApplicationConstants.COMPANY_CODE_DUBAI.equals(getDataSource()) || ApplicationConstants.COMPANY_CODE_OMAN.equals(getDataSource())) {
                timezone = 60 / 1440;
            }
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CT_ID key, CT_SUBJECT value, TO_CHAR(CT_DUE_DATE, 'DD/MM/YYYY HH24:MI') info1 ");
            query.append("FROM T_CRM_TASKS ");
            query.append("WHERE CT_ASSIGNED_TO = ? ");
            query.append("AND CT_STATUS = 'H' AND SYSDATE BETWEEN ((CT_DUE_DATE - ").append(timezone).append(") - (NVL(CT_REMIND_BEFORE, 0) / 24 / 60)) AND ((CT_DUE_DATE - ").append(timezone).append(") + 2 ) ");
            query.append("ORDER BY CT_DUE_DATE ");
            LOGGER.info("Query :: {} [{}]", query, userId);
            list = executeList(con, query.toString(), new Object[]{userId}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving my reminders => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /**
     *
     * @param appType
     * @param userId
     * @param fetch
     * @return list of recent activities
     */
  //commented for corporate
    /*public List<KeyValue> loadMyRecentActivities(final String appType, final String userId, String fetch) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', IL_TYPE) key, IL_LOG_DETAILS value, TO_CHAR(IL_CR_DT, 'DD/MM/YYYY HH:MM') info1 ");
            query.append("FROM T_INSURED_LOG ");
            query.append("WHERE IL_CR_UID = ? ");
            List l = new LinkedList();
            l.add(userId);
            if (StringUtils.isNotBlank(appType)) {
                query.append("AND IL_CRM_ID = ? ");
                l.add(appType);
            }
            query.append("AND TRUNC(IL_CR_DT) >= TRUNC(SYSDATE) - 10 ");
            query.append("ORDER BY IL_CR_DT DESC ");
            if("ALL".equals(fetch)) {
                query.append("OFFSET 5 ROWS");
            } else {
                query.append("OFFSET 0 ROWS FETCH NEXT 6 ROWS ONLY");
            }
            LOGGER.info("Query :: {} [{}]", query, userId, appType);
            list = (List<KeyValue>) executeList(con, query.toString(), l.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving my recent activities => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }*/

    public List<KeyValue> loadMyRecentActivities(final String userId, String fetch) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', IL_TYPE) key, IL_LOG_DETAILS value, TO_CHAR(IL_CR_DT, 'DD/MM/YYYY HH:MM') info1 ");
            query.append("FROM T_INSURED_LOG ");
            query.append("WHERE IL_CR_UID = ? ");
            query.append("AND TRUNC(IL_CR_DT) >= TRUNC(SYSDATE) - 10 ");
            query.append("ORDER BY IL_CR_DT DESC ");
            if("ALL".equals(fetch)) {
                query.append("OFFSET 5 ROWS");
            } else {
                query.append("OFFSET 0 ROWS FETCH NEXT 6 ROWS ONLY");
            }
            LOGGER.info("Query :: {} [{}]", query, userId);
            list = executeList(con, query.toString(), new Object[]{userId}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving my recent activities => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /**
     *
     * @param userId
     * @param status
     * @param fetch
     * @return list of opportunities
     */
    public List<KeyValue> loadMyOpportunities(final String userId, final String status, String fetch) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_OPP_TYPE', CO_OPP_TYPE) key, CO_REF_NO value, CO_VALUE info1, TO_CHAR(CO_CR_DT, 'DD/MM/YYYY') info2 ");
            query.append("FROM T_CRM_OPPURTUNITIES ");
            query.append("WHERE CO_ASSIGNED_TO = ? ");
            query.append("AND CO_STATUS = ? ");
            query.append("ORDER BY CO_CR_DT DESC ");
            if("ALL".equals(fetch)) {
                query.append("OFFSET 5 ROWS");
            } else {
                query.append("OFFSET 0 ROWS FETCH NEXT 6 ROWS ONLY");
            }
            LOGGER.info("Query :: {} [{}:{}]", new Object[]{query, userId, status});
            list = executeList(con, query.toString(), new Object[]{userId, status}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving my opportunities => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /**
     *
     * @param userId
     * @param dateRange
     * @param fetch
     * @return list of revenue
     */
    public List<KeyValue> loadMyRevenue(final String userId, final DateRange dateRange, String fetch) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_OPP_TYPE', CO_OPP_TYPE) key, NVL(SUM(CO_VALUE), 0) value ");
            query.append("FROM T_CRM_OPPURTUNITIES ");
            query.append("WHERE CO_ASSIGNED_TO = ? AND CO_STATUS = 'S' ");
            if (dateRange != null) {
                query.append("AND TRUNC(CO_CLOSE_DT) ").append(dateRange.getRange());
            }
            query.append("GROUP BY CO_OPP_TYPE ORDER BY CO_OPP_TYPE ASC ");
            if("ALL".equals(fetch)) {
                query.append("OFFSET 5 ROWS");
            } else {
                query.append("OFFSET 0 ROWS FETCH NEXT 6 ROWS ONLY");
            }
            LOGGER.info("Query :: {} [{}]", query, userId);
            list = executeList(con, query.toString(), new Object[]{userId}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving my revenue => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /**
     *
     * @param appType
     * @param userId
     * @param dateRange
     * @param fetch
     * @return list of leads
     */
  //commented for corporate
    /*public List<KeyValue> loadMyLeaderBoard(final String appType, final String userId, final DateRange dateRange, String fetch) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT NVL(PKG_REP_UTILITY.FN_GET_USER_NAME(CCL_CR_UID), CCL_CR_UID) as key, SUM(CASE WHEN CCL_CRM_TYPE <> '008' THEN 1 ELSE 0 END) value, SUM(CASE WHEN CCL_CRM_TYPE = '008' THEN 1 ELSE 0 END) info1 ");
            query.append("FROM T_CRM_CALL_LOG ");
            query.append("WHERE TRUNC(CCL_CALL_DT) ").append(dateRange.getRange());
            List l = new LinkedList();
            if (StringUtils.isNotBlank(appType)) {
                query.append("AND CCL_CRM_ID = ? ");
                l.add(appType);
            }
            query.append("AND CCL_CR_UID IS NOT NULL GROUP BY CCL_CR_UID ORDER BY value DESC ");
            if("ALL".equals(fetch)) {
                query.append("OFFSET 5 ROWS");
            } else {
                query.append("OFFSET 0 ROWS FETCH NEXT 6 ROWS ONLY");
            }
            LOGGER.info("Query :: {} [{}]", query, appType);
            list = (List<KeyValue>) executeList(con, query.toString(), l.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving my leader board => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }*/

    public List<KeyValue> loadMyLeaderBoard(final String userId, final DateRange dateRange, String fetch) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT NVL(PKG_REP_UTILITY.FN_GET_USER_NAME(CCL_CR_UID), CCL_CR_UID) as key, SUM(CASE WHEN CCL_CRM_TYPE <> '008' THEN 1 ELSE 0 END) value, SUM(CASE WHEN CCL_CRM_TYPE = '008' THEN 1 ELSE 0 END) info1 ");
            query.append("FROM T_CRM_CALL_LOG ");
            query.append("WHERE TRUNC(CCL_CALL_DT) ").append(dateRange.getRange());
            query.append("AND CCL_CR_UID IS NOT NULL GROUP BY CCL_CR_UID ORDER BY value DESC ");
            if("ALL".equals(fetch)) {
                query.append("OFFSET 5 ROWS");
            } else {
                query.append("OFFSET 0 ROWS FETCH NEXT 6 ROWS ONLY");
            }
            LOGGER.info("Query :: {} [{}]", query);
            list = executeList(con, query.toString(), new Object[]{}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving my leader board => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /**
     *
     * @param userId
     * @param dateRange
     * @param fetch
     * @return list of leads
     */
    public List<KeyValue> loadMyLeads(final String userId, final DateRange dateRange, String fetch) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CL_NAME key, CL_MOBILE_NO value, TO_CHAR(CL_ASSIGNED_DT, 'DD/MM/YYYY HH:MM') info1, PKG_REP_UTILITY.FN_GET_AC_NAME('NATIONALITY', CL_NATIONALITY) info2 ");
            query.append("FROM T_CRM_LEADS ");
            query.append("WHERE CL_ASSIGNED_TO = NVL(?, CL_ASSIGNED_TO) AND CL_STATUS = 'P' ");
            if (dateRange != null) {
                query.append("AND TRUNC(CL_ASSIGNED_DT) ").append(dateRange.getRange());
            }
            query.append("ORDER BY CL_ASSIGNED_TO DESC ");
            if("ALL".equals(fetch)) {
                query.append("OFFSET 5 ROWS");
            } else {
                query.append("OFFSET 0 ROWS FETCH NEXT 6 ROWS ONLY");
            }
            LOGGER.info("Query :: {} [{}]", query, userId);
            list = executeList(con, query.toString(), new Object[]{userId}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving my leads => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public String getDataSource() {
        return dataSource;
    }
}
