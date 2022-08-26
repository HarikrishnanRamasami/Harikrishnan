<%-- 
    Document   : marketingCampaign
    Created on : 19 Sep, 2019, 4:28:55 PM
    Author     : sutharsan.g
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<link href="<%=request.getContextPath()%>/css/marketingCampaign.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/smartWizard.js"></script>

<style>
    #wizard .stepContainer,  #wizard .actionBar {display: none;}
    .tile .tile-header #wizard {
        margin-bottom: 0px;
    }
    #wizard {
        margin-bottom: 15px;	
    }
    .swMain > ul {
        display: table;
        list-style: none;
        margin: 0 0 20px 0;
        padding: 0;
        position: relative;
        width: 100%;
    }

    .swMain > ul li {
        display: table-cell;
        text-align: center;
        width: 1%;
    }

    .swMain > ul li > a:before {
        border-top: 4px solid #CED1D6;
        content: "";
        display: block;
        font-size: 0;
        height: 1px;
        overflow: hidden;
        position: relative;
        top: 21px;
        width: 100%;
        z-index: 1;
    }

    .swMain > ul li:first-child > a:before {
        left: 50%;
        max-width: 51%;
    }

    .swMain > ul li:last-child > a:before {
        max-width: 50%;
        width: 50%;
    }

    .swMain > ul li > a.selected:before, .swMain li > a.done:before {
        border-color: #3399cc;
    }

    .swMain > ul .stepNumber {
        background-color: white;
        border: 5px solid #CED1D6;
        border-radius: 100% 100% 100% 100%;
        color: #546474;
        display: inline-block;
        font-size: 15px;
        height: 40px;
        line-height: 30px;
        position: relative;
        text-align: center;
        width: 40px;
        z-index: 2;
        cursor: pointer;
    }

    .swMain > ul li > a.selected .stepNumber {
        border-color: #3399cc;
    }

    .swMain ul li > a.done .stepNumber {
        border-color: #3399cc;
        background-color: #3399cc;
        color: #fff;
        text-indent: -9999px;
    }

    .swMain ul li > a.done .stepNumber:before {
        content: "\f00c";
        display: inline;
        float: right;
        font-family: FontAwesome;
        font-weight: 300;
        height: auto;
        text-shadow: none;
        margin-right: 7px;
        text-indent: 0;
    }

    .swMain ul li > a.done.wait .stepNumber {
        background-color: #F6F6F6 !important;
        color: #CCCCCC !important;
        text-indent: -0px !important;
    }

    .swMain ul li > a.done.wait .stepNumber:before {
        content: "" !important;
    }

    .swMain > ul li .stepDesc {
        color: #8b91a0;
        display: block;
        font-size: 14px;
        margin-top: 4px;
        max-width: 100%;
        table-layout: fixed;
        text-align: center;
        word-wrap: break-word;
        z-index: 104;
        cursor: pointer;
    }

    .swMain > ul li > a.selected .stepDesc, .swMain li > a.done .stepDesc {
        color: #2B3D53;
        font-weight: bold;
    }

    .swMain > ul li > a:hover {
        text-decoration: none;
    }

    .swMain > ul li > a.disabled {
        cursor: default;
    }

    .swMain .progress {
        margin-bottom: 30px;
    }

    .swMain .stepContainer {
        height: auto !important;
    }

    .swMain .loader {
        display: none;
    }

    .swMain [class^="button"], .swMain [class*=" button"] {
        display: none;
    }

    .swMain .close {
        display: none;
    }
    .swMain > ul{margin: 0 0 10px 0;}
    .dash-leads{
        padding: 20px;
    }
    .acti-off-heads.hcamp{
        display: none !important;
    }
    .acti-off-heads{
        margin-top: 37px;
        padding: 4px;
        border-bottom: 1px dotted #dde4ed;
        border-top-color: #FFF;
    }
</style>

<%
    int showPage = 1;
    if (request.getParameter("showPage") != null && !"".equals(request.getParameter("showPage").trim())) {
        showPage = Integer.parseInt(request.getParameter("showPage"));
    }
%>

<div class="acti-off-heads">
    <a href="javascript:void(0);" class="" data-tab="tab_agent_details" onclick="openDashBoard();"><s:text name="lbl.dashboard"/></a>
    <a href="javascript:void(0);" class="active" data-tab="tab_agent_db" onclick="openCampaignList();"><s:text name="lbl.campaign.campaigns"/></a>
    <a href="javascript:void(0);" class="" data-tab="tab_agent_rep" onclick="openCampaignReports();"><s:text name="lbl.reports"/></a>
</div>
<div class=" my-bord row">
    <div class="col-md-12 text-center">
        <s:if test="campaign.mcCampName != null">
            <h3 style="font-size: 20px;"><s:property value="campaign.mcCampId"/>. <s:property value="campaign.mcCampName"/></h3>
        </s:if>
    </div>
