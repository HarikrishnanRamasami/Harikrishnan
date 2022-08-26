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

import javax.validation.ConstraintViolationException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.CrmCallLog;
import qa.com.qic.common.vo.CrmHolidays;
import qa.com.qic.common.vo.CrmUser;
import qa.com.qic.common.vo.DataBean;
import qa.com.qic.common.vo.MAppCodes;
import qa.com.qic.common.vo.MCrmAgentsTaskAlloc;
import qa.com.qic.common.vo.TMailCampaignImages;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class AdminDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(AdminDAO.class);

    private final String dataSource;

    public AdminDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    private final static String QRY_CRM_USER_FOR_LIST = "SELECT CU_USER_ID userId, CU_LDAP_ID userLdapId, CU_USER_NAME userName, CU_TEL_NO userTelNo, NVL(CU_AMDIN_YN, 0) userAdminYn, CU_EMAIL_ID  userEmailId, CU_MOBILE_NO userMobileNo, CU_LOCK_YN  userLockYn, (SELECT COUNT(CA_USER_ID) FROM T_CRM_AGENTS WHERE CA_USER_ID = CU_USER_ID) userCrmAgentYn, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_TEAM', CU_TEAM) userTeamDesc, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LEVEL', CU_ROLE_SEQ) userRoleSeqDesc, CU_LAST_LOGIN_DT userLastLoginDt, (CASE WHEN TRUNC(SYSDATE) = TRUNC(CU_LAST_LOGIN_DT) THEN 1 ELSE 0 END) userLoginStatus FROM M_CRM_USER  ORDER BY CU_USER_NAME";

    public List<CrmUser> loadCrmUserList() {
        List<CrmUser> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            list = executeList(con, QRY_CRM_USER_FOR_LIST, null, CrmUser.class);
        } catch (Exception e) {
            LOGGER.error("Error while user list => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public void saveTaskAttendeeData(final String data, final String userId) throws Exception {
        Connection con = null;
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            sql = "DELETE FROM T_CRM_AGENTS";
            int recCnt = executeUpdate(con, sql, new Object[]{});
            LOGGER.info("Deleted {}", recCnt);
            sql = "INSERT INTO T_CRM_AGENTS (CA_USER_ID, CA_CR_UID, CA_CR_DT) VALUES (?, ?, SYSDATE)";
            String s[] = data.split(",");
            Object[][] params = new Object[s.length][2];
            for (int i = 0; i < s.length; i++) {
                params[i][0] = s[i];
                params[i][1] = userId;
            }
            QueryRunner run = new QueryRunner();
            int recsCnt[] = run.batch(con, sql, params);
            LOGGER.info("Inserted {}", recsCnt);
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while save user task entry => {}", e);
            throw e;
        } finally {
            DbUtils.close(con);
        }
    }
  //commented for corporate
