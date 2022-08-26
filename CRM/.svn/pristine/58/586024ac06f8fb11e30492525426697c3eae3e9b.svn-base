<%-- 
    Document   : handySms
    Created on : Aug 28, 2017, 11:43:30 AM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="card">
    <div class="header">
        <h4 class="m-tb-6 d-inline-block"><s:text name="lbl.handy.sms"/></h4>
        <div class="pull-right cstm_btn">
            <button type="button" class="btn btn-success waves-effect" id="btn_add_crmsms" onclick="showSmsForm('add');"><s:text name="btn.add.handy.sms"/></button>
        </div>
    </div>
    <div class="body">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-custom table-bordered table-responsive nowrap nolink" id="tbl_handy_sms" style="display: block;" role="grid">
                    <thead>
                        <tr>
                            <th width="8%"><s:text name="lbl.code"/></th>
                            <th width="8%"><s:text name="lbl.type"/></th>
                            <th width="8%"><s:text name="lbl.description"/></th>
                            <th width="8%"><s:text name="lbl.action"/></th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody> 
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var crm_data = {"appcodes.acCode": "", "operation": ""};
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        var url = APP_CONFIG.context + "/admin/loadHandySmsEntryData.do";
        tbl_handy_sms = $("#tbl_handy_sms").DataTable({
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
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "acCode"},
                {data: "acValue",
                    render: function (data, type, row, meta) {
                        if (data !== "" && data !== null && data === 'M') {
                            return "Modify";
                        } else if (data !== "" && data !== null && data === 'F') {
                            return "Fixed";
                        } else {
                            return data;
                        }
                    }
                },
                {data: "acDesc"},
                {data: "acDesc",
                    render: function (data, type, row, meta) {
                        if (row.acDesc !== null && row.acDesc !== "")
                            return '<center><i class="fa fa-pencil" title="Edit" data-toggle="tooltip" data-placement="top" style="cursor:pointer;color: #418bca;" onclick="showSmsForm(\'edit\',\'' + row.acCode + '\');"></i></center>';
                        else
                            return "";
                    }
                }
            ],
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
        });
    });
    function showSmsForm() {
        crm_data["operation"] = arguments[0];
        if ("edit" === arguments[0]) {
            crm_data["appcodes.acCode"] = arguments[1];
        }
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/admin/openHandySmsEntryForm.do",
            data: crm_data,
            success: function (result) {
                $('#plugin_modal_dialog .modal-dialog').removeClass("modal-mg modal-sm").addClass("modal-lg");
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
    function showSmsForm(operation, acCode) {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/admin/openHandySmsEntryForm.do",
            data: {"appcodes.acCode": acCode, "operation": operation},
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
</script>