<%-- 
    Document   : userDetails
    Created on : 22 Nov, 2017, 3:52:10 PM
    Author     : haridass.a
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<a href="#" class="close-link">&#10006;</a>
<div class="task-form">
    <div class="popup-title">
        <h3><s:text name="lbl.user.details"/></h3>
        <div style="float:right;" class="">
            <button class="save-btn btn btn-primary" id="btn_user_save">&#10004;<s:text name="btn.save"/></button>
            <button class="close-btn btn" data-dismiss="modal">&#10006; <s:text name="btn.close"/></button>
        </div><hr>
    </div>

    <div id="msg_user_detail" class="alert alert-danger" style="display: none;"></div>
    <s:form id="frm_user_detail" name="frm_user_detail" method="post" theme="simple" autocomplete="off">
        <s:hidden name="operation"/>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="frm_userId" name="crmUser.userId" cssClass="textbox" disabled="true"/>
                <s:hidden name="crmUser.userId"/>
                <label class="textboxlabel"><s:text name="lbl.anoud.user.id"/> <span>*</span></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="userName" name="crmUser.userName" cssClass="textbox"/>
                <label class="textboxlabel"><s:text name="lbl.username"/> <span>*</span></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="userEmailId" name="crmUser.userEmailId" cssClass="textbox"/>
                <label class="textboxlabel"><s:text name="lbl.email.id"/> <span>*</span></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="userMobileNo" name="crmUser.userMobileNo" cssClass="textbox"/>
                <label class="textboxlabel"><s:text name="lbl.mobile.no"/></label>
            </div>
        </div>     
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <div class="input-group">
                    <label class="textboxlabel"><s:text name="lbl.user.team"/></label>

                    <div style="display: table-cell; word-break: break-all;">
                        <s:select name="crmUser.userTeam" id="userTeam" list="userTeamlist" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox"/>
                    </div>
                    <span class="input-group-addon" id="program_span" onclick="openTeamForm();" style="cursor: pointer; display: table-cell;">
                        <span class="glyphicon">+</span>
                    </span>
                </div>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="crmUser.userRoleSeq" id="userRoleSeq" list="userRoleSeqlist" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox"/>
                <label class="textboxlabel"><s:text name="lbl.level"/> <span>*</span></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="userTelNo" name="crmUser.userTelNo" cssClass="textbox"/>
                <label class="textboxlabel"><s:text name="lbl.extension"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="userMaxSession" name="crmUser.userMaxSession" cssClass="textbox" maxlength="2"/>
                <label class="textboxlabel"><s:text name="lbl.max.session"/></label>
            </div> 
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="userLdapId" name="crmUser.userLdapId" cssClass="textbox"/>
                <label class="textboxlabel"><s:text name="lbl.windows.user.id"/> <span>*</span></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="userStaticIp" name="crmUser.userStaticIp" cssClass="textbox"/>
                <label class="textboxlabel"><s:text name="lbl.agent.system.ip"/></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="crmUser.userAdministratorYn" list="#{'0':'Normal', '1':'Admin', '2': 'Least'}" cssClass="textbox"/>
                <label class="textboxlabel"><s:text name="lbl.privilege"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <input type="checkbox" name="crmUser.userLockYn" id="chk_userLockYn" value="1" class="filled-in chk-col-deep-orange">
                <label for="chk_userLockYn" required="true"></label>
                <label class="textboxlabel"><s:text name="lbl.disabled"/></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="crmUser.userAgentType" id="userAgentType" list="userAgentTypelist" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox"/>
                <label class="textboxlabel"><s:text name="lbl.agent.type"/><span>*</span></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="cuChatUid" name="crmUser.userChatUid" cssClass="textbox"/>
                <label class="textboxlabel"><s:text name="lbl.chat.userName"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="crmUser.userChatPwd" id="cuChatPwd" cssClass="textbox" type="password" autocomplete="off"/>
                <label class="textboxlabel"><s:text name="lbl.chat.password"/></label>
            </div> 
        </div>
        <s:if test='%{"edit".equals(operation) && !@qa.com.qic.common.util.ApplicationConstants@COMPANY_CODE_BEEMA.equals(company)}'>
            <div class="body">
                <div class="form-fields clearfix">
                    <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                        <label><s:text name="lbl.data.source"/> <span>*</span></label>
                        <div class="input-group">
                            <div style="display: table-cell; word-break: break-all;">
                                <select name="user.companyCode" id="company" class="form-control">
                                    <option value="001">DOHA</option>
                                    <option value="002">DUBAI</option>
                                    <option value="005">KUWAIT</option>
                                    <option value="006">OMAN</option>
                                </select>
                            </div>
                            <span class="input-group-addon" id="btn_add_user_frm_appl_comp">
                                <span><i class="fa fa-plus-circle"></i> <s:text name="btn.add"/></span>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                    <table class="table table-striped table-bordered display dataTable dtr-inline" id="tbl_user_company" role="grid" style="width: 95%;">
                        <thead>
                            <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                <th class="text-center">&nbsp;<s:text name="lbl.username"/></th>
                                <th class="text-center"><s:text name="lbl.company.name"/></th>                   
                                <th class="text-center"><s:text name="lbl.action"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </s:if>
    </s:form>
