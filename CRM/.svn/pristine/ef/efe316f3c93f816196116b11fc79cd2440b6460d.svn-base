<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : reportEnquiryPlugin
    Created on : Aug 21, 2017, 8:31:37 AM
    Author     : ravindar.singh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dynamicReport.js"></script>
<%--div class="card">
    <div class="header">
        <h4 class="m-tb-6 d-inline-block">Criteria </h4>
    </div>
    <div class="body">
        <div class="row">
            <div class="col-md-12 mt-10">
                <div id="ReportsDesignerErrorDiv" class="alert alert-danger" style="display: <s:if test="hasActionErrors()">block</s:if><s:else>none</s:else>;" >
                    <p><s:text name="error.title" /></p>
                    <s:iterator value="actionErrors">
                        <ul><li><s:property escapeHtml="false" /></li></ul>
                    </s:iterator>
                </div>
            </div>
        </div>
        <div class="row" style="padding: 10px;"--%>
            <s:form name="ReportsEnquiryForm" id="ReportsEnquiryForm" theme="simple" method="post" autocomplete="off" >
                <s:hidden name="reportView" id="reportView" />
                <s:hidden name="reportViewDesc" id="reportViewDesc" />
                <s:hidden name="reportTitle" id="reportTitle" />
                <s:hidden name="reportTable" id="reportTable" />
                <s:hidden name="pluginFor" />
                <div class="form-fields clearfix">
                    <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                    <%--div class="col-md-2"><b>View</b></div>
                    <div class="col-md-8"--%>
                        <%--s:select name="reportViewSelect" id="reportViewSelect" list="reportsViewList" listKey="key" listValue="value" 
                                  listTitle="info" headerKey="" headerValue="-Select-" cssClass="form-control" onchange="showViewDesc(this.value);" /--%>
                        <label><s:text name="lbl.view"/> <span>*</span></label>
                    <div class="input-group">
                        <select name="reportViewSelect" id="reportViewSelect" class="form-control">
                            <option value="">-Select-</option>
                            <s:iterator value="reportsViewList">
                                <option value="<s:property value="key"/>" data-subtext="<s:property value="value"/>" title="<s:property value="info"/>"><s:property value="value"/></option>
                            </s:iterator>
                        </select>
                        <span class="input-group-addon" id="AddEnq" onclick="loadColumnsAndFilters();" style="cursor: pointer;"><s:text name="lbl.common.proceed"/>
                    <%--/div>
                    <div class="col-md-2 mt-10" style="text-align: center"--%>
                        <%--button id="AddEnq" type="button" class="btn btn-success waves-effect pull-right" style="margin-right: 10px;" onclick="loadColumnsAndFilters();"> Proceed</button--%>
                        </span>
                    </div>
                </div>
                </div>
                <%--div class="col-md-12 mt-10" id="ReportViewDescDivId">
                    <div class="col-md-3" id="ReportViewDescId"><b>Description</b></div>
                    <s:iterator value="reportsViewList">
                        <div class="col-md-9" id="ReportViewDesc_<s:property value="key"/>" style="display: none;"><s:property value="info1"/></div>
                    </s:iterator>
                </div>
                <div class="col-md-12 mt-10" style="text-align: center">
                    <button id="AddEnq" type="button" class="btn btn-success waves-effect pull-right" style="margin-right: 10px;" onclick="loadColumnsAndFilters();"> Proceed</button>
                </div--%>
            </s:form>
        <%--/div>
    </div>
    <div id="block_smart_plugin"></div>
</div--%>
<script type="text/javascript">
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
        block();
        $.ajax({
            type: "POST",
            data: $('#ReportsEnquiryForm').serialize(),
            url: "${pageContext.request.contextPath}/LoadEnqRptDesinger.do",
            success: function (result) {
                if (!applyAjaxResponseError(result, 'ReportsDesignerErrorDiv'))
                {
                    $('#AddNewEnquiryDivId').modal('hide');
                    setTimeout(function () {
                        $('#block_smart_plugin').html(result);
                        $('#block_smart_plugin').show();
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