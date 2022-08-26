<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : leadsUploadedData
    Created on : Oct 26, 2017, 2:31:54 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<table class="table table-custom table-bordered table-responsive nowrap nolink" id="lead_tbl" style="display: block;" role="grid">
    <thead>
        <tr>
            <th width="8%"><s:text name="lbl.leads.head.lead.id"/></th>
            <th width="8%">><s:text name="lbl.source"/></th>
            <th width="8%">><s:text name="lbl.common.refid"/></th>                                              
            <th width="8%">><s:text name="lbl.common.name"/></th> 
            <th width="8%">><s:text name="lbl.mobile.no"/></th> 
            <th width="8%">><s:text name="lbl.common.crdate"/></th>
            <th width="8%">><s:text name="lbl.status"/></th>
        </tr>
    </thead>
    <tbody>
    </tbody> 
</table>
<div class="row">
    <div class="col-lg-12 col-md-12 col-sm-9 col-xs-12 text-center">
        <input type="button" name="btn_excel_confirm" id="btn_excel_confirm" value="Confirm" data-toggle="tooltip" data-placement="bottom" title="<s:text name="lbl.common.confirm"/>" onclick="excelDataConfirm();" class="btn bg-red waves-effect" /> 
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        lead_tbl = $("#lead_tbl").DataTable({
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
                "url": APP_CONFIG.context + "/loadLeadsEntryUploadedData.do?leads.clId=${leads.clId}",
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "clId"},
                {data: "clWorkPlace"},
                {data: "clRefId"},
                {data: "clName"},
                {data: "clMobileNo"},
                {data: "clCrDt",
                    render: function (data, type, row, meta) {
                        if (data !== "" && data !== null) {
                            var result = moment(data, "YYYY-MM-DDTHH:mm:ss").format('DD/MM/YYYY');
                            return result;
                        }
                        else {
                            return "";
                        }
                    }
                },
                {data: "clStatus",
                    render: function (data, type, row, meta) {
                        if (data !== "" && data !== null && data === 'P') {
                            return "Pending";
                        } else if (data !== "" && data !== null && data === 'C') {
                            return "Closed";
                        } else {
                            return data;
                        }
                    }
                }
            ],
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function () {
                $("#lead_tbl tr td").css('cursor', 'default');
                $('#quote_search').html($('#lead_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
        });
    });

    function excelDataConfirm() {
        block("block_body");
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/confirmLeadsEntryUploadedForm.do",
            data: {"leads.clId": "${leads.clId}"},
            success: function (result) {
                unblock("block_body");
                if (result.messageType === "S") {
                    $("#btn_excel_extract_data").remove();
                    $("#btn_excel_confirm").remove();
                    $("#msg_lead_upload").removeClass("alert-danger").addClass("alert-success").html("Leads confirmed successfully").show();
                } else {
                    $("#msg_lead_upload").removeClass("alert-success").addClass("alert-danger").html(result.message).show();
                }
                //$("#block_excel_data").html(result);
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