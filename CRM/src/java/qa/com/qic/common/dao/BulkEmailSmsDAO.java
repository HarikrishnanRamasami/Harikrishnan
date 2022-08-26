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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import qa.com.qic.anoud.vo.BulkSMSEmail;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.utility.helpers.LogUtil;
import qa.com.qic.utility.helpers.Validatory;

/**
 *
 * @author ravindar.singh
 */
public class BulkEmailSmsDAO extends DatabaseDAO {

    private static final Logger logger = LogUtil.getLogger(BulkEmailSmsDAO.class);

    private final String dataSource;

    public BulkEmailSmsDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public List<KeyValue> getTemplateList(String string) {
       List<KeyValue> template = null;
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT SMT_TEMP_CODE, SMT_TEMP_CODE||' - '||SMT_TEMP_DESC SMT_TEMP_DESC, SMT_EMAIL_FROM  FROM M_SMS_EMAIL_TEMPLATE ORDER BY ROWID";
            logger.debug(" Query :: {}", query);
            prs = con.prepareStatement(query);
            rs = prs.executeQuery();
            template = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString("SMT_TEMP_CODE"));
                keyValue.setValue(rs.getString("SMT_TEMP_DESC"));
                template.add(keyValue);
            }
            logger.info("Retreiving Property Information for template ");
        } catch (Exception e) {
            logger.error("Error while retreiving properties for {}");
            logger.debug("Error while retreiving properties ", e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        return template;
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
            if(ApplicationConstants.M_APP_CODE_RELATION.equalsIgnoreCase(condtnString))
                query += " ORDER BY TO_NUMBER(NVL(AC_MC_CODE,99))";
            else
                query += " ORDER BY AC_DESC";
            logger.debug(" Query :: {}", query);
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
            logger.info("Retreiving Property Information for condition {}", condtnString);
        } catch (Exception e) {
            logger.error("Error while retreiving key value for {}", condtnString);
            logger.debug("Error while retreiving key value ", e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        return vehicleMake;
    }

    public String loadBulkSMSEmail(String sendOption, String customizeData, String includePol, final String companyCode) {
        String msg = null;
        CallableStatement callStmt = null;
        Connection con = null;
        try {
            logger.debug("PKG_SMS_EMAIL.PR_LOAD_BULK_SMS_EMAIL => {}:{};{}", new Object[]{sendOption, customizeData, includePol});
            con = getDBConnection(getDataSource());
            callStmt = con.prepareCall("{call PKG_SMS_EMAIL.PR_LOAD_BULK_SMS_EMAIL(?,?,?,?)}");
            callStmt.setString(1, sendOption);
            callStmt.setString(2, customizeData);
            callStmt.setString(3, includePol);
            callStmt.registerOutParameter(4, Types.VARCHAR);
            callStmt.executeQuery();
            msg = callStmt.getString(4);
            con.commit();
            logger.info("PR_LOAD_BULK_SMS_EMAIL => Error: {}", msg);
        } catch (Exception ex) {
            logger.error("Error on PKG_SMS_EMAIL.PR_LOAD_BULK_SMS_EMAIL", ex);
        } finally{
            closeDBCallSt(callStmt, con);
        }
        return msg;
    }

    public List<BulkSMSEmail> bulksmsDetails(Integer start, Integer end,String companyCode) {
        List<BulkSMSEmail> bulkSMSEmailList = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT ROWID ROW_ID, A.* FROM T_BULK_SMS_EMAIL A OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

            logger.debug(" Query :: {}", query);
            st = con.prepareStatement(query);
            st.setInt(1, start);
            st.setInt(2,end);
            rs = st.executeQuery();
            bulkSMSEmailList = new ArrayList<>();
            while (rs.next()) {
                BulkSMSEmail bulkSMSEmail = new BulkSMSEmail();
                bulkSMSEmail.setRowId(rs.getString("ROW_ID"));
                bulkSMSEmail.setSmsEmailHeading(rs.getString("SE_EMAIL_SMS"));
                bulkSMSEmail.setCol1(rs.getString("SE_COL_1"));
                bulkSMSEmail.setCol2(rs.getString("SE_COL_2"));
                bulkSMSEmail.setCol3(rs.getString("SE_COL_3"));
                bulkSMSEmail.setCol4(rs.getString("SE_COL_4"));
                bulkSMSEmail.setCol5(rs.getString("SE_COL_5"));
                bulkSMSEmail.setCol6(rs.getString("SE_COL_6"));
                bulkSMSEmail.setCol7(rs.getString("SE_COL_7"));
                bulkSMSEmail.setCol8(rs.getString("SE_COL_8"));
                bulkSMSEmail.setCol9(rs.getString("SE_COL_9"));
                bulkSMSEmail.setCol10(rs.getString("SE_COL_10"));
                bulkSMSEmailList.add(bulkSMSEmail);
            }
            logger.info(" Default BULK SMS EMAIL retreived succussfully  ", "");
        } catch (Exception e) {
            logger.error(" Error while BULK SMS EMAIL information", e);
        } finally {
            closeDBComp(st, rs, con);
        }

        return bulkSMSEmailList;
    }

    public String sendBulkSMSEmail(String sendOption, String tempCode, final String companyCode) {
        String msg = null;
        CallableStatement callStmt = null;
        Connection con = null;
        try {
            logger.debug("PKG_SMS_EMAIL.PR_SEND_BULK_SMS_EMAIL Package Called...");
            logger.info(sendOption + "::" + tempCode);
            con = getDBConnection(getDataSource());
            callStmt = con.prepareCall("{call PKG_SMS_EMAIL.PR_SEND_BULK_SMS_EMAIL(?,?,?)}");
            callStmt.setString(1, sendOption);
            callStmt.setString(2, tempCode);
            callStmt.registerOutParameter(3, Types.VARCHAR);
            callStmt.executeQuery();
            msg = callStmt.getString(3);
            logger.info(" PR_SEND_BULK_SMS_EMAIL ");
        } catch (Exception ex) {
            logger.error(" Error on PKG_SMS_EMAIL.PR_SEND_BULK_SMS_EMAIL");
            logger.debug(" Error on PKG_SMS_EMAIL.PR_SEND_BULK_SMS_EMAIL", ex);
        } finally{
            closeDBCallSt(callStmt, con);
        }
        return msg;
    }

    public String previewSMSEmail(String sendOption, String tempCode, String rowId, final String companyCode) {
        String msg = "";
        CallableStatement callStmt = null;

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            String query = "select PKG_SMS_EMAIL.FN_VIEW_BULK_SMS_EMAIL (?,?,?) viewSMSEmail from  dual";

            logger.debug(" Query :: {}", query + sendOption + ":" + tempCode + ":" + rowId);
            st = con.prepareStatement(query);
            st.setString(1, sendOption);
            st.setString(2, tempCode);
            st.setString(3, rowId);

            rs = st.executeQuery();
            while (rs.next()) {
                msg = rs.getString("viewSMSEmail");
            }
            System.out.println("msg" + msg);
            logger.info(" value retreived succussfully for Temp Code - {} ", tempCode);
        } catch (Exception e) {
            logger.error(" Error while retrieving Email/SMS information", e);
        } finally {
            closeDBComp(st, null, con);
        }
        return msg;
    }

    public BulkSMSEmail getEditSMSEmailTemplate(String template_code, String companyCode) {
        logger.info("getEditSMSEmailTemplate");
       BulkSMSEmail bulksmsEmail = null;
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps=null;
        try {
            con = getDBConnection(getDataSource());
            String query = "select SMT_TEMP_CODE,SMT_TEMP_DESC, SMT_EMAIL_TO,SMT_EMAIL_FROM,SMT_EMAIL_SUBJECT,SMT_EMAIL_BODY,SMT_SMS_TXT,SMT_EMAIL_CC from m_sms_email_template where SMT_TEMP_CODE=? ORDER BY 1,2 ";

            logger.debug(query);
            ps = con.prepareStatement(query);
            ps.setString(1, template_code);
            rs = ps.executeQuery();
            while (rs.next()) {
                bulksmsEmail = new BulkSMSEmail();
                bulksmsEmail.setTemplate_code(rs.getString("SMT_TEMP_CODE"));
                bulksmsEmail.setDescription(rs.getString("SMT_TEMP_DESC"));
                bulksmsEmail.setEmailto(rs.getString("SMT_EMAIL_TO"));
                bulksmsEmail.setEmailfrom(rs.getString("SMT_EMAIL_FROM"));
                bulksmsEmail.setEmailsubject(rs.getString("SMT_EMAIL_SUBJECT"));
                bulksmsEmail.setEmailbody(rs.getString("SMT_EMAIL_BODY"));
                bulksmsEmail.setSmstext(rs.getString("SMT_SMS_TXT"));
                bulksmsEmail.setEmailcc(rs.getString("SMT_EMAIL_CC"));
            }

        } catch (Exception e) {
            logger.error("Exception => ", e);
        } finally {
            closeDBComp(ps, rs, con);
        }

        return bulksmsEmail;
    }

    public String insertUploadedDetails(File csvFile, String company, String sendOption, String mobileNoLength, String mobileNoStartsWith) {
        String result = "";
        if (csvFile.exists() && csvFile.isFile()) {
            Connection con = getDBConnection(getDataSource());
            PreparedStatement prs = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            String error;
            try {
                prs = con.prepareStatement("DELETE FROM T_BULK_SMS_EMAIL");
                boolean test = prs.execute();
                logger.info("Uploading Started");
                int rowCount = 0;
                boolean errorStatus = false;
                List cellDataList = new ArrayList();
                FileInputStream fileInputStream = new FileInputStream(csvFile);
                Workbook workBook = WorkbookFactory.create(fileInputStream);
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
                        String query = "INSERT INTO T_BULK_SMS_EMAIL(SE_EMAIL_SMS";
                        String values = "VALUES(? ";
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
                                if (j != 0) {
                                    query = query + ", SE_COL_" + (j);
                                    values = values + ", ? ";
                                }
                                cellValues[j] = colValue;
                            }
                            query = query + " ) ";
                            values = values + ")";
                            query = query + values;
                            logger.info("Query" + rowCount + "==> " + query);

                            if (cellCount == cellTempList.size()) {
                                emptyRowCount++;
                            } else {
                                prs = con.prepareStatement(query);
                                int l = 0;
                                if (cellValues != null && cellValues.length > 0) {
                                    for (String cellValue : cellValues) {

                                        if ("S".equalsIgnoreCase(sendOption)) {
                                            if (!Validatory.validateString(cellValues[0])) {
                                                result = "Enter Mobile Number";
                                                break;
                                            } else if (Validatory.validateString(cellValues[0]) && !Validatory.validateLength(cellValues[0], Integer.valueOf(mobileNoLength))) {
                                                result = "Mobile Number should be exact " + mobileNoLength + " digit";
                                                break;
                                            } else if ((Validatory.validateString(cellValues[0]) && !Validatory.validateMobileNo(cellValues[0], Integer.valueOf(mobileNoLength), mobileNoStartsWith))) {
                                                result = "Mobile number should starts with " + mobileNoStartsWith;
                                                break;
                                            }
                                            prs.setString(++l, cellValue);
                                        } else if ("E".equalsIgnoreCase(sendOption)) {
                                            if (!Validatory.validateString(cellValues[0])) {
                                                result = "Enter Email Address ";
                                                break;
                                            } else if (Validatory.validateString(cellValues[0]) && !Validatory.isValidEmail(cellValues[0])) {
                                                result = "Enter valid Email Address";
                                                break;
                                            }
                                            prs.setString(++l, cellValue);
                                        }

                                    }
                                }
                                prs.execute();
                                prs.close();
                            }
                            if (emptyRowCount > 1) {
                                break;
                            }
                        } catch (Exception e) {
                            logger.info("Query" + rowCount + "==> Not Inserted. Because " + e.getMessage());
                            errorStatus = true;
                        }
                    }
                }
            } catch (Exception e) {
                result = e.getMessage();
                logger.debug("Error while Inserting Upload Details : ", e);
            } finally {
                closeDBComp(prs, rs, con);
                closeDBCallSt(cs, con);
            }
        } else {
            result = "File Not Available.";
        }

        return result;
    }
      public String getErrorMsg(final String errorCode, final String company) {
        String errMsg = "";
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            String sql = "select err_msg from m_err_msg a where err_code = ?";
            logger.info(sql);
            prs = con.prepareStatement(sql);
            prs.setString(1, errorCode);
            rs = prs.executeQuery();
            if (rs.next()) {
                errMsg = rs.getString(1);
            }
        } catch (Exception e) {
            logger.info("Exception=>" + e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        return errMsg;
    }

    public List<BulkSMSEmail> bulksmsDetails(String companyCode) {

        List<BulkSMSEmail> bulkSMSEmailList = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = getDBConnection(getDataSource());
            String query = "SELECT ROWID ROW_ID, A.* FROM T_BULK_SMS_EMAIL A ";

            logger.debug(" Query :: {}", query);
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            bulkSMSEmailList = new ArrayList<>();
            while (rs.next()) {
                BulkSMSEmail bulkSMSEmail = new BulkSMSEmail();
                bulkSMSEmail.setRowId(rs.getString("ROW_ID"));
                bulkSMSEmail.setSmsEmailHeading(rs.getString("SE_EMAIL_SMS"));
                bulkSMSEmail.setCol1(rs.getString("SE_COL_1"));
                bulkSMSEmail.setCol2(rs.getString("SE_COL_2"));
                bulkSMSEmail.setCol3(rs.getString("SE_COL_3"));
                bulkSMSEmail.setCol4(rs.getString("SE_COL_4"));
                bulkSMSEmail.setCol5(rs.getString("SE_COL_5"));
                bulkSMSEmail.setCol6(rs.getString("SE_COL_6"));
                bulkSMSEmail.setCol7(rs.getString("SE_COL_7"));
                bulkSMSEmail.setCol8(rs.getString("SE_COL_8"));
                bulkSMSEmail.setCol9(rs.getString("SE_COL_9"));
                bulkSMSEmail.setCol10(rs.getString("SE_COL_10"));
                bulkSMSEmailList.add(bulkSMSEmail);
            }
            logger.info(" Default BULK SMS EMAIL retreived succussfully  ", "");
        } catch (Exception e) {
            logger.error(" Error while BULK SMS EMAIL information", e);
        } finally {
            closeDBComp(st, rs, con);
        }

        return bulkSMSEmailList;
    }

    public void updateSMSEmailTemplateService(BulkSMSEmail bean, String companyCode) {
       int i = 0;
        ResultSet rs = null;
        Connection con = null;
       PreparedStatement ps =null;
        String sql = "update m_sms_email_template set SMT_TEMP_DESC=?,"
                + "SMT_EMAIL_TO=?, SMT_EMAIL_FROM=?, SMT_EMAIL_SUBJECT=?,"
                + "SMT_EMAIL_BODY=?, SMT_SMS_TXT=?, SMT_EMAIL_CC=? where SMT_TEMP_CODE=?";
        logger.debug(sql);
        try {
            con = getDBConnection(getDataSource());
            ps = con.prepareStatement(sql);
            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getEmailto());
            ps.setString(3, bean.getEmailfrom());
            ps.setString(4, bean.getEmailsubject());
            ps.setString(5, bean.getEmailbody());
            ps.setString(6, bean.getSmstext());
            ps.setString(7, bean.getEmailcc());
            ps.setString(8, bean.getTemplate_code());
            i = ps.executeUpdate();
            logger.debug("Updated" + i);
        } catch (SQLException ex) {
            logger.error("Exception => {}", ex);
        } finally {
            closeDBComp(ps, rs, con);
        }
    }
}
