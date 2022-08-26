<%-- 
    Document   : reportDesigner
    Created on : Oct 26, 2015, 11:43:33 AM
    Author     : palani.rajan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%--@page errorPage="/jsp/common/ErrorMsg.jsp" --%>

<style type="text/css">
    #accordion .panel .panel-heading h4 a {
        font-weight: bold;
    }
    .mt-10 {
        margin-top: 10px;
    }
</style>
<s:set var="PLUGIN"><s:if test='"email".equals(pluginFor) || "lead".equals(pluginFor)|| "salesforce".equals(pluginFor)'>Y</s:if><s:else>N</s:else></s:set>
<div class="col-md-12 <s:if test='"N".equals(#PLUGIN)'>right-pad</s:if>">
    <div class="<s:if test='"N".equals(#PLUGIN)'>dash-leads</s:if>" style="border-top:0!important;" id="rules_div">
        <s:if test='!"email".equals(pluginFor) && !"lead".equals(pluginFor)'>
            <div class="modal-header">
                <h4 class="modal-title"><s:text name="lbl.reports.head.add.edit.report"/></h4>
            </div>
        </s:if>
        <div class="body">
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-12 mt-10">
                            <div id="ReportsDesignerErrorDiv" class="alert alert-danger" style="display:none;"></div>
                        </div>
                    </div>
                    <s:form name="ReportsDesignerForm" action="ReportsDesignerForm" theme="simple" method="post" autocomplete="off" >    
                        <s:hidden name="reportView" id="reportView" />
                        <s:hidden name="reportTitle" id="reportTitle" />
                        <s:hidden name="reportTable" id="reportTable" />
                        <s:hidden name="pluginFor" />
                        <s:hidden name="errMsg" id="errMsg" />
                        <s:hidden name="reportType" id="campType" />
                        <s:if test='!"email".equals(pluginFor) && !"lead".equals(pluginFor)'>
                            <div class="row mt-10">
                                <div class="col-md-12">
                                    <div class="col-md-1">
                                        <Strong> <s:text name="lbl.view"/> </strong>
                                    </div>
                                    <div class="col-md-2">
                                        <s:textfield name="reportTitle" disabled='true' cssClass="form-control" />
                                    </div>
                                    <div class="col-md-1" style="vertical-align: middle;">
                                        <Strong> <s:text name="lbl.description"/> </strong>
                                    </div>
                                    <div class="col-md-8">
                                        <s:textfield name="reportViewDesc" id="reportViewDesc" disabled='true' cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                        </s:if>
                        <div class="row mt-10">
                            <div id="ReportColumsDiv" class="col-md-12">
                                <s:include value="columnsAndFilters.jsp" />
                            </div>
                        </div>
                        <div class="row">
                            <s:if test='"email".equals(pluginFor) || "lead".equals(pluginFor)|| "salesforce".equals(pluginFor)'>
                                <div class="col-md-12" style="text-align: center;">
                                    <button id="btnProceed" type="button" class="btn bg-red waves-effect" onclick="runDynamicReportJS();">
                                        <s:text name="lbl.common.extract.data"/>
                                    </button>
                                </div>
                            </s:if>
                            <s:else>
                                <div class="col-md-12" style="margin-right: 20px">
                                    <div class="col-md-1 mb-10" style="text-align: center;">
                                        <button id="btnBack" type="button" class="btn btn-warning leads-tab" onclick="LoadEnquiryGrid();">
                                            <s:text name="btn.cancel"/>
                                        </button>
                                    </div>
                                    <div class="col-md-1 mb-10">
                                        <button id="btnSave" type="button" class="btn btn-warning leads-tab" style="margin-right: 10px;" onclick="captureReportInfo();">
                                            <s:text name="btn.save.as"/>
                                        </button>
                                    </div>
                                    <div class="col-md-1 mb-10">
                                        <button id="btnProceed" type="button" class="btn btn-warning leads-tab" onclick="runDynamicReportJS();">
                                            <s:text name="btn.run"/>
                                        </button>
                                    </div>
                                </div>
                            </s:else>
                        </div>
                    </s:form>
                    <div class="row">
                        <div id="ReportResultDiv" class="col-md-12"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    
    $(document).ready(function() {
        $('.datepickerFrom').each(function(i, obj){
            var indx = obj.id.replace("urpValueFm_", "");
            $("#urpValueFm_"+indx).datepicker({
                dateFormat: 'dd/mm/yy'+($("#urpDataType_"+indx).val()=="T"?" 00:00":""),
                changeMonth: true,
                changeYear: true,
                onSelect: function( selectedDate ) {
                    if($("#urpValueTo_"+indx).length>0)
                        $("#urpValueTo_"+indx).datepicker( "option", "minDate", new Date($('#urpValueFm_'+indx).datepicker('getDate')) );
                }}
            );
            if($("#urpValueTo_"+indx).length>0){         
                $("#urpValueTo_"+indx).datepicker({
                    dateFormat: 'dd/mm/yy'+($("#urpDataType_"+indx).val()=="T"?" 23:59":""),
                    changeMonth: true,
                    changeYear: true,
                    minDate: new Date($('#urpValueFm_'+indx).datepicker('getDate')),
                    onSelect: function( selectedDate ) {
                        $("#urpValueFm_"+indx).datepicker( "option", "maxDate", new Date($('#urpValueTo_'+indx).datepicker('getDate')));
                    }
                });
            }
        });
       // $("#ReportColumsDiv").accordion({header: "h4", collapsible: true, heightStyle: "content", activate: function( event, ui ) {checkColumnsSelected();}});
    });
    
    function checkColumnsSelected(indx){
        if(indx!= 0){
            $("#selectedColumnsId option").prop('selected', true);
            if($("#selectedColumnsId :selected").length<=0){
                $("#ReportsDesignerErrorDiv").html("Select columns to display before proceeding");
                $("#ReportsDesignerErrorDiv").show();
                //$('.panel-collapse').removeClass('in').attr('aria-expanded', 'false');
                //$('#'+indx).addClass('in').attr('aria-expanded', 'true');
                //return false;
            }
                if(indx === '1' || indx === '2'){
                    $("#2Btn").attr('style', 'display:block;');
                    $("#3Btn").attr('style', 'display:block;');
                }
        }
        /*alert($('#urpValueFm_0').val()+" : "+$('#urpColumn_0').val());
        var prevVal = $('#urpValueFm_0').val();
        DataTypeChange('urpColumn_0', '0');
        $('#urpValueFm_0').val(prevVal);
        $('#urpSelect_0').val(prevVal).trigger("change");*/
    
    }
    
    function editReportName(){
        $("#reportId").val("");
        $("#reportNameText").removeAttr("readonly");
        $("#BtnEditRptNameDivId").hide();
    }
    
    function captureReportInfo(){
    	// Remove filter if nothing selected
    	$('[id^="urpColumn_"]').each(function(i) {
            if($.trim(this.value).length == 0) 
            {
            	var id = this.id.substring(10, this.id.length);
            	$('#FilterRow_'+id).remove();
            }
        });
    	
    	 $('[id^="sel_sortby_"]').each(function(i, v) {
            if($.trim(this.value).length == 0) 
            {
                var id = this.id.substring(11, this.id.length);
                $('#SortingRow_'+id).remove();
            }
        });
    	
        $("#selectedColumnsId option").prop('selected', true);
        $("#selectedColumnsgrpId option").prop('selected', true);
        
        if($("#selectedColumnsId :selected").length>0){
            $('#ReportSaveInfoDivId').modal('show');
        } else {
            $("#ReportsDesignerErrorDiv").html("Select columns to display before proceeding");
            $("#ReportsDesignerErrorDiv").show();
            return false;
        }
    }
    
    function clearDuplicateFields()
    {
          $('[id^="FilterRow_"]').each(function(i) {
              var index = this.id.replace('FilterRow_', '');
              // alert('index='+index);
              if( $('#urpValuesSelect_'+index).css('display') == 'none' )
                  $('#urpValuesSelect_'+index).html('');
              if( $('#urpValuesText_'+index).css('display') == 'none' )
                  $('#urpValuesText_'+index).html('');
          });
    }
    
    function saveReportEnquiry(){
        $("#selectedColumnsId option").prop('selected', true);
        $("#selectedUsersId option").prop('selected', true);
        $("#selectedRolesId option").prop('selected', true);
        
        if($("#selectedColumnsId :selected").length>0)
        {
        	clearDuplicateFields();
        	block('block_body');
            $.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}/SaveEnquiryReport.do',
                data: $("#ReportsDesignerForm").serialize(),
                success: function (result) {
                    if(result.reportStatus!=null && ""!=result.reportStatus){
                        $("#ReportsSaveErrorDiv").html(result.reportStatus);
                        $("#ReportsSaveErrorDiv").show();
                    } else {
                      $('#ReportSaveInfoDivId').modal('hide');
                      if($("#campType").val() === "M"){
                          loadCampaignReports();
                        }else{
                       LoadEnquiryGrid();
                   }
                    }
                },
                complete: function(){
                	unblock('block_body');
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                }
            });
        } else {
            alert("Select Columns to display");
            return false;
        }
    }

    function runDynamicReportJS(){
        $("#selectedColumnsId option").prop('selected', true);
        $("#selectedColumnsgrpId option").prop('selected', true);
        $("#selectedUsersId option").prop('selected', true);
        $("#selectedRolesId option").prop('selected', true);
        if($("#selectedColumnsId :selected").length>0){
        	clearDuplicateFields();
            block();
            $.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}/RunDynamicReport.do',
                data: $("#ReportsDesignerForm").serialize(),
                async: true,
                success: function (result) {
                    <s:if test='"lead".equals(pluginFor)'>
                        $('#ReportResultDiv').load(APP_CONFIG.context + "/saveLeadsEntryReportData.do", $("#uptFrm").serialize());
                    </s:if>
                    <s:else>
                    $('#ReportResultDiv').html(result);
                    </s:else>
                    $('#btnRptRsltBack').hide();
                    unblock();
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                    unblock();
                }
            });
        } else {
            alert("Select columns to dispaly");
            return false;
        }
    }
    
    function LoadEnquiryGrid(){
     if ($("#campType").val() === "M") {
            $("#ReportsDesignerForm").attr("action", "<%=request.getContextPath()%>/camp/openCampaignPage.do?campaignTab=report");
            $("#ReportsDesignerForm").submit();
    } else {
        $("#ReportsDesignerForm").attr("action", "${pageContext.request.contextPath}/LoadDynamicReports.do");
        $("#ReportsDesignerForm").submit();
    }
      //  block('block_body');
       /* $.ajax({
               type: "POST",
               url: '<%=request.getContextPath()%>/LoadDynamicReportsAjax.do',
               data: $("#ExcelExportForm").serialize(),
               success:function(result){
                if(!applyAjaxResponseError(result, 'templateErrorDiv')){
                    $('#ReportSaveInfoDivId').modal('hide');
                                setTimeout(function() {
                                    //$('.modal-backdrop').remove();
                                    $('#div_body').html(result);
                                    $('#div_body').show();
                                }, 1000);
                }
               },
               complete: function() {
            	 //  unblock('block_body');
               },
               error:function(xhr, status, error){
                   alert("Error: " + error);
               }
        });*/
    }
    
    function loadCampaignReports(){
        $("#ReportsDesignerForm").attr("action", "<%=request.getContextPath()%>/camp/openCampaignPage.do?campaignTab=report");
        $("#ReportsDesignerForm").submit()
    }
    
</script>