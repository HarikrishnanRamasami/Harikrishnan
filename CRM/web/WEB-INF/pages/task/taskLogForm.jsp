<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : taskLogForm
    Created on : Mar 14, 2017, 11:54:54 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%--div class="modal-content"--%>
<div class="modal-header">
    <h4 class="modal-title">Log Task Form</h4>
    <div  style="margin-top: -31px; float: right;">
        <button class="save-btn btn btn-primary" id="btn_logtask_save">&#10004;<s:text name="btn.save"/></button>
        <button class="close-btn btn" data-dismiss="modal" id="btn_logtask_close">&#10006; <s:text name="btn.close"/></button>
    </div>
</div>
<div class="body" style="padding: 7px;">
    <div id="msg_logtask" class="alert alert-danger" style="display: none;"></div>
    <s:form id="frm_logtask" name="frm_logtask" method="post" theme="simple">
        <s:hidden name="operation" id="operation" />
        <s:hidden name="tasksLog.ctlId" />
        <s:hidden name="tasksLog.ctlCtId" />
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
                <label><s:text name="lbl.status"/> </label>
                <div class="form-group">
                    <s:select name="tasksLog.ctlStatus" id="ctlStatus" list="statusList" listKey="key" listValue="value" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.status')}"/>
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <label><s:text name="lbl.task.assigned.to"/> <span>*</span></label>
                <div class="form-group">
                    <s:select name="tasksLog.task.ctAssignedTo" id="ctAssignedTo" list="assignToList" listKey="key" listValue="value" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.task.assigned.to')}" cssClass="form-control" />
                </div>
            </div>   
            <%--div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <label>Remind Before</label>
                <div class="form-group">
                    <div class="form-line">
                        <s:textfield name="tasksLog.task.ctRemindBefore" id="ctRemindBefore" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="Remind Before"/>
                    </div>
                </div>
            </div--%>
            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <label><s:text name="lbl.due.date"/></label>
                <div class="form-group">
                    <div class="form-line">
                        <s:textfield name="tasksLog.task.ctDueDate" id="ctDueDate" cssClass="form-control datepicker calicon" placeholder="DD/MM/YYYY" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.due.date')}"/>
                    </div>
                </div>
            </div> 
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 required">
                <label><s:text name="lbl.remarks"/> <span>*</span></label>
                <div class="form-group form-float">
                    <div class="form-line">
                        <s:textarea name="tasksLog.ctlDetails" id="ctlDetails" data-toggle="tooltip" data-placement="top" cssClass="form-control" title="%{getText('lbl.common.please.enter.remarks')}"/>
                    </div>
                    <div class="help-info"><span id="textarea_feedback"></span></div>
                </div>
            </div> 
        </div>
    </s:form>
</div>
<%--/div--%>

<script type="text/javascript">

//    $('#ctDueDate').bootstrapMaterialDatePicker({
//        format: 'DD/MM/YYYY',
//        clearButton: true,
//        weekStart: 1,
//        time: false
//    });
    $(function () {
        $(".datepicker").datepicker({format: 'dd/mm/yyyy'});


    });

    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        $('#frm_logtask').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            rules: {
                "tasksLog.ctlDetails": {
                    required: true
                },
                "tasksLog.ctlStatus": {
                    required: true
                },
            },
            messages: {
                "tasksLog.ctlDetails": {
                    required: "Please Enter Remarks"
                },
                "tasksLog.ctlStatus": {
                    required: "Please Select Status"
                },
            },
            highlight: function (element) {
                $(element).removeClass('error');
            },
            showErrors: function (errorMap, errorList) {
                if (errorList.length)
                {
                    var s = errorList.shift();
                    var n = [];
                    n.push(s);
                    this.errorList = n;
                }
                if (this.numberOfInvalids())
                    this.defaultShowErrors();
                else
                    $("#msg_logtask").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_logtask").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_logtask").show();
                else
                    $("#msg_logtask").hide();
            }
        });
        $("#btn_logtask_save").on("click", function () {
            if (!$('#frm_logtask').valid()) {
                return false;
            }
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveTaskEntryLogForm.do",
                data: $("#frm_logtask").serialize(),
                success: function (result) {
                    if (result.messageType === "S") {
                        //$("#frm_logtask").removeClass("alert-danger").addClass("alert-success").html("Saved Successfully").show();
                        $('#plugin_modal_dialog').modal('hide');
                        $.notify("Saved Successfully", "custom");
                        //reloadDataTable("log_task_tbl");
                        reloadDt("log_task_tbl");
                        $("#task_tbl").DataTable().ajax.url(APP_CONFIG.context + "/loadTaskEntryData.do?" + $('#frm_search').serialize()).load();
                    } else {
                        $('#frm_logtask').empty().html(result.message).show();
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        });
    });
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>