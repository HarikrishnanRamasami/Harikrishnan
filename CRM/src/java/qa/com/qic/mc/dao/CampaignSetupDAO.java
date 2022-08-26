/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.mc.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import qa.com.qic.anoud.reports.ReportsBeanVO;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.anoud.vo.campaignChart;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.email.core.MailBodyBuilder;
import qa.com.qic.email.core.MailBodyDataPropertyMap;
import qa.com.qic.email.core.MailBodyUrl;
import qa.com.qic.mc.model.MMktgCampaign;
import qa.com.qic.mc.model.MMktgCampaignDataMap;
import qa.com.qic.mc.model.MMktgCampaignDataParam;
import qa.com.qic.mc.model.MMktgCampaignFilter;
import qa.com.qic.mc.model.MMktgCampaignFilterParm;
import qa.com.qic.mc.model.MMktgCampaignFormField;
import qa.com.qic.mc.model.MMktgCampaignForms;
import qa.com.qic.mc.model.MMktgCampaignPath;
import qa.com.qic.mc.model.MMktgCampaignPathFlow;
import qa.com.qic.mc.model.MMktgCampaignTempUrl;
import qa.com.qic.mc.model.MMktgCampaignTemplate;
import qa.com.qic.mc.model.MMktgCampaignTxn;
import qa.com.qic.mc.model.MMktgCampaignTxnData;
import qa.com.qic.mc.model.TMktgCampaignTxn;
import qa.com.qic.mc.model.TMktgCampaignTxnData;
import qa.com.qic.mc.model.TMktgCampaignTxnLog;
import qa.com.qic.mc.vo.CampaignVO;
import qa.com.qic.utility.helpers.LogUtil;
import qa.com.qic.utility.helpers.Validatory;

/**
 *
 * @author ravindar.singh
 */
