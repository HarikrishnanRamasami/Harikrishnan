<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : taskForm
    Created on : Mar 14, 2017, 11:54:41 AM
    Author     : ravindar.singh
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<style>
    .dtp{
        z-index: 200000;
    }
</style>
<a href="#" class="close-link">&#10006;</a>
<div class="task-form">
    <div class="popup-title">
        <h3><s:text name="lbl.task.head.task.form"/></h3>
        <div style="float:right;" class="">
            <s:if test='!"view".equals(operation)'>
                <button class="save-btn btn btn-primary" id="btn_task_save">&#10004;<s:text name="btn.save"/></button>
            </s:if>
            <button class="close-btn btn" data-dismiss="modal">&#10006; <s:text name="btn.close"/></button>
        </div><hr>
    </div>
    <div id="msg_task" class="alert alert-danger" style="display: none;"></div>
    <s:form id="frm_task" name="frm_task" method="post" theme="simple" enctype="multipart/form-data">
        <s:hidden name="company" id="company" />
        <s:hidden name="operation" id="operation" />
        <s:hidden name="task.ctId"/>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="task.ctModule"  id="ctModule" list="moduleList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.module')}" />
                <label class="textboxlabel"><s:text name="lbl.common.module"/><span>*</span></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="task.ctPriority"  id="ctPriority" list="priorityList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.priority')}"/>
                <label class="textboxlabel"><s:text name="lbl.priority"/><span>*</span></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="task.ctCatg"  id="ctCatg" list="categoryList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" onchange="loadDropDown(this.value, 'ctSubCatg');" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.category')}"/>
                <label class="textboxlabel"><s:text name="lbl.category"/><span>*</span></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="task.ctSubCatg" id="ctSubCatg" list="aaData" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.sub.category')}"/>
                <label class="textboxlabel"><s:text name="lbl.sub.category"/><span>*</span></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="ctRefId" name="task.ctRefId" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.refid')}"></s:textfield>
                <label class="textboxlabel"><s:text name="lbl.civil.id"/></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="ctRefNo" name="task.ctRefNo" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.refno')}"></s:textfield>
                <label class="textboxlabel"><s:text name="lbl.task.quote.policy.claim.refno"/></label>
                </div>
            </div>
        <s:if test='!"add".equals(operation)'>      
            <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:select name="task.ctStatus" id="ctStatus" list="statusList" listKey="key" listValue="value" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.status')}"/>
                    <label class="textboxlabel"><s:text name="lbl.status"/></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:select name="task.ctCloseCode" id="ctCloseCode"  list="statusRemarksList" listKey="key" listValue="value" headerKey="" headerValue="" disabled="true" cssClass="textbox"  data-toggle="tooltip" data-placement="top" title="%{getText('lbl.task.status.close.remarks')}"/>
                    <label class="textboxlabel"><s:text name="lbl.task.status.close.remark"/></label>
                </div>
            </div>
        </s:if>

        <div class="form-fields clearfix">
            <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                <s:textfield id="ctSubject" name="task.ctSubject"  cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.subject')}"></s:textfield>
                <label class="textboxlabel"><s:text name="lbl.subject"/><span>*</span></label>
                </div>
            </div>
            <div class="form-fields clearfix">
                <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                <s:textarea name="task.ctMessage" id="ctMessage" title="%{getText('lbl.please.enter.message')}" class="textbox"/>
                <label class="textboxlabel"><s:text name="lbl.message"/><span>*</span></label>
            </div>
        </div>

        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="task.ctDueDate" id="datepicker" class="textbox calicon" placeholder="DD/MM/YYYY HH:mm" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.due.date')}"/>
                <label class="textboxlabel"><s:text name="lbl.due.date"/></label>
            </div>
            <%--s:if test='!"add".equals(operation)'>      

                        <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">

                            <s:select name="task.ctRemindBefore" id="ctRemindBefore" cssClass="textbox" list="hoursRangeList" data-toggle="tooltip" data-placement="top" title="Remind Before Hours"/>

                            <label class="textboxlabel">Remind Before Hours</label>
                        </div>
                    </s:if--%>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="ctFlex01" name="task.ctFlex01" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.mobile.no')}"></s:textfield>
                <label class="textboxlabel"><s:text name="lbl.mobile.no"/></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="task.ctAssignedTo" id="ctAssignedTo" list="assignToList" listKey="key" listValue="value" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.task.assigned.to')}"/>
                <label class="textboxlabel"><s:text name="lbl.task.assigned.to"/><span>*</span></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <input type="checkbox" name="task.ctCreateActivityYn" id="chk_add_activity" value="1" class="filled-in chk-col-deep-orange">
                <label for="chk_add_activity" required="true">
                    <label><s:text name="lbl.task.create.activity"/></label></label>
            </div>
        </div>
        <s:if test='!"view".equals(operation)'>
        <div class="row">                             
            <div class="col-md-12 form-group">
                <s:action name="loadFileUpload"  executeResult="true" ignoreContextParams="true">
                    <s:param name="formId">frm_task</s:param>
                     <s:param name="count">4</s:param>
                </s:action> 
            </div>
        </div>
        </s:if>
        <div class="col-md-12" id="block_task_attach">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th><s:text name="lbl.common.srno"/>.</th>
                        <th><s:text name="lbl.file.name"/></th>
                        <th><s:text name="lbl.action"/></th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </s:form>
