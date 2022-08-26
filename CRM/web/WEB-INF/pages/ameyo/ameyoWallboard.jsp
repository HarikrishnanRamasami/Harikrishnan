<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : ameyoWallboard
    Created on : Feb 22, 2018, 7:14:53 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
    .agentAnsCall {
        color: #5c8205f7;
        font-size: 39px;
    }
    .agentAbonCall {
        color: #efc80b;
        font-size: 39px;
    }
    .totalcall {
        color: white;
        font-size: 114px;
    }
    .sladet {
        text-align: center;
    }
    .prev, .next {
        cursor: pointer;
        position: absolute;
        top: 300px;
        width: auto;
        margin-top: -22px;
        padding: 16px;
        color: #4c4747;
        font-weight: bold;
        font-size: 70px;
        transition: 0.6s ease;
        border-radius: 0 3px 3px 0;
    }

    /* Position the "next button" to the right */
    .next {
        right: 0;
        border-radius: 3px 0 0 3px;
    }

    /* On hover, add a black background color with a little bit see-through */
    .prev:hover, .next:hover {
        background-color: rgba(0,0,0,0.8);
    }
    .wallboard-dashes{
        width:100%;
        min-height: 1000px;
        background-color: #1e2a35;
        color: #bdb3b3;
        margin-top: -20px;
        /*padding-left:4%;*/
    }
    .wallboard-table{
        color: white;
    }
    .agentwall{
        color: white;
        text-align: center;
        background-color: #1e2a35;
    }
    .agentStats {
        width: 35px;
        color: white;
        text-align: center;
        border-radius: 15px;
    }

    .sladet {
        text-align: center;
    }
    .agentTable, th, td {
        border: 1px solid white;
        border-collapse: collapse;
        padding-top: 15px;
    }
    .agentHead {
        text-align: center;
        color: white;
    }
    /* Slideshow container */

    /* Hide the images by default */
    .mySlides {
        display: none;
    }
    .bg-round {
        padding: 4px 4px 2px 4px;
        border-radius: 6px;
    }
    /* Next & previous buttons */
</style>
<div class="row" style="padding: 15px;">
    <div class="col-md-6">
        <a href="wallboardPage.do?sessionId=${param.sessionId}"><img src="images/logo.png" class="img-responsive log2"></a>
    </div>
    <div class="col-md-4" style="padding: 15px;">
        <select id="agentCampaigns" class="form-control" style="width: 150px;"></select>
    </div>
    <div class="col-md-2" style="padding: 15px;">
        <a href="#" id="btn_carousel_control" onclick="carouselControl()"><i class="fa fa-pause fa-3x"></i></a>
        &nbsp;&nbsp;<a href="#" onclick="wallboardLogout()" title="Logout"><i class="fa fa-sign-out fa-3x"></i></a>
    </div>
