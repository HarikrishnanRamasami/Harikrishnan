<%-- 
    Document   : campaignPathFlow
    Created on : 1 Oct, 2019, 9:23:50 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/bootstrap-multiselect/bootstrap-multiselect.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-multiselect/bootstrap-multiselect.min.js"></script>
<style type="text/css">
    .multiselect-container>li>a>label {
        padding: 4px 20px 3px 20px;
    }
    .open>.dropdown-menu {
        max-width: 1000px;
    }
    button.multiselect.dropdown-toggle.btn.btn-default {
        width: 420px;
    }
</style>

<div class="col-md-12 right-pad" id="block_body">
    <div class="card">
        <div class="modal-header">
            <h4 class="modal-title"><s:text name="lbl.campaign.head.journey.path.flow"/></h4>
        </div>
        <div id="msg_jrny" class="alert alert-danger" style="display: none;"></div>
        <div class="body" style="padding: 7px;">
            <div id="msg_camp_temp" class="alert-danger" style="display: none;"></div>
            <s:form id="frm_mcf_path_flow" name="frm_mcf_path_flow">
                <s:hidden name="campPathFlow.mcpfPathId" id="mcpfPathId" />
                <s:hidden name="campPathFlow.mcpfFlowNo" id="mcpfFlowNo" />
                <s:hidden name="oper" id="oper2" />
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                        <label><s:text name="lbl.action"/>: <span>*</span></label>
                        <div class="form-group">
                            <div class="form-line">
                                <s:select name="campPathFlow.mcpfAction" id="mcpfAction" required="true" headerKey="" headerValue="--select--" list="actionList" listKey="key" listValue="value" cssClass="form-control" onchange="showMsgDiv(this.value);" />
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6" id='waitDiv' style="display: none;">
                        <label><s:text name="lbl.campaign.wait.freq"/>: <span>*</span> </label>
                        <div class="form-group">
                            <div class="form-line">
                                <s:select name="campPathFlow.mcpfWaitFreq" id="mcpfWaitFreq" required="true" list="waitFreqList" headerKey="" headerValue="--select--" listKey="key" listValue="value" cssClass="form-control" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" id="splitDiv" style="display: none;">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                        <label><s:text name="lbl.common.split"/></label>
                        <div class="form-group">
                            <div class="form-line">
                                <s:radio name="campPathFlow.mcpfSplitYn" id="mcpfSplitYn" list="#{'1': 'Yes', '0': 'No'}"/> 
                            </div>   
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6" id="splitPercDiv" style="display: none;">
                        <label><s:text name="lbl.campaign.split.perc"/> (% <s:text name="lbl.campaign.applied.on.template1"/>)</label>
                        <div class="form-group">
                            <div class="form-line">
                                <s:textfield name="campPathFlow.mcpfSplitPerc" required="true" id="mcpfSplitPerc" cssClass="form-control"  title="%{getText('lbl.campaign.enter.path.name')}" maxlength="2"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" id="tempDiv">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                        <label><s:text name="lbl.common.template1"/> <span>*</span></label>
                        <div class="form-group">
                            <div class="form-line">
                                <s:select name="campPathFlow.mcpfTemplateId" id="mcpfTemplateId" required="true" headerKey="" headerValue="--select--" list="tempList" listKey="key" listValue="value" cssClass="form-control"  />
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6" id="templ2" style="display: none;">
                        <label><s:text name="lbl.common.template2"/> </label>
                        <div class="form-group">
                            <div class="form-line">
                                <s:select name="campPathFlow.mcpfTemplateId2" required="true" id="mcpfTemplateId2" headerKey="" headerValue="--select--" list="tempList" listKey="key" listValue="value" cssClass="form-control" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" id="msgDiv" style="display: none;">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <label><s:text name="lbl.arabic"/> </label>
                        <div class="form-group">
                            <div class="form-line">
                                <s:radio name="campPathFlow.mcpfUnicode" id="mcpfUnicode" list="#{'A': 'Yes', 'E': 'No'}"/> 
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <label><s:text name="lbl.common.message.text"/> </label>
                        <div class="form-group">
                            <div class="form-line">
                                <s:textarea  name="campPathFlow.mcpfText" id="mcpfText" required="true"  cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.campaign.enter.campaign.msg')}" style="min-height: 100px;"/> 
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="clickDiv" style="display: none;">
                        <label><s:text name="lbl.campaign.clicked.urls="/> </label>
                        <div class="form-group">
                            <select name="campPathFlow.mcpfClickUrlKey" id="mcpfClickUrlKey" class="multiselect-ui form-control" multiple="multiple">
                                <s:iterator value="campaignUrlList" status="stat">
                                    <option value="<s:property  value="key"/>"><s:property  value="value"/></option>
                                </s:iterator>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row" style="margin-left: 400px;">
                    <button class="save-btn btn btn-primary" type="button" onclick="saveCampPathFlow();">&#10004;<s:text name="btn.save"/></button>&nbsp;
                    <button class="close-btn btn" id="btn_journ_close" data-dismiss="modal">&#10006; <s:text name="btn.close"/></button>
                </div>
            </div>
        </s:form>
    </div>
