/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ravindar.singh
 */
public class CrmCallLog implements Cloneable, Serializable {

    private Long cclId;
    private String cclType;
    private Integer cclExtId;
    private String cclCallNo;
    private String cclCallDt;
    private Date cclCallDtTime;
    private Integer cclDuration;
    private String cclDurationDesc;
    private String cclFilePath;
    private String cclStatus;
    private String cclCrDt;
    private String cclCrUid;
    private String cclCrmType;
    private Long cclCallRefId;
    private String cclRemarks;

    private String cclCrmTypeDesc;
    private Long cclNotAnswered;
    private String cclCallCode;
    private String cclCivilId;
    private String cclRefId;
    private String cclRefName;
    private String cclFlex1;
    private String cclFlex2;
    private String cclFlex3;
    private String cclCrmId;
    private String cclCrmDesc;

    // DTO
    private String cclCallCodeDesc;
    private List<UserAssociations> userAssociations;

    //Ameyo feedBack
    private String cclFbLanguage;
    private String cclFbOption1;
    private String cclFbOption2;
    private String cclFbOption3;

    public Long getCclId() {
        return cclId;
    }

    public void setCclId(Long cclId) {
        this.cclId = cclId;
    }

    /**
     * I – Incoming / D- Dial from Dialer
     *
     * @return Call type
     */
    public String getCclType() {
        return cclType;
    }

    public void setCclType(String cclType) {
        this.cclType = cclType;
    }

    public Integer getCclExtId() {
        return cclExtId;
    }

    public void setCclExtId(Integer cclExtId) {
        this.cclExtId = cclExtId;
    }

    public String getCclCallNo() {
        return cclCallNo;
    }

    public void setCclCallNo(String cclCallNo) {
        this.cclCallNo = cclCallNo;
    }

    public String getCclCallDt() {
        return cclCallDt;
    }

    public void setCclCallDt(String cclCallDt) {
        this.cclCallDt = cclCallDt;
    }

    public Date getCclCallDtTime() {
        return cclCallDtTime;
    }

    public void setCclCallDtTime(Date cclCallDtTime) {
        this.cclCallDtTime = cclCallDtTime;
    }

    public Integer getCclDuration() {
        return cclDuration;
    }

    public void setCclDuration(Integer cclDuration) {
        this.cclDuration = cclDuration;
    }

    public String getCclDurationDesc() {
        return cclDurationDesc;
    }

    public void setCclDurationDesc(String cclDurationDesc) {
        this.cclDurationDesc = cclDurationDesc;
    }

    public String getCclFilePath() {
        return cclFilePath;
    }

    public void setCclFilePath(String cclFilePath) {
        this.cclFilePath = cclFilePath;
    }

    public String getCclStatus() {
        return cclStatus;
    }

    public void setCclStatus(String cclStatus) {
        this.cclStatus = cclStatus;
    }

    public String getCclCrDt() {
        return cclCrDt;
    }

    public void setCclCrDt(String cclCrDt) {
        this.cclCrDt = cclCrDt;
    }

    public String getCclCrUid() {
        return cclCrUid;
    }

    public void setCclCrUid(String cclCrUid) {
        this.cclCrUid = cclCrUid;
    }

    public String getCclCrmType() {
        return cclCrmType;
    }

    public void setCclCrmType(String cclCrmType) {
        this.cclCrmType = cclCrmType;
    }

    public Long getCclCallRefId() {
        return cclCallRefId;
    }

    public void setCclCallRefId(Long cclCallRefId) {
        this.cclCallRefId = cclCallRefId;
    }

    public String getCclCrmTypeDesc() {
        return cclCrmTypeDesc;
    }

    public void setCclCrmTypeDesc(String cclCrmTypeDesc) {
        this.cclCrmTypeDesc = cclCrmTypeDesc;
    }

    public Long getCclNotAnswered() {
        return cclNotAnswered;
    }

    public void setCclNotAnswered(Long cclNotAnswered) {
        this.cclNotAnswered = cclNotAnswered;
    }

    public String getCclRemarks() {
        return cclRemarks;
    }