public class CampaignSetupDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(CampaignSetupDAO.class);

    private final String dataSource;
    private Connection con = null;
    private PreparedStatement prs = null;
    private ResultSet rs = null;
    private final static String QRY_DATA = "select mcd_camp_id mcdCampId, mcd_contact_id mcdContactId, mcd_contact_ref mcdContactRef, mcd_data_1 mcdData1, mcd_data_2 mcdData2, mcd_data_3 mcdData3, mcd_data_4 mcdData4, mcd_data_5 mcdData5, mcd_data_6 mcdData6, mcd_data_7 mcdData7, mcd_data_8 mcdData8, mcd_data_9 mcdData9, mcd_data_10 mcdData10, mcd_data_11 mcdData11, mcd_data_12 mcdData12, mcd_data_13 mcdData13, mcd_data_14 mcdData14, mcd_data_15 mcdData15, mcd_data_16 mcdData16, mcd_data_17 mcdData17, mcd_data_18 mcdData18, mcd_data_19 mcdData19, mcd_data_20 mcdData20 from w_mktg_campaign_data where mcd_camp_id = ? ";
    private final static String DEL_DATA = "delete from w_mktg_campaign_data where rowid in (select * from (select rowid from w_mktg_campaign_data where mcd_camp_id = ? ";
    private final static String INS_AB_TXT = "insert into t_mktg_campaign_txn (mct_txn_id, mct_camp_id, mct_path_id, mct_path_flow_no, mct_next_run_date,mct_ab_yn) values (s_mct_txn_id.nextval,?,?,?,sysdate,1)";
    private final static String UPD_TXT_RUN = "update t_mktg_campaign_txn set mct_data_count = ?, mct_next_run_date = ?, mct_txn_status = ?, mct_start_date = ?, mct_end_date = ? where mct_txn_id = ? and mct_camp_id = ?";

    private final static String QRY_TXN_DATA_COUNT = "select count(mctd_data_id) from t_mktg_campaign_txn_data where mctd_txn_id = ? and mctd_camp_id = ?";

    public CampaignSetupDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<KeyValue> loadReportColumnsForTab(final String tableName) {
        LOGGER.debug("CampaignSetupDao - Report Columns List from " + tableName + " - Entry");
        List<KeyValue> result = new LinkedList<>();
        if (tableName != null && !"".equals(tableName.trim())) {
            try {
                con = getDBConnection(getDataSource());
                Statement st = con.createStatement();
                ResultSet rset = st.executeQuery("SELECT * FROM \"" + tableName + "\" WHERE 1 = 2");
                ResultSetMetaData md = rset.getMetaData();
                String sql = "SELECT ac_Mc_Code FROM M_App_Codes WHERE ac_Type = 'ENQ_LOOKUP'";
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
                LOGGER.error("Error in Fetching Report Columns List from " + tableName + " => {}", e);
            } finally {
                closeDbConn(con);
            }
        }
        LOGGER.debug("CampaignSetupDao - Report Columns List from " + tableName + " - Exit");
        return result;
    }

    public List<KeyValue> loadReportFilterColumns(final String tableName) {
        LOGGER.debug("CampaignSetupDao - Report Filter Columns List from " + tableName + " - Entry");
        List<KeyValue> result = new LinkedList<>();
        if (tableName != null && !"".equals(tableName.trim())) {
            try {
                con = getDBConnection(getDataSource());
                Statement st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM \"" + tableName + "\" WHERE 1 = 2");
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
                LOGGER.error("Error in Fetching Report Filter Columns List from " + tableName + " => {}", e);
            } finally {
                closeDbConn(con);
            }
        }
        LOGGER.debug("CampaignSetupDao - Report Filter Columns List from " + tableName + " - Exit");
        return result;
    }

    public List<String> getCampaignStoredColumns(Long mcCampId) {
        LOGGER.debug("CampaignSetupDao - Get Campaign Stored Columns Entry");
        List<String> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT MC_SELECTED_COLS FROM m_mktg_campaign WHERE MC_CAMP_ID = ?";
            prs = con.prepareStatement(query);
            prs.setLong(1, mcCampId);
            rs = prs.executeQuery();
            while (rs.next()) {
                String listString = rs.getString("MC_SELECTED_COLS");
                result = Arrays.asList(listString.substring(0, listString.length()).split("~\\s*"));
            }
        } catch (Exception e) {
            LOGGER.error("Error in Fetching Stored Campaign Columns List => {}", e);
        } finally {
            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetupDao - Stored Report Columns List - Exit");
        return result;
    }

    public String getCampaignView(long mcCampId) throws Exception {
        LOGGER.debug("CampaignSetupDao - Get Campaign View - Enter CampId: {}", new Object[]{mcCampId});
        String result = null;
        Connection con = null;
        PreparedStatement ps = null;
        con = getDBConnection(dataSource);

        try {
            String query = "SELECT MC_DATA_SOURCE_VIEW FROM M_MKTG_CAMPAIGN WHERE MC_CAMP_ID = ?";
            ps = con.prepareStatement(query);
            ps.setLong(1, mcCampId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getString("MC_DATA_SOURCE_VIEW");
            }

        } catch (Exception e) {
            LOGGER.error("Error while Get Campaign View", e);
            throw e;
        } finally {
            con.close();
        }
        return result;
    }

    public String updateSelectedColumns(List<String> repColumns, long mcCampId, String userId) throws Exception {
        LOGGER.info("Update Selected Columns CampId: {}, userId: {}", new Object[]{mcCampId, userId});
        String result = null;
        PreparedStatement prs = null;
        int st = 0;
        Connection con = null;
        try {
            con = getDBConnection(dataSource);
            String sql = "UPDATE M_MKTG_CAMPAIGN SET MC_SELECTED_COLS = ?, MC_UPD_UID = ?, MC_UPD_DT = SYSDATE WHERE MC_CAMP_ID = ?";
            prs = con.prepareStatement(sql);
            String columns = "";
            for (String col : repColumns) {
                columns += col + "~";
            }
            //columns.setString(1, columns.substring(0, columns.length() - 1));
            prs.setString(1, columns.substring(0, columns.length() - 1));
            prs.setString(2, userId);
            prs.setLong(3, mcCampId);
            st = prs.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error while Updating Selected Columns", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
        return result;
    }

    public void insertMktgFilterParams(CampaignVO bean, Long mcCampId, String userId) {
        try {
            con = getDBConnection(getDataSource());
            String query = "DELETE FROM M_MKTG_CAMPAIGN_DATA_PARAM WHERE MDP_CAMP_ID = ?";
            prs = con.prepareStatement(query);
            prs.setLong(1, mcCampId);
            prs.executeUpdate();
            prs.close();
            if (bean.getDataParam() != null && !bean.getDataParam().isEmpty()) {
                query = "INSERT INTO M_MKTG_CAMPAIGN_DATA_PARAM(MDP_CAMP_ID, MDP_TYPE, MDP_SR_NO, MDP_DATA_TYPE, MDP_COLUMN, MDP_OPERATOR, MDP_VALUE_FM,"
                        + " MDP_VALUE_TO, MDP_CONDITION, MDP_ORDER, MDP_CR_UID, MDP_CR_DT) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
                prs = con.prepareStatement(query);
                int j = 0;
                for (MMktgCampaignDataParam filters : bean.getDataParam()) {
                    if (filters != null) {
                        prs.setLong(1, mcCampId);
                        prs.setString(2, "F");
                        prs.setInt(3, ++j);
                        prs.setString(4, filters.getMdpDataType());
                        prs.setString(5, filters.getMdpColumn());
                        prs.setString(6, filters.getMdpOperator());
                        prs.setString(7, filters.getMdpValueFm());
                        prs.setString(8, filters.getMdpValueTo());
                        prs.setString(9, filters.getMdpCondition());
                        prs.setString(10, filters.getMdpOrder());
                        prs.setString(11, userId);
                        prs.addBatch();
                    }
                }
                prs.executeBatch();
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error in Insert Data Param => {}", e);
        } finally {
            closeDBComp(prs, null, con);
        }
        LOGGER.debug("DcampaignSetupDAO - Insert Data Params Exit");
    }

    public List<MMktgCampaignDataParam> getCampaignStoredFilters(Long mcCampId) {
        LOGGER.debug("campaignSetUp DAO - Stored Campaign Data Param Filters List - Entry CampaignId: {}", new Object[]{mcCampId});
        List<MMktgCampaignDataParam> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT MDP_SR_NO, MDP_DATA_TYPE, MDP_COLUMN, MDP_OPERATOR, MDP_VALUE_FM, MDP_VALUE_TO, MDP_CONDITION,"
                    + " MDP_ORDER FROM M_MKTG_CAMPAIGN_DATA_PARAM WHERE MDP_CAMP_ID = ? ORDER BY MDP_SR_NO";
            prs = con.prepareStatement(query);
            prs.setLong(1, mcCampId);
            rs = prs.executeQuery();
            while (rs.next()) {
                MMktgCampaignDataParam obj = new MMktgCampaignDataParam();
                obj.setMdpSrNo(rs.getByte("MDP_SR_NO"));
                obj.setMdpDataType(rs.getString("MDP_DATA_TYPE"));
                obj.setMdpColumn(rs.getString("MDP_COLUMN"));
                obj.setMdpOperator(rs.getString("MDP_OPERATOR"));
                obj.setMdpValueFm(rs.getString("MDP_VALUE_FM"));
                obj.setMdpValueTo(rs.getString("MDP_VALUE_TO"));
                obj.setMdpCondition(rs.getString("MDP_CONDITION"));
                obj.setMdpOrder(rs.getString("MDP_ORDER"));
                result.add(obj);
            }
        } catch (Exception e) {
            LOGGER.error("Error in Fetching Stored Data Param Filters List => {}", e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        LOGGER.debug("CampaignSetupDao - Stored Campaign Data Param Filters List - Exit");
        return result;
    }

    public List<MMktgCampaignDataMap> loadCampaignDataMappings(Long mcCampId) {
        LOGGER.debug("campaignSetUp DAO - Load Campaign Data Mappings List - Entry CampId: {}", new Object[]{mcCampId});
        List<MMktgCampaignDataMap> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{mcCampId};
            query = new StringBuilder("SELECT MCDM_DATA_COL mcdmDataCol, MCDM_DATA_TYPE mcdmDataType, MCDM_DATA_NAME mcdmDataName, MCDM_SAMPLE_DATA mcdmSampleData  FROM M_MKTG_CAMPAIGN_DATA_MAP WHERE MCDM_CAMP_ID = ? ");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignDataMap.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving campaign list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> getDataTypes() {
        LOGGER.debug("CampaignSetupDao - getDataTypes");
        List<KeyValue> result = new ArrayList<>();
        try {
            con = getDBConnection(getDataSource());
            String sql = "SELECT PARA_SUB_CODE key, PARA_NAME value FROM M_APP_PARAMETER WHERE PARA_CODE = 'MK_DATA_TYPE'";
            result = executeList(con, sql, null, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error in Fetching Type List => {}", e);
        } finally {

            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetupDao - getDataTypes");
        return result;
    }

    public List<KeyValue> getFieldTypes() {
        LOGGER.debug("CampaignSetupDao - getFieldTypes");
        List<KeyValue> result = new ArrayList<>();
        try {
            con = getDBConnection(getDataSource());
            String sql = "SELECT PARA_SUB_CODE key, PARA_NAME value FROM M_APP_PARAMETER WHERE PARA_CODE = 'MK_FLD_TYPE'";
            result = executeList(con, sql, null, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error in Fetching Field Type List => {}", e);
        } finally {

            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetupDao - getFieldTypes");
        return result;
    }

    public void updateDataMappings(CampaignVO bean, Long mcCampId, String userId) {
        LOGGER.debug("CampaignSetupDao - Update Data Mappings - Enter CampId: {}, UserId: {}", new Object[]{mcCampId, userId});
        try {
            con = getDBConnection(getDataSource());
            String query = "UPDATE M_MKTG_CAMPAIGN_DATA_MAP SET MCDM_DATA_NAME = ?, MCDM_DATA_TYPE = ?,  MCDM_SAMPLE_DATA = ?, MCDM_UPD_UID = ?, MCDM_UPD_DT = SYSDATE WHERE MCDM_DATA_COL = ? AND MCDM_CAMP_ID = ? ";
            prs = con.prepareStatement(query);
            if (bean.getDataMap() != null) {
                for (MMktgCampaignDataMap dataMap : bean.getDataMap()) {
                    if (dataMap != null) {
                        prs.setString(1, dataMap.getMcdmDataName());
                        prs.setString(2, dataMap.getMcdmDataType());
                        prs.setString(3, dataMap.getMcdmSampleData());
                        prs.setString(4, userId);
                        prs.setString(5, dataMap.getMcdmDataCol());
                        prs.setLong(6, mcCampId);
                        prs.addBatch();
                    }
                }
            }
            prs.executeBatch();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error in Update Data Mappings => {}", e);
        } finally {
            closeDBComp(prs, null, con);
        }
        LOGGER.debug("CampaignSetupDao - Update Data Mappings - Exit");
    }

    public List<MMktgCampaignFilter> getMktgCampaignFilter(Long mcCampId) {
        LOGGER.debug("campaignSetUp DAO - Stored Campaign Filters List - Entry CampaignId: {}", new Object[]{mcCampId});
        List<MMktgCampaignFilter> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{mcCampId};
            query = new StringBuilder("SELECT MCF_FILTER_ID mcfFilterId, MCF_FILTER_NAME mcfFilterName FROM M_MKTG_CAMPAIGN_FILTER WHERE MCF_CAMP_ID = ? ");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignFilter.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving campaign list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public int saveCampaignFilter(MMktgCampaignFilter campaignFilter, String userId) {
        Connection con = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String query = "INSERT INTO M_MKTG_CAMPAIGN_FILTER(MCF_FILTER_ID, MCF_CAMP_ID, MCF_FILTER_NAME, MCF_CR_UID, MCF_CR_DT) "
                    + "VALUES (S_MCF_FILTER_ID.NEXTVAL, ?, ?, ?, SYSDATE)";
            ps = con.prepareStatement(query);
            int i = 0;
            ps.setLong(++i, campaignFilter.getMcfCampId());
            ps.setString(++i, campaignFilter.getMcfFilterName());
            ps.setString(++i, userId);
            cnt = ps.executeUpdate();
        } catch (Exception ex) {
            LOGGER.error("Error while inserting Campaign Filter", ex);
        } finally {
            closeDBComp(ps, null, con);
        }
        return cnt;
    }

    public void updateCampaignFilter(MMktgCampaignFilter campaignFilter, String userId) throws Exception {
        int st = 0;
        Connection con = null;
        try {
            con = getDBConnection(dataSource);
            String sql = "UPDATE M_MKTG_CAMPAIGN_FILTER SET MCF_FILTER_NAME = ? WHERE MCF_CAMP_ID = ? AND MCF_FILTER_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setString(1, campaignFilter.getMcfFilterName());
            prs.setLong(2, campaignFilter.getMcfCampId());
            prs.setLong(3, campaignFilter.getMcfFilterId());
            st = prs.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error while Updating Campaign Filters", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
    }

    public MMktgCampaignFilter getCampaignFilter(MMktgCampaignFilter campaignFilter) {
        MMktgCampaignFilter campFilter = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT MCF_FILTER_NAME mcfFilterName, MCF_FILTER_ID mcfFilterId, MCF_CAMP_ID mcfCampId FROM M_MKTG_CAMPAIGN_FILTER where MCF_FILTER_ID = ? ");
            LOGGER.info("Query :: {} [{}]", query, campaignFilter.getMcfFilterId());
            campFilter = (MMktgCampaignFilter) executeQuery(con, query.toString(), new Object[]{campaignFilter.getMcfFilterId()}, MMktgCampaignFilter.class);
        } catch (Exception e) {
            LOGGER.error("Error while getting Filter {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return campFilter;
    }

    public void saveMktgFilterParams(CampaignVO bean, String userId) {
        try {
            con = getDBConnection(getDataSource());
            String query = "DELETE FROM M_MKTG_CAMPAIGN_FILTER_PARAM WHERE MCFP_FILTER_ID = ?";
            prs = con.prepareStatement(query);
            prs.setLong(1, bean.getCampaignFilterParam().getMcfpFilterId());
            prs.executeUpdate();
            prs.close();
            query = "INSERT INTO M_MKTG_CAMPAIGN_FILTER_PARAM(MCFP_FILTER_ID, MCFP_SNO, MCFP_DATA_COL, MCFP_DATA_TYPE, MCFP_OPERATOR, MCFP_VALUE_FM, MCFP_VALUE_TO,"
                    + " MCFP_CONDITION, MCFP_CR_UID, MCFP_CR_DT) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
            prs = con.prepareStatement(query);
            int j = 0;
            if (bean.getCampaignFilterParm() != null) {
                for (MMktgCampaignFilterParm filterParm : bean.getCampaignFilterParm()) {
                    if (filterParm != null) {
                        prs.setLong(1, bean.getCampaignFilterParam().getMcfpFilterId());
                        prs.setInt(2, ++j);
                        prs.setString(3, filterParm.getMcfpDataCol());
                        prs.setString(4, filterParm.getMcfpDataType());
                        prs.setString(5, filterParm.getMcfpOperator());
                        prs.setString(6, filterParm.getMcfpValueFm());
                        prs.setString(7, filterParm.getMcfpValueTo());
                        prs.setString(8, filterParm.getMcfpCondition());
                        prs.setString(9, userId);
                        prs.addBatch();
                    }
                }
            }
            prs.executeBatch();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error in Insert Data Param => {}", e);
        } finally {
            closeDBComp(prs, null, con);
        }
        LOGGER.debug("DcampaignSetupDAO - Insert Data Params Exit");
    }

    public List<MMktgCampaignFilterParm> getmktgCampFilterParams(Long mcfpFilterId) {
        LOGGER.debug("campaignSetUp DAO - Stored Campaign Data Param Filters List - Entry");
        List<MMktgCampaignFilterParm> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT MCFP_SNO, MCFP_DATA_COL, MCFP_DATA_TYPE, MCFP_OPERATOR, MCFP_VALUE_FM, MCFP_VALUE_TO, MCFP_CONDITION "
                    + " FROM M_MKTG_CAMPAIGN_FILTER_PARAM WHERE MCFP_FILTER_ID = ? ORDER BY MCFP_SNO";
            prs = con.prepareStatement(query);
            prs.setLong(1, mcfpFilterId);
            rs = prs.executeQuery();
            while (rs.next()) {
                MMktgCampaignFilterParm obj = new MMktgCampaignFilterParm();
                obj.setMcfpSno(rs.getByte("MCFP_SNO"));
                obj.setMcfpDataCol(rs.getString("MCFP_DATA_COL"));
                obj.setMcfpDataType(rs.getString("MCFP_DATA_TYPE"));
                obj.setMcfpOperator(rs.getString("MCFP_OPERATOR"));
                obj.setMcfpValueFm(rs.getString("MCFP_VALUE_FM"));
                obj.setMcfpValueTo(rs.getString("MCFP_VALUE_TO"));
                obj.setMcfpCondition(rs.getString("MCFP_CONDITION"));
                result.add(obj);
            }
        } catch (Exception e) {
            LOGGER.error("Error in Fetching Stored Data Param Filters List => {}", e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        LOGGER.debug("CampaignSetupDao - Stored Campaign Data Param Filters List - Exit");
        return result;

    }

    public List<KeyValue> loadDataMapColumns(final Long mcCampId) {
        LOGGER.info("Load data map columns => CampaignId: {}", new Object[]{mcCampId});
        List<KeyValue> list = null;
        Connection con = null;
        Object[] param = new Object[]{mcCampId};
        try {
            con = getDBConnection(getDataSource());
            StringBuilder query = new StringBuilder("SELECT MCDM_DATA_COL key, MCDM_DATA_NAME value, MCDM_DATA_TYPE info, MCDM_SAMPLE_DATA info1  FROM M_MKTG_CAMPAIGN_DATA_MAP WHERE MCDM_CAMP_ID = ?  AND MCDM_DATA_NAME is NOT NULL");
            LOGGER.info("Query :: {} [{}]", query, mcCampId);
            list = executeList(con, query.toString(), param, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Data Map Columns => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public void deleteCampaignFilter(Long mcfFilterId) throws Exception {
        LOGGER.info("Delete campaign filter FilterId: {}", new Object[]{mcfFilterId});
        int st = 0;
        Connection con = null;
        try {
            con = getDBConnection(dataSource);
            String sql = "DELETE FROM M_MKTG_CAMPAIGN_FILTER_PARAM WHERE MCFP_FILTER_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setLong(1, mcfFilterId);
            st = prs.executeUpdate();
            sql = "DELETE FROM  M_MKTG_CAMPAIGN_FILTER WHERE MCF_FILTER_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setLong(1, mcfFilterId);
            st = prs.executeUpdate();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error Delete Campaign Filter", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
    }

    public void deleteCampaignFilterParam(Long mcfFilterId) throws Exception {
        LOGGER.info("Delete campaign filter param => FilterId: {}", new Object[]{mcfFilterId});
        int st = 0;
        Connection con = null;
        try {
            con = getDBConnection(dataSource);
            String sql = "DELETE FROM M_MKTG_CAMPAIGN_FILTER_PARAM WHERE MCFP_FILTER_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setLong(1, mcfFilterId);
            st = prs.executeUpdate();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while Delete CampaignFilter Param", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
    }

    //TEMPLATE
    public List<MMktgCampaignTemplate> loadTemplateList(Long mcCampId) {
        LOGGER.info("Load template list CampaignId: {}", new Object[]{mcCampId});
        List<MMktgCampaignTemplate> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{mcCampId};
            query = new StringBuilder("SELECT MCT_TEMPLATE_ID mctTemplateId,MCT_CAMP_ID mctCampId ,MCT_NAME mctName, MCT_TYPE mctType, "
                    + "MCT_SUBJECT mctSubject,MCT_CR_DT mctCrDt FROM M_MKTG_CAMPAIGN_TEMPLATE WHERE MCT_CAMP_ID = ?");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignTemplate.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving  template list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public int saveTemplateData(MMktgCampaignTemplate template, String companyCode) {
        Connection con = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            con = getDBConnection(getDataSource());
            StringBuilder query = new StringBuilder("INSERT INTO M_MKTG_CAMPAIGN_TEMPLATE (MCT_CAMP_ID, MCT_TEMPLATE_ID, MCT_CR_UID, MCT_CR_DT, MCT_NAME, MCT_TYPE,");
            query.append("MCT_SUBJECT, MCT_BODY, MCT_CAMP_BODY) VALUES(?, S_MCT_TEMPLATE_ID.NEXTVAL, ?, SYSDATE,?,?,?,?,?)");
            ps = con.prepareStatement(query.toString(), new String[]{"MCT_TEMPLATE_ID"});
            int i = 0;
            List<MailBodyUrl> urls = null;
            ps.setLong(1, template.getMctCampId());
            ps.setString(2, template.getMctCrUid());
            ps.setString(3, template.getMctName());
            ps.setString(4, template.getMctType());
            if (null == template.getMctSubject()) {
                prs.setNull(5, Types.VARCHAR);
            } else {
                ps.setString(5, template.getMctSubject());
            }
            ps.setCharacterStream(6, new StringReader(template.getMctBody().toString()), template.getMctBody().length());
            MailBodyBuilder mbb = new MailBodyBuilder();
            mbb.setBody(template.getMctBody());
            mbb.setPropertyDelimit(ApplicationConstants.CAMPAIGN_PROPERTY_DELIMIT);
            mbb.setBaseURL(ApplicationConstants.getCampaignBaseDomain(companyCode));
            if("S".equals(template.getMctType())) {
                urls = mbb.parseSmsBody();
            } else {
                urls = mbb.parseBody();
            }
            ps.setCharacterStream(7, new StringReader(mbb.getParsedBody().toString()), mbb.getParsedBody().length());
            cnt = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            Long tempid = 0L;
            if (rs.next()) {
                tempid = rs.getLong(1);
            }
            ps.close();
            if (urls != null && !urls.isEmpty()) {
                i = 0;
                ps = con.prepareStatement("INSERT INTO M_MKTG_CAMPAIGN_TEMP_URL (MTU_TEMPLATE_ID,MTU_URL,MTU_URL_KEY,MTU_CR_UID,MTU_CR_DT) VALUES(?,?,?,?,SYSDATE)");
                for (MailBodyUrl url : urls) {
                    int j = 0;
                    ps.setLong(++j, tempid);
                    ps.setString(++j, url.getUrl());
                    ps.setString(++j, url.getToken());
                    ps.setString(++j, template.getMctCrUid());
                    ps.addBatch();
                    ++i;
                }
                if (i > 0) {
                    ps.executeBatch();
                }
                DbUtils.closeQuietly(ps);
            }

        } catch (Exception ex) {
            LOGGER.error("Error while inserting template", ex);
        } finally {
            closeDBComp(ps, null, con);
        }
        return cnt;
    }

    public void updateTemplateData(MMktgCampaignTemplate template, String companyCode) throws Exception {
        int i = 0;
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;
        List<MailBodyUrl> urls = null;
        StringBuilder query = new StringBuilder("UPDATE M_MKTG_CAMPAIGN_TEMPLATE SET MCT_NAME = ?, MCT_TYPE = ?, MCT_UPD_UID = ?, MCT_UPD_DT = SYSDATE, ");
        query.append("MCT_SUBJECT = ?, MCT_BODY = ?, MCT_CAMP_BODY = ? WHERE MCT_TEMPLATE_ID = ? AND MCT_CAMP_ID = ?");
        try {
            con = getDBConnection(getDataSource());
            String query_url = "SELECT MTU_URL_KEY token, MTU_URL url FROM M_MKTG_CAMPAIGN_TEMP_URL WHERE MTU_TEMPLATE_ID = ?";
            List<MailBodyUrl> tempUrlList = executeList(con, query_url, new Object[]{template.getMctTemplateId()}, MailBodyUrl.class);
            ps = con.prepareStatement(query.toString());
            ps.setString(++i, template.getMctName());
            ps.setString(++i, template.getMctType());
            ps.setString(++i, template.getMctUpdUid());
            if (null == template.getMctSubject()) {
                ps.setNull(++i, Types.VARCHAR);
            } else {
                ps.setString(++i, template.getMctSubject());
            }
            ps.setCharacterStream(++i, new StringReader(template.getMctBody().toString()), template.getMctBody().length());
            MailBodyBuilder mbb = new MailBodyBuilder();
            mbb.setBody(template.getMctBody());
            mbb.setPropertyDelimit(ApplicationConstants.CAMPAIGN_PROPERTY_DELIMIT);
            mbb.setBaseURL(ApplicationConstants.getCampaignBaseDomain(companyCode));
            mbb.setMailBodyUrlList(tempUrlList);
            if("S".equals(template.getMctType())) {
                urls = mbb.parseSmsBody();
            } else {
                urls = mbb.parseBody();
            }
            ps.setCharacterStream(++i, new StringReader(mbb.getParsedBody().toString()), mbb.getParsedBody().length());
            ps.setLong(++i, template.getMctTemplateId());
            ps.setLong(++i, template.getMctCampId());
            i = ps.executeUpdate();
            LOGGER.debug("Template updated. Status: {}", new Object[]{i});
            DbUtils.closeQuietly(ps);
            if (urls != null && !urls.isEmpty()) {
                i = 0;
                ps = con.prepareStatement("INSERT INTO M_MKTG_CAMPAIGN_TEMP_URL (MTU_TEMPLATE_ID, MTU_URL, MTU_URL_KEY) VALUES (?, ?, ?)");
                for (MailBodyUrl url : urls) {
                    int j = 0;
                    if (url.isNewUrl()) {
                        ps.setLong(++j, template.getMctTemplateId());
                        ps.setString(++j, url.getUrl());
                        ps.setString(++j, url.getToken());
                        ps.addBatch();
                        ++i;
                    }
                }
                if (i > 0) {
                    ps.executeBatch();
                }
                DbUtils.closeQuietly(ps);
            }
        } catch (SQLException ex) {
            LOGGER.error("Error while updating  template details => {}", ex);
        } finally {
            closeDBComp(ps, rs, con);
        }
    }

    public MMktgCampaignTemplate getCampaignTemplate(Long templateId, Long mctCampId) {
        LOGGER.info("Get Campaign template TempId: {}, CampId: {}", new Object[]{templateId, mctCampId});
        MMktgCampaignTemplate bean = new MMktgCampaignTemplate();
        try {
            con = getDBConnection(getDataSource());
            StringBuilder query = new StringBuilder("SELECT MCT_TEMPLATE_ID, MCT_NAME, MCT_TYPE, MCT_BODY , MCT_SUBJECT ");
            query.append("FROM M_MKTG_CAMPAIGN_TEMPLATE WHERE MCT_TEMPLATE_ID = ? AND MCT_CAMP_ID = ?");
            prs = con.prepareStatement(query.toString());
            prs.setLong(1, templateId);
            prs.setLong(2, mctCampId);
            rs = prs.executeQuery();
            if (rs.next()) {
                bean.setMctTemplateId(rs.getLong("MCT_TEMPLATE_ID"));
                bean.setMctName(rs.getString("MCT_NAME"));
                bean.setMctType(rs.getString("MCT_TYPE"));
                java.sql.Clob clob = rs.getClob("MCT_BODY");
                bean.setMctBody(new StringBuilder(IOUtils.toString(clob.getCharacterStream())));
                bean.setMctSubject(rs.getString("MCT_SUBJECT"));
            }
        } catch (Exception e) {
            LOGGER.error("Error while retreiving campaign  template details => {}", e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        return bean;
    }

    public MMktgCampaignTemplate getCampaignTemplateForAB(Long templateId, Long mcCampId) {
        LOGGER.info("Get campaign template for AB TemplateId: {}, campId: {}", new Object[]{templateId, mcCampId});
        List<MMktgCampaignTemplate> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{templateId, mcCampId};
            query = new StringBuilder("select mct_template_id mctTemplateId, mct_camp_id mctCampId, mct_name mctName, mct_type mctType, mct_subject mctSubject, mct_camp_body mctCampBody, mct_unicode mctUnicode, mct_text mctText, mct_cr_uid mctCrUid, mct_cr_dt mctCrDt, mct_upd_uid mctUpdUid, mct_upd_dt mctUpdDt FROM M_MKTG_CAMPAIGN_TEMPLATE where mct_template_id = ? AND mct_camp_id = ?");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignTemplate.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving  template list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list.get(0);
    }

    public KeyValue previewTemplate(Long tempId) {
        LOGGER.info("preview template : Templ;ateId: {}", new Object[]{tempId});
        //String msg = "";
        KeyValue keyVal = new KeyValue();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            MMktgCampaignTemplate bean = new MMktgCampaignTemplate();
            con = getDBConnection(getDataSource());
            String query = "SELECT MCT_TYPE,MCT_SUBJECT,MCT_BODY FROM M_MKTG_CAMPAIGN_TEMPLATE WHERE MCT_TEMPLATE_ID= ?";
            LOGGER.debug(" Query :: {}", query + "tempCode:" + tempId);
            st = con.prepareStatement(query);
            st.setLong(1, tempId);
            rs = st.executeQuery();
            while (rs.next()) {
                bean.setMctType(rs.getString("MCT_TYPE"));
                keyVal.setInfo1(rs.getString("MCT_SUBJECT"));
                keyVal.setInfo2(rs.getString("MCT_BODY"));
            }
        } catch (Exception e) {
            LOGGER.error(" Error while previewing template body information", e);
        } finally {
            closeDBComp(st, rs, con);
        }
        return keyVal;
    }

    public List<MMktgCampaignTempUrl> loadUrlList(Long tempId) {
        LOGGER.info("Load url list TemplateId: {}", new Object[]{tempId});
        List<MMktgCampaignTempUrl> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{tempId};
            query = new StringBuilder("SELECT MTU_URL_KEY mtuUrlKey, MTU_TEMPLATE_ID mtuTemplateId, SUBSTR(MTU_URL, 1, INSTR(MTU_URL, 'qtm_cid=@TOKEN@') - 2) mtuUrl, MTU_CLICKED_COUNT mtuClickedCount "
                    + "FROM M_MKTG_CAMPAIGN_TEMP_URL WHERE MTU_TEMPLATE_ID= ?");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignTempUrl.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving url list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public MMktgCampaign getCampaignDetails(Long mcCampId) {
        LOGGER.info("Get Campaign details campId: {}", new Object[]{mcCampId});
        Connection con = null;
        StringBuilder query = null;
        MMktgCampaign bean = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("select MC_CAMP_NAME mcCampName, MC_CAMP_CODE mcCampCode, MC_DATA_SOURCE_TYPE mcDataSourceType, (select para_name from m_app_parameter where para_sub_code = MC_CAMP_TYPE AND PARA_CODE = 'MK_CAMP_TYPE') mcCampType, MC_SCHEDULE_MODE mcScheduleMode, MC_SCHEDULE_VALUE mcScheduleValue, MC_RECURRING_YN mcRecurringYn, MC_SCHEDULE_VALUE mcScheduleValue, MC_STATUS mcStatus, MC_AB_YN mcAbYn  FROM M_MKTG_CAMPAIGN where MC_CAMP_ID = ?");
            LOGGER.info("Query :: {} [{}]", query, mcCampId);
            bean = (MMktgCampaign) executeQuery(con, query.toString(), new Object[]{mcCampId}, MMktgCampaign.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving  template details => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return bean;
    }

    public List<MMktgCampaign> loadCampaignList(final MMktgCampaign campaign) {
        List<MMktgCampaign> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            List param = new LinkedList();
            //commented for corporate
            //query = new StringBuilder("SELECT MC_CAMP_ID mcCampId, MC_CAMP_NAME mcCampName, MC_CRM_ID mcCrmId, PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', MC_CRM_ID) as mcCrmDesc, ");
            query = new StringBuilder("SELECT MC_CAMP_ID mcCampId, MC_CAMP_NAME mcCampName, ");
            query.append("PKG_REP_UTILITY.FN_GET_PARA_NAME('MK_CAMP_TYPE', MC_CAMP_TYPE) mcCampType, PKG_REP_UTILITY.FN_GET_PARA_NAME('MK_DATA_SRC', MC_DATA_SOURCE_TYPE) mcDataSourceType, ");
            query.append("PKG_REP_UTILITY.FN_GET_PARA_NAME('MK_STATUS', MC_STATUS) mcStatusDesc, MC_STATUS mcStatus, MC_NEXT_RUN_DATE mcNextRunDate, ");
            query.append("MC_RECURRING_YN mcRecurringYn, MC_AB_YN mcAbYn, MC_AB_DATA_COUNT mcAbDataCount, MC_AB_END_DATE mcAbEndDate FROM M_MKTG_CAMPAIGN ");
            if (campaign != null) {
                query.append("WHERE ");
                if (StringUtils.isNoneBlank(campaign.getMcCampName())) {
                    query.append("MC_CAMP_NAME = ? ");
                    param.add(campaign.getMcCampName());
                }
                if (StringUtils.isNoneBlank(campaign.getMcStatus())) {
                    if (!param.isEmpty()) {
                        query.append("AND ");
                    }
                    query.append("MC_STATUS  = ?  ");
                    param.add(campaign.getMcStatus());
                }
                query.append("ORDER BY MC_CR_DT DESC");

            }

            LOGGER.info("Query :: {}", new Object[]{query});
            Object[] data = param.toArray();
            list = executeList(con, query.toString(), data, MMktgCampaign.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving campaign list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public void saveCampaignData(final MMktgCampaign campaign, final String userId, final String operation) {
        String query = null;
        Connection con = null;
        PreparedStatement prs = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equalsIgnoreCase(operation)) {
                query = " INSERT INTO M_MKTG_CAMPAIGN(MC_CAMP_ID, MC_CAMP_NAME, MC_CAMP_CODE, MC_DATA_SOURCE_TYPE, MC_DATA_SOURCE_VIEW, MC_CAMP_TYPE,"
                		//commented for corporate
                       // + "MC_SENDER_ID, MC_REPLY_TO, MC_UNSUBSCRIBE_INCL_YN, MC_RECURRING_YN, MC_SCHEDULE_MODE, MC_SCHEDULE_VALUE, MC_SCHEDULE_TIME, "
                       // + "MC_STATUS, MC_PREVIEW_YN, MC_ALLOW_UNSUBSCRIBE_YN, MC_VERIFY_CONVERSION_YN, MC_TARGET_AMT, MC_CR_UID, MC_CR_DT, MC_SELECTED_COLS, MC_CRM_ID) VALUES (S_MC_CAMP_ID.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?)";
                		+ "MC_SENDER_ID, MC_REPLY_TO, MC_UNSUBSCRIBE_INCL_YN, MC_RECURRING_YN, MC_SCHEDULE_MODE, MC_SCHEDULE_VALUE, MC_SCHEDULE_TIME, MC_STATUS, MC_PREVIEW_YN, MC_ALLOW_UNSUBSCRIBE_YN, MC_VERIFY_CONVERSION_YN, MC_TARGET_AMT, MC_CR_UID, MC_CR_DT, MC_SELECTED_COLS) VALUES (S_MC_CAMP_ID.nextval, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,SYSDATE, ?)";
                prs = con.prepareStatement(query, new String[]{"MC_CAMP_ID"});
                int j = 0;
                prs.setString(++j, campaign.getMcCampName());
                prs.setString(++j, campaign.getMcCampCode());
                prs.setString(++j, campaign.getMcDataSourceType());
                if ("A".equals(campaign.getMcDataSourceType())) {
                    prs.setString(++j, campaign.getMcDataSourceView());
                } else {
                    prs.setNull(++j, Types.VARCHAR);
                }
                prs.setString(++j, campaign.getMcCampType());
                if ("E".equals(campaign.getMcCampType())) {
                    prs.setString(++j, campaign.getMcSenderId());
                    prs.setString(++j, campaign.getMcReplyTo());
                } else {
                    prs.setString(++j, campaign.getMcSenderId());
                    prs.setNull(++j, Types.VARCHAR);
                }

                prs.setString(++j, campaign.getMcUnsubscribeInclYn());
                prs.setString(++j, campaign.getMcRecurringYn());
                if ("1".equals(campaign.getMcRecurringYn())) {
                    prs.setString(++j, campaign.getMcScheduleMode());
                    prs.setInt(++j, campaign.getMcScheduleValue());
                    prs.setBigDecimal(++j, campaign.getMcScheduleTime());
                } else {
                    prs.setString(++j, "");
                    prs.setInt(++j, 0);
                    prs.setBigDecimal(++j, new BigDecimal(0));
                }
                prs.setString(++j, "P");
                prs.setString(++j, campaign.getMcPreviewYn());
                prs.setString(++j, campaign.getMcAllowUnsubscribeYn());
                prs.setString(++j, campaign.getMcVerifyConversionYn());
                if ("1".equals(campaign.getMcVerifyConversionYn())) {
                    prs.setBigDecimal(++j, campaign.getMcTargetAmt());
                } else {
                    prs.setBigDecimal(++j, new BigDecimal(0));
                }
                prs.setString(++j, userId);
                prs.setString(++j, campaign.getMcSelectedCols());
                //commented for corporate
                //prs.setString(++j, campaign.getMcCrmId());
                prs.executeUpdate();
                rs = prs.getGeneratedKeys();
                if (rs.next()) {
                    campaign.setMcCampId(rs.getLong(1));
                }

                DbUtils.closeQuietly(rs);
                DbUtils.closeQuietly(prs);
            } else if ("edit".equalsIgnoreCase(operation)) {
                query = "UPDATE M_MKTG_CAMPAIGN SET MC_CAMP_NAME = ?, MC_CAMP_CODE = ?, MC_DATA_SOURCE_TYPE = ?, MC_DATA_SOURCE_VIEW = ?, MC_CAMP_TYPE = ?, "
                        + "MC_SENDER_ID = ?, MC_REPLY_TO = ?, MC_UNSUBSCRIBE_INCL_YN = ?, MC_RECURRING_YN = ?, MC_SCHEDULE_MODE = ?, MC_SCHEDULE_VALUE = ?, "
                      //commented for corporate
                    //    + "MC_SCHEDULE_TIME = ?, MC_PREVIEW_YN = ?, MC_ALLOW_UNSUBSCRIBE_YN = ?, MC_VERIFY_CONVERSION_YN = ?, MC_TARGET_AMT = ?, MC_CRM_ID = ?, MC_CR_UID = ?, MC_CR_DT = SYSDATE  WHERE MC_CAMP_ID = ? ";
                        + "MC_SCHEDULE_TIME = ?, MC_PREVIEW_YN = ?, MC_ALLOW_UNSUBSCRIBE_YN = ?, MC_VERIFY_CONVERSION_YN = ?, MC_TARGET_AMT = ?, MC_CR_UID = ? , MC_CR_DT = SYSDATE  WHERE MC_CAMP_ID = ? ";
                Object[] params = new Object[]{campaign.getMcCampName(), campaign.getMcCampCode(), campaign.getMcDataSourceType(), "A".equals(campaign.getMcDataSourceType()) ? campaign.getMcDataSourceView() : "", campaign.getMcCampType(), campaign.getMcSenderId(),
                    "E".equals(campaign.getMcCampType()) ? campaign.getMcReplyTo() : "", campaign.getMcUnsubscribeInclYn(), campaign.getMcRecurringYn(), "1".equals(campaign.getMcRecurringYn()) ? campaign.getMcScheduleMode() : "", "1".equals(campaign.getMcRecurringYn()) ? campaign.getMcScheduleValue() : "", "1".equals(campaign.getMcRecurringYn()) ? campaign.getMcScheduleTime() : "",
                    		//commented for corporate
                //    campaign.getMcPreviewYn(), campaign.getMcAllowUnsubscribeYn(), campaign.getMcVerifyConversionYn(), "1".equals(campaign.getMcVerifyConversionYn()) ? campaign.getMcTargetAmt() : "", campaign.getMcCrmId(), userId, campaign.getMcCampId()};
                    		 campaign.getMcPreviewYn(), campaign.getMcAllowUnsubscribeYn(), campaign.getMcVerifyConversionYn(), "1".equals(campaign.getMcVerifyConversionYn()) ? campaign.getMcTargetAmt() : "", userId, campaign.getMcCampId()};

                executeUpdate(con, query, params);

            }

        } catch (Exception e) {
            LOGGER.error("Save Campaign data", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
    }

    public MMktgCampaign loadCampaign(final Long mcCampId) {
        MMktgCampaign bean = null;
        Connection con = null;
        String sql = "SELECT MC_CAMP_ID mcCampId, MC_CAMP_NAME mcCampName, MC_CAMP_CODE mcCampCode, PKG_REP_UTILITY.FN_GET_PARA_NAME('MK_DATA_SRC', MC_DATA_SOURCE_TYPE) mcDataSourceType, MC_DATA_SOURCE_VIEW mcDataSourceView, "
                + "MC_CAMP_TYPE mcCampType, PKG_REP_UTILITY.FN_GET_PARA_NAME('MK_CAMP_TYPE', MC_CAMP_TYPE) mcFlex05, MC_UNSUBSCRIBE_INCL_YN mcUnsubscribeInclYn, MC_SENDER_ID mcSenderId, MC_REPLY_TO mcReplyTo, "
                + "MC_RECURRING_YN mcRecurringYn, MC_SCHEDULE_MODE mcScheduleMode, MC_SCHEDULE_VALUE mcScheduleValue, MC_SCHEDULE_TIME mcScheduleTime, MC_START_TIME mcStartTime, MC_PREVIEW_YN mcPreviewYn, "
                + "MC_ALLOW_UNSUBSCRIBE_YN mcAllowUnsubscribeYn, MC_VERIFY_CONVERSION_YN mcVerifyConversionYn, MC_TARGET_AMT mcTargetAmt, MC_STATUS mcStatus "
                + "FROM M_MKTG_CAMPAIGN WHERE MC_CAMP_ID = ?";
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{sql, mcCampId});
            Object[] params = new Object[]{mcCampId};
            bean = (MMktgCampaign) executeQuery(con, sql, params, MMktgCampaign.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving customer details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return bean;
    }

    public void updateCampaignStatus(final MMktgCampaign campaign) {
        LOGGER.info("in update**");
        Connection con = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String sql = "UPDATE M_MKTG_CAMPAIGN SET MC_STATUS = ?, MC_UPD_UID = ?, MC_UPD_DT = SYSDATE WHERE MC_CAMP_ID = ?";
            Object[] params = new Object[]{campaign.getMcStatus(), campaign.getMcUpdUid(), campaign.getMcCampId()};
            recCnt = executeUpdate(con, sql, params);
        } catch (Exception e) {
            LOGGER.error("Error while updating campaign", e);
        } finally {
            closeDbConn(con);
        }
    }

    public void updateCampaign(final MMktgCampaign campaign) {
        LOGGER.info("in update**");
        Connection con = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String sql = "UPDATE M_MKTG_CAMPAIGN SET MC_AB_YN = '1', MC_AB_DATA_COUNT = ?, MC_AB_START_DATE = SYSDATE, MC_AB_END_DATE = ?, MC_AB_ACTION_TYPE = ? WHERE MC_CAMP_ID = ?";
            Object[] params = new Object[]{campaign.getMcAbDataCount(), new java.sql.Timestamp(campaign.getMcAbEndDate().getTime()), campaign.getMcAbActionType(), campaign.getMcCampId()};
            recCnt = executeUpdate(con, sql, params);
        } catch (Exception e) {
            LOGGER.error("Error while updating campaign", e);
        } finally {
            closeDbConn(con);
        }
    }

    public List<MMktgCampaignForms> loadCampaignFormList(final Long mcCampId) {
        List<MMktgCampaignForms> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());

            //  List param = new LinkedList();
            query = new StringBuilder("SELECT MCF_FORM_ID mcfFormId, MCF_CAMP_ID mcfCampId, MCF_NAME  mcfName, MCF_FORM_TITLE  mcfFormTitle, MCF_VALIDITY  mcfValidity  "
                    + "FROM M_MKTG_CAMPAIGN_FORMS WHERE MCF_CAMP_ID = ? ");
            LOGGER.info("Query :: {}", new Object[]{query, mcCampId});
            Object[] param = new Object[]{mcCampId};
            //   Object[] data = param.toArray();
            list = executeList(con, query.toString(), param, MMktgCampaignForms.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving campaign form list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public MMktgCampaignForms loadCampaignForm(final MMktgCampaignForms campForm) {
        MMktgCampaignForms bean = null;
        Connection con = null;
        String sql = "SELECT MCF_FORM_ID mcfFormId, MCF_CAMP_ID mcfCampId, MCF_NAME  mcfName, MCF_BANNER_URL  mcfBannerUrl, "
                + "MCF_FORM_TITLE  mcfFormTitle,  MCF_FORM_HEADER mcfFormHeader , MCF_FORM_FOOTER  mcfFormFooter , MCF_FORM_COLOR mcfFormColor ,  MCF_VALIDITY  mcfValidity "
                + "FROM M_MKTG_CAMPAIGN_FORMS WHERE MCF_FORM_ID = ?";
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{sql, campForm.getMcfFormId()});
            Object[] params = new Object[]{campForm.getMcfFormId()};
            bean = (MMktgCampaignForms) executeQuery(con, sql, params, MMktgCampaignForms.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving customer details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return bean;
    }

    public void saveCampaignFormData(final MMktgCampaignForms campForm, final String userId, final String operation) {
        String query = null;
        Connection con = null;
        PreparedStatement prs = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equalsIgnoreCase(operation)) {
                query = " INSERT INTO M_MKTG_CAMPAIGN_FORMS(MCF_FORM_ID, MCF_CAMP_ID, MCF_NAME, MCF_FORM_TITLE, MCF_VALIDITY, MCF_BANNER_URL,"
                        + "MCF_FORM_HEADER, MCF_FORM_FOOTER, MCF_FORM_COLOR, MCF_CR_UID, MCF_CR_DT) VALUES(S_MCF_FORM_ID.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
                prs = con.prepareStatement(query);
                int j = 0;
                prs.setLong(++j, campForm.getMcfCampId());
                prs.setString(++j, campForm.getMcfName());
                prs.setString(++j, campForm.getMcfFormTitle());
                prs.setTimestamp(++j, new java.sql.Timestamp(campForm.getMcfValidity().getTime()));
                prs.setString(++j, campForm.getMcfBannerUrl());
                prs.setString(++j, campForm.getMcfFormHeader());
                prs.setString(++j, campForm.getMcfFormFooter());
                prs.setString(++j, campForm.getMcfFormColor());
                prs.setString(++j, userId);
                prs.executeUpdate();
            } else if ("edit".equalsIgnoreCase(operation)) {
                query = "UPDATE M_MKTG_CAMPAIGN_FORMS SET  MCF_NAME = ?, MCF_FORM_TITLE = ?, MCF_VALIDITY = ?, MCF_BANNER_URL = ?, "
                        + "MCF_FORM_HEADER = ?, MCF_FORM_FOOTER = ?, MCF_FORM_COLOR = ? , MCF_UPD_UID = ?, MCF_UPD_DT = SYSDATE "
                        + "WHERE MCF_FORM_ID = ? ";
                Object[] params = new Object[]{campForm.getMcfName(), campForm.getMcfFormTitle(), new java.sql.Timestamp(campForm.getMcfValidity().getTime()),
                    campForm.getMcfBannerUrl(), campForm.getMcfFormHeader(), campForm.getMcfFormFooter(), campForm.getMcfFormColor(), userId, campForm.getMcfFormId()};
                executeUpdate(con, query, params);
            }

        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
    }

    public List<MMktgCampaignFormField> loadCampaignFormFieldList(final Long formId) {
        List<MMktgCampaignFormField> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT MCFF_FORM_ID mcffFormId, MCFF_COL_NO mcffColNo, MCFF_FIELD_NAME  mcffFieldName, (SELECT PARA_NAME FROM M_APP_PARAMETER WHERE PARA_CODE = 'MK_DATA_TYPE' AND PARA_SUB_CODE = MCFF_DATA_TYPE)   mcffDataType, (SELECT PARA_NAME FROM M_APP_PARAMETER WHERE PARA_CODE = 'MK_FLD_TYPE' AND PARA_SUB_CODE = MCFF_FIELD_TYPE)   mcffFieldType  "
                    + "FROM M_MKTG_CAMPAIGN_FORM_FIELD WHERE MCFF_FORM_ID = ?   ");

            LOGGER.info("Query :: {}", new Object[]{query, formId});
            Object[] param = new Object[]{formId};
            //   Object[] data = param.toArray();
            list = executeList(con, query.toString(), param, MMktgCampaignFormField.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving campaign form list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public MMktgCampaignFormField loadCampaignFormField(final MMktgCampaignFormField formField) {
        MMktgCampaignFormField bean = null;
        Connection con = null;
        String sql = "SELECT MCFF_FORM_ID mcffFormId, MCFF_COL_NO mcffColNo, MCFF_FIELD_NAME  mcffFieldName, MCFF_FIELD_HINT mcffFieldHint, MCFF_DATA_TYPE  mcffDataType, MCFF_FIELD_TYPE  mcffFieldType, "
                + "MCFF_MAX_LENGTH mcffMaxLength, MCFF_MANDETORY_YN  mcffMandetoryYn, MCFF_DATA_SQL  mcffDataSql, MCFF_VALIDATION  mcffValidation, MCFF_VALIDATION_MSG mcffValidationMsg "
                + "FROM M_MKTG_CAMPAIGN_FORM_FIELD WHERE MCFF_FORM_ID = ?  AND MCFF_COL_NO = ? ";
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{sql, formField.getMcffFormId(), formField.getMcffColNo()});
            Object[] params = new Object[]{formField.getMcffFormId(), formField.getMcffColNo()};
            bean = (MMktgCampaignFormField) executeQuery(con, sql, params, MMktgCampaignFormField.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving customer details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return bean;
    }

    public void deleteCampaignFormFields(MMktgCampaignFormField formField) throws Exception {
        int st = 0;
        Connection con = null;
        try {
            con = getDBConnection(dataSource);
            String sql = "UPDATE M_MKTG_CAMPAIGN_FORM_FIELD SET MCFF_FIELD_NAME = ?, MCFF_FIELD_HINT = ?, MCFF_DATA_TYPE =?, MCFF_FIELD_TYPE = ?, MCFF_MAX_LENGTH = ?, MCFF_MANDETORY_YN = ?, MCFF_DATA_SQL = ?, MCFF_VALIDATION = ?, MCFF_VALIDATION_MSG = ? WHERE MCFF_FORM_ID = ? AND MCFF_COL_NO = ?";
            prs = con.prepareStatement(sql);
            prs.setNull(1, Types.VARCHAR);
            prs.setNull(2, Types.VARCHAR);
            prs.setNull(3, Types.VARCHAR);
            prs.setNull(4, Types.VARCHAR);
            prs.setNull(5, Types.VARCHAR);
            prs.setString(6, "0");
            prs.setNull(7, Types.VARCHAR);
            prs.setNull(8, Types.VARCHAR);
            prs.setNull(9, Types.VARCHAR);
            prs.setLong(10, formField.getMcffFormId());
            prs.setLong(11, formField.getMcffColNo());
            st = prs.executeUpdate();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while Delete Campaign form Fields Param", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
    }

    public void saveCampaignFormField(final MMktgCampaignFormField formField, final String userId) {
        String query = null;
        Connection con = null;

        try {
            con = getDBConnection(getDataSource());
            /* if ("add".equalsIgnoreCase(operation)) {
             query = " INSERT INTO M_MKTG_CAMPAIGN_FORM_FIELD(MCFF_FORM_ID, MCFF_COL_NO, MCFF_FIELD_NAME, MCFF_FIELD_HINT, MCFF_DATA_TYPE,"
             + "MCFF_FIELD_TYPE, MCFF_MAX_LENGTH, MCFF_MANDETORY_YN, MCFF_DATA_SQL, MCFF_VALIDATION, MCFF_VALIDATION_MSG, MCFF_CR_UID, MCFF_CR_DT) VALUES( ? , (SELECT MAX(MCFF_COL_NO)+1 FROM M_MKTG_CAMPAIGN_FORM_FIELD), ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, SYSDATE)";
             prs = con.prepareStatement(query);
             int j = 0;
             prs.setLong(++j, formId);
             prs.setString(++j, formField.getMcffFieldName());
             prs.setString(++j, formField.getMcffFieldHint());
             prs.setString(++j, formField.getMcffDataType());
             prs.setString(++j, formField.getMcffFieldType());
             prs.setLong(++j, formField.getMcffMaxLength());
             prs.setString(++j, formField.getMcff);
             prs.setString(++j, campForm.getMcfFormColor());
             prs.setString(++j, userId);
             prs.executeUpdate();
             } else if ("edit".equalsIgnoreCase(operation)) {*/
            query = "UPDATE M_MKTG_CAMPAIGN_FORM_FIELD SET  MCFF_FIELD_NAME = ?, MCFF_FIELD_HINT = ?, MCFF_DATA_TYPE = ?, MCFF_FIELD_TYPE = ?, "
                    + "MCFF_MAX_LENGTH = ?, MCFF_MANDETORY_YN = ?, MCFF_DATA_SQL = ? , MCFF_VALIDATION = ?, MCFF_VALIDATION_MSG = ?, MCFF_UPD_UID = ?,  MCFF_UPD_DT = SYSDATE "
                    + "WHERE  MCFF_FORM_ID = ?  AND MCFF_COL_NO = ? ";
            Object[] params = new Object[]{formField.getMcffFieldName(), formField.getMcffFieldHint(), formField.getMcffDataType(), formField.getMcffFieldType(),
                formField.getMcffMaxLength(), formField.getMcffMandetoryYn(), formField.getMcffDataSql(), formField.getMcffValidation(), formField.getMcffValidationMsg(), userId, formField.getMcffFormId(), formField.getMcffColNo()};
            executeUpdate(con, query, params);
            //  }

        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
    }

    public List<MMktgCampaignFormField> viewFormFieldList(final Long formId) {
        List<MMktgCampaignFormField> list = null;
        Connection con = null;
        StringBuilder query = null;
        PreparedStatement prs = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT MCFF_FORM_ID mcffFormId, MCFF_COL_NO mcffColNo, MCFF_FIELD_NAME  mcffFieldName,  MCFF_DATA_TYPE   mcffDataType, MCFF_FIELD_TYPE   mcffFieldType,  "
                    + " MCFF_MAX_LENGTH mcffMaxLength, MCFF_MANDETORY_YN  mcffMandetoryYn, MCFF_DATA_SQL mcffDataSql  FROM M_MKTG_CAMPAIGN_FORM_FIELD WHERE MCFF_FORM_ID = ? AND MCFF_FIELD_NAME IS NOT NULL  ");

            LOGGER.info("Query :: {}", new Object[]{query, formId});
            Object[] param = new Object[]{formId};
            //   Object[] data = param.toArray();
            list = executeList(con, query.toString(), param, MMktgCampaignFormField.class);
            for (MMktgCampaignFormField formField : list) {
                prs = con.prepareStatement(formField.getMcffDataSql());

            }

        } catch (Exception e) {
            LOGGER.error("Error while retreiving campaign form list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    //Transactions
    public List<MMktgCampaignTxn> loadCampaignTxn(CampaignVO bean) {
        LOGGER.info("CampaignSetupDao - Load Campaign Txn Enter-  CampId: {}, Year: {}, Month: {}", new Object[]{bean.getMcCampId(), bean.getYear(), bean.getMonth()});
        List<MMktgCampaignTxn> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{bean.getMcCampId(), bean.getYear()};
            query = new StringBuilder("SELECT MCT_START_DATE mctStartDate, MCT_END_DATE mctEndDate, MCT_TXN_STATUS mctTxnStatus, PKG_REP_UTILITY.FN_GET_PARA_NAME('MK_TXNSTATUS', MCT_TXN_STATUS) mctStatusDesc, MCT_DATA_COUNT mctDataCount, MCT_TXN_ID mctTxnId, (SELECT PKG_REP_UTILITY.FN_GET_PARA_NAME ('MK_ACTION', MCPF_ACTION) MCPF_ACTION FROM M_MKTG_CAMPAIGN_PATH_FLOW WHERE MCPF_PATH_ID = MCT_PATH_ID AND MCPF_FLOW_NO = MCT_PATH_FLOW_NO) mctFlowAction, MCT_AB_YN mctAbYn FROM T_MKTG_CAMPAIGN_TXN WHERE MCT_CAMP_ID = ? ");
            if(bean.getMonth() != null && bean.getMonth()!= 0){
               query.append("AND EXTRACT(MONTH FROM MCT_START_DATE) = ").append(bean.getMonth()).append(" ");
            }
            query.append("AND EXTRACT(YEAR FROM MCT_START_DATE) = ? ORDER BY MCT_START_DATE DESC");

            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignTxn.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Campaign Txn  list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<MMktgCampaignTxnData> loadCampaignTxnData(MMktgCampaignTxnData txnData) {
        LOGGER.info("CampaignSetupDao - Load Campaign Txn Data Enter-  CampId: {}, TxnId: {}", new Object[]{txnData.getMctdCampId(), txnData.getMctdTxnId()});
        List<MMktgCampaignTxnData> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{txnData.getMctdCampId(), txnData.getMctdTxnId()};
            query = new StringBuilder("SELECT MCTD_CONTACT_ID mctdContactId, MCTD_CONTACT_REF mctdContactRef, MCTD_SENT_YN mctdSentYn, MCTD_BOUNCED_YN mctdBouncedYn, MCTD_REPLIED_YN mctdRepliedYn, MCTD_CLICKED_YN mctdClickedYn, MCTD_CONVERTED_YN mctdConvertedYn FROM T_MKTG_CAMPAIGN_TXN_DATA WHERE MCTD_CAMP_ID = ? AND MCTD_TXN_ID = ?");
            if (txnData.getMctdAction() != null) {
                if (StringUtils.isNoneBlank(txnData.getMctdAction())) {
                    query.append(" AND ");
                    switch (txnData.getMctdAction()) {
                        case "MB":
                            query.append("MCTD_BOUNCED_YN = 1");
                            break;
                        case "MO":
                            query.append("MCTD_OPENED_YN = 1");
                            break;
                        case "MR":
                            query.append("MCTD_REPLIED_YN = 1");
                            break;
                        case "MC":
                            query.append("MCTD_CLICKED_YN = 1");
                            break;
                        case "MCD":
                            query.append("MCTD_CONVERTED_YN = 1");
                            break;
                        case "MS":
                            query.append("MCTD_SENT_YN = 1");
                            break;
                    }
                }

            }
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignTxnData.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Campaign Txn Data list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public void updateCampaignTxn(Long mcCampId, Long mctTxnId) throws Exception {
        LOGGER.info("CampaignSetupDao - Update Campaign Txn Enter-  CampId: {}, TxnId: {}", new Object[]{mcCampId, mctTxnId});
        int st = 0;
        Connection con = null;
        try {
            con = getDBConnection(dataSource);
            String sql = "UPDATE T_MKTG_CAMPAIGN_TXN SET MCT_TXN_STATUS = 'S', MCT_NEXT_RUN_DATE = null, MCT_END_DATE = SYSDATE WHERE MCT_CAMP_ID = ? AND MCT_TXN_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setLong(1, mcCampId);
            prs.setLong(2, mctTxnId);
            st = prs.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error while Updating Campaign Filters", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
    }

    public MMktgCampaignTxnData loadCampaignTxnCount(Long mcCampId, Long mctTxnId) {
        LOGGER.info("CampaignSetupDao -  loadCampaignTxnCount  Enter-  CampId: {}, TxnId: {}", new Object[]{mcCampId, mctTxnId});
        MMktgCampaignTxnData bean = null;
        Connection con = null;
        String sql = "SELECT COUNT(*) mctdTotalCnt, NVL(SUM(MCTD_SENT_YN), 0) mctdSentCnt, NVL(SUM(MCTD_BOUNCED_YN), 0) mctdBouncedCnt, NVL(SUM(MCTD_REPLIED_YN), 0) mctdRepliedCnt, NVL(SUM(MCTD_CLICKED_YN), 0) mctdClickedCnt, NVL(SUM(MCTD_CONVERTED_YN), 0) mctdConvertedCnt FROM T_MKTG_CAMPAIGN_TXN_DATA WHERE MCTD_CAMP_ID = ? AND MCTD_TXN_ID = ?";
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{sql, mcCampId, mctTxnId});
            Object[] params = new Object[]{mcCampId, mctTxnId};
            bean = (MMktgCampaignTxnData) executeQuery(con, sql, params, MMktgCampaignTxnData.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving customer details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return bean;
    }

    public List<MMktgCampaignTxnData> loadTxnConvertedData(Long mcCampId, Long mctTxnId) {
        LOGGER.info("CampaignSetupDao -  loadTxnConvertedData  Enter-  CampId: {}, TxnId: {}", new Object[]{mcCampId, mctTxnId});
        List<MMktgCampaignTxnData> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{mcCampId, mctTxnId};
            query = new StringBuilder("SELECT MCTD_CONTACT_ID mctdContactId, MCTD_CONTACT_REF mctdContactRef, MCTD_CONVERTED_DT mctdConvertedDt, MCTD_CONVERTED_VALUE mctdConvertedValue FROM T_MKTG_CAMPAIGN_TXN_DATA WHERE MCTD_CAMP_ID = ? AND MCTD_TXN_ID = ?");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignTxnData.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Campaign Converted  Data list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public MMktgCampaign getCampaignByCampId(Long mcCampId) {
        MMktgCampaign bean = null;
        Connection con = null;
        String sql = "SELECT MC_CAMP_ID mcCampId, MC_CAMP_NAME mcCampName, MC_CAMP_CODE mcCampCode, MC_DATA_SOURCE_TYPE mcDataSourceType, MC_STATUS mcStatus, "
        		//commented for corporate
                //+ "MC_CAMP_TYPE mcCampType, MC_UNSUBSCRIBE_INCL_YN mcUnsubscribeInclYn, MC_SENDER_ID mcSenderId, MC_REPLY_TO mcReplyTo, MC_CRM_ID mcCrmId, "
                + " MC_CAMP_TYPE mcCampType, MC_UNSUBSCRIBE_INCL_YN mcUnsubscribeInclYn, MC_SENDER_ID mcSenderId, MC_REPLY_TO mcReplyTo, "
                + "MC_RECURRING_YN mcRecurringYn, MC_SCHEDULE_MODE mcScheduleMode, MC_SCHEDULE_VALUE mcScheduleValue, MC_SCHEDULE_TIME mcScheduleTime, MC_PREVIEW_YN mcPreviewYn, MC_SELECTED_COLS mcSelectedCols, MC_DATA_SOURCE_VIEW mcDataSourceView, MC_START_TIME mcStartTime, MC_AB_DATA_COUNT mcAbDataCount  "
                + "FROM M_MKTG_CAMPAIGN WHERE MC_CAMP_ID = ?";
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{sql, mcCampId});
            Object[] params = new Object[]{mcCampId};
            bean = (MMktgCampaign) executeQuery(con, sql, params, MMktgCampaign.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving customer details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return bean;
    }

    public List<KeyValue> loadCampaignUploadColumns(Long mcCampId) {
        List<KeyValue> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT MC_SELECTED_COLS key, MC_SELECTED_COLS value FROM M_MKTG_CAMPAIGN WHERE MC_CAMP_ID = " + mcCampId);
            while (rs.next()) {
                String[] listString = rs.getString("key").split("~");
                for (String element : listString) {
                    KeyValue obj = new KeyValue();
                    obj.setKey(element);
                    obj.setValue(element);
                    obj.setInfo("C");
                    result.add(obj);
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error in Fetching Selected Columns", e);
        } finally {
            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetupDao  - Exit");
        return result;
    }

    public String insertCampaignData(final File csvFile, Long mcCampId, String mcCampType, String regex) throws SQLException {
        String result = "";
        String sql;
        Connection con = null;
        CallableStatement cs = null;
        PreparedStatement prs = null;
        FileInputStream fileInputStream = null;
         HSSFWorkbook workbook = null;
         HSSFSheet sheet = null;
        XSSFWorkbook xssWorkbook = null;
        XSSFSheet xssSheet = null;
         Iterator<Row> rowIterator = null;
        if (csvFile.exists() && csvFile.isFile()) {
            con = getDBConnection(dataSource);
            con.setAutoCommit(true);
            try {
                LOGGER.info("Uploading Started");
                prs = con.prepareStatement("DELETE FROM W_MKTG_CAMPAIGN_DATA WHERE MCD_CAMP_ID =" + mcCampId);
                prs.execute();
                int batchRowCount = 0, emptyRowCount = 0, rowCount = 0, invalidRowCount = 0;
                boolean isHeader = true, isEmptyRow = false;
                fileInputStream = new FileInputStream(csvFile);
                xssWorkbook = new XSSFWorkbook(fileInputStream);
                xssSheet = xssWorkbook.getSheetAt(0);
                rowIterator = xssSheet.rowIterator();
                sql = "INSERT INTO W_MKTG_CAMPAIGN_DATA (MCD_CONTACT_ID, MCD_CONTACT_REF, MCD_DATA_1, MCD_DATA_2, MCD_DATA_3, MCD_DATA_4, MCD_DATA_5, MCD_DATA_6, MCD_DATA_7, MCD_DATA_8, MCD_DATA_9, MCD_DATA_10, MCD_DATA_11, MCD_DATA_12, MCD_DATA_13, MCD_DATA_14, MCD_DATA_15, MCD_DATA_16, MCD_DATA_17, MCD_DATA_18, MCD_DATA_19, MCD_DATA_20, MCD_CAMP_ID) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                prs = con.prepareStatement(sql);
                while (rowIterator.hasNext()) {
                    if (emptyRowCount > 2) {
                        break;
                    }
                    isEmptyRow = false;
                    Row hssfRow = rowIterator.next();
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }
                    int j = 0;
                    ++rowCount;
                    for (int i = 0; i < 22; i++) {
                        Cell hssfCell = hssfRow.getCell(i);
                        String colValue = null;
                        if (hssfCell != null) {
                            hssfCell.setCellType(Cell.CELL_TYPE_STRING);
                            colValue = hssfCell.getStringCellValue();
                            colValue = colValue == null ? colValue : colValue.trim();
                        }
                        if (i == 0) {
                            if (StringUtils.isBlank(colValue)) {
                                LOGGER.info("Skipping empty row. Row Count: {} ", new Object[]{emptyRowCount});
                                emptyRowCount++;
                                isEmptyRow = true;
                                break;
                            } else if ("E".equals(mcCampType)) {
                                if (!Validatory.isValidEmail(colValue)) {
                                    LOGGER.info("Skipping invalid row. Row: {}, Email Address: {} ", new Object[]{rowCount, colValue});
                                    isEmptyRow = true;
                                    ++invalidRowCount;
                                    break;
                                }
                            } else if (!Validatory.isValidPattern(colValue, regex)) {
                                LOGGER.info("Skipping invalid row. Row: {}, Mobile Number: {} ", new Object[]{rowCount, colValue});
                                isEmptyRow = true;
                                ++invalidRowCount;
                                break;
                            }
                        }
                        if (colValue == null) {
                            prs.setNull(++j, Types.VARCHAR);
                        } else {
                            prs.setString(++j, colValue);
                        }
                    }
                    if (isEmptyRow) {
                        continue;
                    }
                    prs.setLong(++j, mcCampId);
                    ++batchRowCount;
                    prs.addBatch();
                    if (batchRowCount > 50) {
                        LOGGER.info("Inserting batch. Row Count: {} ", new Object[]{batchRowCount});
                        batchRowCount = 0;
                        prs.executeBatch();
                    }
                }
                if (batchRowCount > 0) {
                    LOGGER.info("Inserting batch. Row Count: {} ", new Object[]{batchRowCount});
                    prs.executeBatch();
                }
                prs.close();
                if (invalidRowCount > 0) {
                    result = invalidRowCount + " Invalid Rows";
                }

            } catch (Exception e) {
                result = e.getMessage();
                LOGGER.debug("Error while Inserting Upload Details : ", e);
            } finally {
                try {
                   /* if (workBook != null) {
                        workBook.close();
                    }*/
                     sheet = null;
                workbook = null;
                } catch (Exception ex1) {
                    LOGGER.error("Exception while closing workbook instance => {}", ex1);
                }
                try {
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                } catch (Exception ex2) {
                    LOGGER.error("Exception while closing FIS => {}", ex2);
                }
                closeDBCallSt(cs, con);
            }
        } else {
            result = "File Not Available.";
        }
        return result;
    }

    public int getUploadedCampaignCount(Long mcCampId) {
        con = getDBConnection(dataSource);
        int count = 0;
        try {
            prs = con.prepareStatement("SELECT COUNT(*) FROM W_MKTG_CAMPAIGN_DATA WHERE MCD_CAMP_ID = ?");
            prs.setLong(1, mcCampId);
            rs = prs.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            LOGGER.error("Error while getting Count => {}", e);
        } finally {
            try {
                if (prs != null) {
                    prs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
        }
        return count;
    }

    //Journey paths
    public List<MMktgCampaignPath> loadPathList(Long mcCampId) {
        List<MMktgCampaignPath> list = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{mcCampId};
            query = new StringBuilder("SELECT MCP_PATH_ID mcpPathId, MCP_CAMP_ID mcpCampId, MCP_PATH_NAME mcpPathName, MCP_FILTER_ID mcpFilterId, (SELECT MCF_FILTER_NAME FROM M_MKTG_CAMPAIGN_FILTER WHERE MCF_FILTER_ID = MCP_FILTER_ID) filterName ");
            query.append("FROM M_MKTG_CAMPAIGN_PATH WHERE MCP_CAMP_ID = ?");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignPath.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving  path list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadCampaignFilter(Long mcfCampId) {
        Connection con = null;
        List<KeyValue> list = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{mcfCampId};
            String query = "SELECT MCF_FILTER_ID key, MCF_FILTER_NAME value FROM M_MKTG_CAMPAIGN_FILTER WHERE MCF_CAMP_ID = ?";
            list = executeList(con, query, data, KeyValue.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving  filter list details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public void saveJourneyPath(MMktgCampaignPath campPath, Long mcCampId, String userId) {
        Connection con = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String query = "INSERT INTO M_MKTG_CAMPAIGN_PATH(MCP_PATH_ID, MCP_PATH_NAME, MCP_FILTER_ID ,MCP_CAMP_ID, MCP_CR_UID, MCP_CR_DT) "
                    + "VALUES (S_MCP_PATH_ID.NEXTVAL, ?, ?, ?, ?, SYSDATE)";
            ps = con.prepareStatement(query);
            int i = 0;
            ps.setString(++i, campPath.getMcpPathName());
            if (campPath.getMcpFilterId() != null) {
                ps.setLong(++i, campPath.getMcpFilterId());
            } else {
                ps.setString(++i, "");
            }
            ps.setLong(++i, mcCampId);
            ps.setString(++i, userId);
            cnt = ps.executeUpdate();
        } catch (Exception ex) {
            LOGGER.error("Error while inserting Campaign journey path", ex);
        } finally {
            closeDBComp(ps, null, con);
        }
    }

    public MMktgCampaignPath getCampaignPath(MMktgCampaignPath camPath) {
        MMktgCampaignPath path = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT MCP_PATH_ID mcpPathId,MCP_CAMP_ID mcpCampId, MCP_PATH_NAME mcpPathName, MCP_FILTER_ID mcpFilterId FROM M_MKTG_CAMPAIGN_PATH WHERE MCP_PATH_ID = ? AND MCP_CAMP_ID = ? ");
            LOGGER.info("Query :: {} [{}]", query, camPath.getMcpPathId(), camPath.getMcpCampId());
            path = (MMktgCampaignPath) executeQuery(con, query.toString(), new Object[]{camPath.getMcpPathId(), camPath.getMcpCampId()}, MMktgCampaignPath.class);
        } catch (Exception e) {
            LOGGER.error("Error while getting campaign path{}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return path;
    }

    public void updateJourneyPath(MMktgCampaignPath campPath, Long mcCampId, String userId) {
        LOGGER.info("in update**");
        Connection con = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String sql = "UPDATE M_MKTG_CAMPAIGN_PATH SET MCP_PATH_NAME=?,MCP_FILTER_ID=?,MCP_CR_UID=?,MCP_CR_DT=SYSDATE WHERE MCP_PATH_ID=? AND MCP_CAMP_ID=?";
            Object[] params = new Object[]{campPath.getMcpPathName(), campPath.getMcpFilterId(), userId, campPath.getMcpPathId(), mcCampId};
            recCnt = executeUpdate(con, sql, params);
        } catch (Exception e) {
            LOGGER.error("Error while updating jouney path", e);
        } finally {
            closeDbConn(con);
        }
    }

    public void deleteJourneyPath(Long mcpPathId) throws Exception {
        int st = 0;
        Connection con = null;
        try {
            con = getDBConnection(dataSource);
            String sql1 = "DELETE FROM M_MKTG_CAMPAIGN_PATH_FLOW WHERE MCPF_PATH_ID = ?";
            String sql2 = "DELETE FROM M_MKTG_CAMPAIGN_PATH WHERE MCP_PATH_ID = ?";
            prs = con.prepareStatement(sql1);
            prs.setLong(1, mcpPathId);
            st = prs.executeUpdate();
            if (st >= 0) {
                prs = con.prepareStatement(sql2);
                prs.setLong(1, mcpPathId);
                prs.executeUpdate();
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while Delete Campaign journey path Param", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
    }
//journey path flow

    public List<MMktgCampaignPathFlow> loadPathFlowList(Long pathId, Long campaignId) {
        List<MMktgCampaignPathFlow> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = null;
            query = new StringBuilder("SELECT PF.MCPF_PATH_ID mcpfPathId, PF.MCPF_FLOW_NO mcpfFlowNo, "
                    + " (CASE WHEN PF.MCPF_FLOW_NO = Q.MCPF_FLOW_NO THEN '1' ELSE '0' END) mcpfDelYn,"
                    + " PKG_REP_UTILITY.FN_GET_PARA_NAME('MK_ACTION', MCPF_ACTION) mcpfAction, MCPF_SPLIT_YN mcpfSplitYn, "
                    + " (SELECT MCT_NAME FROM M_MKTG_CAMPAIGN_TEMPLATE WHERE MCT_TEMPLATE_ID = MCPF_TEMPLATE_ID) mcpfTemplateName,"
                    + " (SELECT MCT_NAME FROM M_MKTG_CAMPAIGN_TEMPLATE WHERE MCT_TEMPLATE_ID = MCPF_TEMPLATE_ID_2) mcpfTemplate2Name");
            if (campaignId != null && campaignId > 0) {
                query.append(",(SELECT COUNT(MCTL_DATA_ID)  FROM T_MKTG_CAMPAIGN_TXN_LOG WHERE MCTL_PATH_ID  = PF.MCPF_PATH_ID AND "
                        + " MCTL_FLOW_NO = PF.MCPF_FLOW_NO AND MCTL_CAMP_ID = ? AND MCTL_LOG_TYPE IN ('E', 'S')) mcpfCount");
                data = new Object[]{campaignId, pathId, pathId};
            } else {
                data = new Object[]{pathId, pathId};
            }
            query.append(" FROM M_MKTG_CAMPAIGN_PATH_FLOW PF, (SELECT MCPF_PATH_ID, MAX(MCPF_FLOW_NO) MCPF_FLOW_NO FROM M_MKTG_CAMPAIGN_PATH_FLOW "
                    + " WHERE MCPF_PATH_ID = ? GROUP BY MCPF_PATH_ID) Q WHERE Q.MCPF_PATH_ID = PF.MCPF_PATH_ID"
                    + " AND PF.MCPF_PATH_ID = ?");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignPathFlow.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving  template list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<MMktgCampaignPathFlow> loadPathFlowListForAB(Long pathId) {
        List<MMktgCampaignPathFlow> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{pathId};
            query = new StringBuilder("select mcpf_path_id mcpfPathId, mcpf_flow_no mcpfFlowNo, mcpf_action mcpfAction, mcpf_split_yn mcpfSplitYn, mcpf_split_perc mcpfSplitPerc, mcpf_template_id mcpfTemplateId, mcpf_template_id_2 mcpfTemplateId2, mcpf_unicode mcpfUnicode, mcpf_text mcpfText, mcpf_wait_freq mcpfWaitFreq, mcpf_click_url_key mcpfClickUrlKey from m_mktg_campaign_path_flow where mcpf_path_id = ? and mcpf_flow_no = 1");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignPathFlow.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving  template list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> getActionTypes() {
        LOGGER.debug("CampaignSetupDao - getDataTypes");
        List<KeyValue> result = new ArrayList<>();
        try {
            con = getDBConnection(getDataSource());
            String sql = "SELECT PARA_SUB_CODE key, PARA_NAME value FROM M_APP_PARAMETER WHERE PARA_CODE = 'MK_ACTION'";
            result = executeList(con, sql, null, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error in Fetching Type List => {}", e);
        } finally {

            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetupDao - getDataTypes");
        return result;
    }

    public List<KeyValue> getWaitFreq() {
        LOGGER.debug("CampaignSetupDao - getDataTypes");
        List<KeyValue> result = new ArrayList<>();
        try {
            con = getDBConnection(getDataSource());
            String sql = "SELECT PARA_SUB_CODE key, PARA_NAME value FROM M_APP_PARAMETER WHERE PARA_CODE = 'MK_WAIT_FREQ' ORDER BY CASE WHEN INSTR(PARA_SUB_CODE, 'H') > 0 THEN REGEXP_REPLACE(PARA_SUB_CODE, 'H', '') / 24 ELSE TO_NUMBER(PARA_SUB_CODE) END ASC";
            result = executeList(con, sql, null, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error in Fetching Type List => {}", e);
        } finally {

            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetupDao - getDataTypes");
        return result;
    }

    public List<KeyValue> getTemplate(Long mctCampId) {
        LOGGER.debug("CampaignSetupDao - getDataTypes");
        List<KeyValue> result = new ArrayList<>();
        try {
            con = getDBConnection(getDataSource());
            Object[] params = new Object[]{mctCampId};
            String sql = "SELECT MCT_TEMPLATE_ID key, MCT_NAME value FROM M_MKTG_CAMPAIGN_TEMPLATE WHERE MCT_CAMP_ID = ?";
            result = executeList(con, sql, params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error in Fetching Tempalte List => {}", e);
        } finally {

            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetupDao - getTemlate");
        return result;
    }

    public List<KeyValue> getTemplateUrl(Long mcCampId) {
        LOGGER.debug("CampaignSetupDao - getDataTypes");
        List<KeyValue> result = new ArrayList<>();
        try {
            con = getDBConnection(getDataSource());
            Object[] params = new Object[]{mcCampId};
            String sql = "SELECT MTU_URL_KEY key, MCT_NAME ||' - '|| SUBSTR(MTU_URL, 1, INSTR(MTU_URL, 'qtm_cid=@TOKEN@') - 2) value "
                    + "FROM M_MKTG_CAMPAIGN_TEMP_URL, M_MKTG_CAMPAIGN_TEMPLATE WHERE MTU_TEMPLATE_ID = MCT_TEMPLATE_ID "
                    + "AND MCT_CAMP_ID = ?";
            result = executeList(con, sql, params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error in Fetching Tempalte Url List => {}", e);
        } finally {

            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetupDao - getTemlateUrl");
        return result;
    }

    public void saveJourneyPathFlow(MMktgCampaignPathFlow campPathFlow) {
        Connection con = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String query = "INSERT INTO M_MKTG_CAMPAIGN_PATH_FLOW(MCPF_FLOW_NO, MCPF_PATH_ID, MCPF_ACTION, MCPF_WAIT_FREQ, MCPF_TEMPLATE_ID, MCPF_SPLIT_YN, MCPF_SPLIT_PERC, MCPF_TEMPLATE_ID_2, "
                    + "MCPF_UNICODE, MCPF_TEXT, MCPF_CLICK_URL_KEY, MCPF_CR_DT, MCPF_CR_UID) "
                    + "VALUES ((SELECT NVL(MAX(MCPF_FLOW_NO)+1,1) FROM M_MKTG_CAMPAIGN_PATH_FLOW WHERE MCPF_PATH_ID = ?),?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE,?)";
            ps = con.prepareStatement(query);
            int i = 0;
            ps.setLong(++i, campPathFlow.getMcpfPathId());
            ps.setLong(++i, campPathFlow.getMcpfPathId());
            ps.setString(++i, campPathFlow.getMcpfAction());
            if (campPathFlow.getMcpfAction().equals("W")) {
                ps.setString(++i, campPathFlow.getMcpfWaitFreq());
            } else {
                ps.setNull(++i, Types.VARCHAR);
            }
            if ("M".equals(campPathFlow.getMcpfAction()) || "MB".equals(campPathFlow.getMcpfAction()) || "MC".equals(campPathFlow.getMcpfAction())
                    || "MO".equals(campPathFlow.getMcpfAction()) || "MV".equals(campPathFlow.getMcpfAction())) {
                ps.setLong(++i, campPathFlow.getMcpfTemplateId());
            } else {
                ps.setNull(++i, Types.VARCHAR);
            }
            if ("1".equals(campPathFlow.getMcpfSplitYn()) && "M".equals(campPathFlow.getMcpfAction())) {
                ps.setString(++i, campPathFlow.getMcpfSplitYn());
                ps.setByte(++i, campPathFlow.getMcpfSplitPerc());
                ps.setLong(++i, campPathFlow.getMcpfTemplateId2());
            } else {
                ps.setString(++i, "0");
                ps.setNull(++i, Types.VARCHAR);
                ps.setNull(++i, Types.VARCHAR);
            }
            if ("P".equals(campPathFlow.getMcpfAction()) || "S".equals(campPathFlow.getMcpfAction())) {
                ps.setString(++i, campPathFlow.getMcpfUnicode());
                ps.setString(++i, campPathFlow.getMcpfText());
            } else {
                ps.setNull(++i, Types.VARCHAR);
                ps.setNull(++i, Types.VARCHAR);
            }
            if ("MC".equals(campPathFlow.getMcpfAction())) {
                ps.setString(++i, campPathFlow.getMcpfClickUrlKey());
            } else {
                ps.setNull(++i, Types.VARCHAR);
            }

            ps.setString(++i, campPathFlow.getMcpfCrUid());
            cnt = ps.executeUpdate();

        } catch (Exception ex) {
            LOGGER.error("Error while inserting Campaign Filter", ex);
        } finally {
            closeDBComp(ps, null, con);
        }
    }

    public void updateJourneyPathFlow(MMktgCampaignPathFlow campPathFlow) {
        LOGGER.info("in update jouney path flow**");
        Connection con = null;
        PreparedStatement ps = null;
        int cnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String query = "UPDATE M_MKTG_CAMPAIGN_PATH_FLOW SET MCPF_ACTION= ?, MCPF_WAIT_FREQ= ?, MCPF_TEMPLATE_ID=?, MCPF_SPLIT_YN= ?, MCPF_SPLIT_PERC= ?, "
                    + "MCPF_TEMPLATE_ID_2=?, MCPF_UNICODE=?, MCPF_TEXT=?, MCPF_CLICK_URL_KEY=?, MCPF_UPD_DT=SYSDATE, MCPF_UPD_UID=? WHERE MCPF_FLOW_NO= ? AND MCPF_PATH_ID=? ";
            ps = con.prepareStatement(query);
            int i = 0;

            ps.setString(++i, campPathFlow.getMcpfAction());
            if (campPathFlow.getMcpfAction().equals("W")) {
                ps.setString(++i, campPathFlow.getMcpfWaitFreq());
            } else {
                ps.setNull(++i, Types.VARCHAR);
            }
            if ("M".equals(campPathFlow.getMcpfAction()) || "MB".equals(campPathFlow.getMcpfAction()) || "MC".equals(campPathFlow.getMcpfAction())
                    || "MO".equals(campPathFlow.getMcpfAction()) || "MV".equals(campPathFlow.getMcpfAction())) {
                ps.setLong(++i, campPathFlow.getMcpfTemplateId());
            } else {
                ps.setNull(++i, Types.VARCHAR);
            }
            if ("1".equals(campPathFlow.getMcpfSplitYn()) && "M".equals(campPathFlow.getMcpfAction())) {
                ps.setString(++i, campPathFlow.getMcpfSplitYn());
                ps.setByte(++i, campPathFlow.getMcpfSplitPerc());
                ps.setLong(++i, campPathFlow.getMcpfTemplateId2());
            } else {
                ps.setString(++i, "0");
                ps.setNull(++i, Types.VARCHAR);
                ps.setNull(++i, Types.VARCHAR);
            }
            if ("P".equals(campPathFlow.getMcpfAction()) || "S".equals(campPathFlow.getMcpfAction())) {
                ps.setString(++i, campPathFlow.getMcpfUnicode());
                ps.setString(++i, campPathFlow.getMcpfText());
            } else {
                ps.setNull(++i, Types.VARCHAR);
                ps.setNull(++i, Types.VARCHAR);
            }
            if ("MC".equals(campPathFlow.getMcpfAction())) {
                ps.setString(++i, campPathFlow.getMcpfClickUrlKey());
            } else {
                ps.setNull(++i, Types.VARCHAR);
            }
            ps.setString(++i, campPathFlow.getMcpfCrUid());
            ps.setShort(++i, campPathFlow.getMcpfFlowNo());
            ps.setLong(++i, campPathFlow.getMcpfPathId());
            cnt = ps.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error while updating jouney path flow", e);
        } finally {
            closeDBComp(ps, null, con);
        }
    }

    public MMktgCampaignPathFlow getCampaignPathFlow(MMktgCampaignPathFlow camPathFlow) {
        MMktgCampaignPathFlow pathflow = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT MCPF_PATH_ID mcpfPathId,MCPF_FLOW_NO mcpfFlowNo,MCPF_ACTION mcpfAction, MCPF_WAIT_FREQ mcpfWaitFreq, MCPF_SPLIT_YN mcpfSplitYn,MCPF_SPLIT_PERC mcpfSplitPerc, "
                    + " MCPF_TEMPLATE_ID_2 mcpfTemplateId2, MCPF_TEMPLATE_ID mcpfTemplateId, MCPF_UNICODE mcpfUnicode, MCPF_TEXT mcpfText, MCPF_CLICK_URL_KEY mcpfClickUrlKey FROM M_MKTG_CAMPAIGN_PATH_FLOW WHERE MCPF_PATH_ID = ? AND MCPF_FLOW_NO = ?");
            LOGGER.info("Query :: {} [{}]", query, camPathFlow.getMcpfPathId());
            pathflow = (MMktgCampaignPathFlow) executeQuery(con, query.toString(), new Object[]{camPathFlow.getMcpfPathId(), camPathFlow.getMcpfFlowNo()}, MMktgCampaignPathFlow.class);
        } catch (Exception e) {
            LOGGER.error("Error while getting Filter {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return pathflow;
    }

    public void deleteCampaignPathFlow(MMktgCampaignPathFlow campPathFlow) throws Exception {
        LOGGER.info("Delete Campaign Filter - Enter PathFlowNo: {}, PathId: {}", new Object[]{campPathFlow.getMcpfFlowNo(), campPathFlow.getMcpfPathId()});
        int st = 0;
        Connection con = null;
        try {
            con = getDBConnection(dataSource);
            String sql = "DELETE FROM M_MKTG_CAMPAIGN_PATH_FLOW WHERE MCPF_FLOW_NO = ? AND MCPF_PATH_ID = ?";
            prs = con.prepareStatement(sql);
            prs.setLong(1, campPathFlow.getMcpfFlowNo());
            prs.setLong(2, campPathFlow.getMcpfPathId());
            st = prs.executeUpdate();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while Delete Campaign form Fields Param", e);
            throw e;
        } finally {
            closeDBComp(prs, null, con);
        }
    }

    public String getCampaignDataType(Long mcCampId) throws Exception {
        LOGGER.debug("CampaignSetupDao - Get Campaign View - Enter CampId: {}", new Object[]{mcCampId});
        String result = null;
        Connection con = null;
        PreparedStatement ps = null;
        con = getDBConnection(dataSource);

        try {
            String query = "SELECT MC_CAMP_TYPE FROM M_MKTG_CAMPAIGN WHERE MC_CAMP_ID = ?";
            ps = con.prepareStatement(query);
            ps.setLong(1, mcCampId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getString("MC_CAMP_TYPE");
            }

        } catch (Exception e) {

            LOGGER.debug("CampaignSetupDao - Get Campaign type - Enter");
        } finally {
            con.close();
        }
        return result;
    }

    public TMktgCampaignTxnLog validateUnsubscribeToken(String token) {
        TMktgCampaignTxnLog campaignLog = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT MCTL_CAMP_ID mctlCampId, MCTL_TEMPLATE_ID mctlTemplateId, MCTL_URL_KEY mctlUrlKey  "
                    + " FROM T_MKTG_CAMPAIGN_TXN_LOG WHERE MCTL_URL_KEY = ? ");
            LOGGER.info("Query :: {} [{}]", query, token);
            campaignLog = (TMktgCampaignTxnLog) executeQuery(con, query.toString(), new Object[]{token}, TMktgCampaignTxnLog.class);
        } catch (Exception e) {
            LOGGER.error("Error while getting validating token {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return campaignLog;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void insertCampaignRecuringData(CampaignVO bean) throws SQLException {
        int rec_cnt = 0;
        con = getDBConnection(getDataSource());
        StringBuilder camp_sb = new StringBuilder("select ");
        constructSelectClause(bean.getCampaign(), camp_sb);
        camp_sb.append("from ").append(bean.getCampaign().getMcDataSourceView()).append(" ");
        if (bean.getDataParam() != null && !bean.getDataParam().isEmpty()) {
            camp_sb.append("where ");
            bean.getDataParam().forEach((dataParam) -> {
                constructWhereClause(dataParam, camp_sb);
            });
        }
        LOGGER.debug("Pull data from view. Query: {}", new Object[]{camp_sb});
        camp_sb.insert(0, "insert into w_mktg_campaign_data (mcd_camp_id, mcd_contact_id, mcd_contact_ref, mcd_data_1, mcd_data_2, mcd_data_3, mcd_data_4, mcd_data_5, mcd_data_6, mcd_data_7, mcd_data_8, mcd_data_9, mcd_data_10, mcd_data_11, mcd_data_12, mcd_data_13, mcd_data_14, mcd_data_15, mcd_data_16, mcd_data_17, mcd_data_18, mcd_data_19, mcd_data_20) ");
        LOGGER.debug("Insert data from view. Query: {}", new Object[]{camp_sb});
        prs = con.prepareStatement(camp_sb.toString());
        rec_cnt = prs.executeUpdate();
        LOGGER.info("Record populated in campaign data. Id: {}, Status: {}", new Object[]{bean.getCampaign().getMcCampId(), rec_cnt});
        DbUtils.closeQuietly(prs);
    }

    private void constructSelectClause(final MMktgCampaign camp, final StringBuilder sb) {
        sb.append(camp.getMcCampId()).append(", ");
        StringTokenizer col_st = new StringTokenizer(camp.getMcSelectedCols(), "~");
        for (int i = 0; i < 22; i++) {
            if (col_st.hasMoreTokens()) {
                sb.append("\"").append(col_st.nextToken()).append("\"");
                if (col_st.hasMoreTokens()) {
                    sb.append(", ");
                }
            } else {
                sb.append(", null");
            }
        }
        sb.append(" ");
    }

    private void constructWhereClause(final MMktgCampaignDataParam dataParam, final StringBuilder sb) {
        if (StringUtils.isNotBlank(dataParam.getMdpColumn())) {
            LOGGER.debug("Column: {}, Oper: {}, Val From: {}, Val To: {} ", new Object[]{dataParam.getMdpColumn(), dataParam.getMdpOperator(), dataParam.getMdpValueFm(), dataParam.getMdpValueTo()});
            if (StringUtils.isNotBlank(dataParam.getMdpCondition())) {
                sb.append(dataParam.getMdpCondition()).append(" ");
            }
            if (StringUtils.isBlank(dataParam.getMdpValueFm())) {
                sb.append("\"").append(dataParam.getMdpColumn()).append("\" is null ");
            } else if ("N".equals(dataParam.getMdpDataType()) || "C".equals(dataParam.getMdpDataType())) {
                if ("=".equals(dataParam.getMdpOperator()) || "!=".equals(dataParam.getMdpOperator())
                        || ">".equals(dataParam.getMdpOperator()) || "<".equals(dataParam.getMdpOperator())) {
                    sb.append("\"").append(dataParam.getMdpColumn()).append("\" ").append(dataParam.getMdpOperator());
                    if ("C".equals(dataParam.getMdpDataType())) {
                        sb.append("'");
                    }
                    sb.append(dataParam.getMdpValueFm());
                    if ("C".equals(dataParam.getMdpDataType())) {
                        sb.append("'");
                    }
                    sb.append(" ");
                } else {
                    if ("in".equalsIgnoreCase(dataParam.getMdpOperator()) || "not in".equalsIgnoreCase(dataParam.getMdpOperator())) {
                        sb.append("\"").append(dataParam.getMdpColumn()).append("\" ").append(dataParam.getMdpOperator()).append(" (");
                        if ("C".equalsIgnoreCase(dataParam.getMdpDataType())) {
                            sb.append("'").append(dataParam.getMdpValueFm().replaceAll(",", "','")).append("'");
                        } else {
                            sb.append(dataParam.getMdpValueFm());
                        }
                        sb.append(")");
                    } else if ("between".equalsIgnoreCase(dataParam.getMdpOperator()) || "not between".equalsIgnoreCase(dataParam.getMdpOperator())) {
                        sb.append("\"").append(dataParam.getMdpColumn()).append("\" ").append(dataParam.getMdpOperator()).append(dataParam.getMdpValueFm()).append(" and ").append(dataParam.getMdpValueTo());
                    }
                    sb.append(" ");
                }
            } else if ("D".equals(dataParam.getMdpDataType())) {
                boolean isDateTime = false;
                if (dataParam.getMdpValueFm().matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
                    isDateTime = true;
                }
                if (!isDateTime) {
                    sb.append("trunc(");
                }
                sb.append("\"").append(dataParam.getMdpColumn()).append("\"");
                if (!isDateTime) {
                    sb.append(")");
                }
                sb.append(" ").append(dataParam.getMdpOperator()).append(" ");
                if (!isDateTime) {
                    sb.append("trunc(");
                }
                sb.append("to_date('").append(dataParam.getMdpValueFm());
                if (isDateTime) {
                    sb.append("', 'DD/MM/YYYY HH24:MI')");
                } else {
                    sb.append("', 'DD/MM/YYYY')");
                }
                if (!isDateTime) {
                    sb.append(")");
                }
                if ("between".equalsIgnoreCase(dataParam.getMdpOperator()) || "not between".equalsIgnoreCase(dataParam.getMdpOperator())) {
                    sb.append(" and ");
                    if (!isDateTime) {
                        sb.append("trunc(");
                    }
                    sb.append("to_date('").append(dataParam.getMdpValueTo());
                    if (isDateTime) {
                        sb.append("', 'DD/MM/YYYY HH24:MI')");
                    } else {
                        sb.append("', 'DD/MM/YYYY')");
                    }
                    if (!isDateTime) {
                        sb.append(")");
                    }
                }
                sb.append(" ");
            }
        }
    }

    public Map<String, String> getCampaignOperators() {
        LOGGER.debug("CampaignSetupDAO - Active Users List Entry");
        Map<String, String> result = new HashMap<>();
        try {
            con = getDBConnection(getDataSource());
            String sql = "SELECT UPPER(PARA_SUB_CODE) PARA_SUB_CODE, PARA_NAME FROM M_APP_PARAMETER WHERE PARA_CODE = 'MK_OPERATOR'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                result.put(rs.getString("PARA_SUB_CODE"), rs.getString("PARA_NAME"));
            }
        } catch (Exception e) {
            LOGGER.error("Error in Fetching Operator Filter List => {}", e);
        } finally {
            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetupDAO - Filter Operator List Exit");
        return result;
    }

    public void updateCampaignColumns(MMktgCampaign campaign, Long mcCampId, String userId) {
        LOGGER.info("in update Campaign Columns campId: {}, userId: {}", new Object[]{mcCampId, userId});
        Connection con = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String sql = "UPDATE M_MKTG_CAMPAIGN SET MC_SELECTED_COLS= ?, MC_DATA_SOURCE_VIEW = ?, MC_UPD_DT=SYSDATE, MC_UPD_UID=? WHERE MC_CAMP_ID=? ";
            Object[] params = new Object[]{campaign.getMcSelectedCols(), campaign.getMcDataSourceView(), userId, mcCampId};
            recCnt = executeUpdate(con, sql, params);
        } catch (Exception e) {
            LOGGER.error("Error while updating campaign Columns", e);
        } finally {
            closeDbConn(con);
        }
    }

    public List<TMktgCampaignTxnLog> loadTxnClickedData(Long mcCampId) {
        LOGGER.info("CampaignSetupDao -  load clicked Data  Enter-  CampId: {}", new Object[]{mcCampId});
        List<TMktgCampaignTxnLog> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{mcCampId};
            query = new StringBuilder("SELECT MLOG.MCTL_DATA_ID mctldataId, MLOG.MCTL_CR_DT mctlCrDt, (SELECT SUBSTR(MTU_URL, 1, INSTR(MTU_URL, 'qtm_cid=@TOKEN@') - 2) FROM M_MKTG_CAMPAIGN_TEMP_URL WHERE  MTU_URL_KEY = MLOG.MCTL_URL_KEY) mctlUrl FROM T_MKTG_CAMPAIGN_TXN_LOG MLOG WHERE MCTL_CAMP_ID = ? AND MCTL_LOG_TYPE = 'C'");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, TMktgCampaignTxnLog.class
            );
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Campaign Clicked list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadSenderAndReplyList(String mcCampType) {
        LOGGER.debug("CampaignSetupDao - loadSenderAndReplyList CampType: {}", new Object[]{mcCampType});
        List<KeyValue> result = new ArrayList<>();
        try {
            con = getDBConnection(getDataSource());
            Object[] params = new Object[]{"," + mcCampType + ","};
            String query = "SELECT AC_VALUE key, AC_DESC value, AC_MC_CODE info FROM M_APP_CODES WHERE AC_TYPE = 'MK_SENDER' AND INSTR(AC_MC_CODE, ?) > 0";
            result = executeList(con, query, params, KeyValue.class
            );
        } catch (Exception e) {
            LOGGER.error("Error in Fetching loadSenderAndReplyList => {}", e);
        } finally {
            closeDbConn(con);
        }
        return result;
    }

    public void updateCampaignStatus(CampaignVO bean, String status, String userId) {
        LOGGER.info("in update Campaign Status ==> Campaign Id: {}, UserId: {}", new Object[]{bean.getMcCampId(), userId});
        Connection con = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            java.sql.Timestamp nextRun = null;
            if ("R".equals(status)) {
                if (bean.getCampaign().getMcDelayStartDate() == null) {
                    Calendar cal = Calendar.getInstance();
                    Calendar cal_sys = Calendar.getInstance();
                    if ("1".equals(bean.getCampaign().getMcRecurringYn())) {
                        if ("H".equals(bean.getCampaign().getMcScheduleMode())) {
                            cal.add(Calendar.HOUR, 1);
                            cal.set(Calendar.MINUTE, 0);
                            cal.set(Calendar.SECOND, 0);
                        } else if (null != bean.getCampaign().getMcScheduleTime()) {
                            DecimalFormat df = new DecimalFormat("#0.00");
                            String s = df.format(bean.getCampaign().getMcScheduleTime().doubleValue());
                            String[] schTime = s.split("\\.");
                            cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(schTime[0]));
                            cal.set(Calendar.MINUTE, Integer.valueOf(schTime[1]));
                            cal.set(Calendar.SECOND, 0);
                        }
                        if (null != bean.getCampaign().getMcScheduleMode()) {
                            //M-Monthly on, W-Weekly on
                            switch (bean.getCampaign().getMcScheduleMode()) {
                                case "D":
                                    cal.add(Calendar.DATE, bean.getCampaign().getMcScheduleValue());
                                case "M":
                                    if (cal.get(Calendar.DAY_OF_MONTH) > bean.getCampaign().getMcScheduleValue()) {
                                        cal.add(Calendar.MONTH, 1);
                                    } else if ((cal.get(Calendar.DAY_OF_MONTH) == bean.getCampaign().getMcScheduleValue())
                                            && cal_sys.get(Calendar.HOUR_OF_DAY) > cal.get(Calendar.HOUR_OF_DAY)) {
                                        cal.add(Calendar.MONTH, 1);
                                    } else if ((cal.get(Calendar.DAY_OF_MONTH) == bean.getCampaign().getMcScheduleValue())
                                            && cal_sys.get(Calendar.HOUR_OF_DAY) == cal.get(Calendar.HOUR_OF_DAY)
                                            && cal_sys.get(Calendar.MINUTE) > cal.get(Calendar.MINUTE)) {
                                        cal.add(Calendar.MONTH, 1);
                                    }
                                    cal.set(Calendar.DAY_OF_MONTH, bean.getCampaign().getMcScheduleValue());
                                    break;
                                case "W":
                                    if (cal.get(Calendar.DAY_OF_WEEK) > bean.getCampaign().getMcScheduleValue()) {
                                        cal.add(Calendar.WEEK_OF_YEAR, 1);
                                    } else if ((cal.get(Calendar.DAY_OF_WEEK) == bean.getCampaign().getMcScheduleValue())
                                            && cal_sys.get(Calendar.HOUR_OF_DAY) > cal.get(Calendar.HOUR_OF_DAY)) {
                                        cal.add(Calendar.WEEK_OF_YEAR, 1);
                                    } else if ((cal.get(Calendar.DAY_OF_MONTH) == bean.getCampaign().getMcScheduleValue())
                                            && cal_sys.get(Calendar.HOUR_OF_DAY) == cal.get(Calendar.HOUR_OF_DAY)
                                            && cal_sys.get(Calendar.MINUTE) > cal.get(Calendar.MINUTE)) {
                                        cal.add(Calendar.WEEK_OF_YEAR, 1);
                                    }
                                    cal.set(Calendar.DAY_OF_WEEK, bean.getCampaign().getMcScheduleValue());
                                    break;
                                default:
                                    break;
                            }
                        }
                    } else if (null != bean.getCampaign().getMcStartTime()) {
                        cal.add(Calendar.HOUR_OF_DAY, bean.getCampaign().getMcStartTime());
                    }
                    nextRun = new java.sql.Timestamp(cal.getTime().getTime());
                    LOGGER.info("Next Run Date: {}", new Object[]{nextRun});
                } else {
                    nextRun = new java.sql.Timestamp(bean.getCampaign().getMcDelayStartDate().getTime());
                }
            }
            StringBuilder sql = new StringBuilder("UPDATE M_MKTG_CAMPAIGN SET MC_STATUS = ?, MC_UPD_UID = ?, MC_UPD_DT = SYSDATE, ");
            if ("R".equals(status)) {
                sql.append("MC_START_DATE = SYSDATE, MC_NEXT_RUN_DATE = DECODE(MC_AB_YN, '1', MC_AB_END_DATE, ?) ");
            } else {
                sql.append("MC_END_DATE = SYSDATE, MC_NEXT_RUN_DATE = ? ");
            }

            sql.append("WHERE MC_CAMP_ID = ?");

            Object[] params = new Object[]{status, userId, nextRun, bean.getMcCampId()};
            LOGGER.debug("Updating campaign status ==> Status: {}, User Id: {}, Next Run: {}, Camp Id: {}", params);
            recCnt = executeUpdate(con, sql.toString(), params);
        } catch (Exception e) {
            LOGGER.error("Error while updating campaign Status", e);
        } finally {
            closeDbConn(con);
        }
    }

    public void updateCampaignTxnStatus(Long mcCampId) {
        LOGGER.info("in update Campaign Txn Status ==> Campaign Id: {}, UserId: {}", new Object[]{mcCampId});
        Connection con = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String sql = "UPDATE T_MKTG_CAMPAIGN_TXN SET MCT_TXN_STATUS = 'S', MCT_END_DATE = DECODE(MCT_TXN_STATUS, 'W', SYSDATE, NULL) WHERE MCT_TXN_STATUS IN ('R', 'W') AND MCT_CAMP_ID = ?";
            Object[] params = new Object[]{mcCampId};
            recCnt = executeUpdate(con, sql, params);
        } catch (Exception e) {
            LOGGER.error("Error while updating Txn campaign Status", e);
        } finally {
            closeDbConn(con);
        }
    }

    //check for Templates
    public int checkTemplateForPaths(final Long mcCampId) {
        LOGGER.info("Check template for paths CampId: {}", new Object[]{mcCampId});
        List<KeyValue> result = new ArrayList<>();
        int listSize = 0;
        try {
            con = getDBConnection(getDataSource());
            Object[] params = new Object[]{mcCampId};
            String query = "SELECT MCP_PATH_ID key, MCPF_FLOW_NO value FROM (SELECT * FROM M_MKTG_CAMPAIGN_PATH, M_MKTG_CAMPAIGN_PATH_FLOW "
                    + " WHERE MCP_CAMP_ID = ?  AND MCP_PATH_ID = MCPF_PATH_ID "
                    + " AND MCPF_FLOW_NO = 1 AND MCPF_TEMPLATE_ID IS NOT NULL "
                    + " AND MCPF_TEMPLATE_ID_2 IS NOT NULL ORDER BY MCP_PATH_ID) WHERE ROWNUM <= 1 ";
            result = executeList(con, query, params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error in Fetching Template => {}", e);
        } finally {
            closeDbConn(con);
        }
        listSize = result.size();
        return listSize;
    }

    public void setDataForABTesting(final MMktgCampaign bean, final Long mcCampId) throws IOException {
        LOGGER.info("in setDataForABTesting**");
        PreparedStatement pst = null;
        LOGGER.info(" McDataSourceType is **" + bean.getMcDataSourceType());
        try {
            con = getDBConnection(getDataSource());
            int rec_cnt = 0;
            if ("A".equals(bean.getMcDataSourceType())) {
                LOGGER.info("wen McDataSourceType is A");
                List<MMktgCampaignDataParam> dataParamList = getCampaignStoredFilters(mcCampId);
                StringBuilder camp_sb = new StringBuilder("select ");
                constructSelectClause(bean, camp_sb);
                camp_sb.append("from ").append(bean.getMcDataSourceView()).append(" ");
                if (dataParamList != null && !dataParamList.isEmpty()) {
                    camp_sb.append("where ");
                    dataParamList.forEach((dataParam) -> {
                        constructWhereClause(dataParam, camp_sb);
                    });
                }
                LOGGER.debug("Pull data from view. Query: {}", new Object[]{camp_sb});
                camp_sb.insert(0, "insert into w_mktg_campaign_data (mcd_camp_id, mcd_contact_id, mcd_contact_ref, mcd_data_1, mcd_data_2, mcd_data_3, mcd_data_4, mcd_data_5, mcd_data_6, mcd_data_7, mcd_data_8, mcd_data_9, mcd_data_10, mcd_data_11, mcd_data_12, mcd_data_13, mcd_data_14, mcd_data_15, mcd_data_16, mcd_data_17, mcd_data_18, mcd_data_19, mcd_data_20) ");
                LOGGER.debug("Insert data from view. Query: {}", new Object[]{camp_sb});
                // Delete existing recodrds and inserting new records from view
                rec_cnt = updateDataFromView(camp_sb.toString(), mcCampId);
            } else if ("E".equals(bean.getMcDataSourceType())) {
                LOGGER.info("wen McDataSourceType is E");
                rec_cnt = 1;
            }
            if (rec_cnt > 0) {
                LOGGER.info("wen rec_cnt > 0");
                List<MMktgCampaignPath> pathList = loadPathList(mcCampId);
                MMktgCampaignPath path = pathList.get(0);
                StringBuilder data_sb = new StringBuilder(QRY_DATA);
                StringBuilder data_del = new StringBuilder(DEL_DATA);
                if (path.getMcpFilterId() != null) {
                    List<MMktgCampaignFilterParm> filterParamList = getmktgCampFilterParams(path.getMcpFilterId());
                    if (filterParamList != null && !filterParamList.isEmpty()) {
                        data_sb.append(" and ");
                        filterParamList.forEach((filterParam) -> {
                            MMktgCampaignDataParam dataParam = new MMktgCampaignDataParam();
                            dataParam.setMdpColumn(filterParam.getMcfpDataCol());
                            dataParam.setMdpDataType(filterParam.getMcfpDataType());
                            dataParam.setMdpOperator(filterParam.getMcfpOperator());
                            dataParam.setMdpValueFm(filterParam.getMcfpValueFm());
                            dataParam.setMdpValueTo(filterParam.getMcfpValueTo());
                            dataParam.setMdpCondition(filterParam.getMcfpCondition());
                            constructWhereClause(dataParam, data_sb);
                        });
                    }
                }
                data_sb.append(" ORDER BY mcd_contact_id) A WHERE ROWNUM <= " + bean.getMcAbDataCount());
                data_del.append(" ORDER BY mcd_contact_id) A WHERE ROWNUM <= " + bean.getMcAbDataCount() + " )");

                LOGGER.info("con is**" + con);
                LOGGER.info("About to create transaction**" + mcCampId);
                LOGGER.info("path.getMcpPathId() is**" + path.getMcpPathId());
                if (con == null) {
                    LOGGER.info("WEN con is NULL**" + con);
                    con = getDBConnection(getDataSource());
                }
                con = getDBConnection(getDataSource());
                rs = null;
                int txn_cnt = 0;
                // Create Transaction
                TMktgCampaignTxn txn = new TMktgCampaignTxn();
                txn.setMctCampId(mcCampId);
                txn.setMctPathId(path.getMcpPathId());
                txn.setMctPathFlowNo((short) 1);
                pst = con.prepareStatement(INS_AB_TXT, new String[]{"MCT_TXN_ID"});
                pst.setLong(1, txn.getMctCampId());
                pst.setLong(2, txn.getMctPathId());
                pst.setShort(3, txn.getMctPathFlowNo());
                txn_cnt = pst.executeUpdate();
                rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    txn.setMctTxnId(rs.getLong(1));
                }
                List<TMktgCampaignTxn> txnList = loadTxnData(txn.getMctTxnId());
                if (null == txnList || txnList.isEmpty()) {
                    LOGGER.info("Unable to get the trasaction.");
                } else {
                    txn = txnList.get(0);
                }

                LOGGER.info("Creating transaction. Camp Id: {}, Txn Id: {}, Status: {}", new Object[]{txn.getMctCampId(), txn.getMctTxnId(), rec_cnt});
                prs.close();
                rs.close();

                // Pulling data from "W_MKTG_CAMPAIGN_DATA" by applying dynamic filter (if exists) and load into "T_MKTG_CAMPAIGN_TXN_DATA"
                data_sb.insert(0, "SELECT s_mctd_data_id.nextval, " + txn.getMctTxnId() + ", standard_hash(s_mctd_data_id.currval, 'MD5'), sysdate, A.* FROM (");
                data_sb.insert(0, "insert into t_mktg_campaign_txn_data (mctd_data_id, mctd_txn_id, mctd_key, mctd_cr_dt, mctd_camp_id, mctd_contact_id, mctd_contact_ref, mctd_data_1, mctd_data_2, mctd_data_3, mctd_data_4, mctd_data_5, mctd_data_6, mctd_data_7, mctd_data_8, mctd_data_9, mctd_data_10, mctd_data_11, mctd_data_12, mctd_data_13, mctd_data_14, mctd_data_15, mctd_data_16, mctd_data_17, mctd_data_18, mctd_data_19, mctd_data_20) ");
                data_sb.insert(0, "DECLARE P_ERROR VARCHAR2(1000) := NULL; BEGIN ");
                data_sb.append(";").append(data_del.toString());
                data_sb.append(";EXCEPTION WHEN OTHERS THEN P_ERROR := 'Error in moving data : '|| SQLERRM; ? := P_ERROR; END;");
                LOGGER.debug("Pull data from campaign and moving to campaign txn data. Id: {} Query: {}", new Object[]{bean.getMcCampId(), data_sb});
                CallableStatement cs = null;
                cs = con.prepareCall(data_sb.toString());
                cs.setLong(1, mcCampId);
                cs.setLong(2, mcCampId);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.executeQuery();
                LOGGER.info("Record(s) populated in transaction data from campaign. Query: {}, Status: {}", new Object[]{data_sb, rec_cnt});
                cs.close();

                prs = con.prepareStatement(QRY_TXN_DATA_COUNT);
                prs.setLong(1, txn.getMctTxnId());
                prs.setLong(2, mcCampId);
                rs = prs.executeQuery();
                int txn_rec_cnt = 0;
                if (rs.next()) {
                    txn_rec_cnt = rs.getInt(1);
                }
                LOGGER.info("Total record(s) found in transaction data. Camp Id: {}, Txn Id: {}, Records: {}", new Object[]{bean.getMcCampId(), txn.getMctTxnId(), txn_rec_cnt});
                prs.close();
                //private final static String UPD_TXT_RUN = "update t_mktg_campaign_txn set mct_data_count = ?, mct_next_run_date = ?, mct_txn_status = ?, mct_start_date = ?, mct_end_date = ? where mct_txn_id = ? and mct_camp_id = ?";
                if (txn_rec_cnt == 0) {
                    updateCampaignTxnRun(0, null, "D", new java.sql.Timestamp(new Date().getTime()), new java.sql.Timestamp(new Date().getTime()), txn.getMctTxnId(), bean.getMcCampId());
                    LOGGER.info("Due to zero record(s) closing the transaction. Camp Id: {}, Txn Id: {}, Status: {}", new Object[]{bean.getMcCampId(), txn.getMctTxnId(), rec_cnt});
                } else {
                    updateCampaignTxnRun(txn_rec_cnt, null, "R", new java.sql.Timestamp(new Date().getTime()), null, txn.getMctTxnId(), bean.getMcCampId());
                    //  rec_cnt = run.update(m$connection, UPD_TXT_RUN, txn_rec_cnt, null, "R", new java.sql.Timestamp(new Date().getTime()), null, txn.getMctTxnId(), camp.getMcCampId());
                    LOGGER.info("Starting the transaction. Camp Id: {}, Txn Id: {}, Status: {}", new Object[]{bean.getMcCampId(), txn.getMctTxnId(), rec_cnt});
                    abFlowProcess(bean, txn, txn_rec_cnt);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // query = null;
            closeDBComp(prs, rs, con);
        }
    }

    public int updateDataFromView(final String insertQuery, final Long mcCampId) {
        int rec_count = 0;
        try {
            con = getDBConnection(getDataSource());
            String query = "delete from w_mktg_campaign_data where mcd_camp_id = ?";
            prs = con.prepareStatement(query);
            prs.setLong(1, mcCampId);
            prs.executeUpdate();
            prs.close();
            query = insertQuery;
            prs = con.prepareStatement(query);
            rec_count = prs.executeUpdate();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error in Insert Data Param => {}", e);
        } finally {
            closeDBComp(prs, null, con);
        }
        LOGGER.debug(" Insert Data From View Exit");
        return rec_count;
    }

    public List<TMktgCampaignTxn> loadTxnData(Long txnId) {
        LOGGER.info("CampaignSetupDao -  load txn Data  Enter-  txnId: {}", new Object[]{txnId});
        List<TMktgCampaignTxn> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{txnId};
            query = new StringBuilder("select mct_txn_id mctTxnId, mct_camp_id mctCampId, mct_path_id mctPathId, mct_path_flow_no mctPathFlowNo, mct_data_count mctDataCount, mct_wait_yn mctWaitYn, mct_next_run_date mctNextRunDate, mct_txn_status mctTxnStatus, mct_start_date mctStartDate, mct_end_date mctEndDate from t_mktg_campaign_txn where mct_txn_id = ?");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, TMktgCampaignTxn.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving load Txn Data => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public void updateCampaignTxnRun(int count, Date nextRunDate, String status, Date startDate, Date endDate, Long txnId, Long mcCampId) {
        LOGGER.info("in update Campaign Txn Status ==> Campaign Id: {}, UserId: {}", new Object[]{mcCampId});
        Connection con = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String sql = UPD_TXT_RUN;
            Object[] params = new Object[]{count, nextRunDate, status, startDate, endDate, txnId, mcCampId};
            recCnt = executeUpdate(con, sql, params);
        } catch (Exception e) {
            LOGGER.error("Error while updating Txn campaign Status", e);
        } finally {
            closeDbConn(con);
        }
    }

    public List<TMktgCampaignTxnData> loadTxnDataList(Long txnId) {
        LOGGER.info("CampaignSetupDao - Load Txn Data List - Enter TxnId: {}", new Object[]{txnId});
        List<TMktgCampaignTxnData> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{txnId};
            query = new StringBuilder("select mctd_data_id mctdDataId, mctd_camp_id mctdCampId, mctd_txn_id mctdTxnId, mctd_contact_id mctdContactId, mctd_contact_ref mctdContactRef, mctd_key mctdKey, mctd_sent_yn mctdSentYn, mctd_sent_dt mctdSentDt, mctd_bounced_yn mctdBouncedYn, mctd_opened_yn mctdOpenedYn, mctd_replied_yn mctdRepliedYn, mctd_clicked_yn mctdClickedYn, mctd_converted_yn mctdConvertedYn, mctd_converted_dt mctdConvertedDt, mctd_converted_value mctdConvertedValue, mctd_converted_ref_no mctdConvertedRefNo, mctd_data_1 mctdData1, mctd_data_2 mctdData2, mctd_data_3 mctdData3, mctd_data_4 mctdData4, mctd_data_5 mctdData5, mctd_data_6 mctdData6, mctd_data_7 mctdData7, mctd_data_8 mctdData8, mctd_data_9 mctdData9, mctd_data_10 mctdData10, mctd_data_11 mctdData11, mctd_data_12 mctdData12, mctd_data_13 mctdData13, mctd_data_14 mctdData14, mctd_data_15 mctdData15, mctd_data_16 mctdData16, mctd_data_17 mctdData17, mctd_data_18 mctdData18, mctd_data_19 mctdData19, mctd_data_20 mctdData20, mctd_cr_dt mctdCrDt, mctd_upd_dt mctdUpdDt from t_mktg_campaign_txn_data where mctd_txn_id = ?");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, TMktgCampaignTxnData.class);
        } catch (Exception e) {
            LOGGER.error("Error Load Txn Data list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    private void abFlowProcess(final MMktgCampaign camp, final TMktgCampaignTxn txn, final int cnt) throws SQLException, IOException, Exception {
        LOGGER.debug("Loading path. Camp Id: {}, Txn Id: {}, Path Id: {}, Flow No: {}", new Object[]{camp.getMcCampId(), txn.getMctTxnId(), txn.getMctPathId(), txn.getMctPathFlowNo()});
        List<MMktgCampaignPathFlow> pathFlowList = loadPathFlowListForAB(txn.getMctPathId());
        if (pathFlowList != null && !pathFlowList.isEmpty()) {
            MMktgCampaignPathFlow flow = pathFlowList.get(0);
            if (null == flow.getMcpfAction()) {
                LOGGER.warn("No action defined. Camp Id: {}, Txn Id: {}, Path Id: {}, Flow No: {}", new Object[]{camp.getMcCampId(), txn.getMctTxnId(), flow.getMcpfPathId(), flow.getMcpfFlowNo()});
            }

            List<TMktgCampaignTxnData> txnDataList = null;
            MMktgCampaignTemplate template = null;
            MMktgCampaignTemplate template2 = null;
            if (flow.getMcpfAction().matches("(M|S|N)")) {
                txnDataList = loadTxnDataList(txn.getMctTxnId());
                if (flow.getMcpfAction().matches("(S|N)")) {
                    template = new MMktgCampaignTemplate();
                    template.setMctBody(new StringBuilder(flow.getMcpfText()));
                }
            }

            if (txnDataList != null && !txnDataList.isEmpty()) {
                LOGGER.info("wen txnDataList is not null**");
                // LOGGER.info("flow.getMcpfTemplateId() is**" + flow.getMcpfTemplateId());
                //   LOGGER.info("flow.getMcpfTemplateId2() is**" + flow.getMcpfTemplateId2());
                if (null != flow.getMcpfTemplateId() && null != flow.getMcpfTemplateId2()) {
                    //   LOGGER.info("wen flow.getMcpfTemplateId() is not null**");
                    MMktgCampaignTemplate templateData = getCampaignTemplateForAB(flow.getMcpfTemplateId(), txn.getMctCampId());

                    if (templateData != null) {

                        template = templateData;
                        //LOGGER.info("wen templateData is not null**" + template.getMctCampBody().getCharacterStream());

                        template.setMctBody(new StringBuilder(IOUtils.toString(template.getMctCampBody().getCharacterStream())));
                    }

                    MMktgCampaignTemplate templateData2 = getCampaignTemplateForAB(flow.getMcpfTemplateId2(), txn.getMctCampId());
                    if (templateData2 != null) {
                        LOGGER.info("wen templateData is not null**");
                        template2 = templateData2;
                        template2.setMctBody(new StringBuilder(IOUtils.toString(template2.getMctCampBody().getCharacterStream())));
                    }
                }
                /*   LOGGER.info("after if of flow.getMcpfTemplateId() is not null");
                 if (null != flow.getMcpfTemplateId2()) {
                 LOGGER.info("wen flow.getMcpfTemplateId2() is not null**");
                 MMktgCampaignTemplate templateData = getCampaignTemplateForAB(flow.getMcpfTemplateId2(), txn.getMctCampId());
                 if (templateData != null) {
                 LOGGER.info("wen templateData is not null**");
                 template2 = templateData;
                 template2.setMctBody(new StringBuilder(IOUtils.toString(template2.getMctCampBody().getCharacterStream())));
                 }
                 }*/

                int rec_cnt = 0;
                if ("1".equals(flow.getMcpfSplitYn()) && cnt > 0) {
                    rec_cnt = Math.round(cnt * flow.getMcpfSplitPerc() / 100);
                }
                //  LOGGER.info("rec_cnt is**" + rec_cnt);
                if (null != template) {
                    LOGGER.info("Preparing for sendind campaign. Camp Id: {}, Txn Id: {}", new Object[]{camp.getMcCampId(), txn.getMctTxnId()});
                    List<Object[]> txnLog_params = new ArrayList<>();
                    List<Object[]> eventLog_params = new ArrayList<>();
                    int rows = 0;
                    for (TMktgCampaignTxnData txnData : txnDataList) {
                        rows++;
                        MailBodyBuilder mbb = new MailBodyBuilder(txnData);
                        mbb.setPropertyDelimit("@");
                        if (template2 == null || rows <= rec_cnt) {
                            mbb.setBody(new StringBuilder(template.getMctBody()));
                            mbb.setSubject(template.getMctSubject());
                            txnLog_params.add(new Object[]{txn.getMctCampId(), flow.getMcpfPathId(), flow.getMcpfFlowNo(), txn.getMctTxnId(), txnData.getMctdDataId(), flow.getMcpfTemplateId(), null, "E"});
                        } else {
                            mbb.setBody(new StringBuilder(template2.getMctBody()));
                            mbb.setSubject(template2.getMctSubject());
                            txnLog_params.add(new Object[]{txn.getMctCampId(), flow.getMcpfPathId(), flow.getMcpfFlowNo(), txn.getMctTxnId(), txnData.getMctdDataId(), flow.getMcpfTemplateId2(), null, "E"});
                        }
                        mbb.buildBody(PROPERTY_LIST);
                        if ("1".equals(camp.getMcAllowUnsubscribeYn())) {
                            String unsubUrl = ApplicationConstants.getCampaignBaseDomain(dataSource) + "/unsubscribe/" + txnData.getMctdKey();
                            String unsub = "<table cellpadding=\"0\" cellspacing=\"0\" style=\"vertical-align:top;\" width=\"100%\" border=\"0\"><tbody>"
                                    //+ "<tr><td style=\"word-wrap:break-word;font-size:0px;padding:0px 0px 0px 0px;padding-top:0px;padding-bottom:0px;\" align=\"center\"><div style=\"cursor:auto;color:#000000;font-family:Cabin, sans-serif;font-size:11px;line-height:22px;text-align:center;\"><p>Please enter your address and your contact here.</p><p>Explain why your subscribers are receiving this email.</p></div></td></tr>"
                                    + "<tr><td style=\"word-wrap:break-word;font-size:0px;padding:0px 0px 0px 0px;padding-top:0px;padding-bottom:0px;\" align=\"center\"><div style=\"cursor:auto;color:#000000;font-family:Cabin, sans-serif;font-size:11px;line-height:22px;text-align:center;\"><p style=\"font-size: 11px;\">If you don´t want to receive our emails, you can<span style=\"color: #000000;\"><strong>&nbsp;<a href=\"" + unsubUrl + "\" style=\"color: #000000;\">unsubscribe here.</a></strong></span></p></div></td></tr>"
                                    + "</tbody></table>";
                            mbb.getBody().append(unsub);
                        }

                        eventLog_params.add(new Object[]{txnData.getMctdDataId(), camp.getMcCampType(), txnData.getMctdContactId(), null, null, camp.getMcSenderId(), camp.getMcReplyTo(), mbb.getSubject(), mbb.getBody().toString(), "N"});
                        /*if (rows == 50) {
                         rows = 0;
                         insertEventLog(eventLog_params);
                         insertTxnLog(txnLog_params);
                         }*/
                        mbb = null;
                    }
                    if (rows > 0) {
                        insertEventLog(eventLog_params);
                        insertTxnLog(txnLog_params);
                    }
                }

            } else {
                LOGGER.debug("No records found for path. Camp Id: {}, Txn Id: {}, Flow No: {}", new Object[]{camp.getMcCampId(), txn.getMctTxnId(), flow.getMcpfFlowNo()});
            }

        }
        closeABCampaign(txn.getMctTxnId());
        // run.update(m$connection, "update t_mktg_campaign_txn set mct_txn_status = 'C', mct_wait_yn = '0', mct_next_run_date = null, mct_end_date = sysdate where mct_txn_id = ?", txn.getMctTxnId());
    }

    public void closeABCampaign(Long txnId) {
        LOGGER.info("in update Campaign Txn  ==> Campaign Id: {}, UserId: {}", new Object[]{txnId});
        Connection con = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String sql = "update t_mktg_campaign_txn set mct_txn_status = 'C', mct_wait_yn = '0', mct_next_run_date = null, mct_end_date = sysdate where mct_txn_id = ?";
            Object[] params = new Object[]{txnId};
            recCnt = executeUpdate(con, sql, params);
        } catch (Exception e) {
            LOGGER.error("Error while updating Txn campaign ", e);
        } finally {
            closeDbConn(con);
        }
    }

    public void insertEventLog(List<Object[]> eventParams) throws SQLException {
        String insQuery = "insert into t_mktg_event_log (mel_data_id, mel_type, mel_to, mel_cc, mel_bcc, mel_fm, mel_reply_to, mel_subject, mel_body, mel_unicode, mel_cr_dt) values (?,?,?,?,?,?,?,?,?,?, sysdate)";
        prs = con.prepareStatement(insQuery);
        int i = 0;
        for (Object[] params : eventParams) {
            int j = 0;
            prs.setLong(++j, Long.parseLong(params[0].toString()));
            prs.setString(++j, params[1].toString());
            prs.setString(++j, params[2].toString());
            prs.setString(++j, "");
            prs.setString(++j, "");
            prs.setString(++j, params[5].toString());
            prs.setString(++j, params[6].toString());
            prs.setString(++j, params[7].toString());
            prs.setString(++j, params[8].toString());
            prs.setString(++j, params[9].toString());
            prs.addBatch();
            ++i;

        }
        if (i > 0) {
            prs.executeBatch();
        }
        prs.close();

    }

    public void insertTxnLog(List<Object[]> txnParams) throws SQLException {
        String insQuery = "insert into t_mktg_campaign_txn_log (mctl_id, mctl_camp_id, mctl_path_id, mctl_flow_no, mctl_txn_id, mctl_data_id, mctl_template_id, mctl_url_key, mctl_log_type, mctl_cr_dt) values (s_mctl_id.nextval,?,?,?,?,?,?,?,?,sysdate)";
        prs = con.prepareStatement(insQuery);
        int i = 0;
        for (Object[] params : txnParams) {
            int j = 0;
            prs.setLong(++j, Long.parseLong(params[0].toString()));
            prs.setLong(++j, Long.parseLong(params[1].toString()));
            prs.setLong(++j, Long.parseLong(params[2].toString()));
            prs.setLong(++j, Long.parseLong(params[3].toString()));
            prs.setLong(++j, Long.parseLong(params[4].toString()));
            prs.setLong(++j, Long.parseLong(params[5].toString()));
            prs.setString(++j, "");
            prs.setString(++j, params[7].toString());
            prs.addBatch();
            ++i;

        }
        if (i > 0) {
            prs.executeBatch();
        }
        prs.close();

    }
    public static final List<MailBodyDataPropertyMap> PROPERTY_LIST = new LinkedList<MailBodyDataPropertyMap>() {
        private static final long serialVersionUID = 1L;

        {
            add(new MailBodyDataPropertyMap(MailBodyBuilder.PROPERTY_TOKEN, "mctdKey"));
            add(new MailBodyDataPropertyMap("MCD_CONTACT_ID", "mctdContactId"));
            add(new MailBodyDataPropertyMap("MCD_CONTACT_REF", "mctdContactRef"));
            add(new MailBodyDataPropertyMap("MCD_DATA_1", "mctdData1"));
            add(new MailBodyDataPropertyMap("MCD_DATA_2", "mctdData2"));
            add(new MailBodyDataPropertyMap("MCD_DATA_3", "mctdData3"));
            add(new MailBodyDataPropertyMap("MCD_DATA_4", "mctdData4"));
            add(new MailBodyDataPropertyMap("MCD_DATA_5", "mctdData5"));
            add(new MailBodyDataPropertyMap("MCD_DATA_6", "mctdData6"));
            add(new MailBodyDataPropertyMap("MCD_DATA_7", "mctdData7"));
            add(new MailBodyDataPropertyMap("MCD_DATA_8", "mctdData8"));
            add(new MailBodyDataPropertyMap("MCD_DATA_9", "mctdData9"));
            add(new MailBodyDataPropertyMap("MCD_DATA_10", "mctdData10"));
            add(new MailBodyDataPropertyMap("MCD_DATA_11", "mctdData11"));
            add(new MailBodyDataPropertyMap("MCD_DATA_12", "mctdData12"));
            add(new MailBodyDataPropertyMap("MCD_DATA_13", "mctdData13"));
            add(new MailBodyDataPropertyMap("MCD_DATA_14", "mctdData14"));
            add(new MailBodyDataPropertyMap("MCD_DATA_15", "mctdData15"));
            add(new MailBodyDataPropertyMap("MCD_DATA_16", "mctdData16"));
            add(new MailBodyDataPropertyMap("MCD_DATA_17", "mctdData17"));
            add(new MailBodyDataPropertyMap("MCD_DATA_18", "mctdData18"));
            add(new MailBodyDataPropertyMap("MCD_DATA_19", "mctdData19"));
            add(new MailBodyDataPropertyMap("MCD_DATA_20", "mctdData20"));
        }
    };

    public List<KeyValue> loadActiveCampaignChart() {
        LOGGER.info("CampaignSetupDao -  load Active Campaign Charts Enter");
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            //Object[] data = new Object[]{};
            query = new StringBuilder("SELECT SUM(DECODE(MC_CAMP_TYPE, 'E', 1, 0)) key,SUM(DECODE(MC_CAMP_TYPE, 'S', 1, 0)) value, SUM(DECODE(MC_CAMP_TYPE, 'P', 1, 0)) info FROM M_MKTG_CAMPAIGN WHERE MC_STATUS = 'R'");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), null, KeyValue.class
            );
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Campaign Clicked list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadCampaignStatusChart(DateRange dateRange) {
        LOGGER.info("CampaignSetupDao - Load Campaign Status Chart - Enter DateRange: {}", new Object[]{dateRange});
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT SUM(DECODE(MCT_TXN_STATUS, 'R', 1, 0)) key, SUM(DECODE(MCT_TXN_STATUS, 'C', 1, 0)) value, SUM(DECODE(MCT_TXN_STATUS, 'D', 1, 0)) info, SUM(DECODE(MCT_TXN_STATUS, 'W', 1, 0)) info1 FROM T_MKTG_CAMPAIGN_TXN ");
            query.append("WHERE TRUNC(MCT_START_DATE) ").append(dateRange.getRange());
            LOGGER.info("Query :: {} [{}]", query);
            list = executeList(con, query.toString(), new Object[]{}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while Load Campaign Status chart => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadCampaignTypeChart(DateRange dateRange) {
        LOGGER.info("CampaignSetupDao - Load Campaign Type Chart - Enter DateRange: {}", new Object[]{dateRange});
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT SUM(DECODE(MEL_TYPE, 'E', 1, 0)) key, SUM(DECODE(MEL_TYPE, 'S', 1, 0)) value, SUM(DECODE(MEL_TYPE, 'P', 1, 0)) info FROM T_MKTG_EVENT_LOG  ");
            query.append("WHERE TRUNC(MEL_CR_DT) ").append(dateRange.getRange());
            LOGGER.info("Query :: {} [{}]", query);
            list = executeList(con, query.toString(), new Object[]{}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while Load Campaign Type Chart => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadCampaignList() {
        LOGGER.info("CampaignSetupDao - Load Campaign List");
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT MC_CAMP_ID key, MC_CAMP_NAME value , MC_TARGET_AMT info FROM M_MKTG_CAMPAIGN WHERE MC_PREVIEW_YN = '1' AND (MC_STATUS = 'R' OR MC_STATUS = 'C' AND TRUNC(MC_END_DATE) >= TRUNC(SYSDATE) - 30)");
            LOGGER.info("Query :: {} [{}]", query);
            list = executeList(con, query.toString(), new Object[]{}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Campaign List => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadCampaignGraphData(Long mcCampId) {
        LOGGER.info("CampaignSetupDao Load Campaign Graph data CampaignId: {}", new Object[]{mcCampId});
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{mcCampId};
            query = new StringBuilder("SELECT * FROM (SELECT TRUNC(MCTL_CR_DT) key, SUM(DECODE(MCTL_LOG_TYPE, 'O', 1, 0)) value,SUM(DECODE(MCTL_LOG_TYPE, 'C', 1, 0)) info, SUM(DECODE(MCTL_LOG_TYPE, 'M', 1, 0)) info1 "
                    + " FROM T_MKTG_CAMPAIGN_TXN_DATA, T_MKTG_CAMPAIGN_TXN_LOG WHERE MCTD_DATA_ID = MCTL_DATA_ID AND MCTD_CAMP_ID = ? GROUP BY TRUNC(MCTL_CR_DT)  ORDER BY key DESC) WHERE ROWNUM <= 10");
            LOGGER.info("Query :: {} [{}]", query);
            list = executeList(con, query.toString(), data, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while Load Campaign Graph Data => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<? extends Object> loadCampaignDataCount(Long mcCampId) {
        LOGGER.info("Load campaign data count campId: {}",new Object[]{mcCampId});
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{mcCampId};
            query = new StringBuilder("SELECT SUM(DECODE(MCTL_LOG_TYPE, 'E', 1, 0)) key, SUM(DECODE(MCTL_LOG_TYPE, 'O', 1, 0)) value, SUM(DECODE(MCTL_LOG_TYPE, 'C', 1, 0)) info, "
                    + " SUM(DECODE(MCTL_LOG_TYPE, 'M', 1, 0)) info1, SUM(DECODE(MCTL_LOG_TYPE, 'M', TO_NUMBER(MCTL_FLEX_01), 0)) info2, "
                    + " COUNT(DISTINCT (CASE WHEN MCTL_LOG_TYPE = 'O' THEN MCTL_DATA_ID ELSE NULL END)) info3, COUNT(DISTINCT (CASE WHEN MCTL_LOG_TYPE = 'C' THEN MCTL_DATA_ID ELSE NULL END)) text "
                    + " FROM T_MKTG_CAMPAIGN_TXN_DATA, T_MKTG_CAMPAIGN_TXN_LOG "
                    + " WHERE MCTD_DATA_ID = MCTL_DATA_ID "
                    + " AND MCTD_CAMP_ID = ?");
            LOGGER.info("Query :: {} [{}]", query);
            list = executeList(con, query.toString(), data, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Campaign List => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<campaignChart> loadCampaignBouncedCount(final long McCampId) {
        LOGGER.debug("CampaignSetupDao - loadCampaignBouncedCount - Enter CampaignId: {}", new Object[]{McCampId});
        List<campaignChart> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT SENT, PENDING, BOUNCED, (CASE WHEN SENT <> 0 THEN ROUND((BOUNCED / SENT) * 100, 2) ELSE 0 END) BOUNCE_RATIO,OPENED,  "
                    + " (CASE WHEN SENT <> 0 THEN ROUND((OPENED / SENT) * 100, 2) ELSE 0 END) OPENED_RATIO,UNIQUE_OPENED, "
                    + " (CASE WHEN OPENED <> 0 THEN ROUND((UNIQUE_OPENED / OPENED) * 100,  2) ELSE 0 END) UNIQUE_OPEN_RATIO,"
                    + " CLICKED,(CASE WHEN SENT <> 0 THEN ROUND((CLICKED / SENT) * 100, 2) ELSE 0 END) CLICKED_RATIO,"
                    + " UNIQUE_CLICKED, (CASE WHEN CLICKED <> 0 THEN ROUND((UNIQUE_CLICKED / CLICKED) * 100,  2) ELSE 0 END) UNIQUE_CLICK_RATIO,"
                    + " COVERTED,(CASE WHEN SENT <> 0 THEN ROUND((COVERTED / SENT) * 100, 2) ELSE 0 END) CONVERTED_RATIO,  "
                    + " TAGET_AMT,  UN_SUB, (CASE WHEN SENT <> 0 THEN ROUND((UN_SUB / SENT) * 100, 2) ELSE 0 END) UB_SUB_RATIO, "
                    + " COVERTED_AMT,(CASE WHEN NVL(TAGET_AMT, 0) <> 0 THEN ROUND((COVERTED_AMT / TAGET_AMT) * 100,  2) ELSE 0 END) ACHIEVE_RATIO"
                    + " FROM (SELECT SUM(DECODE(MEL_STATUS, '1', 1, 0)) SENT,SUM(DECODE(MEL_STATUS, '0', 1, 0)) PENDING,        "
                    + " SUM(DECODE(MEL_STATUS, '2', 1, 0)) BOUNCED FROM T_MKTG_EVENT_LOG WHERE EXISTS(SELECT 1 FROM T_MKTG_CAMPAIGN_TXN_DATA"
                    + " WHERE MCTD_DATA_ID = MEL_DATA_ID AND MCTD_CAMP_ID = ?)),(SELECT SUM(DECODE(MCTL_LOG_TYPE, 'O', 1, 0)) OPENED,  "
                    + " SUM(DECODE(MCTL_LOG_TYPE, 'C', 1, 0)) CLICKED, SUM(DECODE(MCTL_LOG_TYPE, 'M', 1, 0)) COVERTED,"
                    + " COUNT(DISTINCT (CASE WHEN MCTL_LOG_TYPE = 'O' THEN MCTL_DATA_ID ELSE NULL END)) UNIQUE_OPENED,"
                    + " COUNT(DISTINCT (CASE WHEN MCTL_LOG_TYPE = 'C' THEN MCTL_DATA_ID ELSE NULL END)) UNIQUE_CLICKED,        "
                    + " SUM(DECODE(MCTL_LOG_TYPE, 'M', TO_NUMBER(MCTL_FLEX_01), 0)) COVERTED_AMT FROM T_MKTG_CAMPAIGN_TXN_DATA, T_MKTG_CAMPAIGN_TXN_LOG"
                    + " WHERE MCTD_DATA_ID = MCTL_DATA_ID AND MCTD_CAMP_ID = ?),(SELECT COUNT(1) UN_SUB FROM M_MKTG_UNSUBSCRIBE_LIST"
                    + " WHERE MUL_CAMP_ID = ?),(SELECT MC_TARGET_AMT TAGET_AMT FROM M_MKTG_CAMPAIGN WHERE MC_CAMP_ID = ?)";
            LOGGER.debug("Query=> {}", query);
            prs = con.prepareStatement(query);
            prs.setLong(1, McCampId);
            prs.setLong(2, McCampId);
            prs.setLong(3, McCampId);
            prs.setLong(4, McCampId);
            rs = prs.executeQuery();
            while (rs.next()) {
                campaignChart bean = new campaignChart();
                bean.setSent(rs.getString("SENT"));
                bean.setPending(rs.getString("PENDING"));
                bean.setBounced(rs.getString("BOUNCED"));
                bean.setBounceRatio(rs.getString("BOUNCE_RATIO"));
                bean.setOpened(rs.getString("OPENED"));
                bean.setOpenedRatio(rs.getString("OPENED_RATIO"));
                bean.setUniqueOpened(rs.getString("UNIQUE_OPENED"));
                bean.setUniqueOpenRatio(rs.getString("UNIQUE_OPEN_RATIO"));
                bean.setClicked(rs.getString("CLICKED"));
                bean.setClickedRatio(rs.getString("CLICKED_RATIO"));
                bean.setUniqueClicked(rs.getString("UNIQUE_CLICKED"));
                bean.setUniqueClickedRatio(rs.getString("UNIQUE_CLICK_RATIO"));
                bean.setConverted(rs.getString("COVERTED"));
                bean.setConvertedAmt(rs.getString("COVERTED_AMT"));
                bean.setConvertedRatio(rs.getString("CONVERTED_RATIO"));
                bean.setTargAmt(rs.getString("TAGET_AMT"));
                bean.setUnSub(rs.getString("UN_SUB"));
                bean.setUnSubRatio(rs.getString("UB_SUB_RATIO"));
                bean.setAcheiveRatio(rs.getString("ACHIEVE_RATIO"));
                result.add(bean);
            }
        } catch (Exception e) {
            LOGGER.error("Error in load Campaign Bounced Count => {}", e);
        } finally {

            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetupDao - loadCampaignBouncedCount - Exit");
        return result;
    }

    public List<ReportsBeanVO> loadUserReportsList(String userId) {
        LOGGER.debug("CampaignSetUpDao - Load User report List Enter UserId: {}", new Object[]{userId});
        List<ReportsBeanVO> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT URE_ID, URE_VIEW, (SELECT AC_DESC FROM M_APP_CODES WHERE AC_TYPE='ENQUIRY_VIEW' AND AC_CODE=URE_VIEW) URE_VIEW_NAME, "
                    + "(SELECT AC_MC_CODE FROM M_APP_CODES WHERE AC_TYPE='ENQUIRY_VIEW' AND AC_CODE=URE_VIEW) URE_TABLE_NAME, URE_NAME, URE_NARRATIVE, "
                    + "URE_CR_UID, PKG_REP_UTILITY.FN_GET_USER_NAME(URE_CR_UID) URE_CR_UNAME, URE_CR_DT, (SELECT COUNT(URP_URE_ID) FROM M_USER_REPORTS_PARAM WHERE URP_TYPE = 'F' AND URP_URE_ID = URE_ID) FILTER_CNT "
                    + "FROM M_USER_REPORTS WHERE URE_SEC_TYPE = 'U' AND URE_TYPE = 'M' "
                    + "AND INSTR(URE_USER_ROLES, ?) > 1 "
                    + "UNION "
                    + "SELECT URE_ID, URE_VIEW, (SELECT AC_DESC FROM M_APP_CODES WHERE AC_TYPE = 'ENQUIRY_VIEW' AND AC_CODE = URE_VIEW) URE_VIEW_NAME, "
                    + "(SELECT AC_MC_CODE FROM M_APP_CODES WHERE AC_TYPE = 'ENQUIRY_VIEW' AND AC_CODE = URE_VIEW) URE_TABLE_NAME, URE_NAME, URE_NARRATIVE, "
                    + "URE_CR_UID, PKG_REP_UTILITY.FN_GET_USER_NAME(URE_CR_UID) URE_CR_UNAME, URE_CR_DT, (SELECT COUNT(URP_URE_ID) FROM M_USER_REPORTS_PARAM WHERE URP_TYPE = 'F' AND URP_URE_ID = URE_ID) FILTER_CNT "
                    + "FROM M_USER_REPORTS, M_CRM_USER WHERE URE_SEC_TYPE = 'R' AND URE_TYPE = 'M' "
                    + "AND CU_USER_ID = ? AND INSTR(URE_USER_ROLES, CU_TEAM) > 1";
            LOGGER.debug("Query=> {}", query);
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
            LOGGER.error("Error in Load user Reports List => {}", e);
        } finally {

            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetUpDao - Load User report List Exit");
        return result;
    }

    public List<MMktgCampaignTxnData> downloadCampaignTxnData(MMktgCampaignTxnData txnData) {
        LOGGER.info("CampaignSetupDao - download Campaign Txn Data Enter-  CampId: {}, TxnId: {}", new Object[]{txnData.getMctdCampId(), txnData.getMctdTxnId()});
        List<MMktgCampaignTxnData> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{txnData.getMctdCampId(), txnData.getMctdTxnId()};
            query = new StringBuilder("SELECT MCTD_CONTACT_ID mctdContactId, MCTD_CONTACT_REF mctdContactRef, MCTD_SENT_YN mctdSentYn, "
                    + "MCTD_BOUNCED_YN mctdBouncedYn, MCTD_REPLIED_YN mctdRepliedYn, MCTD_CLICKED_YN mctdClickedYn, MCTD_CONVERTED_YN mctdConvertedYn, "
                    + "MCTD_DATA_1 mcData1, MCTD_DATA_2 mcData2, MCTD_DATA_3 mcData3, MCTD_DATA_4 mcData4, MCTD_DATA_5 mcData5, "
                    + "MCTD_DATA_6 mcData6, MCTD_DATA_7 mcData7, MCTD_DATA_8 mcData8, MCTD_DATA_9 mcData9, MCTD_DATA_10 mcData10, "
                    + "MCTD_DATA_11 mcData11, MCTD_DATA_12 mcData12, MCTD_DATA_13 mcData13, MCTD_DATA_14 mcData14, MCTD_DATA_15 mcData15, "
                    + "MCTD_DATA_16 mcData16, MCTD_DATA_17 mcData17, MCTD_DATA_18 mcData18, MCTD_DATA_19 mcData19, MCTD_DATA_20 mcData20 "
                    + "FROM T_MKTG_CAMPAIGN_TXN_DATA WHERE MCTD_CAMP_ID = ? AND MCTD_TXN_ID = ?");
            if (txnData.getMctdAction() != null) {
                if (StringUtils.isNoneBlank(txnData.getMctdAction())) {
                    query.append(" AND ");
                    switch (txnData.getMctdAction()) {
                        case "MB":
                            query.append("MCTD_BOUNCED_YN = 1");
                            break;
                        case "MO":
                            query.append("MCTD_OPENED_YN = 1");
                            break;
                        case "MR":
                            query.append("MCTD_REPLIED_YN = 1");
                            break;
                        case "MC":
                            query.append("MCTD_CLICKED_YN = 1");
                            break;
                        case "MCD":
                            query.append("MCTD_CONVERTED_YN = 1");
                            break;
                        case "MS":
                            query.append("MCTD_SENT_YN = 1");
                            break;
                    }
                }

            }
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignTxnData.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving download Campaign Txn Data list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<? extends Object> loadCampaignPerformance(Long mcCampId, Long mctTxnId) {
        LOGGER.debug("CampaignSetupDao - Load campaign Performance CampId: {}, TxnId: {}", new Object[]{mcCampId, mctTxnId});
        List<campaignChart> result = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT SENT, OPENED,(CASE WHEN SENT <> 0 THEN ROUND((OPENED / SENT) * 100, 2) ELSE 0 END) OPENED_RATIO, "
                    + " UNIQUE_OPENED,(CASE WHEN OPENED <> 0 THEN ROUND((UNIQUE_OPENED / OPENED) * 100,  2) ELSE 0 END) UNIQUE_OPEN_RATIO, "
                    + " CLICKED,(CASE WHEN SENT <> 0 THEN ROUND((CLICKED / SENT) * 100, 2) ELSE 0 END) CLICKED_RATIO, "
                    + " UNIQUE_CLICKED,(CASE WHEN CLICKED <> 0 THEN ROUND((UNIQUE_CLICKED / CLICKED) * 100,  2) ELSE 0 END) UNIQUE_CLICK_RATIO, "
                    + " COVERTED, (CASE WHEN SENT <> 0 THEN ROUND((COVERTED / SENT) * 100, 2) ELSE 0 END) CONVERTED_RATIO, COVERTED_AMT "
                    + " FROM (SELECT SUM(DECODE(MCTL_LOG_TYPE, 'E', 1, 0)) SENT, SUM(DECODE(MCTL_LOG_TYPE, 'O', 1, 0)) OPENED, "
                    + " SUM(DECODE(MCTL_LOG_TYPE, 'C', 1, 0)) CLICKED, SUM(DECODE(MCTL_LOG_TYPE, 'M', 1, 0)) COVERTED, COUNT(DISTINCT (CASE WHEN MCTL_LOG_TYPE = 'O' THEN MCTD_DATA_ID ELSE NULL END)) UNIQUE_OPENED, "
                    + " COUNT(DISTINCT (CASE WHEN MCTL_LOG_TYPE = 'C' THEN MCTD_DATA_ID ELSE NULL END)) UNIQUE_CLICKED, SUM(DECODE(MCTL_LOG_TYPE, 'M', TO_NUMBER(MCTL_FLEX_01), 0)) COVERTED_AMT "
                    + " FROM T_MKTG_CAMPAIGN_TXN_DATA, T_MKTG_CAMPAIGN_TXN_LOG "
                    + " WHERE MCTD_DATA_ID = MCTL_DATA_ID AND MCTD_CAMP_ID = ? AND MCTD_TXN_ID = ?)";
            LOGGER.debug("Query=> {}", query);
            prs = con.prepareStatement(query);
            prs.setLong(1, mcCampId);
            prs.setLong(2, mctTxnId);
            rs = prs.executeQuery();
            while (rs.next()) {
                campaignChart bean = new campaignChart();
                bean.setSent(rs.getString("SENT"));
                bean.setOpened(rs.getString("OPENED"));
                bean.setOpenedRatio(rs.getString("OPENED_RATIO"));
                bean.setUniqueOpened(rs.getString("UNIQUE_OPENED"));
                bean.setUniqueOpenRatio(rs.getString("UNIQUE_OPEN_RATIO"));
                bean.setClicked(rs.getString("CLICKED"));
                bean.setClickedRatio(rs.getString("CLICKED_RATIO"));
                bean.setUniqueClicked(rs.getString("UNIQUE_CLICKED"));
                bean.setUniqueClickedRatio(rs.getString("UNIQUE_CLICK_RATIO"));
                bean.setConverted(rs.getString("COVERTED"));
                bean.setConvertedRatio(rs.getString("CONVERTED_RATIO"));
                bean.setConvertedAmt(rs.getString("COVERTED_AMT"));
                result.add(bean);
            }
        } catch (Exception e) {
            LOGGER.error("Error in Load Campaign Performance => {}", e);
        } finally {

            closeDbConn(con);
        }
        LOGGER.debug("CampaignSetupDao - Load Campaign Performance Exit");
        return result;
    }

    public List<MMktgCampaignFormField> getFormFieldList(Long mcfFormId) {
        LOGGER.info("get form field List mcfFormId: {}", new Object[]{mcfFormId});
        List<MMktgCampaignFormField> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{mcfFormId};
            query = new StringBuilder("select MCFF_COL_NO mcffColNo, MCFF_FIELD_COL mcffFieldCol, MCFF_FIELD_NAME mcffFieldName,  MCFF_FIELD_HINT mcffFieldHint FROM M_MKTG_CAMPAIGN_FORM_FIELD where MCFF_FORM_ID = ?");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignFormField.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving  template list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public void UpdateDataMappings(Long mcCampId, String columns) {
        LOGGER.info("CampaignId: {}, Columns: {}", new Object[]{mcCampId, columns});
        try {
            con = getDBConnection(getDataSource());
            String query = "UPDATE M_MKTG_CAMPAIGN_DATA_MAP SET MCDM_DATA_NAME = ?, MCDM_UPD_DT = SYSDATE WHERE MCDM_DATA_COL = ? AND MCDM_CAMP_ID = ?";
            prs = con.prepareStatement(query);
            String result[] = columns.split("~");
            for (int i = 2; i < 22; i++) {
                if (result.length > i) {
                    prs.setString(1, result[i].trim());
                } else {
                    prs.setNull(1, Types.VARCHAR);
                }
                prs.setString(2, "MCD_DATA_" + (i - 1));
                prs.setLong(3, mcCampId);
                prs.addBatch();
            }
            prs.executeBatch();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error in Update Data Mappings => {}", e);
        } finally {
            closeDBComp(prs, null, con);
        }
        LOGGER.debug("CampaignSetupDao - Update Data Mappings - Exit");
    }

    public List<MMktgCampaignPathFlow> getCampaignFlowData(Long mcCampId) {
       LOGGER.info("get form field List mcfFormId: {}", new Object[]{mcCampId});
        List<MMktgCampaignPathFlow> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{mcCampId};
            query = new StringBuilder("SELECT MCP_PATH_ID mcpfPathId, MCP_PATH_NAME mcpfFlex01, PKG_REP_UTILITY.FN_GET_PARA_NAME('MK_ACTION', MCPF_ACTION) mcpfAction, MCPF_SPLIT_YN mcpfSplitYn, "
                    + "MCPF_SPLIT_PERC mcpfSplitPerc, (SELECT MCT_NAME FROM M_MKTG_CAMPAIGN_TEMPLATE WHERE MCT_TEMPLATE_ID = MCPF_TEMPLATE_ID) mcpfTemplateName,"
                    + "(SELECT MCT_NAME FROM M_MKTG_CAMPAIGN_TEMPLATE WHERE MCT_TEMPLATE_ID = MCPF_TEMPLATE_ID_2) mcpfTemplate2Name, "
                    + "MCPF_FLOW_NO mcpfFlowNo, MCPF_WAIT_FREQ mcpfWaitFreq FROM M_MKTG_CAMPAIGN_PATH, M_MKTG_CAMPAIGN_PATH_FLOW "
                    + "WHERE MCP_PATH_ID = MCPF_PATH_ID AND MCP_CAMP_ID = ? ORDER BY MCP_PATH_ID, MCPF_FLOW_NO");
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MMktgCampaignPathFlow.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving  template list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    //added for corporate

    public Long getSeqVal(Connection con, String query) throws SQLException {
        Long id = null;
        prs = con.prepareStatement(query);
        rs = prs.executeQuery();
        if (rs.next()) {
            id = rs.getLong(1);
        }
        DbUtils.closeQuietly(rs);
        DbUtils.closeQuietly(prs);

        return id;
    }

    public void copyCampaign(MMktgCampaign campaign, String userId) {
        String query = null;
        Connection con = null;
        String campType = null;
        try {
            con = getDBConnection(getDataSource());
            Long seq_id = getSeqVal(con, "SELECT S_MC_CAMP_ID.NEXTVAL FROM DUAL");
            query = " INSERT INTO M_MKTG_CAMPAIGN (MC_CAMP_ID, MC_CAMP_NAME,MC_CAMP_CODE,MC_DATA_SOURCE_TYPE,MC_DATA_SOURCE_VIEW,MC_CAMP_TYPE,MC_SENDER_ID,MC_REPLY_TO,"
                    + " MC_UNSUBSCRIBE_INCL_YN,MC_RECURRING_YN,MC_SCHEDULE_MODE,MC_SCHEDULE_VALUE,MC_SCHEDULE_TIME,MC_STATUS,MC_PREVIEW_YN,MC_ALLOW_UNSUBSCRIBE_YN,MC_VERIFY_CONVERSION_YN,MC_TARGET_AMT,"
                    + " MC_CR_UID,MC_CR_DT,MC_SELECTED_COLS) SELECT  ?, ?, ?, MC_DATA_SOURCE_TYPE,MC_DATA_SOURCE_VIEW,MC_CAMP_TYPE,MC_SENDER_ID,MC_REPLY_TO,MC_UNSUBSCRIBE_INCL_YN,"
                    + " MC_RECURRING_YN,MC_SCHEDULE_MODE,MC_SCHEDULE_VALUE,MC_SCHEDULE_TIME,"
                    + " 'P',MC_PREVIEW_YN,MC_ALLOW_UNSUBSCRIBE_YN,MC_VERIFY_CONVERSION_YN,MC_TARGET_AMT,"
                    + " ?,SYSDATE,MC_SELECTED_COLS FROM M_MKTG_CAMPAIGN WHERE MC_CAMP_ID = ?";
            prs = con.prepareStatement(query);
            prs.setLong(1, seq_id);
            prs.setString(2, campaign.getMcCampName());
            prs.setString(3, campaign.getMcCampCode());
            prs.setString(4, userId);
            prs.setLong(5, campaign.getMcCampId());
            prs.executeUpdate();
            DbUtils.closeQuietly(prs);
            String sqlCamp = "SELECT MC_DATA_SOURCE_TYPE FROM M_MKTG_CAMPAIGN WHERE MC_CAMP_ID = ?";
            prs = con.prepareStatement(sqlCamp);
            prs.setLong(1, campaign.getMcCampId());
            rs = prs.executeQuery();
            if (rs.next()) {
                campType = rs.getString("MC_DATA_SOURCE_TYPE");
            }
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(prs);
            if ("E".equals(campType)) {
                String queryData = "INSERT INTO W_MKTG_CAMPAIGN_DATA(MCD_CONTACT_ID, MCD_CONTACT_REF, MCD_DATA_1, MCD_DATA_2, MCD_DATA_3, MCD_DATA_4, MCD_DATA_5, MCD_DATA_6, MCD_DATA_7, MCD_DATA_8, MCD_DATA_9, MCD_DATA_10, MCD_DATA_11, MCD_DATA_12, MCD_DATA_13, MCD_DATA_14, MCD_DATA_15, MCD_DATA_16, MCD_DATA_17, MCD_DATA_18, MCD_DATA_19, MCD_DATA_20, MCD_CAMP_ID) "
                        + " SELECT MCD_CONTACT_ID, MCD_CONTACT_REF, MCD_DATA_1, MCD_DATA_2, MCD_DATA_3, MCD_DATA_4, MCD_DATA_5, MCD_DATA_6, MCD_DATA_7, MCD_DATA_8, MCD_DATA_9, MCD_DATA_10, MCD_DATA_11, MCD_DATA_12, MCD_DATA_13, MCD_DATA_14, MCD_DATA_15, MCD_DATA_16, MCD_DATA_17, MCD_DATA_18, MCD_DATA_19, MCD_DATA_20, ? FROM W_MKTG_CAMPAIGN_DATA WHERE MCD_CAMP_ID = ?";
                prs = con.prepareStatement(queryData);
                prs.setLong(1, seq_id);
                prs.setLong(2, campaign.getMcCampId());
                prs.executeUpdate();
                DbUtils.closeQuietly(prs);
            } else {
                String sqlData = "DELETE FROM M_MKTG_CAMPAIGN_DATA_MAP WHERE MCDM_CAMP_ID = ?";
                prs = con.prepareStatement(sqlData);
                prs.setLong(1, seq_id);
                prs.executeUpdate();
                DbUtils.closeQuietly(prs);
                String queryData = "INSERT INTO M_MKTG_CAMPAIGN_DATA_MAP(MCDM_CAMP_ID, MCDM_DATA_COL, MCDM_DATA_TYPE, MCDM_DATA_NAME, MCDM_SAMPLE_DATA, MCDM_CR_UID, MCDM_CR_DT) "
                        + " SELECT ?, MCDM_DATA_COL, MCDM_DATA_TYPE, MCDM_DATA_NAME, MCDM_SAMPLE_DATA, ?, SYSDATE FROM M_MKTG_CAMPAIGN_DATA_MAP WHERE MCDM_CAMP_ID = ?";
                prs = con.prepareStatement(queryData);
                prs.setLong(1, seq_id);
                prs.setString(2, userId);
                prs.setLong(3, campaign.getMcCampId());
                prs.executeUpdate();
                DbUtils.closeQuietly(prs);
            }
            String query1 = "INSERT INTO M_MKTG_CAMPAIGN_DATA_PARAM(MDP_CAMP_ID, MDP_TYPE, MDP_SR_NO, MDP_DATA_TYPE, MDP_COLUMN, MDP_OPERATOR, MDP_VALUE_FM,"
                    + " MDP_VALUE_TO, MDP_CONDITION, MDP_ORDER, MDP_CR_UID, MDP_CR_DT) "
                    + " SELECT ?, MDP_TYPE,MDP_SR_NO,MDP_DATA_TYPE, MDP_COLUMN,MDP_OPERATOR,MDP_VALUE_FM,MDP_VALUE_TO,"
                    + " MDP_CONDITION,MDP_ORDER,?,SYSDATE FROM M_MKTG_CAMPAIGN_DATA_PARAM WHERE MDP_CAMP_ID = ?";
            prs = con.prepareStatement(query1);
            prs.setLong(1, seq_id);
            prs.setString(2, userId);
            prs.setLong(3, campaign.getMcCampId());
            prs.executeUpdate();
            DbUtils.closeQuietly(prs);

            List<MMktgCampaignForms> forms = getFormByCampId(campaign.getMcCampId(), con);
            if (forms != null && !forms.isEmpty()) {
                for (MMktgCampaignForms formFields : forms) {
                    copyForm(formFields, campaign.getMcCampId(), seq_id, userId, con);
                }
            }
            List<MMktgCampaignTemplate> template = getTemplateByCampId(campaign.getMcCampId(), con);
            if (template != null && !template.isEmpty()) {
                for (MMktgCampaignTemplate tempLink : template) {
                    copyTemplate(tempLink, campaign.getMcCampId(), seq_id, userId, con);
                }
            }
            List<MMktgCampaignFilter> filters = getFilterByCampId(campaign.getMcCampId(), con);
            if (filters != null && !filters.isEmpty()) {
                for (MMktgCampaignFilter filterparam : filters) {
                    copyFilter(filterparam, campaign.getMcCampId(), seq_id, userId, con);
                }
            }
            List<MMktgCampaignPath> path = getPathByCampId(campaign.getMcCampId(), con);
            if (path != null && !path.isEmpty()) {
                for (MMktgCampaignPath pathFlow : path) {
                    copyPath(pathFlow, campaign.getMcCampId(), seq_id, userId, con);
                }
            }
            campaign.setMcCampId(seq_id);
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(prs);
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Copy Campaign data", e);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {

                }
            }
        } finally {
            query = null;
            closeDbConn(con);
        }
    }

    public List<KeyValue> loadCampaignTypeList(String flex1) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{"%" + flex1 + "%", "%" + flex1 + "%"};
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());//Agents/Brokers/Banks
            query = new StringBuilder("SELECT mc_camp_id key, mc_camp_name value  FROM M_MKTG_CAMPAIGN WHERE (upper(MC_CAMP_ID) like upper(?) OR upper(MC_CAMP_NAME) like upper(?))");

            LOGGER.info("Query :: {} []", params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while load Campaign List Task List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    private List<MMktgCampaignFilter> getFilterByCampId(Long mcCampId, Connection con) throws Exception {
        LOGGER.info("get form field List mcfFormId: {}", new Object[]{mcCampId});
        List<MMktgCampaignFilter> list = null;
        StringBuilder query = null;
        Object[] data = new Object[]{mcCampId};
        query = new StringBuilder("SELECT MCF_FILTER_ID mcfFilterId FROM M_MKTG_CAMPAIGN_FILTER WHERE MCF_CAMP_ID = ?");
        LOGGER.info("Query :: {}", new Object[]{query});
        list = executeList(con, query.toString(), data, MMktgCampaignFilter.class);
        return list;
    }

    private void copyFilter(final MMktgCampaignFilter filterparam, final Long mcCampId, final Long seqId, final String userId, final Connection con) throws SQLException {
        LOGGER.debug("CampaignSetupDao - Insert Copy Filter - Enter CampId: {}", new Object[]{mcCampId});
        Long seq_filter_id = getSeqVal(con, "SELECT S_MCF_FILTER_ID.NEXTVAL FROM DUAL");
        String query = "INSERT INTO M_MKTG_CAMPAIGN_FILTER(MCF_FILTER_ID, MCF_CAMP_ID, MCF_FILTER_NAME, MCF_CR_UID, MCF_CR_DT) "
                + "SELECT ?, ?, MCF_FILTER_NAME, ?, SYSDATE FROM M_MKTG_CAMPAIGN_FILTER WHERE MCF_FILTER_ID = ?";
        prs = con.prepareStatement(query);
        prs.setLong(1, seq_filter_id);
        prs.setLong(2, seqId);
        prs.setString(3, userId);
        prs.setLong(4, filterparam.getMcfFilterId());
        prs.executeUpdate();
        DbUtils.closeQuietly(prs);
        String query1 = "INSERT INTO M_MKTG_CAMPAIGN_FILTER_PARAM(MCFP_FILTER_ID, MCFP_SNO, MCFP_DATA_COL, MCFP_DATA_TYPE, MCFP_OPERATOR, MCFP_VALUE_FM, MCFP_VALUE_TO, MCFP_CONDITION, MCFP_CR_UID, MCFP_CR_DT) "
                + "SELECT ?, MCFP_SNO, MCFP_DATA_COL, MCFP_DATA_TYPE, MCFP_OPERATOR, MCFP_VALUE_FM, MCFP_VALUE_TO, MCFP_CONDITION, ?, SYSDATE FROM M_MKTG_CAMPAIGN_FILTER_PARAM WHERE MCFP_FILTER_ID = ?";
        prs = con.prepareStatement(query1);
        prs.setLong(1, seq_filter_id);
        prs.setString(2, userId);
        prs.setLong(3, filterparam.getMcfFilterId());
        prs.executeUpdate();
        DbUtils.closeQuietly(prs);
    }

    private List<MMktgCampaignPath> getPathByCampId(Long mcCampId, Connection con) throws Exception {
        LOGGER.info("get form Path List {}", new Object[]{mcCampId});
        List<MMktgCampaignPath> list = null;
        StringBuilder query = null;
        Object[] data = new Object[]{mcCampId};
        query = new StringBuilder("SELECT MCP_PATH_ID mcpPathId FROM M_MKTG_CAMPAIGN_PATH WHERE MCP_CAMP_ID = ?");
        LOGGER.info("Query :: {}", new Object[]{query});
        list = executeList(con, query.toString(), data, MMktgCampaignPath.class);
        return list;
    }

    private void copyPath(MMktgCampaignPath pathFlow, Long mcCampId, Long seq_id, String userId, Connection con) throws SQLException {
        LOGGER.debug("CampaignSetupDao - Insert Copy Paths - Enter CampId: {}", new Object[]{mcCampId});
        Long seq_path_id = getSeqVal(con, "SELECT S_MCP_PATH_ID.NEXTVAL FROM DUAL");
        String query = "INSERT INTO M_MKTG_CAMPAIGN_PATH(MCP_PATH_ID, MCP_PATH_NAME, MCP_CAMP_ID, MCP_FILTER_ID, MCP_CR_UID, MCP_CR_DT) "
                + "SELECT ?, MCP_PATH_NAME, ?, MCP_FILTER_ID, ?, SYSDATE FROM M_MKTG_CAMPAIGN_PATH WHERE MCP_PATH_ID = ?";
        prs = con.prepareStatement(query);
        prs.setLong(1, seq_path_id);
        prs.setLong(2, seq_id);
        prs.setString(3, userId);
        prs.setLong(4, pathFlow.getMcpPathId());
        prs.executeUpdate();
        DbUtils.closeQuietly(prs);
        String query1 = "INSERT INTO M_MKTG_CAMPAIGN_PATH_FLOW(MCPF_FLOW_NO, MCPF_PATH_ID, MCPF_ACTION, MCPF_WAIT_FREQ, MCPF_TEMPLATE_ID, MCPF_SPLIT_YN, MCPF_SPLIT_PERC, MCPF_TEMPLATE_ID_2, "
                + "MCPF_UNICODE, MCPF_TEXT, MCPF_CLICK_URL_KEY, MCPF_CR_DT, MCPF_CR_UID) "
                + "SELECT MCPF_FLOW_NO, ?, MCPF_ACTION, MCPF_WAIT_FREQ, MCPF_TEMPLATE_ID, MCPF_SPLIT_YN, MCPF_SPLIT_PERC, MCPF_TEMPLATE_ID_2, "
                + "MCPF_UNICODE, MCPF_TEXT, MCPF_CLICK_URL_KEY, SYSDATE, ? FROM M_MKTG_CAMPAIGN_PATH_FLOW WHERE MCPF_PATH_ID = ?";
        prs = con.prepareStatement(query1);
        prs.setLong(1, seq_path_id);
        prs.setString(2, userId);
        prs.setLong(3, pathFlow.getMcpPathId());
        prs.executeUpdate();
        DbUtils.closeQuietly(prs);

    }

    private List<MMktgCampaignForms> getFormByCampId(Long mcCampId, Connection con) throws Exception {
        LOGGER.info("get form field List mcfFormId: {}", new Object[]{mcCampId});
        List<MMktgCampaignForms> list = null;
        StringBuilder query = null;
        Object[] data = new Object[]{mcCampId};
        query = new StringBuilder("SELECT MCF_FORM_ID mcfFormId FROM M_MKTG_CAMPAIGN_FORMS WHERE MCF_CAMP_ID = ?");
        LOGGER.info("Query :: {}", new Object[]{query});
        list = executeList(con, query.toString(), data, MMktgCampaignForms.class);
        return list;
    }

    private void copyForm(MMktgCampaignForms formFields, Long mcCampId, Long seq_id, String userId, Connection con) throws SQLException {
        LOGGER.debug("CampaignSetupDao - Insert Copy Form  - Enter CampId: {}", new Object[]{mcCampId});
        Long seq_form_id = getSeqVal(con, "SELECT S_MCF_FORM_ID.NEXTVAL FROM DUAL");
        String query3 = "INSERT INTO M_MKTG_CAMPAIGN_FORMS(MCF_FORM_ID, MCF_CAMP_ID, MCF_NAME, MCF_FORM_TITLE, MCF_VALIDITY, MCF_BANNER_URL, MCF_FORM_HEADER,"
                + " MCF_FORM_FOOTER, MCF_FORM_COLOR, MCF_CR_UID, MCF_CR_DT) "
                + " SELECT  ?, ?, MCF_NAME, MCF_FORM_TITLE, MCF_VALIDITY, MCF_BANNER_URL, MCF_FORM_HEADER,"
                + " MCF_FORM_FOOTER, MCF_FORM_COLOR,?,SYSDATE FROM M_MKTG_CAMPAIGN_FORMS WHERE MCF_FORM_ID = ?";
        prs = con.prepareStatement(query3);
        prs.setLong(1, seq_form_id);
        prs.setLong(2, seq_id);
        prs.setString(3, userId);
        prs.setLong(4, formFields.getMcfFormId());
        prs.executeUpdate();
        DbUtils.closeQuietly(prs);
        String sql = "DELETE FROM M_MKTG_CAMPAIGN_FORM_FIELD WHERE MCFF_FORM_ID = ?";
        prs = con.prepareStatement(sql);
        prs.setLong(1, seq_form_id);
        prs.executeUpdate();
        DbUtils.closeQuietly(prs);
        String query4 = "INSERT INTO M_MKTG_CAMPAIGN_FORM_FIELD(MCFF_FORM_ID, MCFF_COL_NO, MCFF_FIELD_COL, MCFF_FIELD_NAME, MCFF_FIELD_HINT, MCFF_DATA_TYPE, MCFF_FIELD_TYPE, MCFF_MAX_LENGTH, MCFF_MANDETORY_YN, "
                + "MCFF_DATA_SQL, MCFF_VALIDATION, MCFF_VALIDATION_MSG, MCFF_CR_UID, MCFF_CR_DT) "
                + " SELECT ?, MCFF_COL_NO, MCFF_FIELD_COL, MCFF_FIELD_NAME, MCFF_FIELD_HINT, MCFF_DATA_TYPE, MCFF_FIELD_TYPE, MCFF_MAX_LENGTH, MCFF_MANDETORY_YN, "
                + "MCFF_DATA_SQL, MCFF_VALIDATION, MCFF_VALIDATION_MSG, ?, SYSDATE FROM m_MKTG_CAMPAIGN_FORM_FIELD WHERE MCFF_FORM_ID = ?";
        prs = con.prepareStatement(query4);
        prs.setLong(1, seq_form_id);
        prs.setString(2, userId);
        prs.setLong(3, formFields.getMcfFormId());
        prs.executeUpdate();
        DbUtils.closeQuietly(prs);

    }

    private List<MMktgCampaignTemplate> getTemplateByCampId(Long mcCampId, Connection con) throws Exception {
        LOGGER.info("get template By Camp id: {}", new Object[]{mcCampId});
        List<MMktgCampaignTemplate> list = null;
        StringBuilder query = null;
        Object[] data = new Object[]{mcCampId};
        query = new StringBuilder("SELECT MCT_TEMPLATE_ID mctTemplateId FROM M_MKTG_CAMPAIGN_TEMPLATE WHERE MCT_CAMP_ID = ?");
        LOGGER.info("Query :: {}", new Object[]{query});
        list = executeList(con, query.toString(), data, MMktgCampaignTemplate.class);
        return list;
    }

    private void copyTemplate(MMktgCampaignTemplate template, Long mcCampId, Long seq_id, String userId, Connection con) throws SQLException {
        LOGGER.debug("CampaignSetupDao - Insert Copy template - Enter CampId: {}", new Object[]{mcCampId});
        Long seq_temp_id = getSeqVal(con, "SELECT  S_MCT_TEMPLATE_ID.NEXTVAL FROM DUAL");
        String query = "INSERT INTO m_MKTG_CAMPAIGN_TEMPLATE(MCT_TEMPLATE_ID, MCT_CAMP_ID, MCT_NAME, MCT_TYPE, MCT_SUBJECT, MCT_BODY, MCT_UNICODE, MCT_TEXT, MCT_CR_UID, MCT_CR_DT, "
                + "MCT_CAMP_BODY) "
                + " SELECT ?, ?, MCT_NAME, MCT_TYPE, MCT_SUBJECT, MCT_BODY, MCT_UNICODE, MCT_TEXT, ?, SYSDATE, "
                + "MCT_CAMP_BODY FROM m_MKTG_CAMPAIGN_TEMPLATE WHERE MCT_TEMPLATE_ID = ?";
        prs = con.prepareStatement(query);
        prs.setLong(1, seq_temp_id);
        prs.setLong(2, seq_id);
        prs.setString(3, userId);
        prs.setLong(4, template.getMctTemplateId());
        prs.executeUpdate();
        DbUtils.closeQuietly(prs);
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

}
