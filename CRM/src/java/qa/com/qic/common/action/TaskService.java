/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.vo.CrmTasks;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author sutharsan.g
 */
public class TaskService extends ActionSupport implements SessionAware, ServletRequestAware {

    private static final Logger LOGGER = LogUtil.getLogger(TaskAction.class);
    private transient HttpServletRequest request;
    private transient Map<String, Object> session;
    private transient AnoudDAO anoudDao;
    private transient CommonDAO commonDao;

    private transient UserInfo userInfo;

    /**
     * messageType - S: Success, E: Error message - details description about
     * success/error
     */
    private String message;
    private String messageType;

    /**
     * operation - add, edit, view, delete
     */
    /**
     * Flexible fields
     */
    private String company;

    private File attachment;

    private transient Activity activity;
    private transient List<KeyValue> moduleList;
    private transient List<KeyValue> priorityList;
    private transient List<KeyValue> categoryList;
    private transient List<KeyValue> subCategoryList;
    private transient List<KeyValue> userList;
    private transient List<KeyValue> statusList;
    private transient List<KeyValue> assignToList;
    private transient List<KeyValue> statusRemarksList;
    private InputStream fileInputStream;
    private String filePath;
    private File file;

    private final String companyCode;

    public String getCompanyCode() {
        return companyCode;
    }

    public TaskService(String companyCode) {
        this.companyCode = companyCode;
        anoudDao = new AnoudDAO(companyCode);
        commonDao = new CommonDAO(companyCode);
    }

