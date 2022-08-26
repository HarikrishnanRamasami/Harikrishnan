/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.Appointment;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author karthik.j
 */
public class AppointmentDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(AppointmentDAO.class);

    private final String dataSource;

    public AppointmentDAO(String dataSource) {
        this.dataSource = dataSource;
    }


    public int saveAppointmentDetail(final Appointment bean) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        Long appt_seqId;

        try {
        	 con = getDBConnection(getDataSource());
             appt_seqId = getSeqVal(con, "SELECT S_CA_ID.NEXTVAL FROM DUAL");



             if ("add".equalsIgnoreCase(bean.getOperation())) {
                sql = "INSERT INTO T_CRM_APPOINTMENTS (CA_ID,CA_TITLE, CA_DESC, CA_APP_FM_DT , CA_APP_TO_DT, "
                		+ "CA_TIMEZONE, CA_LOCATION,CA_ATTENDEES,CA_CR_UID,CA_CR_DT,CA_CRM_ID,CA_REF_ID,CA_CONTACT_TYP, CA_DEAL_ID) "
                        + "VALUES(:caId,:title, :description, TO_DATE(:startDate, 'DD/MM/YYYY hh:mi AM') , TO_DATE(:endDate, 'DD/MM/YYYY hh:mi AM') , :timezone, "
                        + ":location, :attendees, :crUid,SYSDATE,:crmId,:caRefId,:caContactTyp, :caDealId)";
                ps = con.prepareStatement(sql);
                ps.setLong(++i, appt_seqId );
                ps.setString(++i, bean.getCaTitle());
                ps.setString(++i, bean.getCaDescription());
                ps.setString(++i, bean.getCaStartDate());
                ps.setString(++i, bean.getCaEndDate());
                ps.setString(++i, bean.getCaTimezone());
                ps.setString(++i, bean.getCaLocation());
                ps.setString(++i, bean.getCaAttendees());
                ps.setString(++i, bean.getCaCrUid());
                ps.setString(++i, bean.getCaCrmId());
                ps.setString(++i, bean.getCaRefId());
                ps.setString(++i, bean.getCaContactType());
                ps.setString(++i, bean.getCaDealId());

                recCnt = ps.executeUpdate();

            } else if ("edit".equalsIgnoreCase(bean.getOperation())) {
                sql = " UPDATE T_CRM_APPOINTMENTS SET CA_TITLE = ?, CA_DESC = ?, CA_APP_FM_DT = TO_DATE(?, 'DD/MM/YYYY hh:mi AM') , CA_APP_TO_DT = TO_DATE(?, 'DD/MM/YYYY hh:mi AM'), "
                        + "CA_TIMEZONE = ?, CA_LOCATION = ?, CA_ATTENDEES = ?, CA_UPD_UID = ?, CA_UPD_DT = SYSDATE, CA_CRM_ID = ? "
                        + " where CA_ID = ?";

                LOGGER.info("Query :: {} [{}]", new Object[]{sql, bean.getCaId()});
                ps = con.prepareStatement(sql);
                ps.setString(++i, bean.getCaTitle());
                ps.setString(++i, bean.getCaDescription());
                ps.setString(++i, bean.getCaStartDate());
                ps.setString(++i, bean.getCaEndDate());
                ps.setString(++i, bean.getCaTimezone());
                ps.setString(++i, bean.getCaLocation());
                ps.setString(++i, bean.getCaAttendees());
                ps.setString(++i, bean.getCaUpdUid());
                ps.setString(++i, bean.getCaCrmId());
                ps.setLong(++i, bean.getCaId());
                recCnt = ps.executeUpdate();
            } else if("delete".equalsIgnoreCase(bean.getOperation())){
                sql = "DELETE FROM  T_CRM_APPOINTMENTS WHERE CA_ID = ? ";
                LOGGER.info("Query :: {} [{}]", new Object[]{sql, bean.getCaId()});
                ps = con.prepareStatement(sql);
                ps.setLong(++i, bean.getCaId());
                recCnt = ps.executeUpdate();
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error adding/editing appointment entry => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public int saveMeetingNotes(final Appointment bean, final String operation) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        try {
                con = getDBConnection(getDataSource());
                sql = " UPDATE T_CRM_APPOINTMENTS SET CA_MEETING_NOTES = ?"
                        + " where CA_ID = ?";
                LOGGER.info("Query :: {} [{}]", new Object[]{sql, bean.getCaId()});
                ps = con.prepareStatement(sql);
                ps.setString(++i,bean.getCaNotes());
                ps.setLong(++i, bean.getCaId());
                recCnt = ps.executeUpdate();
                con.commit();

        } catch (Exception e) {
            LOGGER.error("Error while adding notes => {}", e);
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public List<Appointment> loadCustomerAppointmentData(String civilId, String crmId) {
        List<Appointment> list = null;
        Connection con = null;
        StringBuilder query = null;
        TimeZone tz = Calendar.getInstance().getTimeZone();


        try {
            con = getDBConnection(getDataSource());
            Object[] param;
            List<Object> params = new LinkedList<>();
            params.add(civilId.trim());
            query = new StringBuilder("SELECT CA_ID caId,CA_CONTACT_TYP caContactType,CA_CRM_ID caCrmId,PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CA_CRM_ID) caCrmDesc, CA_TITLE caTitle, CA_DESC caDescription, TO_CHAR(CA_APP_FM_DT, 'DD/MM/YYYY hh:mi AM')  caStartDate, ");
            query.append("TO_CHAR(CA_APP_TO_DT, 'DD/MM/YYYY hh:mi AM')  caEndDate, CA_LOCATION caLocation, CA_TIMEZONE caTimezone, CA_REF_ID caRefId, CA_ATTENDEES caAttendees, CA_MEETING_NOTES caNotes ");
            query.append("FROM T_CRM_APPOINTMENTS WHERE CA_REF_ID = ? ");

            if (StringUtils.isNotBlank(crmId)) {
                    query.append("AND CA_CRM_ID = ? ");
                    params.add(crmId.trim());
            }

            query.append(" ORDER BY  CA_APP_FM_DT ASC ");
            param = params.toArray();
            LOGGER.info("Query :: {} {}", query, Arrays.toString(param));
            list = executeList(con, query.toString(), param, Appointment.class);

            for(Appointment appt:list){
                DateFormat originalDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sourceTzID = appt.getCaTimezone();
                originalDateFormat.setTimeZone(TimeZone.getTimeZone(sourceTzID));
                DateFormat targetDateFormat = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
                String targetTzID = Calendar.getInstance().getTimeZone().getID();
                targetDateFormat.setTimeZone(TimeZone.getTimeZone(targetTzID));
                String tzShortCode = TimeZone.getTimeZone(targetTzID).getDisplayName(false, TimeZone.SHORT);
                appt.setCaStartDate(appt.getCaStartDate() + " " + tzShortCode);
                appt.setCaEndDate(appt.getCaEndDate() + " " + tzShortCode);
             }


        } catch (Exception e) {
            LOGGER.error("Error while retreiving customer360 appointments", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public List<Appointment> loadAppointmentList(Appointment bean) {
        List<Appointment> list = new LinkedList<>();
        Connection con = null;
        StringBuilder queryBl = null;
        String query = null;
        String[] start, end;
        String select = null;
        StringBuilder whereClause = null;
        String orderBy  = " ORDER BY caStartDate ASC ";

        try {
            con = getDBConnection(getDataSource());
            List<String> param = new LinkedList();
            select = "SELECT * FROM ";
            queryBl = new StringBuilder("(SELECT CA_ID caId,CA_CONTACT_TYP caContactType,CA_CRM_ID caCrmId,PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CA_CRM_ID) caCrmDesc, CA_TITLE caTitle, CA_DESC caDescription, TO_CHAR(CA_APP_FM_DT, 'DD/MM/YYYY hh:mi AM') caStartDate, ");
            queryBl.append("TO_CHAR(CA_APP_TO_DT, 'DD/MM/YYYY hh:mi AM') caEndDate, CA_LOCATION caLocation, CA_TIMEZONE caTimezone, "
            		+ "CA_REF_ID caRefId, INS_NAME caCustName,"
            		+ "CA_ATTENDEES caAttendees, CA_MEETING_NOTES caNotes , CA_CR_UID caCrUid, "
            		+ "USER_NAME caCrUidDesc ");
            queryBl.append("FROM T_CRM_APPOINTMENTS JOIN TABLE(FN_GET_CRM_AGENTS(CA_CR_UID)) ON CA_CR_UID=USER_ID ");
            queryBl.append("JOIN M_INSURED ON CA_REF_ID=INS_CIVIL_ID WHERE CA_CONTACT_TYP=002 ");
            queryBl.append("UNION ");
            queryBl.append("SELECT CA_ID caId,CA_CONTACT_TYP caContactType,CA_CRM_ID caCrmId,PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CA_CRM_ID) caCrmDesc, CA_TITLE caTitle, CA_DESC caDescription, TO_CHAR(CA_APP_FM_DT, 'DD/MM/YYYY hh:mi AM')  caStartDate, ");
            queryBl.append("TO_CHAR(CA_APP_TO_DT, 'DD/MM/YYYY hh:mi AM')  caEndDate, CA_LOCATION caLocation, CA_TIMEZONE caTimezone, "
            		+ "CA_REF_ID caRefId,CD_CUST_NAME caCustName,"
            		+ "CA_ATTENDEES caAttendees, CA_MEETING_NOTES caNotes , CA_CR_UID caCrUid, "
            		+ "USER_NAME caCrUidDesc ");
            queryBl.append("FROM T_CRM_APPOINTMENTS JOIN TABLE(FN_GET_CRM_AGENTS(CA_CR_UID)) ON CA_CR_UID=USER_ID  "
            		+ "JOIN T_CRM_DEALS ON CA_DEAL_ID=CD_DEAL_ID WHERE CA_CONTACT_TYP=001 ) ");

            whereClause = new StringBuilder("  ");

            if (StringUtils.isNoneBlank(bean.getCaCrUid())) {
            	whereClause.append(" WHERE ");
                if (bean.getCaCrUid().startsWith("ALL")) {
                	whereClause.append(" EXISTS(SELECT USER_ID FROM TABLE(FN_GET_CRM_AGENTS(?)) WHERE USER_ID = caCrUid) ");
                } else {
                	whereClause.append(" caCrUid = ? ");
                }
                param.add(bean.getCaCrUid().replaceFirst("ALL", ""));
            }

            if (StringUtils.isNotBlank(bean.getCaCrmId())) {
                if (!"ALL".equals(bean.getCaCrmId())) {
                    if (!param.isEmpty()) {
                    	whereClause.append(" AND ");
                    } else{
                    	 whereClause.append(" WHERE ");
                    }
                    whereClause.append(" caCrmId  = ? ");
                    param.add(bean.getCaCrmId());
                }
            }
            if (StringUtils.isNotBlank(bean.getCaContactType())) {
                if (!"ALL".equals(bean.getCaContactType())) {
                    if (!param.isEmpty()) {
                    	whereClause.append(" AND ");
                    }else{
                   	    whereClause.append(" WHERE ");
                   }
                    whereClause.append(" caContactType = ? ");
                    param.add(bean.getCaContactType());
                }
            }


            if (StringUtils.isNotBlank(bean.getCaDateRange())) {
                if (!param.isEmpty()) {
                	whereClause.append(" AND ");
                } else{
               	    whereClause.append(" WHERE ");
               }
                whereClause.append(" TRUNC(TO_DATE(caStartDate, 'DD/MM/YYYY hh:mi AM')) BETWEEN TRUNC(TO_DATE(?, 'DD/MM/YYYY')) AND TRUNC(TO_DATE(?, 'DD/MM/YYYY')) ");
                String s[] = bean.getCaDateRange().split(" - ");
                param.add(s[0]);
                param.add(s[1]);
            }
            query = select + queryBl.toString();
            query += whereClause.toString();
            query += orderBy;

            LOGGER.info("Query :: {}[{}] ", query , param.toArray());
            list = executeList(con, query, param.toArray(),Appointment.class);

            for(Appointment appt:list){


                DateFormat originalDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                String sourceTzID = appt.getCaTimezone();
                originalDateFormat.setTimeZone(TimeZone.getTimeZone(sourceTzID));
                DateFormat targetDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                String targetTzID = Calendar.getInstance().getTimeZone().getID();
                targetDateFormat.setTimeZone(TimeZone.getTimeZone(targetTzID));
                Date originalstartDate = originalDateFormat.parse(appt.getCaStartDate());
                Date originalEndDate = originalDateFormat.parse(appt.getCaEndDate());
                String formattedstartDate = targetDateFormat.format(originalstartDate);
                String formattedendDate = targetDateFormat.format(originalEndDate);
                start= formattedstartDate.split(" ",5);
            	String starttime = start[1] + " " + start[2];
            	appt.setCaStartTime(starttime);
            	end= formattedendDate.split(" ",5);
            	String endtime = end[1] + " " + end[2];
            	appt.setCaEndTime(endtime);
                String tzShortCode = TimeZone.getTimeZone(targetTzID).getDisplayName(false, TimeZone.SHORT);
                appt.setCaStartDate(formattedstartDate + " " + tzShortCode);
                appt.setCaEndDate(formattedendDate + " " + tzShortCode);
            }


        } catch (Exception e) {
            LOGGER.error("Error while retreiving appointment list", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public  Appointment loadAppointmentById(String type , Long caId) {

    	Connection con = null;
        StringBuilder query = null;
        String[] start, end;
        Appointment appointment = new Appointment();
        String joinClause = null;
        String custName = null;

        try {
            con = getDBConnection(getDataSource());
            Object[] param;
            List<Object> params = new LinkedList<>();
            params.add(caId);

            if(StringUtils.equals(type,TypeConstants.CrmContactType.LEAD.getCode())){
            	 joinClause = " JOIN T_CRM_DEALS ON CD_DEAL_ID=CA_DEAL_ID  ";
            	 custName  = " CD_CUST_NAME ";
            } else if(StringUtils.equals(type,TypeConstants.CrmContactType.CUSTOMER.getCode())) {
                 joinClause = " JOIN M_INSURED ON CA_REF_ID=INS_CIVIL_ID ";
                 custName  = " INS_NAME ";
            }

            query = new StringBuilder("SELECT CA_ID caId, CA_CONTACT_TYP caContactType,PKG_REP_UTILITY.FN_GET_AC_NAME( 'CRM_CONT_TYP', CA_CONTACT_TYP) caContTypDesc,CA_TITLE caTitle, CA_DESC caDescription, TO_CHAR(CA_APP_FM_DT, 'DD/MM/YYYY hh:mi AM')  caStartDate, ")
                .append("TO_CHAR(CA_APP_TO_DT, 'DD/MM/YYYY hh:mi AM') caEndDate, CA_LOCATION caLocation, CA_TIMEZONE caTimezone, CA_REF_ID caRefId ,CA_CRM_ID caCrmId, PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CA_CRM_ID) caCrmDesc, ")
                .append(custName +" caCustName,")
                .append("CA_ATTENDEES caAttendees, CA_MEETING_NOTES caNotes ")
                .append("FROM T_CRM_APPOINTMENTS ")
                .append(joinClause)
                .append("WHERE CA_ID = ? ");



            param = params.toArray();
            LOGGER.info("Query :: {} {}", query, Arrays.toString(param));
            appointment = (Appointment) executeQuery(con, query.toString(), param, Appointment.class);

            String formattedstartDate = appointment.getCaStartDate();
            String formattedendDate =   appointment.getCaEndDate();
            start= formattedstartDate.split(" ",5);
        	String starttime = start[1] + " " + start[2];
        	appointment.setCaStartTime(starttime);
        	end= formattedendDate.split(" ",5);
        	String endtime = end[1] + " " + end[2];
        	appointment.setCaEndTime(endtime);
        	appointment.setCaStartDate(start[0]);
        	appointment.setCaEndDate(end[0]);


        } catch (Exception e) {
            LOGGER.error("Error while retreiving appointment by Id", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return appointment;
    }

    public List<KeyValue> loadContactList(String searchString, String type) {
        List<KeyValue> list = null;
        Object[] params = new Object[]{"%" + searchString + "%", "%" + searchString + "%"};
        Connection con = null;
        StringBuilder query = null;

        try {
            con = getDBConnection(getDataSource());

            if(StringUtils.equals(type,TypeConstants.CrmContactType.LEAD.getCode())){
                query = new StringBuilder("SELECT CL_ID key, CL_ID ||'-'|| CL_NAME value  , CL_NAME info FROM T_CRM_LEADS "
                        + "WHERE (upper(CL_ID) like upper(?) OR upper(CL_NAME) like upper(?)) ");
            } else if(StringUtils.equals(type,TypeConstants.CrmContactType.CUSTOMER.getCode())) {
                query = new StringBuilder("SELECT INS_CIVIL_ID key, INS_CIVIL_ID ||'-'|| INS_NAME value , INS_NAME info FROM M_INSURED "
                        + "WHERE (upper(INS_CIVIL_ID) like upper(?) OR upper(INS_NAME) like upper(?)) ");
            }

            LOGGER.info("Query :: {} []", params);
            list = executeList(con, query.toString(), params, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while loading contact List", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadAppointmentSummary(final String type) {
        List<KeyValue> list = null;
        String query = null;
        String select = null;
        String filter = null;
        String addCondition = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            if (null != type) {
                switch (type) {
                    case "OWNER":
                    	select = "SELECT DISTINCT USER_ID key,USER_NAME value ,";
                    	filter = "CA_CR_UID ";
                    	addCondition =  "JOIN TABLE(FN_GET_CRM_AGENTS(t1."+filter+")) ON t1."+filter+"=USER_ID" ;
                        break;
                    case "ENTITY":
                       	select = "select PARA_SUB_CODE key,PARA_NAME value,PARA_VALUE text,NVL(rs.info,0) info,NVL(rs.info1,0) info1,NVL(rs.info2,0) info2 FROM M_APP_PARAMETER "+
                        "LEFT OUTER  JOIN (SELECT DISTINCT t1.CA_CRM_ID key,PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', t1.CA_CRM_ID) value ,";
                    	filter = "CA_CRM_ID ";
                    	addCondition = ") rs ON key=PARA_SUB_CODE WHERE PARA_CODE='CRM_TYPE' ORDER BY PARA_NAME ASC ";
                        break;
                    case "CONTACT":
                       	select = "SELECT AC_CODE key,AC_DESC value,NVL(rs.info,0) info,NVL(rs.info1,0) info1,NVL(rs.info2,0) info2 FROM M_APP_CODES "+
                        "LEFT OUTER  JOIN ( SELECT DISTINCT t1.CA_CONTACT_TYP key,PKG_REP_UTILITY.FN_GET_AC_NAME( 'CRM_CONT_TYP', t1.CA_CONTACT_TYP) value ,";
                        filter = "CA_CONTACT_TYP ";
                        addCondition = ") rs ON key=AC_CODE WHERE AC_TYPE='CRM_CONT_TYP' ORDER BY AC_DESC ASC ";
                        break;
                }

                query = select
                		+  " NVL(x.due_today,0) as info , NVL(y.upcoming,0) as info1, NVL(z.closed,0) as info2 FROM T_CRM_APPOINTMENTS t1 "
                        + "LEFT OUTER  JOIN "
                        + "(SELECT  "+filter+",NVL(COUNT(CA_ID), 0) due_today FROM T_CRM_APPOINTMENTS t1 WHERE TRUNC (SYSDATE) = TRUNC (CA_APP_TO_DT) GROUP BY  "+filter+" ) x "
                        +  "ON t1."+filter+"=x."+filter+" "
                        +  "LEFT OUTER  JOIN "
                        +  "(SELECT  "+filter+",NVL(COUNT(CA_ID), 0) upcoming FROM T_CRM_APPOINTMENTS t2 WHERE TRUNC (SYSDATE) < TRUNC (CA_APP_TO_DT) GROUP BY  "+filter+" ) y "
                        + "ON t1."+filter+"=y."+filter+" "
                        + "LEFT OUTER  JOIN "
                        + "(SELECT  "+filter+",NVL(COUNT(CA_ID), 0) closed FROM T_CRM_APPOINTMENTS t3 WHERE TRUNC (SYSDATE) > TRUNC (CA_APP_TO_DT) GROUP BY  "+filter+" ) z "
                        + "ON t1."+filter+"=z."+filter+" "
                        + addCondition;

            }

            LOGGER.info("Query :: {}", new Object[]{query});
            list = executeList(con, query,null, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("", e);
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

    public String getInsuredName(String civilId) {
        String insName = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        con = getDBConnection(getDataSource());
        String sql = "SELECT INS_NAME insName"
                + "FROM M_INSURED WHERE "
                + "INS_CIVIL_ID = ? ";
        ps = con.prepareStatement(sql);
        ps.setString(1, civilId.trim());
        rs = ps.executeQuery();
        if (rs.next()) {
        	insName = rs.getString(1);
            }
        } catch (Exception e) {
            LOGGER.error("Error in getInsuredCode ", e);
        } finally {
            closeDBComp(ps, rs, con) ;
        }

        return insName;
    }


	public String getDataSource() {
		return dataSource;
	}
}
