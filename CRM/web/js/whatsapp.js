var unread_tbl_interval;
$(document).ready(function () {
    $('#modal_dialog').modal('hide');
    connect(WA_CONFIG.loggedInUserId);
    unread_tbl = $("#unread_tbl").DataTable({
        paging: true,
        searching: true,
        ordering: true,
        info: true,
        lengthChange: false,
        autoWidth: false,
        pageLength: 10,
        responsive: true,
        destory: true,
        processing: false,
        serverSide: false,
        "ajax": {
            "url": APP_CONFIG.context + "/loadWhatsAppData.do",
            "type": "POST",
            "dataSrc": "txnList"
        },
        columns: [
            {data: "wtName", className: "never"},
            {data: "wtMobileNo", className: "col-md-4",
                render: function (data, type, row, meta) {
                    var notificationCnt = row.wtFlex01 !== "0" ? '&nbsp;<span class="badge" style="background: green">' + row.wtFlex01 + '</span>' : '';
                    if (row.wtName !== null) {
                    	return row.wtName + ' - ' + row.wtMobileNo + notificationCnt;
                    } else {
                    	return row.wtMobileNo + notificationCnt;
                    }
                }
            },
            {data: "wtUpdDt", className: "col-md-2",
                render: function (data, type, row, meta) {
                    if (type === "sort") {
                        data = moment(data).format('YYYYMMDDHHmm');
                    } else {
                        data = moment(data).format('DD/MM/YYYY HH:mm');
                    }                    
                    return data + '&nbsp;<i class="fa fa-info-circle hand" title="Since ' + moment(row.wtDate).format("DD/MM/YYYY HH:mm") + '"></i>';
                }
            },
            {data: "wtAssignedTo", className: "never"},
            {data: "wtMsgCount", className: "col-md-1"},
            {data: "wtAssignedToDesc", className: "col-md-3"},
            {data: "",
                render: function (data, type, row, meta) {
                    return '<center>' +
                            '<i class="fa fa-comments-o hand" title="View Message" style="color: #418bca;" onclick="transferRequest(\'' + row.wtTxnId + '\',\'' + (row.wtAssignedTo ? row.wtAssignedTo : '') + '\',\'' + row.wtMobileNo + '\',\'' + (row.wtName ? fixedEncodeURIComponent(row.wtName) : '') + '\');"></i>' +
                            '&nbsp;&nbsp;<i class="fa fa-address-card hand" title="Customer 360 view" style="color: #418bca;" onclick="viewCustomer(\'' + row.wtMobileNo + '\');"></i>' +
                            '&nbsp;&nbsp;<i class="fa fa-fast-forward hand" title="Forward" style="color: #418bca;" onclick="forwardRequest(\'' + row.wtTxnId + '\');"></i>' +
                            '</center>';
                }
            }
        ],
        dom: 'T<"clear">lfrtip',
        "order": [
            [2, "desc"]
        ],
        initComplete: function () {
            $("#unread_tbl tr td").css('cursor', 'default');
        },
        tableTools: {
            "sRowSelect": "single",
            "aButtons": [],
        }
    });

    $("#resolve_submit").on("click", function () {
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/resolveChat.do",
            data: $('#resolve_chat').serialize(),
            success: function () {
                $('#summary-page-two').modal('hide');
                $('#qnimate').removeClass('popup-box-on');
                $('#unread_tbl').DataTable().ajax.reload();
                $.notify("Closed successfully", "custom");
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
            }
        });
    });

    $('#btn_doc,#btn_audio,#btn_img').on("click", function () {
        var type = $(this).data('type');
        if (type === 'file') {
            $('#attachment').attr('accept', 'application/pdf');
        } else if (type === 'audio') {
            $('#attachment').attr('accept', 'audio/*');
        } else if (type === 'image') {
            $('#attachment').attr('accept', 'image/*');
        }
        console.log('File Type', type);
        $('#attachment').data('type', type);
        $('#attachment').trigger('click');
    });

    $("#lab-slide-bottom-popup").on("show.bs.modal", function () {
        removeUpload();
    });

    $("#attachment").on("change", function () {
        var type = $(this).data('type');
        if (type === 'file') {
            $('#btn_audio,#btn_img').hide();
            $('#btn_doc').show();
        } else if (type === 'audio') {
            $('#btn_doc,#btn_img').hide();
            $('#btn_audio').show();
        } else if (type === 'image') {
            $('#btn_doc,#btn_audio').hide();
            $('#btn_img').show();
        }
        var files = this.files;
        if (files && files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('.file-upload-image').attr('src', e.target.result);
                $('.file-upload-content').show();
                $('.image-title').html(files[0].name);
            };
            reader.readAsDataURL(files[0]);
        } else {
            removeUpload();
        }
    });

    $("#send_msg").on("click", function () {
        if ($.trim($("#text_msg").val()) === '' && $("#attachment").val() === '') {
            alert('Please enter data');
            return;
        } else if ($("#attachment").val() !== '' && $.trim($("#text_msg").val()) === '') {
            alert('Please enter text along with attachment');
            return;
        }
        showSending();
        var data = new FormData();
        let t = $("#text_msg").val();
        data.append("log.wlMobileNo", $('#wtMobileNo').val());
        data.append("log.wlTemplateId", $('#hid_template_id').val());
        if($('#hid_template_id').val() !== "") {
            var isok = true;
            let d = [];
            $("#template_data input").each(function(i, o) {
                if($.trim(o.value) === "") {
                    alert('Please fill the all template data');
                    isok = false;
                    return false;
                }
                d.push(o.value);
                data.append(o.name, o.value)
            });
            if(!isok) {
                hideSending();
                return false;
            }
            t = $.validator.format($("#text_msg").val(), d);
        }
        data.append("log.wlText", t);
        //data.append("log.templateData[0].value", ($('#chat_contact').text() ? $('#chat_contact').text() : 'Customer'));
        var msgType = 'T';
        if ($("#attachment").val() !== '') {
            var files = $('input[id="attachment"]')[0].files;
            data.append('attachment', files[0]);
            var fname = files[0].name;
            var ext = fname.slice((fname.lastIndexOf(".") - 1 >>> 0) + 2);
            console.log('Extention', ext);
            if (/(jpe?g|png)$/i.test(ext)) {
                console.log('Image file');
                msgType = 'I';
            } else if (/(pdf|doc|ppt|xls)$/i.test(ext)) {
                console.log('PDF file');
                msgType = 'D';
            } else if (/(mp3|mp4|amr)$/i.test(ext)) {
                console.log('Audio file');
                msgType = 'A';
            } else {
                alert('Unsupported file format');
                hideSending();
                return;
            }
        }
        data.append("log.wlMsgType", msgType);
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: APP_CONFIG.context + "/sendMessage.do",
            data: data,
            dataType: 'json',
            cache: false,
            processData: false,
            contentType: false,
            success: function (result) {
                processMessage(result.log, true);
                $('#lab-slide-bottom-popup').modal('hide');
                $('input[id="text_msg"]').prop('disabled', false).val('');
                $('input[id="attachment"]').val('');
                $('#sel_handy_sms, #hid_template_id').val('');
                $('#template_data').empty().hide();
                scrollDown();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                hideSending();
            }
        });

    });

    $("#sel_handy_sms").on("change", function () {
        $('#hid_template_id').val('');
        $('#template_data').empty().hide();
        $("#text_msg").prop('disabled', false);
        if ($(this).val() !== "") {
            setHandyMsg($(this).val());
        } else {
            $("#text_msg").val("");
        }
    });

    $("#closeChat").click(function () {
        var message = {"wlMobileNo": $('#qnimate').data('mobile'), "wlCrUid": WA_CONFIG.loggedInUserId, "type": "LEAVE"};
        sendMessage(message);
        $('#qnimate').removeClass('popup-box-on');
    });

    $("#profile-img").click(function () {
        $("#status-options").toggleClass("active");
    });

    $(".expand-button").click(function () {
        $("#profile").toggleClass("expanded");
        $("#contacts").toggleClass("expanded");
    });

    $("#status-options ul li").click(function () {
        $("#profile-img").removeClass();
        $("#status-online").removeClass("active");
        $("#status-away").removeClass("active");
        $("#status-busy").removeClass("active");
        $("#status-offline").removeClass("active");
        $(this).addClass("active");
        if ($("#status-online").hasClass("active")) {
            $("#profile-img").addClass("online");
        } else if ($("#status-away").hasClass("active")) {
            $("#profile-img").addClass("away");
        } else if ($("#status-busy").hasClass("active")) {
            $("#profile-img").addClass("busy");
        } else if ($("#status-offline").hasClass("active")) {
            $("#profile-img").addClass("offline");
        } else {
            $("#profile-img").removeClass();
        }
        ;
        $("#status-options").removeClass("active");
    });

    $("#text_msg").on('keydown', function (e) {
        if (e.which === 13) {
            $("#send_msg").trigger("click");
            return false;
        }
    });

    $(".messages").on('click', function () {
        $('#lab-slide-bottom-popup').modal('hide');
    });

    $('#all-resolved').on('click', '[data-dismiss="modal"]', function (e) {
        e.stopPropagation();
    });
    
    $("#btn_init_chat_start").on('click', function () {
        resetInitChatFrm();
        $('#modal_dialog_init_chat').modal('show');
    });
    
    $(document).on("keyup paste input", ".number", function() {
        this.value = this.value.replace(/\D/g, '');
    });

    unread_tbl_interval = window.setInterval(function () {
        unread_tbl.ajax.reload();
    }, (60 * 1000));
    
    $("#modal_dialog_forward #forward_btn_save").on("click", function () {
        if ($('#modal_dialog_forward #forward_assigned_to').val() !== '') {
            $('#modal_dialog_forward #forward_btn_save').hide();
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/updateForwardUser.do",
                data: {"txn.wtAssignedTo": $('#modal_dialog_forward #forward_assigned_to').val(), "txn.wtTxnId": $('#modal_dialog_forward #fwd_wtTxnId').val()},
                success: function () {
                    $('#modal_dialog_forward #msg_fwd').removeClass('alert-danger').addClass('alert-success').html("Successfully forwarded").show();
                    unread_tbl.ajax.reload();
                    setTimeout(function () {
                        $('#modal_dialog_forward').modal('hide');
                    }, 2000);
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                }
            });
        } else {
            $('#modal_dialog_forward #msg_fwd').removeClass('alert-success').addClass('alert-danger').html("Please select forward user").show();
        }
    });
});

