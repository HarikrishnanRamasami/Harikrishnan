<%-- 
    Document   : opportunityForm
    Created on : 20 Mar, 2017, 3:36:18 PM
    Author     : haridass.a
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<a href="#" class="close-link">&#10006;</a>
<div class="task-form">
    <div class="popup-title">
        <h3><s:text name="lbl.leads.head.opportunity.form"/></h3>
        <div style="float:right;" class="">
            <s:if test='"add".equalsIgnoreCase(operation)'>
                <button class="save-btn btn btn-primary" id="btn_opportunities_save">&#10004;<s:text name="btn.save"/></button>
                <button class="close-btn btn">&#10006;<s:text name="btn.close"/></button>
            </s:if>
            <s:elseif test='"edit".equalsIgnoreCase(operation)'>    
                <button id="btn_opportunities_save" class="save-btn btn btn-primary"  onclick="changeOpportunityStatus('S');">&#10004;<s:text name="btn.success"/></button>
                <button class="close-btn btn" id="btn_opportunities_close" onclick="changeOpportunityStatus('F');">&#10006;<s:text name="btn.fail"/></button>
                <button class="close-btn btn" id="btn_opportunities_close" data-dismiss="modal" aria-hidden="true">&#10006;<s:text name="btn.close"/></button> 
            </s:elseif>
        </div><hr>
    </div>
    <div id="msg_opportunities" class="alert alert-danger" style="display: none;"></div>
    <s:form id="frm_opportunities" name="frm_opportunities" method="post" theme="simple" >
        <s:hidden name="leads.clId"/>
        <s:hidden name ="operation"/>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="oppurtunities.coOppType"  id="coOppType" list="oppTypeList" listKey="key" listValue="value" headerKey="" headerValue="" cssClass="textbox"  required="true" title="%{getText('lbl.common.please.select.type')}" />
                <label class="textboxlabel"><s:text name="lbl.typ"/><span>*</span></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="oppurtunities.coRefNo" id="coRefNo" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.ref.no')}"/>
                <label class="textboxlabel"><s:text name="lbl.ref.no"/></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="oppurtunities.coCurrency"  id="coCurrency" list="currencyList" listKey="key" listValue="value" headerKey="" headerValue="" cssClass="textbox" required="true" title="%{getText('lbl.common.please.select.currency')}" />
                <label class="textboxlabel"><s:text name="lbl.common.currency"/><span>*</span></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="oppurtunities.coValue" id="coValue"  cssClass="textbox" required="true" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.please.enter.value')}" />
                <label class="textboxlabel"><s:text name="lbl.value"/><span>*</span></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textarea name="oppurtunities.coRemarks" id="coRemarks"  cssClass="textbox" required="true" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.please.enter.remarks')}" />
                <label class="textboxlabel"><s:text name="lbl.remarks"/><span>*</span></label>
            </div>
        </div>
        <s:if test='"add".equalsIgnoreCase(operation)'>                
            <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <input type="checkbox"  name="oppurtunities.coCreateTaskYn" value="1" id="md_checkbox_36" class="filled-in chk-col-deep-orange" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.create.task')}" onchange="enableAddlDetails();">
                    <label for="md_checkbox_36"><s:text name="lbl.create.task"/></label>
                </div>
            </div>
        </s:if>
        <div class="" id="check_show" style="display:none">
            <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:select name="oppurtunities.task.ctCatg"  id="ctCatg" list="categoryList" listKey="key" listValue="value" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.category')}" onchange="loadDropDown(this.value, 'ctSubCatg');" />
                    <label class="textboxlabel"><s:text name="lbl.category"/><span>*</span></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:select name="oppurtunities.task.ctSubCatg" id="ctSubCatg" list="aaData" listKey="key" listValue="value" headerKey="" headerValue="" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.sub.category')}" cssClass="textbox" />
                    <label class="textboxlabel"><s:text name="lbl.sub.category"/><span>*</span></label>
                </div>
            </div>
            <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield name="oppurtunities.task.ctDueDate"  id="datepicker"  cssClass="textbox calicon" required="true" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.please.select.duedate')}"/>
                    <label class="textboxlabel"><s:text name="lbl.due.date"/><span>*</span></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield name="oppurtunities.task.ctRemindBefore" id="ctRemindBefore" cssClass="textbox" required="true" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.leads.enter.remind.before.hrs')}"/>
                    <label class="textboxlabel"><s:text name="lbl.leads.remind.before"/></label>
                </div>
            </div>
        </div>
    </div>