    public void setCclRemarks(String cclRemarks) {
        this.cclRemarks = cclRemarks;
    }

    @Override
    public CrmCallLog clone() throws CloneNotSupportedException {
        return (CrmCallLog) super.clone();
    }

    public String getCclCallCode() {
        return cclCallCode;
    }

    public void setCclCallCode(String cclCallCode) {
        this.cclCallCode = cclCallCode;
    }

    public String getCclCivilId() {
        return cclCivilId;
    }

    public void setCclCivilId(String cclCivilId) {
        this.cclCivilId = cclCivilId;
    }

    public String getCclRefId() {
        return cclRefId;
    }

    public void setCclRefId(String cclRefId) {
        this.cclRefId = cclRefId;
    }

    public String getCclRefName() {
        return cclRefName;
    }

    public void setCclRefName(String cclRefName) {
        this.cclRefName = cclRefName;
    }

    public String getCclFlex1() {
        return cclFlex1;
    }

    public void setCclFlex1(String cclFlex1) {
        this.cclFlex1 = cclFlex1;
    }

    public String getCclFlex2() {
        return cclFlex2;
    }

    public void setCclFlex2(String cclFlex2) {
        this.cclFlex2 = cclFlex2;
    }

    public String getCclFlex3() {
        return cclFlex3;
    }

    public void setCclFlex3(String cclFlex3) {
        this.cclFlex3 = cclFlex3;
    }

    public String getCclCrmId() {
        return cclCrmId;
    }

    public void setCclCrmId(String cclCrmId) {
        this.cclCrmId = cclCrmId;
    }

    public String getCclCrmDesc() {
        return cclCrmDesc;
    }

    public void setCclCrmDesc(String cclCrmDesc) {
        this.cclCrmDesc = cclCrmDesc;
    }

    public String getCclCallCodeDesc() {
        return cclCallCodeDesc;
    }

    public void setCclCallCodeDesc(String cclCallCodeDesc) {
        this.cclCallCodeDesc = cclCallCodeDesc;
    }

    public List<UserAssociations> getUserAssociations() {
        return userAssociations;
    }

    public void setUserAssociations(List<UserAssociations> userAssociations) {
        this.userAssociations = userAssociations;
    }

    public String getCclFbLanguage() {
        return cclFbLanguage;
    }

    public void setCclFbLanguage(String cclFbLanguage) {
        this.cclFbLanguage = cclFbLanguage;
    }

    public String getCclFbOption1() {
        return cclFbOption1;
    }

    public void setCclFbOption1(String cclFbOption1) {
        this.cclFbOption1 = cclFbOption1;
    }

    public String getCclFbOption2() {
        return cclFbOption2;
    }

    public void setCclFbOption2(String cclFbOption2) {
        this.cclFbOption2 = cclFbOption2;
    }

    public String getCclFbOption3() {
        return cclFbOption3;
    }

    public void setCclFbOption3(String cclFbOption3) {
        this.cclFbOption3 = cclFbOption3;
    }


    public class UserAssociations {

        private String userId;
        private String associtionType;
        private String dispositionCode;
        private String associationId;
        // DTO
        private String crmType;
        private Long cclId;

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAssocitionType() {
            return this.associtionType;
        }

        public void setAssocitionType(String associtionType) {
            this.associtionType = associtionType;
        }

        public String getDispositionCode() {
            return this.dispositionCode;
        }

        public void setDispositionCode(String dispositionCode) {
            this.dispositionCode = dispositionCode;
        }

        public String getAssociationId() {
            return this.associationId;
        }

        public void setAssociationId(String associationId) {
            this.associationId = associationId;
        }

        public String getCrmType() {
            return this.crmType;
        }

        public void setCrmType(String crmType) {
            this.crmType = crmType;
        }

        public Long getCclId() {
            return cclId;
        }

        public void setCclId(Long cclId) {
            this.cclId = cclId;
        }

        @Override
        public String toString() {
            return "UserAssociations{" + "userId=" + userId + ", associtionType=" + associtionType + ", dispositionCode=" + dispositionCode + ", associationId=" + associationId + ", crmType=" + crmType + '}';
        }
    }
}