function showSending() {
    $("#text_msg").prop('disabled', true);
    $("#send_msg .msg-sending").show();
    $("#send_msg .msg-send").hide();
}

function hideSending() {
    $("#text_msg").prop('disabled', false);
    $("#send_msg .msg-sending").hide();
    $("#send_msg .msg-send").show();
}

function setHandyMsg(code) {
    var lang = "E";
    $.each(HANDY_MSG_JSON, function (i, o) {
        if (o.key === code) {
            var info2 = o.info2;
            info2 = info2.replace(/<br>/g, "\n");
            var info3 = o.info3;
            var info4 = o.info4;
            if(info4 !== null && info4 !== '' && info4 !== undefined) {
                $('#template_data').show();
                $.each(info4.split(";"), function (i, o) {
                    var files = o.split(":")
                    $("#template_data").append('<input type="text" name="log.templateData['+files[0]+'].value" placeholder="'+files[1]+'" class="form-control"/>');
                });
            } else {
                $('#template_data').hide();
            }
            info3 = info3.replace(/<br>/g, "\n");
            $("#text_msg").val((lang === "A" ? info3 : info2));
            if (o.info1 === "T") {
                $('#hid_template_id').val(code);
            }
            if (o.info1 === "F" || o.info1 === "T") {
                $("#text_msg").prop('disabled', true);
            }
            return false;
        }
    });
}

