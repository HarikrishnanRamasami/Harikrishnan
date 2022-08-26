/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.common.dms;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author sutharsan.g
 */
public class DmsDAO extends DatabaseDAO {

    private static final Logger logger = LogUtil.getLogger(DmsDAO.class);
    private String query = null;
    private Object[] params = null;
    private Connection con = null;
    private PreparedStatement prs = null;
    private CallableStatement cs = null;
    private ResultSet rs = null;
    private String dataSource;

    public DmsDAO() {
    }

    public DmsDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public static final String DOC_INFO = "INSERT INTO T_CRM_DOC_INFO(CDI_DOC_ID,CDI_TRANS_ID,CDI_TRAN_SR_NO,CDI_REF_NO,CDI_DOC_TYPE,CDI_DOC_CODE,"
            + "CDI_DOC_NAME,CDI_FILE_EXTN,CDI_REMARKS,CDI_LINK,CDI_CR_UID,CDI_CR_DT) VALUES(S_CDI_DOC_ID.NEXTVAL,?,?,?,?,?,?,?,?,?,?,SYSDATE)";
    public static final String DOCERROR_INFO = "INSERT INTO T_CRM_DOC_ERR(CDE_TRANS_ID,CDE_TRAN_SR_NO,CDE_FILE_NAME,CDE_FILE_EXTN,CDE_COMPANY,CDE_DOC_TYPE,"
            + "CDE_LOB,CDE_QUOT_CLM_NO,CDE_TRANS_PARTY_NO,CDE_FILE_TYPE,CDE_REMARKS,CDE_ERR_CODE,CDE_ERR_DESC,CDE_UPL_DT,CDE_UPL_USER)"
            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'dd/mm/yyyy HH24:MI'),?)";

    public static final String NEWGEN_URL = "SELECT AC_VALUE FROM M_APP_CONFIG WHERE AC_NAME = 'DMS_STREAM_URL'";

    public static final String DELETE_DOC_INFO = "DELETE FROM T_DOC_INFO WHERE TDI_DOC_ID = ?";
    public static final String INS_DOC_URL_FOR_ALL = "INSERT INTO DMS_DOC_SECURITY_TABLE (KEY, VALUE) VALUES (?, ?)";

    public List<TDocInfoBean> loadUploadInformation(TDocInfoBean bean, final String company) throws Exception {
        List<TDocInfoBean> list = new LinkedList<>();
        try {
            con = getDBConnection(dataSource);
            query = "SELECT CDI_DOC_ID cdiDocId,CDI_TRANS_ID cdiTransId,CDI_TRAN_SR_NO cdiTranSrNo,CDI_DOC_TYPE cdiDocType, CDI_DOC_CODE cdiDocCode,"
                    + "CDI_DOC_NAME cdiDocName,CDI_REMARKS cdiRemarks,CDI_CR_UID cdiCrUid,CDI_CR_DT cdiCrDt,CDI_LINK cdiLink,CDI_FILE_EXTN cdiFileExtn "
                    + "FROM T_CRM_DOC_INFO WHERE CDI_TRANS_ID = ? AND CDI_DOC_TYPE = ?";
            logger.debug("Query :: {}", query);
            logger.debug("Query Params :: {}, {}, {}", new Object[]{bean.getCdiTransId(), bean.getCdiDocType()});
            params = new Object[]{bean.getCdiTransId(), bean.getCdiDocType()};
            logger.info("loadUploadInformation Retrieved");
            list = executeList(con, query, params, TDocInfoBean.class);
        } catch (Exception e) {
            logger.error("Error in loadUploadInformation => {}", e);
            throw e;
        } finally {
            closeDBComp(prs, rs, con);
            query = null;
        }
        return list;
    }

    protected List<KeyValue> getDmsDocTypeList(final String acMcCode) {
        List<KeyValue> dmsTypeList = null;
        try {
            con = getDBConnection(dataSource);
            String query = "select ac_code, ac_desc from m_app_codes where AC_TYPE = 'DOC_TYPE' and ac_mc_code = ? "
                    + "union select ac_code, ac_desc from m_app_codes where AC_TYPE = 'DOC_TYPE' "
                    + "and ac_mc_code = 'B' AND SYSDATE BETWEEN AC_EFF_FM_DT AND AC_EFF_TO_DT ORDER BY AC_CODE";
            logger.debug(" Query :: {}", query);
            prs = con.prepareStatement(query);
            prs.setString(1, acMcCode);
            rs = prs.executeQuery();
            dmsTypeList = new ArrayList<>();
            while (rs.next()) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(rs.getString("AC_DESC"));
                keyValue.setValue(rs.getString("AC_CODE"));
                dmsTypeList.add(keyValue);
            }
            logger.info("Retreiving Property Information for condition DOC_TYPE");
        } catch (Exception e) {
            logger.error("Error while retreiving key value for DOC_TYPE");
            logger.debug("Error while retreiving key value ", e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        return dmsTypeList;
    }

    protected String insertUploadedDocInfo(final String transactionId, final String transactionSrNo, final String refNo, final String polOrClm, final String docCode,
            final List<String> docName, final List<String> fileExt, final String dmsRemarks, final List<String> urlLink, final String userId) throws Exception {
        logger.info("insertUploadedDocInfo - Enter");
        String result = "falied";
        String query = DOC_INFO;
        try {
            con = getDBConnection(getDataSource());
            prs = con.prepareStatement(query);
            for (int i = 0; i < urlLink.size(); i++) {
                prs.setString(1, transactionId);
                prs.setString(2, transactionSrNo);
                prs.setString(3, refNo);
                prs.setString(4, polOrClm);
                prs.setString(5, docCode);
                prs.setString(6, docName.get(i));
                prs.setString(7, fileExt.get(i));
                prs.setString(8, dmsRemarks);
                prs.setString(9, urlLink.get(i));
                prs.setString(10, userId);
                prs.addBatch();
            }
            int[] insertedeRows = prs.executeBatch();
            if (insertedeRows != null) {
                for (int insertedeRow : insertedeRows) {
                    logger.debug(" Inserted Row Status :: " + insertedeRow);
                }
                result = "success";
            }
        } catch (Exception e) {
            logger.error("Exception => ", e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        return result;
    }

    protected String insertErrorDocInfo(final String transactionId, final String transactionSrNo, final String refNo, final String polOrClm,
            final List<String> docName, final List<String> fileExt, final String dmsRemarks, final List<String> errFileNames, final String userId,
            final String lob, final List<String> errorCodes, final List<String> errorDescs) {
        String result = "falied";
        String query = DOCERROR_INFO;
        try {
            con = getDBConnection(getDataSource());
            prs = con.prepareStatement(query);
            for (int i = 0; i < errorCodes.size(); i++) {
                prs.setString(1, transactionId);
                prs.setString(2, transactionSrNo);
                prs.setString(3, errFileNames.get(i));
                prs.setString(4, fileExt.get(i));
                prs.setString(5, polOrClm);
                prs.setString(6, lob);
                prs.setString(7, refNo);
                prs.setString(8, docName.get(i));
                prs.setString(9, dmsRemarks);
                prs.setString(10, errorCodes.get(i));
                prs.setString(11, errorDescs.get(i));
                prs.setString(12, userId);
                prs.addBatch();
            }
            int[] insertedeRows = prs.executeBatch();
            if (insertedeRows != null) {
                for (int insertedeRow : insertedeRows) {
                    logger.debug(" Inserted Row Status :: " + insertedeRow);
                }
                result = "success";
            }
        } catch (Exception e) {
            logger.error("Exception => ", e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        return result;
    }

    public int deleteFileByDocId(final String docId, final String company) {
        int affectedRows = 0;
        PreparedStatement ps = null;
        try {
            con = getDBConnection(dataSource);

            String query = DELETE_DOC_INFO;
            ps = con.prepareStatement(query);
            logger.debug("Query => " + query + ":" + docId);
            int i = 0;
            ps.setString(++i, docId);
            affectedRows = ps.executeUpdate();
        } catch (Exception e) {
            logger.error("Exception => ", e);
        } finally {
            closeDBComp(ps, rs, con);
        }
        return affectedRows;
    }

    public String getDmsUrl() {
        String url = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getDBConnection(dataSource);
            String query = NEWGEN_URL;
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                url = rs.getString(1);
            }
        } catch (Exception e) {
            logger.error("Error while retreiving app parameter => {}", e);
        } finally {
            closeDbConn(con);
        }
        return url;
    }
}
