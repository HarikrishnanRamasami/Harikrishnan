/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function() {
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

$("#search_ctAssignedTo").on("change", function() {
    var $select = $('#search_ctCatg');
    $select.find('option').remove();
    $('<option>').val("ALL").text("All").appendTo($select);
    $('#search_ctSubCatg').html($('<option>').val("ALL").text("All"));
    $.ajax({
        type: "POST",
        url: APP_CONFIG.context + "/loadCategoryList.do",
        data: {"task.ctAssignedTo": $("#search_ctAssignedTo").val()},
        success: function(result) {
            $.each(result.categoryList, function(i, v) {
                $('<option>').val(v.key).text(v.value).appendTo($select);
            });
        },
        error: function(xhr, status, error) {
            alert("Error: " + error);
        },
        complete: function() {
        }
    });
});

$("#search_ctCatg").on("change", function() {
    var $select = $('#search_ctSubCatg');
    $select.find('option').remove();
    $('<option>').val("ALL").text("All").appendTo($select);
    if ($(this).val() === "") {
        return;
    }
    $.ajax({
        type: "POST",
        url: APP_CONFIG.context + "/loadSubCategoryList.do",
        data: {"task.ctCatg": $("#search_ctCatg").val(), "task.ctAssignedTo": $("#search_ctAssignedTo").val()},
        success: function(result) {
            $.each(result.subCategoryList, function(i, v) {
                $('<option>').val(v.key).text(v.value).appendTo($select);
            });
        },
        error: function(xhr, status, error) {
            alert("Error: " + error);
        },
        complete: function() {
        }
    });
});

$(document).ready(function() {

    $('#ctId tbody').on('click', 'tr', function() {
        //            var tabl = $("#ctId").DataTable();
        //            tabl.$('tr.selected').removeClass('selected');
        //            $(this).addClass('selected');
        //            var data = tabl.row(this).data();
        openTaskLog(data.ctId, data.ctStatus);
    });
    
    $("#btn_add_task").on("click", function() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openTaskEntryForm.do?operation=add",
            data: {},
            success: function(result) {
                //reloadDataTable('#task_tbl', result.aaData)
                $('#popup_custom').html(result);
                //$('#plugin_modal_dialog').modals().mm(result);
                $('.popup-wrap').addClass('popup-open');
                $('#overlay').show();
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
                unblock('block_body');
            }
        });
    });
    var addOptions = {
        url: APP_CONFIG.context + "/updateTaskEntryForm.do",
        type: "POST",
        success: tasksaveCallback,
        complete: function() {
        },
        error: function(xhr, status, error) {
            alert(error);
        }
    };
    $('#frm_task_fwd').ajaxForm(addOptions);
    $("#search_ctStatus").on("change", function() {
        if ($(this).val() === 'P') {
            $('.assignAll').show();
            $('[name="select_all"]').show();
        } else {
            $('.assignAll').hide();
            $('[name="select_all"]').hide();
        }
    });

    $("#btn_fwd_bulk_task").on("click", function() {
        /*let selected = task_tbl.$('input[type=checkbox]:checked').map(function() {
         return $(this).val();
         }).get().join(',');*/
        let selected = {}, tot = 0;
        task_tbl.$('input[type=checkbox]:checked').each(function(i, o) {
            let t = {};
            ++tot;
            t['assignToList[' + i + '].key'] = $(o).val();
            Object.assign(selected, t);
        });
        if (tot === 0) {
            alert("Please select at least one task to forward");
        } else {
            if ($("#search_ctStatus").data('dt-uid') === $("#assignAll_ctAssignedTo").val()) {
                alert("You can't forward the task yourself");
                return;
            } else if (confirm('Do you want asign ' + tot + ' task to ' + $("#assignAll_ctAssignedTo option:selected").text())) {
                Object.assign(selected, {'task.ctAssignedTo': $("#assignAll_ctAssignedTo").val(), 'task.ctAssignedToDesc': $("#assignAll_ctAssignedTo option:selected").text()});
                console.log(selected);
                block('block_body');
                $.ajax({
                    type: "POST",
                    url: APP_CONFIG.context + "/saveTaskEntryBulkAssign.do",
                    data: selected,
                    success: function(result) {
                        if (result.messageType === 'S') {
                            $.notify(result.message, "custom");
                            //task_tbl.ajax.url(APP_CONFIG.context + "/loadTaskEntryData.do?" + $('#frm_search').serialize()).load();
                            $("#task_tbl").DataTable().ajax.reload();
                        } else {
                            $.notify("Opps! Error occurred", "custom");
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
        }
    });

    $("#frm_task_close #ctStat").on("change", function() {
        if ($(this).val() === "C") {
            $("#frm_task_close #ctCloseCode").removeAttr("disabled");
        } else {
            $("#frm_task_close #ctCloseCode").attr("disabled", true);
        }
        //$("#ctFlex01").selectpicker('refresh');
    });

    $("#search_task").on("click", function() {
        if (($("#search_ctStatus").val() === 'ALL' || $("#search_ctAssignedTo").val() === 'ALL') && $('#search_ctAssignedDt').val() === '') {
            alert('Please select date range');
            reloadDataTable("#task_tbl", []);
            return false;
        }
        if (($("#search_ctStatus").val() === 'ALL' || $("#search_ctAssignedTo").val() === 'ALL') && (numberOfMonths > 90 || numberOfMonths < 0)) {
            alert('Please select date range within 90 days. Selected ' + numberOfMonths + ' days');
            reloadDataTable("#task_tbl", []);
            return false;
        } else {
            $("#search_ctStatus").data('dt-uid', $("#search_ctAssignedTo").val());
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/loadTaskEntryData.do",
                data: $("#frm_search").serialize(),
                success: function(result) {
                    reloadDataTable("#task_tbl", result.aaData);
                },
                error: function(xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function() {
                    unblock('block_body');
                }
            });
        }
        // numberOfMonths = 0; 
    });

    $("#btn_upd_task").on("click", function() {
        if ($('#frm_task_close #ctCloseRemarks').val().length > 2000) {
            $.notify("remarks should be less than 2000", "custom");
            return false;
        }
        if ($("#ctCloseCode").val() === null) {
            $.notify("Please Select Close Type", "custom");
            return false;
        }
        $("#frm_task_close #ctCloseYn").val($('#ctCloseCode').find('option:selected').attr('title'));
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/updateTaskEntryForm.do",
            data: $("#frm_task_close").serialize(),
            success: function(result) {
                if (result.messageType === "S") {
                    $("#task_modal_dialog").modal("hide");
                    //$("#task_tbl").DataTable().ajax.url(getTaskdDataTableUrl()).load();
                    $("#task_tbl").DataTable().ajax.reload();
                } else {
                    $.notify(result.message, "custom");
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

    $("#btn_fwd_task").on("click", function() {
        if ($('#frm_task_fwd #ctCloseRemarks').val().length > 2000) {
            $.notify("remarks should be less than 2000", "custom");
            return false;
        }
        $('#frm_task_fwd').submit();
    });

});

function tasksaveCallback(result, statusText, xhr, $form) {
    if (result.messageType === "S") {
        $("#taskfwd_modal_dialog").modal("hide");
        //$("#task_tbl").DataTable().ajax.url(getTaskdDataTableUrl()).load();
        $("#task_tbl").DataTable().ajax.reload();
    } else {
        //$('#msg_fwdtask').empty().html(result.message).show();
        $.notify(result.message, "custom");
    }
}

function viewTask(id) {
    openForm("view", "Task", id);
}

function modifyTask(id) {
    openForm("edit", "Task", id);
}

function closeTask(id) {
    taskForm("edit", "Task", id, "")
}

function forwardTask(id) {
    taskforward(id);
}

function openTaskLog(id, status) {
    var data = {"task.ctId": id, "task.ctStatus": status, "operation": "add"};
    block('block_body');
    $.ajax({
        type: "POST",
        url: APP_CONFIG.context + "/openTaskEntryLogPage.do",
        data: data,
        success: function(result) {
            $('#task_add').empty().html(result);
        },
        error: function(xhr, status, error) {
            alert("Error: " + error);
        },
        complete: function() {
            unblock('block_body');
        }
    });
}

function UploadMyTaskFile() {
    block('block_body');
    $.ajax({
        type: "POST",
        data: {},
        url: APP_CONFIG.context + "/openUploadTaskEntryForm.do",
        success: function(result) {
            $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-mm modal-sm").addClass("modal-mm");
            $('#plugin_modal_dialog .modal-content').empty().html(result);
            $('#plugin_modal_dialog').modal('show');
        },
        error: function(xhr, status, error) {
            alert("Error: " + error);
        },
        complete: function() {
            unblock('block_body');
        }
    });
}

function taskForm(mode, type, id, callId) {
    $("#ctId_close_id").val(id);
    $("#frm_task_close #ctCloseRemarks").val("");
    $("#frm_task_close #ctCloseCode").val("");
    $("#frm_task_close #ctCloseYn").val("");
    $("#ctId_log_id").val(callId);
    $("#task_modal_dialog").modal("show");
    $("#ctStat").val("C");
    $("#frm_task_close #ctCloseCode").removeAttr("disabled");
}

function taskforward(id) {
    var data = {"task.ctId": id};
    block('block_body')
    $.ajax({
        type: "POST",
        url: APP_CONFIG.context + "/openTaskEntryFwdForm.do",
        data: data,
        success: function(result) {

            if (result.task !== 'undefined') {
                $("#frm_task_fwd #ctAssignedTo").val(result.task.ctAssignedTo);
                $("#frm_task_fwd #ctStatus").val(result.task.ctStatus);
                $("#frm_task_fwd #ctPriority").val(result.task.ctPriority);
                $("#frm_task_fwd #ctDueDate").val(result.task.ctDueDate);
                $("#frm_task_fwd #ctRemindBefore").val(result.task.ctRemindBefore);
                $("#frm_task_fwd #ctReason").val(result.task.ctReason);
                $("#frm_task_fwd #ctCloseRemarks").val(result.task.ctCloseRemarks);
                $("#ctId_fwd_id").val(id);
                $("#taskfwd_modal_dialog").modal("show");
                $('#frm_task_fwd #ctStatus').trigger('change');
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

function getTaskdDataTableUrl() {
    return APP_CONFIG.context + "/loadTaskEntryData.do?" + $('#frm_search').serialize();
}

function openTaskClForm(mobile, id) {
    var data = {"customer.mobileNo": mobile, "flex2": id};
    $.ajax({
        type: "POST",
        url: APP_CONFIG.context + "/loadTaskEntryClForm.do",
        data: data,
        async: true,
        success: function(result) {
            if (result.messageType === "S") {
                taskForm("edit", "Task", id, result.message);
            }
        },
        error: function(xhr, status, error) {
            alert("Error: " + error);
        },
        complete: function() {
        }
    });
}