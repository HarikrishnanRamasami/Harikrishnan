<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : leadsUpload
    Created on : Oct 23, 2017, 2:24:56 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div id="msg_lead_upload" class="alert alert-danger" style="display: none;"></div>
<%--This form id used in reportDesigner.jsp--%>
<s:form id="uptFrm" name="uptFrm" theme="simple" method="post" autocomplete="off" enctype="multipart/form-data" action="UploadFileDetails.do">
    <div class="col-md-12 right-pad">
        <div class="dash-leads" style="border-top:0!important">
            <div class="my-bord">
                <h3><s:text name="lbl.leads"/></h3>
            </div>
            <div class="my-bord">
                <%--div class="col-md-6 no-padding margin-bottom-5">
                    <div class="col-md-4 form-label required">
                        Source
                    </div>
                    <div class="col-md-8">
                        <s:select name="leads.clSource" id="clSource" list="sourceList" listKey="key" listValue="value" onchange="setList(this.value)" headerKey="" headerValue="-- Select --" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="Source"/>
                    </div>
                </div--%>
                <div class="form-fields clearfix">
                    <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                        <label><s:text name="lbl.source"/> <span>*</span></label>
                        <s:select name="leads.clSource" id="clSource" list="sourceList" listKey="key" listValue="value" onchange="setList(this.value)" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.source')}"/>
                    </div>
                </div>
                <%--div class="col-md-6">
                    <label>Targetted Recipients <span>*</span></label>
                    <div class="form-group">
                        <s:select name="leads.sendTo" list="sendToList" id="sendTo" data-toggle="tooltip" data-placement="top" title="Target Email Address" onchange="setList(this.value)" cssClass="form-control"/>
                    </div>
                </div--%>
                <div class="form-fields clearfix">
                    <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                        <label><s:text name="lbl.data.source"/> <span>*</span></label>
                        <div class="input-group">
                            <div style="display: table-cell; word-break: break-all;">
                                <s:select name="leads.clWorkPlace" list="categoryList" listKey="key" listValue="value" cssClass="Select2 form-control" 
                                          id="clWorkPlace" headerKey="" headerValue="--Select--"/>
                            </div>
                            <span class="input-group-addon" id="program_span" onclick="showPorgrammeForm('');" style="cursor: pointer; display: table-cell;">
                                <span class="glyphicon">+</span>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="form-fields clearfix ExcelFileDivId">
                    <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                        <label><s:text name="lbl.file.path"/> <span>*</span> (<a href="<%=request.getContextPath()%>/Forms_Templates/LEADS_UPL_TEMPLATE.xls"><s:text name="lbl.common.download.excel"/>.</a>)</label>
                        <div>
                            <a class='btn btn-primary' href='javascript:;'>
                                <s:file name="attachment" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
                            </a>
                        </div>
                    </div>
                </div>
            </s:form>
            <div class="CustomizedTextDivId col-md-6" style="padding: 0px;">
                <s:action namespace="/" name="LoadDynamicReportsPlugin" executeResult="true" ignoreContextParams="true">
                    <s:param name="pluginFor">lead</s:param>
                    <s:param name="param1">lead</s:param>
                </s:action>
            </div>
        </div>
        <div class="row ExcelFileDivId">
            <div class="col-md-12 text-center">
                <input type="button" name="btn_excel_extract_data" id="btn_excel_extract_data" value="Extract Data" data-toggle="tooltip" data-placement="bottom" title="<s:text name="lbl.common.extract.data"/> onclick="ExtractData(this.form);" class="btn bg-success" /> 
            </div>
        </div>
        <div class="row CustomizedTextDivId">
            <div id="block_smart_plugin"></div>
        </div>
        <div id="bulksmsDetail">
            <%--s:include value="/pages/bulksms/bulkSMSEmailDetails.jsp" /--%>
        </div>
        <div id="block_excel_data">
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/pages/leads/leadsDataSourceForm.jsp"/>
<script type="text/javascript">
    $(document).ready(function () {
        if ($('#clSource').val() !== "O") {
            $(".CustomizedTextDivId").show();
            $(".ExcelFileDivId").hide();
        } else {
            $(".CustomizedTextDivId").hide();
            $(".ExcelFileDivId").show();
        }
    });
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

    $(document).ready(function () {
        var addOptions = {
            success: uploadCallback, // post-submit callback
            url: APP_CONFIG.context + "/saveUploadLeadsEntryForm.do",
            type: "POST"    // 'get' or 'post', override for form's 'method' attribute
        };
        $('#uptFrm').ajaxForm(addOptions);
    });

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

    function uploadCallback(result, statusText, xhr, $form) {
        if (result.messageType === "S") {
            $("#msg_lead_upload").removeClass("alert-danger").addClass("alert-success").html("File upload successfully").show();
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/openLeadsEntryUploadedForm.do",
                data: {"leads.clId": result.aaData[0].clId},
                success: function (result) {
                    unblock("block_body");
                    $("#block_excel_data").html(result);
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });

        } else {
            unblock("block_body");
            $("#msg_lead_upload").removeClass("alert-success").addClass("alert-danger").html(result.message).show();
        }
    }
</script>
