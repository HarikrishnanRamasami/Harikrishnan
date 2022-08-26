/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.vo.CrmLeads;
import qa.com.qic.common.vo.CrmOpportunities;
import qa.com.qic.common.vo.DataBean;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author kosuri.rao
 */
public class LeadDAO extends DatabaseDAO {

    private static final Logger LOGGER = LogUtil.getLogger(LeadDAO.class);

    private final String dataSource;

    public LeadDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public List<KeyValue> loadStatusList() {
        List<KeyValue> statusList = new ArrayList<>();
        statusList.add(new KeyValue("P", "Pending"));
        statusList.add(new KeyValue("C", "Closed"));
        return statusList;
    }

    public List<KeyValue> loadOppStatusList() {
        List<KeyValue> statusList = new ArrayList<>();
        statusList.add(new KeyValue("O", "Open"));
        statusList.add(new KeyValue("C", "Closed"));
        return statusList;
    }

    public CrmLeads loadLeadData(final Long leadId) {
        CrmLeads CrmLeads = null;
        List<CrmLeads> list = loadLeadList(null, null, null, leadId, null,null, false);
        if (list != null && !list.isEmpty()) {
            CrmLeads = list.get(0);
        }
        return CrmLeads;
    }

    public List<CrmLeads> loadLeadList(final String userId, final String refId, final String refNo, String status, final String workPlace) {
        return loadLeadList(userId, refId, refNo, null, status,workPlace, false);
    }

    public List<CrmLeads> loadLeadUploadedList(final Long leadId) {
        return loadLeadList(null, null, null, leadId, null,null, true);
    }

