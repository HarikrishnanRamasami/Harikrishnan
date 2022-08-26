/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.crm.restapi.resources;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import org.apache.logging.log4j.Logger;

import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.vo.CrmCallLog;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author sutharsan.g
 */
public class CrmDAO extends DatabaseDAO {

    private static final Logger logger = LogUtil.getLogger(CrmDAO.class);

    private Connection con = null;
    private PreparedStatement prs = null;
    private ResultSet rs = null;
    private String dataSource;

    CrmDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /*public int saveTasks(CrmTasks crmTasks, UserInfo userInfo) {
     Connection con = null;
     UserInfo user = null;
     PreparedStatement ps = null;
     int i = 0, recCnt = 0;
     String sql = null;
     try {
     con = getDBConnection(getDataSource());
     sql = "INSERT INTO T_CRM_TASKS (CT_ID, CT_REF_ID, CT_REF_NO, CT_MODULE, CT_PRIORITY, CT_CATG, CT_SUB_CATG, CT_STATUS, "
     + "CT_FLEX_01, CT_SUBJECT, CT_MESSAGE, CT_DUE_DATE, CT_ASSIGNED_TO, CT_ASSIGNED_DT, "
     + "CT_REMIND_BEFORE, CT_CR_UID, CT_CR_DT) VALUES "
     + "(S_CT_ID.NEXTVAL, :ctRefId, :ctRefNo, :ctModule, :ctPriority, :ctCatg, :ctSubCatg, :ctStatus, :ctFlex01, :ctSubject, "
     + ":ctMessage, TO_DATE(:ctDueDate, 'DD/MM/YYYY'), :ctAssignedTo, SYSDATE, :ctRemindBefore, :ctCrUid, SYSDATE)";
     Object[] params = new Object[]{crmTasks.getCtRefId(), crmTasks.getCtRefNo(), crmTasks.getCtModule(), crmTasks.getCtPriority(), crmTasks.getCtCatg(), crmTasks.getCtSubCatg(), crmTasks.getCtStatus(),
     crmTasks.getCtFlex01(), crmTasks.getCtSubject(), crmTasks.getCtMessage(), crmTasks.getCtDueDate(), crmTasks.getCtAssignedTo(), crmTasks.getCtRemindBefore(), userInfo.getUserId()};
     recCnt = executeUpdate(con, sql, params);
     con.commit();
     } catch (Exception e) {
     logger.error("Error while task entry => {}", e);
     } finally {
     closeDBComp(ps, null, con);
     }
     return recCnt;

     }

     public int saveCustomer(Customer customer, UserInfo userInfo) {
     Connection con = null;
     PreparedStatement ps = null;
     int i = 0, recCnt = 0;
     String sql = null;
     try {
     con = getDBConnection(getDataSource());
     sql = "INSERT INTO M_Insured_Info (INF_NAME, INF_CIVIL_ID, INF_SOURCE, INF_SOURCE_DETAILS, INF_PO_BOX,"
     + "INF_COUNTRY, INF_NATIONALITY, INF_CITY, INF_GENDER, INF_AGE, INF_OCCUPATION, INF_WORK_PLACE, INF_MEM_PROMO_CATG, INF_MOBILE_NO, INF_TEL_NO, INF_EMAIL_ID, INF_FAX_NO,"
     + "INF_BIRTH_DT, INF_WEDDING_DT, INF_LICENSE_EXP_DT, INF_ID_EXP_DT,INF_MOBILE_NO_1,INF_MOBILE_NO_2,INF_CR_UID,INF_CR_DT) VALUES(:name, :civilId, :source, :sourceDetails, :poBox,"
     + ":country, :nationality, :city, :gender, :age, :occupation, :workPlace, :custType, :mobileNo, :telNo, :emailId, :faxNo, :birthDt, :weddingDt, :licenseExpDt, :idExpDt,:mobileNo1,"
     + ":mobileNo2,:crUid,SYSDATE)";
     ps = con.prepareStatement(sql);
     ps.setString(++i, customer.getName());
     ps.setString(++i, customer.getCivilId());
     ps.setString(++i, customer.getSource());
     ps.setString(++i, customer.getSourceDetails());
     ps.setString(++i, customer.getPoBox());
     ps.setString(++i, customer.getCountry());
     ps.setString(++i, customer.getNationality());
     ps.setString(++i, customer.getCity());
     ps.setString(++i, customer.getGender());
     ps.setString(++i, customer.getAge());
     ps.setString(++i, customer.getOccupation());
     ps.setString(++i, customer.getWorkPlace());
     ps.setString(++i, customer.getCustType());
     ps.setString(++i, customer.getMobileNo());
     ps.setString(++i, customer.getTelNo());
     ps.setString(++i, customer.getEmailId());
     ps.setString(++i, customer.getFaxNo());
     ps.setString(++i, customer.getBirthDt());
     ps.setString(++i, customer.getWeddingDt());
     ps.setString(++i, customer.getLicenseExpDt());
     ps.setString(++i, customer.getIdExpDt());
     ps.setString(++i, customer.getMobileNo1());
     ps.setString(++i, customer.getMobileNo2());
     ps.setString(++i, userInfo.getUserId());
     recCnt = ps.executeUpdate();
     con.commit();
     } catch (Exception e) {
     logger.error("Error while Customer entry => {}", e);
     } finally {
     closeDBComp(ps, null, con);
     }
     return recCnt;

     }

     public List<CrmLeads> loadLeadData(String leadId) {
     List<CrmLeads> result = null;
     Connection con = null;
     try {
     con = getDBConnection(getDataSource());
     String sql = "SELECT CL_ID clId, CL_REF_ID clRefId, CL_REF_NO clRefNo, CL_NAME clName, CL_GENDER clGender, CL_NATIONALITY clNationality, CL_PO_BOX clPoBox, "
     + "CL_CITY clCity, CL_COUNTRY clCountry, CL_TEL_NO clTelNo, CL_MOBILE_NO clMobileNo, CL_FAX_NO clFaxNo, CL_EMAIL_ID clEmailId, "
     + "CL_SOURCE clSource, CL_WORK_PLACE clWorkPlace, CL_OCCUPATION clOccupation, "
     + "TO_CHAR(CL_BIRTH_DT, 'DD/MM/YYYY') clBirthDt, CL_REMARKS clRemarks, "
     + "CL_ASSIGNED_TO clAssignedTo, CL_STATUS clStatus "
     + "FROM T_CRM_LEADS where CL_ID = ?";
     Object[] params = new Object[]{leadId};
     result = executeList(con, sql, params, CrmLeads.class);
     } catch (Exception e) {
     logger.error("Error while retreiving customer details => {}", e);
     } finally {
     closeDbConn(con);
     }
     return result;
     }

     public int saveCallLog(CrmCallLog crmCallLog, UserInfo userInfo) {
     Connection con = null;
     PreparedStatement ps = null;
     int i = 0, recCnt = 0;
     String sql = null;
     try {
     con = getDBConnection(getDataSource());
     sql = "INSERT INTO T_INSURED_LOG (IL_ID, IL_TYPE, IL_CIVIL_ID, IL_LOG_DETAILS, IL_CR_UID, IL_CR_DT) "
     + "VALUES (S_IL_ID.NEXTVAL, ?, ?, ?, ?, TO_DATE(?, 'YYYYMMDD HH24:MI:SS'))";
     Object[] params = new Object[]{crmCallLog.getCclCrmType(), crmCallLog.getCclCivilId(), crmCallLog.getCclRemarks(), userInfo.getUserId()};
     recCnt = executeUpdate(con, sql, params);
     con.commit();
     } catch (Exception e) {
     logger.error("Error while CallLog entry => {}", e);
     } finally {
     closeDBComp(ps, null, con);
     }
     return recCnt;
     }

     /*  public int saveLeads(CrmLeads crmLeads, UserInfo userInfo) {
     Connection con = null;
     PreparedStatement ps = null;
     int i = 0, recCnt = 0;
     String sql = null;
     try {
     con = getDBConnection(getDataSource());
     sql = "INSERT INTO T_CRM_LEADS (CL_ID, CL_REF_ID, CL_REF_NO, CL_NAME, CL_GENDER, CL_NATIONALITY, CL_PO_BOX, CL_CITY, CL_COUNTRY,"
     + "CL_TEL_NO, CL_MOBILE_NO, CL_FAX_NO, CL_EMAIL_ID, CL_SOURCE, CL_WORK_PLACE, CL_OCCUPATION, "
     + "CL_BIRTH_DT, CL_REMARKS, CL_CR_UID, CL_CR_DT, CL_ASSIGNED_TO, CL_ASSIGNED_DT, CL_STATUS) VALUES"
     + "(S_CL_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,TO_DATE(?, 'DD/MM/YYYY'), ?, ?, SYSDATE, ?, SYSDATE, ?)";
     Object[] params = new Object[]{crmLeads.getClRefId(), crmLeads.getClRefNo(), crmLeads.getClName(), crmLeads.getClGender(), crmLeads.getClNationality(), crmLeads.getClPoBox(), crmLeads.getClCity(), crmLeads.getClCountry(), crmLeads.getClTelNo(),
     crmLeads.getClMobileNo(), crmLeads.getClFaxNo(), crmLeads.getClEmailId(), crmLeads.getClSource(), crmLeads.getClWorkPlace(), crmLeads.getClOccupation(), crmLeads.getClBirthDt(), crmLeads.getClRemarks(),userInfo.getUserId(),
     crmLeads.getClAssignedTo(), "P"};
     recCnt = executeUpdate(con, sql, params);
     con.commit();
     } catch (Exception e) {
     logger.error("Error while Creating Leads entry => {}", e);
     } finally {
     closeDBComp(ps, null, con);
     }
     return recCnt;
     }
     */
    /**
     * CRM API login
     *
     * @param username
     * @param password
     * @return user details
     * @throws Exception
     */
    public UserInfo login(final String username, final String password) throws Exception {
        UserInfo user = null;
        CallableStatement cs = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT CU_USER_ID userId, CU_LDAP_ID userLdapId, CU_USER_NAME userName, CU_TEL_NO userTelNo, NVL(CU_AMDIN_YN, 0) userAdminYn, CU_PASSWORD password FROM M_CRM_USER WHERE CU_LOCK_YN = '0' AND LOWER(CU_LDAP_ID) = ? ";
            logger.info("Query :: {} [{}]", query, username);
            user = (UserInfo) executeQuery(con, query, new Object[]{username}, UserInfo.class);
            if (user == null || "".equals(user.getUserId())) {
                throw new Exception("Invalid user");
            } else {
                /*"DECLARE\n" +
                "   P_USER       M_CRM_USER.CU_USER_ID%TYPE := NULL;\n" +
                "   P_PASSWORD   M_CRM_USER.CU_PASSWORD%TYPE := NULL;\n" +
                "BEGIN\n" +
                "   P_USER := ?;\n" +
                "   P_PASSWORD := ?;\n" +
                "   PKG_USER_PASSWORD.PR_ENCRYPT (P_PASSWORD);\n" +
                "   UPDATE M_CRM_USER\n" +
                "      SET CU_PASSWORD = P_PASSWORD, CU_PWD_CHANGE_DATE = SYSDATE\n" +
                "    WHERE CU_USER_ID = P_USER;\n" +
                "END;"*/
                query = "DECLARE\n" +
                "   P_PASSWORD   M_CRM_USER.CU_PASSWORD%TYPE;\n" +
                "BEGIN\n" +
                "   P_PASSWORD := ?;\n" +
                "   BEGIN\n" +
                "      IF P_PASSWORD IS NOT NULL THEN\n" +
                "         PKG_USER_PASSWORD.PR_DECRYPT (P_PASSWORD);\n" +
                "      END IF;\n" +
                "   EXCEPTION\n" +
                "      WHEN OTHERS THEN\n" +
                "         P_PASSWORD := NULL;\n" +
                "   END;\n" +
                "   -- DBMS_OUTPUT.put_line ('Decrypted password is ' || P_PASSWORD);\n" +
                "   ? := P_PASSWORD;\n" +
                "END;";
                cs = con.prepareCall(query);
                cs.setString(1, user.getPassword());
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.executeQuery();
                user.setPassword(cs.getString(2));
            }
            logger.debug("API loggedin user name: {}", user.getUserName());
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        } finally {
            closeDBCallSt(cs, con);
        }
        return user;
    }

    public int updateAmeyoFeedBack(CrmCallLog callLog) throws Exception {
        PreparedStatement ps = null;
        int recCnt = 0;
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            sql = "UPDATE T_CRM_CALL_LOG SET CCL_FB_LANGUAGE = ?, CCL_FB_OPTION_1 = ?, CCL_FB_OPTION_2 = ?, CCL_FB_OPTION_3 = ? WHERE CCL_CALL_NO = ? AND CCL_FILE_PATH = ?";
            Object[] params = new Object[]{callLog.getCclFbLanguage(), callLog.getCclFbOption1(), callLog.getCclFbOption2(), callLog.getCclFbOption3(), callLog.getCclCallNo(), callLog.getCclFilePath()};
            recCnt = executeUpdate(con, sql, params);
            con.commit();
        } catch (Exception e) {
            logger.error("Error while Update ameyo feedback entry => {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;

    }
}
