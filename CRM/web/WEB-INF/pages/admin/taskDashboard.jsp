<%-- 
    Document   : taskDashboard
    Created on : 4 Nov, 2017, 1:35:22 PM
    Author     : sutharsan.g
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
<div class="col-md-12 right-pad">
    <div class="dash-leads" style="border-top:0!important">
        <div class="col-md-12 col-sm-12 col-xs-12 nopad">
            <div class="acti-off my-reminder" style="margin-top: -3px;border-top-color: #FFF">
                <div class="acti-off-heads">
                    <a href="javascript:void(0);" class="active" data-tab="activities" onclick="loadTaskDetails();"><s:text name="lbl.task.details"/></a>
                    <a href="javascript:void(0);" class="" data-tab="eligible" onclick="taskDashboard();"><s:text name="lbl.task.dashboard"/></a>
                </div>
                <ul class="act-name current " id="activities">
                    <div id="task_details" class="col-md-12 col-sm-12 col-xs-12 nopad">
                    </div>
                </ul>
                <ul class="act-name " id="eligible">
                    <div id="task_dashboard" class="col-md-12 col-sm-12 col-xs-12 nopad">
                    </div>
                </ul>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        loadTaskDetails();
        taskDashboard();
    });

    function loadTaskDetails() {
        block('block_body');
        var data = "";
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openTaskEntryPage.do",
            data: data,
            success: function (result) {
                $("#task_details").html(result).show();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function taskDashboard() {
        block('block_body');
        var data = "";
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openTaskDashBoardPage.do",
            data: data,
            success: function (result) {
                $("#task_dashboard").html(result).show();
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
