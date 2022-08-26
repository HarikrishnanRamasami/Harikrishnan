<%-- 
    Document   : WhatsAppDetailPage
    Created on : Apr 18, 2019, 1:30:55 PM
    Author     : soumya.gaur
--%>

<%@page import="qa.com.qic.common.util.ApplicationConstants, qa.com.qic.crm.restapi.resources.services.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    .home-card {
        min-height: 370px;
    }
    .no-data:after {
        content: "No data available";
        color: rgba(30, 127, 210, 0.62);
    }
</style>
<%
    WhatsAppProperty prop = new WhatsAppProperties((String) session.getAttribute(ApplicationConstants.SESSION_NAME_COMPANY_CODE)).getProp();
%>
<script>
    var WA_CONFIG = {
        context: "<%=request.getContextPath()%>",
        notifyBaseUrl: "<%=prop.getNotifyBaseUrl()%>",
        countryCode: "<%=prop.getCountryCode()%>",
        loggedInUserId: "<s:property value="#session.USER_INFO.userId" />",
        loggedInUserName: "<s:property value="#session.USER_INFO.userName" />"
    };

    var HANDY_MSG_JSON = [];
    <s:iterator value="keyValueList">
    HANDY_MSG_JSON.push({"key": "<s:property value="key"/>", "value": "<s:property value="value"/>", "info1": "<s:property value="info1"/>", "info2": "<s:property value="info2" escapeHtml="false"/>", "info3": "<s:property value="info3"/>", "info4": "<s:property value="info4"/>"});
    </s:iterator>
</script>
<script src="<%=request.getContextPath()%>/js/sockjs-client/sockjs.min.js"></script>
<script src="<%=request.getContextPath()%>/js/stomp.js/stomp.min.js"></script>
<script src="<%=request.getContextPath()%>/js/whatsapp.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/whatsapp.css">

