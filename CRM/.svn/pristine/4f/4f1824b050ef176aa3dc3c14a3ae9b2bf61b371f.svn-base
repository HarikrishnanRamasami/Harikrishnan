<%-- 
    Document   :columnAndFilters
    Created on : Oct 26, 2015, 11:43:33 AM
    Author     : palani.rajan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%--@page errorPage="/jsp/common/ErrorMsg.jsp" --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/struts/optiontransferselect.js"></script>
<%--<script type="text/javascript" src="<%=request.getContextPath()%>/javascript/codeDesc.js"></script>--%>

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
<!--Applying New Style for Accordion--Begin-->
<div class="col-md-12">
    <div class="panel-group" id="accordion" role="tablist">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingOne">
                <h4  class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne"><s:text name="lbl.reports.select.columns.to.display"/></a>
                </h4>
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
                                        onclick="detachAggregateFunction('S');
                                                setTimeout(moveSelectedOptions(document.getElementById('selectedColumnsId'), document.getElementById('allColumnsId'), false, ''), 10);"><i class="fa fa-chevron-left"></i></button><br><br>
                                <button type="button" class="btn btn-warning leads-tab"  style="width: 42px;"
                                        onclick="moveSelectedOptions(document.getElementById('allColumnsId'), document.getElementById('selectedColumnsId'), false, '');
                                                setTimeout(attachAggregateFunction('S'), 10);"><i class="fa fa-chevron-right"></i></button><br><br>
                                <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                        onclick="detachAggregateFunction('A');
                                                setTimeout(moveAllOptions(document.getElementById('selectedColumnsId'), document.getElementById('allColumnsId'), false, ''), 10);"><i class="fa fa-step-backward"></i></button><br><br>
                                <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                        onclick="moveAllOptions(document.getElementById('allColumnsId'), document.getElementById('selectedColumnsId'), false, '');
                                                setTimeout(attachAggregateFunction('A'), 10);"><i class="fa fa-step-forward"></i></button><br><br>
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
                <h4  class="panel-title" onclick="checkColumnsSelected('1')">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo"><s:text name="lbl.reports.step.two.filter"/></a>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo" style="height:0px;">
                <div class="panel-body">
                    <div class="col-sm-12 col-md-3 board-icons1 Rbtn" id="e_filter" style="display: none;"></div>
                    <button type="button" class="btn btn-warning tmargin cbtn" style="display:none;float: right;" onclick="AddFilterRow();" id="2Btn" >
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
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingThree">
                <h4  class="panel-title" onclick="checkColumnsSelected('2')">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree"><s:text name="lbl.reports.sort.by"/></a>
                </h4>
            </div>
            <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree" >
                <div class="panel-body">
                    <div class="col-sm-12 col-md-3 board-icons1 Rbtn" id="e_sortby"></div>
                    <button type="button" class="btn btn-warning tmargin cbtn" id="3Btn" onclick="AddSortingRow();">
                        <s:text name="btn.add"/>
                    </button>
                    <div id="SortingTableId" class="mb-10">
                        <s:iterator var="sortList" value="repSort" status="stat">
                            <div id="SortingRow_<s:property value="#stat.index" />" class="col-wdth-100">
                                <div class="col-wdth-50 mt-10">
                                    <s:select name="repSort[%{#stat.index}].urpColumn" list="repColumns" cssClass="form-control input-sm" 
                                              headerKey="" headerValue="-Select-" id="sel_sortby_%{#stat.index}" />
                                </div>
                                <div class="col-wdth-30 mt-10">
                                    <s:select name="repSort[%{#stat.index}].urpOrder" list="#{'ASC':'Asc', 'DESC':'Desc'}" cssClass="form-control input-sm" />
                                </div>
                                <div class="col-wdth-20 mt-10">
                                    <button type="button"  class="btn bg-red waves-effect" onclick="RemoveSortingRow('<s:property value="#stat.index" />');"><i class="fa fa-remove"></i></button>
                                </div>
                            </div>
                        </s:iterator>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFour">
                <h4  class="panel-title" onclick="checkColumnsSelected('3')">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour"><s:text name="lbl.reports.group.by"/></a>
                </h4>
            </div>
            <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour" aria-expanded="true">
                <div class="panel-body">
                    <table border="0" style="margin: 10px; width: 96%;">
                        <tr> 
                            <td style="width: 38%;">
                                <s:select name="actualColumns" size="15" id="allColumnsgrpId" cssClass="form-control input-sm" list="actualColumns" multiple="true" listKey="key" listTitle="info" listValue="value"/>
                            </td>
                            <td valign="middle" align="center" style="width: 2%;">
                                <button type="button" class="btn btn-warning leads-tab"  style="width: 42px;"
                                        onclick="moveSelectedOptions(document.getElementById('selectedColumnsgrpId'), document.getElementById('allColumnsgrpId'), false, '');"><i class="fa fa-chevron-left"></i></button><br><br>
                                <button type="button" class="btn btn-warning leads-tab"  style="width: 42px;"
                                        onclick="moveSelectedOptions(document.getElementById('allColumnsgrpId'), document.getElementById('selectedColumnsgrpId'), false, '');"><i class="fa fa-chevron-right"></i></button><br><br>
                                <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                        onclick="moveAllOptions(document.getElementById('selectedColumnsgrpId'), document.getElementById('allColumnsgrpId'), false, '');"><i class="fa fa-step-backward"></i></button><br><br>
                                <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                        onclick="moveAllOptions(document.getElementById('allColumnsgrpId'), document.getElementById('selectedColumnsgrpId'), false, '');"><i class="fa fa-step-forward"></i></button><br><br>
                            </td>
                            <td style="width: 38%;">
                                <s:select name="repGroup" size="15" id="selectedColumnsgrpId" cssClass="form-control input-sm" list="repGroup" multiple="true" />
                            </td>
                            <td valign="middle" align="center" style="width: 10%;">
                                <button type="button" class="btn btn-warning leads-tab"  style="width: 42px;" 
                                        onclick="moveOptionUp(document.getElementById('selectedColumnsgrpId'), 'key', '');" ><i class="fa fa-chevron-up"></i></button>
                                <br><br>
                                <button type="button" class="btn btn-warning leads-tab"  style="width: 42px;" 
                                        onclick="moveOptionDown(document.getElementById('selectedColumnsgrpId'), 'key', '');" ><i class="fa fa-chevron-down"></i></button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<!--Applying New Style for Accordion--End-->
