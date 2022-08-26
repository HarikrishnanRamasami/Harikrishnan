/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;
import qa.com.qic.utility.helpers.Validatory;

/**
 *
 * @author palani.rajan
 */
public class DynamicReportsAction extends ActionSupport implements ServletRequestAware, Preparable, SessionAware, ServletResponseAware, ModelDriven<ReportsBeanVO> {

    private static final Logger logger = LogUtil.getLogger(DynamicReportsAction.class);

    private static final String dateRegex = "^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$";
    private final static String AGGREGATE_FUNCTIONS[] = new String[]{"MIN", "MAX", "SUM", "COUNT", "AVG"};

    ReportsBeanVO bean = new ReportsBeanVO();
    DynamicReportsService service;
    HttpServletResponse response;
    private List<ReportsBeanVO> userReportsList;
    private List<KeyValue> reportsViewList;
    private Map<String, String> condTypes;
    private Map<String, String> dtFilterTypes;
    private List<KeyValue> filterColumns;
    private List<KeyValue> actualColumns;
    private Map<String, String> usersList;
    private Map<String, String> rolesList;
    private Map<String, String> usersAllList;
    private Map<String, String> rolesAllList;
    private InputStream excelStream;
    private List<KeyValue> list;
    private transient UserInfo userInfo;

    public HttpServletRequest request = null;
    public Map<String, Object> session = null;
    public String param1;
    public String param2;
    public String param3;
    public String jsonString;
    private transient String company;

    @Override
    public void prepare() throws Exception {
        service = new DynamicReportsService(getUserInfo().getCompanyCode());
    }

