<%-- 
    Document   : callDetails
    Created on : Jun 21, 2017, 9:15:42 AM
    Author     : haridass.a
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<a href="#" class="close-link">&#10006;</a>
<div class="task-form">
    <div class="popup-title">
        <h3><s:text name="lbl.call.details"/></h3>
        <div style="float:right;" class="">
            <button class="save-btn btn btn-primary" id="btn_remark_update">&#10004;<s:text name="btn.save"/></button>
            <button class="close-btn btn" data-dismiss="modal">&#10006;<s:text name="btn.close"/></button>
        </div><hr>
    </div>
    <div id="msg_call_det" class="alert alert-danger" style="display: none;"></div>
    <s:form id="frm_call" name="frm_call" method="post" theme="simple">
        <s:hidden name="callLog.cclId" />
        <table class="table table-striped table-bordered display dataTable dtr-inline">
            <tbody>
                <%--tr>
                    <td style="border-top: 0px solid #eee;">Agent Name</td>
                    <td style="border-top: 0px solid #eee;"><s:property value="callLog.cclCrUid"/></td>
                </tr--%>
                <tr>
                    <td><label><s:text name="lbl.agent.name"/></label></td>
                    <td><s:property value="callLog.cclCrUid"/></td>
                </tr>
                <tr>
                    <td><label><s:text name="lbl.call.no"/></label></td>
                    <td><s:property value="callLog.cclCallNo"/></td>
                </tr>
                <tr>
                    <td><label><s:text name="lbl.call.date"/></label></td>
                    <td><s:property value="callLog.cclCallDt"/></td>
                </tr>
                <tr>
                    <td><label><label><s:text name="lbl.call.duration"/></label></label></td>
                    <td><s:property value="callLog.cclDurationDesc"/></td>
                </tr>
                <tr>
                    <td><label><s:text name="lbl.call.type"/></label></td>
                    <td><s:property value="callLog.cclCrmTypeDesc"/></td>
                </tr>
                <tr>
                    <td><label><s:text name="lbl.issue.type"/><span>*</span></label></td>
                    <td><s:select name="callLog.cclCallCode" id="cclCallCode" list="keyValueList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control"/></td>
                </tr>
                <tr>
                    <td><label><s:text name="lbl.civil.id"/></label></td>
                    <td><s:textfield name="callLog.cclCivilId" id="cclCivilId" maxlength="60" cssClass="form-control"/></td>
                </tr>
                <tr>
                    <td><label><s:text name="lbl.name"/></label></td>
                    <td><s:textfield name="callLog.cclRefName" id="cclRefName" maxlength="240" cssClass="form-control"/></td>
                </tr>
                <tr>
                    <td><label><s:text name="lbl.remarks"/><span>*</span></label></td>
                    <td><s:textarea name="callLog.cclRemarks" id="cclRemarks" cssClass="form-control"/></td> 
                </tr> 
            </tbody>
        </table>
    </s:form>
</div>
<script type="text/javascript">
    $(function () {
        $("#datepicker").datepicker({format: 'dd/mm/yyyy'});
    });
    $(function () {

        //  $('#overflow').slimScroll();

        $('#sidebar').slimScroll({
            height: '100%',
            width: '100%',
        });
        $('.leads-tab').click(function () {
            $('.popup-wrap').addClass('popup-open');
            $('#overlay').show();

        });
        $('.close-link').click(function () {
            $('.popup-wrap').removeClass('popup-open');
            $('#overlay').hide();
        });
        $('.close-btn').click(function () {
            $('.popup-wrap').removeClass('popup-open');
            $('#overlay').hide();
        });
    });
</script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#frm_call').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            rules: {
                "callLog.cclCallCode": {
                    required: true
                },
                "callLog.cclRemarks": {
                    required: true
                }
            },
            messages: {
                "callLog.cclCallCode": {
                    required: "Please select Type"
                },
                "callLog.cclRemarks": {
                    required: "Please enter Remarks"
                }
            },
            highlight: function (element) {
                $(element).removeClass('error');
            },
            showErrors: function (errorMap, errorList) {
                if (errorList.length)
                {
                    var s = errorList.shift();
                    var n = [];
                    n.push(s);
                    this.errorList = n;
                }
                if (this.numberOfInvalids())
                    this.defaultShowErrors();
                else
                    $("#msg_call_det").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_call_det").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_call_det").show();
                else
                    $("#msg_call_det").hide();
            }
        });

        $("#btn_remark_update").on("click", function () {
            if (!$('#frm_call').valid()) {
                return false;
            }
            block('block_body');
            $.ajax({
                data: $("#frm_call").serialize(),
                url: APP_CONFIG.context + "/saveMissedCallDetails.do",
                success: function (result) {
                    if (!applyAjaxResponseError(result, 'msg_call_det')) {
                        if (result.messageType === "S") {
                            //$('.popup-wrap').addClass('popup-open').hide();
                            $.notify("Call details Updated successfully", "custom");
                            //$("#msg_call_det").removeClass("alert-danger").addClass("alert-success").html(result.message).show();
                            $('.popup-wrap').removeClass('popup-open');
                            $('#overlay').hide();
                        } else {
                            $("#msg_call_det").removeClass("alert-success").addClass("alert-danger").html(result.message).show();
                        }
                    } else {
                        $("#msg_call_det").removeClass("alert-success").addClass("alert-danger").html(result.message).show();
                    }
                },
                complete: function () {
                    unblock('block_body');
                },
                error: function (xhr, status, error) {
                    unblock('block_body');
                    //displayAlert('E', error);
                }
            });
        });
    });
</script>

