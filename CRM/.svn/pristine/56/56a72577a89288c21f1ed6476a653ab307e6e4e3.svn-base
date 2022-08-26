<%@page import="qa.com.qic.anoud.vo.KeyValue"%>
<%@page import="qa.com.qic.anoud.vo.FeedBackInfoBO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<%--
Prameters: userNameYN, custIdYN, emailYN, mobileYN
If parameter values are "Y" It ask from customer else the values(userName, custId, emailId & mobileNo) stored in hidden

<s:action name="openFeedback" namespace="/Anoud" executeResult="true">
    <s:param name="formId">POL_APPR</s:param>
    <s:param name="refNo">${refNo}</s:param>
    <s:param name="userNameYN">Y</s:param>
    <s:param name="userName">${assuredName}</s:param>
    <s:param name="custIdYN">Y</s:param>
    <s:param name="custId">${qatarId}</s:param>
    <s:param name="emailIdYN">Y</s:param>
    <s:param name="emailId">${emailId}</s:param>
    <s:param name="mobileNoYN">N</s:param>
    <s:param name="mobileNo">${mobileNo}</s:param>
</s:action>
--%>

<s:set var="userNameYN"><s:property value="%{#parameters['userNameYN']==null?'N':#parameters['userNameYN']}"/></s:set>
<s:set var="custIdYN"><s:property value="%{#parameters['custIdYN']==null?'N':#parameters['custIdYN']}"/></s:set>
<s:set var="emailIdYN"><s:property value="%{#parameters['emailIdYN']==null?'N':#parameters['emailIdYN']}"/></s:set>
<s:set var="mobileNoYN"><s:property value="%{#parameters['mobileNoYN']==null?'N':#parameters['mobileNoYN']}"/></s:set>

    <div class="modal-header">
        <h4 class="modal-title"><span><img src="<%=request.getContextPath()%>/images/social.png" class="img-responsive" style="max-width:32px; display: inline-block;" alt="Activity"></span> <s:text name="lbl.customer.feedback"/></h4>
    <div style="margin-top: -31px; float: right;">
        <button class="btn btn-danger" data-dismiss="modal" style="margin-left: 270px;">&#10006; <s:text name="btn.close"/></button>
    </div>
</div>
<div class="body">
    <div class="container-fluid">                            
        <div class="row">
            <div class="col-md-12">
                <span class>
                    <s:text name="lbl.feedback.type"/>
                </span>
                <span style="display: inline-block; padding:0px 15px;">
                    <s:select  name="formId" id="frmType" list="keyValueList" listKey="key" listValue="value" class="form-control" />
                </span>
                <span>
                    <button type="button" class="btn btn-warning" id="btn_add_feed" onclick="openFeedBackForm($('#frmType').val())"><s:text name="btn.add"/></button>
                </span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="pull-right" style="margin-bottom:-20px;" id="feedback_tbl_search"></div>
            <div class="clear"></div>
            <table class="table table-striped table-bordered display dataTable dtr-inline" id="feedback_tbl" role="grid" style="min-height: 200px;">
                <thead>
                    <tr>
                        <th class="text-center"><s:text name="lbl.feedback.type"/></th>
                        <th class="text-center"> <s:text name="lbl.date"/></th>     
                    </tr>
                </thead>
                <tbody>
                </tbody> 
            </table>
        </div>
    </div>
    <div class="row" id="feedback_frm" style="display: none">
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("#feedback_tbl").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadListFeedback.do?custId=<s:property value='custId'/>&company=<s:property value='company'/>",
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "key"},
                {data: "value"},
            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function () {
                $("#feedback_tbl tr td").css('cursor', 'default');
                $('#feedback_tbl_search').html($('#feedback_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
            "drawCallback": function (settings) {
                //  $('#quote_tbl').DataTable().$('.decimal').autoNumeric('init');
            }
        });

        //$(".modal-dialog").css({'width': '55%'});
        setTimeout(function () {
            $("#fbMsgModal").modal("show");
        }, 1000);

        $("#suggestions").bind('input propertychange', function () {
            var maxLength = $(this).attr('maxlength');
            if ($(this).val().length > maxLength) {
                $(this).val($(this).val().substring(0, maxLength));
            }
            $("#suggest_len_left").html((maxLength - $(this).val().length));
        });

        $("#remarks").bind('input propertychange', function () {
            var maxLength = $(this).attr('maxlength');
            if ($(this).val().length > maxLength) {
                $(this).val($(this).val().substring(0, maxLength));
            }
            $("#remarks_len_left").html((maxLength - $(this).val().length));
        });
    });

    function openFeedBackForm(val) {
        //alert(val);
        $("#formId").val(val);
        $("#feedback_frm").show();
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/showQIFeedback.do",
            data: {'formId': $("#frmType").val(), 'company': '${company}', 'suggestYN': 'Y', 'custId': '${custId}', 'emailId': '${emailId}', 'userName': '${userName}'},
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
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>