function removeUpload() {
    $('.file-upload-content').hide();
    $('.image-upload-wrap').show();
    $("#attachment").val('');
    $('#btn_doc,#btn_img,#btn_audio').show();
}

function transferRequest(txnId, assignTo, mobileNo, contactName) {
    if (assignTo === '' || assignTo === WA_CONFIG.loggedInUserId) {
        callTransfer(assignTo, txnId, mobileNo, contactName);
    } else {
        $('#modal_dialog').modal('show');
        $('#modal_dialog #assignedUser').html(assignTo);
        $("#btn_save").on("click", function () {
            callTransfer(assignTo, txnId, mobileNo, contactName);
        });
    }
}

function callTransfer(assignTo, txnId, mobileNo, contactName) {
    $.ajax({
        type: "POST",
        url: APP_CONFIG.context + "/transferRequest.do",
        data: {"txn.wtAssignedTo": assignTo, "txn.wtTxnId": txnId, "txn.wtMobileNo": mobileNo},
        success: function (result) {
            $('#modal_dialog').modal('hide');
            unread_tbl.ajax.reload();
            $("#print_msgs").empty();
            $.each(result.logList, function (i, v) {
                processMessage(v);
            });
            scrollDown();
            contactName = decodeURIComponent(contactName);
            $('#resolve_chat #wtMobileNo').val(mobileNo);
            $('#resolve_chat #wtTxnId').val(txnId);
            $('#resolve_chat #wtName').val(contactName);
            $('#qnimate #chat_num').html(mobileNo + ' - ');
            $('#qnimate #chat_contact').val(contactName);
            $('#qnimate #chat_contact').html(contactName);
            // }
            /* else {
             $('#qnimate #chat_num').html(mobileNo);
             }*/
            $('#qnimate').addClass('popup-box-on');
            $('#qnimate').data('mobile', mobileNo);
            var message = {"wlMobileNo": $('#qnimate').data('mobile'), "wlCrUid": WA_CONFIG.loggedInUserId, "type": "JOIN"};
            sendMessage(message);
        },
        error: function (xhr, status, error) {
            alert("Error: " + error);
        },
        complete: function () {
        }
    });
}

