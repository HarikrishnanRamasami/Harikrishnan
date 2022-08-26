/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.corporate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.corporate.vo.CustomerContact;
import qa.com.qic.anoud.corporate.vo.GIPolicyBean;
import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.anoud.vo.Policy;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.utility.helpers.LogUtil;

/**
 * @author Karthik Jayakumar
 *
 */
public class AnoudGIDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(AnoudGIDAO.class);

    private final String dataSource;
    private transient Map<String, Object> session;

    public AnoudGIDAO(String dataSource) {
        this.dataSource = dataSource;
    }


    public List<KeyValue> loadGIPremiumVsClaimsFigures(final String civillid, String searchBy, String fromyear, String toYear) {

    	List<KeyValue> list = null;
        Connection con = null;
        String sql = null;
        String inscode = null;
        Object[] params =  null;


        try{

          inscode = getInsuredCode(civillid);

          if(inscode != null){

        	 con = getDBConnection(getDataSource());
             sql = "SELECT"
            	        +" UW_YEAR KEY, "
            	        +" SUM(NVL(GROSS_PREM,0)) VALUE, "
            	        +" SUM(NVL(CLM_INCURRED,0)) INFO1 "
            	        +" FROM V_CUST_CLM_EXP "
            	        +" WHERE  NVL(INS_CODE,'0') = NVL(?, INS_CODE) "
            	        +" AND CUST_CODE = NVL(?,CUST_CODE) "
            	        +" AND NVL(BROK_CODE,0) = NVL(?, NVL(BROK_CODE,0)) "
            	        +" AND UW_YEAR BETWEEN ? AND ? "
            	        +" GROUP BY UW_YEAR ORDER by 1 ";

              params =  getGiSearchParameters(inscode,searchBy,fromyear,toYear);

              LOGGER.info("Query :: {} [{}]", sql, params);
              list = executeList(con, sql, params, KeyValue.class);

        }

        } catch (Exception e){
        	LOGGER.error("Error while retreiving premium vs claims data => {}", e);
        }finally {
            sql = null;
            closeDbConn(con);
        }

        return list;
    }

    public List<KeyValue> loadGILOBWiseCountFigures(final String civilId,String searchBy, String fromYear, String toYear) {

    	List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        String inscode = null;
        Object[] params = null;

        try{

          inscode = getInsuredCode(civilId);

          if(inscode != null){

             con = getDBConnection(getDataSource());

             query = new StringBuilder(" ");
             query.append("SELECT LOB_CODE key , ");
             query.append("PKG_REP_UTILITY.FN_GET_LOB_NAME(LOB_CODE) value , ");
             query.append("DEPT_CODE info1, ");
             query.append("PKG_REP_UTILITY.FN_GET_DEPT_NAME(COMP_CODE, DIVN_CODE, DEPT_CODE) info2, ");
             query.append("MAX(UW_YEAR) info3, ");
             query.append("COUNT(DISTINCT POL_NO) info4, ");
             query.append("SUM(NVL(GROSS_PREM,0)) info5, ");
             query.append("SUM(NVL(QIC_RETN_PREM,0)) info6, ");
             query.append("SUM(NVL(CLM_INCURRED,0)) info7, ");
             query.append("SUM(NVL(QIC_RETN_CLM,0)) info8, ");
             query.append("ROUND((CASE WHEN SUM(NVL(GROSS_PREM,0)) = 0 THEN 0 ELSE SUM(NVL(CLM_INCURRED,0)) / SUM(NVL(GROSS_PREM,0))END) * 100,2) info9, ");
             query.append("ROUND((CASE WHEN SUM(NVL(QIC_RETN_PREM,0)) = 0 THEN 0 ELSE SUM(NVL(QIC_RETN_CLM,0)) / SUM(NVL(QIC_RETN_PREM,0)) END) * 100,2) info10, ");
             query.append("SUM(CLM_COUNT) clmCount, ROUND(SUM(NVL(CLM_OS,0)),2) info11 ");
             query.append("FROM V_CUST_CLM_EXP ");
             query.append("WHERE  NVL(INS_CODE,'0') = NVL(?, INS_CODE) ");
             query.append("AND CUST_CODE = NVL(?,CUST_CODE) ");
             query.append("AND NVL(BROK_CODE,0) = NVL(?, NVL(BROK_CODE,0)) ");
             query.append("AND UW_YEAR BETWEEN ? AND ? ");
             query.append("GROUP BY COMP_CODE, DIVN_CODE, LOB_CODE, DEPT_CODE ");
             query.append("ORDER by  LOB_CODE    ");

             params =  getGiSearchParameters(inscode,searchBy,fromYear,toYear);


            LOGGER.info("Query :: {} [{}]", query, params);
            list = executeList(con, query.toString(), params, KeyValue.class);
         }

        } catch (Exception e){
        	LOGGER.error("Error while retreiving LOB Wise Summary => {}", e);
        }finally {
            query = null;
            closeDbConn(con);
        }

        return list;
    }

    public List<KeyValue> loadGIProductBWiseCountFigures(final String civilId,String searchBy, String fromYear, String toYear) {

    	List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        String inscode = null;
        Object[] params = null;

        try{

         inscode = getInsuredCode(civilId);
         if(inscode != null){

        	 con = getDBConnection(getDataSource());

             query = new StringBuilder(" ");
             query.append("SELECT PROD_CODE key , ");
             query.append("PKG_REP_UTILITY.FN_GET_PROD_NAME(PROD_CODE) value , ");
             query.append("DEPT_CODE info1, ");
             query.append("PKG_REP_UTILITY.FN_GET_DEPT_NAME(COMP_CODE, DIVN_CODE, DEPT_CODE) info2, ");
             query.append("MAX(UW_YEAR) info3, ");
             query.append("COUNT(DISTINCT POL_NO) info4, ");
             query.append("SUM(NVL(GROSS_PREM,0)) info5, ");
             query.append("SUM(NVL(QIC_RETN_PREM,0)) info6, ");
             query.append("SUM(NVL(CLM_INCURRED,0)) info7, ");
             query.append("SUM(NVL(QIC_RETN_CLM,0)) info8, ");
             query.append("ROUND((CASE WHEN SUM(NVL(GROSS_PREM,0)) = 0 THEN 0 ELSE SUM(NVL(CLM_INCURRED,0)) / SUM(NVL(GROSS_PREM,0))END) * 100,2) info9, ");
             query.append("ROUND((CASE WHEN SUM(NVL(QIC_RETN_PREM,0)) = 0 THEN 0 ELSE SUM(NVL(QIC_RETN_CLM,0)) / SUM(NVL(QIC_RETN_PREM,0)) END) * 100,2) info10, ");
             query.append("SUM(CLM_COUNT) clmCount, ROUND(SUM(NVL(CLM_OS,0)),2) info11 ");
             query.append("FROM V_CUST_CLM_EXP ");
             query.append("WHERE  NVL(INS_CODE,'0') = NVL(?, INS_CODE) ");
             query.append("AND CUST_CODE = NVL(?,CUST_CODE) ");
             query.append("AND NVL(BROK_CODE,0) = NVL(?, NVL(BROK_CODE,0)) ");
             query.append("AND UW_YEAR BETWEEN ? AND ? ");
             query.append("GROUP BY COMP_CODE, DIVN_CODE, PROD_CODE, DEPT_CODE ");
             query.append("ORDER by  PROD_CODE    ");

             params =  getGiSearchParameters(inscode,searchBy,fromYear,toYear);

            LOGGER.info("Query :: {} [{}]", query, params);
            list = executeList(con, query.toString(), params, KeyValue.class);

         }
        } catch (Exception e){
        	LOGGER.error("Error while retreiving Product Wise Summary => {}", e);
        }finally {
            query = null;
            closeDbConn(con);
        }

        return list;
    }

 public GIPolicyBean loadGIPolicyDetails(String civilid,String fromYear, String toYear, String searchBy, String company) {

        GIPolicyBean polBean = new GIPolicyBean();
        Connection con = null;
        Object[] params = null;
        String inscode = null;

        inscode = getInsuredCode(civilid);


        String sql = "SELECT COUNT(DISTINCT POL_NO) policyCnt, SUM(CLM_COUNT) claimCnt, ROUND(SUM(NVL(GROSS_PREM,0))) grossPrem, "
                + "ROUND(SUM(NVL(CLM_INCURRED,0))) claimInc, ROUND(SUM(NVL(CLM_OS,0))) claimOS, (ROUND(SUM(NVL(CLM_INCURRED,0))) - ROUND(SUM(NVL(CLM_OS,0)))) claimPaid, "
                + "ROUND((CASE WHEN SUM(NVL(GROSS_PREM,0)) = 0 THEN 0 ELSE SUM(NVL(CLM_INCURRED,0)) / SUM(NVL(GROSS_PREM,0))END) * 100,2) lossRatio "
                + "FROM V_CUST_CLM_EXP WHERE "
                + "NVL(INS_CODE,0) = NVL(?, NVL(INS_CODE,0)) "
    	        +" AND CUST_CODE = NVL(?,CUST_CODE) "
    	        +" AND NVL(BROK_CODE,0) = NVL(?, NVL(BROK_CODE,0)) "
                + "AND UW_YEAR BETWEEN ? AND ? " ;

        params =  getGiSearchParameters(inscode,searchBy,fromYear,toYear);

       try {

        if(inscode != null){

            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{sql, params});

            polBean = (GIPolicyBean) executeQuery(con, sql, params, GIPolicyBean.class);

            polBean.setCustStartYear(getInsuredStartYear(civilid));
            polBean.setCustStatus(getInsuredStatus(civilid));
            polBean.setCurrency(getCurrencyCodeForCompany(company));
            polBean.setCustGrpName(getInsuredGroupName(civilid));

            if(Integer.parseInt(polBean.getPolicyCnt()) > 0){
                DecimalFormat thousandSeperator = new DecimalFormat("###,###");
                polBean.setGrossPrem(thousandSeperator.format(Integer.parseInt(polBean.getGrossPrem())));
                polBean.setClaimPaid(thousandSeperator.format(Integer.parseInt(polBean.getClaimPaid())));
                polBean.setClaimOS(thousandSeperator.format(Integer.parseInt(polBean.getClaimOS())));
                polBean.setClaimInc(thousandSeperator.format(Integer.parseInt(polBean.getClaimInc())));
            }
          } else{

        	  polBean.setPolicyCnt(ApplicationConstants.VALUE_ZERO);
        	  polBean.setClaimCnt(ApplicationConstants.VALUE_ZERO);
              polBean.setGrossPrem(ApplicationConstants.VALUE_ZERO);
              polBean.setClaimPaid(ApplicationConstants.VALUE_ZERO);
              polBean.setClaimOS(ApplicationConstants.VALUE_ZERO);
              polBean.setClaimInc(ApplicationConstants.VALUE_ZERO);
              polBean.setLossRatio(ApplicationConstants.VALUE_ZERO);
              polBean.setCustStatus(TypeConstants.InsuredStatus.UNKNOWN.getCode());


          }
        } catch (Exception e) {
            LOGGER.error("Error in loadPolicyDetails ", e);
        } finally {
            sql = null;
            closeDbConn(con);
        }
        return polBean;
    }





    public List<Policy> loadGIPolicyList(String civillid,String fromYear, String toYear, String searchBy) {
        List<Policy> list = null;
        Connection con = null;
        StringBuilder query = null;
        Object[] params = null;
        String inscode = null;



        try {

        	con = getDBConnection(getDataSource());
            inscode = getInsuredCode(civillid);

            query = new StringBuilder(" ");
            query.append("SELECT TPI_TRANS_ID transId, TPI_POLICY_NO policyNo, TPI_INS_NAME insuredName, ");
            query.append("PKG_REP_UTILITY.FN_GET_PROD_NAME(TPI_PROD_CODE) product, ");
            query.append("PKG_REP_UTILITY.FN_GET_SCH_NAME(TPI_COMP_CODE, TPI_PROD_CODE, TPI_SCH_CODE) scheme, ");
            query.append("PKG_REP_UTILITY.FN_GET_PARA_NAME('APP_PORTAL', TPI_ORG_PORTAL) portal, ");
            query.append("PKG_REP_UTILITY.FN_GET_CUST_NAME(TPI_ORG_CUST_CODE) customerName, ");
            query.append("PKG_REP_UTILITY.FN_GET_CUST_NAME(NVL(TPI_ORG_AGENT_ID,TPI_AGENT_ID)) agentName, ");
            query.append("(SELECT AC_DESC FROM M_APP_CODES WHERE AC_TYPE LIKE '%MENU_CODE%' AND AC_CODE = TPI_ORG_SOURCE) source, ");
            query.append("TO_CHAR(TPI_FM_DT, 'DD/MM/RRRR') fromDate, ");
            query.append("TO_CHAR(TPI_TO_DT, 'DD/MM/RRRR') toDate, ");
            query.append("TPI_UW_YEAR uwYear, ");
            query.append("SUM(TPI_PREM_FC - TPI_POL_DISC_FC + TPI_POL_LOAD_FC + TPI_FEE_FC) netAmount,  ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('MENU_CODES', TPI_MENU)||PKG_REP_UTILITY.FN_GET_AC_NAME('MENU_CODE', TPI_MENU) businessSource, ");
            query.append("PKG_CLAIM_PROCESS.FN_GET_CLAIM_RATIO(TPI_TRANS_ID) lossRatio , ");
            query.append("(CASE WHEN TRUNC(SYSDATE) BETWEEN TRUNC(TPI_FM_DT) AND TRUNC(TPI_TO_DT) OR TRUNC(SYSDATE) < TRUNC(TPI_FM_DT) THEN 'Active' ELSE 'In-Active' END) status ");
            query.append("FROM T_POL_INFO ");
            query.append("WHERE  ");
            query.append("NVL(TPI_INS_CODE,0) = NVL( ? , NVL(TPI_INS_CODE,0)) ");
            query.append("AND TPI_CUST_CODE = NVL(? ,TPI_CUST_CODE) ");
            query.append("AND NVL(TPI_AGENT_ID,0) = NVL( ? , NVL(TPI_AGENT_ID,0)) ");
            query.append("AND TPI_UW_YEAR BETWEEN ? AND ? ");
            query.append("GROUP BY TPI_TRANS_ID,  ");
            query.append("TPI_POLICY_NO, ");
            query.append("TPI_INS_NAME, ");
            query.append("TPI_COMP_CODE, TPI_PROD_CODE, TPI_SCH_CODE, ");
            query.append("TPI_ORG_PORTAL, ");
            query.append("TPI_COMP_CODE, TPI_PROD_CODE, TPI_SCH_CODE,");
            query.append("TPI_ORG_PORTAL,TPI_ORG_CUST_CODE,TPI_ORG_AGENT_ID,TPI_AGENT_ID, ");
            query.append("TPI_ORG_SOURCE,TPI_FM_DT,TPI_TO_DT,TPI_UW_YEAR, ");
            query.append("TPI_MENU ");
            query.append("ORDER BY TPI_FM_DT DESC ");


            params =  getGiSearchParameters(inscode,searchBy,fromYear,toYear);


            LOGGER.info("Query :: {} [{}]", query, params);

            list = executeList(con, query.toString(), params, Policy.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving policy list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }


    public List<Policy> loadGIClaimsList(String civillid,String fromYear, String toYear, String searchBy) {
        List<Policy> list = null;
        Connection con = null;
        StringBuilder query = null;
        Object[] params = null;
        String inscode = null;
        try {
            con = getDBConnection(getDataSource());
            inscode = getInsuredCode(civillid);


            query = new StringBuilder("SELECT CRP_CLM_TRANS_ID transId,  CLM_NO claimNo, CLM_POLICY_NO policyNo, ");
            query.append("CRP_PARTY_REF_NO partyRefNo, PKG_REP_UTILITY.FN_GET_PROD_NAME(CLM_PROD_CODE) product, ");
            query.append("CLM_POLICE_REF_NO policeRefNo, DECODE(CRP_PARTY_REF_NO, '1', 'OWN', 'TP') partyClaimType, ");
            query.append("TO_CHAR(CLM_LOSS_DT, 'DD/MM/RRRR') lossDate, TO_CHAR(CLM_INTM_DT, 'DD/MM/RRRR') intimatedDate, ");
            query.append("DECODE(CLM_RECOVERY_YN, '1', 'Yes', 'No') recoveryYn, PKG_REP_UTILITY.FN_GET_CUST_NAME(CLM_RECOVERY_CUST) recoveryCust, ");
            query.append("CRP_OWNER_NAME insuredName, ");
            query.append("PKG_REP_UTILITY.FN_GET_CUST_NAME(CRP_GARAGE_CODE) garageName, ");
            query.append("(SELECT NVL(USER_SHORT_NAME, USER_NAME) FROM M_USER WHERE USER_EMP_NO = CRP_SURVEYOR_CODE) surveyorName, ");
            query.append("CRP_OS_PAY, CRP_PAID, DECODE(CLM_STS, 'C', 'Closed', 'Active') claimStatus, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('CLM_PROCESS', CRS_STATUS_CODE) status, ");
            query.append("CLM_YEAR Year ");
            query.append("FROM T_CLAIM, T_CLM_RISK_PARTY_INFO, T_CLM_RISK_PARTY_STATUS ");
            query.append("WHERE CLM_TRANS_ID = CRP_CLM_TRANS_ID  ");
            query.append("AND NVL(CLM_INS_CODE,0) = NVL( ? , NVL(CLM_INS_CODE,0)) ");
            query.append("AND CLM_CUST_CODE = NVL( ? ,CLM_CUST_CODE) ");
            query.append("AND NVL(CLM_AGENT_ID,0) = NVL( ? , NVL(CLM_AGENT_ID,0)) ");
            query.append("AND CLM_YEAR BETWEEN ? AND ? ");
            query.append("AND CRP_CLM_TRANS_ID = CRS_CLM_TRANS_ID ");
            query.append("AND CRP_PARTY_REF_NO = CRS_PARTY_REF_NO ");
            query.append("ORDER BY CLM_INTM_DT DESC");

            params =  getGiSearchParameters(inscode,searchBy,fromYear,toYear);
            LOGGER.info("Query :: {} [{}]", query, params);

            list = executeList(con, query.toString(), params, Policy.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving claim list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }



    public int saveContactDetail(final CustomerContact bean, String civilid) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        String custCode =null;
        try {

        	    custCode = getInsuredCode(civilid);
        	    bean.setCustCode(custCode);


        	    con = getDBConnection(getDataSource());
                sql = " INSERT INTO M_CUSTOMER_CONTACTS (CC_CUST_CODE,CC_CONTACT_NAME,CC_DESIGNATION,"
                		+ "CC_TEL_NO , CC_MOBILE_NO,CC_FAX_NO,CC_EMAIL_ID, CC_CR_UID,CC_CR_DT) "
                        + "VALUES(:ccCustCode, :ccContactName, :ccDesignation , :ccTelNo, :ccMobileNo, "
                        + ":ccFaxNo, :ccEmailNo, :crUid,SYSDATE)"   ;

                LOGGER.info("Query :: {} ", new Object[]{sql});
                ps = con.prepareStatement(sql);
                ps.setString(++i,bean.getCustCode());
                ps.setString(++i,bean.getContactName());
                ps.setString(++i,bean.getDesignation());
                ps.setString(++i,bean.getTelNo());
                ps.setString(++i,bean.getMobileNo());
                ps.setString(++i,bean.getFaxNo());
                ps.setString(++i,bean.getEmail());
                ps.setString(++i,bean.getCrUid());
                recCnt = ps.executeUpdate();
                con.commit();

        } catch (Exception e) {
            LOGGER.error("Error while adding contact => {}", e);
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




    public List<CustomerContact> loadContactDetails(String civilId) {

        List<CustomerContact> list = null;
        Connection con = null;
        StringBuilder query = null;
        String custCode = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] param;
            List<Object> params = new LinkedList<>();
            custCode = getInsuredCode(civilId);
            params.add(custCode.trim());

            query = new StringBuilder("SELECT CC_CUST_CODE custCode , CC_CONTACT_NAME contactName, ")
                .append("CC_DESIGNATION designation, CC_TEL_NO telNo,CC_MOBILE_NO mobileNo,CC_FAX_NO faxNo,CC_EMAIL_ID email ")
                .append("FROM M_CUSTOMER_CONTACTS WHERE CC_CUST_CODE = ? ");
            param = params.toArray();

            LOGGER.info("Query :: {} {}", query, Arrays.toString(param));
            list = executeList(con, query.toString(), param, CustomerContact.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving contact details", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }




    public KeyValue getContactTypeDesc(String acCode) {
    	Connection con = null;
    	String sql = null;
    	KeyValue  appCode = new KeyValue();

        try {
            List<Object> params = new LinkedList<>();
            con = getDBConnection(getDataSource());
            sql = "SELECT AC_CODE \"key\", AC_DESC \"value\" FROM M_APP_CODES WHERE AC_TYPE = 'CONTACT_TYPE' "
            		+ "AND  TRUNC(SYSDATE) BETWEEN TRUNC(NVL(AC_EFF_FM_DT, SYSDATE)) AND TRUNC(NVL(AC_EFF_TO_DT, SYSDATE)) "
            		+ "AND AC_TYPE='CONTACT_TYPE' AND AC_CODE= ?";
            params.add(acCode);
            LOGGER.info("Query :: {} {}", sql,acCode);
            appCode = (KeyValue) executeQuery(con, sql,params.toArray(), KeyValue.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving ac desc from app codes", e);
        } finally {
            sql = null;
            closeDbConn(con);
        }
        return appCode;
    }




    private  String convertTimezones(Date date,String sourceTz, String targetTz) {

           String formattedTimezone = null;

           Calendar calendar = Calendar.getInstance();
           calendar.setTime(date);
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
           sdf.setTimeZone(TimeZone.getTimeZone(sourceTz));
           //Will print in sourceTz
           System.out.println(sdf.format(calendar.getTime()));

          //Here you set to your timezone
          sdf.setTimeZone(TimeZone.getTimeZone(targetTz));
          //Will print in targetTz
          System.out.println(sdf.format(calendar.getTime()));
          formattedTimezone = sdf.format(date);


	return formattedTimezone;

    }




    public List<KeyValue> getProducts() {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT PROD_CODE key, PROD_DESC value FROM M_PRODUCT ORDER BY PROD_DESC";
            list = executeList(con, query, new Object[]{}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving products => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }



    public String getDataSource() {
        return dataSource;
    }


    public Object[] getGiSearchParameters(String inscode, String searchBy, String fromYear, String toYear){

    	Object[] params;
        int fromYearVal;
        int toYearVal;
        Calendar cal = Calendar.getInstance();

        if(StringUtils.isNotBlank(fromYear)){
        	fromYearVal = Integer.parseInt(fromYear);
        } else {
        	cal.add(Calendar.YEAR, -2);
        	fromYearVal = cal.get(Calendar.YEAR);
        }

       if(StringUtils.isNotBlank(toYear)){
        	toYearVal = Integer.parseInt(toYear);
        }else {
        	toYearVal = Calendar.getInstance().get(Calendar.YEAR);
        }



    	 if(searchBy.equals(ApplicationConstants.CUSTOMER360_GI_SEARCH_CUSTOMER)){
  		          params  = new Object[]{null,inscode,null,fromYearVal,toYearVal};
  	         } else if(searchBy.equals(ApplicationConstants.CUSTOMER360_GI_SEARCH_BROKER)){
  		          params = new Object[]{null,null,inscode,fromYearVal,toYearVal};
  	         }
  	           else {
        	      params = new Object[]{inscode,null,null,fromYearVal,toYearVal};
  	        }

    	 return params;
    }

    public String getInsuredCode(String civilId) {
        String insCode = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        con = getDBConnection(getDataSource());
        String sql = "SELECT INS_CODE insCode "
                + "FROM M_INSURED WHERE "
                + "INS_CIVIL_ID = ? ";
        ps = con.prepareStatement(sql);
        ps.setString(1, civilId.trim());
        rs = ps.executeQuery();
        if (rs.next()) {
                insCode = rs.getString(1);
            }
        } catch (Exception e) {
            LOGGER.error("Error in getInsuredCode ", e);
        } finally {
            closeDBComp(ps, rs, con) ;
        }

        return insCode;
    }

    public String getInsuredStartYear(String civilId) {
        String eff_frm_date = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Calendar cal = Calendar.getInstance();
        String startYear = null;

        try {
        con = getDBConnection(getDataSource());
        String sql = "SELECT INS_EFF_FM_DT eff_frm_date "
                + "FROM M_INSURED WHERE "
                + "INS_CIVIL_ID = ? ";
        ps = con.prepareStatement(sql);
        ps.setString(1, civilId);
        rs = ps.executeQuery();
        if (rs.next()) {
        	eff_frm_date = rs.getString(1);
            }

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(eff_frm_date);
        cal.setTime(date);
        startYear = Integer.toString(cal.get(Calendar.YEAR));


        } catch (Exception e) {
            LOGGER.error("Error in getInsuredStartYear ", e);
        } finally {
            closeDBComp(ps, rs, con) ;
        }

        return startYear;
    }

    public String getInsuredGroupName(String civilId) {
        String group_name = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        con = getDBConnection(getDataSource());
        String sql =    "SELECT  mcg.CG_GROUP_NAME FROM M_INSURED mi, M_CUSTOMER_GROUP mcg"
        	    + " WHERE mi.INS_CIVIL_ID = ?"
        	    + " AND mi.INS_GROUP_CODE = mcg.CG_GROUP_CODE";

        ps = con.prepareStatement(sql);
        ps.setString(1, civilId);
        rs = ps.executeQuery();
        if (rs.next()) {
        	   group_name = rs.getString(1);
           }

        } catch (Exception e) {
            LOGGER.error("Error in getInsuredGroupName ", e);
        } finally {
            closeDBComp(ps, rs, con) ;
        }

        return group_name;
    }


    public String getInsuredStatus(String civilId) {
        String eff_to_date = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String status = null;

        try {
          con = getDBConnection(getDataSource());
           String sql = "SELECT INS_EFF_TO_DT eff_To_date "
                + "FROM M_INSURED WHERE "
                + "INS_CIVIL_ID = ? ";
          ps = con.prepareStatement(sql);
          ps.setString(1, civilId);
          rs = ps.executeQuery();
          if (rs.next()) {
        	  eff_to_date = rs.getString(1);
            }

          if(eff_to_date != null)   {
              Date to_date = new SimpleDateFormat("yyyy-MM-dd").parse(eff_to_date);
              long millis=System.currentTimeMillis();
              Date curr_date=new Date(millis);
              if(curr_date.after(to_date))
            	     status = TypeConstants.InsuredStatus.IN_ACTIVE.getCode();
              else
            	     status = TypeConstants.InsuredStatus.ACTIVE.getCode();
           }  else {
         	     status = TypeConstants.InsuredStatus.UNKNOWN.getCode();
           }



        } catch (Exception e) {
            LOGGER.error("Error in getInsuredStatus ", e);
        } finally {
            closeDBComp(ps, rs, con) ;
        }


        return status;
    }

    public String getCurrencyCodeForCompany(String company) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String currency = null;

        try {
        con = getDBConnection(getDataSource());
        String sql = "SELECT COMP_BASE_CURR_CODE_1 FROM M_COMPANY,M_CURRENCY where CURR_CODE=COMP_BASE_CURR_CODE_1 AND COMP_CODE=?";

        ps = con.prepareStatement(sql);
        ps.setString(1, company);
        rs = ps.executeQuery();
        if (rs.next()) {
        	currency = rs.getString(1);
            }

        } catch (Exception e) {
            LOGGER.error("Error in getCurrencyCodeForCompany ", e);
        } finally {
            closeDBComp(ps, rs, con) ;
        }


        return currency;
    }


   public int saveLogOrActivity(final Activity activity) {
       Connection con = null;
       PreparedStatement ps = null;
       int i = 0, recCnt = 0;
       String sql = null;
       try {
           con = getDBConnection(getDataSource());
           sql = " INSERT INTO T_INSURED_LOG   (IL_ID, IL_TYPE, IL_CIVIL_ID, IL_LOG_DETAILS, IL_CR_UID, IL_CR_DT,IL_CRM_ID)"
           		+ " VALUES(S_IL_ID.NEXTVAL, ? , ? , ? , ?, SYSDATE, ?)";
           ps = con.prepareStatement(sql);
           ps.setString(++i, activity.getTemplate());
           ps.setString(++i, activity.getCivilId());
           ps.setString(++i, activity.getMessage());
           ps.setString(++i, activity.getUserId());
           ps.setString(++i, activity.getCrmId());
           LOGGER.info("Query :: {} [{}]", new Object[]{sql, activity.getTemplate(),activity.getCivilId(),activity.getMessage(),activity.getUserId(),activity.getCrmId()});
           recCnt = ps.executeUpdate();
           con.commit();
       } catch (Exception e) {
           LOGGER.error("Error during logCorpEmailActivity => {}", e);
       } finally {
           closeDBComp(ps, null, con);
       }
       return recCnt;
   }


}

