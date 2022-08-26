<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : campaignSetup
    Created on : Aug 17, 2017, 12:50:39 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery.form.js"></script> --%>
<style type="text/css">
    .acti-off my-reminder{
        display: none;
    }
</style>
<div class="col-md-12 right-pad" id="block_body">
    <s:include value="/WEB-INF/pages/campaign/campaignHeader.jsp?stepNo=1"></s:include> 
        <div class="dash-leads" style="border-top:0!important">
            <div class="my-bord">
                <h3><s:text name="lbl.campaign.head.campaign.form"/></h3>
            </div>
            <div id="msg_email_setup" class="alert alert-danger" style="display: none;"></div>
            <div class="body" style="margin-top: 10px;">
            <s:form  id="frm_campaign" theme="simple" name="frm_campaign" method="post" autocomplete="off" >
                <s:hidden name="oper" id="oper"/>
                <s:hidden name="campaign.mcStatus" id="mcStatus"/>  
                <s:hidden name="campaign.mcCampId" id="mcCampId" />
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <label><s:text name="lbl.common.name"/> <span>*</span></label>
                        <div class="form-group">
                            <s:textfield id="mcCampName" name="campaign.mcCampName" required="true" cssClass="form-control" data-toggle="tooltip" data-placement="top" />
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <label><s:text name="lbl.campaign.tracking.code"/> <span>*</span></label>
                        <div class="form-group">
                            <s:textfield id="mcCampCode" name="campaign.mcCampCode" required="true" cssClass="form-control" data-toggle="tooltip" data-placement="top" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <%-- <s:hidden name="sendOption" value="E"/> --%>
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <label><s:text name="lbl.data.source"/><span>*</span></label>
                        <div class="form-group">
                            <s:select id="mcDataSourceType" name="campaign.mcDataSourceType" required="true" list="dataSourceList"  listKey="key" listValue="value"  data-toggle="tooltip" data-placement="top" onchange="setList(this.value)" cssClass="form-control"/>
                        </div>
                    </div>
                    <%--div class="ExcelFileDivId col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <ul>
                            <li>
                                <label>File Path <span>*</span></label>
                                <div>
                                    <a href="<%=request.getContextPath()%>/Forms_Templates/MAIL_UPL_TEMPLATE.xls">Click this link to download a sample excel.</a>
                                </div>
                            </li>
                        </ul>
                        <div class="form-group">
                            <a class='btn btn-primary' href='javascript:;'>
                                <s:file  name="campaign.excelFile" cssStyle="width: 350px;"  accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
                            </a>
                        </div>
                    </div--%>
                    <div class="CustomizedTextDivId col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <%--  <s:action namespace="/" name="LoadDynamicReportsPlugin" executeResult="true" ignoreContextParams="true">
                              <s:param name="pluginFor">email</s:param>
                              <s:param name="param1">email</s:param>
                          </s:action> --%>
                        <label><s:text name="lbl.view"/><span>*</span></label>
                        <div class="form-group">
                            <s:select name="campaign.mcDataSourceView" id="mcDataSourceView" list="reportsViewList" listKey="info" listValue="value" headerKey="" headerValue="--select--"   listTitle="info" required="true" cssClass="form-control"  />
                        </div>
                    </div> 
                </div>
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <label><s:text name="lbl.campaign.head.sub.campaign.type"/> <span>*</span></label>
                        <div class="form-group">
                            <s:select name="campaign.mcCampType" id="mcCampType" list="campTypeList" listKey="key" listValue="value" required="true" onchange="setCampaignType(this.value)" data-toggle="tooltip"  cssClass="form-control" />
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <label><s:text name="lbl.campaign.include.unSubscribers"/> </label>
                        <div class="form-group">
                            <s:radio name="campaign.mcUnsubscribeInclYn" id="mcUnsubscribeInclYn" list="#{'1': 'Yes','0' :'No'}"  data-toggle="tooltip" data-placement="top" />
                        </div>
                    </div>
                </div>
                <div class="row EmailFieldsDiv">
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12" id="senderId">
                        <label><s:text name="lbl.campaign.sender.id"/><span>*</span></label>
                        <div class="form-group">
                            <s:select name="campaign.mcSenderId" id="mcSenderId"  list="senderAndReplyList" listKey="key" listValue="value"  required="true"   cssClass="form-control" />                            
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12 ReplyId" id="replyId">
                        <label><s:text name="lbl.campaign.reply.to"/></label>
                        <div class="form-group">
                            <s:select name="campaign.mcReplyTo" id="mcReplyTo"  list="senderAndReplyList" listKey="key" listValue="value"    cssClass="form-control" />
                        </div>
                    </div>
                </div>
                <div class="row Recurring">
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <label><s:text name="lbl.campaign.recurring"/></label>
                        <div class="form-group">
                            <s:radio name="campaign.mcRecurringYn" id="mcRecurringYn" list="#{'1': 'Yes','0' :'No'}"  onchange="setRecurring(this.value)"  data-toggle="tooltip" data-placement="top" />
                        </div>
                    </div>
                </div>
                <div class="row ">
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12 RecurringFieldsDiv">
                        <label><s:text name="lbl.campaign.schedule.frequency"/><span>*</span></label>
                        <div class="form-group">
                            <s:select name="campaign.mcScheduleMode" id="mcScheduleMode"  list="scheduleModeList" listKey="key" listValue="value" headerKey="" headerValue="--select--" required="true" onchange="setScheduleTime(this.value,this.text)" data-toggle="tooltip" data-placement="top"  cssClass="form-control" />
                        </div>
                    </div>
                </div>
                <div class="row RecurringFieldsDiv">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                        <label><s:text name="lbl.campaign.schedule.for.every"/><span>*</span></label>
                        <div class="input-group">
                            <s:textfield id="mcScheduleValue" name="campaign.mcScheduleValue" required="true" cssClass="form-control" />  
                            <span class="input-group-addon"  id="program_span"   style="cursor: pointer; display: table-cell;">
                                <span id="schNode" class="" style="font-weight: 700;"></span>
                            </span>  
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 ScheduleTimeDiv">
                        <label><s:text name="lbl.campaign.schedule.time"/><span>*</span></label>
                        <div class="input-group">
                            <s:textfield name="campaign.mcScheduleTimeHrs" required="true" id="mcScheduleTimeHrs" cssClass="form-control" maxlength="2"/>
                            <span class="input-group-addon"  id="program_span"   style="cursor: pointer; display: table-cell;">
                                <span class="" style="font-weight: 700;"><s:text name="lbl.common.hrs"/></span>
                            </span>
                        </div> 
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 ScheduleTimeDiv">
                        <label>&nbsp;<span></span></label>
                        <div class="input-group ">
                            <s:textfield name="campaign.mcScheduleTimeMin" required="true" id="mcScheduleTimeMin" cssClass="form-control" maxlength="2"/>
                            <span class="input-group-addon"   id="program_span"  style="cursor: pointer; display: table-cell;">
                                <span class="" style="font-weight: 700;"><s:text name="lbl.common.min"/></span>
                            </span>
                        </div> 
                    </div>    

                </div>               
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <label><s:text name="lbl.campaign.watch.list"/></label>
                        <div class="form-group">
                            <s:radio name="campaign.mcPreviewYn" list="#{'1': 'Yes','0' :'No'}" id="mcPreviewYn"   data-toggle="tooltip" data-placement="top" />
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <label><s:text name="lbl.campaign.allow.unSubscribe"/></label>
                        <div class="form-group">
                            <s:radio name="campaign.mcAllowUnsubscribeYn" list="#{'1': 'Yes','0' :'No'}" id="mcAllowUnsubscribeYn"   data-toggle="tooltip" data-placement="top" />
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <label><s:text name="lbl.campaign.verify.conversions"/></label>
                        <div class="form-group">
                            <s:radio name="campaign.mcVerifyConversionYn" list="#{'1': 'Yes','0' :'No'}" id="mcVerifyConversionYn"  onchange="setTargetAmt(this.value)"   data-toggle="tooltip" data-placement="top" />
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12 TargetFieldDiv">
                        <label><s:text name="lbl.campaign.target.amt"/></label>
                        <div class="form-group">
                            <s:textfield name="campaign.mcTargetAmt" required="true" id="mcTargetAmt" cssClass="form-control"/>&nbsp;
                        </div>
                    </div>

                </div>
                <!--    <div class="row ">
                        <div class="col-lg-12 col-md-12 col-sm-9 col-xs-12 text-center">
                            <input type="button" name="btn_emails_save" id="btn_emails_save" value="Proceed" data-toggle="tooltip" data-placement="bottom" title="Proceed"  class="btn bg-red waves-effect" /> 
                        </div>
                         </div> -->


            </s:form>
            <div class="row b-t mt-20 row" id="div_btns">
                <div class="col-md-12 form-group center mt-10">
                    <%--   <button type="submit" class="btn btn-primary leads-tab" style="margin-top: 32px;float: right;" onclick="saveCampaignData()"> Proceed <i class="fa fa-arrow-circle-right"></i></button>  --%>
                    <input type="button" name="btn_camaign_save" id="btn_camaign_save" value="Proceed" data-toggle="tooltip" data-placement="bottom" title="<s:text name="btn.proceed"/>" style="margin-top: 32px;float: right;"  class="btn btn-primary leads-tab" /> 
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(document).ready(function () {
            $('[data-toggle="tooltip"]').tooltip();
            $('#frm_campaign').validate({
                onfocusout: false,
                onkeyup: false,
                onclick: false,
                wrapper: 'li',
                rules: {
                },
                messages: {
                    "campaign.mcCampName": {
                        required: "Please Enter Name"
                    },
                    "campaign.mcCampCode": {
                        required: "Please Enter Code"
                    },
                    "campaign.mcCampType": {
                        required: "Please Enter Campaign Type"
                    },
                    "campaign.mcDataSourceView": {
                        required: "Please Select View"
                    },
                    "campaign.mcDataSourceType": {
                        required: "Please Select Data Source"
                    },
                    "campaign.mcSenderId": {
                        required: "Please enter sender id"
                    },
                    "campaign.mcScheduleMode": {
                        required: "Please Select Schedule Frequency"
                    },
                    "campaign.mcScheduleValue": {
                        required: "Please Enter Schedule For Every"
                    },
                    "campaign.mcScheduleTimeHrs": {
                        required: "Please Enter Schedule Time in Hrs"
                    },
                    "campaign.mcScheduleTimeMin": {
                        required: "Please Enter Schedule Time in Minutes"
                    },
                    "campaign.mcTargetAmt": {
                        required: "Please Enter Target Amount"
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
                        $("#msg_email_setup").hide();
                },
                errorPlacement: function (error, element) {
                    $("#msg_email_setup").html(error);
                },
                invalidHandler: function (form, validator) {
                    var errors = validator.numberOfInvalids();
                    if (errors > 0)
                        $("#msg_email_setup").show();
                    else
                        $("#msg_email_setup").hide();
                }
            });

            var campId = $("#mcCampId").val();
            if (campId === "" || campId === undefined) {
                $('input[name="campaign.mcUnsubscribeInclYn"][value="1"]').prop('checked', true);
                $('input[name="campaign.mcRecurringYn"][value="0"]').prop('checked', true);
                $(".RecurringFieldsDiv").hide();
                $('input[name="campaign.mcPreviewYn"][value="0"]').prop('checked', true);
                $('input[name="campaign.mcAllowUnsubscribeYn"][value="1"]').prop('checked', true);
                $('input[name="campaign.mcVerifyConversionYn"][value="0"]').prop('checked', true);
                $(".CustomizedTextDivId").show();
                $(".TargetFieldDiv").hide();
            } else {
        <s:if test='campaign.mcRecurringYn == "1"'>
                $(".RecurringFieldsDiv").show();
        </s:if>
        <s:else>
                $(".RecurringFieldsDiv").hide();
        </s:else>
        <s:if test="campaign.mcDataSourceType == 'Excel'">
                $('#mcDataSourceType').val('E');
                $(".CustomizedTextDivId").hide();
                $(".Recurring").hide();
                $(".RecurringFieldsDiv").hide();
        </s:if>
        <s:else >
                $('#mcDataSourceType').val('A');
                $(".CustomizedTextDivId").show();
                $(".Recurring").show();

        </s:else>
        <s:if test='campaign.mcVerifyConversionYn == "1"'>
                $(".TargetFieldDiv").show();
        </s:if>
        <s:else>
                $(".TargetFieldDiv").hide();
        </s:else>
        <s:if test='campaign.mcCampType == "E"'>
                $("#senderId").show();
                $("#replyId").show();
        </s:if>
        <s:else>
                $("#senderId").show();
                $("#replyId").hide();
        </s:else>
        <s:if test='campaign.mcStatus != "P"'>
                $('#frm_campaign :not([type=submit])').prop('disabled', true);
        </s:if>
        <s:if test='campaign.mcScheduleMode == "H"'>
                $(".ScheduleTimeDiv").hide();
                $('#schNode').html("hour");
        </s:if>
        <s:else>
            <s:if test='campaign.mcScheduleMode == "D"'>
                $('#schNode').html("day");
            </s:if>
            <s:elseif    test='campaign.mcScheduleMode == "W"'>
                $('#schNode').html("day of week");
            </s:elseif>
            <s:elseif    test='campaign.mcScheduleMode == "M"'>
                $('#schNode').html("day of month");
            </s:elseif>

        </s:else>
            }


            $(".ExcelFileDivId").hide();
            $(".EmailFieldsDiv").show();
            if ($('#oper').val() === 'add') {
                $('#mcCampType').trigger('change');
            }
        });

        $("#btn_camaign_save").on("click", function () {
            var recurring = $("input[name='campaign.mcRecurringYn']:checked").val();
            if ($('#mcDataSourceType').val() === 'A' && recurring === '1')
            {
                if ($('#mcScheduleMode').val() === 'D' && $('#mcScheduleValue').val() > 356) {
                    alert("schedule value show be less than or equals 356 Days");
                    return false;
                } else if ($('#mcScheduleMode').val() === "W" && $('#mcScheduleValue').val() > 7) {
                    alert("schedule value show be less than or equals 7 Days");
                    return false;
                } else if ($('#mcScheduleMode').val() === "M" && $('#mcScheduleValue').val() > 31) {
                    alert("schedule value show be less than or equals 31 Days");
                    return false;
                } else if ($('#mcScheduleMode').val() === "H" && $('#mcScheduleValue').val() > 24) {
                    alert("schedule value show be less than or equals 24Hrs");
                    return false;
                } else if ($('#mcScheduleMode').val() !== null && $('#mcScheduleMode').val() !== "H") {
                    if ($('#mcScheduleTimeHrs').val() > 24) {
                        alert("schedule hrs show be less than  24Hrs");
                        return false;
                    } else if ($('#mcScheduleTimeMin').val() > 60) {
                        alert("schedule min show be less than 60 mins");
                        return false;
                    }
                }

            }

            var status = $('#mcStatus').val();
            if (status !== 'P' && status !== null)
            {
                openCampaignStep(2);
            } else {
                if (!$('#frm_campaign').valid()) {
                    return false;
                }
                if ($('#mcScheduleMode').val() === "H") {
                    $('#mcScheduleTimeHrs').val(0);
                    $('#mcScheduleTimeMin').val(0);
                }
                console.log("DATA ::" + JSON.stringify($("#frm_campaign").serialize()));
                // Get form
                var form = $('#frm_campaign')[0];
                var data = new FormData(form);
                block('block_body');
                $.ajax({
                    type: "POST",
                    url: APP_CONFIG.context + "/camp/saveCampaign.do",
                    data: data,
                    enctype: 'multipart/form-data',
                    processData: false,
                    contentType: false,
                    cache: false,
                    success: function (result) {
                        if (result.message === null) {

                            $("#frm_campaign").attr("action", "<%=request.getContextPath()%>/camp/loadDataParamFilter.do?mcCampId=" + result.mcCampId);
                            $("#frm_campaign").submit();
                        } else {
                            $('#msg_email_setup').empty().html(result.message).show();
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


        });

        function setList(val) {
            $("#message").html("");
            $("#bulksmsDetail").html("");
            if (val === "A") {
                $(".CustomizedTextDivId").show();
                $(".ExcelFileDivId").hide();
                $(".Recurring").show();
                $('input[name="campaign.mcRecurringYn"][value="0"]').prop('checked', true);
            } else {
                $(".CustomizedTextDivId").hide();
                $(".Recurring").hide();
                $(".RecurringFieldsDiv").hide();
            }
        }

        function setCampaignType(val) {
            if (val === "S") {
                $("#senderId").show();
                $("#replyId").hide();
            } else {
                $("#senderId, #replyId").show();
            }
            block('block_body');
            var senderOpt = '<option value="">--Select-</option>';
            var replyToOpt = '<option value="">--Select-</option>';
            $.ajax({
                type: "POST",
                data: {"campaign.mcCampType": $("#mcCampType").val()},
                url: APP_CONFIG.context + "/camp/loadSenderAndReplyList.do",
                success: function (data) {
                    $.each(data.aaData, function (i, d) {
                        if (d.info.includes("S"))
                        {
                            senderOpt += '<option value="' + d.key + '">' + d.value + '</option>';
                        } else {
                            senderOpt += '<option value="' + d.key + '">' + d.value + '</option>';
                            replyToOpt += '<option value="' + d.key + '">' + d.value + '</option>';
                        }

                    });
                    $('#frm_campaign #mcSenderId').html(senderOpt);
                    $('#frm_campaign #mcReplyTo').html(replyToOpt);
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
            /*    if (val === "E") {
             opt = '<option value="">--Select-</option>';
             $.ajax({
             type: "POST",
             data: {"campaign.mcCampType": "ER"},
             url: APP_CONFIG.context + "/camp/loadSenderAndReplyList.do",
             success: function (data) {
             $.each(data.aaData, function (i, d) {
             
             opt += '<option value="' + d.key + '">' + d.value + '</option>';
             });
             $('#frm_campaign #mcReplyTo').html(opt);
             },
             error: function (xhr, status, error) {
             alert("Error: " + error);
             },
             complete: function () {
             unblock('block_body');
             }
             });
             } */
        }

        function setScheduleTime(val) {

            if (val === "H")
            {
                $(".ScheduleTimeDiv").hide();
                $('#schNode').html("hour");
            } else {
                $(".ScheduleTimeDiv").show();
                if (val === 'D') {
                    $('#schNode').html("day");
                } else if (val === 'W')
                {
                    $('#schNode').html("day of week");
                } else if (val === 'M')
                {
                    $('#schNode').html("day of month");
                }
            }

        }

        function setRecurring(val)
        {
            if (val === '1')
            {
                $(".RecurringFieldsDiv").show();
            } else {
                $(".RecurringFieldsDiv").hide();
            }
        }

        function setTargetAmt(val)
        {
            if (val === '1')
            {
                $(".TargetFieldDiv").show();
            } else {
                $(".TargetFieldDiv").hide();
            }

        }



    </script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>