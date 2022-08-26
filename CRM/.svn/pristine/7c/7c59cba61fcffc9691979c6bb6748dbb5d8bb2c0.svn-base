<%-- 
    Document   : customerFeedback
    Created on : 27 Dec, 2017, 2:00:40 PM
    Author     : sutharsan.g
--%>
<%@page import="qa.com.qic.common.util.ApplicationConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!--s:action namespace="" name="openCustomerFeedBack" executeResult="true" >
  
<--/s:action-->
<s:hidden name="company" value ="009" id="companyCode"/>
<s:set var="suggestYN"><s:property value="%{#parameters['suggestYN']==null?'Y':#parameters['suggestYN']}"/></s:set>
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css" />
            <!--link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/font-awesome/css/font-awesome.min.css" /-->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/font-awesome.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/bootstrap.min.css" />
        <link href="<%=request.getContextPath()%>/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/css/styleV2.css" rel="stylesheet" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/main.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/dataTables.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/datatables/Responsive/css/dataTables.responsive.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/flags.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/magnific-popup/magnific-popup.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/line-awesome.min.css">
        <link href="<%=request.getContextPath()%>/plugins/richText/css/jquery-te-1.4.0.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/plugins/innovate/css/datepicker.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/plugins/bootstrap-fileupload/bootstrap-fileupload.min.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/plugins/bootstrap-multiselect/bootstrap-multiselect.css" rel="stylesheet" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/jquery-ui.css" />
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery.min.js"></script>

        <script type="text/javascript">
            var APP_CONFIG = {
                context: "<%=request.getContextPath()%>",
                messageType: {
                    success: "<%=ApplicationConstants.MESSAGE_TYPE_SUCCESS%>",
                    error: "<%=ApplicationConstants.MESSAGE_TYPE_ERROR%>"
                },
                companyList: {"001": {"code": "QA", "name": "Doha"}, "002": {"code": "AE", "name": "Dubai"}, "005": {"code": "KW", "name": "Kuwait"}, "006": {"code": "OM", "name": "Oman"}}
            };
        </script>
    </head>
    <body>
        <section class="dashboard-mainfunction">
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>                        
                        </button>
                        <a class="navbar-brand log1" href="<%=request.getContextPath()%>/home.do"><img src="images/logo.png" class="img-responsive log2"></a>
                    </div>
            </nav>
            <div class="my-bord">
                <h3><span><img src="<%=request.getContextPath()%>/images/social.png" class="img-responsive" style="max-width:32px; display: inline-block;" alt="Activity"></span> <s:text name="lbl.customer.feedback"/></h3>
            </div>
            <div class="my-bord">
                <h3><span><img src="<%=request.getContextPath()%>/images/social.png" class="img-responsive" style="max-width:32px; display: inline-block;" alt="Activity"></span> <s:text name="lbl.customer.feedback"/></h3>
            </div>
            <div class="row" id="feedback_frm" style="display: none">
                <jsp:include page="/WEB-INF/pages/anoud/feedbackQuestion.jsp"/>
            </div>
        </section>
    </body>
    <!--Common modal window -->
</html>
<script type="text/javascript">
    $(document).ready(function () {
        openFeedBackForm();
        var text_max = 2000;
        $('#textarea_feedback').html(text_max + ' characters remaining');

        $('#suggestions').keyup(function () {
            var text_length = $('#suggestions').val().length;
            var text_remaining = text_max - text_length;

            $('#textarea_feedback').html(text_remaining + ' characters remaining');
        });

    });
    function ajaxSubmitFeedback() {
        var msgId = "#feedbackFrmMsg";
        var isError = true;
        $("#fb_submit_btn").val("Processing...");
        $("#fb_submit_btn").attr("disabled", "disabled");
        // block();
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/submitFeedback.do",
            data: $('#feedbackFrm').serialize(),
            async: true,
            complete: function (result) {
                // unblock();
            },
            success: function (response) {
                if (response !== null) {
                    if (typeof (response.errorMsg) === "undefined" || response.errorMsg === "") {
                        isError = false;
                        $("#fb_submit_btn").val("Done");
                        $(msgId).removeClass("alert-warning");
                        $(msgId).addClass("alert-success");
                        $(msgId).html(response.successMsg);
                    } else {
                        $(msgId).addClass("alert-warning");
                        $(msgId).removeClass("alert-success");
                        $(msgId).html(response.errorMsg);
                    }
                } else {
                    $(msgId).addClass("alert-warning");
                    $(msgId).removeClass("alert-success");
                    $(msgId).html("Oops! Unable to process your request");
                }
                $(msgId).show();
                $("#plugin_modal_dialog").scrollTop(10);
                if (isError) {
                    $("#fb_submit_btn").val("Submit");
                    $("#fb_submit_btn").removeAttr("disabled");
                }
                $(msgId).delay(4000).fadeOut('slow', function () {
                    if (!isError)
                        $("#plugin_modal_dialog").modal("hide");
                });
            },
            error: function (xhr, status, error) {
                $("#fb_submit_btn").val("Submit");
                $("#fb_submit_btn").removeAttr("disabled");
                $(msgId).addClass("alert-warning");
                $(msgId).removeClass("alert-success");
                $(msgId).html("Oops! Unable to process your request");
                $(msgId).show();
                $(msgId).delay(4000).fadeOut('slow');
            }
        });
    }
    function openFeedBackForm() {
        var company = $("#companyCode").val();
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/showQIFeedback.do",
            data: {'formId': "QLM_ONLINE", 'company': company, 'suggestYN': 'Y', 'custId': '${custId}', 'emailId': '${emailId}', 'userName': '${userName}', 'mobileNo': '${mobileNo}'},
            async: true,
            complete: function (result) {
                // unblock();
            },
            success: function (response) {
                $("#feedback_frm").html(response).show();
                
            },
            error: function (xhr, status, error) {

            }
        });
    }
</script>
