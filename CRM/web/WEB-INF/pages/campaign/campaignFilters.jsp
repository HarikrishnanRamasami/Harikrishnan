<%-- 
    Document   : campaignFilters
    Created on : 18 Sep, 2019, 5:27:50 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 right-pad" id="block_body">
    <s:include value="/WEB-INF/pages/campaign/campaignHeader.jsp?stepNo=6"></s:include>
    <s:hidden name="mcCampId" id="mcCampId"/>
    <s:hidden name="campaign.mcStatus" id="mcStatus"/>
    <div class="dash-leads" style="border-top:0!important">
        <!--div class="my-bord">
            <h3>Campaign Path Filters</h3>
        </div-->
        <s:if test='"P".equals(campaign.mcStatus)'>
        <div class="col-sm-12 col-md-3 board-icons1 Rbtn">
            <button type="button" class="btn btn-success waves-effect" style="width: 50px; margin-top: -8px" onclick="addPathFilter('add');" data-dismiss="modal" aria-hidden="true" data-toggle="tooltip" data-placement="top" ><s:text name="btn.add"/></button>
        </div>
        </s:if>
        <div class="my-bord bor2">
            <div id="datamap_error_div" class="alert alert-danger mb-10" style="display:none"></div>
            <table id="tbl_data_filter" class="table table-striped table-bordered display dataTable dtr-inline">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                        <th style="width: 10%;" class="text-center"><s:text name="lbl.id"/></th>
                        <th style="width: 10%;" class="text-center"><s:text name="lbl.common.name"/></th>
                        <th style="width: 10%;" class="text-center"><s:text name="lbl.common.head.sub.actions"/></th>
                    </tr>
                </thead>
                <tbody>	   
                </tbody>
            </table>
        </div>
        <div id="filter_params">
        </div>
        <div class="row b-t mt-10" id="div_btns">
            <div class="col-md-12 form-group center mt-30">
                <button type="button" class="btn btn-primary leads-tab" onclick="goBackWard();"> <s:text name="btn.back"/> <i class="fa fa-arrow-circle-left"></i></button>
                <button type="button" class="btn btn-primary leads-tab" style="float: right" onclick="proceedJourney();"> <s:text name="btn.proceed"/> <i class="fa fa-arrow-circle-right"></i></button>
            </div>
        </div>
    </div>

</div>
<div class="modal fade" id="data_filter" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog  modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><s:text name="lbl.campaign.head.campaign.filter"/></h4>
                <div style="margin-top: -31px; float: right;">
                    <button class="save-btn btn btn-primary" onclick="saveCampFilter();">&#10004;<s:text name="btn.save"/></button>
                    <button class="close-btn btn" data-dismiss="modal">&#10006; <s:text name="btn.close"/></button>
                </div>
            </div>
            <div class="body" style="padding: 7px;">
                <s:form id="frm_mcf_camp" name="frm_mcf_camp">
                    <s:hidden name="campaignFilter.mcfCampId" id="mcfCampId"/>
                    <s:hidden name="oper" id="oper" />
                    <s:hidden name="campaignFilter.mcfFilterId" id="mcfFilterId"/>
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <label><s:text name="lbl.name"/>: <span>*</span></label>
                            <div class="form-group">
                                <div class="form-line">
                                    <s:textfield name="campaignFilter.mcfFilterName" id="mcfFilterName" cssClass="form-control" required="true" title="%{getText('lbl.campaign.enter.filter.name')}"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </s:form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="del_filter" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog  modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title custom-font"><s:text name="lbl.campaign.head.delete.campaign.filter"/></h3>
            </div>
            <div class="modal-body">        
                <div class="alert alert-danger" id="msg_delete" style="display: none;"></div>
                <s:form name="frm_del_filter" id="frm_del_filter">
                    <div class="col-md-12 no-padding margin-bottom-10">
                        <s:hidden name="campaignFilter.mcfFilterId" id="mcfFilterId"></s:hidden>
                            <div class="col-md-12">
                            <s:text name="lbl.common.are.you.sure.to.delete"/> <span id="common_del_name"><s:text name="lbl.common.record"/></span>?  
                            </div>
                        </div>
                </s:form>
                <div align="center" class="padding-vertical-10">
                    <button id="btn_common_del_save" type="button" class="btn btn-blue" onclick="deleteCampFilter()">
                        <i class="fa fa-check"></i>&nbsp;<s:text name="btn.yes"/>
                    </button>
                    <button type="button" class="btn btn-blue" data-dismiss="modal" aria-hidden="true" onclick="closepopUp();">
                        <i class="fa fa-close"></i> &nbsp;<s:text name="btn.close"/>
                    </button>
                </div>
            </div>

        </div>
    </div>
