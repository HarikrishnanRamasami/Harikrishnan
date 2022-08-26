<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : emailForm
    Created on : Feb 28, 2017, 6:24:31 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

    <div class="modal-header">
        <h4 class="modal-title"><span><img src="<%=request.getContextPath()%>/images/gmail.png" class="img-responsive" style="max-width:32px; display: inline-block; margin-top: -7px;" alt="mail"></span> <s:text name="lbl.email"/></h4>
<div style="margin-top: -31px; float: right;">
    <button class="save-btn btn btn-primary" id="btn_email_save">&#10004;<s:text name="btn.send"/></button>
    <button class="close-btn btn" data-dismiss="modal" id="btn_email_close">&#10006; <s:text name="btn.close"/></button>
</div>
    </div>
    <div class="body" style="padding: 7px;">
        <div id="msg_email" class="alert alert-danger" style="display: none;"></div>
        <s:form id="frm_email" name="frm_email" action="/saveEmailActivityForm.do">
            <s:hidden name="activity.civilId"/>
            <s:hidden name="company"/>
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.to"/>: <span>*</span></label>
                    <div class="form-group">
                        <div class="form-line">
                            <s:textfield name="activity.to" cssClass="form-control" required="true" title="%{getText('lbl.please.enter.to.address')}"/>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.cc"/>: </label>
                    <div class="form-group">
                        <div class="form-line">
                            <s:textfield name="activity.cc" cssClass="form-control" />
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.template"/>: <span>*</span></label>
                    <div class="form-group">
                        <div class="form-line">
                            <s:select name="activity.template" list="keyValueList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control" required="true" title="%{getText('lbl.please.select.template')}"/> 
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.subject"/>: <span>*</span></label>
                    <div class="form-group">
                        <div class="form-line">
                            <s:textfield name="activity.subject" required="true" cssClass="form-control" title="%{getText('lbl.please.enter.subject')}"/>
                        </div>
                    </div>
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <label><s:text name="lbl.message"/>: <span>*</span></label>
                    <div class="form-group">
                        <div class="form-line">
                            <s:textarea name="activity.message" id="emailMessage" cssClass="form-control" required="true" title="%{getText('lbl.please.enter.message')}"/>
                        </div>
                    </div>
                </div>
            </div>
        </s:form>
    </div>
<script type="text/javascript">
    $(document).ready(function () {
       
        $("#emailMessage").jqte();
        $('.jqte_editor').css({height: '250px'});
        
        $('#frm_email').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            ignore:':hidden:not(#emailMessage)',
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
                    $("#msg_email").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_email").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_email").show();
                else
                    $("#msg_email").hide();
            }
        });

        $("#btn_email_save").on("click", function () {
            if (!$('#frm_email').valid()) {
                return false;
            }
            $.ajax({
                type: "POST",
                url: $("#frm_email").prop("action"),
                data: $("#frm_email").serialize(),
                async: true,
                success: function (result) {
                    if (result.messageType === "S") {
                            $('#plugin_modal_dialog').modal('hide');
                            $.notify("Email send successfully", "custom");
                    } else {
                        $("#plugin_modal_dialog #msg_email").removeClass("alert-success").addClass("alert-danger").html(result.message).show();
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