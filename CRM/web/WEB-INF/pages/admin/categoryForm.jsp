<%-- 
    Document   : categoryForm
    Created on : 4 Nov, 2017, 12:27:00 PM
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
                <h4><s:if test='"category".equals(operation)'><s:text name="lbl.category"/></s:if><s:else><s:text name="lbl.sub.category"/></s:else> <s:text name="lbl.form"/><</h4>
                </div>
            </div> 
        </div>
        <div class="row">
            <div class="col-md-12">
                <div id="msg_catg" class="alert alert-danger" style="display: none;"></div>
            <s:form id="frm_catg" name="frm_catg" method="post" theme="simple">
                <s:if test='"subcategory".equals(operation)'>
                    <s:hidden name="flex3"/>
                </s:if>
                <s:hidden name ="operation" id="hid_operation"/>
                <div class="row" style="margin-top:25px;margin-left:3px !important"> 
                    <div class=" form-group col-md-2">
                        <label><s:text name="lbl.name"/> <span>*</span></label>
                    </div>
                    <div class="col-md-6 form-group">
                        <s:textfield name="flex1"  id="catgName"  cssClass="form-control" required="true" title="%{getText('lbl.please.enter.name')}"/>
                    </div>
                </div>
                <div class="row" style="margin-top:25px;margin-left:3px !important"> 
                    <div class=" form-group col-md-2">
                        <label><s:text name="lbl.description"/></label>
                    </div>
                    <div class="col-md-6 form-group">
                        <s:textfield name="flex2"  id="catgDescription"  cssClass="form-control"/>
                    </div>
                </div>                
            </s:form>
            <div style="float:left; margin-left: 225px" class="btn-group">
                <button class="save-btn btn btn-primary" id="btn_task_catg_save" onclick="saveCategoryForm()">&#10004;<s:text name="btn.save"/></button>
                <button class="close-btn btn" id="btn_crmsms_close"data-dismiss="modal">&#10006;<s:text name="btn.close"/></button>
            </div>
        </div>
    </div>  
</div>
<script type="text/javascript">
    function saveCategoryForm(){
        block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveTaskCategoryForm.do",
                data: $("#frm_catg").serialize(),
                success: function (result) {
                    if(result.messageType==="S"){
                    $('#plugin_modal_dialog').modal('hide');
                    
                    var oper = $("#hid_operation").val();
                    if(oper != null && oper !="" && oper =="category"){
	                    $("#catTaskCatg").append("<option value='" + result.flex4 + "'>" + $("#catgName").val() + "</option>");
	                    $('#catTaskCatg').val(result.flex4);
	                    loadDropDown(result.flex4, 'catTaskSubCatg');
	                    $.notify("Category saved successfully", "custom");
                    }else{
                    	 $("#catTaskSubCatg").append("<option value='" + result.flex4 + "'>" +$("#catgName").val()+ "</option>");
                         $('#catTaskSubCatg').val(result.flex4);
                         $.notify("Sub Category saved successfully", "custom");
                    }
                  
                }else{
                    $("#msg_catg").html(result.message).show();
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