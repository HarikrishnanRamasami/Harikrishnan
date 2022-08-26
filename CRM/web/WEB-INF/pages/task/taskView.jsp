<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : taskView
    Created on : Aug 28, 2018, 1:22:14 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="modal fade" id="taskview_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" style="display: inline-block;"><s:text name="lbl.task.head.view.task"/>&nbsp;<button class="btn save-btn" id="btn_call_cust" data-phone="" data-id=""><i class="fa fa-phone-square"></i>&nbsp;<span id="btn_call_cust_no"></span></button></h4>
                <button class="close-btn btn pull-right" id="btn_view_close">&#10006; <s:text name="btn.close"/></button>
            </div>
            <div class="modal-body" style="margin-top: 10px;">
                <div class="row">
                    <div class="col-md-12">
                        <s:form id="frm_task_attach" name="frm_task_attach">
                            <div class="form-fields clearfix">
                                <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                                    <s:textfield id="ctSubject" name="task.ctSubject"  cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.subject')}" readonly="true"/>
                                    <label class="textboxlabel"><s:text name="lbl.subject"/><span>*</span></label>
                                </div>
                            </div>
                            <div class="form-fields clearfix">
                                <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                                    <s:textarea name="task.ctMessage" id="ctMessage" title="%{getText('lbl.please.enter.message')}" class="textbox" readonly="true" cssStyle="height: 200px; resize: vertical;"/>
                                    <label class="textboxlabel"><s:text name="lbl.message"/><span>*</span></label>
                                </div>
                            </div>
                            <!--h4 class="modal-title">TASK ATTACHMENTS</h4-->
                            <div class="col-md-12" id="block_task_attach">
                                <table class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th><s:text name="lbl.common.srno"/>.</th>
                                            <th><s:text name="lbl.file.name"/></th>
                                            <th><s:text name="lbl.action"/></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </s:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("#btn_call_cust").on("click", function () {
            calltask($(this).data("phone"), $(this).data("id"));
        });
        
        $("#btn_view_close").on("click",function (){
            $("#taskview_modal_dialog").modal("hide");
        });
    });
    
    function viewTasks(id) {
        block('block_body');
        var mode = "view";
        var data = {"task.ctId": id, "operation": mode};
        var DmsData = APP_CONFIG.context + "/LoadDmsDocs.do?docInfoBean.cdiTransId=" + id + "&docInfoBean.cdiDocType=CRM_TASKS&companyCode=${company}";
        data["operation"] = mode;
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openTaskEntryCloseForm.do",
            data: data,
            success: function (result) {
                $("#frm_task_attach #ctSubject").val(result.task.ctSubject);
                $("#frm_task_attach #ctMessage").val(result.task.ctMessage);
                if(result.task.ctFlex01) {
                    $("#taskview_modal_dialog #btn_call_cust_no").text(result.task.ctFlex01);
                    $("#taskview_modal_dialog #btn_call_cust").data("id", result.task.ctId);
                    $("#taskview_modal_dialog #btn_call_cust").data("phone", result.task.ctFlex01);
                    $("#btn_call_cust").show();
                } else {
                    $("#btn_call_cust").hide();
                }
                $("#frm_task_attach #block_task_attach").load(DmsData);
                <%--$("#frm_task_attach table > tbody").html("");
                var fileNames = result.task.ctFlex05 ? result.task.ctFlex05 : "";
                $.each(fileNames.split(","), function (i, o) {
                    var files = o.split(";")
                    if (files && files.length > 1) {
                        $("#frm_task_attach table > tbody").append("<tr><td>" + (i + 1) + "</td><td>" + files[1] + "</td><td><i class=\"fa fa-download\" onclick=\"downloadAttachment('" + id + "', '" + files[0] + "')\"></i><i class=\"fa fa-eye\" onclick=\"viewDmsFile('" + id + "', '" + files[0] + "')\"></i></td></tr>");
                    }
                });--%>
                $("#taskview_modal_dialog").modal("show");
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }
    
    function calltask(mobile, id) {
        <s:if test='%{!"N".equals(#session.USER_INFO.ameyoChannel) && #session.USER_INFO.agentType == 1}'>
            doDial(mobile);
            openTaskClForm(mobile, id);
        </s:if>
        <s:else>
        var data = {"customer.mobileNo": mobile};
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/callCustomer.do",
            data: data,
            async: true,
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify(mobile + " calling...", "custom");
                    openTaskClForm(mobile, id);
                } else {
                    $.notify("CTI not configured", "custom");
                }
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
            }
        });
        </s:else>
    }
</script>