    @Override
    public ReportsBeanVO getModel() {
        return bean;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public List<ReportsBeanVO> getUserReportsList() {
        return userReportsList;
    }

    public List<KeyValue> getReportsViewList() {
        reportsViewList = service.loadReportViews(getParam1());
        return reportsViewList;
    }

    public Map<String, String> getCondTypes() {
        condTypes = new LinkedHashMap<>();
        condTypes.put("eq", "Equals");
        condTypes.put("nq", "Not Equals");
        condTypes.put("gt", "Greater than");
        condTypes.put("lt", "Less than");
        condTypes.put("btw", "Between");
        condTypes.put("nbtw", "Not Between");
        condTypes.put("in", "Contains");
        condTypes.put("nin", "Not Contains");
        return condTypes;
    }

    public Map<String, String> getDtFilterTypes() {
        dtFilterTypes = new LinkedHashMap<>();
        dtFilterTypes.put("td", "Today");
        dtFilterTypes.put("yd", "Yesterday");
        dtFilterTypes.put("tw", "This Week");
        dtFilterTypes.put("lw", "Last Week");
        dtFilterTypes.put("tm", "This Month");
        dtFilterTypes.put("lm", "Last Month");
        dtFilterTypes.put("tq", "This Quarter");
        dtFilterTypes.put("lq", "Last Quarter");
        dtFilterTypes.put("ty", "This Year");
        dtFilterTypes.put("ly", "Last Year");
        return dtFilterTypes;
    }

    public List<KeyValue> getFilterColumns() {
        return filterColumns;
    }

    public void setFilterColumns(List<KeyValue> filterColumns) {
        this.filterColumns = filterColumns;
    }

    public List<KeyValue> getActualColumns() {
        return actualColumns;
    }

    public void setActualColumns(List<KeyValue> actualColumns) {
        this.actualColumns = actualColumns;
    }

    public Map<String, String> getUsersList() {
        return usersList;
    }

    public void setUsersList(Map<String, String> usersList) {
        this.usersList = usersList;
    }

    public Map<String, String> getRolesList() {
        return rolesList;
    }

    public void setRolesList(Map<String, String> rolesList) {
        this.rolesList = rolesList;
    }

    public Map<String, String> getUsersAllList() {
        return usersAllList;
    }

    public void setUsersAllList(Map<String, String> usersAllList) {
        this.usersAllList = usersAllList;
    }

    public Map<String, String> getRolesAllList() {
        return rolesAllList;
    }

    public void setRolesAllList(Map<String, String> rolesAllList) {
        this.rolesAllList = rolesAllList;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public List<KeyValue> getList() {
        return list;
    }

    public void setList(List<KeyValue> list) {
        this.list = list;
    }

    public String loadDynamicReports() {
        logger.debug("DynamicReportAction - Home Entry");
        getUserInfo().setActiveMenu(TypeConstants.Menu.REPORTS);
        userReportsList = service.loadUserReportsList(getUserInfo().getUserId());
        logger.debug("DynamicReportAction - Home Exit");
        return INPUT;
    }

    public String runSavedReport() {
        logger.debug("DynamicReportAction - Stored Report Generation Entry");
        bean.setRepColumns(service.loadStoredReportColumns(bean));
        setupDisplayColumns();
        if (bean.getRepFilter() == null) { // If not run with param
            bean.setRepFilter(service.loadStoredReportFilters(bean.getReportId()));
        }
        bean.setRepSort(service.loadStoredReportSorts(bean.getReportId()));
        bean.setRepGroup(service.loadStoredReportGroups(bean));
        List<Map<String, Map<String, String>>> list = service.loadReportData(bean);
        request.setAttribute("reportColumnsSelected", bean.getRepDisplayColumns());

        Gson gson = new Gson();
        bean.setXlRepColumns(gson.toJson(bean.getRepDisplayColumns()));
        bean.setXlRepDatas(gson.toJson(list));
        if (list.size() > 0 && list.get(0).containsKey("Error")) {
            addActionError(list.get(0).toString());
        }else{
              request.setAttribute("reportDataList", list);
        }
        logger.debug("DynamicReportAction - Stored Report Generation Exit");
        return INPUT;
    }

    public String loadReportParams() {
        bean.setRepFilter(service.loadStoredReportFilters(bean.getReportId()));
        filterColumns = service.loadReportFilterColumns(bean.getReportTable());
        actualColumns = service.loadReportColumnsForTab(bean.getReportTable());
        return INPUT;
    }

    public String saveReportParams() {
        String status;
        status = service.saveReportParams(bean, getUserInfo().getUserId());
        if (status != null) {
            bean.setReportStatus(status);
            return INPUT;
        }
        return SUCCESS;
    }

    public String loadColumnsAndFilters() {
        logger.debug("DynamicReportAction - Table Colums List Entry");
        if (Validatory.isValidString(bean.getReportId())) {
            bean.setRepColumns(service.loadStoredReportColumns(bean));
            bean.setRepFilter(service.loadStoredReportFilters(bean.getReportId()));
            bean.setRepSort(service.loadStoredReportSorts(bean.getReportId()));
            bean.setRepGroup(service.loadStoredReportGroups(bean));
        } else {
            bean.setReportUsers(new LinkedList<String>());
            bean.getReportUsers().add(getUserInfo().getUserId());
            bean.setReportRoles(new LinkedList<String>());
            bean.getReportRoles().add(getUserInfo().getUserTeam());
            bean.setRepColumns(new LinkedList<String>());
            bean.setRepFilter(new LinkedList<ReportsFilterVO>());
            bean.setRepSort(new LinkedList<ReportsFilterVO>());
            bean.setRepGroup(new LinkedList<String>());
        }
        actualColumns = service.loadReportColumnsForTab(bean.getReportTable());
        actualColumns.removeAll(bean.getRepColumns());
        filterColumns = service.loadReportFilterColumns(bean.getReportTable());
        usersList = service.loadAllUsersList();
        usersAllList = new HashMap<>();
        rolesAllList = new HashMap<>();
        List<String> l = bean.getReportUsers();
        for (String s : l) {
            usersAllList.put(s, usersList.get(s));
            usersList.remove(s);
        }
        rolesList = service.loadAllRolesList();
        l = bean.getReportRoles();
        for (String s : l) {
            rolesAllList.put(s, rolesList.get(s));
            rolesList.remove(s);
        }
        logger.debug("DynamicReportAction - Table Colums List Exit");
        return INPUT;
    }

    public String saveReportEnquiry() {
        logger.debug("DynamicReportAction - Save Report Enquiry Entry");
        String status;
        if (!Validatory.isValidString(bean.getReportName())) {
            status = "Enter Report Name";
        } else if (bean.getRepColumns() == null || bean.getRepColumns().size() <= 0) {
            status = "Select Columns";
        } else {
            status = service.saveReportEnquiry(bean, getUserInfo().getUserId());
        }
        bean.setReportStatus(status);
        logger.debug("DynamicReportAction - Save Report Enquiry Exit");
        return INPUT;
    }

    private void setupDisplayColumns() {
        logger.debug("DynamicReportAction - Setup display columns");
        List<String> list = new ArrayList<>(bean.getRepColumns());
        bean.setRepDisplayColumns(list);
        if (bean.getRepDisplayColumns() != null && !bean.getRepDisplayColumns().isEmpty()) {
            int index = 0;
            for (String col : bean.getRepDisplayColumns()) {
                for (String s : AGGREGATE_FUNCTIONS) {
                    if (col.startsWith(s)) {
                        s = col.substring(col.indexOf("(") + 1, col.indexOf(")"));
                        bean.getRepDisplayColumns().set(index, s);
                        break;
                    }
                }
            }

        }
    }

    public String runDynamicReport() {
        logger.debug("DynamicReportAction - Build Report Generation Entry");
        setupDisplayColumns();
        List<Map<String, Map<String, String>>> list = service.loadReportData(bean);
        // "email" - Email campaign, "lead" - Lead uploaad
        if ("email".equals(bean.getPluginFor()) || "lead".equals(bean.getPluginFor())) {
            setParam1("S");
            return bean.getPluginFor();
        } else {
            request.setAttribute("reportColumnsSelected", bean.getRepDisplayColumns());
            Gson gson = new Gson();
            bean.setXlRepColumns(gson.toJson(bean.getRepDisplayColumns()));
            bean.setXlRepDatas(gson.toJson(list));
            logger.debug("DynamicReportAction - Build Report Generation Exit");
            if (list.size() > 0 && list.get(0).containsKey("Error")) {
                addActionError(list.get(0).toString());
            } else {
                request.setAttribute("reportDataList", list);
            }
            return INPUT;
        }
    }

    public String generateExcelReport() {
        logger.debug("DynamicReportAction - Export Excel Report Generation Entry");
        try {
            Gson gson = new Gson();
            bean.setRepColumns(gson.fromJson(bean.getXlRepColumns(), List.class));
            List<Map<String, Map>> list = (gson.fromJson(bean.getXlRepDatas(), List.class));
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(bean.getReportTitle());
            int rows = 1, cols = 0;

            // Sheet Header
            XSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) 18);
            font.setColor(new XSSFColor(new java.awt.Color(23, 79, 153)));
            XSSFCellStyle headerStyle = wb.createCellStyle();
            headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
            headerStyle.setFont(font);
            XSSFRow rowHead = sheet.createRow((short) rows++);
            XSSFCell header = rowHead.createCell((short) cols);
            header.setCellValue(bean.getReportTitle());
            header.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, bean.getRepColumns().size() - 1));

