<%-- 
    Document   : DataParamFilter
    Created on : 16 Sep, 2019, 11:52:25 AM
    Author     : sutharsan.g
--%>

<%@page import="qa.com.qic.anoud.vo.KeyValue"%>
<%@page import="java.util.List"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/marketingCampaign.js"></script>
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
<!--Applying New Style for Accordion--Begin-->
<div class="col-md-12 right-pad" id="block_body">
    <s:include value="/WEB-INF/pages/campaign/campaignHeader.jsp?stepNo=2"></s:include>
        <div class="dash-leads" style="border-top:0!important">
        <s:hidden name="mcCampId" id="mcCampId"/>
        <s:hidden name="campaign.mcStatus" id="mcStatus"/>  
        <!--div class="my-bord">
            <h3>Data Param Filter</h3>
        </div-->
        <div id="msg_data_param" class="alert alert-danger" style="display: none;"></div>
        <s:form name="ReportMktgForm" action="ReportMktgForm" theme="simple" method="post" autocomplete="off" > 
            <s:hidden name="mcfpFilterId" id="mcfpFilterId"/>
            <s:hidden name="campaignFilter.mcfpFilterId" id="mcfpFilterId"></s:hidden>
                <div class="panel-group" id="accordion" role="tablist" style="margin-top: 30px!important;">
                <s:if test='"A".equals(campaign.mcDataSourceType)'>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                <h4  class="panel-title" style="color:black;">
                                    <s:text name="lbl.campaign.select.columns.display"/>
                                </h4>
                            </a>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne" aria-expanded="true">
                            <div class="panel-body">
                                <table border="0" style="margin: 10px; width: 96%;">
                                    <tr> 
                                        <td style="width: 38%;">
                                            <s:select name="actualColumns" size="15" id="allColumnsId" cssClass="form-control input-sm" list="actualColumns" multiple="true" listKey="key" listTitle="info" listValue="value"/>
                                        </td>
                                        <td valign="middle" align="center" style="width: 2%;">
                                            <button type="button" class="btn btn-warning leads-tab"  style="width: 42px;"
                                                    onclick="
                                                            setTimeout(moveSelectedOptions(document.getElementById('selectedColumnsId'), document.getElementById('allColumnsId'), false, ''), 10);"><i class="fa fa-chevron-left"></i></button><br><br>
                                            <button type="button" class="btn btn-warning leads-tab"  style="width: 42px;"
                                                    onclick="moveSelectedOptions(document.getElementById('allColumnsId'), document.getElementById('selectedColumnsId'), false, '');
                                                    "><i class="fa fa-chevron-right"></i></button><br><br>
                                            <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                                    onclick="detachAggregateFunction('A');
                                                            setTimeout(moveAllOptions(document.getElementById('selectedColumnsId'), document.getElementById('allColumnsId'), false, ''), 10);"><i class="fa fa-step-backward"></i></button><br><br>
                                            <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                                    onclick="moveAllOptions(document.getElementById('allColumnsId'), document.getElementById('selectedColumnsId'), false, '');
                                                    "><i class="fa fa-step-forward"></i></button><br><br>
                                        </td>
                                        <td style="width: 38%;">
                                            <s:select name="repColumns" size="15" id="selectedColumnsId" cssClass="form-control input-sm" list="repColumns" multiple="true" />
                                        </td>
                                        <td valign="middle" align="center" style="width: 10%;">
                                            <button type="button" class="btn btn-warning leads-tab"  style="width: 42px;" 
                                                    onclick="moveOptionUp(document.getElementById('selectedColumnsId'), 'key', '');" ><i class="fa fa-chevron-up"></i></button>
                                            <br><br>
                                            <button type="button" class="btn btn-warning leads-tab"  style="width: 42px;" 
                                                    onclick="moveOptionDown(document.getElementById('selectedColumnsId'), 'key', '');" ><i class="fa fa-chevron-down"></i></button>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                <h4  class="panel-title" onclick="checkColumnsSelected('1')" style="color:black;">
                                    <s:text name="lbl.campaign.step2.filter"/>
                                </h4>
                            </a>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo" style="height:0px;">
                            <div class="panel-body">
                                <div class="col-sm-12 col-md-3 board-icons1 Rbtn" id="e_filter" style="display: none;"></div>
                                <button type="button" class="btn btn-warning tmargin cbtn" style="display:none;float: right;" onclick="AddFilterRow();" id="2Btn" >
                                    <s:text name="btn.add"/>
                                </button>
                                <div id="FilterTableId" class="mb-10" style="margin-top: 10px;">
                                    <s:iterator var="filterList" value="dataParam" status="stat">
                                        <div id="FilterRow_<s:property value="#stat.index" />" class="col-wdth-100 mt-10">
                                            <s:if test="%{#stat.index != 0}">   
                                                <div class="col-wdth-12" id="mdp_%{#stat.index}">
                                                    <s:select name="dataParam[%{#stat.index}].mdpCondition" id="mdpCondition_%{#stat.index}" list="#{'AND':'And', 'OR':'Or'}" cssClass="form-control input-sm" />
                                                </div>
                                            </s:if>
                                            <s:else>
                                                <div class="col-wdth-12" style="visibility : hidden;">  
                                                    <s:select  disabled="disabled" id="mdpCondition_%{#stat.index}" list="#{'AND':'And', 'OR':'Or'}" cssClass="form-control input-sm" />
                                                </div>
                                            </s:else>
                                            <div class="col-wdth-30">
                                                <s:select name="dataParam[%{#stat.index}].mdpColumn" list="filterColumns" cssClass="form-control input-sm" id="mdpColumn_%{#stat.index}"
                                                          listKey="key" listValue="value" listTitle="info" headerKey="" headerValue="-Select-" label="info1" 
                                                          onchange="DataTypeChange(this.id, '%{#stat.index}');" />
                                            </div>
                                            <div class="col-wdth-18">
                                                <s:if test='"D".equalsIgnoreCase(dataParam[#stat.index].mdpDataType) || "T".equalsIgnoreCase(dataParam[#stat.index].mdpDataType)'>
                                                    <s:select name="dataParam[%{#stat.index}].mdpOperator" id="mdpOperator_%{#stat.index}" list="condTypes" cssClass="form-control input-sm"
                                                              headerKey="bp" headerValue="By Period" 
                                                              onchange="DataTypeChange('mdpColumn_%{#stat.index}', '%{#stat.index}');" />
                                                    <!--   onchange="OperatorTypeChange(this.value, '%{#stat.index}');"  -->
                                                </s:if>
                                                <s:else>
                                                    <s:select name="dataParam[%{#stat.index}].mdpOperator" id="mdpOperator_%{#stat.index}" list="condTypes" cssClass="form-control input-sm"
                                                              onchange="DataTypeChange('mdpColumn_%{#stat.index}', '%{#stat.index}');" />
                                                    <!-- onchange="OperatorTypeChange(this.value, '%{#stat.index}');" /> -->
                                                </s:else>
                                            </div>
                                            <s:if test='"BETWEEN".equalsIgnoreCase(dataParam[#stat.index].mdpOperator)'>
                                                <div id="mdpValuesText_<s:property value="#stat.index" />" class="col-wdth-30">
                                                    <div class="col-wdth-50">
                                                        <s:textfield name="dataParam[%{#stat.index}].mdpValueFm" id="mdpValueFm_%{#stat.index}" cssClass='form-control %{"N".equals(dataParam[#stat.index].mdpDataType)?"decimal":(("D".equals(dataParam[#stat.index].mdpDataType) || "T".equals(dataParam[#stat.index].mdpDataType))?"datepickerFrom":"")}' />
                                                    </div>
                                                    <div class="col-wdth-50">
                                                        <s:textfield name="dataParam[%{#stat.index}].mdpValueTo" id="mdpValueTo_%{#stat.index}" cssClass='form-control %{"N".equals(dataParam[#stat.index].mdpDataType)?"decimal":""}' />
                                                    </div>
                                                </div>
                                                <div id="mdpValuesHidden_<s:property value="#stat.index"/>" class="col-wdth-30" style="display: none;">
                                                    <input type="hidden" name="dataParam<s:property value="#stat.index" />.mdpValueFm" id="mdpValueHidden_<s:property value="#stat.index" />" class="form-control" style="display: none;" disabled>
                                                </div>
                                                <div id="mdpValuesSelect_<s:property value="#stat.index"/>" style="display:none;" class="col-wdth-30">
                                                    <select name="dataParam<s:property value="#stat.index"/>.mdpValueFm" id="mdpValueSelect_<s:property value="#stat.index"/>" class="form-control input-sm" 
                                                            style="display: none;"></select></div>
                                                </s:if>
                                                <s:elseif test='"bp".equalsIgnoreCase(dataParam[#stat.index].mdpOperator)'>
                                                <div id="mdpValuesSelect_<s:property value="#stat.index" />" class="col-wdth-30">
                                                    <s:select name="dataParam[%{#stat.index}].mdpValueFm" list="dtFilterTypes" id="mdpValueFm_%{#stat.index}" cssClass='form-control input-sm' />
                                                </div>
                                                <div id="mdpValuesText_<s:property value="#stat.index" />" class="col-wdth-30" style="display: none;">
                                                    <span role="status" aria-live="polite" class="ui-helper-hidden-accessible"></span>
                                                    <input type="text" name="mdpValueFm_desc" id="mdpValueText_<s:property value="#stat.index" />" class="form-control ui-autocomplete-input" autocomplete="off" style="display: none;" disabled="disabled">
                                                </div>
                                            </s:elseif>
                                            <s:else>
                                                <div id="mdpValuesText_<s:property value="#stat.index" />" class="col-wdth-30">
                                                    <s:textfield name="dataParam[%{#stat.index}].mdpValueFm" id="mdpValueFm_%{#stat.index}" cssClass='form-control %{"N".equals(dataParam[#stat.index].mdpDataType)?"decimal":(("D".equals(dataParam[#stat.index].mdpDataType) || "T".equals(dataParam[#stat.index].mdpDataType))?"datepickerFrom":"")}' />
                                                </div>
                                                <div id="mdpValuesHidden_<s:property value="#stat.index"/>" class="col-wdth-30" style="display: none;">
                                                    <input type="hidden" name="dataParam<s:property value="#stat.index" />.mdpValueFm" id="mdpValueHidden_<s:property value="#stat.index" />" class="form-control" style="display: none;" disabled="disabled">
                                                </div>
                                                <div id="mdpValuesSelect_<s:property value="#stat.index"/>" class="col-wdth-30" style="display:none;">
                                                    <select name="dataParam<s:property value="#stat.index"/>.mdpValueFm" id="mdpValueSelect_<s:property value="#stat.index"/>" class="form-control input-sm" 
                                                            style="display: none;"></select></div>
                                                </s:else>
                                                <s:hidden name="dataParam[%{#stat.index}].mdpDataType" id="mdpDataType_%{#stat.index}" cssClass="form-control" />
                                            <div class="col-wdth-10">
                                                <button type="button" class="btn bg-red waves-effect" onclick="RemoveFilterRow('<s:property value="#stat.index" />');"><i class="fa fa-remove"></i></button>
                                            </div>
                                        </div>
                                    </s:iterator>
                                </div>
                            </div>
                        </div>
                    </div>
                </s:if>
            </div>
        </s:form>
        <s:if test='"E".equals(campaign.mcDataSourceType)'>
            <s:if test='"P".equals(campaign.mcStatus)'>
                <div class="ExcelFileDivId col-lg-6 col-md-6 col-sm-9 col-xs-12">
                    <s:form name="frm_upl_camp" id="frm_upl_camp">
                        <ul>
                            <li>
                                <label><s:text name="lbl.file.path"/> <span>*</span></label>
                                <div>
                                    <a href="<%=request.getContextPath()%>/Forms_Templates/MAIL_UPL_TEMPLATE.xls"><s:text name="lbl.common.download.excel"/>.</a>
                                </div>
                            </li>
                        </ul>
                        <div class="form-group">
                            <a class='btn btn-primary' href='javascript:;'>
                                <s:file  name="campaign.excelFile" cssStyle="width: 350px;"  accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
                            </a>
                        </div>
                    </s:form>
                </div>
                <div class="form-group col-md-7" style="margin-top: 32px; float: right">
                    <!--button type="button" class="btn btn-primary leads-tab" onclick="validate();" style="margin-top: -8px" data-toggle="tooltip" data-placement="top" title="Validate"><i class="fa fa-check"></i> Validate</button-->
                    <button type="button" class="btn btn-primary leads-tab" onclick="uploadCampaign();" style="margin-top: -8px" data-toggle="tooltip" data-placement="top" title="Validate"><i class="fa fa-check"></i> <s:text name="btn.validate"/></button>
                </div>
                <div class="form-group col-md-7" style="margin-top: 50px; float: right" id="div_count">
                    <b><s:text name="lbl.common.total"/>&nbsp;<span  id="row_count"> <s:text name="lbl.common.records.found"/></span></b><br>
                    <b><span id="invalid_row_count"></span></b><br>
                </div>
            </s:if>
        </s:if>
        <div class="row b-t mt-10" id="div_btns">
            <div class="col-md-12 form-group center mt-30">
                <button type="button" class="btn btn-primary leads-tab" onclick="goBackWard();"> <s:text name="btn.back"/> <i class="fa fa-arrow-circle-left"></i></button>
                <button type="button" class="btn btn-primary leads-tab" style="float: right;" onclick="saveDataParamFilter();"> <s:text name="btn.proceed"/> <i class="fa fa-arrow-circle-right"></i></button>
            </div>
        </div>
    </div>

