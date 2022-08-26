<%-- 
    Document   : campaignConfigList
    Created on : Sep 24, 2019, 1:31:18 PM
    Author     : soumya.gaur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div id="body">
    <div class="card" id="form_temp_field">
        <div class="col-md-12 right-pad">
            <div class="dash-leads" style="border-top:0!important">
                <div class="my-bord bor2">
                    <h3><s:text name="lbl.campaign.head.config"/></h3>
                </div>
                <div style="padding-left: 15px; padding-right: 15px; padding-top: 10px;padding-top: 23px;">
                    <div id="msg_camp_temp" class="alert-danger" style="display: none;"></div>
                    <s:form id="frm_campaign_config" theme="simple" name="frm_campaign_config" method="post" autocomplete="off">
                        <s:hidden name="oper" id="operForm"/>
                        <s:hidden name="formField.mcffFormId" id="mcffFormId" />
                        <s:hidden name="formField.mcffColNo" id="mcffColNo" />
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.name"/><span>*</span></label>
                                <div class="form-group">                                   
                                    <s:textfield id="mcffFieldName" name="formField.mcffFieldName" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.enter.name')}"/>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.campaign.hint"/></label>
                                <div class="form-group">
                                    <s:textfield id="mcffFieldHint" name="formField.mcffFieldHint" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.enter.title')}"/>
                                </div>
                            </div>
                        </div>
                        <div class="row"  >
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.campaign.data.type"/> <span>*</span></label>
                                <div class="form-group">
                                    <s:select id="mcffDataType" name="formField.mcffDataType" list="dataTypes"  listKey="key" listValue="value"  data-toggle="tooltip" data-placement="top" title="%{getText('lbl.campaign.data.type')}"  cssClass="form-control" />
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.common.field.type"/> <span>*</span></label>
                                <div class="form-group">
                                    <s:select id="mcffFieldType" name="formField.mcffFieldType" list="fieldTypes"  onchange="viewSql(this.value)"  listKey="key" listValue="value"  data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.field.type')}" cssClass="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.common.maxlength"/></label>
                                <div class="form-group">                                   
                                    <s:textfield id="mcffMaxLength" name="formField.mcffMaxLength" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.enter.name')}"/>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.common.mandatory"/></label>
                                <div class="form-group">
                                    <s:radio name="formField.mcffMandetoryYn" id="mcffMandetoryYn" list="#{'1': 'Yes','0' :'No'}"   data-toggle="tooltip" data-placement="top" />
                                </div>
                            </div>
                        </div>
                        <div class="row" id="SqlDiv" >
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <label><s:text name="lbl.common.sql"/> </label>
                                <div class="form-group">
                                    <s:textarea id="mcffDataSql" name="formField.mcffDataSql" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.enter.sql')}"/>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 15px;">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <label><s:text name="lbl.common.validation.expression"/></label>
                                <div class="input-group">
                                    <s:textfield id="mcffValidation" name="formField.mcffValidation" cssClass="form-control" title="%{getText('lbl.common.enter.validation')}"/>
                                    <span class="input-group-addon" data-toggle="modal" data-target="#modal_regexp">
                                        <span><i class="fa fa-code" aria-hidden="true"></i></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <label><s:text name="lbl.common.validation.message"/></label>
                                <div class="form-group">
                                    <s:textfield id="mcffValidationMsg" name="formField.mcffValidationMsg" cssClass="form-control" title="%{getText('lbl.campaign.enter.validation.msg')}"/>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="margin-left: 400px;">
                            <button type="button" class="save-btn btn btn-primary" id="btn_config_save" style="margin-top: -8px" data-toggle="tooltip" data-placement="top" title="Save"><i class="fa fa-save"></i> <s:text name="btn.save"/></button>
                            <button type="button" class="btn btn-danger waves-effect" style="margin-top: -8px" id="btn_config_close" data-toggle="tooltip" data-placement="top" title="Close"><i class="fa fa-close"></i> <s:text name="btn.close"/></button>
                        </div>
                    </s:form>
                </div>
            </div>
        </div>  
    </div>
