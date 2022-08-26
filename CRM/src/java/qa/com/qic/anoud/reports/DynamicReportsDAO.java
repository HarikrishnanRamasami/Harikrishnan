/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.reports;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author palani.rajan
 */
public class DynamicReportsDAO extends DatabaseDAO {

    private static final Logger logger = LogUtil.getLogger(DynamicReportsDAO.class);

    private Connection con = null;
    private PreparedStatement prs = null;
    private ResultSet rs = null;
    private String dataSource;

    private final static String AGGREGATE_FUNCTIONS[] = new String[]{"MIN", "MAX", "SUM", "COUNT", "AVG"};

    public DynamicReportsDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

        public List<ReportsBeanVO> loadUserReportsList(final String userId) {
        logger.debug("DynamicReportsDAO - Load ");
        List<ReportsBeanVO> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT URE_ID, URE_VIEW, (SELECT AC_DESC FROM M_APP_CODES WHERE AC_TYPE='ENQUIRY_VIEW' AND AC_CODE=URE_VIEW) URE_VIEW_NAME, "
                    + "(SELECT AC_MC_CODE FROM M_APP_CODES WHERE AC_TYPE='ENQUIRY_VIEW' AND AC_CODE=URE_VIEW) URE_TABLE_NAME, URE_NAME, URE_NARRATIVE, "
                    + "URE_CR_UID, PKG_REP_UTILITY.FN_GET_USER_NAME(URE_CR_UID) URE_CR_UNAME, URE_CR_DT, (SELECT COUNT(URP_URE_ID) FROM M_USER_REPORTS_PARAM WHERE URP_TYPE = 'F' AND URP_URE_ID = URE_ID) FILTER_CNT "
                    + "FROM M_USER_REPORTS WHERE URE_SEC_TYPE = 'U' AND URE_TYPE = 'C' "
                    + "AND INSTR(URE_USER_ROLES, ?) > 1 "
                    + "UNION "
                    + "SELECT URE_ID, URE_VIEW, (SELECT AC_DESC FROM M_APP_CODES WHERE AC_TYPE = 'ENQUIRY_VIEW' AND AC_CODE = URE_VIEW) URE_VIEW_NAME, "
                    + "(SELECT AC_MC_CODE FROM M_APP_CODES WHERE AC_TYPE = 'ENQUIRY_VIEW' AND AC_CODE = URE_VIEW) URE_TABLE_NAME, URE_NAME, URE_NARRATIVE, "
                    + "URE_CR_UID, PKG_REP_UTILITY.FN_GET_USER_NAME(URE_CR_UID) URE_CR_UNAME, URE_CR_DT, (SELECT COUNT(URP_URE_ID) FROM M_USER_REPORTS_PARAM WHERE URP_TYPE = 'F' AND URP_URE_ID = URE_ID) FILTER_CNT "
                    + "FROM M_USER_REPORTS, M_CRM_USER WHERE URE_SEC_TYPE = 'R' AND URE_TYPE = 'C' "
                    + "AND CU_USER_ID = ? AND INSTR(URE_USER_ROLES, CU_TEAM) > 1";
            logger.debug("Query=> {}", query);
            prs = con.prepareStatement(query);
            prs.setString(1, userId);
            prs.setString(2, userId);
            rs = prs.executeQuery();
            while (rs.next()) {
                ReportsBeanVO bean = new ReportsBeanVO();
                bean.setReportId(rs.getString("URE_ID"));
                bean.setReportView(rs.getString("URE_VIEW"));
                bean.setReportTitle(rs.getString("URE_VIEW_NAME"));
                bean.setReportTable(rs.getString("URE_TABLE_NAME"));
                bean.setReportName(rs.getString("URE_NAME"));
                bean.setReportRemarks(rs.getString("URE_NARRATIVE"));
                bean.setRepCrUid(rs.getString("URE_CR_UID"));
                bean.setRepCrUname(rs.getString("URE_CR_UNAME"));
                bean.setRepCrDt(rs.getTimestamp("URE_CR_DT"));
                bean.setReportSec(rs.getString("FILTER_CNT"));
                result.add(bean);
            }
        } catch (Exception e) {
            logger.error("Error in Fetching Report Type from View List => {}", e);
        } finally {

            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - Report Type from View List Exit");
        return result;
    }

    public Map<String, String> loadAllUsersList() {
        logger.debug("DynamicReportsDAO - Active Users List Entry");
        Map<String, String> result = new HashMap<>();
        try {
            con = getDBConnection(getDataSource());
            String sql = "SELECT CU_USER_ID, CU_USER_NAME FROM M_CRM_USER WHERE CU_LOCK_YN = '0'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                result.put(rs.getString("CU_USER_ID"), rs.getString("CU_USER_NAME"));
            }
        } catch (Exception e) {
            logger.error("Error in Fetching Active Users List => {}", e);
        } finally {
            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - Active Users List Exit");
        return result;
    }