</div>
<div class="modal fade" id="del_filter_param" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog  modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title custom-font"><s:text name="lbl.campaign.head.delete.campaign.filter.param"/></h3>
            </div>
            <div class="modal-body">        
                <div class="alert alert-danger" id="msg_delete" style="display: none;"></div>
                <s:form name="frm_del_filter_param" id="frm_del_filter_param">
                    <div class="col-md-12 no-padding margin-bottom-10">
                        <s:hidden name="campaignFilter.mcfpSno" id="mcfpSno"></s:hidden>
                            <div class="col-md-12">
                            <s:text name="lbl.common.are.you.sure.to.delete"/> <span id="common_del_name"><s:text name="lbl.common.record"/></span>?  
                            </div>
                        </div>
                </s:form>
                <div align="center" class="padding-vertical-10">
                    <button id="btn_common_del_save" type="button" class="btn btn-blue" onclick="deleteCampFilterParam()">
                        <i class="fa fa-check"></i>&nbsp;<s:text name="btn.yes"/>
                    </button>
                    <button type="button" class="btn btn-blue" data-dismiss="modal" aria-hidden="true" onclick="closepopUp();">
                        <i class="fa fa-close"></i> &nbsp;<s:text name="btn.close"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<div >
    <s:form name="camp_filt" id="camp_filt"/>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        // $("#camp_contacts").hide();
        var url = APP_CONFIG.context + "/camp/loadCampaignFilter.do?mcCampId=" + $("#mcCampId").val();
        var table = $("#tbl_data_filter").DataTable({
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
                {data: "mcfFilterId"},
                {data: "mcfFilterName"},
                {data: "mcfFilterId",
                    render: function (data, type, row, meta) {
                        if($("#mcStatus").val() === "P") {
                            return  '<center><i class="fa fa-pencil action-icon" onclick="javascript: editPathFilter(\'edit\',\'' + row.mcfFilterId + '\');" style="color: #418bca;" title="Edit"></i>&nbsp;&nbsp' +
                                '<i class="fa fa-filter action-icon" onclick="javascript: openFilterParam(\'' + row.mcfFilterId + '\');" style="color: #418bca;" title="Filter"></i>&nbsp;&nbsp' +
                                '<i class="fa fa-trash action-icon" onclick="javascript: deletePathFilter(\'' + row.mcfFilterId + '\');" style="color: #418bca;" title="Delete"></i>';
                        } else {
                            return '<i class="fa fa-filter action-icon" onclick="javascript: openFilterParam(\'' + row.mcfFilterId + '\');" style="color: #418bca;" title="Filter"></i>&nbsp;&nbsp';
                        }
                        
                    }

                },
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
        $('#tbl_data_filter tbody').on('click', 'tr', function () {
            var code = table.row(this).data().mcfFilterId;
            openFilterParam(code);
        });



    });

    function addPathFilter(mode) {
        $("#data_filter").modal("show");
        $("#frm_mcf_camp #mcfFilterName").val("");
        $("#frm_mcf_camp #mcfCampId").val($("#mcCampId").val());
        $("#frm_mcf_camp #mcfFilterId").val("");
        $("#frm_mcf_camp #oper").val(oper);
        $("#oper").val(mode);
    }

    function editPathFilter(oper, code) {
        var data = {"company": "<s:property value="#session.USER_INFO.companyCode"/>", "campaignFilter.mcfFilterId": code};
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/openCampFilterParamForm.do?oper=" + oper,
            data: data,
            success: function (result) {
                console.log(result.campaignFilter);
                $("#frm_mcf_camp #mcfFilterName").val(result.campaignFilter.mcfFilterName);
                $("#frm_mcf_camp #mcfCampId").val(result.campaignFilter.mcfCampId);
                $("#frm_mcf_camp #mcfFilterId").val(code);
                $("#frm_mcf_camp #oper").val(oper);
                $("#data_filter").modal("show");
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }
    function openFilterParam(id) {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/openCampFilterParam.do",
            data: {"mcCampId": "${mcCampId}", "campaignFilter.mcfFilterId": id},
            success: function (result) {
                $("#filter_params").empty().show();
                $("#filter_params").empty().html(result);
                $("#camp_filter_param #mcfpFilterId").val(id);
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function saveCampFilter() {
        if ($.trim($("#frm_mcf_camp #mcfFilterName").val()) === "") {
            $.notify("Enter Filter Name", "custom");
            return;
        }
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/saveCampaignFilter.do",
            data: $("#frm_mcf_camp").serialize(),
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify("Saved Successfully", "custom");
                    $("#data_filter").modal("hide");
                    reloadDt("tbl_data_filter");
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

    function deletePathFilter(id) {
        $("#frm_del_filter #mcfFilterId").val(id);
        $("#del_filter").modal("show");
    }

    function deleteCampFilter() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/deleteCampaignFilter.do",
            data: $("#frm_del_filter").serialize(),
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify("Saved Successfully", "custom");
                    $("#del_filter").modal("hide");
                    reloadDt("tbl_data_filter");
                } else {
                    $.notify("Unable to Process", "custom");
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
    function deleteCampFilterParam() {
        deleteFilterParam($("#mcfpSno").val());
        $("#del_filter_param").modal("hide");
    }
    function proceedJourney() {
        if ($('#mcStatus').val() !== 'P')
        {
            openCampaignStep(7);
        } else {
            $("#camp_filt").attr("action", "<%=request.getContextPath()%>/camp/LoadCampaignJourney.do?mcCampId=" + $("#mcCampId").val());
            $("#camp_filt").submit()
        }

    }
    function goBackWard() {
        if ($('#mcStatus').val() !== 'P')
        {
            openCampaignStep(5);
        } else {
            $("#camp_filt").attr("action", "<%=request.getContextPath()%>/camp/loadCampaignTemplateData.do?mcCampId=" + $("#mcCampId").val());
            $("#camp_filt").submit()
        }

    }
</script>