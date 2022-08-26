/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.uam;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.server.provider.Authorization;

import qa.com.qic.common.util.ActiveDirectoryAuthentication;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.crm.restapi.resources.CrmService;

/**
 *
 * @author ravindar.singh
 */
public class UserAuth implements Authorization {

    @Override
    public boolean isUserAllowed(String username, final String password, final Set<String> rolesSet, final ContainerRequestContext requestContext) {
        boolean isAllowed = false;
        final String company = (String) requestContext.getProperty("COMPANY");
        final String appType = (String) requestContext.getProperty("APP_TYPE");
        final CrmService dao = new CrmService(company);
        final Map map = new HashMap();
        username = username.toLowerCase();
        try {
            final UserInfo userInfo = dao.login(username, password);
            if (password.equals(userInfo.getPassword())) {
                isAllowed = true;
            } else {
                ActiveDirectoryAuthentication lDap = new ActiveDirectoryAuthentication(company);
                isAllowed = lDap.authenticate(username, password);
            }
            if (isAllowed) {
                requestContext.setSecurityContext(new SecurityContext() {

                    @Override
                    public Principal getUserPrincipal() {
                        userInfo.setCompanyCode(company);
                        userInfo.setAppType(appType);
                        return userInfo;
                    }

                    @Override
                    public boolean isUserInRole(String string) {
                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return "db";
                    }

                });
            }
        } catch (Exception ex) {

        }
        return isAllowed;
    }

}
