<%-- 
    Document   : campaignFilters
    Created on : 18 Sep, 2019, 5:27:50 PM
    Author     : syed.basha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 right-pad" id="block_body">
    <s:include value="/WEB-INF/pages/campaign/campaignHeader.jsp?stepNo=7"></s:include>
    <s:hidden name="mcCampId" id="mcCampId"/>
    <s:hidden name="campaign.mcStatus" id="mcStatus"/>
    <s:hidden name="campTemplate.mctTemplateId" id="teempId"/>
    <s:hidden  id="mcpfPathId"/>
    <div class="dash-leads" style="border-top:0!important">
        <s:if test='"P".equals(campaign.mcStatus)'>
            <div class="col-sm-12 col-md-3 board-icons1 Rbtn">
                <button type="button" class="btn btn-success waves-effect" style="width: 50px; margin-top: -8px" onclick="addPath('add');"><s:text name="btn.add"/></button>
            </div>
        </s:if>
        <div class="my-bord bor2">
            <table id="tbl_jour_path" class="table table-striped table-bordered display dataTable dtr-inline">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                        <th style="width: 10%;" class="text-center"><s:text name="lbl.name"/></th>
                        <th style="width: 10%;" class="text-center"><s:text name="lbl.filter"/></th>
                        <th style="width: 10%;" class="text-center"><s:text name="lbl.common.head.sub.actions"/></th>
                    </tr>
                </thead>
                <tbody>	   
                </tbody>
            </table>
        </div>
        <div class="dash-leads" id="path_flow_div" style="border-top:0!important;display: none;">
            <div class="col-md-12 Rbtn">
                <h3 class="pull-left"><s:text name="lbl.campaign.head.path.filters"/></h3>
                <s:if test='"P".equals(campaign.mcStatus)'>
                    <button type="button" class="btn btn-success waves-effect" style="width: 50px; margin-top: 3px" onclick="addPathFlow('add');"><s:text name="btn.add"/></button>
                </s:if>
            </div>
            <div class="my-bord bor2">              
                <table id="tbl_jour_path_flow" class="table table-striped table-bordered display dataTable dtr-inline">
                    <thead>
                        <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                            <th style="width: 10%;"><s:text name="lbl.campaign.head.sub.flow.no"/></th>
                            <th style="width: 10%;"><s:text name="lbl.action"/></th>
                            <th style="width: 10%;"><s:text name="lbl.campaign.head.sub.split.yn"/></th>
                            <th style="width: 10%;"><s:text name="lbl.common.template1"/></th>
                            <th style="width: 10%;"><s:text name="lbl.common.template2"/></th>
                            <th style="width: 10%;"><s:text name="lbl.common.count"/></th>
                            <th style="width: 10%;"><s:text name="lbl.common.head.sub.actions"/></th>
                        </tr>
                    </thead>
                    <tbody>	   
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row b-t mt-10" id="div_btns">
            <div class="col-md-12 form-group center mt-30">
                <button type="button" class="btn btn-primary leads-tab" style="margin-left: 16px;" onclick="goBackWard();"> <s:text name="btn.back"/> <i class="fa fa-arrow-circle-left"></i></button>
                <button type="button" class="btn btn-primary leads-tab" style="float: right" onclick="proceedJourney();"> <s:text name="btn.proceed"/> <i class="fa fa-arrow-circle-right"></i></button>
            </div>
        </div>
    </div>

    <div class="modal fade" id="data_path" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
        <div class="modal-dialog modal-mm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Journey</h4>
                    <div style="margin-top: -31px; float: right;">
                        <button class="save-btn btn btn-primary" onclick="saveCampPath();">&#10004;<s:text name="btn.save"/></button>
                        <button class="close-btn btn" data-dismiss="modal">&#10006; <s:text name="btn.close"/></button>
                    </div>
                </div>
                <div class="body" style="padding: 7px;">
                    <div id="msg_camp_temp" class="alert-danger" style="display: none;"></div>
                    <s:form id="frm_mcf_path" name="frm_mcf_path">
                        <s:hidden name="campPath.mcpPathId" id="pathId"/>
                        <s:hidden name="oper" id="oper" />
                        <div class="row">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <label><s:text name="lbl.name"/>: <span>*</span></label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <s:textfield name="campPath.mcpPathName" id="mcpPathName" cssClass="form-control" required="true" title="%{getText('lbl.campaign.enter.path.name')}"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <label><s:text name="lbl.filter"/>: </label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <s:select name="campPath.mcpFilterId" id="mcpFilterId" headerKey="" headerValue="--select--" list="filterList" listKey="key" listValue="value" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var url = APP_CONFIG.context + "/camp/loadJourneyPathList.do?mcCampId=" + $("#mcCampId").val();
        var table = $("#tbl_jour_path").DataTable({
            paging: true,
            searching: true,
            ordering: true,
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
                {data: "mcpPathName"},
                {data: "filterName"},
                {data: "mcpFilterId",
                    render: function (data, type, row, meta) {
                        if ($("#mcStatus").val() === "P") {
                            return  '<left><i class="fa fa-pencil action-icon" onclick="javascript: editPathJourney(\'edit\',\'' + row.mcpPathId + '\',\'' + row.mcpCampId + '\' );" title="Edit"></i>&nbsp;&nbsp' +
                                    '<i class="fa fa-list alt action-icon" onclick="javascript: loadPathFlowList(\'' + row.mcpPathId + '\');" title="Flows"></i>&nbsp;&nbsp' +
                                    '<i class="fa fa-trash action-icon" onclick="javascript: deletePathFilter(\'' + row.mcpPathId + '\');" title="Delete"></i></center>';
                        } else {
                            return '<i class="fa fa-list alt action-icon" onclick="javascript: loadPathFlowList(\'' + row.mcpPathId + '\');" title="Flows"></i>&nbsp;&nbsp';
                        }
                    }
                }
            ],
            columnDefs: [
                {targets: 2, orderable: false}
            ],
            language: {
                searchPlaceholder: "Search..."
            },
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function () {
            }
        });

        $('#tbl_jour_path tbody').on('click', 'tr', function () {
            var pathId = table.row(this).data().mcpPathId;
            loadPathFlowList(pathId);
        });

    });

    function addPath(mode) {
        $("#data_path").modal("show");
        $("#frm_mcf_path #mcpPathName").val("");
        $("#frm_mcf_path #mcpCampId").val($("#mcpCampId").val());
        $("#frm_mcf_path #mcpFilterId").val("");
        $("#oper").val(mode);

    }

    function saveCampPath() {
        if ($.trim($("#frm_mcf_path #mcpPathName").val()) === "") {
            $.notify("Enter Path Name", "custom");
            return;
        }
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/saveJourneyPathData.do?mcCampId=" + $("#mcCampId").val(),
            data: $("#frm_mcf_path").serialize(),
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify(result.message, "custom");
                    $("#data_path").modal("hide");
                    reloadDt("tbl_jour_path");
                }
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }


    function editPathJourney(oper, pathId, campId) {
        var data = {"company": "<s:property value="#session.USER_INFO.companyCode"/>"};
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/openCampaignJouneyForm.do?oper=" + oper + "&campPath.mcpPathId=" + pathId + "&campPath.mcpCampId=" + campId,
            data: data,
            success: function (result) {
                console.log(result.campPath);
                $("#data_path #mcpPathName").val(result.campPath.mcpPathName);
                $("#frm_mcf_path #mcpFilterId").val(result.campPath.mcpFilterId);
                $("#data_path #pathId").val(pathId);
                $("#frm_mcf_path #oper").val(oper);
                $("#data_path").modal("show");
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function deletePathFilter(rowId) {
        var data = {"company": "<s:property value="#session.USER_INFO.companyCode"/>", "campPath.mcpPathId": rowId, "oper": "delete"};
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/saveJourneyPathData.do",
            data: data,
            success: function (result) {
                console.log(result.campPath);
                reloadDt("tbl_jour_path");

            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function loadPathFlowList(pathId) {
        $("#mcpfPathId").val(pathId);
        $("#path_flow_div").show();
        var url = APP_CONFIG.context + "/camp/loadJourneyPahtFlowList.do?campPathFlow.mcpfPathId=" + pathId+"&mcCampId=" + $("#mcCampId").val();
        $("#tbl_jour_path_flow").DataTable({
            paging: true,
            searching: false,
            ordering: true,
            rowReorder: true,
            info: true,
            "destroy": true,
            "lengthChange": false,
            "pageLength": 10,
            "responsive": true,
            autoWidth: false,
            "processing": true,
            "order": [[0, 'asc']],
            "ajax": {
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "mcpfFlowNo"},
                {data: "mcpfAction"},
                {data: "mcpfSplitYn",
                    render: function (data) {
                        if (data === '1') {
                            return "Yes";
                        } else if (data === '0') {
                            return "No";
                        } else {
                            return "";
                        }
                    }
                },
                {data: "mcpfTemplateName"},
                {data: "mcpfTemplate2Name"},
                {data: "mcpfCount"},
                {data: "mcpfText",
                    render: function (data, type, row, meta) {
                    <s:if test='"P".equals(campaign.mcStatus)'>
                        var res = '<center><i class="fa fa-pencil action-icon" onclick="javascript: editPathFlowJourney(\'edit\',\'' + row.mcpfPathId + '\',\'' + row.mcpfFlowNo + '\' );" title="Edit"></i>&nbsp;&nbsp;'
                        if (row.mcpfDelYn === "1") {
                            res = res + '<i class="fa fa-trash action-icon" onclick="javascript: deletePathFlowJourney(\'' + row.mcpfPathId + '\',\'' + row.mcpfFlowNo + '\' );" title="Delete"></i>';
                        }
                        return res;
                    </s:if>
                    <s:else>
                        return  '<center><i class="fa fa-eye action-icon" onclick="javascript: editPathFlowJourney(\'view\',\'' + row.mcpfPathId + '\',\'' + row.mcpfFlowNo + '\' );" title="Edit"></i></center>';
                    </s:else>
                    }
                }
            ],
            columnDefs: [
                {targets: 1, orderable: false},
                {targets: 2, orderable: false},
                {targets: 3, orderable: false},
                {targets: 4, orderable: false},
                {targets: 5, orderable: false}
            ],
            language: {
                searchPlaceholder: "Search..."
            },
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function () {
            }
        });
    }

//Path flow
    function addPathFlow(oper) {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/openCampaignPathFlow.do?oper=" + oper,
            data: {"mcCampId": $("#mcCampId").val(), "campPathFlow.mcpfPathId": $("#mcpfPathId").val()},
            success: function (result) {
                //console.log(result);
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


    function editPathFlowJourney(oper, pathId, flowNo) {
        //$("#mcpfFlowNo").val(flowNo);
        var data = {"mcCampId": $("#mcCampId").val(), "campPathFlow.mcpfPathId": pathId, "campPathFlow.mcpfFlowNo": flowNo, "company": "<s:property value="#session.USER_INFO.companyCode"/>"};
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/openCampaignPathFlow.do?oper=" + oper,
            data: data,
            success: function (result) {
                $('input[name="campPathFlow.mcpfSplitYn"][value="0"]').prop('checked', true);
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
    function deletePathFlowJourney(pathId, flowNo) {
        var data = {"mcCampId": $("#mcCampId").val(), "campPathFlow.mcpfPathId": pathId, "campPathFlow.mcpfFlowNo": flowNo, "company": "<s:property value="#session.USER_INFO.companyCode"/>"};
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/deleteCampaignPathFlow.do",
            data: data,
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify("Path Deleted successfully", "custom");
                    reloadDt("tbl_jour_path_flow");
                } else {
                    $.notify(result.message, "custom");
                }
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }


    function proceedJourney() {
        if ($('#mcStatus').val() !== 'P')
        {
            openCampaignStep(8);
        } else {
            $("#frm_mcf_path").attr("action", "<%=request.getContextPath()%>/camp/campaignSummary.do?mcCampId=" + $("#mcCampId").val());
            $("#frm_mcf_path").submit();
        }

    }

    function goBackWard() {
        if ($('#mcStatus').val() !== 'P')
        {
            openCampaignStep(6);
        } else {
            $("#frm_mcf_path").attr("action", "<%=request.getContextPath()%>/camp/openCampaignFilter.do?mcCampId=" + $("#mcCampId").val());
            $("#frm_mcf_path").submit();
        }

    }
</script>