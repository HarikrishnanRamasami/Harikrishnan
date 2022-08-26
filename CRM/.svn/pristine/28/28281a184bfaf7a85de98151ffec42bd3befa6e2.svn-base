<%-- 
    Document   : commonLayoutV2
    Created on : 20 Oct, 2017, 2:34:48 PM
    Author     : thoufeak.rahman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="qa.com.qic.common.util.ApplicationConstants"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Dashboard</title>
        <meta charset="utf-8">
        <s:if test="%{@com.qa.common.util.ApplicationConstants@Environment.MVP_QA == @com.qa.common.util.ApplicationConstants@ENVIRONMENT ||
              @com.qa.common.util.ApplicationConstants@Environment.MVP_DEV == @com.qa.common.util.ApplicationConstants@ENVIRONMENT}">
            <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon_at.ico">
        </s:if>
        <s:elseif test="%{@com.qa.common.util.ApplicationConstants@COMPANY_CODE_BEEMA.equals(@com.qa.common.util.ApplicationConstants@DEFAULT_COMPANY_CODE)}">
            <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon_bem.ico">
        </s:elseif>
        <s:elseif test="%{@com.qa.common.util.ApplicationConstants@COMPANY_CODE_MED_DOHA.equals(@com.qa.common.util.ApplicationConstants@DEFAULT_COMPANY_CODE)}">
            <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon_qlm.ico">
        </s:elseif>
        <s:else>
            <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon_qic.ico">
        </s:else>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/font-awesome/css/font-awesome.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/bootstrap/css/bootstrap.min.css" />
        <link href="<%=request.getContextPath()%>/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/css/styleV2.css?v=${application.R_TOCKEN}" rel="stylesheet" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/main.css?v=${application.R_TOCKEN}" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/datatables/datatables.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/flags.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/magnific-popup/magnific-popup.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/line-awesome.min.css">
        <link href="<%=request.getContextPath()%>/plugins/richText/css/jquery-te-1.4.0.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/plugins/innovate/css/datepicker.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/plugins/bootstrap-fileupload/bootstrap-fileupload.min.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/plugins/bootstrap-multiselect/bootstrap-multiselect.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/jquery-ui.css" />
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery-ui.js"></script>
        <script src="<%=request.getContextPath()%>/plugins/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/datatables/datatables.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/fileDownload/jquery.fileDownload.js"></script>
        <script type="text/javascript">
            var APP_CONFIG = {
                context: "<%=request.getContextPath()%>",
                crmOrigin: window.location.href,
                messageType: {
                    success: "<%=ApplicationConstants.MESSAGE_TYPE_SUCCESS%>",
                    error: "<%=ApplicationConstants.MESSAGE_TYPE_ERROR%>"
                },
                companyList: {"001": {"code": "QA", "name": "Doha"}, "002": {"code": "AE", "name": "Dubai"}, "005": {"code": "KW", "name": "Kuwait"}, "006": {"code": "OM", "name": "Oman"}, "100": {"code": "QA", "name": "Doha"}, "009": {"code": "QA", "name": "Doha"}},
                companyCode: "<s:property value="#session.USER_INFO.companyCode" />",
                appType: "<%=ApplicationConstants.APP_TYPE%>",
                ameyo: {
                    channel: "<s:property value="#session.USER_INFO.ameyoChannel" />",
                    url: "<%=ApplicationConstants.RETAIL_CTI_AMEYO_URL%>",
                    loggedIn: "<%=ApplicationConstants.MESSAGE_TYPE_ERROR%>"
                },
                agentType: <s:property value="#session.USER_INFO.agentType" />,
                extension: "<s:property value="#session.USER_INFO.userTelNo" />",
                popupFeatures: "toolbar=0, scrollbars=1, location=0, statusbar=0, menubar=0, resizable=1, width=1100, height=800"
            };
            var ls = localStorage, ss = sessionStorage;
        </script>
        <style type="text/css">
            .ui-front {
                z-index: 99999!important;
            }
        </style>
    </head>
    <body>
        <section class="dashboard-mainfunction">
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                            <div id="nav-icon1">
                            <span></span>
                            <span></span>
                            <span></span>
                          </div>
                        </button>
                        <a class="navbar-brand log1" href="<%=request.getContextPath()%>/home.do"><img src="<%=request.getContextPath()%>/images/logo_anoud_crm.svg" class="img-responsive log2"></a>
                    </div>
                    <div class="collapse navbar-collapse navbar-right" id="myNavbar">
                        <ul class="nav navbar-nav">
                            <!--li>
                                <input type="text" name="search" class="form-control" id="txt_gbl_search">
                            </li-->
                            <li class="input-group">
                                <div class=" tp-search-wrapper pull-left">
                                <input type="text" name="search" class="form-control tp-search" id="txt_gbl_search" placeholder="Search">
                                <span class="input-group-addon" onclick="search();"><i class="fa fa-search"></i>
                                </span>
                                </div>
                                <div class="pull-left">
                                    <a href="<%=request.getContextPath()%>/openSearchPage.do" alt="Advanced Search"><div class="btn btn-advanced-search" style="">Advanced Search</div></a>
                                </div>
                                <div  class="clearfix"></div>
                            </li>
                            <li><div class="tp-menu-seperator"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/tp-menu-seperator.png" alt="separator" class="img-respondsive"/></div></li>
                            <li>  
                                <div id="country"></div>
                            </li>
                            <li>
                                <div class="tp-menu-seperator"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/tp-menu-seperator.png" alt="separator" class="img-respondsive"/></div>
                            </li>
                            <li>
                                <span class="user-images">
                                    <img src="<%=request.getContextPath()%>/plugins/innovate/images/prof-img.png" alt="user-images" class="img-respondsive"/>
                                </span>
                                <span class="user-details" title="<s:property value="#session.USER_INFO.userName" />"><s:property value="#session.USER_INFO.userName" /></span>
                            </li>  
                            <li><div class="tp-menu-seperator"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/tp-menu-seperator.png" alt="separator" class="img-respondsive"/></div></li>
                            <li class="logout-tpmm"><a href="javascript:void(0);" onclick="logoutCrm()" class="lgout-small-de" ><img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_view_logout_tpmm.svg" height="20" alt="Logout" class="img-respondsive" data-toggle = "tooltip" data-placement = "bottom" title="Logout" /></a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        </section>
        <section class="dashboard-function">
            <s:if test='%{#session.USER_INFO.userAdminYn <= 1}'>
                <div class="page-container sidebar-collapsed shake" >
                    <div class="sidebar-menu">
                        <div id="sidebar" >
                            <div class="logo">
                                <a href="#" class="sidebar-icon shake"> <span class="fa fa-bars"></span></a>
                            </div>
                            <div class="menu">
                                <ul id="menu">
                                    <s:iterator value="#session.USER_INFO.menuList" var="row">
                                        <li>
                                            <s:if test='"D".equals(#row.key)'>
                                                <a data-toggle="tooltip" data-placement="top" title="Dashboard" href="<%=request.getContextPath()%>/home.do"><i class="new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_dashboard2.svg" alt="Dashboard" style="width: 27px; margin-left: -3px;" /></i><span>Dashboard</span></a>
                                            </s:if>
                                            <s:elseif test='"T".equals(#row.key)'>
                                                <a data-toggle="tooltip" data-placement="top" title="Task" href="<%=request.getContextPath()%>/openTaskDetails.do"><i class="new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_tasks.svg" alt="Tasks" /></i><span>Tasks</span></a>
                                            </s:elseif>
                                            <s:elseif test='"A".equals(#row.key)'>
                                                <a data-toggle="tooltip" data-placement="top" title="Activities" href="<%=request.getContextPath()%>/openActivitiesEntryPage.do"><i class="new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_activities.svg" alt="Activities" /></i><span>Activities</span></a>
                                            </s:elseif>
                                            <s:elseif test='"L".equals(#row.key)'>
                                                <s:if test='#session.USER_INFO.userAdminYn == 1'>
                                                    <a data-toggle="tooltip" data-placement="top" title="Leads" href="<%=request.getContextPath()%>/openLeadsEntryPage.do"><i class="new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_leads.svg" alt="Leads" /></i><span>Leads</span></a>
                                                </s:if>
                                            </s:elseif>
                                            <s:elseif test='"C".equals(#row.key)'>
                                                <a data-toggle="tooltip" data-placement="top" title="Contacts" href="<%=request.getContextPath()%>/openInsuredDetailPage.do"><i class="new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_contact.svg" alt="Contact" /></i><span>Contact</span></a>   
                                            </s:elseif>
                                            <s:elseif test='"S".equals(#row.key)'>
                                                <a data-toggle="tooltip" data-placement="top" title="Search" href="<%=request.getContextPath()%>/openSearchPage.do"><i class="new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_search.svg" alt="Search" /></i><span>Search</span></a>   
                                            </s:elseif>
                                            <s:elseif test='"R".equals(#row.key)'>
                                                <a data-toggle="tooltip" data-placement="top" title="Reports" href="<%=request.getContextPath()%>/LoadDynamicReports.do"><i class="new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_reports.svg" alt="Reports" /></i><span>Reports</span></a>
                                            </s:elseif>
                                            <s:elseif test='"DM".equals(#row.key)'>
                                                    <a data-toggle="tooltip" data-placement="top" title="Digital &#013; Marketing" href="<%=request.getContextPath()%>/camp/openCampaignPage.do"><i class="new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_digital_marketing.svg" alt="Digital Marketing" /></i><span>Digital Marketing</span></a>   
                                                <%--  <a data-toggle="tooltip" data-placement="top" title="Campaign" href="<%=request.getContextPath()%>/camp/openEmailSmsReport.do"><i class="fa fa-gears"></i><span>Campaign</span></a> --%>
                                            </s:elseif> 
                                            <s:elseif test='"AD".equals(#row.key) && !#row.value.isEmpty()'>
                                                <a data-toggle="tooltip" data-placement="top" title="Admin" href="javascript:void(0);"><i class=" new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_admin.svg" alt="Admin" /></i><span>Admin</span></a>    
                                            </s:elseif>
                                            <s:elseif test='"W".equals(#row.key)'>
                                                <a data-toggle="tooltip" data-placement="top" title="WhatsApp" href="<%=request.getContextPath()%>/openWhatsAppDetails.do"><i class="new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_whatsApp.svg" alt="WhatsApp" /></i><span>WhatsApp</span></a>
                                            </s:elseif>  
                                            <s:if test="#row.value != null && !#row.value.isEmpty()">
                                                <ul class="absPos">
                                                    <s:iterator value="#row.value" var="subrow">
                                                        <li>
                                                            <s:if test='"CL".equals(#subrow)'>
                                                                <a href="javascript:void(0);" data-type="page" data-url="/openMissedCallPage.do"><i class="fa fa-phone fa-2x" style="font-size:22px;"></i>Call Log</a>
                                                            </s:if>
                                                            <s:elseif test='"BS".equals(#subrow)'>
                                                                <a href="<%=request.getContextPath()%>/BulkSMSEmail.do"><i class="fa fa-mobile fa-2x" style="font-size:26px;"></i>Bulk SMS</a>
                                                            </s:elseif>
                                                            <s:elseif test='"U".equals(#subrow)'>
                                                                <a href="javascript:void(0);" data-type="page" data-url="/openTaskAttendeePage.do"><i class="fa fa-id-badge" style="font-size:22px;"></i>Agent</a>
                                                            </s:elseif>
                                                            <s:elseif test='"AL".equals(#subrow)'>
                                                                <a href="<%=request.getContextPath()%>/openTaskAllocateEntryPage.do"><i class="fa fa-newspaper-o"></i>Allocation</a>
                                                            </s:elseif>
                                                            <s:elseif test='"WM".equals(#subrow)'>
                                                                <li><a href="javascript:void(0);" data-type="page" data-url="/loadWhatsAppMasterScreen.do"><i class="new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_whatsApp.svg" alt="WhatsApp" /></i>WhatsApp</a></li>
                                                            </s:elseif>
                                                            <s:elseif test='"HO".equals(#subrow)'>
                                                                <a href="javascript:void(0);" data-type="page" data-url="/loadHolidaysMasterScreen.do"><i class="fa fa-calendar"></i>Holidays</a>
                                                            </s:elseif>
                                                        </li>
                                                    </s:iterator>
                                                </ul>
                                            </s:if>
                                        </li>
                                    </s:iterator>
                                    <%--li><a data-toggle="tooltip" data-placement="top" title="Opportunities" href="<%=request.getContextPath()%>/openOpportunitiesEntryPage.do"><i class="fa fa-sitemap"></i><span>Opportunities</span></a></li--%>
                                    <%--s:if test='#session.USER_INFO.userAdminYn == 1'>
                                        <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@COMPANY_CODE_BEEMA.equals(company)}'>
                                        <li><a data-toggle="tooltip" data-placement="top" title="Agents" href="javascript:return void(0);"><i class="fa fa-id-badge"></i><span>Agent</span></a>
                                            <ul class="absPos">
                                                <li><a href="<%=request.getContextPath()%>/monitor/loadCiscoMonitorPage.do?status=1"><i class="fa fa-car"></i>Agent Status</a></li>
                                                <li><a href="<%=request.getContextPath()%>/monitor/loadCiscoMonitorPage.do?status=2"><i class="fa fa-home"></i>View IVR</a></li>
                                            </ul>
                                        </li>
                                        </s:if>
                                    </s:if--%>
                                    <s:if test='%{#session.USER_INFO.userChatYn == "1"}'>
                                        <li><a data-toggle="tooltip" data-placement="top" title="Chat" href="<%=request.getContextPath()%>/openExternalAppPage.do?appType=ENGATI" target="_blank"><i class=" new-icons"> <img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_chat.svg" alt="Live chat" /></i><span>Live chat</span></a> </li>  
                                    </s:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </s:if>
            <div id="block_body" class="crm-dashes">
                <tiles:insertAttribute name="body"></tiles:insertAttribute>
                <s:include value="../task/taskView.jsp"/>
            </div>
        </section>
        <style type="text/css">
            .softPhone {
                position: fixed;
                top: 0px;
                z-index: 999999999;
            }
            .applyTab {
                width: 42px;
                cursor: pointer;
                left: -52px;
                top: 150px;
                margin: 0;
                padding: 0;
                position: relative;
            }
            .contentWrap {
                margin: 0px 0 0;
                overflow-x: auto;
                overflow-y: hidden;
                padding: 10px 10px 10px;
                width: 300px;
                background-color: #ffffff;
                border: 1px solid #ddd;
                border-right: none;
                box-shadow: 10px 0 10px #666;
                -webkit-box-shadow: 10px 0 10px #666;
                position:absolute;
                top:30%;
            }
            .contentWrapRht {
                width: 100%;
                height: auto;
            }
            .slideWrap {
            }
            .if-ameyo {
                border: 0px;
                height: 100% !important;
                width: 280px;
                border: 0px;
                min-height:450px;
                overflow: auto
            }
            .callFeedback {
                position: fixed;
                top: 44px;
                z-index: 999999999;
            }
            .callFeedbackWrap {
                height: 165px;
                margin: 201px 0 0;
                overflow: hidden;
                padding: 10px 20px 20px;
                width: 300px;
                background-color: #ffffff;
                border: 1px solid #ddd;
                border-right: none;
                box-shadow: 10px 0 10px #666;
                -webkit-box-shadow: 10px 0 10px #666;
            }
        </style>
        <%--script type="text/javascript">
            $(document).ready(function () {
                $("#softPhoneIcon").on("click", function () {
                    if ($("#softPhone .softPhone").css("right") === "0px")
                        $("#softPhone .softPhone").animate({"right": "-300px"}, "slow");
                    else
                        $("#softPhone .softPhone").animate({"right": "0px"}, "slow");
                });
            });
        </script--%>
        <s:if test='%{!"N".equals(#session.USER_INFO.ameyoChannel) && #session.USER_INFO.agentType == 1}'>
            <section>
                <div id="softPhone" class="sidenav">
                  <div id="nav-toggle"> <img class="atIcon atIconform" src="<%=request.getContextPath()%>/plugins/innovate/images/call-icon.svg" width="50" alt="call"/> </div> 
                  <div class="">
                        <div class="contentWrap">
                            <div class="contentWrapRht">
                                <div class="slideWrap" id="block_softPhoneIframe">

                                </div>
                            </div>
                            <script>
                                function initialise() {
                                    iframeDiv = document.getElementById("block_softPhoneIframe");
                                    iframeUrl = APP_CONFIG.ameyo.url + "ameyowebaccess/toolbar/toolbar-crm.htm?";
                                    iframeUrl = iframeUrl + "origin=" + APP_CONFIG.crmOrigin;
                                    iframeHtml = '<iframe class="if-ameyo" id="ameyoIframe" src="' + iframeUrl + '"></iframe>';
                                    iframeDiv.innerHTML = iframeHtml;
                                }
                                $(document).ready(function () {
                                    initialise();
                                });

                                function ameyoLogin() {
                                    doLogin("<s:property value="#session.USER_INFO.appType" />_<s:property value="#session.USER_INFO.companyCode" />_<s:property value="#session.USER_INFO.userId" />", "<s:property value="#session.USER_INFO.userPwdResetToken" />", null);
                                }
                            </script>
                        </div>
                    </div>
                  </div>
            </section>
        </s:if>
        <div id="callFeedback" style="display: none;">
            <div class="callFeedback" style="right: -300px;">
                <div class="applyTab">
                    <ul class="sticky">
                        <li class="fl-logo" style="margin-top: 100px;">
                            <a href="javascript:;" id="callFeedbackIcon">
                                <img class="atIcon atIconform" src="<%=request.getContextPath()%>/images/social.png" alt="" style="width:22px;"/>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="callFeedbackWrap">
                    <div class="contentWrapRht">
                        <div class="popover-title">
                            <h4 class="modal-title" style="font-weight: bold;">Call feedback for <span id="callNo"></span></h4>
                        </div>
                        <div class="form-group">
                            <s:form id="feed_form" name="feed_form" method="post" theme="simple">
                                <div class="form-fields clearfix">
                                    <s:textarea class="form-control" name="callLog.cclRemarks" id="cclRemarks"></s:textarea>
                                    </div>
                            </s:form>
                        </div> 
                        <button class="save-btn btn btn-primary" onclick="saveFeedBack();">&#10004;Save</button>
                    </div>
                </div>
            </div>
            <script type="text/javascript">
                $(document).ready(function () {
                    $("#callFeedback #callFeedbackIcon").on("click", function () {
                        if ($("#callFeedback .callFeedback").css("right") === "0px")
                            $("#callFeedback .callFeedback").animate({"right": "-300px"}, "slow");
                        else
                            $("#callFeedback .callFeedback").animate({"right": "0px"}, "slow");
                    });
                });
                function openCallFeedback(callId) {
                    /*$("#callFeedback #callNo").text(callId);
                     $("#callFeedback #cclRemarks").val("");
                     $("#callFeedback").show();
                     if ($("#callFeedback .callFeedback").css("right") === "-300px")
                     $("#callFeedback .callFeedback").animate({"right": "0px"}, "slow");*/
                }
                <%--s:if test="#session.CALL_LOG.cclId != null">
                    openCallFeedback("<s:property value="#session.CALL_LOG.cclId"/>");
                </s:if--%>
            </script>
        </div>
        <section class="float-icons">
            <div class="sticky-container">
                <ul class="social-share hidden-rsidebar">
                    <li class="anoud">
                        <a href="#" id="out_btn_anoud"><span><img title="Anoud" src="<%=request.getContextPath()%>/images/logo_anoud_crm.svg" width="80"></span></a>
                    </li>
                    <li class="email">
                        <a href="#" onclick="callEmail();"><span><i class="fa fa-envelope fa-2x" title="Email"></i></span></a>
                    </li>
                    <li class="message-sidebar">
                        <a href="#" onclick="callSms();"><span><i class="fa fa-comment-o fa-2x" title="SMS"></i></span></a>
                    </li>
                    <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@APP_TYPE_QLM.equals(#session.USER_INFO.appType)}'>
                        <li class="fl-chat">
                            <a href="#" onclick="callProvider()"><i class="fa fa-stethoscope fa-2x" title="Provider"></i></a>
                        </li>
                    </s:if>
                    <%--s:if test='%{@qa.com.qic.common.util.ApplicationConstants@COMPANY_CODE_BEEMA.equals(company) && @qa.com.qic.common.util.ApplicationConstants@APP_TYPE_RETAIL.equals(#session.USER_INFO.appType)}'>
                    <li class="fl-call">
                        <a href="#" onclick="call();"><img title="DialCall" src="<%=request.getContextPath()%>/plugins/innovate/images/telephone.png"></a>
                    </li>
                    </s:if--%>
                    <li class="hide-button">
                        <a class="fa fa-arrow-right" href="javascript:void(0);"></a> </li>
                </ul>
            </div>
            <div class="popup-wrap" id="popup_custom"></div>
        </section>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/innovate/js/jquery.flagstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery.form.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/validator/jquery.validate.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echarts/echarts.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/autoNumeric/autoNumeric.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echarts/plotly-latest.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/innovate/js/jquery.slimscroll.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/node-waves/waves.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/blockUI/blockui.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/notify/notify.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/momentjs/moment.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/innovate/js/datepicker.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/richText/jquery-te-1.4.0.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-multiselect/bootstrap-multiselect.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js?v=${application.R_TOCKEN}"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/util.min.js?v=${application.R_TOCKEN}"></script>
        <s:if test='%{!"N".equals(#session.USER_INFO.ameyoChannel) && #session.USER_INFO.agentType == 1}'>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/ameyo-integration.js?v=${application.R_TOCKEN}"></script>
        </s:if>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/maxlength/jquery.maxlength.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/context-menu/contextMenu.min.js"></script>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/context-menu/contextMenu.min.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/daterangepicker/daterangepicker.css"/>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/daterangepicker/daterangepicker.js"></script>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css"/>
        <script src="<%=request.getContextPath()%>/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
        <style>
            .notifyjs-task-base {
                opacity: 0.90;
                width: 400px;
                background: #1a1919;
                padding: 5px;
                border-radius: 10px;
            }

            .notifyjs-task-base .title {
                width: 380px;
                margin: 10px 0 0 10px;
                text-align: left;
                color: #ffffff;
            }

            .notifyjs-task-base .buttons {
                width: 380px;
                font-size: 9px;
                padding: 5px;
                margin: 2px;
                text-align: center;
            }

            .notifyjs-task-base button {
                font-size: 9px;
                padding: 5px;
                margin: 2px;
                width: 60px;
            }
        </style>
        <script>
                                var crmLogout = false;
                                function ameyoLogout() {
                                    if (crmLogout) {
                                        window.location.href = APP_CONFIG.context + "/logout.do";
                                    }
                                }

                                function logoutCrm() {
                                    crmLogout = true;
                                    if (APP_CONFIG.ameyo.channel !== "N" && APP_CONFIG.agentType === 1) {
                                        setTimeout(function () {
                                            ameyoLogout();
                                        }, 1000);
                                        try {
                                            doLogout();
                                        } catch (err) {
                                            ameyoLogout();
                                        }
                                    } else {
                                        ameyoLogout();
                                    }
                                }

                                $(document).ready(function () {
            <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@PUSH_NOTIFICATION_TASK}'>
                                    loadTaskNotify();
                                    setInterval("loadTaskNotify();", (1000 * 60 * 1));
            </s:if>
                                    
                                    var el_height = $('.social-share').height();
                                    $('.social-share').css('margin-top', - ( el_height / 2 ));

                                    $('.social-share li.hide-button a').click(function() {
                                        $('.social-share').toggleClass('hidden-rsidebar'); 
                                    });
                                    
                                    $.notify.addStyle('task', {
                                        html:
                                                "<div>" +
                                                "<div class='clearfix'>" +
                                                "<div class='title' data-notify-html='title'/>" +
                                                "<div class='buttons'>" +
                                                "<button class='no close-btn btn'>Close</button>" +
                                                "<button class='yes btn save-btn'>View</button>" +
                                                "</div>" +
                                                "</div>" +
                                                "</div>"
                                    });

                                    $(document).on('click', '.notifyjs-task-base .no', function () {
                                        $(this).trigger('notify-hide');
                                    });
                                    $(document).on('click', '.notifyjs-task-base .yes', function () {
                                        viewTasks($(this).parent().parent().find("input[name='taskId']").val());
                                        //hide notification
                                        $(this).trigger('notify-hide');
                                    });
                                });

                                function loadTaskNotify() {
                                    $.ajax({
                                        type: "POST",
                                        url: APP_CONFIG.context + '/loadTaskEntryNotifyData.do',
                                        data: {},
                                        success: function (result) {
                                            if (result.aaData && result.aaData.length > 0) {
                                                $.each(result.aaData, function (i, o) {
                                                    $.notify(
                                                            {taskId: o.ctId, title: $('<p style="">' + o.ctSubCatgDesc + '<input type="hidden" name="taskId" value="' + o.ctId + '"/> (Task Id: ' + o.ctId + ')</p>')},
                                                            {style: "task", autoHide: false, clickToHide: false, globalPosition: "top right"});
                                                });
                                            }
                                        },
                                        complete: function () {
                                        },
                                        error: function (xhr, status, error) {
                                            alert("Error: " + error);
                                        }
                                    });
                                }

                                $(document).ready(function () {
                                    $('[data-toggle="tooltip"]').tooltip();
                                    $('a[data-toggle="tooltip"]').tooltip({
                                        container: 'body'
                                    });
                                    $("#txt_gbl_search").on("keypress", function (event) {
                                        var keyCode = (event.keyCode ? event.keyCode : event.which);
                                        if (keyCode === 13) {
                                            if ($.trim($("#txt_gbl_search").val()) !== "") {
                                                window.location.href = APP_CONFIG.context + "/openInsuredDetailPage.do?search=" + $("#txt_gbl_search").val();
                                            }
                                            event.preventDefault();
                                        }
                                    });

                                    $('#sidebar').slimScroll({
                                        height: '100%',
                                        width: '100%',
                                    });

                                    $("#dropdown").on("click", function (e) {
                                        e.preventDefault();

                                        if ($(this).hasClass("open")) {
                                            $(this).removeClass("open");
                                            $(this).children("ul").slideUp("fast");
                                        } else {
                                            $(this).addClass("open");
                                            $(this).children("ul").slideDown("fast");
                                        }
                                    });
                                    var countries = {};
                                    <s:iterator value="#session.USER_INFO.applCompanyList">
                                    countries[APP_CONFIG.companyList["<s:property value="key"/>"].code] = APP_CONFIG.companyList["<s:property value="key"/>"].name;
                                    </s:iterator>
                                    if (Object.keys(countries).length === 0) {
                                        countries[APP_CONFIG.companyList["<s:property value="#session.USER_INFO.companyCode"/>"].code] = APP_CONFIG.companyList["<s:property value="#session.USER_INFO.companyCode"/>"].name;
                                    }
                                    //console.log("Countries", countries);
                                    $('#country').flagStrap({
                                        inputName: "company",
                                        countries: countries,
                                        buttonSize: "btn-sm",
                                        buttonType: "btn-info",
                                        labelMargin: "10px",
                                        slimScroll: true,
                                        SlimScrollableHeight: "350px",
                                        selectedCountry: APP_CONFIG.companyList["<s:property value="#session.USER_INFO.companyCode"/>"].code,
                                        onSelect: function (value, element) {
                                            if (value !== "" && Object.keys(countries).length > 1) {
                                                $('<form action="' + APP_CONFIG.context + "/changeCrmAppCompany.do" + '" method="post"></form>').append(element).appendTo('body').submit();
                                            }
                                        },
                                        placeholder: {
                                            value: "",
                                            text: "Select Country"
                                        }
                                    });
                                    $('[data-type="page"]').openpage();
                                });
        </script>
        <script type="text/javascript">
            $(document).ready(function () {
                $(window).on("load resize ", function () {
                    var scrollWidth = $('.sidebar-menu').width() - $('.sidebar-menu menu').width();
                    $('.sidebar-menu');
                }).resize();
                $("#out_btn_anoud").on("click", function () {
                    if ($("#contact_block_left").hasClass("active")) {
                        $("#contact_block_left .title_block").trigger("click");
                    }
            <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@COMPANY_CODE_MED_DOHA.equals(#session.COMPANY_CODE)}'>
                    window.open(APP_CONFIG.context + "/QLMAppIntegration.do?flex1=SSO");
            </s:if>
            <s:else>
                    window.open(APP_CONFIG.context + "/AnoudAppIntegration.do?flex1=SSO");
            </s:else>
                });
            });

            $(document).ready(function () {
                $('#plugin_modal_dialog').on('shown.bs.modal', function () {
                    //Get the datatable which has previously been initialized
                    var dataTable = $('#plugin_modal_dialog .table1').DataTable();
                    //recalculate the dimensions
                    dataTable.columns.adjust().responsive.recalc();
                });

                $('#select_country').attr('data-selected-country', 'QA');
                $('#select_country').flagStrap();
            });

            function callEmail() {
                block('block_body');
                var data = {"company": "${company}", "agentid": "${agentid}", "customer.mobileNo": "${customer.mobileNo}", "customer.civilId": "${customer.civilId}", "customer.emailId": "${customer.emailId}", "customer.name": "${customer.name}"};
                if ($("#contact_block_left").hasClass("active")) {
                    $("#contact_block_left .title_block").trigger("click");
                }
                $.ajax({
                    type: "POST",
                    url: APP_CONFIG.context + "/openEmailActivityForm.do",
                    data: data,
                    success: function (result) {
                        $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm").addClass("modal-mm");
                        $('#plugin_modal_dialog .modal-content').empty().html(result);
                        $('#plugin_modal_dialog').modal('show');
                    },
                    error: function (xhr, status, error) {
                        alert("Error: " + error);
                    },
                    complete: function () {
                        unblock('block_body');
                    }
                });
            }

            function callSms(mobileNo) {
                block('block_body');
                var data = {"company": "${company}", "agentid": "${agentid}", "customer.mobileNo": mobileNo, "customer.civilId": "${customer.civilId}", "customer.emailId": "${customer.emailId}", "customer.name": "${customer.name}", "customer.mobileNo1": mobileNo, "customer.mobileNo2": mobileNo};
                if ($("#contact_block_left").hasClass("active")) {
                    $("#contact_block_left .title_block").trigger("click");
                }
                $.ajax({
                    type: "POST",
                    url: APP_CONFIG.context + "/openSmsActivityForm.do",
                    data: data,
                    success: function (result) {
                        $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm").addClass("modal-mm");
                        $('#plugin_modal_dialog .modal-content').empty().html(result);
                        $('#plugin_modal_dialog').modal('show');
                    },
                    error: function (xhr, status, error) {
                        alert("Error: " + error);
                    },
                    complete: function () {
                        unblock('block_body');
                    }
                });
            }

            function callProvider() {
                block('block_body');
                $.ajax({
                    type: "POST",
                    url: APP_CONFIG.context + "/AddPharmacypreApproval.do",
                    success: function (result) {
                        $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm").addClass("modal-mm");
                        $('#plugin_modal_dialog .modal-content').empty().html(result);
                        $('#plugin_modal_dialog').modal('show');
                    },
                    error: function (xhr, status, error) {
                        alert("Error: " + error);
                    },
                    complete: function () {
                        unblock('block_body');
                    }
                });

            }

            function saveFeedBack() {
                $.ajax({
                    type: "POST",
                    url: APP_CONFIG.context + "/saveCallActivityFeedBack.do",
                    data: $("#feed_form").serialize(),
                    success: function (result) {
                        $("#callFeedback").hide();
                    },
                    error: function (xhr, status, error) {
                        alert("Error: " + error);
                    },
                    complete: function () {
                    }
                });
            }

            function call() {
                $("#contact_block_left .title_block").trigger("click");
            }
            function search() {
                var text = $("#txt_gbl_search").val();
                if (text === "") {
                    alert("Please Enter Text")
                    return false;
                }
                window.location.href = APP_CONFIG.context + "/openInsuredDetailPage.do?search=" + text;
            }
        </script>
        <style>
            #contact_block_left {
                position: fixed;
                right: 60px;
                bottom: -385px;/*-350px;*/
                width: 310px;
                background: #fff;
                -webkit-transition: bottom 0.5s ease-in-out 0s;
                transition: bottom 0.5s ease-in-out 0s;
            }
            #contact_block_left {
                border-radius: 0;
                overflow: hidden;
                z-index: 1000;
                margin-bottom: 0;
            }
            #contact_block_left .title_block {
                letter-spacing: 0;
                text-align: center;
                padding: 6px 10px;
                cursor: pointer;
            }
            #contact_block_left .title_block {
                font-weight: 400;
                margin: 0;
                background-color: #346EA3;
                color: #fff;
                font-family: "droid Sans", sans-serif;
            }
            #contact_block_left .block_content {
                padding: 10px 20px 20px;
                color: #777777;
                border: 1px solid #346EA3;
                border-width: 0 1px;
            }
            #contact_block_left.active {
                bottom: 0
            }
            strong,p,sub,sup,ol,ul,li{margin:0;padding:0;border:0;font:inherit;font-size:100%;vertical-align:baseline}ol,ul{list-style:none}
            .dialpad .number{position:relative;z-index:2;padding:0px 5px;color:#4d4d4d;font-weight:300;font-size:40px;background:#fff;height:50px}.dialpad .dials{margin:-1px 0 0 -1px;background:#1d1918;cursor:pointer}.dialpad .dials:before,.dialpad .dials:after{content:"\0020";display:block;height:0;overflow:hidden}.dialpad .dials:after{clear:both}.dialpad .dials .digits{float:left;width:33.33%}.dialpad .dials .digits p{font-weight:600;padding:15px 25px;border-top:1px solid #4d4d4d;border-right:1px solid #000;border-bottom:1px solid #000;border-left:1px solid #4d4d4d}.dialpad .dials .digits p strong{font-size:50px;margin-right:8px;color:#fff}.dialpad .dials .digits:active{background:#00caf2;border-top-color:#b2f2ff}
            .compact .dials .digits p{padding:5px 8px}.compact .dials .digits p strong{font-size:30px}.compact .dials .digits p sup{text-transform:uppercase;color:#c1c1c1}.compact .dials .pad-action{background:#093}.compact .dials .pad-action:active{background:#0c3}.ir{background-color:transparent;border:0;overflow:hidden;*text-indent:-9999px}.ir:before{content:"";display:block;width:0;height:150%}.hidden{display:none !important;visibility:hidden}.visuallyhidden{border:0;clip:rect(0 0 0 0);height:1px;margin:-1px;overflow:hidden;padding:0;position:absolute;width:1px}.visuallyhidden.focusable:active,.visuallyhidden.focusable:focus{clip:auto;height:auto;margin:0;overflow:visible;position:static;width:auto}.invisible{visibility:hidden}.clearfix:before,.clearfix:after{content:" ";display:table}.clearfix:after{clear:both}.clearfix{*zoom:1}
            .dialpad .dials .digits.active {
                background: #00caf2;
                border-top-color: #b2f2ff;
            }
        </style>
        <div id="contact_block_left" class="block pts-contact hidden-xs hidden-sm hidden-md">
            <h4 class="title_block">Call</h4>
            <div class="block_content clearfix">
                <div class="dialpad compact">
                    <div class="number"></div>
                    <div class="dials">
                        <ol>
                            <li class="digits"><p><strong>1</strong></p></li>
                            <li class="digits"><p><strong>2</strong><sup>abc</sup></p></li>
                            <li class="digits"><p><strong>3</strong><sup>def</sup></p></li>
                            <li class="digits"><p><strong>4</strong><sup>ghi</sup></p></li>
                            <li class="digits"><p><strong>5</strong><sup>jkl</sup></p></li>
                            <li class="digits"><p><strong>6</strong><sup>mno</sup></p></li>
                            <li class="digits"><p><strong>7</strong><sup>pqrs</sup></p></li>
                            <li class="digits"><p><strong>8</strong><sup>tuv</sup></p></li>
                            <li class="digits"><p><strong>9</strong><sup>wxyz</sup></p></li>
                            <li class="digits"><p><strong>*</strong></p></li>
                            <li class="digits"><p><strong>0</strong><sup>+</sup></p></li>
                            <li class="digits"><p><strong>#</strong></p></li>
                            <li class="digits"><p><strong style="margin-right:1px;"><i class=""></i></strong><sup>Clear</sup></p></li>
                            <li class="digits"><p><strong style="margin-right:3px;"><i class=""></i></strong><sup>Del</sup></p></li>
                            <li class="digits pad-action"><p><strong style="margin-right:0px;"><i class=""></i></strong> <sup>Call</sup></p></li>
                        </ol>
                    </div>
                </div>
            </div>

            <script type="text/javascript">

                $('#contact_block_left .title_block').click(function () {
                    if ($(this).parent().hasClass('active'))
                        $(this).parent().removeClass('active');
                    else
                        $(this).parent().addClass('active');
                });

                $(function () {
                    var dials = $(".dials ol li");
                    var index;
                    var number = $(".dialpad .number");
                    var total;

                    dials.click(function () {
                        index = dials.index(this);
                        if (index === 9) {
                            number.append("*");
                        } else if (index === 10) {
                            number.append("0");
                        } else if (index === 11) {
                            number.append("#");
                        } else if (index === 12) {
                            number.empty();
                        } else if (index === 13) {
                            total = number.text();
                            total = total.slice(0, -1);
                            number.empty().append(total);
                        } else if (index === 14) {
                            var callNo = number.text();
                            if ($.trim(callNo).length < 8) {
                                alert("Invalid No");
                                return;
                            }
                            $.ajax({
                                type: "POST",
                                url: APP_CONFIG.context + "/callCustomer.do",
                                data: {"customer.mobileNo": number.text()},
                                async: true,
                                success: function (result) {
                                    if (result.messageType === "S") {
                                        $.notify(number.text() + " calling...", "custom");
                                    }
                                },
                                error: function (xhr, status, error) {
                                    alert("Error: " + error);
                                },
                                complete: function () {
                                    unblock('block_body');
                                }
                            });
                        } else {
                            number.append(index + 1);
                        }
                    });

                    $(document).keydown(function (e) {
                        if (!$("#contact_block_left").hasClass('active')) {
                            return;
                        }
                        switch (e.which) {
                            case 96:
                                $(".dials ol li:eq(10)").addClass("active");
                                number.append("0");
                                break;
                            case 97:
                                $(".dials ol li:eq(0)").addClass("active");
                                number.append("1");
                                break;
                            case 98:
                                $(".dials ol li:eq(1)").addClass("active");
                                number.append("2");
                                break;
                            case 99:
                                $(".dials ol li:eq(2)").addClass("active");
                                number.append("3");
                                break;
                            case 100:
                                $(".dials ol li:eq(3)").addClass("active");
                                number.append("4");
                                break;
                            case 101:
                                $(".dials ol li:eq(4)").addClass("active");
                                number.append("5");
                                break;
                            case 102:
                                $(".dials ol li:eq(5)").addClass("active");
                                number.append("6");
                                break;
                            case 103:
                                $(".dials ol li:eq(6)").addClass("active");
                                number.append("7");
                                break;
                            case 104:
                                $(".dials ol li:eq(7)").addClass("active");
                                number.append("8");
                                break;
                            case 105:
                                $(".dials ol li:eq(8)").addClass("active");
                                number.append("9");
                                break;
                            case 8:
                                $(".dials ol li:eq(13)").addClass("active");
                                total = number.text();
                                total = total.slice(0, -1);
                                number.empty().append(total);
                                break;
                            case 27:
                                $(".dials ol li:eq(12)").addClass("active");
                                number.empty();
                                break;
                            case 106:
                                number.append("*");
                                break;
                            case 35:
                                number.append("#");
                                break;
                            case 13:
                                $('.pad-action').click();
                                break;
                            default:
                                return;
                        }
                        setTimeout(function () {
                            $(".dials ol li").removeClass("active");
                        }, 50);
                        e.preventDefault();
                    });
                });
            </script>
            <script>

                var toggle = true;

                $(".sidebar-icon").click(function () {
                    if (toggle)
                    {
                        $(".page-container").removeClass("sidebar-collapsed").addClass("sidebar-collapsed-back");
                        $("#menu span").css({"position": "relative"});
                    } else
                    {
                        $(".page-container").addClass("sidebar-collapsed").removeClass("sidebar-collapsed-back");
                        setTimeout(function () {
                            $("#menu span").css({"position": "absolute"});
                        }, 400);
                    }
                    toggle = !toggle;
                });
            </script>
            <script>
               $(".absPos").parent().hover(
                       function () {
                          $(".slimScrollDiv, #sidebar").css({"overflow": "hidden", "position": "fixed"});
                       }, function () {
                   $(".slimScrollDiv, #sidebar").css({"overflow": "hidden", "position": "relative"});
               }
                );
            </script>
            <script>
                $(document).ready(function(){
                    $('#nav-icon1').click(function(){
                        $(this).toggleClass('open');
                    });
                    
                    $('ul.nav li.dropdown').hover(function() {
                        $(this).find('.dropdown-menu').stop(true, true).delay(20).fadeIn(500);
                    }, function() {
                        $(this).find('.dropdown-menu').stop(true, true).delay(20).fadeOut(500);
                    });
                    
                    $("#nav-toggle").click(function() {
                        $(this).toggleClass("active");
                        $("#softPhone").toggleClass("sidenav-actif");
                    });
                    
                    $("#block_body").click(function() {
                        toggle = false;
                        $(".sidebar-icon").trigger('click');
                    });
                });
            </script>
            <script>
                $(function(){
                    var current_page_URL = location.href;
                    $( "#menu a" ).each(function() {
                        if ($(this).attr("href") !== "#") {
                            var target_URL = $(this).prop("href");
                            if (target_URL === current_page_URL) {
                                $('#menu li').parents('li').removeClass('active');
                                $(this).parent('li').addClass('active');
                                return false;
                            }
                        }
                    });
                });
            </script>
        </div>
        <style>
            /*.blockOverlay {background:red !important;}*/
            .blockUI.blockOverlay {background:#000 !important; opacity: .7 !important;}
            .blockUI.blockOverlay:before {content:"Please wait.."; color:#fff; position: absolute; top:40%; left:50%; z-index: 100000; font-size:2em;}
        </style>
    </body>
    <!--Common modal window -->
    <div class="modal fade" id="plugin_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="plugin_modal_dialog_heading" aria-hidden="false">
        <div class="modal-dialog  modal-lg">
            <div class="modal-content">
            </div>
        </div>
    </div>
</html>