</div>
<%--s:if test='"edit".equals(operation)'>
    <s:include value="/WEB-INF/pages/task/taskLog.jsp"></s:include>
</s:if--%>

<!--/div-->

<script type="text/javascript">

    $(function () {
        //  $('#overflow').slimScroll();

        $('#sidebar').slimScroll({
            height: '100%',
            width: '100%',
        });
        $('.leads-tab').click(function () {
            $('.popup-wrap').addClass('popup-open');
            $('#overlay').show();

        });
        $('.close-link').click(function () {
            $('.popup-wrap').removeClass('popup-open');
            $('#overlay').hide();
        });
        $('.close-btn').click(function () {
            $('.popup-wrap').removeClass('popup-open');
            $('#overlay').hide();
        });
    });
</script>
<script type="text/javascript">
    <s:if test='"view".equals(operation)'>
    $("#frm_task").find('input, textarea, button, select').attr('disabled', 'disabled');
    </s:if>
    /*$('#ctDueDate').bootstrapMaterialDatePicker({
     format: 'DD/MM/YYYY',
     clearButton: true,
     weekStart: 1,
     time: false
     });*/

    $("#frm_task #ctStatus").on("change", function () {
        if ($(this).val() === "C") {
            $("#frm_task #ctCloseCode").removeAttr("disabled");
        } else {
            $("#frm_task #ctCloseCode").attr("disabled", true);
        }
        //$("#ctFlex01").selectpicker('refresh');
    });
    //$("#ctFlex01").selectpicker('refresh');

    function loadDropDown(val, id) {
        $("#" + id).html("<option value=''>--Select-</option>");
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/loadTaskEntrySubCategoryList.do",
            data: {"task.ctCatg": val, "company": $("#company").val()},
            success: function (data) {
                var opt = '<option value="">--Select-</option>';
                $.each(data.aaData, function (i, d) {
                    opt += '<option value="' + d.key + '">' + d.value + '</option>';
                });
                $('#' + id).html(opt);
                //$('#' + id).selectpicker('refresh');
            }
        });
    }
    $(document).ready(function () {
        var addOptions = {
            url: APP_CONFIG.context + "/saveTaskEntryForm.do",
            type: "POST",
            success: tasksaveCallback,
            complete: function () {
            },
            error: function (xhr, status, error) {
                alert(error);
            }
        };
        $('#frm_task').ajaxForm(addOptions);

        //$("#datepicker").datepicker({format: 'dd/mm/yyyy'});
        $('#datepicker').bootstrapMaterialDatePicker({format: 'DD/MM/YYYY HH:mm'});
    <s:if test='"add".equals(operation)'>
        $("#ctModule").val('000');
        $("#ctPriority").val('N');
        $('#ctModule').attr("style", "pointer-events: none;");
    </s:if>
        $('[data-toggle="tooltip"]').tooltip();
        $('#frm_task').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            rules: {
                "task.ctModule": {
                    required: true
                },
                "task.ctPriority": {
                    required: true
                },
                "task.ctCatg": {
                    required: true
                },
                "task.ctSubCatg": {
                    required: true,
                },
                "task.ctSubject": {
                    required: true,
                },
                "task.ctMessage": {
                    required: true,
                },
                "task.ctAssignedTo": {
                    required: true
                },
            },
            messages: {
                "task.ctModule": {
                    required: "Please Select Module"
                },
                "task.ctPriority": {
                    required: "Please Select Priority"
                },
                "task.ctCatg": {
                    required: "Please Select Catagory"
                },
                "task.ctSubCatg": {
                    required: "Please Select Sub Category"
                },
                "task.ctSubject": {
                    required: "Please Select Subject"
                },
                "task.ctMessage": {
                    required: "Please Select Message"
                },
                "task.ctAssignedTo": {
                    required: "Please Select AssignedTo"
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
                    $("#msg_task").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_task").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_task").show();
                else
                    $("#msg_task").hide();
            }
        });

        $("#btn_task_save").on("click", function () {
            if (!$('#frm_task').validate()) {
                return false;
            }
            $('#frm_task').submit();
        });
    });

    function tasksaveCallback(result, statusText, xhr, $form) {        
        if (result.messageType === "S") {
            $('.popup-wrap').removeClass('popup-open');
            $('#overlay').hide();
            $.notify("Task created successfully", "custom");
            $("#task_tbl").DataTable().ajax.reload();
        } else {
            $('#msg_task').empty().html(result.message).show();
            setTimeout(function () {
                $('#msg_task').hide();
            }, 2000);
        }
    }

    function getTaskdDataTableUrl() {  
        return APP_CONFIG.context + "/loadTaskEntryData.do?" + $('#frm_search').serialize();      
    }
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>