    public Map<String, String> loadAllRolesList() {
        logger.debug("DynamicReportsDAO - Active Roles List Entry");
        Map<String, String> result = new HashMap<>();
        try {
            con = getDBConnection(getDataSource());
            String sql = "SELECT AC_CODE, AC_DESC FROM M_APP_CODES WHERE AC_TYPE = 'CRM_TEAM'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                result.put(rs.getString("AC_CODE"), rs.getString("AC_DESC"));
            }
        } catch (Exception e) {
            logger.error("Error in Fetching Active Roles List => {}", e);
        } finally {
            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - Active Roles List Exit");
        return result;
    }

    public List<KeyValue> loadReportViews(String acMcDfCode) {
        logger.debug("DynamicReportsDAO - Report View List Entry");
        List<KeyValue> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            if (acMcDfCode == null || "".equals(acMcDfCode)) {
                acMcDfCode = "crm_all";
            } else {
                acMcDfCode = "crm_" + acMcDfCode;
            }
            Object[] params = new Object[]{"," + acMcDfCode + ","};
            String sql = " SELECT ac_Code key, ac_Desc value, ac_Mc_Code info, ac_Long_Desc info1 FROM M_App_Codes WHERE ac_Type = 'ENQUIRY_VIEW' AND "
                    + " TRUNC(SYSDATE) BETWEEN TRUNC(NVL(AC_EFF_FM_DT, SYSDATE)) AND TRUNC(NVL(AC_EFF_TO_DT, SYSDATE)) AND INSTR(AC_MAST_DEF_CODE, ?) > 0";
            result = executeList(con, sql, params, KeyValue.class);
        } catch (Exception e) {
            logger.error("Error in Fetching Report View List => {}", e);
        } finally {
            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - Report View List Exit");
        return result;
    }

