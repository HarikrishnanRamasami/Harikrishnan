/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.dbutils.QueryExecutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.Customer;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.anoud.vo.Policy;
import qa.com.qic.anoud.vo.TRiskMedical;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants.CallType;
import qa.com.qic.common.util.TypeConstants.SystemDispositionType;
import qa.com.qic.common.vo.CrmCallLog;
import qa.com.qic.common.vo.CrmTasks;
import qa.com.qic.common.vo.CrmUser;
import qa.com.qic.common.vo.FlexBean;
import qa.com.qic.common.vo.LoginHistory;
import qa.com.qic.common.vo.MAppCodes;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author Ravindar Singh T
 * <a href="mailto:ravindar.singh@qicgroup.com.qa">ravindar.singh@qicgroup.com.qa</a>
 */
public class AnoudDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(AnoudDAO.class);

    private final String dataSource;
    private transient Map<String, Object> session;

    public AnoudDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<KeyValue> loadMailDetails(String emailId) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        // emailId = "ravindar.singh@qicgroup.com.qa";
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{emailId};
            query = new StringBuilder("SELECT MC_CAMP_NAME key, MC_CAMP_DESC value, MT_OPENED info, MT_VISITED info1 FROM T_MAIL_TXN, T_MAIL_CAMPAIGN WHERE "
                    + "MC_ID = MT_MC_ID AND MT_TO = ? order by MT_CR_DT desc");
            LOGGER.info("Query :: {} [{}]", query, emailId);
            list = executeList(con, query.toString(), date, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Mail Data ", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public enum DashboardFigures {

        POLICIES,
        QUOTES,
        CLAIMS,
        TOTAL_AND_LOSSRATIO,
        PRODUCTIVITY,
        ACTIVITIES,
        ELIGIBLE_OFFERS,
        MEMBER_CLAIMS,
        MEMBER_PRE_APPROVALS,
        MEMBER_ENQUIRIES,
        MEMBER_VISITS

    }

    //commented for corporate
    // public List<Customer> loadCutomerDetials(final String crmId, final String searchBy, final String searchValue) {
    public List<Customer> loadCutomerDetials(final String searchBy, final String searchValue) {
        List<Customer> list = null;
        Connection con = null;
        String sql = "SELECT INF_NAME as name, INF_START_YEAR as startYear, INF_CIVIL_ID as civilId, INF_PREFER_LANG preferLang, "
                //+ "(CASE WHEN INF_SOURCE <> 'O' OR (INF_SOURCE = 'O' AND INF_SOURCE_DETAILS IS NULL) THEN PKG_REP_UTILITY.FN_GET_PARA_NAME('CUST_SOURCE', INF_SOURCE) ELSE INF_SOURCE_DETAILS END) as source, "
                + "PKG_REP_UTILITY.FN_GET_PARA_NAME('APP_PORTAL', INF_SOURCE) as source, INF_SOURCE_DETAILS as sourceDetails,INF_SOURCE as sourceType, "
                + "PKG_REP_UTILITY.FN_GET_AC_NAME('PROMO_CATG', INF_MEM_PROMO_CATG) as custTypeDesc, INF_PO_BOX as poBox, "
                + "PKG_REP_UTILITY.FN_GET_AC_NAME('STATE', INF_CITY) as city, "
                + "PKG_REP_UTILITY.FN_GET_AC_NAME('COUNTRY', INF_COUNTRY) as country, "
                + "PKG_REP_UTILITY.FN_GET_AC_NAME('NATIONALITY', INF_NATIONALITY) as nationality, "
                + "PKG_REP_UTILITY.FN_GET_PARA_NAME('GENDER', INF_GENDER) as gender, INF_AGE as age, "
                + "PKG_REP_UTILITY.FN_GET_AC_NAME('OCCUPATION', INF_OCCUPATION) as occupation, "
                + "INF_WORK_PLACE as workPlace, INF_MOBILE_NO as mobileNo, INF_TEL_NO as telNo, INF_EMAIL_ID as emailId, INF_FAX_NO as faxNo, INF_MOBILE_NO_1 as mobileNo1, INF_MOBILE_NO_2 as mobileNo2, "
                + "TO_CHAR(INF_BIRTH_DT, 'DD/MM/RRRR') as birthDt, TO_CHAR(INF_WEDDING_DT, 'DD/MM/RRRR') as weddingDt, "
                + "TO_CHAR(INF_LICENSE_EXP_DT, 'DD/MM/RRRR') as licenseExpDt, TO_CHAR(INF_ID_EXP_DT, 'DD/MM/RRRR') as idExpDt "
                + "FROM M_INSURED_INFO "
                + "WHERE "
                + ("MOBILE_NO".equals(searchBy) ? "(INF_MOBILE_NO = ? OR INF_MOBILE_NO_1 = ? OR INF_MOBILE_NO_2 = ?) " : "INF_" + searchBy + " = ? ");
                //commented for corporate
                //+"AND INSTR(INF_CRM_ID, ?) > 0";
        try {
            con = getDBConnection(getDataSource());
            //commented for corporate
            //LOGGER.info("Query :: {} [{}:{}:{}]", new Object[]{sql, searchBy, searchValue, crmId});
            LOGGER.info("Query :: {} [{}:{}]", new Object[]{sql, searchBy, searchValue});
            Object[] params = null;
            if("MOBILE_NO".equals(searchBy)) {
            	params = new Object[]{searchValue, searchValue, searchValue};
            	//commented for corporate
               // params = new Object[]{searchValue, searchValue, searchValue, crmId};
            } else {
            	//commented for corporate
                //params = new Object[]{searchValue, crmId};
            	params = new Object[]{searchValue};
            }
            list = executeList(con, sql, params, Customer.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Driver Details List => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadMAppConfig(final FieldConstants.AppConfig appConfig) {
        List<KeyValue> list = null;
        Connection con = null;
        Object[] param = new Object[]{appConfig.getCode()};
        try {
            con = getDBConnection(getDataSource());
            StringBuilder query = new StringBuilder("SELECT AC_VALUE key, AC_REMARKS value, AC_NAME info1 FROM M_APP_CONFIG WHERE AC_NAME = ?");
            LOGGER.info("Query :: {} [{}]", query, appConfig.getCode());
            list = executeList(con, query.toString(), param, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving app config => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

  //commented for corporate
    /*public List<KeyValue> getMAppCodes(final String crmId, final FieldConstants.AppCodes appCodes) {
        return this.getMAppCodes(crmId, appCodes, null, null, null);
    }

    public List<KeyValue> getMAppCodes(final FieldConstants.AppCodes appCodes) {
        return this.getMAppCodes(null, appCodes, null, null, null);
    }

    public List<KeyValue> getMAppCodes(final FieldConstants.AppCodes appCodes, final String mcCode, final FieldConstants.AppCodes mcDefCode) {
        return getMAppCodes(null, appCodes, null, mcCode, mcDefCode);
    }

    public KeyValue getMAppCodes(final FieldConstants.AppCodes appCodes, final String acCode) {
        KeyValue kv = null;
        List<KeyValue> list = this.getMAppCodes(null, appCodes, acCode, null, null);
        if(list != null && !list.isEmpty()) {
            kv = list.get(0);
        }
        return kv;
    }*/

    public List<KeyValue> getMAppCodes(final String crmId, final FieldConstants.AppCodes appCodes) {
        return this.getMAppCodes(crmId, appCodes, null, null, null);
    }

    public List<KeyValue> getMAppCodes(final FieldConstants.AppCodes appCodes) {
        return this.getMAppCodes(appCodes, null, null, null);
    }

    public List<KeyValue> getMAppCodes(final FieldConstants.AppCodes appCodes, final String mcCode, final FieldConstants.AppCodes mcDefCode) {
        return getMAppCodes(appCodes, null, mcCode, mcDefCode);
    }

    public KeyValue getMAppCodes(final FieldConstants.AppCodes appCodes, final String acCode) {
        KeyValue kv = null;
        List<KeyValue> list = this.getMAppCodes(appCodes, acCode, null, null);
        if(list != null && !list.isEmpty()) {
            kv = list.get(0);
        }
        return kv;
    }

    /**
     * Load code & description from M_APP_CODES
     *
     * @param appCodes
     * @param acCode
     * @param mcCode
     * @param mcDefCode
     * @return list of values
     */
    //commented for corporate
    //private List<KeyValue> getMAppCodes(final String crmId, final FieldConstants.AppCodes appCodes, final String acCode, final String mcCode, final FieldConstants.AppCodes mcDefCode) {
    private List<KeyValue> getMAppCodes(final FieldConstants.AppCodes appCodes, final String acCode, final String mcCode, final FieldConstants.AppCodes mcDefCode) {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            List params = new LinkedList<>();
            params.add(appCodes.getCode());
            //commented for corporate
            //StringBuilder query = new StringBuilder("SELECT AC_CODE key, AC_DESC value, AC_VALUE info1, AC_LONG_DESC info2, AC_LONG_DESC_BL info3, AC_MC_CODE info4, AC_MAST_DEF_CODE text, AC_MAP_CODE info FROM M_APP_CODES WHERE AC_TYPE = ? ");
            StringBuilder query = new StringBuilder("SELECT AC_CODE key, AC_DESC value, AC_VALUE info1, AC_LONG_DESC info2, AC_LONG_DESC_BL info3, AC_MC_CODE info4, AC_MAST_DEF_CODE text FROM M_APP_CODES WHERE AC_TYPE = ? ");
            if (StringUtils.isNotBlank(mcCode)) {
                query.append("AND AC_MC_CODE = ? ");
                params.add(mcCode);
            }
            if (mcDefCode != null) {
                query.append("AND AC_MAST_DEF_CODE = ? ");
                params.add(mcDefCode.getCode());
            }
            if (StringUtils.isNotBlank(acCode)) {
                query.append("AND AC_CODE = ? ");
                params.add(acCode);
            }
            //commented for corporate
            /*if (StringUtils.isNotBlank(crmId)) {
                query.append("AND AC_MAP_CODE = ? ");
                params.add(crmId);
            }*/
            query.append("AND TRUNC(SYSDATE) BETWEEN TRUNC(NVL(AC_EFF_FM_DT, SYSDATE)) AND TRUNC(NVL(AC_EFF_TO_DT, SYSDATE)) ");
            //commented for corporate
            //query.append("ORDER BY AC_CODE");
            query.append("ORDER BY AC_DESC");
            //commented for corporate
            //LOGGER.info("Query :: {} [{}:{}:{}:{}]", new Object[]{query, appCodes.getCode(), mcCode, (null == mcDefCode ? null : mcDefCode.getCode()), acCode});
            LOGGER.info("Query :: {} [{}:{}:{}:{}]", new Object[]{query, appCodes.getCode(), mcCode, mcDefCode, acCode});
            list = executeList(con, query.toString(), params.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving app codes => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

  //added for corporate
    private List<KeyValue> getMAppCodes(final String crmId, final FieldConstants.AppCodes appCodes, final String acCode, final String mcCode, final FieldConstants.AppCodes mcDefCode) {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            List params = new LinkedList<>();
            params.add(appCodes.getCode());

            StringBuilder query = new StringBuilder("SELECT AC_CODE key, AC_DESC value, AC_VALUE info1, AC_LONG_DESC info2, AC_LONG_DESC_BL info3, AC_MC_CODE info4, AC_MAST_DEF_CODE text, AC_MAP_CODE info FROM M_APP_CODES WHERE AC_TYPE = ? ");

            if (StringUtils.isNotBlank(mcCode)) {
                query.append("AND AC_MC_CODE = ? ");
                params.add(mcCode);
            }
            if (mcDefCode != null) {
                query.append("AND AC_MAST_DEF_CODE = ? ");
                params.add(mcDefCode.getCode());
            }
            if (StringUtils.isNotBlank(acCode)) {
                query.append("AND AC_CODE = ? ");
                params.add(acCode);
            }

            if (StringUtils.isNotBlank(crmId)) {
                query.append("AND AC_MAP_CODE = ? ");
                params.add(crmId);
            }
            query.append("AND TRUNC(SYSDATE) BETWEEN TRUNC(NVL(AC_EFF_FM_DT, SYSDATE)) AND TRUNC(NVL(AC_EFF_TO_DT, SYSDATE)) ");
            //commented for corporate
            query.append("ORDER BY AC_CODE");

            LOGGER.info("Query :: {} [{}:{}:{}:{}]", new Object[]{query, appCodes.getCode(), mcCode, (null == mcDefCode ? null : mcDefCode.getCode()), acCode});

            list = executeList(con, query.toString(), params.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving app codes => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

  //added for corporate
    public List<KeyValue> getCrmTaskCategory(final String crmId, final FieldConstants.AppCodes appCodes) {
        return this.getCrmTaskCategory(crmId, appCodes, null, null);
    }

    public List<KeyValue> getCrmTaskCategory(final String crmId, final FieldConstants.AppCodes appCodes, final String mcCode) {
        return getCrmTaskCategory(crmId, appCodes, null, mcCode);
    }

    public KeyValue getCrmTaskCategory(final FieldConstants.AppCodes appCodes, final String acCode) {
        KeyValue kv = null;
        List<KeyValue> list = this.getCrmTaskCategory(null, appCodes, acCode, null);
        if(list != null && !list.isEmpty()) {
            kv = list.get(0);
        }
        return kv;
    }

    private List<KeyValue> getCrmTaskCategory(final String crmId, final FieldConstants.AppCodes appCodes, final String acCode, final String mcCode) {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            List<Object> params = new LinkedList<>();
            params.add(appCodes.getCode());
            StringBuilder query = new StringBuilder("SELECT CTG_CODE key, CTG_DESC value, CTG_MAP_CODE info4 FROM M_CRM_TASK_CATG WHERE CTG_TYPE = ? ");
            if (crmId != null) {
                query.append("AND CTG_CRM_ID = ? ");
                params.add(crmId);
            }
            if (StringUtils.isNotBlank(mcCode)) {
                query.append("AND CTG_MAP_CODE = ? ");
                params.add(mcCode);
            }
            if (StringUtils.isNotBlank(acCode)) {
                query.append("AND CTG_CODE = ? ");
                params.add(acCode);
            }
            query.append("AND TRUNC(SYSDATE) BETWEEN TRUNC(NVL(CTG_EFF_FM_DT, SYSDATE)) AND TRUNC(NVL(CTG_EFF_TO_DT, SYSDATE)) ");
            query.append("ORDER BY CTG_DESC");
            Object[] param = params.toArray();
            LOGGER.info("Query :: {} {}", new Object[]{query, Arrays.toString(param)});
            list = executeList(con, query.toString(), param, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving categories => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    /**
     * Load code & description from M_APP_PARAMETER
     *
     * @param appParameter
     * @param subCode
     * @param value
     * @return list of values
     */
    public List<KeyValue> getMAppParameter(final FieldConstants.AppParameter appParameter, final String subCode, final String value) {
        return loadMAppParameter(appParameter, subCode, value);
    }

    /**
     * Load code & description from M_APP_PARAMETER
     *
     * @param appParameter
     * @return list of values
     */
    public List<KeyValue> getMAppParameter(final FieldConstants.AppParameter appParameter) {
        return loadMAppParameter(appParameter, null, null);
    }

    private List<KeyValue> loadMAppParameter(final FieldConstants.AppParameter appParameter, final String subCode, final String value) {
        List<KeyValue> list = null;
        Connection con = null;
        Object[] param = new Object[]{appParameter.getCode()};
        try {
            con = getDBConnection(getDataSource());
            StringBuilder query = new StringBuilder("SELECT PARA_SUB_CODE key, PARA_NAME value, PARA_VALUE info1, PARA_NAME_BL info2 FROM M_APP_PARAMETER WHERE PARA_CODE = ? ");
            if (subCode != null) {
                query.append("AND PARA_SUB_CODE = ? ");
                param = new Object[]{appParameter.getCode(), subCode};
            }
            if (value != null) {
                query.append("AND PARA_VALUE = ? ");
                param = new Object[]{appParameter.getCode(), value};
            }
            query.append("ORDER BY PARA_NAME");
            LOGGER.info("Query :: {} [{},{},{}]", query, appParameter.getCode(), subCode, value);
            list = executeList(con, query.toString(), param, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving app parameter => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    /**
     *
     * @param model
     * @param type
     * @return error
     * @throws Exception
     */
    public String callCRMPackage(final Activity model, CommonDAO.ActivityTypes type) throws Exception {
        Connection con = null;
        CallableStatement cs = null;
        String sql = null, errorMsg = null;
        try {
            con = getDBConnection(getDataSource());
            con.setAutoCommit(true);
            int indx = 0;
            switch (type) {
                case SMS:
                case EMAIL:
                    CommonDAO dao = new CommonDAO(con);
                    errorMsg = dao.callCRMPackage(model, type);
                    break;
                case ACTIVITY:
                	//commented for corporate
                    /*PKG_CRM.PR_REG_ACTIVITY( // Procedure change required in PKG_CRM
                     P_ID VARCHAR2,
                     P_CRM_ID VARCHAR2,
                     P_TYPE VARCHAR2,
                     P_DETAILS VARCHAR2,
                     P_USER_ID VARCHAR2)
                	if(StringUtils.isNotBlank(model.getDealId()) || StringUtils.isNotBlank(model.getCrmId())){
                		saveLogOrActivity(model);      // Java method - To be removed after PKG update
                	}else {
                        sql = "{call PKG_CRM.PR_REG_ACTIVITY(?,?,?,?)}";
                        cs = con.prepareCall(sql);
                        LOGGER.info("Input for PKG_CRM.PR_REG_ACTIVITY ==> {}:{}:{}", new Object[]{model.getCivilId(), model.getTemplate(), model.getUserId()});
                        cs.setString(++indx, model.getCivilId());
                        cs.setString(++indx, model.getTemplate());
                        cs.setString(++indx, model.getMessage());
                        cs.setString(++indx, model.getUserId());
                        cs.execute();

                	}*/

                	sql = "{call PKG_CRM.PR_REG_ACTIVITY(?,?,?,?)}";
                    cs = con.prepareCall(sql);
                    LOGGER.info("Input for PKG_CRM.PR_REG_ACTIVITY ==> {}:{}:{}", new Object[]{model.getCivilId(), model.getTemplate(), model.getUserId()});
                    cs.setString(++indx, model.getCivilId());
                    cs.setString(++indx, model.getTemplate());
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
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return errorMsg;
    }

  //commented for corporate
   /* public List<KeyValue> loadDashboardFigures(final String civilId, final DashboardFigures type, final String crmId) {
        return this.loadDashboardFigures(civilId, null, null, type, crmId);
    }*/

    public List<KeyValue> loadDashboardFigures(final String civilId, final DashboardFigures type) {
        return this.loadDashboardFigures(civilId, null, null, type);
    }

    /**
     *
     * @param civilId
     * @param totPremium
     * @param totClaim
     * @param type
     * @return list of values
     */
    //commented for corporate
    //public List<KeyValue> loadDashboardFigures(final String civilId, final Double totPremium, final Double totClaim, final DashboardFigures type, final String crmId) {
    public List<KeyValue> loadDashboardFigures(final String civilId, final Double totPremium, final Double totClaim, final DashboardFigures type) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};
            switch (type) {
                case POLICIES:
                    // ACTIVE POLICIES
                    query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_PROD_NAME(TPI_PROD_CODE) key, COUNT(1) value ");
                    query.append("FROM T_POL_INFO ");
                    query.append("WHERE TPI_CIVIL_ID = ? ");
                    query.append("AND TRUNC(SYSDATE) BETWEEN TRUNC(TPI_FM_DT) AND TRUNC(TPI_TO_DT) ");
                    query.append("AND TPI_STATUS = 'A' ");
                    query.append("GROUP BY TPI_PROD_CODE");
                    break;
                case QUOTES:
                    // ACTIVE QUOTES
                    query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_PROD_NAME(QPI_PROD_CODE) key, COUNT(1) value ");
                    query.append("FROM Q_POL_INFO ");
                    query.append("WHERE QPI_CIVIL_ID = ? ");
                    query.append("AND QPI_PROD_CODE IS NOT NULL AND QPI_TRAN_SR_NO = 0 ");
                    query.append("AND TRUNC(QPI_CR_DT) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) ");
                    query.append("GROUP BY QPI_PROD_CODE");
                    break;
                case CLAIMS:
                    // ACTIVE CLAIMS
                    query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_PROD_NAME(CLM_PROD_CODE) key, COUNT(1) value ");
                    query.append("FROM T_CLAIM, T_CLM_RISK_PARTY_INFO ");
                    query.append("WHERE CLM_TRANS_ID = CRP_CLM_TRANS_ID ");
                    query.append("AND CRP_LICENSE_NO = ? ");
                    query.append("AND CLM_STS <> 'C' ");
                    query.append("GROUP BY CLM_PROD_CODE");
                    break;
                case TOTAL_AND_LOSSRATIO:
                    // TOTAL VALUE & LOSS RATIO
                    query = new StringBuilder("SELECT SUM(TOT_PREM) info1, SUM(TOT_CLAIM) info2, ");
                    query.append("ROUND((CASE WHEN SUM(TOT_PREM) <> 0 THEN SUM(TOT_CLAIM) / SUM(TOT_PREM) * 100 ELSE 0 END), 2) info3 ");
                    query.append("FROM (SELECT TPI_TRANS_ID, TOT_PREM, NVL((SELECT SUM(CLM_OS_PAY - CLM_OS_REC + CLM_PAID - CLM_RECOVERED) ");
                    query.append("FROM T_CLAIM WHERE CLM_POL_TRANS_ID = TPI_TRANS_ID ");
                    query.append("AND TRUNC(CLM_LOSS_DT) BETWEEN TRUNC(TPI_FM_DT) AND TRUNC(TPI_TO_DT)), 0) TOT_CLAIM ");
                    query.append("FROM (SELECT TPI_TRANS_ID, MIN(TPI_FM_DT) TPI_FM_DT, MAX(TPI_TO_DT) TPI_TO_DT, ");
                    query.append("SUM(TPI_PREM_FC - TPI_POL_DISC_FC - TPI_CVR_DISC_FC + TPI_POL_LOAD_FC + TPI_CVR_LOAD_FC + TPI_FEE_FC) TOT_PREM ");
                    query.append("FROM T_POL_INFO ");
                    query.append("WHERE TPI_CIVIL_ID = ? ");
                    query.append("GROUP BY TPI_TRANS_ID)) ");
                    break;
                case PRODUCTIVITY:
                    // 3 YEARS PRODUCTIVITY (GRAPH)
                    query = new StringBuilder("SELECT UW_YEAR key, SUM(TOT_PREM) value, SUM(TOT_CLAIM) info1 ");
                    query.append("FROM (SELECT TPI_UW_YEAR UW_YEAR, TPI_TRANS_ID, TOT_PREM, ");
                    query.append("NVL((SELECT SUM(CLM_OS_PAY - CLM_OS_REC + CLM_PAID - CLM_RECOVERED) ");
                    query.append("FROM T_CLAIM WHERE CLM_POL_TRANS_ID = TPI_TRANS_ID ");
                    query.append("AND TRUNC(CLM_LOSS_DT) BETWEEN TRUNC(TPI_FM_DT) AND TRUNC(TPI_TO_DT)), 0) TOT_CLAIM ");
                    query.append("FROM (SELECT TPI_UW_YEAR, TPI_TRANS_ID, MIN(TPI_FM_DT) TPI_FM_DT, MAX(TPI_TO_DT) TPI_TO_DT, ");
                    query.append("SUM(TPI_PREM_FC - TPI_POL_DISC_FC - TPI_CVR_DISC_FC + TPI_POL_LOAD_FC + TPI_CVR_LOAD_FC + TPI_FEE_FC) TOT_PREM ");
                    query.append("FROM T_POL_INFO WHERE TPI_CIVIL_ID = ? ");
                    query.append("AND TPI_UW_YEAR >= EXTRACT(YEAR FROM SYSDATE) - 3 ");
                    query.append("GROUP BY TPI_UW_YEAR, TPI_TRANS_ID)) ");
                    query.append("GROUP BY UW_YEAR ORDER BY 1");
                    break;
                case ACTIVITIES:
                    // ACTIVITIES LIST
                    query = new StringBuilder("SELECT IL_ID key, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', IL_TYPE) value, IL_LOG_DETAILS info1, ");
                    //commented for corporate
                    //query.append("PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', IL_CRM_ID) text, IL_CRM_ID info, ");
                    query.append("(SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_USER_ID = IL_CR_UID) info2, TO_CHAR(IL_CR_DT, 'DD/MM/YYYY HH12:MI AM') info3, (SELECT AC_VALUE FROM M_APP_CODES WHERE AC_TYPE = 'CRM_LOG_TYPE' AND AC_CODE = IL_TYPE) info4 ");
                    query.append("FROM T_INSURED_LOG ");
                    query.append("WHERE IL_CIVIL_ID = ? ");
                    //commented for corporate
                    /*if (StringUtils.isNotBlank(crmId)) {
                        query.append("AND IL_CRM_ID = '").append(crmId).append("' ");
                    }*/
                    query.append("ORDER BY IL_CR_DT DESC ");
                    query.append("OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY");
                    break;
                case ELIGIBLE_OFFERS:
                    // ELIGIBLE OFFERS
                    query = new StringBuilder("SELECT PKG_CRM.FN_GET_OFFERS(?, ?, ?) value FROM DUAL");
                    date = new Object[]{civilId, totPremium, totClaim};
                    break;
                default:
                    throw new Exception("Invalid call");
            }
            LOGGER.info("Query :: {} [{}]", query, civilId);
            list = executeList(con, query.toString(), date, KeyValue.class);
            if (DashboardFigures.ELIGIBLE_OFFERS == type) {
                if (list != null && !list.isEmpty()) {
                    KeyValue keyValue = null;
                    StringTokenizer st = new StringTokenizer(list.get(0).getValue(), "\n");
                    list = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        keyValue = new KeyValue();
                        keyValue.setValue(st.nextToken());
                        list.add(keyValue);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while retreiving dashboard figures => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /**
     *
     * @param civilId
     * @return all quotations
     */
    public List<Policy> loadQuoteList(final String civilId) {
        List<Policy> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};
            query = new StringBuilder("SELECT QPI_TRANS_ID transId, QPI_TRAN_SR_NO tranSrNo, QPI_QUOT_NO policyNo, QPI_INS_NAME insuredName, ");
            query.append("DECODE(QPI_TRAN_TYPE, 'POL', 'New', 'REN', 'Renewal') tranType, ");
            query.append("PKG_REP_UTILITY.FN_GET_PROD_NAME(QPI_PROD_CODE) product, ");
            query.append("PKG_REP_UTILITY.FN_GET_SCH_NAME(QPI_COMP_CODE, QPI_PROD_CODE, QPI_SCH_CODE) scheme, ");
            query.append("PKG_REP_UTILITY.FN_GET_PARA_NAME('APP_PORTAL', QPI_PORTAL) portal, ");
            query.append("PKG_REP_UTILITY.FN_GET_CUST_NAME(QPI_CUST_CODE) customerName, ");
            query.append("PKG_REP_UTILITY.FN_GET_CUST_NAME(QPI_AGENT_ID) agentName, ");
            query.append("(SELECT AC_DESC FROM M_APP_CODES WHERE AC_TYPE LIKE '%MENU_CODE%' AND AC_CODE = QPI_SOURCE) source, ");
            query.append("TO_CHAR(QPI_FM_DT, 'DD/MM/RRRR') fromDate, TO_CHAR(QPI_TO_DT, 'DD/MM/RRRR') toDate, QPI_UW_YEAR uwYear, QPI_PREM_FC premium, QPI_POL_DISC_FC + QPI_CVR_DISC_FC discount, ");
            query.append("QPI_POL_LOAD_FC + QPI_CVR_LOAD_FC loadings, QPI_FEE_FC fees, ");
            query.append("QPI_PREM_FC - QPI_POL_DISC_FC - QPI_CVR_DISC_FC + QPI_POL_LOAD_FC + QPI_CVR_LOAD_FC + QPI_FEE_FC netAmount, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('MOT_VEH_MAKE', QRI_VEH_MAKE_CODE) vehicleMake, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('MOT_VEH_MOD', QRI_VEH_MODEL_CODE) vehicleModel, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('MOT_VEH_NOC', QRI_VEH_NO_CYLINDERS) vehicleCylinders, ");
            query.append("QRI_MFG_YEAR vehicleMfgYear, QRI_REG_NO vehicleRegNo, QRI_CHASSIS_NO vehicleChassisNo, QRI_SI_FC sumInsured, QPI_CR_DT createdDate, ");
            query.append("(CASE WHEN QPI_PROD_CODE IS NULL THEN 'Product Selection Page' ");
            query.append("WHEN QPI_PROD_CODE IS NOT NULL AND QPI_PREM_FC = 0 THEN 'Optional Cover Page' ");
            query.append("WHEN QPI_PROD_CODE IS NOT NULL AND NVL(QPI_TERMS_AGREE_YN, '0') = '0' THEN 'Additional Info Page' ");
            query.append("WHEN QPI_PROD_CODE IS NOT NULL AND NVL(QPI_TERMS_AGREE_YN, '0') = '1' THEN 'Payment Page' END) leftPage ");
            query.append("FROM Q_POL_INFO, Q_RISK_INFO ");
            query.append("WHERE QPI_CIVIL_ID = ? ");
            query.append("AND QPI_TRANS_ID = QRI_TRANS_ID ");
            query.append("AND QPI_TRAN_SR_NO = QRI_TRAN_SR_NO ");
            query.append("AND QPI_PROD_CODE IS NOT NULL ");
            query.append("AND QPI_TRAN_TYPE <> 'END' ");
            query.append("AND QPI_STATUS = 'A' ");
            query.append("AND TRUNC(QPI_CR_DT) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) ");
            query.append("ORDER BY QPI_FM_DT DESC ");
            LOGGER.info("Query :: {} [{}]", query, civilId);
            list = executeList(con, query.toString(), date, Policy.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving quote list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /**
     *
     * @param civilId
     * @return all active and expired policies
     */
    public List<Policy> loadPolicyList(final String civilId) {
        List<Policy> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};
            query = new StringBuilder("SELECT TPI_TRANS_ID transId, TPI_TRAN_SR_NO tranSrNo, TPI_POLICY_NO policyNo, TPI_INS_NAME insuredName, ");
            query.append("PKG_REP_UTILITY.FN_GET_PROD_NAME(TPI_PROD_CODE) product, ");
            query.append("PKG_REP_UTILITY.FN_GET_SCH_NAME(TPI_COMP_CODE, TPI_PROD_CODE, TPI_SCH_CODE) scheme, ");
            query.append("PKG_REP_UTILITY.FN_GET_PARA_NAME('APP_PORTAL', TPI_ORG_PORTAL) portal, ");
            query.append("PKG_REP_UTILITY.FN_GET_CUST_NAME(TPI_ORG_CUST_CODE) customerName, ");
            query.append("PKG_REP_UTILITY.FN_GET_CUST_NAME(TPI_ORG_AGENT_ID) agentName, ");
            query.append("(SELECT AC_DESC FROM M_APP_CODES WHERE AC_TYPE LIKE '%MENU_CODE%' AND AC_CODE = TPI_ORG_SOURCE) source, ");
            query.append("TO_CHAR(TPI_FM_DT, 'DD/MM/RRRR') fromDate, TO_CHAR(TPI_TO_DT, 'DD/MM/RRRR') toDate, TPI_UW_YEAR uwYear, TPS_PREM_FC premium, TPS_DISC_FC discount, TPS_LOAD_FC loadings, TPS_FEE_FC fees, ");
            query.append("TPS_PREM_FC - TPS_DISC_FC + TPS_LOAD_FC + TPS_FEE_FC netAmount, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('MOT_VEH_MAKE', TRI_VEH_MAKE_CODE) vehicleMake, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('MOT_VEH_MOD', TRI_VEH_MODEL_CODE) vehicleModel, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('MOT_VEH_NOC', TRI_VEH_NO_CYLINDERS) vehicleCylinders, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('MENU_CODES', TPI_MENU)||PKG_REP_UTILITY.FN_GET_AC_NAME('MENU_CODE', TPI_MENU)businessSource, ");
            query.append("TRI_MFG_YEAR vehicleMfgYear, TRI_REG_NO vehicleRegNo, TRI_CHASSIS_NO vehicleChassisNo, TRI_CUM_SI_FC sumInsured, ");
            query.append("(CASE WHEN TRUNC(SYSDATE) BETWEEN TRUNC(TPI_FM_DT) AND TRUNC(TPI_TO_DT) OR TRUNC(SYSDATE) < TRUNC(TPI_FM_DT) THEN 'Active' ELSE 'In-Active' END) status, PKG_CLAIM_PROCESS.FN_GET_CLAIM_RATIO(TPI_TRANS_ID) lossRatio ");
            query.append("FROM T_POL_INFO, T_RISK_INFO, T_POL_SUMMARY ");
            query.append("WHERE TPI_CIVIL_ID = ? ");
            query.append("AND TPI_TRANS_ID = TPS_TRANS_ID ");
            query.append("AND TPI_TRAN_SR_NO = TPS_TRAN_SR_NO ");
            query.append("AND TPI_TRANS_ID = TRI_TRANS_ID ");
            query.append("AND TPI_TRAN_SR_NO = TRI_TRAN_SR_NO AND TRI_RISK_SR_NO = 1 ");
            query.append("AND TPI_STATUS = 'A' ");
            query.append("ORDER BY TPI_FM_DT DESC ");
            LOGGER.info("Query :: {} [{}]", query, civilId);
            list = executeList(con, query.toString(), date, Policy.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving policy list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /**
     *
     * @param civilId
     * @return all claim details
     */
    public List<Policy> loadClaimsList(final String civilId) {
        List<Policy> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};
            query = new StringBuilder("SELECT CRP_CLM_TRANS_ID transId, CRP_ONLINE_REF onlineRef, CLM_NO claimNo, CLM_POLICY_NO policyNo, ");
            query.append("CRP_PARTY_REF_NO partyRefNo, PKG_REP_UTILITY.FN_GET_PROD_NAME(CLM_PROD_CODE) product, ");
            query.append("CLM_POLICE_REF_NO policeRefNo, DECODE(CRP_PARTY_REF_NO, '1', 'OWN', 'TP') partyClaimType, ");
            query.append("TO_CHAR(CLM_LOSS_DT, 'DD/MM/RRRR') lossDate, TO_CHAR(CLM_INTM_DT, 'DD/MM/RRRR') intimatedDate, ");
            query.append("DECODE(CLM_RECOVERY_YN, '1', 'Yes', 'No') recoveryYn, PKG_REP_UTILITY.FN_GET_CUST_NAME(CLM_RECOVERY_CUST) recoveryCust, ");
            query.append("CRP_OWNER_NAME insuredName, CRP_VEH_REGN_NO vehicleRegNo, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('MOT_VEH_MAKE', CRP_VEH_MAKE_CODE) vehicleMake, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('MOT_VEH_MOD', CRP_VEH_MODEL_CODE) vehicleModel, ");
            query.append("PKG_REP_UTILITY.FN_GET_CUST_NAME(CRP_GARAGE_CODE) garageName, ");
            query.append("(SELECT NVL(USER_SHORT_NAME, USER_NAME) FROM M_USER WHERE USER_EMP_NO = CRP_SURVEYOR_CODE) surveyorName, ");
            query.append("CRP_OS_PAY, CRP_PAID, DECODE(CLM_STS, 'C', 'Closed', 'Active') claimStatus, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('CLM_PROCESS', CRS_STATUS_CODE) status ");
            query.append("FROM T_CLAIM, T_CLM_RISK_PARTY_INFO, T_CLM_RISK_PARTY_STATUS ");
            query.append("WHERE CLM_TRANS_ID = CRP_CLM_TRANS_ID ");
            query.append("AND CRP_LICENSE_NO = ? ");
            query.append("AND CRP_CLM_TRANS_ID = CRS_CLM_TRANS_ID ");
            query.append("AND CRP_PARTY_REF_NO = CRS_PARTY_REF_NO ");
            query.append("ORDER BY CLM_INTM_DT DESC");
            LOGGER.info("Query :: {} [{}]", query, civilId);
            list = executeList(con, query.toString(), date, Policy.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving claim list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadEmailTemplateList() {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT SMT_TEMP_CODE key, SMT_TEMP_DESC value, SMT_EMAIL_SUBJECT info1 FROM M_SMS_EMAIL_TEMPLATE WHERE SMT_TEMP_CODE LIKE '%CRM%'");
            list = executeList(con, query.toString(), new Object[]{}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving e-mail template => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public Customer loadCustomerDetailByCivilId(final String civilId) {
        Customer bean = null;
        Connection con = null;
        String sql = "SELECT INF_NAME name, INF_CIVIL_ID civilId, INF_SOURCE source, INF_SOURCE_DETAILS sourceDetails, INF_PO_BOX poBox, INF_MEM_PROMO_CATG custType, "
                + "INF_COUNTRY country, INF_NATIONALITY nationality, INF_CITY city, INF_GENDER gender, INF_AGE age, INF_OCCUPATION occupation, "
                + "INF_WORK_PLACE workPlace, INF_MOBILE_NO mobileNo, INF_TEL_NO telNo, INF_EMAIL_ID emailId, INF_FAX_NO faxNo, "
              //commented for corporate
                //+ "TO_CHAR(INF_BIRTH_DT, 'DD/MM/YYYY') birthDt, TO_CHAR(INF_WEDDING_DT, 'DD/MM/YYYY') weddingDt, INF_CRM_ID crmId, "
                + "TO_CHAR(INF_BIRTH_DT, 'DD/MM/YYYY') birthDt, TO_CHAR(INF_WEDDING_DT, 'DD/MM/YYYY') weddingDt, "
                + "TO_CHAR(INF_LICENSE_EXP_DT, 'DD/MM/YYYY') licenseExpDt, TO_CHAR(INF_ID_EXP_DT, 'DD/MM/YYYY') idExpDt,INF_MOBILE_NO_1 mobileNo1,INF_MOBILE_NO_2 mobileNo2 "
                + "FROM M_INSURED_INFO WHERE INF_CIVIL_ID = ?";
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{sql, civilId});
            Object[] params = new Object[]{civilId};
            bean = (Customer) executeQuery(con, sql, params, Customer.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving customer details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return bean;
    }

  //commented for corporate
   /* public int saveCustomerDetail(final Customer bean, final String operation) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equalsIgnoreCase(operation)) {
                sql = "INSERT INTO M_INSURED_INFO (INF_CRM_ID, INF_NAME, INF_CIVIL_ID, INF_SOURCE, INF_SOURCE_DETAILS, INF_PO_BOX, "
                        + "INF_COUNTRY, INF_NATIONALITY, INF_CITY, INF_GENDER, INF_AGE, INF_OCCUPATION, INF_WORK_PLACE, INF_MEM_PROMO_CATG, INF_MOBILE_NO, INF_TEL_NO, INF_EMAIL_ID, INF_FAX_NO, "
                        + "INF_BIRTH_DT, INF_WEDDING_DT, INF_LICENSE_EXP_DT, INF_ID_EXP_DT,INF_MOBILE_NO_1,INF_MOBILE_NO_2,INF_CR_UID,INF_CR_DT) VALUES(:crmId, :name, :civilId, :source, :sourceDetails, :poBox, "
                        + ":country, :nationality, :city, :gender, :age, :occupation, :workPlace, :custType, :mobileNo, :telNo, :emailId, :faxNo, :birthDt, :weddingDt, :licenseExpDt, :idExpDt, :mobileNo1, :mobileNo2, :crUid,SYSDATE)";
                ps = con.prepareStatement(sql);
                ps.setString(++i, bean.getCrmId());
                ps.setString(++i, bean.getName());
                ps.setString(++i, bean.getCivilId());
                ps.setString(++i, bean.getSource());
                ps.setString(++i, bean.getSourceDetails());
                ps.setString(++i, bean.getPoBox());
                ps.setString(++i, bean.getCountry());
                ps.setString(++i, bean.getNationality());
                ps.setString(++i, bean.getCity());
                ps.setString(++i, bean.getGender());
                ps.setString(++i, bean.getAge());
                ps.setString(++i, bean.getOccupation());
                ps.setString(++i, bean.getWorkPlace());
                ps.setString(++i, bean.getCustType());
                ps.setString(++i, bean.getMobileNo());
                ps.setString(++i, bean.getTelNo());
                ps.setString(++i, bean.getEmailId());
                ps.setString(++i, bean.getFaxNo());
                ps.setString(++i, bean.getBirthDt());
                ps.setString(++i, bean.getWeddingDt());
                ps.setString(++i, bean.getLicenseExpDt());
                ps.setString(++i, bean.getIdExpDt());
                ps.setString(++i, bean.getMobileNo1());
                ps.setString(++i, bean.getMobileNo2());
                ps.setString(++i, bean.getCrUid());
                recCnt = ps.executeUpdate();
            } else if ("edit".equalsIgnoreCase(operation)) {
                sql = " UPDATE M_INSURED_INFO SET INF_CRM_ID = ?, INF_NAME = ?, INF_SOURCE = ?, INF_SOURCE_DETAILS = ?, "
                        + "INF_PO_BOX = ?, INF_COUNTRY = ?, INF_NATIONALITY = ?, INF_CITY = ?, INF_GENDER = ?, INF_AGE = ?, "
                        + "INF_OCCUPATION = ?, INF_WORK_PLACE = ?, INF_MEM_PROMO_CATG = ?, INF_MOBILE_NO = ?, INF_TEL_NO = ?, "
                        + "INF_EMAIL_ID = ?, INF_FAX_NO = ?, INF_BIRTH_DT = TO_DATE(?, 'DD/MM/YYYY'), "
                        + "INF_WEDDING_DT = TO_DATE(?, 'DD/MM/YYYY'), INF_LICENSE_EXP_DT = TO_DATE(?, 'DD/MM/YYYY'), "
                        + "INF_ID_EXP_DT = TO_DATE(?, 'DD/MM/YYYY'),INF_MOBILE_NO_1 = ?,INF_MOBILE_NO_2 = ?,INF_UPD_UID = ?,INF_UPD_DT = SYSDATE "
                        + "where INF_CIVIL_ID = ?";

                LOGGER.info("Query :: {} [{}]", new Object[]{sql, bean.getCivilId()});
                ps = con.prepareStatement(sql);
                ps.setString(++i, bean.getCrmId());
                ps.setString(++i, bean.getName());
                ps.setString(++i, bean.getSource());
                if ("O".equalsIgnoreCase(bean.getSource())) {
                    ps.setString(++i, bean.getSourceDetails());
                } else {
                    ps.setNull(++i, Types.VARCHAR);
                }
                ps.setString(++i, bean.getPoBox());
                ps.setString(++i, bean.getCountry());
                ps.setString(++i, bean.getNationality());
                ps.setString(++i, bean.getCity());
                ps.setString(++i, bean.getGender());
                ps.setString(++i, bean.getAge());
                ps.setString(++i, bean.getOccupation());
                ps.setString(++i, bean.getWorkPlace());
                ps.setString(++i, bean.getCustType());
                ps.setString(++i, bean.getMobileNo());
                ps.setString(++i, bean.getTelNo());
                ps.setString(++i, bean.getEmailId());
                ps.setString(++i, bean.getFaxNo());
                ps.setString(++i, bean.getBirthDt());
                ps.setString(++i, bean.getWeddingDt());
                ps.setString(++i, bean.getLicenseExpDt());
                ps.setString(++i, bean.getIdExpDt());
                ps.setString(++i, bean.getMobileNo1());
                ps.setString(++i, bean.getMobileNo2());
                ps.setString(++i, bean.getUpdUid());
                ps.setString(++i, bean.getCivilId());
                recCnt = ps.executeUpdate();
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while customer entry => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }*/

    public int saveCustomerDetail(final Customer bean, final String operation) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equalsIgnoreCase(operation)) {
                sql = "INSERT INTO M_Insured_Info (INF_NAME, INF_CIVIL_ID, INF_SOURCE, INF_SOURCE_DETAILS, INF_PO_BOX, "
                        + "INF_COUNTRY, INF_NATIONALITY, INF_CITY, INF_GENDER, INF_AGE, INF_OCCUPATION, INF_WORK_PLACE, INF_MEM_PROMO_CATG, INF_MOBILE_NO, INF_TEL_NO, INF_EMAIL_ID, INF_FAX_NO, "
                        + "INF_BIRTH_DT, INF_WEDDING_DT, INF_LICENSE_EXP_DT, INF_ID_EXP_DT,INF_MOBILE_NO_1,INF_MOBILE_NO_2,INF_CR_UID,INF_CR_DT) VALUES(:name, :civilId, :source, :sourceDetails, :poBox, "
                        + ":country, :nationality, :city, :gender, :age, :occupation, :workPlace, :custType, :mobileNo, :telNo, :emailId, :faxNo, :birthDt, :weddingDt, :licenseExpDt, :idExpDt, :mobileNo1, :mobileNo2, :crUid,SYSDATE)";
                ps = con.prepareStatement(sql);
                ps.setString(++i, bean.getName());
                ps.setString(++i, bean.getCivilId());
                ps.setString(++i, bean.getSource());
                ps.setString(++i, bean.getSourceDetails());
                ps.setString(++i, bean.getPoBox());
                ps.setString(++i, bean.getCountry());
                ps.setString(++i, bean.getNationality());
                ps.setString(++i, bean.getCity());
                ps.setString(++i, bean.getGender());
                ps.setString(++i, bean.getAge());
                ps.setString(++i, bean.getOccupation());
                ps.setString(++i, bean.getWorkPlace());
                ps.setString(++i, bean.getCustType());
                ps.setString(++i, bean.getMobileNo());
                ps.setString(++i, bean.getTelNo());
                ps.setString(++i, bean.getEmailId());
                ps.setString(++i, bean.getFaxNo());
                ps.setString(++i, bean.getBirthDt());
                ps.setString(++i, bean.getWeddingDt());
                ps.setString(++i, bean.getLicenseExpDt());
                ps.setString(++i, bean.getIdExpDt());
                ps.setString(++i, bean.getMobileNo1());
                ps.setString(++i, bean.getMobileNo2());
                ps.setString(++i, bean.getCrUid());
                recCnt = ps.executeUpdate();
            } else if ("edit".equalsIgnoreCase(operation)) {
                sql = " UPDATE M_Insured_Info SET INF_NAME = ?, INF_SOURCE = ?, INF_SOURCE_DETAILS = ?, "
                        + "INF_PO_BOX = ?, INF_COUNTRY = ?, INF_NATIONALITY = ?, INF_CITY = ?, INF_GENDER = ?, INF_AGE = ?, "
                        + "INF_OCCUPATION = ?, INF_WORK_PLACE = ?, INF_MEM_PROMO_CATG = ?, INF_MOBILE_NO = ?, INF_TEL_NO = ?, "
                        + "INF_EMAIL_ID = ?, INF_FAX_NO = ?, INF_BIRTH_DT = TO_DATE(?, 'DD/MM/YYYY'), "
                        + "INF_WEDDING_DT = TO_DATE(?, 'DD/MM/YYYY'), INF_LICENSE_EXP_DT = TO_DATE(?, 'DD/MM/YYYY'), "
                        + "INF_ID_EXP_DT = TO_DATE(?, 'DD/MM/YYYY'),INF_MOBILE_NO_1 = ?,INF_MOBILE_NO_2 = ?,INF_UPD_UID = ?,INF_UPD_DT = SYSDATE "
                        + "where INF_CIVIL_ID = ?";

                LOGGER.info("Query :: {} [{}]", new Object[]{sql, bean.getCivilId()});
                ps = con.prepareStatement(sql);
                ps.setString(++i, bean.getName());
                ps.setString(++i, bean.getSource());
                if ("O".equalsIgnoreCase(bean.getSource())) {
                    ps.setString(++i, bean.getSourceDetails());
                } else {
                    ps.setNull(++i, Types.VARCHAR);
                }
                ps.setString(++i, bean.getPoBox());
                ps.setString(++i, bean.getCountry());
                ps.setString(++i, bean.getNationality());
                ps.setString(++i, bean.getCity());
                ps.setString(++i, bean.getGender());
                ps.setString(++i, bean.getAge());
                ps.setString(++i, bean.getOccupation());
                ps.setString(++i, bean.getWorkPlace());
                ps.setString(++i, bean.getCustType());
                ps.setString(++i, bean.getMobileNo());
                ps.setString(++i, bean.getTelNo());
                ps.setString(++i, bean.getEmailId());
                ps.setString(++i, bean.getFaxNo());
                ps.setString(++i, bean.getBirthDt());
                ps.setString(++i, bean.getWeddingDt());
                ps.setString(++i, bean.getLicenseExpDt());
                ps.setString(++i, bean.getIdExpDt());
                ps.setString(++i, bean.getMobileNo1());
                ps.setString(++i, bean.getMobileNo2());
                ps.setString(++i, bean.getUpdUid());
                ps.setString(++i, bean.getCivilId());
                recCnt = ps.executeUpdate();
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while customer entry => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public List<KeyValue> loadLogActivities(String civilId) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};

            // ACTIVITIES LIST
            query = new StringBuilder("SELECT IL_ID key, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', IL_TYPE) value, IL_LOG_DETAILS info1, ");
            query.append("(SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_USER_ID = IL_CR_UID) info2, TO_CHAR(IL_CR_DT, 'DD/MM/YYYY HH12:MI AM') info3, ");
            query.append("(SELECT AC_VALUE FROM M_APP_CODES WHERE AC_TYPE = 'CRM_LOG_TYPE' AND AC_CODE = IL_TYPE) info4 ");
            query.append("FROM T_INSURED_LOG ");
            query.append("WHERE IL_CIVIL_ID = ? ");
            query.append("ORDER BY IL_ID DESC ");

            LOGGER.info("Query :: {} [{}]", query, civilId);
            list = executeList(con, query.toString(), date, KeyValue.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving dashboard figures => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    private final static String QRY_USER_INFO = "SELECT CU_USER_ID userId, CU_PASSWORD password, CU_LDAP_ID userLdapId, CU_USER_NAME userName, CU_TEL_NO userTelNo, NVL(CU_AMDIN_YN, 0) userAdminYn, CU_PWD_RESET_TOKEN pwdResetToken, CU_AGENT_TYPE agentType, CU_TEL_NO userTeam, CU_ROLE_SEQ userRoleSeq, NVL2(CU_CHAT_UID, '1', '0') userChatYn, CU_APPL_MODULES applModules FROM M_CRM_USER WHERE CU_LOCK_YN = '0' AND LOWER(CU_LDAP_ID) = ?";
    private final static String QRY_USER_APPL_COMP = "SELECT MUC_COMP_CODE key FROM M_CRM_USER_COMP WHERE MUC_USER_ID = ?";

    /**
     *
     * @param userId
     * @return user details
     */
    public UserInfo getUserDetails(final String userId) {
        UserInfo user = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", QRY_USER_INFO, userId);
            user = (UserInfo) executeQuery(con, QRY_USER_INFO, new Object[]{userId}, UserInfo.class);
            if (user != null) {
                LOGGER.info("Query :: {} [{}]", QRY_USER_APPL_COMP, user.getUserId());
                List<KeyValue> list = executeList(con, QRY_USER_APPL_COMP, new Object[]{user.getUserId()}, KeyValue.class);
                user.setApplCompanyList(list);
            }
        } catch (Exception e) {
            LOGGER.error("Error while retreiving user details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return user;
    }

    private final static String INS_LOGIN_HISTORY = "INSERT INTO T_CRM_LOGIN_HISTORY(CLH_SYS_ID, CLH_USER_ID, CLH_STATUS, CLH_REMARK, CLH_SESSION_ID, CLH_IP_ADDRESS, CLH_BROWSER_INFO, CLH_LOGIN_DT) VALUES (S_CLH_SYS_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, SYSDATE)";

    public void insertLoginDetails(LoginHistory loginHistory) {
        Connection con = null;
        PreparedStatement ps = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            ps = con.prepareStatement(INS_LOGIN_HISTORY);
            ps.setString(1, loginHistory.getUserId());
            ps.setString(2, loginHistory.getStatus());
            ps.setString(3, loginHistory.getRemarks());
            ps.setString(4, loginHistory.getSessionId());
            ps.setString(5, loginHistory.getIpAddress());
            ps.setString(6, loginHistory.getBrowserInfo());
            recCnt = ps.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error while inserting login history => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
    }

    private final static String QRY_USER_INFO_BY_USER_ID = "SELECT CU_USER_ID userId, CU_PASSWORD password, CU_LDAP_ID userLdapId, CU_USER_NAME userName, CU_TEL_NO userTelNo, NVL(CU_AMDIN_YN, 0) userAdminYn, CU_PWD_RESET_TOKEN userPwdResetToken, CU_CHAT_UID userChatUid, CU_CHAT_PWD userChatPwd FROM M_CRM_USER WHERE CU_LOCK_YN = '0' AND LOWER(CU_USER_ID) = ?";

    /**
     *
     * @param userId
     * @return user details
     */
    public CrmUser getUserDetailsByUserId(final String userId) {
        CrmUser user = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", QRY_USER_INFO_BY_USER_ID, userId);
            user = (CrmUser) executeQuery(con, QRY_USER_INFO_BY_USER_ID, new Object[]{userId}, CrmUser.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving dashboard figures => {}", e);
        } finally {
            closeDbConn(con);
        }
        return user;
    }

    public int updateUserLoginInfo(final UserInfo user) throws Exception {
        Connection con = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String sql = "UPDATE M_CRM_USER SET CU_PASSWORD = ?, CU_PWD_RESET_TOKEN = ?, CU_LAST_LOGIN_DT = SYSDATE WHERE CU_USER_ID = ?";
            Object[] params = new Object[]{user.getPassword(), user.getUserPwdResetToken(), user.getUserId()};
            recCnt = executeUpdate(con, sql, params);
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while update User Login Details  => {}", e);
            throw e;
        } finally {
            closeDBComp(null, null, con);
        }
        return recCnt;
    }

    private static final String DEL_LOGGED_IN_AGENT = "DELETE FROM T_CRM_AGENTS WHERE CA_USER_ID = ?";
    private static final String INS_LOGGED_IN_AGENT = "INSERT INTO T_CRM_AGENTS(CA_USER_ID, CA_CR_UID, CA_CR_DT) VALUES (?, ?, SYSDATE)";

    public int insertCrmAgents(final UserInfo user) throws Exception {
        Connection con = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            PreparedStatement ps = con.prepareStatement(DEL_LOGGED_IN_AGENT);
            ps.setString(1, user.getUserId());
            ps.executeUpdate();
            ps.close();
            LOGGER.info("Query :: {} [{}]", INS_LOGGED_IN_AGENT, user.getUserId());
            Object[] params = new Object[]{user.getUserId(), user.getUserId()};
            recCnt = executeUpdate(con, INS_LOGGED_IN_AGENT, params);
        } catch (Exception e) {
            LOGGER.error("Error while Insert/Update Crm Agents => {}", e);
            throw e;
        } finally {
            closeDBComp(null, null, con);
        }
        return recCnt;
    }

    public UserInfo getUserNameDetails(final String ipAddress) {
        UserInfo user = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CU_USER_ID userId, CU_LDAP_ID userLdapId, CU_USER_NAME userName, NVL(CU_AMDIN_YN, 0) userAdminYn, CU_TEL_NO userTelNo FROM M_CRM_USER WHERE CU_LOCK_YN = '0' AND CU_STATIC_IP = ? ");
            LOGGER.info("Query :: {} [{}]", query, ipAddress);
            user = (UserInfo) executeQuery(con, query.toString(), new Object[]{ipAddress}, UserInfo.class);
        } catch (Exception e) {
            LOGGER.error("Error while getting userName {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return user;
    }

     public CrmUser getUserMenuByUserId(final String userId) {
        CrmUser user = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CU_USER_ID userId, NVL(CU_APPL_MODULES, '[]') userApplModules FROM M_CRM_USER where CU_USER_ID = ?");
            LOGGER.info("Query :: {} [{}]", query, userId);
            user = (CrmUser) executeQuery(con, query.toString(), new Object[]{userId}, CrmUser.class);
        } catch (Exception e) {
            LOGGER.error("Error while getting userName {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return user;
    }

   //commented for corporate
    /*public List<Customer> loadInsuredList(final String crmId, Integer start, Integer end, final String search, final boolean isCheckCount, final String source) {
        List<Customer> list = null;
        Connection con = null;
        String sql = null;
        try {
            Object[] data = null;
            List<Object> paramList = new LinkedList<>();
            if (isCheckCount) {
                sql = "SELECT COUNT(INF_CIVIL_ID) as age FROM M_INSURED_INFO ";
            } else {
                sql = "SELECT "
                        + "(SELECT LISTAGG(PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', REGEXP_SUBSTR(INF_CRM_ID, '[^,]+', 1, LEVEL)), ',') WITHIN GROUP(ORDER BY LEVEL) FROM DUAL CONNECT BY REGEXP_SUBSTR(INF_CRM_ID, '[^,]+', 1, LEVEL) IS NOT NULL) as crmDesc, "
                        + "INF_CRM_ID crmId, INF_NAME as name, INF_START_YEAR as startYear, "
                        + "INF_CIVIL_ID as civilId, INF_PREFER_LANG preferLang, PKG_REP_UTILITY.FN_GET_AC_NAME('NATIONALITY', INF_NATIONALITY) as nationality, "
                        + "INF_WORK_PLACE as workPlace, INF_MOBILE_NO as mobileNo, INF_TEL_NO as telNo, INF_EMAIL_ID as emailId, INF_FAX_NO as faxNo, "
                        + "INF_MOBILE_NO_1 as mobileNo1, INF_MOBILE_NO_2 as mobileNo2, INF_STATUS as status "
                        + "FROM M_INSURED_INFO ";
            }
            if (StringUtils.isNotBlank(crmId)) {
                sql += "AND INSTR(INF_CRM_ID, ?) > 0 ";
                paramList.add(crmId);
            }
            if (StringUtils.isNoneBlank(source)) {
                sql += "WHERE INF_SOURCE = ? ";
                paramList.add(source);
            }
            if (StringUtils.isNoneBlank(search)) {
                if(!paramList.isEmpty()) {
                    sql += "AND ";
                } else {
                    sql += "WHERE ";
                }
                sql += "LOWER(INF_NAME||INF_CIVIL_ID||INF_MOBILE_NO||INF_MOBILE_NO_1||INF_MOBILE_NO_2||INF_EMAIL_ID) LIKE ? ";
                paramList.add("%" + search.toLowerCase() + "%");
            }
            if (!isCheckCount) {
                sql += "ORDER BY INF_NAME OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
                paramList.add(start);
                paramList.add(end);
            }
            con = getDBConnection(getDataSource());
            data = paramList.toArray();
            LOGGER.info("{} {}", sql, data);
            list = executeList(con, sql, data, Customer.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving policy list => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }*/

     public List<Customer> loadInsuredList(Integer start, Integer end, final String search, final boolean isCheckCount, final String source) {
         List<Customer> list = null;
         Connection con = null;
         String sql = null;
         try {
             Object[] data = null;
             List<Object> paramList = new LinkedList<>();
             if (isCheckCount) {
                 sql = "SELECT COUNT(INF_CIVIL_ID) as age FROM M_INSURED_INFO ";
             } else {
                 sql = "SELECT INF_NAME as name, INF_START_YEAR as startYear, INF_CIVIL_ID as civilId, INF_PREFER_LANG preferLang, "
                         + "PKG_REP_UTILITY.FN_GET_AC_NAME('NATIONALITY', INF_NATIONALITY) as nationality, INF_WORK_PLACE as workPlace, "
                         + "INF_MOBILE_NO as mobileNo, INF_TEL_NO as telNo, INF_EMAIL_ID as emailId, INF_FAX_NO as faxNo, "
                         + "INF_MOBILE_NO_1 as mobileNo1, INF_MOBILE_NO_2 as mobileNo2, INF_STATUS as status "
                         + "FROM M_INSURED_INFO ";
             }
             if (StringUtils.isNoneBlank(source)) {
                 sql += "WHERE INF_SOURCE = ? ";
                 paramList.add(source);
             }
             if (StringUtils.isNoneBlank(search)) {
                 if(!paramList.isEmpty()) {
                     sql += "AND ";
                 } else {
                     sql += "WHERE ";
                 }
                 sql += "LOWER(INF_NAME||INF_CIVIL_ID||INF_MOBILE_NO||INF_MOBILE_NO_1||INF_MOBILE_NO_2||INF_EMAIL_ID) LIKE ? ";
                 paramList.add("%" + search.toLowerCase() + "%");
             }
             if (!isCheckCount) {
                 sql += "ORDER BY INF_NAME OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
                 paramList.add(start);
                 paramList.add(end);
             }
             con = getDBConnection(getDataSource());
             data = paramList.toArray();
             LOGGER.info("{} {}", sql, data);
             list = executeList(con, sql, data, Customer.class);
         } catch (Exception e) {
             LOGGER.error("Error while retreiving policy list => {}", e);
         } finally {
             closeDbConn(con);
         }
         return list;
     }

    private final static String SEQ_CALL_LOG = "SELECT S_CCL_ID.NEXTVAL FROM DUAL";
    private final static String QRY_GET_EXT_BY_UID = "SELECT CU_TEL_NO FROM M_CRM_USER WHERE CU_USER_ID = ?";
    private final static String QRY_GET_UID_BY_EXT = "SELECT CU_USER_ID userId FROM M_CRM_USER WHERE CU_LOCK_YN = '0' AND CU_TEL_NO = ?";
    private final static String QRY_GET_CIVILID_BY_CALLNO = "SELECT INF_CIVIL_ID civilId, INF_NAME name FROM M_INSURED_INFO WHERE INF_MOBILE_NO = ? OR INF_MOBILE_NO_1 = ? OR INF_MOBILE_NO_2 = ?";
    private final static String SEQ_INSURED_LOG = "SELECT S_IL_ID.NEXTVAL FROM DUAL";
    //commented for corporate
    // private final static String INS_CALL_LOG = "INSERT INTO T_CRM_CALL_LOG (CCL_ID, CCL_TYPE, CCL_CRM_TYPE, CCL_EXT_ID, CCL_CALL_NO, CCL_CALL_DT, CCL_DURATION, CCL_FILE_PATH, CCL_STATUS, CCL_CR_DT, CCL_CR_UID, CCL_CIVIL_ID, CCL_REF_ID, CCL_REF_NAME, CCL_CALL_CODE, CCL_FLEX_1, CCL_FLEX_2, CCL_FLEX_3, CCL_CRM_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String INS_CALL_LOG = "INSERT INTO T_CRM_CALL_LOG (CCL_ID, CCL_TYPE, CCL_CRM_TYPE, CCL_EXT_ID, CCL_CALL_NO, CCL_CALL_DT, CCL_DURATION, CCL_FILE_PATH, CCL_STATUS, CCL_CR_DT, CCL_CR_UID, CCL_CIVIL_ID, CCL_REF_ID, CCL_REF_NAME, CCL_CALL_CODE, CCL_FLEX_1, CCL_FLEX_2, CCL_FLEX_3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String INS_INSURED_LOG = "INSERT INTO T_INSURED_LOG (IL_ID, IL_TYPE, IL_CIVIL_ID, IL_LOG_DETAILS, IL_CR_UID, IL_CR_DT, IL_FLEX_01, IL_FLEX_02, IL_FLEX_03, IL_FLEX_04) VALUES(?, ?, ?, ?, ?, SYSDATE, ?, ?, ?, ?)";
    private final static String QRY_GET_DISPOSITION_DESC = "SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CALL_TYP', ?) cclCallCodeDesc FROM DUAL";

    public Activity saveCallLog(CrmCallLog bean) {
        List<Customer> customer = null;
        Activity activity = new Activity();
        Connection con = null;
        Object[] params = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String query = null;
            if (StringUtils.isNotBlank(bean.getCclCrUid()) || bean.getCclExtId() > 0) {
                //For getting extension by user id
                if (bean.getCclExtId() == 0) {
                    LOGGER.debug("Getting extension by user id [{}]", new Object[]{bean.getCclCrUid()});
                    PreparedStatement ps = con.prepareStatement(QRY_GET_EXT_BY_UID);
                    ps.setString(1, bean.getCclCrUid());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        bean.setCclExtId(rs.getInt(1));
                    }
                    rs.close();
                    ps.close();
                } else {
                    // For getting user id by extention
                    LOGGER.debug("Getting user id by extension [{}]", new Object[]{bean.getCclExtId()});
                    UserInfo user = (UserInfo) executeQuery(con, QRY_GET_UID_BY_EXT, new Object[]{bean.getCclExtId()}, UserInfo.class);
                    if (user != null) {
                        bean.setCclCrUid(user.getUserId());
                    }
                }
            } else {
                bean.setCclCrUid(ApplicationConstants.ABANDONED_CALL_USER_ID);
            }
            //For Getting Civil Id
            if (bean.getCclCallNo().startsWith("9")) {
                bean.setCclCallNo(bean.getCclCallNo().substring(1));
            }
            LOGGER.debug("Getting civil id and name by call no [{}]", new Object[]{bean.getCclCallNo()});
            customer = executeList(con, QRY_GET_CIVILID_BY_CALLNO, new Object[]{bean.getCclCallNo(), bean.getCclCallNo(), bean.getCclCallNo()}, Customer.class);
            if (customer != null && customer.size() == 1) {
                bean.setCclCivilId(customer.get(0).getCivilId());
                bean.setCclRefId(customer.get(0).getCivilId());
                bean.setCclRefName(customer.get(0).getName());
            } else if (customer != null && customer.size() > 1) {
                bean.setCclCivilId(ApplicationConstants.MULTIPLE_CIVIL_ID_EXIST);
            }

            /* 004 - Inbound Call
             * 005 - Outbound Call
             * 008 - Missed Call
             */
            if ("I".equals(bean.getCclType())) {
                bean.setCclCrmType(CallType.INBOUND.getCode());
            } else if ("M".equals(bean.getCclType())) {
                bean.setCclCrmType(CallType.MISSED.getCode());
            } else if ("O".equals(bean.getCclType())) {
                bean.setCclCrmType(CallType.OUTBOUND.getCode());
            }
            if (bean.getUserAssociations() != null) {
                boolean isFirst = false;
                int userAssociationsCnt = bean.getUserAssociations().size();
                for (CrmCallLog.UserAssociations ua : bean.getUserAssociations()) {
                    if (isFirst) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(bean.getCclCallDtTime());
                        cal.add(Calendar.SECOND, 30);
                        bean.setCclCallDtTime(cal.getTime());
                    } else {
                        isFirst = true;
                    }
                    bean.setCclId(null);
                    query = "SELECT CCL_ID FROM T_CRM_CALL_LOG WHERE CCL_CR_UID = ? AND CCL_CALL_NO = ? AND CCL_DURATION IS NULL AND TRUNC(CCL_CR_DT) >= TRUNC(SYSDATE - 1)";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, ua.getUserId());
                    ps.setString(2, bean.getCclCallNo());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        bean.setCclId(rs.getLong(1));
                    }
                    rs.close();
                    ps.close();
                    if (bean.getCclId() != null) {
                        ua.setCclId(bean.getCclId());
                        query = "UPDATE T_CRM_CALL_LOG SET CCL_TYPE = ?, CCL_CRM_TYPE = ?, CCL_CALL_DT = ?, CCL_DURATION = ?, CCL_FILE_PATH = ?, CCL_STATUS = ?, CCL_REF_ID = ?, CCL_REF_NAME = ?, CCL_CALL_CODE = ?, CCL_FLEX_1 = ?, CCL_FLEX_2 = ?, CCL_FLEX_3 = ? WHERE CCL_ID = ?";
                        params = new Object[]{bean.getCclType(), ua.getCrmType(), new java.sql.Timestamp(bean.getCclCallDtTime().getTime()), bean.getCclDurationDesc(), bean.getCclFilePath(), bean.getCclStatus(), bean.getCclRefId(), bean.getCclRefName(), ua.getDispositionCode(), bean.getCclFlex1(), bean.getCclFlex2(), bean.getCclFlex3(), bean.getCclId()};
                        recCnt = executeUpdate(con, query, params);
                    } else {
                        CrmCallLog ccl = bean.clone();
                        ccl.setCclCrUid(ua.getUserId());
                        ccl.setCclCallCode(ua.getDispositionCode());
                        ccl.setCclCrmType(ua.getCrmType());
                        insertCallLog(ccl, con);
                        ua.setCclId(ccl.getCclId());
                    }
                }
                if (!CallType.MISSED.getCode().equals(bean.getCclCrmType())) {
                    if (userAssociationsCnt > 1) {
                        bean.setCclId(bean.getUserAssociations().get(userAssociationsCnt - 1).getCclId());
                        StringBuffer cclIds = new StringBuffer("");
                        for (CrmCallLog.UserAssociations ua : bean.getUserAssociations()) {
                            if (bean.getCclId() != null && bean.getCclId().compareTo(ua.getCclId()) != 0) {
                                cclIds.append(",").append(ua.getCclId());
                            }
                        }
                        cclIds.delete(0, 1);
                        LOGGER.debug("Updating call ref Id {} to {}", new Object[]{bean.getCclId(), cclIds});
                        query = "UPDATE T_CRM_CALL_LOG SET CCL_CALL_REF_ID = ? WHERE CCL_ID IN (" + cclIds + ")";
                        params = new Object[]{bean.getCclId()};
                        recCnt = executeUpdate(con, query, params);
                    } else if (userAssociationsCnt == 1) {
                        bean.setCclId(bean.getUserAssociations().get(0).getCclId());
                    }
                }
            }
            if ((CallType.INBOUND.getCode().equals(bean.getCclCrmType()) || CallType.OUTBOUND.getCode().equals(bean.getCclCrmType())) && bean.getUserAssociations() == null) {
                query = "SELECT CCL_ID FROM T_CRM_CALL_LOG WHERE CCL_CRM_TYPE = ? AND CCL_CALL_NO = ? AND CCL_DURATION IS NULL AND TRUNC(CCL_CR_DT) >= TRUNC(SYSDATE - 1) ORDER BY CCL_CALL_DT DESC";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, bean.getCclCrmType());
                ps.setString(2, bean.getCclCallNo());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bean.setCclId(rs.getLong(1));
                }
                rs.close();
                ps.close();
                if (bean.getCclId() == null) {// If not found
                    insertCallLog(bean, con);
                } else {
                    query = "UPDATE T_CRM_CALL_LOG SET CCL_CALL_DT = ?, CCL_DURATION = ?, CCL_FILE_PATH = ?, CCL_STATUS = ?, CCL_REF_ID = ?, CCL_REF_NAME = ?, CCL_CALL_CODE = ?, CCL_FLEX_1 = ?, CCL_FLEX_2 = ?, CCL_FLEX_3 = ? WHERE CCL_ID = ?";
                    params = new Object[]{new java.sql.Timestamp(bean.getCclCallDtTime().getTime()), bean.getCclDurationDesc(), bean.getCclFilePath(), bean.getCclStatus(), bean.getCclRefId(), bean.getCclRefName(), bean.getCclCallCode(), bean.getCclFlex1(), bean.getCclFlex2(), bean.getCclFlex3(), bean.getCclId()};
                    recCnt = executeUpdate(con, query, params);
                }
            } else if (CallType.MISSED.getCode().equals(bean.getCclCrmType()) && bean.getUserAssociations() == null) {
                insertCallLog(bean, con);
            }
            // Callback update for Missed call & Updating incoming calls which are missed by agent
            if (CallType.INBOUND.getCode().equals(bean.getCclCrmType())
                    || (CallType.OUTBOUND.getCode().equals(bean.getCclCrmType())
                    && (SystemDispositionType.NO_ANSWER.getCode().equals(bean.getCclFlex1()) || SystemDispositionType.CONNECTED.getCode().equals(bean.getCclFlex1())))) {
                LOGGER.info("Updating missed calls with current call reference [{}:{}]", new Object[]{bean.getCclId(), bean.getCclCallNo()});
                query = "UPDATE T_CRM_CALL_LOG SET CCL_CALL_REF_ID = ? WHERE CCL_CALL_NO = ? AND CCL_CRM_TYPE = ? AND CCL_CALL_REF_ID IS NULL AND TRUNC(CCL_CR_DT) >= TRUNC(SYSDATE - 2)";
                params = new Object[]{bean.getCclId(), bean.getCclCallNo(), CallType.MISSED.getCode()};
                recCnt = executeUpdate(con, query, params);
            }

            activity.setTemplate(bean.getCclCrmType());
            activity.setCallLog(bean);
            if (bean.getCclCivilId() != null && StringUtils.isNoneBlank(bean.getCclCivilId()) && !bean.getCclCivilId().equals(ApplicationConstants.MULTIPLE_CIVIL_ID_EXIST)) {
                Long seq_val = getSeqVal(con, SEQ_INSURED_LOG);
                String disposeDesc = "-";
                if (StringUtils.isNotBlank(bean.getCclCallCode())) {
                    PreparedStatement ps = con.prepareStatement(QRY_GET_DISPOSITION_DESC);
                    ps.setString(1, bean.getCclCallCode());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        disposeDesc = rs.getString(1);
                    }
                    rs.close();
                    ps.close();
                }
                String details = "Had a conversation with " + bean.getCclCallNo() + " for " + disposeDesc + ". Total duration " + bean.getCclDurationDesc();
                if (bean.getCclDuration() == 0) {
                    if (CallType.OUTBOUND.getCode().equals(bean.getCclCrmType())) {
                        details = "Call was not connected to " + bean.getCclCallNo();
                    } else {
                        details = "Missed call from " + bean.getCclCallNo();
                    }
                }
                params = new Object[]{seq_val, bean.getCclCrmType(), bean.getCclCivilId(), details, bean.getCclCrUid(), bean.getCclExtId(), bean.getCclCallNo(), bean.getCclDurationDesc(), bean.getCclFilePath()};
                QueryExecutor run = new QueryExecutor();
                run.insert(con, INS_INSURED_LOG, params);
                activity.setId(seq_val);
                activity.setCivilId(bean.getCclCivilId());
                activity.setUserId(bean.getCclRefName());
                LOGGER.info("Insert insured log => Call Id: {}, Type: {}, CallNo: {}, User Id: {}", new Object[]{seq_val, bean.getCclType(), bean.getCclCallNo(), bean.getCclCrUid()});
            }
        } catch (Exception e) {
            LOGGER.error("Error while inserting call log => {}", e);
        } finally {
            closeDbConn(con);
        }
        return activity;
    }

    /**
     *
     * @param bean
     * @param con DB connection
     * @return total no. of record inserted
     * @throws SQLException
     */
    private int insertCallLog(final CrmCallLog bean, final Connection con) throws SQLException {
        Long seq_id = getSeqVal(con, SEQ_CALL_LOG);
        LOGGER.info("Inserting call info (CDR) => Call Log Id: {}, Type: {}, Call No: {}, CivilId: {}, crtObjectId: {}, User Id: {}", new Object[]{seq_id, bean.getCclCrmType(), bean.getCclCallNo(), bean.getCclCivilId(), bean.getCclFilePath(), bean.getCclCrUid()});
        bean.setCclId(seq_id);
        //commented for corporate
        //Object[] params = new Object[]{bean.getCclId(), bean.getCclType(), bean.getCclCrmType(), bean.getCclExtId(), bean.getCclCallNo(), new java.sql.Timestamp(bean.getCclCallDtTime().getTime()), bean.getCclDurationDesc(), bean.getCclFilePath(), bean.getCclStatus(), bean.getCclCrUid(), bean.getCclCivilId(), bean.getCclRefId(), bean.getCclRefName(), bean.getCclCallCode(), bean.getCclFlex1(), bean.getCclFlex2(), bean.getCclFlex3(), bean.getCclCrmId()};
        Object[] params = new Object[]{bean.getCclId(), bean.getCclType(), bean.getCclCrmType(), bean.getCclExtId(), bean.getCclCallNo(), new java.sql.Timestamp(bean.getCclCallDtTime().getTime()), bean.getCclDurationDesc(), bean.getCclFilePath(), bean.getCclStatus(), bean.getCclCrUid(), bean.getCclCivilId(), bean.getCclRefId(), bean.getCclRefName(), bean.getCclCallCode(), bean.getCclFlex1(), bean.getCclFlex2(), bean.getCclFlex3()};
        QueryExecutor run = new QueryExecutor();
        return run.insert(con, INS_CALL_LOG, params);
    }

    public int saveCallLog(final Activity bean) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            if (bean.getCallLog().getCclId() != null) {
                StringBuilder sql = new StringBuilder("UPDATE T_INSURED_LOG SET IL_LOG_DETAILS = ? WHERE IL_ID = ?");
                Object[] params = new Object[]{bean.getMessage(), bean.getCallLog().getCclId()};
                recCnt = executeUpdate(con, sql.toString(), params);
            }
            if (bean.getId() != null) {
                String sql = "UPDATE T_CRM_CALL_LOG SET CCL_REMARKS = ? WHERE CCL_ID = ?";
                Object[] params = new Object[]{bean.getMessage(), bean.getId()};
                recCnt = executeUpdate(con, sql, params);
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while insert/update log entry => {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public Long getSeqVal(Connection con, String query) {
        PreparedStatement prs = null;
        ResultSet rs = null;
        Long id = null;
        try {
            prs = con.prepareStatement(query);
            rs = prs.executeQuery();
            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (Exception e) {
            LOGGER.error("Error while getting sequence generated value => {}", e);
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
        return id;
    }

    public List<KeyValue> loadMailData(String emailId) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        //emailId = "ravindar.singh@qicgroup.com.qa";
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{emailId};
            query = new StringBuilder("SELECT MC_CAMP_NAME key, MC_CAMP_DESC value, MT_OPENED info, MT_VISITED info1 FROM T_MAIL_TXN, T_MAIL_CAMPAIGN WHERE "
                    + "MC_ID = MT_MC_ID AND MT_TO = ? order by MT_CR_DT desc OFFSET 0 ROWS FETCH NEXT 4 ROWS ONLY");
            LOGGER.info("Query :: {} [{}]", query, emailId);
            list = executeList(con, query.toString(), date, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving ", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    /*public List<KeyValue> loadCallLogDetails(String civilId, String crmId) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] param;
            List<Object> params = new LinkedList<>();
            params.add(civilId);
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) as key,CCL_CALL_NO as value ,CCL_DURATION as info,CCL_REMARKS as info1 FROM T_CRM_CALL_LOG ");
            query.append("WHERE CCL_CIVIL_ID = ? ");
            if (StringUtils.isNotBlank(crmId)) {
                query.append("AND CCL_CRM_ID = ? ");
                params.add(crmId);
            }
            query.append("OFFSET 0 ROWS FETCH NEXT 4 ROWS ONLY");
            param = params.toArray();
            LOGGER.info("Query :: {} {}", query, Arrays.toString(param));
            list = executeList(con, query.toString(), param, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving ", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }*/

    public List<KeyValue> loadCallLogDetails(String civilId) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CCL_CRM_TYPE) as key,CCL_CALL_NO as value ,CCL_DURATION as info,CCL_REMARKS as info1 FROM T_CRM_CALL_LOG"
                    + " WHERE CCL_CIVIL_ID = ? OFFSET 0 ROWS FETCH NEXT 4 ROWS ONLY");
            LOGGER.info("Query :: {} [{}]", query, civilId);
            list = executeList(con, query.toString(), date, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving ", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

  //commented for corporate
    /*public List<CrmTasks> loadTaskData(String civilId, String crmId) {
        List<CrmTasks> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());            Object[] date = new Object[]{civilId};
            query = new StringBuilder("SELECT CT_ID ctId, CT_REF_NO ctRefNo, CT_PRIORITY ctPriority, "
                    + "CT_SUBJECT ctSubject, CT_CLOSE_DATE ctCloseDate, "
                    + "(CASE WHEN CT_STATUS = 'C' THEN CT_CLOSE_REMARKS ELSE 'Pending' END) ctCloseRemarks, "
                    + "PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_PRIORITY', CT_PRIORITY) ctPriorityDesc, "
                    + "PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CATG', CT_CATG) ctCatgDesc, "
                    + "PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_SUB_CATG', CT_SUB_CATG) ctSubCatgDesc, "
                    + "(SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_USER_ID = CT_ASSIGNED_TO) ctAssignedToDesc, "
                    + "CT_CR_DT ctCrDt "
                    + "FROM T_CRM_TASKS WHERE CT_REF_ID = ? "
                    + "ORDER BY CT_CLOSE_DATE DESC OFFSET 0 ROWS FETCH NEXT 10 ROWS ONLY");
            LOGGER.info("Query :: {} [{}]", query, civilId);
            list = executeList(con, query.toString(), date, CrmTasks.class);
            Object[] param;
            List<Object> params = new LinkedList<>();
            params.add(civilId);
            query = new StringBuilder("SELECT CT_ID ctId, CT_REF_NO ctRefNo, CT_PRIORITY ctPriority, ")
                .append("CT_SUBJECT ctSubject, CT_MESSAGE ctMessage, CT_CLOSE_DATE ctCloseDate, CT_CLOSE_REMARKS ctCloseRemarks, ")
                .append("PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_PRIORITY', CT_PRIORITY) ctPriorityDesc, ")
                .append("PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CT_CRM_ID) as ctCrmDesc, CT_CRM_ID ctCrmId, ")
                .append("PKG_REP_UTILITY.FN_GET_AC_NAME( 'CRM_CATG', CT_CATG) ctCatgDesc, ")
                .append("PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_SUB_CATG', CT_SUB_CATG) ctSubCatgDesc, ")
                .append("(SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_USER_ID = CT_ASSIGNED_TO) ctAssignedToDesc ")
                .append("FROM T_CRM_TASKS WHERE CT_STATUS = 'C' AND CT_REF_ID = ? ");
                if (StringUtils.isNotBlank(crmId)) {
                    query.append("AND CT_CRM_ID = ? ");
                    params.add(crmId);
                }
            query.append("ORDER BY CT_CLOSE_DATE DESC OFFSET 0 ROWS FETCH NEXT 10 ROWS ONLY");
            param = params.toArray();
            LOGGER.info("Query :: {} {}", query, Arrays.toString(param));
            list = executeList(con, query.toString(), param, CrmTasks.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving member 360 task", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }*/

    public List<CrmTasks> loadTaskData(String civilId) {
        List<CrmTasks> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};
            query = new StringBuilder("SELECT CT_ID ctId, CT_REF_NO ctRefNo, CT_PRIORITY ctPriority, "
                    + "CT_SUBJECT ctSubject, CT_CLOSE_DATE ctCloseDate, "
                    + "(CASE WHEN CT_STATUS = 'C' THEN CT_CLOSE_REMARKS ELSE 'Pending' END) ctCloseRemarks, "
                    + "PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_PRIORITY', CT_PRIORITY) ctPriorityDesc, "
                    + "PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CATG', CT_CATG) ctCatgDesc, "
                    + "PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_SUB_CATG', CT_SUB_CATG) ctSubCatgDesc, "
                    + "(SELECT CU_USER_NAME FROM M_CRM_USER WHERE CU_USER_ID = CT_ASSIGNED_TO) ctAssignedToDesc, "
                    + "CT_CR_DT ctCrDt "
                    + "FROM T_CRM_TASKS WHERE CT_REF_ID = ? "
                    + "ORDER BY CT_CLOSE_DATE DESC OFFSET 0 ROWS FETCH NEXT 10 ROWS ONLY");
            LOGGER.info("Query :: {} [{}]", query, civilId);
            list = executeList(con, query.toString(), date, CrmTasks.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving member 360 task", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    //Save Incoming Call
  //commented for corporate
    /*public Activity saveIncomingCall(CrmCallLog crmCallLog) {
        List<Customer> customer = null;
        Activity activity = new Activity();
        Connection con = null;
        Long seq_id = null;
        Object[] params = null;
        StringBuilder query = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            seq_id = getSeqVal(con, SEQ_CALL_LOG);

            //For Getting Civil Id
            LOGGER.info("Query :: {} [{}]", QRY_GET_CIVILID_BY_CALLNO, crmCallLog.getCclCallNo());
            customer = executeList(con, QRY_GET_CIVILID_BY_CALLNO, new Object[]{crmCallLog.getCclCallNo(), crmCallLog.getCclCallNo(), crmCallLog.getCclCallNo()}, Customer.class);
            if (customer != null && customer.size() == 1) {
                crmCallLog.setCclCivilId(customer.get(0).getCivilId());
            } else if (customer != null && customer.size() > 1) {
                crmCallLog.setCclCivilId(ApplicationConstants.MULTIPLE_CIVIL_ID_EXIST);
            }

            StringBuilder sql = new StringBuilder("INSERT INTO T_CRM_CALL_LOG (CCL_ID, CCL_EXT_ID, CCL_TYPE, CCL_CRM_TYPE, CCL_CALL_NO, CCL_CIVIL_ID, CCL_CR_UID, CCL_CR_DT, CCL_FILE_PATH, CCL_CRM_ID) VALUES ");
            sql.append("(?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?)");
            params = new Object[]{seq_id, crmCallLog.getCclExtId(), crmCallLog.getCclType(), crmCallLog.getCclCrmType(), crmCallLog.getCclCallNo(), crmCallLog.getCclCivilId(), crmCallLog.getCclCrUid(), crmCallLog.getCclFilePath(), crmCallLog.getCclCrmId()};
            QueryExecutor run = new QueryExecutor();
            run.insert(con, sql.toString(), params);
            activity.setId(seq_id);
            LOGGER.info("Insert incoming call log => Type: {}, CallNo: {}, Id: {}, crtObjectId: {}, User Id: {}, Crm Id: {}", new Object[]{crmCallLog.getCclCrmType(), crmCallLog.getCclCallNo(), seq_id, crmCallLog.getCclFilePath(), crmCallLog.getCclCrUid(), crmCallLog.getCclCrmId()});
        } catch (Exception e) {
            LOGGER.error("Error while saving incoming call ", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return activity;
    }*/

    public Activity saveIncomingCall(CrmCallLog crmCallLog) {
        List<Customer> customer = null;
        Activity activity = new Activity();
        Connection con = null;
        Long seq_id = null;
        Object[] params = null;
        StringBuilder query = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            seq_id = getSeqVal(con, SEQ_CALL_LOG);

            //For Getting Civil Id
            LOGGER.info("Query :: {} [{}]", QRY_GET_CIVILID_BY_CALLNO, crmCallLog.getCclCallNo());
            customer = executeList(con, QRY_GET_CIVILID_BY_CALLNO, new Object[]{crmCallLog.getCclCallNo(), crmCallLog.getCclCallNo(), crmCallLog.getCclCallNo()}, Customer.class);
            if (customer != null && customer.size() == 1) {
                crmCallLog.setCclCivilId(customer.get(0).getCivilId());
            } else if (customer != null && customer.size() > 1) {
                crmCallLog.setCclCivilId(ApplicationConstants.MULTIPLE_CIVIL_ID_EXIST);
            }

            StringBuilder sql = new StringBuilder("INSERT INTO T_CRM_CALL_LOG (CCL_ID, CCL_EXT_ID, CCL_TYPE, CCL_CRM_TYPE, CCL_CALL_NO, CCL_CIVIL_ID, CCL_CR_UID, CCL_CR_DT, CCL_FILE_PATH) VALUES ");
            sql.append("(?, ?, ?, ?, ?, ?, ?, SYSDATE, ?)");
            params = new Object[]{seq_id, crmCallLog.getCclExtId(), crmCallLog.getCclType(), crmCallLog.getCclCrmType(), crmCallLog.getCclCallNo(), crmCallLog.getCclCivilId(), crmCallLog.getCclCrUid(), crmCallLog.getCclFilePath()};
            QueryExecutor run = new QueryExecutor();
            run.insert(con, sql.toString(), params);
            activity.setId(seq_id);
            LOGGER.info("Insert incoming call log => Type: {}, CallNo: {}, cclId: {}, crtObjectId: {}, User Id: {}", new Object[]{crmCallLog.getCclCrmType(), crmCallLog.getCclCallNo(), seq_id, crmCallLog.getCclFilePath(), crmCallLog.getCclCrUid()});

        } catch (Exception e) {
            LOGGER.error("Error while retreiving Incoming call ", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return activity;

    }

    public List<Customer> loadInsuredListByName(String name) {
        List<Customer> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] params = new Object[]{"%" + name.toUpperCase() + "%"};
            query = new StringBuilder("SELECT INF_NAME as name, INF_START_YEAR as startYear, INF_CIVIL_ID as civilId, INF_PREFER_LANG preferLang,"
                    + "PKG_REP_UTILITY.FN_GET_AC_NAME('NATIONALITY', INF_NATIONALITY) as nationality, INF_WORK_PLACE as workPlace,"
                    + "INF_MOBILE_NO as mobileNo, INF_TEL_NO as telNo, INF_EMAIL_ID as emailId, INF_FAX_NO as faxNo "
                    + "FROM M_INSURED_INFO WHERE INF_STATUS = 'A' AND INF_NAME LIKE ? ORDER BY INF_NAME");
            LOGGER.info("query :: {} [{}]", query, name.toUpperCase());
            list = executeList(con, query.toString(), params, Customer.class);
        } catch (Exception e) {
            LOGGER.error("Error While Load Insured List", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public Long saveMAppCodes(final MAppCodes mAppCodes, Object obj) {
        LOGGER.debug("saveMAppCodes -- Enter ");
        String query;
        Connection con = null;
        Long seq_id = null;
        Object[] params = null;
        try {
            con = getDBConnection(getDataSource());
            seq_id = getSeqVal(con, "SELECT NVL((MAX(TO_NUMBER(AC_CODE)) + 1), 1) FROM M_APP_CODES WHERE AC_TYPE = '" + mAppCodes.getAcType() + "'");
            mAppCodes.setAcCode(String.valueOf(seq_id));
            query = "INSERT INTO M_APP_CODES (AC_TYPE, AC_CODE, AC_DESC, AC_LONG_DESC, AC_FLEX_01, AC_FLEX_02, AC_FLEX_03, AC_FLEX_04, AC_EFF_FM_DT, AC_EFF_TO_DT, AC_CR_UID, AC_CR_DT) VALUES (?, ?, ?, ?, TO_CHAR(TO_DATE(?, 'DD/MM/YYYY'), 'DD-MON-YYYY'), TO_CHAR(TO_DATE(?, 'DD/MM/YYYY'), 'DD-MON-YYYY'), ?, ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, SYSDATE)";
            params = new Object[]{mAppCodes.getAcType(), mAppCodes.getAcCode(), mAppCodes.getAcDesc(), mAppCodes.getAcLongDesc(), mAppCodes.getAcFlex01(), mAppCodes.getAcFlex02(), mAppCodes.getAcFlex03(), mAppCodes.getAcFlex04(), mAppCodes.getAcEffFmDt(), mAppCodes.getAcEffToDt(), mAppCodes.getAcCrUid()};

            QueryExecutor run = new QueryExecutor();
            run.insert(con, query, params);
        } catch (Exception e) {
            LOGGER.error("saveMAppCodes -- I/P params Type: {}, Code: {}, Desc: {}",
                    new Object[]{mAppCodes.getAcType(), mAppCodes.getAcCode(), mAppCodes.getAcDesc()});
            LOGGER.error("saveMAppCodes -- ", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        LOGGER.debug("saveMAppCodes -- Exit ");
        return seq_id;
    }

    public int saveCallFeedback(CrmCallLog crmCallLog) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            String sql = "UPDATE T_CRM_CALL_LOG SET CCL_REMARKS = ? WHERE CCL_ID = ?";
            Object[] params = new Object[]{crmCallLog.getCclRemarks(), crmCallLog.getCclId()};
            recCnt = executeUpdate(con, sql, params);
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while insert/update Remarks Call log => {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public List<KeyValue> getProducts() {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
          //commented for corporate
            //String query = "SELECT PROD_CODE key, PROD_DESC value , PROD_LOB_CODE info FROM M_PRODUCT ORDER BY PROD_DESC";
            String query = "SELECT PROD_CODE key, PROD_DESC value FROM M_PRODUCT ORDER BY PROD_DESC";
            list = executeList(con, query, new Object[]{}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving products => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<TRiskMedical> loadMemberPolicyDetails(String civilId) {
        List<TRiskMedical> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};
            query = new StringBuilder("SELECT TPI_POLICY_NO||' ('||TPI_UW_YEAR||')'trmPolicyNo, "
                    + " TRM_TRANS_ID trmTransId, TRM_MEM_SR_NO trmMemSrNo, TRM_MEM_ID trmMemId, TRM_CIVIL_ID trmCivilId "
                    + " FROM  T_POL_INFO, T_RISK_MEDICAL "
                    + " WHERE TPI_TRANS_ID = TRM_TRANS_ID "
                    + " AND TPI_TRAN_SR_NO = TRM_TRAN_SR_NO "
                    + " AND TRM_STATUS <> 'E'  "
                    + " AND TRM_CIVIL_ID = ? "
                    + " AND TRUNC(SYSDATE) BETWEEN TRUNC(TRM_ENTRY_DATE) AND TRUNC(TRM_EXIT_DATE) + 90 "
                    + " ORDER BY TRM_ENTRY_DATE DESC");
            LOGGER.info("Query :: {} [{}]", query, civilId);
            list = executeList(con, query.toString(), date, TRiskMedical.class);
        } catch (Exception e) {
            LOGGER.error("Error while  retreiving Member Policy details  ", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<TRiskMedical> loadMemberList(final String transId, final String memberNo) {
        return loadMemberDetails(transId, memberNo, 0);
    }

    public List<TRiskMedical> loadMemberDetails(final String transId, final String memberNo) {
        return loadMemberDetails(transId, memberNo, 1);
    }

    private final static String MEDICAL_MEMBER_LIST = "SELECT TRM_MEM_SR_NO trmMemSrNo, TRM_MEM_ID trmMemId, TRM_MEMBER_NAME ||' (SELF)' trmMemberName "
            + "FROM T_POL_INFO, T_RISK_MEDICAL WHERE TPI_TRANS_ID = TRM_TRANS_ID AND TPI_TRAN_SR_NO = TRM_TRAN_SR_NO AND TRM_STATUS <> 'E' "
            + "AND TRM_TRANS_ID = ? AND TRM_MEM_SR_NO = ? "
            + "UNION "
            //commented for corporate
            //+ "SELECT TRM_MEM_SR_NO, TRM_MEM_ID trmMemId, TRM_MEMBER_NAME || ' ('||PKG_REP_UTILITY.FN_GET_AC_NAME ('RELATION', TRM_MEM_RELATION) ||')' trmMemberName "
            + "SELECT TRM_MEM_SR_NO, TRM_MEM_ID trmMemId, TRM_MEMBER_NAME || ' ('||PKG_MED_REP_UTILITY.FN_GET_AC_NAME ('RELATION', TRM_MEM_RELATION) ||')' trmMemberName "
            + "FROM T_POL_INFO, T_RISK_MEDICAL WHERE TPI_TRANS_ID = TRM_TRANS_ID AND TPI_TRAN_SR_NO = TRM_TRAN_SR_NO AND TRM_STATUS <> 'E' "
            + "AND TRM_TRANS_ID = ? AND TRM_PARENT_SR_NO = ?";
    private final static String MEDICAL_MEMBER_DETAILS = "SELECT TRM_TRANS_ID trmTransId, TRM_MEM_SR_NO trmMemSrNo, TRM_MEM_ID trmMemId, TRM_CIVIL_ID trmCivilId, "
            + "TPI_INS_NAME trmCustName, TRM_MEMBER_NAME ||' (SELF)' trmMemberName, TRM_EMP_NO trmEmpNo, TRM_ENTRY_DATE trmEntryDate, TRM_EXIT_DATE trmExitDate, "
            + "TRM_VIP_YN trmVipYn, "
            + "(CASE WHEN TRM_PARENT_SR_NO IS NOT NULL THEN PKG_MEDICAL_UTILITY.FN_GET_PARENT_NAME(TPI_TRANS_ID, TRM_MEM_RELATION, TRM_PARENT_SR_NO) ELSE NULL END) trmParentName, "
            //commented for corporate
            //+ "PKG_REP_UTILITY.FN_GET_AC_NAME('MD_PLAN', TRM_CATG) trmPlan, PKG_REP_UTILITY.FN_GET_AC_NAME('MD_SUB_PLAN', TRM_SUB_CATG) trmSubPlan, "
            + "PKG_MED_REP_UTILITY.FN_GET_AC_NAME('MD_PLAN', TRM_CATG) trmPlan, PKG_MED_REP_UTILITY.FN_GET_AC_NAME('MD_SUB_PLAN', TRM_SUB_CATG) trmSubPlan, "
            + "(CASE WHEN TRUNC(SYSDATE) BETWEEN TRUNC(TRM_ENTRY_DATE) AND TRUNC(TRM_EXIT_DATE) THEN 'Active' ELSE 'In Active' END) trmStatus "
            + "FROM T_POL_INFO, T_RISK_MEDICAL WHERE TPI_TRANS_ID = TRM_TRANS_ID AND TPI_TRAN_SR_NO = TRM_TRAN_SR_NO AND TRM_STATUS <> 'E' "
            + "AND TRM_TRANS_ID = ? AND TRM_MEM_SR_NO = ?";
    private final static String MEDICAL_MEMBER_APP_USAGE = "SELECT 'Yes' key, DECODE(MU_LAST_LOGIN_DT, NULL, 'No', 'Yes') value FROM M_MED_MOBILE_USERS WHERE MU_CIVIL_ID = ?";

    private List<TRiskMedical> loadMemberDetails(final String transId, final String memberNo, final int type) {
        List<TRiskMedical> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Member Query => {} [{}:{}]", new Object[]{(type == 1 ? MEDICAL_MEMBER_DETAILS : MEDICAL_MEMBER_LIST), transId, memberNo});
            Object[] date = null;
            if (type == 1) {
                date = new Object[]{transId, memberNo};
                list = executeList(con, MEDICAL_MEMBER_DETAILS, date, TRiskMedical.class);
                if (list != null && !list.isEmpty()) {
                    TRiskMedical trm = list.get(0);
                    LOGGER.info("Member mobile usage. Query :: {} [{}]", new Object[]{MEDICAL_MEMBER_APP_USAGE, trm.getTrmCivilId()});
                    date = new Object[]{trm.getTrmCivilId()};
                    KeyValue kv = (KeyValue) executeQuery(con, MEDICAL_MEMBER_APP_USAGE, date, KeyValue.class);
                    if (kv != null) {
                        trm.setTrmMobileAppYn(kv.getValue());
                        trm.setTrmPortalYn(kv.getKey());
                    } else {
                        trm.setTrmMobileAppYn("No");
                        trm.setTrmPortalYn("No");
                    }
                    list.set(0, trm);
                }
            } else {
                date = new Object[]{transId, memberNo, transId, memberNo};
                list = executeList(con, MEDICAL_MEMBER_LIST, date, TRiskMedical.class);
            }
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Member details", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    private final static String MEDICAL_MEMBER_CHAT_LIST = "SELECT TO_CHAR(TC_DATE_TIME,'DD/MM/YYYY HH12:MI AM') key, TC_CHAT_BY value, TC_TYPE info, TC_TEXT info1 "
            + "FROM T_MOBILE_CHAT A WHERE TC_CIVIL_ID = ? ORDER BY TC_DATE_TIME DESC";

    public List<KeyValue> loadMemberChats(String civilId) {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};
            LOGGER.info("Query :: {} [{}]", new Object[]{MEDICAL_MEMBER_CHAT_LIST, civilId});
            list = executeList(con, MEDICAL_MEMBER_CHAT_LIST, date, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while  retreiving Member Policy details  ", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

  //commented for corporate
    /*private final static String MEDICAL_MEMBER_CLAIMS_LIST = "SELECT MB_REF_NO flex1, PKG_REP_UTILITY.FN_GET_PARA_NAME('APP_PORTAL', MB_MODE_OF_COMM) flex2, "
            + "TO_CHAR(MB_INTM_DT, 'DD/MM/YYYY HH12:MI AM') flex3, CB_MEM_NAME MB_MEMBER_NAME, SUM(NVL(CB_GROSS_AMT_LC_1, 0)) flex4, SUM(NVL(CB_NET_PAY_CURR_AMT_LC_1, 0)) flex5, "
            + "PKG_REP_UTILITY.FN_GET_PARA_NAME('REIMB_STS', PKG_MEDICAL_UTILITY.FN_GET_MB_STATUS (MB_TRANS_ID)) flex6, "
            + "(CASE WHEN CB_DOC_NO IS NOT NULL THEN FN_GET_PAYMENT_STATUS(CB_TRAN_CODE, CB_DOC_NO) ELSE NULL END) flex7, MB_TRANS_ID flex8, MB_STATUS flex9 FROM T_MED_CLM_MASTER_BATCH, T_MED_CLM_BATCH "
            + "WHERE MB_TRANS_ID = CB_TRANS_ID AND MB_TYPE = 'R' AND TRUNC(MB_INTM_DT) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) "
            + "AND CB_POL_TRANS_ID = ? AND CB_MEM_ID = ? GROUP BY MB_REF_NO, MB_TRANS_ID, MB_MODE_OF_COMM, MB_INTM_DT, CB_MEM_NAME, CB_TRAN_CODE, CB_DOC_NO, MB_STATUS";
    private final static String MEDICAL_MEMBER_PRE_APPROVALS_LIST = "SELECT PA_REF_NO flex1, PA_PROVIDER_NAME flex2, TO_CHAR(PA_INTM_DATE, 'DD/MM/YYYY HH12:MI AM') flex3, "
            + "DECODE(PA_SERVICE_TYPE, 'I', 'In Patient', 'Out Patient') flex4, PKG_MEDICAL_UTILITY.FN_GET_DIAG_DESC(PA_PRIMARY_DIAG) flex5, PA_EST_AMT_LC_1 flex6, "
            + "PA_APPR_AMT_LC_1 flex7, PKG_REP_UTILITY.FN_GET_PARA_NAME('PA_STATUS', PA_APPR_STATUS) flex8, PA_TRANS_ID flex9, PA_TRAN_SR_NO flex10 "
            + "FROM T_PRE_APPROVAL WHERE TRUNC(PA_INTM_DATE) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) AND PA_STATUS = 'A' AND PA_POL_TRANS_ID = ? AND PA_MEM_ID = ? ";
    private final static String MEDICAL_MEMBER_ENQUIRIES_LIST = "SELECT ME_REF_ID flex1, PKG_REP_UTILITY.FN_GET_PARA_NAME('APP_PORTAL', ME_PORTAL) flex2, "
            + "PKG_REP_UTILITY.FN_GET_PARA_NAME ('ENQUIRY_TYPE', ME_ENQUIRY_TYPE) flex3, ME_ENQUIRY_DETAILS flex4, PKG_REP_UTILITY.FN_GET_PARA_NAME('ENQUIRY_STS', ME_STATUS) flex5 "
            + "FROM T_MED_ENQUIRIES WHERE TRUNC(ME_REQ_DT) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) AND ME_POL_TRANS_ID = ? AND ME_MEM_ID = ?";
    private final static String MEDICAL_MEMBER_VISITS_LIST = "SELECT MV_VISIT_NO flex1, PKG_REP_UTILITY.FN_GET_CUST_NAME(MV_PROV_CODE) flex2, "
            + "TO_CHAR(MV_VISIT_DT, 'DD/MM/YYYY HH12:MI AM') flex3 FROM T_MED_VISITS WHERE TRUNC(MV_VISIT_DT) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) AND MV_TRANS_ID = ? AND MV_MEM_SR_NO = ? "
            + "ORDER BY MV_VISIT_DT DESC";*/

    private final static String MEDICAL_MEMBER_CLAIMS_LIST = "SELECT MB_REF_NO flex1, PKG_MED_REP_UTILITY.FN_GET_PARA_NAME('APP_PORTAL', MB_MODE_OF_COMM) flex2, "
            + "TO_CHAR(MB_INTM_DT, 'DD/MM/YYYY HH12:MI AM') flex3, CB_MEM_NAME MB_MEMBER_NAME, SUM(NVL(CB_GROSS_AMT_LC_1, 0)) flex4, SUM(NVL(CB_NET_PAY_CURR_AMT_LC_1, 0)) flex5, "
            + "PKG_MED_REP_UTILITY.FN_GET_PARA_NAME('REIMB_STS', PKG_MEDICAL_UTILITY.FN_GET_MB_STATUS (MB_TRANS_ID)) flex6, "
            + "(CASE WHEN CB_DOC_NO IS NOT NULL THEN FN_GET_PAYMENT_STATUS(CB_TRAN_CODE, CB_DOC_NO) ELSE NULL END) flex7, MB_TRANS_ID flex8, MB_STATUS flex9 FROM T_MED_CLM_MASTER_BATCH, T_MED_CLM_BATCH "
            + "WHERE MB_TRANS_ID = CB_TRANS_ID AND MB_TYPE = 'R' AND TRUNC(MB_INTM_DT) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) "
            + "AND CB_POL_TRANS_ID = ? AND CB_MEM_ID = ? GROUP BY MB_REF_NO, MB_TRANS_ID, MB_MODE_OF_COMM, MB_INTM_DT, CB_MEM_NAME, CB_TRAN_CODE, CB_DOC_NO, MB_STATUS";
    private final static String MEDICAL_MEMBER_PRE_APPROVALS_LIST = "SELECT PA_REF_NO flex1, PA_PROVIDER_NAME flex2, TO_CHAR(PA_INTM_DATE, 'DD/MM/YYYY HH12:MI AM') flex3, "
            + "DECODE(PA_SERVICE_TYPE, 'I', 'In Patient', 'Out Patient') flex4, PKG_MEDICAL_UTILITY.FN_GET_DIAG_DESC(PA_PRIMARY_DIAG) flex5, PA_EST_AMT_LC_1 flex6, "
            + "PA_APPR_AMT_LC_1 flex7, PKG_MED_REP_UTILITY.FN_GET_PARA_NAME('PA_STATUS', PA_APPR_STATUS) flex8, PA_TRANS_ID flex9, PA_TRAN_SR_NO flex10 "
            + "FROM T_PRE_APPROVAL WHERE TRUNC(PA_INTM_DATE) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) AND PA_STATUS = 'A' AND PA_POL_TRANS_ID = ? AND PA_MEM_ID = ? ";
    private final static String MEDICAL_MEMBER_ENQUIRIES_LIST = "SELECT ME_REF_ID flex1, PKG_MED_REP_UTILITY.FN_GET_PARA_NAME('APP_PORTAL', ME_PORTAL) flex2, "
            + "PKG_MED_REP_UTILITY.FN_GET_PARA_NAME ('ENQUIRY_TYPE', ME_ENQUIRY_TYPE) flex3, ME_ENQUIRY_DETAILS flex4, PKG_MED_REP_UTILITY.FN_GET_PARA_NAME('ENQUIRY_STS', ME_STATUS) flex5 "
            + "FROM T_MED_ENQUIRIES WHERE TRUNC(ME_REQ_DT) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) AND ME_POL_TRANS_ID = ? AND ME_MEM_ID = ?";
    private final static String MEDICAL_MEMBER_VISITS_LIST = "SELECT MV_VISIT_NO flex1, PKG_MED_REP_UTILITY.FN_GET_CUST_NAME(MV_PROV_CODE) flex2, "
            + "TO_CHAR(MV_VISIT_DT, 'DD/MM/YYYY HH12:MI AM') flex3 FROM T_MED_VISITS WHERE TRUNC(MV_VISIT_DT) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) AND MV_TRANS_ID = ? AND MV_MEM_SR_NO = ? "
            + "ORDER BY MV_VISIT_DT DESC";

    public List<FlexBean> loadMemberFigures(final String transId, final String memId, final DashboardFigures type) {
        List<FlexBean> list = null;
        Connection con = null;
        String query = null;
        if(StringUtils.isBlank(transId) || StringUtils.isBlank(memId)) {
            return null;
        }
        try {
            con = getDBConnection(getDataSource());
            Object[] data = new Object[]{transId, memId};
            LOGGER.info("Query :: {} [{}:{}]", new Object[]{type, transId, memId});
            switch (type) {
                case MEMBER_CLAIMS:
                    // Recent Claims
                    query = MEDICAL_MEMBER_CLAIMS_LIST;
                    break;
                case MEMBER_PRE_APPROVALS:
                    // Recent preApprovals
                    query = MEDICAL_MEMBER_PRE_APPROVALS_LIST;
                    break;
                case MEMBER_ENQUIRIES:
                    // Recent Enquiries
                    query = MEDICAL_MEMBER_ENQUIRIES_LIST;
                    break;
                case MEMBER_VISITS:
                    // Recent visits
                    query = MEDICAL_MEMBER_VISITS_LIST;
                    break;
                default:
                    throw new Exception("Invalid call");
            }
            list = executeList(con, query, data, FlexBean.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving member figures => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    private final static String MEDICAL_MEMBER_CLAIMS_COUNT = "SELECT COUNT(1) FROM T_MED_CLM_MASTER_BATCH "
            + "WHERE MB_TYPE = 'R' AND TRUNC(MB_INTM_DT) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) "
            + "AND EXISTS(SELECT 1 FROM T_MED_CLAIMS WHERE MC_TRANS_ID = MB_TRANS_ID AND MC_POL_TRANS_ID = ? AND MC_MEM_ID = ?)";
    private final static String MEDICAL_MEMBER_PRE_APPROVALS_COUNT = "SELECT COUNT(1) CLM_COUNT FROM T_PRE_APPROVAL WHERE TRUNC(PA_INTM_DATE) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) "
            + "AND PA_STATUS = 'A' AND PA_POL_TRANS_ID = ? AND PA_MEM_ID = ?";
    private final static String MEDICAL_MEMBER_ENQUIRIES_COUNT = "SELECT COUNT(1) FROM T_MED_ENQUIRIES WHERE TRUNC(ME_REQ_DT) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) "
            + "AND ME_POL_TRANS_ID = ? AND ME_MEM_ID = ?";
    private final static String MEDICAL_MEMBER_VISITS_COUNT = "SELECT COUNT(1)FROM T_MED_VISITS WHERE TRUNC(MV_VISIT_DT) BETWEEN TRUNC(SYSDATE) - 30 AND TRUNC(SYSDATE) "
            + "AND MV_TRANS_ID = ? AND MV_MEM_SR_NO = ?";

    public KeyValue loadCountDashBoard(final String transId, final String memId, final String memSrNo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        con = getDBConnection(getDataSource());
        KeyValue kv = new KeyValue();
        try {
            for (int d = 1; d <= 4; d++) {
                switch (d) {
                    case 1:
                        ps = con.prepareStatement(MEDICAL_MEMBER_CLAIMS_COUNT);
                        ps.setString(1, transId);
                        ps.setString(2, memId);
                        break;
                    case 2:
                        ps = con.prepareStatement(MEDICAL_MEMBER_PRE_APPROVALS_COUNT);
                        ps.setString(1, transId);
                        ps.setString(2, memId);
                        break;
                    case 3:
                        ps = con.prepareStatement(MEDICAL_MEMBER_ENQUIRIES_COUNT);
                        ps.setString(1, transId);
                        ps.setString(2, memId);
                        break;
                    case 4:
                        ps = con.prepareStatement(MEDICAL_MEMBER_VISITS_COUNT);
                        ps.setString(1, transId);
                        ps.setString(2, memSrNo);
                        break;
                }
                rs = ps.executeQuery();
                if (rs.next()) {
                    String count = rs.getString(1);
                    switch (d) {
                        case 1:
                            kv.setInfo1(count);
                            break;
                        case 2:
                            kv.setInfo2(count);
                            break;
                        case 3:
                            kv.setInfo3(count);
                            break;
                        case 4:
                            kv.setInfo4(count);
                            break;
                    }
                }
                rs.close();
                ps.close();
            }

        } catch (Exception e) {

            LOGGER.error("Error while retreiving dashboard count figures => {}", e);
        } finally {
            closeDBComp(ps, rs, con);
        }
        return kv;
    }

    public List<FlexBean> loadProviderPreApprovalDetails(String civilId) {
        List<FlexBean> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};
            query = new StringBuilder("SELECT PA_TRANS_ID flex6,PA_TRAN_SR_NO flex7,PA_REF_NO flex1,PA_INS_NAME flex2,PA_MEM_NAME flex3,NVL(PA_APPEAL_DATE,PA_INTM_DATE) flex4,"
            		//commented for corporate
                    //+ "PKG_REP_UTILITY.FN_GET_PARA_NAME('PA_STATUS',PA_APPR_STATUS)flex5 FROM T_PRE_APPROVAL WHERE "
                    + "PKG_MED_REP_UTILITY.FN_GET_PARA_NAME('PA_STATUS',PA_APPR_STATUS)flex5 FROM T_PRE_APPROVAL WHERE "
                    + "TRUNC(PA_INTM_DATE) BETWEEN TRUNC (SYSDATE) - 7 AND TRUNC(SYSDATE) AND "
                    + "PA_STATUS = 'A' AND "
                    + "PA_APPR_STATUS IN('I','P') AND PA_PROVIDER_CODE = ?");
            list = executeList(con, query.toString(), date, FlexBean.class);
        } catch (Exception e) {
            LOGGER.error("Error while  retreiving Provider Pre Approvals ", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<FlexBean> loadProviderEclaimDetails(String civilId) {
        List<FlexBean> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};
            query = new StringBuilder("SELECT MCH_MB_NO flex1,MCH_PROV_REF_NO flex2,MCH_YEAR flex3,MCH_MONTH flex4,MCH_INTM_DT flex5, "
            		//commented for corporate
                    //+ "PKG_REP_UTILITY.FN_GET_PARA_NAME ('MB_STATUS', MCH_STATUS) flex6 "
                    + "PKG_MED_REP_UTILITY.FN_GET_PARA_NAME ('MB_STATUS', MCH_STATUS) flex6 "
                    + "FROM INT_MED_CLAIM_HEAD WHERE TRUNC(MCH_INTM_DT) BETWEEN TRUNC (SYSDATE) - 7 AND TRUNC(SYSDATE) AND MCH_PROVIDER = ?");
            list = executeList(con, query.toString(), date, FlexBean.class);
        } catch (Exception e) {
            LOGGER.error("Error while  retreiving Claims Pre Approvals ", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    private final static String MEDICAL_PROVIDER_ENQUIRIES_LIST = "SELECT ME_REF_ID flex1, PKG_REP_UTILITY.FN_GET_PARA_NAME('APP_PORTAL', ME_PORTAL) flex2, "
    		//commented for corporate
    		//+ "PKG_REP_UTILITY.FN_GET_PARA_NAME('ENQUIRY_TYPE', ME_ENQUIRY_TYPE) flex3, ME_ENQUIRY_DETAILS flex4, "
            //+ "PKG_REP_UTILITY.FN_GET_PARA_NAME('ENQUIRY_STS', ME_STATUS) flex5 FROM T_MED_ENQUIRIES "
            + "PKG_MED_REP_UTILITY.FN_GET_PARA_NAME('ENQUIRY_TYPE', ME_ENQUIRY_TYPE) flex3, ME_ENQUIRY_DETAILS flex4, "
            + "PKG_MED_REP_UTILITY.FN_GET_PARA_NAME('ENQUIRY_STS', ME_STATUS) flex5 FROM T_MED_ENQUIRIES "
            + "WHERE TRUNC(ME_REQ_DT) BETWEEN TRUNC(SYSDATE) - 7 AND TRUNC(SYSDATE) AND ME_CUST_CODE = ?";

    public List<FlexBean> loadProviderEnquiries(String civilId) {
        List<FlexBean> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{civilId};
            list = executeList(con, MEDICAL_PROVIDER_ENQUIRIES_LIST, date, FlexBean.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving Claims Pre Enquiries ", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> getDiagnosisList(String flex1) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{"%" + flex1 + "%"};
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("select diag_code key,diag_desc value from m_diagnosis"
                    + " WHERE upper(diag_desc || diag_code) like upper"
                    + " (?)  and rownum<=25");
            LOGGER.info("Query :: {} {} []", query, params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public KeyValue callPharmacyApproval(String memberId, String provCode, String diagCode, String details, int amount, String userId, String qatarId) {
        Connection con = null;
        KeyValue kv = new KeyValue();
        CallableStatement cs = null;
        String sql = null, errorMsg = null;
        try {
            con = getDBConnection(getDataSource());
            con.setAutoCommit(true);
            int indx = 0;
            sql = "{call PR_CREATE_PHARMACY_PA(?,?,?,?,?,?,?,?,?,?)}";
            cs = con.prepareCall(sql);
            LOGGER.info("Input for PR_CREATE_PHARMACY ==> {}:{}:{}:{}:{}", new Object[]{memberId, provCode, diagCode, details, amount, userId, qatarId});
            cs.setString(++indx, provCode);
            if (StringUtils.isNoneBlank(memberId)) {
                cs.setString(++indx, memberId);
            } else {
                cs.setNull(++indx, Types.VARCHAR);
            }
            if (StringUtils.isNoneBlank(qatarId)) {
                cs.setString(++indx, qatarId);

            } else {
                cs.setNull(++indx, Types.VARCHAR);
            }
            //cs.setString(++indx, memberId);
            cs.setString(++indx, diagCode);
            cs.setString(++indx, details);
            cs.setInt(++indx, amount);
            cs.setString(++indx, userId);
            cs.registerOutParameter(++indx, Types.VARCHAR);
            cs.registerOutParameter(++indx, Types.NUMERIC);
            cs.registerOutParameter(++indx, Types.VARCHAR);
            cs.execute();

            kv.setInfo(cs.getString(8));
            kv.setInfo1(cs.getString(9));
            kv.setText(cs.getString(10));
        } catch (Exception e) {
            LOGGER.error("", e);
            kv.setText("Unable to procced");
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
            } catch (Exception e) {
            }
        }
        return kv;
    }

    public KeyValue getGeneralApproval(String memberId, String searchType) {
        KeyValue bean = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = new Object[]{memberId};
            query = new StringBuilder("SELECT TPI_POLICY_NO key, TRM_TRANS_ID value, TRM_TRAN_SR_NO info, TRM_MEM_SR_NO info1");
            query.append(" FROM T_POL_INFO, T_RISK_MEDICAL ");
            query.append(" WHERE TPI_TRANS_ID = TRM_TRANS_ID ");
            query.append(" AND TPI_TRAN_SR_NO = TRM_TRAN_SR_NO ");

            if ("TRM_MEM_ID".equalsIgnoreCase(searchType)) {
                query.append(" AND TRM_MEM_ID =?");
            } else {
                query.append(" AND TRM_CIVIL_ID =?");
            }

            query.append(" AND TRUNC(SYSDATE) BETWEEN TRUNC(TRM_ENTRY_DATE) AND TRUNC(TRM_EXIT_DATE) ");
            query.append(" AND TRM_STATUS = 'A'");
            bean = (KeyValue) executeQuery(con, query.toString(), date, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while  General Approval", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return bean;
    }

  //commented for corporate
   /* public int savememberNotification(String civilId, String flex2, String flex3, String crmId) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = null;
        int i = 0, recCnt = 0;
        String type = ApplicationConstants.PUSH_NOTIFICATION_TYPE;
        String status = ApplicationConstants.PUSH_NOTIFICATION_STATUS;
        try {
            con = getDBConnection(getDataSource());
            Long seq_id = getSeqVal(con, SEQ_INSURED_LOG);
            sql = "INSERT INTO T_INSURED_LOG (IL_ID, IL_CRM_ID, IL_TYPE, IL_CIVIL_ID, IL_LOG_DETAILS,IL_FLEX_01, IL_CR_UID, IL_CR_DT) "
                    + "VALUES (?, ?, ?, ?, ?, ?, SYSDATE)";
            Object[] params = new Object[]{seq_id, crmId, type, civilId.trim(), flex2, status, flex3};
            recCnt = executeUpdate(con, sql, params);
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while insert/Notification=> {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }*/

    public int savememberNotification(String civilId, String flex2, String flex3) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = null;
        int i = 0, recCnt = 0;
        String type = ApplicationConstants.PUSH_NOTIFICATION_TYPE;
        String status = ApplicationConstants.PUSH_NOTIFICATION_STATUS;
        try {
            con = getDBConnection(getDataSource());
            Long seq_id = getSeqVal(con, SEQ_INSURED_LOG);
            sql = "INSERT INTO T_INSURED_LOG (IL_ID, IL_TYPE, IL_CIVIL_ID, IL_LOG_DETAILS,IL_FLEX_01, IL_CR_UID, IL_CR_DT) "
                    + "VALUES (?, ?, ?, ?, ?, ?, SYSDATE)";
            Object[] params = new Object[]{seq_id, type, civilId.trim(), flex2, status, flex3};
            recCnt = executeUpdate(con, sql, params);
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while insert/Notification=> {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public List<KeyValue> getCustomerTypeList() {
        List<KeyValue> customerType = new ArrayList<>();
        customerType.add(new KeyValue("", "All"));
        customerType.add(new KeyValue("M", "Member"));
        customerType.add(new KeyValue("P", "Provider"));
        return customerType;
    }

    public String getDataSource() {
        return dataSource;
    }

    private final static String UPD_LOGOUT = "UPDATE T_CRM_LOGIN_HISTORY SET CLH_LOGOUT_DT = SYSDATE, CLH_LOGOUT_BY = ? WHERE CLH_SESSION_ID = ? AND CLH_USER_ID = ? AND CLH_LOGOUT_DT IS NULL";

    public void logout(LoginHistory lh) {
        Connection con = null;
        PreparedStatement ps = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            ps = con.prepareStatement(UPD_LOGOUT);
            ps.setString(1, lh.getLogoutBy());
            ps.setString(2, lh.getSessionId());
            ps.setString(3, lh.getUserId());
            recCnt = ps.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error while logout => {}", e);
        } finally {
            closeDbConn(con);
        }
    }

  //commented for corporate
    /*public List<KeyValue> loadProviderList(String crmId, String searchValue) {
        Connection con = null;
        List<KeyValue> providernameList = new ArrayList<>();
        try {
            con = getDBConnection(getDataSource());
            if (StringUtils.isNoneEmpty(searchValue)) {
                String query = "SELECT INF_CIVIL_ID key, INF_NAME value from M_INSURED_INFO where INF_NAME LIKE ? AND INF_SOURCE = 'P' AND INSTR(INF_CRM_ID, ?) > 0";
                Object[] params = new Object[]{"%" + searchValue + "%", crmId};
                providernameList = executeList(con, query, params, KeyValue.class);
            }
        } catch (Exception e) {
            LOGGER.error("Error => {}", e);
        } finally {
            closeDbConn(con);
        }
        return providernameList;
    }*/

    public List<KeyValue> loadProviderList(String searchValue) {
        Connection con = null;
        List<KeyValue> providernameList = new ArrayList<>();
        try {
            con = getDBConnection(getDataSource());
            if (StringUtils.isNoneEmpty(searchValue)) {
                String query = "SELECT INF_CIVIL_ID key, INF_NAME value from M_INSURED_INFO where INF_NAME LIKE ? AND INF_SOURCE = 'P'";
                Object[] params = new Object[]{"%" + searchValue + "%"};
                providernameList = executeList(con, query, params, KeyValue.class);
            }
        } catch (Exception e) {
            LOGGER.error("Error => {}", e);
        } finally {
            closeDbConn(con);
        }
        return providernameList;
    }

  //commented for corporate
    /*public int linkCustomerNewNumber(CrmCallLog callLog) {
        Connection con = null;
        PreparedStatement ps = null;
        CallableStatement callStmt = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            con.setAutoCommit(false);
            Object[] params = new Object[]{callLog.getCclCivilId()};
            Customer customer = null;
            List<Customer> customerList = null;
            customerList = executeList(con, "SELECT INF_MOBILE_NO as mobileNo, INF_MOBILE_NO_1 as mobileNo1, INF_MOBILE_NO_2 as mobileNo2 FROM M_INSURED_INFO WHERE INF_CIVIL_ID = ?", params, Customer.class);
            if(customerList != null && !customerList.isEmpty()) {
                customer = customerList.get(0);
                LOGGER.info("Retrieved customer({}) mobile numbers [{}:{}:{}]", new Object[]{callLog.getCclCivilId(), customer.getMobileNo(), customer.getMobileNo1(), customer.getMobileNo2()});
                String column = null;
                if(StringUtils.isNotBlank(callLog.getCclType())) {
                    if(callLog.getCclType().startsWith("MOBILE_NO")) {
                        column = "INF_" + callLog.getCclType();
                    } else {
                        throw new Exception("Tempered value");
                    }
                } else {
                    if(customer.getMobileNo() == null) {
                        column = "INF_MOBILE_NO";
                    } else if(customer.getMobileNo1() == null) {
                        column = "INF_MOBILE_NO_1";
                    } else {
                        column = "INF_MOBILE_NO_2";
                    }
                }
                ps = con.prepareStatement("UPDATE M_INSURED_INFO SET " + column + " = ? WHERE INF_CIVIL_ID = ?");
                ps.setString(1, callLog.getCclCallNo());
                ps.setString(2, callLog.getCclCivilId());
                recCnt = ps.executeUpdate();
                LOGGER.debug("Mobile number {} linked to {} and updated in {}. Status: {}", new Object[]{callLog.getCclCallNo(), callLog.getCclCivilId(), column, recCnt});
                if(recCnt > 0) {
                    params = new Object[]{callLog.getCclCivilId(), callLog.getCclCivilId(), callLog.getCclRefName(), callLog.getCclCrmId(), callLog.getCclCallNo()};
                    executeUpdate(con, "UPDATE T_CRM_CALL_LOG SET CCL_CIVIL_ID = ?, CCL_REF_ID = ?, CCL_REF_NAME = ? WHERE CCL_CRM_ID = ? AND CCL_CALL_NO = ? AND TRUNC(CCL_CR_DT) >= TRUNC(SYSDATE - 35)", params);

                    String msg = null;
                    String query =
                        "DECLARE "
                            + "P_ERROR VARCHAR2(1000) := NULL; "
                            + "CALL_NO VARCHAR2(20) := NULL; "
                        + "BEGIN "
                        + "CALL_NO := ?; "
                        + "INSERT INTO T_INSURED_LOG (IL_ID, IL_CRM_ID, IL_TYPE, IL_CIVIL_ID, IL_LOG_DETAILS, IL_CR_UID, IL_CR_DT, IL_FLEX_01, IL_FLEX_02, IL_FLEX_03, IL_FLEX_04) "
                        + "SELECT S_IL_ID.NEXTVAL, CCL_CRM_ID, CCL_CRM_TYPE, CCL_CIVIL_ID, "
                        + "CASE WHEN CCL_DURATION = '00:00:00' AND CCL_CRM_TYPE = '005' THEN 'Call was not connected to ' || CCL_CALL_NO "
                        + "WHEN CCL_DURATION = '00:00:00' THEN 'Missed call from ' || CCL_CALL_NO ELSE "
                        + "'Had a conversation with ' || CCL_CALL_NO || ' for ' || PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CALL_TYP', CCL_CALL_CODE) || '. Total duration ' || CCL_DURATION "
                        + "END, "
                        + "CCL_CR_UID, CCL_CR_DT, CCL_EXT_ID, CCL_CALL_NO, CCL_DURATION, CCL_FILE_PATH "
                        + "FROM T_CRM_CALL_LOG WHERE CCL_CALL_NO = CALL_NO AND TRUNC(CCL_CR_DT) >= TRUNC(SYSDATE - 35); "
                        + "EXCEPTION "
                            + "WHEN OTHERS THEN "
                                + "P_ERROR := 'Error in moving data : '|| SQLERRM; "
                        + "? := P_ERROR; "
                        + "END;";
                    LOGGER.info("Query :: {} [{}]", new Object[]{query, callLog.getCclCallNo()});
                    callStmt = con.prepareCall(query);
                    callStmt.setString(1, callLog.getCclCallNo());
                    callStmt.registerOutParameter(2, Types.VARCHAR);
                    callStmt.executeQuery();
                    msg = callStmt.getString(2);
                    LOGGER.info("Mobile number {} link => Error: {}", new Object[]{callLog.getCclCallNo(), msg});
                }
            }
            con.commit();
        } catch (Exception e) {
            if(con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {

                }
            }
            recCnt = 0;
            LOGGER.error("Error while linking mobile no to existing customer => {}", e);
        } finally {
            closeDBComp(ps, null, null);
            closeDBCallSt(callStmt, con);
        }
        return recCnt;
    }*/

    public int linkCustomerNewNumber(CrmCallLog callLog) {
        Connection con = null;
        PreparedStatement ps = null;
        CallableStatement callStmt = null;
        int recCnt = 0;
        try {
            con = getDBConnection(getDataSource());
            con.setAutoCommit(false);
            Object[] params = new Object[]{callLog.getCclCivilId()};
            Customer customer = null;
            List<Customer> customerList = null;
            customerList = executeList(con, "SELECT INF_MOBILE_NO as mobileNo, INF_MOBILE_NO_1 as mobileNo1, INF_MOBILE_NO_2 as mobileNo2 FROM M_INSURED_INFO WHERE INF_CIVIL_ID = ?", params, Customer.class);
            if(customerList != null && !customerList.isEmpty()) {
                customer = customerList.get(0);
                LOGGER.info("Retrieved customer({}) mobile numbers [{}:{}:{}]", new Object[]{callLog.getCclCivilId(), customer.getMobileNo(), customer.getMobileNo1(), customer.getMobileNo2()});
                String column = null;
                if(StringUtils.isNotBlank(callLog.getCclType())) {
                    if(callLog.getCclType().startsWith("MOBILE_NO")) {
                        column = "INF_" + callLog.getCclType();
                    } else {
                        throw new Exception("Tempered value");
                    }
                } else {
                    if(customer.getMobileNo() == null) {
                        column = "INF_MOBILE_NO";
                    } else if(customer.getMobileNo1() == null) {
                        column = "INF_MOBILE_NO_1";
                    } else {
                        column = "INF_MOBILE_NO_2";
                    }
                }
                ps = con.prepareStatement("UPDATE M_INSURED_INFO SET " + column + " = ? WHERE INF_CIVIL_ID = ?");
                ps.setString(1, callLog.getCclCallNo());
                ps.setString(2, callLog.getCclCivilId());
                recCnt = ps.executeUpdate();
                LOGGER.debug("Mobile number {} linked to {} and updated in {}. Status: {}", new Object[]{callLog.getCclCallNo(), callLog.getCclCivilId(), column, recCnt});
                if(recCnt > 0) {
                    params = new Object[]{callLog.getCclCivilId(), callLog.getCclCivilId(), callLog.getCclRefName(), callLog.getCclCallNo()};
                    executeUpdate(con, "UPDATE T_CRM_CALL_LOG SET CCL_CIVIL_ID = ?, CCL_REF_ID = ?, CCL_REF_NAME = ? WHERE CCL_CALL_NO = ? AND TRUNC(CCL_CR_DT) >= TRUNC(SYSDATE - 35)", params);

                    String msg = null;
                    String query =
                        "DECLARE "
                            + "P_ERROR VARCHAR2(1000) := NULL; "
                            + "CALL_NO VARCHAR2(20) := NULL; "
                        + "BEGIN "
                        + "CALL_NO := ?; "
                        + "INSERT INTO T_INSURED_LOG (IL_ID, IL_TYPE, IL_CIVIL_ID, IL_LOG_DETAILS, IL_CR_UID, IL_CR_DT, IL_FLEX_01, IL_FLEX_02, IL_FLEX_03, IL_FLEX_04) "
                        + "SELECT S_IL_ID.NEXTVAL, CCL_CRM_TYPE, CCL_CIVIL_ID, "
                        + "CASE WHEN CCL_DURATION = '00:00:00' AND CCL_CRM_TYPE = '005' THEN 'Call was not connected to ' || CCL_CALL_NO "
                        + "WHEN CCL_DURATION = '00:00:00' THEN 'Missed call from ' || CCL_CALL_NO ELSE "
                        + "'Had a conversation with ' || CCL_CALL_NO || ' for ' || PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CALL_TYP', CCL_CALL_CODE) || '. Total duration ' || CCL_DURATION "
                        + "END, "
                        + "CCL_CR_UID, CCL_CR_DT, CCL_EXT_ID, CCL_CALL_NO, CCL_DURATION, CCL_FILE_PATH "
                        + "FROM T_CRM_CALL_LOG WHERE CCL_CALL_NO = CALL_NO AND TRUNC(CCL_CR_DT) >= TRUNC(SYSDATE - 35); "
                        + "EXCEPTION "
                            + "WHEN OTHERS THEN "
                                + "P_ERROR := 'Error in moving data : '|| SQLERRM; "
                        + "? := P_ERROR; "
                        + "END;";
                    LOGGER.info("Query :: {} [{}]", new Object[]{query, callLog.getCclCallNo()});
                    callStmt = con.prepareCall(query);
                    callStmt.setString(1, callLog.getCclCallNo());
                    callStmt.registerOutParameter(2, Types.VARCHAR);
                    callStmt.executeQuery();
                    msg = callStmt.getString(2);
                    LOGGER.info("Mobile number {} link => Error: {}", new Object[]{callLog.getCclCallNo(), msg});
                }
            }
            con.commit();
        } catch (Exception e) {
            if(con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {

                }
            }
            recCnt = 0;
            LOGGER.error("Error while linking mobile no to existing customer => {}", e);
        } finally {
            closeDBComp(ps, null, null);
            closeDBCallSt(callStmt, con);
        }
        return recCnt;
    }

  //added for corporate
    private int saveLogOrActivity(final Activity activity) {

        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            sql = " INSERT INTO T_INSURED_LOG   (IL_ID, IL_TYPE, IL_CIVIL_ID, IL_LOG_DETAILS, IL_CR_UID, IL_CR_DT,IL_CRM_ID,IL_DEAL_ID)"
            		+ " VALUES(S_IL_ID.NEXTVAL, ? , ? , ? , ?, SYSDATE, ? ,?)";
            ps = con.prepareStatement(sql);
            ps.setString(++i, activity.getTemplate());
            ps.setString(++i, activity.getCivilId());
            ps.setString(++i, activity.getMessage());
            ps.setString(++i, activity.getUserId());
            ps.setString(++i, activity.getCrmId());
            ps.setString(++i, activity.getDealId());

            LOGGER.info("Query :: {} [{},{},{},{},{},{}]", new Object[]{sql, activity.getTemplate(),activity.getCivilId(),activity.getMessage(),activity.getUserId(),activity.getCrmId(),activity.getDealId()});
            recCnt = ps.executeUpdate();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error during save deal activity => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }


}
