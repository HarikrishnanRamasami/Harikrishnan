<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : activityForm
    Created on : Feb 28, 2017, 6:25:15 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="modal-header">
    <h4 class="modal-title"><span><img src="<%=request.getContextPath()%>/images/notepad.png" class="img-responsive" style="max-width:32px; display: inline-block;" alt="Activity"></span> <s:text name="lbl.activities"/></h4>
    <div style="margin-top: -31px; float: right;">
        <button class="save-btn btn btn-primary" id="btn_log_save">&#10004;<s:text name="btn.save"/></button>
        <button class="close-btn btn" data-dismiss="modal">&#10006; <s:text name="btn.close"/></button>
    </div>
</div>
<div class="body" style="padding: 7px;">
    <div id="msg_log" class="alert alert-danger" style="display: none;"></div>
    <s:form id="frm_log" name="frm_log" action="/saveLogActivityForm.do">
        <s:hidden name="activity.civilId"/>
        <s:hidden name="company"/>
        <div class="row">
            <%--div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <label>Subject: <span>*</span></label>
                <div class="input-group">
                    <div class="form-line">
                        <s:select name="activity.subject" list="subjectList" listKey="key" listValue="value" cssClass="form-control"/>
                    </div>
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <label>Type: <span>*</span></label>
                <div class="input-group">
                    <div class="form-line">
                        <s:select name="activity.template" list="keyValueList" listKey="key" listValue="value" cssClass="form-control"/>
                    </div>
                </div>
            </div--%>
            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <label><s:text name="lbl.subject"/><span>*</span></label>
                <div class="form-group">
                    <s:select name="activity.subject" list="subjectList" listKey="key" listValue="value" cssClass="form-control"/>
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <label><s:text name="lbl.type"/><span>*</span></label>
                <div class="form-group">
                    <s:select name="activity.template" list="keyValueList" listKey="key" listValue="value" cssClass="form-control"/>
                </div>
            </div>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <label><s:text name="lbl.message"/>: <span>*</span></label>
                <div class="form-group form-float">
                    <div class="form-line">
                        <s:textarea name="activity.message" id="activityMessage" rows="10" cssClass="form-control" maxLength="240"/>
                    </div>
                    <div class="help-info"><span id="textarea_feedback"></span></div>
                </div>
            </div>      

            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <input type="checkbox"  name="activity.coCreateTaskYn" value="1" id="md_checkbox_36" class="filled-in chk-col-deep-orange" onchange="enableAddlDetails();">
                <label for="md_checkbox_36"><s:text name="lbl.create.task"/></label>
            </div>
            <div id="check_show" style="display:none;"> 
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.category"/>  <span>*</span></label>
                    <div class="form-group">
                        <s:select name="activity.task.ctCatg"  id="ctCatg" list="categoryList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control" onchange="loadDropDown(this.value, 'ctSubCatg');" />
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.sub.category"/>  <span>*</span></label>
                    <div class="form-group">
                        <s:select name="activity.task.ctSubCatg" id="ctSubCatg" list="aaData" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control" />
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.due.date"/><span>*</span></label>
                    <div class="form-group">
                        <s:textfield name="activity.task.ctDueDate"  id="ctDueDate"  cssClass="form-control datepicker calicon"  placeholder="DD/MM/YYYY"  required="true" title="%{getText('lbl.please.select.duedate')}"/>
                    </div>
                </div>
                <%--div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
               <label>Remind Before Hours<span>*</span></label>
               <div class="form-group">
                       <s:textfield name="activity.task.ctRemindBefore" id="ctRemindBefore" cssClass="form-control" required="true" title="Please Enter Remind Before Hours"/>
               </div>
           </div--%>
                <%--div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
                    <div class="input-group">
                        <label class="input-group-addon">Due Date<span>*</span></label>
                        <div class="">
                            <s:textfield name="activity.task.ctDueDate"  id="ctDueDate"  cssClass="datepicker"  placeholder="DD/MM/YYYY"  required="true" title="Please Select Due Date"/>
                        </div>
                    </div>
                </div> 
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
                    <div class="input-group">
                        <label class="input-group-addon">Remind Before Hours<span>*</span></label>
                        <div class="form-group">
                            <s:textfield name="activity.task.ctRemindBefore" id="ctRemindBefore" cssClass="form-control" required="true" title="Please Enter Remind Before Hours"/>
                        </div>
                    </div>
                </div--%>
            </div>
        </div>
    </s:form>
</div>

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

        var text_max = 240;
        $('#textarea_feedback').html(text_max + ' characters remaining');

        $('#activityMessage').keyup(function () {
            var text_length = $('#activityMessage').val().length;
            var text_remaining = text_max - text_length;

            $('#textarea_feedback').html(text_remaining + ' characters remaining');
        });

        $("#btn_log_save").on("click", function () {
            if (!$('#frm_log').valid()) {
                return false;
            }
            $.ajax({
                type: "POST",
                url: $("#frm_log").prop("action"),
                data: $("#frm_log").serialize(),
                async: true,
                success: function (result) {
                    if (result.messageType === "S") {
                        loadActivities();
                        $('#plugin_modal_dialog').modal('hide');
                        $.notify("Log saved successfully", "custom");
                    } else {
                        $("#plugin_modal_dialog #msg_log").removeClass("alert-success").addClass("alert-danger").html(result.message).show();
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    //unblock();
                }
            });
        });
    });

    function enableAddlDetails() {
        if ($('#md_checkbox_36').is(":checked"))
            $("#check_show").show();
        else
            $("#check_show").hide();
    }
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
                $('#' + id).selectpicker('refresh');
            }
        });
    }
    $(document).ready(function () {
        //$('[data-toggle="tooltip"]').tooltip();
        $('#frm_log').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            rules: {
                "activity.message": {
                    required: true
                },
                "activity.task.ctCatg": {
                    required: true
                },
                "activity.task.ctSubCatg": {
                    required: true
                },
                "activity.task.ctDueDate": {
                    required: true
                },
//               "activity.task.ctRemindBefore": {
//                    required: true
//                },
            },
            messages: {
                "activity.message": {
                    required: "Please Enter Message"
                },
                "activity.task.ctCatg": {
                    required: "Please Select Category"
                },
                "activity.task.ctSubCatg": {
                    required: "Please Select SubCategory "
                },
                "activity.task.ctDueDate": {
                    required: "Please Select DueDate "
                },
//                "activity.task.ctRemindBefore": {
//                    required: "Please Enter Reminder"
//                },
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
                    $("#msg_log").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_log").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_log").show();
                else
                    $("#msg_log").hide();
            }
        });
    });
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>
