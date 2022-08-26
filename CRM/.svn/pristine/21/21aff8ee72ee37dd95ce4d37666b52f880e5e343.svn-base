<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : taskAttendee
    Created on : Apr 25, 2017, 9:41:30 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    div.dataTables_wrapper div.dataTables_filter input {
        margin-top: 10Px !important;
    }
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery-ui.js"></script>
<div class="modal fade" id="user_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog  modal-mm">
        <div class="modal-content">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><s:text name="lbl.user"/></h4>
                    <div style="margin-top: -31px; float: right;">
                        <button class="save-btn btn btn-primary" onclick="saveUserExtension();">&#10004;<s:text name="btn.save"/></button>
                        <button class="close-btn btn" data-dismiss="modal">&#10006; <s:text name="btn.close"/></button>
                    </div>
                </div>
                <div class="body" style="padding: 7px;">
                    <s:form id="frm_user" name="frm_user">
                        <s:hidden name="user.userId" id="userId"/>
                        <div class="row">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <label><s:text name="lbl.extension"/>: <span>*</span></label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <s:textfield name="user.userTelNo" id="userTelNo" cssClass="form-control" required="true" title="%{getText('lbl.please.enter.extension')}"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </s:form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="menu_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog  modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <div id="msg_menu" class="alert alert-danger" style="display: none;"></div>
                <h4 class="modal-title"><s:text name="lbl.menu"/></h4>
                <div style="margin-top: -31px; float: right;">
                    <button class="save-btn btn btn-primary" id="btn_user_team_save" onclick="saveUserMenuForm()">&#10004;<s:text name="btn.save"/></button>
                    <button class="close-btn btn" data-dismiss="modal">&#10006; <s:text name="btn.close"/></button>
                </div>
            </div>
            <div class="body" style="margin-top: 40px;">
                <s:form id="frm_userTeam" name="frm_userTeam" method="post" theme="simple">
                    <s:hidden name="userId" id="frm_userId"/>
                    <div class="row" style="margin-top:25px;margin-left:3px !important">
                        <div class="form-group col-md-12">
                            <ul>
                                <li>
                                    <input type="checkbox" name="userMenus" id="chk_menu_D" value="D" class="filled-in chk-col-deep-orange"/>
                                    <label for="chk_menu_D"><s:text name="lbl.dashboard"/></label>
                                </li>
                                <li>
                                    <input type="checkbox" name="userMenus" id="chk_menu_T" value="T" class="filled-in chk-col-deep-orange">
                                    <label for="chk_menu_T"><s:text name="lbl.tasks"/></label>
                                </li>
                                <li>
                                    <input type="checkbox" name="userMenus" id="chk_menu_A" value="A" class="filled-in chk-col-deep-orange">
                                    <label for="chk_menu_A"><s:text name="lbl.activities"/></label>
                                </li>
                                <li>
                                    <input type="checkbox" name="userMenus" id="chk_menu_L" value="L" class="filled-in chk-col-deep-orange">
                                    <label for="chk_menu_L"><s:text name="lbl.leads"/></label>
                                </li>
                                <li>
                                    <input type="checkbox" name="userMenus" id="chk_menu_C" value="C" class="filled-in chk-col-deep-orange">
                                    <label for="chk_menu_C"><s:text name="lbl.contact"/></label>
                                </li>
                                <li>
                                    <input type="checkbox" name="userMenus" id="chk_menu_S" value="S" class="filled-in chk-col-deep-orange">
                                    <label for="chk_menu_S"><s:text name="btn.search"/></label>
                                </li>
                                <li>
                                    <input type="checkbox" name="userMenus" id="chk_menu_R" value="R" class="filled-in chk-col-deep-orange">
                                    <label for="chk_menu_R"><s:text name="lbl.reports"/></label>
                                </li>
                                <li>
                                    <input type="checkbox" name="userMenus" id="chk_menu_DM" value="DM" class="filled-in chk-col-deep-orange">
                                    <label for="chk_menu_DM"><s:text name="lbl.marketing"/></label>
                                </li>
                                <li>
                                    <input type="checkbox" name="userMenus" id="chk_menu_W" value="W" class="filled-in chk-col-deep-orange">
                                    <label for="chk_menu_W"><s:text name="lbl.whatsApp"/></label>
                                </li>
                                <ul><b><s:text name="lbl.admin"/></b>
                                    <li>
                                        <input type="checkbox" name="userMenus" id="chk_menu_CL" value="CL" data-mm="AD" class="filled-in chk-col-deep-orange">
                                        <label for="chk_menu_CL"><s:text name="lbl.calllog"/></label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="userMenus" id="chk_menu_BS" value="BS" data-mm="AD" class="filled-in chk-col-deep-orange">
                                        <label for="chk_menu_BS"><s:text name="lbl.bulk.sms"/></label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="userMenus" id="chk_menu_U" value="U" data-mm="AD" class="filled-in chk-col-deep-orange">
                                        <label for="chk_menu_U"><s:text name="lbl.agent"/></label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="userMenus" id="chk_menu_AL" value="AL" data-mm="AD" class="filled-in chk-col-deep-orange">
                                        <label for="chk_menu_AL"><s:text name="lbl.allocation"/></label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="userMenus" id="chk_menu_WM" value="WM" data-mm="AD" class="filled-in chk-col-deep-orange">
                                        <label for="chk_menu_WM"><s:text name="lbl.whatsApp"/></label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="userMenus" id="chk_menu_HO" value="HO" data-mm="AD" class="filled-in chk-col-deep-orange">
                                        <label for="chk_menu_HO"><s:text name="lbl.holiday"/></label>
                                    </li>
                                </ul>
                            </ul>
                        </div>
                    </div>
                </s:form>
            </div>
        </div>
    </div>