function editContactName() {
    $("#edit_name").modal("show");
    var contact = $('#qnimate #chat_contact').val();
    $('#frm_name #wtName').val(contact);
}

function saveContact() {
    if ($('#frm_name #wtName').val() !== 'null' || $('#frm_name #wtName').val() !== '')
    {
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/updateTxnName.do",
            data: {"txn.wtTxnId": $('#resolve_chat #wtTxnId').val(), "txn.wtName": $('#frm_name #wtName').val(), "txn.wtMobileNo": $('#resolve_chat #wtMobileNo').val()},
            success: function () {
                //  alert("Successfully updated the name");
                $("#edit_name").modal("hide");
                $('#qnimate #chat_contact').val($('#frm_name #wtName').val());
                $('#qnimate #chat_contact').html($('#frm_name #wtName').val());
                $('#unread_tbl').DataTable().ajax.reload();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
            }
        });
    }
}

function processMessage(message) {
    console.log("Message", message);
    var isShowError = arguments[1];
    console.log(isShowError);
    var logDate = moment(new Date(message.wlLogDate)).format("HH:mm A | MMMM DD, YYYY");
    if (message.wlFilePath || message.wlText) {
        var isSent = false;
        var hasAttachment = false;
        var attachmentClass = "";
        if (message.wlMsgMode === "I") {
            isSent = false;
        } else if (message.wlMsgMode === "O") {
            isSent = true;
        } else {
            return;
        }
        if (message.wlFilePath) {
            var ext = (message.wlFilePath).slice(((message.wlFilePath).lastIndexOf(".") - 1 >>> 0) + 2);
            if (/(mp3|ogg)$/i.test(ext)) {
                attachmentClass = "audio";
            } else if (/(pdf|doc?x|xls?x|ppt?x)$/i.test(ext)) {
                attachmentClass = "document";
            } else if (/(jpe?g|png|gif|bmp)$/i.test(ext)) {
                attachmentClass = "image";
            } else if (/(mp4)$/i.test(ext)) {
                attachmentClass = "video";
            }
            hasAttachment = true;
        }
        var msg = '<li id="msg_' + message.wlLogId + '" class="' + (isSent ? "sent" : "replies") + ' ' + (hasAttachment ? 'attachment' : '') + '">' + (message.wlParentLogId && !hasAttachment ? '<a href="#msg_' + message.wlParentLogId + '">' : '') + '<p class="tri-right ' + (isSent ? "right-in" : "left-top") + '" data-msgId="' + message.wlMsgId + '">';
        if (hasAttachment) {
            msg += '<a href="' + APP_CONFIG.context + '/api/wa/docs/' + APP_CONFIG.appType + '_' + APP_CONFIG.companyCode + '/' + message.wlFilePath + '" target="blank"><span class="' + attachmentClass + '"></span></a>';
        }
        var statusClass = "";
        if (message.wlErrorId) {
            statusClass = '<span class="error-msg hand" title="' + message.wlErrorMsg + '"></span>';
        } else {
            if ("Y" === message.wlReadYn) {
                statusClass = '<span class="double-tick-active"></span>';
            } else if ("Y" === message.wlDeliverdYn) {
                statusClass = '<span class="double-tick"></span>';
            } else {
                statusClass = '<span class="single-tick"></span>';
            }
        }
        if (message.wlText) {
            if (hasAttachment) {
                msg += '<br />';
            }
            msg += message.wlText;
        }
        msg += '<span class="msg_time_send">' + logDate + (message.wlMsgMode === "I" ? '' : statusClass) + '</span>';
        msg += '</p>' + (message.wlParentLogId && !hasAttachment ? '</a>' : '') + '</li>';
        $(msg).appendTo($('.messages ul'));
        if (message.wlErrorId) {
            if (isShowError === true) {
                alert(message.wlErrorMsg);
            } else {
                console.log(message.wlErrorMsg);
            }
        }
    } else {
        if (message.wlErrorId) {
            var msg_content = $('.messages ul').find('[data-msgid="' + message.wlMsgId + '"]');
            $(msg_content).find('span > span').removeClass().addClass('error-msg hand').attr('title', message.wlErrorMsg);
        } else if ("Y" === message.wlReadYn) {
            var msg_content = $('.messages ul').find('[data-msgid="' + message.wlMsgId + '"]');
            $(msg_content).find('span > span').removeClass().addClass('double-tick-active');
        } else if ("Y" === message.wlDeliverdYn) {
            var msg_content = $('.messages ul').find('[data-msgid="' + message.wlMsgId + '"]');
            $(msg_content).find('span > span').removeClass().addClass('double-tick');
        }
    }
}