</div>
<div id="wizard" class="swMain" style="margin-top: 25px;">
    <ul>
        <li>
            <a href="#step-1" id="wiz-step-1" onclick="openCampaignStep(1)">
                <label class="stepNumber">1</label>
                <span class="stepDesc"><s:text name="lbl.campaign.campaigns.dashboard"/></span>
            </a>
        </li>
        <li>
            <a href="#step-2" id="wiz-step-2" onclick="openCampaignStep(2)">
                <label class="stepNumber">2</label>
                <span class="stepDesc"><s:text name="lbl.common.data"/></span>
            </a>
        </li>
        <li>
            <a href="#step-3" id="wiz-step-3" onclick="openCampaignStep(3)">
                <label class="stepNumber">3</label>
                <span class="stepDesc"><s:text name="lbl.common.data.mappings"/></span>
            </a>
        </li>
        <li>
            <a href="#step-4" id="wiz-step-4" onclick="openCampaignStep(4)">
                <label class="stepNumber">4</label>
                <span class="stepDesc"><s:text name="lbl.common.forms"/></span>                   
            </a>
        </li>
        <li>
            <a href="#step-5" id="wiz-step-5" onclick="openCampaignStep(5)">
                <label class="stepNumber">5</label>
                <span class="stepDesc"><s:text name="lbl.template"/></span>                   
            </a>
        </li>
        <li>
            <a href="#step-6" id="wiz-step-6" onclick="openCampaignStep(6)">
                <label class="stepNumber">6</label>
                <span class="stepDesc"><s:text name="lbl.common.data.filters"/></span>                   
            </a>
        </li>
        <li>
            <a href="#step-7" id="wiz-step-7" onclick="openCampaignStep(7)">
                <label class="stepNumber">7</label>
                <span class="stepDesc"><s:text name="lbl.common.journey"/></span>
            </a>
        </li>
        <li>
            <a href="#step-8" id="wiz-step-8" onclick="openCampaignStep(8)">
                <label class="stepNumber">8</label>
                <span class="stepDesc"><s:text name="lbl.common.summary"/></span>                   
            </a>
        </li>
    </ul>
    <div id="step-1"></div>
    <div id="step-2"></div>
    <div id="step-3"></div>
    <div id="step-4"></div>
    <div id="step-5"></div>
    <div id="step-6"></div>
    <div id="step-7"></div>
    <div id="step-8"></div>
</div>
<s:form name="camp_tab_head" id="camp_tab_head"></s:form>
    <script type="text/javascript">

        $(document).ready(function () {
            var stepNo = '<%=request.getParameter("stepNo")%>';
            $('#wizard').smartWizard({
                selected: parseInt(stepNo) - 1,
                enableAllSteps: false,
                keyNavigation: false
            });
            for (i = 0; i < stepNo; i++) {
                //console.log(" Index Val :: "+i);
                $('#wiz-step-' + i).addClass("selected");
            }
        });

        function openCampaignStep(step) {
            var url = "/", campId = "${mcCampId}";
            if (campId == "")
                return;
            if (step === 1) {
                url = APP_CONFIG.context + "/camp/openCampaignForm.do?oper=edit&mcCampId=" + campId;
            } else if (step === 2) {
                url = APP_CONFIG.context + "/camp/loadDataParamFilter.do?mcCampId=" + campId;
            } else if (step === 3) {
                url = APP_CONFIG.context + "/camp/openDataMappings.do?mcCampId=" + campId;
            } else if (step === 4) {
                url = APP_CONFIG.context + "/camp/loadForms.do?mcCampId=" + campId;
            } else if (step === 5) {
                url = APP_CONFIG.context + "/camp/loadCampaignTemplateData.do?mcCampId=" + campId;
            } else if (step === 6) {
                url = APP_CONFIG.context + "/camp/openCampaignFilter.do?mcCampId=" + campId;
            } else if (step === 7) {
                url = APP_CONFIG.context + "/camp/LoadCampaignJourney.do?mcCampId=" + campId;
            } else if (step === 8) {
                url = APP_CONFIG.context + "/camp/campaignSummary.do?mcCampId=" + campId;
            }
            window.location.href = url;
        }

        function goForward()
        {
            $('#wizard').smartWizard('goForward');
        }
        function goBackward()
        {
            $('#wizard').smartWizard('goBackward');
        }
        function openCampaignList() {
            $("#camp_tab_head").attr("action", "<%=request.getContextPath()%>/camp/openCampaignPage.do?campaignTab=campaign");
            $("#camp_tab_head").submit()

        }
        function openCampaignReports() {
            $("#camp_tab_head").attr("action", "<%=request.getContextPath()%>/camp/openCampaignPage.do?campaignTab=report");
            $("#camp_tab_head").submit();
        }
        function openDashBoard() {
            $("#camp_tab_head").attr("action", "<%=request.getContextPath()%>/camp/openCampaignPage.do");
            $("#camp_tab_head").submit();
        }
</script>