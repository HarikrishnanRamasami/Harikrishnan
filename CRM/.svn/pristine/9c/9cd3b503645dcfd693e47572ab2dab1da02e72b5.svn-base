<%-- 
    Document   : campaignFilterParams
    Created on : 18 Sep, 2019, 5:59:02 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/campaignFilter.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/struts/optiontransferselect.js"></script>
<style type="text/css">
    #accordion .panel .panel-heading h4 a {
        font-weight: bold;
    }
    .mt-10 {
        margin-top: 10px;
    }
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
<div class="col-md-12 right-pad" id="block_body">
    <div class="dash-leads" style="border-top:0!important">
        <div class=" panel-body">
            <div class="panel-heading" role="tab" id="headingTwo">
                <h4  class="panel-title" onclick="checkColumnsSelected('1')">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo"><s:text name="lbl.campaign.filter.param"/></a>
                </h4>
            </div>
            <s:form name="camp_filter_param" action="camp_filter_param" theme="simple" method="post" autocomplete="off" >    
                <s:hidden name="campaignFilterParam.mcfpFilterId" id="mcfpFilterId"/>
                <s:hidden name="campaign.mcStatus" id="mcStatus"/>
                <div class="panel-body">
                    <div class="col-sm-12 col-md-3 board-icons1 Rbtn" id="e_filter" ></div>
                    <button type="button" class="btn btn-warning tmargin cbtn" style="float: right;" onclick="AddFilterRow();" id="2Btn" >
                        <s:text name="btn.add"/>
                    </button>
                    <div id="FilterTableId" class="mb-10" style="margin-top: 50px;">
                        <s:iterator var="filterList" value="campaignFilterParm" status="stat">
                            <div id="FilterRow_<s:property value="#stat.index" />" class="col-wdth-100 mt-10">
                                <s:if test="%{#stat.index != 0}">   
                                    <div class="col-wdth-12" id="mcfp_%{#stat.index}">
                                        <s:select name="campaignFilterParm[%{#stat.index}].mcfpCondition" id="mcfpCondition_%{#stat.index}" list="#{'AND':'And', 'OR':'Or'}" cssClass="form-control input-sm" />
                                    </div>
                                </s:if>
                                <s:else>
                                    <div class="col-wdth-12" style="visibility : hidden;">  
                                        <s:select  disabled="disabled" id="mcfpCondition_%{#stat.index}" list="#{'AND':'And', 'OR':'Or'}" cssClass="form-control input-sm" />
                                    </div>
                                </s:else>
                                <div class="col-wdth-30">
                                    <s:property value="campaignFilterParm[%{#stat.index}].mcfpDataCol"/>
                                    <s:select name="campaignFilterParm[%{#stat.index}].mcfpDataCol" list="filterColumns" cssClass="form-control input-sm" id="mcfpDataCol_%{#stat.index}"
                                              listKey="key" listValue="value" listTitle="info" headerKey="" headerValue="-Select-" label="info1" 
                                              onchange="DataTypeChange(this.id, '%{#stat.index}');" />
                                </div>
                                <div class="col-wdth-18">
                                    <s:if test='"D".equalsIgnoreCase(campaignFilterParm[#stat.index].mcfpDataType) || "T".equalsIgnoreCase(campaignFilterParm[#stat.index].mcfpDataType)'>
                                        <s:select name="campaignFilterParm[%{#stat.index}].mcfpOperator" id="mcfpOperator_%{#stat.index}" list="condTypes" cssClass="form-control input-sm"
                                                  headerKey="bp" headerValue="By Period" 
                                                  onchange="DataTypeChange('mcfpDataCol_%{#stat.index}', '%{#stat.index}');" />
                                        <!--   onchange="OperatorTypeChange(this.value, '%{#stat.index}');"  -->
                                    </s:if>
                                    <s:else>
                                        <s:select name="campaignFilterParm[%{#stat.index}].mcfpOperator" id="mcfpOperator_%{#stat.index}" list="condTypes" cssClass="form-control input-sm"
                                                  onchange="DataTypeChange('mcfpDataCol_%{#stat.index}', '%{#stat.index}');" />
                                        <!-- onchange="OperatorTypeChange(this.value, '%{#stat.index}');" /> -->
                                    </s:else>
                                </div>
                                <s:if test='"BETWEEN".equalsIgnoreCase(campaignFilterParm[#stat.index].mcfpOperator)'>
                                    <div id="mcfpValuesText_<s:property value="#stat.index" />" class="col-wdth-30">
                                        <div class="col-wdth-50">
                                            <s:textfield name="campaignFilterParm[%{#stat.index}].mcfpValueFm" id="mcfpValueFm_%{#stat.index}" cssClass='form-control %{"N".equals(campaignFilterParm[#stat.index].mcfpDataType)?"decimal":(("D".equals(campaignFilterParm[#stat.index].mcfpDataType) || "T".equals(campaignFilterParm[#stat.index].mcfpDataType))?"datepickerFrom":"")}' />
                                        </div>
                                        <div class="col-wdth-50">
                                            <s:textfield name="campaignFilterParm[%{#stat.index}].mcfpValueTo" id="mcfpValueTo_%{#stat.index}" cssClass='form-control %{"N".equals(campaignFilterParm[#stat.index].mcfpDataType)?"decimal":""}' />
                                        </div>
                                    </div>
                                    <div id="mcfpValuesHidden_<s:property value="#stat.index"/>" class="col-wdth-30" style="display: none;">
                                        <input type="hidden" name="campaignFilterParm<s:property value="#stat.index" />.mcfpValueFm" id="mcfpValueHidden_<s:property value="#stat.index" />" class="form-control" style="display: none;" disabled>
                                    </div>
                                    <div id="mcfpValuesSelect_<s:property value="#stat.index"/>" style="display:none;" class="col-wdth-30">
                                        <select name="campaignFilterParm<s:property value="#stat.index"/>.mcfpValueFm" id="mcfpValueSelect_<s:property value="#stat.index"/>" class="form-control input-sm" 
                                                style="display: none;"></select></div>
                                    </s:if>
                                    <s:elseif test='"bp".equalsIgnoreCase(campaignFilterParm[#stat.index].mcfpOperator)'>
                                    <div id="mcfpValuesSelect_<s:property value="#stat.index" />" class="col-wdth-30">
                                        <s:select name="campaignFilterParm[%{#stat.index}].mcfpValueFm" list="dtFilterTypes" id="mcfpValueFm_%{#stat.index}" cssClass='form-control input-sm' />
                                    </div>
                                    <div id="mcfpValuesText_<s:property value="#stat.index" />" class="col-wdth-30" style="display: none;">
                                        <span role="status" aria-live="polite" class="ui-helper-hidden-accessible"></span>
                                        <input type="text" name="mcfpValueFm_desc" id="mcfpValueText_<s:property value="#stat.index" />" class="form-control ui-autocomplete-input" autocomplete="off" style="display: none;" disabled="disabled">
                                    </div>
                                </s:elseif>
                                <s:else>
                                    <div id="mcfpValuesText_<s:property value="#stat.index" />" class="col-wdth-30">
                                        <s:textfield name="campaignFilterParm[%{#stat.index}].mcfpValueFm" id="mcfpValueFm_%{#stat.index}" cssClass='form-control %{"N".equals(campaignFilterParm[#stat.index].mcfpDataType)?"decimal":(("D".equals(campaignFilterParm[#stat.index].mcfpDataType) || "T".equals(campaignFilterParm[#stat.index].mcfpDataType))?"datepickerFrom":"")}' />
                                    </div>
                                    <div id="mcfpValuesHidden_<s:property value="#stat.index"/>" class="col-wdth-30" style="display: none;">
                                        <input type="hidden" name="campaignFilterParm<s:property value="#stat.index" />.mcfpValueFm" id="mcfpValueHidden_<s:property value="#stat.index" />" class="form-control" style="display: none;" disabled="disabled">
                                    </div>
                                    <div id="mcfpValuesSelect_<s:property value="#stat.index"/>" class="col-wdth-30" style="display:none;">
                                        <select name="campaignFilterParm<s:property value="#stat.index"/>.mcfpValueFm" id="mcfpValueSelect_<s:property value="#stat.index"/>" class="form-control input-sm" 
                                                style="display: none;"></select></div>
                                    </s:else>
                                    <s:hidden name="campaignFilterParm[%{#stat.index}].mcfpDataType" id="mcfpDataType_%{#stat.index}" cssClass="form-control" />
                                <div class="col-wdth-10">
                                    <button type="button" class="btn bg-red waves-effect" onclick="RemoveFilterRow('<s:property value="#stat.index" />');"><i class="fa fa-remove"></i></button>
                                </div>
                            </div>
                        </s:iterator>
                    </div>
                </div>
                <div class="form-group col-md-7" style="margin-top: 32px; float: right">
                    <button type="button" class="save-btn btn btn-primary" onclick="saveCampFilterParam();" style="margin-top: -8px" title="Save"><i class="fa fa-save"></i> <s:text name="btn.save"/></button>
                    <button type="button" id="btn_filter_close" class="btn btn-danger waves-effect" style="margin-top: -8px" onclick="closeFilterParam();" title="Close"><i class="fa fa-close"></i> <s:text name="btn.close"/></button>
                </diV>
            </s:form>
        </div>
    </div>
