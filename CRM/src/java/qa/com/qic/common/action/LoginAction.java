/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.util.ActiveDirectoryAuthentication;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.HashUtil;
import qa.com.qic.common.vo.CrmUser;
import qa.com.qic.common.vo.LoginHistory;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.CommonUtil;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class LoginAction extends ActionSupport implements Preparable, SessionAware, ServletRequestAware, ServletResponseAware {

    private static final Logger LOGGER = LogUtil.getLogger(LoginAction.class);
    private final Pattern PATTERN_FIRST_CHAR = Pattern.compile("^[\\S]{0,1}");

    private transient Map<String, Object> session = null;
    private transient HttpServletRequest request = null;
    private transient HttpServletResponse response = null;

    /**
     * messageType - S: Success, E: Error message - details description about
     * success/error
     */
    private transient String message;
    private transient String messageType;

    private transient String company;
    private transient String usrnm;
    private transient String psswrd;
    private transient String rememberme;
    private transient String joinCallCenter;

    private transient String url;
    private transient String nameSpace;

    public String appType;

    /**
     * Open login page if already logged in will land on the dashboard
     *
     * @return navigation
     * @author ravindar.singh
     */
    public String openLoginPage() throws Exception {
        String result = SUCCESS;
        String message = request.getParameter("message");
        if (StringUtils.isNotBlank(message)) {
            addActionError(message);
        }
        if (session != null && StringUtils.isNotBlank((String) session.get(ApplicationConstants.SESSION_NAME_USER_ID))) {
            nameSpace = "/";
            url = "home";
            result = "redirectAction";
        }
        String src = request.getParameter("src");
        if ("lg".equals(src)) {
            return result;
        }
        String ipAddress = request.getRemoteAddr();
        AnoudDAO dao = new AnoudDAO(getCompany());
        UserInfo user = dao.getUserNameDetails(ipAddress);
        if (user == null) {
            return result;
        }
        try {
            createUserSession(user, dao);
        } catch (Exception ex) {

        }
        return result;
    }

    /**
     * This is used to validate the user. Determine the type of user and
     * retrieve/redirect to the corresponding home page.
     *
     * @return navigation
     * @author ravindar.singh
     */
    //commented for corporate
    /*public String authorize() {
        String result = INPUT;
        boolean isAuthenticated = false;
        AnoudDAO dao = null;
        LoginHistory loginHistory = new LoginHistory();
        try {
            if (session == null || getCompany() == null || "".equals(getCompany())) {
                addActionError("Invalid access");
                return result;
            }
            if (getUsrnm() == null || "".equals(getUsrnm().trim()) || getPsswrd() == null || "".equals(getPsswrd().trim())) {
                addActionError("Enter the Username/Password");
            } else {
                setUsrnm(getUsrnm().toLowerCase().trim());
                loginHistory.setStatus("E");
                loginHistory.setIpAddress(request.getHeader("X-Forwarded-For") == null ? request.getRemoteAddr() : request.getHeader("X-Forwarded-For"));
                loginHistory.setBrowserInfo(request.getHeader("user-agent"));
                loginHistory.setUserId(getUsrnm());
                if(ApplicationConstants.Environment.MVP_QA == ApplicationConstants.ENVIRONMENT
                        || ApplicationConstants.Environment.MVP_DEV == ApplicationConstants.ENVIRONMENT) {
                    isAuthenticated = "ATat$123".equals(getPsswrd());
                } else {
                    ActiveDirectoryAuthentication lDap = new ActiveDirectoryAuthentication(getCompany());
                    isAuthenticated = lDap.authenticate(getUsrnm(), getPsswrd());
                }
                if (!isAuthenticated) {
                    throw new Exception("Invalid Username/Password");
                }
                dao = new AnoudDAO(getCompany());
                UserInfo user = dao.getUserDetails(getUsrnm());
                if (user == null) {
                    throw new Exception("Access restricted");
                }
                List<KeyValue> keyList = dao.loadMAppConfig(FieldConstants.AppConfig.BOT_KEY_FAQ);
                if(keyList != null && !keyList.isEmpty()) {
                    KeyValue key = keyList.get(0);
                    if(StringUtils.isNotBlank(key.getKey())) {
                        user.setFaqBotKey(key.getKey());
                    }
                }
                List<KeyValue> appTypeList = dao.getMAppParameter(FieldConstants.AppParameter.APP_TYPE);
                List<KeyValue> appTypeAllList = null;
                if(null == appTypeList || appTypeList.isEmpty()) {
                    throw new Exception("Application configuration missing");
                } else {
                    appTypeAllList = new ArrayList<>(appTypeList);
                    if(appTypeList.size() > 1) {
                        appTypeAllList.add(0, new KeyValue("", "All"));
                    }
                }
                user.setAppTypeList(appTypeAllList);
                user.setAppTypeMinList(appTypeList);
                Set<String> set = null;
                if(!appTypeList.isEmpty()) {
                    set = appTypeList.stream().filter(o -> (!"".equals(o.getKey()))).map(mapper -> {
                        Matcher matcher = PATTERN_FIRST_CHAR.matcher(mapper.getKey());
                        if(matcher.find()) {
                            return matcher.group();
                        }
                       return "";
                    }).collect(Collectors.toSet());
                }
                user.setAppList(set);
                Gson gson = new Gson();
                LOGGER.info("{} menus are {}", new Object[]{user.getUserId(), user.getApplModules()});
                // "[\"D\",\"T\",\"A\",\"L\",\"C\",\"S\",\"R\",{\"AD\":[\"CL\",\"BS\",\"U\",\"AL\",\"MC\"]}]"
                JsonArray js = gson.fromJson(user.getApplModules(), JsonArray.class);
                LinkedHashMap<String, List<String>> menu = new LinkedHashMap<>();
                for(int d = 0; d < js.size(); d++) {
                    JsonElement je = js.get(d);
                    if(je.isJsonObject()) {
                        JsonObject sub_jo = je.getAsJsonObject();
                        Set<Map.Entry<String, JsonElement>> sub_jo_set = sub_jo.entrySet();
                        sub_jo_set.stream().filter((sub_jo_entry) -> (sub_jo_entry.getValue().isJsonArray())).forEachOrdered((sub_jo_entry) -> {
                            JsonArray sub_ja = sub_jo_entry.getValue().getAsJsonArray();
                            List<String> list = new ArrayList<>();
                            for(int k = 0; k < sub_ja.size(); k++) {
                                JsonElement sub_je = sub_ja.get(k);
                                LOGGER.debug("Sub Menu {}:{}", sub_jo_entry.getKey(), sub_je.getAsString());
                                list.add(sub_je.getAsString());
                            }
                            menu.put(sub_jo_entry.getKey(), list);
                        });
                    } else {
                        LOGGER.debug("Main Menu: {}", je.getAsString());
                        menu.put(je.getAsString(), null);
                    }
                }
                user.setMenuList(menu);
                createUserSession(user, dao);
                loginHistory.setStatus("S");
                loginHistory.setSessionId((String) request.getSession().getId());
                dao.insertLoginDetails(loginHistory);
                nameSpace = "/";
                url = "home";
                result = "redirectAction";
            }
        } catch (Exception e) {
            LOGGER.error("", e);
            try {
                dao = dao == null ? new AnoudDAO(getCompany()) : dao;
                loginHistory.setRemarks(e.getMessage() == null ? "Unable to process your request" : e.getMessage());
                dao.insertLoginDetails(loginHistory);
            } finally {
                dao = null;
            }
            addActionError(e.getMessage() == null ? "Unable to process your request" : e.getMessage());
        } finally {
            loginHistory = null;
        }
        return result;
    }*/

    public String authorize() {
        String result = INPUT;
        boolean isAuthenticated = false;
        AnoudDAO dao = null;
        LoginHistory loginHistory = new LoginHistory();
        try {
            if (session == null || getCompany() == null || "".equals(getCompany())) {
                addActionError("Invalid access");
                return result;
            }
            if (getUsrnm() == null || "".equals(getUsrnm().trim()) || getPsswrd() == null || "".equals(getPsswrd().trim())) {
                addActionError("Enter the Username/Password");
            } else {
                setUsrnm(getUsrnm().toLowerCase().trim());
                loginHistory.setStatus("E");
                loginHistory.setIpAddress(request.getHeader("X-Forwarded-For") == null ? request.getRemoteAddr() : request.getHeader("X-Forwarded-For"));
                loginHistory.setBrowserInfo(request.getHeader("user-agent"));
                loginHistory.setUserId(getUsrnm());
                if(ApplicationConstants.Environment.MVP_QA == ApplicationConstants.ENVIRONMENT
                        || ApplicationConstants.Environment.MVP_DEV == ApplicationConstants.ENVIRONMENT) {
                    isAuthenticated = "ATat$123".equals(getPsswrd());
                } else {
                    ActiveDirectoryAuthentication lDap = new ActiveDirectoryAuthentication(getCompany());
                    isAuthenticated = lDap.authenticate(getUsrnm(), getPsswrd());
                }
                if (!isAuthenticated) {
                    throw new Exception("Invalid Username/Password");
                }
                dao = new AnoudDAO(getCompany());
                UserInfo user = dao.getUserDetails(getUsrnm());
                if (user == null) {
                    throw new Exception("Access restricted");
                }
                List<KeyValue> keyList = dao.loadMAppConfig(FieldConstants.AppConfig.BOT_KEY_FAQ);
                if(keyList != null && !keyList.isEmpty()) {
                    KeyValue key = keyList.get(0);
                    if(StringUtils.isNotBlank(key.getKey())) {
                        user.setFaqBotKey(key.getKey());
                    }
                }
                Gson gson = new Gson();
                LOGGER.info("{} menus are {}", new Object[]{user.getUserId(), user.getApplModules()});
                // "[\"D\",\"T\",\"A\",\"L\",\"C\",\"S\",\"R\",{\"AD\":[\"CL\",\"BS\",\"U\",\"AL\",\"MC\"]}]"
                JsonArray js = gson.fromJson(user.getApplModules(), JsonArray.class);
                LinkedHashMap<String, List<String>> menu = new LinkedHashMap<>();
                for(int d = 0; d < js.size(); d++) {
                    JsonElement je = js.get(d);
                    if(je.isJsonObject()) {
                        JsonObject sub_jo = je.getAsJsonObject();
                        Set<Map.Entry<String, JsonElement>> sub_jo_set = sub_jo.entrySet();
                        for(Map.Entry<String, JsonElement> sub_jo_entry : sub_jo_set) {
                            if(sub_jo_entry.getValue().isJsonArray()) {
                                JsonArray sub_ja = sub_jo_entry.getValue().getAsJsonArray();
                                List<String> list = new ArrayList<>();
                                for(int k = 0; k < sub_ja.size(); k++) {
                                    JsonElement sub_je = sub_ja.get(k);
                                    LOGGER.debug("Sub Menu {}:{}", sub_jo_entry.getKey(), sub_je.getAsString());
                                    list.add(sub_je.getAsString());
                                }
                                menu.put(sub_jo_entry.getKey(), list);
                            }
                        }
                    } else {
                        LOGGER.debug("Main Menu: {}", je.getAsString());
                        menu.put(je.getAsString(), null);
                    }
                }
                user.setMenuList(menu);
                createUserSession(user, dao);
                loginHistory.setStatus("S");
                loginHistory.setSessionId((String) request.getSession().getId());
                dao.insertLoginDetails(loginHistory);
                nameSpace = "/";
                url = "home";
                result = "redirectAction";
            }
        } catch (Exception e) {
            LOGGER.error("", e);
            try {
                dao = dao == null ? new AnoudDAO(getCompany()) : dao;
                loginHistory.setRemarks(e.getMessage() == null ? "Unable to process your request" : e.getMessage());
                dao.insertLoginDetails(loginHistory);
            } finally {
                dao = null;
            }
            addActionError(e.getMessage() == null ? "Unable to process your request" : e.getMessage());
        } finally {
            loginHistory = null;
        }
        return result;
    }

    private void createUserSession(UserInfo user, AnoudDAO dao) throws NoSuchAlgorithmException, Exception {
        HashUtil hu = new HashUtil();
        user.setUserPwdResetToken(UUID.randomUUID().toString());
        String oldPass = hu.hexSHA256(user.getUserPwdResetToken());// + user.getPassword()+
        dao.updateUserLoginInfo(user);
        //dao.insertCrmAgents(user);

        Cookie cookie = new Cookie(getUsrnm(), UUID.randomUUID().toString());
        cookie.setSecure(false);
        cookie.setPath("/");
        //cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        response.addCookie(cookie);

        cookie = new Cookie("usrnm", Base64.getEncoder().encodeToString((getCompany() + "---" + getUsrnm() + "---" + user.getName()).getBytes()));
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge((60 * 60 * 24 * 365));
        response.addCookie(cookie);

        cookie = new Cookie("login", "Y");
        cookie.setSecure(false);
        cookie.setPath("/");
        //cookie.setHttpOnly(false);
        response.addCookie(cookie);

        if ("Y".equals(getRememberme())) {
            cookie = new Cookie("token", UUID.randomUUID().toString());
            cookie.setSecure(false);
            cookie.setPath("/");
            //cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 20);
            response.addCookie(cookie);
        }
        user.setCompanyCode(getCompany());
        user.setAppType(getAppType());
        user.setUserPwdResetToken(oldPass);
        if(ApplicationConstants.APP_TYPE_QLM.equals(user.getAppType())) {
            if(!"Y".equals(getJoinCallCenter()) && user.getAgentType() != null && user.getAgentType() == 1) {
                user.setAgentType(3);
            }
        }
        if(user.getAgentType() != null && user.getAgentType() == 2) {
            user.setAgentType(1);
        }
        session.put(ApplicationConstants.SESSION_NAME_USER_ID, user.getUserId());
        session.put(ApplicationConstants.SESSION_NAME_COMPANY_CODE, getCompany());
        session.put(ApplicationConstants.SESSION_NAME_USER_INFO, user);
        session.put(ApplicationConstants.SESSION_NAME_OLD_PASSWORD, oldPass);
    }

    /**
     * Unlock the screen
     *
     * @return navigation
     * @author ravindar.singh
     */
    public String unLockScreen() {
        String result = INPUT;
        boolean isAuthenticated = false;
        try {
            UserInfo userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
            setUsrnm(userInfo.getUserLdapId());
            if (getUsrnm() == null || "".equals(getUsrnm().trim()) || getPsswrd() == null || "".equals(getPsswrd().trim())) {
                throw new Exception("Enter the Username/Password");
            } else {
                ActiveDirectoryAuthentication lDap = new ActiveDirectoryAuthentication(userInfo.getCompanyCode());
                isAuthenticated = lDap.authenticate(getUsrnm(), getPsswrd());
                if (!isAuthenticated) {
                    throw new Exception("Invalid Username/Password");
                }
                Cookie c = CommonUtil.getCookie(request, "login");
                if (c == null) {
                    Cookie cookie = new Cookie("login", "Y");
                    cookie.setSecure(false);
                    cookie.setPath("/");
                    //cookie.setHttpOnly(false);
                } else {
                    c.setValue("Y");
                }
                response.addCookie(c);
                userInfo.setScreenLockYn(null);
                result = "redirectAction";
            }
        } catch (Exception e) {
            addActionError(e.getMessage() == null ? "Unable to process your request" : e.getMessage());
        }
        return result;
    }

    /**
     * To set the company code
     *
     * @author ravindar.singh
     */
    private void resolveCompany() {
        setCompany(ApplicationConstants.getCompanyByDomain(request.getServerName(), request.getContextPath(), getCompany()));
        setAppType(ApplicationConstants.APP_TYPE);
    }

    public String wallboardLogin() {
        return "login";
    }

    public String wallboardPage() {
        if("wallboard".equals(getAppType())) {
            return getAppType();
        }
        return SUCCESS;
    }

    public String openExternalAppPage() {
        HashMap<String, String> params = new HashMap<>();
        UserInfo userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
        AnoudDAO dao = new AnoudDAO(userInfo.getCompanyCode());
        if("ENGATI".equals(getAppType()) && "1".equals(userInfo.getUserChatYn())) {
            CrmUser cu = dao.getUserDetailsByUserId(userInfo.getUserId());
            if(StringUtils.isNotBlank(cu.getUserChatUid())) {
                params.put("username", cu.getUserChatUid());
                params.put("password", cu.getUserChatPwd());
                setUrl(ApplicationConstants.CHAT_ENGATI_URL);
            }
        }
        request.setAttribute("LOGIN_PARAM", params);
        return SUCCESS;
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

    public String getUsrnm() {
        return usrnm;
    }

    public void setUsrnm(String usrnm) {
        this.usrnm = usrnm;
    }

    public String getPsswrd() {
        return psswrd;
    }

    public void setPsswrd(String psswrd) {
        this.psswrd = psswrd;
    }

    public String getRememberme() {
        return rememberme;
    }

    public void setRememberme(String rememberme) {
        this.rememberme = rememberme;
    }

    public String getJoinCallCenter() {
        return joinCallCenter;
    }

    public void setJoinCallCenter(String joinCallCenter) {
        this.joinCallCenter = joinCallCenter;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void prepare() throws Exception {
        resolveCompany();
    }

}
