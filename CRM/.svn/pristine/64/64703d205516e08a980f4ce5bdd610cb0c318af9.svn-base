<%-- 
    Document   : salesForceReport
    Created on : 1 Feb, 2018, 5:05:17 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 right-pad">
    <div class="dash-leads" style="border-top:0!important">
        <div class="my-bord">
            <h3><s:text name="lbl.reports.head.marketing.cloud"/></h3>
        </div>
        <div id="msg_sales" class="alert alert-danger" style="display: none;"></div>
        <%--This form id used in reportDesigner.jsp--%>
        <div class="CustomizedTextDivId col-md-6" style="padding: 0px;">
            <s:action namespace="/" name="LoadDynamicReportsPlugin" executeResult="true" ignoreContextParams="true">
                <s:param name="pluginFor">salesforce</s:param>
                <s:param name="param1">salesforce</s:param>
            </s:action>
        </div>
    </div>
    <div class="row CustomizedTextDivId">
        <div id="block_smart_plugin"></div>
    </div>
    <div id="bulksmsDetail">
        <%--s:include value="/pages/bulksms/bulkSMSEmailDetails.jsp" /--%>
    </div>
    <div id="block_excel_data"></div>
</div>
<script type="text/javascript">
    function setList(val) {
        $("#message").html("");
        $("#bulksmsDetail").html("");
        if (val !== "O") {
            $(".CustomizedTextDivId").show();
            $(".ExcelFileDivId").hide();
        } else {
            $(".CustomizedTextDivId").hide();
            $(".ExcelFileDivId").show();
        }
    }

    function ExtractData() {
        if ($('#clSource').val() === "") {
            alert('Please select the source');
        } else if ($('#clWorkPlace').val() === "") {
            alert('Please select the data source');
        } else if ($("input[name=attachment]").val() !== "" && $("input[name=attachment]").val().match(/\.(xls[mx]?)$/)) {
            block("block_body");
            $("#uptFrm").submit();
        }
        return false;
    }
</script>