    public List uploadedDataConfirm(final File csvFile, CrmTasks tasks) {
        LOGGER.debug("*** TaskAction -- uploadedDataConfirm() -- Enter ***");
        List<CrmTasks> FinalList = new ArrayList<>();
        CrmTasks crmTasksg = null;
        Workbook workBook = null;
        FileInputStream fileInputStream = null;
        this.setMessageType("OK");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        moduleList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CRM_MODULE);
        priorityList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CRM_PRIORITY);
        //commented for corporate
        /*AnoudDAO ad = new AnoudDAO(tasks.getCtCrmId());
        categoryList = ad.getMAppCodes(FieldConstants.AppCodes.CRM_CATEGORY);
        subCategoryList = ad.getMAppCodes(FieldConstants.AppCodes.CRM_SUB_CATEGORY);*/
        categoryList = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_CATEGORY);
        subCategoryList = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_SUB_CATEGORY);
        assignToList = commonDao.loadCrmAgentList(null);
        if (csvFile.exists() && csvFile.isFile()) {
            try {
                LOGGER.info("Uploading Started");
                int rowCount = 0;
                boolean errorStatus = false;
                ArrayList cellDataList = new ArrayList();
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
                boolean invalidCol = false;
                for (int i = 0; i < cellDataList.size(); i++) {
                    StringBuilder errorMsg = new StringBuilder("");
                    if (i != 0) {
                        //sql = "INSERT INTO T_CRM_LEADS (CL_ID, CL_REF_ID, CL_NAME, CL_MOBILE_NO, CL_EMAIL_ID, CL_WORK_PLACE, CL_ASSIGNED_TO, CL_STATUS, CL_CR_UID, CL_CR_DT) VALUES (S_CL_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
                        List<Object> cellTempList = (ArrayList) cellDataList.get(i);
                        crmTasksg = new CrmTasks();
                        String colValue = null;
                        try {
                            int cellCount = 0;
                            String[] cellValues = new String[cellTempList.size()];
                            for (int j = 0; j < cellTempList.size(); j++) {
                                Cell hssfCell = (Cell) cellTempList.get(j);

                                if (j == 8) {
                                    hssfCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                                    Date dateCellValue = hssfCell.getDateCellValue();
                                    if (dateCellValue != null) {
                                        colValue = sdf.format(dateCellValue);
                                    } else {
                                        colValue = "";
                                    }

                                } else {
                                    hssfCell.setCellType(Cell.CELL_TYPE_STRING);
                                    String stringCellValue = hssfCell.getStringCellValue();
                                    colValue = stringCellValue == null ? "" : stringCellValue;
                                }
                                if ("".equalsIgnoreCase(colValue.trim())) {
                                    cellCount++;
                                }
                                cellValues[j] = colValue;
                            }
                            if (cellCount == cellTempList.size()) {
                                break;
                            }
                            try {
                                crmTasksg.setCtModuleDesc(validateCTModule(cellValues[0]));
                                crmTasksg.setCtModule(cellValues[0]);
                            } catch (IllegalArgumentException il) {
                                crmTasksg.setCtModule(il.getMessage());
                                errorMsg.append(il.getMessage());
                            }
                            try {
                                crmTasksg.setCtPriorityDesc(validateCTPriority(cellValues[1]));
                                crmTasksg.setCtPriority(cellValues[1]);
                            } catch (IllegalArgumentException il) {
                                crmTasksg.setCtModule(il.getMessage());
                                errorMsg.append(il.getMessage());
                            }
                            try {
                                crmTasksg.setCtCatgDesc(validateCTCategory(cellValues[2]));
                                crmTasksg.setCtCatg(cellValues[2]);
                            } catch (IllegalArgumentException il) {
                                crmTasksg.setCtModule(il.getMessage());
                                errorMsg.append(il.getMessage());
                            }
                            try {
                                crmTasksg.setCtSubCatgDesc(validateCTSubCategory(cellValues[3], cellValues[2]));
                                crmTasksg.setCtSubCatg(cellValues[3]);
                            } catch (IllegalArgumentException il) {
                                crmTasksg.setCtModule(il.getMessage());
                                errorMsg.append(il.getMessage());
                            }
                            crmTasksg.setCtRefId(cellValues[4]);
                            crmTasksg.setCtRefNo(cellValues[5]);
                            crmTasksg.setCtSubject(cellValues[6]);
                            crmTasksg.setCtMessage(cellValues[7]);
                            crmTasksg.setCtDueDate(cellValues[8]);
                            try{
                            crmTasksg.setCtFlex01(validateMobile(cellValues[9]));
                            crmTasksg.setCtFlex02(cellValues[9]);
                            } catch(IllegalArgumentException il) {
                                crmTasksg.setCtFlex01(il.getMessage());
                                errorMsg.append(il.getMessage());
                            }
                            // crmTasksg.setCtRemindBefore(Integer.parseInt(cellValues[12]));
                            try {
                                crmTasksg.setCtAssignedToDesc(validateAssignedTo(cellValues[10]));
                                crmTasksg.setCtAssignedTo(cellValues[10]);
                            } catch (IllegalArgumentException il) {
                                crmTasksg.setCtModule(il.getMessage());
                                errorMsg.append(il.getMessage());
                            }
                            // crmTasksg.setCtAssignedToDesc(cellValues[9]);
                            crmTasksg.setCtStatus("P");

                            if (errorMsg.length() == 0) {
                                crmTasksg.setCtFlex02("OK");
                                this.setMessage("OK");
                            } else {
                                crmTasksg.setCtFlex02(errorMsg.toString());
                                invalidCol = true;
                            }

                            if (cellCount == cellTempList.size()) {
                                emptyRowCount++;
                            } else {
                                FinalList.add(crmTasksg);
                            }
                            if (emptyRowCount > 1) {
                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            LOGGER.info("Query" + rowCount + "==> Not Inserted. Because " + e.getMessage());
                            throw e;
                        }
                    }
                }
                if (invalidCol) {
                    this.setMessageType("Error");
                }

            } catch (Exception e) {
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
            }
        }
        LOGGER.debug("*** TaskAction -- uploadedDataConfirm() -- Exit ***");
        return FinalList;

    }

    private String validateCTModule(String ctModule) {
        String desc = null;
        for (KeyValue k : moduleList) {
            if (k.getKey().equals(ctModule)) {
                desc = k.getValue();
                break;
            }
        }
        if (desc == null) {
            throw new IllegalArgumentException("Error");
        }

        return desc;
    }

    private String validateCTPriority(String ctPriority) {
        String desc = null;
        for (KeyValue k : priorityList) {
            if (k.getKey().equals(ctPriority)) {
                desc = k.getValue();
                break;
            }
        }
        if (desc == null) {
            throw new IllegalArgumentException("Error");
        }
        return desc;
    }

    private String validateCTCategory(String ctCategory) {
        String desc = null;
        for (KeyValue k : categoryList) {
            if (k.getKey().equals(ctCategory)) {
                desc = k.getValue();
                break;
            }
        }
        if (desc == null) {
            throw new IllegalArgumentException("Error");
        }
        return desc;
    }

    private String validateCTSubCategory(String subCatg, String catg) {
        String desc = null;
        for (KeyValue k : subCategoryList) {
            if (k.getKey().equals(subCatg) && k.getInfo4().equals(catg)) {
                desc = k.getValue();
                break;
            }
        }
        if (desc == null) {
            throw new IllegalArgumentException("Error");
        }
        return desc;

    }

    private String validateAssignedTo(String ctAssignedTo) {
        String desc = null;
        for (KeyValue k : assignToList) {
            if (k.getKey().equals(ctAssignedTo)) {
                desc = k.getValue();
                break;
            }
        }
        if (desc == null) {
            throw new IllegalArgumentException("Error");
        }
        return desc;
    }

    public String validateMobile(String ctFlex01) {
        String mobile = null;
        if (ctFlex01.matches("^[0-9]*$") || ctFlex01=="") {
           mobile = ctFlex01;
        }
        if(mobile == null){
           throw new IllegalArgumentException("Error");
        }
        return mobile;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<KeyValue> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<KeyValue> moduleList) {
        this.moduleList = moduleList;
    }

    public List<KeyValue> getPriorityList() {
        return priorityList;
    }

    public void setPriorityList(List<KeyValue> priorityList) {
        this.priorityList = priorityList;
    }

    public List<KeyValue> getAssignToList() {
        return assignToList;
    }

    public void setAssignToList(List<KeyValue> assignToList) {
        this.assignToList = assignToList;
    }

    public List<KeyValue> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<KeyValue> categoryList) {
        this.categoryList = categoryList;
    }

    public List<KeyValue> getUserList() {
        return userList;
    }

    public void setUserList(List<KeyValue> userList) {
        this.userList = userList;
    }

    public List<KeyValue> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<KeyValue> statusList) {
        this.statusList = statusList;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
        setCompany((String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE));
        this.userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<KeyValue> getStatusRemarksList() {
        return statusRemarksList;
    }

    public void setStatusRemarksList(List<KeyValue> statusRemarksList) {
        this.statusRemarksList = statusRemarksList;
    }

    public List<KeyValue> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<KeyValue> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public File getAttachment() {
        return attachment;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