</div>
<div class="modal fade" id="modal_regexp" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><s:text name="lbl.common.regular.expression"/></h4>
                    <div style="margin-top: -31px; float: right;">
                        <button class="close-btn btn" data-dismiss="modal">&#10006; <s:text name="btn.close"/></button>
                    </div>
                </div>
                <div class="body" style="padding: 7px;">
                    <div class="row">
                        <div class="col-md-12">
                            <table id="tbl_regexp" class="table">
                                <thead>
                                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                        <th style="width: 20%;" class="text-center"><s:text name="lbl.description"/></th>
                                        <th style="width: 80%;" class="text-center"><s:text name="lbl.common.expression"/></th>
                                    </tr>
                                </thead>
                                <tbody>	   
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        if ($("#operForm").val() === "view") {
            $("#frm_campaign_config").find('input, textarea, select, button').attr('disabled', true);
            $('#frm_campaign_config button').remove('.save-btn');
            $('#frm_campaign_config #btn_config_close').prop('disabled', false);
        }
        $('#frm_campaign_config').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            ignore: ':hidden:not(#tempBody)',
            messages: {
                "formField.mcffFieldName": {
                    required: "Please Enter Name"
                },
                "formField.mcffDataType": {
                    required: "Please Enter Data Type"
                },
                "formField.mcffFieldType": {
                    required: "Please Enter Field Type"
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
                    $("#msg_camp_temp").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_camp_temp").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_camp_temp").show();
                else
                    $("#msg_camp_temp").hide();
            }
        });

    <s:if test='formField.mcffFieldType == "A" || formField.mcffFieldType == "S"'>
        $("#SqlDiv").show();
    </s:if>
    <s:else>
        $("#SqlDiv").hide();
    </s:else>
        
        $("#tbl_regexp").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            "lengthChange": false,
            "pageLength": 5,
            "responsive": true,
            autoWidth: false,
            data: [
                {"name": "Email", "regExp": "^[a-zA-Z0-9._-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"},
                {"name": "Mobile - Doha", "regExp": "^((3|5|6|7){1}[0-9]{7})$"},
                {"name": "Amount", "regExp": "^\d+(\.\d{1,2})?$"},
                {"name": "Name", "regExp": "^[\w ]+(.){1}?[\w ]+$"},
                {"name": "Date", "regExp": "^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$"},
                {"name": "Civil Id - Doha", "regExp": "^\d{11}$"}
            ],
            columns: [
                {data: "name"},
                {data: "regExp"}
            ],
            columnDefs: [
                {targets: 1, orderable: false}
            ],
            language: {
                searchPlaceholder: "Search..."
            },
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            }
        });
    });

    $("#btn_config_save").on("click", function () {

        if (!$('#frm_campaign_config').valid()) {
            return false;
        }
        block('block_body');
        $.ajax({
            type: "POST",
            url: '${pageContext.request.contextPath}/camp/saveCampaignFormField.do?',
            data: $("#frm_campaign_config").serialize(),
            async: true,
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify(result.message, "custom");
                    $('#form_list').show();
                    $('#form_temp_field').empty().hide();
                    $('#form_field').empty().hide();
                    reloadDt("tbl_form_config");

                } else {
                    $('#msg_camp_temp').empty().html(result.message).show();
                    // $.notify(result.message, "custom");
                }
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
                block('block_body');
            },
            complete: function () {
                unblock('block_body');
            }
        });
    });

    $("#btn_config_close").on("click", function () {
        $('#form_list').show();
        $('#form_temp_field').empty().hide();
    });

    function viewSql(val)
    {
        if (val === 'A' || val === 'S')
        {
            $('#SqlDiv').show();
        } else {
            $('#SqlDiv').hide();
        }
    }
</script>