<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : campaignList
    Created on : Aug 17, 2017, 12:51:37 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 right-pad" id="block_camp">
    <div class="dash-leads">
        <div class="my-bord">
            <h3><s:text name="lbl.campaign.head.campaigns.uppercase"/></h3>
        </div>
        <div class="my-bord bor2">
            <div class="col-sm-12 col-md-3 board-icons1 Rbtn">
                <button type="button" style="margin-top: -9px;" class="btn btn-success waves-effect" id="btn_add_campaign" data-dismiss="modal" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="<s:text name="btn.add.copy.campaign"/>"><s:text name="btn.add.campaign"/></button>       
                <button type="button" style="margin-top: -9px;" class="btn btn-success waves-effect" id="btn_copy_campaign" data-dismiss="modal" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="<s:text name="btn.add.copy.campaign"/>"><s:text name="btn.add.copy.campaign"/></button>       
            </div>
            <s:form class="form-inline" id="frm_search" name="frm_search" method="post" theme="simple">
                <div class="col-sm-12 col-md-12 bor2 Tmar">
                    <div class="row bor2">
                        <div class="form-group padR">
                            <s:textfield  name="campaign.mcCampName" id="mcCampName" class="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.campaign.enter.campaign.name')}"/></div> 
                        <div class="form-group padR">
                            <label><s:text name="lbl.status"/></label>
                            <s:select name="campaign.mcStatus" id="mcStatus" class="form-control" list="campStatusList" listKey="key" listValue="value" cssClass="form-control"/>
                        </div>
                        <div class="form-group padR">
                            <button type="button" id="search_campaign" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
                        </div>
                    </div>
                </div>
            </s:form>

            <!--Displaying Campaign List -->
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-custom table-bordered table-striped table-responsive nowrap nolink" id="tbl_campaign_view" role="grid">
                        <thead>
                            <tr>  
                                <th class="text-center"><s:text name="lbl.campaign.name"/></th>
                                <th class="text-center"><s:text name="lbl.campaign.head.sub.campaign.type"/></th>
                                <th class="text-center"><s:text name="lbl.data.source"/></th>
                                <th class="text-center"><s:text name="lbl.status"/></th>
                                <th class="text-center"><s:text name="lbl.campaign.head.sub.next.rundate"/></th>
                                <th class="text-center"><s:text name="lbl.action"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody> 
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="block_camp_report">
</div>
<div id="block_camp_txn">
</div>
<div id="block_txn_dashboard">
</div>
<s:form name="camp_list" id="camp_list"/>
<script type="text/javascript">
    $(document).ready(function () {
        $('#mcStatus').val('R');
        var url = APP_CONFIG.context + "/camp/loadCampaignData.do?campaign.mcStatus=R";
        $("#tbl_campaign_view").DataTable({
            paging: true,
            searching: true,
            ordering: false,
            info: true,
            "destroy": true,
            "lengthChange": false,
            "pageLength": 10,
            "responsive": true,
            autoWidth: false,
            "processing": true,
            "ajax": {
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "mcCampName"},
                {data: "mcCampType"},
                {data: "mcDataSourceType"},
                {data: "mcStatusDesc"},
                {data: "mcNextRunDate",
                    render: function (data, type, row, meta) {
                        if (data !== "" && data !== null) {
                            var result = moment(data).format('DD/MM/YYYY HH:mm');
                            return result;
                        } else {
                            return "";
                        }
                    }
                },
                {data: "",
                    render: function (data, type, row, meta) {
                        let t = '<center>';
                        if (row.mcStatus === 'P') {
                            t += '<i class="fa fa-edit action-icon" title="Edit" onclick="editCampaign(\'' + row.mcCampId + '\', \'edit\');"></i>&nbsp;&nbsp;';
                        } else if (row.mcStatus === 'S' && row.mcRecurringYn === '1') {
                            t += '<i class="fa fa-edit action-icon" title="Start Again" onclick="editCampaign(\'' + row.mcCampId + '\', \'change\');"></i>&nbsp;&nbsp;';
                        }
                        if (row.mcStatus !== 'P') {
                            t += '<i class="fa fa-eye action-icon" title="View" onclick="editCampaign(\'' + row.mcCampId + '\', \'view\');"></i>&nbsp;&nbsp;';
                            t += '<i class="fa fa-tasks action-icon" title="Transactions" onclick="openTransactions(\'' + row.mcCampId + '\')";></i>&nbsp;&nbsp;';
                            t += '<i class="fa fa-line-chart action-icon" title="Dashboard" onclick="campaignDashboard(\'' + row.mcCampId + '\')";></i>';
                        }
                        t += '</center>';
                        return t;
                    }
                }
            ],
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function () {
                $("#tbl_campaign_view tr td").css('cursor', 'default');
            }
        });
        
        $("#btn_add_campaign").on("click", function () {
            editCampaign("", "add");
        });
        
        $("#btn_copy_campaign").on("click", function () {
             block('block_body');
            $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/openCampaignCopyPage.do",
            data: {},
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
        });
        
        $("#search_campaign").on("click", function () {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/loadCampaignData.do",
                data: $('#frm_search').serialize(),
                success: function (result) {
                    reloadDataTable("#tbl_campaign_view", result.aaData);
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
    
    function editCampaign(mcCampId, oper) {
        /*block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/AddCampaign.do",
            data: {"mcCampId": mcCampId, "oper": oper},
            success: function (result) {
                $('#block_camp').hide();
                $('#block_camp_report').empty().html(result).show();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });*/
        var frm = $('<form>', {
            'action': APP_CONFIG.context + '/camp/openCampaignForm.do',
            'method': 'post'
        }).append($('<input>', {
            'name': 'mcCampId',
            'value': mcCampId,
            'type': 'hidden'
        })).append($('<input>', {
            'name': 'oper',
            'value': oper,
            'type': 'hidden'
        }));
        $(document.body).append(frm);
        frm.submit();
    }

    function openABPerformance(campId, count, date) {

        $("#frm_ab_performance #mcCampId").val(campId);
        if (count !== 'null' && date !== null)
        {
            $("#frm_ab_performance #mcAbDataCount").val(count);
            $("#frm_ab_performance #mcAbEndDate").val(date);
        } else {
            $('#frm_ab_performance')[0].reset();
        }
        $('#err_ab_dialog').hide();
        $("#ab_modal_dialog").modal("show");
    }

    $("#btn_start").on("click", function () {
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/updateCampaign.do",
            data: $("#frm_ab_performance").serialize(),
            success: function (result) {
                if (result.message === null) {

                    $("#ab_modal_dialog").modal("hide");
                    $('#tbl_campaign_view').DataTable().ajax.reload();
                } else {
                    $("#err_ab_dialog").html(result.message).show();
                }
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    });

    function openTransactions(campId) {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/campaignTxn.do",
            data: {"mcCampId": campId},
            success: function (result) {
                $('#block_camp').hide();
                $('#block_camp_txn').empty().html(result).show();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }
    
    function campaignDashboard(mcCampId){
         block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/campaignTxnDash.do",
            data: {"mcCampId": mcCampId},
            success: function (result) {
                $('#block_camp').hide();
                $('#block_camp_txn').hide();
                $('#block_txn_dashboard').empty().html(result).show();
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