</div>
<div class="modal fade" id="team_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog  modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <div id="msg_team" class="alert alert-danger" style="display: none;"></div>
                <h4 class="modal-title"><s:text name="lbl.team.form"/></h4>
                <div style="margin-top: -31px; float: right;">
                    <button class="save-btn btn btn-primary" id="btn_user_team_save" onclick="saveUserTeamForm()">&#10004;<s:text name="btn.save"/></button>
                    <button class="close-btn1 btn" id="btn_team_close">&#10006;<s:text name="btn.close"/></button>
                </div>
            </div>
            <div class="body" style="margin-top: 40px;">
                <s:form id="frm_userTeam" name="frm_userTeam" method="post" theme="simple">
                    <s:hidden name="appcodes.acType" value="CRM_TEAM"/>
                    <div class="row" style="margin-top:25px;margin-left:3px !important"> 
                        <div class=" form-group col-md-2">
                            <label><s:text name="lbl.team.name"/><span>*</span></label>
                        </div>
                        <div class="col-md-6 form-group">
                            <s:textfield name="appcodes.acDesc" cssClass="form-control" required="true" title="%{getText('lbl.please.enter.name')}"/>
                        </div>
                    </div>
                    <div class="row" style="margin-top:25px;margin-left:3px !important"> 
                        <div class=" form-group col-md-2">
                            <label><s:text name="lbl.description"/></label>
                        </div>
                        <div class="col-md-6 form-group">
                            <s:textfield name="appcodes.acLongDesc" cssClass="form-control"/>
                        </div>
                    </div>
                </s:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('#chk_userLockYn').prop('checked', <s:property value='"1".equals(crmUser.userLockYn)'/>);
        $('#frm_user_detail').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            rules: {
                "crmUser.userName": {
                    required: true
                },
                "crmUser.userEmailId": {
                    required: true
                },
                "crmUser.userRoleSeq": {
                    required: true
                },
                "crmUser.userLdapId": {
                    required: true
                },
                "crmUser.userAgentType": {
                    required: true
                },
            },
            messages: {
                "crmUser.userName": {
                    required: "Please enter username"
                },
                "crmUser.userEmailId": {
                    required: "Please enter valid email id"
                },
                "crmUser.userRoleSeq": {
                    required: "Please select level"
                },
                "crmUser.userLdapId": {
                    required: "Please Windows User Id"
                },
                "crmUser.userAgentType": {
                    required: "Please Select Agent Type "
                },
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
                    $("#msg_user_detail").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_user_detail").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_user_detail").show();
                else
                    $("#msg_user_detail").hide();
            }
        });

        $("#userTeam").on("change", function () {
            if ($(this).val() === '') {
                $("#userRoleSeq").val('1');
            }
        });

        $("#userRoleSeq").on("change", function () {
            if ($(this).val() === '1') {
                $("#userTeam").val('');
            }
        });

        console.log('Before loading tbl_user_company..');
        var tbl_user_company = $("#tbl_user_company").DataTable({
            searching: false,
            ordering: false,
            info: false,
            "destroy": true,
            "lengthChange": false,
            "pageLength": 5,
            "responsive": true,
            autoWidth: false,
            "processing": true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadTaskAttendeeCompany.do?user.userId=" + $("#frm_userId").val()
            },
            columns: [
                {data: "key"},
                {data: "value",
                    render: function (data, type, row, meta) {
                        return APP_CONFIG.companyList[data] ? APP_CONFIG.companyList[data].name : data;
                    }
                },
                {data: "key",
                    render: function (data, type, row, meta) {
                        return '<i class="fa fa-trash-o" title="delete" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="deleteCompany(\'' + row.key + '\', \'' + row.value + '\');"></i>';
                    }
                }
            ],
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            }
        });

        $("#btn_add_user_frm_appl_comp").on("click", function () {
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveTaskAttendeeCompany.do",
                data: {"user.companyCode": $("#company").val(), "user.userId": $("#frm_userId").val()},
                success: function (result) {
                    if (result.messageType === "S") {
                        $.notify("Record saved successfully", "custom");
                        $("#tbl_user_company").DataTable().ajax.reload();
                    } else {
                        $.notify("Record Already Added", "custom");
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        });
        $("#btn_team_close").on("click", function () {
            $("#team_modal_dialog").modal("hide");
        });


        $("#btn_add_user").on("click", function () {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/openUserDetailForm.do",
                data: {"flex1": $("#hid_username").val()},
                success: function (result) {
                    $('#popup_custom').html(result);
                    $('.popup-wrap').addClass('popup-open');
                    $('#overlay').show();
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        });


        $("#btn_user_save").on("click", function () {
            if (!$('#frm_user_detail').valid()) {
                return false;
            }
            if ($("#userMaxSession").val() > 10) {
                 $('#msg_user_detail').empty().html("Enter Maximum Session Count to 10").show();
                return false;
            }
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveUserDetailsForm.do?",
                data: $("#frm_user_detail").serialize(),
                success: function (result) {
                    if (result.messageType === "S") {
                        $.notify("User details saved successfully", "custom");
                        $('.popup-wrap').removeClass('popup-open');
                        $('#overlay').hide();
                        reloadDt("tbl_task_allocate");
                    } else {
                        $('#msg_user_detail').empty().html(result.message).show();
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
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
    function deleteCompany(userId, code) {
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/deleteTaskAttendeeCompany.do",
            data: {"user.companyCode": code, "user.userId": userId},
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify("Record Deleted successfully", "custom");
                    $("#tbl_user_company").DataTable().ajax.reload();

                } else {
                    $.notify("Record not Deleted successfully", "custom");
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
    /* function showUserTeamForm(){
     block('block_body');
     $.ajax({
     type: "POST",
     url: APP_CONFIG.context + "/openUserTeamForm.do",
     data: $("#frm_user_detail").serialize(),
     success: function (result) {
     $('#plugin_modal_dialog .modal-dialog').removeClass("modal-mg modal-mm modal-lg modal-sm").addClass("modal-mm");
     $('#plugin_modal_dialog .modal-content').empty().html(result);
     $('#plugin_modal_dialog').modal('show');
     },
     error: function (xhr, status, error) {
     alert("Error: " + error);
     },
     complete: function () {
     unblock('block_body');
     
     }
     });
     }*/
    function openTeamForm() {
        $("#frm_userTeam input[type='text']").val("");
        $("#team_modal_dialog").modal("show");

    }

    function saveUserTeamForm() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/saveUserTeamForm.do",
            data: $("#frm_userTeam").serialize(),
            success: function (result) {
                if (result.messageType === "S") {
                    $("#userTeam").append("<option value='" + result.flex4 + "'>" + $("#frm_userTeam input[name='appcodes.acDesc']").val() + "</option>");
                    $('#userTeam').val(result.flex4);
                    $.notify("User Team saved successfully", "custom");
                    $("#team_modal_dialog").modal("hide");
                } else {
                    $("#msg_team").html(result.message).show();
                    setTimeout(function () {
                        $('#msg_team').hide();
                    }, 2000);
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