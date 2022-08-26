<%-- 
    Document   : taskAllocation
    Created on : 21 Sep, 2017, 10:41:09 AM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style>
    .bor6 {
        margin-top: 5px;
        margin-right: -59px;
        margin-left: 64px;
    }
    .h3{
        text-transform: uppercase;
        margin: 6px;
        padding: 15px 20px;
        font-family: ProximaNova-Bold;
        font-size: 15px;
        text-align: left;
        color: #1f252e;
    }
</style>
<div class="col-md-12 right-pad">
    <div class="dash-leads" style="border-top:0!important">
        <div id="msg_categoryError" class="alert alert-danger" style="display: none;"></div>
        <div class="my-bord">
            <h3><s:text name="lbl.tasksorsubTasks"/></h3>
        </div>
        <div class="my-bord bor2">                
            <div class="col-md-9 Tmar">
                <s:form  class="form-inline" id="frm_search" name="frm_search" method="post" theme="simple">
                    <input type="hidden" name="mCrmAgentsTask.userType" id = "hid_userType" />
                    <label><s:text name="lbl.category"/></label>
                    <div class="input-group">
                        <select name="mCrmAgentsTask.catTaskCatg" id="catTaskCatg" class="form-control"  onchange="loadDropDown(this.value, 'catTaskSubCatg');">
                            <option value="">--Select--</option>       
                            <s:iterator value="taskList" status="stat">
                                <option value="<s:property escapeJavaScript="true"  value="key"/>" data-info4="<s:property escapeJavaScript="true"  value="info4"/>"><s:property  value="value"/></option>
                            </s:iterator>
                        </select>
                        <span class="input-group-addon" id="program_span" onclick="openCatgForm('category');" style="cursor: pointer; display: table-cell;">
                            <span class="glyphicon">+</span>
                        </span>
                    </div>
                    <label><s:text name="lbl.sub.category"/></label>
                    <div class="input-group">
                        <s:select name="mCrmAgentsTask.catTaskSubCatg" id="catTaskSubCatg" list="subtaskList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control"/>
                        <span class="input-group-addon" id="program_span" onclick="openCatgForm('subcategory');" style="cursor: pointer; display: table-cell;">
                            <span class="glyphicon">+</span>
                        </span>					  
                    </div>
                    <div class="form-group padR">
                        <button type="button" id="search_allocate" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
                    </div>
                </s:form>
            </div>
            <div class="col-sm-12 col-md-3 board-icons1 Rbtn" id="btn_upload" style="margin-top: 1px;padding:6px;">
                <button class="btn btn-primary tmargin cbtn" onclick="UploadMyTaskFile();"><s:text name="lbl.upload"/></button>
            </div> 
        </div>
    </div>
    <div class="col-md-6" style="border-top:0!important;display:none; margin-top: 20px; margin-left: -15px;" id="alloc_div">
        <div class="my-bord bor2">
            <div class="col-sm-12 col-md-12">               
                <div class="form-inline">
                    <div class="form-group padR"style="margin-left: -15px;">
                        <label><s:text name="lbl.task.allocation.by.type"/></label>
                        <s:select name="utype" id="userType" list="userTypeList" listKey="key" listValue="value" cssClass="form-control" onchange="loadRoleList(this.value, 'roleList');"/>	
                    </div>
                    <div class="form-group padR" id="div_role" style="display: none;">
                        <label><s:text name="lbl.roles"/></label>
                        <s:select name="urole" id="userRole" list="userRoleList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control"/>	
                    </div>
                    <div class="form-group padR" style="display: none;" id="div_save">
                        <button class="save-btn btn btn-primary" onclick="UpdateAppcodes();" style="margin-top: 4px;"><s:text name="btn.save"/></button>
                    </div>
                    <div class="form-group padR" style="margin-top: 1px;float: right;" id="div_add">
                        <button class="btn btn-warning tmargin cbtn" id="btn_add_task" onclick="showForm('add');" style="margin-top: 2px;"><s:text name="btn.add.edit"/> </button>
                    </div>
                </div>
            </div>
        </div>
        <table id="tbl_task_pending" class="table table-striped table-bordered display dataTable dtr-inline">
            <thead>
                <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                    <th width="8%"><s:text name="lbl.agent.name"/></th>
                    <th width="8%"><s:text name="lbl.ratio"/> (%)</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div class="col-md-6" style="border-top:0!important;display: none;" id="sla_div">
        <div class="my-bord">
            <div class="my-bord">
                <h3 class="h3"><s:text name="lbl.task.sla"/></h3>
                <div class="col-sm-12 col-md-3 board-icons1 Rbtn">
                    <button class="btn btn-warning tmargin cbtn" style="width: 84px; margin-top: 2px;"id="btn_add_task" onclick="showSLAForm('add');"><s:text name="btn.add.edit"/> </button>
                </div>
            </div>
        </div>
        <div class="my-bord bor2">
            <table id="tbl_task_sla" class="table table-striped table-bordered display dataTable dtr-inline">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                        <th width="8%"><s:text name="lbl.priority"/></th>
                        <th width="8%"><s:text name="lbl.sla.days"/></th>
                        <th width="8%"><s:text name="lbl.action"/></th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <div class="col-md-12 right-pad">
        <div class="dash-leads" style="border-top:0!important;display: none" id="rules_div">
            <div class="my-bord">
                <div class="my-bord bor2">
                    <h3><s:text name="lbl.task.rules"/></h3>
                    <button class="btn btn-warning tmargin cbtn" style="width: 84px;float: right; margin-top: 7px;" id="btn_add_task" onclick="showRulesForm('add');"><s:text name="btn.add.edit"/> </button>
                </div>
            </div>
            <table id="tbl_task_rules" class="table table-striped table-bordered display dataTable dtr-inline">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                        <th width="8%"><s:text name="lbl.filter"/></th>
                        <th width="8%"><s:text name="lbl.operator"/></th>
                        <th width="8%"><s:text name="lbl.value"/></th>
                        <th width="8%"><s:text name="lbl.action"/></th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <div class="my-bord bor2">
        <div class="col-md-6" style="border-top: 0 !important; display: none;"
             id="ds_div">
            <s:form class="form-inline" id="" name="" method="post"
                    theme="simple">
                <div class="form-group">
                    <label><s:text name="lbl.data.source"/></label>
                    <s:select id="source_id" name="" list="categoryList" listKey="key"
                              listValue="value" class="form-control"
                              style="padding-right: 150px;margin-top: 4px;" />
                </div>
                <div class="form-group padR">
                    <button type="button" id="btn_save_lead_to_task"
                            class="btn btn-primary tmargin cbtn" style="margin-top: 1px;">
                        <i class=""></i> <s:text name="btn.submit"/>
                    </button>
                </div>
            </s:form>
        </div>
    </div>
