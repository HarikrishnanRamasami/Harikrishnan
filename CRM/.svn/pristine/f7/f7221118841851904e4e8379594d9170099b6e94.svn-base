<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    All Rights Reserved
    Unauthorized copying of this file, via any medium is strictly prohibited.
 
    Document   : initiateChat
    Created on : Nov 12, 2019, 2:28:02 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<form id="frm_init_chat">
    <div id="msg_init_chat" class="col-md-12 alert alert-danger" style="display: none;"></div>
    <input type="hidden" name="log.wlMobileNo"/>
    <input type="hidden" name="log.wlText"/>
    <input type="hidden" id="hid_temp_text"/>
    <div class="row">
        <div class="col-md-12 m-tb-3">
            <label><s:text name="lbl.to"/> <span>*</span></label>
            <div class="input-group">
                <span class="input-group-addon" style="padding: 0px 0px;">
                    <span><s:select id="sel_init_mobile_cc" list="countryList" listKey="key" listValue="value"/></span>
                </span>
                <s:textfield id="txt_init_mobile" maxlength="15" cssClass="form-control number" required="true" title="%{getText('lbl.common.enter.mob.no')}" />
            </div>
        </div>
        <div class="col-md-12 m-tb-3">
            <label><s:text name="lbl.template"/> <span>*</span></label>
            <select name="log.wlTemplateId" id="sel_init_temp" class="form-control"></select>
        </div>
        <div id="init_temp_data" style="display: none;"></div>
        <div class="col-md-12 m-tb-3">
            <label><s:text name="lbl.message"/></label>
            <div id="init_temp_view" style="display: none;"></div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 m-tb-3">
            <button type="button" class="save-btn btn btn-primary" id="btn_init_chat_save"><i class="fa fa-send"></i> <s:text name="btn.send"/></button>
            <button type="button" class="close-btn btn" data-dismiss="modal"><i class="fa fa-times-circle-o"></i> <s:text name="btn.close"/></button>
        </div>
    </div>
</form>
        
<script type="text/javascript">
    $(document).ready(function () {
        $('#sel_init_temp').append('<option value="">Please select</option>');
        $.each(HANDY_MSG_JSON, function (i, o) {
            if (o.info1 === "T") {
                $('#sel_init_temp').append('<option value="'+o.key+'">'+o.value+'</option>');
            }
        });
        //$('#txt_init_mobile').prev('span').find('span').text(WA_CONFIG.countryCode);
        $('#sel_init_temp').on('change', function () {
            $('#init_temp_data, #init_temp_view').empty().show();
            if ($(this).val() !== "") {
                let code = $(this).val();
                $.each(HANDY_MSG_JSON, function (i, o) {
                    if (o.key === code) {
                        var info2 = o.info2;
                        info2 = info2.replace(/<br>/g, "\n");
                        var info4 = o.info4;
                        if(info4 !== null && info4 !== '' && info4 !== undefined) {
                            $.each(info4.split(";"), function (i, o) {
                                var files = o.split(":");
                                var ip = 
                                '<div class="col-md-12">' +
                                    '<label>'+files[1]+': <span>*</span></label>' +
                                    '<input type="text" name="log.templateData['+files[0]+'].value" class="form-control" required="true" title="Please enter '+files[1]+'" />' +
                                '</div>';
                                $("#init_temp_data").append(ip);
                            });
                        }
                        $('#hid_temp_text').val(info2);
                        fillTemplate();
                        return false;
                    }
                });
                $('#init_temp_data input').on('blur', function() {
                    fillTemplate();
                });
            }
        });
        
        $("#btn_init_chat_save").on("click", function () {
            if ($.trim($("#txt_init_mobile").val()) === '') {
                alert('Please enter mobile number');
                $("#txt_init_mobile").focus();
                return;
            } else if ($("#sel_init_temp").val() === '') {
                alert('Please select a template');
                $("#sel_init_temp").focus();
                return;
            }
            var isok = true;
            $("#init_temp_data input").each(function(i, o) {
                if($.trim(o.value) === "") {
                    isok = false;
                    alert('Please fill the all template data');
                    $(this).focus();
                    return false;
                }
            });
            if(!isok) {
                return false;
            }
            $('input[name="log.wlMobileNo"]').val($('#sel_init_mobile_cc').val() + $('#txt_init_mobile').val());
            $('input[name="log.wlText"]').val($('#init_temp_view').html());
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/sendInviteMessage.do",
                data: $('#frm_init_chat').serialize(),
                success: function (response) {
                    if (response.messageType === APP_CONFIG.messageType.success) {
                        $('#unread_tbl').DataTable().ajax.reload();
                        $('#modal_dialog_init_chat').modal('hide');
                        $.notify("message send successfully", "custom");
                    } else {
                        $("#modal_dialog_init_chat #msg_init_chat").removeClass("alert-success").addClass("alert-danger").html(response.message).show();
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                }
            });
        });
    });
    
    function fillTemplate() {
        let d = [];
        $("#init_temp_data input").each(function(i, o) {
            if($.trim(o.value) === "") {
                d.push("XXXX");
            } else {
                d.push(o.value);
            }
        });
        console.log('Data', d);
        let t = $.validator.format($('#hid_temp_text').val(), d);
        console.log('Text', t);
        $('#init_temp_view').html(t);
    }
    
    function resetInitChatFrm() {
        $("#frm_init_chat")[0].reset();
        $('#sel_init_mobile_cc').val(WA_CONFIG.countryCode);
        $('#init_temp_data, #init_temp_view, #msg_init_chat').empty().hide();
    }
</script>