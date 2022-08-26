/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.corporate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.corporate.vo.KPITarget;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author karthik.j
 */
public class KpiDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(KpiDAO.class);

    private final String dataSource;

    public KpiDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public int saveTargetDetail(final KPITarget bean, final String operation) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0;
        int recCnt = 0;
        String sql = null;
        String whereParams = null;
        Long lvlNo = 0L;

        try {
        	 con = getDBConnection(getDataSource());

        	 lvlNo = getTargetLvlNum(bean);
             bean.setPlanType(getPlanType(lvlNo));




             if ("add".equalsIgnoreCase(operation)) {

               sql = "INSERT INTO T_CRM_TARGETS (CT_PLAN_TYP,CT_TRGT_TYP,CT_TRGT_LVL,CT_TRGT_LVL_NO,CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_LOB_CODE,CT_PROD_CODE,CT_CHANNEL,CT_EMP_CODE,CT_VER_NO,CT_EFF_FRM_DT,"
                		+ "CT_JAN, CT_FEB, CT_MAR,CT_APR,CT_MAY,CT_JUN,CT_JUL,CT_AUG,CT_SEP,CT_OCT,CT_NOV,CT_DEC, "
                        + "CT_TOTAL,CT_APPR_STS,CT_CR_UID,CT_CR_DT,CT_CRM_ID) "
                		+ "VALUES(:planTyp,:trgrtType ,:trgtLvl,:trgtLvlNo,:year,:compCode, :divCode, :deptCode , :lobCode , :prodCode , :channel , :empCode , :revision , TO_DATE(:eff_frm_dt, 'DD/MM/YYYY hh:mi AM') ,"
                		+ "0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0,"
                        + "0,\'OPEN\', :crUid,SYSDATE, :crmId)";

                   if(lvlNo == 1)
                      bean.setEffFrmDt(convertToDBTimeZone(bean.getEffFrmDt()));

                       ps = con.prepareStatement(sql);
                       ps.setString(++i, getPlanType(lvlNo));
                       ps.setString(++i, bean.getTargetType());
                       ps.setString(++i, bean.getLevel().toUpperCase());
                       ps.setLong(++i,  lvlNo);
                       ps.setLong(++i, bean.getYear());
                       ps.setString(++i, bean.getCompCode());
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getDivCode(),null));
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getDeptCode(),null));
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getLobCode(),null));
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getProdCode(),null));
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getChannel(),null));
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getEmpCode(),null));
                       ps.setLong(++i, bean.getRevision());
                       ps.setString(++i,  StringUtils.defaultIfEmpty(bean.getEffFrmDt(),null));
                       ps.setString(++i, bean.getCrUid());
                       ps.setString(++i, bean.getCrmId());
                       recCnt = ps.executeUpdate();

            } else if ("edit".equalsIgnoreCase(operation)) {


                sql = "UPDATE T_CRM_TARGETS SET CT_JAN=?, CT_FEB=?, CT_MAR=?,CT_APR=?,CT_MAY=?,CT_JUN=?,CT_JUL=?,CT_AUG=?,CT_SEP=?,CT_OCT=?,CT_NOV=?,CT_DEC=?,CT_TOTAL=?,"
                		+ "CT_UPD_UID = ?,CT_UPD_DT= SYSDATE,CT_CRM_ID = ?,CT_APPR_STS = 'OPEN' "
                        + " WHERE CT_PLAN_TYP=? AND CT_TRGT_TYP=? AND CT_TRGT_LVL= ? AND CT_YEAR = ? AND CT_VER_NO = ? AND CT_EFF_FRM_DT = TO_DATE(?, 'DD/MM/YYYY hh:mi AM') AND CT_COMP_CODE =? "
                        + " AND NVL(CT_DIVN_CODE,0) = NVL( ? , NVL(CT_DIVN_CODE,0)) "
                        + " AND NVL(CT_DEPT_CODE,0) = NVL( ? , NVL(CT_DEPT_CODE,0)) "
                        + " AND NVL(CT_LOB_CODE,0) = NVL( ? , NVL(CT_LOB_CODE,0)) "
                        + " AND NVL(CT_PROD_CODE,0) = NVL( ? , NVL(CT_PROD_CODE,0)) "
                        + " AND NVL(CT_CHANNEL,0) = NVL( ? , NVL(CT_CHANNEL,0)) "
                        + " AND NVL(CT_EMP_CODE,0) = NVL( ? , NVL(CT_EMP_CODE,0)) ";



                        LOGGER.info("Query :: {} [{},{},{},{},{},{},{},{},{},{},{},{}]", new Object[]{sql, bean.getPlanType(), bean.getTargetType(),bean.getLevel().toUpperCase(),bean.getYear(),
                        bean.getRevision(), bean.getEffFrmDt(), bean.getCompCode(),StringUtils.defaultIfEmpty(bean.getDivCode(),null),
                        StringUtils.defaultIfEmpty(bean.getDeptCode(),null), StringUtils.defaultIfEmpty(bean.getLobCode(),null),
                        StringUtils.defaultIfEmpty(bean.getProdCode(),null), StringUtils.defaultIfEmpty(bean.getChannel(),null),
                        StringUtils.defaultIfEmpty(bean.getEmpCode(),null)});



                        ps = con.prepareStatement(sql);
                        ps.setLong(++i, bean.getMonth_1());
                        ps.setLong(++i, bean.getMonth_2());
                        ps.setLong(++i, bean.getMonth_3());
                        ps.setLong(++i, bean.getMonth_4());
                        ps.setLong(++i, bean.getMonth_5());
                        ps.setLong(++i, bean.getMonth_6());
                        ps.setLong(++i, bean.getMonth_7());
                        ps.setLong(++i, bean.getMonth_8());
                        ps.setLong(++i, bean.getMonth_9());
                        ps.setLong(++i, bean.getMonth_10());
                        ps.setLong(++i, bean.getMonth_11());
                        ps.setLong(++i, bean.getMonth_12());
                        ps.setLong(++i, bean.getTotal());
                        ps.setString(++i, bean.getUpdUid());
                        ps.setString(++i, bean.getCrmId());
                        ps.setString(++i, bean.getPlanType());
                        ps.setString(++i, bean.getTargetType());
                        ps.setString(++i, bean.getLevel().toUpperCase());
                        ps.setLong(++i, bean.getYear());
                        ps.setLong(++i, bean.getRevision());
                        ps.setString(++i, bean.getEffFrmDt());
                        ps.setString(++i, bean.getCompCode());
                        ps.setString(++i, StringUtils.defaultString(bean.getDivCode(), null));
                        ps.setString(++i, StringUtils.defaultIfEmpty(bean.getDeptCode(),null));
                        ps.setString(++i, StringUtils.defaultIfEmpty(bean.getLobCode(),null));
                        ps.setString(++i, StringUtils.defaultIfEmpty(bean.getProdCode(),null));
                        ps.setString(++i, StringUtils.defaultIfEmpty(bean.getChannel(),null));
                        ps.setString(++i, StringUtils.defaultIfEmpty(bean.getEmpCode(),null));

                        recCnt = ps.executeUpdate();

                  	    updateTargetApprStatus(bean, TypeConstants.KPITargetApprovalStatus.OPEN.getCode());


            } else if("delete".equalsIgnoreCase(operation)){

            	if( StringUtils.equals(TypeConstants.KPITargetLevel.COMPANY.getCode(),  bean.getLevel())  ||
                    StringUtils.equals(TypeConstants.KPITargetLevel.DIVISION.getCode(),  bean.getLevel()) ||
                    StringUtils.equals(TypeConstants.KPITargetLevel.DEPARTMENT.getCode(),  bean.getLevel()) ){

                	  whereParams = "(\'COMPANY\',\'DIVISION\',\'DEPARTMENT\')";

                }else if(StringUtils.equals(TypeConstants.KPITargetLevel.LOB.getCode(),  bean.getLevel())     ||
                         StringUtils.equals(TypeConstants.KPITargetLevel.PRODUCT.getCode(),  bean.getLevel()) ||
                         StringUtils.equals(TypeConstants.KPITargetLevel.CHANNEL.getCode(),  bean.getLevel()) ||
                         StringUtils.equals(TypeConstants.KPITargetLevel.EMPLOYEE.getCode(),  bean.getLevel())) {

                	  whereParams = "(\'LOB\',\'PRODUCT\',\'CHANNEL\',\'EMPLOYEE\')";


                }

          	     updateTargetApprStatus(bean,"OPEN");

            	 sql = "DELETE FROM T_CRM_TARGETS" + " WHERE CT_TRGT_LVL IN" + whereParams
            			+ " AND CT_YEAR = ? AND CT_VER_NO = ? AND CT_EFF_FRM_DT = TO_DATE(?, 'DD/MM/YYYY hh:mi AM') "
            	 		+ " AND CT_COMP_CODE =?"
                        + " AND NVL(CT_DIVN_CODE,0) = NVL( ? , NVL(CT_DIVN_CODE,0)) "
                        + " AND NVL(CT_DEPT_CODE,0) = NVL( ? , NVL(CT_DEPT_CODE,0)) "
                        + " AND NVL(CT_LOB_CODE,0) = NVL( ? , NVL(CT_LOB_CODE,0)) "
                        + " AND NVL(CT_PROD_CODE,0) = NVL( ? , NVL(CT_PROD_CODE,0)) "
                        + " AND NVL(CT_CHANNEL,0) = NVL( ? , NVL(CT_CHANNEL,0)) "
                        + " AND NVL(CT_EMP_CODE,0) = NVL( ? , NVL(CT_EMP_CODE,0)) ";


                        LOGGER.info("Query :: {} [{},{},{},{},{},{},{},{},{}]", new Object[]{sql, bean.getYear(),bean.getRevision(),bean.getEffFrmDt(), bean.getCompCode(),
                         StringUtils.defaultIfEmpty(bean.getDivCode(),null),  StringUtils.defaultIfEmpty(bean.getDeptCode(),null), StringUtils.defaultIfEmpty(bean.getLobCode(),null),
                         StringUtils.defaultIfEmpty(bean.getProdCode(),null), StringUtils.defaultIfEmpty(bean.getChannel(),null),  StringUtils.defaultIfEmpty(bean.getEmpCode(),null)});

            		   ps = con.prepareStatement(sql);
                       ps.setLong(++i, bean.getYear());
                       ps.setLong(++i, bean.getRevision());
                       ps.setString(++i, bean.getEffFrmDt());
                       ps.setString(++i, bean.getCompCode());
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getDivCode(),null));
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getDeptCode(),null));
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getLobCode(),null));
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getProdCode(),null));
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getChannel(),null));
                       ps.setString(++i, StringUtils.defaultIfEmpty(bean.getEmpCode(),null));
                       recCnt = ps.executeUpdate();



            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error adding/editing targets => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public List<KPITarget> loadTargetById(KPITarget bean) {
        List<KPITarget> list = null;
        Connection con = null;
        StringBuilder query = null;
        Object[] param;
        List<Object> params = new LinkedList<>();

        try {


               con = getDBConnection(getDataSource());
               query = new StringBuilder("SELECT CT_TRGT_TYP targetType, PKG_REP_UTILITY.FN_GET_AC_NAME(\'CRM_TRGT_TYP\', CT_TRGT_TYP) trgtTypName, LOWER(CT_TRGT_LVL) AS \"level\",CT_TRGT_LVL_NO trgtLvlNo, CT_VER_NO revision,CT_YEAR year ,"
               		+ " CT_COMP_CODE compCode , PKG_REP_UTILITY.FN_GET_COMP_NAME(CT_COMP_CODE) compName , "
            		+ " CT_DIVN_CODE divCode , PKG_REP_UTILITY.FN_GET_DIVN_NAME(CT_COMP_CODE,CT_DIVN_CODE) divName , "
            		+ " CT_DEPT_CODE deptCode , PKG_REP_UTILITY.FN_GET_DEPT_NAME(CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE) deptName , "
            		+ " CT_LOB_CODE lobCode , PKG_REP_UTILITY.FN_GET_LOB_NAME(CT_LOB_CODE) lobName , "
            		+ " CT_PROD_CODE prodCode , PKG_REP_UTILITY.FN_GET_PROD_NAME(CT_PROD_CODE) prodName , "
            		+ " CT_CHANNEL channel ,  PKG_REP_UTILITY.FN_GET_AC_NAME('MENU_CODE', CT_CHANNEL) channelName , "
            		+ " CT_EMP_CODE empCode , PKG_REP_UTILITY.FN_GET_USER_NAME(CT_EMP_CODE) empName , "
               		+ " CT_JAN month_1,CT_FEB month_2,CT_MAR month_3,CT_APR month_4,CT_MAY month_5,CT_JUN month_6,CT_JUL month_7,"
            		+ " CT_AUG month_8,CT_SEP month_9,CT_OCT month_10,CT_NOV month_11,CT_DEC month_12,CT_TOTAL total, (SELECT TO_CHAR(CT_EFF_FRM_DT, 'DD/MM/YYYY hh:mi AM') FROM dual) effFrmDt"
               		+ " FROM T_CRM_TARGETS "
               		+ " WHERE CT_PLAN_TYP= ? AND CT_TRGT_TYP=? AND NVL(CT_TRGT_LVL,0) = NVL(?,0) AND CT_COMP_CODE =? AND  CT_YEAR = ? AND CT_VER_NO = ?"
               		+ " AND CT_EFF_FRM_DT = TO_DATE( ? , 'DD/MM/YYYY hh:mi AM')      "
               		+ " AND NVL(CT_DIVN_CODE,0) = NVL(?,0) AND  NVL(CT_DEPT_CODE,0) = NVL(?,0) "
            	    + " AND NVL(CT_LOB_CODE,0) = NVL(?,0)  AND NVL(CT_PROD_CODE,0) = NVL(?,0) AND NVL(CT_CHANNEL,0) = NVL(?,0) AND NVL(CT_EMP_CODE,0) = NVL(?,0)  ");

               params.add(bean.getPlanType());
               params.add(bean.getTargetType());
               params.add(StringUtils.defaultIfEmpty(bean.getLevel().toUpperCase(),null));
               params.add(bean.getCompCode());
               params.add(bean.getYear());
               if(bean.getTrgtLvlNo() < 4)
                   params.add(bean.getRevision());
               else
            	   params.add(getMaxRevNumForSalesPlan(bean));
               params.add(bean.getEffFrmDt());
               params.add(StringUtils.defaultIfEmpty(bean.getDivCode(),null));
               params.add(StringUtils.defaultIfEmpty(bean.getDeptCode(),null));
               params.add(StringUtils.defaultIfEmpty(bean.getLobCode(),null));
               params.add(StringUtils.defaultIfEmpty(bean.getProdCode(),null));
               params.add(StringUtils.defaultIfEmpty(bean.getChannel(),null));
               params.add(StringUtils.defaultIfEmpty(bean.getEmpCode(),null));


               param = params.toArray();

               LOGGER.info("Query########### :: {}{}", query.toString(), params.toArray());
               list = executeList(con, query.toString(), param, KPITarget.class);




        } catch (Exception e) {
            LOGGER.error("Error while retreiving target by Id", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }
    public int saveTargetApproval(KPITarget bean) {
        Connection con = null;
    	int i = 0;
        int recCnt = 0;
        PreparedStatement ps = null;
        StringBuilder query = null;

        try {

               bean.setPlanType(getPlanType(bean.getTrgtLvlNo()));
        	  // updateApprovedTargetsToExpired(bean);

               con = getDBConnection(getDataSource());
               query = new StringBuilder("UPDATE T_CRM_TARGETS SET CT_APPR_STS = 'APPROVED' , CT_APPR_UID = ? , CT_APPR_DT = SYSDATE "
               		+ " WHERE CT_PLAN_TYP=? AND CT_TRGT_TYP=? AND CT_YEAR = ? AND  CT_COMP_CODE = ? AND CT_VER_NO = ? "
               		+ " AND CT_EFF_FRM_DT=TO_DATE( ? , 'DD/MM/YYYY hh:mi AM')  ");


               ps = con.prepareStatement(query.toString());
               ps.setString(++i, bean.getApprUid());
               ps.setString(++i, bean.getPlanType());
               ps.setString(++i, bean.getTargetType());
               ps.setLong(++i, bean.getYear());
               ps.setString(++i, bean.getCompCode());
               if(bean.getTrgtLvlNo() > 3){
            	  bean.setRevision(getMaxRevNumForSalesPlan(bean));
                  ps.setLong(++i,bean.getRevision());
               } else {
                  ps.setLong(++i, bean.getRevision());
               }
               ps.setString(++i, bean.getEffFrmDt());


               LOGGER.info("Query :: {} [{},{},{}, {},{},{},{}]", new Object[]{query, bean.getApprUid(),bean.getPlanType(),
            		       bean.getTargetType(),bean.getYear(), bean.getCompCode(),bean.getRevision(),bean.getEffFrmDt()});
               recCnt = ps.executeUpdate();



          } catch (Exception e) {
            LOGGER.error("Error during target approval", e);
        } finally {
        	query = null;
            closeDbConn(con);
        }

        return recCnt;
    }

    public int saveTargetAmend(KPITarget bean) {
        Connection con = null;
    	int i = 0;
        int recCnt = 0;
        PreparedStatement ps = null;
        String sql = null;


        try {
               con = getDBConnection(getDataSource());


                        bean.setPlanType(getPlanType(bean.getTrgtLvlNo()));


                        if(StringUtils.equals(bean.getPlanType(),TypeConstants.KPIPlanType.TARGET.getCode())){
                            sql = "INSERT INTO T_CRM_TARGETS (CT_PLAN_TYP,CT_TRGT_TYP,CT_TRGT_LVL,CT_TRGT_LVL_NO,CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_LOB_CODE,CT_PROD_CODE,CT_CHANNEL,CT_EMP_CODE,CT_VER_NO,CT_EFF_FRM_DT,"
                            		   + "CT_JAN, CT_FEB, CT_MAR,CT_APR,CT_MAY,CT_JUN,CT_JUL,CT_AUG,CT_SEP,CT_OCT,CT_NOV,CT_DEC, "
                                    + "CT_TOTAL,CT_APPR_STS,CT_CR_UID,CT_CR_DT,CT_CRM_ID) "
                            		   + "SELECT CT_PLAN_TYP,CT_TRGT_TYP,CT_TRGT_LVL,CT_TRGT_LVL_NO,CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_LOB_CODE,CT_PROD_CODE,CT_CHANNEL,CT_EMP_CODE,CT_VER_NO+1,TO_DATE(?, 'DD/MM/YYYY hh:mi AM'),"
                            		   + "CT_JAN, CT_FEB, CT_MAR,CT_APR,CT_MAY,CT_JUN,CT_JUL,CT_AUG,CT_SEP,CT_OCT,CT_NOV,CT_DEC, "
                                    + " CT_TOTAL,'OPEN',?,SYSDATE,? FROM T_CRM_TARGETS"
                            		+ " WHERE CT_PLAN_TYP=? AND CT_TRGT_TYP=? AND  CT_YEAR= ? AND CT_VER_NO= ? AND CT_APPR_STS='APPROVED' "
                            		+ " AND CT_COMP_CODE=? ";

                            ps = con.prepareStatement(sql);
                            ps.setString(++i,  convertToDBTimeZone(bean.getEffFrmDt()));
                            ps.setString(++i, bean.getCrUid());
                            ps.setString(++i, bean.getCrmId());
                            ps.setString(++i, bean.getPlanType());
                            ps.setString(++i, bean.getTargetType());
                            ps.setLong(++i, bean.getYear());
                            ps.setLong(++i, bean.getRevision());
                            ps.setString(++i, bean.getCompCode());

                        }else if(StringUtils.equals(bean.getPlanType(),TypeConstants.KPIPlanType.SALES.getCode())){

                            sql = "INSERT INTO T_CRM_TARGETS (CT_PLAN_TYP,CT_TRGT_TYP,CT_TRGT_LVL,CT_TRGT_LVL_NO,CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_LOB_CODE,CT_PROD_CODE,CT_CHANNEL,CT_EMP_CODE,CT_VER_NO,CT_EFF_FRM_DT,"
                            		   + "CT_JAN, CT_FEB, CT_MAR,CT_APR,CT_MAY,CT_JUN,CT_JUL,CT_AUG,CT_SEP,CT_OCT,CT_NOV,CT_DEC, "
                                    + "CT_TOTAL,CT_APPR_STS,CT_CR_UID,CT_CR_DT,CT_CRM_ID) "
                            		   + "SELECT CT_PLAN_TYP,CT_TRGT_TYP,CT_TRGT_LVL,CT_TRGT_LVL_NO,CT_YEAR,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_LOB_CODE,CT_PROD_CODE,CT_CHANNEL,CT_EMP_CODE,CT_VER_NO+1,TO_DATE(?, 'DD/MM/YYYY hh:mi AM'),"
                            		   + "CT_JAN, CT_FEB, CT_MAR,CT_APR,CT_MAY,CT_JUN,CT_JUL,CT_AUG,CT_SEP,CT_OCT,CT_NOV,CT_DEC, "
                                    + "CT_TOTAL,'OPEN',?,SYSDATE,? FROM T_CRM_TARGETS"
                            		   +" WHERE CT_PLAN_TYP=? AND CT_TRGT_TYP=?  AND  CT_YEAR= ? AND CT_VER_NO= ? AND CT_APPR_STS='APPROVED' "
                            		   + "AND CT_COMP_CODE=? AND CT_DIVN_CODE=? AND CT_DEPT_CODE=?";

                            ps = con.prepareStatement(sql);
                            ps.setString(++i, bean.getEffFrmDt());
                            ps.setString(++i, bean.getCrUid());
                            ps.setString(++i, bean.getCrmId());
                            ps.setString(++i, bean.getPlanType());
                            ps.setString(++i, bean.getTargetType());
                            ps.setLong(++i, bean.getYear());
                            ps.setLong(++i, bean.getRevision());
                            ps.setString(++i, bean.getCompCode());
                            ps.setString(++i, bean.getDivCode());
                            ps.setString(++i, bean.getDeptCode());
                        }




                        LOGGER.info("Query :: {} [{},{},{},{},{}]", new Object[]{sql, bean.getPlanType(),bean.getTargetType(), bean.getCompCode(),bean.getYear(),bean.getRevision()});
                        recCnt = ps.executeUpdate();




        } catch (Exception e) {
            LOGGER.error("Error during target amend", e);
        } finally {
        	sql = null;
            closeDbConn(con);
        }

        return recCnt;
    }

    public List<KPITarget> loadCompanyTargetHistoryData(KPITarget bean) {
        List<KPITarget> list = null;
        Connection con = null;
        StringBuilder query = null;
        List<Object> params = new LinkedList<>();


        try {
              con = getDBConnection(getDataSource());
              params.add(bean.getYear());
              params.add(bean.getCompCode());
              params.add(bean.getTrgtLvlNo());
              params.add(bean.getTargetType());


               query = new StringBuilder( "SELECT t1.CT_VER_NO revision,t2.maxRev maxRevision,LOWER(t1.CT_TRGT_LVL) AS \"level\",t1.CT_TRGT_LVL_NO trgtLvlNo,"
                       + " t1.CT_YEAR year,t1.CT_COMP_CODE compCode,t1.CT_VER_NO , PKG_REP_UTILITY.FN_GET_COMP_NAME(T1.CT_COMP_CODE)  compName, t1.CT_JAN month_1, t1.CT_FEB month_2, "
                       + " t1.CT_MAR month_3, t1.CT_APR month_4, t1.CT_MAY month_5, t1.CT_JUN month_6, t1.CT_JUL month_7, t1.CT_AUG month_8,"
                       + " t1.CT_SEP month_9, t1.CT_OCT month_10, t1.CT_NOV month_11, t1.CT_DEC month_12, t1.CT_TOTAL total, t1.CT_APPR_STS apprStatus,"
                       + " t1.CT_CR_UID crUid,t1.CT_CR_DT crDt,t1.CT_UPD_UID updUid,t1.CT_UPD_DT upDt,t1.CT_APPR_UID apprUid,t1.CT_APPR_DT apprDt,TO_CHAR(t1.CT_EFF_FRM_DT, 'DD/MM/YYYY hh:mi AM')  effFrmDt"
                       + " FROM T_CRM_TARGETS t1"
                       + " JOIN (SELECT MAX(CT_VER_NO) maxRev, CT_YEAR ,CT_COMP_CODE FROM T_CRM_TARGETS"
                       + " GROUP BY CT_YEAR, CT_COMP_CODE ) t2"
                       + " ON t1.CT_YEAR=t2.CT_YEAR  AND t1.CT_COMP_CODE=t2.CT_COMP_CODE"
                       + " AND t1.CT_YEAR=? "
                       + " AND t1.CT_COMP_CODE=? "
                       + " AND t1.CT_TRGT_LVL_NO=?"
                       + " AND t1.CT_TRGT_TYP=LPAD(?,3,'0')"
                       + " ORDER BY t1.CT_VER_NO DESC");



               LOGGER.info("Query :: {}{}", query ,params.toArray());
               list = executeList(con, query.toString(), params.toArray(), KPITarget.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving company history data", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KPITarget> loadSalesPlanTargetHistoryData(KPITarget bean) {
        List<KPITarget> list = null;
        Connection con = null;
        String query = null;
        String addCondition = "";
        List<Object> params = new LinkedList<>();


        try {
              con = getDBConnection(getDataSource());


              params.add(bean.getYear());
              params.add(bean.getTrgtLvlNo());
              params.add(bean.getTargetType());
              params.add(bean.getCompCode());
              params.add(bean.getDivCode());
              params.add(bean.getDeptCode());

              if(StringUtils.isNotBlank(bean.getLobCode())){
                  addCondition = addCondition +  " AND  NVL(CT_LOB_CODE,0) = NVL(?,0)  ";
                  params.add(bean.getLobCode());
              }
              if(StringUtils.isNotBlank(bean.getProdCode())){
	                addCondition = addCondition +  " AND  NVL(CT_PROD_CODE,0) = NVL(?,0) ";
                    params.add(bean.getProdCode());
             }
	         if(StringUtils.isNotBlank(bean.getChannel())){
                  addCondition = addCondition +  " AND  NVL(CT_CHANNEL,0) = NVL(?,0)  ";
                  params.add(bean.getChannel());
	         }
	         if(StringUtils.isNotBlank(bean.getEmpCode())){
        	         addCondition = addCondition +  " AND  NVL(CT_EMP_CODE,0) = NVL(?,0)";
                     params.add(bean.getEmpCode());
	         }


            query = "SELECT t1.CT_VER_NO revision,t2.maxRev maxRevision,LOWER(t1.CT_TRGT_LVL) AS \"level\",t1.CT_TRGT_LVL_NO trgtLvlNo,"
                         + " t1.CT_YEAR year,t1.CT_COMP_CODE compCode,t1.CT_VER_NO , PKG_REP_UTILITY.FN_GET_COMP_NAME(T1.CT_COMP_CODE)  compName, "
                 		 + " t1.CT_DIVN_CODE divCode , PKG_REP_UTILITY.FN_GET_DIVN_NAME(t1.CT_COMP_CODE,t1.CT_DIVN_CODE) divName , "
                		 + " t1.CT_DEPT_CODE deptCode , PKG_REP_UTILITY.FN_GET_DEPT_NAME(t1.CT_COMP_CODE,t1.CT_DIVN_CODE,t1.CT_DEPT_CODE) deptName , "
             		     + " CT_LOB_CODE lobCode , PKG_REP_UTILITY.FN_GET_LOB_NAME(CT_LOB_CODE) lobName , "
             		     + " CT_PROD_CODE prodCode , PKG_REP_UTILITY.FN_GET_PROD_NAME(CT_PROD_CODE) prodName , "
             		     + " CT_CHANNEL channel , PKG_REP_UTILITY.FN_GET_AC_NAME('MENU_CODE', CT_CHANNEL) channelName , "
             		     + " CT_EMP_CODE empCode , PKG_REP_UTILITY.FN_GET_USER_NAME(CT_EMP_CODE) empName , "
                         + " t1.CT_JAN month_1, t1.CT_FEB month_2, "
                         + " t1.CT_MAR month_3, t1.CT_APR month_4, t1.CT_MAY month_5, t1.CT_JUN month_6, t1.CT_JUL month_7, t1.CT_AUG month_8,"
                         + " t1.CT_SEP month_9, t1.CT_OCT month_10, t1.CT_NOV month_11, t1.CT_DEC month_12, t1.CT_TOTAL total, t1.CT_APPR_STS apprStatus,"
                         + " t1.CT_CR_UID crUid,t1.CT_CR_DT crDt,t1.CT_UPD_UID updUid,t1.CT_UPD_DT upDt,t1.CT_APPR_UID apprUid,t1.CT_APPR_DT apprDt, TO_CHAR(t1.CT_EFF_FRM_DT, 'DD/MM/YYYY hh:mi AM') effFrmDt"
                         + " FROM T_CRM_TARGETS t1"
                         + " JOIN (SELECT MAX(CT_VER_NO) maxRev, CT_YEAR ,CT_COMP_CODE FROM T_CRM_TARGETS "
                         + " GROUP BY CT_YEAR ,CT_COMP_CODE) t2"
                         + " ON t1.CT_YEAR=t2.CT_YEAR  AND t1.CT_COMP_CODE=t2.CT_COMP_CODE"
                         + " AND t1.CT_YEAR=? "
                         + " AND t1.CT_TRGT_LVL_NO=?"
                         + " AND t1.CT_TRGT_TYP=LPAD(?,3,'0')"
                         + " AND  t1.CT_COMP_CODE=? AND t1.CT_DIVN_CODE=? AND t1.CT_DEPT_CODE=? " + addCondition
                         + " ORDER BY t1.CT_VER_NO DESC";



               LOGGER.info("Query :: {}{}", query,params.toArray());
               list = executeList(con, query, params.toArray(), KPITarget.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving sales plan history data", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KPITarget> loadTargetData(KPITarget bean) {
        List<KPITarget> list = new LinkedList<>();

        Connection con = null;
        String query = null;
        String orderBy = null;
        String codeName = null;
         String  addCondition = "";
        List<Object> params = new LinkedList<>();

        try {
              con = getDBConnection(getDataSource());

               query = "SELECT CT_PLAN_TYP planType, CT_TRGT_TYP targetType, LOWER(CT_TRGT_LVL) AS \"level\" ,CT_TRGT_LVL_NO trgtLvlNo, CT_VER_NO revision, CT_YEAR year,"
                      + " CT_COMP_CODE compCode , PKG_REP_UTILITY.FN_GET_COMP_NAME(CT_COMP_CODE) compName , "
            		    + " CT_DIVN_CODE divCode , PKG_REP_UTILITY.FN_GET_DIVN_NAME(CT_COMP_CODE,CT_DIVN_CODE) divName , "
            		    + " CT_DEPT_CODE deptCode , PKG_REP_UTILITY.FN_GET_DEPT_NAME(CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE) deptName , "
            		    + " CT_LOB_CODE lobCode , PKG_REP_UTILITY.FN_GET_LOB_NAME(CT_LOB_CODE) lobName , "
            		    + " CT_PROD_CODE prodCode , PKG_REP_UTILITY.FN_GET_PROD_NAME(CT_PROD_CODE) prodName , "
            		    + " CT_CHANNEL channel , PKG_REP_UTILITY.FN_GET_AC_NAME('MENU_CODE', CT_CHANNEL) channelName , "
            		    + " CT_EMP_CODE empCode , PKG_REP_UTILITY.FN_GET_USER_NAME(CT_EMP_CODE) empName , "
               	        + " CT_JAN month_1,CT_FEB month_2,CT_MAR month_3,CT_APR month_4,CT_MAY month_5,CT_JUN month_6,CT_JUL month_7,"
            		    + " CT_AUG month_8,CT_SEP month_9,CT_OCT month_10,CT_NOV month_11,CT_DEC month_12,CT_TOTAL total,"
            		    + " CT_CR_UID crUid,CT_CR_DT crDt, CT_UPD_UID upUid, CT_UPD_DT upDt,CT_APPR_STS apprStatus, CT_APPR_DT apprDt, CT_APPR_UID apprUid,TO_CHAR(CT_EFF_FRM_DT, 'DD/MM/YYYY hh:mi AM') effFrmDt "
                        + " FROM T_CRM_TARGETS WHERE CT_PLAN_TYP= ? AND CT_TRGT_TYP= NVL(?, '001')  AND CT_TRGT_LVL= ? AND CT_COMP_CODE =? AND  CT_YEAR = ? AND CT_VER_NO = ?  ";


                        String planTyp = getPlanType(bean.getTrgtLvlNo());
                        bean.setPlanType(planTyp);
                        params.add(bean.getPlanType());
                        params.add(StringUtils.defaultIfEmpty(bean.getTargetType(),null));
                        params.add(StringUtils.defaultIfEmpty(bean.getLevel().toUpperCase(),null));
                        params.add(StringUtils.defaultIfEmpty(bean.getCompCode(),null));
                        params.add(bean.getYear());
                        params.add(bean.getRevision());


              if(StringUtils.equals(TypeConstants.KPITargetLevel.COMPANY.getCode(), bean.getLevel())){


                  query = "WITH params AS(SELECT ? AS trgtTyp FROM DUAL) "
                  		+ "SELECT t1.CT_PLAN_TYP planType, t1.CT_TRGT_TYP targetType, LOWER(CT_TRGT_LVL) AS \"level\" ,CT_TRGT_LVL_NO trgtLvlNo, t1.CT_VER_NO revision,t2.maxRevision maxRevision, t1.CT_YEAR year,t1.CT_COMP_CODE compCode, "
            		        + "PKG_REP_UTILITY.FN_GET_COMP_NAME(t1.CT_COMP_CODE) compName, "
            		        + "CT_JAN month_1,CT_FEB month_2,CT_MAR month_3,CT_APR month_4,CT_MAY month_5,CT_JUN month_6,CT_JUL month_7, "
            		        + "CT_AUG month_8,CT_SEP month_9,CT_OCT month_10,CT_NOV month_11,CT_DEC month_12,CT_TOTAL total, "
            		        + "t1.CT_APPR_STS apprStatus, TO_CHAR(t1.CT_EFF_FRM_DT, 'DD/MM/YYYY hh:mi AM') effFrmDt FROM T_CRM_TARGETS t1  "
            		        + "JOIN (SELECT MAX(CT_VER_NO) maxRevision,CT_YEAR ,CT_COMP_CODE FROM T_CRM_TARGETS WHERE CT_TRGT_LVL = 'COMPANY' AND CT_TRGT_TYP=(select trgtTyp from params) GROUP BY CT_YEAR, CT_COMP_CODE ) t2 "
            		        + "ON t1.CT_YEAR=t2.CT_YEAR AND t1.CT_COMP_CODE=t2.CT_COMP_CODE AND t1.CT_VER_NO=t2.maxRevision AND t1.CT_TRGT_LVL = \'COMPANY\' "
            		        + "AND t1.CT_TRGT_TYP = (select trgtTyp from params) "
            		        + "ORDER BY t1.CT_YEAR DESC, t1.CT_VER_NO DESC ";

                            LOGGER.info("Query :: {}{}", query,bean.getTargetType());
                            list = executeList(con, query, new Object[]{bean.getTargetType()}, KPITarget.class);

              } else if(StringUtils.equals(TypeConstants.KPITargetLevel.DIVISION.getCode(), bean.getLevel())){

                            LOGGER.info("Query :: {}[{}]", query, params.toArray());
                            query = query + " ORDER BY divName ASC" ;
                            list = executeList(con, query, params.toArray(), KPITarget.class);

              } else if(StringUtils.equals(TypeConstants.KPITargetLevel.DEPARTMENT.getCode(), bean.getLevel())){

            	            query = query +  " AND  NVL(CT_DIVN_CODE,0) = NVL(?,0) ORDER BY deptName ASC ";
                            params.add(bean.getDivCode());
                            LOGGER.info("Query :: {}[{}]", query, params.toArray());
                            list = executeList(con, query, params.toArray(), KPITarget.class);

              } else {

            	            switch(bean.getLevel().toLowerCase()) {
            	    	      case "lob" :
            	    	    	codeName = "lobName";
            	    	        break;
            	    	      case "product":
            	    	    	codeName = "prodName";
            	    	    	break;
            	    	      case "channel" :
            	    	    	codeName = "channelName";
            	    	        break;
            	    	      case "employee" :
            	    	    	codeName = "empName";
            	    	    	break;
            	            }
            	               params = new LinkedList<>();
            	               params.add(bean.getEffFrmDt());
                               params.add(getPlanType(bean.getTrgtLvlNo()));
                               params.add(bean.getTargetType());
                               params.add(bean.getYear());
                               params.add(bean.getCompCode());
                               params.add(bean.getDivCode());
                               params.add(bean.getDeptCode());
           	                   params.add(bean.getLevel().toUpperCase());

            	       if(bean.getTrgtLvlNo() == 4){

            	            query = "WITH params AS(SELECT ? AS effDt FROM DUAL) "
            	                  +" SELECT t1.CT_PLAN_TYP planType, t1.CT_TRGT_TYP targetType, LOWER(CT_TRGT_LVL) AS \"level\" ,CT_TRGT_LVL_NO trgtLvlNo, t1.CT_VER_NO revision,t2.maxRevision maxRevision, t1.CT_YEAR year,t1.CT_COMP_CODE compCode,"
   	            		          +" PKG_REP_UTILITY.FN_GET_COMP_NAME(t1.CT_COMP_CODE) compName, "
   	                		      +" t1.CT_DIVN_CODE divCode , PKG_REP_UTILITY.FN_GET_DIVN_NAME(t1.CT_COMP_CODE,t1.CT_DIVN_CODE) divName ,    "
   	                		      +" t1.CT_DEPT_CODE deptCode , PKG_REP_UTILITY.FN_GET_DEPT_NAME(t1.CT_COMP_CODE,t1.CT_DIVN_CODE,t1.CT_DEPT_CODE) deptName ,  "
   	                		      +" CT_LOB_CODE lobCode , PKG_REP_UTILITY.FN_GET_LOB_NAME(CT_LOB_CODE) lobName ,  "
   	                		      +" CT_PROD_CODE prodCode , PKG_REP_UTILITY.FN_GET_PROD_NAME(CT_PROD_CODE) prodName ,  "
   	                		      +" CT_CHANNEL channel , PKG_REP_UTILITY.FN_GET_AC_NAME('MENU_CODE', CT_CHANNEL) channelName ,  "
   	                		      +" CT_EMP_CODE empCode , PKG_REP_UTILITY.FN_GET_USER_NAME(CT_EMP_CODE) empName ,  "
   	            		          + "CT_JAN month_1,CT_FEB month_2,CT_MAR month_3,CT_APR month_4,CT_MAY month_5,CT_JUN month_6,CT_JUL month_7, "
   	            		          + "CT_AUG month_8,CT_SEP month_9,CT_OCT month_10,CT_NOV month_11,CT_DEC month_12,CT_TOTAL total, "
   	            		          + "t1.CT_APPR_STS apprStatus,TO_CHAR(CT_EFF_FRM_DT, 'DD/MM/YYYY hh:mi AM') effFrmDt FROM T_CRM_TARGETS t1  "
   	            		          +" JOIN (SELECT MAX(CT_VER_NO) maxRevision,CT_PLAN_TYP,CT_TRGT_TYP,CT_YEAR ,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE FROM T_CRM_TARGETS "
   	            		          +" WHERE CT_PLAN_TYP=? AND CT_TRGT_TYP=? AND CT_YEAR =? AND CT_COMP_CODE=? AND CT_DIVN_CODE =? AND CT_DEPT_CODE=? AND CT_EFF_FRM_DT >= TO_DATE((select effDt from params) , 'DD/MM/YYYY hh:mi AM')  "
   	            		          +" GROUP BY CT_PLAN_TYP,CT_TRGT_TYP,CT_YEAR, CT_COMP_CODE ,CT_DIVN_CODE,CT_DEPT_CODE) t2 "
   	            		          +" ON  t1.CT_PLAN_TYP = t2.CT_PLAN_TYP  AND t1.CT_TRGT_TYP = t2.CT_TRGT_TYP AND t1.CT_YEAR=t2.CT_YEAR "
   	            		          +" AND t1.CT_COMP_CODE=t2.CT_COMP_CODE AND t1.CT_DIVN_CODE=t2.CT_DIVN_CODE AND t1.CT_DEPT_CODE=t2.CT_DEPT_CODE AND t1.CT_EFF_FRM_DT >= TO_DATE((select effDt from params) , 'DD/MM/YYYY hh:mi AM')  "
   	            		          +" AND t1.CT_VER_NO=t2.maxRevision AND t1.CT_TRGT_LVL=?  " ;


            	       }else{

            	    	      query = "WITH params AS(SELECT ? AS effDt FROM DUAL) "
           	                      + "SELECT t1.CT_PLAN_TYP planType, t1.CT_TRGT_TYP targetType, LOWER(CT_TRGT_LVL) AS \"level\" ,CT_TRGT_LVL_NO trgtLvlNo, t1.CT_VER_NO revision, t1.CT_YEAR year,t1.CT_COMP_CODE compCode,"
 	            		          +" PKG_REP_UTILITY.FN_GET_COMP_NAME(t1.CT_COMP_CODE) compName, "
 	                		      +" t1.CT_DIVN_CODE divCode , PKG_REP_UTILITY.FN_GET_DIVN_NAME(t1.CT_COMP_CODE,t1.CT_DIVN_CODE) divName ,    "
 	                		      +" t1.CT_DEPT_CODE deptCode , PKG_REP_UTILITY.FN_GET_DEPT_NAME(t1.CT_COMP_CODE,t1.CT_DIVN_CODE,t1.CT_DEPT_CODE) deptName ,  "
 	                		      +" CT_LOB_CODE lobCode , PKG_REP_UTILITY.FN_GET_LOB_NAME(CT_LOB_CODE) lobName ,  "
 	                		      +" CT_PROD_CODE prodCode , PKG_REP_UTILITY.FN_GET_PROD_NAME(CT_PROD_CODE) prodName ,  "
 	                		      +" CT_CHANNEL channel , PKG_REP_UTILITY.FN_GET_AC_NAME('MENU_CODE', CT_CHANNEL) channelName ,  "
 	                		      +" CT_EMP_CODE empCode , PKG_REP_UTILITY.FN_GET_USER_NAME(CT_EMP_CODE) empName ,  "
 	            		          + "CT_JAN month_1,CT_FEB month_2,CT_MAR month_3,CT_APR month_4,CT_MAY month_5,CT_JUN month_6,CT_JUL month_7, "
 	            		          + "CT_AUG month_8,CT_SEP month_9,CT_OCT month_10,CT_NOV month_11,CT_DEC month_12,CT_TOTAL total, "
 	            		          + "t1.CT_APPR_STS apprStatus,TO_CHAR(CT_EFF_FRM_DT, 'DD/MM/YYYY hh:mi AM') effFrmDt FROM T_CRM_TARGETS t1  "
 	            		          + "WHERE t1.CT_PLAN_TYP= ? AND t1.CT_TRGT_TYP= ?  AND t1.CT_YEAR = ?  "
 	            		          +" AND t1.CT_COMP_CODE=? AND t1.CT_DIVN_CODE=? AND t1.CT_DEPT_CODE=? AND CT_EFF_FRM_DT = TO_DATE((select effDt from params) , 'DD/MM/YYYY hh:mi AM') AND t1.CT_TRGT_LVL=? AND CT_VER_NO = ?" ;

                                 params.add(bean.getRevision());


            	       }


                             if(StringUtils.isNotEmpty(bean.getLobCode())){
            	                	addCondition = addCondition +  " AND  NVL(CT_LOB_CODE,0) = NVL(?,0)  ";
                                    params.add(bean.getLobCode());
                             }
                             if(StringUtils.isNotEmpty(bean.getProdCode())){
                	                addCondition = addCondition +  " AND  NVL(CT_PROD_CODE,0) = NVL(?,0) ";
                                    params.add(bean.getProdCode());
                             }
                	         if(StringUtils.isNotEmpty(bean.getChannel())){
                                    addCondition = addCondition +  " AND  NVL(CT_CHANNEL,0) = NVL(?,0)  ";
                                    params.add(bean.getChannel());
                	         }
                	         if(StringUtils.isNotEmpty(bean.getEmpCode())){
                          	         addCondition = addCondition +  " AND  NVL(CT_EMP_CODE,0) = NVL(?,0)";
                                     params.add(bean.getEmpCode());
                	         }

            	             orderBy  = 	 " ORDER BY " + codeName + " ASC ";
                  	         query = query + addCondition + orderBy;

                            LOGGER.info("Query :: {}[{}]", query, params.toArray());
                            list = executeList(con, query, params.toArray(), KPITarget.class);

             }





        } catch (Exception e) {
            LOGGER.error("Error while retreiving  targets", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

 public List<KeyValue> loadTargetLvlsRemainingForSalesPlan(KPITarget bean) {

        Connection con = null;
        String query = null;
        List<Object> params = new LinkedList<>();
        List<KeyValue> allLvls = null;
        List<String> currLvls = null;
        List<KeyValue> remLvls = null;

        try {
          con = getDBConnection(getDataSource());

            allLvls =  getAllTargetLevels();
            currLvls = loadCurrentTargetLvlsForSalesPlan(bean).stream().map(KeyValue::getValue).collect(Collectors.toList());

            for(String currLvlVal:currLvls){
              for(int i=0;i< allLvls.size(); i++)    {
        	    if(StringUtils.equalsIgnoreCase(allLvls.get(i).getValue(),currLvlVal))
        	    	allLvls.remove(i);
             }

           }

        remLvls = allLvls;

        } catch (Exception e) {
            LOGGER.error("Error while retreiving target level for sales planning", e);
        } finally {
            query = null;
            closeDbConn(con);
        }

    	return remLvls;
    }

    public List<KeyValue> loadCurrentTargetLvlsForSalesPlan(KPITarget bean) {

        Connection con = null;
        String query = null;
        String apprStatus = null;
        List<Object> params = new LinkedList<>();
        List<KeyValue> list = new LinkedList<>();

        try {

          con = getDBConnection(getDataSource());

          if(bean.getTrgtLvlNo() > 3){
        	  apprStatus  = " AND CT_APPR_STS = 'APPROVED' ";
          }else {
        	  apprStatus = "";
          }

    	  query =   "WITH params AS(  " +
    		             "SELECT "+
    		                  "? AS targetType, " +
    		                  "? AS compCode, "+
    		                  "? AS yr " +
    		             "FROM DUAL "+
    		      " ) " +
    			  "(SELECT CT_TRGT_LVL_NO key,LOWER(CT_TRGT_LVL) value,CT_VER_NO,x1.CT_PLAN_TYP,x1.CT_TRGT_TYP,x1.CT_YEAR ,x1.CT_COMP_CODE FROM T_CRM_TARGETS x1 "+
    			   " JOIN "+
    			   " (SELECT max(CT_VER_NO) maxRev,x2.CT_PLAN_TYP,x2.CT_TRGT_TYP,x2.CT_YEAR ,x2.CT_COMP_CODE FROM T_CRM_TARGETS x2 "+
    			    	           " WHERE   "+
    			    	            "x2.CT_TRGT_TYP=(select targetType from params) "+
    			    	            "AND x2.CT_PLAN_TYP=001 " +
    			    	            "AND x2.CT_COMP_CODE=(select compCode from params)  "+
    			    	            "AND x2.CT_YEAR=(select yr from params) "
    			    	            + apprStatus +
    			    	            "GROUP BY x2.CT_PLAN_TYP,x2.CT_TRGT_TYP,x2.CT_YEAR ,x2.CT_COMP_CODE  ) x3 "+
    			    	            "ON   x1.CT_TRGT_TYP=x3.CT_TRGT_TYP AND " +
    			    	            "x1.CT_YEAR=x3.CT_YEAR AND "+
    			    	            "x1.CT_COMP_CODE=x3.CT_COMP_CODE AND " +
    			    	            "CT_VER_NO=x3.maxRev " +
    			    	            "WHERE x1.CT_PLAN_TYP=001 " +

    			    "UNION "+
    			   " SELECT CT_TRGT_LVL_NO key,LOWER(CT_TRGT_LVL) value,CT_VER_NO,t1.CT_PLAN_TYP,t1.CT_TRGT_TYP,t1.CT_YEAR ,t1.CT_COMP_CODE FROM T_CRM_TARGETS t1 "+
                                   "WHERE CT_TRGT_TYP=(select targetType from params) AND " +
    			    	            "CT_YEAR=(select yr from params) AND " +
    			    	            "CT_COMP_CODE=(select compCode from params) AND " +
    			    	            "CT_VER_NO=0 AND CT_PLAN_TYP=002  )  " +
    			    	            "ORDER BY key ASC ";


    	  params.add(bean.getTargetType());
    	  params.add(bean.getCompCode());
    	  params.add(bean.getYear());

          LOGGER.info("Query :: {}[{}]", query, params.toArray());
          list = executeList(con, query, params.toArray(), KeyValue.class);



        } catch (Exception e) {
            LOGGER.error("Error while retreiving target level for sales planning", e);
        } finally {
            query = null;
            closeDbConn(con);
        }

    	return list;
    }

    public String loadParentLevelForSalesPlan(KPITarget bean) {

        Connection con = null;
        String query = null;
    	String level = null;
        List<Object> params = new LinkedList<>();
        List<KeyValue> list = null;

        try {

          con = getDBConnection(getDataSource());

    	  query = "SELECT     CT_TRGT_LVL_NO key,CT_TRGT_LVL value  FROM T_CRM_TARGETS JOIN (" +
                  " SELECT     (MAX(LPAD(CT_TRGT_LVL_NO,3,'0') - 1)) parentLvl   FROM T_CRM_TARGETS  t1" +
     	          " JOIN    " +
    	          " (SELECT MAX(CT_VER_NO) maxRevision, CT_TRGT_TYP, CT_YEAR ,CT_COMP_CODE FROM T_CRM_TARGETS WHERE CT_APPR_STS = 'APPROVED'  " +
    	          " AND CT_TRGT_TYP=? " +
    	          " AND CT_COMP_CODE=? " +
    	          " AND CT_EFF_FRM_DT <= CURRENT_DATE " +
    	          " GROUP BY CT_YEAR, CT_COMP_CODE,CT_TRGT_TYP) t2" +
    	          " ON t1.CT_TRGT_TYP=t2.CT_TRGT_TYP " +
    	          " AND t1.CT_YEAR=t2.CT_YEAR " +
    	          " AND t1.CT_COMP_CODE=t2.CT_COMP_CODE " +
    	          " AND t1.CT_VER_NO = t2.maxRevision  " +
    	          " AND t1.CT_YEAR = (select to_char(sysdate, 'YYYY') from dual) " +
    	          " )  ON  CT_TRGT_LVL_NO=parentLvl   ";

    	  params.add(bean.getTargetType());
    	  params.add(bean.getCompCode());

          LOGGER.info("Query :: {}[{}]", query, params.toArray());
          list = executeList(con, query, params.toArray(), KeyValue.class);
          level = list.get(0).getValue();




        } catch (Exception e) {
            LOGGER.error("Error while retreiving target level for sales planning", e);
        } finally {
            query = null;
            closeDbConn(con);
        }

    	return level;
    }

    public boolean loadIsApproveDisabled(KPITarget bean) {

        boolean isApproveDisabled = true;
        bean.setPlanType(getPlanType(bean.getTrgtLvlNo()));
        if(bean.getTrgtLvlNo() < 3){
        	isApproveDisabled = checkTargetsMonthlySumValues(bean);
        }else{
        	isApproveDisabled = checkSalesPlanMonthlySumValues(bean);
        }
        return isApproveDisabled;
    }


    private boolean checkTargetsMonthlySumValues(KPITarget bean) {

        Connection con = null;
        StringBuilder query = null;
        StringBuilder qryApprStatus = null;
        Object[] param;
        List<Object> params = new LinkedList<>();
        boolean approveDisabled = true;
        String sumComp =null;
        String sumDiv= null;
        String sumDept = null;
        List<String> monthList = new ArrayList<>(Arrays.asList("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"));

        try {

               con = getDBConnection(getDataSource());
               params.add(bean.getTargetType());
               params.add(bean.getYear());
               params.add(bean.getCompCode());
               params.add(bean.getRevision());
               params.add(bean.getEffFrmDt());
               params.add(bean.getPlanType());
               param = params.toArray();

              if(bean.getTrgtLvlNo() < 3){


               qryApprStatus = new StringBuilder("SELECT t1.CT_YEAR key, t1.CT_COMP_CODE value ,t1.CT_VER_NO info , t1.CT_APPR_STS info1 FROM T_CRM_TARGETS t1 "
                             + " WHERE t1.CT_TRGT_TYP=? AND t1.CT_YEAR = ? AND  t1.CT_COMP_CODE = ?  AND t1.CT_VER_NO = ? AND CT_EFF_FRM_DT = TO_DATE(? , 'DD/MM/YYYY hh:mi AM')"
                             + " AND  CT_TRGT_LVL=\'COMPANY\' AND t1.CT_PLAN_TYP=?") ;

               LOGGER.info("Query************** :: {}{}", qryApprStatus.toString(), params.toArray());
               List<KeyValue> list1 = executeList(con, qryApprStatus.toString(), param, KeyValue.class);


              if(StringUtils.equals(list1.get(0).getInfo1() , TypeConstants.KPITargetApprovalStatus.OPEN.getCode())){

              for(String month: monthList){

           	   query = new StringBuilder("SELECT  t1.CT_TRGT_TYP key, t1.CT_YEAR value, t1.CT_COMP_CODE info ,t1.CT_VER_NO info1 , (SELECT TO_CHAR(t1.CT_EFF_FRM_DT, 'DD/MM/YYYY hh:mi AM') FROM dual) info2, t1.CT_PLAN_TYP info3 , SUM(t1.CT_"+month+")  info4 ,  "
            	  +" (SELECT SUM(t2.CT_"+month+")  FROM T_CRM_TARGETS t2 WHERE t1.CT_TRGT_TYP=t2.CT_TRGT_TYP  AND t2.CT_TRGT_LVL=\'DIVISION\' AND t1.CT_YEAR=t2.CT_YEAR AND t1.CT_COMP_CODE=t2.CT_COMP_CODE AND t1.CT_VER_NO=t2.CT_VER_NO AND t1.CT_EFF_FRM_DT=t2.CT_EFF_FRM_DT) info5 , "
            	  +" (SELECT SUM(t3.CT_"+month+")  FROM T_CRM_TARGETS t3 WHERE t1.CT_TRGT_TYP=t3.CT_TRGT_TYP AND t3.CT_TRGT_LVL=\'DEPARTMENT\' AND t1.CT_YEAR=t3.CT_YEAR AND t1.CT_COMP_CODE=t3.CT_COMP_CODE AND t1.CT_VER_NO=t3.CT_VER_NO AND t1.CT_EFF_FRM_DT=t3.CT_EFF_FRM_DT) info6 "
            	  +" FROM T_CRM_TARGETS t1 WHERE  t1.CT_TRGT_TYP=? AND t1.CT_TRGT_LVL=\'COMPANY\' AND t1.CT_YEAR = ? AND  t1.CT_COMP_CODE = ?  "
            	  + " AND t1.CT_VER_NO = ? AND CT_EFF_FRM_DT = TO_DATE(?, 'DD/MM/YYYY hh:mi AM') "
            	  + " AND t1.CT_PLAN_TYP = ? "
            	  +"  GROUP BY t1.CT_TRGT_TYP, t1.CT_YEAR, t1.CT_COMP_CODE,t1.CT_VER_NO, t1.CT_EFF_FRM_DT , t1.CT_PLAN_TYP");

                   LOGGER.info("Query :: {}{}", query, params.toArray());
                   List<KeyValue> list = executeList(con, query.toString(), param, KeyValue.class);
                   if(list.size() > 0){
                     sumComp = list.get(0).getInfo4();
                     sumDiv = list.get(0).getInfo5();
                     sumDept = list.get(0).getInfo6();
                   }

                if((StringUtils.isNotEmpty(sumComp)) && (StringUtils.isNotEmpty(sumDiv)) && (StringUtils.isNotEmpty(sumDept))){
                	if((Long.parseLong(sumComp) == Long.parseLong(sumDiv)) && (Long.parseLong(sumDiv) == Long.parseLong(sumDept))){
                   	     approveDisabled = false;
                   } else{
                   	     approveDisabled = true;
                   	     break;
                   }
                 } else {
              	        approveDisabled = true;
              	        break;
                 }

              }

              if(BooleanUtils.isNotTrue(approveDisabled)){
            	  updateTargetApprStatus(bean, TypeConstants.KPITargetApprovalStatus.MATCHED.getCode());
              } else {
            	  updateTargetApprStatus(bean, TypeConstants.KPITargetApprovalStatus.OPEN.getCode());
              }

            } else if (StringUtils.equals(list1.get(0).getInfo1() , TypeConstants.KPITargetApprovalStatus.MATCHED.getCode())){
            	 approveDisabled = false;

            }
         }

        } catch (Exception e) {
            LOGGER.error("Error while retreiving approve status", e);
        } finally {
        	query = null;
        	qryApprStatus = null;
            closeDbConn(con);
        }

        return approveDisabled;
    }

    private boolean checkSalesPlanMonthlySumValues(KPITarget bean) {

        Connection con = null;
        StringBuilder query = null;
        StringBuilder qryApprStatus = null;
        Object[] param;
        List<Object> params = new LinkedList<>();
        boolean approveDisabled = true;
        Long deptSum = null;
        String sumLob =null;
        String sumProd= null;
        String sumChn = null;
        String sumEmp = null;
        List<String> monthList = new LinkedList<>(Arrays.asList("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"));
        List<Long> deptValues = new LinkedList<>(Arrays.asList(bean.getMonth_1(),bean.getMonth_2(),bean.getMonth_3(),bean.getMonth_4(),bean.getMonth_5(),bean.getMonth_6(),
        bean.getMonth_7(),bean.getMonth_8(),bean.getMonth_9(),bean.getMonth_10(),bean.getMonth_11(),bean.getMonth_12()));

        try {

              con = getDBConnection(getDataSource());
              params.add(bean.getTargetType());
              params.add(bean.getYear());
              params.add(bean.getCompCode());
              params.add(bean.getDivCode());
              params.add(bean.getDeptCode());
              if(bean.getTrgtLvlNo() > 3){
                  Long maxRev = getMaxRevNumForSalesPlan(bean);
            	  params.add(maxRev);
            	  bean.setRevision(maxRev);
              }
              else{
                  params.add(bean.getRevision());
              }
              params.add(bean.getEffFrmDt());
              params.add(bean.getPlanType());
              param = params.toArray();

              qryApprStatus = new StringBuilder("SELECT t1.CT_YEAR key, t1.CT_COMP_CODE value ,t1.CT_VER_NO info , t1.CT_APPR_STS info1 FROM T_CRM_TARGETS t1 "
                             + " WHERE t1.CT_TRGT_TYP=? AND t1.CT_YEAR = ? AND  t1.CT_COMP_CODE = ? AND t1.CT_DIVN_CODE=? AND t1.CT_DEPT_CODE=? "
                             + " AND t1.CT_VER_NO = ? AND CT_EFF_FRM_DT = TO_DATE(? , 'DD/MM/YYYY hh:mi AM') "
                             + " AND t1.CT_PLAN_TYP=? AND CT_TRGT_LVL_NO=4") ;

             LOGGER.info("Query************** :: {}{}", qryApprStatus.toString(), params.toArray());
             List<KeyValue> list1 = executeList(con, qryApprStatus.toString(), param, KeyValue.class);


             if(list1.size() > 0){
                if(StringUtils.equals(list1.get(0).getInfo1() , TypeConstants.KPITargetApprovalStatus.OPEN.getCode())){



                for(String month: monthList){


           	      query = new StringBuilder("SELECT  t1.CT_TRGT_TYP key, t1.CT_YEAR value, t1.CT_COMP_CODE info , t1.CT_DIVN_CODE,t1.CT_DEPT_CODE,t1.CT_VER_NO info1, t1.CT_EFF_FRM_DT info2,t1.CT_PLAN_TYP info3 ,   "
            	  +" (SELECT SUM(t2.CT_"+month+")  FROM T_CRM_TARGETS t2 WHERE t1.CT_TRGT_TYP=t2.CT_TRGT_TYP  AND t2.CT_TRGT_LVL=\'LOB\' AND t1.CT_YEAR=t2.CT_YEAR AND t1.CT_COMP_CODE=t2.CT_COMP_CODE AND  t1.CT_DIVN_CODE=t2.CT_DIVN_CODE AND t1.CT_DEPT_CODE=t2.CT_DEPT_CODE AND t1.CT_VER_NO=t2.CT_VER_NO AND t1.CT_EFF_FRM_DT=t2.CT_EFF_FRM_DT) info4 , "
            	  +" (SELECT SUM(t3.CT_"+month+")  FROM T_CRM_TARGETS t3 WHERE t1.CT_TRGT_TYP=t3.CT_TRGT_TYP AND t3.CT_TRGT_LVL=\'PRODUCT\' AND t1.CT_YEAR=t3.CT_YEAR AND t1.CT_COMP_CODE=t3.CT_COMP_CODE AND t1.CT_DIVN_CODE=t3.CT_DIVN_CODE AND t1.CT_DEPT_CODE=t3.CT_DEPT_CODE AND t1.CT_VER_NO=t3.CT_VER_NO AND t1.CT_EFF_FRM_DT=t3.CT_EFF_FRM_DT) info5 , "
            	  +" (SELECT SUM(t4.CT_"+month+")  FROM T_CRM_TARGETS t4 WHERE t1.CT_TRGT_TYP=t4.CT_TRGT_TYP AND t4.CT_TRGT_LVL=\'CHANNEL\' AND t1.CT_YEAR=t4.CT_YEAR AND t1.CT_COMP_CODE=t4.CT_COMP_CODE AND t1.CT_DIVN_CODE=t4.CT_DIVN_CODE AND t1.CT_DEPT_CODE=t4.CT_DEPT_CODE AND t1.CT_VER_NO=t4.CT_VER_NO AND t1.CT_EFF_FRM_DT=t4.CT_EFF_FRM_DT) info6 , "
                  +" (SELECT SUM(t5.CT_"+month+")  FROM T_CRM_TARGETS t5 WHERE t1.CT_TRGT_TYP=t5.CT_TRGT_TYP AND t5.CT_TRGT_LVL=\'EMPLOYEE\' AND t1.CT_YEAR=t5.CT_YEAR AND t1.CT_COMP_CODE=t5.CT_COMP_CODE AND t1.CT_DIVN_CODE=t5.CT_DIVN_CODE AND t1.CT_DEPT_CODE=t5.CT_DEPT_CODE AND t1.CT_VER_NO=t5.CT_VER_NO AND t1.CT_EFF_FRM_DT=t5.CT_EFF_FRM_DT) info7  "
            	  +" FROM T_CRM_TARGETS t1 WHERE  t1.CT_TRGT_TYP=? AND t1.CT_YEAR = ? AND  t1.CT_COMP_CODE = ? "
            	  +" AND t1.CT_DIVN_CODE=? AND t1.CT_DEPT_CODE=? "
            	  +" AND t1.CT_VER_NO = ? AND CT_EFF_FRM_DT = TO_DATE(? , 'DD/MM/YYYY hh:mi AM') "
            	  +" AND t1.CT_PLAN_TYP = ? "
            	  +" GROUP BY t1.CT_TRGT_TYP, t1.CT_YEAR, t1.CT_COMP_CODE,t1.CT_DIVN_CODE,t1.CT_DEPT_CODE,t1.CT_VER_NO,t1.CT_EFF_FRM_DT, t1.CT_PLAN_TYP");
                   LOGGER.info("Query :: {}{}", query, params.toArray());
                   List<KeyValue> list = executeList(con, query.toString(), param, KeyValue.class);

                   if(list.size() > 0){
                	 deptSum = deptValues.get(monthList.indexOf(month));
                     sumLob = list.get(0).getInfo4();
                     sumProd = list.get(0).getInfo5();
                     sumChn = list.get(0).getInfo6();
                     sumEmp = list.get(0).getInfo7();
                   }

                if((StringUtils.isNotEmpty(sumLob)) && (StringUtils.isNotEmpty(sumProd)) && (StringUtils.isNotEmpty(sumChn) && (StringUtils.isNotEmpty(sumEmp)))){

               	if((deptSum == Long.parseLong(sumLob)) && (Long.parseLong(sumLob) == Long.parseLong(sumProd)) && (Long.parseLong(sumProd) == Long.parseLong(sumChn) && (Long.parseLong(sumChn) == Long.parseLong(sumEmp)))){
                   	     approveDisabled = false;
                   } else{
                   	     approveDisabled = true;
                   	     break;
                   }
                 } else {
              	        approveDisabled = true;
              	        break;
                 }

              }

              if(BooleanUtils.isNotTrue(approveDisabled)){
            	  updateTargetApprStatus(bean, TypeConstants.KPITargetApprovalStatus.MATCHED.getCode());
              } else {
            	  updateTargetApprStatus(bean, TypeConstants.KPITargetApprovalStatus.OPEN.getCode());
              }

            } else if (StringUtils.equals(list1.get(0).getInfo1() , TypeConstants.KPITargetApprovalStatus.MATCHED.getCode())){

            	approveDisabled = false;

            }

         }

        } catch (Exception e) {
            LOGGER.error("Error while retreiving approved status", e);
        } finally {
        	query = null;
        	qryApprStatus = null;
            closeDbConn(con);
        }

        return approveDisabled;
    }

 public List<KPITarget> loadApprovedTargets(String trgtTyp, List<KeyValue> userDivnDept) {


    	String compCode = null;
    	String divCode = null;
    	List<String> deptList = new ArrayList<>();
    	String deptsCommaSeparated = null;
        Connection con = null;
        String query = null;
        Object[] param;
        List<Object> params = new LinkedList<>();
        List<KPITarget> list = new ArrayList<>();

        try {

        	if(userDivnDept.size() > 0){

        	  compCode = userDivnDept.get(0).getValue();
        	  divCode = userDivnDept.get(0).getInfo();

        	  for(KeyValue userInfo:userDivnDept){
        		     deptList.add(userInfo.getInfo1());
        	  }

        	   deptsCommaSeparated = String.join(",", deptList);

               con = getDBConnection(getDataSource());

               params.add(trgtTyp);
               params.add(compCode);
               params.add(divCode);
               param = params.toArray();

               query = "SELECT t1.CT_TRGT_TYP targetType, t1.CT_VER_NO revision,t1.CT_YEAR year, LOWER(CT_TRGT_LVL) AS \"level\" , CT_TRGT_LVL_NO trgtLvlNo, "
                      + " t1.CT_COMP_CODE compCode , PKG_REP_UTILITY.FN_GET_COMP_NAME(t1.CT_COMP_CODE) compName , "
                      + " t1.CT_DIVN_CODE divCode , PKG_REP_UTILITY.FN_GET_DIVN_NAME(t1.CT_COMP_CODE,t1.CT_DIVN_CODE) divName ,  "
                      + " t1.CT_DEPT_CODE deptCode , PKG_REP_UTILITY.FN_GET_DEPT_NAME(t1.CT_COMP_CODE,t1.CT_DIVN_CODE,t1.CT_DEPT_CODE) deptName , "
                      + " CT_LOB_CODE lobCode , PKG_REP_UTILITY.FN_GET_LOB_NAME(CT_LOB_CODE) lobName , "
                      + " CT_PROD_CODE prodCode , PKG_REP_UTILITY.FN_GET_PROD_NAME(CT_PROD_CODE) prodName , "
                      + " CT_CHANNEL channel , "
                      + " CT_EMP_CODE empCode , CT_EMP_CODE empName , "
                      + " CT_JAN month_1,CT_FEB month_2,CT_MAR month_3,CT_APR month_4,CT_MAY month_5,CT_JUN month_6,CT_JUL month_7,"
                      + " CT_AUG month_8,CT_SEP month_9,CT_OCT month_10,CT_NOV month_11,CT_DEC month_12,CT_TOTAL total, CT_APPR_STS apprStatus,"
                      + " CT_CR_UID crUid,CT_CR_DT crDt, CT_UPD_UID upUid, CT_UPD_DT upDt,CT_APPR_DT apprDt, CT_APPR_UID apprUid , (SELECT TO_CHAR(CT_EFF_FRM_DT, 'DD/MM/YYYY hh:mi AM') FROM dual) effFrmDt "
                      + " FROM T_CRM_TARGETS t1"
                      + " JOIN (SELECT MAX(CT_VER_NO) maxRevision,CT_TRGT_TYP,CT_YEAR ,CT_COMP_CODE FROM T_CRM_TARGETS WHERE CT_APPR_STS = \'APPROVED\' "
                      + " AND CT_TRGT_TYP=? "
                      + " AND CT_PLAN_TYP=001 "
                      + " AND CT_EFF_FRM_DT <= SYSDATE "
                      + " GROUP BY CT_TRGT_TYP,CT_YEAR, CT_COMP_CODE  ) t2"
                      + " ON t1.CT_YEAR=t2.CT_YEAR "
                      + " AND t1.CT_COMP_CODE=t2.CT_COMP_CODE "
                      + " AND t1.CT_VER_NO = t2.maxRevision"
                      + " AND t1.CT_TRGT_TYP = t2.CT_TRGT_TYP"
                      + " AND t1.CT_APPR_STS = \'APPROVED\'"
                      + " AND t1.CT_TRGT_LVL=\'DEPARTMENT\' "
                      + " AND t1.CT_COMP_CODE = ? "
                      + " AND NVL(t1.CT_DIVN_CODE,0) = NVL(? ,0)   "
                      + " AND NVL(t1.CT_DEPT_CODE,0) IN (" + deptsCommaSeparated + ") "
                      + " AND t1.CT_YEAR = (select to_char(sysdate, 'YYYY') from dual) "
                      + " ORDER BY deptName ASC";



                LOGGER.info("Query :: {}{}", query, params.toArray());
                list = executeList(con, query, param, KPITarget.class);


        	}



        } catch (Exception e) {
            LOGGER.error("Error while retreiving approved targets", e);
        } finally {
        	query = null;
            closeDbConn(con);
        }

        return list;
    }

    public List<KPITarget> loadAllTargetLvlsForYrCompRev(KPITarget bean){


        List<KPITarget> list = new LinkedList<>();
        Connection con = null;
        StringBuilder query = null;
        List<Object> params = new LinkedList<>();


        try {
               con = getDBConnection(getDataSource());
               query = new StringBuilder("SELECT CT_TRGT_TYP targetType, LOWER(CT_TRGT_LVL) AS \"level\" ,CT_TRGT_LVL_NO trgtLvlNo, CT_VER_NO revision,CT_YEAR year,"
               		+ " CT_COMP_CODE compCode , PKG_REP_UTILITY.FN_GET_COMP_NAME(CT_COMP_CODE) compName , "
            		+ " CT_DIVN_CODE divCode , PKG_REP_UTILITY.FN_GET_DIVN_NAME(CT_COMP_CODE,CT_DIVN_CODE) divName , "
            		+ " CT_DEPT_CODE deptCode , PKG_REP_UTILITY.FN_GET_DEPT_NAME(CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE) deptName , "
            		+ " CT_LOB_CODE lobCode , PKG_REP_UTILITY.FN_GET_LOB_NAME(CT_LOB_CODE) lobName , "
            		+ " CT_PROD_CODE prodCode , PKG_REP_UTILITY.FN_GET_PROD_NAME(CT_PROD_CODE) prodName , "
            		+ " CT_CHANNEL channel ,  PKG_REP_UTILITY.FN_GET_AC_NAME('MENU_CODE', CT_CHANNEL) channelName , "
            		+ " CT_EMP_CODE empCode , PKG_REP_UTILITY.FN_GET_USER_NAME(CT_EMP_CODE) empName , "
               		+ " CT_JAN month_1,CT_FEB month_2,CT_MAR month_3,CT_APR month_4,CT_MAY month_5,CT_JUN month_6,CT_JUL month_7,"
            		+ " CT_AUG month_8,CT_SEP month_9,CT_OCT month_10,CT_NOV month_11,CT_DEC month_12,CT_TOTAL total,(SELECT TO_CHAR(CT_EFF_FRM_DT, 'DD/MM/YYYY hh:mi AM') FROM dual) effFrmDt"
               		+ " FROM T_CRM_TARGETS "
               		+ " WHERE CT_TRGT_TYP=? AND CT_COMP_CODE =? AND  CT_YEAR = ? AND CT_VER_NO = ? ORDER BY CT_TRGT_LVL_NO ASC");


               params.add(bean.getTargetType());
               params.add(bean.getCompCode());
               params.add(bean.getYear());
               params.add(bean.getRevision());

               LOGGER.info("Query :: {}[{}]", query, params.toArray());
			   list = executeList(con, query.toString(), params.toArray(), KPITarget.class);


          } catch (Exception e) {
                LOGGER.error("Error while retreiving  targets by company, year, revision", e);
          } finally {
            query = null;
            closeDbConn(con);
         }

        return list;

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


    public List<KeyValue> getCompanyList() {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT COMP_CODE key, COMP_NAME value ");
            query.append("FROM M_COMPANY ");

            LOGGER.info("Query :: {} ", query);
            list = executeList(con, query.toString(),null, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving companies list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }



    public List<KeyValue> getDivisionList(String compCode) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        List<Object> params = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT DIVN_CODE key, DIVN_NAME value ");
            query.append("FROM M_DIVISION WHERE DIVN_COMP_CODE = ? ");
            params.add(compCode);

            LOGGER.info("Query :: {}{} ", query, params.toArray());
            list = executeList(con, query.toString(),params.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving divisions list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> getDepartmentList(String compCode,String divCode) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        List<Object> params = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT DEPT_CODE key, DEPT_NAME value ");
            query.append("FROM M_DEPARTMENT WHERE DEPT_COMP_CODE = ? AND DEPT_DIVN_CODE = ?");
            params.add(compCode);
            params.add(divCode);

            LOGGER.info("Query :: {}{} ", query, params.toArray());
            list = executeList(con, query.toString(),params.toArray(), KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving departments list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> getLobList(String compCode,String divCode, String deptCode) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        List<Object> params = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT t1.LACD_LOB_CODE key,t2.LOB_DESC value FROM M_LOB_APPL_DEPT t1"
                    + " JOIN M_LOB t2 ON t1.LACD_LOB_CODE=t2.LOB_CODE"
                    + " AND t1.LACD_COMP_CODE = ? AND t1.LACD_DIVN_CODE = ? AND t1.LACD_DEPT_CODE = ?  ");
            params.add(compCode);
            params.add(divCode);
            params.add(deptCode);

            LOGGER.info("Query :: {}{} ", query, params.toArray());
            list = executeList(con, query.toString(),params.toArray(), KeyValue.class);



        } catch (Exception e) {
            LOGGER.error("Error while retreiving lob list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> getProductList(String lobCode) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        List<Object> params = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PROD_CODE key, PROD_DESC value FROM M_PRODUCT WHERE PROD_LOB_CODE = NVL(? ,PROD_LOB_CODE)   ");
            params.add(lobCode);

            LOGGER.info("Query :: {}{} ", query, params.toArray());
            list = executeList(con, query.toString(),params.toArray(), KeyValue.class);



        } catch (Exception e) {
            LOGGER.error("Error while retreiving product list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> getChannels(KPITarget bean) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        LinkedList<Object> params = new LinkedList<>();
        try {
            con = getDBConnection(getDataSource());
            if(StringUtils.isBlank(bean.getLobCode())){
                List<KeyValue> applLobs = getLobList(bean.getCompCode(),bean.getDivCode(),bean.getDeptCode());
                List<String> lobs = applLobs.stream().map(KeyValue::getKey).collect(Collectors.toList());
                String lobsCommaSeparated = String.join(",", lobs);
                query = new StringBuilder("SELECT DISTINCT AC_CODE key,AC_DESC value FROM M_APP_CODES WHERE AC_TYPE = 'MENU_CODE' AND AC_LOB_CODE IN "
                		                 + " ( " + lobsCommaSeparated + " ) ") ;
            }else{
                query = new StringBuilder("SELECT DISTINCT AC_CODE key,AC_DESC value FROM M_APP_CODES WHERE AC_TYPE = 'MENU_CODE' AND AC_LOB_CODE =? ") ;
                params.add(bean.getLobCode());
            }

            LOGGER.info("Query :: {} [{}]", query , params.toArray());
            list = executeList(con, query.toString(),params.toArray(), KeyValue.class);
            list = list.stream().filter(distinctByKey(kv -> kv.getValue() )).collect( Collectors.toList() );


        } catch (Exception e) {
            LOGGER.error("Error while retreiving channels => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

	public List<KeyValue> getEmployeeListForDept(String comp, String divn, String dept) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        List<Object> params = new LinkedList<>();

        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT USER_ID key, USER_NAME value FROM M_USER, M_USER_COMP_DIVN  WHERE USER_ID=MUCD_USER_ID"+
                     " AND MUCD_COMP_CODE= ? AND MUCD_DIVN_CODE= ? AND MUCD_DEPT_CODE= ? AND USER_CRM_AGENT_YN = 1");
            params.add(comp);
            params.add(divn);
            params.add(dept);
            LOGGER.info("Query :: {}{} ", query, params.toArray() );
            list = executeList(con, query.toString(),params.toArray(), KeyValue.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving employee list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
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





    public Long getLatestRevisionId(Long year,String compCode){
        Connection con = null;
        String query = null;
        int i = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Long id = null;
        List<Object> params = new LinkedList<>();

        try {

            params.add(year);
            params.add(compCode);
            con = getDBConnection(getDataSource());
        	query = "SELECT MAX(CCT_VER_NO) FROM T_CRM_COMP_TARGETS WHERE CCT_YEAR = ? AND CCT_COMP_CODE = ?";
            ps = con.prepareStatement(query);
            ps.setLong(++i, year);
            ps.setString(++i, compCode);

            LOGGER.info("Query :: {}{} ", query, params.toArray());

            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (Exception e) {
            LOGGER.error("Error while getting latest revision number => {}", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
        }
        return id;
    }

    public int updateTargetApprStatus(KPITarget bean, String status){

        Connection con = null;
    	PreparedStatement ps = null;
    	int recCnt = 0;
  	    int t = 0;

  	    try{



           con = getDBConnection(getDataSource());
  	       StringBuilder uptApprStatus = new StringBuilder("UPDATE T_CRM_TARGETS t1 SET t1.CT_APPR_STS = ? "
                + " WHERE t1.CT_TRGT_TYP=? AND t1.CT_YEAR = ? AND  t1.CT_COMP_CODE = ?  AND t1.CT_VER_NO = ?  AND CT_EFF_FRM_DT = TO_DATE(? , 'DD/MM/YYYY hh:mi AM')  "
                + " AND t1.CT_PLAN_TYP=? " );

  	       LOGGER.info("Query :: {} [{},{},{},{},{},{}]", new Object[]{uptApprStatus, status , bean.getTargetType(),bean.getYear(), bean.getCompCode() ,bean.getRevision(),bean.getEffFrmDt()});
           ps = con.prepareStatement(uptApprStatus.toString());
           ps.setString(++t, status);
           ps.setString(++t, bean.getTargetType());
           ps.setLong(++t, bean.getYear());
           ps.setString(++t, bean.getCompCode());
           ps.setLong(++t, bean.getRevision());
           ps.setString(++t, bean.getEffFrmDt());
           ps.setString(++t, bean.getPlanType());
           recCnt = ps.executeUpdate();

  	   }catch(Exception e){

          LOGGER.error("Error in updateTargetApprStatus ", e);

  	   }finally {

  	    closeDbConn(con) ;

  	   }

        return recCnt;
    }

  private  void updateApprovedTargetsToExpired(KPITarget bean){

        Connection con = null;
    	PreparedStatement ps = null;
    	int recCnt = 0;
  	    int t = 0;
  	    String sql = null;

  	    try{

           con = getDBConnection(getDataSource());
           sql = " UPDATE T_CRM_TARGETS SET CT_APPR_STS = 'EXPIRED' "
                +" WHERE CT_PLAN_TYP=? AND CT_TRGT_TYP=? AND CT_YEAR = ? AND  CT_COMP_CODE = ? AND CT_EFF_FRM_DT < TO_DATE( ? , 'DD/MM/YYYY hh:mi AM')";

  	       LOGGER.info("Query :: {}, [{},{},{},{},{}]", new Object[]{sql,bean.getPlanType(),bean.getTargetType(), bean.getYear(), bean.getCompCode(),bean.getEffFrmDt() });

           ps = con.prepareStatement(sql);
           ps.setString(++t, bean.getPlanType());
           ps.setString(++t, bean.getTargetType());
           ps.setLong(++t, bean.getYear());
           ps.setString(++t, bean.getCompCode());
           ps.setString(++t, bean.getEffFrmDt());
           recCnt = ps.executeUpdate();

  	   }catch(Exception e){
          LOGGER.error("Error in setExpired ", e);
  	   }finally {
  	     closeDbConn(con) ;
  	   }


    }

    public String getBaseCurrencyForCompany(String company) {

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

    public List<KeyValue> getCrmTargetTypeList() {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = null;
            query = new StringBuilder("SELECT AC_CODE key, AC_DESC value FROM M_APP_CODES WHERE AC_TYPE = 'CRM_TRGT_TYP'");
            LOGGER.info("Query :: {} []", new Object[]{query});
            list = executeList(con, query.toString(), data, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while fetching CRM target types", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> getSalesPlanningTargetLevels() {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = null;
            query = new StringBuilder("SELECT AC_CODE key, AC_DESC value FROM M_APP_CODES WHERE AC_TYPE = 'CRM_TRGT_LVL' AND AC_CODE > 003 ORDER BY AC_CODE ASC");
            LOGGER.info("Query :: {} []", new Object[]{query});
            list = executeList(con, query.toString(), data, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while fetching CRM target levels", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> getAllTargetLevels() {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] data = null;
            query = new StringBuilder("SELECT AC_CODE key, AC_DESC value FROM M_APP_CODES WHERE AC_TYPE = 'CRM_TRGT_LVL' ORDER BY AC_CODE ASC");
            LOGGER.info("Query :: {} []", new Object[]{query});
            list = executeList(con, query.toString(), data, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while fetching CRM target levels", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    private String getPlanType(Long lvlNo) {
        String planType;

           	if(lvlNo < 4){
           		planType = TypeConstants.KPIPlanType.TARGET.getCode();
        	}else {
        		planType = TypeConstants.KPIPlanType.SALES.getCode();
        	}

        return planType;
    }

    private String convertToDBTimeZone(String effDt) {

//          DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
//          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
//    	  ZoneId toTimeZone = ZoneId.of(ApplicationConstants.DB_TIMEZONE);
//    	  ZonedDateTime d;
//
//		try {
//
//			System.out.println();
//
//			Date effDate = df.parse(effDt);
//
//			Calendar cal = Calendar.getInstance();
//		    cal.setTime(effDate);
//
//
//			 d = ZonedDateTime.ofInstant(effDate.toInstant(), ZoneId.of(cal.getTimeZone().getID()));
//    	     ZonedDateTime currentDBtime = d.withZoneSameInstant(toTimeZone);
//    	     effDt = currentDBtime.format(formatter);
//
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

       return effDt;
    }

 public Long getMaxRevNumForSalesPlan(KPITarget bean){

     Connection con = null;
     String sql = null;
     PreparedStatement ps = null;
     ResultSet rs = null;
     int i = 0;
	 Long maxLvlNum = null;


     try {

         con = getDBConnection(getDataSource());
	     sql = "SELECT max(CT_VER_NO) maxRev,t2.CT_PLAN_TYP,CT_TRGT_TYP, CT_YEAR ,CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE FROM T_CRM_TARGETS t2 " +
               "WHERE  t2.CT_PLAN_TYP=002 " +
               "AND CT_TRGT_TYP=? " +
               "AND CT_YEAR = (select to_char(sysdate, 'YYYY') from dual) " +
               "AND CT_COMP_CODE=? AND CT_DIVN_CODE=?  AND CT_DEPT_CODE=? " +
               "GROUP BY CT_PLAN_TYP,CT_YEAR, CT_COMP_CODE,CT_DIVN_CODE,CT_DEPT_CODE,CT_TRGT_TYP " ;

          LOGGER.info("Query :: {}{}{} ", sql, bean.getTargetType(),bean.getCompCode());

          ps = con.prepareStatement(sql);
          ps.setString(++i, bean.getTargetType());
          ps.setString(++i, bean.getCompCode());
          ps.setString(++i, bean.getDivCode());
          ps.setString(++i, bean.getDeptCode());

          rs = ps.executeQuery();
          if (rs.next()) {
     	       maxLvlNum = rs.getLong(1);
         }
    }catch (Exception e) {
             LOGGER.error("Error while fetching max target level number", e);
    } finally {
            closeDbConn(con);
    }

     return maxLvlNum;

 }
  private Long getTargetLvlNum(KPITarget bean){

	  if( bean.getTrgtLvlNo() != null)
		      return bean.getTrgtLvlNo();

	  long trgrtLvlNum = 0L;
	  Long maxLvlNum = 0L;
      Connection con = null;
      String sql = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      int i = 0;

	  if(StringUtils.equals(TypeConstants.KPITargetLevel.COMPANY.getCode(),bean.getLevel().toLowerCase())){
		  trgrtLvlNum = 1L;
	  } else if (StringUtils.equals(TypeConstants.KPITargetLevel.DIVISION.getCode(),bean.getLevel().toLowerCase())){
		  trgrtLvlNum = 2L;
	  } else if (StringUtils.equals(TypeConstants.KPITargetLevel.DEPARTMENT.getCode(),bean.getLevel().toLowerCase())){
		  trgrtLvlNum = 3L;
	  } else {

	        try {
	            con = getDBConnection(getDataSource());
	            sql = "SELECT MAX(CT_TRGT_LVL_NO) key, CT_TRGT_TYP value, CT_COMP_CODE info,CT_YEAR info1, CT_VER_NO info2 FROM T_CRM_TARGETS  " +
                        "WHERE CT_TRGT_TYP= ? AND CT_COMP_CODE = ? AND  CT_YEAR = ? AND CT_VER_NO = ? " +
                        "GROUP BY CT_TRGT_TYP,CT_COMP_CODE,CT_YEAR,CT_VER_NO";

	            LOGGER.info("Query :: {}{} ", sql, bean.toString());


	            ps = con.prepareStatement(sql);
	            ps.setString(++i, bean.getTargetType());
	            ps.setString(++i, bean.getCompCode());
	            ps.setLong(++i, bean.getYear());
	            ps.setLong(++i, bean.getRevision());
	            rs = ps.executeQuery();
	            if (rs.next()) {
	            	maxLvlNum = rs.getLong(1);
	                }

	        } catch (Exception e) {
	            LOGGER.error("Error while fetching max target level number", e);
	        } finally {
	            closeDbConn(con);
	        }

	        trgrtLvlNum = maxLvlNum + 1;
	  }

	  return trgrtLvlNum;

  }



    public String getDataSource() {
        return dataSource;
    }
}
