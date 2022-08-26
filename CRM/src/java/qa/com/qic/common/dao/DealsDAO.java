/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.CrmDeal;
import qa.com.qic.common.vo.CrmDealEnquiry;
import qa.com.qic.common.vo.CrmDealPipeline;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author karthik.j
 */
public class DealsDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(DealsDAO.class);

    private final String dataSource;

    public DealsDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public int saveDealDetail(final CrmDeal bean, final String operation) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        Long deal_seqId;

        try {
        	 con = getDBConnection(getDataSource());
        	 deal_seqId = getSeqVal(con, "SELECT S_CD_ID.NEXTVAL FROM DUAL");

             if ("add".equalsIgnoreCase(operation)) {

                sql = "INSERT into T_CRM_DEALS" +
                      "(CD_DEAL_ID, CD_PL_ID, CD_STAGE_NO, CD_CUST_CODE,CD_CUST_NAME,CD_PROD_CODE,CD_CONTACT_NAME,CD_CONTACT_NO,CD_CONTACT_EMAIL, CD_DEAL_NAME, " +
                       "CD_ENQ_NO, CD_QUOT_NO, CD_POLICY_NO, CD_DEAL_VALUE, CD_DEAL_STATUS, " +
                       "CD_CUST_FIELD_1, CD_CUST_FIELD_2, CD_CUST_FIELD_3, CD_CUST_FIELD_4, CD_CUST_FIELD_5, " +
                       "CD_CUST_FIELD_6, CD_CUST_FIELD_7, CD_CUST_FIELD_8, CD_CUST_FIELD_9, CD_CUST_FIELD_10,  " +
                       "CD_CR_UID, CD_CR_DT, CD_CRM_ID, CD_CONTACT_TYPE, CD_LOST_REASON_CODE, CD_CLOSE_DT )" +
                      "VALUES " +
                      "(:cd_deal_id, :cd_pl_id, :cd_stage_no,:CD_CUST_CODE,:CD_CUST_NAME,:CD_PROD_CODE,:CD_CONTACT_NAME,:CD_CONTACT_NO,:CD_CONTACT_EMAIL, :cd_deal_name, " +
                      " :cd_enq_no,  :cd_quot_no,  :cd_policy_no,  :cd_deal_value,  :cd_deal_status," +
                      " :cd_cust_field_1,  :cd_cust_field_2,  :cd_cust_field_3,  :cd_cust_field_4,  :cd_cust_field_5," +
                      " :cd_cust_field_6,  :cd_cust_field_7,  :cd_cust_field_8,  :cd_cust_field_9,  :cd_cust_field_10,"+
                      " :cd_cr_uid,  SYSDATE  , :cd_crm_id, :cd_contact_type,:cd_lost_code," +
                      "TO_DATE(:cd_close_dt, 'DD/MM/YYYY')  )";



                bean.setCdDealStatus(getDealStatus(bean.getCdPlId(), bean.getCdStageNo()));
                ps = con.prepareStatement(sql);
                ps.setLong(++i, deal_seqId );
                ps.setLong(++i, bean.getCdPlId());
                ps.setLong(++i, bean.getCdStageNo());

                if(bean.getCdCustCode() == null)
                	 ps.setNull(++i, java.sql.Types.NUMERIC);
                else
                    ps.setLong(++i, bean.getCdCustCode());


                ps.setString(++i, bean.getCdCustName());
                ps.setString(++i, bean.getCdProdCode());
                ps.setString(++i, bean.getCdContactName());
                ps.setLong(++i, bean.getCdContactNo());
                ps.setString(++i, bean.getCdContactEmail());
                ps.setString(++i, bean.getCdDealName());
                ps.setString(++i, bean.getCdEnqNo());
                ps.setString(++i, bean.getCdQuotNo());
                ps.setString(++i, bean.getCdPolicyNo());
                ps.setString(++i, bean.getCdDealVal().replace(",", ""));
                ps.setString(++i, bean.getCdDealStatus());
                ps.setString(++i, bean.getCdCustFld1());
                ps.setString(++i, bean.getCdCustFld2());
                ps.setString(++i, bean.getCdCustFld3());
                ps.setString(++i, bean.getCdCustFld4());
                ps.setString(++i, bean.getCdCustFld5());
                ps.setString(++i, bean.getCdCustFld6());
                ps.setString(++i, bean.getCdCustFld7());
                ps.setString(++i, bean.getCdCustFld8());
                ps.setString(++i, bean.getCdCustFld9());
                ps.setString(++i, bean.getCdCustFld10());
                ps.setString(++i, bean.getCdCrUid());
                ps.setString(++i, bean.getCdCrmId());
                ps.setString(++i, bean.getCdContactType());
                ps.setString(++i, bean.getCdQuotDeclRes());
                ps.setString(++i, bean.getCdDealClDt());

                recCnt = ps.executeUpdate();
            } else if ("edit".equalsIgnoreCase(operation)) {
                sql = "UPDATE T_CRM_DEALS SET" +
                        " CD_PL_ID= ? , CD_STAGE_NO= ? , CD_CUST_CODE= ? , CD_PROD_CODE = ?, CD_CONTACT_NAME= ?,CD_CONTACT_NO = ? ,CD_CONTACT_EMAIL = ? , CD_DEAL_NAME= ? ,"  +
                         "CD_ENQ_NO= ? , CD_QUOT_NO= ? , CD_POLICY_NO= ? , CD_DEAL_VALUE= ? , CD_DEAL_STATUS= ? , " +
                         "CD_CUST_FIELD_1= ? , CD_CUST_FIELD_2= ? , CD_CUST_FIELD_3= ? , CD_CUST_FIELD_4= ? , CD_CUST_FIELD_5= ? , " +
                         "CD_CUST_FIELD_6= ? , CD_CUST_FIELD_7= ? , CD_CUST_FIELD_8= ? , CD_CUST_FIELD_9= ? , CD_CUST_FIELD_10= ? ,  " +
                         "CD_CR_UID= ?,CD_UPD_UID= ? , CD_UPD_DT= SYSDATE , CD_CRM_ID =?, CD_CONTACT_TYPE =? , CD_LOST_REASON_CODE=?," +
                        "CD_CLOSE_DT=TO_DATE(?, 'DD/MM/YYYY')" +
                         "WHERE CD_DEAL_ID= ?" ;

                bean.setCdDealStatus(getDealStatus(bean.getCdPlId(), bean.getCdStageNo()));

                LOGGER.info("Query :: {} [{}]", new Object[]{sql, bean.getCdDealId()});
                ps = con.prepareStatement(sql);
                ps.setLong(++i, bean.getCdPlId());
                ps.setLong(++i, bean.getCdStageNo());
                if(bean.getCdCustCode() == null)
               	 ps.setNull(++i, java.sql.Types.NUMERIC);
               else
                   ps.setLong(++i, bean.getCdCustCode());
                ps.setString(++i, bean.getCdProdCode());
                ps.setString(++i, bean.getCdContactName());
                ps.setLong(++i, bean.getCdContactNo());
                ps.setString(++i, bean.getCdContactEmail());
                ps.setString(++i, bean.getCdDealName());
                ps.setString(++i, bean.getCdEnqNo());
                ps.setString(++i, bean.getCdQuotNo());
                ps.setString(++i, bean.getCdPolicyNo());
                ps.setString(++i, bean.getCdDealVal());
                ps.setString(++i, bean.getCdDealStatus());
                ps.setString(++i, bean.getCdCustFld1());
                ps.setString(++i, bean.getCdCustFld2());
                ps.setString(++i, bean.getCdCustFld3());
                ps.setString(++i, bean.getCdCustFld4());
                ps.setString(++i, bean.getCdCustFld5());
                ps.setString(++i, bean.getCdCustFld6());
                ps.setString(++i, bean.getCdCustFld7());
                ps.setString(++i, bean.getCdCustFld8());
                ps.setString(++i, bean.getCdCustFld9());
                ps.setString(++i, bean.getCdCustFld10());
                ps.setString(++i, bean.getCdCrUid());
                ps.setString(++i, bean.getCdUpdUid());
                ps.setString(++i, bean.getCdCrmId());
                ps.setString(++i, bean.getCdContactType());
                ps.setString(++i, bean.getCdQuotDeclRes());
                ps.setString(++i, bean.getCdDealClDt());
                ps.setLong(++i, bean.getCdDealId());



                recCnt = ps.executeUpdate();

            } else if("delete".equalsIgnoreCase(operation)){
                sql = "DELETE FROM  T_CRM_DEALS WHERE CD_DEAL_ID = ? ";
                LOGGER.info("Query :: {} [{}]", new Object[]{sql, bean.getCdDealId()});
                ps = con.prepareStatement(sql);
                ps.setLong(++i, bean.getCdDealId());
                recCnt = ps.executeUpdate();
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error adding/editing deals => {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public KeyValue saveDealPipeline(final List<CrmDealPipeline> list,String operation) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0, tot = 0;
        String sql = null;
        String update = null;
        String insert = null;
        Long pl_seqId = null;
        KeyValue kv = null;


        try {
        	 con = getDBConnection(getDataSource());

             if ("add".equalsIgnoreCase(operation)) {
            	 pl_seqId = getSeqVal(con, "SELECT S_CDP_ID.NEXTVAL FROM DUAL");

            	for(CrmDealPipeline record: list){
            	       sql = "INSERT into T_CRM_DEAL_PIPELINE "
                           + "(CDP_PL_ID, CDP_PL_NAME, CDP_STAGE_NO, CDP_STAGE_TYP, CDP_STAGE_NAME, "
                           + "CDP_STAGE_PROB,"
                           + "CDP_CR_UID, CDP_CR_DT)"
                           +"Values  (:CDP_PL_ID,:CDP_PL_NAME, :CDP_STAGE_NO,"
                           + " :CDP_STAGE_TYP, :CDP_STAGE_NAME, "
                           + " NVL(:CDP_STAGE_PROB,0),"
                           + ":CDP_CR_UID, SYSDATE)";

            	       if(record.getCdpStageProb() == null)
            	    	   record.setCdpStageProb(0L);

                       ps = con.prepareStatement(sql);
                       ps.setLong(++i, pl_seqId );
                       ps.setString(++i, record.getCdpPlName());
                       ps.setLong(++i, record.getCdpStageNo());
                       ps.setString(++i, record.getCdpStageTyp());
                       ps.setString(++i, record.getCdpStageName());
                       ps.setLong(++i, record.getCdpStageProb());
                       ps.setString(++i, record.getCdpCrUid());
                       recCnt = ps.executeUpdate();
                       i=0;
            	}
                kv =    new KeyValue( pl_seqId.toString() , String.valueOf(recCnt) );

            } else if ("edit".equalsIgnoreCase(operation)) {

            	for(CrmDealPipeline record: list){

            		sql = "SELECT CDP_PL_ID key,CDP_STAGE_NO value FROM T_CRM_DEAL_PIPELINE  WHERE CDP_PL_ID=? AND CDP_STAGE_NO=? ";
                    LOGGER.info("Query :: {} [{},{}]", sql,record.getCdpPlId(),record.getCdpStageNo());
                    List<KeyValue> stage = executeList(con,sql, new Object[]{record.getCdpPlId(),record.getCdpStageNo()}, KeyValue.class);

                    if(stage.size() > 0){

                		update = "UPDATE T_CRM_DEAL_PIPELINE SET "
                                + "CDP_PL_NAME=?,  CDP_STAGE_PROB=(CASE CDP_STAGE_TYP "
                                + " WHEN 'WON' THEN 100"
                                + " WHEN 'LOST' THEN 0"
                                + " ELSE  "  + record.getCdpStageProb()
                                + " END ),"
                                + " CDP_STAGE_TYP=?, CDP_STAGE_NAME=?, "
                                + "CDP_UP_UID=?, CDP_UP_DT=SYSDATE WHERE CDP_PL_ID=? AND CDP_STAGE_NO=? ";
                		int j = 0;
                        ps = con.prepareStatement(update);
                        ps.setString(++j, record.getCdpPlName());
                        ps.setString(++j, record.getCdpStageTyp());
                        ps.setString(++j, record.getCdpStageName());
                        ps.setString(++j, record.getCdpUpUid());
                        ps.setLong(++j, record.getCdpPlId());
                        ps.setLong(++j, record.getCdpStageNo());
                        LOGGER.info("Query :: {} [{}{}]", new Object[]{update, record.getCdpPlId(), record.getCdpStageProb()});
                        recCnt = ps.executeUpdate();
                        tot = recCnt;

                    }else{

                        insert = "INSERT into T_CRM_DEAL_PIPELINE "
                                + "(CDP_PL_ID, CDP_PL_NAME, CDP_STAGE_NO, CDP_STAGE_TYP, CDP_STAGE_NAME, "
                                + "CDP_STAGE_PROB,"
                                + "CDP_UP_UID, CDP_UP_DT)"
                                +"Values  ( :CDP_PL_ID,:CDP_PL_NAME, :CDP_STAGE_NO,"
                                + " :CDP_STAGE_TYP, :CDP_STAGE_NAME, "
                                + " :CDP_STAGE_PROB,"
                                + ":CDP_UP_UID, SYSDATE)";

                        if(record.getCdpStageProb() == null)
             	    	   record.setCdpStageProb(0L);

                        int k = 0;
                        ps = con.prepareStatement(insert);
                        ps.setLong(++k, record.getCdpPlId());
                        ps.setString(++k, record.getCdpPlName());
                        ps.setLong(++k, record.getCdpStageNo());
                        ps.setString(++k, record.getCdpStageTyp());
                        ps.setString(++k, record.getCdpStageName());
                        ps.setLong(++k, record.getCdpStageProb());
                        ps.setString(++k, record.getCdpUpUid());
                        LOGGER.info("Query :: {} [{}{}]", new Object[]{insert, record.getCdpPlId(), record.getCdpStageProb()});
                        recCnt = ps.executeUpdate();
                    }

                    tot = tot+ recCnt;
            	}

            	 kv =    new KeyValue( list.get(0).getCdpPlId().toString() , String.valueOf(recCnt) );

            } else if ("delete".equalsIgnoreCase(operation)){

            	List<String> sqls = new LinkedList<>();
            	sqls.add("DELETE FROM T_CRM_DEAL_PIPELINE WHERE CDP_PL_ID = ? ");
               	sqls.add("DELETE FROM T_CRM_DEALS WHERE CD_PL_ID = ? ");

               	for(String query : sqls){
                    LOGGER.info("Query :: {} [{}]", new Object[]{query, list.get(0).getCdpPlId()});
                    ps = con.prepareStatement(query);
                    ps.setLong(++i, list.get(0).getCdpPlId());
                    recCnt = ps.executeUpdate();
                    tot = tot+ recCnt;
                    i=0;
               	}

                kv =    new KeyValue( list.get(0).getCdpPlId().toString() , String.valueOf(tot) );
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error adding/editing deal pipeline => {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }


        return kv;
    }


    public List<KeyValue> loadStageData(CrmDeal bean, String compCode){


    	List<KeyValue> stageData = null;
        Connection con = null;
        StringBuilder query = null;
        String whereOwner = "";
        String whereShowClosed = "";
        try {
            con = getDBConnection(getDataSource());
            Object[] param;
            List<Object> params = new LinkedList<>();
            params.add(bean.getCdPlId());

            if(BooleanUtils.isNotTrue(bean.isShowClosed()))
            	whereShowClosed = " AND CD_DEAL_STATUS NOT IN ('WON' , 'LOST') ";
            else
            	whereShowClosed = "";

            if(StringUtils.equalsIgnoreCase("ALL",bean.getCdCrUid()))
            	whereOwner += "";
            else {
            	whereOwner += " AND CD_CR_UID=? ";
            	params.add(bean.getCdCrUid());
            }


            query = new StringBuilder("WITH params AS(SELECT ? AS plId FROM DUAL) "
            		+ " SELECT t1.CDP_PL_ID key,t1.CDP_PL_NAME text,t1.CDP_STAGE_NO value, t1.CDP_STAGE_NAME info, " +"\'"+getBaseCurrShortForCompany(compCode)+"\'"+"||' '|| PKG_REP_UTILITY.FN_GET_FORMAT_MASK("+compCode+",t2.totVal)||'K'   info1, NVL(t2.dlCnt,0) info2 , t1.CDP_STAGE_TYP info3  ");
            query.append("FROM T_CRM_DEAL_PIPELINE t1 LEFT OUTER JOIN "
            		+ "(SELECT CD_PL_ID ,CD_STAGE_NO,   NVL(SUM(CD_DEAL_VALUE)/1000,0) totVal, COUNT(CD_DEAL_ID) dlCnt FROM "
            		+ " T_CRM_DEALS WHERE CD_PL_ID = (select plId from params) "
            		+  whereShowClosed
            		+  whereOwner
            		+ " GROUP BY CD_PL_ID ,CD_STAGE_NO) t2"
            		+ " ON t1.CDP_PL_ID=t2.CD_PL_ID AND t1.CDP_STAGE_NO=t2.CD_STAGE_NO "
            		+ " WHERE t1.CDP_PL_ID = (select plId from params) "
            		+ " ORDER BY t1.CDP_STAGE_NO  ");



            param = params.toArray();
            LOGGER.info("Query :: {} {}", query, Arrays.toString(param));
            stageData = executeStageDataList(con,bean.isShowClosed(),bean.getCdCrUid(), compCode, query.toString(), param, KeyValue.class);


        } catch (Exception e) {
            LOGGER.error("Error while retreiving stage data", e);
        } finally {
            query = null;
            closeDbConn(con);
        }

		return stageData;


    }

   public List<KeyValue> loadDealsActivityList(String dealId , String filter){

       List<KeyValue> list = null;
       LinkedList<Object> params = new LinkedList<>();
       Connection con = null;
       StringBuilder query = null;
       String dateFilter = null;

       try {

    	   if(StringUtils.equals(filter,TypeConstants.DateFilter.CURRENT.getCode())){
    		   dateFilter = " TRUNC(CDL_CR_DT) = TRUNC(SYSDATE) ";
    	   }else if(StringUtils.equals(filter,TypeConstants.DateFilter.PAST.getCode())){
    		   dateFilter = " TRUNC(CDL_CR_DT) < TRUNC(SYSDATE) ";
    	   }else if(StringUtils.equals(filter,TypeConstants.DateFilter.FUTURE.getCode())){
    		   dateFilter = " TRUNC(CDL_CR_DT) > TRUNC(SYSDATE) ";
    	   }

    	    con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CDL_TYPE key, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOG_TYPE', CDL_TYPE) value,"
            		+ " PKG_REP_UTILITY.FN_GET_AC_VALUE('CRM_LOG_TYPE', CDL_TYPE) info1 , AC_FLEX_01 info2 , TO_CHAR(CDL_CR_DT, 'DD/MM/YYYY hh:mi AM')  info3 ,"
            		+ " CDL_LOG_DETAILS info4 , TO_CHAR(CDL_CR_DT, 'DD/MM/YYYY hh:mi AM')   info5, CDL_CR_UID, USER_NAME info6 "
            		+ " FROM T_CRM_DEALS_LOG  "
            		+ " JOIN M_APP_CODES ON CDL_TYPE=AC_CODE "
                    +"  JOIN TABLE(FN_GET_CRM_AGENTS(CDL_CR_UID)) ON CDL_CR_UID=USER_ID "
            		+ " WHERE AC_TYPE='CRM_LOG_TYPE' AND CDL_DEAL_ID = ? AND"
            		+ dateFilter
            		+ " ORDER BY CDL_CR_DT DESC ");

            params.add(dealId);

           LOGGER.info("Query :: {} [{}]", query ,params.toArray());
           list = executeList(con, query.toString(), params.toArray(), KeyValue.class);
       } catch (Exception e) {
           LOGGER.error("Error while loading deals activity list", e);
       } finally {
           closeDbConn(con);
       }
       return list;

   }

   public List<KeyValue> getUnderWriterList(String crmId,String agent) {
       List<KeyValue> list = new ArrayList<>();
       Connection con = null;
       String query = null;
       PreparedStatement ps = null;
       ResultSet rs = null;


       try {
           con = getDBConnection(crmId);
           query = "SELECT USER_ID, USER_NAME FROM M_USER "
           		+ " WHERE USER_ID IN (SELECT MUCD_USER_ID FROM M_USER_COMP_DIVN A WHERE EXISTS (SELECT 1 FROM M_USER_COMP_DIVN WHERE MUCD_USER_ID = ? "
           		+ " AND MUCD_COMP_CODE = A.MUCD_COMP_CODE AND MUCD_DIVN_CODE=A.MUCD_DIVN_CODE AND MUCD_DEPT_CODE=A.MUCD_DEPT_CODE)) "
           		+ " ORDER BY USER_ID";
           LOGGER.debug("Query :: {}", query);
           ps = con.prepareStatement(query);
           ps.setString(1, agent);
           rs = ps.executeQuery();
           while (rs.next()) {
               KeyValue keyValue = new KeyValue();
               keyValue.setKey(rs.getString("USER_ID"));
               keyValue.setValue(rs.getString("USER_NAME"));
               list.add(keyValue);
           }
           LOGGER.info("MAppCodes - App Codes Information Retrieved");
       } catch (Exception e) {
           LOGGER.error("Error while retreiving GL Account Info => {}", e);
       } finally {
           closeDBComp(ps, rs, con);
           query = null;
       }
       return list;
   }



   public List<KeyValue> getEnquiryProductTypeList(String compCode, String agent) {
       List<KeyValue> list = new ArrayList<>();
       Connection con = null;
       String query = null;
       try {
           con = getDBConnection(compCode);

           KeyValue user = getDeptForUser(agent, compCode).get(0);
           String company = user.getValue();
           String divn = user.getInfo();
           String dept = user.getInfo1();


           query =   " SELECT PSCH_SCHEME_CODE key, PSCH_SCHEME_NAME value, PROD_LOB_CODE info FROM M_PROD_SCHEMES, M_PRODUCT "
                   + " WHERE PSCH_COMP_CODE = ? AND PROD_CODE = PSCH_PROD_CODE AND TRUNC(SYSDATE) BETWEEN PSCH_EFF_FM_DT AND PSCH_EFF_TO_DT "
                   + " AND EXISTS(SELECT 1 FROM M_LOB_APPL_DEPT WHERE LACD_COMP_CODE = PSCH_COMP_CODE "
                   + " AND LACD_DIVN_CODE = ? AND LACD_DEPT_CODE = ? AND LACD_LOB_CODE = PROD_LOB_CODE) ORDER BY 1";

           LOGGER.info("Query :: {} [{}]", new Object[]{company, divn, dept });
           list = executeList(con, query,  new Object[]{company, divn, dept } , KeyValue.class);
       } catch (Exception e) {
    	   LOGGER.error("Error: {}" , e);
       } finally {
           closeDbConn(con);
           query = null;
       }
       return list;
   }

    public List<KeyValue> loadContactList(String searchString, String type, String crmId) {
        List<KeyValue> list = null;
        Object[] params = null;
        Connection con = null;
        StringBuilder query = null;

        try {
            con = getDBConnection(getDataSource());

            if(StringUtils.equals(type,TypeConstants.CrmContactType.LEAD.getCode())){
            	params = new Object[]{"%" + searchString + "%", "%" + searchString + "%" ,crmId};
                query = new StringBuilder("SELECT DISTINCT CD_CUST_CODE key, CD_CUST_CODE||'-'|| CD_CUST_NAME  value , CD_CUST_NAME  info FROM T_CRM_DEALS "
                        + " WHERE ((upper(CD_CUST_CODE) like upper(?) OR upper(CD_CUST_NAME) like upper(?))) "
                        + " AND CD_CRM_ID= ? AND CD_CONTACT_TYPE=" + TypeConstants.CrmContactType.LEAD.getCode());
            } else if(StringUtils.equals(type,TypeConstants.CrmContactType.CUSTOMER.getCode())) {
            	params = new Object[]{"%" + searchString + "%", "%" + searchString + "%" ,"%" + searchString + "%" ,crmId};
            	query = new StringBuilder("SELECT INF_CIVIL_ID key, INF_CIVIL_ID ||'-'||INS_CUST_CODE||'-'||INF_NAME value , INF_NAME info FROM M_INSURED_INFO "
                		+ " JOIN M_INSURED ON INF_CIVIL_ID=INS_CIVIL_ID"
                        + " WHERE ((upper(INF_CIVIL_ID) like upper(?) OR upper(INS_CUST_CODE) like upper(?) OR upper(INF_NAME) like upper(?))) AND INF_CRM_ID= ? ");
            }

            LOGGER.info("Query :: {} [{}]", params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while loading contact List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadEnquiryList(String searchString) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{"%" + searchString + "%", "%" + searchString + "%"};
        Connection con = null;
        StringBuilder query = null;

        try {
               con = getDBConnection(getDataSource());
               query = new StringBuilder("SELECT QEL_NO key, QEL_NO ||'-'|| QEL_CUST_NAME value  , QEL_CUST_NAME info FROM Q_ENQUIRY_LOG "
                        + "WHERE (upper(QEL_NO) like upper(?) OR upper(QEL_CUST_NAME) like upper(?)) ");

            LOGGER.info("Query :: {} []",query.toString(), params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while loading enquiry numbers autofill", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadQuoteList(String searchString) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{"Q%" + searchString + "%", "%" + searchString + "%"};
        Connection con = null;
        StringBuilder query = null;

        try {
               con = getDBConnection(getDataSource());
               query = new StringBuilder("SELECT QEL_QUOT_NO key, QEL_QUOT_NO ||'-'|| QEL_CUST_NAME value  , QEL_CUST_NAME info "
               		+ "  FROM Q_ENQUIRY_LOG "
                       + " WHERE (upper(QEL_QUOT_NO) like upper(?) OR upper(QEL_CUST_NAME) like upper(?)) "
                       + " AND upper(QEL_QUOT_NO) NOT LIKE 'P%' ");

            LOGGER.info("Query :: {} [{}]", query.toString(), params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while loading quote numbers autofill", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadPolicyList(String searchString) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{"P%" + searchString + "%", "%" + searchString + "%"};
        Connection con = null;
        StringBuilder query = null;

        try {
               con = getDBConnection(getDataSource());
               query = new StringBuilder("SELECT QEL_QUOT_NO key, QEL_QUOT_NO ||'-'|| QEL_CUST_NAME value  , QEL_CUST_NAME info "
                  		+ "  FROM Q_ENQUIRY_LOG "
                          + " WHERE (upper(QEL_QUOT_NO) like upper(?) OR upper(QEL_CUST_NAME) like upper(?)) "
                          + " AND upper(QEL_QUOT_NO) NOT LIKE 'Q%' ");


            LOGGER.info("Query :: {} []", params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while loading policy numbers autofill", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }


    public List<KeyValue> loadDealPipelineList(Boolean includeDefault) {

        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;

        try {
              con = getDBConnection(getDataSource());

              if(BooleanUtils.isTrue(includeDefault))
                 query = new StringBuilder("SELECT DISTINCT CDP_PL_ID key, CDP_PL_NAME value FROM T_CRM_DEAL_PIPELINE WHERE CDP_PL_ID NOT IN (0) ");
              else
            	  query = new StringBuilder("SELECT DISTINCT CDP_PL_ID key, CDP_PL_NAME value FROM T_CRM_DEAL_PIPELINE WHERE CDP_PL_ID NOT IN (0, 1)");

              LOGGER.info("Query :: {}" , query.toString());
              list = executeList(con, query.toString(), null , KeyValue.class);
        } catch (Exception e) {
              LOGGER.error("Error while loading deal pipelines", e);
        } finally {
            closeDbConn(con);
        }

        return list;
    }

    public List<CrmDealPipeline> loadTemplatePipelIne() {

    	List<CrmDealPipeline> list = null;
        Connection con = null;
        StringBuilder query = null;

        try {
              con = getDBConnection(getDataSource());

                 query = new StringBuilder("SELECT DISTINCT CDP_PL_ID cdpPlId, CDP_PL_NAME cdpPipelineName, "
                 		+ " CDP_STAGE_NO cdpStageNo , CDP_STAGE_TYP cdpStageTyp, CDP_STAGE_NAME cdpStageName"
                 		+ " FROM T_CRM_DEAL_PIPELINE WHERE CDP_PL_ID=0 ORDER BY cdpStageNo  ");

              LOGGER.info("Query :: {}" , query.toString());
              list = executeList(con, query.toString(), null , CrmDealPipeline.class);
        } catch (Exception e) {
              LOGGER.error("Error while loading template pipeline", e);
        } finally {
            closeDbConn(con);
        }

        return list;
    }

    public List<CrmDealPipeline> loadPipelineById(Long plId) {

    	List<CrmDealPipeline> list = null;
        Connection con = null;
        StringBuilder query = null;
        Object[] params = new Object[]{plId};

        try {
              con = getDBConnection(getDataSource());
                 query = new StringBuilder("SELECT DISTINCT CDP_PL_ID cdpPlId, CDP_PL_NAME cdpPlName,CDP_STAGE_PROB cdpStageProb, "
                 		+ " CDP_STAGE_NO cdpStageNo , CDP_STAGE_TYP cdpStageTyp, CDP_STAGE_NAME cdpStageName"
                 		+ " FROM T_CRM_DEAL_PIPELINE WHERE CDP_PL_ID= ? ORDER BY cdpStageNo ");

              LOGGER.info("Query :: {}{}" , query.toString(),params);
              list = executeList(con, query.toString(), params , CrmDealPipeline.class);
        } catch (Exception e) {
              LOGGER.error("Error while loading  pipeline by Id", e);
        } finally {
            closeDbConn(con);
        }

        return list;
    }


    public  CrmDeal loadDealById(String compCode , CrmDeal bean) {

    	Connection con = null;
        StringBuilder query = null;
        CrmDeal crmDeal = new CrmDeal();


        try {
            con = getDBConnection(getDataSource());
            Object[] param;
            List<Object> params = new LinkedList<>();
            params.add(bean.getCdDealId());





            query = new StringBuilder("SELECT CD_DEAL_ID cdDealId,  CD_PL_ID cdPlId,CDP_PL_NAME cdPipeLineName,  CD_STAGE_NO cdStageNo,CDP_STAGE_NAME cdStageName, " +
                 "CD_CONTACT_TYPE cdContactType, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CONT_TYP', CD_CONTACT_TYPE) cdContTypDesc, CD_CUST_CODE cdCustCode, " +
              	  "CD_PROD_CODE cdProdCode, PKG_REP_UTILITY.FN_GET_PROD_NAME(CD_PROD_CODE) cdProdName,PROD_LOB_CODE cdProdLob,  " +
             	  "CD_CUST_NAME cdCustName,CD_CONTACT_NAME cdContactName,CD_CONTACT_NO cdContactNo,CD_CONTACT_EMAIL cdContactEmail,CD_CRM_ID cdCrmId, PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CD_CRM_ID) cdCrmDesc, PKG_REP_UTILITY.FN_GET_PARA_VALUE('CRM_TYPE', CD_CRM_ID) cdCrmCssClass," +
                  "CD_DEAL_NAME cdDealName, CD_ENQ_NO cdEnqNo,CD_QUOT_NO cdQuotNo,  CD_POLICY_NO cdPolicyNo,CD_DEAL_VALUE cdDealVal, CD_DEAL_STATUS cdDealStatus," +
                  "CD_CUST_FIELD_1 cdCustFld1,  CD_CUST_FIELD_2 cdCustFld2,  CD_CUST_FIELD_3 cdCustFld3, CD_CUST_FIELD_4 cdCustFld4,  CD_CUST_FIELD_5 cdCustFld5, "+
                  "CD_CUST_FIELD_6 cdCustFld6,CD_CUST_FIELD_7 cdCustFld7, CD_CUST_FIELD_8 cdCustFld8,  CD_CUST_FIELD_9 cdCustFld9, CD_CUST_FIELD_10 cdCustFld10," +
                  "CD_CR_UID cdCrUid, USER_NAME cdCrUidDesc,  CD_CR_DT cdCrDt, CD_UPD_UID cdUpdUid, CD_UPD_DT cdUpDt, CD_LOST_REASON_CODE cdQuotDeclRes,PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOST_DL', CD_LOST_REASON_CODE) cdQuotDeclResDesc," +
                  "TO_CHAR(CD_CLOSE_DT, 'DD/MM/YYYY') cdDealClDt, TRUNC (SYSDATE - CD_CR_DT) cdDealAge FROM T_CRM_DEALS " +
                 " JOIN T_CRM_DEAL_PIPELINE ON CD_PL_ID=CDP_PL_ID AND CD_STAGE_NO=CDP_STAGE_NO "+
                 " JOIN TABLE(FN_GET_CRM_AGENTS(CD_CR_UID)) ON CD_CR_UID=USER_ID "+
                 " JOIN M_PRODUCT ON CD_PROD_CODE=PROD_CODE "+
                 " WHERE CD_DEAL_ID=?");



            param = params.toArray();
            LOGGER.info("Query :: {} {}", query, param);
            crmDeal = (CrmDeal) executeQuery(con, query.toString(), param, CrmDeal.class);

            if(StringUtils.equals(bean.getOperation(),"view"))
                crmDeal.setCdDealVal(getBaseCurrShortForCompany(compCode)+" "+Integer.parseInt(crmDeal.getCdDealVal())/1000+ "K");

        } catch (Exception e) {
            LOGGER.error("Error while retreiving deal by Id", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return crmDeal;
    }

    public List<KeyValue> loadDealsChartData(CrmDeal bean) {
        List<KeyValue> list = null;
        String query = null;
        Connection con = null;
        LinkedList<Object> params = new LinkedList<>();


        try {
            con = getDBConnection(getDataSource());
            if (null != bean.getCdChartType()) {
                switch (bean.getCdChartType().toUpperCase()) {
                    case "STAGE":
                    	query = "SELECT t1.CDP_STAGE_NO key,t1.CDP_STAGE_NAME value,NVL(t2.cnt,0) info1 FROM  T_CRM_DEAL_PIPELINE t1"
                    	+ " LEFT OUTER JOIN (SELECT COUNT(CD_DEAL_ID) cnt, CD_STAGE_NO,CD_PL_ID  FROM T_CRM_DEALS"
                        + " WHERE TRUNC(CD_CR_DT) " + bean.getCdDateRange().getRange()
                    	+ " GROUP BY CD_STAGE_NO,CD_PL_ID)  t2  "
                    	+ " ON CD_STAGE_NO=CDP_STAGE_NO "
                    	+ " AND CD_PL_ID=CDP_PL_ID"
                    	+ " WHERE CDP_PL_ID = ? "
                    	+ " ORDER BY key";
                        break;
                    case "FUNNEL":
                    case "PIE":
                    	query =" SELECT key,text,"
                       + " tot value, ROUND((tot/cuml)*100) info1"
                       + " FROM (SELECT t1.CDP_STAGE_NO key,t1.CDP_STAGE_NAME text,CDP_STAGE_TYP typ  ,"
                       + " NVL(cnt,0) cnt , "
                       + " CASE CDP_STAGE_NO "
                       + " WHEN "+getWonLostStageNo(bean.getCdPlId(),"WON") +" THEN cnt"
                       + " WHEN "+getWonLostStageNo(bean.getCdPlId(),"LOST")+" THEN cnt"
                       + " ELSE SUM(cnt) OVER(ORDER BY CDP_STAGE_NO DESC ) "
                       + " END  tot,"
                       + " SUM(cnt) OVER() cuml"
                       + " FROM  T_CRM_DEAL_PIPELINE t1"
                       + " LEFT OUTER JOIN ("
                       + " SELECT CD_PL_ID,CD_STAGE_NO,COUNT(CD_DEAL_ID) cnt "
                       + " FROM T_CRM_DEALS  "
                       + " GROUP BY CD_STAGE_NO,CD_PL_ID)  t2"
                       + " ON CD_STAGE_NO=CDP_STAGE_NO  AND CD_PL_ID=CDP_PL_ID"
                       + " WHERE CDP_PL_ID = ?  "
                       + " ORDER BY key ) x"
                       + " WHERE  key !="+getWonLostStageNo(bean.getCdPlId(),"LOST") ;
                        break;
                    case "WL":
                    	query =	"SELECT MONTH_VALUE key,SUBSTR(MONTH_DISPLAY,1,3) value,nvl(won,0) info1,NVL(lost,0) info2 FROM WWV_FLOW_MONTHS_MONTH LEFT OUTER JOIN " +
                        " (SELECT  t2.CD_PL_ID plId,to_char(TRUNC(sysdate),'Month') mon,COUNT(CD_DEAL_ID) won , t3.cnt lost  FROM T_CRM_DEALS  t2" +
                      " LEFT OUTER JOIN " +
                      " (SELECT CD_PL_ID, to_char(TRUNC(sysdate),'Month'), COUNT(CD_DEAL_ID) cnt  FROM T_CRM_DEALS   WHERE CD_STAGE_NO="+getWonLostStageNo(bean.getCdPlId(),"LOST")+
                      " GROUP BY TRUNC(CD_CR_DT),CD_PL_ID) t3 "+
                      " ON t2.CD_PL_ID=t3.CD_PL_ID "+
                      " WHERE t2.CD_STAGE_NO="+getWonLostStageNo(bean.getCdPlId(),"WON")+" AND t2.CD_PL_ID=? "+
                      " GROUP BY to_char(TRUNC(sysdate),'Month') ,t2.CD_PL_ID ) x " +
                      " ON MONTH_DISPLAY=x.mon ORDER BY MONTH_VALUE";
                        break;
                }
                 params.add(bean.getCdPlId());
            }

            LOGGER.info("Query :: {}", new Object[]{query, bean.getCdPlId()});
            list = executeList(con, query,params.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public  List<CrmDeal> loadDealData(CrmDeal crmDeal, String compCode) {

    	Connection con = null;
        String query = null;
        String select = null;
        String whereClause = "";
        List<CrmDeal> list = new LinkedList<>();

        try {
            con = getDBConnection(getDataSource());


            select = " WITH params AS(SELECT ? AS plId FROM DUAL)  (SELECT * FROM (";
            query = "SELECT CD_DEAL_ID cdDealId,  CD_PL_ID cdPlId,  CD_STAGE_NO cdStageNo,CDP_STAGE_NAME cdStageName," +
            	   "CD_CONTACT_TYPE cdContactType, PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_CONT_TYP', CD_CONTACT_TYPE) cdContTypDesc, " +
                   "CD_CUST_CODE cdCustCode,CD_CUST_NAME cdCustName, " +
               	   "CD_PROD_CODE cdProdCode,PKG_REP_UTILITY.FN_GET_PROD_NAME(CD_PROD_CODE) cdProdName," +
            	   "CD_CRM_ID cdCrmId, PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CD_CRM_ID) cdCrmDesc, PKG_REP_UTILITY.FN_GET_PARA_VALUE('CRM_TYPE', CD_CRM_ID) cdCrmCssClass," +
                   "CD_DEAL_NAME cdDealName, CD_ENQ_NO cdEnqNo,CD_QUOT_NO cdQuotNo,  CD_POLICY_NO cdPolicyNo,"+"\'"+getBaseCurrShortForCompany(compCode)+"\'"+"||' '|| PKG_REP_UTILITY.FN_GET_FORMAT_MASK("+compCode+",CD_DEAL_VALUE) cdDealVal, CD_DEAL_STATUS cdDealStatus," +
                   "CD_CUST_FIELD_1 cdCustFld1,  CD_CUST_FIELD_2 cdCustFld2,  CD_CUST_FIELD_3 cdCustFld3, CD_CUST_FIELD_4 cdCustFld4,  CD_CUST_FIELD_5 cdCustFld5, "+
                   "CD_CUST_FIELD_6 cdCustFld6,CD_CUST_FIELD_7 cdCustFld7, CD_CUST_FIELD_8 cdCustFld8,  CD_CUST_FIELD_9 cdCustFld9, CD_CUST_FIELD_10 cdCustFld10," +
                   "CD_CR_UID cdCrUid,USER_NAME cdCrUidDesc,   CD_CR_DT cdCrDt, CD_UPD_UID cdUpdUid, CD_UPD_DT cdUpDt, CD_LOST_REASON_CODE cdQuotDeclRes,PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_LOST_DL', CD_LOST_REASON_CODE) cdQuotDeclResDesc," +
                   "TO_CHAR(CD_CLOSE_DT, 'DD/MM/YYYY') cdDealClDt  ";

            query = query + " FROM T_CRM_DEALS JOIN TABLE(FN_GET_CRM_AGENTS(CD_CR_UID)) ON CD_CR_UID=USER_ID "
            + "JOIN T_CRM_DEAL_PIPELINE ON CD_PL_ID=CDP_PL_ID AND CD_STAGE_NO=CDP_STAGE_NO   "
            + "WHERE CD_CONTACT_TYPE=001 AND CD_PL_ID=(select plId from params)"
            + " UNION "
            + query +   " FROM T_CRM_DEALS JOIN TABLE(FN_GET_CRM_AGENTS(CD_CR_UID)) ON CD_CR_UID=USER_ID "
            + "JOIN T_CRM_DEAL_PIPELINE ON CD_PL_ID=CDP_PL_ID AND CD_STAGE_NO=CDP_STAGE_NO   "
            + "WHERE CD_CONTACT_TYPE=002 AND CD_PL_ID=(select plId from params)";

            if(BooleanUtils.isNotTrue(crmDeal.isShowClosed()))
            	whereClause = " ) WHERE cdCrUid=? AND cdDealStatus NOT IN ('WON' , 'LOST')) ";
            else
            	whereClause = ") WHERE cdCrUid=? )";

            select = select + query + whereClause;

            LOGGER.info("Query :: {} ", select);
            list = executeList(con, select, new Object[]{crmDeal.getCdPlId(),crmDeal.getCdCrUid()}, CrmDeal.class);


        } catch (Exception e) {
            LOGGER.error("Error while retreiving deals list", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    protected List executeStageDataList(Connection conn,boolean showCLosed,String owner,String compCode, String sql, Object[] params, Class clazz) throws Exception {


    	StageDataHandler h = new StageDataHandler(conn,showCLosed, owner,compCode);
    	QueryRunner run = new QueryRunner();
        List list = run.query(conn, sql, h, params);
        return list;

    }

    class StageDataHandler extends BeanListHandler<KeyValue> {

        private Connection connection;
        private boolean showClosed;
        private String compCode;
        private String owner;

        public StageDataHandler(Connection con,boolean showClosed,String owner, String compCode) {
            super(KeyValue.class);
            this.connection = con;
            this.showClosed = showClosed;
            this.compCode = compCode;
            this.owner = owner;
        }

        @Override
        public List<KeyValue> handle(ResultSet rs) throws SQLException {
            List<KeyValue> stageData = super.handle(rs);

            QueryRunner runner = new QueryRunner();
            BeanListHandler<CrmDeal> handler = new BeanListHandler<>(CrmDeal.class);
            LinkedList<Object> params = new LinkedList<>();
            String query = "SELECT CD_DEAL_ID cdDealId,NVL(t2.cnt,0) cdActivityCount,CD_STAGE_NO cdStageNo,CD_CONTACT_TYPE cdContactType,"
            		+  " CD_PROD_CODE cdProdCode,PKG_REP_UTILITY.FN_GET_PROD_NAME(CD_PROD_CODE) cdProdName,CD_CUST_CODE cdCustCode,CD_CUST_NAME cdCustName, CD_DEAL_NAME cdDealName,CD_DEAL_STATUS cdDealStatus,"+"\'"+getBaseCurrShortForCompany(compCode)+"\'"+"||' '|| PKG_REP_UTILITY.FN_GET_FORMAT_MASK("+compCode+",CD_DEAL_VALUE/1000)||'K' cdDealVal,"
            		+  " CD_CRM_ID cdCrmId, PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CD_CRM_ID) cdCrmDesc, PKG_REP_UTILITY.FN_GET_PARA_VALUE('CRM_TYPE', CD_CRM_ID) cdCrmCssClass "
            		+  " FROM T_CRM_DEALS t1  "
            		+  " LEFT OUTER JOIN (SELECT COUNT(CDL_ID) cnt ,CDL_DEAL_ID  FROM  T_CRM_DEALS_LOG "
            		+  " WHERE CDL_DEAL_ID IS NOT NULL "
            		+  " GROUP BY CDL_DEAL_ID) t2 "
            		+  " ON t1.CD_DEAL_ID=t2.CDL_DEAL_ID ";

            if(StringUtils.equalsIgnoreCase("ALL",owner))
            	query += "WHERE CD_PL_ID = ? AND CD_STAGE_NO=?";
            else {
            	query += " WHERE CD_CR_UID=? AND CD_PL_ID = ? AND CD_STAGE_NO=?";
            	params.add(owner);
            }

            if(BooleanUtils.isNotTrue(showClosed))
            	query += " AND CD_DEAL_STATUS NOT IN ('WON' , 'LOST') ";

        	  query += " ORDER BY CD_STAGE_NO ";


            for (KeyValue stage : stageData) {
            	params.add(stage.getKey());
            	params.add(stage.getValue());
                LOGGER.info("Query :: {} [{}{}]", query,params.toArray(),params.size());
                List<CrmDeal> deals
                  = runner.query(connection, query, handler, params.toArray());
                stage.setList1(deals);
                int last = params.size()-1;
                int lastBefore = params.size()-2;
            	params.remove(last);
            	params.remove(lastBefore);
            }
            return stageData;
        }
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

    public String getDealStatus(Long plId,Long stageNum) {

    	Connection con = null;
    	String dealStatus= null;
    	String query = null;
        ResultSet rs = null;
        List<Object> params = new LinkedList<>();
        KeyValue kv = new KeyValue();


        try {
            con = getDBConnection(getDataSource());
            params.add(plId);
            params.add(stageNum);

        	query = "SELECT CDP_STAGE_TYP key FROM T_CRM_DEAL_PIPELINE "
        			+ "WHERE CDP_PL_ID=? AND CDP_STAGE_NO=?";

            kv =  (KeyValue) executeQuery(con, query, params.toArray(), KeyValue.class);
            dealStatus =  kv.getKey();
        } catch (Exception e) {
            LOGGER.error("Error while getting deal status", e);
        } finally {
            query = null;
            closeDBComp(null, rs, con);
        }

        return  dealStatus;

    }

    public String getBaseCurrShortForCompany(String company) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String currency = null;

        try {
        con = getDBConnection(getDataSource());
        String sql = "SELECT t2.CURR_SHORT_NAME FROM M_COMPANY t1,M_CURRENCY t2 where "
        		+   " t1.COMP_BASE_CURR_CODE_1=t2.CURR_CODE AND COMP_CODE=? ";
        LOGGER.info("Query :: {}{} ", sql, company);
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

    public int saveDealActivity(final Activity activity) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            sql = " INSERT INTO T_CRM_DEALS_LOG   (CDL_ID, CDL_TYPE, CDL_CIVCDL_ID, CDL_LOG_DETAILS, CDL_CR_UID, CDL_CR_DT,CDL_CRM_ID,CDL_DEAL_ID)"
            		+ " VALUES(S_CDL_ID.NEXTVAL, ? , ? , ? , ?, SYSDATE, ? ,?)";
            ps = con.prepareStatement(sql);
            ps.setString(++i, activity.getTemplate());
            ps.setString(++i, activity.getCivilId());
            ps.setString(++i, activity.getMessage());
            ps.setString(++i, activity.getUserId());
            ps.setString(++i, activity.getCrmId());
            ps.setString(++i, activity.getDealId());

            LOGGER.info("Query :: {} [{}]", new Object[]{sql, activity.getTemplate(),activity.getCivilId(),activity.getMessage(),activity.getUserId(),activity.getCrmId(),activity.getDealId()});
            recCnt = ps.executeUpdate();
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error during save deals log => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public Long saveDealsEnquiryDetail(CrmDealEnquiry quotationEnqueryBean, String company) {
        String result = null;
        Connection con = null;
        PreparedStatement prs = null;
        int i = 0, recCnt = 0;
        String query = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());

            query = "SELECT S_QEL_NO.NEXTVAL FROM DUAL";
            quotationEnqueryBean.setEnqNo( getSeqVal(con, query));

            LOGGER.info("Enq Number :" + quotationEnqueryBean.getEnqNo());



            	query = "INSERT INTO Q_ENQUIRY_LOG ( QEL_DT,QEL_COMP_CODE,QEL_DIVN_CODE,QEL_DEPT_CODE, QEL_OPEN_POL_YN, QEL_MODE, QEL_SCH_CODE, QEL_LOB, QEL_VALID_DAYS,QEL_EXPIRY_DT,"
                        + " QEL_CUST_NAME, QEL_ASSR_NAME, QEL_REF_NO, QEL_REMARKS,QEL_EMAIL_ID,QEL_CONTACT_NO,QEL_SUB_DT,QEL_RECEIVED_BY, QEL_HANDLED_BY, QEL_STS,  QEL_CR_UID,"
                        + " QEL_CR_DT, QEL_QUOT_NO, QEL_TRANS_ID, QEL_TRAN_SR_NO, QEL_NO)VALUES(SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,SYSDATE,?,?,?,?)";

            LOGGER.debug("Query :: {}", query);
            prs = con.prepareStatement(query);
            int j = 0;

            prs.setString(++j, quotationEnqueryBean.getCompanyCode());
            prs.setString(++j, quotationEnqueryBean.getDivisionCode());
            prs.setString(++j, quotationEnqueryBean.getDeptCode());
            prs.setString(++j, quotationEnqueryBean.getOpenPolicyYesNo() == null ? "2" : quotationEnqueryBean.getOpenPolicyYesNo());
            prs.setString(++j, quotationEnqueryBean.getQuoteMode());
            prs.setString(++j, quotationEnqueryBean.getQuoteType());
            prs.setString(++j, quotationEnqueryBean.getQuoteLOB());
            prs.setInt(++j, quotationEnqueryBean.getExpdays());
            prs.setTimestamp(++j, new Timestamp(quotationEnqueryBean.getExpDt().getTime()));
            prs.setString(++j, quotationEnqueryBean.getCustName());
            prs.setString(++j, quotationEnqueryBean.getAssrName());
            prs.setString(++j, quotationEnqueryBean.getRefNo());
            prs.setString(++j, quotationEnqueryBean.getRemarks());
            prs.setString(++j, quotationEnqueryBean.getEmailId());
            prs.setString(++j, quotationEnqueryBean.getContactNo());
            prs.setTimestamp(++j, new Timestamp(quotationEnqueryBean.getSubDt().getTime()));
            prs.setString(++j, quotationEnqueryBean.getReceivedBy());
            prs.setString(++j, quotationEnqueryBean.getHandledBy());
            prs.setString(++j, quotationEnqueryBean.getStatus());
            prs.setString(++j, quotationEnqueryBean.getCreatedBy());
            prs.setString(++j, quotationEnqueryBean.getQuoteNo());
            prs.setLong(++j, quotationEnqueryBean.getTransId() ==null?0:quotationEnqueryBean.getTransId());
            prs.setInt(++j, quotationEnqueryBean.getTranSrNo() ==null?0:quotationEnqueryBean.getTranSrNo());
            prs.setLong(++j, quotationEnqueryBean.getEnqNo());
            recCnt = prs.executeUpdate();
            LOGGER.info("Q_ENQUIRY_LOG - Quotation Enquiry Information Saved");


        } catch (Exception e) {
            LOGGER.error("Error while save Q_ENQUIRY_LOG - Quotation Enquiry => {}", e);
            result = "Error in Q_ENQUIRY_LOG :: " + e.getMessage();
        } finally {
            closeDBComp(prs, rs, con);
            query = null;
        }
        return quotationEnqueryBean.getEnqNo();
    }

    public List<KeyValue> getLobApplDept(String lobCode) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        List<Object> params = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT LACD_LOB_CODE key,LACD_COMP_CODE value,LACD_DIVN_CODE info,LACD_DEPT_CODE info1 FROM M_LOB_APPL_DEPT "
                    + " WHERE LACD_LOB_CODE=? " )  ;
            params.add(lobCode);


            LOGGER.info("Query :: {}{} ", query, params.toArray());
            list = executeList(con, query.toString(),params.toArray(), KeyValue.class);



        } catch (Exception e) {
            LOGGER.error("Error while retreiving lob appl dept => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public Long getWonLostStageNo(Long plId , String type){

        Connection con = null;
        PreparedStatement ps = null;

        String sql = null;
        KeyValue kv = new KeyValue();
        List<Object> params = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            sql = " SELECT CDP_STAGE_NO key FROM T_CRM_DEAL_PIPELINE "
            	  +" WHERE CDP_PL_ID=? AND CDP_STAGE_TYP='" + type + "'";

            params.add(plId);

            LOGGER.info("Query :: {} [{}]", new Object[]{sql, plId});
             kv =   (KeyValue) executeQuery(con, sql, params.toArray(), KeyValue.class);
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error during getWonLostStageNo => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return Long.parseLong(kv.getKey());


    }



    public List<KeyValue> getDeptForUser(String userId, String compCode) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        List<Object> params = new LinkedList<>();

        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT CU_USER_ID key, MUCD_COMP_CODE value, MUCD_DIVN_CODE info, MUCD_DEPT_CODE info1 FROM M_USER_COMP_DIVN , M_CRM_USER " +
                                      "WHERE CU_USER_ID=MUCD_USER_ID AND CU_USER_ID=? AND MUCD_COMP_CODE=? ");


            params.add(userId);
            params.add(compCode);

            LOGGER.info("Query :: {}{} ", query, params.toArray() );
            list = executeList(con, query.toString(),params.toArray(), KeyValue.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving departments for user list => {}", e);
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
