/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.anoud.reports;

import java.util.Date;
import java.util.List;

/**
 *
 * @author palani.rajan
 */
public class ReportsBeanVO {
    private String reportView;
    private String reportTitle;
    private String reportTable;
    private String reportViewDesc;
    private String reportId;
    private String reportName;
    private String reportRemarks;
    private String reportSec;
    private String reportStatus;
    private List<String> reportUsers;
    private List<String> reportRoles;
    private List<String> repColumns;
    private List<String> repDisplayColumns;
    private List<ReportsFilterVO> repFilter;
    private List<ReportsFilterVO> repSort;
    private List<String> repGroup;
    private String repCrUid;
    private String repCrUname;
    private Date repCrDt;

    private String xlRepColumns;
    private String xlRepDatas;
    private String searchValue;
    private String pluginFor;//For email campaign
    private String reportType; //FOr Marketing Campaign

    public String getReportView() {
        return reportView;
    }

    public void setReportView(String reportView) {
        this.reportView = reportView;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportTable() {
        return reportTable;
    }

    public void setReportTable(String reportTable) {
        this.reportTable = reportTable;
    }

    public String getReportViewDesc() {
        return reportViewDesc;
    }

    public void setReportViewDesc(String reportViewDesc) {
        this.reportViewDesc = reportViewDesc;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportRemarks() {
        return reportRemarks;
    }

    public void setReportRemarks(String reportRemarks) {
        this.reportRemarks = reportRemarks;
    }

    public String getReportSec() {
        return reportSec;
    }

    public void setReportSec(String reportSec) {
        this.reportSec = reportSec;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public List<String> getReportUsers() {
        return reportUsers;
    }

    public void setReportUsers(List<String> reportUsers) {
        this.reportUsers = reportUsers;
    }

    public List<String> getReportRoles() {
        return reportRoles;
    }

    public void setReportRoles(List<String> reportRoles) {
        this.reportRoles = reportRoles;
    }

    public List<String> getRepColumns() {
        return repColumns;
    }

    public void setRepColumns(List<String> repColumns) {
        this.repColumns = repColumns;
    }

    public List<String> getRepDisplayColumns() {
        return repDisplayColumns;
    }

    public void setRepDisplayColumns(List<String> repDisplayColumns) {
        this.repDisplayColumns = repDisplayColumns;
    }

    public List<ReportsFilterVO> getRepFilter() {
        return repFilter;
    }

    public void setRepFilter(List<ReportsFilterVO> repFilter) {
        this.repFilter = repFilter;
    }

    public List<ReportsFilterVO> getRepSort() {
        return repSort;
    }

    public void setRepSort(List<ReportsFilterVO> repSort) {
        this.repSort = repSort;
    }

    public List<String> getRepGroup() {
        return repGroup;
    }

    public void setRepGroup(List<String> repGroup) {
        this.repGroup = repGroup;
    }

    public String getRepCrUid() {
        return repCrUid;
    }

    public void setRepCrUid(String repCrUid) {
        this.repCrUid = repCrUid;
    }

    public String getRepCrUname() {
        return repCrUname;
    }

    public void setRepCrUname(String repCrUname) {
        this.repCrUname = repCrUname;
    }

    public Date getRepCrDt() {
        return repCrDt;
    }

    public void setRepCrDt(Date repCrDt) {
        this.repCrDt = repCrDt;
    }

    public String getXlRepColumns() {
        return xlRepColumns;
    }

    public void setXlRepColumns(String xlRepColumns) {
        this.xlRepColumns = xlRepColumns;
    }

    public String getXlRepDatas() {
        return xlRepDatas;
    }

    public void setXlRepDatas(String xlRepDatas) {
        this.xlRepDatas = xlRepDatas;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getPluginFor() {
        return pluginFor;
    }

    public void setPluginFor(String pluginFor) {
        this.pluginFor = pluginFor;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

}
