/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.dao;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.vo.BulkSMSEmail;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.vo.MailCampaign;
import qa.com.qic.common.vo.MailTxn;
import qa.com.qic.email.core.MailBodyBuilder;
import qa.com.qic.email.core.MailBodyUrl;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class EmailMarketingDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(EmailMarketingDAO.class);

    private final String dataSource;

    public EmailMarketingDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<MailCampaign> loadCampaignList(final MailCampaign campaign) {
        List<MailCampaign> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[0];
            query = new StringBuilder("SELECT MC_ID mcId, MC_CAMP_NAME mcCampName, MC_CAMP_DESC mcCampDesc, MC_FROM mcFrom, "
                    + "MC_SUBJECT mcSubject, MC_SCHEDULE_ON mcScheduleOn, MC_BODY_TYPE mcBodyType, "
                    + "MC_CR_UID mcCrUid, MC_CR_DT mcCrDt, MC_CAMP_STATUS mcCampStatus, MC_EXPIRE_ON mcExpireOn FROM T_MAIL_CAMPAIGN ");
            if (campaign != null && campaign.getMcId() != null) {
                query.append("WHERE MC_ID = ?");
                data = new Object[]{campaign.getMcId()};
            }
            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query.toString(), data, MailCampaign.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving campaign list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public MailCampaign loadCampaignBody(final MailCampaign campaign) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            ps = con.prepareStatement("SELECT MC_CAMP_NAME, MC_CAMP_BODY FROM T_MAIL_CAMPAIGN WHERE MC_ID = ?");
            LOGGER.info("Param :: {}", new Object[]{campaign.getMcId()});
            ps.setBigDecimal(1, campaign.getMcId());
            rs = ps.executeQuery();
            String campName = "";
            if(rs.next()) {
                campName = rs.getString("MC_CAMP_NAME");
                java.sql.Clob clob = rs.getClob("MC_CAMP_BODY");
                InputStream in = clob.getAsciiStream();
                BufferedInputStream bis = new BufferedInputStream(in);
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                int result = bis.read();
                while (result != -1) {
                    buf.write((byte) result);
                    result = bis.read();
                }
                campaign.setMcCampBody(new StringBuilder(buf.toString("UTF-8")));
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("SELECT SUM(NVL(TPI_PREM_FC, 0) - NVL(TPI_POL_DISC_FC, 0) - NVL(TPI_CVR_DISC_FC, 0) + NVL(TPI_POL_LOAD_FC, 0) + NVL(TPI_CVR_LOAD_FC, 0) + NVL(TPI_FEE_FC, 0)) PREMIUM FROM T_POL_INFO WHERE TPI_CAMPAIGN LIKE '%utm_campaign="+campName+"%' AND TPI_SOURCE = 'utm_source=crm'");
            rs = ps.executeQuery();
            if(rs.next()) {
                campaign.setDecimalData1(rs.getBigDecimal("PREMIUM"));
            }
            List<MailBodyUrl> list = executeList(con, "SELECT MCU_TOKEN token, MCU_VISITED visited FROM T_MAIL_CAMPAIGN_URL WHERE MCU_MC_ID = ?", new Object[]{campaign.getMcId()}, MailBodyUrl.class);
            MailBodyBuilder mbb = new MailBodyBuilder(null);
            mbb.setBody(campaign.getMcCampBody());
            mbb.applyBodyWithRatting(list);
            campaign.setMcCampBody(mbb.getParsedBody());
        } catch (Exception e) {
            LOGGER.error("Error while retreiving campaign body => {}", e);
        } finally {
            closeDBComp(ps, rs, con);
        }
        return campaign;
    }

    public List<MailCampaign> loadCampaignChartData(final MailCampaign model) {
        List<MailCampaign> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{model.getMcId()};
            /*
             intData1 - Total
             intData2 - Opened
             intData3 - Visited

             intData4 - Not Send
             intData5 - Sent
             intData6 - Error
             intData7 - Bounced
             */
            query = new StringBuilder("SELECT COUNT(MT_ID) intData1, SUM(DECODE(MT_OPENED, 0, NULL, 1)) intData2, SUM(DECODE(MT_VISITED, 0, NULL, 1)) intData3, "
                    + "SUM(DECODE(MT_STATUS, 'I', 1, 0)) intData4, SUM(DECODE(MT_STATUS, 'S', 1, 0)) intData5, "
                    + "SUM(DECODE(MT_STATUS, 'E', 1, 0)) intData6, SUM(DECODE(MT_STATUS, 'B', 1, 0)) intData7 "
                    + "FROM T_MAIL_TXN WHERE MT_MC_ID = ? GROUP BY MT_MC_ID");
            LOGGER.info("Query :: {} [{}]", new Object[]{query});
            list = executeList(con, query.toString(), data, MailCampaign.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving campaign chart => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> getKeyValue(String condtnString, String[] additionalParamKey, String[] additionalParamVal, String company) {
        List<KeyValue> vehicleMake = null;
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT AC_CODE,AC_DESC FROM  M_APP_CODES WHERE AC_TYPE = ?"
                    + " AND SYSDATE BETWEEN AC_EFF_FM_DT AND AC_EFF_TO_DT";
            if (additionalParamKey != null && additionalParamVal != null) {
                query += " AND " + additionalParamKey[0] + " = ? ";
            }
            if (ApplicationConstants.M_APP_CODE_RELATION.equalsIgnoreCase(condtnString)) {
                query += " ORDER BY TO_NUMBER(NVL(AC_MC_CODE,99))";
            } else {
                query += " ORDER BY AC_DESC";
            }
            LOGGER.debug(" Query :: {}", query);
            prs = con.prepareStatement(query);
            int j = 0;
            prs.setString(++j, condtnString);
            if (additionalParamKey != null && additionalParamVal != null) {
                prs.setString(++j, additionalParamVal[0]);
            }
            rs = prs.executeQuery();
            vehicleMake = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString("AC_CODE"));
                keyValue.setValue(rs.getString("AC_DESC"));
                vehicleMake.add(keyValue);
            }
            LOGGER.info("Retreiving Property Information for condition {}", condtnString);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving key value for {}", condtnString);
            LOGGER.debug("Error while retreiving key value ", e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        return vehicleMake;
    }

    public List<KeyValue> getTemplateList(String string) {
        List<KeyValue> template = null;
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT SMT_TEMP_CODE, SMT_TEMP_CODE||' - '||SMT_TEMP_DESC SMT_TEMP_DESC, SMT_EMAIL_FROM  FROM M_SMS_EMAIL_TEMPLATE ORDER BY ROWID";
            LOGGER.debug(" Query :: {}", query);
            prs = con.prepareStatement(query);
            rs = prs.executeQuery();
            template = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString("SMT_TEMP_CODE"));
                keyValue.setValue(rs.getString("SMT_TEMP_DESC"));
                template.add(keyValue);
            }
            LOGGER.info("Retreiving Property Information for template ");
        } catch (Exception e) {
            LOGGER.error("Error while retreiving properties for {}");
            LOGGER.debug("Error while retreiving properties ", e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        return template;
    }

    public List<KeyValue> loadStatusEmailList() {
        List<KeyValue> statusEmailList = new ArrayList<>();
        statusEmailList.add(new KeyValue("E", "Error"));
        statusEmailList.add(new KeyValue("O", "Open"));
        statusEmailList.add(new KeyValue("C", "Clicked"));
        statusEmailList.add(new KeyValue("N", "No Action"));
        statusEmailList.add(new KeyValue("OC", "Open & Clicked"));
        statusEmailList.add(new KeyValue("S", "Send"));
        return statusEmailList;
    }

    public List<MailTxn> loadCampaignTxnData(final MailTxn mailtx) {
        List<MailTxn> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT MT_TO mtTo, MT_OPENED mtOpened, MT_VISITED mtVisisted FROM T_MAIL_TXN WHERE MT_MC_ID = ? ");
            switch (mailtx.getMtStatus()) {
                case "E":
                    query.append("AND MT_STATUS IN ('E', 'B', 'D') ");
                    break;
                case "O":
                    query.append("AND MT_OPENED > 0 ");
                    break;
                case "C":
                    query.append("AND MT_VISITED > 0 ");
                    break;
                case "N":
                    query.append("AND MT_OPENED <= 0 AND MT_VISITED <= 0 ");
                    break;
                case "OC":
                    query.append("AND MT_OPENED > 0 AND MT_VISITED > 0 ");
                    break;
                case "S":
                    query.append("AND MT_STATUS = 'S' ");
                    break;
            }
            LOGGER.info("Query :: {} [{}]", new Object[]{query, mailtx.getMtMcId(), mailtx.getMtStatus()});
            Object[] data = new Object[]{mailtx.getMtMcId()};
            list = executeList(con, query.toString(), data, MailTxn.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving mailtxn chart => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public MailTxn loadCampaignTxnData(final String id) {
        MailTxn list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT SE_EMAIL_SMS mtTo, SE_COL_1 mtData1, SE_COL_2 mtData2, SE_COL_3 mtData3, SE_COL_4 mtData4, SE_COL_5 mtData5, SE_COL_6 mtData6, SE_COL_7 mtData7, SE_COL_8 mtData8, SE_COL_9 mtData9, SE_COL_10 mtData10 FROM T_BULK_SMS_EMAIL WHERE ROWID = ?");
            LOGGER.info("Query :: {} [{}]", new Object[]{query, id});
            Object[] data = new Object[]{id};
            list = (MailTxn) executeQuery(con, query.toString(), data, MailTxn.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving mailtxn => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public String sendBulkSMSEmail(final BulkSMSEmail model) {
        String msg = null;
        CallableStatement callStmt = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            PreparedStatement ps = con.prepareStatement("INSERT INTO T_MAIL_CAMPAIGN "
                    + "(MC_ID, MC_CAMP_NAME, MC_CAMP_DESC, MC_FROM, MC_SUBJECT, MC_BODY, MC_CAMP_BODY, MC_CR_UID, MC_SCHEDULE_ON, MC_CR_DT, MC_EXPIRE_ON) "
                    + "VALUES (S_MC_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, NVL(?, SYSDATE), SYSDATE, ?)", new String[]{"MC_ID"});
            int i = 0;
            ps.setString(++i, model.getMcCampName());
            ps.setString(++i, model.getMcCampDesc());
            ps.setString(++i, model.getMcFrom());
            ps.setString(++i, model.getMcSubject());
            ps.setCharacterStream(++i, new StringReader(model.getMcBody().toString()), model.getMcBody().length());
            MailBodyBuilder mbb = new MailBodyBuilder(null);
            mbb.setBody(model.getMcBody());
            mbb.setBaseURL("http://172.20.101.56:8084/crm");
            List<MailBodyUrl> urls = mbb.parseBody();
            ps.setCharacterStream(++i, new StringReader(mbb.getParsedBody().toString()), model.getMcBody().length());
            ps.setString(++i, model.getMcCrUid());
            if(model.getMcScheduleOn() == null) {
                ps.setNull(++i, Types.DATE);
            } else {
                ps.setTimestamp(++i, new java.sql.Timestamp(model.getMcScheduleOn().getTime()));
            }
            if(model.getMcExpireOn() == null) {
                ps.setNull(++i, Types.DATE);
            } else {
                ps.setTimestamp(++i, new java.sql.Timestamp(model.getMcExpireOn().getTime()));
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            Long mcId = 0L;
            if (rs.next()) {
                mcId = rs.getLong(1);
            }
            ps.close();
            if(urls != null && !urls.isEmpty()) {
                ps = con.prepareStatement("INSERT INTO T_MAIL_CAMPAIGN_URL (MCU_MC_ID, MCU_SR_NO, MCU_URL, MCU_TOKEN) VALUES (?, ?, ?, ?)");
                for(MailBodyUrl url : urls) {
                    ps.setLong(1, mcId);
                    ps.setInt(2, url.getSrNo());
                    ps.setString(3, url.getUrl());
                    ps.setString(4, url.getToken());
                    ps.addBatch();
                }
                ps.executeBatch();
                ps.close();
            }
            /*PreparedStatement ps = con.prepareStatement("INSERT INTO T_MAIL_CAMPAIGN (MC_ID, MC_CAMP_NAME, MC_FROM, MC_SUBJECT, MC_BODY, MC_CR_DT) SELECT S_MC_ID.NEXTVAL, SMT_TEMP_DESC, SMT_EMAIL_FROM, SMT_EMAIL_SUBJECT, SMT_EMAIL_BODY, SYSDATE FROM M_SMS_EMAIL_TEMPLATE WHERE SMT_TEMP_CODE = ?", new String[]{"MC_ID"});
             //((OraclePreparedStatement)ps).setExecuteBatch(100);
             ps.setString(1, tempCode);
             ps.executeUpdate();
             //((OraclePreparedStatement)ps).sendBatch();
             ResultSet rs = ps.getGeneratedKeys();
             Long mcId = 0L;
             if(rs.next()) {
             mcId = rs.getLong("MC_ID");
             }
             ps.close();
             Statement st = con.createStatement();
             String query = "INSERT INTO T_MAIL_TXN "
             + "(MT_ID, MT_TOKEN, MT_MC_ID, MT_TO, MT_DATA_1, MT_DATA_2, MT_DATA_3, MT_DATA_4, MT_DATA_5, MT_DATA_6, MT_DATA_7, MT_DATA_8, MT_DATA_9, MT_DATA_10, MT_CR_DT) "
             + "SELECT S_MT_ID.NEXTVAL, S_MT_ID.CURRVAL, " + mcId + ", SE_EMAIL_SMS, SE_COL_1, SE_COL_2, SE_COL_3, SE_COL_4, SE_COL_5, SE_COL_6, SE_COL_7, SE_COL_8, SE_COL_9, SE_COL_10, SYSDATE FROM T_BULK_SMS_EMAIL";
             st.executeUpdate(query);
             st.close();
             st = con.createStatement();
             st.executeUpdate("DELETE FROM T_BULK_SMS_EMAIL");
             st.close();
             con.commit();*/
            //PR_MAIL_CAMPAIGN(P_SMT_TEMP_CODE IN VARCHAR2 , P_ERROR OUT VARCHAR2)
            callStmt = con.prepareCall("{call PR_MAIL_CAMPAIGN(?, ?)}");
            callStmt.setLong(1, mcId);
            callStmt.registerOutParameter(2, Types.VARCHAR);
            callStmt.executeQuery();
            msg = callStmt.getString(2);
            LOGGER.info("PR_MAIL_CAMPAIGN => Error: {}", msg);
            /*logger.debug("PKG_SMS_EMAIL.PR_SEND_BULK_SMS_EMAIL Package Called...");
             logger.info(sendOption + "::" + tempCode);
             callStmt = con.prepareCall("{call PKG_SMS_EMAIL.PR_SEND_BULK_SMS_EMAIL(?,?,?)}");
             callStmt.setString(1, sendOption);
             callStmt.setString(2, tempCode);
             callStmt.registerOutParameter(3, Types.VARCHAR);
             callStmt.executeQuery();
             msg = callStmt.getString(3);
             logger.info(" PR_SEND_BULK_SMS_EMAIL ");*/
        } catch (Exception ex) {
            LOGGER.error(" Error on PKG_SMS_EMAIL.PR_SEND_BULK_SMS_EMAIL", ex);
        } finally {
            closeDBCallSt(callStmt, con);
        }
        return msg;
    }

    public String getDataSource() {
        return dataSource;
    }
}
