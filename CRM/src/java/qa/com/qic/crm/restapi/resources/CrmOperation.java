/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.crm.restapi.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.server.container.BasicAuthAccess;
import org.glassfish.jersey.server.container.Identifier;
import org.glassfish.jersey.server.core.RolesType;

import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.Customer;
import qa.com.qic.common.action.TaskService;
import qa.com.qic.common.dao.TaskDAO;
import qa.com.qic.common.dms.NewGenDmsHelper;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.FileDescriptor;
import qa.com.qic.common.vo.CrmLeads;
import qa.com.qic.common.vo.CrmTasks;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.crm.restapi.uam.UserAuth;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author thoufeak.rahman, ravindar.singh
 */
@Path("")
@Identifier
@BasicAuthAccess(name = UserAuth.class)
public class CrmOperation {

    private static final Logger logger = LogUtil.getLogger(CrmOperation.class);
    private File[] attachments;
    private String[] attachmentsFileName;

    @Context
    private SecurityContext securityContext;
    @Context
    UriInfo uriInfo;

    private CrmService crmService;
    private CrmTasks task;

    @POST
    @Path("/task")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createTask(CrmTasks crmTasks) {
        logger.info("API => Create task - Enter");
        final UserInfo userInfo = (UserInfo) securityContext.getUserPrincipal();
        crmService = new CrmService(userInfo.getCompanyCode());
        int count = crmService.saveTasks(crmTasks, userInfo);
        logger.info("API => Create task - Exit [Count: {}]", count);
        if (count == 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/activity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createActivity(Activity activity) throws Exception {
        logger.info("API => Create activity - Enter");
        final UserInfo userInfo = (UserInfo) securityContext.getUserPrincipal();
        crmService = new CrmService(userInfo.getCompanyCode());
        String count = crmService.createActivity(activity, userInfo);
        logger.info("API => Create activity - Exit [Count: {}]", count);
        if (count != null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok().build();
    }

    @POST
    @Path("/customer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createCustomer(Customer customer) {
        logger.info("API => Save customer - Enter");
        final UserInfo userInfo = (UserInfo) securityContext.getUserPrincipal();
        crmService = new CrmService(userInfo.getCompanyCode());
        int count = crmService.saveCustomer(customer, userInfo);
        logger.info("API => Save customer - Exit [Count: {}]", count);
        if (count == 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("/lead/{leadId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public CrmLeads retreiveLeadStatus(@PathParam("leadId") long leadId) {
        logger.info("API => Get lead - Enter [Id: {}]", leadId);
        //List<CrmLeads> list = null;
        CrmLeads crmLeads = null;
        try {
            final UserInfo userInfo = (UserInfo) securityContext.getUserPrincipal();
            crmService = new CrmService(userInfo.getCompanyCode());
            crmLeads = crmService.loadLeadData(leadId);
        } catch (Exception e) {
        }
        logger.info("API => Get lead - Exit [Id: {}]", leadId);
        return crmLeads;
    }

    @POST
    @Path("/lead")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createLead(CrmLeads crmLeads) throws Exception {
        logger.info("API => Save lead - Enter");
        final UserInfo userInfo = (UserInfo) securityContext.getUserPrincipal();
        crmService = new CrmService(userInfo.getCompanyCode());
        int count = crmService.saveLeads(crmLeads, userInfo);
        if (count == 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("API => Save lead - Exit");
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/taskUpload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response taskUpload(FormDataMultiPart fdmp) throws IOException {
        logger.info("API => Task upload - Enter");
        File csvFile = null;
        try {
            ResponseEntity re = new ResponseEntity();
            re.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
            final UserInfo userInfo = (UserInfo) securityContext.getUserPrincipal();
            FormDataBodyPart formDataBodyPart = fdmp.getField("file");
            String orgFileName = formDataBodyPart.getFormDataContentDisposition().getFileName();
            String fileExt = FilenameUtils.getExtension(orgFileName);
            if (StringUtils.isBlank(fileExt)) {
                re.setMessage("Invalid file");
                return Response.status(Response.Status.BAD_REQUEST).entity(re).build();
            }
            String fileName = Math.random() + "_" + orgFileName;

            StringBuilder uploadFilePath = new StringBuilder(ApplicationConstants.FILE_STORE_LOC(userInfo.getAppType(), userInfo.getCompanyCode()));
            uploadFilePath.append(ApplicationConstants.TASK_FILE_PATH);
            File f = new File(uploadFilePath.toString());
            if (!f.exists()) {
                FileUtils.forceMkdir(f);
            }
            logger.debug("File name: {}, Temp File name: {}, Path: {}", new Object[]{orgFileName, fileName, f.getAbsolutePath()});
            csvFile = new File(f, fileName);
            InputStream in = formDataBodyPart.getValueAs(InputStream.class);
            FileUtils.copyInputStreamToFile(in, csvFile);
            crmService = new CrmService(userInfo.getCompanyCode());
            TaskService taskService = new TaskService(userInfo.getCompanyCode());
            TaskDAO taskDAO = new TaskDAO(userInfo.getCompanyCode());
            List<CrmTasks> uploadTaskList = taskService.uploadedDataConfirm(csvFile, getTask());
            List<CrmTasks> errorList = new ArrayList<>();
            if (uploadTaskList != null) {
                for (CrmTasks ct : uploadTaskList) {
                    if (!"OK".equals(ct.getCtFlex02())) {
                        errorList.add(ct);
                    }
                }
                if (!errorList.isEmpty()) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(errorList).build();
                } else {
                    String s = taskDAO.insertUploadedTaskFileDetails(uploadTaskList, task);
                    logger.info("API => Task upload - Exit");
                    re.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                    re.setMessage("Uploaded Succesfully");
                    return Response.status(Response.Status.OK).entity(re).build();
                }
            } else {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        } catch (Exception e) {
        } finally {
            /*if (csvFile != null) {
             FileUtils.deleteQuietly(csvFile);
             }*/
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    //Dms Upload
    @POST
    @Path("/dmsUpload")
    @RolesAllowed(value = {RolesType.ROLES_TYPE_PROVIDER})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response DmsUpload(@BeanParam OmniDocsParam model) throws IOException, Exception {
        logger.info("API => Dms Email File upload - Enter");
        ResponseEntity re = new ResponseEntity();
        List<FileDescriptor> fileList = null;
        FileDescriptor fileDesc = null;
        File f = null;
        try {
            final UserInfo userInfo = (UserInfo) securityContext.getUserPrincipal();
            model = model == null ? new OmniDocsParam() : model;
            model.setCompany(userInfo.getCompanyCode());
            model.setAppType(userInfo.getAppType());
            if (model.getDocument() != null) {
                fileList = new ArrayList<>();
                StringBuilder uploadFilePath = new StringBuilder(ApplicationConstants.FILE_STORE_LOC(model.getAppType(), model.getCompany()));
                uploadFilePath.append(ApplicationConstants.TEMP_FILE_PATH);
                f = new File(uploadFilePath.toString());
                if (!f.exists()) {
                    FileUtils.forceMkdir(f);
                }
                int sNo = 0;
                for (FormDataBodyPart stream : model.getDocument()) {
                    InputStream fileInputStream = stream.getValueAs(InputStream.class);
                    try {
                        if (fileInputStream != null) {
                            String orgFileName = stream.getFormDataContentDisposition().getFileName();
                            File descFile = new File(uploadFilePath.toString() + orgFileName);
                            FileUtils.copyInputStreamToFile(fileInputStream, descFile);
                            fileDesc = new FileDescriptor();
                            fileDesc.setDocName(orgFileName);
                            fileDesc.setFile(descFile);
                            fileDesc.setFileName(orgFileName);
                            fileDesc.setContentType(Files.probeContentType(descFile.toPath()));
                            fileList.add(fileDesc);
                            ++sNo;
                        }
                    } catch (IOException e) {
                        logger.warn("Error while uploading file");
                        throw new IOException("Error: Document(s) upload unsuccessful");
                    }
                }
                if (sNo > 0) {
                    attachmentsFileName = new String[sNo];
                    attachments = new File[sNo];
                    int i = 0;
                    for (FileDescriptor refbean : fileList) {
                        attachmentsFileName[i] = refbean.getFileName();
                        attachments[i] = refbean.getFile();
                        i++;
                    }
                    NewGenDmsHelper dms = new NewGenDmsHelper(model.getCompany());
                    HashMap<String, String> dataClassPropMap = new HashMap<>();
                    dataClassPropMap.put("DOCUMENT_TYPE", "CRM_TASKS");
                    dataClassPropMap.put("REF_NO_1", model.getId());
                    dms.saveMultipleDoc(attachments, attachmentsFileName, model.getDocCode(), null, model.getDocType(), dataClassPropMap, model.getId(), null, null, null, model.getUserId());
                    TaskDAO taskDAO = new TaskDAO(model.getCompany());
                    long taskId = Long.parseLong(model.getId());
                    taskDAO.updateTaskFileDetails(taskId);
                }
                re.setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                re.setMessage("File Uploaded Succesfully");
            }
        } catch (Exception ex) {
            logger.error("Error: {}", ex);
            re.setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
            re.setMessage("File Not Uploaded");
        } finally {
            if (fileList != null) {
                for (FileDescriptor refbean : fileList) {
                    logger.debug("Deleting uploaded dms file " + refbean.getFileName());
                    FileUtils.deleteQuietly(refbean.getFile());
                }
            }
        }
        logger.info("API => Dms Email File upload - Exit");
        return Response.status(Response.Status.ACCEPTED).entity(re).build();
    }

    public class ResponseEntity {

        private String message;
        private String messageType;

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
    }

    public CrmTasks getTask() {
        return task;
    }

    public void setTask(CrmTasks task) {
        this.task = task;
    }

}