    public List<KeyValue> loadReportColumnsForTab(final String tableName) {
        logger.debug("DynamicReportsDAO - Report Columns List from " + tableName + " - Entry");
        List<KeyValue> result = new LinkedList<>();
        if (tableName != null && !"".equals(tableName.trim())) {
            try {
                con = getDBConnection(getDataSource());
                Statement st = con.createStatement();
                ResultSet rset = st.executeQuery("SELECT * FROM \"" + tableName + "\"");
                ResultSetMetaData md = rset.getMetaData();
                String sql = "SELECT ac_Mc_Code FROM   M_App_Codes WHERE ac_Type = 'ENQ_LOOKUP'";
                Object[] params = new Object[]{};
                List<Object> list = executeList(con, sql, params, Object.class);
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    KeyValue obj = new KeyValue();
                    obj.setKey(md.getColumnLabel(i));
                    obj.setValue(md.getColumnLabel(i));
                    for (Object obj1 : list) {
                        String s = (String) obj1;
                        if (md.getColumnLabel(i).equals(s)) {
                            obj.setInfo("Yes");
                            break;
                        } else {
                            obj.setInfo("No");
                        }
                    }
                    result.add(obj);
                }
                /*rs = con.getMetaData().getColumns(null, null, tableName, null);
                 while(rs.next()){
                 System.out.println(" Cl :: "+rs.getString("COLUMN_NAME"));
                 result.add(rs.getString("COLUMN_NAME"));
                 }*/
            } catch (Exception e) {
                logger.error("Error in Fetching Report Columns List from " + tableName + " => {}", e);
            } finally {
                closeDbConn(con);
            }
        }
        logger.debug("DynamicReportsDAO - Report Columns List from " + tableName + " - Exit");
        return result;
    }

    public List<KeyValue> loadReportFilterColumns(final String tableName) {
        logger.debug("DynamicReportsDAO - Report Filter Columns List from " + tableName + " - Entry");
        List<KeyValue> result = new LinkedList<>();
        if (tableName != null && !"".equals(tableName.trim())) {
            try {
                con = getDBConnection(getDataSource());
                Statement st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM \"" + tableName + "\"");
                ResultSetMetaData md = rs.getMetaData();
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    KeyValue obj = new KeyValue();
                    obj.setKey(md.getColumnLabel(i));
                    obj.setValue(md.getColumnLabel(i));
                    if (Types.DATE == md.getColumnType(i)) {
                        obj.setInfo("D");
                    } else if (Types.TIMESTAMP == md.getColumnType(i)) {
                        obj.setInfo("T");
                    } else if (Types.BIGINT == md.getColumnType(i) || Types.DECIMAL == md.getColumnType(i)
                            || Types.DOUBLE == md.getColumnType(i) || Types.FLOAT == md.getColumnType(i)
                            || Types.INTEGER == md.getColumnType(i) || Types.NUMERIC == md.getColumnType(i)) {
                        obj.setInfo("N");
                    } else {
                        obj.setInfo("C");
                    }
                    result.add(obj);
                }
            } catch (Exception e) {
                logger.error("Error in Fetching Report Filter Columns List from " + tableName + " => {}", e);
            } finally {
                closeDbConn(con);
            }
        }
        logger.debug("DynamicReportsDAO - Report Filter Columns List from " + tableName + " - Exit");
        return result;
    }

    public String saveReportParams(final ReportsBeanVO bean, final String userId){
        String result = null;
        try{
            con = getDBConnection(getDataSource());
            String query = "DELETE FROM M_USER_REPORTS_PARAM WHERE URP_TYPE = 'F' AND URP_URE_ID = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, bean.getReportId());
            prs.executeUpdate();
            prs.close();
            query = "INSERT INTO M_USER_REPORTS_PARAM(URP_URE_ID, URP_TYPE, URP_SR_NO, URP_DATA_TYPE, URP_COLUMN, URP_OPERATOR, URP_VALUE_FM,"
                    + " URP_VALUE_TO, URP_CONDITION, URP_ORDER, URP_CR_UID, URP_CR_DT) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
            prs = con.prepareStatement(query);
            int j = 0;
            if (bean.getRepFilter() != null) {
                for (ReportsFilterVO filters : bean.getRepFilter()) {
                    if (filters != null) {
                        prs.setString(1, bean.getReportId());
                        prs.setString(2, "F");
                        prs.setInt(3, ++j);
                        prs.setString(4, filters.getUrpDataType());
                        prs.setString(5, filters.getUrpColumn());
                        prs.setString(6, filters.getUrpOperator());
                        prs.setString(7, filters.getUrpValueFm());
                        prs.setString(8, filters.getUrpValueTo());
                        prs.setString(9, filters.getUrpCondition());
                        prs.setString(10, filters.getUrpOrder());
                        prs.setString(11, userId);
                        prs.addBatch();
                    }
                }
            }
            prs.executeBatch();
            con.commit();
        } catch (Exception e) {
            logger.error("Error in Insert Report Params Enquiry => {}", e);
            result = e.getMessage();
        } finally {
            closeDBComp(prs, null, con);
        }
        logger.debug("DynamicReportsDAO - Insert Report Params Enquiry Exit");
        return result;
    }

    public String insertReportEnquiry(final ReportsBeanVO bean, final String userId) {
        logger.debug("DynamicReportsDAO - Insert Report Enquiry Entry");
        String result = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT NVL(MAX(URE_ID), 0)+1 FROM M_USER_REPORTS";
            prs = con.prepareStatement(query);
            rs = prs.executeQuery();
            if (rs.next()) {
                bean.setReportId(rs.getString(1));
            }
            query = "INSERT INTO M_USER_REPORTS(URE_ID, URE_VIEW, URE_NAME, URE_NARRATIVE, URE_SELECTED_COLS, URE_SEC_TYPE, URE_USER_ROLES,"
                    + " URE_CR_UID, URE_CR_DT, URE_TYPE) VALUES(?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?)";
            prs = con.prepareStatement(query);
            prs.setString(1, bean.getReportId());
            prs.setString(2, bean.getReportView());
            prs.setString(3, bean.getReportName());
            prs.setString(4, bean.getReportRemarks());
            Clob clob = con.createClob();
            String columns = "";
            for (String col : bean.getRepColumns()) {
                columns += col + "~";
            }
            clob.setString(1, columns.substring(0, columns.length() - 1));
            prs.setClob(5, clob);
            prs.setString(6, bean.getReportSec());
            columns = ",";
            if (!"R".equalsIgnoreCase(bean.getReportSec())) {
                for (String col : bean.getReportUsers()) {
                    columns += col + ",";
                }
            } else {
                for (String col : bean.getReportRoles()) {
                    columns += col + ",";
                }
            }
            prs.setString(7, columns);
            prs.setString(8, userId);
            System.out.println(bean.getReportType());
            if(!"".equals(bean.getReportType())){
                prs.setString(9, bean.getReportType());
            }else{
                prs.setString(9, "C");
            }
            int i = prs.executeUpdate();
            clob.free();
            if (i > 0) {
                query = "INSERT INTO M_USER_REPORTS_PARAM(URP_URE_ID, URP_TYPE, URP_SR_NO, URP_DATA_TYPE, URP_COLUMN, URP_OPERATOR, URP_VALUE_FM,"
                        + " URP_VALUE_TO, URP_CONDITION, URP_ORDER, URP_CR_UID, URP_CR_DT) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
                prs = con.prepareStatement(query);
                int j = 0;
                if (bean.getRepFilter() != null) {
                    for (ReportsFilterVO filters : bean.getRepFilter()) {
                        if (filters != null) {
                            prs.setString(1, bean.getReportId());
                            prs.setString(2, "F");
                            prs.setInt(3, ++j);
                            prs.setString(4, filters.getUrpDataType());
                            prs.setString(5, filters.getUrpColumn());
                            prs.setString(6, filters.getUrpOperator());
                            prs.setString(7, filters.getUrpValueFm());
                            prs.setString(8, filters.getUrpValueTo());
                            prs.setString(9, filters.getUrpCondition());
                            prs.setString(10, filters.getUrpOrder());
                            prs.setString(11, userId);
                            prs.addBatch();
                        }
                    }
                }
                j = 0;
                if (bean.getRepSort() != null) {
                    for (ReportsFilterVO sorts : bean.getRepSort()) {
                        if (sorts != null) {
                            prs.setString(1, bean.getReportId());
                            prs.setString(2, "O");
                            prs.setInt(3, ++j);
                            prs.setString(4, sorts.getUrpDataType());
                            prs.setString(5, sorts.getUrpColumn());
                            prs.setString(6, sorts.getUrpOperator());
                            prs.setString(7, sorts.getUrpValueFm());
                            prs.setString(8, sorts.getUrpValueTo());
                            prs.setString(9, sorts.getUrpCondition());
                            prs.setString(10, sorts.getUrpOrder());
                            prs.setString(11, userId);
                            prs.addBatch();
                        }
                    }
                }
                j = 0;
                if (bean.getRepGroup() != null) {
                    int k =0;
                    for (String group : bean.getRepGroup()) {
                        if (group != null) {
                            prs.setString(1, bean.getReportId());
                            prs.setString(2, "G");
                            prs.setInt(3, ++j);
                            prs.setNull(4, Types.VARCHAR);
                            prs.setString(5, bean.getRepGroup().get(k));
                            prs.setNull(6, Types.VARCHAR);
                            prs.setNull(7, Types.VARCHAR);
                            prs.setNull(8, Types.VARCHAR);
                            prs.setNull(9, Types.VARCHAR);
                            prs.setNull(10, Types.VARCHAR);
                            prs.setString(11, userId);
                            prs.addBatch();
                            ++k;
                        }
                    }
                }

                prs.executeBatch();
            }
        } catch (Exception e) {
            logger.error("Error in Insert Report Enquiry => {}", e);
            result = e.getMessage();
        } finally {

            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - Insert Report Enquiry Exit");
        return result;
    }

    public String updateReportEnquiry(final ReportsBeanVO bean, final String userId) {
        logger.debug("DynamicReportsDAO - Update Report Enquiry Entry");
        String result = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "UPDATE M_USER_REPORTS SET URE_NAME = ?, URE_NARRATIVE = ?, URE_SELECTED_COLS = ?, URE_SEC_TYPE = ?, URE_USER_ROLES = ?, URE_UPD_UID = ?,"
                    + " URE_UPD_DT = SYSDATE WHERE URE_ID = ? AND URE_VIEW = ? ";
            prs = con.prepareStatement(query);
            prs.setString(1, bean.getReportName());
            prs.setString(2, bean.getReportRemarks());
            Clob clob = con.createClob();
            String columns = "";
            for (String col : bean.getRepColumns()) {
                columns += col + "~";
            }
            clob.setString(1, columns.substring(0, columns.length() - 1));
            prs.setClob(3, clob);
            prs.setString(4, bean.getReportSec());
            columns = ",";
            if (!"R".equalsIgnoreCase(bean.getReportSec())) {
                for (String col : bean.getReportUsers()) {
                    columns += col + ",";
                }
            } else {
                for (String col : bean.getReportRoles()) {
                    columns += col + ",";
                }
            }
            prs.setString(5, columns);
            prs.setString(6, userId);
            prs.setString(7, bean.getReportId());
            prs.setString(8, bean.getReportView());
            int i = prs.executeUpdate();
            clob.free();
            if (i > 0) {
                query = "DELETE FROM M_USER_REPORTS_PARAM WHERE URP_URE_ID = ?";
                prs = con.prepareStatement(query);
                prs.setString(1, bean.getReportId());
                prs.executeUpdate();
                query = "INSERT INTO M_USER_REPORTS_PARAM(URP_URE_ID, URP_TYPE, URP_SR_NO, URP_DATA_TYPE, URP_COLUMN, URP_OPERATOR, URP_VALUE_FM,"
                        + " URP_VALUE_TO, URP_CONDITION, URP_ORDER, URP_CR_UID, URP_CR_DT) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
                prs = con.prepareStatement(query);
                int j = 0;
                if (bean.getRepFilter() != null) {
                    for (ReportsFilterVO filters : bean.getRepFilter()) {
                        if (filters != null) {
                            prs.setString(1, bean.getReportId());
                            prs.setString(2, "F");
                            prs.setInt(3, ++j);
                            prs.setString(4, filters.getUrpDataType());
                            prs.setString(5, filters.getUrpColumn());
                            prs.setString(6, filters.getUrpOperator());
                            prs.setString(7, filters.getUrpValueFm());
                            prs.setString(8, filters.getUrpValueTo());
                            prs.setString(9, filters.getUrpCondition());
                            prs.setString(10, filters.getUrpOrder());
                            prs.setString(11, userId);
                            prs.addBatch();
                        }
                    }
                }
                j = 0;
                if (bean.getRepSort() != null) {
                    for (ReportsFilterVO sorts : bean.getRepSort()) {
                        if (sorts != null) {
                            prs.setString(1, bean.getReportId());
                            prs.setString(2, "O");
                            prs.setInt(3, ++j);
                            prs.setString(4, sorts.getUrpDataType());
                            prs.setString(5, sorts.getUrpColumn());
                            prs.setString(6, sorts.getUrpOperator());
                            prs.setString(7, sorts.getUrpValueFm());
                            prs.setString(8, sorts.getUrpValueTo());
                            prs.setString(9, sorts.getUrpCondition());
                            prs.setString(10, sorts.getUrpOrder());
                            prs.setString(11, userId);
                            prs.addBatch();
                        }
                    }
                }
                j = 0;
                if (bean.getRepGroup() != null) {
                    int k =0;
                    for (String group : bean.getRepGroup()) {
                        if (group != null) {
                            prs.setString(1, bean.getReportId());
                            prs.setString(2, "G");
                            prs.setInt(3, ++j);
                            prs.setNull(4, Types.VARCHAR);
                            prs.setString(5, bean.getRepGroup().get(k));
                            prs.setNull(6, Types.VARCHAR);
                            prs.setNull(7, Types.VARCHAR);
                            prs.setNull(8, Types.VARCHAR);
                            prs.setNull(9, Types.VARCHAR);
                            prs.setNull(10, Types.VARCHAR);
                            prs.setString(11, userId);
                            prs.addBatch();
                            ++k;
                        }
                    }
                }
                prs.executeBatch();
            }
        } catch (Exception e) {
            logger.error("Error in Update Report Enquiry => {}", e);
            result = e.getMessage();
        } finally {
            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - Update Report Enquiry Exit");
        return result;
    }

    public List<String> loadStoredReportColumns(final ReportsBeanVO bean) {
        logger.debug("DynamicReportsDAO - Stored Report Columns List - Entry");
        List<String> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT URE_NARRATIVE, URE_SELECTED_COLS, URE_SEC_TYPE, URE_USER_ROLES, AC_LONG_DESC FROM M_USER_REPORTS, M_APP_CODES"
                    + " WHERE URE_VIEW = ? AND URE_ID = ? AND AC_TYPE='ENQUIRY_VIEW' AND AC_CODE=URE_VIEW";
            prs = con.prepareStatement(query);
            prs.setString(1, bean.getReportView());
            prs.setString(2, bean.getReportId());
            rs = prs.executeQuery();
            while (rs.next()) {
                bean.setReportRemarks(rs.getString("URE_NARRATIVE"));
                Clob clob = rs.getClob("URE_SELECTED_COLS");
                String listString = clob.getSubString(1, (int) clob.length());
                result = Arrays.asList(listString.substring(0, listString.length()).split("~\\s*"));
                bean.setReportSec(rs.getString("URE_SEC_TYPE"));
                listString = rs.getString("URE_USER_ROLES");
                if (!"R".equalsIgnoreCase(bean.getReportSec())) {
                    bean.setReportUsers(Arrays.asList(listString.substring(1, listString.length()).split(",\\s*")));
                    bean.setReportRoles(new LinkedList<String>());
                } else {
                    bean.setReportUsers(new LinkedList<String>());
                    bean.setReportRoles(Arrays.asList(listString.substring(1, listString.length()).split(",\\s*")));
                }
                bean.setReportViewDesc(rs.getString("AC_LONG_DESC"));
            }
        } catch (Exception e) {
            logger.error("Error in Fetching Stored Report Columns List => {}", e);
        } finally {
            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - Stored Report Columns List - Exit");
        return result;
    }

    public List<ReportsFilterVO> loadStoredReportParameters(final String type, final String reportId) {
        logger.debug("DynamicReportsDAO - Stored Report Filters List - Entry");
        List<ReportsFilterVO> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT URP_SR_NO, URP_DATA_TYPE, URP_COLUMN, URP_OPERATOR, URP_VALUE_FM, URP_VALUE_TO, URP_CONDITION,"
                    + " URP_ORDER FROM M_USER_REPORTS_PARAM WHERE URP_TYPE=? AND URP_URE_ID = ? ORDER BY URP_SR_NO";
            prs = con.prepareStatement(query);
            prs.setString(1, type);
            prs.setString(2, reportId);
            rs = prs.executeQuery();
            while (rs.next()) {
                ReportsFilterVO obj = new ReportsFilterVO();
                obj.setUrpSrNo(rs.getInt("URP_SR_NO"));
                obj.setUrpDataType(rs.getString("URP_DATA_TYPE"));
                obj.setUrpColumn(rs.getString("URP_COLUMN"));
                obj.setUrpOperator(rs.getString("URP_OPERATOR"));
                obj.setUrpValueFm(rs.getString("URP_VALUE_FM"));
                obj.setUrpValueTo(rs.getString("URP_VALUE_TO"));
                obj.setUrpCondition(rs.getString("URP_CONDITION"));
                obj.setUrpOrder(rs.getString("URP_ORDER"));
                result.add(obj);
            }
        } catch (Exception e) {
            logger.error("Error in Fetching Stored Report Filters List => {}", e);
        } finally {
            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - Stored Report Filters List - Exit");
        return result;
    }

    public List<Map<String, Map<String, String>>> loadReportData(final ReportsBeanVO bean) {
        logger.debug("DynamicReportsDAO - Report Generation from " + bean.getReportTable() + " - Entry");
        List<Map<String, Map<String, String>>> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT ";
            for (String col : bean.getRepColumns()) {
                boolean aggregate = false;
                for(String s : AGGREGATE_FUNCTIONS) {
                    if(col.startsWith(s)) {
                        s = col.substring(col.indexOf("(") + 1, col.indexOf(")"));
                        col = col.replace(s, "\"" + s + "\"");
                        aggregate = true;
                        break;
                    }
                }
                query = query + (aggregate ? col : "\"" + col + "\"") + ", ";
            }
            query = query + "1 FROM " + bean.getReportTable();
            if (bean.getRepFilter() != null && !(bean.getRepFilter().isEmpty())) {
                query += " WHERE ";
                for (ReportsFilterVO filter : bean.getRepFilter()) {
                    if (filter != null && filter.getUrpColumn() != null && !"".equals(filter.getUrpColumn().trim())) {
                        logger.info("Cond: " + filter.getUrpColumn() + " : " + filter.getUrpOperator() + " : " + filter.getUrpValueFm());
                        if (filter.getUrpCondition() != null) {
                            query += " " + filter.getUrpCondition();
                        }
                        query += " " + constructCondt(filter.getUrpColumn(), filter.getUrpDataType(), filter.getUrpOperator(), filter.getUrpValueFm(), filter.getUrpValueTo());
                    }
                }
            }
            if (bean.getRepSort() != null) {
                int i = 0;
                for (ReportsFilterVO order : bean.getRepSort()) {
                    if (order != null && order.getUrpColumn() != null && !"".equals(order.getUrpColumn().trim())) {
                        logger.debug("Sort: " + order.getUrpColumn() + " : " + order.getUrpOrder());
                        query += (i == 0 ? " ORDER BY " : ", ") + ("\"" + order.getUrpColumn() + "\" " + order.getUrpOrder());
                        i++;
                    }
                }
            }
            if (bean.getRepGroup() != null && !bean.getRepGroup().isEmpty()) {
                String groupBy = "";
                for (String grp : bean.getRepGroup()) {
                    groupBy += ", \"" + grp + "\"";
                }
                groupBy = groupBy.substring(2);
                query += " GROUP BY " + groupBy;
            }
            logger.debug("Query => " + query);
            prs = con.prepareStatement(query);
            rs = prs.executeQuery();
            int i = 1;
            // "email" - Email campaign, "lead" - Lead uploaad
            if ("email".equals(bean.getPluginFor()) || "lead".equals(bean.getPluginFor())) {
                PreparedStatement prs_temp = con.prepareStatement("DELETE FROM T_BULK_SMS_EMAIL");
                prs_temp.executeUpdate();
                prs_temp.close();
                prs_temp = con.prepareStatement("INSERT INTO T_BULK_SMS_EMAIL (SE_EMAIL_SMS, SE_COL_1, SE_COL_2, SE_COL_3, SE_COL_4, SE_COL_5, SE_COL_6, SE_COL_7, SE_COL_8, SE_COL_9, SE_COL_10) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                int row = 0;
                while (rs.next()) {
                    int d = 0;
                    for (String col : bean.getRepColumns()) {
                        prs_temp.setString(++d, rs.getString(col) == null ? "X" : rs.getString(col));
                    }
                    for (int k = bean.getRepColumns().size() + 1; k <= 11; k++) {
                        prs_temp.setString(++d, "X");
                    }
                    prs_temp.addBatch();
                    if (row == 50) {
                        row = 0;
                        prs_temp.executeBatch();
                    } else {
                        ++row;
                    }
                }
                if (row != 0) {
                    prs_temp.executeBatch();
                }
                prs_temp.close();
                con.commit();
            } else {
                ResultSetMetaData rsmd = null;
                if (rs != null) {
                    rsmd = rs.getMetaData();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                while (rs.next()) {
                    Map<String, Map<String, String>> map = new LinkedHashMap<>();
                    for (String col : bean.getRepDisplayColumns()) {
                        //map.put(col, rs.getString(col));
                        Map<String, String> val = new LinkedHashMap<>();
                        //KeyValue keyValue = new KeyValue(rs.getString(col), String.valueOf(rsmd.getColumnType(i++)));

                        if (Types.TIMESTAMP == rsmd.getColumnType(i) || Types.DATE == rsmd.getColumnType(i)) {
                            if (rs.getDate(i) == null) {
                                val.put("", String.valueOf(rsmd.getColumnType(i)));
                            } else {
                                val.put(sdf.format(new Date(rs.getTimestamp(i).getTime())), String.valueOf(rsmd.getColumnType(i)));
                            }
                        } else {
                            val.put(rs.getString(i) == null ? "" : rs.getString(i), String.valueOf(rsmd.getColumnType(i)));
                        }
                        map.put(col, val);
                        ++i;
                    }
                    result.add(map);
                    i = 1;
                }
            }
            prs.close();
        } catch (Exception e) {
            logger.error("Error in Fetching Report eneration from " + bean.getReportTable() + " => {}", e);
            Map err = new HashMap<String, String>();
            err.put("Error", e.getMessage());
            result.add(err);
        } finally {
            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - Report Generation from " + bean.getReportTable() + " - Exit");
        return result;
    }

    private String constructCondt(String column, String dataType, String condt, String valueFm, String valueTo) {
        String result = "";
        if (valueFm == null || "null".equalsIgnoreCase(valueFm)) {
            result = "\"" + column + "\" IS NULL ";
        } else if ("N".equalsIgnoreCase(dataType)) {
            if ("eq".equals(condt)) {
                result = "\"" + column + "\" = " + valueFm;
            } else if ("nq".equals(condt)) {
                result = "\"" + column + "\" != " + valueFm;
            } else if ("gt".equals(condt)) {
                result = "\"" + column + "\" > " + valueFm;
            } else if ("lt".equals(condt)) {
                result = "\"" + column + "\" < " + valueFm;
            } else if ("btw".equals(condt)) {
                result = "\"" + column + "\" between " + valueFm + " and " + valueTo;
            } else if ("nbtw".equals(condt)) {
                result = "\"" + column + "\" not between " + valueFm + " and " + valueTo;
            } else if ("in".equals(condt)) {
                result = "\"" + column + "\" in (" + valueFm + ")";
            } else if ("nin".equals(condt)) {
                result = "\"" + column + "\" not in (" + valueFm + ")";
            }
        } else if ("C".equalsIgnoreCase(dataType)) {
            if ("eq".equals(condt)) {
                result = "\"" + column + "\" = '" + valueFm + "'";
            } else if ("nq".equals(condt)) {
                result = "\"" + column + "\" != '" + valueFm + "'";
            } else if ("btw".equals(condt)) {
                result = "\"" + column + "\" between '" + valueFm + "' and '" + valueTo + "'";
            } else if ("nbtw".equals(condt)) {
                result = "\"" + column + "\" not between '" + valueFm + "' and '" + valueTo + "'";
            } else if ("in".equals(condt)) {
                valueFm = valueFm.replaceAll("'", "");
                result = "\"" + column + "\" in ('" + valueFm.replaceAll(",", "','") + "')";
            } else if ("nin".equals(condt)) {
                valueFm = valueFm.replaceAll("'", "");
                result = "\"" + column + "\" not in ('" + valueFm.replaceAll(",", "','") + "')";
            }
        } else if ("bp".equals(condt)) {
            if ("td".equals(valueFm)) {
                result = "TRUNC(\"" + column + "\") = TRUNC(SYSDATE)";
            } else if ("yd".equals(valueFm)) {
                result = "TRUNC(\"" + column + "\") = TRUNC(SYSDATE-1)";
            } else if ("tw".equals(valueFm)) {
                result = "TRUNC(\"" + column + "\") BETWEEN TRUNC(SYSDATE, 'DAY') AND TRUNC(SYSDATE, 'DAY') + 6";
            } else if ("lw".equals(valueFm)) {
                result = "TRUNC(\"" + column + "\") BETWEEN TRUNC(SYSDATE - 7, 'DAY') AND TRUNC(SYSDATE - 7, 'DAY') + 6";
            } else if ("tm".equals(valueFm)) {
                result = "TRUNC(\"" + column + "\") BETWEEN TRUNC(SYSDATE, 'MONTH') AND TRUNC(LAST_DAY(SYSDATE))";
            } else if ("lm".equals(valueFm)) {
                result = "TRUNC(\"" + column + "\") BETWEEN TRUNC(ADD_MONTHS(SYSDATE, -1), 'MONTH') AND TRUNC(LAST_DAY(ADD_MONTHS(SYSDATE, -1)))";
            } else if ("tq".equals(valueFm)) {
                result = "TRUNC(\"" + column + "\") BETWEEN TRUNC(SYSDATE, 'Q') AND ADD_MONTHS(TRUNC(SYSDATE, 'Q'), 3) - 1";
            } else if ("lq".equals(valueFm)) {
                result = "TRUNC(\"" + column + "\") BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'Q'), -3) AND ADD_MONTHS(ADD_MONTHS(TRUNC(SYSDATE, 'Q'), -3), 3) - 1";
            } else if ("ty".equals(valueFm)) {
                result = "TRUNC(\"" + column + "\") BETWEEN TRUNC(SYSDATE, 'YEAR') AND ADD_MONTHS(TRUNC(SYSDATE, 'YEAR'), 12) - 1 ";
            } else if ("ly".equals(valueFm)) {
                result = "TRUNC(\"" + column + "\") BETWEEN TRUNC(ADD_MONTHS(SYSDATE, -12), 'YEAR') AND ADD_MONTHS(TRUNC(ADD_MONTHS(SYSDATE, -12), 'YEAR'), 12) - 1";
            }
        } else if ("D".equalsIgnoreCase(dataType)) {
            if ("eq".equals(condt)) {
                result = "TRUNC(\"" + column + "\") = TRUNC(TO_DATE('" + valueFm + "', 'DD/MM/YYYY'))";
            } else if ("nq".equals(condt)) {
                result = "TRUNC(\"" + column + "\") != TRUNC(TO_DATE('" + valueFm + "', 'DD/MM/YYYY'))";
            } else if ("gt".equals(condt)) {
                result = "TRUNC(\"" + column + "\") > TRUNC(TO_DATE('" + valueFm + "', 'DD/MM/YYYY'))";
            } else if ("lt".equals(condt)) {
                result = "TRUNC(\"" + column + "\") < TRUNC(TO_DATE('" + valueFm + "', 'DD/MM/YYYY'))";
            } else if ("btw".equals(condt)) {
                result = "TRUNC(\"" + column + "\") between TRUNC(TO_DATE('" + valueFm + "', 'DD/MM/YYYY')) and TRUNC(TO_DATE('" + valueTo + "', 'DD/MM/YYYY'))";
            } else if ("nbtw".equals(condt)) {
                result = "TRUNC(\"" + column + "\") not between TRUNC(TO_DATE('" + valueFm + "', 'DD/MM/YYYY')) and TRUNC(TO_DATE('" + valueTo + "', 'DD/MM/YYYY'))";
            }
        } else if ("T".equalsIgnoreCase(dataType)) {
            if ("eq".equals(condt)) {
                result = "\"" + column + "\" = TO_DATE('" + valueFm + "', 'DD/MM/YYYY HH24:MI')";
            } else if ("nq".equals(condt)) {
                result = "\"" + column + "\" != TO_DATE('" + valueFm + "', 'DD/MM/YYYY HH24:MI')";
            } else if ("gt".equals(condt)) {
                result = "\"" + column + "\" > TO_DATE('" + valueFm + "', 'DD/MM/YYYY HH24:MI')";
            } else if ("lt".equals(condt)) {
                result = "\"" + column + "\" < TO_DATE('" + valueFm + "', 'DD/MM/YYYY HH24:MI')";
            } else if ("btw".equals(condt)) {
                result = "\"" + column + "\" between TO_DATE('" + valueFm + "', 'DD/MM/YYYY HH24:MI') and TO_DATE('" + valueTo + "', 'DD/MM/YYYY HH24:MI')";
            } else if ("nbtw".equals(condt)) {
                result = "\"" + column + "\" not between TO_DATE('" + valueFm + "', 'DD/MM/YYYY HH24:MI') and TO_DATE('" + valueTo + "', 'DD/MM/YYYY HH24:MI')";
            }
        }
        return result;
    }

    public List<KeyValue> inputTypeChange(final String inputType) {
        logger.debug("DynamicReportsDAO - inputTypeChange");
        List<KeyValue> result = new ArrayList<>();
        try {
            con = getDBConnection(getDataSource());
            String sql = "SELECT ac_Value, ac_Long_Desc FROM M_App_Codes WHERE ac_Type = 'ENQ_LOOKUP' AND ac_Mc_Code=?";
            Object[] params = new Object[]{inputType};
            result = executeList(con, sql, params, KeyValue.class);
        } catch (Exception e) {
            logger.error("Error in Fetching Type List => {}", e);
        } finally {

            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - inputTypeChange");
        return result;
    }

    public List<KeyValue> listQuery(final String qry, final String searchValue) {
        logger.debug("DynamicReportsDAO - inputTypeChange");
        List<KeyValue> result = new ArrayList<>();
        try {
            String sql = qry;
            con = getDBConnection(getDataSource());
            Object[] params = null;
            if (searchValue != null) {
                params = new Object[]{"%" + searchValue + "%"};

            } else {
                params = new Object[]{};
            }
            result = executeList(con, sql, params, KeyValue.class);
        } catch (Exception e) {
            logger.error("Error in Fetching Type List => {}", e);
        } finally {

            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - inputTypeChange");
        return result;
    }

   public List<String> loadStoredReportParameterGroups(String type, final ReportsBeanVO bean) {
       List<String> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT URP_COLUMN FROM M_USER_REPORTS_PARAM WHERE URP_TYPE = ? AND  URP_URE_ID = ? ";
            prs = con.prepareStatement(query);
            prs.setString(1, type);
            prs.setString(2, bean.getReportId());
            rs = prs.executeQuery();
            while (rs.next()) {
               result.add(rs.getString("URP_COLUMN"));
            }
        } catch (Exception e) {
            logger.error("Error in Fetching Stored Report Columns List => {}", e);
        } finally {
            closeDbConn(con);
        }
        logger.debug("DynamicReportsDAO - Stored Report Columns List - Exit");
         return result;
    }


}
