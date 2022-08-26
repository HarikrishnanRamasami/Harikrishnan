/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ravindar.singh
 */
public class CrmUser implements Serializable {

    private String userId;
    private String userName;
    private String userTelNo;
    private String userEmailId;
    private Long userMobileNo;
    private String userAdministratorYn;
    private String userCrmAgentYn;
    private String userLdapUid;
    private String userTeam;
    private String userTeamDesc;
    private Integer userRoleSeq;
    private String userRoleSeqDesc;
    private String userStaticIp;
    private String userLockYn;
    private Integer userMaxSession;
    private String userLdapId;
    private String userCreateId;
    private Date userLastLoginDt;
    private String userAgentType;
    private String userLoginStatus;
    private String userPwdResetToken;
    private String userChatUid;
    private String userChatPwd;
    private String userApplModules;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTelNo() {
        return userTelNo;
    }

    public void setUserTelNo(String userTelNo) {
        this.userTelNo = userTelNo;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public Long getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(Long userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getUserAdministratorYn() {
        return userAdministratorYn;
    }

    public void setUserAdministratorYn(String userAdministratorYn) {
        this.userAdministratorYn = userAdministratorYn;
    }

    public String getUserCrmAgentYn() {
        return userCrmAgentYn;
    }

    public void setUserCrmAgentYn(String userCrmAgentYn) {
        this.userCrmAgentYn = userCrmAgentYn;
    }

    public String getUserLdapUid() {
        return userLdapUid;
    }

    public void setUserLdapUid(String userLdapUid) {
        this.userLdapUid = userLdapUid;
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

    public String getUserStaticIp() {
        return userStaticIp;
    }

    public void setUserStaticIp(String userStaticIp) {
        this.userStaticIp = userStaticIp;
    }

    public String getUserLockYn() {
        return userLockYn;
    }

    public void setUserLockYn(String userLockYn) {
        this.userLockYn = userLockYn;
    }

    public Integer getUserMaxSession() {
        return userMaxSession;
    }

    public void setUserMaxSession(Integer userMaxSession) {
        this.userMaxSession = userMaxSession;
    }

    public String getUserLdapId() {
        return userLdapId;
    }

    public void setUserLdapId(String userLdapId) {
        this.userLdapId = userLdapId;
    }

    public String getUserCreateId() {
        return userCreateId;
    }

    public void setUserCreateId(String userCreateId) {
        this.userCreateId = userCreateId;
    }

    public String getUserTeamDesc() {
        return userTeamDesc;
    }

    public void setUserTeamDesc(String userTeamDesc) {
        this.userTeamDesc = userTeamDesc;
    }

    public String getUserRoleSeqDesc() {
        return userRoleSeqDesc;
    }

    public void setUserRoleSeqDesc(String userRoleSeqDesc) {
        this.userRoleSeqDesc = userRoleSeqDesc;
    }

    public Date getUserLastLoginDt() {
        return userLastLoginDt;
    }

    public void setUserLastLoginDt(Date userLastLoginDt) {
        this.userLastLoginDt = userLastLoginDt;
    }

    public String getUserAgentType() {
        return userAgentType;
    }

    public void setUserAgentType(String userAgentType) {
        this.userAgentType = userAgentType;
    }

    public String getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(String userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public String getUserPwdResetToken() {
        return userPwdResetToken;
    }

    public void setUserPwdResetToken(String userPwdResetToken) {
        this.userPwdResetToken = userPwdResetToken;
    }

    public String getUserChatUid() {
        return userChatUid;
    }

    public void setUserChatUid(String userChatUid) {
        this.userChatUid = userChatUid;
    }

    public String getUserChatPwd() {
        return userChatPwd;
    }

    public void setUserChatPwd(String userChatPwd) {
        this.userChatPwd = userChatPwd;
    }

    /**
     * @return the userApplModules
     */
    public String getUserApplModules() {
        return userApplModules;
    }

    /**
     * @param userApplModules the userApplModules to set
     */
    public void setUserApplModules(String userApplModules) {
        this.userApplModules = userApplModules;
    }

}
