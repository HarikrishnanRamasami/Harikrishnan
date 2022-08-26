/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.reports;

import java.util.List;
import java.util.Map;

import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.utility.helpers.Validatory;

/**
 *
 * @author palani.rajan
 */
public class DynamicReportsService {

    DynamicReportsDAO dao;
    private String dataSource;

    public DynamicReportsService(String dataSource) {
        this.dataSource = dataSource;
        dao = new DynamicReportsDAO(dataSource);
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<ReportsBeanVO> loadUserReportsList(final String userId) {
        return dao.loadUserReportsList(userId);
    }

    public Map<String, String> loadAllUsersList() {
        return dao.loadAllUsersList();
    }

    public Map<String, String> loadAllRolesList() {
        return dao.loadAllRolesList();
    }

    public List<KeyValue> loadReportViews(final String acMcDfCode) {
        return dao.loadReportViews(acMcDfCode);
    }

    public List<KeyValue> loadReportColumnsForTab(final String tableName) {
        return dao.loadReportColumnsForTab(tableName);
    }

    public List<KeyValue> loadReportFilterColumns(final String tableName) {
        return dao.loadReportFilterColumns(tableName);
    }

    public String saveReportEnquiry(final ReportsBeanVO bean, final String userId) {
        String result;
        if (Validatory.isValidString(bean.getReportId())) {
            result = dao.updateReportEnquiry(bean, userId);
        } else {
            result = dao.insertReportEnquiry(bean, userId);
        }
        return result;
    }

    public String saveReportParams(final ReportsBeanVO bean, final String userId) {
        String result;
        result = dao.saveReportParams(bean, userId);
        return result;
    }

    public List<String> loadStoredReportColumns(final ReportsBeanVO bean) {
        return dao.loadStoredReportColumns(bean);
    }

    public List<ReportsFilterVO> loadStoredReportFilters(final String reportId) {
        return dao.loadStoredReportParameters("F", reportId);
    }

    public List<ReportsFilterVO> loadStoredReportSorts(final String reportId) {
        return dao.loadStoredReportParameters("O", reportId);
    }

    public List<String> loadStoredReportGroups(final ReportsBeanVO bean) {
        return dao.loadStoredReportParameterGroups("G", bean);
    }

    public List<Map<String, Map<String, String>>> loadReportData(final ReportsBeanVO bean) {
        return dao.loadReportData(bean);
    }

    public List<KeyValue> inputTypeChange(final String inputType) {
        return dao.inputTypeChange(inputType);
    }

    public List<KeyValue> listQuery(final String qry, final String searchValue) {
        return dao.listQuery(qry, searchValue);
    }

}
