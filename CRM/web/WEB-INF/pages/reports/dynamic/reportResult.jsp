<%-- 
    Document   : report Result
    Created on : Oct 26, 2015, 11:43:33 AM
    Author     : palani.rajan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%--@page errorPage="/jsp/common/ErrorMsg.jsp" --%>
<!DOCTYPE html>
<style>
    .scrolledTable{ overflow-y: auto; clear:both; white-space :nowrap;}    
</style>
<div class="col-md-12 right-pad">
    <!--    <div class="col-md-12">-->
    <div class="dash-leads" style="border-top:0!important">
        <div  class="form-group mt-20" >
            <div class="my-bord bor2">
                <div id="ReportsDesignerErrorDiv" class="alert alert-danger"
                     style="display: <s:if test="hasActionErrors()">block;</s:if><s:else>none;</s:else>">
                    <s:iterator value="actionErrors">
                        <h5><span class="mandatory">*</span><s:property /></h5>
                    </s:iterator>
                </div> 
                <s:form name="ExcelExportForm" id="ExcelExportForm" method="post" action="">
                    <s:hidden name="reportTitle" />
                    <s:hidden name="xlRepColumns" />
                    <s:hidden name="xlRepDatas" />
                    <s:hidden name="pluginFor" />
                    <s:hidden name="reportType" id="reportType"/>
                </s:form>
                <div class="">
                    <div class="board-icons1">
                        <button id="btnRptRsltBack" type="button" class="btn btn-warning tmargin cbtn" onclick="backBtnAction();">
                        <s:text name="btn.back"/>
                        </button>
                    </div>
                    <s:if test='"salesforce".equals(pluginFor)'>
                        <div class="board-icons1">
                            <button id="btnRptRsltBack" type="button" class="btn btn-warning tmargin cbtn" onclick="pushReport();">
                                <s:text name="btn.push"/>
                            </button>
                        </div>
                    </s:if>
                    <s:if test='#request.reportColumnsSelected.size()>0'>
                        <div class="col-sm-12 col-md-3 board-icons1 Rbtn">
                            <button id="btnGo" type="button" class="btn btn-warning tmargin cbtn" onclick="exportToExcel();">
                                <s:text name="btn.excel"/>
                            </button>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div id="datatable_search" style="float: right; margin-right: -970px;margin-top: 48px;">
                                    &nbsp;
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row mt-20">
                    <div class="col-md-12">
                        <div id="ExportAreaDivId">
                            <table id="ReportResultTable" align="center" cellpadding='0' border="0" class="table table-striped table-bordered display dataTable dtr-inline">
                                <thead>
                                    <tr>
                                        <s:iterator value="#request.reportColumnsSelected" var="repColumnsVar">
                                            <th class="text-center"><s:property /></th>
                                            </s:iterator>
                                    </tr>
                                </thead>
                                <s:if test='#request.reportDataList!=null && #request.reportDataList.size()>0'>
                                    <s:iterator value="#request.reportDataList" var="repDataVar">
                                        <tr>
                                            <s:iterator value="#request.reportColumnsSelected" var="repColumnsVar">
                                                <s:iterator value="#repDataVar[#repColumnsVar]">
                                                    <td><s:property value="key" /></td>
                                                </s:iterator>
                                            </s:iterator>
                                        </tr>
                                    </s:iterator>
                                </s:if>
                            </table>
                        </div>
                    </div>
                </div>
                <script type="text/javascript">
                    var tbl = $("#ReportResultTable").DataTable({
                        paging: true,
                        searching: true,
                        pageLength: 10,
                        ordering: true,
                        info: false,
                        lengthChange: false,
                        responsive: true,
                        autoWidth: true,
                        dom: 'lfrtpT',
                        /*tableTools: {
                         "sRowSelect": "single",
                         "aButtons": ["xls", "print"]
                         }*/
                        initComplete: function () {
                            //$('#datatable_search').html($('#ReportResultTable_filter input').attr('placeholder', 'Search..'));
                        }
                    });
                    $('#ReportResultTable').wrap("<div class='scrolledTable'></div>");
                    function exportToExcel() {
                        $("#ExcelExportForm").attr("action", "<%=request.getContextPath()%>/ExportExcelRpt.do");
                        $("#ExcelExportForm").submit();
                        return false;
                    }
                </script>
            </s:if>
            <s:else>
                <div class="alert alert-danger" id="errorMsg" style="display: <s:if test="hasActionErrors()">block;</s:if><s:else>none;</s:else>">
                    <s:text name="lbl.common.table.name"/>
                    </div>
            </s:else>
        </div>
        <div class="modal fade" id="report_sales" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
            <div class="modal-dialog  modal-mm">
                <div class="modal-content">
                    <div class="modal-header">
                            <h4 class="modal-title"><s:text name="lbl.reports.head.marketing.cloud"/></h4>
                        <div style="margin-top: -31px; float: right;">
                            <button class="save-btn btn btn-primary" onclick="pushSales();">&#10004;<s:text name="btn.submit"/></button>
                            <button class="close-btn btn" data-dismiss="modal" id="btn_task_close">&#10006; <s:text name="btn.close"/></button>
                        </div>
                    </div>
                    <div class="body" style="margin-top: 40px;">
                        <div id="msg_closetask" class="alert alert-danger" style="display: none;"></div>
                        <s:form id="frm_sales" name="frm_sales" method="post" theme="simple">
                            <div class="form-fields clearfix">
                                <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                                    <s:textfield name="tableName" id="tableName" title="Please Enter Message" class="textbox"/>
                                    <label class="textboxlabel" ><s:text name="lbl.common.table.name"/><span>*</span></label>
                                </div>
                            </div>
                        </s:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    function backBtnAction() {
        if ($("#reportType").val() === "M") {
            $("#ExcelExportForm").attr("action", "<%=request.getContextPath()%>/camp/openCampaignPage.do?campaignTab=report");
            $("#ExcelExportForm").submit();
        } else {
            $("#ExcelExportForm").attr("action", "${pageContext.request.contextPath}/LoadDynamicReports.do");
            $("#ExcelExportForm").submit();
        }
        /* $.ajax({
         type: "POST",
         url: '<%=request.getContextPath()%>/LoadDynamicReportsAjax.do',
         data: $("#ExcelExportForm").serialize(),
         success:function(result){
         if(!applyAjaxResponseError(result, 'templateErrorDiv')){
         $('#div_body').empty().html(result);
         }
         },
         complete: function() {
         unblock('block_body');
         },
         error:function(xhr, status, error){
         alert("Error: " + error);
         }
         });*/
    }
    function pushReport() {
        $("#report_sales").modal("show");
    }
    function pushSales() {
        var tableName = $("#tableName").val();
        alert(tableName);
    }
</script>