</div>
<div class="col-md-12 right-pad">
    <div class="dash-leads" style="border-top:0!important">
        <div class="acti-off my-reminder" style="margin-top: -3px;border-top-color: #FFF">

            <div class="acti-off-heads">
                <a href="javascript:void(0);" class="active" data-tab="tab_agent_details"><s:text name="lbl.agent.details"/></a>
                <a href="javascript:void(0);" class="" data-tab="tab_agent_db" onclick="openAgentDashboard();"><s:text name="lbl.agent.dashboard"/></a>
            </div>
            <ul class="act-name current" id="tab_agent_details">
                <div class="my-bord bor2" style="margin-bottom: 10px;">
                    <div id="msg_user_detail_show" class="alert alert-danger" style="display: none;"></div>
                    <div class="form-inline">
                        <div class="form-group padR">
                            <label><s:text name="lbl.anoud.user.name"/></label>
                            <s:textfield name="userNameDesc"  id="txt_username" cssClass="form-control"  placeholder="%{getText('lbl.enter.min.three.chars')}"/>
                            <s:hidden name="userName" id="hid_username"/> 
                        </div>
                        <div class="form-group padR">
                            <button class="btn btn-warning tmargin cbtn" id="btn_add_user" data-dismiss="modal" aria-hidden="true" style="width: 74px; margin-top: 0.5px;"><s:text name="btn.add"/></button>
                        </div>
                    </div>
                    <table id="tbl_task_allocate" class="table table-striped table-bordered display dataTable dtr-inline">
                        <thead>
                            <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                <th class="text-center"><s:text name="lbl.status"/></th>
                                <th class="text-center"><s:text name="lbl.name"/></th>
                                <th class="text-center"><s:text name="lbl.email.id"/></th>
                                <th class="text-center"><s:text name="lbl.mobile"/></th>
                                <th class="text-center"><s:text name="lbl.ext"/></th>
                                <th class="text-center"><s:text name="lbl.team"/></th>
                                <th class="text-center"><s:text name="lbl.level"/></th>
                                <th class="text-center"><s:text name="lbl.action"/></th>
                            </tr>
                        </thead>
                        <tbody>	   
                        </tbody>
                    </table>
                </div>
            </ul>
            <ul class="act-name" id="tab_agent_db">
                <div id="agent_dashboard">
                </div>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1; //January is 0!
        var yyyy = today.getFullYear();
        if (dd < 10) {
            dd = '0' + dd;
        }
        if (mm < 10) {
            mm = '0' + mm;
        }
        var today = dd + '/' + mm + '/' + yyyy;
        var tbl_task_allocate = $("#tbl_task_allocate").DataTable({
            //paging: true,
            searching: true,
            ordering: true,
            info: false,
            "destroy": true,
            "lengthChange": false,
            //"pageLength": 20,
            "responsive": true,
            autoWidth: false,
            "processing": true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadTaskAttendeeData.do",
                "datatype": "json",
            },
            columns: [
                {data: "userLastLoginDt",
                    render: function(data, type, row, meta) {
                        if (row.userLoginStatus === "1") {
                            return '<div class="status-online stats" title="Online" data-toggle="tooltip" data-placement="top">&nbsp;</div>&nbsp;';
                        } else {
                            return '<div class="status-offline stats" title="Offline" data-toggle="tooltip" data-placement="top">&nbsp;</div>&nbsp;';
                        }
                    }
                },
                {data: "userName",
                    render: function(data, type, row, meta) {
                        return  data + (row.userAdministratorYn === '1' ? '&nbsp;<i class="fa fa-address-card" style="font-size: 16px; vertical-align: middle; color: #004b80;"></i>' : '');
                    }
                },
                {data: "userEmailId"},
                {data: "userMobileNo",
                    render: function(data, type, row, meta) {
                        return data;
                        //return row.userMobileNo ? row.userMobileNo + (row.userTelNo ? '/' + row.userTelNo : '') : (row.userTelNo ? row.userTelNo : '');
                    }
                },
                {data: "userTelNo"},
                {data: "userTeamDesc"},
                {data: "userRoleSeqDesc"},
                {data: "userCrmAgentYn",
                    render: function(data, type, row, meta) {
                        let l = '<center><i class="fa fa-pencil hand" onclick="javascript: editUserInfo(\'' + row.userId + '\');" title="Edit"></i>';
                        if (row.userLockYn !== "1") {
                            l += '&nbsp;&nbsp;<i class="fa fa-bars hand" onclick="javascript: editMenu(\'' + row.userId + '\');" title="Modify menu permission"></i>';
                        }
                        l += '</center>';
                        return  l;
                    }
                }
            ],
            language: {
                searchPlaceholder: "Search..."
            },
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function() {
                $("#tbl_task_allocate tr td").css('cursor', 'default');
                //$('#datatable_search').html($('#tbl_task_allocate_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
            "fnRowCallback": function(nRow, data, row, iDisplayIndex, iDisplayIndexFull) {
                if (data.userLockYn === '1') {
                    $(nRow).attr('style', 'color:red !important; text-decoration: line-through; text-decoration-style: double');
                }
            }
        });

        $(function() {
            $('.acti-off-heads a').click(function() {
                var tab_id = $(this).attr('data-tab');

                $('.acti-off-heads a').removeClass('active');
                $('.act-name').removeClass('current');

                $(this).addClass('active');
                $("#" + tab_id).addClass('current');
            });

            $('#sidebar').slimScroll({
                height: '100%',
                width: '100%',
            });
            $('.leads-tab').click(function() {
                $('.popup-wrap').addClass('popup-open');
                $('#overlay').show();

            });
            $('.close-link').click(function() {
                $('.popup-wrap').removeClass('popup-open');
                $('#overlay').hide();
            });
            $('.close-btn').click(function() {
                $('.popup-wrap').removeClass('popup-open');
                $('#overlay').hide();
            });
        });
        
        $("#btn_add_user").on("click", function() {
            if ($("#txt_username").val() === "" || $("#txt_username").val() === null || $("#txt_username").val() === undefined) {
                $.notify("Please Select Anoud User Name", "custom");
                $("#hid_username").clear();
                return false;
            }
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/openUserDetailForm.do?",
                data: {"flex1": $("#hid_username").val(), "operation": "add"},
                success: function(result) {
                    if (result.messageType === "E") {

                        $('#msg_user_detail_show').empty().html(result.message).show();
                        setTimeout(function() {
                            $('#msg_user_detail_show').hide();
                        }, 2000);
                    } else {
                        $('#popup_custom').html(result);
                        $('.popup-wrap').addClass('popup-open');
                        $('#overlay').show();
                    }
                },
                error: function(xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function() {
                    unblock('block_body');
                }
            });
        });

        var has_valid_values = false;
        var has_values_modified = false;
        $("#txt_username").autocomplete({
            source: function(request, response) {
                var flex1 = request.term;
                $.ajax({
                    url: APP_CONFIG.context + "/UserList.do",
                    data: {
                        flex1: flex1
                    },
                    success: function(data) {
                        unblock();
                        var rows = [];
                        $.each(data.userList, function(i, v) {

                            rows.push({label: v.value, value: v.value, key: v.key}); //label=display, value=value to display in txtbox after selection
                        });
                        response(rows);
                    },
                    error: function(xhr, status, error) {
                        displayAlert('E', error);
                    }
                });
            },
            select: function(event, ui) {
                has_valid_values = true;
                $("#hid_username").val(ui.item.key);
                $(this).val(ui.item.value);
                //var inputs = $(this).closest('form').find(':input').not(':hidden');
                // inputs.eq(inputs.index(this) + 1).focus();
                event.preventDefault();
            },
            change: function(event, ui) {
                has_values_modified = true;
            },
            open: function() {
                $(this).removeClass("ui-corner-all ui-corner-top");
            },
            close: function() {
                $(this).removeClass("ui-corner-top ui-corner-all");
            },
            minLength: 3,
            autoFocus: true
        }).on("blur", function(event) {
            if (!has_valid_values && has_values_modified) {
                $(this).val("");
            }
            has_valid_values = false;
            has_values_modified = false;
        });
    });

    function saveTaskAttendeeData() {
        var data = $('#tbl_task_allocate [name="task_allocate_name"]:checked').map(function() {
            return this.value;
        }).get().join(',');
        if ($.trim(data) === "") {
            return;
        }
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/saveTaskAttendeeData.do",
            data: {"flex1": data},
            success: function(result) {
                if (result.messageType === "S") {
                    $.notify("Record saved successfully", "custom");
                    $("#tbl_task_allocate").DataTable().ajax.reload();
                } else {
                    $.notify("Record not saved successfully", "custom");
                }
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
                unblock('block_body');
            }
        });
    }

    function editUserInfo(userId) {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openUserDetailForm.do?",
            data: {"flex1": userId, "operation": "edit"},
            success: function(result) {
                if (result.messageType === "E") {
                    $('#msg_user_detail_show').empty().html(result.message).show();
                } else {
                    $('#popup_custom').html(result);
                    $('.popup-wrap').addClass('popup-open');
                    $('#overlay').show();
                }
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
                $.notify("Record All Ready Exist", "custom");
            },
            complete: function() {
                unblock('block_body');
            }
        });
    }

    function editMenu(userId) {
        var data = {"crmUser.userId": userId};
        $("#frm_userTeam input[name=userMenus").prop('checked', false);
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openMenuForm.do",
            data: data,
            success: function(result) {
                if (result.crmUser !== 'undefined' && result.crmUser.userApplModules) {
                    var menus = JSON.parse(result.crmUser.userApplModules);
                    $.each(menus, function(i, o) {
                        if (o.hasOwnProperty("AD")) {
                            var sm = o.AD;
                            $.each(sm, function(i, o) {
                                $("#frm_userTeam #chk_menu_" + o).prop('checked', true);
                            });
                        } else {
                            $("#frm_userTeam #chk_menu_" + o).prop('checked', true);
                        }
                    });
                    $("#menu_modal_dialog").modal("show");
                    $("#frm_userTeam #frm_userId").val(userId);
                }
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
                unblock('block_body');
            }
        });
    }

    function saveUserExtension() {
        if ($.trim($("#frm_user #userTelNo").val()) === "") {
            return;
        }
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/saveTaskAttendeeExtnForm.do",
            data: $("#frm_user").serialize(),
            success: function(result) {
                if (result.messageType === "S") {
                    $.notify("Record saved successfully", "custom");
                    $("#tbl_task_allocate").DataTable().ajax.reload();
                } else {
                    $.notify("Record not saved successfully", "custom");
                }
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
                $("#user_modal_dialog").modal("hide");
                unblock('block_body');
            }
        });
    }

    function openAgentDashboard() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openAgentDashBoardPage.do",
            data: {},
            success: function(result) {
                $("#agent_dashboard").html(result).show();
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
                unblock('block_body');
            }
        });
    }

    function saveUserMenuForm() {
        var userId = $("#frm_userId").val();
        var subMenu = [];
        var menu = [];
        var selectedMenu = $('input[name=userMenus]');
        $.each(selectedMenu, function(i, o) {
            if ($(o).prop('checked')) {
                if ($(o).data('mm') === 'AD') {
                    subMenu.push($(o).val());
                } else {
                    menu.push($(o).val());
                }
            }
        });
        menu.push({"AD": subMenu});
        console.log(JSON.stringify(menu));
        var data = {"crmUser.userApplModules": JSON.stringify(menu), "crmUser.userId": userId}
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/saveUserMenuForm.do",
            data: data,
            success: function(result) {
                if (result.messageType === "S") {
                    $.notify("Record saved successfully", "custom");
                    $("#tbl_task_allocate").DataTable().ajax.reload();
                } else {
                    $.notify("Record not saved successfully", "custom");
                }
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
                $("#menu_modal_dialog").modal("hide");
                unblock('block_body');
            }
        });
    }
</script>