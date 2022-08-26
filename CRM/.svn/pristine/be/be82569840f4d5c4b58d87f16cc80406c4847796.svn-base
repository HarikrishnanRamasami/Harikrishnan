<%-- 
    Document   : CopyCampaign
    Created on : 28 Jul, 2020, 6:36:05 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/bootstrap-multiselect/bootstrap-multiselect.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-multiselect/bootstrap-multiselect.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/tokenizeSearch/tokenize2.min.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/tokenizeSearch/tokenize2.min.js"></script>
<style type="text/css">
    .modal-content {
        min-height: 300px;
        padding: 15px !important;
    }
</style>
<div class="col-md-12 right-pad" id="block_body">
    <div class="card">
        <div class="modal-header">
            <h4 class="modal-title"><s:text name="lbl.campaign.campaign.copy.form"/></h4>
        </div>
        <div id="msg_copy_camp" class="alert alert-danger" style="display: none;"></div>
        <div class="body" style="padding: 7px;">
            <div id="msg_camp_temp" class="alert-danger" style="display: none;"></div>
            <s:form id="frm_copy_camp" name="frm_copy_camp">
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                        <label><s:text name="lbl.common.name"/>: <span>*</span></label>
                        <div class="form-group">
                            <div class="form-line">
                                <s:textfield id="mcfName" name="campaign.mcCampName" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.enter.name')}"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                        <label><s:text name="lbl.common.code"/>: <span>*</span> </label>
                        <div class="form-group">
                            <div class="form-line">
                                <s:textfield id="mcCampCode" name="campaign.mcCampCode" required="true" cssClass="form-control" data-toggle="tooltip" data-placement="top" />
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                        <label><s:text name="lbl.campaign.campaign"/> <span>*</span></label>
                        <div class="form-group">
                            <div class="form-line">
                                <s:textfield name="flex2"  id="txt_campName" cssClass="form-control"  placeholder="%{getText('lbl.enter.min.three.chars')}"/>
                                <s:hidden name="campaign.mcCampId" id="hid_campId"/> 
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" style="margin-left: 400px;">
                    <button class="save-btn btn btn-primary" type="button" onclick="proccedCopyFlow();">&#10004;<s:text name="btn.copy"/></button>&nbsp;
                    <button class="close-btn btn" id="btn_journ_close" data-dismiss="modal">&#10006; <s:text name="btn.close"/></button>
                </div>
            </div>
        </s:form>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var has_valid_values = false;
        var has_values_modified = false;
        $("#txt_campName").autocomplete({
            source: function (request, response) {
                var flex1 = request.term;
                $.ajax({
                    url: "<%=request.getContextPath()%>/camp/loadCampaignTypeList.do",
                    data: {
                        flex1: flex1
                    },
                    success: function (data) {
                        unblock();
                        var rows = [];
                        $.each(data.aaData, function (i, v) {

                            rows.push({label: v.value, value: v.value, key: v.key}); //label=display, value=value to display in txtbox after selection
                        });
                        response(rows);
                    },
                    error: function (xhr, status, error) {
                        displayAlert('E', error);
                    }
                });
            },
            select: function (event, ui) {
                has_valid_values = true;
                $("#hid_campId").val(ui.item.key);
                $(this).val(ui.item.value);
                //var inputs = $(this).closest('form').find(':input').not(':hidden');
                // inputs.eq(inputs.index(this) + 1).focus();
                event.preventDefault();
            },
            change: function (event, ui) {
                has_values_modified = true;
            },
            open: function () {
                $(this).removeClass("ui-corner-all ui-corner-top");
            },
            close: function () {
                $(this).removeClass("ui-corner-top ui-corner-all");
            },
            minLength: 3,
            autoFocus: true
        }).on("blur", function (event) {
            if (!has_valid_values && has_values_modified) {
                $(this).val("");
            }
            has_valid_values = false;
            has_values_modified = false;
        });
    });
    $("#btn_camp_close").on("click", function () {
        $('#block_camp_form').empty().hide();
    });

    function proccedCopyFlow() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/proceedCopyCampaign.do",
            data: $("#frm_copy_camp").serialize(),
            success: function (result) {
                if (result.message === null) {
                    $("#frm_copy_camp").attr("action", "<%=request.getContextPath()%>/camp/openCampaignForm.do?mcCampId=" + result.mcCampId);
                    $("#frm_copy_camp").submit();
                } else {
                    $('#msg_copy_camp').empty().html(result.message).show();
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