    private List<CrmLeads> loadLeadList(final String userId, final String refId, final String refNo, final Long leadId, final String status,String workPlace, final boolean isTemp) {
        List<CrmLeads> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = null;
            List param = new LinkedList();

            query = new StringBuilder("SELECT CL_ID clId, PKG_REP_UTILITY.FN_GET_PARA_NAME('CUST_SOURCE', CL_SOURCE) clSource, CL_REF_ID clRefId, CL_REF_NO clRefNo, CL_NAME clName, CL_MOBILE_NO clMobileNo, CL_CR_DT clCrDt, CL_STATUS clStatus, ");
          //commented for corporate
            /*query.append("CL_CRM_ID clCrmId, PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_TYPE', CL_CRM_ID) clCrmDesc, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('LEADS_DS', CL_WORK_PLACE) as clWorkPlace, NVL(PKG_REP_UTILITY.FN_GET_AC_NAME('STATE', CL_CITY), CL_CITY) as clCity, NVL(PKG_REP_UTILITY.FN_GET_AC_NAME('NATIONALITY', CL_NATIONALITY), CL_NATIONALITY) as clNationality, CL_FLEX_05 clFlex05 ");
            query.append("FROM ");
            if(isTemp) query.append("W"); else query.append("T");
            query.append("_CRM_LEADS ");
            if (StringUtils.isNoneBlank(userId) || StringUtils.isNoneBlank(refId) ||
                    StringUtils.isNoneBlank(refNo) || StringUtils.isNoneBlank(workPlace) ||
                    (leadId != null && leadId > 0) || StringUtils.isNoneBlank(status)) {
                query.append("WHERE ");
            }*/
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('LEADS_DS', CL_WORK_PLACE) as clWorkPlace,  NVL(PKG_REP_UTILITY.FN_GET_AC_NAME('STATE', CL_CITY), CL_CITY) as clCity, NVL(PKG_REP_UTILITY.FN_GET_AC_NAME('NATIONALITY', CL_NATIONALITY), CL_NATIONALITY) as clNationality, CL_FLEX_05 clFlex05 ");
            query.append("FROM ");
            if(isTemp) query.append("W"); else query.append("T");
            query.append("_CRM_LEADS WHERE ");
            if (StringUtils.isNoneBlank(userId)) {
                query.append("CL_ASSIGNED_TO = ? ");
                date = new Object[]{userId};
                param.add(userId);
            } else if (StringUtils.isNoneBlank(refId)) {
                query.append("CL_REF_ID = ? ");
                date = new Object[]{refId};
                param.add(refId);
            } else if (StringUtils.isNoneBlank(refNo)) {
                query.append("CL_REF_NO = ? ");
                date = new Object[]{refNo};
                param.add(refNo);
            }
            else if (StringUtils.isNoneBlank(workPlace)) {
                query.append("CL_WORK_PLACE = ? ");
                date = new Object[]{workPlace};
                param.add(workPlace);
            }
            else if (leadId != null && leadId > 0) {
                query.append("CL_ID = ? ");
                date = new Object[]{leadId};
                param.add(leadId);
            }
            if (StringUtils.isNoneBlank(status)) {
                if (!param.isEmpty()) {
                    query.append("AND ");
                }
                query.append("CL_STATUS = ? ");
                date = new Object[]{status};
                param.add(status);
            }
            query.append("ORDER BY CL_ID DESC");
            date = param.toArray();
            LOGGER.info("Query :: {} [{}:{}:{}:{}:{}:{}]", new Object[]{query, userId, refId, refNo, leadId,status,workPlace});
            list = executeList(con, query.toString(), date, CrmLeads.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving task list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public CrmLeads loadLeadDetailById(long clId) {
        CrmLeads bean = null;
        Connection con = null;
       //commented for corporate
       // String sql = "SELECT CL_ID clId, CL_CRM_ID clCrmId, CL_REF_ID clRefId, CL_REF_NO clRefNo, CL_NAME clName, CL_GENDER clGender, CL_NATIONALITY clNationality, CL_PO_BOX clPoBox, "
        String sql = "SELECT CL_ID clId, CL_REF_ID clRefId, CL_REF_NO clRefNo, CL_NAME clName, CL_GENDER clGender, CL_NATIONALITY clNationality, CL_PO_BOX clPoBox, "
                + "CL_CITY clCity, CL_COUNTRY clCountry, CL_TEL_NO clTelNo, CL_MOBILE_NO clMobileNo, CL_FAX_NO clFaxNo, CL_EMAIL_ID clEmailId, "
                + "CL_SOURCE clSource, CL_WORK_PLACE clWorkPlace, CL_OCCUPATION clOccupation, "
                + "TO_CHAR(CL_BIRTH_DT, 'DD/MM/YYYY') clBirthDt, CL_REMARKS clRemarks, "
                + "CL_ASSIGNED_TO clAssignedTo, CL_STATUS clStatus, CL_FLEX_01 clFlex01, CL_FLEX_02 clFlex02, CL_FLEX_03 clFlex03, CL_FLEX_04 clFlex04 "
                + "FROM T_CRM_LEADS WHERE CL_ID = ?";
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{sql, clId});
            Object[] params = new Object[]{clId};
            bean = (CrmLeads) executeQuery(con, sql, params, CrmLeads.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving customer details => {}", e);
        } finally {
            closeDbConn(con);
        }
        return bean;
    }

    public int saveLeads(final CrmLeads bean, final String operation) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equalsIgnoreCase(operation)) {
            	//commented for corporate
                //sql = "INSERT INTO T_CRM_LEADS (CL_ID, CL_CRM_ID, CL_REF_ID, CL_REF_NO, CL_NAME, CL_GENDER, CL_NATIONALITY, CL_PO_BOX, CL_CITY, CL_COUNTRY, "
            	sql = "INSERT INTO T_CRM_LEADS (CL_ID, CL_REF_ID, CL_REF_NO, CL_NAME, CL_GENDER, CL_NATIONALITY, CL_PO_BOX, CL_CITY, CL_COUNTRY, "
                        + "CL_TEL_NO, CL_MOBILE_NO, CL_FAX_NO, CL_EMAIL_ID, CL_SOURCE, CL_WORK_PLACE, CL_OCCUPATION, "
                        + "CL_BIRTH_DT, CL_REMARKS, CL_CR_UID, CL_CR_DT, CL_ASSIGNED_TO, CL_ASSIGNED_DT, CL_STATUS, CL_CLOSE_DT, "
                        + "CL_FLEX_01, CL_FLEX_02, CL_FLEX_03, CL_FLEX_04) VALUES "
                        //commented for corporate
                        //+ "(S_CL_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "(S_CL_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, "
                        + "TO_DATE(?, 'DD/MM/YYYY'), ?, ?, SYSDATE, ?, SYSDATE, ?, DECODE(?, 'C', SYSDATE, NULL), ?, ?, ?, ?)";
            	//commented for corporate
                //Object[] params = new Object[]{bean.getClCrmId(), bean.getClRefId(), bean.getClRefNo(), bean.getClName(), bean.getClGender(), bean.getClNationality(), bean.getClPoBox(), bean.getClCity(), bean.getClCountry(), bean.getClTelNo(),
            	Object[] params = new Object[]{bean.getClRefId(), bean.getClRefNo(), bean.getClName(), bean.getClGender(), bean.getClNationality(), bean.getClPoBox(), bean.getClCity(), bean.getClCountry(), bean.getClTelNo(),
                    bean.getClMobileNo(), bean.getClFaxNo(), bean.getClEmailId(), bean.getClSource(), bean.getClWorkPlace(), bean.getClOccupation(), bean.getClBirthDt(), bean.getClRemarks(), bean.getClCrUid(),
                    bean.getClAssignedTo(), bean.getClStatus(), bean.getClStatus(), bean.getClFlex01(), bean.getClFlex02(), bean.getClFlex03(), bean.getClFlex04()};
                recCnt = executeUpdate(con, sql, params);
            } else if ("edit".equalsIgnoreCase(operation)) {
            	//commented for corporate
                //sql = "UPDATE T_CRM_LEADS SET CL_CRM_ID = ?, CL_REF_ID = ?, CL_REF_NO = ?, CL_NAME = ?, CL_GENDER = ?, CL_NATIONALITY = ?, CL_PO_BOX = ?, CL_CITY = ?, CL_COUNTRY = ?, "
            	 sql = "UPDATE T_CRM_LEADS SET CL_REF_ID = ?, CL_REF_NO = ?, CL_NAME = ?, CL_GENDER = ?, CL_NATIONALITY = ?, CL_PO_BOX = ?, CL_CITY = ?, CL_COUNTRY = ?, "
                        + "CL_TEL_NO = ?, CL_MOBILE_NO = ?, CL_FAX_NO = ?, CL_EMAIL_ID = ?, CL_SOURCE = ?, CL_WORK_PLACE =?, CL_OCCUPATION = ?, "
                        + "CL_BIRTH_DT = TO_DATE(?, 'DD/MM/YYYY'), CL_REMARKS = ?, CL_ASSIGNED_TO = ?, CL_STATUS = ?, CL_CLOSE_DT = DECODE(?, 'C', SYSDATE, NULL), "
                        + "CL_FLEX_01 = ?, CL_FLEX_02 = ?, CL_FLEX_03 = ?, CL_FLEX_04 = ? WHERE CL_ID = ?";
            	//commented for corporate
               // Object[] params = new Object[]{bean.getClCrmId(), bean.getClRefId(), bean.getClRefNo(), bean.getClName(), bean.getClGender(), bean.getClNationality(), bean.getClPoBox(), bean.getClCity(), bean.getClCountry(), bean.getClTelNo(),
            	 Object[] params = new Object[]{bean.getClRefId(), bean.getClRefNo(), bean.getClName(), bean.getClGender(), bean.getClNationality(), bean.getClPoBox(), bean.getClCity(), bean.getClCountry(), bean.getClTelNo(),
                    bean.getClMobileNo(), bean.getClFaxNo(), bean.getClEmailId(), bean.getClSource(), bean.getClWorkPlace(), bean.getClOccupation(), bean.getClBirthDt(), bean.getClRemarks(),
                    bean.getClAssignedTo(), bean.getClStatus(), bean.getClStatus(), bean.getClFlex01(), bean.getClFlex02(), bean.getClFlex03(), bean.getClFlex04(), bean.getClId()};
                recCnt = executeUpdate(con, sql, params);
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while task entry => {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public int saveOpportunities(final CrmLeads leadsBean, final CrmOpportunities oppBean, final String operation) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        int i = 0, recCnt = 0;
        String sql = null;
        try {
            con = getDBConnection(getDataSource());
            if ("add".equalsIgnoreCase(operation)) {
                sql = "INSERT INTO T_CRM_OPPURTUNITIES (CO_ID, CO_CL_ID, CO_REF_ID, CO_NAME, CO_GENDER, CO_NATIONALITY, CO_PO_BOX, CO_CITY, CO_COUNTRY, "
                        + "CO_TEL_NO, CO_MOBILE_NO, CO_FAX_NO, CO_EMAIL_ID, CO_SOURCE, CO_WORK_PLACE, CO_OCCUPATION, "
                        + "CO_BIRTH_DT, CO_OPP_TYPE, CO_REF_NO, CO_CURRENCY, CO_VALUE, CO_REMARKS, CO_CR_UID, CO_CR_DT, CO_ASSIGNED_TO, CO_STATUS) VALUES "
                        + "(S_CO_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, "
                        + "TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?)";
                Object[] params = new Object[]{leadsBean.getClId(), leadsBean.getClRefId(), leadsBean.getClName(), leadsBean.getClGender(), leadsBean.getClNationality(), leadsBean.getClPoBox(), leadsBean.getClCity(), leadsBean.getClCountry(), leadsBean.getClTelNo(),
                    leadsBean.getClMobileNo(), leadsBean.getClFaxNo(), leadsBean.getClEmailId(), leadsBean.getClSource(), leadsBean.getClWorkPlace(), leadsBean.getClOccupation(), leadsBean.getClBirthDt(), oppBean.getCoOppType(), oppBean.getCoRefNo(),
                    oppBean.getCoCurrency(), oppBean.getCoValue(), oppBean.getCoRemarks(), oppBean.getCoCrUid(), leadsBean.getClAssignedTo(), "O"};
                recCnt = executeUpdate(con, sql, params);
            } else if ("edit".equalsIgnoreCase(operation)) {
                sql = "UPDATE T_CRM_OPPURTUNITIES SET CO_STATUS = ? WHERE CO_ID = ?";
                Object[] params = new Object[]{oppBean.getCoStatus(), oppBean.getCoId()};
                recCnt = executeUpdate(con, sql, params);
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.error("Error while task entry => {}", e);
            throw e;
        } finally {
            closeDBComp(ps, null, con);
        }
        return recCnt;
    }

    public CrmOpportunities loadOpportunityData(final Long leadId) {
        CrmOpportunities opportunities = null;
        List<CrmOpportunities> list = loadOpportunityList(null, null, leadId, null);
        if (list != null && !list.isEmpty()) {
            opportunities = list.get(0);
        }
        return opportunities;
    }

    public List<CrmOpportunities> loadOpportunityList(final String userId, final String refId, String status) {
        return loadOpportunityList(userId, refId, null, status);
    }

    private List<CrmOpportunities> loadOpportunityList(final String userId, final String refId, final Long opportunityId, final String status) {
        List<CrmOpportunities> list = null;
        Connection con = null;
        StringBuilder query = null;
        try {
            con = getDBConnection(getDataSource());
            Object[] date = null;
            List param = new LinkedList();

            query = new StringBuilder("SELECT CO_ID coId, CO_ASSIGNED_TO coAssignedTo, TO_CHAR(CO_BIRTH_DT, 'DD/MM/YYYY') coBirthDt, CO_CITY coCity, CO_CL_ID coClId, ");
            query.append("CO_CLOSE_DT coCloseDt, CO_COUNTRY coCountry, CO_CR_DT coCrDt, CO_CR_UID coCrUid, CO_CURRENCY coCurrency, ");
            query.append("CO_EMAIL_ID coEmailId, CO_FAX_NO coFaxNo, CO_FLEX_01 coFlex01, CO_FLEX_02 coFlex02, CO_FLEX_03 coFlex03, ");
            query.append("CO_FLEX_04 coFlex04, CO_FLEX_05 coFlex05, CO_GENDER coGender, CO_MOBILE_NO coMobileNo, CO_NAME coName, ");
            query.append("CO_NATIONALITY coNationality, CO_OCCUPATION coOccupation, CO_OPP_TYPE coOppType, CO_PO_BOX coPoBox, CO_REF_ID coRefId, ");
            query.append("CO_REF_NO coRefNo, CO_REMARKS coRemarks, CO_SOURCE coSource, CO_STATUS coStatus, CO_TEL_NO coTelNo, ");
            query.append("CO_VALUE coValue, CO_WORK_PLACE coWorkPlace, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('NATIONALITY', CO_NATIONALITY) coNationalityDesc, PKG_REP_UTILITY.FN_GET_AC_NAME('OCCUPATION', CO_OCCUPATION) coOccupationDesc, ");
            query.append("PKG_REP_UTILITY.FN_GET_PARA_NAME('GENDER', CO_GENDER) coGenderDesc, PKG_REP_UTILITY.FN_GET_AC_NAME('COUNTRY', CO_COUNTRY) as countryDesc, ");
            query.append("PKG_REP_UTILITY.FN_GET_AC_NAME('STATE', CO_CITY) as coCityDesc, PKG_REP_UTILITY.FN_GET_PARA_NAME('CRM_OPP_TYPE', CO_OPP_TYPE) coOppTypeDesc, ");
            query.append("PKG_REP_UTILITY.FN_GET_PARA_NAME('CUST_SOURCE', CO_SOURCE) coSourceDesc, PKG_REP_UTILITY.FN_GET_CURR_NAME(CO_CURRENCY) coCurrencyDesc ");
            query.append("FROM T_CRM_OPPURTUNITIES WHERE ");
            if (StringUtils.isNoneBlank(userId)) {
                query.append("CO_ASSIGNED_TO = ? ");
                date = new Object[]{userId};
                param.add(userId);
            } else if (StringUtils.isNoneBlank(refId)) {
                query.append("CO_REF_ID = ? ");
                date = new Object[]{refId};
                param.add(refId);
            } else if (opportunityId != null && opportunityId > 0) {
                query.append("CO_ID = ? ");
                date = new Object[]{opportunityId};
                param.add(opportunityId);
            }
            if (StringUtils.isNoneBlank(status)) {
                if (!param.isEmpty()) {
                    query.append("AND ");
                }
                query.append("CO_STATUS = ? ");
                date = new Object[]{status};
                param.add(status);
            }
            query.append("ORDER BY CO_ID DESC");
            date = param.toArray();
            LOGGER.info("Query :: {} [{}:{}:{}:{}]", new Object[]{query, userId, refId, opportunityId, status});
            list = executeList(con, query.toString(), date, CrmOpportunities.class);

        } catch (Exception e) {
            LOGGER.error("Error while retreiving opportunity list => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }

    public String insertUploadedFileDetails(final File csvFile, CrmLeads leads) throws SQLException {
        String result = "";
        String sql;
        Connection con = null;
        CallableStatement cs = null;
        PreparedStatement prs = null;
        FileInputStream fileInputStream = null;
        Workbook workBook = null;
        if (csvFile.exists() && csvFile.isFile()) {
            con = getDBConnection(dataSource);
            con.setAutoCommit(true);
            try {
                LOGGER.info("Uploading Started");
                int rowCount = 0;
                boolean errorStatus = false;
                List cellDataList = new ArrayList();
                fileInputStream = new FileInputStream(csvFile);
                workBook = WorkbookFactory.create(fileInputStream);
                Sheet hssfSheet = workBook.getSheetAt(0);
                Iterator rowIterator = hssfSheet.rowIterator();
                while (rowIterator.hasNext()) {
                    Row hssfRow = (Row) rowIterator.next();
                    Iterator iterator = hssfRow.cellIterator();
                    List cellTempList = new ArrayList();
                    while (iterator.hasNext()) {
                        Cell hssfCell = (Cell) iterator.next();
                        cellTempList.add(hssfCell);
                    }
                    cellDataList.add(cellTempList);
                }
                int emptyRowCount = 0;
                for (int i = 0; i < cellDataList.size(); i++) {
                    if (i != 0) {
                        sql = "INSERT INTO W_CRM_LEADS (CL_ID, CL_REF_ID, CL_NAME, CL_MOBILE_NO, CL_EMAIL_ID, CL_CITY, CL_NATIONALITY, CL_FLEX_01, CL_FLEX_02, CL_FLEX_03, CL_FLEX_04, CL_WORK_PLACE, CL_ASSIGNED_TO, CL_STATUS, CL_CR_UID, CL_CR_DT,CL_SOURCE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE,?)";//S_CL_ID.NEXTVAL
                        List cellTempList = (List) cellDataList.get(i);
                        try {
                            int cellCount = 0;
                            String[] cellValues = new String[cellTempList.size()];
                            for (int j = 0; j < cellTempList.size(); j++) {
                                Cell hssfCell = (Cell) cellTempList.get(j);
                                hssfCell.setCellType(Cell.CELL_TYPE_STRING);
                                String stringCellValue = hssfCell.getStringCellValue();
                                String colValue = stringCellValue == null ? "" : stringCellValue;
                                if ("".equalsIgnoreCase(colValue.trim())) {
                                    cellCount++;
                                }
                                cellValues[j] = colValue;
                            }

                            LOGGER.info("Query" + rowCount + "==> " + sql);
                            if (cellCount == cellTempList.size()) {
                                emptyRowCount++;
                            } else {
                                prs = con.prepareStatement(sql);
                                int l = 0;
                                if (cellValues != null && cellValues.length > 0) {
                                    prs.setLong(++l, leads.getClId());
                                    for (String cellValue : cellValues) {
                                        prs.setString(++l, cellValue);
                                    }
                                    prs.setString(++l, leads.getClWorkPlace());
                                    prs.setString(++l, leads.getClAssignedTo());
                                    prs.setString(++l, leads.getClStatus());
                                    prs.setString(++l, leads.getClCrUid());
                                    prs.setString(++l, leads.getClSource());

                                }
                                prs.execute();
                                prs.close();
                                con.commit();
                            }
                            if (emptyRowCount > 1) {
                                break;
                            }
                        } catch (Exception e) {
                            con.rollback();
                            LOGGER.info("Query" + rowCount + "==> Not Inserted. Because " + e.getMessage());
                            throw e;
                        }
                    }
                }

            } catch (Exception e) {
                result = e.getMessage();
                LOGGER.debug("Error while Inserting Upload Details : ", e);
            } finally {
                try {
                    if (workBook != null) {
                        workBook.close();
                    }
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

    public String confirmLeadsUploadedForm(final Long leadId) {
        String msg = null;
        CallableStatement callStmt = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            String query =
                    "DECLARE "
                        + "P_ERROR VARCHAR2(1000) := NULL; "
                        + "L_ID NUMBER(20) := NULL; "
                    + "BEGIN "
                    + "L_ID := ?; "
                    + "INSERT INTO T_CRM_LEADS (CL_ID, CL_REF_ID, CL_NAME, CL_MOBILE_NO, CL_EMAIL_ID, CL_CITY, CL_NATIONALITY, CL_FLEX_01, CL_FLEX_02, CL_FLEX_03, CL_FLEX_04, CL_SOURCE, CL_WORK_PLACE, CL_ASSIGNED_TO, CL_STATUS, CL_CR_UID, CL_CR_DT) "
                    + "SELECT S_CL_ID.NEXTVAL, CL_REF_ID, CL_NAME, CL_MOBILE_NO, CL_EMAIL_ID, CL_CITY, CL_NATIONALITY, CL_FLEX_01, CL_FLEX_02, CL_FLEX_03, CL_FLEX_04, CL_SOURCE, CL_WORK_PLACE, CL_ASSIGNED_TO, CL_STATUS, CL_CR_UID, CL_CR_DT FROM W_CRM_LEADS WHERE CL_ID = L_ID; "
                    + "DELETE FROM W_CRM_LEADS WHERE CL_ID = L_ID; "
                    + "EXCEPTION "
                        + "WHEN OTHERS THEN "
                            + "P_ERROR := 'Error in moving data : '|| SQLERRM; "
                    + "? := P_ERROR; "
                    + "END;";
            LOGGER.info("Query :: {} [{}]", new Object[]{query, leadId});
            callStmt = con.prepareCall(query);
            callStmt.setLong(1, leadId);
            callStmt.registerOutParameter(2, Types.VARCHAR);
            callStmt.executeQuery();
            msg = callStmt.getString(2);
            LOGGER.info("Confirm uploaded leads => Error: {}", msg);
        } catch (Exception ex) {
            LOGGER.error("Confirm uploaded leads", ex);
        } finally {
            closeDBCallSt(callStmt, con);
        }
        return msg;
    }

    public String saveLeadsReportData(final CrmLeads leads) {
        String msg = null;
        CallableStatement callStmt = null;
        Connection con = null;
        PreparedStatement prs = null;
        try {
            String query = null;
            con = getDBConnection(getDataSource());
            query = "UPDATE T_BULK_SMS_EMAIL SET SE_COL_10 = ?";
            prs = con.prepareStatement(query);
            int l = 0;
            prs.setLong(++l, leads.getClId());
            prs.executeUpdate();
            query = "DECLARE "
                        + "P_ERROR VARCHAR2(1000) := NULL; "
                        + "L_ID NUMBER(20) := NULL; "
                    + "BEGIN "
                    + "L_ID := ?; "
                    + "INSERT INTO W_CRM_LEADS (CL_ID, CL_REF_ID, CL_NAME, CL_MOBILE_NO, CL_EMAIL_ID, CL_NATIONALITY, CL_CITY, CL_FLEX_01, CL_FLEX_02, CL_FLEX_03, CL_FLEX_04, CL_CR_DT) "
                    + "SELECT SE_COL_10, DECODE(SE_EMAIL_SMS, 'X', NULL, SE_EMAIL_SMS), DECODE(SE_COL_1, 'X', NULL, SE_COL_1), DECODE(SE_COL_2, 'X', NULL, SE_COL_2), DECODE(SE_COL_3, 'X', NULL, SE_COL_3),DECODE(SE_COL_4, 'X', NULL, SE_COL_4), DECODE(SE_COL_5, 'X', NULL, SE_COL_5), DECODE(SE_COL_6, 'X', NULL, SE_COL_6), DECODE(SE_COL_7, 'X', NULL, SE_COL_7), DECODE(SE_COL_8, 'X', NULL, SE_COL_8), DECODE(SE_COL_9, 'X', NULL, SE_COL_9), SYSDATE FROM T_BULK_SMS_EMAIL; "
                    + "DELETE FROM T_BULK_SMS_EMAIL; "
                    + "EXCEPTION "
                        + "WHEN OTHERS THEN "
                            + "P_ERROR := 'Error in moving report data : '|| SQLERRM; "
                    + "? := P_ERROR; "
                    + "END;";
            LOGGER.info("Query :: {} [{}]", new Object[]{query, leads.getClId()});
            callStmt = con.prepareCall(query);
            callStmt.setLong(1, leads.getClId());
            callStmt.registerOutParameter(2, Types.VARCHAR);
            callStmt.executeQuery();
            query = "UPDATE W_CRM_LEADS SET CL_SOURCE = ?, CL_WORK_PLACE = ?, CL_ASSIGNED_TO = ?, CL_STATUS = ?, CL_CR_UID = ? WHERE CL_ID = ?";
            prs = con.prepareStatement(query);
            int k = 0;
            prs.setString(++k, leads.getClSource());
            prs.setString(++k, leads.getClWorkPlace());
            prs.setString(++k, leads.getClAssignedTo());
            prs.setString(++k, leads.getClStatus());
            prs.setString(++k, leads.getClCrUid());
            prs.setLong(++k, leads.getClId());
            prs.executeUpdate();
            msg = callStmt.getString(2);
            LOGGER.info("Confirm uploaded leads => Error: {}", msg);
        } catch (Exception ex) {
            LOGGER.error("Confirm uploaded leads", ex);
        } finally {
            closeDBCallSt(callStmt, con);
        }
        return msg;
    }

    private final static String QRY_DATA_SOURCE_LIST = "SELECT AC_CODE key, AC_DESC value, TO_CHAR(TO_DATE(AC_FLEX_01, 'DD-MON-YYYY'), 'DD/MM/YYYY') info1, TO_CHAR(TO_DATE(AC_FLEX_02, 'DD-MON-YYYY'), 'DD/MM/YYYY') info2 FROM M_APP_CODES WHERE AC_TYPE = ? ORDER BY AC_DESC";
    private final static String QRY_ACTIVE_DATA_SOURCE_LIST = "SELECT AC_CODE key, AC_DESC value, TO_CHAR(TO_DATE(AC_FLEX_01, 'DD-MON-YYYY'), 'DD/MM/YYYY') info1, TO_CHAR(TO_DATE(AC_FLEX_02, 'DD-MON-YYYY'), 'DD/MM/YYYY') info2 FROM M_APP_CODES WHERE TRUNC(SYSDATE) BETWEEN TO_DATE(AC_FLEX_01, 'DD-MON-YYYY') AND TO_DATE(AC_FLEX_02, 'DD-MON-YYYY') AND AC_TYPE = ? ORDER BY AC_DESC";
    public List<KeyValue> loadDataSource(final FieldConstants.AppCodes appCodes) {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{QRY_DATA_SOURCE_LIST, appCodes.getCode()});
            list = executeList(con, QRY_DATA_SOURCE_LIST, new Object[]{appCodes.getCode()}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving data source => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    public List<KeyValue> loadActiveDataSource(final FieldConstants.AppCodes appCodes) {
        List<KeyValue> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{QRY_ACTIVE_DATA_SOURCE_LIST, appCodes.getCode()});
            list = executeList(con, QRY_ACTIVE_DATA_SOURCE_LIST, new Object[]{appCodes.getCode()}, KeyValue.class);
        } catch (Exception e) {
            LOGGER.error("Error while retreiving data source => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }

    private final static String QRY_LEAD_DASHBOARD_SUMMARY = "SELECT COUNT(1) count1, SUM(DECODE(CL_STATUS, 'O', 1, 0)) count2, SUM(DECODE(CL_STATUS, 'C', 1, 0)) data1, AC_FLEX_03 data2, SUM(DECODE(CL_STATUS, 'O', CL_VALUE, 0)) data3, SUM(DECODE(CL_STATUS, 'C', CL_VALUE, 0)) data4, TO_DATE(AC_FLEX_01, 'DD-MON-YYYY') date1, TO_DATE(AC_FLEX_02, 'DD-MON-YYYY') date2, (CASE WHEN TRUNC(SYSDATE) BETWEEN AC_FLEX_01 AND AC_FLEX_02 THEN 1 ELSE 0 END) count3 FROM T_CRM_LEADS , M_APP_CODES WHERE AC_TYPE = 'LEADS_DS' AND CL_WORK_PLACE = ? AND AC_CODE = CL_WORK_PLACE GROUP BY AC_FLEX_01, AC_FLEX_02, AC_FLEX_03";
    private final static String QRY_LEAD_DASHBOARD_PIE = "SELECT CL_STATUS key, COUNT(1) value FROM T_CRM_LEADS WHERE CL_WORK_PLACE = ? GROUP BY CL_STATUS";
    private final static String QRY_LEAD_DASHBOARD_SUMMARY_PERIOD = "SELECT TO_CHAR(CL_CLOSE_DT, 'DD-MON') data1, SUM(CL_VALUE) number1 FROM T_CRM_LEADS WHERE CL_WORK_PLACE = ? AND CL_CLOSE_DT IS NOT NULL GROUP BY TO_CHAR(CL_CLOSE_DT, 'DD-MON')";
    public Map loadDashBoardSummary(final String source) {
        Map map = new HashMap();
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            List<KeyValue> list = null;
            DataBean bean = null;

            /*
            count1 - Total Leads
            count2 - Opportunity
            data1 - Materialized
            data2 - Expected Income
            data3 - Income
            data4 - Actual Income
            date1 - Start Date
            date2 - End Date
            count3 - Active
            */
            LOGGER.info("Query :: {} [{}]", new Object[]{QRY_LEAD_DASHBOARD_SUMMARY, source});
            Object[] params = new Object[]{source};
            bean = (DataBean) executeQuery(con, QRY_LEAD_DASHBOARD_SUMMARY, params, DataBean.class);
            map.put("SUMMARY", bean);

            LOGGER.info("Query :: {} [{}]", new Object[]{QRY_LEAD_DASHBOARD_PIE, source});
            list = executeList(con, QRY_LEAD_DASHBOARD_PIE, params, KeyValue.class);
            map.put("PIE", list);
        } catch (Exception e) {
            LOGGER.error("Error in lead dashboard summary => {}", e);
        } finally {
            closeDbConn(con);
        }
        return map;
    }

    public List<DataBean> loadDashBoardSummaryByPeriod(final String source) {
        List<DataBean> list = null;
        Connection con = null;
        try {
            con = getDBConnection(getDataSource());
            LOGGER.info("Query :: {} [{}]", new Object[]{QRY_LEAD_DASHBOARD_SUMMARY_PERIOD, source});
            Object[] params = new Object[]{source};
            list = executeList(con, QRY_LEAD_DASHBOARD_SUMMARY_PERIOD, params, DataBean.class);
        } catch (Exception e) {
            LOGGER.error("Error in dashboard summary by period => {}", e);
        } finally {
            closeDbConn(con);
        }
        return list;
    }
}
