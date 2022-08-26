<%-- 
    Document   : leads
    Created on : 20 Mar, 2017, 2:50:32 PM
    Author     : haridass.a
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 right-pad">
    <div class="dash-leads" style="border-top:0!important">
        <%--div class="my-bord">
            <h3>Leads</h3>
        </div--%>
        <div class="col-md-12 col-sm-12 col-xs-12 nopad">
            <div class="acti-off my-reminder" style="margin-top: -3px;border-top-color: #FFF">
                <div class="acti-off-heads">
                    <a href="javascript:void(0);" class="active" data-tab="tab_lead_details"><s:text name="lbl.leads.lead.details"/></a>
                    <a href="javascript:void(0);" class="" data-tab="tab_lead_db" onclick="openLeadDashboard();"><s:text name="lbl.leads.lead.dashboard"/></a>
                </div>
                <ul class="act-name current" id="tab_lead_details">
                    <div id="lead_details">
                        <div class="my-bord bor2 row nopadding">
                            <div class="col-md-9 Tmar">               
                                <s:form class="form-inline" id="frm_search" name="frm_search" method="post" theme="simple">
                                    <div class="form-group padR">
                                        <label for="email"><s:text name="lbl.data.source"/></label>
                                        <s:select name="leads.clWorkPlace" list="categoryList" listKey="key" listValue="value" cssClass="form-control" id="clWorkPlace"/>
                                    </div>
                                    <div class="form-group padR">
                                        <button type="button" id="search_lead" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
                                    </div>
                                </s:form>
                            </div>
                            <div class="col-md-3 board-icons1 Rbtn">
                                <button class="btn btn-warning tmargin cbtn" onclick="UploadTaskFile();"><s:text name="btn.upload"/></button>
                                <button class="btn btn-warning tmargin cbtn" onclick="showLeadForm('add');"><s:text name="btn.add"/></button>
                            </div>
                        </div>
                        <table id="lead_tbl" class="table table-striped table-bordered display dataTable dtr-inline">
                            <thead>
                                <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                    <th width="8%"><s:text name="lbl.leads.head.lead.id"/></th>
                                    <!--th width="8%">Source</th-->     
                                    <th width="8%"><s:text name="lbl.common.refno"/></th>                                              
                                    <th width="8%"><s:text name="lbl.common.name"/></th> 
                                    <th width="8%"><s:text name="lbl.mobile.no"/></th> 
                                    <th width="8%"><s:text name="lbl.common.crdate"/></th>
                                    <th width="8%"><s:text name="lbl.city"/></th>
                                    <th width="8%"><s:text name="lbl.nationality"/></th>
                                    <th width="8%"><s:text name="lbl.status"/></th>
                                    <th width="8%" class="no-sort"><s:text name="lbl.action"/></th>
                                </tr>
                            </thead>
                            <tbody>	   
                            </tbody>
                        </table>
                    </div>
                </ul>
                <ul class="act-name" id="tab_lead_db">
                    <div id="lead_dashboard">
                    </div>
                </ul>
            </div>
        </div>
    </div>
</div>
<div id="lead_add"></div>
<script type="text/javascript">
    var lead_data = {"leads.clId": "", "operation": ""};
    $(document).ready(function () {
        var url = APP_CONFIG.context + "/loadLeadsEntryData.do?" + $('#frm_search').serialize();
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
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "clId",
                    render: function (data, type, row, meta) {
                        return data + "&nbsp;" + (row.clFlex05 === '1' ? '<i class="fa fa-tasks" title="Task created"></i>' : '');
                    }
                },
                //{data: "clSource"},
                {data: "clRefNo"},
                {data: "clName"},
                {data: "clMobileNo"},
                {data: "clCrDt",
                    render: function (data, type, row, meta) {
                        if(data && type === "sort") {
                            data = moment(data, "YYYY-MM-DDTHH:mm:ss").format('YYYYMMDDHHmmss');
                        } else {
                            data = moment(data, "YYYY-MM-DDTHH:mm:ss").format('DD/MM/YYYY');
                        }
                        return data;
                    }
                },
                {data: "clCity"},
                {data: "clNationality"},
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
                },
                {data: "clStatus",
                    render: function (data, type, row, meta) {
                        return '<center>'
                                + ((row.clStatus !== "" && row.clStatus !== null && row.clStatus === 'C') ? '' : ' <i class="fa fa-pencil" title="Modify" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="showLeadForm(\'edit\',\'' + row.clId + '\');"></i>')
                                //+ '&nbsp;&nbsp;<i class="fa fa-eye" title="Create opportunity" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="createOpportunity(\'' + row.clId + '\');"></i>'
                                + ((row.clRefId === null || row.clRefId === '' || row.clRefId === 'undefined') ? '' :
                                        '&nbsp;&nbsp;<i class="fa fa-address-card" title="View Customer Info" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="window.location.href = APP_CONFIG.context + \'/customer360.do?company=<s:property value="#session.USER_INFO.companyCode" />&civilid=' + row.clRefId + '&search=view\';"></i>')
                                + '</center>';

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
                $("#lead_tbl tr td").css('cursor', 'default');
                //$('#datatable_search').html($('#lead_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
        });
        $("#search_lead").on("click", function () {
        block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/loadLeadsEntryData.do",
                data: $("#frm_search").serialize(),
                success: function (result) {
                    reloadDataTable("#lead_tbl", result.aaData);

                    //$('#task_tbl').html(result);
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

    function showLeadForm() {
        lead_data["operation"] = arguments[0];
        if ("edit" === arguments[0]) {
            lead_data["leads.clId"] = arguments[1];
        }
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/showLeadsEntryForm.do",
            data: lead_data,
            success: function (result) {
//                $('#plugin_modal_dialog .modal-dialog').removeClass("modal-mg modal-sm").addClass("modal-lg");
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

    function createOpportunity(clId) {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openOpportunitiesEntryForm.do",
            data: {"leads.clId": clId, "operation": "add"},
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

    function viewCustomer(ctRefId) {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/customer360.do?company=<s:property value="#session.USER_INFO.companyCode" />&search=search&civilid=" + ctRefId,
            data: {"task.ctRefId": ctRefId},
            success: function (result) {
                $('#plugin_modal_dialog .modal-dialog').removeClass("modal-mm modal-sm").addClass("modal-lg");
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

    function UploadTaskFile() {
        block('block_body');
        $.ajax({
            type: "POST",
            data: {},
            url: APP_CONFIG.context + "/openUploadLeadsEntryForm.do",
            success: function (result) {
                $("#block_body").html(result);
                /*$('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm").addClass("modal-mm");
                 $('#plugin_modal_dialog .modal-content').empty().html(result);
                 $('#plugin_modal_dialog').modal('show');*/
//                     $('#popup_leadupload').html(result);
//                   $('.popup-wrap').addClass('popup-open');
//                    $('#overlay').show();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }
    
    function openLeadDashboard() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openLeadsDashBoardPage.do",
            data: {},
            success: function (result) {
               $("#lead_dashboard").html(result).show();
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

        $('.acti-off-heads a').click(function () {
            var tab_id = $(this).attr('data-tab');

            $('.acti-off-heads a').removeClass('active');
            $('.act-name').removeClass('current');

            $(this).addClass('active');
            $("#" + tab_id).addClass('current');
        });

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