</div>
<script type="text/javascript">
        var utype = "U", urole = "";
        $(document)
                .ready(
                        function () {
                            $("#frm_ds").hide();

                            $('[data-toggle="tooltip"]').tooltip();
                            var taskAllocUrl = APP_CONFIG.context
                                    + "/loadTaskAllocateEntryList.do?"
                                    + $('#frm_search').serialize();
                            var slaUrl = APP_CONFIG.context
                                    + "/loadTaskAllocateSLAList.do?"
                                    + $('#frm_search').serialize();
                            var taskrulesUrl = APP_CONFIG.context
                                    + "/loadTaskAllocateRulesList.do?"
                                    + $('#frm_search').serialize();

                            $('#tbl_task_sla ,#tbl_task_rules').on('draw.dt',
                                    function () {
                                        $('[data-toggle="tooltip"]').tooltip();
                                    });

                            var tbl_task_pending = $("#tbl_task_pending")
                                    .DataTable(
                                            {
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
                                                    "url": taskAllocUrl,
                                                    "type": "POST",
                                                    "contentType": "application/json",
                                                    "dataSrc": "aaData"
                                                },
                                                columns: [{
                                                        data: "info"
                                                    }, {
                                                        data: "value"
                                                    }],
                                                dom: 'lftrpi',
                                                tableTools: {
                                                    "sRowSelect": "single",
                                                    "aButtons": []
                                                },
                                                order: [[0, "desc"]],
                                                initComplete: function () {
                                                    $("#tbl_task_pending tr td").css(
                                                            'cursor', 'default');
                                                }
                                            });

                            var tbl_task_sla = $("#tbl_task_sla")
                                    .DataTable(
                                            {
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
                                                    "url": slaUrl,
                                                    "type": "POST",
                                                    "contentType": "application/json",
                                                    "dataSrc": "aaData"
                                                },
                                                columns: [
                                                    {
                                                        data: "value"
                                                    },
                                                    {
                                                        data: "info"
                                                    },
                                                    {
                                                        data: "key",
                                                        render: function (data,
                                                                type, row, meta) {
                                                            return '<i class="fa fa-peancil fa-lg" title="Modify" data-toggle="tooltip" style="cursor:pointer;color:#418bca;" onclick="modifysla(\''
                                                                    + row.key
                                                                    + '\');"></i>'
                                                                    + '&nbsp;<i class="fa fa-trash fa-lg" title="delete" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="deletesla(\''
                                                                    + row.key
                                                                    + '\');"></i>';

                                                        }

                                                    }],
                                                dom: 'lftrpi',
                                                tableTools: {
                                                    "sRowSelect": "single",
                                                    "aButtons": []
                                                },
                                                order: [[0, "desc"]],
                                                initComplete: function () {
                                                         $("#tbl_task_sla tr td").css(
                                                            'cursor', 'default');
                                                }
                                            });

                            var tbl_task_rules = $("#tbl_task_rules")
                                    .DataTable(
                                            {
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
                                                    "url": taskrulesUrl,
                                                    "type": "POST",
                                                    "contentType": "application/json",
                                                    "dataSrc": "aaData"
                                                },
                                                columns: [
                                                    {
                                                        data: "value"
                                                    },
                                                    {
                                                        data: "info"
                                                    },
                                                    {
                                                        data: "info1"
                                                    },
                                                    {
                                                        data: "key",
                                                        render: function (data,
                                                                type, row, meta) {
                                                            /*if (row.info2 === '9') {
                                                                return '<i class="fa fa-trash fa-lg " title="delete" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="deleteRules(\''
                                                                        + row.key
                                                                        + '\');"></i>';
                                                            } else {*/
                                                                return '<i class="fa fa-pencil fa-lg" title="Modify" data-toggle="tooltip" style="cursor:pointer;color:#418bca;" onclick="modifyRules(\''
                                                                        + row.key
                                                                        + '\');"></i>'
                                                                        + '&nbsp;<i class="fa fa-trash fa-lg " title="delete" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="deleteRules(\''
                                                                        + row.key
                                                                        + '\');"></i>';
                                                           // }

                                                        }

                                                    }],
                                                dom: 'lftrpi',
                                                tableTools: {
                                                    "sRowSelect": "single",
                                                    "aButtons": []
                                                },
                                                order: [[0, "desc"]],
                                                initComplete: function () {
                                                     $("#tbl_task_rules tr td").css('cursor', 'default');
                                                }
                                            });

                        });
        $("#search_allocate").on(
                "click",
                function () {
                    if (($("#catTaskCatg").val() == "" || $("#catTaskCatg").val() == undefined)
                            || $("#catTaskSubCatg").val() == "" || $("#catTaskSubCatg").val() == undefined) {
                        $("#alloc_div").hide();
                        $("#sla_div").hide();
                        //$("#btn_upload").hide();
                        $("#msg_categoryError").html("Please Select Category and SubCategory").show();
                        setTimeout(function () {
                            $('#msg_categoryError').hide();
                        }, 2000);
                    } else {
                        $("#alloc_div").show();
                        $("#sla_div").show();
                        //$("#btn_upload").show();
                    }

                    var mcCode = $("#catTaskCatg").find(':selected').data('info4');
                    if (mcCode === 'Y') {
                        $("#rules_div").show();
                    } else {
                        $("#rules_div").hide();
                    }
                    var catg = $("#catTaskCatg").val();
                    var subcatgory = $("#catTaskSubCatg").val();
                    if (catg == 12 && subcatgory != "") {
                        //$("#frm_ds").show();

                        $("#ds_div").show();
                    } else {
                        $("#ds_div").hide();
                    }
                    $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/allocationDetails.do?"+ $('#frm_search').serialize(),
                success: function (result) {
                    console.log(result.utype + " : "+result.urole);
                    $('#userType').val(result.utype);
                    loadRoleList(result.utype);
                    $('#userRole').val(result.urole);
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');

                }
            });

                    var taskAllocUrl = APP_CONFIG.context
                            + "/loadTaskAllocateEntryList.do?"
                            + $('#frm_search').serialize();
                    var slaUrl = APP_CONFIG.context
                            + "/loadTaskAllocateSLAList.do?"
                            + $('#frm_search').serialize();
                    var taskrulesUrl = APP_CONFIG.context
                            + "/loadTaskAllocateRulesList.do?"
                            + $('#frm_search').serialize();
                    reloadDtByUrl("tbl_task_pending", taskAllocUrl);
                    
                    reloadDtByUrl("tbl_task_sla", slaUrl);
                    reloadDtByUrl("tbl_task_rules", taskrulesUrl);
                    
                });

        $("#btn_save_lead_to_task").on("click", function () {
            var srcId = $("#source_id").val();
            var subCat = $("#catTaskSubCatg").val();
            if (srcId == "" || srcId == null || srcId == undefined) {
                $.notify("DataSourc is empty", "custom");
                return false;
            }

            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveLeadToTask.do",
                data: {
                    "mCrmAgentsTask.catTaskSubCatg": subCat,
                    "mCrmAgentsTask.dataSouceId": srcId
                },
                success: function (result) {
                    console.log(result.messageType);
                    if (result.messageType === "S") {
                        $.notify("Allocated successfully", "custom");
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
        });
        function showForm() {
            block('block_body');
            $("#hid_userType").val($("#userType").val());
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/openTaskAllocateEntryForm.do",
                data: $("#frm_search").serialize(),
                success: function (result) {
                    $('#plugin_modal_dialog .modal-dialog').removeClass(
                            "modal-mg modal-mm modal-lg modal-sm").addClass(
                            "modal-mm");
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
        function loadDropDown(val, id) {
            $("#" + id).html("<option value=''>--Select-</option>");
            var mcCode = $("#catTaskCatg").find(':selected').data('info4');
            /*if(mcCode === 'Y'){
             $("#rules_div").show();
             }else{
             $("#rules_div").hide();
             }*/
            block('block_body');
            $
                    .ajax({
                    type: "POST",
                        url: "<%=request.getContextPath()%>/loadTaskAllocateEntryCatList.do",
                        data: {"mCrmAgentsTask.catTaskCatg": val, "company": $("#company").val()},
                        success: function (data) {
                            var opt = '<option value="">--Select-</option>';
                            $.each(data.aaData, function (i, d) {
                                opt += '<option value="' + d.key + '">' + d.value + '</option>';
                            });
                            $('#' + id).html(opt);
                            //$('#' + id).selectpicker('refresh');
                        },
                        complete: function () {
                            unblock('block_body');
                            // $("#search_allocate").click();
                        }
                    });
        }


        function openCatgForm(type) {
            var catg;
            if (type === 'subcategory') {
                catg = $("#catTaskCatg").val();
                if (catg === "") {
                    alert("Please Select Category");
                    return fasle;
                }
            }
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/openTaskCategoryForm.do?operation=" + type + "&flex3=" + catg,
                data: $("#frm_search").serialize(),
                success: function (result) {
                    $('#plugin_modal_dialog .modal-dialog').removeClass("modal-mg modal-mm modal-lg modal-sm").addClass("modal-mm");
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

        function showSLAForm(operation) {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/loadTaskAllocateSLAForm.do?operation=" + operation,
                data: $("#frm_search").serialize(),
                success: function (result) {
                    $('#plugin_modal_dialog .modal-dialog').removeClass("modal-mg modal-mm modal-lg modal-sm").addClass("modal-mm");
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
        function modifysla(id) {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/loadTaskAllocateSLAForm.do?operation=edit&flex3=" + id,
                data: $("#frm_search").serialize(),
                success: function (result) {
                    $('#plugin_modal_dialog .modal-dialog').removeClass("modal-mg modal-mm modal-lg modal-sm").addClass("modal-mm");
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
        function deletesla(id) {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveTaskSLAForm.do?operation=delete&flex3=" + id,
                data: {},
                success: function (result) {
                    if (result.messageType === "S") {
                        $.notify("SLA deleted successfully", "custom");
                        reloadDt("tbl_task_sla");
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

        function showRulesForm(operation) {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/loadTaskAllocateRulesForm.do?operation=" + operation,
                data: $("#frm_search").serialize(),
                success: function (result) {
                    $('#plugin_modal_dialog .modal-dialog').removeClass("modal-mg modal-mm modal-lg modal-sm").addClass("modal-mm");
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

        function modifyRules(id) {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/loadTaskAllocateRulesForm.do?operation=edit&flex5=" + id,
                data: $("#frm_search").serialize(),
                success: function (result) {
                    $('#plugin_modal_dialog .modal-dialog').removeClass("modal-mg modal-mm modal-lg modal-sm").addClass("modal-mm");
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

        function deleteRules(id) {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveTaskRulesForm.do?operation=delete&flex5=" + id,
                data: {},
                success: function (result) {
                    if (result.messageType === "S") {
                        $.notify("Rules deleted successfully", "custom");
                        reloadDt("tbl_task_rules");
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
        function UploadMyTaskFile() {
            block('block_body');
            $.ajax({
                type: "POST",
                data: {},
                url: APP_CONFIG.context + "/openUploadTaskEntryForm.do",
                success: function (result) {
                    $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-mm modal-sm").addClass("modal-lg");
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
        function loadRoleList(val) {
            if (val === "R") {
                $("#div_role").show();
                $("#div_add").hide();
                $("#div_save").show();
            } else if (val === "U") {
                $("#div_role").hide();
                $("#div_add").show();
                $("#div_save").hide();
            } else if(val=== "P"){
                $("#div_role").hide();
                $("#div_add").show();
                $("#div_save").hide(); 
            }
        }
        function UpdateAppcodes() {
            var category = $("#catTaskCatg").val();
            var subcategory = $("#catTaskSubCatg").val();
            var masterCode = $("#userType").val();
            var role = $("#userRole").val();
            var data = {"flex1": masterCode, "flex2": role, "flex3": category, "flex4": subcategory};
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/updateAppCodes.do",
                data: data,
                success: function (result) {
                    if (result.messageType === "S") {
                        $.notify("Appcodes Updated successfully", "custom");
                        $("#search_allocate").trigger("click");
                    } else {
                        $.notify(result.message, "custom");
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                lete: function () {
                    unblock('block_body');
                }
            });
        }
    </script>