<s:select id="filterColumnsListId" list="filterColumns" cssStyle="display: none;" listKey="key" listValue="value" 
          listTitle="info" headerKey="" headerValue="-Select-" cssClass="form-control input-sm" />
<s:select id="filterCondtListId" list="condTypes" cssStyle="display: none;" cssClass="form-control input-sm" />
<s:select id="filterDateListId" list="dtFilterTypes" cssStyle="display: none;" cssClass="form-control input-sm" />

<div class="modal fade" id="ReportSaveInfoDivId" role="dialog" aria-labelledby="report_modal_label" data-keyboard="false" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="report_modal_label"><s:text name="lbl.reports.head.report.information"/></h4>
            </div>
            <div class="modal-body" id="report_modal_body">
                <div class="row" style="padding: 10px;">
                    <div id="ReportsSaveErrorDiv" class="alert alert-danger" style="display: <s:if test="hasActionErrors()">block</s:if><s:else>none</s:else>;" >
                        <p><s:text name="error.title" /></p>
                        <s:iterator value="actionErrors">
                            <ul><li><s:property escapeHtml="false"/></li></ul>
                                </s:iterator>
                    </div>
                    <div class="col-md-12">
                        <div class="col-md-2">
                            <Strong> <s:text name="lbl.reports.report.name"/> </strong>
                        </div>
                        <div class="col-md-4">
                            <s:hidden name="reportId" id="reportId" />
                            <s:textfield name="reportName" id="reportNameText" cssClass="form-control" />
                        </div>
                        <div class="col-md-2">&nbsp;</div>
                        <div class="col-md-4">&nbsp;</div>
                    </div>
                    <div class="col-md-12 mt-10">
                        <div class="col-md-2">
                            <Strong> <s:text name="lbl.description"/> </strong>
                        </div>
                        <div class="col-md-10">
                            <s:textarea name="reportRemarks" id="reportRemarks" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="col-md-12 mt-10">
                        <div class="col-md-2">
                            <Strong> <s:text name="lbl.reports.applicable.to"/> </strong>
                        </div>                    
                        <div class="col-md-4">
                            <input type="radio"  id="u_radio" name="reportSec" value="U" class="with-gap radio-col-red" <s:if test='!"R".equalsIgnoreCase(reportSec)' >checked</s:if>
                                   onclick="AppliedToUsers();" />  <label for="u_radio"><s:text name="lbl.reports.users"/></label>
                                &nbsp;&nbsp;
                                <input type="radio"id="r_radio"  name="reportSec" value="R" class="with-gap radio-col-red" <s:if test='"R".equalsIgnoreCase(reportSec)'>checked</s:if>
                                   onclick="AppliedToRoles();" /> <label for="r_radio"><s:text name="lbl.reports.roles"/></label>
                            </div>
                            <div class="col-md-6" id="AppliedToUserDivId" style="display: <s:if test='!"R".equalsIgnoreCase(reportSec)'>block;</s:if><s:else>none;</s:else>">
                                <table border="0">
                                    <tr> 
                                        <td style="width: 45%;">
                                        <s:select size="15" id="allUsersId" cssClass="form-control input-sm" list="usersList" multiple="true" />
                                    </td>
                                    <td valign="middle" align="center" style="width: 10%;">
                                        <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                                onclick="moveSelectedOptions(document.getElementById('selectedUsersId'), document.getElementById('allUsersId'), false, '');"><i class="fa fa-custom fa-angle-left"/></button><br><br>
                                        <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                                onclick="moveSelectedOptions(document.getElementById('allUsersId'), document.getElementById('selectedUsersId'), false, '');"><i class="fa fa-custom fa-angle-right"/></button><br><br>
                                        <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                                onclick="moveAllOptions(document.getElementById('selectedUsersId'), document.getElementById('allUsersId'), false, '');"><i class="fa fa-custom fa-angle-double-left"/></button><br><br>
                                        <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                                onclick="moveAllOptions(document.getElementById('allUsersId'), document.getElementById('selectedUsersId'), false, '');"><i class="fa fa-custom fa-angle-double-right"/></button><br><br>
                                    </td>
                                    <td style="width: 45%;">
                                        <s:select name="reportUsers" size="15" id="selectedUsersId" cssClass="form-control input-sm" list="usersAllList" multiple="true"
                                                  disabled='%{"R".equalsIgnoreCase(reportSec)}' />
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="col-md-6" id="AppliedToRoleDivId" style="display: <s:if test='"R".equalsIgnoreCase(reportSec)'>block;</s:if><s:else>none;</s:else>">
                                <table border="0">
                                    <tr> 
                                        <td style="width: 45%;">
                                        <s:select size="15" id="allRolesId" cssClass="form-control input-sm" list="rolesList" multiple="true" />
                                    </td>
                                    <td valign="middle" align="center" style="width: 10%;">
                                        <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                                onclick="moveSelectedOptions(document.getElementById('selectedRolesId'), document.getElementById('allRolesId'), false, '');"><i class="fa fa-chevron-left"></i> </button><br><br>
                                        <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                                onclick="moveSelectedOptions(document.getElementById('allRolesId'), document.getElementById('selectedRolesId'), false, '');"><i class="fa fa-chevron-right"></i> </button><br><br>
                                        <button type="button" class="btn btn-warning leads-tab" style="width: 42px;" 
                                                onclick="moveAllOptions(document.getElementById('selectedRolesId'), document.getElementById('allRolesId'), false, '');"><i class="fa fa-step-backward"> </i></button><br><br>
                                        <button type="button" class="btn btn-warning leads-tab" style="width: 42px;"
                                                onclick="moveAllOptions(document.getElementById('allRolesId'), document.getElementById('selectedRolesId'), false, '');"><i class="fa fa-step-forward"></i> </button><br><br>
                                    </td>
                                    <td style="width: 45%;">
                                        <s:select name="reportRoles" size="15" id="selectedRolesId" cssClass="form-control input-sm" list="rolesAllList" multiple="true"
                                                  disabled='%{!"R".equalsIgnoreCase(reportSec)}' />
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-12 mt-10" align="center" style="margin-top: 10px">
                        <button id="btnSave" type="button" class="close-btn btn" style="margin-right: 10px; margin-top: 14px" data-dismiss="modal">
                            <s:text name="btn.cancel"/>
                        </button>
                        <button id="btnSave" type="button" class="btn btn-primary leads-tab" style="margin-right: 10px; margin-top: 14px" onclick="saveReportEnquiry();">
                            <s:text name="btn.submit"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    <%----%>
    var filterRowId = "<s:property value="repFilter==null?0:repFilter.size()" />";
    var sortingRowId = "<s:property value="repSort==null?0:repSort.size()" />";
    initAggregateFunction();
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
        var columnSelect = '<div class="col-wdth-50 mt-10"><select name="repSort[' + sortingRowId + '].urpColumn" class="form-control input-sm" id="sel_sortby_' + sortingRowId + '">';
        columnSelect += '<option value="">-Select-</option>';
        $("#selectedColumnsId option").each(function (i) {
            columnSelect += '<option value="' + $(this).val() + '">' + $(this).text() + '</option>';
        });
        columnSelect += '</select></div>';
        var condtSelect = '<div class="col-wdth-30 mt-10"><select name="repSort[' + sortingRowId + '].urpOrder" class="form-control input-sm"><option value="ASC">Asc</option><option value="DESC">Desc</option></select></div>';
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
                var oper = $('#urpOperator_' + i).val();
                if (oper == 'btw' || oper == 'nbtw')
                {
                    if ($('input[name="repFilter[' + i + '].urpValueFm"]').length && $('input[name="repFilter[' + i + '].urpValueFm"]').parent().css('display') != 'none')
                        fmValue = $('input[name="repFilter[' + i + '].urpValueFm"]').val();
                    else if ($('select[name="repFilter[' + i + '].urpValueFm"]').length && $('select[name="repFilter[' + i + '].urpValueFm"]').parent().css('display') != 'none')
                        fmValue = $('select[name="repFilter[' + i + '].urpValueFm"]').val();

                    if ($('input[name="repFilter[' + i + '].urpValueTo"]').length && $('input[name="repFilter[' + i + '].urpValueTo"]').parent().css('display') != 'none')
                        toValue = $('input[name="repFilter[' + i + '].urpValueTo"]').val();
                    else if ($('select[name="repFilter[' + i + '].urpValueTo"]').length && $('select[name="repFilter[' + i + '].urpValueTo"]').parent().css('display') != 'none')
                        toValue = $('select[name="repFilter[' + i + '].urpValueTo"]').val();
                }
                else
                {
                    if ($('input[name="repFilter[' + i + '].urpValueFm"]').length && $('input[name="repFilter[' + i + '].urpValueFm"]').parent().css('display') != 'none')
                        fmValue = $('input[name="repFilter[' + i + '].urpValueFm"]').val();
                    else if ($('select[name="repFilter[' + i + '].urpValueFm"]').length && $('select[name="repFilter[' + i + '].urpValueFm"]').parent().css('display') != 'none')
                        fmValue = $('select[name="repFilter[' + i + '].urpValueFm"]').val();
                }

                DataTypeChange('urpColumn_' + i, i);
                //alert('fmValue='+fmValue+", toValue="+toValue);

                if (oper == 'btw' || oper == 'nbtw')
                {
                    if ($('#urpValuesText_' + i).css('display') != 'none')
                    {
                        $('input[name="repFilter[' + i + '].urpValueFm"]').val(fmValue);
                        $('input[name="repFilter[' + i + '].urpValueTo"]').val(toValue);
                    }
                    else if ($('#urpValuesSelect_' + i).css('display') != 'none')
                    {
                        $('select[name="repFilter[' + i + '].urpValueFm"]').val(fmValue);
                        $('select[name="repFilter[' + i + '].urpValueTo"]').val(toValue);
                    }
                }
                else
                {
                    if ($('#urpValuesText_' + i).css('display') != 'none')
                    {
                        $('input[name="repFilter[' + i + '].urpValueFm"]').val(fmValue);
                    }
                    else if ($('#urpValuesSelect_' + i).css('display') != 'none')
                    {
                        $('select[name="repFilter[' + i + '].urpValueFm"]').val(fmValue);
                    }
                }

            });
        }
    });
</script>