</div>

<s:select id="filterColumnsListId" list="filterColumns" cssStyle="display: none;" listKey="key" listValue="value" 
          listTitle="info" headerKey="" headerValue="-Select-" cssClass="form-control input-sm" />
<s:select id="filterCondtListId" list="condTypes" cssStyle="display: none;" cssClass="form-control input-sm" />
<s:select id="filterDateListId" list="dtFilterTypes" cssStyle="display: none;" cssClass="form-control input-sm" />

<script type="text/javascript">

    <%----%>
    var filterRowId = "<s:property value="dataParam==null?0:dataParam.size()" />";
    var sortingRowId = "<s:property value="repSort==null?0:repSort.size()" />";
    $("#div_count").hide();
    if ($("#mcStatus").val() !== "P") {
        $('#ReportMktgForm button,#ReportMktgForm input, #ReportMktgForm select').prop('disabled', true);
    }
    // initAggregateFunction();
    function RemoveSortingRow(rowId) {
        $("#SortingRow_" + rowId).remove();
        return false;
    }

    function AddSortingRow() {
        var isProceed = true;
        $('[id^="sel_sortby_"]').each(function (i, v) {
            if ($.trim(this.value).length == 0)
            {
                isProceed = false;
                $(this).focus();
                return false;
            }
        });
        if (!isProceed)
        {
            showMsg('e_sortby', 'Please select filter');
            return false;
        }


        var filterHtml = '<div id="SortingRow_' + sortingRowId + '" class="col-wdth-100">';
        var columnSelect = '<div class="col-wdth-50 mt-10"><select name="repSort[' + sortingRowId + '].mdpColumn" class="form-control input-sm" id="sel_sortby_' + sortingRowId + '">';
        columnSelect += '<option value="">-Select-</option>';
        $("#selectedColumnsId option").each(function (i) {
            columnSelect += '<option value="' + $(this).val() + '">' + $(this).text() + '</option>';
        });
        columnSelect += '</select></div>';
        var condtSelect = '<div class="col-wdth-30 mt-10"><select name="repSort[' + sortingRowId + '].mdpOrder" class="form-control input-sm"><option value="ASC">Asc</option><option value="DESC">Desc</option></select></div>';
        filterHtml += columnSelect + condtSelect;
        filterHtml += '<div class="col-wdth-20 mt-10"><button type="button" class="btn bg-red waves-effect" onclick="RemoveSortingRow(\'' + sortingRowId + '\');"><i class="fa fa-remove"></i></button></div></div>';
        $("#SortingTableId").append(filterHtml);
        sortingRowId++;
        return false;
    }

    function AppliedToUsers() {
        $("#AppliedToRoleDivId").hide();
        $("#selectedUsersId").removeAttr("disabled");
        $("#AppliedToUserDivId").show();
        $("#selectedRolesId").attr("disabled", "disabled");
        return false;
    }

    function AppliedToRoles() {
        $("#AppliedToUserDivId").hide();
        $("#selectedUsersId").attr("disabled", "disabled");
        $("#AppliedToRoleDivId").show();
        $("#selectedRolesId").removeAttr("disabled");
        return false;
    }

    $(function () {
        if ($('#btnSave').length)
        {
            $('[id^="FilterRow_"]').each(function (i) {
                var fmValue = "";
                var toValue = "";
                var oper = $('#mdpOperator_' + i).val();
                if (oper == 'BETWEEN' || oper == 'NOT BETWEEN')
                {
                    if ($('input[name="dataParam[' + i + '].mdpValueFm"]').length && $('input[name="dataParam[' + i + '].mdpValueFm"]').parent().css('display') != 'none')
                        fmValue = $('input[name="dataParam[' + i + '].mdpValueFm"]').val();
                    else if ($('select[name="dataParam[' + i + '].mdpValueFm"]').length && $('select[name="dataParam[' + i + '].mdpValueFm"]').parent().css('display') != 'none')
                        fmValue = $('select[name="dataParam[' + i + '].mdpValueFm"]').val();

                    if ($('input[name="dataParam[' + i + '].mdpValueTo"]').length && $('input[name="dataParam[' + i + '].mdpValueTo"]').parent().css('display') != 'none')
                        toValue = $('input[name="dataParam[' + i + '].mdpValueTo"]').val();
                    else if ($('select[name="dataParam[' + i + '].mdpValueTo"]').length && $('select[name="dataParam[' + i + '].mdpValueTo"]').parent().css('display') != 'none')
                        toValue = $('select[name="dataParam[' + i + '].mdpValueTo"]').val();
                } else
                {
                    if ($('input[name="dataParam[' + i + '].mdpValueFm"]').length && $('input[name="dataParam[' + i + '].mdpValueFm"]').parent().css('display') != 'none')
                        fmValue = $('input[name="dataParam[' + i + '].mdpValueFm"]').val();
                    else if ($('select[name="dataParam[' + i + '].mdpValueFm"]').length && $('select[name="dataParam[' + i + '].mdpValueFm"]').parent().css('display') != 'none')
                        fmValue = $('select[name="dataParam[' + i + '].mdpValueFm"]').val();
                }

                DataTypeChange('mdpColumn_' + i, i);
                //alert('fmValue='+fmValue+", toValue="+toValue);

                if (oper == 'BETWEEN' || oper == 'NOT BETWEEN')
                {
                    if ($('#mdpValuesText_' + i).css('display') != 'none')
                    {
                        $('input[name="dataParam[' + i + '].mdpValueFm"]').val(fmValue);
                        $('input[name="dataParam[' + i + '].mdpValueTo"]').val(toValue);
                    } else if ($('#mdpValuesSelect_' + i).css('display') != 'none')
                    {
                        $('select[name="dataParam[' + i + '].mdpValueFm"]').val(fmValue);
                        $('select[name="dataParam[' + i + '].mdpValueTo"]').val(toValue);
                    }
                } else
                {
                    if ($('#mdpValuesText_' + i).css('display') != 'none')
                    {
                        $('input[name="dataParam[' + i + '].mdpValueFm"]').val(fmValue);
                    } else if ($('#mdpValuesSelect_' + i).css('display') != 'none')
                    {
                        $('select[name="dataParam[' + i + '].mdpValueFm"]').val(fmValue);
                    }
                }

            });
        }
    });
    function checkColumnsSelected(indx) {
        if (indx != 0) {
            $("#selectedColumnsId option").prop('selected', true);
            if ($("#selectedColumnsId :selected").length <= 0) {
                $("#ReportsDesignerErrorDiv").html("Select columns to display before proceeding");
                $("#ReportsDesignerErrorDiv").show();
                //$('.panel-collapse').removeClass('in').attr('aria-expanded', 'false');
                //$('#'+indx).addClass('in').attr('aria-expanded', 'true');
                //return false;
            }
            if (indx === '1' || indx === '2') {
                $("#2Btn").attr('style', 'display:block; float:right;');
                $("#3Btn").attr('style', 'display:block;');
            }
        }         /*alert($('#mdpValueFm_0').val()+" : "+$('#mdpColumn_0').val());
         var prevVal = $('#mdpValueFm_0').val();
         DataTypeChange('mdpColumn_0', '0');
         $('#mdpValueFm_0').val(prevVal);
         $('#mdpSelect_0').val(prevVal).trigger("change");*/

    }

    function saveDataParamFilter() {
        $("#selectedColumnsId option").prop('selected', true);
        if ($('#mcStatus').val() !== 'P')
        {
            openCampaignStep(3);
        } else {

            block('block_body');
            $.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}/camp/saveDataParamFilter.do?mcCampId=' + $("#mcCampId").val(),
                data: $("#ReportMktgForm").serialize(),
                async: true,
                success: function (result) {
                    if (result.messageType === "S") {
                        $.notify("Data Filter Updated Successfully", "custom");
                        $("#ReportMktgForm").attr("action", "<%=request.getContextPath()%>/camp/openDataMappings.do?mcCampId=" + $("#mcCampId").val());
                        $("#ReportMktgForm").submit()
                    } else {
                        $.notify(result.message, "custom");
                    }
                },
                complete: function () {
                    unblock('block_body');
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                    block('block_body');
                }
            });
        }
    }

    function validate() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: '${pageContext.request.contextPath}/camp/saveUploadCampaign.do?mcCampId=' + $("#mcCampId").val(),
            async: true,
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify("Data Uploaded  Successfully", "custom");
                    $("#row_count").empty().html(result.rowsCount + " Records Found");
                    $("#div_count").show();
                } else {
                    $.notify(result.message, "custom");
                }
            },
            complete: function () {
                unblock('block_body');
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
                block('block_body');
            }
        });
    }

    function goBackWard() {
        if ($('#mcStatus').val() !== 'P') {
            openCampaignStep(1);
        } else {
            $("#ReportMktgForm").attr("action", "<%=request.getContextPath()%>/camp/openCampaignForm.do?oper=edit&mcCampId=" + $("#mcCampId").val());
            $("#ReportMktgForm").submit()
        }
    }

    function uploadCampaign() {
        block('block_body');
        var form = $('#frm_upl_camp')[0];
        var data = new FormData(form);
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/saveUploadExcelCampaign.do?mcCampId=" + $("#mcCampId").val(),
            data: data,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify("Data Uploaded  Successfully", "custom");
                    $("#row_count").empty().html(result.rowsCount + " Records Found");
                    $("#div_count").show();
                    if (result.message != null) {
                        $("#invalid_row_count").empty().html(result.message);
                    }
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

</script>
