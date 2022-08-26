<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : campaignPage
    Created on : Aug 17, 2017, 12:51:48 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<div class="col-md-12 right-pad">
    <div class="dash-leads" style="border-top:0!important">
        <s:hidden name="oper" id="oper"/>
        <s:hidden name="campaignId" id="campaignId"/>
        <s:hidden name="campaignTab" id="campaignTab"/>
        <div class="acti-off my-reminder" style="margin-top: -3px;border-top-color: #FFF">
            <div class="acti-off-heads hcamp">
                <a href="javascript:void(0);" class="active" data-tab="tab_agent_details" onclick="openDashBoard();"><s:text name="lbl.dashboard"/></a>
                <a href="javascript:void(0);" class="" data-tab="tab_agent_db" onclick="openCampaignList();"><s:text name="lbl.campaign.campaigns"/></a>
                <a href="javascript:void(0);" class="" id="tab_report" data-tab="tab_agent_rep" onclick="openCampaignReports();"><s:text name="lbl.reports"/></a>
            </div>
            <ul class="act-name current" id="tab_agent_details">
                <div id="agent_dashboard">
                </div>
            </ul>
            <ul class="act-name" id="tab_agent_db">
                <div id="agent_campaigns"></div>
            </ul>
            <ul class="act-name" id="tab_agent_rep">
                <div id="agent_report">
                </div>
            </ul>
        </div>
    </div>
</div>
<s:form name="camp_dashs" id="camp_dashs"></s:form>
    <script type="text/javascript">
        $(document).ready(function () {
            
            $(function () {
                $('.acti-off-heads a').click(function () {
                    var tab_id = $(this).attr('data-tab');
                    $('.acti-off-heads a').removeClass('active');
                    $('.act-name').removeClass('current');

                    $(this).addClass('active');
                    $("#" + tab_id).addClass('current');
                });
                if ($("#campaignTab").val() === "report") {
                    $("#tab_report").click();
                } else if ($("#campaignTab").val() === "campaign") {
                    openCampaignList();
                    $('.acti-off-heads a').removeClass('active');
                    $('.act-name').removeClass('current');
                    $("#tab_report").addClass('active');
                    $("#tab_agent_db").addClass('current');
                }else{
                    openDashBoard();
                }

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
        });

        function openDashBoard() {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/campaignDashboard.do",
                data: {"mcCampId": $("#campaignId").val(), "oper": $("#oper").val()},
                success: function (result) {
                    $("#agent_dashboard").html(result).show();
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        }

        function openCampaignReports() {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/campaignReports.do",
                data: {},
                success: function (result) {
                    $("#agent_report").html(result).show();
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        }
        function openCampaignList() {
            //$("#camp_dashs").attr("action", "<%=request.getContextPath()%>/camp/openCampaignPage.do?campaignTab=campaign");
            //$("#camp_dashs").submit()
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/campaignList.do",
                data: {},
                success: function (result) {
                    $("#agent_campaigns").html(result).show();
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
