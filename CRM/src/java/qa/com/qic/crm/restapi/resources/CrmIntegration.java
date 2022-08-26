/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.container.Identifier;

import qa.com.qic.common.util.HashUtil;
import qa.com.qic.common.vo.CrmCallLog;
import qa.com.qic.common.vo.CrmTasks;
import qa.com.qic.common.vo.CrmUser;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
@Path("")
@Identifier
public class CrmIntegration {

    private static final Logger logger = LogUtil.getLogger(CrmIntegration.class);

    private CrmService crmService;
    @Context
    private HttpServletRequest request;

    @POST
    @Path("ameyo/sso")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response ameyoLogin(UserInfo model) throws Exception {
        logger.info("API => authorize Call Center - Enter");
        final String company = (String) request.getAttribute("COMPANY");
        HashUtil hu = new HashUtil();
        if (StringUtils.isBlank(model.getUserId()) || StringUtils.isBlank(model.getPassword())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        crmService = new CrmService(company);
        CrmUser user = crmService.getUserDetailsByUserId(model.getUserId());
        if (user != null && model.getPassword().equals(hu.hexSHA256(user.getUserPwdResetToken()))) {//(user.getPassword() == null ? "" : user.getPassword()) +
            return Response.status(Response.Status.OK).build();
        }
        logger.info("API => authorize Call Center - Exit");
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/agent/task")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response generateTask(@Context HttpHeaders httpheaders, CrmTasks crmTasks) {
        logger.info("API => Generate task - Enter");
        String companyCode = httpheaders.getHeaderString("company");
        String userId = httpheaders.getHeaderString("username");
        String password = httpheaders.getHeaderString("password");
        logger.info("API => Generate task - Company: {}, User Id: {}", new Object[]{companyCode, userId});
        if (crmTasks == null || StringUtils.isBlank(companyCode) || StringUtils.isBlank(userId) || StringUtils.isBlank(password)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else if (StringUtils.isBlank(crmTasks.getCtRefNo()) || StringUtils.isBlank(crmTasks.getCtModule())
                || StringUtils.isBlank(crmTasks.getCtCatg()) || StringUtils.isBlank(crmTasks.getCtSubCatg())
                || StringUtils.isBlank(crmTasks.getCtSubject()) || StringUtils.isBlank(crmTasks.getCtMessage())
                || StringUtils.isBlank(crmTasks.getCtMobileNo()) || !StringUtils.containsOnly(crmTasks.getCtMobileNo(),"0123456789")
                || StringUtils.isBlank(crmTasks.getCtEmailId())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Long taskId = null;
        try {
            crmService = new CrmService(companyCode);
            CrmUser user = crmService.getUserDetailsByUserId(userId);
            HashUtil hu = new HashUtil();
            String hashPwd = hu.hexSHA256(password);
            if (user == null || !hashPwd.equals(user.getUserPwdResetToken())) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            taskId = crmService.createTasks(crmTasks, userId);
            logger.info("API => Generate task - Exit [Id: {}]", taskId);
            if (taskId == null || taskId == 0) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            logger.error("Authorizatoin and Task Creation error {}", e);
        }
        return Response.ok("{\"ctId\": \"" + taskId + "\", \"message\": \"Task created successfully\", \"messageType\": \"S\"}", MediaType.APPLICATION_JSON_TYPE).build();
    }

    //Ameyo Feedback
    @POST
    @Path("/ameyo/feedback")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response ameyoFeedback(CrmCallLog callLog, @Context HttpHeaders headers) {
        Response response;
        try {
            String companyCode = headers.getHeaderString("company");
            crmService = new CrmService(companyCode);
            int count = crmService.updateAmeyoFeedBack(callLog);
            logger.info("API => Feedback - Call No: {}, Id: {}, Status: {}", new Object[]{callLog.getCclCallNo(), callLog.getCclFilePath(), count});
            response = Response.ok("Ameyo feedback updated successfully").build();
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }
}