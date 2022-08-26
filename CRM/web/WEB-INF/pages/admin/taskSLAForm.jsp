<%-- 
    Document   : taskSLAForm
    Created on : 4 Nov, 2017, 4:00:18 PM
    Author     : palani.rajan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<div class="card">
    <div class="row">
        <div class="col-md-12">
            <div class="popup-title">
                <h4><s:text name="lbl.sla.form"/></h4>
            </div>
        </div> 
    </div>
    <div class="row">
        <div class="col-md-12">
            <div id="msg_sla" class="alert alert-danger" style="display: none;"></div>
            <s:form id="frm_sla" name="frm_sla" method="post" theme="simple">
                  <s:hidden name ="operation"/>
                  <s:hidden name ="mCrmAgentsTask.catTaskCatg"/>
                  <s:hidden name ="mCrmAgentsTask.catTaskSubCatg"/>
                  <s:hidden name="flex3"/>
                <div class="row" style="margin-top:25px;margin-left:3px !important"> 
                    <div class=" form-group col-md-2">
                        <label><s:text name="lbl.priority"/> <span>*</span></label>
                    </div>
                    <div class="col-md-6 form-group">
                        <s:if test='"edit".equals(operation)'>
                             <s:hidden name ="flex1"/>
                            <s:select name="flex1" id="priority" list="priorityList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" disabled="true" cssClass="form-control"/>
                        </s:if>
                        <s:else>
                             <s:select name="flex1" id="priority" list="priorityList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control"/>
                        </s:else>                       
                    </div>
                </div>
                <div class="row" style="margin-top:25px;margin-left:3px !important"> 
                    <div class=" form-group col-md-2">
                        <label><s:text name="lbl.sla.days"/></label>
                    </div>
                    <div class="col-md-6 form-group">
                        <s:textfield name="flex2"  id="slyDays"  cssClass="form-control numbers"/>
                    </div>
                </div>
            </s:form>
            <div style="float:left; margin-left: 225px" class="">
                <button class="save-btn btn btn-primary" id="btn_task_sla_save" onclick="return saveSlaForm()">&#10004;<s:text name="btn.save"/></button>
                <button class="close-btn btn" id="btn_crmsms_close"data-dismiss="modal">&#10006;<s:text name="btn.close"/></button>
            </div>
        </div>
    </div>  
</div>
<script type="text/javascript">
     $( document ).ready(function() {
            $('.numbers').on('input', function (event) { 
                    this.value = this.value.replace(/[^0-9]/g, '');
           }); 
        });
    function saveSlaForm(){
        block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveTaskSLAForm.do",
                data: $("#frm_sla").serialize(),
                success: function (result) {
                    if(result.messageType==="S"){
                    $('#plugin_modal_dialog').modal('hide');
                    $.notify("SLA saved successfully", "custom");
                    reloadDt("tbl_task_sla");
                }else{
                    $("#msg_sla").html(result.message).show();
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
</script>