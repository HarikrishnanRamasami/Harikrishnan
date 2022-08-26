/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.common.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.logging.log4j.Logger;

import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author Ravindar Singh T
 * <a href="mailto:ravindar.singh@qicgroup.com.qa">ravindar.singh@qicgroup.com.qa</a>
 */
public class DatabaseDAO {

    private static final Logger logger = LogUtil.getLogger(DatabaseDAO.class);

    public Connection getDBConnection(String companyCode) {
    	//commented for corporate
        /*String appType = ApplicationConstants.APP_TYPE;
        if(companyCode.contains("_")) {
            String s[] = companyCode.split("_");
            appType = s[0];
            companyCode = s[1];
        }
        return getDBConnection(appType, companyCode);*/

    	return getDBConnection(ApplicationConstants.APP_TYPE, companyCode);
    }

    public Connection getDBConnection(String appType, String companyCode) {

        DataSource dataSource = null;
        Connection con = null;

        try {
            // Get DataSource
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            if (null != companyCode) {
                if (ApplicationConstants.APP_TYPE_RETAIL.equals(appType)) {
                    switch (companyCode) {
                        case ApplicationConstants.COMPANY_CODE_DOHA:
                            dataSource = (DataSource) envContext.lookup("jdbc/crm_doha");
                            break;
                        case ApplicationConstants.COMPANY_CODE_OMAN:
                            dataSource = (DataSource) envContext.lookup("jdbc/crm_oman");
                            break;
                        case ApplicationConstants.COMPANY_CODE_DUBAI:
                            dataSource = (DataSource) envContext.lookup("jdbc/crm_dubai");
                            break;
                        case ApplicationConstants.COMPANY_CODE_KUWAIT:
                            dataSource = (DataSource) envContext.lookup("jdbc/crm_kwt");
                            break;
                        case ApplicationConstants.COMPANY_CODE_BEEMA:
                            dataSource = (DataSource) envContext.lookup("jdbc/crm_beema");
                            break;
                    }
                } else if (ApplicationConstants.APP_TYPE_QLM.equals(appType)) {
                    switch (companyCode) {
                        case ApplicationConstants.COMPANY_CODE_MED_DOHA:
                            dataSource = (DataSource) envContext.lookup("jdbc/crm_med");
                            break;
                        case ApplicationConstants.COMPANY_CODE_MED_OMAN:
                            dataSource = (DataSource) envContext.lookup("jdbc/crm_med_oman");
                            break;
                    }
                } else if (ApplicationConstants.APP_TYPE_GI.equals(appType)){
                        switch (companyCode) {
                        case ApplicationConstants.COMPANY_CODE_CORP_DOHA:
                            dataSource = (DataSource) envContext.lookup("jdbc/dohagi");
                            break;
                        }
                }
            }
            if (dataSource != null) {
                con = dataSource.getConnection();
            }
        } catch (Exception e) {
            logger.error("Error while creating DB instance ", e);
        }
        return con;
    }

    protected void closeDBComp(PreparedStatement prs, ResultSet rs, Connection conn) {
        try {
            if (prs != null) {
                prs.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("{}", e);
        }
    }

    protected void closeDBCallSt(CallableStatement cs, Connection conn) {
        try {
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("{}", e);
        }
    }

    private String buildLogQuery(Object[] params) {
        String res = "Query Params ==> ";
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                res = res + "{}:";
            }
        }
        return res;
    }

    public void removeElement(Object[] a, int del) {
        System.arraycopy(a, del + 1, a, del, a.length - 1 - del);
    }

    protected List executeList(Connection conn, String sql, Object[] params, Class clazz) throws Exception {
        QueryRunner run = new QueryRunner();
        // Use the BeanListHandler implementation to convert all ResultSet rows into a List of Person JavaBeans.
        ResultSetHandler<List> h = new BeanListHandler(clazz);
        // Execute the SQL statement and return the results in a List of Class objects generated by the BeanListHandler.
        List list = run.query(conn, sql, h, params);
        return list;
    }

    protected Object executeQuery(Connection conn, String sql, Object[] params, Class clazz) throws Exception {
        logger.info("Query :: {}", sql);
        logger.info(buildLogQuery(params), params);
        QueryRunner run = new QueryRunner();

        // Use the BeanListHandler implementation to convert all ResultSet rows into a List of Person JavaBeans.
        ResultSetHandler<List> h = new BeanListHandler(clazz);
        // Execute the SQL statement and return the results in a List of Class objects generated by the BeanListHandler.
        List list = run.query(conn, sql, h, params);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    protected int executeUpdate(Connection conn, String sql, Object[] params) throws Exception {
        logger.info("Query :: {}", sql);
        logger.info(buildLogQuery(params), params);
        QueryRunner run = new QueryRunner();
        // Execute the SQL statement and return the affected rows.
        return run.update(conn, sql, params);
    }

    protected void copyResultToBean(final Object bean, final ResultSet rs) throws Exception {
        BeanUtilsBean converter = BeanUtilsBean.getInstance();
        converter.getConvertUtils().register(false, false, 0);
        ResultSetMetaData rsmd = rs.getMetaData();
        if (rs.next()) {
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                String columnName = rsmd.getColumnName(i + 1);
                if (Types.DATE == rsmd.getColumnType(i + 1)) {
                    converter.setProperty(bean, columnName, rs.getDate(columnName));
                } else if (Types.TIMESTAMP == rsmd.getColumnType(i + 1)) {
                    converter.setProperty(bean, columnName, rs.getTimestamp(columnName));
                } else {
                    converter.setProperty(bean, columnName, rs.getString(columnName));
                }
            }
        }
    }

    protected void closeDbConn(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("{}", e);
        }
    }
}