</div>
<div class="wallboard-dashes">
    <div class="slideshow-container" id="wallboardSlides">
        <div class="mySlides">
            <div style="text-align: center;">
                <h3><s:text name="lbl.call.center.sla"/></h3>
            </div>
            <section class="col-md-12">
                <div class="col-md-4" style="height: 250px;">
                    <div class="" style="text-align: center;">
                        <h3></i><s:text name="lbl.calls.answerd"/></h3>
                        <div class="" style="margin-top: 49px;">
                            <li class="totalcall" id="div_sla_answer_calls"></li>
                        </div>
                    </div>
                </div>
                <div class="col-md-4" style="height: 250px;">
                    <div class="sladet">
                        <h3></i><s:text name="lbl.calls.missed"/></h3>
                    </div>
                    <div class="" style="margin-left: 173px; margin-top: 49px;">
                        <li class="totalcall" id="div_sla_abonded_calls"></li>
                    </div>
                </div>
                <div class="col-md-4" style="height: 250px;">
                    <div class="sladet">
                        <h3><s:text name="lbl.total.calls"/></h3>
                    </div>
                    <div class="" style="margin-left: 184px; margin-top: 39px;">
                        <li class="totalcall" id="div_sla_total_calls"></li>
                    </div>
                </div>
            </section>
            <section class="col-md-12">
                <div class="col-md-6" style="height: 250px;">
                    <div class="sladet">
                        <h3><s:text name="lbl.today.answer.rate"/></h3>
                    </div>
                    <div id="div_answer_rate" style="height: 300px; width: 100%; margin-top: 46px;"></div>
                </div>
            </section>
        </div>
        <div class="mySlides">
            <div style="text-align: center;">
                <h3><s:text name="lbl.Real.time.status"/></h3>
            </div>
            <section class="col-md-12">
                <div class="col-md-6 right-pad">
                    <div class="" style="height: 600px;">
                        <div class="" style="text-align: center;">
                            <h3></i><s:text name="lbl.agent.status"/></h3>
                        </div>
                        <div class="agentStatus">
                            <table id="tbl_agentStatus" class="agentTable" style="background-color: #1e2a35; width: 80%; overflow: scroll;">
                                <thead>
                                    <tr style="background-color:#1e2a35;">
                                        <th class="agentHead"><s:text name="lbl.name"/></th>
                                        <th class="agentHead" style="background-color: #FF9800;"><s:text name="lbl.status"/></th>
                                        <th class="agentHead" style="background-color: #FF9800;"><s:text name="lbl.auto.call"/></th>
                                        <th class="agentHead" style="background-color: #FF9800;"><s:text name="lbl.phone.status"/></th>
                                        <th class="agentHead" style="background-color: #d8514d;"><s:text name="lbl.duration"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 right-pad">
                    <div class="" style="height: 600px;">
                        <div class="" style="text-align: center;">
                            <h3><s:text name="lbl.top.ten.agent"/></h3>
                            <div id="agent_details">
                                <div id="div_top_agents" style="height: 300px; width: 100%;"></div>
                            </div>
                            <div id="agent_calls" style="margin-top: 35px;">
                                <h3><s:text name="lbl.calls.received.answered"/></h3>
                                <div class="" style="height: 150px;">
                                    <li class="totalcall"><h5 class="totalcall" id="div_total_calls"></h5></li>
                                </div>
                                <div class="">
                                    <li class="agentAnsCall"><s:text name="lbl.answered"/>:<span id="div_answer_calls"></span></li>
                                    <li class="agentAbonCall"><s:text name="lbl.abonded"/>:<span id="div_abonded_calls"></span></li>                        
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <a class="prev" onclick="prevSlides(-1)">&#10094;</a>
        <a class="next" onclick="nextSlides(1)">&#10095;</a>
    </div>