</s:form>
<script type="text/javascript">
    /*$('#ctDueDate').bootstrapMaterialDatePicker({
        format: 'DD/MM/YYYY',
        clearButton: true,
        weekStart: 1,
        time: false
    });*/
     $( function() {
     $( "#datepicker").datepicker({format: 'dd/mm/yyyy'});
	
	 
  } );
    function enableAddlDetails() {
        if ($('#md_checkbox_36').is(":checked"))
            $("#check_show").show();
        else
            $("#check_show").hide();
    }
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        <s:if test='"edit".equalsIgnoreCase(operation)'>
            $("#frm_opportunities").find('input, textarea, button, select').attr('disabled','disabled');
        </s:if>
        $('#frm_opportunities').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            showErrors: function (errorMap, errorList) {
                if (errorList.length) {
                    $("#msg_opportunities").show();
                    $("#msg_opportunities").html(errorList[0].message).fadeIn().delay(3000).fadeOut();
                }
            }
        });
        
        $("#btn_opportunities_save").on("click", function () {
            event.preventDefault();
            if (!$('#frm_opportunities').valid()) {
                return false;
            }
              block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveOpportunitiesEntryForm.do",
                data: $("#frm_opportunities").serialize(),
                success: function (result) {
                    if(result.messageType === "S"){
                        //$("#msg_opportunities").removeClass("alert-danger").addClass("alert-success").html(result.message).show();
                        //$('#plugin_modal_dialog').modal('hide');
                         $('.popup-wrap').removeClass('popup-open');
                         $('#overlay').hide();
                        $.notify("Opportunity  details saved successfully", "custom");
                    }else{
                        $('#msg_opportunities').empty().html(result.message).show();
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
    function changeOpportunityStatus(status){
         $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveOpportunitiesEntryForm.do",
                data: {"operation":"edit", "oppurtunities.coStatus":status, "oppurtunities.coId": '${oppurtunities.coId}'},
                success: function (result) {
                    if(result.messageType === "S"){
//                        $("#msg_opportunities").removeClass("alert-danger").addClass("alert-success").html(result.message).show();
//                        $('#plugin_modal_dialog').modal('hide');
                        $('.popup-wrap').removeClass('popup-open');
                        $('#overlay').hide();
                        $("#opportunity_tbl").DataTable().ajax.url(APP_CONFIG.context + "/loadOpportunitiesEntryData.do?" + $('#frm_opportunity_search').serialize()).load();
                    }else{
                        $('#msg_opportunities').empty().html(result.message).show();
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                }
            });
    }
     function loadDropDown(val, id) {
            $("#" + id).html("<option value=''>--Select-</option>");
            $.ajax({
                type: "POST",
                url: "<%=request.getContextPath()%>/loadTaskEntrySubCategoryList.do",
                data: {"task.ctCatg": val, "company": $("#company").val()},
                success: function (data) {
                    var opt = '<option value="">--Select-</option>';
                    $.each(data.aaData, function (i, d) {
                        opt += '<option value="' + d.key + '">' + d.value + '</option>';
                    });
                    $('#' + id).html(opt);
                    //$('#' + id).selectpicker('refresh');
                }
            });
        }
</script>
<script type="text/javascript">
    $(function(){

    //  $('#overflow').slimScroll();

      $('#sidebar').slimScroll({
	  
           height: '100%', 
		  width:'100%', 
		  
		   
      });
     $('.leads-tab').click(function(){
    	  $('.popup-wrap').addClass('popup-open');
    	  $('#overlay').show();
    	  
      });
      $('.close-link').click(function(){
    	  $('.popup-wrap').removeClass('popup-open');
    	  $('#overlay').hide();
      });
	  $('.close-btn').click(function(){
		  $('.popup-wrap').removeClass('popup-open');
		  $('#overlay').hide();
	  });
	  

    });
</script>