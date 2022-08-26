<%-- 
    Document   : teskRulesForm
    Created on : 6 Nov, 2017, 12:51:54 PM
    Author     : palani.rajan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/bootstrap-multiselect/bootstrap-multiselect.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-multiselect/bootstrap-multiselect.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/tokenizeSearch/tokenize2.min.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/tokenizeSearch/tokenize2.min.js"></script>

<style type="text/css">
    .multiselect-container>li>a>label {
  padding: 4px 20px 3px 20px;
}
</style>
<div class="card">
    <div class="row">
        <div class="col-md-12">
            <div class="popup-title">
                <h4><s:text name="lbl.task.rules.form"/>/h4>
            </div>
        </div> 
    </div>
    <div class="row">
        <div class="col-md-12">
            <div id="msg_rules" class="alert alert-danger" style="display: none;"></div>
            <s:form id="frm_rules" name="frm_rules" method="post" theme="simple">
                <s:hidden name ="operation"/>
                <s:hidden name ="mCrmAgentsTask.catTaskCatg"/>
                <s:hidden name ="mCrmAgentsTask.catTaskSubCatg"/>
                <s:hidden name="flex5"/>
                <div class="row" style="margin-top:25px;margin-left:3px !important"> 
                    <div class=" form-group col-md-2">
                        <label><s:text name="lbl.filter"/> <span>*</span></label>
                    </div>
                    <div class="col-md-6 form-group">
                        <select name="flex1" id="filter" class="form-control"  onchange="loadOperatorList()" >
                            <option value="">--Select--</option>    
                            <s:iterator value="filterlist" status="stat">
                                <option value="<s:property escapeJavaScript="true"  value="key"/>" data-info="<s:property escapeJavaScript="true" value="info1"/>" data-info2="<s:property escapeJavaScript="true" value="info2"/>"><s:property  value="value"/></option>
                            </s:iterator>
                        </select>
                    </div>
                </div>
                <div class="row" style="margin-top:25px;margin-left:3px !important"> 
                    <div class=" form-group col-md-2">
                        <label><s:text name="lbl.operator"/></label>
                    </div>
                    <div class="col-md-6 form-group">
                        <s:select name="flex2" id="operator" list="operatorlist" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control"/>
                    </div>
                </div>
                <div class="row" style="margin-top:25px;margin-left:3px !important"> 
                    <div class=" form-group col-md-2">
                        <label><s:text name="lbl.value"/></label>
                    </div>
                    <div class="col-md-6 form-group">
                        <div id="val_sel_div">
                            <select name="flex3" id="valuedd" class="multiselect-ui form-control" multiple="multiple">
                                <s:iterator value="valuelist" status="stat">
                                    <option value="<s:property  value="key"/>"><s:property  value="value"/></option>
                                </s:iterator>
                            </select>
                        </div>
                        <div id="val_text_div" style="display: none">
                            <s:textfield name="flex4"  id="value"  cssClass="form-control numbers"/>
                        </div>
                        <div id="val_autoComp_div" style="display: none">
                            <select name="flex3" class="tokenize-remote-demo1" multiple></select>
                        </div>
                        <div id="val_date_div" style="display: none">
                            <s:textfield name="flex4"  id="dateValue"  cssClass="form-control datepicker calicon"  placeholder="DD/MM/YYYY"/>
                        </div>
                    </div>
                </div>
            </s:form>
            <div style="float:left; margin-left: 225px" class="">
                <button class="save-btn btn btn-primary" id="btn_task_sla_save" onclick="return saveRulesForm()">&#10004;<s:text name="btn.save"/></button>
                <button class="close-btn btn" id="btn_crmsms_close"data-dismiss="modal">&#10006;<s:text name="btn.close"/></button>
            </div>
        </div>
    </div>  
