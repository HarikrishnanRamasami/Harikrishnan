<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : callFeedback
    Created on : Apr 30, 2017, 8:36:03 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="card">   
    <div class="header">
        <h4 class="m-tb-3 d-inline-block"><span><img src="<%=request.getContextPath()%>/images/notepad.png" class="img-responsive" style="max-width:32px; display: inline-block;" alt="Activity"></span> <s:text name="lbl.activities"/></h4>
        <div class="pull-right cstm_btn">
            <button type="button" class="btn btn-success waves-effect m-r-5" id="btn_log_save"><i class="fa fa-save"></i> <s:text name="btn.save"/></button>
            <%--button type="button" class="btn btn-danger waves-effect" id="btn_log_close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-close"></i> Close</button--%>
        </div>
    </div>
    <div class="body">
        <div id="msg_log" class="alert alert-danger" style="display: none;"></div>
        <s:form id="frm_log" name="frm_log" action="/saveCallFeedbackForm.do">
            <s:hidden name="activity.id"/>
            <s:hidden name="activity.callLog.cclId"/>
            <s:hidden name="activity.callLog.cclExtId"/>
            <s:hidden name="activity.callLog.cclCallNo"/>
            <s:hidden name="activity.callLog.cclCallDt"/>
            <s:hidden name="activity.callLog.cclFilePath"/>
            <div class="row">
                <s:if test='activity.civilId != null && activity.civilId != ""'>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <label><s:text name="lbl.civil.id"/>: </label>
                        <div class="input-group">
                            <div class="form-line">
                                <s:property value="activity.civilId"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <label><s:text name="lbl.name"/>: </label>
                        <div class="input-group">
                            <div class="form-line">
                                <s:property value="activity.userId"/>
                            </div>
                        </div>
                    </div>
                </s:if>
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.dateAndTime"/>: </label>
                    <div class="input-group">
                        <div class="form-line">
                            <s:property value="activity.callLog.cclCallDt"/>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.duration"/>: </label>
                    <div class="input-group">
                        <div class="form-line">
                            <s:property value="activity.callLog.cclDurationDesc"/>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.type"/>: </label>
                    <div class="input-group">
                        <div class="form-line">
                            <s:select name="activity.template" list="keyValueList" listKey="key" listValue="value" cssClass="form-control" disabled="true"/>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.subject"/>: <span>*</span></label>
                    <div class="input-group">
                        <div class="form-line">
                            <s:select name="activity.subject" list="subjectList" listKey="key" listValue="value" cssClass="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.message"/>: <span>*</span></label>
                    <div class="form-group form-float">
                        <div class="form-line">
                            <s:textarea name="activity.message" cssClass="form-control" id="smsMessage"/>
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
                        <label><s:text name="btn.save"/><s:text name="lbl.category"/>  <span>*</span></label>
                        <div class="form-group">
                            <s:select name="activity.task.ctCatg" id="ctCatg" list="categoryList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control" onchange="loadDropDown(this.value, 'ctSubCatg');" />
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <label><s:text name="lbl.sub.category"/>  <span>*</span></label>
                        <div class="form-group">
                            <s:select name="activity.task.ctSubCatg" id="ctSubCatg" list="aaData" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
                        <div class="input-group">
                            <label class="input-group-addon"><s:text name="lbl.due.date"/><span>*</span></label>
                            <div class="form-group">
                                <s:textfield name="activity.task.ctDueDate" id="ctDueDate" cssClass="form-control datepicker"  placeholder="DD/MM/YYYY"  required="true" title="%{getText('lbl.please.select.due.date')}"/>
                            </div>
                        </div>
                    </div> 
                    <%--div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
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
</div>
<script type="text/javascript">
//    $('#ctDueDate').bootstrapMaterialDatePicker({
//        format: 'DD/MM/YYYY',
//        clearButton: true,
//        weekStart: 1,
//        time: false
//    });
    $(document).ready(function () {
         $(".datepicker").datepicker({format: 'dd/mm/yyyy'});
        var text_max = 240;
        $('#textarea_feedback').html(text_max + ' characters remaining');

        $('[name="activity.message"]').on("keyup keypress paste", function (e) {
            var text_length = $(this).val().length;
            if(text_length > text_max) {
                $(this).val($(this).val().substr(0, text_max));
                e.preventDefault();
                return;
            }
            var text_remaining = text_max - text_length;
            $('#textarea_feedback').html(text_remaining + ' characters remaining');
        });

        $("#btn_log_save").on("click", function () {
            if ($("#smsMessage").val() === null || $.trim($("#smsMessage").val()) === "") {
                $('#msg_log').html('Please Enter Message').show();
                return false;
            }
            $.ajax({
                type: "POST",
                url: $("#frm_log").prop("action"),
                data: $("#frm_log").serialize(),
                async: true,
                success: function (result) {
                    if (result.messageType === "S") {
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
</script>
