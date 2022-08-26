/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.vo;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.util.TypeConstants;

/**
 *
 * @author ravindar.singh
 */
public class UserInfo implements Serializable, Principal {

    private String userId;
    private String password;
    private String userLdapId;
    private String userName;
    private Integer userAdminYn;
    private String companyCode;
    private TypeConstants.Menu activeMenu;
    private Map<String, String> tokens = new HashMap<>();
    private String screenLockYn;
    private Integer userTelNo;
    private List<KeyValue> applCompanyList;
    private String userPwdResetToken;
    private String userTeam;
    private Integer userRoleSeq;
    /**
     * R - Retail Q - QLM
     */
    private String appType;
    /**
     * 1 - Call center, 2 - Department Team, 3 - IT Admin
     */
    private Integer agentType;
    private String userChatYn;
    private Map<String, List<String>> menuList;
    private String applModules;
    private String faqBotKey;
    private List<KeyValue> appTypeList;
    private List<KeyValue> appTypeMinList;
    private Set<String> appList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserLdapId() {
        return userLdapId;
    }

    public void setUserLdapId(String userLdapId) {
        this.userLdapId = userLdapId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAdminYn() {
        return userAdminYn;
    }

    public void setUserAdminYn(Integer userAdminYn) {
        this.userAdminYn = userAdminYn;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public TypeConstants.Menu getActiveMenu() {
        return activeMenu;
    }

    public void setActiveMenu(TypeConstants.Menu activeMenu) {
        this.activeMenu = activeMenu;
    }

    public Map<String, String> getTokens() {
        return tokens;
    }

    public void setTokens(Map<String, String> tokens) {
        this.tokens = tokens;
    }

    public String getScreenLockYn() {
        return screenLockYn;
    }

    public void setScreenLockYn(String screenLockYn) {
        this.screenLockYn = screenLockYn;
    }

    public Integer getUserTelNo() {
        return userTelNo;
    }

    public void setUserTelNo(Integer userTelNo) {
        this.userTelNo = userTelNo;
    }

    public String getUserPwdResetToken() {
        return userPwdResetToken;
    }

    public void setUserPwdResetToken(String userPwdResetToken) {
        this.userPwdResetToken = userPwdResetToken;
    }

    public String getUserTeam() {
        return userTeam;
    }

    public void setUserTeam(String userTeam) {
        this.userTeam = userTeam;
    }

    public Integer getUserRoleSeq() {
        return userRoleSeq;
    }

    public void setUserRoleSeq(Integer userRoleSeq) {
        this.userRoleSeq = userRoleSeq;
    }

    public List<KeyValue> getApplCompanyList() {
        return applCompanyList;
    }

    public void setApplCompanyList(List<KeyValue> applCompanyList) {
        this.applCompanyList = applCompanyList;
    }

    @Override
    public String getName() {
        return userName;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public Integer getAgentType() {
        return agentType;
    }

    public void setAgentType(Integer agentType) {
        this.agentType = agentType;
    }

    public String getUserChatYn() {
        return userChatYn;
    }

    public void setUserChatYn(String userChatYn) {
        this.userChatYn = userChatYn;
    }

    public Map<String, List<String>> getMenuList() {
        return menuList;
    }

    public void setMenuList(Map<String, List<String>> menuList) {
        this.menuList = menuList;
    }

    public String getApplModules() {
        return applModules;
    }

    public void setApplModules(String applModules) {
        this.applModules = applModules;
    }

    public String getFaqBotKey() {
        return faqBotKey;
    }

    public void setFaqBotKey(String faqBotKey) {
        this.faqBotKey = faqBotKey;
    }

    public List<KeyValue> getAppTypeList() {
        return appTypeList;
    }

    public void setAppTypeList(List<KeyValue> appTypeList) {
        this.appTypeList = appTypeList;
    }

    public List<KeyValue> getAppTypeMinList() {
        return appTypeMinList;
    }

    public void setAppTypeMinList(List<KeyValue> appTypeMinList) {
        this.appTypeMinList = appTypeMinList;
    }

    public Set<String> getAppList() {
        return appList;
    }

    public void setAppList(Set<String> appList) {
        this.appList = appList;
    }

    public String getAmeyoChannel() {
        String channel = "N";
        if (appType != null && companyCode != null) {
            switch (appType + "" + companyCode) {
                case "R001":
                case "Q009":
                    channel = "QIC_Qatar";
                    break;
                case "R002":
                    channel = "QIC_UAE";
                    break;
                case "R005":
                    break;
                case "R006":
                case "Q006":
                    channel = "QIC_Oman";
                    break;
                case "R100":
                    channel = "QICBeema16_BeemaDamanCS";
                    break;
            }
        }
        return channel;
    }
}
