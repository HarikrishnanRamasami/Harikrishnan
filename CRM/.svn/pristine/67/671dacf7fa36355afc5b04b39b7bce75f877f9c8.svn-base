<%-- 
    Document   : taskAllocationForm
    Created on : 21 Sep, 2017, 10:41:25 AM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="qa.com.qic.anoud.vo.KeyValue"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<style>
    .delete {
        position:absolute;
        top:0;
        right:0;
    }
</style>
<div class="card">
    <div class="row">
        <div class="col-md-12">
            <div class="popup-title">
                <h4><s:text name="lbl.task.allocation.form"/></h4>
            </div>
        </div> 
    </div>
    <div class="row">
        <div class="col-md-12">
            <div id="msg_taskallocate" class="alert alert-danger" style="display: none;"></div>
            <s:form id="frm_taskallocate" name="frm_taskallocate" method="post" theme="simple">
                <s:hidden name ="operation"/>
                <div class="row" style="margin-top:25px;margin-left:3px !important"> 
                    <div class=" form-group col-md-2">
                        <label><s:text name="lbl.tasks"/> <span>*</span></label>
                    </div>
                    <div class="col-md-6 form-group">
                        <s:select name="mCrmAgentsTask.catTaskCatg"  id="catTaskCatg" list="taskList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control" onchange="loadDropDown(this.value, 'catTaskSubCatg1');" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.category')}"/>
                    </div>
                </div>
                <div class="row" style="margin-top:25px;margin-left:3px !important"> 
                    <div class=" form-group col-md-2">
                        <label><s:text name="lbl.sub.tasks"/> <span>*</span></label>
                    </div>
                    <div class="col-md-6 form-group">
                        <s:select name="mCrmAgentsTask.catTaskSubCatg" id="catTaskSubCatg1" list="aaData" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.sub.category')}" onchange="loadTaskAllocation();"/>
                    </div>
                </div>
                <s:hidden name="mCrmAgentsTask.userType" id="userType"/>
                <style>
                    #btn_add_task {
                        display: block;
                        float: right;
                        margin: 0 10px 10px 0;
                    }
                </style>
                <div class="row" style="margin-top:25px;"> 
                    <div class="col-md-12 form-group">
                        <div id="div_tasks_tbl">
                            <button type="button" class="btn btn-warning leads-tab" id="btn_add_task" onclick="addRow();"><s:text name="btn.add.agent"/></button>
                            <table class="table table-custom table-bordered table-responsive nowrap nolink" id="task_tbl" name ="task_tbl" style="display: block;" role="grid">
                                <thead>
                                    <tr>
                                        <th width="8%" id="catAgentId"><s:text name="lbl.agent.id"/></th>
                                        <th width="8%" id="catAllocRatio"><s:text name="lbl.ratio"/> (%)</th>
                                        <th width="3%" id="Delet"><s:text name="lbl.action"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div> 
                </div>
            </s:form>
            <div style="float:left; margin-left: 225px" class="">
                <button class="save-btn btn btn-primary" id="btn_task_alloc_save">&#10004;<s:text name="btn.save"/></button>
                <button class="close-btn btn" id="btn_crmsms_close"data-dismiss="modal">&#10006;<s:text name="btn.close"/></button>
            </div>
        </div>
    </div>  
