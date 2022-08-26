/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.uam;

import java.util.Enumeration;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.container.Identifier;
import org.glassfish.jersey.server.core.Priorities;

import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
@Provider
@Priority(Priorities.LEVEL_1)
@Identifier
public class CompanyIdentifier implements javax.ws.rs.container.ContainerRequestFilter {

    private static final Logger logger = LogUtil.getLogger(CompanyIdentifier.class);

    @Context
    UriInfo uriInfo;

    @Context
    private HttpServletRequest request;

    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).entity("Access blocked!!").build();

    @Override
    public void filter(ContainerRequestContext requestContext) {

        String company = null, appType = null;
        Enumeration<String> values = request.getHeaders("company");
        if (values != null) {
            if (values.hasMoreElements()) {
                company = values.nextElement();
                logger.debug("Company from header: {} ", new Object[]{company});
            }
        }
        values = request.getHeaders("apptype");
        if (values != null) {
            if (values.hasMoreElements()) {
                appType = values.nextElement();
                logger.debug("apptype from header: {} ", new Object[]{appType});
            }
        }
        if (company == null) {
            company = ApplicationConstants.getCompanyByDomain(request.getServerName(), request.getContextPath(), ApplicationConstants.COMPANY_CODE_DOHA);
            logger.debug("Company from domain: {}", new Object[]{company});
        }

        if (!StringUtils.isNotBlank(company)) {
            requestContext.abortWith(ACCESS_FORBIDDEN);
            return;
        }
        request.setAttribute("COMPANY", company);
        request.setAttribute("APP_TYPE", appType);
    }
}
