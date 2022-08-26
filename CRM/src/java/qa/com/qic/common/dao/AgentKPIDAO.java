/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.TypeConstants.DateRange;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author karthik.j
 */
public class AgentKPIDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(AgentKPIDAO.class);

    private final String dataSource;

    public AgentKPIDAO(String dataSource) {
        this.dataSource = dataSource;
    }



    public List<KeyValue> loadAgentKPISummaryData(String type, String agent, DateRange dr) {
        List<KeyValue> list = null;
        String query = null;
        Connection con = null;
        LinkedList<Object> params = new LinkedList<>();


        try {
            con = getDBConnection(getDataSource());

                switch (type.toUpperCase()) {
                    case "LOB":
                    	query ="WITH params as (select ? as agent from dual)"
                                +" SELECT  agent,effDate,LOB_CODE , LOB_DESC key, target value,actual info, NVL(ROUND((actual/target*100),1),0) info1  FROM M_LOB lob JOIN("
                                +" SELECT t2.emp agent,t1.CT_YEAR,t1.CT_COMP_CODE,t1.CT_DIVN_CODE,t1.CT_DEPT_CODE,t1.CT_LOB_CODE,t1.maxEff effDate,t2.total target,"
                                +" deals.actual actual FROM (SELECT CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_LOB_CODE,MAX(CT_EFF_FRM_DT) maxEff "
                                +" FROM T_CRM_TARGETS WHERE CT_APPR_STS='APPROVED'  GROUP BY CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_LOB_CODE) t1"
                                +" JOIN ( SELECT CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_EFF_FRM_DT,CT_LOB_CODE,CT_EMP_CODE emp,SUM("+getTargetSumExpression(dr)+")  total"
                                +" FROM T_CRM_TARGETS  WHERE"
                                +" CT_YEAR=(select to_char(sysdate, 'YYYY') from dual) "
                                +" AND CT_APPR_STS='APPROVED' "
                                +" AND CT_EFF_FRM_DT <= SYSDATE "
                                +" AND CT_EMP_CODE=(select agent from params) GROUP BY CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_EFF_FRM_DT,"
                                +" CT_LOB_CODE,CT_EMP_CODE ) t2   ON t1.maxEff=t2.CT_EFF_FRM_DT  AND t2.CT_COMP_CODE=t1.CT_COMP_CODE AND t2.CT_DIVN_CODE=t1.CT_DIVN_CODE "
                                +" AND t2.CT_DEPT_CODE=t1.CT_DEPT_CODE  AND  t2.CT_LOB_CODE=t1.CT_LOB_CODE JOIN (SELECT PROD_LOB_CODE,CD_CR_UID,SUM(ACTUAL) actual from  (SELECT CD_CR_UID,CD_PROD_CODE,PROD_LOB_CODE,"
                                +" SUM(CD_DEAL_VALUE) actual FROM T_CRM_DEALS JOIN M_PRODUCT ON  PROD_CODE=CD_PROD_CODE "
                                +" WHERE CD_CR_UID=(select agent from params) AND CD_STAGE_NO=4 AND TRUNC(CD_UPD_DT) "  + dr.getRange()
                                +" GROUP BY CD_CR_UID,CD_PROD_CODE,PROD_LOB_CODE)"
                                +" GROUP BY CD_CR_UID,PROD_LOB_CODE) deals  "
                                +" ON t2.emp=deals.CD_CR_UID AND t2.CT_LOB_CODE=deals.PROD_LOB_CODE)   "
                                +" ON lob.LOB_CODE=CT_LOB_CODE  WHERE target != 0  ORDER BY LOB_DESC " ;
                        params.add(agent);
                        break;
                    case "PRODUCT":
                    	query ="WITH params as (select ? as agent from dual)"
                        +" SELECT  agent,effDate,PROD_CODE , PROD_DESC key, target value,actual info, NVL(ROUND((actual/target*100),1),0) info1  FROM M_PRODUCT prod JOIN("
                        +" SELECT t2.emp agent,t1.CT_YEAR,t1.CT_COMP_CODE,t1.CT_DIVN_CODE,t1.CT_DEPT_CODE,t1.CT_PROD_CODE,t1.maxEff effDate,t2.total target,"
                        +" deals.actual actual FROM (SELECT CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_PROD_CODE,MAX(CT_EFF_FRM_DT) maxEff "
                        +" FROM T_CRM_TARGETS WHERE CT_APPR_STS='APPROVED'  GROUP BY CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_PROD_CODE) t1"
                        +" JOIN ( SELECT CT_EFF_FRM_DT,CT_PROD_CODE,CT_EMP_CODE emp,SUM("+getTargetSumExpression(dr)+")  total"
                        +" FROM T_CRM_TARGETS  WHERE"
                        +" CT_YEAR=(select to_char(sysdate, 'YYYY') from dual) "
                        +" AND CT_APPR_STS='APPROVED' "
                        +" AND CT_EFF_FRM_DT <= SYSDATE "
                        +" AND CT_EMP_CODE=(select agent from params) GROUP BY CT_EFF_FRM_DT,"
                        +" CT_PROD_CODE,CT_EMP_CODE ) t2   ON t1.maxEff=t2.CT_EFF_FRM_DT  "
                        +" AND  t2.CT_PROD_CODE=t1.CT_PROD_CODE JOIN (SELECT CD_CR_UID,CD_PROD_CODE,SUM(CD_DEAL_VALUE) actual FROM T_CRM_DEALS "
                        +" WHERE CD_CR_UID=(select agent from params) AND CD_STAGE_NO=4 AND TRUNC(CD_UPD_DT) "  + dr.getRange()
                        +" GROUP BY CD_CR_UID,CD_PROD_CODE) deals  "
                        + " ON t2.emp=deals.CD_CR_UID AND t2.CT_PROD_CODE=deals.CD_PROD_CODE)   "
                        +"  ON prod.PROD_CODE=CT_PROD_CODE  WHERE target != 0  ORDER BY PROD_DESC " ;
                        params.add(agent);
                        break;
                /*    case "CHANNEL":
                    	query ="WITH params as (select ? as agent from dual)"
                                +" SELECT  agent,effDate,LOB_CODE , LOB_DESC key, target value,actual info, NVL(ROUND((actual/target*100),1),0) info1  FROM M_LOB lob JOIN("
                                +" SELECT t2.emp agent,t1.CT_YEAR,t1.CT_COMP_CODE,t1.CT_DIVN_CODE,t1.CT_DEPT_CODE,t1.CT_LOB_CODE,t1.maxEff effDate,t2.total target,"
                                +" deals.actual actual FROM (SELECT CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_LOB_CODE,MAX(CT_EFF_FRM_DT) maxEff "
                                +" FROM T_CRM_TARGETS WHERE CT_APPR_STS='APPROVED'  GROUP BY CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_LOB_CODE) t1"
                                +" JOIN ( SELECT CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_EFF_FRM_DT,CT_LOB_CODE,CT_EMP_CODE emp,SUM("+getTargetSumExpression(dr)+")  total"
                                +" FROM T_CRM_TARGETS  WHERE"
                                +" CT_YEAR=(select to_char(sysdate, 'YYYY') from dual) "
                                +" AND CT_APPR_STS='APPROVED' "
                                +" AND CT_EFF_FRM_DT <= SYSDATE "
                                +" AND CT_EMP_CODE=(select agent from params) GROUP BY CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_EFF_FRM_DT,"
                                +" CT_LOB_CODE,CT_EMP_CODE ) t2   ON t1.maxEff=t2.CT_EFF_FRM_DT  AND t2.CT_COMP_CODE=t1.CT_COMP_CODE AND t2.CT_DIVN_CODE=t1.CT_DIVN_CODE "
                                +" AND t2.CT_DEPT_CODE=t1.CT_DEPT_CODE  AND  t2.CT_LOB_CODE=t1.CT_LOB_CODE JOIN (SELECT PROD_LOB_CODE,CD_CR_UID,SUM(ACTUAL) actual from  (SELECT CD_CR_UID,CD_PROD_CODE,PROD_LOB_CODE,"
                                +" SUM(CD_DEAL_VALUE) actual FROM T_CRM_DEALS JOIN M_PRODUCT ON  PROD_CODE=CD_PROD_CODE "
                                +" WHERE CD_CR_UID='karthik' AND CD_STAGE_NO=4 "
                                +" GROUP BY CD_CR_UID,CD_PROD_CODE,PROD_LOB_CODE)"
                                +" GROUP BY CD_CR_UID,PROD_LOB_CODE) deals  "
                                +" ON t2.emp=deals.CD_CR_UID AND t2.CT_LOB_CODE=deals.PROD_LOB_CODE)   "
                                +" ON lob.LOB_CODE=CT_LOB_CODE  WHERE target != 0  ORDER BY LOB_DESC " ;
                        params.add(agent);
                        break;      */

                }




            LOGGER.info("Query :: {}{}", new Object[]{query, params.toArray()});
            list = executeList(con, query,params.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadAgentKPIChartData(String chart,String agent, DateRange dr) {
        List<KeyValue> list = null;
        String query = null;
        Connection con = null;
        LinkedList<Object> params = new LinkedList<>();
        String sumStr = null;


        try {
            con = getDBConnection(getDataSource());
            params.add(agent);

            switch (chart.toUpperCase()) {
            case "WIN_RATIO":
            	query = "WITH params as (SELECT ? as agent from dual) "
            		  +  " SELECT NVL(ROUND((won/new)*100 ,0 ),0) value  FROM (SELECT COUNT(CD_DEAL_ID) new  FROM T_CRM_DEALS"
                       + " WHERE  CD_CR_UID=(select agent from params) AND TRUNC(CD_CR_DT) " + dr.getRange()
            	       + " )  t1 JOIN "
            	       + " (SELECT COUNT(CD_DEAL_ID) won, CD_STAGE_NO  FROM T_CRM_DEALS"
                       + " WHERE CD_STAGE_NO=4 AND CD_UPD_UID=(select agent from params) AND TRUNC(CD_UPD_DT) "  + dr.getRange()
                       + " GROUP BY CD_STAGE_NO)  t2"
                       + " ON 1=1";
            	break;
            case "TA_GAUGE":
                query = "WITH params as (SELECT ? as agent from dual) "
                       + " SELECT t1.CT_EMP_CODE key,target info ,actual info1, NVL(ROUND((actual/target)*100 ,0 ),0) value from ((select CT_EMP_CODE,SUM("+getTargetSumExpression(dr)+") target FROM T_CRM_TARGETS "
          		       + " WHERE CT_YEAR=(select to_char(sysdate, 'YYYY') from dual)"
          		       + " AND CT_EMP_CODE=(select agent from params) AND CT_APPR_STS='APPROVED' GROUP BY CT_EMP_CODE ) t1"
            	       + " JOIN (SELECT SUM(CD_DEAL_VALUE) actual, CD_STAGE_NO  FROM T_CRM_DEALS"
                       + " WHERE CD_STAGE_NO=4 AND CD_UPD_UID=(select agent from params) AND TRUNC(CD_UPD_DT) "  + dr.getRange()
                       + " GROUP BY CD_STAGE_NO)  t2 ON 1=1 ) WHERE  target != 0   "  ;
                break;
            case "TA_BAR":
                query = "WITH params as (SELECT ? as agent from dual) "
                	   + " SELECT MONTH_VALUE key,SUBSTR(MONTH_DISPLAY,1,3) value,nvl(target,0) info1,NVL(actual,0) info2 FROM WWV_FLOW_MONTHS_MONTH "
                	   + " LEFT OUTER JOIN "
                       +" (select t1.CT_EMP_CODE key ,t1.month value,t1.target target,t2.actual actual from"
                       +" (SELECT CT_EMP_CODE,to_char(TRUNC(sysdate),'Month') month,SUM(CT_TOTAL) target FROM T_CRM_TARGETS"
                       +" WHERE CT_YEAR=(select to_char(sysdate, 'YYYY') from dual)"
                       +" AND CT_EMP_CODE=(select agent from params) AND CT_APPR_STS='APPROVED'"
                       +" GROUP BY CT_EMP_CODE,to_char(TRUNC(sysdate),'Month')) t1"
                       +" JOIN(SELECT CD_CR_UID,to_char(TRUNC(CD_CR_DT),'Month') month, SUM(CD_DEAL_VALUE) actual FROM T_CRM_DEALS WHERE CD_CR_UID=(select agent from params)"
                       +" AND to_char(CD_CR_DT, 'YYYY')=(select to_char(sysdate, 'YYYY') from dual)"
                       +" GROUP BY CD_CR_UID ,to_char(TRUNC(CD_CR_DT),'Month')) t2 "
                       +" ON  t1.CT_EMP_CODE=t2.CD_CR_UID AND t1.month=t2.month) x"
                       +" ON MONTH_DISPLAY=x.value ORDER BY MONTH_VALUE "   ;

                 break;


            }



            LOGGER.info("Query :: {}{}", new Object[]{query, params.toArray()});
            list = executeList(con, query,params.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }



    private String getTargetSumExpression(DateRange dr){

        Map<Integer, String> monthList  = new LinkedHashMap<>();
        monthList.put(1, "JAN");
        monthList.put(2, "FEB");
        monthList.put(3, "MAR");
        monthList.put(4, "APR");
        monthList.put(5, "MAY");
        monthList.put(6, "JUN");
        monthList.put(7, "JUL");
        monthList.put(8, "AUG");
        monthList.put(9, "SEP");
        monthList.put(10, "OCT");
        monthList.put(11, "NOV");
        monthList.put(12, "DEC");
		Date date = new Date();
	    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		ArrayList<String> sumStr = new ArrayList<>();
		String sum = null;



    	switch(dr.getDesc().toUpperCase().replace(" ", "_")){

    	case "THIS_MONTH" :
      	      sumStr.add( "CT_"+monthList.get(localDate.getMonthValue()));
  		      break;
       	case "LAST_MONTH" :
       		  localDate = localDate.minusMonths(1);
       		  sumStr.add(  "CT_"+monthList.get(localDate.getMonthValue()));
    		  break;
       	case "THIS_QUARTER" :
       	      sumStr.add(  "CT_"+localDate.getMonth().firstMonthOfQuarter().toString().substring(0,3));
       		  localDate = localDate.plusMonths(1) ;
       		  sumStr.add( "CT_"+localDate.getMonth().toString().substring(0,3));
       		  localDate = localDate.plusMonths(1) ;
       		  sumStr.add( "CT_"+localDate.getMonth().toString().substring(0,3));
       	      break;
       	case "LAST_QUARTER" :
       		  localDate= localDate.minusDays(90);
       		  sumStr.add( "CT_"+localDate.getMonth().firstMonthOfQuarter().toString().substring(0,3));
      		  localDate = localDate.plusMonths(1) ;
      		  sumStr.add(  "CT_"+localDate.getMonth().toString().substring(0,3));
      		  localDate = localDate.plusMonths(1) ;
      		  sumStr.add(  "CT_"+localDate.getMonth().toString().substring(0,3));
     	      break;
    	case "THIS_YEAR" :
    		 sumStr.add(  "CT_TOTAL");
    		 break;
    	}


    	sum = String.join("+", sumStr);

    	return sum;

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



    private String getBaseCurrShortForCompany(String company) {

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

	public String getDataSource() {
		return dataSource;
	}
}
