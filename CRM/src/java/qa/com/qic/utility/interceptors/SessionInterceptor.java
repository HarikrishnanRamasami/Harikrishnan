/**
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar All Rights
 * Reserved Unauthorized copying of this file, via any medium is strictly
 * prohibited
 *
 */
package qa.com.qic.utility.interceptors;

import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.CommonUtil;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author Ravindar Singh T
 */
public class SessionInterceptor implements Interceptor {

    private static final Logger LOGGER = LogUtil.getLogger(SessionInterceptor.class);

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext context = actionInvocation.getInvocationContext();
        Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
        HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
        HttpSession reqSession = request.getSession(false);
        /*Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("JSESSIONID".equalsIgnoreCase(c.getName()) || "CRM_COOKIE".equalsIgnoreCase(c.getName())) {
                    LOGGER.info("Name: " + c.getName() + "(" + c.getDomain() + "), Value: " + c.getValue() + "(" + c.getPath() + "), Age: " + c.getMaxAge());
                }
            }
        }*/
        if (reqSession != null) {
            long createdTime = reqSession.getCreationTime();
            long currentTime = new GregorianCalendar().getTimeInMillis();
            long diff = ((currentTime - reqSession.getLastAccessedTime()) / 1000);
            long idle = ((currentTime - createdTime) / 1000);
            LOGGER.info(session.get(ApplicationConstants.SESSION_NAME_USER_ID) + " [" + reqSession.getId() + "] last accessed time is " + Math.round(diff) + " sec and active since " + Math.round(idle / 60) + " min");
        }
        String result;
        UserInfo userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
        if (userInfo == null || userInfo.getUserId() == null) {
            result = "session-expired";
        } else {
            result = actionInvocation.invoke();
            if("Y".equals(userInfo.getScreenLockYn()) && !"unlock".equals(actionInvocation.getProxy().getActionName())) {
                result = "lock_screen";
            } else {
                Cookie c = CommonUtil.getCookie(request, "token");
                if(c == null) {
                    c = CommonUtil.getCookie(request, userInfo.getUserLdapId());
                    if(c == null) {
                        result = "logout";
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }
}
