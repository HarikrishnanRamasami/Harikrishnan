<%-- 
    Document   : campaignSummary
    Created on : 20 Sep, 2019, 3:11:34 PM
    Author     : sutharsan.g
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 right-pad" id="block_body">
    <s:include value="/WEB-INF/pages/campaign/campaignHeader.jsp?stepNo=8"></s:include>
    <s:hidden name="mcCampId" id="mcCampId"/>
    <s:hidden name="campaign.mcStatus" id="mcStatus"/>
    <div class="dash-leads" style="border-top:0!important">
        <div class="container-fluid" style="margin-top: 50px;">
            <div class="row clearfix">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-2 form-group"><s:text name="lbl.common.name"/>:</div>
                        <div class="col-md-6 form-group">
                            <b><s:property value="campaign.mcCampName"/></b>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2 form-group"><s:text name="lbl.type"/>:</div>
                        <div class="col-md-6 form-group">
                            <b><s:property value="campaign.mcCampType"/></b>
                        </div>
                    </div>
                    <div class="row">
                        <s:if test='"1".equals(campaign.mcRecurringYn)'>
                            <div class="col-md-2 form-group"><s:text name="lbl.campaign.scheduled.on"/>:</div>
                            <div class="col-md-6 form-group">
                                <s:if test='"D".equals(campaign.mcScheduleMode)'>
                                    <b><s:text name="lbl.common.every"/> <s:property value="campaign.mcScheduleValue"/> <s:text name="lbl.common.day"/></b>
                                </s:if>
                                <s:elseif test='"H".equals(campaign.mcScheduleMode)'>
                                    <b><s:text name="lbl.common.every"/> <s:property value="campaign.mcScheduleValue"/> <s:text name="lbl.common.hour"/></b>
                                </s:elseif>
                                <s:elseif test='"M".equals(campaign.mcScheduleMode)'>
                                    <b><s:text name="lbl.campaign.every.month.on"/> <s:property value="campaign.mcScheduleValue"/></b>
                                </s:elseif>
                                <s:elseif test='"W".equals(campaign.mcScheduleMode)'>
                                    <b><s:text name="lbl.campaign.every.month.on"/> <s:property value="campaign.mcScheduleValue"/></b>
                                </s:elseif>
                            </div>
                        </s:if>
                    </div>
                </div>
            </div>
            <div class="col-md-8 board-icons1" style="margin-top: 10px;">   
                <s:if test='"P".equals(campaign.mcStatus)'>
                    <s:if test='"0".equals(campaign.mcRecurringYn)'>
                        <button class="btn btn-success waves-effect" onclick="delayStart();"><s:text name="btn.delaystart"/></button>&nbsp;&nbsp;
                    </s:if>
                        <button class="btn btn-success waves-effect" id="btn_start_camp" onclick="start();"><s:text name="btn.start"/></button>&nbsp;&nbsp;
                </s:if>
                <s:if test='"P".equals(campaign.mcStatus) && "0".equals(campaign.mcRecurringYn) && ("0".equals(campaign.mcAbYn) || campaign.mcAbYn == mull)'>
                    <button class="btn btn-primary leads-tab" id="ab_testing" onclick="openAb();" style="margin-top:2px;"><s:text name="btn.ab.testing"/></button>&nbsp;&nbsp;
                </s:if>
                <s:if test='campaign.mcStatus.equals("R")'>
                    <button class="btn btn-danger waves-effect" onclick="stopCampaign()" id="btn_stop"><s:text name="btn.stop"/></button>
                </s:if>
            </div>
        </div>
        <div class="row b-t mt-20" id="div_btns">
            <div class="col-md-12 form-group center mt-30">
                <button type="button" class="btn btn-primary leads-tab" onclick="goBackWard();"> <s:text name="btn.back="/> <i class="fa fa-arrow-circle-left"></i></button>
            </div>
        </div>
        <div class="modal fade" id="ab_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
            <div class="modal-dialog  modal-mm">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title"><s:text name="lbl.campaign.ab.performance"/></h4>
                        <div style="margin-top: -31px; float: right;">
                            <button class="save-btn btn btn-primary" id="btn_ab_start">&#10004;<s:text name="btn.start.ab"/></button>
                            <button class="close-btn btn" data-dismiss="modal" id="btn_ab_close">&#10006; <s:text name="btn.close"/></button>
                        </div>
                    </div>

                    <div class="body"  style="padding: 7px;">
                        <div id="err_ab_dialog" class="alert alert-danger" style="display: none;"></div>
                        <s:form id="frm_ab_performance" name="frm_ab_performance" method="post" theme="simple">
                            <s:include value="abPerformance.jsp"/>  
                        </s:form>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="ds_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
            <div class="modal-dialog  modal-mm">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title"><s:text name="lbl.campaign.delay.start"/></h4>
                        <div style="margin-top: -31px; float: right;">
                            <button class="save-btn btn btn-primary" onclick="start('D');">&#10004;<s:text name="btn.start"/></button>
                            <button class="close-btn btn" data-dismiss="modal">&#10006; <s:text name="btn.close"/></button>
                        </div>
                    </div>
                    <div class="body" style="padding: 7px;">
                        <div id="msg_ds_dialog" class="alert alert-danger" style="display: none;"></div>
                        <div class="row">
                            <div class="col-md-8">
                                <label><s:text name="lbl.common.dateandtime"/><span>*</span></label>
                                <div class="form-group">
                                    <input type="text" id="txt_ds_date" class="form-control textbox calicon" placeholder="DD/MM/YYYY MI:SS" required="true"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div> <s:form name="frm_camp_sum" id="frm_camp_sum"></s:form></div>

    <script type="text/javascript">
        $(document).ready(function () {
            //$("#btn_stop").hide();
            $("#txt_ds_date").datetimepicker({
                format: 'DD/MM/YYYY HH:mm',
                minDate: new Date()
            });
        });
        
        function goBackWard() {
            openCampaignStep(7);
        }
        
        function delayStart() {
            $("#ds_modal_dialog").modal("show");
        }

        function start(arg) {
            var d = {"mcCampId": "${mcCampId}", "campaign.mcDelayStartDate": ""};
            if(arg === 'D') {
                d['campaign.mcDelayStartDate'] = $("#txt_ds_date").val();
            }
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/startCampaign.do",
                data: d,
                success: function (result) {
                    if (result.messageType === "S") {
                        openCampaignStep(8);
                    } else {
                        $.notify(result.message, "custom");
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

        function stopCampaign() {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/stopCampaign.do",
                data: {"mcCampId": "${mcCampId}"},
                success: function (result) {
                    if (result.messageType === "S") {
                        openCampaignStep(8);
                    } else {
                        $.notify(result.message, "custom");
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

        function openAb() {
        
        $("#btn_start_camp").hide();
            // block('block_body');
            var actionTypeOpt = '<option value="">--Select-</option>';
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/checkTemplate.do",
                data: {"mcCampId": "${mcCampId}"},
                success: function (result) {
                    if (result.messageType === "S") {
                         $("#frm_ab_performance #mcCampId").val(${mcCampId});
                          $.each(result.actionTypeList, function (i, d) {
                            actionTypeOpt += '<option value="' + d.key + '">' + d.value + '</option>';
                        });
                      
                        $("#frm_ab_performance #mcAbActionType").html(actionTypeOpt);
                        $("#ab_modal_dialog").modal("show");
                       
                    } else {
                        $.notify(result.message, "custom");
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

        $("#btn_ab_start").on("click", function () {
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/startABCampaign.do",
                data: $("#frm_ab_performance").serialize(),
                success: function (result) {
                   if (result.messageType === "S") {
                        $("#ab_modal_dialog").modal("hide");
                          openCampaignStep(8);
                    } else {
                        $("#err_ab_dialog").html(result.message).show();
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
</script>