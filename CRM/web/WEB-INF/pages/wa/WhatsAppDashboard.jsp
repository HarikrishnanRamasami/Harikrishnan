<%-- 
    Document   : WhatsAppDashboard
    Created on : May 1, 2019, 10:36:22 AM
    Author     : ravindar.singh
--%>

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
<div class="crm-dashes taskleft">
    <div class="dash-leads" style="border-top:0!important">
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12 nopad">
                <div class="acti-off my-reminder" style="margin-top: -3px;border-top-color: #FFF">
                    <div class="acti-off-heads">
                        <a href="javascript:void(0);" class="active" data-tab="wat_messages_block" onclick="loadMessages();"><s:text name="lbl.whatsapp.unread.msg"/></a>
                        <a href="javascript:void(0);" class="" data-tab="wat_dashboard_block" onclick="loadDashboard();"><s:text name="lbl.dashboard"/></a>
                        <a href="javascript:void(0);" class="" data-tab="wat_history_block" onclick="loadTransactionHistory();"><s:text name="lbl.common.history"/></a>
                    </div>
                    <ul class="act-name current" id="wat_messages_block">
                        <div id="wat_messages" class="col-md-12 col-sm-12 col-xs-12 nopad">
                        </div>
                    </ul>
                    <ul class="act-name" id="wat_dashboard_block">
                        <div id="wat_dashboard" class="col-md-12 col-sm-12 col-xs-12 nopad">
                        </div>
                    </ul>
                    <ul class="act-name" id="wat_history_block">
                        <div id="wat_history" class="col-md-12 col-sm-12 col-xs-12 nopad">
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        loadMessages();

        $('.acti-off-heads a').click(function () {
            var tab_id = $(this).attr('data-tab');

            $('.acti-off-heads a').removeClass('active');
            $('.act-name').removeClass('current');

            $(this).addClass('active');
            $("#" + tab_id).addClass('current');
        });
    });

    function loadMessages() {
        block('block_body');
        var data = "";
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openWhatsAppDetailPage.do",
            data: data,
            success: function (result) {
                $("#wat_messages, #wat_dashboard, #wat_history").empty();
                $("#wat_messages").html(result).show();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function loadDashboard() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openWhatsAppDashBoardPage.do",
            data: {},
            success: function (result) {
                $("#wat_messages, #wat_dashboard, #wat_history").empty();
                $("#wat_dashboard").html(result);
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function loadTransactionHistory() {
        var data = "";
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openWhatsAppTxnHistoryPage.do",
            data: data,
            success: function (result) {
                $("#wat_messages, #wat_dashboard, #wat_history").empty();
                $("#wat_history").html(result).show();
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