</div>
<script type="text/javascript">

    $(document).ready(function () {
//         $('#mcpfClickUrlKey').multiselect({
//            buttonWidth: '100%'
//        });
        if ($("#oper2").val() === "view") {
            $("#frm_mcf_path_flow").find('input, textarea, select, button').attr('disabled', true);
            $('#frm_mcf_path_flow button').remove('.save-btn');
            $('#frm_mcf_path_flow #btn_journ_close').prop('disabled', false);
        }

        $(function () {
            $('#mcpfClickUrlKey').multiselect({
                includeSelectAllOption: true
            });
        });
    <s:if test='"edit".equals(oper)'>

        $("#mcpfClickUrlKey").val(<s:property value="campPathFlow.mcpfClickUrlKey"/>);
        var val = $("#mcpfClickUrlKey").find(':selected').data('value');

        var data = '<s:property value="campPathFlow.mcpfClickUrlKey"/>';
        if (data.includes(",")) {

            var valArr = data.split(",");
            var i = 0, size = valArr.length;
            for (i; i < size; i++) {
                valArr[i] = $.trim(valArr[i]);
            }
            $("#mcpfClickUrlKey").val(valArr);
        } else {
            console.log("esss " + data);
            $("#mcpfClickUrlKey").val(data);
        }
        $("#mcpfClickUrlKey").multiselect("refresh");


    </s:if>
        $('#mcpfTemplateId').trigger('change');

        if ($("#oper2").val() === 'add') {
            $('input[name="campPathFlow.mcpfSplitYn"][value="0"]').prop('checked', true);
            $('input[name="campPathFlow.mcpfUnicode"][value="E"]').prop('checked', true);
        }
        $('#mcpfAction').trigger('change');

        $('input[name="campPathFlow.mcpfSplitYn"]').on('click', function () {
            console.log('calling', $(this).val());
            var val = $(this).val();
            if (val === "1") {
                $("#splitPercDiv").show();
                $("#templ2").show();
            } else if (val === "0") {
                $("#splitPercDiv").hide();
                $("#templ2").hide();
            }
        });

        $('input[name="campPathFlow.mcpfSplitYn"][value="${campPathFlow.mcpfSplitYn}"]').trigger('click');



        $('#frm_mcf_path_flow').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            rules: {
            },
            messages: {
                "campPathFlow.mcpfAction": {
                    required: "Please Select Action"
                },
                "campPathFlow.mcpfTemplateId": {
                    required: "Please Select Template1"
                },
                "campPathFlow.mcpfSplitPerc": {
                    required: "Please Enter Split Percentage"
                },
                "campPathFlow.mcpfTemplateId2": {
                    required: "Please Select Template2"
                },
                "campPathFlow.mcpfClickUrlKey": {
                    required: "Please Select Clicked Url"
                },
                "campPathFlow.mcpfText": {
                    required: "Please Enter Message Text"
                },
                "campPathFlow.mcpfWaitFreq": {
                    required: "Please Select Waiting frequency"
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
                if (this.numberOfInvalids() > 0)
                    this.defaultShowErrors();
                else
                    $("#msg_jrny").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_jrny").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors > 0)
                    $("#msg_jrny").show();
                else
                    $("#msg_jrny").hide();
            }
        });
    });

    function showUrl(tempId) {
        var opt = '<option value="">--Select-</option>';
        $("#mcpfClickUrlKey").html(opt);
        block('block_body');
        $.ajax({
            type: "POST",
            data: {"mcCampId": "${mcCampId}"},
            url: APP_CONFIG.context + "/camp/loadTemplateUrlList.do?campTemplate.mctTemplateId=" + tempId,
            success: function (data) {
                $.each(data.aaData, function (i, d) {
                    opt += '<option value="' + d.key + '">' + d.value + '</option>';
                });
                $('#plugin_modal_dialog #mcpfClickUrlKey').html(opt);
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function showMsgDiv(val) {
        if (val === 'M') {
            $("#tempDiv").show();
            $("#splitDiv").show();
            $("#waitDiv").hide();
            $("#templ2").hide();
            $("#clickDiv").hide();
            $("#msgDiv").hide();
            refreshModal(val);
        }
        if (val === 'MC') {
            $("#clickDiv").show();
            $("#tempDiv").show();
            $("#splitDiv").hide();
            $("#waitDiv").hide();
            $("#templ2").hide();
            $("#msgDiv").hide();
            refreshModal(val);
        }
        if (val === 'P' || val === 'S') {
            $("#msgDiv").show();
            $("#tempDiv").hide();
            $("#splitDiv").hide();
            $("#waitDiv").hide();
            $("#templ2").hide();
            $("#clickDiv").hide();
            refreshModal(val);

        }
        if (val === 'W') {
            $("#waitDiv").show();
            $("#clickDiv").hide();
            $("#splitDiv").hide();
            $("#tempDiv").hide();
            $("#templ2").hide();
            $("#msgDiv").hide();


        }
        if (val === 'MB' || val === 'MO' || val === 'MV') {
            $("#tempDiv").show();
            $("#clickDiv").hide();
            $("#splitDiv").hide();
            $("#templ2").hide();
            $("#waitDiv").hide();
            $("#msgDiv").hide();
            refreshModal(val);
        }

    }
    function refreshModal(action) {
        if ($("#oper2").val() === 'add') {
            if (action === 'M' || action === 'MB' || action === 'MO' || action === 'MV') {
                document.getElementById("mcpfSplitPerc").value = "";
                document.getElementById("mcpfTemplateId").value = "";
                document.getElementById("mcpfTemplateId2").value = "";
                $('input[name="campPathFlow.mcpfSplitYn"][value="0"]').prop('checked', true);
                $("#splitPercDiv").hide();
            } else if (action === 'P' || action === 'S') {
                document.getElementById("mcpfText").value = "";
            } else if (action === 'MC') {
                document.getElementById("mcpfTemplateId").value = "";
            }
        }
    }

    function saveCampPathFlow() {
        if (!$('#frm_mcf_path_flow').valid()) {
            return false;
        }

        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/saveJourneyPathFlowData.do",
            data: $("#frm_mcf_path_flow").serialize(),
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify(result.message, "custom");
                    $('#plugin_modal_dialog').modal('hide');
                    reloadDt("tbl_jour_path_flow");
                }
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

</script>