// CRM SMS
    /*public List<CrmCallLog> loadCrmCallLogList(CrmCallLog log) {
        List<CrmCallLog> list = null;
        Connection con = null;
        StringBuilder query = null;
        Object[] param = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CCL_ID cclId, CCL_TYPE cclType, CCL_EXT_ID cclExtId, DECODE(CCL_CR_UID, '").append(ApplicationConstants.ABANDONED_CALL_USER_ID).append("', 'Abandoned', PKG_REP_UTILITY.FN_GET_USER_NAME(CCL_CR_UID)) cclCrUid, ");
            query.append("CCL_CALL_NO cclCallNo, TO_CHAR(CCL_CALL_DT, 'DD/MM/YYYY HH:MI:SS AM') cclCallDt, CCL_DURATION cclDurationDesc, TO_CHAR(CCL_CR_DT, 'DD/MM/YYYY HH:MI:SS AM') cclCrDt, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) cclCrmTypeDesc, CCL_CRM_TYPE cclCrmType, CCL_CALL_REF_ID cclCallRefId, CCL_REMARKS cclRemarks, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CALL_TYP', CCL_CALL_CODE) cclCallCodeDesc, CCL_CALL_CODE cclCallCode, CCL_CIVIL_ID cclCivilId, CCL_REF_ID cclRefId, ");
            query.append("CCL_REF_NAME cclRefName, CCL_FLEX_1 cclFlex1, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_DNIS', CCL_FLEX_2) cclFlex2, CCL_FILE_PATH cclFilePath, CCL_FLEX_3 cclFlex3, CCL_FB_LANGUAGE cclFbLanguage, ");
            query.append("CCL_CRM_ID cclCrmId, PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CCL_CRM_ID) cclCrmDesc FROM T_CRM_CALL_LOG WHERE ");
            List l = new LinkedList();
            if (StringUtils.isNotBlank(log.getCclCrmId())) {
                query.append("CCL_CRM_ID = ? AND ");
                l.add(log.getCclCrmId());
            }
            if (log.getCclId() != null && log.getCclId() > 0) {
                query.append("CCL_ID = ? ");
                l.add(log.getCclId());
            } else if (StringUtils.isNotBlank(log.getCclCallNo())) {
                query.append("CCL_CALL_NO = ? ");
                l.add(log.getCclCallNo().trim());
            } else {
                query.append("CCL_CR_DT BETWEEN TO_DATE(?, 'DD/MM/YYYY HH:MI AM') AND TO_DATE(?, 'DD/MM/YYYY HH:MI AM') ");
                String s[] = log.getCclCallDt().split(" - ");
                l.add(s[0]);
                l.add(s[1]);
                if (TypeConstants.CallType.ABANDONED.getCode().equals(log.getCclCrmType())) {
                    query.append("AND CCL_CR_UID = '").append(ApplicationConstants.ABANDONED_CALL_USER_ID).append("' AND CCL_CRM_TYPE = '").append(TypeConstants.CallType.MISSED.getCode()).append("' ");
                } else {
                    if (StringUtils.isNotBlank(log.getCclCrUid())) {
                        query.append("AND CCL_CR_UID = ? ");
                        l.add(log.getCclCrUid());
                    } else {
                        query.append("AND EXISTS (SELECT USER_ID FROM TABLE(FN_GET_CRM_AGENTS(?)) WHERE USER_ID = CCL_CR_UID");
                        if (StringUtils.isBlank(log.getCclCrmType())) {
                            query.append(" UNION SELECT '").append(ApplicationConstants.ABANDONED_CALL_USER_ID).append("' FROM DUAL");
                        }
                        query.append(") ");
                        l.add(log.getCclFlex3());
                    }
                    if (StringUtils.isNoneBlank(log.getCclCrmType())) {
                        query.append("AND CCL_CRM_TYPE = ? ");
                        l.add(log.getCclCrmType());
                    }
                }
                if ((TypeConstants.CallType.MISSED.getCode().equals(log.getCclCrmType()) || "AC".equals(log.getCclCrmType())) && log.getCclNotAnswered() != null && log.getCclNotAnswered() == 1) {
                    query.append("AND CCL_CALL_REF_ID IS NULL ");
                }
                query.append("ORDER BY CCL_CR_DT");
                param = l.toArray();
            }
            String s = "";
            for (Object p : param) {
                s += "," + p;
            }
            param = l.toArray();
            LOGGER.info("Query :: {} [{}]", new Object[]{query, Arrays.toString(param)});
            list = executeList(con, query.toString(), param, CrmCallLog.class);
        } catch (Exception e) {
            LOGGER.error("Error while call log list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }*/

    public List<CrmCallLog> loadCrmCallLogList(CrmCallLog log) {
        List<CrmCallLog> list = null;
        Connection con = null;
        StringBuilder query = null;
        Object[] param = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CCL_ID cclId, CCL_TYPE cclType, CCL_EXT_ID cclExtId, DECODE(CCL_CR_UID, '").append(ApplicationConstants.ABANDONED_CALL_USER_ID).append("', 'Abandoned', PKG_REP_UTILITY.FN_GET_USER_NAME(CCL_CR_UID)) cclCrUid, ");
            query.append("CCL_CALL_NO cclCallNo, TO_CHAR(CCL_CALL_DT, 'DD/MM/YYYY HH:MI:SS AM') cclCallDt, CCL_DURATION cclDurationDesc, TO_CHAR(CCL_CR_DT, 'DD/MM/YYYY HH:MI:SS AM') cclCrDt, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) cclCrmTypeDesc, CCL_CRM_TYPE cclCrmType, CCL_CALL_REF_ID cclCallRefId, CCL_REMARKS cclRemarks, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CALL_TYP', CCL_CALL_CODE) cclCallCodeDesc, CCL_CALL_CODE cclCallCode, CCL_CIVIL_ID cclCivilId, CCL_REF_ID cclRefId, ");
            query.append("CCL_REF_NAME cclRefName, CCL_FLEX_1 cclFlex1, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_DNIS', CCL_FLEX_2) cclFlex2, CCL_FILE_PATH cclFilePath, CCL_FLEX_3 cclFlex3, CCL_FB_LANGUAGE cclFbLanguage  FROM T_CRM_CALL_LOG WHERE ");
            if (log.getCclId() != null && log.getCclId() > 0) {
                query.append("CCL_ID = ? ");
                param = new Object[]{log.getCclId()};
            } else if (StringUtils.isNotBlank(log.getCclCallNo())) {
                query.append("CCL_CALL_NO = ? ");
                param = new Object[]{log.getCclCallNo().trim()};
            } else {
                query.append("CCL_CR_DT BETWEEN TO_DATE(?, 'DD/MM/YYYY HH:MI AM') AND TO_DATE(?, 'DD/MM/YYYY HH:MI AM') ");
                List l = new LinkedList();
                String s[] = log.getCclCallDt().split(" - ");
                l.add(s[0]);
                l.add(s[1]);
                if (TypeConstants.CallType.ABANDONED.getCode().equals(log.getCclCrmType())) {
                    query.append("AND CCL_CR_UID = '").append(ApplicationConstants.ABANDONED_CALL_USER_ID).append("' AND CCL_CRM_TYPE = '").append(TypeConstants.CallType.MISSED.getCode()).append("' ");
                } else {
                    if (StringUtils.isNotBlank(log.getCclCrUid())) {
                        query.append("AND CCL_CR_UID = ? ");
                        l.add(log.getCclCrUid());
                    } else {
                        query.append("AND EXISTS (SELECT USER_ID FROM TABLE(FN_GET_CRM_AGENTS(?)) WHERE USER_ID = CCL_CR_UID");
                        if (StringUtils.isBlank(log.getCclCrmType())) {
                            query.append(" UNION SELECT '").append(ApplicationConstants.ABANDONED_CALL_USER_ID).append("' FROM DUAL");
                        }
                        query.append(") ");
                        l.add(log.getCclFlex3());
                    }
                    if (StringUtils.isNoneBlank(log.getCclCrmType())) {
                        query.append("AND CCL_CRM_TYPE = ? ");
                        l.add(log.getCclCrmType());
                    }
                }
                if ((TypeConstants.CallType.MISSED.getCode().equals(log.getCclCrmType()) || "AC".equals(log.getCclCrmType())) && log.getCclNotAnswered() != null && log.getCclNotAnswered() == 1) {
                    query.append("AND CCL_CALL_REF_ID IS NULL ");
                }
                query.append("ORDER BY CCL_CR_DT");
                param = l.toArray();
            }
            String s = "";
            for (Object p : param) {
                s += "," + p;
            }
            LOGGER.info("Query :: {} [{}]", new Object[]{query, s});
            list = executeList(con, query.toString(), param, CrmCallLog.class);
        } catch (Exception e) {
            LOGGER.error("Error while call log list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<CrmCallLog> loadCallLogSummaryList(CrmCallLog log, final String operation) {
        List<CrmCallLog> list = null;
        Connection con = null;
        StringBuilder query = null;
        Object[] param = null;
        try {
            con = getDBConnection(getDataSource());
            /*query = new StringBuilder("SELECT CCL_CRM_TYPE \"cclCrmType\", PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) \"cclCrmTypeDesc\", ");
            query.append("COUNT(CCL_CRM_TYPE) \"cclCallRefId\", SUM(CASE WHEN CCL_CRM_TYPE = '008' AND CCL_CALL_REF_ID IS NOT NULL THEN 1 ELSE 0 END) \"cclNotAnswered\" ");
            if ("user".equals(operation)) {
                query.append(", PKG_REP_UTILITY.FN_GET_USER_NAME(CCL_CR_UID) \"cclCrUid\" ");
            }*/
            query = new StringBuilder("SELECT CASE WHEN CCL_CRM_TYPE = '004' AND  CCL_CR_UID <> 'abandoned' THEN PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) "
                    + "WHEN CCL_CRM_TYPE = '005' AND CCL_CR_UID <> 'abandoned' THEN PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) "
                    + "WHEN CCL_CRM_TYPE = '008' AND CCL_CR_UID <> 'abandoned' THEN PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) "
                    + "ELSE 'Abandoned Calls' END \"cclCrmTypeDesc\", "
                    + "MIN(CCL_CRM_TYPE) \"cclCrmType\", "
                    + "SUM(CASE WHEN CCL_CRM_TYPE = '004' AND CCL_CR_UID <> 'abandoned' THEN 1 "
                    + "WHEN CCL_CRM_TYPE = '005' AND CCL_CR_UID <> 'abandoned' THEN 1 "
                    + "WHEN CCL_CRM_TYPE = '008' AND CCL_CR_UID <> 'abandoned' THEN 1 "
                    + "WHEN CCL_CRM_TYPE = '008' AND CCL_CR_UID = 'abandoned' THEN 1 "
                    + "END) \"cclCallRefId\", "
                    + "SUM(CASE WHEN CCL_CRM_TYPE = '008' AND  CCL_CALL_REF_ID IS NOT NULL THEN 1 ELSE 0 END) \"cclNotAnswered\" ");
            query.append("FROM T_CRM_CALL_LOG WHERE CCL_CR_DT BETWEEN TO_DATE(?, 'DD/MM/YYYY HH:MI AM') AND TO_DATE(?, 'DD/MM/YYYY HH:MI AM') AND LENGTH(CCL_CALL_NO) > 4 ");
            if (StringUtils.isNotBlank(log.getCclCrUid())) {
                query.append("AND CCL_CR_UID = ? ");
            }
            if (StringUtils.isNotBlank(log.getCclType())) {
                query.append("AND CCL_CRM_TYPE = ? ");
            }
            /*query.append("GROUP BY CCL_CRM_TYPE ");
            if ("user".equals(operation)) {
                query.append(", CCL_CR_UID ");
            }*/
            query.append("GROUP BY CASE WHEN CCL_CRM_TYPE = '004' AND  CCL_CR_UID <> 'abandoned' THEN PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) "
                    + "WHEN CCL_CRM_TYPE ='005' AND  CCL_CR_UID <> 'abandoned' THEN PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) "
                    + "WHEN CCL_CRM_TYPE = '008' AND  CCL_CR_UID <> 'abandoned' THEN PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) "
                    + "ELSE 'Abandoned Calls' END "
                    + "ORDER BY 1");
            String s[] = log.getCclCallDt().split(" - ");
            param = new Object[]{s[0], s[1], log.getCclCrUid(), log.getCclType()};
            if (StringUtils.isBlank(log.getCclCrUid())) {
                param = ArrayUtils.remove(param, 2);
            }
            if (StringUtils.isBlank(log.getCclType())) {
                param = ArrayUtils.remove(param, param.length - 1);
            }
            LOGGER.info("Query :: {} [{}]", new Object[]{query, Arrays.toString(param)});
            list = executeList(con, query.toString(), param, CrmCallLog.class);
        } catch (Exception e) {
            LOGGER.error("Error while call log list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public Map<Integer, String> loadCallLogFeedBack(final Long cclId) {
        CrmCallLog bean = null;
        Connection con = null;
        Map<Integer, String> map = new HashMap<>();
        String sql = "SELECT CCL_FB_OPTION_1 cclFbOption1, CCL_FB_OPTION_2 cclFbOption2, CCL_FB_OPTION_3 cclFbOption3 FROM T_CRM_CALL_LOG  WHERE CCL_ID = ?";
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{sql, cclId});
            Object[] params = new Object[]{cclId};
            bean = (CrmCallLog) executeQuery(con, sql, params, CrmCallLog.class);
            for (int i = 1; i <= 3; i++) {
                map.put(i, BeanUtils.getProperty(bean, "cclFbOption" + i));
            }
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Feedback details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return map;
    }

    public DataBean loadCallLogWaitTimeSummaryHead() {
        DataBean dataBean = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            List<DataBean> list = executeList(con, "SELECT TO_NUMBER(NVL(AC_VALUE, 0))/60 integer1, TO_NUMBER(NVL(AC_FLEX_01, 0))/60 integer2, TO_NUMBER(NVL(AC_FLEX_02, 0))/60 integer3, TO_NUMBER(NVL(AC_FLEX_03, 0))/60 integer4, TO_NUMBER(NVL(AC_FLEX_04, 0))/60 integer5, NVL(AC_VALUE, 0) || '-' || NVL(AC_FLEX_01, 0) || '-' || NVL(AC_FLEX_02, 0) || '-' || NVL(AC_FLEX_03, 0) || '-' || NVL(AC_FLEX_04, 0) data1 FROM M_APP_CODES WHERE AC_TYPE = 'CRM_CALL_SLA'", null, DataBean.class);
            if (list != null && !list.isEmpty()) {
                dataBean = list.get(0);
            }
        } catch (Exception e) {
            LOGGER.error("Error - Load call log wait time header => {}", e);
        } finally {
            closeDbConn(con);
        }
        return dataBean;
    }

    public List<DataBean> loadCallLogWaitTimeSummary(CrmCallLog log) {
        List<DataBean> list = null;
        Connection con = null;
        StringBuilder query = null;
        Object[] param = null;
        try {
            String mins[] = log.getCclFlex3().split("-");
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) \"data1\", ");
            query.append("SUM(CASE WHEN CCL_FLEX_3 BETWEEN 0 AND ").append(mins[0]).append(" THEN 1 ELSE 0 END) integer1, ");
            query.append("SUM(CASE WHEN CCL_FLEX_3 BETWEEN ").append(mins[0]).append(" + 1 AND ").append(mins[1]).append(" THEN 1 ELSE 0 END) integer2, ");
            query.append("SUM(CASE WHEN CCL_FLEX_3 BETWEEN ").append(mins[1]).append(" + 1 AND ").append(mins[2]).append(" THEN 1 ELSE 0 END) integer3, ");
            query.append("SUM(CASE WHEN CCL_FLEX_3 BETWEEN ").append(mins[2]).append(" + 1 AND ").append(mins[3]).append(" THEN 1 ELSE 0 END) integer4, ");
            query.append("SUM(CASE WHEN CCL_FLEX_3 BETWEEN ").append(mins[3]).append(" + 1 AND ").append(mins[4]).append(" THEN 1 ELSE 0 END) integer5, ");
            query.append("SUM(CASE WHEN CCL_FLEX_3 > ").append(mins[4]).append(" THEN 1 ELSE 0 END) integer6, COUNT(1) count1 ");
            query.append("FROM (SELECT CCL_CRM_TYPE, CCL_CALL_NO, CCL_CALL_DT, SUBSTR (NVL (CCL_FLEX_3, '00:00:00'), 1, 2) * 3600 + SUBSTR (NVL (CCL_FLEX_3, '00:00:00'), 4, 2) * 60 + SUBSTR (NVL (CCL_FLEX_3, '00:00:00'), 7, 2) CCL_FLEX_3 ");
            query.append("FROM T_CRM_CALL_LOG WHERE CCL_CR_DT BETWEEN TO_DATE(?, 'DD/MM/YYYY HH:MI AM') AND TO_DATE(?, 'DD/MM/YYYY HH:MI AM') AND LENGTH(CCL_CALL_NO) > 4 ");
            query.append("AND CCL_FLEX_3 IS NOT NULL ");
            query.append("AND CCL_CRM_TYPE IN ('008','004') ");
            if (StringUtils.isNoneBlank(log.getCclCrUid())) {
                query.append("AND CCL_CR_UID = ? ");
            }
            query.append("GROUP BY CCL_CRM_TYPE, CCL_CALL_NO, CCL_CALL_DT, CCL_FLEX_3) GROUP BY CCL_CRM_TYPE");
            String s[] = log.getCclCallDt().split(" - ");
            param = new Object[]{s[0], s[1], log.getCclCrUid()};
            if (StringUtils.isBlank(log.getCclCrUid())) {
                param = ArrayUtils.remove(param, 2);
            }
            LOGGER.info("Query :: {} [{}]", new Object[]{query, Arrays.toString(param)});
            list = executeList(con, query.toString(), param, DataBean.class);
        } catch (Exception e) {
            LOGGER.error("Error - Load call log wait time => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public void updateCrmCallLog(CrmCallLog callLog) throws Exception {
        Connection con = null;
        String sql = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            sql = "UPDATE T_CRM_CALL_LOG SET CCL_REMARKS = ?, CCL_CALL_CODE = ?, CCL_REF_ID = ?, CCL_REF_NAME =? WHERE CCL_ID = ?";
            Object[] params = new Object[]{callLog.getCclRemarks(), callLog.getCclCallCode(), callLog.getCclRefId(), callLog.getCclRefName(), callLog.getCclId()};
            recCnt = executeUpdate(con, sql, params);
            LOGGER.debug("Update call log remarks [{}:{}]", params);
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while update userAgent extension => {}", e);
            throw e;
        } finally {
            DbUtils.close(con);
        }
    }

    public List<MAppCodes> loadHandySmsList() {
        List<MAppCodes> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = null;
            query = new StringBuilder("SELECT AC_CODE acCode,AC_VALUE acValue,AC_DESC acDesc  FROM M_APP_CODES WHERE AC_TYPE  = 'CRM_HAND_SMS'");
            LOGGER.info("Query :: {} []", new Object[]{query});
            list = executeList(con, query.toString(), date, MAppCodes.class);
        } catch (Exception e) {
            LOGGER.error("Error while user list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public MAppCodes saveHandySms(final MAppCodes bean, final String operation) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        int i = 0, recCnt = 0;
        String acType = "CRM_HAND_SMS";
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equalsIgnoreCase(operation)) {
                sql = "INSERT INTO M_APP_CODES(AC_TYPE,AC_CODE,AC_VALUE,AC_DESC,AC_LONG_DESC,AC_LONG_DESC_BL,AC_CR_UID,AC_CR_DT)VALUES(:acType,:acCode,:acValue,:acDesc,:acLongDesc,:acLongDescBl,:acCrUid,SYSDATE)";
                ps = con.prepareStatement(sql);
                ps.setString(++i, acType);
                ps.setString(++i, bean.getAcCode());
                ps.setString(++i, bean.getAcValue());
                ps.setString(++i, bean.getAcDesc());
                ps.setString(++i, bean.getAcLongDesc());
                ps.setString(++i, bean.getAcLongDescBl());
                ps.setString(++i, bean.getAcCrUid());
                recCnt = ps.executeUpdate();
            } else if ("edit".equalsIgnoreCase(operation)) {
                sql = "UPDATE M_APP_CODES SET AC_VALUE = ?, AC_DESC = ?, AC_LONG_DESC = ?, AC_LONG_DESC_BL = ?,AC_UPD_UID = ?, AC_UPD_DT = SYSDATE "
                        + " WHERE  AC_TYPE = 'CRM_HAND_SMS' and AC_CODE = ? ";
                LOGGER.info("Query :: {} [{}]", new Object[]{sql, bean.getAcCode()});
                ps1 = con.prepareStatement(sql);
                ps1.setString(1, bean.getAcValue());
                ps1.setString(2, bean.getAcDesc());
                ps1.setString(3, bean.getAcLongDesc());
                ps1.setString(4, bean.getAcLongDescBl());
                ps1.setString(5, bean.getAcUpdUid());
                ps1.setString(6, bean.getAcCode());
                recCnt = ps1.executeUpdate();
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while Handy Sms entry => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return bean;
    }

    public MAppCodes loadHandySmsByCode(final String acCode) {
        MAppCodes bean = null;
        Connection con = null;
        String sql = "SELECT AC_CODE acCode,AC_VALUE acValue,AC_DESC acDesc,AC_LONG_DESC acLongDesc,AC_LONG_DESC_BL acLongDescBl FROM M_APP_CODES WHERE AC_TYPE = 'CRM_HAND_SMS' AND AC_CODE = ?";
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{sql, acCode});
            Object[] params = new Object[]{acCode};
            bean = (MAppCodes) executeQuery(con, sql, params, MAppCodes.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving AppCodes details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return bean;
    }

    public List<TMailCampaignImages> loadCampImagesList() {
        List<TMailCampaignImages> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = null;
            query = new StringBuilder("SELECT MCI_ID mciID,MCI_DESC mciDesc,MCI_EXT mciExt,MCI_MIME mciMime,MCI_CR_UID mciCrUid,MCI_CR_DT mciCrDt FROM T_MAIL_CAMPAIGN_IMAGES");
            LOGGER.info("Query :: {} []", new Object[]{query});
            list = executeList(con, query.toString(), date, TMailCampaignImages.class);
        } catch (Exception e) {
            LOGGER.error("Error while load Images List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public TMailCampaignImages insertUploadedImageDetails(final TMailCampaignImages campaignImages) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            sql = "INSERT INTO T_MAIL_CAMPAIGN_IMAGES(MCI_ID,MCI_DESC,MCI_NAME,MCI_EXT,MCI_MIME,MCI_SIZE,MCI_CR_UID,MCI_CR_DT)"
                    + "VALUES(:mciId,:mciDesc,:mciName,:mciExt,:mciMime,:mciSize,:mciCrUid,SYSDATE)";
            ps = con.prepareStatement(sql);
            ps.setString(++i, campaignImages.getMciId());
            ps.setString(++i, campaignImages.getMciDesc());
            ps.setString(++i, campaignImages.getMciName());
            ps.setString(++i, campaignImages.getMciExt());
            ps.setString(++i, campaignImages.getMciMime());
            ps.setLong(++i, campaignImages.getMciSize());
            ps.setString(++i, campaignImages.getMciCrUid());
            recCnt = ps.executeUpdate();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while Insert Image Entry => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return campaignImages;
    }

    public List<KeyValue> loadTaskAllocateDetails(final String catTaskCatg, final String catTaskSubCatg) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{catTaskCatg, catTaskSubCatg};
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CAT_AGENT_ID key,CAT_ALLOC_RATIO value,"
                    + "(SELECT CU_USER_NAME value FROM M_CRM_USER WHERE CU_USER_ID = CAT_AGENT_ID) as info "
                    + " FROM M_CRM_AGENTS_TASK_ALLOC where CAT_TASK_CATG = ? "
                    + "AND CAT_TASK_SUB_CATG = ?");
            LOGGER.info("Query :: {} {}", query, params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while load Allocation Task List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public String[] getAllocationBy(final String catTaskCatg, final String catTaskSubCatg) {
        String[] str = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getDBConnection(getDataSource());
          //commented for corporate
            //String sql = "SELECT AC_FLEX_01, AC_FLEX_02 from M_APP_CODES WHERE AC_MC_CODE = ? AND AC_CODE = ? AND AC_TYPE = 'CRM_SUB_CATG'";
            //String sql = "SELECT CTG_FLEX_01, CTG_FLEX_02 from M_CRM_TASK_CATG WHERE CTG_MAP_CODE = ? AND CTG_CODE = ? AND CTG_TYPE = 'CRM_SUB_CATG'";
            //LOGGER.info("Query :: {} / Params : {} {}", sql, catTaskCatg, catTaskSubCatg);
            String sql = "SELECT AC_FLEX_01, AC_FLEX_02 from M_APP_CODES WHERE  AC_MC_CODE = ? AND AC_CODE = ? AND AC_TYPE = 'CRM_SUB_CATG'";
            LOGGER.info("Query :: {} / Params : {} {}", sql, catTaskCatg, catTaskSubCatg);
            ps = con.prepareStatement(sql);
            ps.setString(1, catTaskCatg);
            ps.setString(2, catTaskSubCatg);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                str = new String[2];
              //commented for corporate
                /*str[0] = rs.getString(1);
                str[1] = rs.getString(2);*/
                str[0] = rs.getString("AC_FLEX_01");
                str[1] = rs.getString("AC_FLEX_02");
            }
        } catch (Exception e) {
            LOGGER.error("Error while load Allocation Task List", e);
        } finally {
            closeDbConn(con);
        }
        return str;
    }

    public int saveTaskAllocate(String[] catAgentTempId, String[] catAllocTempRatio, MCrmAgentsTaskAlloc mCrmAgentsTask) {
        //int recCnt[] = new int[0];
        int recCent = 0;
        Connection con = null;
        PreparedStatement ps = null;
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] params = new Object[]{mCrmAgentsTask.getCatTaskCatg(), mCrmAgentsTask.getCatTaskSubCatg()};
            sql = "DELETE FROM M_CRM_AGENTS_TASK_ALLOC WHERE CAT_TASK_CATG = ? AND CAT_TASK_SUB_CATG = ?";
            int recCnt1 = executeUpdate(con, sql, params);
            int length = catAgentTempId.length;
            for (int j = 0; j < length; j++) {
                int i = 0;
                sql = "INSERT INTO M_CRM_AGENTS_TASK_ALLOC(CAT_ID,CAT_TASK_CATG,CAT_TASK_SUB_CATG,CAT_AGENT_ID,CAT_ALLOC_RATIO,CAT_CR_UID,CAT_CR_DT)"
                        + "VALUES(S_CAT_ID.NEXTVAL,:catTaskCatg,:catTaskSubCatg,:catAgentId,:catAllocRatio,:catCrUid,SYSDATE)";
                ps = con.prepareStatement(sql);
                ps.setString(++i, mCrmAgentsTask.getCatTaskCatg());
                ps.setString(++i, mCrmAgentsTask.getCatTaskSubCatg());
                ps.setString(++i, catAgentTempId[j]);
                if ("".equals(catAllocTempRatio[j])) {
                    ps.setNull(++i, Types.INTEGER);
                } else {
                    ps.setInt(++i, Integer.valueOf(catAllocTempRatio[j]));
                }
                ps.setString(++i, mCrmAgentsTask.getCatCrUid());
                recCent = ps.executeUpdate();
            }
            con.commit();
            updateAppcodes(mCrmAgentsTask.getUserType(), "", mCrmAgentsTask.getCatTaskCatg(), mCrmAgentsTask.getCatTaskSubCatg());
        } catch (Exception e) {
            LOGGER.error("Error while Task Allocate  entry => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }

        return recCent;
    }

  //commented for corporate
    //@Deprecated
    public String saveCategoryDetails(String flex1, String flex2, String flex3, String operation, String userId) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0;
        String sql = null;
        String result = null;
        try {
            con = getDBConnection(getDataSource());
            String acType = null;
            String acCode = "";
            String acMcCode = null;
            String acMcType = "";
            if ("category".equals(operation)) {
                acType = "CRM_CATG";
            } else {
                acType = "CRM_SUB_CATG";
                acMcCode = flex3;
                acMcType = "CRM_CATG";
            }
            String sql1 = "SELECT NVL(MAX(TO_NUMBER(AC_CODE)), 0) + 1 FROM M_APP_CODES WHERE AC_TYPE = ?";
            ps = con.prepareStatement(sql1);
            ps.setString(1, acType);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
            rs.close();
            ps.close();

            sql = "INSERT INTO M_APP_CODES(AC_TYPE,AC_CODE,AC_MC_CODE,AC_DESC,AC_SHORT_DESC,AC_EFF_FM_DT,AC_EFF_TO_DT,AC_CR_UID,AC_CR_DT,AC_MAST_DEF_CODE, AC_FLEX_01)"
                    + "VALUES(:acType,:acCode,:acMcCode,:acDesc,:acShortDesc,:effFromDt,:effToDt,:userId,SYSDATE,:acMasterDefCode, :acFlex01)";
            ps = con.prepareStatement(sql);
            ps.setString(++i, acType);
            ps.setString(++i, result);
            ps.setString(++i, acMcCode);
            ps.setString(++i, flex1);
            ps.setString(++i, flex2);
            ps.setDate(++i, java.sql.Date.valueOf("1990-01-01"));
            ps.setDate(++i, java.sql.Date.valueOf("2099-12-31"));
            ps.setString(++i, userId);
            ps.setString(++i, acMcType);
            if ("CRM_SUB_CATG".equals(acType)) {
                LOGGER.info("Updating default allocation type as 'User'");
                ps.setString(++i, "U");
            } else {
                ps.setString(++i, "");
            }
            ps.executeUpdate();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while Insert Image Entry => {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }
        return result;
    }

  //added for corporate
    public String saveTaskCategory(String crmId, String desc, String longDesc, String flex3, String operation, String userId) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0;
        String sql = null;
        String result = null;
        try {
            con = getDBConnection(getDataSource());
            String acType = null;
            String acCode = "";
            String acMcCode = null;
            if ("category".equals(operation)) {
                acType = "CRM_CATG";
            } else {
                acType = "CRM_SUB_CATG";
                acMcCode = flex3;
            }
            String sql1 = "SELECT NVL(MAX(TO_NUMBER(CTG_CODE)), 0) + 1 FROM M_CRM_TASK_CATG WHERE CTG_TYPE = ?";
            ps = con.prepareStatement(sql1);
            ps.setString(1, acType);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
            rs.close();
            ps.close();

            sql = "INSERT INTO M_CRM_TASK_CATG(CTG_CRM_ID, CTG_TYPE, CTG_CODE, CTG_MAP_CODE, CTG_DESC, "
                    + "CTG_DESC_BL, CTG_EFF_FM_DT, CTG_EFF_TO_DT, CTG_CR_UID, CTG_CR_DT, "
                    + "CTG_FLEX_01)"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(++i, crmId);
            ps.setString(++i, acType);
            ps.setString(++i, result);
            ps.setString(++i, acMcCode);
            ps.setString(++i, desc);
            ps.setString(++i, longDesc);
            ps.setDate(++i, java.sql.Date.valueOf("1990-01-01"));
            ps.setDate(++i, java.sql.Date.valueOf("2099-12-31"));
            ps.setString(++i, userId);
            if ("CRM_SUB_CATG".equals(acType)) {
                LOGGER.info("Updating default allocation type as 'User'");
                ps.setString(++i, "U");
            } else {
                ps.setString(++i, "");
            }
            ps.executeUpdate();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while Insert Image Entry => {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }
        return result;
    }

    public List<KeyValue> loadTaskSLAList(final String catTaskCatg, final String catTaskSubCatg) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{catTaskCatg, catTaskSubCatg};
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CTS_ID key,PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_PRIORITY',CTS_PRIORITY) value,"
                    + " CTS_SLA_DAYS info "
                    + " FROM M_CRM_TASK_SLA WHERE CTS_CATG=? AND CTS_SUB_CATG=?");
            LOGGER.info("Query :: {} []", params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while load Allocation Task List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public String saveTaskSLAForm(String flex1, String flex2, String flex3, MCrmAgentsTaskAlloc mCrmAgentsTask, String operation, String userId) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0;
        String sql = null;
        String result = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equals(operation)) {

                sql = "INSERT INTO M_CRM_TASK_SLA(CTS_ID,CTS_PRIORITY,CTS_CATG,CTS_SUB_CATG,CTS_SLA_DAYS,CTS_CR_UID,CTS_CR_DT)"
                        + "VALUES(S_CTS_ID.NEXTVAL,:flex1,:catg,:subCatg,:flex2,:userId,SYSDATE)";
                ps = con.prepareStatement(sql);
                ps.setString(++i, flex1);
                ps.setString(++i, mCrmAgentsTask.getCatTaskCatg());
                ps.setString(++i, mCrmAgentsTask.getCatTaskSubCatg());
                ps.setLong(++i, new Long(flex2));
                ps.setString(++i, userId);
                ps.executeUpdate();
                con.commit();
            } else if ("edit".equals(operation)) {
                sql = "UPDATE M_CRM_TASK_SLA SET CTS_PRIORITY =? ,CTS_SLA_DAYS =?,CTS_UPD_UID = ?,CTS_UPD_DT=SYSDATE where CTS_ID=?";
                ps = con.prepareStatement(sql);
                ps.setString(++i, flex1);
                ps.setLong(++i, new Long(flex2));
                ps.setString(++i, userId);
                ps.setString(++i, flex3);
                ps.executeUpdate();
                con.commit();
            } else {
                sql = "DELETE FROM M_CRM_TASK_SLA WHERE CTS_ID=?";
                ps = con.prepareStatement(sql);
                ps.setString(++i, flex3);
                ps.executeUpdate();
                con.commit();
            }
        } catch (Exception e) {
            LOGGER.error("Error while Insert Image Entry => {}", e);
            result = e.getMessage();
        } finally {
            closeDBComp(ps, null, con);
        }
        return result;
    }

    public KeyValue loadTaskAllocateSLADetails(String flex3) {
        KeyValue values = null;
        Object[] params = new Object[]{};
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getDBConnection(getDataSource());
            String sql = "SELECT CTS_ID key,CTS_PRIORITY value,"
                    + " CTS_SLA_DAYS info, CTS_CATG info1, CTS_SUB_CATG info2"
                    + " FROM M_CRM_TASK_SLA WHERE CTS_ID=?";
            LOGGER.info("Query :: {} []", params);
            ps = con.prepareStatement(sql);
            ps.setLong(1, new Long(flex3));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                values = new KeyValue();
                values.setKey(String.valueOf(rs.getLong(1)));
                values.setValue(rs.getString(2));
                values.setInfo(rs.getString(3));
                values.setInfo1(rs.getString(4));
                values.setInfo2(rs.getString(5));
            }
        } catch (Exception e) {
            LOGGER.error("Error while load Allocation Task List", e);
        } finally {
            closeDbConn(con);
        }
        return values;

    }

    public List<KeyValue> loadTaskAllocateRulesList(String catTaskCatg, String catTaskSubCatg) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{catTaskCatg, catTaskSubCatg};
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CAR_ID key,PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_FILTER',CAR_FILTER) value,"
                    + " PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_OPERATOR',CAR_OPERATOR) info,PKG_CRM_TASKS.FN_GET_RULE_DETAILS(CAR_ID) info1,CAR_FILTER info2  "
                    + " FROM M_CRM_AGENTS_TASK_RULES WHERE CAR_TASK_CATG=? AND CAR_TASK_SUB_CATG=?");
            LOGGER.info("Query :: {} []", params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while load Allocation Task List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadValueList(String flex1, String flex3) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        String sqlQuery = "";
        String filterquery = "";
        String type = "";
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            sqlQuery = "SELECT PARA_REMARK, PARA_NAME_BL, PARA_REMARK_BL FROM M_APP_PARAMETER WHERE PARA_CODE = 'CRM_FILTER' AND PARA_SUB_CODE = ?";
            PreparedStatement ps1 = con.prepareStatement(sqlQuery);
            ps1.setString(1, flex1);
            rs = ps1.executeQuery();
            if (rs.next()) {
                type = rs.getString(2);
                filterquery = ("A".equalsIgnoreCase(type) ? rs.getString(3) : rs.getString(1));
            }
            rs.close();
            ps1.close();
            if ("A".equalsIgnoreCase(type)) {
                String[] ids = flex3.split(",");
                list = new ArrayList<>();
                prs = con.prepareStatement(filterquery);
                for (String id : ids) {
                    LOGGER.info("param :{}", id);
                    prs.setString(1, id);
                    rs = prs.executeQuery();
                    while (rs.next()) {
                        KeyValue customer = new KeyValue();
                        customer.setKey(rs.getString("key"));
                        customer.setValue(rs.getString("value"));
                        customer.setValue(rs.getString("text"));
                        list.add(customer);
                    }
                }
            } else if ("D".equalsIgnoreCase(type)) {
                list = executeList(con, filterquery, new Object[]{}, KeyValue.class);
            }
        } catch (Exception e) {
            LOGGER.error("Error while load Filter List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public String saveTaskRulesForm(String flex1, String flex2, String flex3, String flex5, MCrmAgentsTaskAlloc mCrmAgentsTask, String operation, String userId) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0;
        String sql = null;
        String result = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equals(operation)) {

                sql = "INSERT INTO M_CRM_AGENTS_TASK_RULES(CAR_ID,CAR_TASK_CATG,CAR_TASK_SUB_CATG,CAR_FILTER,CAR_OPERATOR,CAR_VALUE,CAR_CR_UID,CAR_CR_DT)"
                        + "VALUES(S_CAR_ID.NEXTVAL,:catg,:subcatg,:flex1,:flex2,:flex3,:userId,SYSDATE)";
                ps = con.prepareStatement(sql);
                ps.setString(++i, mCrmAgentsTask.getCatTaskCatg());
                ps.setString(++i, mCrmAgentsTask.getCatTaskSubCatg());
                ps.setString(++i, flex1);
                ps.setString(++i, flex2);
                ps.setString(++i, flex3);
                ps.setString(++i, userId);
                ps.executeUpdate();
                con.commit();
            } else if ("edit".equals(operation)) {
                sql = "UPDATE M_CRM_AGENTS_TASK_RULES SET CAR_FILTER =? ,CAR_OPERATOR =?,CAR_VALUE=?,CAR_UPD_UID = ?,CAR_UPD_DT=SYSDATE where CAR_ID=?";
                ps = con.prepareStatement(sql);
                ps.setString(++i, flex1);
                ps.setString(++i, flex2);
                ps.setString(++i, flex3);
                ps.setString(++i, userId);
                ps.setString(++i, flex5);
                ps.executeUpdate();
                con.commit();
            } else {
                sql = "DELETE FROM M_CRM_AGENTS_TASK_RULES WHERE CAR_ID=?";
                ps = con.prepareStatement(sql);
                ps.setString(++i, flex5);
                ps.executeUpdate();
                con.commit();
            }
        } catch (Exception e) {
            LOGGER.error("Error while Insert Image Entry => {}", e);
            result = e.getMessage();
        } finally {
            closeDBComp(ps, null, con);
        }
        return result;
    }

    public KeyValue loadTaskRulesDetails(String flex5) {
        KeyValue values = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getDBConnection(getDataSource());
            String sql = "SELECT CAR_ID key,CAR_TASK_CATG value,"
                    + " CAR_TASK_SUB_CATG info, CAR_FILTER info1, CAR_OPERATOR info2,CAR_VALUE info3,PKG_REP_UTILITY.FN_GET_PARA_VALUE('CRM_FILTER',CAR_FILTER) info4 "
                    + " FROM M_CRM_AGENTS_TASK_RULES WHERE CAR_ID=?";
            ps = con.prepareStatement(sql);
            ps.setLong(1, new Long(flex5));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                values = new KeyValue();
                values.setKey(String.valueOf(rs.getLong(1)));//ID
                values.setValue(rs.getString(2)); // CATG
                values.setInfo(rs.getString(3));  // SUB CATG
                values.setInfo1(rs.getString(4)); // FILTER
                values.setInfo2(rs.getString(5)); // OPERATOR
                values.setInfo3(rs.getString(6)); // VALUE
                values.setInfo4(rs.getString(7));
            }
        } catch (Exception e) {
            LOGGER.error("Error while load Allocation Task List", e);
        } finally {
            closeDbConn(con);
        }
        return values;
    }

    public List<KeyValue> getPriorityList(FieldConstants.AppParameter appParameter, MCrmAgentsTaskAlloc mCrmAgentsTask) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{mCrmAgentsTask.getCatTaskCatg(), mCrmAgentsTask.getCatTaskSubCatg()};
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PARA_SUB_CODE key, PARA_NAME value, PARA_VALUE info FROM M_APP_PARAMETER WHERE PARA_CODE = 'CRM_PRIORITY' AND PARA_SUB_CODE NOT IN "
                    + " (SELECT CTS_PRIORITY FROM  M_CRM_TASK_SLA where CTS_CATG=? AND CTS_SUB_CATG=?) ");
            LOGGER.info("Query :: {} []", params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while load Allocation Task List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    private final static String QRY_USER_APPL_COMP = "SELECT MUC_USER_ID key, MUC_COMP_CODE value, MUC_DFLT_YN info FROM M_CRM_USER_COMP WHERE MUC_USER_ID = ?";
    private final static String INS_USER_APPL_COMP = "INSERT INTO M_CRM_USER_COMP (MUC_USER_ID, MUC_COMP_CODE, MUC_CR_UID, MUC_CR_DT) VALUES (?, ?, ?, SYSDATE)";
    private final static String DEL_USER_APPL_COMP = "DELETE FROM M_CRM_USER_COMP WHERE MUC_USER_ID = ? AND MUC_COMP_CODE = ?";

    public List<KeyValue> loadApplCompanyByUser(final String userId) {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{userId};
            LOGGER.info("Query :: {} [{}]", new Object[]{QRY_USER_APPL_COMP, userId});
            list = executeList(con, QRY_USER_APPL_COMP, data, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while user appl. company list => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public int saveUserApplCompany(final UserInfo user, final String operation) {
        int recCent = 0;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equals(operation)) {
                LOGGER.info("Query :: {} [{}, {}, {}]", new Object[]{INS_USER_APPL_COMP, user.getUserId(), user.getCompanyCode(), user.getUserName()});
                recCent = executeUpdate(con, INS_USER_APPL_COMP, new Object[]{user.getUserId(), user.getCompanyCode(), user.getUserName()});
            } else if ("delete".equals(operation)) {
                LOGGER.info("Query :: {} [{}, {}, {}]", new Object[]{DEL_USER_APPL_COMP, user.getUserId(), user.getCompanyCode(), user.getUserName()});
                executeUpdate(con, DEL_USER_APPL_COMP, new Object[]{user.getUserId(), user.getCompanyCode()});
            }
        } catch (Exception e) {
            LOGGER.error("Error while inserting/deleting user appl. company => {}", e);
        } finally {
            closeDbConn(con);
        }
        return recCent;
    }

    public List<KeyValue> loadCustomersList(String flex1) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{"%" + flex1 + "%", "%" + flex1 + "%"};
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());//Agents/Brokers/Banks
            query = new StringBuilder("SELECT CUST_CODE id, CUST_CODE key, CUST_CODE value,CUST_CODE||'-'||CUST_NAME text,CUST_NAME info  FROM M_CUSTOMER "
                    + "  WHERE TRUNC(SYSDATE) BETWEEN TRUNC(NVL(CUST_EFF_FM_DT, SYSDATE)) AND TRUNC(NVL(CUST_EFF_TO_DT, SYSDATE)) AND (upper(CUST_CODE) like upper(?) OR upper(CUST_NAME) like upper(?)) ");

            LOGGER.info("Query :: {} []", params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while load Allocation Task List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadAutoCompleteList(String flex1, String flex2) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{"%" + flex1 + "%", "%" + flex1 + "%"};
        Connection con = null;
        StringBuilder query = null;
        String sqlQuery = "";
        String filterquery = "";
        String type = "";
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("flex1 :{}, flex2: {}", flex1, flex2);
            sqlQuery = "SELECT PARA_REMARK,PARA_NAME_BL FROM M_APP_PARAMETER WHERE PARA_CODE = 'CRM_FILTER' AND PARA_SUB_CODE = ?";
            prs = con.prepareStatement(sqlQuery);
            prs.setString(1, flex2);
            rs = prs.executeQuery();
            if (rs.next()) {
                filterquery = rs.getString(1);
                type = rs.getString(2);
            }
            rs.close();
            prs.close();
            if ("A".equalsIgnoreCase(type)) {
                query = new StringBuilder(filterquery);
                list = executeList(con, query.toString(), params, KeyValue.class);
            }
        } catch (Exception e) {
            LOGGER.error("Error while load Auto Complete Filter List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    private final static String QRY_CALL_SUMMARY = "SELECT * FROM V_CRM_MONTH_CALL_SUMMARY WHERE \"Year\" = ? AND \"Month\" = ? AND \"Agent\" = NVL(?, \"Agent\")";
    private final static String QRY_QUOTE_SUMMARY = "SELECT * FROM V_CRM_QUOTE_SUMMARY WHERE \"Year\" = ? AND \"Month\" = ? AND \"Agent\" = NVL(?, \"Agent\")";
    private final static String QRY_RENEWAL_SUMMARY = "SELECT * FROM V_CRM_RENEWAL_SUMMARY WHERE \"Year\" = ? AND \"Month\" = ? AND \"Agent\" = NVL(?, \"Agent\")";
    private final static String QRY_PAY_LINK_SUMMARY = "SELECT * FROM V_CRM_PAY_LINK_SUMMARY WHERE \"Year\" = ? AND \"Month\" = ? AND \"Agent\" = NVL(?, \"Agent\")";

  //commented for corporate
    //private final static String QRY_CALL_SUMMARY = "SELECT \"Agent\", SUM(\"Inbound Calls\"), SUM(\"Outbound Calls\"), SUM(\"Missed Calls\") FROM V_CRM_MONTH_CALL_SUMMARY WHERE \"Year\" = ? AND \"Month\" = ? AND \"Agent\" = NVL(?, \"Agent\") AND \"Entity\" = NVL(?, \"Entity\") GROUP BY \"Agent\", \"Year\", \"Month\"";
    //private final static String QRY_QUOTE_SUMMARY = "SELECT \"Agent\", SUM(\"Alloc Count\"), SUM(\"Policy Count\"), SUM(\"Net Premium\") FROM V_CRM_QUOTE_SUMMARY WHERE \"Year\" = ? AND \"Month\" = ? AND \"Agent\" = NVL(?, \"Agent\") AND \"Entity\" = NVL(?, \"Entity\") GROUP BY \"Agent\", \"Year\", \"Month\"";
   // private final static String QRY_RENEWAL_SUMMARY = "SELECT \"Agent\", SUM(\"Alloc Count\"), SUM(\"Policy Count\"), SUM(\"Net Premium\") FROM V_CRM_RENEWAL_SUMMARY WHERE \"Year\" = ? AND \"Month\" = ? AND \"Agent\" = NVL(?, \"Agent\") AND \"Entity\" = NVL(?, \"Entity\") GROUP BY \"Agent\", \"Year\", \"Month\"";
    //private final static String QRY_PAY_LINK_SUMMARY = "SELECT \"Agent\", SUM(\"Link Count\"), SUM(\"Policy Count\"), SUM(\"Net Premium\") FROM V_CRM_PAY_LINK_SUMMARY WHERE \"Year\" = ? AND \"Month\" = ? AND \"Agent\" = NVL(?, \"Agent\") AND \"Entity\" = NVL(?, \"Entity\") GROUP BY \"Agent\", \"Year\", \"Month\"";


    /*public List<KeyValue> loadAgentCallSummary(final String crmId, String agentId, String yearRange, String month) {
        List<KeyValue> callList = null;
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        int j = 0;
        try {
            con = getDBConnection(getDataSource());
            prs = con.prepareStatement(QRY_CALL_SUMMARY);
            prs.setString(++j, yearRange);
            prs.setString(++j, month);
            prs.setString(++j, agentId);
            prs.setString(++j, crmId);
            rs = prs.executeQuery();
            callList = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString(1));
                keyValue.setValue(rs.getString(2));
                keyValue.setInfo(rs.getString(3));
                keyValue.setInfo1(rs.getString(4));
                callList.add(keyValue);
            }
        } catch (Exception e) {
            LOGGER.error("Error while load agent call summary", e);
        } finally {
            closeDbConn(con);
        }
        return callList;
    }*/

    public List<KeyValue> loadAgentCallSummary(String agentId, String yearRange, String month) {
        List<KeyValue> callList = null;
        //Object[] params = new Object[]{agentId};
        Connection con = null;
        StringBuilder query = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        int j = 0;
        try {
            con = getDBConnection(getDataSource());
            prs = con.prepareStatement(QRY_CALL_SUMMARY);
            prs.setString(1, yearRange);
            prs.setString(2, month);
            prs.setString(3, agentId);
            rs = prs.executeQuery();
            callList = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString(1));
                keyValue.setValue(rs.getString(5));
                keyValue.setInfo(rs.getString(6));
                keyValue.setInfo1(rs.getString(7));
                callList.add(keyValue);
            }
        } catch (Exception e) {
            LOGGER.error("Error while load agent dashboard", e);
        } finally {
            closeDbConn(con);
        }
        return callList;
    }

  //commented for corporate
    /*public List<KeyValue> loadAgentQuoteSummary(final String crmId, String agentId, String yearRange, String month) {
        List<KeyValue> quotelist = null;
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            prs = con.prepareStatement(QRY_QUOTE_SUMMARY);
            int j = 0;
            prs.setString(++j, yearRange);
            prs.setString(++j, month);
            prs.setString(++j, agentId);
            prs.setString(++j, crmId);
            rs = prs.executeQuery();
            quotelist = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString(1));
                keyValue.setValue(rs.getString(2));
                keyValue.setInfo(rs.getString(3));
                keyValue.setInfo1(rs.getString(4));
                quotelist.add(keyValue);
            }
        } catch (Exception e) {
            LOGGER.error("Error while load quote summary", e);
        } finally {
            closeDbConn(con);
        }
        return quotelist;
    }*/

    public List<KeyValue> loadAgentQuoteSummary(String agentId, String yearRange, String month) {
        List<KeyValue> quotelist = null;
        Object[] params = new Object[]{agentId};
        Connection con = null;
        StringBuilder query = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            prs = con.prepareStatement(QRY_QUOTE_SUMMARY);
            int j = 0;
            prs.setString(++j, yearRange);

            prs.setString(++j, month);
            prs.setString(++j, agentId);

            rs = prs.executeQuery();
            quotelist = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString(1));
                keyValue.setValue(rs.getString(5));
                keyValue.setInfo(rs.getString(6));
                keyValue.setInfo1(rs.getString(7));
                quotelist.add(keyValue);
            }
        } catch (Exception e) {
            LOGGER.error("Error While Load ");
        } finally {
            closeDbConn(con);
        }
        return quotelist;
    }

  //commented for corporate
    /*public List<KeyValue> loadAgentRenewalSummary(final String crmId, String agentId, String yearRange, String month) {
        List<KeyValue> renewalList = null;
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            prs = con.prepareStatement(QRY_RENEWAL_SUMMARY);
            int j = 0;
            prs.setString(++j, yearRange);
            prs.setString(++j, month);
            prs.setString(++j, agentId);
            prs.setString(++j, crmId);
            rs = prs.executeQuery();
            renewalList = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString(1));
                keyValue.setValue(rs.getString(2));
                keyValue.setInfo(rs.getString(3));
                keyValue.setInfo1(rs.getString(4));
                renewalList.add(keyValue);
            }
        } catch (Exception e) {
            LOGGER.error("Error while load renewal summary", e);
        } finally {
            closeDbConn(con);
        }
        return renewalList;
    }*/

    public List<KeyValue> loadAgentRenewalSummary(String agentId, String yearRange, String month) {
        List<KeyValue> renewalList = null;
        Object[] params = new Object[]{agentId};
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            prs = con.prepareStatement(QRY_RENEWAL_SUMMARY);
            int j = 0;
            prs.setString(++j, yearRange);

            prs.setString(++j, month);
            prs.setString(++j, agentId);
            rs = prs.executeQuery();
            renewalList = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString(1));
                keyValue.setValue(rs.getString(5));
                keyValue.setInfo(rs.getString(6));
                keyValue.setInfo1(rs.getString(7));
                renewalList.add(keyValue);
            }
        } catch (Exception e) {
            LOGGER.error("Error While Load ");
        } finally {
            closeDbConn(con);
        }
        return renewalList;
    }

  //commented for corporate
    /*public List<KeyValue> loadAgentPayLinkSummary(final String crmId, String agentId, String yearRange, String month) {
        List<KeyValue> payLinklist = null;
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        int j = 0;
        try {
            con = getDBConnection(getDataSource());
            prs = con.prepareStatement(QRY_PAY_LINK_SUMMARY);
            prs.setString(++j, yearRange);
            prs.setString(++j, month);
            prs.setString(++j, agentId);
            prs.setString(++j, crmId);
            rs = prs.executeQuery();
            payLinklist = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString(1));
                keyValue.setValue(rs.getString(2));
                keyValue.setInfo(rs.getString(3));
                keyValue.setInfo1(rs.getString(4));
                payLinklist.add(keyValue);
            }
        } catch (Exception e) {
            LOGGER.error("Error while load payLink summary", e);
        } finally {
            closeDbConn(con);
        }
        return payLinklist;
    }*/

    public List<KeyValue> loadAgentPayLinkSummary(String agentId, String yearRange, String month) {
        List<KeyValue> payLinklist = null;
        Object[] params = new Object[]{agentId};
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        StringBuilder query = null;
        int j = 0;

        try {
            con = getDBConnection(getDataSource());
            prs = con.prepareStatement(QRY_PAY_LINK_SUMMARY);
            prs.setString(++j, yearRange);

            prs.setString(++j, month);
            prs.setString(++j, agentId);
            rs = prs.executeQuery();
            payLinklist = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString(1));
                keyValue.setValue(rs.getString(5));
                keyValue.setInfo(rs.getString(6));
                keyValue.setInfo1(rs.getString(7));
                payLinklist.add(keyValue);
            }
        } catch (Exception e) {
            LOGGER.error("Error While Load ");
        } finally {
            closeDbConn(con);
        }
        return payLinklist;
    }

    public enum YearRange {

        THIS_YEAR("This year", "BETWEEN TRUNC(SYSDATE, 'YEAR') AND ADD_MONTHS(TRUNC(SYSDATE, 'YEAR'), 12) - 1 "),
        LAST_YEAR("Last year", "BETWEEN TRUNC(ADD_MONTHS(SYSDATE, -12), 'YEAR') AND ADD_MONTHS(TRUNC(ADD_MONTHS(SYSDATE, -12), 'YEAR'), 12) - 1 ");

        final String desc;
        final String range;

        YearRange(String desc, String range) {
            this.desc = desc;
            this.range = range;
        }

        public String getDesc() {
            return this.desc;
        }

        public String getRange() {
            return this.range;
        }
    }

    public List<KeyValue> loaduserList(String flex1) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{"%" + flex1 + "%", "%" + flex1 + "%"};
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT USER_ID key, USER_ID ||'-'|| USER_NAME value  FROM M_USER "
                    + "WHERE (upper(USER_ID) like upper(?) OR upper(USER_NAME) like upper(?)) ");
            LOGGER.info("Query :: {} []", params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while load User List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    private final static String QRY_ANOUD_USER_TO_CRM = "SELECT USER_ID userId, USER_NAME userName, USER_LDAP_UID userLdapId, USER_EMAIL_ID userEmailId, USER_MOBILE_NO userMobileNo, USER_TEL_NO userTelNo, USER_ADMINISTRATOR_YN userAdministratorYn, "
            + "USER_SESS_COUNT userMaxSession, USER_STATIC_IP userStaticIp FROM M_USER WHERE USER_ID = ? AND NOT EXISTS (SELECT CU_USER_ID FROM M_CRM_USER WHERE CU_USER_ID = USER_ID)";

    private final static String QRY_CRM_USER_FOR_EDIT = "SELECT CU_USER_ID userId, CU_USER_NAME userName, CU_LDAP_ID userLdapId, CU_EMAIL_ID userEmailId, CU_MOBILE_NO userMobileNo, CU_TEL_NO userTelNo, CU_AMDIN_YN userAdministratorYn, "
            + "CU_MAX_SESSION_CNT userMaxSession, CU_STATIC_IP userStaticIp, CU_LOCK_YN userLockYn, CU_TEAM userTeam, CU_ROLE_SEQ userRoleSeq, CU_AGENT_TYPE userAgentType, CU_CHAT_UID userChatUid, CU_CHAT_PWD userChatPwd FROM M_CRM_USER WHERE CU_USER_ID = ?";

    public CrmUser openUserDetailForm(final String userId, final String operation) throws Exception {
        CrmUser bean = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equals(operation)) {
                LOGGER.info("Query :: {} [{}]", new Object[]{QRY_ANOUD_USER_TO_CRM, userId});
                Object[] params = new Object[]{userId};
                bean = (CrmUser) executeQuery(con, QRY_ANOUD_USER_TO_CRM, params, CrmUser.class);
            } else if ("edit".equals(operation)) {
                LOGGER.info("Query :: {} [{}]", new Object[]{QRY_ANOUD_USER_TO_CRM, userId});
                Object[] params = new Object[]{userId};
                bean = (CrmUser) executeQuery(con, QRY_CRM_USER_FOR_EDIT, params, CrmUser.class);
            }
        } catch (Exception e) {
            LOGGER.error("Error while retreiving UserInfo details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return bean;
    }

    private static final String INS_USER_INFO = "INSERT INTO M_CRM_USER (CU_USER_ID, CU_USER_NAME, CU_LDAP_ID, CU_EMAIL_ID, CU_TEL_NO, CU_MOBILE_NO, CU_TEAM, CU_ROLE_SEQ, CU_LOCK_YN, CU_AMDIN_YN, CU_MAX_SESSION_CNT, CU_STATIC_IP, CU_CR_UID, CU_CR_DT, CU_AGENT_TYPE, CU_CHAT_UID, CU_CHAT_PWD, CU_APPL_MODULES) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?, ?, '[\"D\",\"T\",\"A\",\"C\",\"S\"]')";
    private final static String UPD_CRM_USER = "UPDATE M_CRM_USER SET CU_USER_NAME = ?, CU_LDAP_ID = ?, CU_EMAIL_ID = ?, CU_TEL_NO = ?, CU_MOBILE_NO = ?, CU_TEAM = ?, CU_ROLE_SEQ = ?, CU_LOCK_YN = ?, CU_AMDIN_YN = ?, CU_MAX_SESSION_CNT = ?, CU_STATIC_IP = ?, CU_UPD_UID = ?, CU_UPD_DT = SYSDATE, CU_AGENT_TYPE = ?, CU_CHAT_UID = ?, CU_CHAT_PWD = ? WHERE CU_USER_ID = ?";
    private final static String UPD_CRM_USER_MENU = "UPDATE M_CRM_USER SET CU_APPL_MODULES = ? WHERE CU_USER_ID = ?";

    public String saveUserDetailsForm(CrmUser bean, final String operation) throws Exception {
        Connection con = null;
        String result = null;
        int count;
        try {
            con = getDBConnection(getDataSource());
            if (bean.getUserLockYn() == null) {
                bean.setUserLockYn("0");
            }
            if (bean.getUserAdministratorYn() == null) {
                bean.setUserAdministratorYn("0");
            }
            Object[] params = null;
            if ("add".equals(operation)) {
                params = new Object[]{bean.getUserId(), bean.getUserName(), bean.getUserLdapId(), bean.getUserEmailId(), bean.getUserTelNo(), bean.getUserMobileNo(), bean.getUserTeam(), bean.getUserRoleSeq(), bean.getUserLockYn(), bean.getUserAdministratorYn(), bean.getUserMaxSession(), bean.getUserStaticIp(), bean.getUserCreateId(), bean.getUserAgentType(), bean.getUserChatUid(), bean.getUserChatPwd()};
                LOGGER.info("Inserting user => User Id: {}, Ldap: {}, Tel No: {}, Team: {}, Role: {}, Admin: {}, Agent: {}, CrUid: {}", new Object[]{bean.getUserId(), bean.getUserLdapId(), bean.getUserTelNo(), bean.getUserTeam(), bean.getUserRoleSeq(), bean.getUserAdministratorYn(), bean.getUserAgentType(), bean.getUserCreateId()});
                count = executeUpdate(con, INS_USER_INFO, params);
            } else if ("edit".equals(operation)) {
                params = new Object[]{bean.getUserName(), bean.getUserLdapId(), bean.getUserEmailId(), bean.getUserTelNo(), bean.getUserMobileNo(), bean.getUserTeam(), bean.getUserRoleSeq(), bean.getUserLockYn(), bean.getUserAdministratorYn(), bean.getUserMaxSession(), bean.getUserStaticIp(), bean.getUserCreateId(), bean.getUserAgentType(), bean.getUserChatUid(), bean.getUserChatPwd(), bean.getUserId()};
                LOGGER.info("Updating user => User Id: {}, Ldap: {}, Tel No: {}, Team: {}, Role: {}, Admin: {}, Agent: {}, CrUid: {}", new Object[]{bean.getUserId(), bean.getUserLdapId(), bean.getUserTelNo(), bean.getUserTeam(), bean.getUserRoleSeq(), bean.getUserAdministratorYn(), bean.getUserAgentType(), bean.getUserCreateId()});
                count = executeUpdate(con, UPD_CRM_USER, params);
            } else if ("menu".equals(operation)) {
                params = new Object[]{bean.getUserApplModules(), bean.getUserId()};
                LOGGER.info("Updating user menu => User Id: {}, Menu: {}", new Object[]{bean.getUserId(), bean.getUserApplModules()});
                count = executeUpdate(con, UPD_CRM_USER_MENU, params);
                if (count != 1) {
                    throw new Exception("Failed to update the menu");
                }
            }
            con.commit();
        } catch (ConstraintViolationException cve) {
            LOGGER.error(cve.getMessage());
            throw new Exception("Record already exist!");
        } catch (Exception e) {
            LOGGER.error("Error while save user extension => {}", e);
            throw e;
        } finally {
            closeDbConn(con);
        }
        return result;
    }

    public String callPkgCrmTasks(final String subCatg, final String sourceId) throws Exception {
        String result = null;
        Connection con = null;
        CallableStatement cs = null;
        int indx = 0;
        try {
            con = getDBConnection(getDataSource());
            String query = "{CALL PKG_CRM_TASKS.PR_LEADS_TO_TASK(?, ?, ?)}";
            LOGGER.debug("Query: PKG_CRM_TASKS.PR_LEADS_TO_TASK, Sub Catg: {}, Source Id: {}", new Object[]{query, subCatg, sourceId});
            cs = con.prepareCall(query);
            cs.setString(++indx, subCatg);
            cs.setString(++indx, sourceId);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.executeQuery();
            result = cs.getString(3);
            LOGGER.debug("Query: PKG_CRM_TASKS.PR_LEADS_TO_TASK, Source Id: {}, Result: {}", new Object[]{sourceId, result});
        } catch (Exception e) {
            LOGGER.error("", e);
            throw e;
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    public List<KeyValue> getuserRoleList() {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = null;
            query = new StringBuilder("SELECT AC_CODE key, AC_DESC value FROM M_APP_CODES WHERE AC_TYPE = 'CRM_TEAM'");
            LOGGER.info("Query :: {} []", new Object[]{query});
            list = executeList(con, query.toString(), data, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while load User Roles List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public void updateAppcodes(String masterCode, String roleId, String acMcCode, String acCode) throws Exception {
        Connection con = null;
        String sql = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            sql = "UPDATE M_APP_CODES SET AC_FLEX_01 = ?, AC_FLEX_02 = ? WHERE AC_CODE = ? AND AC_MC_CODE = ? AND AC_TYPE = 'CRM_SUB_CATG'";
            Object[] params = new Object[]{masterCode, roleId, acCode, acMcCode};
            recCnt = executeUpdate(con, sql, params);
            LOGGER.debug("Update appcodes[{}:{}]", params);
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while update Appcodes => {}", e);
            throw e;
        } finally {
            DbUtils.close(con);
        }
    }

    public List<CrmHolidays> getHolidaysDetailList(String year, final String company) {
        List<CrmHolidays> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] param = new Object[]{year};
            query = new StringBuilder(
                    "select CH_ID holidaysId, CH_YEAR holidaysYear, CH_TYPE holidaysType, CH_NAME name, CH_FM_DATE fromDt, CH_TO_DATE toDt, TO_DATE(SYSDATE, 'DD-MM-RRRR') currDate  from m_crm_holidays where CH_YEAR = ? order by CH_FM_DATE desc");
            LOGGER.info("Query :: {} []", new Object[]{query});
            list = executeList(con, query.toString(), param, CrmHolidays.class);
        } catch (Exception e) {
            LOGGER.error("Error while user list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public String checkHolidayWeekendDataExist(final CrmHolidays bean, String id, String companyCode) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String status = null;
        String query;
        try {
            con = getDBConnection(companyCode);
            query = "SELECT COUNT(1) COUNT FROM M_CRM_HOLIDAYS WHERE " +
            		" CH_TYPE = ? " +
            		" AND ((TRUNC(CH_FM_DATE) BETWEEN TRUNC(TO_DATE(?, 'dd/mm/yyyy')) AND TRUNC(TO_DATE(?, 'dd/mm/yyyy')) " +
            		" OR  TRUNC(CH_TO_DATE) BETWEEN TRUNC(TO_DATE(?, 'dd/mm/yyyy')) AND TRUNC(TO_DATE(?, 'dd/mm/yyyy'))) " +
            		" OR  (TRUNC(TO_DATE(?, 'dd/mm/yyyy')) BETWEEN TRUNC(CH_FM_DATE) AND TRUNC(CH_TO_DATE) " +
            		" AND TRUNC(TO_DATE(?, 'dd/mm/yyyy')) BETWEEN TRUNC(CH_FM_DATE) AND TRUNC(CH_TO_DATE))) ";
            if ("edit".equals(id)) {
                query += " and CH_ID <> ?";
            }
            LOGGER.debug(" Query :: {}, HolidayType : {}, HolidayId : {}, FromDate : {}, ToDate : {}", query, bean.getHolidaysType(), bean.getHolidaysId(), bean.getFromDt(), bean.getToDt());
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getHolidaysType());
            ps.setString(2, StringUtils.isBlank(bean.getFromDt()) ? "" : bean.getFromDt());
            ps.setString(3, StringUtils.isBlank(bean.getToDt()) ? "" : bean.getToDt());
            ps.setString(4, StringUtils.isBlank(bean.getFromDt()) ? "" : bean.getFromDt());
            ps.setString(5, StringUtils.isBlank(bean.getToDt()) ? "" : bean.getToDt());
            ps.setString(6, StringUtils.isBlank(bean.getFromDt()) ? "" : bean.getFromDt());
            ps.setString(7, StringUtils.isBlank(bean.getToDt()) ? "" : bean.getToDt());
            if ("edit".equals(id)) {
                ps.setString(8, bean.getHolidaysId());
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                status = 0 != rs.getInt("COUNT") ? "Record already exists with matching criteria. Please check your input" : "";
            }
            ps.close();
            rs.close();

            if (StringUtils.isBlank(status) && "W".equals(bean.getHolidaysType())) {
                query = "select COUNT(1) COUNT from m_crm_holidays where CH_YEAR= ? AND CH_TYPE= ?";
                LOGGER.debug(" Query :: {}, HolidayType : {}, Year : {}", query, bean.getHolidaysType(), bean.getHolidaysYear());
                ps = con.prepareStatement(query);
                ps.setString(1, bean.getHolidaysYear());
                ps.setString(2, bean.getHolidaysType());
                rs = ps.executeQuery();
                if (rs.next()) {
                    status = 0 != rs.getInt("COUNT") ? "Weekend record already exists for the year" : "";
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error on checking Holiday Info", e);
            status = e.getMessage();
        } finally {
            closeDBComp(ps, rs, con);
        }
        return status;
    }

    public String saveHolidayForm(final CrmHolidays bean, final String userId, final String companyCode) {
        String query = null;
        String status = null;
        Connection con = null;
        PreparedStatement prs = null;
        try {
            con = getDBConnection(getDataSource());
            status = checkHolidayWeekendDataExist(bean, userId, companyCode);
            if (StringUtils.isBlank(status)) {
                query = " INSERT INTO m_crm_holidays(ch_id, ch_year, ch_type, ch_name, ch_fm_date, ch_to_date,CH_CR_UID, CH_CR_DT)"
                        + " VALUES (NVL((SELECT MAX(ch_id) + 1 FROM m_crm_holidays), 1),?,?,?,TO_DATE(?, 'DD/MM/YYYY'),TO_DATE(?, 'DD/MM/YYYY'),?,SYSDATE)";
                prs = con.prepareStatement(query);
                int j = 0;
                prs.setString(++j, bean.getHolidaysYear());
                prs.setString(++j, bean.getHolidaysType());
                prs.setString(++j, bean.getName());
                prs.setString(++j, StringUtils.isBlank(bean.getFromDt()) ? "" : bean.getFromDt());
                prs.setString(++j, StringUtils.isBlank(bean.getToDt()) ? "" : bean.getToDt());
                prs.setString(++j, userId);
                prs.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return status;
    }

    public String saveEditedHolidayForm(CrmHolidays crmHolidays, final String id, final String userId, String companyCode)
            throws Exception {
        Connection con = null;
        String sql = null;
        String status = null;
        try {
            con = getDBConnection(getDataSource());
            con.setAutoCommit(true);
            status = checkHolidayWeekendDataExist(crmHolidays, id, companyCode);
            if (StringUtils.isBlank(status)) {
                sql = "UPDATE M_CRM_HOLIDAYS SET CH_NAME = ?, CH_FM_DATE = to_date(?,'DD-MM-YYYY'), CH_TO_DATE = to_date(?,'DD-MM-YYYY'), CH_UPD_UID = ?, CH_UPD_DT = SYSDATE WHERE CH_ID = ?";
                Object[] params = new Object[]{crmHolidays.getName(), crmHolidays.getFromDt(), crmHolidays.getToDt(),
                    userId, crmHolidays.getHolidaysId()};
                executeUpdate(con, sql, params);
                LOGGER.debug("Update call log remarks [{}:{}]", params);
                con.commit();
            }
        } catch (Exception e) {
            LOGGER.error("Error while update userAgent extension => {}", e);
            throw e;
        } finally {
            DbUtils.close(con);
        }
        return status;
    }

    public String deleteHoliday(final String id, String companyCode) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0;
        String sql = null;
        String result = null;
        try {
            con = getDBConnection(getDataSource());
            sql = "DELETE FROM M_CRM_HOLIDAYS WHERE CH_ID=?";
            ps = con.prepareStatement(sql);
            ps.setString(++i, id);
            ps.executeUpdate();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while deleting record => {}", e);
            result = e.getMessage();
        } finally {
            closeDBComp(ps, null, con);
        }
        return result;
    }
}
