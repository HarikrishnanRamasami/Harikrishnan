<%-- 
    Document   : handySmsForm
    Created on : Aug 28, 2017, 12:38:33 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="card">
    <div class="header">
        <h4 class="m-tb-3 d-inline-block"><s:text name="lbl.handy.sms.form"/></h4>
        <div class="pull-right cstm_btn">
            <button type="button" id="btn_crmsms_save" class="btn btn-success waves-effect m-r-5" data-toggle="tooltip" data-placement="top" title="<s:text name="btn.save"/>" ><i class="fa fa-save"></i> <s:text name="btn.save"/></button>
            <button type="button" class="btn btn-danger waves-effect" id="btn_crmsms_close" data-dismiss="modal" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="<s:text name="btn.close"/>"><i class="fa fa-close"></i> <s:text name="btn.close"/></button> 
        </div>
    </div>
    <div class="body">
        <div id="msg_crmsms" class="alert alert-danger" style="display: none;"></div>
        <s:form id="frm_crmsms" name="frm_crmsms" method="post" theme="simple">
            <s:hidden name ="operation"/>
            <div class="row">
                <div class="col-lg-6 col-md-4 col-sm-9 col-xs-12">
                    <div class="input-group">
                        <label class="input-group-addon"><s:text name="lbl.ac.code"/><span>*</span></label>
                        <div class="form-line">
                            <s:textfield name="appcodes.acCode" id="acCode" cssClass="form-control" data-toggle="tooltip" data-placement="top" disabled='"edit".equals(operation)' title="%{getText('lbl.ac.code')}"/>
                            <s:if test='"edit".equals(operation)'>
                                <s:hidden name="appcodes.acCode"/>
                            </s:if>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-4 col-sm-9 col-xs-12 required">
                    <div class="input-group">
                        <label><s:text name="lbl.type"/> <span>*</span></label>
                        <s:radio name="appcodes.acValue" list="#{'F': 'Fixed', 'M': 'Modify'}" required="true" title="%{getText('lbl.ac.value')}" />
                    </div>
                </div>     
            </div>
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-9 col-xs-12 required">

                    <div class="input-group">
                        <label class="input-group-addon"><s:text name="lbl.description"/><span>*</span></label>
                        <div class="form-line">
                            <s:textfield name="appcodes.acDesc" id="acDesc" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.enter.description')}"/>
                        </div>
                    </div>   
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-9 col-xs-12 required">
                    <div class="input-group">
                        <label class="input-group-addon"><s:text name="lbl.english.text"/><span>*</span></label>
                        <div class="form-line">
                            <s:textarea name="appcodes.acLongDesc" id="acLongDesc" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.enter.english.description')}"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-9 col-xs-12 required">
                    <div class="input-group">
                        <label class="input-group-addon"><s:text name="lbl.arabic.text"/><span>*</span></label>
                        <div class="form-line">
                            <s:textarea name="appcodes.acLongDescBl" id="acLongDescBl" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.enter.arabic.description')}"/>
                        </div>
                    </div>
                </div>
            </div>
        </s:form>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        $('#frm_crmsms').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            rules: {
                "appcodes.acCode": {
                    required: true
                },
                "appcodes.acDesc": {
                    required: true
                },
                "appcodes.acValue": {
                    required: true,
                },
                "appcodes.acLongDesc": {
                    required: true,
                },
                "appcodes.acLongDescBl": {
                    required: true,
                }
            },
            messages: {
                "appcodes.acCode": {
                    required: "Please Enter Code"
                },
                "appcodes.acDesc": {
                    required: "Please Enter Description"
                },
                "appcodes.acValue": {
                    required: "Please Select Type"
                },
                "appcodes.acLongDesc": {
                    required: "Please Enter English Description"
                },
                "appcodes.acLongDescBl": {
                    required: "Please Enter Arabic Description"
                }
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
                    $("#msg_crmsms").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_crmsms").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_crmsms").show();
                else
                    $("#msg_crmsms").hide();
            }
        });
        $("#btn_crmsms_save").on("click", function () {
            if (!$('#frm_crmsms').valid()) {
                return false;
            }
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/admin/saveHandySmsEntryForm.do",
                data: $("#frm_crmsms").serialize(),
                success: function (result) {
                    $('#plugin_modal_dialog').modal('hide');
                    $.notify(" Handy Sms saved successfully", "custom");
                    $("#tbl_handy_sms").DataTable().ajax.url(getCrmDataTableUrl()).load();

                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        })
    });
    function getCrmDataTableUrl() {
        return APP_CONFIG.context + "/admin/loadHandySmsEntryData.do";
    }
</script>