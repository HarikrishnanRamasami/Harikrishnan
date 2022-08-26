<%-- 
    Document   : waAutoMessageForm
    Created on : Aug 15, 2019, 2:07:55 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div id="block_body">
    <div class="col-md-12 right-pad" id="block_body">
        <div class="dash-leads">
            <div class="my-bord">
                <h3><s:text name="lbl.whatsapp.head.whatsapp"/></h3>
            </div>
            <div class=" bor2">
                <div class="col-sm-12 col-md-3 board-icons Rbtn">
                    <button id="addAutoMsg" onclick="openAddDialog();" class="btn btn-primary"><i class="fa fa-plus"></i> <s:text name="btn.add"/></button> 
                    <!--    <div class="popup-title">
                            <h4>Auto Message Form</h4>
                        </div> -->
                </div>
            </div>

            <div class="modal fade" id="msg_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
                <div class="modal-dialog  modal-mm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title"><s:text name="lbl.whatsapp.head.auto.msg.form"/></h4>
                            <div style="margin-top: -31px; float: right;">
                                <button class="save-btn btn btn-primary" onclick="saveForm()" id="btn_upd_task">&#10004;<s:text name="btn.save"/></button>
                                <button class="close-btn btn" data-dismiss="modal" id="btn_task_close">&#10006; <s:text name="btn.close"/></button>
                            </div>
                        </div>
                        <div class="modal-body" style="margin-top: 20px;">
                            <div id="msg_frm" class="alert alert-danger" style="display: none;"></div>
                            <s:form id="frm_wa_auto_msg" name="frm_wa_auto_msg" method="post" theme="simple">
                                <s:hidden name ="operation" id="hid_operation"/>                                
                                <div class="row" style="margin-top:10px;"> 
                                    <div class="form-group col-md-2">
                                        <label><s:text name="lbl.common.name"/></label>
                                    </div>
                                    <div class="col-md-10 form-group"> 
                                        <s:textfield name="autoMsg.wamName" id="wamName" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.name')}"  />
                                    </div>
                                </div>

                                <div class="row" style="margin-top:10px;"> 
                                    <div class="form-group col-md-2">
                                        <label><s:text name="lbl.whatsapp.effective.fm.dt"/></label>
                                    </div>
                                    <div class="col-md-4 form-group"> 
                                        <s:textfield name="autoMsg.wamEffFromDate" id="wamEffFromDate" cssClass="textbox datepicker calicon" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.whatsapp.effective.fm.dt')}"  />
                                    </div>

                                    <div class="form-group col-md-2">
                                        <label><s:text name="lbl.whatsapp.effective.to.dt"/></label>
                                    </div>
                                    <div class="col-md-4 form-group"> 
                                        <s:textfield name="autoMsg.wamEffToDate" id="wamEffToDate" cssClass="textbox datepicker calicon" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.whatsapp.effective.to.dt')}"  />
                                    </div>
                                </div>

                                <div class="row" style="margin-top:10px;"> 
                                    <div class="form-group col-md-2">
                                        <label><s:text name="lbl.message"/></label>
                                    </div>
                                    <div class="col-md-10 form-group">
                                        <s:textarea name="autoMsg.wamMessage" id="wamMessage" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="row" style="margin-top:10px;"> 
                                    <div class="form-group col-md-2">
                                        <label><s:text name="lbl.common.head.sub.upper.url"/></label>
                                    </div>
                                    <div class="col-md-10 form-group">
                                        <s:textfield name="autoMsg.wamMessageImg" id="wamMessageImg" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="row" style="margin-top:10px;"> 
                                    <div class=" form-group col-md-2">
                                        <label><s:text name="lbl.whatsapp.repeat.yn"/></label>
                                    </div>
                                    <div class="col-md-10 form-group">
                                        <s:checkbox name="autoMsg.wamRepeatYn" id="wamRepeatYn" />
                                    </div>
                                </div>

                                <div id="block_repeatYn">
                                    <div class="row" style="margin-top:10px;"> 
                                        <div class=" form-group col-md-2">
                                            <label><s:text name="lbl.whatsapp.from.time"/></label>
                                        </div>
                                        <div class="col-md-4 form-group "> 
                                            <s:textfield name="autoMsg.wamFromTime" id="wamFromTime" cssClass="textbox timepicker calicon" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.whatsapp.effective.from.time')}"  />
                                        </div>
                                        <div class=" form-group col-md-2">
                                            <label>To Time</label>
                                        </div>
                                        <div class="col-md-4 form-group"> 
                                            <s:textfield name="autoMsg.wamToTime" id="wamToTime" cssClass="textbox timepicker calicon" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.whatsapp.effective.to.time')}"  />
                                        </div>
                                    </div>
                                    <div class="row" style="margin-top:10px;"> 
                                        <div class=" form-group col-md-2">
                                            <label><s:text name="lbl.whatsapp.from.time.day"/></label>
                                        </div>
                                        <div class="col-md-4 form-group">
                                            <s:select list="#{0:'Same Day', 1:'Next Day'}" name="autoMsg.wamFmTimeOn" cssClass="form-control" value = "#{0}"/>
                                        </div>
                                        <div class=" form-group col-md-2">
                                            <label><s:text name="lbl.whatsapp.to.time.day"/></label>
                                        </div>
                                        <div class="col-md-4 form-group">
                                            <s:select list="#{0:'Same Day', 1:'Next Day'}" name="autoMsg.wamToTimeOn" cssClass="form-control" value = "#{0}"/>
                                        </div>
                                    </div>

                                    <div class="row" style="margin-top:10px;"> 
                                        <div class=" form-group col-md-2">
                                            <label><s:text name="lbl.days"/></label>
                                        </div>
                                        <div class="col-md-2 form-group">
                                            <s:checkbox name="autoMsg.wamDay1Yn"/> <s:text name="lbl.common.sun"/>
                                        </div>
                                        <div class="col-md-2 form-group">
                                            <s:checkbox name="autoMsg.wamDay2Yn"/> <s:text name="lbl.common.mon"/>
                                        </div>
                                        <div class="col-md-2 form-group">
                                            <s:checkbox name="autoMsg.wamDay3Yn"/> <s:text name="lbl.common.tue"/>
                                        </div>
                                        <div class="col-md-2 form-group">
                                            <s:checkbox name="autoMsg.wamDay4Yn"/> <s:text name="lbl.common.wed"/>
                                        </div>
                                        <div class="col-md-2 form-group">
                                            <s:checkbox name="autoMsg.wamDay5Yn"/> <s:text name="lbl.common.thu"/>
                                        </div>
                                    </div>

                                    <div class="row" style="margin-top:10px;"> 
                                        <div class=" form-group col-md-2">
                                            <label></label>
                                        </div>
                                        <div class="col-md-2 form-group">
                                            <s:checkbox name="autoMsg.wamDay6Yn"/> <s:text name="lbl.common.fri"/>
                                        </div>
                                        <div class="col-md-2 form-group">
                                            <s:checkbox name="autoMsg.wamDay7Yn"/> <s:text name="lbl.common.sat"/>
                                        </div>
                                    </div>
                                </div>
                            </s:form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table id="msg_tbl" class="table table-striped table-bordered display dataTable dtr-inline">
                        <thead>
                            <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                <th><s:text name="lbl.common.name"/></th>
                                <th><s:text name="lbl.whatsapp.head.sub.eff.from.dt"/></th>
                                <th><s:text name="lbl.whatsapp.head.sub.eff.to.dt"/></th>
                                <th style="width: 10%;"><s:text name="lbl.whatsapp.from.time"/></th>
                                <th><s:text name="lbl.whatsapp.to.time"/></th> 
                                <th><s:text name="lbl.message"/></th>
                                <th><s:text name="lbl.whatsapp.repeat.yn"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function openAddDialog() {
        $("#msg_modal_dialog").modal("show");
    }

    function saveForm() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/saveWhatsAppAutoForm.do",
            data: $("#frm_wa_auto_msg").serialize(),
            success: function (result) {
                if (result.message !== null)
                {
                    $("#msg_frm").html(result.message).show();
                } else {
                    $("#msg_modal_dialog").modal("hide");
                    $('#frm_wa_auto_msg').trigger("reset");
                    $("#msg_tbl").DataTable().ajax.reload();
                    $("#msg_frm").html(result.message).hide();
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

    $(document).ready(function () {
        $(".datepicker").datetimepicker({format: 'DD/MM/YYYY HH:mm'});
        $(".timepicker").datetimepicker({format: 'HH:mm'});
        $('#msg_tbl').on('draw.dt', function () {
            $('[data-toggle="tooltip"]').tooltip({html: true});
        });

        $('#wamRepeatYn').on('click change', function () {
            if ($(this).is(":checked")) {
                $('#block_repeatYn').show();
            } else {
                $('#block_repeatYn').hide();
            }
        });

        var url = APP_CONFIG.context + "/loadAutoMessageData.do?" + $('#frm_wa_auto_msg').serialize();
        msg_tbl = $("#msg_tbl").DataTable({
            paging: true,
            searching: true,
            responsive: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 10,
            destory: true,
            "ajax": {
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "msgData"
            },
            columns: [
                {data: "wamName"},
                {data: "wamEffFromDate",
                    render: function (data, type, row, meta) {
                        if (row.wamEffFromDate === null || row.wamEffFromDate === '')
                            return   "";
                        else
                            return   moment(new Date(row.wamEffFromDate)).format("DD/MM/YYYY HH:mm");
                    }
                },
                {data: "wamEffToDate",
                    render: function (data, type, row, meta) {
                        if (row.wamEffToDate === null || row.wamEffToDate === '')
                            return   "";
                        else
                            return  moment(new Date(row.wamEffToDate)).format("DD/MM/YYYY HH:mm");
                    }
                },
                {data: "wamFromTime"},
                {data: "wamToTime",
                    render: function (data, type, row, meta) {
                        if (row.wamToTimeOn === 0) {
                            return row.wamToTime;
                        } else {
                            if (row.wamToTime !== null)
                                return row.wamToTime + " + " + row.wamToTimeOn + " day";
                            else
                                return row.wamToTime;
                        }

                    }
                },
                {data: "wamMessage"},
                {data: "wamRepeatYn",
                    render: function (data, type, row, meta) {
                        if (row.wamRepeatYn === 'Y') {
                            return "Yes";
                        } else {
                            return "No";
                        }
                    }
                }
            ],
            tableTools: {
                "sRowSelect": "single",
            },
            initComplete: function () {
                $("#msg_tbl tr td").css('cursor', 'default');
            }
        });

        $('#wamRepeatYn').trigger('change');
    });
</script>