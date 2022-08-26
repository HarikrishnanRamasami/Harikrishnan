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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class CommonDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(CommonDAO.class);

    private final String dataSource;
    private Connection con;

    public static final String MAIL_HOST = "smtp.qichoappl.com";
    public static final String MAIL_PORT = "587";
    public static final String MAIL_USER_ID = "rioadmin";
    public static final String MAIL_PASSWORD = "8htdQxS4DCZBz78flDnz";
    public static final String MAIL_FROM = "AnoudAdmin<anoudadmin@qicgroup.com.qa>";

    public CommonDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public CommonDAO(Connection con) {
        this.dataSource = null;
        this.con = con;
    }

    public enum ActivityTypes {

        SMS,
        EMAIL,
        ACTIVITY
    }

    /**
     *
     * @param model
     * @param type
     * @return error
     * @throws Exception
     */
    public String callCRMPackage(final Activity model, ActivityTypes type) throws Exception {
        CallableStatement cs = null;
        String sql = null, errorMsg = null;
        try {
            if (getDataSource() != null) {
                con = getDBConnection(getDataSource());
                con.setAutoCommit(true);
            }
            int indx = 0;
            switch (type) {
                case SMS:
                    /*PKG_CRM.PR_SEND_SMS(
                     P_ID VARCHAR2,
                     P_CRM_ID VARCHAR2,
                     P_LANG VARCHAR2,
                     P_MOBILE_NO NUMBER,
                     P_MESSAGE VARCHAR2,
                     P_USER_ID VARCHAR2)*/
                	//commented for corporate
                    //sql = "{call PKG_CRM.PR_SEND_SMS(?,?,?,?,?,?)}";
                    sql = "{call PKG_CRM.PR_SEND_SMS(?,?,?,?,?)}";
                    cs = con.prepareCall(sql);
                  //commented for corporate
                    //LOGGER.info("Input for PKG_CRM.PR_SEND_SMS ==> {}:{}:{}:{}", new Object[]{model.getCivilId(), model.getCrmId(), model.getLanguage(), model.getTo(), model.getUserId()});
                    LOGGER.info("Input for PKG_CRM.PR_SEND_SMS ==> {}:{}:{}:{}", new Object[]{model.getCivilId(), model.getLanguage(), model.getTo(), model.getUserId()});
                    cs.setString(++indx, model.getCivilId());
                  //commented for corporate
                    //cs.setString(++indx, model.getCrmId());
                    cs.setString(++indx, model.getLanguage());
                    cs.setLong(++indx, Long.parseLong(model.getTo()));
                    cs.setString(++indx, model.getMessage());
                    cs.setString(++indx, model.getUserId());
                    cs.execute();
                    break;
                case EMAIL:
                    /*PKG_CRM.PR_SEND_EMAIL(
                     P_ID VARCHAR2,
                     P_CRM_ID VARCHAR2,
                     P_TO VARCHAR2,
                     P_CC VARCHAR2 DEFAULT NULL,
                     P_SUBJECT VARCHAR2,
                     P_MESSAGE CLOB,
                     P_USER_ID VARCHAR2)*/
                    sql = "{call PKG_CRM.PR_SEND_EMAIL(?,?,?,?,?,?,?)}";
                    cs = con.prepareCall(sql);
                  //commented for corporate
                   // LOGGER.info("Input for PKG_CRM.PR_SEND_EMAIL ==> {}:{}:{}:{}:{}", new Object[]{model.getCivilId(), model.getCrmId(), model.getTo(), model.getSubject(), model.getUserId()});
                    LOGGER.info("Input for PKG_CRM.PR_SEND_EMAIL ==> {}:{}:{}:{}:{}", new Object[]{model.getCivilId(), model.getTemplate(), model.getTo(), model.getSubject(), model.getUserId()});
                   //  model.setCrmId("CLM_WF_003_S");

                    cs.setString(++indx, model.getCivilId());
                  //commented for corporate
                    //cs.setString(++indx, model.getCrmId());
                    cs.setString(++indx, model.getTemplate());
                    cs.setString(++indx, model.getTo());
                    cs.setString(++indx, model.getCc());
                    cs.setString(++indx, model.getSubject());
                    cs.setString(++indx, model.getMessage());
                    cs.setString(++indx, model.getUserId());
                    cs.execute();
                    break;
                default:
                    throw new Exception("Invalid call");
            }
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
                if (getDataSource() != null) {
                    if (con != null) {
                        con.close();
                    }
                }
            } catch (Exception e) {
            }
        }
        return errorMsg;
    }

    public List<KeyValue> loadCurrencyList() {
        List<KeyValue> list = null;
        try {
            if (getDataSource() != null) {
                con = getDBConnection(getDataSource());
            }
            StringBuilder query = new StringBuilder("SELECT CURR_CODE key, CURR_NAME value FROM M_CURRENCY WHERE CURR_FRZ_FLAG = '0' ");
            query.append("ORDER BY CURR_NAME");
            LOGGER.info("Query :: {} ", query);
            list = executeList(con, query.toString(), new Object[]{}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving loadCurrencyListr => {}", e);
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

    private static final String QRY_USER_LIST = "SELECT CU_USER_ID key, CU_USER_NAME value FROM M_CRM_USER WHERE CU_LOCK_YN = '0' AND CU_AGENT_TYPE <> 4 ORDER BY CU_USER_NAME";
    private static final String QRY_APPL_USER_LIST = "SELECT USER_ID key, USER_NAME value FROM TABLE(FN_GET_CRM_AGENTS(?)) ORDER BY USER_NAME";
    /**
     * Load all CRM users
     *
     * @return list of users
     * @author ravindar.singh
     */
    public List<KeyValue> loadCrmAgentList(final String userId) {
        List<KeyValue> list = null;
        StringBuilder query = null;
        try {
            if (getDataSource() != null) {
                con = getDBConnection(getDataSource());
            }
            if(userId == null) {
                list = executeList(con, QRY_USER_LIST, null, KeyValue.class);
            } else {
                Object[] date = new Object[]{userId};
                list = executeList(con, QRY_APPL_USER_LIST, date, KeyValue.class);
            }
        } catch (Exception e) {
            LOGGER.error("Error while retreiving agent list => {}", e);
        } finally {
            query = null;
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

    public int updateEmailOpenCount(final String token, final Boolean isOpenMail) {
        int count = 0;
        try {
            if (getDataSource() != null) {
                con = getDBConnection(getDataSource());
            }
            StringBuilder query = null;
            if (isOpenMail) {
                query = new StringBuilder("UPDATE T_MAIL_TXN SET MT_OPENED = NVL(MT_OPENED, 0) + 1 WHERE MT_TOKEN = ?");
            } else {
                query = new StringBuilder("UPDATE T_MAIL_TXN SET MT_VISITED = NVL(MT_VISITED, 0) + 1 WHERE MT_TOKEN = ?");
            }
            LOGGER.info("Query :: {} [{}] ", new Object[]{query, token});
            count = executeUpdate(con, query.toString(), new Object[]{token});
        } catch (Exception e) {
            LOGGER.error("Error while updating email open/visit count => {}", e);
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
        return count;
    }

    public String getEmailUrl(final String token, final String urlToken) {
        String url = null;
        try {
            if (getDataSource() != null) {
                con = getDBConnection(getDataSource());
            }
            StringBuilder query = null;
            PreparedStatement ps = con.prepareStatement("SELECT MT_MC_ID FROM T_MAIL_TXN WHERE MT_TOKEN = ?");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            Long mcId = 0L;
            if (rs.next()) {
                mcId = rs.getLong(1);
            }
            ps.close();
            Object[] param = new Object[]{urlToken};
            ps = con.prepareStatement("UPDATE T_MAIL_CAMPAIGN_URL SET MCU_VISITED = NVL(MCU_VISITED, 0) + 1 WHERE MCU_TOKEN = ? " + (mcId != 0 ? "AND MCU_MC_ID = ?" : ""));
            ps.setString(1, urlToken);
            if(mcId != 0) {
                ps.setLong(2, mcId);
                param = new Object[]{urlToken, mcId};
            }
            ps.executeUpdate();
            ps.close();
            query = new StringBuilder("SELECT MCU_URL FROM T_MAIL_CAMPAIGN_URL WHERE MCU_TOKEN = ? " + (mcId != 0 ? "AND MCU_MC_ID = ?" : ""));
            LOGGER.info("Query :: {} [{}:{}] ", new Object[]{query, token, urlToken});
            ResultSetHandler<String> rsh = new ResultSetHandler() {
                @Override
                public String handle(ResultSet rs) throws SQLException {
                    if(rs.next()) {
                        return rs.getString(1);
                    }
                    return null;
                }
            };
            QueryRunner run = new QueryRunner();
            url = run.query(con, query.toString(), rsh, param);
        } catch (Exception e) {
            LOGGER.error("Error while updating email url => {}", e);
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
        return url;
    }

    public void sendEMail(final String from, final String password, String to, String sub, String msg) {

        //Get properties object
         Properties props = new Properties();
         props.setProperty("mail.smtp.host", MAIL_HOST);
         // props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         props.setProperty("mail.smtp.socketFactory.fallback", "false");
         props.setProperty("mail.smtp.port", MAIL_PORT);
         props.put("mail.smtp.auth", "true");
         props.put("mail.debug", "true");
         props.put("mail.transport.protocol", "smtp");

         //get Session
         Session session = Session.getInstance(props, new javax.mail.Authenticator() {
             @Override
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(MAIL_USER_ID, MAIL_PASSWORD);
             }
         });
         //compose message
         try {

             MimeMessage message = new MimeMessage(session);

             InternetAddress fromAddress = null;
             try {
                 fromAddress = new InternetAddress(MAIL_FROM);
             } catch (AddressException e) {
             }
             message.setFrom(fromAddress);
             String delimit = ";";
             if (to.contains(",")) {
                 delimit = ",";
             }
             String tos[] = to.split(delimit);
             List<InternetAddress> toList = new ArrayList<>();
             for (String s : tos) {
                 toList.add(new InternetAddress(s));
             }
             message.addRecipients(Message.RecipientType.TO, toList.toArray(new InternetAddress[tos.length]));
             message.setSubject(sub);
             message.setText(msg, "UTF-8", "html");
             //send message
             Transport.send(message);
             LOGGER.info("message sent successfully to ", to);
         } catch (Exception e) {
             LOGGER.error("", e);
             throw new RuntimeException(e);
         }
     }

    public String getDataSource() {
        return dataSource;
    }
}