            // Table Header
            XSSFCellStyle headerBg = wb.createCellStyle();
            headerBg.setFillForegroundColor(new XSSFColor(new java.awt.Color(70, 176, 199)));
            headerBg.setFillPattern(CellStyle.SOLID_FOREGROUND);
            headerBg.setBorderBottom(CellStyle.BORDER_THIN);
            headerBg.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            headerBg.setBorderLeft(CellStyle.BORDER_THIN);
            headerBg.setLeftBorderColor(IndexedColors.GREEN.getIndex());
            headerBg.setBorderRight(CellStyle.BORDER_THIN);
            headerBg.setRightBorderColor(IndexedColors.BLUE.getIndex());
            headerBg.setBorderTop(CellStyle.BORDER_THIN);
            headerBg.setTopBorderColor(IndexedColors.BLACK.getIndex());
            XSSFFont font1 = wb.createFont();
            font1.setFontHeightInPoints((short) 11);
            font1.setColor(IndexedColors.WHITE.getIndex());
            headerBg.setFont(font1);

            XSSFRow rowhead = sheet.createRow((short) ++rows);
            for (String column : bean.getRepColumns()) {
                XSSFCell cell = rowhead.createCell(cols++);
                cell.setCellValue(column);
                cell.setCellStyle(headerBg);
            }
            //Number style
            CellStyle numberStyle = wb.createCellStyle();
            numberStyle.setAlignment(CellStyle.ALIGN_RIGHT);
            numberStyle.setBorderBottom(CellStyle.BORDER_THIN);
            numberStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            numberStyle.setBorderLeft(CellStyle.BORDER_THIN);
            numberStyle.setLeftBorderColor(IndexedColors.GREEN.getIndex());
            numberStyle.setBorderRight(CellStyle.BORDER_THIN);
            numberStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
            numberStyle.setBorderTop(CellStyle.BORDER_THIN);
            numberStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            DataFormat format = wb.createDataFormat();
            //String precision = "#,##0." + (String) session.get("PrecisionCount");
            String precision = "###,###,###,##0." + (String) session.get("PrecisionCount");
            numberStyle.setDataFormat(format.getFormat(precision));

            //Date Style
            CellStyle dateStyle = wb.createCellStyle();
            dateStyle.setAlignment(CellStyle.ALIGN_CENTER);
            dateStyle.setBorderBottom(CellStyle.BORDER_THIN);
            dateStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            dateStyle.setBorderLeft(CellStyle.BORDER_THIN);
            dateStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
            dateStyle.setBorderRight(CellStyle.BORDER_THIN);
            dateStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
            dateStyle.setBorderTop(CellStyle.BORDER_THIN);
            dateStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            DataFormat dateFormat = wb.createDataFormat();
            dateStyle.setDataFormat(dateFormat.getFormat("dd/MMM/yyyy"));
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            // Table Border
            CellStyle tblBorder = wb.createCellStyle();
            tblBorder.setBorderBottom(CellStyle.BORDER_THIN);
            tblBorder.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            tblBorder.setBorderLeft(CellStyle.BORDER_THIN);
            tblBorder.setLeftBorderColor(IndexedColors.GREEN.getIndex());
            tblBorder.setBorderRight(CellStyle.BORDER_THIN);
            tblBorder.setRightBorderColor(IndexedColors.BLUE.getIndex());
            tblBorder.setBorderTop(CellStyle.BORDER_THIN);
            tblBorder.setTopBorderColor(IndexedColors.BLACK.getIndex());

