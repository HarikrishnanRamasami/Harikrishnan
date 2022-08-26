/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.Customer;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.dao.DashboardDAO;
import qa.com.qic.common.dao.LeadDAO;
import qa.com.qic.common.dao.TaskDAO;
import qa.com.qic.common.dms.DmsDAO;
import qa.com.qic.common.dms.NewGenDmsHelper;
import qa.com.qic.common.dms.TDocInfoBean;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.CrmCallLog;
import qa.com.qic.common.vo.CrmTasks;
import qa.com.qic.common.vo.CrmTasksLog;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class TaskAction extends ActionSupport implements SessionAware, ServletRequestAware {

    private static final Logger LOGGER = LogUtil.getLogger(TaskAction.class);
    private transient HttpServletRequest request;
    private transient Map<String, Object> session;
    private transient TaskDAO dao;
    private transient AnoudDAO anoudDao;
    private transient CommonDAO commonDao;
    private transient LeadDAO leadDao;
    private List<? extends Object> aaData;
    private transient DmsDAO dmsdao;

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
    private String operation;

    /**
     * Flexible fields
     */
    private String flex1;
    private String flex2;
    private String flex3;

    private String company;
    private String enableForwardYn = "N";

    private File attachment;

    private CrmTasks task;
    private transient CrmTasksLog tasksLog;
    private transient Activity activity;
    private transient List<KeyValue> moduleList;
    private transient List<KeyValue> priorityList;
    private transient List<KeyValue> categoryList;
    private transient List<KeyValue> subCategoryList;
    private transient List<KeyValue> userList;
    private transient List<KeyValue> statusList;
    private transient List<KeyValue> assignToList;
    private transient List<KeyValue> statusRemarksList;
    private transient List<KeyValue> reasonList;
    private static final TreeMap<Integer, String> hoursRangeList = new TreeMap<Integer, String>() {
        {
            put(0, "0 minutes");
            put(5, "5 minutes");
            put(10, "10 minutes");
            put(15, "15 minutes");
            put(30, "30 minutes");
            put(45, "45 minutes");
            put(60, "1 hour");
            put((2 * 60), "2 hours");
            put((3 * 60), "3 hours");
            put((4 * 60), "4 hours");
            put((5 * 60), "5 hours");
            put((6 * 60), "6 hours");
            put((7 * 60), "7 hours");
            put((8 * 60), "8 hours");
            put((9 * 60), "9 hours");
            put((10 * 60), "10 hours");
            put((11 * 60), "11 hours");
            put(1440, "1 day");
        }
    };

    private transient List<KeyValue> dateRangeList;
    private transient TDocInfoBean docInfoBean;
    private transient List<TDocInfoBean> DmsFileList;
    private transient Customer customer;
    private transient CrmCallLog callLog;
    private InputStream fileInputStream;
    private String filePath;
    private File file;
    private File[] attachments;
    private String[] attachmentsFileName;

    public String openTaskPage() {
        getUserInfo().setActiveMenu(TypeConstants.Menu.DASHBOARD);
        dao = new TaskDAO(getCompany());
        commonDao = new CommonDAO(getCompany());
        anoudDao = new AnoudDAO(getCompany());
        statusList = dao.loadStatusList();
        statusRemarksList = anoudDao.getMAppCodes(FieldConstants.AppCodes.REN_FOLLOWUP);
        priorityList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CRM_PRIORITY);
        // To disable the forward option
        List<KeyValue> kvList = anoudDao.loadMAppConfig(FieldConstants.AppConfig.CRM_TASK_FWD);
        if(null != kvList && !kvList.isEmpty()) {
            String s = kvList.get(0).getKey();
            if("ALL".matches(s) || ("MGR".matches(s) && userInfo.getUserRoleSeq() == 1)
                     || ("SUP".matches(s) && userInfo.getUserRoleSeq() == 2)) {
                enableForwardYn = "Y";
            }
        }
        assignToList = commonDao.loadCrmAgentList(null);
        userList = commonDao.loadCrmAgentList(getUserInfo().getUserId());
        if (null != userList) {
            userList.add(0, new KeyValue("ALL", "All"));
        }
        reasonList = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_HOLD_TYP);
        task = new CrmTasks();
        task.setCtAssignedTo(getUserInfo().getUserId());
        task.setCtStatus("P");
        loadCategoryList();
        tasksLog = new CrmTasksLog();
        tasksLog.setCtlCtId(task.getCtId());
        return "task";
    }

    public String openTaskForm() {
        anoudDao = new AnoudDAO(getCompany());
        commonDao = new CommonDAO(getCompany());
        dao = new TaskDAO(getCompany());
        moduleList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CRM_MODULE);
        priorityList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CRM_PRIORITY);
        categoryList = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_CATEGORY);
        statusRemarksList = anoudDao.getMAppCodes(FieldConstants.AppCodes.REN_FOLLOWUP);
        assignToList = commonDao.loadCrmAgentList(null);
        statusList = dao.loadStatusList();
        if (("edit".equals(getOperation()) || "view".equals(getOperation())) && getTask().getCtId() != null) {
            task = dao.loadTaskData(getTask().getCtId());
            if (task != null && task.getCtCatg() != null && !task.getCtCatg().isEmpty()) {
                aaData = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_SUB_CATEGORY, task.getCtCatg(), FieldConstants.AppCodes.CRM_CATEGORY);
            }
        } else {
            task = new CrmTasks();
            task.setCtRemindBefore(0);
        }
        if ("add".equals(getOperation())) {
            task = new CrmTasks();
            task.setCtAssignedTo((String) session.get(ApplicationConstants.SESSION_NAME_USER_ID));
        }
        if (aaData == null) {
            aaData = new ArrayList<KeyValue>();
        }
        return "task_form";
    }

    public String loadTaskSubCategoryList() {
        anoudDao = new AnoudDAO(getCompany());
        aaData = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_SUB_CATEGORY, task.getCtCatg(), null);
        return "data";
    }

    public String loadCategoryList() {
        dao = new TaskDAO(getCompany());
        try {
            if(task != null && StringUtils.isNoneBlank(task.getCtAssignedTo())) {
                categoryList = dao.loadCategoryList(task.getCtAssignedTo());
            }
        } catch (Exception e) {
        } finally {
            if(categoryList == null) {
                categoryList = new ArrayList<>();
            }
        }
        return "data";
    }

    public String loadSubCategoryList() {
        dao = new TaskDAO(getCompany());
        try {
            if(getTask() != null && StringUtils.isNoneBlank(getTask().getCtCatg())) {
                subCategoryList = dao.loadSubCategoryList(getTask().getCtCatg(), getTask().getCtAssignedTo());
            }
        } catch (Exception e) {
        } finally {
            if(subCategoryList == null) {
                subCategoryList = new ArrayList<>();
            }
        }
        return "data";
    }

    public String saveTaskForm() {
        if (!ApplicationConstants.MESSAGE_TYPE_ERROR.equals(messageType)) {
            anoudDao = new AnoudDAO(getCompany());
            dao = new TaskDAO(getCompany());
            try {
                if ("add".equals(getOperation()) || "edit".equals(getOperation())) {
                    getTask().setCtCrUid(getUserInfo().getUserId());
                    getTask().setCtUpdUid(getUserInfo().getUserId());
                    if ("add".equals(getOperation())) {
                        getTask().setCtStatus("P");
                    }
                    int taskId = dao.saveTask(getTask(), getOperation());
                    saveTaskFiles(taskId);
                    if ("1".equals(getTask().getCtCreateActivityYn())) {
                        activity = new Activity();
                        activity.setCivilId(getTask().getCtRefId());
                        activity.setTemplate("007");
                        activity.setMessage(getTask().getCtMessage());
                        activity.setUserId(getTask().getCtCrUid());
                        anoudDao.callCRMPackage(activity, CommonDAO.ActivityTypes.ACTIVITY);
                    }
                    setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                    setMessage("Saved successfully");
                }
            } catch (Exception e) {
                setMessage(e.getMessage());
                LOGGER.error("Exception => {}", e);
            }
        }
        return "data";
    }

    public void validateSaveTaskForm() {
        if (task.getCtModule() == null || "".equalsIgnoreCase(task.getCtModule().trim())) {
            setMessage(getText("Please Select Module"));
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        } else if (task.getCtPriority() == null || "".equalsIgnoreCase(task.getCtPriority().trim())) {
            setMessage(getText("Please Select Priority"));
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        } else if (task.getCtCatg() == null || "".equalsIgnoreCase(task.getCtCatg().trim())) {
            setMessage(getText("Please Select Catagory"));
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        } else if (task.getCtSubCatg() == null || "".equalsIgnoreCase(task.getCtSubCatg().trim())) {
            setMessage(getText("Please Select Sub Category"));
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        } else if (task.getCtAssignedTo() == null || "".equalsIgnoreCase(task.getCtSubject().trim())) {
            setMessage(getText("Please Enter Subject"));
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        } else if (task.getCtAssignedTo() == null || "".equalsIgnoreCase(task.getCtMessage().trim())) {
            setMessage(getText("Please Enter Message"));
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        } else if (task.getCtAssignedTo() == null || "".equalsIgnoreCase(task.getCtAssignedTo().trim())) {
            setMessage(getText("Please Select AssignedTo"));
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        }
    }

    public String openTaskCloseForm() throws Exception {
        dao = new TaskDAO(getCompany());
        task = dao.loadTaskData(getTask().getCtId());
        // Mark as read
        dao.updateTaskReadStatus(getTask().getCtId());
        return "task_data";
    }

    public String openTaskFwdForm() {
        anoudDao = new AnoudDAO((getCompany()));
        dao = new TaskDAO(getCompany());
        task = dao.loadTaskData(getTask().getCtId());
        return "task_data";
    }

    public String updateTaskForm() {
        dao = new TaskDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            if (StringUtils.isBlank(task.getCtCloseRemarks())) {
                throw new Exception("Please enter the remarks");
            }
            if ("H".equals(task.getCtStatus())) {
                if (StringUtils.isBlank(task.getCtDueDate())) {
                    throw new Exception("Please enter the due date");
                }
                if (task.getCtReason().equals("")) {
                    throw new Exception("Please enter the reason");
                }
            }
            getTask().setCtCrUid(getUserInfo().getUserId());
            int t = dao.updateTask(getTask());
            saveTaskFiles(getTask().getCtId());
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
            setMessage("Saved successfully");
        } catch (Exception e) {
            setMessage((e.getMessage() == null ? "Something went wrong" : e.getMessage()));
        }
        return "data";
    }

    public String openTaskLogPage() {
        return "task_log";
    }

    public String openTaskLogForm() {
        dao = new TaskDAO(getCompany());
        commonDao = new CommonDAO(getCompany());
        statusList = dao.loadStatusList();
        assignToList = commonDao.loadCrmAgentList(null);
        //getTask().setCtAssignedTo(getUserInfo().getUserId());
        getTasksLog().setTask(dao.loadTaskData(getTask().getCtId()));
        //getTasksLog().setTask(getTask());
        return "task_log_form";
    }

    public String saveTaskLogForm() {
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        dao = new TaskDAO(getCompany());
        try {
            if ("add".equals(getOperation()) || "edit".equals(getOperation())) {
                tasksLog.setCtlCrUid(getUserInfo().getUserId());
                tasksLog = dao.saveTaskLog(getTasksLog(), getOperation());
                setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                setMessage("Saved successfully");
            }
        } catch (Exception e) {
            LOGGER.error("Exception => {}", e);
            setMessage(e.getMessage());
        }
        return "data";
    }

    public String loadTaskData() {
        try {
            dao = new TaskDAO(getCompany());
            if ("ALL".equals(getTask().getCtAssignedTo())) {
                getTask().setCtAssignedTo("ALL" + getUserInfo().getUserId());
            }
            aaData = dao.loadTaskList(getTask());
        } catch (Exception e) {
            aaData = new ArrayList<>();
        }
        return "data";
    }

    public String loadTaskNotifyData() {
        try {
            dao = new TaskDAO(getCompany());
            aaData = dao.loadTaskListForNotify(getUserInfo().getUserId());
        } catch (Exception e) {

        } finally {
            if (aaData == null) {
                aaData = new ArrayList<>();
            }
        }
        return "data";
    }

    public String loadTaskLogData() {
        dao = new TaskDAO(getCompany());
        aaData = dao.loadTaskLogList(getTask().getCtId());
        return "data";
    }

    public String openActivitiesPage() {
        getUserInfo().setActiveMenu(TypeConstants.Menu.DASHBOARD);
        commonDao = new CommonDAO(getCompany());
        leadDao = new LeadDAO((getCompany()));
        userList = commonDao.loadCrmAgentList(null);
        statusList = leadDao.loadStatusList();
        dateRangeList = new LinkedList<>();
        KeyValue kv = null;
        for (DashboardDAO.DateRange d : DashboardDAO.DateRange.values()) {
            kv = new KeyValue(d.name(), d.getDesc());
            dateRangeList.add(kv);
        }

        activity = new Activity();
        activity.setUserId(getUserInfo().getUserId());
        activity.setDateRange(DashboardDAO.DateRange.TODAY);
        return "activities_page";
    }

    public String loadActivitiesData() {
        dao = new TaskDAO(getCompany());
        aaData = dao.loadActivityList(getActivity().getDateRange(), getActivity().getUserId());
        if(null == aaData) {
            aaData = new ArrayList<>();
        }
        return "data";
    }

    public String openUploadTaskForm() {

        return "task_upload_form";
    }

    public String saveUploadTaskForm() {
        LOGGER.debug("*** TaskAction -- saveUploadTaskForm() -- Enter ***");
        LOGGER.info("file name : {}", attachment.getName());
        dao = new TaskDAO(getCompany());
        try {
            task = new CrmTasks();
            task.setCtAssignedTo(getUserInfo().getUserId());
            task.setCtCrUid(getUserInfo().getUserId());
            task.setCtStatus("P");
            //List<CrmTasks> uploadTaskList = uploadedDataConfirm(attachment, getTask());
            TaskService taskService = new TaskService(getCompany());
            List<CrmTasks> uploadTaskList = taskService.uploadedDataConfirm(attachment, getTask());
            message = dao.insertUploadedTaskFileDetails(uploadTaskList, task);
            if (StringUtils.isBlank(message)) {
                setMessageType("S");
            }
        } catch (Exception e) {
            setMessageType("E");
            setMessage(e.getMessage() == null ? "Unable to process" : e.getMessage());
        }
        LOGGER.debug("*** TaskAction -- saveUploadTaskForm() -- Exit ***");
        return "data";
    }

    public String loadTaskEntryUploadData() {
        LOGGER.debug("*** TaskAction -- loadTaskEntryUploadData() -- Enter ***");
        LOGGER.info("file name : {}", attachment.getName());
        TaskService taskService = new TaskService(getUserInfo().getCompanyCode());
        task = new CrmTasks();
        getTask().setCtAssignedTo(getUserInfo().getUserId());
        getTask().setCtCrUid(getUserInfo().getUserId());
        getTask().setCtStatus("P");
        setMessageType("OK");
        //aaData = uploadedDataConfirm(attachment, getTask());
        aaData = taskService.uploadedDataConfirm(attachment, getTask());
        Iterator it = aaData.iterator();
        while (it.hasNext()) {
            CrmTasks crm = (CrmTasks) it.next();
            if ("OK".equals(crm.getCtFlex02())) {
                setMessage(crm.getCtFlex02());
            } else {
                setMessageType("Error");
            }
        }
        LOGGER.debug("*** TaskAction -- loadTaskEntryUploadData() -- Exit ***");
        return "updTask_data";
    }

    public void saveTaskFiles(long taskId) throws IOException, Exception {
        FileInputStream fileInputStream = null;
        int sNo = 1;
        if (attachments != null && attachments.length > 0) {
            /*List<FileDescriptor> fileList = new ArrayList<>();
             for (int i = 0; i < attachments.length && attachments[i] != null; i++) {
             String fileName = taskId + "_" + sNo;
             String fileExt = FilenameUtils.getExtension(attachmentsFileName[i]);
             String orgFileName = FilenameUtils.getName(attachmentsFileName[i]);
             String s = FilenameUtils.removeExtension(orgFileName);
             if (s.length() > 40) {
             orgFileName = s.substring(0, 40) + "." + fileExt;
             }
             File descFile = new File(ApplicationConstants.FILE_STORE_LOC(userInfo.getAppType(), company) + ApplicationConstants.TASK_FILE_PATH, fileName + "." + fileExt);
             FileUtils.copyFile(attachments[i], descFile);
             FileDescriptor fileDesc = new FileDescriptor();
             fileDesc.setDocName(fileName);
             fileDesc.setFileExtn(fileExt);
             fileDesc.setFileName(orgFileName);
             fileDesc.setOrgFile(orgFileName);
             fileList.add(fileDesc);
             ++sNo;
             }*/
            NewGenDmsHelper dmsHelper = new NewGenDmsHelper(userInfo.getCompanyCode());
            HashMap<String, String> dataClassPropMap = new HashMap<>();
            String ctId = String.valueOf(taskId);
            dataClassPropMap.put("DOCUMENT_TYPE", "CRM_TASKS");
            dataClassPropMap.put("REF_NO_1", ctId);
            if (attachmentsFileName != null) {
                dmsHelper.saveMultipleDoc(attachments, attachmentsFileName, "001", null, "CRM_TASKS", dataClassPropMap, ctId, null, null, null, userInfo.getUserId());
                /*StringBuilder sb = new StringBuilder();
                 for (FileDescriptor fd : fileList) {
                 sb.append(",").append(fd.getDocName()).append(".").append(fd.getFileExtn()).append(";").append(fd.getOrgFile());
                 }
                 if (sb.length() > 1) {
                 sb.delete(0, 1);
                 }
                 String filenames = sb.toString();*/
                dao.updateTaskFileDetails(taskId);
            }
        }
    }

    /* public List uploadedDataConfirm(final File csvFile, CrmTasks tasks) {
     LOGGER.debug("*** TaskAction -- uploadedDataConfirm() -- Enter ***");
     List<CrmTasks> FinalList = new ArrayList<>();
     CrmTasks crmTasksg = null;
     Workbook workBook = null;
     FileInputStream fileInputStream = null;
     this.setMessageType("OK");
     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
     anoudDao = new AnoudDAO(getUserInfo().getCompanyCode());
     commonDao = new CommonDAO(getUserInfo().getCompanyCode());
     moduleList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CRM_MODULE);
     priorityList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CRM_PRIORITY);
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
     // crmTasksg.setCtRemindBefore(Integer.parseInt(cellValues[12]));
     try {
     crmTasksg.setCtAssignedToDesc(validateAssignedTo(cellValues[9]));
     crmTasksg.setCtAssignedTo(cellValues[9]);
     } catch (IllegalArgumentException il) {
     crmTasksg.setCtModule(il.getMessage());
     errorMsg.append(il.getMessage());
     }
     // crmTasksg.setCtAssignedToDesc(cellValues[9]);
     crmTasksg.setCtStatus("P");

     if (errorMsg.length() == 0) {
     crmTasksg.setCtFlex01("OK");
     this.setMessage("OK");
     } else {
     crmTasksg.setCtFlex01(errorMsg.toString());
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
     }*/
    public String loadTaskClForm() {
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            anoudDao = new AnoudDAO(getUserInfo().getCompanyCode());
            CrmCallLog crmCallLog = new CrmCallLog();
            crmCallLog.setCclExtId(getUserInfo().getUserTelNo());
            crmCallLog.setCclCallNo(getCustomer().getMobileNo());
            crmCallLog.setCclType("O");
            crmCallLog.setCclCrmType(TypeConstants.CallType.OUTBOUND.getCode());
            crmCallLog.setCclCrUid(getUserInfo().getUserId());
            /*if(ApplicationConstants.APP_TYPE_RETAIL.equals(getUserInfo().getAppType()) && ApplicationConstants.COMPANY_CODE_BEEMA.equals(getUserInfo().getCompanyCode())) {
             if (crmCallLog.getCclCallNo().startsWith("9")) {
             crmCallLog.setCclCallNo(crmCallLog.getCclCallNo().substring(1));
             }
             }*/
            activity = new Activity();
            activity = anoudDao.saveIncomingCall(crmCallLog);
            tasksLog = new CrmTasksLog();
            tasksLog.setCtlCtId(Long.valueOf(getFlex2()));
            tasksLog.setCtlCrUid(getUserInfo().getUserId());
            tasksLog.setCtlDetails("Outgoing call");
            tasksLog.setCtlStatus("P");
            tasksLog.setCtlFlex01("CALL");
            tasksLog.setCtlFlex02(String.valueOf(activity.getId()));
            dao = new TaskDAO(getCompany());
            tasksLog = dao.saveTaskLog(getTasksLog(), "add");
            setMessage(String.valueOf(tasksLog.getCtlId()));
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            LOGGER.error("{}", e);
        }
        return "data";
    }

    public String saveTaskBulkAssign() {
        dao = new TaskDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            task.setCtCrUid(getUserInfo().getUserId());
            dao.saveTaskBulkAssign(task, assignToList);
            setMessage(String.format("%s task(s) assigned to %s", assignToList.size(), task.getCtAssignedToDesc()));
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
        } catch (Exception e) {
            LOGGER.error("{}", e);
        }
        return "data";
    }

    public String downloadTaskRefFile() {
        File file = new File("TASKS_UPL_TEMPLATE.xlsx");
        try (Workbook wb = new XSSFWorkbook();) {
            Sheet taskSheet = wb.createSheet("Tasks");
            Sheet moduleSheet = wb.createSheet("Module");
            Sheet prioritySheet = wb.createSheet("Priority");
            Sheet catgSheet = wb.createSheet("Category & Sub Category");
            Sheet dateSheet = wb.createSheet("Due Date");
            Sheet assignSheet = wb.createSheet("Assigned To");

            //For Task Sheet
            int rowIndex = 0;
            Row headRow = taskSheet.createRow(rowIndex++);
            CellStyle style1 = wb.createCellStyle();
            // Create a cell and put a value in it.
            Cell cell1 = headRow.createCell(0);
            setThinBorderStyle(cell1, style1);
            cell1.setCellValue("Module");
            Cell cell2 = headRow.createCell(1);
            setThinBorderStyle(cell2, style1);
            cell2.setCellValue("Priority");
            Cell cell3 = headRow.createCell(2);
            setThinBorderStyle(cell3, style1);
            cell3.setCellValue("Category");
            Cell cell4 = headRow.createCell(3);
            setThinBorderStyle(cell4, style1);
            cell4.setCellValue("Sub Category");
            Cell cell5 = headRow.createCell(4);
            setThinBorderStyle(cell5, style1);
            cell5.setCellValue("Ref.ID");
            Cell cell6 = headRow.createCell(5);
            setThinBorderStyle(cell6, style1);
            cell6.setCellValue("Ref.No");
            Cell cell7 = headRow.createCell(6);
            setThinBorderStyle(cell7, style1);
            cell7.setCellValue("Subject");
            Cell cell8 = headRow.createCell(7);
            setThinBorderStyle(cell8, style1);
            cell8.setCellValue("Message");
            Cell cell9 = headRow.createCell(8);
            setThinBorderStyle(cell9, style1);
            cell9.setCellValue("Due Date");
            Cell cell10 = headRow.createCell(9);
            setThinBorderStyle(cell10, style1);
            cell10.setCellValue("Mobile");
            Cell cell11 = headRow.createCell(10);
            setThinBorderStyle(cell11, style1);
            cell11.setCellValue("Assigned To");

            for (int i = 1; i <= 11; i++) {
                Row row = taskSheet.createRow(i);
                for (int j = 0; j < 11; j++) {
                    // Create a cell
                    Cell cel = row.createCell(j);
                    // Get the style
                    style1 = wb.createCellStyle();
                    // Set Thin border style
                    setThinBorderStyle(cel, style1);
                }
            }
            //commented for corporate
            //anoudDao = new AnoudDAO(getTask().getCtCrmId());
            anoudDao = new AnoudDAO(getUserInfo().getCompanyCode());
            commonDao = new CommonDAO(getUserInfo().getCompanyCode());
            moduleList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CRM_MODULE);
            priorityList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CRM_PRIORITY);
          //commented for corporate
            /*anoudDao = new AnoudDAO(getUserInfo().getCompanyCode());
            categoryList = anoudDao.getCrmTaskCategory(getTask().getCtCrmId(), FieldConstants.AppCodes.CRM_CATEGORY);
            subCategoryList = anoudDao.getCrmTaskCategory(getTask().getCtCrmId(), FieldConstants.AppCodes.CRM_SUB_CATEGORY);*/
            setCategoryList(anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_CATEGORY));
            subCategoryList = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_SUB_CATEGORY);
            assignToList = commonDao.loadCrmAgentList(null);

            //For Module Sheet
            rowIndex = 0;
            Row moduleRow = moduleSheet.createRow(rowIndex++);
            Cell mCell1 = moduleRow.createCell(0);
            mCell1.setCellValue("Module Code");
            Cell mCell2 = moduleRow.createCell(1);
            mCell2.setCellValue("Module Desc");
            for (KeyValue kv : moduleList) {
                moduleRow = moduleSheet.createRow(rowIndex++);
                mCell1 = moduleRow.createCell(0);
                mCell1.setCellValue(kv.getKey());
                mCell2 = moduleRow.createCell(1);
                mCell2.setCellValue(kv.getValue());
            }
            //For Priority Sheet
            rowIndex = 0;
            Row priorityRow = prioritySheet.createRow(rowIndex++);
            Cell pCell1 = priorityRow.createCell(0);
            pCell1.setCellValue("Priority Code");
            Cell pCell2 = priorityRow.createCell(1);
            pCell2.setCellValue("Priority Desc");
            for (KeyValue kv1 : priorityList) {
                priorityRow = prioritySheet.createRow(rowIndex++);
                pCell1 = priorityRow.createCell(0);
                pCell1.setCellValue(kv1.getKey());
                pCell2 = priorityRow.createCell(1);
                pCell2.setCellValue(kv1.getValue());
            }
            //For Category & Sub Category Sheet
            rowIndex = 0;
            Row catgRow = catgSheet.createRow(rowIndex++);
            Cell catgCell1 = catgRow.createCell(0);
            catgCell1.setCellValue("Category Desc");
            Cell catgCell2 = catgRow.createCell(1);
            catgCell2.setCellValue("Category Code");
            Cell catgCell3 = catgRow.createCell(2);
            catgCell3.setCellValue("Sub Category Desc");
            Cell catgCell4 = catgRow.createCell(3);
            catgCell4.setCellValue("SubCategory Code");
            for (KeyValue kv : getCategoryList()) {
                boolean catgFind = false;
                for (KeyValue kv1 : subCategoryList) {
                    if (kv.getKey().equals(kv1.getInfo4())) {
                        if (!catgFind) {
                            catgRow = catgSheet.createRow(rowIndex++);
                            catgCell1 = catgRow.createCell(0);
                            catgCell1.setCellValue(kv.getValue());
                            catgCell2 = catgRow.createCell(1);
                            catgCell2.setCellValue(kv.getKey());
                            catgFind = true;
                        }
                        catgRow = catgSheet.createRow(rowIndex++);
                        catgCell1 = catgRow.createCell(2);
                        catgCell1.setCellValue(kv1.getValue());
                        catgCell2 = catgRow.createCell(3);
                        catgCell2.setCellValue(kv1.getKey());
                    }
                }

            }
            //For Due Date Sheet
            rowIndex = 0;
            Row dateRow = dateSheet.createRow(rowIndex++);
            Cell dCell1 = dateRow.createCell(0);
            dCell1.setCellValue("Due Date should be date field");
            dateRow = dateSheet.createRow(rowIndex++);
            dCell1 = dateRow.createCell(0);
            // dCell1.setCellValue("dd-mm-yyyy");
            dCell1.setCellType(Cell.CELL_TYPE_FORMULA);
            dCell1.setCellFormula("NOW()");
            //For Assidned To Sheet
            rowIndex = 0;
            Row assignRow = assignSheet.createRow(rowIndex++);
            Cell aCell1 = assignRow.createCell(0);
            aCell1.setCellValue("Assigned To");
            Cell aCell2 = assignRow.createCell(1);
            aCell2.setCellValue("Code");
            for (KeyValue kv2 : assignToList) {
                assignRow = assignSheet.createRow(rowIndex++);
                aCell1 = assignRow.createCell(0);
                aCell1.setCellValue(kv2.getValue());
                aCell2 = assignRow.createCell(1);
                aCell2.setCellValue(kv2.getKey());
            }
            autoSizeColumns(wb);
            FileOutputStream fileOut = new FileOutputStream(file.getAbsoluteFile());
            wb.write(fileOut);
            fileOut.close();
            fileInputStream = new FileInputStream(file);
            filePath = file.getName();
        } catch (Exception e) {
            LOGGER.error("{}", e);
        }
        return SUCCESS;
    }

    private void setThinBorderStyle(Cell cell, CellStyle style) {
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cell.setCellStyle(style);
    }

    public void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(0);
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }
    }

    public List<? extends Object> getAaData() {
        return aaData;
    }

    public void setAaData(List<? extends Object> aaData) {
        this.aaData = aaData;
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getFlex1() {
        return flex1;
    }

    public void setFlex1(String flex1) {
        this.flex1 = flex1;
    }

    public String getFlex2() {
        return flex2;
    }

    public void setFlex2(String flex2) {
        this.flex2 = flex2;
    }

    public String getFlex3() {
        return flex3;
    }

    public void setFlex3(String flex3) {
        this.flex3 = flex3;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEnableForwardYn() {
        return enableForwardYn;
    }

    public void setEnableForwardYn(String enableForwardYn) {
        this.enableForwardYn = enableForwardYn;
    }

    public CrmTasks getTask() {
        return task;
    }

    public void setTask(CrmTasks task) {
        this.task = task;
    }

    public CrmTasksLog getTasksLog() {
        return tasksLog;
    }

    public void setTasksLog(CrmTasksLog tasksLog) {
        this.tasksLog = tasksLog;
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

    public static Map<Integer, String> getHoursRangeList() {
        return hoursRangeList;
    }

    public File getAttachment() {
        return attachment;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

    public List<KeyValue> getDateRangeList() {
        return dateRangeList;
    }

    public void setDateRangeList(List<KeyValue> dateRangeList) {
        this.dateRangeList = dateRangeList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CrmCallLog getCallLog() {
        return callLog;
    }

    public void setCallLog(CrmCallLog callLog) {
        this.callLog = callLog;
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

    public File[] getAttachments() {
        return attachments;
    }

    public void setAttachments(File[] attachments) {
        this.attachments = attachments;
    }

    public String[] getAttachmentsFileName() {
        return attachmentsFileName;
    }

    public void setAttachmentsFileName(String[] attachmentsFileName) {
        this.attachmentsFileName = attachmentsFileName;
    }

    public TDocInfoBean getDocInfoBean() {
        return docInfoBean;
    }

    public void setDocInfoBean(TDocInfoBean docInfoBean) {
        this.docInfoBean = docInfoBean;
    }

    public List<TDocInfoBean> getDmsFileList() {
        return DmsFileList;
    }

    public void setDmsFileList(List<TDocInfoBean> DmsFileList) {
        this.DmsFileList = DmsFileList;
    }

    /**
     * @return the reasonList
     */
    public List<KeyValue> getReasonList() {
        return reasonList;
    }

    /**
     * @param reasonList the reasonList to set
     */
    public void setReasonList(List<KeyValue> reasonList) {
        this.reasonList = reasonList;
    }

}