</div>
<s:select id="filterColumnsListId" list="filterColumns" cssStyle="display: none;" listKey="key" listValue="value" 
          listTitle="info" headerKey="" headerValue="-Select-" cssClass="form-control input-sm" />
<s:select id="filterCondtListId" list="condTypes" cssStyle="display: none;" cssClass="form-control input-sm" />
<s:select id="filterDateListId" list="dtFilterTypes" cssStyle="display: none;" cssClass="form-control input-sm" />

<script type="text/javascript">
    var filterRowId = "<s:property value="campaignFilterParm==null?0:campaignFilterParm.size()" />";
    if ($("#mcStatus").val() === "R" || $("#mcStatus").val() === "C") {
        $('#camp_filter_param button, #camp_filter_param input, #camp_filter_param select').prop('disabled', true);
        $('#camp_filter_param button').remove('.save-btn');
        $('#camp_filter_param #btn_filter_close').prop('disabled', false);
    }

    function saveCampFilterParam() {
        $("#selectedColumnsId option").prop('selected', true);
        block('block_body');
        $.ajax({
            type: "POST",
            url: '${pageContext.request.contextPath}/camp/saveCampFilterParam.do',
            data: $("#camp_filter_param").serialize(),
            async: true,
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify("Data Filter Updated Successfully", "custom");
                    $("#filter_params").hide();
                    unblock('block_body');
                } else {
                    $.notify(result.message, "custom");
                }
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
                block('block_body');
            }
        });
    }


    function closeFilterParam() {
        $("#filter_params").empty().hide();
    }
</script>