var username = "";
var NOTIFY_TYPE = {
    POST: "POST",
    LOGIN: "LOGIN",
    JOIN: "JOIN",
    LEAVE: "LEAVE",
    LOGOUT: "LOGOUT"
}

function connect(un) {
    username = un;
    if (username) {
        var socket = new SockJS(WA_CONFIG.notifyBaseUrl + '/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
}

function onConnected() {
    // Subscribe to the "whatsapp" Topic
    stompClient.subscribe('/topic/whatsapp', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/whatsapp",
            {},
            JSON.stringify({wlCrUid: username, type: NOTIFY_TYPE.LOGIN})
            );
}

function onError(error) {
    var error = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    console.error(error);
}

/**
 * {wlMobileNo: '97430073231', wlCrUid: 'ravindar', wlText: messageInput.value, type: 'JOIN'}
 * 
 * @param {type} message
 * @returns {undefined}
 */
function sendMessage(message) {
    if (message && stompClient) {
        if (($.trim(message.wlMobileNo) !== "" && $.trim(message.wlCrUid) !== "") && (message.type === NOTIFY_TYPE.JOIN || message.type === NOTIFY_TYPE.LEAVE)) {
            stompClient.send("/app/message", {}, JSON.stringify(message));
        } else {
            var error = 'Required parameter missing';
            console.error(error);
        }
    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    if (message.type === NOTIFY_TYPE.JOIN) {
        console.info(message.wlMobileNo + ' viewing by ' + message.wlCrUid);
    } else if (message.type === NOTIFY_TYPE.LEAVE) {
        console.info(message.wlCrUid + ' stopped viewing on ' + message.wlMobileNo);
    } else if (message.type === NOTIFY_TYPE.LOGIN) {
        console.error(message.wlCrUid + ' joined!');
    } else if (message.type === NOTIFY_TYPE.LOGOUT) {
        console.error(message.wlCrUid + ' left!');
    } else {
        // Push the new message to chat window
        if (message !== null && $('#qnimate').data('mobile') === message.wlMobileNo)
            processMessage(message);
    }
    scrollDown();
}

function scrollDown() {
    // Scroll to bottom
    var messageArea = document.querySelector('.messages');
    setTimeout(function () {
        messageArea.scrollTop = messageArea.scrollHeight;
    }, 10);
}

function forwardRequest(txnId) {
    $('#modal_dialog_forward #fwd_wtTxnId').val(txnId);
    $('#modal_dialog_forward #msg_fwd').empty().hide();
    $('#modal_dialog_forward #forward_btn_save').show();
    $('#modal_dialog_forward').modal('show');
}