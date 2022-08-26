/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import qa.com.qic.anoud.vo.FeedBackInfoBO;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class FeedBackDAO extends DatabaseDAO {

    private static final Logger logger = LogUtil.getLogger(qa.com.qic.common.dao.FeedBackDAO.class);

    private final String dataSource;

    public FeedBackDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public FeedBackDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<FeedBackInfoBO> getFeedbackList(String formId, String company) {
        Connection con = getDBConnection(getDataSource());
        List<FeedBackInfoBO> feedbackList = null;
        List<KeyValue> options = null;
        PreparedStatement prs = null;
        ResultSet rs = null;
        String query = "select ff_question_id, ff_question, ff_option_1, ff_option_1_img, ff_option_2, ff_option_2_img,"
                + " ff_option_3, ff_option_3_img, ff_option_4, ff_option_4_img, ff_option_5, ff_option_5_img "
                + " from m_feedback_form where ff_form_id = ? and ff_status = 'A' order by ff_question_id";
        logger.info(" Query :: {}", query + ":" + formId);
        try {
            prs = con.prepareStatement(query);
            prs.setString(1, formId);
            rs = prs.executeQuery();
            feedbackList = new ArrayList<>();
            while (rs.next()) {
                FeedBackInfoBO feedbackInfo = new FeedBackInfoBO();
                feedbackInfo.setId(rs.getString("ff_question_id"));
                feedbackInfo.setQuestion(rs.getString("ff_question"));
                options = new ArrayList<>();
                for (int i = 1; i <= 5; i++) {
                    if (rs.getString("ff_option_" + i) != null && !"".equals(rs.getString("ff_option_" + i))) {
                        KeyValue keyValueBO = new KeyValue();
                        keyValueBO.setKey(i + "");
                        keyValueBO.setValue(rs.getString("ff_option_" + i));
                        keyValueBO.setInfo1(rs.getString("ff_option_" + i + "_img"));
                        options.add(keyValueBO);
                    }
                }
                feedbackInfo.setOptions(options);
                feedbackList.add(feedbackInfo);
            }
        } catch (Exception e) {
            logger.info("{}", e);
        } finally {
            closeDBComp(prs, rs, con);
        }
        return feedbackList;
    }

    public int submitFeedback(String formId, String userName, String custId, String refNo, String emailId, String mobileNo, String suggestions, String remarks, List<FeedBackInfoBO> answers, final String companyCode) {
        String queryValue = ") VALUES (?,?,?,?,?,?,SYSDATE,?,?";
        String query = "INSERT INTO T_CUST_FEEDBACK (CF_FORM_ID, CF_USER_NAME, CF_CUST_ID, CF_REF_NO, CF_EMAIL_ID, CF_MOBILE_NO, CF_CR_DT, CF_SUGGESTIONS ,CF_REMARKS ";
        for (int i = 1; i <= answers.size(); i++) {
            query += ", CF_QUESTION_ID_" + i + ", CF_OPTION_" + i;
            queryValue += ",?,?";
        }
        query = query + queryValue + ")";
        logger.info("Query: {}, Params: [{},{},{},{},{},{},{},{}]",
                new Object[]{query, formId, userName, custId, refNo, emailId, mobileNo, suggestions, remarks});
        int indx = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getDBConnection(getDataSource());
            ps = con.prepareStatement(query);
            ps.setString(++indx, formId);
            if (userName == null) {
                ps.setNull(++indx, Types.VARCHAR);
            } else {
                ps.setString(++indx, userName);
            }
            if (custId == null) {
                ps.setNull(++indx, Types.VARCHAR);
            } else {
                ps.setString(++indx, custId);
            }
            if (refNo == null) {
                ps.setNull(++indx, Types.VARCHAR);
            } else {
                ps.setString(++indx, refNo);
            }
            if (emailId == null) {
                ps.setNull(++indx, Types.VARCHAR);
            } else {
                ps.setString(++indx, emailId);
            }
            if (mobileNo == null) {
                ps.setNull(++indx, Types.VARCHAR);
            } else {
                ps.setString(++indx, mobileNo);
            }
            if (suggestions == null) {
                ps.setNull(++indx, Types.VARCHAR);
            } else {
                ps.setString(++indx, suggestions);
            }
            if (remarks == null) {
                ps.setNull(++indx, Types.VARCHAR);
            } else {
                ps.setString(++indx, remarks);
            }

            for (FeedBackInfoBO fo : answers) {
                ps.setString(++indx, fo.getQuestion());
                ps.setString(++indx, fo.getId());
            }
            indx = 0;
            indx = ps.executeUpdate();
            logger.info("Feedback Information Inserted Successfully, Status: " + indx);
        } catch (Exception e) {
            logger.info("{}", e);
        } finally {
            closeDBComp(ps, rs, con);
        }
        return indx;
    }

    public String getDataSource() {
        return dataSource;
    }

    public List<KeyValue> loadFeedBackList(String custId) {
        List<KeyValue> list = null;
        Connection con = null;
        StringBuilder query = null;
        Object[] data = new Object[]{custId};
        try {
            con = getDBConnection(getDataSource());
            query = new StringBuilder("SELECT PKG_REP_UTILITY.FN_GET_AC_NAME('CRM_FEEDBACK', CF_FORM_ID)  key ,TO_CHAR(CF_CR_DT, 'DD/MM/RRRR')  value FROM T_CUST_FEEDBACK WHERE CF_CUST_ID =?");
            list = executeList(con, query.toString(), data, KeyValue.class);
        } catch (Exception e) {
            logger.error("Error while retreiving e-mail template => {}", e);
        } finally {
            query = null;
            closeDbConn(con);
        }
        return list;
    }
}