            for (Map<String, Map> map : list) {
                cols = 0;
                XSSFRow row = sheet.createRow((short) ++rows);
                for (String column : bean.getRepColumns()) {
                    XSSFCell cell = row.createCell(cols++);
                    Map<String, String> keyValue = map.get(column);
                    for (Map.Entry<String, String> entryVal : keyValue.entrySet()) {
                        Integer colType = Integer.parseInt(entryVal.getValue());
                        if (colType == Types.NUMERIC || colType == Types.DECIMAL) {
                            cell.setCellStyle(numberStyle);
                            if(entryVal.getKey() != null && !entryVal.getKey().isEmpty()) {
                                cell.setCellValue(new Double(entryVal.getKey()));
                            }
                        } else {
                            if (entryVal.getKey().matches(dateRegex)) {
                                try {
                                    cell.setCellValue(df.parse(entryVal.getKey()));
                                    cell.setCellStyle(dateStyle);
                                } catch (Exception dex) {
                                    logger.error("Error while converting to date object", dex);
                                    cell.setCellValue(entryVal.getKey());
                                    cell.setCellStyle(tblBorder);
                                }
                            } else {
                                cell.setCellStyle(tblBorder);
                                cell.setCellValue(entryVal.getKey());
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < bean.getRepColumns().size(); i++) {
                sheet.autoSizeColumn(i);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            wb.write(baos);
            excelStream = new ByteArrayInputStream(baos.toByteArray());
            baos.flush();
            baos.close();
            response.setHeader("Set-Cookie", "fileDownload=true; path=/");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        } catch (Exception e) {
            logger.error("Exception in Export Excel => {}", e);
        }
        logger.debug("DynamicReportAction - Export Excel Report Generation Exit");
        return SUCCESS;
    }

    public String inputTypeChange() {
        filterColumns = service.inputTypeChange(bean.getReportViewDesc());
        //String key, value;
        /*for(KeyValue ky:filterColumns){
         key=ky.getKey();
         value=ky.getValue();
         if("1".equalsIgnoreCase(key)){
         list=service.listQuery(value);
         }
         }*/
        // 0
        // Query to get the drop down values

        return SUCCESS;
    }

    public String listAcntData() {
        list = service.listQuery(bean.getReportSec(), bean.getSearchValue());
        return SUCCESS;
    }

    public String downloadSavedReport() {
        logger.debug("DynamicReportAction - Download Stored Report Generation Entry");
        bean.setRepColumns(service.loadStoredReportColumns(bean));
        setupDisplayColumns();
        bean.setRepFilter(service.loadStoredReportFilters(bean.getReportId()));
        bean.setRepSort(service.loadStoredReportSorts(bean.getReportId()));
        bean.setRepGroup(service.loadStoredReportGroups(bean));
        List<Map<String, Map<String, String>>> list = service.loadReportData(bean);

        request.setAttribute("reportColumnsSelected", bean.getRepColumns());
        request.setAttribute("reportDataList", list);
        Gson gson = new Gson();
        bean.setXlRepColumns(gson.toJson(bean.getRepColumns()));
        bean.setXlRepDatas(gson.toJson(list));
        //logger.info("gson = " + bean.getXlRepDatas());
        generateExcelReport();
        logger.debug("DynamicReportAction - Download Stored Report Generation Exit");
        return SUCCESS;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
     */
    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
        setCompany((String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE));
        this.userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
    }

    /**
     * Gets the param1.
     *
     * @return the param1
     */
    public String getParam1() {
        return param1;
    }

    /**
     * Sets the param1.
     *
     * @param param1 the new param1
     */
    public void setParam1(String param1) {
        this.param1 = param1;
    }

    /**
     * Gets the param2.
     *
     * @return the param2
     */
    public String getParam2() {
        return param2;
    }

    /**
     * Sets the param2.
     *
     * @param param2 the new param2
     */
    public void setParam2(String param2) {
        this.param2 = param2;
    }

    /**
     * Gets the json string.
     *
     * @return the json string
     */
    public String getJsonString() {
        return jsonString;
    }

    /**
     * Sets the json string.
     *
     * @param jsonString the new json string
     */
    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    }
