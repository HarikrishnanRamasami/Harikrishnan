/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.mc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import qa.com.qic.anoud.vo.KeyValue;

/**
 *
 * @author ravindar.singh
 */
public class MMktgCampaignDataMap implements java.io.Serializable {

    private Long mcdmCampId;
    private String mcdmDataCol;
    private String mcdmDataType;
    private String mcdmDataName;
    private String mcdmCrUid;
    private Date mcdmCrDt;
    private String mcdmUpdUid;
    private Date mcdmUpdDt;
    private String mcdmSampleData;

     private List<KeyValue> dataTypeList = new ArrayList<>();

    public MMktgCampaignDataMap() {
    }

    public Long getMcdmCampId() {
        return mcdmCampId;
    }

    public void setMcdmCampId(Long mcdmCampId) {
        this.mcdmCampId = mcdmCampId;
    }

    public String getMcdmDataCol() {
        return mcdmDataCol;
    }

    public void setMcdmDataCol(String mcdmDataCol) {
        this.mcdmDataCol = mcdmDataCol;
    }

    public String getMcdmDataType() {
        return mcdmDataType;
    }

    public void setMcdmDataType(String mcdmDataType) {
        this.mcdmDataType = mcdmDataType;
    }

    public String getMcdmDataName() {
        return mcdmDataName;
    }

    public void setMcdmDataName(String mcdmDataName) {
        this.mcdmDataName = mcdmDataName;
    }

    public String getMcdmCrUid() {
        return mcdmCrUid;
    }

    public void setMcdmCrUid(String mcdmCrUid) {
        this.mcdmCrUid = mcdmCrUid;
    }

    public Date getMcdmCrDt() {
        return mcdmCrDt;
    }

    public void setMcdmCrDt(Date mcdmCrDt) {
        this.mcdmCrDt = mcdmCrDt;
    }

    public String getMcdmUpdUid() {
        return mcdmUpdUid;
    }

    public void setMcdmUpdUid(String mcdmUpdUid) {
        this.mcdmUpdUid = mcdmUpdUid;
    }

    public Date getMcdmUpdDt() {
        return mcdmUpdDt;
    }

    public void setMcdmUpdDt(Date mcdmUpdDt) {
        this.mcdmUpdDt = mcdmUpdDt;
    }

    public List<KeyValue> getDataTypeList() {
        return dataTypeList;
    }

    public void setDataTypeList(List<KeyValue> dataTypeList) {
        this.dataTypeList = dataTypeList;
    }

    public String getMcdmSampleData() {
        return mcdmSampleData;
    }

    public void setMcdmSampleData(String mcdmSampleData) {
        this.mcdmSampleData = mcdmSampleData;
    }

}