</div>
<script type="text/javascript">
    var WALLBOARD_DATA = {sessionId: "${param.sessionId}", campaignId: 0, userIdPrefix: ""};
    $(document).ready(function () {
        pingSession();
        setInterval(function() {
            pingSession();
        }, (1000 * 60 * 5));

        CHART_TOP_10_AGENTS = echarts.init(document.getElementById('div_top_agents'));
        CHART_AGENT_CALL_DETAILS = echarts.init(document.getElementById('div_answer_rate'));
        
        try {
            let loginData = JSON.parse(localStorage.getItem("USER_INFO"));
            let agentCampaigns = '';
            $.each(loginData.campaignInfos, function(i, o) {
                agentCampaigns += '<option value="'+o.campaignId+'">'+o.campaignName+'</option>';
            });
            $("#agentCampaigns").html(agentCampaigns);
        } catch (err) {
            console.log("Error: ", err);
        }
        $("#agentCampaigns").on("change", function() {
            WALLBOARD_DATA.campaignId = Number($(this).val());
            if(WALLBOARD_DATA.campaignId === 4) {// UAE
                WALLBOARD_DATA.userIdPrefix = "r_002_";
            } else if(WALLBOARD_DATA.campaignId === 5) {// QIC
                WALLBOARD_DATA.userIdPrefix = "r_001_";
            } else if(WALLBOARD_DATA.campaignId === 8) {// OMAN
                WALLBOARD_DATA.userIdPrefix = "r_006_";
            }
            clearThread();
            startThread();
        });
        
        setTimeout(function() {
            $("#agentCampaigns").trigger("change");
        }, 2000);
        
        carousel();
        THREAD_CAROUSEL = setInterval(function() {
            carousel();
        }, (1000 * 10));// Change tab every 10 seconds
    });
    
    window.onresize = function () {
        setTimeout(function () {
            CHART_TOP_10_AGENTS.resize();
        }, 200);
    };
    
    window.onresize = function () {
        setTimeout(function () {
            CHART_AGENT_CALL_DETAILS.resize();
        }, 200);
    }
        
    showSlides(CURRENT_SLIDE_INDEX);
    function nextSlides(n) {
        showSlides(CURRENT_SLIDE_INDEX += n);
        CHART_TOP_10_AGENTS.resize();
        CHART_AGENT_CALL_DETAILS.resize();
    }

    function prevSlides(n) {
        showSlides(CURRENT_SLIDE_INDEX -= n);
        CHART_TOP_10_AGENTS.resize();
        CHART_AGENT_CALL_DETAILS.resize();
    }

    function showSlides(n) {
        let slides = document.getElementsByClassName("mySlides");
        if (n > slides.length) {
            CURRENT_SLIDE_INDEX = 1;
        } else if (n < 1) {
            CURRENT_SLIDE_INDEX = slides.length;
        }
        for (let i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        slides[CURRENT_SLIDE_INDEX - 1].style.display = "block";
    }
    
    function carouselControl() {
        if (THREAD_CAROUSEL) {
            // stop
            console.log("Carousel - stop");
            clearInterval(THREAD_CAROUSEL);
            THREAD_CAROUSEL = null;
            $("#btn_carousel_control i").removeClass("fa-pause").addClass("fa-play");
        } else {
            console.log("Carousel - start");
            THREAD_CAROUSEL = setInterval(carousel, 10000);
            $("#btn_carousel_control i").removeClass("fa-play").addClass("fa-pause");
        }
    }

    function carousel() {
        let x = document.getElementsByClassName("mySlides");
        for (let i = 0; i < x.length; i++) {
            x[i].style.display = "none";
        }
        CURRENT_SLIDE_INDEX++;
        if (CURRENT_SLIDE_INDEX > x.length) {
            CURRENT_SLIDE_INDEX = 1
        }
        x[CURRENT_SLIDE_INDEX - 1].style.display = "block";
        CHART_TOP_10_AGENTS.resize();
        CHART_AGENT_CALL_DETAILS.resize();
    }
                
    function loadTop10AgentDetails() {
        console.info("Loading Top10 Agent Details", WALLBOARD_DATA);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            url: URL_WALLBOARD,
            headers: {"sessionid": WALLBOARD_DATA.sessionId, "path": "stats/getAllStats"},
            data: '{"statsName": "AgentCallStatsT2D2", "fromDate": "' + getTime('R') + '"}',
            success: function (result) {
                console.log(result);
                let outerArr = result[result.length - 1];
                let top10Arr = [];
                for (i = 0; i < outerArr.statsArr.length; i++) {
                    if (outerArr.statsArr[i].campaign_id === WALLBOARD_DATA.campaignId) {
                        for (j = 0; j < outerArr.statsArr[i].statsArr.length; j++) {
                            var innerArr = outerArr.statsArr[i].statsArr[j];
                            let item = {};
                            item[innerArr.user_id.replace(WALLBOARD_DATA.userIdPrefix, "")] = innerArr.userCampaignCallAnsweredCount;
                            top10Arr.push(item);
                        }
                    }
                }
                top10Arr = top10Arr.sort(function (a, b) {
                    return a[Object.keys(a)[0]] - b[Object.keys(b)[0]];
                });
                let top10 = {};
                for (i = 0; i < top10Arr.length; i++)
                    top10 = Object.assign(top10, top10Arr[i]);
                OPTION_TOP_10_AGENTS.yAxis[0].data = Object.keys(top10);
                OPTION_TOP_10_AGENTS.series[0].data = Object.keys(top10).map(function (key) {
                    return top10[key];
                });
                OPTION_TOP_10_AGENTS.xAxis[0].max = Number(OPTION_TOP_10_AGENTS.series[0].data[top10Arr.length - 1]) + 20;
                CHART_TOP_10_AGENTS.setOption(OPTION_TOP_10_AGENTS);
                CHART_TOP_10_AGENTS.resize();
            },
            error: function (xhr, status, error) {
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function loadAgentCallDetails() {
        console.info("Loading Agent Call Details", WALLBOARD_DATA);
        let anscalls = 0, abondcalls = 0, totalcalls = 0;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            url: URL_WALLBOARD,
            headers: {"sessionid": WALLBOARD_DATA.sessionId, "path": "stats/getAllStats"},
            data: '{"statsName": "CampaignCallStatsT24D24", "fromDate":"' + getTime('F') + '"}',
            success: function (result) {
                console.log(JSON.stringify(result));
                let outerArr = result[result.length - 1];
                for (i = 0; i < outerArr.statsArr.length; i++) {
                    var innerArr = outerArr.statsArr[i];
                    if (innerArr.campaign_id === WALLBOARD_DATA.campaignId) {
                        totalcalls = innerArr.campaignCallCount;
                        anscalls = innerArr.campaignCallAnsweredCount;
                        abondcalls = innerArr.campaignCallAbandonedCount;
                        $("#div_total_calls").text(totalcalls);
                        $("#div_answer_calls").text(anscalls);
                        $("#div_abonded_calls").text(abondcalls);

                        $("#div_sla_total_calls").text(totalcalls);
                        $("#div_sla_answer_calls").text(anscalls);
                        $("#div_sla_abonded_calls").text(abondcalls);
                        OPTION_AGENT_CALL_DETAILS.series[0].data = [
                            {value: anscalls, name: 'Answered - ' + anscalls, itemStyle: LABEL_ANSWERED},
                            {value: abondcalls, name: 'Abandoned - ' + abondcalls, itemStyle: LABEL_ABANDONED}
                        ];
                        OPTION_AGENT_CALL_DETAILS.legend.data = [
                            {name: 'Answered - ' + anscalls, itemStyle: LABEL_ANSWERED},
                            {name: 'Abandoned - ' + abondcalls, itemStyle: LABEL_ABANDONED}
                        ];
                        CHART_AGENT_CALL_DETAILS.setOption(OPTION_AGENT_CALL_DETAILS);
                        CHART_AGENT_CALL_DETAILS.resize();
                    }
                }
            },
            error: function (xhr, status, error) {
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function loadAgentsStatus() {
        console.info("Loading Agents Status", WALLBOARD_DATA);
        let cc = [];//[{"startTime":1519817933932,"sessionId":"d151-5a8403e2-ses-r_001_alaa-hkT9WY1e-577","campaignId":5,"userId":"r_001_alaa","isWorking":true,"isMonitoring":false,"campaignTeamIds":[],"isReady":true,"isOnAuto":true,"agentBreakReason":"Break"},{"startTime":1519798392179,"sessionId":"d151-5a8403e2-ses-r_001_wissal-Qcu4tZjs-555","campaignId":5,"userId":"r_001_wissal","isWorking":true,"isMonitoring":false,"campaignTeamIds":[],"isReady":true,"isOnAuto":false,"agentBreakReason":"Un-Available"},{"startTime":1519817714588,"sessionId":"d151-5a8403e2-ses-r_001_leena-jykfe2ZK-575","campaignId":5,"userId":"r_001_leena","isWorking":true,"isMonitoring":false,"campaignTeamIds":[],"isReady":false,"isOnAuto":false,"agentBreakReason":"WhatsApp"},{"startTime":1519817731749,"sessionId":"d151-5a8403e2-ses-r_001_mary-onGV00bb-576","campaignId":5,"userId":"r_001_mary","isWorking":true,"isMonitoring":false,"campaignTeamIds":[],"isReady":true,"isOnAuto":true,"agentBreakReason":"Un-Available"},{"startTime":1519817665866,"sessionId":"d151-5a8403e2-ses-r_001_farahnaz-NGzUJN3Z-574","campaignId":5,"userId":"r_001_farahnaz","isWorking":true,"isMonitoring":false,"campaignTeamIds":[],"isReady":true,"isOnAuto":true,"agentBreakReason":"Break"},{"startTime":1519802234232,"sessionId":"d151-5a8403e2-ses-r_001_anand-H6nX5yBQ-556","campaignId":5,"userId":"r_001_anand","isWorking":true,"isMonitoring":false,"campaignTeamIds":[],"isReady":false,"isOnAuto":false,"agentBreakReason":""},{"startTime":1519705744568,"sessionId":"d151-5a8403e2-ses-qicdoharetail-XnjwOxzM-486","campaignId":5,"userId":"qicdoharetail","isWorking":false,"isMonitoring":true,"campaignTeamIds":[],"isReady":false,"isOnAuto":false,"agentBreakReason":""}];
        let voice = [];//[{"numCallsSinceLogin":0,"dispositionSummary":{"005 General Enquiry":6,"002 Claim Intimation":1,"003 Claim Followup":1},"isListeningVoiceLog":false,"status":"inactive","isWorking":true,"isOnHold":false,"currentCallAgentQueueId":null,"userId":"r_001_alaa","sessionId":"d151-5a8403e2-ses-r_001_alaa-hkT9WY1e-577","crtObjectId":"d151-5a8403e2-ses-r_001_alaa-hkT9WY1e-577-uce-3005@20","campaignId":5,"ownerCRTObjectId":null,"name":"r_001_alaa","statusChangeTime":1519824306228,"firstConnected":null},{"numCallsSinceLogin":0,"dispositionSummary":{"005 General Enquiry":8,"001 New Quotation":2,"003 Claim Followup":1,"004 Policy Renewal":1},"isListeningVoiceLog":false,"status":"inactive","isWorking":true,"isOnHold":false,"currentCallAgentQueueId":null,"userId":"r_001_wissal","sessionId":"d151-5a8403e2-ses-r_001_wissal-Qcu4tZjs-555","crtObjectId":"d151-5a8403e2-ses-r_001_wissal-Qcu4tZjs-555-uce-3011@20","campaignId":5,"ownerCRTObjectId":null,"name":"r_001_wissal","statusChangeTime":1519812808831,"firstConnected":null},{"numCallsSinceLogin":0,"dispositionSummary":{"004 Policy Renewal":2},"isListeningVoiceLog":false,"status":"inactive","isWorking":false,"isOnHold":false,"currentCallAgentQueueId":null,"userId":"r_001_leena","sessionId":"d151-5a8403e2-ses-r_001_leena-jykfe2ZK-575","crtObjectId":"d151-5a8403e2-ses-r_001_leena-jykfe2ZK-575-uce-3001@20","campaignId":5,"ownerCRTObjectId":null,"name":"r_001_leena","statusChangeTime":1519824188118,"firstConnected":null},{"numCallsSinceLogin":1,"dispositionSummary":{"004 Policy Renewal":5},"isListeningVoiceLog":false,"status":"connected","isWorking":true,"isOnHold":false,"currentCallAgentQueueId":7,"userId":"r_001_mary","sessionId":"d151-5a8403e2-ses-r_001_mary-onGV00bb-576","crtObjectId":"d151-5a8403e2-ses-r_001_mary-onGV00bb-576-uce-3006@20","campaignId":5,"ownerCRTObjectId":"d151-5a8403e2-vce-daf-7148","name":"r_001_mary","statusChangeTime":1519823989220,"firstConnected":"d151-5a8403e2-vce-daf-7148"},{"numCallsSinceLogin":0,"dispositionSummary":{"001 New Quotation":2,"005 General Enquiry":6,"002 Claim Intimation":1,"003 Claim Followup":1,"004 Policy Renewal":3},"isListeningVoiceLog":false,"status":"inactive","isWorking":true,"isOnHold":false,"currentCallAgentQueueId":null,"userId":"r_001_farahnaz","sessionId":"d151-5a8403e2-ses-r_001_farahnaz-NGzUJN3Z-574","crtObjectId":"d151-5a8403e2-ses-r_001_farahnaz-NGzUJN3Z-574-uce-3008@20","campaignId":5,"ownerCRTObjectId":null,"name":"r_001_farahnaz","statusChangeTime":1519824507808,"firstConnected":null}];
        $.when(
            $.ajax({
                type: "GET",
                contentType: "application/json",
                dataType: "json",
                url: URL_WALLBOARD,
                headers: {"sessionid": WALLBOARD_DATA.sessionId, "path": "/cc/userCampaignRuntimes/getByCampaign"},
                data: {"campaignId": WALLBOARD_DATA.campaignId},
                success: function (result) {
                    cc = result;
                }
            }),
            $.ajax({
                type: "GET",
                contentType: "application/json",
                dataType: "json",
                url: URL_WALLBOARD,
                headers: {"sessionid": WALLBOARD_DATA.sessionId, "path": "/voice/userVoiceCampaignRuntimes/getByCampaign"},
                data: {"campaignId": WALLBOARD_DATA.campaignId},
                success: function (result) {
                    voice = result;
                }
            })
        ).then(function() {
            console.log(cc);
            console.log(voice);
            let data;
            $.each(cc, function(i, o) {
                var voiceItem = voice.filter(d => d.userId == o.userId);
                console.log(JSON.stringify(voiceItem));
                item = $.extend(o, o, voiceItem[0]);
                data += "<tr class='agentwall' style='color:white;'><td>" + item.userId.replace(WALLBOARD_DATA.userIdPrefix, "") + "</td><td>";
                if (item.isReady === true) {
                    data += "<span class='bg-green bg-round'>Available</span>";
                } else {
                    data += "<span class='bg-red bg-round'>Not Available</span>";
                }
                data += "</td><td>" + (item.isOnAuto ? "<b style='color: #4CAF50;'>On</b>" : "<b style='color: #F44336;'>Off</b>") + "</td><td>";
                if (item.isReady === true) {
                    if(item.status === "inactive") {
                        data += "<i class='fa fa-2x fa-smile-o'></i>&nbsp;<span class='bg-orange bg-round'>Ready</span>";
                    } else if(item.status === "connected") {
                        data += "<i class='fa fa-2x fa-volume-control-phone'></i>&nbsp;<span class='bg-purple bg-round'>" + item.status + "</span>";
                    } else if(item.status === "WhatsApp") {
                        data += "<i class='fa fa-2x fa-whatsapp'></i>&nbsp;<span class='bg-green bg-round'>" + item.status + "</span>";
                    } else if(item.status === "hungup") {
                        data += "<i class='fa fa-2x fa-check-square'></i>&nbsp;<span class='bg-teal bg-round'>" + item.status + "</span>";
                    } else {
                        data += "<i class='fa fa-2x fa-frown-o'></i>&nbsp;<span class='bg-green bg-round'>" + item.status + "</span>";
                    }
                } else if (item.isReady === false) {
                    data += "<i class='fa fa-2x fa-coffee'></i>&nbsp;<span class='bg-red bg-round'>" + (item.agentBreakReason === "" ? "Not Available" : item.agentBreakReason) + "</span>";
                }
                let startTime = item.statusChangeTime;//item.startTime;
                if(WALLBOARD_DATA.campaignId === 4 || WALLBOARD_DATA.campaignId === 8)
                    startTime = startTime + (ONE_HOUR * 1)
                data += ("</td><td>" + moment(startTime).format("DD/MMM/YYYY HH:mm") + "</td></tr>");
            });
            $("#tbl_agentStatus > tbody").html(data);
        });
    }
</script>
