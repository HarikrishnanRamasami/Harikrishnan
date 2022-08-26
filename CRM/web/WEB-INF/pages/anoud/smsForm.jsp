<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : smsForm
    Created on : Feb 28, 2017, 6:24:13 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="modal-header">
    <h4 class="modal-title"><span><img src="<%=request.getContextPath()%>/images/chat.png" class="img-responsive" style="max-width:32px; display: inline-block;" alt="sms"></span> <s:text name="lbl.sms"/></h4>
    <div style="margin-top: -31px; float: right;">
        <button class="save-btn btn btn-primary" id="btn_sms_save">&#10004;<s:text name="btn.send"/></button>
        <button class="close-btn btn" data-dismiss="modal" id="btn_sms_close">&#10006; <s:text name="btn.close"/></button>
    </div>
</div>
<div class="body" style="padding: 7px;">
    <div id="msg_sms" class="alert alert-danger" style="display: none;"></div>
    <s:form id="frm_sms" name="frm_sms" action="/saveSmsActivityForm.do" role="form">
        <s:hidden name="activity.civilId"/>
        <s:hidden name="company"/>
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <label><s:text name="lbl.to"/>: <span>*</span></label>
                <div class="form-group">
                    <div class="form-line">
                        <s:textfield name="activity.to" cssClass="form-control" required="true" title="%{getText('lbl.please.enter.mob.no')}" />
                    </div>
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <label><s:text name="lbl.language"/>: <span>*</span></label>
                <div class="form-group">
                    <s:radio name="activity.language" list="#{'E': 'English', 'A': 'Arabic'}" required="true" title="%{getText('lbl.please.enter.lanquage')}" />
                </div>
            </div>
            <s:if test="keyValueList != null && !keyValueList.isEmpty()">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.handy.sms"/>: </label>
                    <div class="form-group">
                        <s:select id="sel_handy_sms" list="keyValueList" listKey="key" listValue="value" headerKey="" headerValue="-- Custom --" cssClass="form-control" />
                    </div>
                </div>
            </s:if>
            <div id="block_smsMessage" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <label><s:text name="lbl.description"/>: <span>*</span></label>
                <div class="form-group form-float">
                    <div class="form-line">
                        <s:textarea name="activity.message" id="smsMessage" cssClass="form-control" maxLength="180" rows="15" required="true" title="%{getText('lbl.please.enter.message')}" />
                    </div>
                    <div class="help-info"><span id="textarea_feedback"></span></div>
                </div>
            </div>
        </div>
    </s:form>
</div>

<script type="text/javascript">
    var sms_JSON = [];
    <s:iterator value="keyValueList">
    sms_JSON.push({"key": "<s:property value="key"/>", "info1": "<s:property value="info1"/>", "info2": '<s:property value="info2" escapeHtml="false"/>', "info3": "<s:property value="info3"/>"});
    </s:iterator>

    function setSMS(code) {
        var lang = $('[name="activity.language"]:checked').val();
        $.each(sms_JSON, function (i, o) {
            if (o.key === code) {
                var info2 = o.info2;
                info2 = info2.replace(/<br>/g, "\n");
                var info3 = o.info3;
                info3 = info3.replace(/<br>/g, "\n");
                $("#frm_sms #smsMessage").val((lang === "A" ? info3 : info2));
                if (o.info1 === "F") {
                    $("#frm_sms #block_smsMessage").hide();
                } else {
                    $("#frm_sms #block_smsMessage").show();
                }
                return false;
            }
        });
    }

    $(document).ready(function () {

        var text_max = 180;
        $('#textarea_feedback').html(text_max + ' characters remaining');

        $('#smsMessage').keyup(function () {
            var text_length = $('#smsMessage').val().length;
            var text_remaining = text_max - text_length;

            $('#textarea_feedback').html(text_remaining + ' characters remaining');
        });

        $('#frm_sms').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            highlight: function (element) {   // <-- fires when element has error
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
                    $("#msg_sms").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_sms").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_sms").show();
                else
                    $("#msg_sms").hide();
            }
        });

        $('[name="activity.language"]').on("click", function () {
            var code = $("#sel_handy_sms").val();
            if (code !== "") {
                setSMS(code);
            }
        });

        $("#sel_handy_sms").on("change", function () {
            if ($(this).val() !== "") {
                setSMS($(this).val());
            } else {
                $("#frm_sms #smsMessage").val("");
                $("#frm_sms #block_smsMessage").show();
            }
        });

        $("#btn_sms_save").on("click", function () {
            if (!$('#frm_sms').valid()) {
                return false;
            }
            $.ajax({
                type: "POST",
                url: $("#frm_sms").prop("action"),
                data: $("#frm_sms").serialize(),
                async: true,
                success: function (result) {
                    if (result.messageType === APP_CONFIG.messageType.success) {
                        $('#plugin_modal_dialog').modal('hide');
                        $.notify("SMS send successfully", "custom");
                    } else {
                        $("#plugin_modal_dialog #msg_sms").removeClass("alert-success").addClass("alert-danger").html(result.message).show();
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
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>
