/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.mc.model;

import java.util.Date;

/**
 *
 * @author ravindar.singh
 */
public class MMktgCampaignPath implements java.io.Serializable {

    private Long mcpPathId;
    private Long mcpCampId;
    private Long mcpFilterId;
    private String mcpPathName;
    private String mcpCrUid;
    private Date mcpCrDt;
    private String mcpUpdUid;
    private Date mcpUpdDt;
    private String filterName;

    public MMktgCampaignPath() {
    }

    public Long getMcpPathId() {
        return mcpPathId;
    }

    public void setMcpPathId(Long mcpPathId) {
        this.mcpPathId = mcpPathId;
    }

    public Long getMcpCampId() {
        return mcpCampId;
    }

    public void setMcpCampId(Long mcpCampId) {
        this.mcpCampId = mcpCampId;
    }

    public Long getMcpFilterId() {
        return mcpFilterId;
    }

    public void setMcpFilterId(Long mcpFilterId) {
        this.mcpFilterId = mcpFilterId;
    }

    public String getMcpPathName() {
        return mcpPathName;
    }

    public void setMcpPathName(String mcpPathName) {
        this.mcpPathName = mcpPathName;
    }

    public String getMcpCrUid() {
        return mcpCrUid;
    }

    public void setMcpCrUid(String mcpCrUid) {
        this.mcpCrUid = mcpCrUid;
    }

    public Date getMcpCrDt() {
        return mcpCrDt;
    }

    public void setMcpCrDt(Date mcpCrDt) {
        this.mcpCrDt = mcpCrDt;
    }

    public String getMcpUpdUid() {
        return mcpUpdUid;
    }

    public void setMcpUpdUid(String mcpUpdUid) {
        this.mcpUpdUid = mcpUpdUid;
    }

    public Date getMcpUpdDt() {
        return mcpUpdDt;
    }

    public void setMcpUpdDt(Date mcpUpdDt) {
        this.mcpUpdDt = mcpUpdDt;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

}
