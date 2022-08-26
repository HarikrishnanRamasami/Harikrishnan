/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ClearSessionAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.vo.LoginHistory;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.CommonUtil;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class LogoutAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, ClearSessionAware {

    private static final Logger LOGGER = LogUtil.getLogger(LogoutAction.class);

    private transient HttpServletRequest request;
    private transient HttpServletResponse response;

    @Override
    public String execute() throws Exception {
        SessionMap<String, Object> session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
        Cookie c = CommonUtil.removeCookie(request, "token");
        if (c != null) {
            response.addCookie(c);
        }
        c = CommonUtil.removeCookie(request, "login");
        if (c != null) {
            response.addCookie(c);
        }
        if (session == null || session.get(ApplicationConstants.SESSION_NAME_USER_ID) == null) {
            LOGGER.info("Session Timed Out...");
        } else {
            try {
                UserInfo userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
                if (userInfo != null) {
                    String sessionId = (String) request.getSession().getId();
                    AnoudDAO dao = new AnoudDAO(userInfo.getCompanyCode());
                    LoginHistory lh = new LoginHistory();
                    lh.setLogoutBy("N");
                    lh.setSessionId(sessionId);
                    lh.setUserId(userInfo.getUserLdapId());
                    dao.logout(lh);
                    dao = null;
                    LOGGER.info("Normal Logging Out... User Id: {}", userInfo.getUserId());
                }
            } finally {

            }
            try {
                session.invalidate();
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
        return INPUT;
    }

    @Override
    public void clearSession(HttpSession session) {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute(ApplicationConstants.SESSION_NAME_USER_INFO);
            if (userInfo != null) {
                String sessionId = (String) session.getId();
                AnoudDAO dao = new AnoudDAO(userInfo.getCompanyCode());
                LoginHistory lh = new LoginHistory();
                lh.setLogoutBy("A");
                lh.setSessionId(sessionId);
                lh.setUserId(userInfo.getUserLdapId());
                dao.logout(lh);
                dao = null;
                LOGGER.info("Auto Logging Out... User Id: {}", userInfo.getUserId());
            }
        } finally {

        }
    }

    /**
     * Lock the user screen
     *
     * @return navigation
     * @author ravindar.singh
     */
    public String lockScreen() {
        SessionMap session = (SessionMap) ActionContext.getContext().getSession();
        UserInfo userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
        if (userInfo != null) {
            userInfo.setScreenLockYn("Y");
        }
        Cookie c = CommonUtil.getCookie(request, "login");
        if (c == null) {
            Cookie cookie = new Cookie("login", "L");
            cookie.setSecure(false);
            cookie.setPath("/");
            //cookie.setHttpOnly(false);
            // A negative value means that the cookie is not stored persistently and will be deleted when the Web browser exits. A zero value causes the cookie to be deleted.
            //cookie.setMaxAge(60 * 60 * 20);
        } else {
            c.setValue("L");
        }
        response.addCookie(c);
        return INPUT;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
}
