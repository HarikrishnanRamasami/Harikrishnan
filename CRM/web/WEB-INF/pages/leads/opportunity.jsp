<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : opportunity
    Created on : Apr 2, 2017, 3:22:16 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 right-pad">
    <div class="dash-leads" style="border-top:0!important">
        <div class="my-bord">
            <h3><s:text name="lbl.leads.opportunity"/></h3>
        </div>
        <div class="my-bord bor2">
            <div class="">
                <div class="col-sm-12 col-md-9 Tmar">               
                    <s:form class="form-inline" id="frm_opportunity_search" name="frm_opportunity_search" method="post" theme="simple">
                        <div class="form-group padR">
                            <label for="email"><s:text name="lbl.status"/></label>
                            <s:select class="form-control" name="leads.clStatus" id="clStatus" list="statusList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --"/>
                        </div>
                        <div class="form-group padR">
                            <label for="pwd"><s:text name="lbl.user"/></label>
                            <s:select class="form-control" name="leads.clAssignedTo" id="clAssignedTo" list="userList" listKey="key" listValue="value"/>
                        </div>
                        <button type="button" id="search_opp_btn" class="tsearchbtn"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
                    </s:form>
                </div>
                <table id="opportunity_tbl" class="table table-striped table-bordered display dataTable dtr-inline">
                    <thead>
                        <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                            <th width="8%"><s:text name="lbl.leads.head.opportunity.id"/></th>
                            <th width="8%"><s:text name="lbl.type"/></th>
                            <th width="8%"><s:text name="lbl.source"/></th>     
                            <th width="8%"><s:text name="lbl.common.refno"/></th>                                              
                            <th width="8%"><s:text name="lbl.common.name"/></th> 
                            <th width="8%"><s:text name="lbl.mobile.no"/></th> 
                            <th width="8%"><s:text name="lbl.common.currency"/></th>
                            <th width="8%"><s:text name="lbl.value"/></th>
                            <th width="8%"><s:text name="lbl.status"/></th>
                            <th width="8%"><s:text name="lbl.action"/></th>
                        </tr>
                        </tr>
                    </thead>
                    <tbody>	   
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<div id="lead_add"></div>
<script type="text/javascript">
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        var url = APP_CONFIG.context + "/loadOpportunitiesEntryData.do?" + $('#frm_opportunity_search').serialize();
        opportunity_tbl = $("#opportunity_tbl").DataTable({
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
                {data: "coId"},
                {data: "coOppTypeDesc"},
                {data: "coSourceDesc"},
                {data: "coRefNo"},
                {data: "coName"},
                {data: "coMobileNo"},
                {data: "coCurrencyDesc"},
                {data: "coValue"},
                {data: "coStatus",
                    render: function (data, type, row, meta) {
                        if (data !== "" && data !== null && data === 'O') {
                            return "Open";
                        } else if (data !== "" && data !== null && data === 'C') {
                            return "Closed";
                        } else if (data !== "" && data !== null && data === 'S') {
                            return "Success";
                        } else if (data !== "" && data !== null && data === 'F') {
                            return "Fail";
                        } else {
                            return data;
                        }
                    }
                },
                {data: "coStatus",
                    render: function (data, type, row, meta) {
                        if (row.coStatus !== null && row.coStatus !== "" && row.coStatus === "O")
                            return '<center><i class="fa fa-pencil" title="Modify" data-toggle="tooltip" data-placement="top" style="cursor:pointer;color: #418bca;" onclick="showOppurtunityForm(\'edit\',\'' + row.coId + '\');"></i></center>';
                        else
                            return "";
                    }
                }
            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function () {
                $("#opportunity_tbl tr td").css('cursor', 'default');
                //$('#datatable_search').html($('#opportunity_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
        });
        $("#search_opp_btn").on("click", function () {
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/loadOpportunitiesEntryData.do",
                data: $("#frm_opportunity_search").serialize(),
                success: function (result) {
                    reloadDataTable("#opportunity_tbl", result.aaData);
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {

                }
            });
        });
    });
    function showOppurtunityForm(operation, coId) {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openOpportunitiesEntryForm.do",
            data: {"oppurtunities.coId": coId, "operation": operation},
            success: function (result) {
//                $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm").addClass("modal-mm");
//                $('#plugin_modal_dialog .modal-content').empty().html(result);
//                $('#plugin_modal_dialog').modal('show');
                $('#popup_custom').html(result);
                //$('#plugin_modal_dialog').modals().mm(result);
                $('.popup-wrap').addClass('popup-open');
                $('#overlay').show();
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
<script type="text/javascript">
    $(function () {

        //  $('#overflow').slimScroll();

        $('#sidebar').slimScroll({
            height: '100%',
            width: '100%',
        });
        $('.leads-tab').click(function () {
            $('.popup-wrap').addClass('popup-open');
            $('#overlay').show();
        });
        $('.close-link').click(function () {
            $('.popup-wrap').removeClass('popup-open');
            $('#overlay').hide();
        });
        $('.close-btn').click(function () {
            $('.popup-wrap').removeClass('popup-open');
            $('#overlay').hide();
        });
    });
</script>