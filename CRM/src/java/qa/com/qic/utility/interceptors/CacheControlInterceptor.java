/**
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar All Rights
 * Reserved Unauthorized copying of this file, via any medium is strictly
 * prohibited
 */
package qa.com.qic.utility.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author Ravindar Singh T <a href="mailto:ravindar.singh@qicgroup.com.qa">ravindar.singh@qicgroup.com.qa</a>
 */
public class CacheControlInterceptor implements Interceptor {

    private static final Logger LOGGER = LogUtil.getLogger(CacheControlInterceptor.class);

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext context = actionInvocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);
        HttpSession session = request.getSession(false);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        if (session != null) {
            response.setHeader("SET-COOKIE", "JSESSIONID=" + session.getId() + "; HttpOnly");
        }
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        return actionInvocation.invoke();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
