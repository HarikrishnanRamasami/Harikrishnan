<%-- 
    Document   : reportParameter
    Created on : 29 Mar, 2018, 12:48:24 PM
    Author     : sutharsan.g
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    .col-wdth-10,.col-wdth-12,.col-wdth-15,.col-wdth-18,.col-wdth-20,.col-wdth-30,.col-wdth-50,.col-wdth-100{
        position: relative; 
        float: left; 
        padding-left: 10px; 
        padding-right: 10px;
    }

    div.col-wdth-30 .col-wdth-50:nth-child(1) {
        padding-left: 0px; 
    } 
    div.col-wdth-30 .col-wdth-50:nth-child(2) {
        padding-right: 0px;
    } 

    @media (min-width:700px){
        .col-wdth-10{ width: 10%; }
        .col-wdth-12{ width: 12%; }
        .col-wdth-15{ width: 15%; }
        .col-wdth-18{ width: 18%; }
        .col-wdth-20{ width: 20%; }
        .col-wdth-30{ width: 30%; }
        .col-wdth-50{ width: 50%; }
        .col-wdth-100{ width: 100%; }
    }
    .fa-custom{
        font-size: 18px !important;
        cursor: pointer;
    }
</style>
<div class="panel">
    <s:form name="ReportsParamForm" action="ReportsParamForm" theme="simple" method="post" autocomplete="off" >    
        <s:hidden name="reportView" id="reportView" />
        <s:hidden name="reportTitle" id="reportTitle" />
        <s:hidden name="reportTable" id="reportTable" />
        <s:hidden name="reportId" id="reportId" />
        <div class="panel-body">
            <button type="button" class="btn btn-warning tmargin cbtn"  onclick="AddFilterRow();" id="2Btn" >
                <s:text name="btn.add"/>
            </button>
            <div id="FilterTableId" class="mb-10">
                <s:iterator var="filterList" value="repFilter" status="stat">
                    <div id="FilterRow_<s:property value="#stat.index" />" class="col-wdth-100 mt-10">
                        <s:if test="%{#stat.index != 0}">   
                            <div class="col-wdth-12" id="urp_%{#stat.index}">
                                <s:select name="repFilter[%{#stat.index}].urpCondition" id="urpCondition_%{#stat.index}" list="#{'AND':'And', 'OR':'Or'}" cssClass="form-control input-sm" />
                            </div>
                        </s:if>
                        <s:else>
                            <div class="col-wdth-12" style="visibility : hidden;">  
                                <s:select  disabled="disabled" id="urpCondition_%{#stat.index}" list="#{'AND':'And', 'OR':'Or'}" cssClass="form-control input-sm" />
                            </div>
                        </s:else>
                        <div class="col-wdth-30">
                            <s:select name="repFilter[%{#stat.index}].urpColumn" list="filterColumns" cssClass="form-control input-sm" id="urpColumn_%{#stat.index}"
                                      listKey="key" listValue="value" listTitle="info" headerKey="" headerValue="-Select-" label="info1" 
                                      onchange="DataTypeChange(this.id, '%{#stat.index}');" />
                        </div>
                        <div class="col-wdth-18">
                            <s:if test='"D".equalsIgnoreCase(repFilter[#stat.index].urpDataType) || "T".equalsIgnoreCase(repFilter[#stat.index].urpDataType)'>
                                <s:select name="repFilter[%{#stat.index}].urpOperator" id="urpOperator_%{#stat.index}" list="condTypes" cssClass="form-control input-sm"
                                          headerKey="bp" headerValue="By Period" 
                                          onchange="DataTypeChange('urpColumn_%{#stat.index}', '%{#stat.index}');" />
                                <!--   onchange="OperatorTypeChange(this.value, '%{#stat.index}');"  -->
                            </s:if>
                            <s:else>
                                <s:select name="repFilter[%{#stat.index}].urpOperator" id="urpOperator_%{#stat.index}" list="condTypes" cssClass="form-control input-sm"
                                          onchange="DataTypeChange('urpColumn_%{#stat.index}', '%{#stat.index}');" />
                                <!-- onchange="OperatorTypeChange(this.value, '%{#stat.index}');" /> -->
                            </s:else>
                        </div>
                        <s:if test='"btw".equalsIgnoreCase(repFilter[#stat.index].urpOperator)'>
                            <div id="urpValuesText_<s:property value="#stat.index" />" class="col-wdth-30">
                                <div class="col-wdth-50">
                                    <s:textfield name="repFilter[%{#stat.index}].urpValueFm" id="urpValueFm_%{#stat.index}" cssClass='form-control %{"N".equals(repFilter[#stat.index].urpDataType)?"decimal":(("D".equals(repFilter[#stat.index].urpDataType) || "T".equals(repFilter[#stat.index].urpDataType))?"datepickerFrom":"")}' />
                                </div>
                                <div class="col-wdth-50">
                                    <s:textfield name="repFilter[%{#stat.index}].urpValueTo" id="urpValueTo_%{#stat.index}" cssClass='form-control %{"N".equals(repFilter[#stat.index].urpDataType)?"decimal":""}' />
                                </div>
                            </div>
                            <div id="urpValuesHidden_<s:property value="#stat.index"/>" class="col-wdth-30" style="display: none;">
                                <input type="hidden" name="repFilter<s:property value="#stat.index" />.urpValueFm" id="urpValueHidden_<s:property value="#stat.index" />" class="form-control" style="display: none;" disabled>
                            </div>
                            <div id="urpValuesSelect_<s:property value="#stat.index"/>" style="display:none;" class="col-wdth-30">
                                <select name="repFilter<s:property value="#stat.index"/>.urpValueFm" id="urpValueSelect_<s:property value="#stat.index"/>" class="form-control input-sm" 
                                        style="display: none;"></select></div>
                            </s:if>
                            <s:elseif test='"bp".equalsIgnoreCase(repFilter[#stat.index].urpOperator)'>
                            <div id="urpValuesSelect_<s:property value="#stat.index" />" class="col-wdth-30">
                                <s:select name="repFilter[%{#stat.index}].urpValueFm" list="dtFilterTypes" id="urpValueFm_%{#stat.index}" cssClass='form-control input-sm' />
                            </div>
                            <div id="urpValuesText_<s:property value="#stat.index" />" class="col-wdth-30" style="display: none;">
                                <span role="status" aria-live="polite" class="ui-helper-hidden-accessible"></span>
                                <input type="text" name="urpValueFm_desc" id="urpValueText_<s:property value="#stat.index" />" class="form-control ui-autocomplete-input" autocomplete="off" style="display: none;" disabled="disabled">
                            </div>
                        </s:elseif>
                        <s:else>
                            <div id="urpValuesText_<s:property value="#stat.index" />" class="col-wdth-30">
                                <s:textfield name="repFilter[%{#stat.index}].urpValueFm" id="urpValueFm_%{#stat.index}" cssClass='form-control %{"N".equals(repFilter[#stat.index].urpDataType)?"decimal":(("D".equals(repFilter[#stat.index].urpDataType) || "T".equals(repFilter[#stat.index].urpDataType))?"datepickerFrom":"")}' />
                            </div>
                            <div id="urpValuesHidden_<s:property value="#stat.index"/>" class="col-wdth-30" style="display: none;">
                                <input type="hidden" name="repFilter<s:property value="#stat.index" />.urpValueFm" id="urpValueHidden_<s:property value="#stat.index" />" class="form-control" style="display: none;" disabled="disabled">
                            </div>
                            <div id="urpValuesSelect_<s:property value="#stat.index"/>" class="col-wdth-30" style="display:none;">
                                <select name="repFilter<s:property value="#stat.index"/>.urpValueFm" id="urpValueSelect_<s:property value="#stat.index"/>" class="form-control input-sm" 
                                        style="display: none;"></select></div>
                            </s:else>
                            <s:hidden name="repFilter[%{#stat.index}].urpDataType" id="urpDataType_%{#stat.index}" cssClass="form-control" />
                            <div class="col-wdth-10">
                                <button type="button" class="btn bg-red waves-effect" onclick="RemoveFilterRow('<s:property value="#stat.index" />');"><i class="fa fa-remove"></i></button>
                            </div>
                    </div>
                </s:iterator>
            </div>
            <div class="col-md-12 mt-10" align="center" style="margin-top: 10px">
                <button id="btnSave" type="button" class="btn btn-primary leads-tab" style="margin-right: 10px; margin-top: 14px" onclick="runParamReports()" >
                    <s:text name="btn.run"/>
                </button>
                <button id="btnSave" type="button" class="btn btn-primary leads-tab" style="margin-right: 10px; margin-top: 14px" onclick="saveReportparams();">
                    <s:text name="btn.save.run"/>
                </button>
            </div>
        </div>
    </s:form>
</div>
<s:select id="filterColumnsListId" list="filterColumns" cssStyle="display: none;" listKey="key" listValue="value" 
          listTitle="info" headerKey="" headerValue="-Select-" cssClass="form-control input-sm" />
<s:select id="filterCondtListId" list="condTypes" cssStyle="display: none;" cssClass="form-control input-sm" />
<s:select id="filterDateListId" list="dtFilterTypes" cssStyle="display: none;" cssClass="form-control input-sm" />
<script type="text/javascript">
    var filterRowId = "<s:property value="repFilter==null?0:repFilter.size()" />";

</script>