</div>
</div>
<script>
    $(document).ready(function () {
        $(window).load(loadTaskAllocation());
        $('[data-toggle="tooltip"]').tooltip();
        var form = $('#frm_taskallocate').serialize();
        $('#frm_taskallocate').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            rules: {
                "mCrmAgentsTask.catTaskCatg": {
                    required: true
                },
                "mCrmAgentsTask.catTaskSubCatg": {
                    required: true
                },
                "catAgentTempId": {
                    required: true
                },
                "catAllocTempRatio": {
                    required: true
                },
            },
            messages: {
                "mCrmAgentsTask.catTaskCatg": {
                    required: "Please Select Category"
                },
                "mCrmAgentsTask.catTaskSubCatg": {
                    required: "Please Select Sub Tasks"
                },
                "catAgentTempId": {
                    required: "Please Select Assigned To"
                },
                "catAllocTempRatio": {
                    required: "Please Enter Ratio"
                }
            },
            highlight: function (element) {
                $(element).removeClass('error');
            },
            showErrors: function (errorMap, errorList) {
                if (errorList.length)
                {
                    var s = errorList.shift();
                    var n = [];
                    n.push(s);
                    this.errorList = n;
                }
                if (this.numberOfInvalids())
                    this.defaultShowErrors();
                else
                    $("#msg_taskallocate").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_taskallocate").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_taskallocate").show();
                else
                    $("#msg_taskallocate").hide();
            }
        });
        $("#btn_task_alloc_save").on("click", function () {
            var type = $("#userType").val();
            if (!$('#frm_taskallocate').valid() && type !== "U") {
                return false;
            }
            var ratioMax = 0;
            $('.ratio1').each(function (i, obj) {
                ratioMax += Number(obj.value);
            });
            if (ratioMax > 100 || ratioMax < 100 && type !== "U") {
                alert("Please Enter ratio Equal to 100")
                return false;
            }
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveTaskAllocateEntryForm.do",
                data: $("#frm_taskallocate").serialize(),
                success: function (result) {
                    if(result.messageType === "S"){
                    $('#plugin_modal_dialog').modal('hide');
                    $.notify(" Task Allocation saved successfully", "custom");
                    // $("#tbl_task_pending").DataTable().ajax.url(getCrmDataTableUrl()).load();
                    reloadDt("tbl_task_pending");
                    $("#search_allocate").click();
                }else{
                    $.notify("Please Add Agent", "custom");
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
    });
    function getCrmDataTableUrl() {
        return APP_CONFIG.context + "/loadTaskAllocateEntryList.do?";
    }
    function loadDropDown(val, id) {
        $("#" + id).html("<option value=''>--Select-</option>");
        block('block_body');
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/loadTaskAllocateEntryCatList.do",
            data: {"mCrmAgentsTask.catTaskCatg": val, "company": $("#company").val()},
            success: function (data) {
                var opt = '<option value="">--Select-</option>';
                $.each(data.aaData, function (i, d) {
                    opt += '<option value="' + d.key + '">' + d.value + '</option>';
                });
                $('#' + id).html(opt);
                // $('#' + id).selectpicker('refresh');
            },
            complete: function () {
                unblock('block_body');

            }
        });
    }
    function loadTaskAllocation() {
        var userType = $("#userType").val();
        block('block_body');
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/loadTaskAllocateEntryList.do?",
            data: $("#frm_taskallocate").serialize(),
            async: true,
            success: function (response) {
                var data = "";
                $.each(response.aaData, function (i, o) {
                    if (userType === "U") {
                        if (o.value === null) {
                            o.value = "";
                        }
                        data += ("<tr id='catAgentIdRow_" + i + "'><td><div class=\"col-sm-40\"><select name=\"catAgentTempId\" class =\"form-control sel\" id=\"catAgentIdDis_" + i + "\" disabled><option value =" + o.key + ">" + o.info + "</option></select></div></td><td><div class=\"col-sm-40\"><input type=\"textfield\" name=\"catAllocTempRatio\"  class =\"ratio1 form-control\"\  readonly = \"true\"n\
                                   </div></td><td><button type=\"button\" class = \"remove btn btn btn-danger waves-effect\">Delete</button><input type = \"hidden\" name =\"catAgentTempId\" id=\"catAgentHidId_" + i + "\" value = '" + o.key + "' /></td>\n\
                                    </tr>");
                    } else {

                        data += ("<tr id='catAgentIdRow_" + i + "'><td><div class=\"col-sm-40\"><select name=\"catAgentTempId\" class =\"form-control sel\" id=\"catAgentIdDis_" + i + "\" disabled><option value =" + o.key + ">" + o.info + "</option></select></div></td><td><div class=\"col-sm-40\"><input type=\"textfield\" name=\"catAllocTempRatio\"  class =\"ratio1 form-control\" value =" + o.value + "\n\
                                   </div></td><td><button type=\"button\" class = \"remove btn btn btn-danger waves-effect\">Delete</button><input type = \"hidden\" name =\"catAgentTempId\" id=\"catAgentHidId_" + i + "\" value = '" + o.key + "' /></td>\n\
                                    </tr>");
                    }
                    $("#task_tbl > tbody").html(data);
                });
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
        $(document).on("click", "button.remove", function () {
            $(this).parent().parent().remove();
        });
    }


    function addRow() {
        var userType = $("#userType").val();
        var table = $('#task_tbl > tbody');
        var rowCount = -1;
        if ($('#task_tbl tr:last').attr('id')) {
            console.log("TEst");
            if ($('#task_tbl tr:last').attr('id').indexOf("_") !== -1) {
                rowCount = $('#task_tbl tr:last').attr('id').split("_")[1];
            }
        }
        if (rowCount !== -1 && !$('#catAgentHidId_' + rowCount)) {
            //console.log(" Forming Hidden");
            var prevRowVal = $("select#catAgentId_" + (rowCount)).val();
            var hiddenTxt = $("<input  type=\"hidden\" name=\"catAgentTempId\"value=\"" + prevRowVal + "\"/>");
            var prevRow = $("#catAgentIdRow_" + rowCount);
            prevRow.append(hiddenTxt);
            $("select#catAgentId_" + (rowCount)).attr("disabled", "disabled");
        }/*else{
         console.log("Hidden already Exists");
         }*/
        var optVal = [];
        $("select[name^='catAgentTempId']").each(function () {
            optVal.push($(this).val());
        });
        $("select[name^='catAgentTempIdDis']").each(function () {
            optVal.push($(this).val());
        });
        var s = $("<select  name=\"catAgentTempId\" id=\"catAgentId_" + (Number(rowCount) + 1) + "\"/>");
        var opts = [];
    <%
        List<KeyValue> keyValueBOs = (ArrayList<KeyValue>) request.getAttribute("assignList");
        if (keyValueBOs != null) {
            for (KeyValue keyValueBO : keyValueBOs) {
    %>
        opts.push($("<option />", {value: "<%=keyValueBO.getKey()%>", text: "<%=keyValueBO.getValue()%>"}));
        s.append(opts);
    <%
            }
        }
    %>
        var divSel = $("<div/>");
        var span1Txt = $("<span/>");
        //var detailTxt = $("<input type=\"text\" name=\"agentRatioList.catAgentId[" + (Number(rowCount) + 1) + "].description\"/>");
        if (userType === "U") {
            var valueTxt = $("<input type=\"textfield\"name=\"catAllocTempRatio\"id=\"catAllocTemp\"/ readonly = \"true\">");
        }
        else {
            var valueTxt = $("<input type=\"textfield\"name=\"catAllocTempRatio\"id=\"catAllocTemp\"/>");
        }
        valueTxt.prop('maxlength', '10');
        var btnCell = $("<button  type=\"button\">Delete </button>");
        // var imageCont = $("<i class=\"fa fa-times\" aria-hidden=\"true\"></i>")
        var tr = $('<tr/>');
        tr.attr('id', 'catAgentIdRow_' + (Number(rowCount) + 1))
        tr.attr('style', 'display:none;')
        var td1 = $('<td/>');
        var td2 = $('<td/>');
        var td3 = $('<td/>');
        var tdSel = s.attr("class", "form-control sel");
        td1.append(divSel.attr("class", "col-sm-40").append(tdSel));
        tr.append(td1);
        td2.append(span1Txt.attr("class", "col-sm-40").append(valueTxt.attr("class", "ratio1 form-control")));
        tr.append(td2);
        td3.append(btnCell.attr("class", "remove btn btn btn-danger waves-effect"));
        tr.append(td3);
        table.append(tr);

        $("select#catAgentId_" + (Number(rowCount) + 1) + " option").each(function (i, obj) {
            var selOpt = $(this);
            $.each(optVal, function (index, value) {
                if (selOpt.val() === value) {
                    selOpt.remove();
                    return false;
                }
            });
        });
        if ($("select#catAgentId_" + (Number(rowCount) + 1) + " option").length === 0) {
            alert("All Types are added");
            $('#catAgentIdRow_' + (Number(rowCount) + 1)).remove();
            return false;
        } else {
            $('#catAgentIdRow_' + (Number(rowCount) + 1)).show();
        }
        $("select#catAgentId_" + (Number(rowCount) + 1) + " option:first").attr('selected', 'selected');

        // $('.selectpicker1').selectpicker('refresh');
        $(document).on("click", "button.remove", function () {
            $(this).parent().parent().remove();
        });
    }


</script>