<div class="dash-leads" style="border-top:0!important">
    <div class="row">
        <div class="col-md-12 m-tb-3">
            <button type="button" class="save-btn btn btn-primary" id="btn_init_chat_start"><i class="fa fa-comment"></i> <s:text name="btn.invite"/></button>
        </div>
    </div>
    <div class="col-md-12 bor2">
        <div class="col-md-12 Tmar" style="margin-bottom: 10px;">
            <s:form class="form-inline" id="frm_whatsapp_unread_search" name="frm_whatsapp_unread_search" method="post" theme="simple">
                <div class="row">
                    <div class="form-group padR">
                        <label class="text-black"><s:text name="lbl.whatsapp.search.chat"/></label>
                        <s:textfield name="txn.wtName" id="whatsapp_txt_unread_message" class="form-control input-sm"  cssStyle="width: 325px;"/>
                    </div>
                    <div class="form-group padR">	                           
                        <s:radio name="txn.wtFlex01" list="#{'I': 'Inbound','O' :'Outbound'}" id="whatsapp_unread_filter_checkbox" cssStyle="margin: 0px 1px;"/>
                    </div>
                    <div class="form-group padR">
                        <button type="button" id="btn_search_whatsapp_unread_msg" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
                        <button type="button" id="btn_clear_whatsapp_unread_msg" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-fa-recycle"></i> <s:text name="btn.clear"/></button>
                        &nbsp;<i style="font-size: 12px;font-weight: bold">(<s:text name="lbl.whatsapp.past.four.month"/>)</i>
                    </div>
                </div>         
            </s:form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="unread_tbl" class="table table-striped table-bordered display dataTable dtr-inline">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                        <th><s:text name="lbl.common.name"/></th>
                        <th><s:text name="lbl.contact"/></th>
                        <th><s:text name="lbl.time"/></th>
                        <th><s:text name="lbl.common.user.id"/></th>
                        <th><s:text name="lbl.whatsapp.msg.count"/></th>
                        <th style="width: 10%;"><s:text name="lbl.whatsapp.locked.user"/></th>
                        <th class="no-sort" style="text-align: center;"><s:text name="lbl.action"/></th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><s:text name="lbl.whatsapp.head.pick.req"/></h4>
                <span style="position: absolute; top: 17px; right: 15px;">
                    <button type="button" class="save-btn btn btn-primary" id="btn_save"><i class="fa fa-check"></i> <s:text name="btn.yes"/></button>
                    <button class="close-btn1 btn" data-dismiss="modal"><i class="fa fa fa-window-close"/> <s:text name="btn.no"/></button>
                </span>
            </div>
            <div class="modal-body">
                <div class="row" style="margin-left:3px !important"> 
                    <div class=" form-group col-md-8">
                        <label><s:text name="lbl.whatsapp.req.currently.assigned.to"/> <u><span id="assignedUser"></span></u>. <s:text name="lbl.whatsapp.pick.it"/>?</label>                           
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="popup-box chat-popup" id="qnimate" data-mobile="" data-lastLoad="0">    
    <div id="frame">
        <div class="content">
            <div class="contact-profile">
                <div class="chat-header text-center"><h4><span id="chat_num"></span><span id="chat_contact"></span><i class="fa fa-pencil hand" onclick="editContactName();" title="Edit"></i><span class="pull-right"> <a data-widget="remove" id="closeChat" class="" type="button" style="border-radius: 0; text-white"><i class="glyphicon glyphicon-remove text-white"></i></a></span></h4>   </div>
            </div>
            <div class="messages">
                <ul id="print_msgs"> </ul> 
                <div class="modal fade summary-page-two" id="summary-page-two" data-keyboard="false" data-backdrop="false" >
                    <div class="lab-modal-body ">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"><s:text name="btn.close"/></span></button>
                        <div class="container-fluid  summary-page-two-fullpage">

                            <div class="row">
                                <div class="col-md-12">
                                    <s:form id="resolve_chat" name="resolve_chat" method="post" theme="simple">
                                        <s:hidden name="txn.wtMobileNo" id="wtMobileNo" />
                                        <s:hidden name="txn.wtTxnId" id="wtTxnId" />
                                        <s:hidden name="txn.wtName" id="wtName" />
                                        <br />
                                        <div class="form-group">
                                            <label for="wtCloseCode" class="control-label"><s:text name="lbl.whatsapp.chat.for"/></label> 
                                            <s:select name="txn.wtCloseCode" id="wtCloseCode" listKey="key" listValue="value" headerKey="-1" headerValue="Select Reason" list="chatReasonList" cssClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label for="text-area" class="control-label"><s:text name="lbl.remarks"/></label> 
                                            <s:textarea name="txn.wtCloseRemarks" id="wtCloseRemarks" cssClass="form-control" rows="10"/>
                                        </div>
                                            <button type="submit" id="resolve_submit" class="btn btn-primary btn-block text-center center-block" data-dismiss="modal" id="all-resolved"><i class="fa fa-paper-plane" aria-hidden="true" ></i> &nbsp;<s:text name="btn.resolve"/></button>
                                        <br />
                                    </s:form>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div> 
                    </div>
                </div>
            </div>
            <div class="message-input">
                <div class="modal fade file-popup-bg" id="lab-slide-bottom-popup" data-keyboard="false" data-backdrop="false" >
                    <div class="lab-modal-body">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"><s:text name="btn.close"/></span></button>
                        <div class="container-fluid">
                            <div class="row no-gutter vertical-align">
                                <div class="col-md-6">
                                    <div class="form-group op-pup">
                                        <s:if test="keyValueList != null && !keyValueList.isEmpty()">
                                            <s:select id="sel_handy_sms" list="keyValueList" listKey="key" listValue="value" headerKey="" headerValue="Please select" cssClass="form-control" />
                                            <input type="hidden" id="hid_template_id"/>
                                        </s:if>
                                        <div id="template_data" style="display: none;"></div>
                                    </div>
                                </div>
                                <div class="col-md-6 text-left">
                                    <div class="upload-btn-wrapper">
                                        <div class="file-upload">
                                            <div class="image-upload-wrap" >
                                                <input class="file-upload-input" type="file" style="display: none;" data-type="file" accept="application/pdf" name="attachment" id="attachment" maxlength="1000"/>
                                                <div class="drag-text" id="file_div">
                                                    <span><img id="btn_doc" src="<%=request.getContextPath()%>/images/wa/ico-files.png" width="50" height="50" data-type="file" style="cursor: pointer;" alt="All Files" /></span>
                                                    <span><img id="btn_audio" src="<%=request.getContextPath()%>/images/wa/ico-audio.png" width="50" height="50" data-type="audio" style="cursor: pointer;" alt="Audio files" /></span>
                                                    <span><img id="btn_img" src="<%=request.getContextPath()%>/images/wa/ico-image.png" width="50" height="50" data-type="image" style="cursor: pointer;" alt="Image files" /></span>
                                                </div>
                                            </div>
                                            <div class="file-upload-content">
                                                <div class="image-title-wrap">
                                                    <button type="button" class="remove-image" onclick="removeUpload()"> <span class="image-title"><s:text name="btn.uploaded.image"/></span>&nbsp;<span class="cross-button">x</span></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div> 
                    </div>
                </div>
                <div class="wrap">
                    <input type="text" id="text_msg" placeholder="<s:text name="lbl.whatsapp.write.your.msg"/>"  maxlength="1000" />
                    <a href="#" class="btn-attach-files" data-toggle="modal" data-target="#lab-slide-bottom-popup"><i class="fa fa-paperclip attachment" aria-hidden="true"></i> </a>
                    <button class="submit" id="send_msg">
                        <i class="fa fa-circle-o-notch fa-spin msg-sending" style="font-size:24px; display:none;"></i>
                        <i class="fa fa-paper-plane msg-send" aria-hidden="true"></i>
                    </button>
                </div>
                <div class="btn-resolve" id="test">
                    <button id="" data-toggle="modal" data-target="#summary-page-two" class="btn btn-success btn-block text-center center-block" type="button" style="border-radius: 0;"><!--<i class="glyphicon glyphicon-off"></i>--><s:text name="btn.resolve"/></button>
                    <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@COMPANY_CODE_MED_DOHA.equals(company) && @qa.com.qic.common.util.ApplicationConstants@APP_TYPE_QLM.equals(#session.USER_INFO.appType)}'>
                        <button id="" class="btn btn-warning btn-block text-center center-block" type="button" style="border-radius: 0;" onclick="openClmSubmit()"><s:text name="btn.submit.claim"/></button>
                    </s:if>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="edit_name" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog  modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><s:text name="lbl.whatsapp.head.edit.contact.name"/></h4>
                <span style="margin-top: -44px; float: right;">
                    <button class="save-btn btn btn-primary" onclick="saveContact()" id="btn_upd_task">&#10004;<s:text name="btn.save"/></button>
                    <button class="close-btn btn" data-dismiss="modal" id="btn_task_close">&#10006; <s:text name="btn.close"/></button>
                </span>    
            </div>
            <div class="modal-body">
                <div class="row" style="margin-left:3px !important"> 
                    <div class="form-group col-md-8">
                        <s:form id="frm_name" name="frm_name" method="post" theme="simple">
                            <s:textfield name="txn.wtName" id="wtName" cssClass="form-control"/>
                        </s:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal_dialog_init_chat" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog  modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><s:text name="lbl.whatsapp.head.invite.customer"/></h4>
            </div>
            <div class="modal-body">
                <s:include value="initiateChat.jsp"/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal_dialog_forward" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><s:text name="lbl.whatsapp.head.forward.req"/></h4>
                <span style="position: absolute; top: 17px; right: 15px;">
                    <button type="button" class="save-btn btn btn-primary" id="forward_btn_save"><i class="fa fa-check"></i> <s:text name="btn.yes"/></button>
                    <button class="close-btn1 btn" data-dismiss="modal"><i class="fa fa fa-window-close"/>  <s:text name="btn.no"/></button>
                </span>
            </div>
            <div class="modal-body">
                <div id="msg_fwd" class="col-md-12 alert alert-danger" style="display: none;"></div>
                <div class="row" style="margin-left:3px !important"> 
                    <div class="form-group col-md-3">
                        <label><s:text name="lbl.whatsapp.forward.to"/></label>                         
                    </div>
                    <div class="form-group col-md-9">
                        <s:hidden name="txn.wtTxnId" id="fwd_wtTxnId" />
                        <s:select class="form-control" name="txn.wtAssignedTo" id="forward_assigned_to" list="userList" listKey="key" listValue="value" headerKey="" headerValue="---Select---"/>                         
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function viewCustomer(mobileNo) {
        var mobile = mobileNo.substring(3);
        var url = APP_CONFIG.context + '/customer360.do?company=<s:property value="#session.USER_INFO.companyCode" />&search=plugin&mobile=' + mobile;
        openCustomerPopup(url);
    }

    function loadResults() {
        var result = "";
        var value = document.getElementById('qnimate').getAttribute('data-lastLoad');
        var mobile = $("#wtMobileNo").val();
        for (var i = 0; i < 10; i++) {
            result += "<li>Result " + i + "</li>";
        }
        $.ajax({
            url: APP_CONFIG.context + "/loadWhatsAppHist.do?watMobileNo=" + mobile + "&fetch=" + value,
            type: "post",
            data: {
                html: result,
                delay: 3
            },
            beforeSend: function (xhr) {
                $("#results").after($("<li class='loading'>Loading...</li>").fadeIn('slow')).data("loading", true);
            },
            success: function (data) {
                $("#data-lastLoad").val(20);
                var $results = $("#replies");
                $(".loading").fadeOut('fast', function () {
                    $(this).remove();
                });
                var $data = $(data);
                $data.hide();
                $results.append($data);
                $data.fadeIn();
                $results.removeData("loading");
            }
        });
    }

    /*$(function () {
        loadResults();
        $(".messages").scroll(function () {
            var $this = $(this);
            var $results = $("#replies");
            loadResults();
            if (!$results.data("loading")) {
                if ($this.scrollTop() + $this.height() == $results.height()) {
                    loadResults();
                }
            }
        });
    });*/

    function openClmSubmit() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadWhatsAppClmSubmission.do",
            data: {"txn.wtTxnId": $('#resolve_chat #wtTxnId').val()},
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

    $('input:radio[id=whatsapp_unread_filter_checkboxI]').prop('checked', true);
    $("#btn_search_whatsapp_unread_msg").on("click", function () {
        if ($.trim($('#whatsapp_txt_unread_message').val()) === '') {
            alert('Please enter message');
            return false;
        }
        $("#unread_tbl").reloadDT(APP_CONFIG.context + "/loadWhatsAppData.do?" + $('#frm_whatsapp_unread_search').serialize());
    });
    $("#btn_clear_whatsapp_unread_msg").on("click", function () {
        $('#whatsapp_txt_unread_message').val('');
        $('input:radio[id=whatsapp_unread_filter_checkboxI]').prop('checked', true);
        $("#unread_tbl").reloadDT(APP_CONFIG.context + "/loadWhatsAppData.do");
    });
</script>
