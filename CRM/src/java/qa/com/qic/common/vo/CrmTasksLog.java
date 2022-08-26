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
public class CrmTasksLog implements Serializable {

    private transient CrmTasks task;
    private Long ctlId;
    private Long ctlCtId;
    private String ctlDetails;
    private String ctlStatus;
    private String ctlCrUid;
    private Date ctlCrDt;
    private String ctlFlex01;
    private String ctlFlex02;

    public CrmTasks getTask() {
        return task;
    }

    public void setTask(CrmTasks task) {
        this.task = task;
    }

    public Long getCtlId() {
        return ctlId;
    }

    public void setCtlId(Long ctlId) {
        this.ctlId = ctlId;
    }

    public Long getCtlCtId() {
        return ctlCtId;
    }

    public void setCtlCtId(Long ctlCtId) {
        this.ctlCtId = ctlCtId;
    }

    public String getCtlDetails() {
        return ctlDetails;
    }

    public void setCtlDetails(String ctlDetails) {
        this.ctlDetails = ctlDetails;
    }

    public String getCtlStatus() {
        return ctlStatus;
    }

    public void setCtlStatus(String ctlStatus) {
        this.ctlStatus = ctlStatus;
    }

    public String getCtlCrUid() {
        return ctlCrUid;
    }

    public void setCtlCrUid(String ctlCrUid) {
        this.ctlCrUid = ctlCrUid;
    }

    public Date getCtlCrDt() {
        return ctlCrDt;
    }

    public void setCtlCrDt(Date ctlCrDt) {
        this.ctlCrDt = ctlCrDt;
    }

    public String getCtlFlex01() {
        return ctlFlex01;
    }

    public void setCtlFlex01(String ctlFlex01) {
        this.ctlFlex01 = ctlFlex01;
    }

    public String getCtlFlex02() {
        return ctlFlex02;
    }

    public void setCtlFlex02(String ctlFlex02) {
        this.ctlFlex02 = ctlFlex02;
    }
}
