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
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.DashboardDAO.DateRange;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.vo.MWhatsappAutoMessage;
import qa.com.qic.common.vo.TWhatsappLog;
import qa.com.qic.common.vo.TWhatsappTxn;
import qa.com.qic.common.vo.TWhatsappTxnHist;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class WhatsAppDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(WhatsAppDAO.class);

    private final String dataSource;

    private static int srNo = 0;

    public WhatsAppDAO(String dataSource) throws Exception {
        this.dataSource = dataSource;
        if (dataSource == null) {
            throw new Exception("Connection parameter missing");
        }
    }

  //commented for corporate
    /*public List<TWhatsappTxn> loadUnreadMessages(TWhatsappTxn txn) {
        List<TWhatsappTxn> list = null;
        Connection con = null;
        List param = new LinkedList();
        try {
            con = getDBConnection(getDataSource());
            StringBuilder query = new StringBuilder();
            Object[] params = new Object[]{};
            query.append("SELECT WT_NAME wtName, WT_MOBILE_NO wtMobileNo, WT_DATE wtDate, WT_ASSIGNED_TO wtAssignedTo, WT_MSG_COUNT wtMsgCount, (SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_LOCK_YN = '0' AND CU_USER_ID = WT_ASSIGNED_TO) wtAssignedToDesc, ");
            query.append("WT_TXN_ID wtTxnId, WT_UPD_DT wtUpdDt, WT_CRM_ID wtCrmId, PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', WT_CRM_ID) wtCrmDesc ");
            query.append("FROM T_WHATSAPP_TXN ");
            if (null != txn && StringUtils.isNotBlank(txn.getWtCrmId())) {
                query.append("WHERE WT_CRM_ID = ? ");
                params = new Object[]{txn.getWtCrmId()};
            }
            query.append("ORDER BY WT_UPD_DT DESC");
            LOGGER.info("Load unread messages. Query: {} {}", new Object[]{query, Arrays.toString(params)});
            list = executeList(con, query.toString(), params, TWhatsappTxn.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving unread messages list", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return list;
    }*/

    public List<TWhatsappTxn> loadUnreadMessages(TWhatsappTxn txn) {
        List<TWhatsappTxn> list = null;
        Connection con = null;
        List param = new LinkedList();
        try {
            con = getDBConnection(getDataSource());
            StringBuilder query = new StringBuilder();
            query.append("SELECT WT_NAME wtName, WT_MOBILE_NO wtMobileNo, WT_DATE wtDate, WT_ASSIGNED_TO wtAssignedTo, WT_MSG_COUNT wtMsgCount, NVL(WT_FLEX_01, '0') wtFlex01, ");
            query.append("(SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_LOCK_YN = '0' AND CU_USER_ID = WT_ASSIGNED_TO) wtAssignedToDesc, WT_TXN_ID wtTxnId, ");
            query.append("COALESCE(WT_UPD_DT,WT_DATE) wtUpdDt FROM T_WHATSAPP_TXN ");
            if (txn == null) {
                query.append("ORDER BY WT_UPD_DT DESC");
            } else {
                query.append("WHERE EXISTS (SELECT 1 FROM T_WHATSAPP_LOG WHERE LOWER(WL_TEXT) LIKE ? ");
                query.append("AND WL_MSG_MODE = ? AND WL_TEXT IS NOT NULL AND WT_MOBILE_NO = WL_MOBILE_NO ");
                query.append("AND WL_LOG_DATE >= SYSDATE - 120)");
                if (StringUtils.isNoneBlank(txn.getWtName())) {
                    param.add("%" + txn.getWtName().toLowerCase() + "%");
                }
                param.add(txn.getWtFlex01());
            }
            Object[] params = param.toArray();
            list = executeList(con, query.toString(), params, TWhatsappTxn.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving unread messages list", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

    public List<TWhatsappTxnHist> loadWhatsAppHistoryData(TWhatsappTxnHist txnHist) {
        List<TWhatsappTxnHist> list = null;
        Connection con = null;
        StringBuilder query = null;
        List param = new LinkedList();
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT WH_NAME whName, WH_MOBILE_NO whMobileNo, WH_DATE whDate,WH_CLOSE_DT whCloseDt, ");
            query.append("(SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_USER_ID = WH_ASSIGNED_TO) whAssignedTo, ");
            query.append("WH_TXN_ID whTxnId, WH_UPD_DT whUpdDt FROM T_WHATSAPP_TXN_HIST WHERE ");

            if (StringUtils.isNoneBlank(txnHist.getWhAssignedTo())) {
                query.append("WH_ASSIGNED_TO = ? ");
                param.add(txnHist.getWhAssignedTo());
            }
            if (txnHist.getWhFromDate() != null && txnHist.getWhToDate() != null) {
                if (!param.isEmpty()) {
                    query.append("AND ");
                }
                query.append("TRUNC(WH_DATE) BETWEEN TRUNC(?) AND TRUNC(?) ");
                param.add(new java.sql.Date(txnHist.getWhFromDate().getTime()));
                param.add(new java.sql.Date(txnHist.getWhToDate().getTime()));
            }
            if (StringUtils.isNoneBlank(txnHist.getWhMobileNo())) {
                if (!param.isEmpty()) {
                    query.append("AND ");
                }
                query.append("WH_MOBILE_NO = ? ");
                param.add(txnHist.getWhMobileNo());
            }
            query.append("ORDER BY WH_DATE DESC");
            Object[] data = param.toArray();
            list = executeList(con, query.toString(), data, TWhatsappTxnHist.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving unread messages list", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

    public List<TWhatsappTxnHist> loadWhatsAppHistoryDataByMessage(TWhatsappTxnHist txnHist) {
        List<TWhatsappTxnHist> list = null;
        Connection con = null;
        StringBuilder query;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT WH_MOBILE_NO whMobileNo, MAX(WH_DATE) whDate, MAX(WH_CLOSE_UID) WH_CLOSE_UID, MAX(WH_CLOSE_DT) whCloseDt,MAX(WL_TEXT) WL_TEXT ");
            query.append("FROM T_WHATSAPP_LOG, T_WHATSAPP_TXN_HIST WHERE ");
            query.append("LOWER(WL_TEXT) LIKE ? AND WL_MSG_MODE = ? AND WL_TEXT IS NOT NULL ");
            query.append("AND WH_MOBILE_NO = WL_MOBILE_NO AND WL_LOG_DATE >= SYSDATE - 120 ");
            query.append("GROUP BY WH_MOBILE_NO");
            Object[] params = new Object[]{"%" + txnHist.getWhName().toLowerCase() + "%", txnHist.getWhFlex01()};
            list = executeList(con, query.toString(), params, TWhatsappTxnHist.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving unread messages list", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

  //commented for corporate
    /*public List<TWhatsappLog> fetchWhatsAppLog(final String mobile, final String crmId) {
        return fetchWhatsAppLog(mobile, crmId, null);
    }

    private List<TWhatsappLog> fetchWhatsAppLog(final String mobile, final String crmId, final Long logId) {
        List<TWhatsappLog> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT WL_LOG_ID wlLogId, WL_PARENT_LOG_ID wlParentLogId, WL_MOBILE_NO wlMobileNo, WL_LOG_DATE wlLogDate, WL_MSG_MODE wlMsgMode, WL_TEXT wlText, WL_MSG_URL wlMsgUrl, WL_FILE_PATH wlFilePath, WL_DELIVERD_YN wlDeliverdYn, WL_READ_YN wlReadYn, WL_ERROR_ID wlErrorId, WL_ERROR_MSG wlErrorMsg, WL_MSG_ID wlMsgId FROM T_WHATSAPP_LOG WHERE "
                    + (StringUtils.isNotBlank(mobile) ? "WL_MOBILE_NO = ?" : "WL_LOG_ID = ?") + " ORDER BY WL_LOG_DATE";
            StringBuilder query = new StringBuilder();
            LinkedList<Object> params = new LinkedList();
            query.append("SELECT WL_LOG_ID wlLogId, WL_MOBILE_NO wlMobileNo, WL_LOG_DATE wlLogDate, WL_MSG_MODE wlMsgMode, WL_TEXT wlText, WL_MSG_URL wlMsgUrl, WL_FILE_PATH wlFilePath, WL_DELIVERD_YN wlDeliverdYn, WL_READ_YN wlReadYn, WL_ERROR_ID wlErrorId, WL_ERROR_MSG wlErrorMsg, WL_MSG_ID wlMsgId FROM T_WHATSAPP_LOG WHERE ");
            if(StringUtils.isNotBlank(crmId)) {
                query.append("WL_CRM_ID = ? ");
                params.add(crmId);
            }
            if(StringUtils.isNotBlank(mobile)) {
                query.append("AND WL_MOBILE_NO = ? ");
                params.add(mobile);
            } else if(null != logId) {
                query.append("WL_LOG_ID = ? ");
                params.add(logId);
            }
            query.append("ORDER BY WL_LOG_DATE");
            //" OFFSET " + fetch + " ROWS FETCH NEXT 20 ROWS ONLY"
            LOGGER.info("Fetching WhatsApp log. Query: {}, Mobile: {}, CRM ID: {}, Log Id: {}", new Object[]{query, mobile, crmId, logId});
            list = executeList(con, query.toString(), params.toArray(), TWhatsappLog.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving message log list", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return list;
    }*/

    public List<TWhatsappLog> fetchWhatsAppLog(final String mobile) {
        return fetchWhatsAppLog(mobile, null);
    }

    private List<TWhatsappLog> fetchWhatsAppLog(final String mobile, final Long logId) {
        List<TWhatsappLog> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT WL_LOG_ID wlLogId, WL_PARENT_LOG_ID wlParentLogId, WL_MOBILE_NO wlMobileNo, WL_LOG_DATE wlLogDate, WL_MSG_MODE wlMsgMode, WL_TEXT wlText, WL_MSG_URL wlMsgUrl, WL_FILE_PATH wlFilePath, WL_DELIVERD_YN wlDeliverdYn, WL_READ_YN wlReadYn, WL_ERROR_ID wlErrorId, WL_ERROR_MSG wlErrorMsg, WL_MSG_ID wlMsgId FROM T_WHATSAPP_LOG WHERE "
                    + (StringUtils.isNotBlank(mobile) ? "WL_MOBILE_NO = ?" : "WL_LOG_ID = ?") + " ORDER BY WL_LOG_DATE";
            //" OFFSET " + fetch + " ROWS FETCH NEXT 20 ROWS ONLY"
            LOGGER.info("Fetching WhatsApp log. Mobile: {}, Log Id: {}", new Object[]{mobile, logId});
            list = executeList(con, query, new Object[]{(StringUtils.isNotBlank(mobile) ? mobile : logId)}, TWhatsappLog.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving message log list", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

    public TWhatsappLog fetchWhatsAppLog(final Long logId) {
        TWhatsappLog log = null;
      //commented for corporate
        //List<TWhatsappLog> list = fetchWhatsAppLog(null, null, logId);
        List<TWhatsappLog> list = fetchWhatsAppLog(null, logId);
        if (list != null && !list.isEmpty()) {
            log = list.get(0);
        }
        return log;
    }

    public void resetUnreadMsgCount(final Long txnId) {
        PreparedStatement prs = null;
        int recCnt;
        Connection con = null;
        try {
            LOGGER.info("Reset unread messgae count. TxnId: {}", new Object[]{txnId});
            con = getDBConnection(dataSource);
            String sql = "UPDATE T_WHATSAPP_TXN SET WT_FLEX_01 = '0' WHERE WT_TXN_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setLong(1, txnId);
            recCnt = prs.executeUpdate();
            if (recCnt > 0) {
                LOGGER.info("Reset unread messgae successfull. TxnId: {}", new Object[]{txnId});
            }
        } catch (Exception e) {
            LOGGER.error("Error while reset message count", e);
        } finally {
            closeDBComp(prs, null, con);
        }
    }

    public void assignNewUser(final TWhatsappTxn txn) throws Exception {
        PreparedStatement prs = null;
        int recCnt;
        Connection con = null;
        try {
            LOGGER.debug("Assigning new user {} to {}", new Object[]{txn.getWtAssignedTo(), txn.getWtTxnId()});
            con = getDBConnection(dataSource);
            String sql = "UPDATE T_WHATSAPP_TXN SET WT_ASSIGNED_TO = ?, WT_ASSIGNED_DT = SYSDATE WHERE WT_TXN_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setString(1, txn.getWtAssignedTo());
            prs.setLong(2, txn.getWtTxnId());
            recCnt = prs.executeUpdate();
            if (recCnt > 0) {
                LOGGER.info("Assigned new user {} to {}", new Object[]{txn.getWtAssignedTo(), txn.getWtTxnId()});
            } else {
                throw new Exception("Unable to assign " + txn.getWtAssignedTo());
            }
        } catch (Exception e) {
            LOGGER.error("Error while updating assigned to", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
    }

    public void updateTxnName(final TWhatsappTxn txn) throws Exception {
        PreparedStatement prs = null;
        int rec_cnt = 0;
        Connection con = null;
        Object[] params = null;
        try {
            LOGGER.debug("Updating name {} to {}", new Object[]{txn.getWtName(), txn.getWtTxnId()});
            con = getDBConnection(dataSource);
            String sql = "UPDATE T_WHATSAPP_TXN SET WT_NAME = ? WHERE WT_TXN_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setString(1, txn.getWtName());
            prs.setLong(2, txn.getWtTxnId());
            rec_cnt = prs.executeUpdate();
            if (rec_cnt > 0) {
                LOGGER.info("Updated name {} to {}", new Object[]{txn.getWtName(), txn.getWtTxnId()});
              //commented for corporate
                /*String query = "SELECT WC_MOBILE_NO FROM M_WHATSAPP_CONTACTS WHERE WC_MOBILE_NO = ?";
                PreparedStatement ps1 = con.prepareStatement(query);
                ps1.setString(1, txn.getWtMobileNo());*/
                String query = "SELECT WC_MOBILE_NO FROM M_WHATSAPP_CONTACTS WHERE WC_MOBILE_NO = " + txn.getWtMobileNo();
                PreparedStatement ps1 = con.prepareStatement(query);
                ResultSet rs = ps1.executeQuery();
                if (rs.next()) {
                    String sql1 = "UPDATE M_WHATSAPP_CONTACTS SET WC_NAME = ? WHERE WC_MOBILE_NO = ?";
                    params = new Object[]{txn.getWtName(), txn.getWtMobileNo()};
                    rec_cnt = executeUpdate(con, sql1, params);
                } else {
                    query = "INSERT INTO M_WHATSAPP_CONTACTS(WC_MOBILE_NO, WC_NAME, WC_CR_UID, WC_CR_DT) VALUES (?, ?, ?, SYSDATE)";
                    prs = con.prepareStatement(query);
                    prs.setString(1, txn.getWtMobileNo());
                    prs.setString(2, txn.getWtName());
                    prs.setString(3, txn.getWtAssignedTo());
                    prs.executeUpdate();
                }
                rs.close();
                prs.close();
            } else {
                throw new Exception("Unable to update " + txn.getWtName());
            }
        } catch (Exception e) {
            LOGGER.error("Error while updating name", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
    }

    public void updateChatReason(final TWhatsappTxn txn) throws Exception {
        PreparedStatement prs = null;
        Connection con = null;
        CallableStatement cs = null;
        int rec_cnt = 0;
        try {
            con = getDBConnection(dataSource);
            con.setAutoCommit(false);
            LOGGER.debug("Updating close remarks. Id: {}, User Id: {}, Close Code: {}", new Object[]{txn.getWtTxnId(), txn.getWtCloseUid(), txn.getWtCloseCode()});
            String sql = "UPDATE T_WHATSAPP_TXN SET WT_CLOSE_DT = SYSDATE, WT_CLOSE_UID = ?, WT_CLOSE_CODE = ?, WT_CLOSE_REMARKS = ? WHERE WT_TXN_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setString(1, txn.getWtCloseUid());
            prs.setString(2, txn.getWtCloseCode());
            prs.setString(3, txn.getWtCloseRemarks());
            prs.setLong(4, txn.getWtTxnId());
            rec_cnt = prs.executeUpdate();
            LOGGER.info("Updated close remarks. Id: {}, Status: {}", new Object[]{txn.getWtTxnId(), rec_cnt});
            if (rec_cnt > 0) {
                /*DbUtils.closeQuietly(prs);
                 prs = con.prepareStatement("INSERT INTO T_WHATSAPP_TXN_HIST(WH_TXN_ID, WH_MOBILE_NO, WH_NAME, WH_DATE, WH_MSG_COUNT, WH_ASSIGNED_TO, WH_ASSIGNED_DT, WH_CLOSE_UID, WH_CLOSE_DT, WH_CLOSE_CODE, WH_CLOSE_REMARKS, WH_SALE_REF_NO, WH_SALE_VALUE, WH_SALE_DT, WH_FLEX_01, WH_FLEX_02, WH_FLEX_03, WH_FLEX_04, WH_FLEX_05, WH_UPD_DT) "
                 + "SELECT WT_TXN_ID, WT_MOBILE_NO, WT_NAME, WT_DATE, WT_MSG_COUNT, WT_ASSIGNED_TO, WT_ASSIGNED_DT, WT_CLOSE_UID, WT_CLOSE_DT, WT_CLOSE_CODE, WT_CLOSE_REMARKS, WT_SALE_REF_NO, WT_SALE_VALUE, WT_SALE_DT, WT_FLEX_01, WT_FLEX_02, WT_FLEX_03, WT_FLEX_04, WT_FLEX_05, WT_UPD_DT FROM T_WHATSAPP_TXN WHERE WT_TXN_ID = ?");
                 prs.setLong(1, txn.getWtTxnId());
                 rec_cnt = prs.executeUpdate();
                 LOGGER.info("WhatsApp log moved to history. Id: {}, Status: {}", new Object[]{txn.getWtTxnId(), rec_cnt});
                 if (rec_cnt > 0) {
                 DbUtils.closeQuietly(prs);
                 prs = con.prepareStatement("DELETE FROM T_WHATSAPP_TXN WHERE WT_TXN_ID = ?");
                 prs.setLong(1, txn.getWtTxnId());
                 rec_cnt = prs.executeUpdate();
                 LOGGER.info("WhatsApp log removed. Id: {}, Status: {}", new Object[]{txn.getWtTxnId(), rec_cnt});
                 }*/

                sql
                        = "DECLARE "
                        + "P_ERROR VARCHAR2(1000) := NULL; "
                        + "L_ID NUMBER(20) := NULL; "
                        + "BEGIN "
                        + "L_ID := ?; "
                        + "INSERT INTO T_WHATSAPP_TXN_HIST (WH_TXN_ID, WH_MOBILE_NO, WH_NAME, WH_DATE, WH_MSG_COUNT, WH_ASSIGNED_TO, WH_ASSIGNED_DT, WH_CLOSE_UID, WH_CLOSE_DT, WH_CLOSE_CODE, WH_CLOSE_REMARKS, WH_SALE_REF_NO, WH_SALE_VALUE, WH_SALE_DT, WH_FLEX_01, WH_FLEX_02, WH_FLEX_03, WH_FLEX_04, WH_FLEX_05, WH_UPD_DT) "
                        + "SELECT WT_TXN_ID, WT_MOBILE_NO, WT_NAME, WT_DATE, WT_MSG_COUNT, WT_ASSIGNED_TO, WT_ASSIGNED_DT, WT_CLOSE_UID, WT_CLOSE_DT, WT_CLOSE_CODE, WT_CLOSE_REMARKS, WT_SALE_REF_NO, WT_SALE_VALUE, WT_SALE_DT, WT_FLEX_01, WT_FLEX_02, WT_FLEX_03, WT_FLEX_04, WT_FLEX_05, WT_UPD_DT FROM T_WHATSAPP_TXN WHERE WT_TXN_ID = L_ID; "
                        + "DELETE FROM T_WHATSAPP_TXN WHERE WT_TXN_ID = L_ID; "
                        + "EXCEPTION "
                        + "WHEN OTHERS THEN "
                        + "P_ERROR := 'Error in moving data : '|| SQLERRM; "
                        + "? := P_ERROR; "
                        + "END;";
                LOGGER.debug("WhatsApp log moving to history. Id: {}", new Object[]{txn.getWtTxnId()});
                cs = con.prepareCall(sql);
                cs.setLong(1, txn.getWtTxnId());
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.executeQuery();
                String msg = cs.getString(2);
                LOGGER.info("WhatsApp log moved to history and removed from log. Id: {}, Status: {}", new Object[]{txn.getWtTxnId(), msg});
                if (StringUtils.isNotBlank(msg)) {
                    throw new Exception(msg);
                }
                con.commit();
            } else {
                con.rollback();
            }
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                }
            }
            LOGGER.error("Error while updating close reason", e);
            throw e;
        } finally {
            DbUtils.closeQuietly(cs);
            DbUtils.closeQuietly(con, prs, null);
        }
    }

    public List<KeyValue> loadTaskSummaryByPeriod(final DateRange dateRange) {
        List<KeyValue> list = null;
        String query = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            query = " SELECT WL_CR_UID key, COUNT(WL_MSG_MODE) value FROM T_WHATSAPP_LOG "
                    + "WHERE WL_MSG_MODE='O' AND TRUNC(WL_LOG_DATE) " + dateRange.getRange() + " "
                    + "GROUP BY WL_CR_UID";
            Object[] date = new Object[]{};
            LOGGER.info("Query :: {} [{}]", new Object[]{query});
            list = executeList(con, query, date, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadWhatsAppDashOpenSummaryByPeriod(final DateRange dateRange, String actionType, final String userId) {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            String query = null;
            con = getDBConnection(getDataSource());
            if ("O".equalsIgnoreCase(actionType)) {
                query = " SELECT WT_ASSIGNED_TO key, COUNT(1) value FROM T_WHATSAPP_TXN,TABLE(FN_GET_CRM_AGENTS(?))"
                        + "WHERE WT_ASSIGNED_TO = USER_ID AND TRUNC(WT_DATE) " + dateRange.getRange() + " "
                        + "GROUP BY WT_ASSIGNED_TO";
            } else if ("CU".equalsIgnoreCase(actionType)) {
                query = " SELECT WH_ASSIGNED_TO key, COUNT(1) value FROM T_WHATSAPP_TXN_HIST,TABLE(FN_GET_CRM_AGENTS(?))"
                        + "WHERE WH_ASSIGNED_TO = USER_ID AND  TRUNC(WH_CLOSE_DT) " + dateRange.getRange() + " "
                        + "GROUP BY WH_ASSIGNED_TO";
            } else if ("CC".equalsIgnoreCase(actionType)) {
                query = " SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CALL_TYP', WH_CLOSE_CODE) key, COUNT(1) value "
                        + "FROM T_WHATSAPP_TXN_HIST, TABLE(FN_GET_CRM_AGENTS(?)) WHERE WH_ASSIGNED_TO = USER_ID "
                        + "AND  TRUNC(WH_CLOSE_DT) " + dateRange.getRange() + " GROUP BY WH_CLOSE_CODE";
            }
            Object[] param = new Object[]{userId};
            LOGGER.info("Query :: {} [{}]", new Object[]{query, userId});
            list = executeList(con, query, param, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadWhatsAppDashMonthlySummaryByPeriod(final DateRange dateRange, String userId) {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT TO_CHAR(MAX(WL_LOG_DATE), 'MON-YYYY') key, SUM(DECODE(WL_MSG_MODE, 'I', 1, 0)) value, "
                    + "SUM(DECODE(WL_MSG_MODE, 'O', 1, 0)) info FROM T_WHATSAPP_LOG "
                    + "WHERE TRUNC(WL_LOG_DATE) " + dateRange.getRange() + " "
                    + "GROUP BY TO_CHAR(WL_LOG_DATE, 'YYYYMM') ORDER BY TO_CHAR(WL_LOG_DATE, 'YYYYMM')";
            Object[] param = new Object[]{};
            LOGGER.info("Query :: {} [{}]", new Object[]{});
            list = executeList(con, query, param, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public Map<String, Object> loadDashMonthlySummaryByCloseCode() {
        Connection con = null;
        Map<String, Object> map = new HashMap<>();
        try {
            con = getDBConnection(getDataSource());
            String qry = "SELECT AC_CODE key, AC_DESC text FROM M_APP_CODES WHERE AC_FLEX_01 = '1' AND AC_TYPE = 'CRM_CALL_TYP' ORDER BY AC_DESC";
            Object[] mappParam = new Object[]{};
            List<KeyValue> catgList = executeList(con, qry, mappParam, KeyValue.class);

            String query = "SELECT TO_CHAR(MAX(WH_DATE), 'MON-YYYY') key, WH_CLOSE_CODE value, COUNT(1) info, AC_DESC info1 "
                    + "FROM T_WHATSAPP_TXN_HIST, M_APP_CODES "
                    + "WHERE TRUNC(WH_CLOSE_DT) >= TRUNC(ADD_MONTHS(SYSDATE, -12)) "
                    + "AND WH_CLOSE_CODE = AC_CODE AND AC_FLEX_01 = '1' AND AC_TYPE = 'CRM_CALL_TYP' "
                    + "GROUP BY WH_CLOSE_CODE, TO_CHAR(WH_CLOSE_DT, 'YYYYMM'), AC_DESC "
                    + "ORDER BY TO_CHAR(WH_CLOSE_DT, 'YYYYMM') ASC";
            Object[] param = new Object[]{};
            LOGGER.info("Query :: {} [{}]", new Object[]{});
            List<KeyValue> list = executeList(con, query, param, KeyValue.class);

            Map<String, List<KeyValue>> monthMapTemp;
            Map<String, List<Integer>> monthDataMap = new HashMap<>();
            if (null != list && !list.isEmpty()) {
                // Year wise group
                monthMapTemp = list.stream().collect(Collectors.groupingBy(KeyValue::getKey, Collectors.toList()));
                for (KeyValue kv : catgList) {
                    // Code wise group
                    List<KeyValue> byCatgList = list.stream().filter(l -> l.getValue().equals(kv.getKey())).collect(Collectors.toList());
                    List<Integer> tempList = new ArrayList<>();
                    if (null != monthMapTemp) {
                        monthMapTemp.forEach((k, v) -> {
                            KeyValue temp = byCatgList.stream().filter(l -> l.getKey().equals(k)).findFirst().orElseGet(() -> {
                                KeyValue empty = new KeyValue();
                                empty.setInfo("0");
                                return empty;
                            });
                            tempList.add(Integer.parseInt(temp.getInfo()));
                        });
                    }
                    monthDataMap.put(kv.getText(), tempList);
                }
                map.put("MON_YEAR", monthMapTemp);
                map.put("DATA", monthDataMap);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            closeDbConn(con);
        }
        return map;
    }

    public void saveWhatsAppAutoForm(final MWhatsappAutoMessage autoMsg, final String userId) {
        String query = null;
        Connection con = null;
        PreparedStatement prs = null;
        try {
            con = getDBConnection(getDataSource());
            query = " INSERT INTO M_WHATSAPP_AUTO_MESSAGE("
                    + "WAM_ID, WAM_NAME, WAM_SR_NO, WAM_EFF_FROM_DATE, WAM_EFF_TO_DATE, WAM_FROM_TIME, WAM_TO_TIME,"
                    + "WAM_REPEAT_YN, WAM_DAY1_YN, WAM_DAY2_YN, WAM_DAY3_YN, WAM_DAY4_YN, WAM_DAY5_YN, "
                    + "WAM_DAY6_YN, WAM_DAY7_YN, WAM_FM_TIME_ON, WAM_TO_TIME_ON, WAM_MESSAGE, WAM_MESSAGE_IMG, "
                    + "WAM_CR_UID, WAM_CR_DT) VALUES (NVL((SELECT MAX(WAM_ID) + 1 FROM M_WHATSAPP_AUTO_MESSAGE), 1),?,?,?,?,?,?, ?,?,?,?,?,?, ?,?,?,?,?,?, ?,SYSDATE)";
            prs = con.prepareStatement(query);
            int j = 0;
            prs.setString(++j, autoMsg.getWamName());
            prs.setInt(++j, ++srNo);
            if (autoMsg.getWamEffFromDate() == null) {
                prs.setNull(++j, Types.DATE);
            } else {
                prs.setTimestamp(++j, new java.sql.Timestamp(autoMsg.getWamEffFromDate().getTime()));
            }
            if (autoMsg.getWamEffToDate() == null) {
                prs.setNull(++j, Types.DATE);
            } else {
                prs.setTimestamp(++j, new java.sql.Timestamp(autoMsg.getWamEffToDate().getTime()));
            }
            prs.setString(++j, autoMsg.getWamFromTime());
            prs.setString(++j, autoMsg.getWamToTime());
            prs.setString(++j, autoMsg.getWamRepeatYn());
            prs.setString(++j, autoMsg.getWamDay1Yn());
            prs.setString(++j, autoMsg.getWamDay2Yn());
            prs.setString(++j, autoMsg.getWamDay3Yn());
            prs.setString(++j, autoMsg.getWamDay4Yn());
            prs.setString(++j, autoMsg.getWamDay5Yn());
            prs.setString(++j, autoMsg.getWamDay6Yn());
            prs.setString(++j, autoMsg.getWamDay7Yn());
            prs.setShort(++j, autoMsg.getWamFmTimeOn());
            prs.setShort(++j, autoMsg.getWamToTimeOn());
            prs.setString(++j, autoMsg.getWamMessage());
            prs.setString(++j, autoMsg.getWamMessageImg());
            prs.setString(++j, userId);
            prs.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
    }

    public List<MWhatsappAutoMessage> loadAutoMessageData() {
        List<MWhatsappAutoMessage> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT WAM_NAME wamName, WAM_EFF_FROM_DATE wamEffFromDate, WAM_EFF_TO_DATE wamEffToDate, WAM_FROM_TIME wamFromTime, WAM_TO_TIME wamToTime, WAM_MESSAGE wamMessage, WAM_MESSAGE_IMG wamMessageImg, WAM_REPEAT_YN wamRepeatYn, "
                    + "WAM_FM_TIME_ON wamFmTimeOn, WAM_TO_TIME_ON wamToTimeOn, WAM_DAY1_YN wamDay1Yn, "
                    + "WAM_DAY2_YN wamDay2Yn, WAM_DAY3_YN wamDay3Yn, WAM_DAY4_YN wamDay4Yn, WAM_DAY5_YN wamDay5Yn, WAM_DAY6_YN wamDay6Yn, WAM_DAY7_YN wamDay7Yn FROM M_WHATSAPP_AUTO_MESSAGE ORDER BY WAM_CR_DT DESC";
            list = executeList(con, query, new Object[]{}, MWhatsappAutoMessage.class);
          //commented for corporate
            /*StringBuilder query = new StringBuilder();
            query.append("SELECT WAM_NAME wamName, WAM_EFF_FROM_DATE wamEffFromDate, WAM_EFF_TO_DATE wamEffToDate, WAM_FROM_TIME wamFromTime, WAM_TO_TIME wamToTime, WAM_MESSAGE wamMessage, WAM_MESSAGE_IMG wamMessageImg, WAM_REPEAT_YN wamRepeatYn, ");
            query.append("WAM_FM_TIME_ON wamFmTimeOn, WAM_TO_TIME_ON wamToTimeOn, WAM_DAY1_YN wamDay1Yn, ");
            query.append("WAM_DAY2_YN wamDay2Yn, WAM_DAY3_YN wamDay3Yn, WAM_DAY4_YN wamDay4Yn, WAM_DAY5_YN wamDay5Yn, WAM_DAY6_YN wamDay6Yn, WAM_DAY7_YN wamDay7Yn FROM M_WHATSAPP_AUTO_MESSAGE ");
            query.append("ORDER BY WAM_CR_DT DESC");
            list = executeList(con, query.toString(), new Object[]{}, MWhatsappAutoMessage.class);*/

        } catch (Exception e) {
            LOGGER.error("Error while retreiving message list", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
        return list;
    }

    /**
     * @return the dataSource
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * For customer 360 view
     *
     * @param log
     * @return
     */
  //commented for corporate
    /*public List<TWhatsappLog> loadLatestConversation(final TWhatsappLog log) {
        List<TWhatsappLog> list = null;
        Connection con = null;
        try {
            String mobNos[] = log.getWlMobileNo().split(",");
            Object[] param;
            LinkedList<Object> params = new LinkedList();
            con = getDBConnection(getDataSource());
            StringBuilder query = new StringBuilder();
            query.append("SELECT WL_LOG_ID wlLogId, WL_MOBILE_NO wlMobileNo, WL_LOG_DATE wlLogDate, WL_MSG_MODE wlMsgMode, WL_MSG_TYPE wlMsgType, WL_TEXT wlText FROM T_WHATSAPP_LOG WHERE ");
            for (int i = 0; i < mobNos.length; i++) {
                String mobCountryCode = ApplicationConstants.getMobileCountryCode(log.getWlCrmId());
                params.add(mobCountryCode + mobNos[i]);
                query.append("WL_MOBILE_NO = ? ");
                if(i < (mobNos.length - 1)) {
                    query.append("AND ");
                }
            }
            if(StringUtils.isNotBlank(log.getWlCrmId())) {
                query.append("AND WL_CRM_ID = ? ");
                params.add(log.getWlCrmId());
            }
            query.append("ORDER BY WL_LOG_DATE OFFSET 0 ROWS FETCH NEXT 20 ROWS ONLY");
            param = params.toArray();
            LOGGER.debug("Query: {} {}", new Object[]{query, Arrays.toString(param)});
            list = executeList(con, query.toString(), param, TWhatsappLog.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Whatsapp history list", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return list;
    }*/

    public List<TWhatsappLog> loadLatestConversation(String watMobileNo) {
        List<TWhatsappLog> list = null;
        Connection con = null;
        try {
            String mobNos[] = watMobileNo.split(",");
            String cond = "";
            Object[] param = new Object[mobNos.length];
            for (int i = 0; i < mobNos.length; i++) {
                param[i] = "974" + mobNos[i];
                cond += "AND WL_MOBILE_NO = ? ";
            }
            cond = cond.substring(4);
            con = getDBConnection(getDataSource());
            String query = "SELECT WL_LOG_ID wlLogId, WL_MOBILE_NO wlMobileNo, WL_LOG_DATE wlLogDate, WL_MSG_MODE wlMsgMode, WL_MSG_TYPE wlMsgType, WL_TEXT wlText FROM T_WHATSAPP_LOG WHERE " + cond + " ORDER BY WL_LOG_DATE OFFSET 0 ROWS FETCH NEXT 20 ROWS ONLY";
            LOGGER.debug("Query: {} [{}]", new Object[]{query, param});
            list = executeList(con, query, param, TWhatsappLog.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving History list", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

    public List<TWhatsappLog> fetchWhatsAppDocLog(final Long txnId) {
        List<TWhatsappLog> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT WL_LOG_ID wlLogId, WL_FILE_PATH wlFilePath FROM T_WHATSAPP_LOG, T_WHATSAPP_TXN "
                    + " WHERE WL_MOBILE_NO = WT_MOBILE_NO AND TRUNC(WL_LOG_DATE) >= TRUNC(WT_DATE) AND WL_MSG_TYPE <> 'T' AND WL_MSG_MODE = 'I'"
                    + " AND WT_TXN_ID = ?";
            LOGGER.info("Fetching WhatsApp Doc list. Txn Id: {}", new Object[]{txnId});
            list = executeList(con, query, new Object[]{txnId}, TWhatsappLog.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Doc list", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                LOGGER.error("", ex);
            }
        }
        return list;
    }

    public List<KeyValue> getWhatsupPermissionUsers() {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = null;
            List<Object> paramList = new LinkedList<>();
            query = new StringBuilder("SELECT CU_USER_ID key, CU_USER_NAME value FROM M_CRM_USER WHERE UPPER(CU_APPL_MODULES) LIKE ? AND CU_AGENT_TYPE != 4 AND CU_LOCK_YN = '0'");
            paramList.add("%\"W\"%");
            data = paramList.toArray();
            LOGGER.info("Query ::{} data {}", query.toString(), data);
            list = executeList(con, query.toString(), data, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while getting userName {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadCountryList() {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT AC_VALUE key, AC_DESC value FROM M_APP_CODES WHERE AC_VALUE IS NOT NULL AND AC_TYPE = 'COUNTRY'";
            list = executeList(con, query, new Object[]{}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while getting country list {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }
}
