<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : leadsDataSourceForm
    Created on : Oct 23, 2017, 3:40:05 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div id="prgDiv" style="display: none;">
    <div class="card">
        <div class="modal-header">
            <h3 class="modal-title"><s:text name="lbl.data.source"/></h3>
            <div  style="margin-top: -31px; float: right;">
                <button type="button" class="save-btn btn btn-primary" id="btn_app_save" onclick="processProgrammeDialog();">&#10004;<s:text name="btn.save"/></button>
                <button type="button" class="close-btn btn" id="btn_app_close" data-dismiss="modal" aria-hidden="true">&#10006;<s:text name="btn.close"/></button>
            </div>
        </div>
        <div class="body">
            <div class="col-md-12">
                <div class="alert alert-danger margin-bottom-10" id="dummyErr" style="display: none;"></div>
            </div>
            <s:form name="mAppForm" id="dummy">
                <s:hidden name="appCodes.acType" value="LEADS_DS" />
                <s:hidden name="operation"/>
                <div class="row">
                    <%--div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
                       <label>Code</label>
                       <div class="form-group">
                           <s:textfield name="appCodes.acCode" cssClass="form-control pgCode" maxlength="6" onkeyup="this.value = (this.value).toUpperCase();" id="pgmAcCode"/>
                       </div>
                   </div--%>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 required">
                        <label><s:text name="lbl.description"/></label>
                        <div class="form-group">
                            <s:textfield name="appCodes.acDesc" cssClass="form-control pgDesc"/>
                        </div>
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <label><s:text name="lbl.common.long.description"/></label>
                        <div class="form-group">
                            <s:textarea name="appCodes.acLongDesc" cssClass="form-control" id="acLongDesc"/>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
                        <label><s:text name="lbl.start.date"/></label>
                        <div class="form-group">
                            <s:textfield name="appCodes.acFlex01" id="acFlex01" cssClass="form-control datepicker calicon" placeholder="dd/mm/yyyy" title="%{getText('lbl.start.date')}"/>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
                        <label><s:text name="lbl.end.date"/></label>
                        <div class="form-group">
                            <s:textfield name="appCodes.acFlex02" id="acFlex02" cssClass="form-control datepicker calicon" placeholder="dd/mm/yyyy" title="%{getText('lbl.end.date')}"/>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
                        <label><s:text name="lbl.leads.expected.revenue"/></label>
                        <div class="form-group">
                            <s:textfield name="appCodes.acFlex03" id="acFlex03" cssClass="form-control number"/>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
                        <label><s:text name="lbl.leads.appl.product"/></label>
                        <div class="form-group">
                            <s:select name="appCodes.acFlex04" id="acFlex04" list="moduleList" listKey="key" listValue="value" multiple="true" cssClass="form-control"/>
                        </div>
                    </div>
                </div>
            </s:form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        //$(".datepicker").datepicker({dateFormat: 'dd/mm/yy'});
    });

    function showPorgrammeForm(pgmCode) {
        if (pgmCode === "") {
            $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm");
            $('#plugin_modal_dialog .modal-dialog').addClass("modal-mm");
            $('#plugin_modal_dialog .modal-content').html($('#prgDiv').html());
            $('#plugin_modal_dialog').modal('show');
            $('#plugin_modal_dialog .modal-content form').attr("id", "mAppForm");
            $('#plugin_modal_dialog .modal-content .alert').attr("id", "mAppCodeErr");
            $("#mAppForm input[name='operation']").val("add");
            $("#mAppForm .datepicker").datepicker({format: 'dd/mm/yyyy'});
            $("#mAppForm #acFlex04").multiselect({buttonWidth: '100%'});
        } else {
            $.ajax({
                async: false,
                type: "POST",
                url: "<%=request.getContextPath()%>/json/jsonGetPgmCode.do",
                data: {'appCodes.acCode': pgmCode, 'appCodes.acType': 'PROGRAMME'},
                success: function (result) {
                    $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm");
                    $('#plugin_modal_dialog .modal-dialog').addClass("modal-mm");
                    $('#plugin_modal_dialog .modal-content').html($('#prgDiv').html());
                    $('#plugin_modal_dialog').modal('show');
                    $('#plugin_modal_dialog .modal-content form').attr("id", "mAppForm");
                    $('#plugin_modal_dialog .modal-content .alert').attr("id", "mAppCodeErr");
                    $("#mAppForm input[name='operation']").val("edit");
                    $("#mAppForm input[name='appCodes.acCode']").val(result.appCodes.id.acCode);
                    $("#mAppForm input[name='appCodes.acDesc']").val(result.appCodes.acDesc);
                    $("#mAppForm #acLongDesc").html(result.appCodes.acLongDesc);
                    $("#mAppForm #pgmAcCode").attr("readOnly", "true");
                },
                error: function (xhr, status, error) {
                    alert(error);
                }
            });
        }
    }

    function processProgrammeDialog() {
        $.ajax({
            async: false,
            type: "POST",
            url: "<%=request.getContextPath()%>/saveLeadDataSource.do",
            data: $('#mAppForm').serialize(),
            success: function (result) {
                if (result.messageType === 'S') {
                    $('#plugin_modal_dialog').modal('hide');
                    var oper = $("#mAppForm input[name='oper']").val();
                    if (oper === "edit") {
                        $("#clWorkPlace option[value='" + result.appCodes.acCode + "']").text(result.appCodes.acDesc);
                    } else {
                        $("#clWorkPlace").append("<option value='" + result.appCodes.acCode + "'>" + result.appCodes.acDesc + "</option>");
                    }
                    $('#clWorkPlace').val(result.appCodes.acCode);
                    //$('#clWorkPlace').trigger('change');
                } else {
                    $("#mAppCodeErr").show();
                    $("#mAppCodeErr").html(result.message);
                }
            },
            error: function (xhr, status, error) {
                alert(error);
            }
        });
    }
</script>