</div>
</div>
<script type="text/javascript">
 $( document ).ready(function() {
       $(".datepicker").datepicker({format: 'dd/mm/yyyy'});
     $('.tokenize-remote-demo1').tokenize2({
                dataSource: function(search, object){
                    $.ajax('<%=request.getContextPath()%>/loadAutoCompleteList.do', {
                        data: { flex1: search, start:0 ,"flex3":$("#filter option:selected").val()},
                        dataType: 'json',
                        success: function(data){
                            var $items = [];
                            $.each(data.aaData, function(k, v){
                                $items.push(v);
                            });
                            object.trigger('tokenize:dropdown:fill', [$items]);
                        }
                    });
                },
        debounce: 1000,
        tokensMaxItems:0,
        searchMinLength:3
    });
   
    
     $('.numbers').on('input', function (event) { 
                    this.value = this.value.replace(/[^0-9]/g, '');
      }); 
    <s:if test='"edit".equals(operation)'>
       $("#filter").val(<s:property value="flex1"/>);
       var val = $("#filter").find(':selected').data('info2');
       var filterVal=$("#filter").val();
      if(val === 'A'){
            $("#val_sel_div").hide();
            $("#val_text_div").hide();
            $("#val_date_div").hide();
            $("#frm_rules #value").attr("disabled", false);
            $("#frm_rules #dateValue").attr("disabled", true);
            $("#val_autoComp_div").show();
        }
        if(val === 'T'){
            $("#val_sel_div").hide();
            $("#val_text_div").show();
            $("#val_date_div").hide();
            $("#frm_rules #value").attr("disabled", false);
            $("#frm_rules #dateValue").attr("disabled", true);
            //$("#value").val(<s:property value="flex4"/>);
        }else if(val === 'DT'){
             $("#val_sel_div").hide();
             $("#val_text_div").hide();
             $("#frm_rules #value").attr("disabled", true);
             $("#frm_rules #dateValue").attr("disabled", false);
             $("#val_date_div").show();
        }else{
              $("#val_sel_div").show();
              $("#val_text_div").hide();
              $("#frm_rules #dateValue").attr("disabled", true);
              $("#frm_rules #value").attr("disabled", true);
                var data='<s:property value="flex3"/>';
                if(data.includes(",")){
                    console.log(data);
                    var valArr = data.split(",");
                      var i = 0, size = valArr.length;
                    for (i; i < size; i++) {
                        valArr[i] = $.trim(valArr[i]);
                    } 
                    $("#valuedd").val(valArr);
                }else{
                     console.log("esss "+data);
                      $("#valuedd").val(data);
                }
               $("#valuedd").multiselect("refresh");
                //}
                
               
         }
    </s:if>
 });
    $(function() {
    $('#valuedd').multiselect({
        includeSelectAllOption: true
    });
    });
    function loadOperatorList(){
        var val = $("#filter").find(':selected').data('info');
        $("#operator").html("<option value=''>--Select-</option>");
        block('block_body');
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/loadOperatorList.do",
            data: {"flex1": val, "company": $("#company").val()},
            success: function (data) {
                var opt = '<option value="">--Select-</option>';
                $.each(data.aaData, function (i, d) {
                    opt += '<option value="' + d.key + '">' + d.value + '</option>';
                });
                $('#operator').html(opt);
                loadValueList();
                //$('#' + id).selectpicker('refresh');
            },
            complete: function () {
                unblock('block_body');
                
            }
        });
    }
    
    function loadValueList(){
        var val = $("#filter").find(':selected').data('info2');
        var filterVal=$("#filter").val();
        if(val === 'A'){
            $("#val_sel_div").hide();
            $("#val_text_div").hide();
            $("#val_date_div").hide();
            $("#frm_rules #value").attr("disabled", false);
            $("#frm_rules #dateValue").attr("disabled", true);
            $("#val_autoComp_div").show();
        }else{
        if(val === 'T'){
            $("#val_sel_div").hide();
            $("#frm_rules #dateValue").attr("disabled", true);
            $("#frm_rules #value").attr("disabled", false);
            $("#val_text_div").show();
             $("#val_autoComp_div").hide();
             $("#val_date_div").hide();
        }else if(val === 'DT'){
            $("#frm_rules #value").attr("disabled", true);
            $("#frm_rules #dateValue").attr("disabled", false);
            $("#val_sel_div").hide();
            $("#val_text_div").hide();
             $("#val_autoComp_div").hide();
             $("#val_date_div").show();
        }else{
             $("#frm_rules #dateValue").attr("disabled", true);
             $("#frm_rules #value").attr("disabled", true);
             $("#val_sel_div").show();
             $("#val_text_div").hide();
             $("#val_autoComp_div").hide();
             $("#val_date_div").hide();
        block('block_body');
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/loadValueList.do",
            data: {"flex1":$("#filter option:selected").val(), "company": $("#company").val()},
            success: function (data) {
                var opt1=null;
                $.each(data.aaData, function (i, d) {
                    opt1 += '<option value="' + d.key + '">' + d.value + '</option>';
                });
                $('#valuedd').empty().prepend(opt1).multiselect("destroy").multiselect();
            },
            complete: function () {
                unblock('block_body');
            }
        });
        }
        }
    }
    function saveRulesForm(){
        block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveTaskRulesForm.do",
                data: $("#frm_rules").serialize(),
                success: function (result) {
                    if(result.messageType==="S"){
                    $('#plugin_modal_dialog').modal('hide');
                    $.notify("Rules saved successfully", "custom");
                    reloadDt("tbl_task_rules");
                }else{
                    $("#msg_rules").html(result.message).show();
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