<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : taskLog
    Created on : Mar 14, 2017, 11:56:13 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 nopad">
    <div class="dash-leads row" style="border-top:0!important">
        <div class="my-bord">
            <h3><s:text name="lbl.task.head.log.task"/></h3>
        </div>
        <div class="my-bord bor2">
            <div class="board-icons1">
                <s:if  test='!"C".equals(task.ctStatus)'>
                    <div><button type="button" class="btn btn-warning leads-tab" id="btn_add_logtask" data-dismiss="modal" aria-hidden="true"><s:text name="btn.add"/></button></div>
                </s:if>   
            </div>
        </div>
        <table id="log_task_tbl" class="table table table-striped table-bordered display dataTable dtr-inline">
            <thead>
                <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                    <th width="8%"><s:text name="lbl.remarks"/></th>
                    <th width="8%"><s:text name="lbl.common.created.date"/></th>     
                    <th width="8%"><s:text name="lbl.common.user.id"/></th> 
                    <th width="8%"><s:text name="lbl.status"/></th> 
                    <!--                            <th width="8%">Action</th> -->
                </tr>
            </thead>
            <tbody>	   
            </tbody>
        </table>
    </div>
</div>
<div id="tasklog_add"></div>
<script type="text/javascript">
    $(document).ready(function () {
        var url = APP_CONFIG.context + "/loadTaskEntryLogData.do?task.ctId=${task.ctId}";
        log_task_tbl = $("#log_task_tbl").DataTable({
            paging: true,
            searching: false,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            "ajax": {
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "ctlDetails"},
                {data: "ctlCrDt",
                    render: function (data, type, row, meta) {
                        if (data !== "" && data !== null) {
                            if (type === "sort") {
                                return moment(data, "YYYY-MM-DDTHH:mm:ss").format('YYYYMMDDHHmmss');
                            } else {
                                return moment(data, "YYYY-MM-DDTHH:mm:ss").format('DD/MM/YYYY HH:mm:ss');
                            }
                        } else {
                            return "";
                        }
                    }
                },
                {data: "ctlCrUid"},
                {data: "ctlStatus",
                    render: function (data, type, row, meta) {
                        if (data !== "" && data !== null) {
                            if (data === 'P') {
                                return "Pending";
                            } else if (data === 'C') {
                                return "Closed";
                            } else if (data === 'H') {
                                return "On Hold";
                            }
                        }
                        return data;
                    }
//                {data: "ctlStatus",
//                      render: function (data, type, row, meta) {
//                    return '<center><i class="fa fa-pencil" title="Modify" data-toggle="tooltip" onclick="modifyTask(\'' + row.ctId + '\');"></i></center>'; 
//                }
                }
            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[1, "desc"]],
            initComplete: function () {
                $("#log_task_tbl tr td").css('cursor', 'default');
                //$('#datatable_search').html($('#log_task_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
        });

        $("#btn_add_logtask").on("click", function () {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/openTaskEntryLogForm.do",
                data: {"operation": "add", "task.ctId": ${task.ctId}, "tasksLog.ctlCtId": ${task.ctId}},
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
        });
    });

    function modifyTaskLog() {
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openTaskEntryLogForm.do?operation=edit",
            data: {},
            success: function (result) {
                $('#tasklog_add').html(result);
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {

            }
        });
    }

</script>