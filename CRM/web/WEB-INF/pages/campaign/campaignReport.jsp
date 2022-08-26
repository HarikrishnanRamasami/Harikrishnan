<%-- 
    Document   : campaignReport
    Created on : 16 Oct, 2019, 1:48:17 PM
    Author     : sutharsan.g
--%>

<%@page import="qa.com.qic.anoud.vo.KeyValue"%>
<%@page import="java.util.List"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dynamicReport.js"></script>
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
<div id="ReportResultDiv">
    <script type="text/javascript">
        function downloadSavedReports(indx) {
            $("#reportView").val($("#ReportView_" + indx).val());
            $("#reportTitle").val($("#ReportTitle_" + indx).val());
            $("#reportTable").val($("#ReportTable_" + indx).val());
            $("#reportId").val($("#ReportId_" + indx).val());
            $("#reportName").val($("#ReportName_" + indx).val());
            var url = APP_CONFIG.context + "/DownloadSavedReport.do";
            block('div_body');
            $.fileDownload(url,
                    {
                        httpMethod: "POST",
                        data: $("#ReportsEnquiryForm").serialize(),
                        successCallback: function (url) {
                            unblock('div_body');
                        },
                        failCallback: function (responseHtml, url) {
                            unblock('div_body');
                        }
                    }
            );
            return false;
        }
        function runSavedReports(indx) {
            block('block_body');
            $("#reportView").val($("#ReportView_" + indx).val());
            $("#reportTitle").val($("#ReportTitle_" + indx).val());
            $("#reportTable").val($("#ReportTable_" + indx).val());
            $("#reportId").val($("#ReportId_" + indx).val());
            $("#reportName").val($("#ReportName_" + indx).val());
            $.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}/RunSavedReport.do',
                data: $("#ReportsEnquiryForm").serialize(),
                async: true,
                success: function (result) {
                    $('#ReportResultDiv').html(result);
                    $("#ReportResultDiv #ExcelExportForm #reportType").val('M');
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        }

        function editSavedReports(indx) {
            $("#reportView").val($("#ReportView_" + indx).val());
            $("#reportTitle").val($("#ReportTitle_" + indx).val());
            $("#reportTable").val($("#ReportTable_" + indx).val());
            $("#reportId").val($("#ReportId_" + indx).val());
            $("#reportName").val($("#ReportName_" + indx).val());
            //$("#ReportsEnquiryForm").attr("action", "${pageContext.request.contextPath}/Enquiry/LoadEnqRptDesinger.do");
            //$("#ReportsEnquiryForm").submit();
            block('EnquiryResultTable_wrapper');
            $.ajax({
                type: "POST",
                data: $('#ReportsEnquiryForm').serialize(),
                url: "${pageContext.request.contextPath}/LoadEnqRptDesinger.do",
                success: function (result) {
                    if (!applyAjaxResponseError(result, 'errorDiv'))
                    {
                        $('#AddNewEnquiryDivId').modal('hide');
                        setTimeout(function () {
                            $('#div_body').html(result);
                            $("#div_body #ReportsDesignerForm #campType").val('M');
                            $('#div_body').show();
                        }, 1000);
                    }
                },
                complete: function () {
                    unblock('EnquiryResultTable_wrapper');
                },
                error: function (xhr, status, error) {
                    alert('Unexpected error. Reason: ' + error);
                }
            });
            return false;
        }

        function addNewReport() {
            $('#AddNewEnquiryDivId').modal('show');

            setTimeout(function () {
                $("#s2id_reportViewSelect input.select2-focusser:first").focus();
            }, 300);

        }

        function showViewDesc(val) {
            $("#ReportViewDescDivId").children().hide();
            $("#ReportViewDescId").show();
            $("#ReportViewDesc_" + val).show();
        }

        function loadColumnsAndFilters() {
            var option = '';
            if ($("#reportViewSelect").val() == "") {
                alert("Select a View");
                return false;
            }
            var reportView = $("#reportViewSelect :selected").val();
            $("#reportView").val(reportView);
            $("#reportViewDesc").val($("#ReportViewDesc_" + reportView).html());
            $("#reportTitle").val($("#reportViewSelect :selected").text());
            $("#reportTable").val($('#reportViewSelect').find('option:selected').attr('title'));
            $("#reportId, #reportName").val("");
            //$("#ReportsEnquiryForm").attr("action", "${pageContext.request.contextPath}/Enquiry/LoadEnqRptDesinger.do");
            //$("#ReportsEnquiryForm").submit();
            block();
            $.ajax({
                type: "POST",
                data: $('#ReportsEnquiryForm').serialize(),
                url: "${pageContext.request.contextPath}/LoadEnqRptDesinger.do?",
                success: function (result) {
                    if (!applyAjaxResponseError(result, 'ReportsDesignerErrorDiv'))
                    {
                        $('#AddNewEnquiryDivId').modal('hide');
                        setTimeout(function () {
                            $('#div_body').html(result);
                            $("#div_body #ReportsDesignerForm #campType").val('M');
                            $('#div_body').show();
                        }, 1000);
                    }
                },
                complete: function () {
                    unblock();
                },
                error: function (xhr, status, error) {
                    alert('Unexpected error. Reason: ' + error);
                }
            });
        }
    </script>
    <style type="text/css">
        <!--    
        .col-wdth-5,.col-wdth-10,.col-wdth-11,.col-wdth-19,.col-wdth-26{
            position: relative; 
            float: left; 
            padding-left: 10px; 
            padding-right: 10px;
        }
        @media (min-width:992px){
            .col-wdth-5{ width: 5%; }
            .col-wdth-10{ width: 10%; }
            .col-wdth-11{ width: 11%; }
            .col-wdth-19{ width: 19%; }
            .col-wdth-26{ width: 26%; }
        }
        -->
    </style>
    <div class="modal fade" id="AddNewEnquiryDivId" role="dialog" aria-labelledby="enq_modal_label" data-keyboard="false" aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog modal-mm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="enq_modal_label"><s:text name="lbl.common.head.add.new.report"/></h4>
                </div>
                <div class="modal-body" id="report_modal_body">
                    <div class="row" style="padding: 10px;">
                        <div class="col-md-12 form-group form-group-sm">
                            <div class="col-md-3"><b>View</b></div>
                            <div class="col-md-6">
                                <s:select name="reportViewSelect" id="reportViewSelect" list="reportsViewList" listKey="key" listValue="value" 
                                          listTitle="info" headerKey="" headerValue="-Select-" cssClass="form-control" onchange="showViewDesc(this.value);" />
                            </div>
                            <div class="col-md-3">&nbsp;</div>
                        </div>
                        <div class="col-md-12 mt-10" id="ReportViewDescDivId">
                            <div class="col-md-3" id="ReportViewDescId"><b>Description</b></div>
                            <%
                                List<KeyValue> list = (List<KeyValue>) ActionContext.getContext().getValueStack().findValue("reportsViewList");
                                if (list != null && list.size() > 0) {
                                    for (KeyValue obj : list) {
                            %>
                            <div class="col-md-9" id="ReportViewDesc_<%=obj.getKey()%>" style="display: none;"><%=(obj.getInfo1() != null ? obj.getInfo1() : "")%></div>
                            <%
                                    }
                                }
                            %>
                        </div>
                        <div class="col-sm-12 col-md-3 board-icons1 Rbtn">
                            <button id="AddEnq" type="button" class="btn btn-warning tmargin cbtn" style="margin-top: -8px" onclick="loadColumnsAndFilters();"> <s:text name="btn.proceed"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="div_body"> 
        <s:hidden id="campType"/> 
        <div class="col-md-12 right-pad">
            <div class="dash-leads" style="border-top:0!important">
                <div class="my-bord">
                    <h3><s:text name="lbl.common.smart.reports"/></h3>
                </div>
                <div class="my-bord bor2">
                    <s:form name="ReportsEnquiryForm" action="ReportsEnquiryForm" theme="simple" method="post" autocomplete="off" >
                        <s:hidden name="reportView" id="reportView" />
                        <s:hidden name="reportViewDesc" id="reportViewDesc" />
                        <s:hidden name="reportId" id="reportId" />
                        <s:hidden name="reportTitle" id="reportTitle" />
                        <s:hidden name="reportTable" id="reportTable" />
                        <s:hidden name="reportName" id="reportName" />
                    </s:form>
                    <div class="col-sm-12 col-md-3 board-icons1 Rbtn">
                        <button class="btn btn-warning tmargin cbtn" style="width: 74px; margin-top: -8px" id="AddEnq" data-toggle="tooltip" data-placement="top" title="Add New Report" onclick="addNewReport();" ><s:text name="btn.add"/></button>
                    </div>
                    <div class="">
                        <div id="ReportsDesignerErrorDiv" class="alert alert-danger" style="display: <s:if test="hasActionErrors()">block</s:if><s:else>none</s:else>;" >
                            <p><s:text name="error.title" /></p>
                            <s:iterator value="actionErrors">
                                <ul><li><s:property escapeHtml="false" /></li></ul>
                                    </s:iterator>
                        </div>
                    </div>
                    <table align="center" id="EnquiryResultTable" cellpadding='0' border="0" class="table table-striped table-bordered display dataTable dtr-inline">
                        <thead>
                            <tr>
                                <th class="text-center"><s:text name="lbl.common.sr.no"/></th>
                                <th class="text-center"><s:text name="lbl.view"/></th>
                                <th class="text-center"><s:text name="lbl.common.report"/></th>
                                <th class="text-center"><s:text name="lbl.description"/></th>
                                <th class="text-center"><s:text name="lbl.created.by"/></th>
                                <th class="text-center"><s:text name="lbl.common.created.date"/></th>
                                <th class="text-center"><s:text name="lbl.action"/></th>
                            </tr>
                        </thead>
                        <s:if test='userReportsList!=null && userReportsList.size()>0'>
                            <s:iterator value="userReportsList" var="urRepVar" status="urRepSts">
                                <tr>
                                    <td class="text-center"><s:property value="#urRepSts.count" /></td>
                                    <td><s:property value="reportTitle" /></td>
                                    <td><s:property value="reportName" /></td>
                                    <td><s:property value="reportRemarks" /></td>
                                    <td><s:property value="repCrUname" /></td>
                                    <td class="text-center"><s:date name="repCrDt" format="dd/MM/yyyy" /></td>
                                    <td class="text-center">
                                        <input type="hidden" id="ReportView_<s:property value="#urRepSts.index" />" value='<s:property value="reportView" />' />
                                        <input type="hidden" id="ReportId_<s:property value="#urRepSts.index" />" value='<s:property value="reportId" />' />
                                        <input type="hidden" id="ReportTitle_<s:property value="#urRepSts.index" />" value='<s:property value="reportTitle" />' />
                                        <input type="hidden" id="ReportTable_<s:property value="#urRepSts.index" />" value='<s:property value="reportTable" />' />
                                        <input type="hidden" id="ReportName_<s:property value="#urRepSts.index" />" value='<s:property value="reportName" />' />
                                        <s:if test='#session.USER_INFO.userId.equals(repCrUid)'>
                                            <i class="fa fa-pencil hand" onclick="editSavedReports('<s:property value="#urRepSts.index" />', 'Edit');" title="<s:text name="lbl.edit"/>"></i>&nbsp;
                                        </s:if>
                                        &nbsp;&nbsp;
                                        <i class="fa fa-play hand" style="color: #418bca;" onclick="runSavedReports('<s:property value="#urRepSts.index" />');" title="<s:text name="lbl.common.run"/>"></i>
                                        &nbsp;&nbsp;
                                        <s:if test='!"0".equals(reportSec)'>
                                            <i class="fa fa-play-circle-o hand" style="color: #418bca;" onclick="runReportWithParams('<s:property value="#urRepSts.index" />');" title="<s:text name="lbl.common.run.with.parameters"/>"></i>
                                        </s:if>
                                        &nbsp;&nbsp;                                           
                                        <i class="fa fa-download hand" style="color: #418bca;" onclick="downloadSavedReports('<s:property value="#urRepSts.index" />');" title="<s:text name="lbl.common.download.excel"/>">
                                        </i>
                                    </td>
                                </tr>
                            </s:iterator>
                        </s:if>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="reportparam_modal_dialog" role="dialog" aria-labelledby="enq_modal_label" data-keyboard="false" aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="report_modal_label"><s:text name="lbl.common.run.with.parameters"/></h4>
                </div>
                <div class="modal-body" id="report_param" style="height: 250px;">
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
    var filterRowId = "<s:property value="repFilter==null?0:repFilter.size()" />";
    var fultersize = "<s:property value="repFilter.size()" />"
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        var tbl = $("#EnquiryResultTable").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: false,
            iDisplayLength: 10,
            aLengthMenu: false,
            "autoWidth": false,
            "lengthChange": false,
            "responsive": true,
            pageLength: 5,
            dom: 'T<"clear">lfltipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            }
        });
    });
    function runReportWithParams(indx) {
        $("#reportView").val($("#ReportView_" + indx).val());
        $("#reportTitle").val($("#ReportTitle_" + indx).val());
        $("#reportTable").val($("#ReportTable_" + indx).val());
        $("#reportId").val($("#ReportId_" + indx).val());
        $.ajax({
            type: "POST",
            data: $("#ReportsEnquiryForm").serialize(),
            url: "${pageContext.request.contextPath}/loadReportParams.do",
            success: function (result) {
                $("#report_param").html(result);
                $('#reportparam_modal_dialog').modal('show');
            },
            complete: function () {
            },
            error: function (xhr, status, error) {
                alert('Unexpected error. Reason: ' + error);
            }
        });
        return false;
    }
    
    function loadCampaignReports(){
    $('.acti-off-heads a').removeClass('active');
        $('.act-name').removeClass('current');

        $(this).addClass('active');
        $("#tab_agent_rep").addClass('current');
    }
    </script>
</div>
