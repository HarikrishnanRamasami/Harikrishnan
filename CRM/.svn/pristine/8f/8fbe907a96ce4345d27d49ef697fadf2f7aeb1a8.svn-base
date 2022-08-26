/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var AGGREGATE_FUNCTIONS = [""];
var menu = [{
        name: 'Max',
        fun: function () {
            $("#selectedColumnsId option:selected").each(function () {
                var t = $(this).text();
                t = $.trim(t.substring(t.indexOf(')') + 1));
                $(this).text("MAX(" + t + ") " + t);
                $(this).val("MAX(" + t + ") " + t);
            });
        }
    }, {
        name: 'Min',
        fun: function () {
            $("#selectedColumnsId option:selected").each(function () {
                var t = $(this).text();
                t = $.trim(t.substring(t.indexOf(')') + 1));
                $(this).text("MIN(" + t + ") " + t);
                $(this).val("MIN(" + t + ") " + t);
            });
        }
    },
    {
        name: 'Sum',
        fun: function () {
            $("#selectedColumnsId option:selected").each(function () {
                var t = $(this).text();
                t = $.trim(t.substring(t.indexOf(')') + 1));
                $(this).text("SUM(" + t + ") " + t);
                $(this).val("SUM(" + t + ") " + t);
            });
        }
    },
    {
        name: 'Count',
        fun: function () {
            $("#selectedColumnsId option:selected").each(function () {
                var t = $(this).text();
                t = $.trim(t.substring(t.indexOf(')') + 1));
                $(this).text("COUNT(" + t + ") " + t);
                $(this).val("COUNT(" + t + ") " + t);
            });
        }
    },
    {
        name: 'Avg',
        fun: function () {
            $("#selectedColumnsId option:selected").each(function () {
                var t = $(this).text();
                t = $.trim(t.substring(t.indexOf(')') + 1));
                $(this).text("AVG(" + t + ") " + t);
                $(this).val("AVG(" + t + ") " + t);
            });
        }
    },
    {
        name: 'Remove',
        icon: "fa fa-trash",
        fun: function () {
            $("#selectedColumnsId option:selected").each(function () {
                var t = $(this).text();
                t = $.trim(t.substring(t.indexOf(')') + 1));
                $(this).text(t);
                $(this).val(t);
            });
        }
    }];

function attachAggregateFunction() {
    $("#selectedColumnsId option").each(function () {
        $(this).contextMenu(menu);
    });
}

function detachAggregateFunction(type) { // A - All, S - Selected
    $("#selectedColumnsId option" + (type === "A" ? "" : ":selected")).each(function () {
        var t = $(this).text();
        t = $.trim(t.substring(t.indexOf(')') + 1));
        $(this).text(t);
        $(this).val(t);
    });
}

function initAggregateFunction() {
    $("#selectedColumnsId option").each(function () {
        $(this).contextMenu(menu);
    });
}

$(document).ready(function () {
});



function RemoveFilterRow(rowId) {
    var p = document.getElementById("FilterRow_" + rowId);
    var x = p.previousElementSibling;
    var y = p.nextElementSibling;
    $("#FilterRow_" + rowId).remove();
    if (x === null && y !== null) {
        var id = y.attributes["id"].value.split("_");
        var nextRow = id[1];
        $("#mdp_" + nextRow).html("");
    }
    return false;
}

function DataTypeChange(obj, indx) {
    var value = $('#' + obj).val();
    var id = obj.substr(obj.indexOf('_') + 1);
    //var code=value.substr(value.indexOf('_')+1);
    var dataType = $("#" + obj).find("option:selected").attr("title");
    $("#mdpDataType_" + indx).val(dataType);
    $("#mdpOperator_" + indx + " option[value='bp']").remove();
    /* var selVal = $("#mdpOperator_" + indx).val();
     if( ("D" == dataType || "T" == dataType) && !$("#mdpOperator_" + indx + " option[value='bp']").length )
     $("#mdpOperator_" + indx).prepend('<option value="bp" selected>By Period</option>').val("bp"); */
    /* else
     $("#mdpOperator_" + indx).val(selVal); */
    OperatorTypeChange($("#mdpOperator_" + indx).val(), indx);
    inputTypeChange(value, id);
    return false;
}

function OperatorTypeChange(val, indx) {
    var targetEle = "";
    if ($("#mdpValuesText_" + indx).is(':visible'))
    {
        targetEle = "#mdpValuesText_" + indx;
        //$('#mdpValuesSelect_'+indx).html('');
    }
    else
    {
        targetEle = "#mdpValuesSelect_" + indx;
        //$('#mdpValuesText_'+indx).html('');
    }
    if ("BETWEEN" == val || "NOT BETWEEN" == val) {
        var fields = '<div class="col-wdth-50"><input type="text" name="dataParam[' + indx + '].mdpValueFm" id="mdpValueFm_' + indx + '" class="form-control" /></div>'
                + '<div class="col-wdth-50"><input type="text" name="dataParam[' + indx + '].mdpValueTo" id="mdpValueTo_' + indx + '" class="form-control" /></div>';
        $(targetEle).html(fields);
    } else if ("bp" == val) {
        var fields = '<select name="dataParam[' + indx + '].mdpValueFm" id="mdpValueFm_' + indx + '" class="form-control input-sm">';
        $("#filterDateListId option").each(function (i) {
            fields += '<option value="' + $(this).val() + '" ' + (i == 0 ? 'selected' : '') + '>' + $(this).text() + '</option>';
        });
        fields += '</select>';
        $(targetEle).html(fields);
    } else {
        var fields = '<input type="text" name="dataParam[' + indx + '].mdpValueFm" id="mdpValueFm_' + indx + '" class="form-control" />';
        $(targetEle).html(fields);
    }
    fieldTypeChange(indx);
    return false;
}
function fieldTypeChange(indx) {
    var dataType = $("#mdpDataType_" + indx).val();
    var oper = $("#mdpOperator_" + indx).val();
    if (oper != "bp") {
        var valTo = $("#mdpValueTo_" + indx).length > 0 ? true : false;
        $("#mdpValueFm_" + indx).val("");
        $("#mdpValueFm_" + indx).removeClass("decimal");
        if ($("#mdpValueFm_" + indx).hasClass("hasDatepicker"))
            $("#mdpValueFm_" + indx).datepicker("destroy");
        if (valTo) {
            $("#mdpValueTo_" + indx).val("");
            if ($("#mdpValueTo_" + indx).hasClass("hasDatepicker"))
                $("#mdpValueTo_" + indx).datepicker("destroy");
            $("#mdpValueTo_" + indx).removeClass("decimal");
        }
        if (dataType == "D" || dataType == "T") {
            $("#mdpValueFm_" + indx).datepicker({
                format: 'dd/mm/yyyy' + (dataType == "T" ? " 00:00" : ""),
                changeMonth: true,
                changeYear: true,
                onSelect: function (selectedDate) {
                    if (valTo)
                        $("#mdpValueTo_" + indx).datepicker("option", "minDate", new Date(selectedDate.substring(3, 6) + selectedDate.substring(0, 3) + selectedDate.substring(6, 10)));
                }}
            );
            if (valTo) {
                $("#mdpValueTo_" + indx).datepicker({
                    format: 'dd/mm/yyyy' + (dataType == "T" ? " 23:59" : ""),
                    changeMonth: true,
                    changeYear: true,
                    onSelect: function (selectedDate) {
                        $("#mdpValueFm_" + indx).datepicker("option", "maxDate", new Date(selectedDate.substring(3, 6) + selectedDate.substring(0, 3) + selectedDate.substring(6, 10)));
                    }
                });
            }
        } else if (dataType == "N") {
            $("#mdpValueFm_" + indx).addClass("decimal");
            if (valTo) {
                $("#mdpValueTo_" + indx).addClass("decimal");
            }
        }
    }
    return false;
}

function inputTypeChange(filterValue, obj) {
    var url, data;
    url = APP_CONFIG.context + "/InputTypeChange.do";
    data = {reportViewDesc: filterValue};
    $.ajax({
        data: data,
        async: false,
        url: url,
        dataType: "json",
        success: function (result) {
            if (!applyAjaxResponseError(result, 'repErrorDiv')) {
                var divId = 'mdpValuesText_' + obj;
                //console.log("obj: "+obj);
                console.log("result.filterColumns: " + JSON.stringify(result.filterColumns));
                if (result.filterColumns != '') {
                    $(result.filterColumns).each(function (i, v) {
                        //console.log("v: "+v);
                        //console.log("v.key"+v.key);
                        if (v.key === '1') {
                            $('#mdpValuesSelect_' + obj).css("display", "none");
                            $('#mdpValueSelect_' + obj).css("display", "none");
                            $('#mdpValuesText_' + obj).css("display", "block");
                            $('#mdpValuesHidden_' + obj).css("display", "none");
                            $('#mdpValueHidden_' + obj).css("display", "block");
                            $('#mdpValueText_' + obj).attr("name", "mdpValueFm_desc");
                            var id = 'mdpValueText_' + obj;
                            var hidden = 'mdpValueHidden_' + obj;
                            var aa = {};
                            aa[hidden] = "desc";
                            //setAccountAutoCompleteForFilter(id  , 'ACCOUNT_TYPE', {"searchCriteria": "BOTH", 'url':value}, {'searchAccountType': 'searchAccountType'}, {"id": 'desc'}, '');
                            var autoCompleteType = "";
                            if (filterValue == "Main Account")
                                autoCompleteType = "MAIN_ACCOUNT";
                            else if (filterValue == "Sub Account")
                                autoCompleteType = "SUB_ACCOUNT";
                            //setAccountAutoCompleteForFilter(id, autoCompleteType, {"searchCriteria": "BOTH", 'qry': value}, {}, aa, '');
                            setAccountAutoComplete('mdpValueFm_' + obj, autoCompleteType, {"searchCriteria": "BOTH", "searchAccountType": ''}, {}, {}, '');
                            if ($('#mdpOperator_' + obj).val() == 'BETWEEN' || $('#mdpOperator_' + obj).val() == 'NOT BETWEEN')
                            {
                                setAccountAutoComplete('mdpValueTo_' + obj, autoCompleteType, {"searchCriteria": "BOTH", "searchAccountType": ''}, {}, {}, '');
                            }
                            //$('#mdpValuesSelect_'+obj).html('');
                        } else if (v.key === '0') {
                            var id1 = 'mdpValuesSelect_' + obj;
                            $('#mdpValuesSelect_' + obj).css("display", "block");
                            $('#mdpValuesText_' + obj).css("display", "none");
                            //$('#mdpValueText_'+obj).css("display","none");
                            $('#mdpValuesHidden_' + obj).css("display", "none");
                            $('#mdpValueHidden_' + obj).css("display", "none");
                            $('#mdpValueText_' + obj).attr("disabled", "disabled");
                            $('#mdpValueHidden_' + obj).attr("disabled", "disabled");
                            var value = v.value;
                            loadDropDown(id1, value, divId, obj);//aaa
                            //$('#mdpValuesText_'+obj).html('');
                        }
                    });
                } else {
                    $('#mdpValuesSelect_' + obj).css("display", "none");
                    $('#mdpValueSelect_' + obj).css("display", "none");
                    $('#mdpValuesHidden_' + obj).css("display", "none");
                    $('#mdpValueHidden_' + obj).css("display", "none");
                    $('#mdpValueText_' + obj).css("display", "block");
                    $('#mdpValuesText_' + obj).css("display", "block");
                    $('#mdpValueText_' + obj).val('');
                    $('#mdpValueText_' + obj).removeAttr("disabled");
                    $('#mdpValueSelect_' + obj).val('');
                    $('#mdpValueHidden_' + obj).attr("disabled", "disabled");
                    //$('#mdpValueTxt_'+obj).attr("name", "dataParam['" + obj + "].mdpValueFm");
                }
            }
        },
        error: function (xhr, status, error) {
            displayAlert('E', error);
        }
    });
    return false;
}
function loadDropDown(id1, value, divId, obj) {
    //var  url = value;alert(value);
    var url = APP_CONFIG.context + "/DropDownChange.do";
    var data = {reportSec: value};
    var fmOpts = '<select name="dataParam[' + obj + '].mdpValueFm" id="mdpSelect_' + obj + '" class="form-control input-sm"><option value="">-- Select --</option>';
    $.ajax({
        data: data,
        url: url,
        async: false,
        dataType: "json",
        success: function (result) {
            if (!applyAjaxResponseError(result, 'repErrorDiv'))
            {
                var opts = "";
                $(result.list).each(function (i, v) {
                    opts += "<option value=\"" + v.value + "\">" + v.key + "</option>";
                });
                opts += "</select>";
                fmOpts = fmOpts + opts;
                if ($('#mdpOperator_' + obj).val() == 'BETWEEN' || $('#mdpOperator_' + obj).val() == 'NOT BETWEEN')
                {
                    var toOpts = '<select name="dataParam[' + obj + '].mdpValueTo" class="form-control input-sm"><option value="">-- Select --</option>' + opts;
                    var html = "<div class='col-wdth-50'>" + fmOpts + "</div>" + "<div class='col-wdth-50'>" + toOpts + "</div>";
                    $('#' + id1).html(html);
                }
                else
                    $('#' + id1).html(fmOpts);
            }
        },
        error: function (xhr, status, error) {
            displayAlert('E', error);
        }
    });
    return false;
}

function AddFilterRow() {
    var isProceed = true;
    $('[id^="mdpColumn_"]').each(function (i, v) {
        if ($.trim(this.value).length == 0)
        {
            isProceed = false;
            $(this).focus();
            return false;
        }
    });
    if (!isProceed)
    {
        showMsg('e_filter', 'Please select filter');
        return false;
    }

    var filterHtml = '<div id="FilterRow_' + filterRowId + '" class="col-wdth-100 mt-10">';
    var columnSelect = '<div class="col-wdth-30 mt-10"><select name="dataParam[' + filterRowId + '].mdpColumn" id="mdpColumn_' + filterRowId + '" class="form-control input-sm" onchange="DataTypeChange(this.id, \'' + filterRowId + '\');">';
    $("#filterColumnsListId option").each(function (i) {
        columnSelect += '<option value="' + $(this).val() + '" title="' + $(this).attr('title') + '">' + $(this).text() + '</option>';
    });
    columnSelect += '</select></div>';
    var operSelect = '<div class="col-wdth-18 mt-10"><select name="dataParam[' + filterRowId + '].mdpOperator" id="mdpOperator_' + filterRowId + '" class="form-control input-sm" onchange="DataTypeChange(\'mdpColumn_' + filterRowId + '\', \'' + filterRowId + '\');">';
    $("#filterCondtListId option").each(function (i) {
        operSelect += '<option value="' + $(this).val() + '">' + $(this).text() + '</option>';
    });
    operSelect += '</select></div>';
    var fieldText = '<div id="mdpValuesText_' + filterRowId + '" class="col-wdth-30 mt-10 h-15">' +
            '<input type="text" name="dataParam[' + filterRowId + '].mdpValueFm" id="mdpValueText_' + filterRowId + '" class="form-control" /></div>';
    fieldText += '<div id="mdpValuesHidden_' + filterRowId + '" class="col-wdth-30 mt-10" style="display:none;">' +
            '<input type="hidden" name="dataParam[' + filterRowId + '].mdpValueFm" id="mdpValueHidden_' + filterRowId + '" class="form-control"></div>';
    fieldText += '<div id="mdpValuesSelect_' + filterRowId + '" class="col-wdth-30 mt-10" style="display:none;">' +
            '<select name="dataParam[' + filterRowId + '].mdpValueFm" id="mdpValueSelect_' + filterRowId + '" class="form-control input-sm" ></select></div>';

    var condtSelect = '<div class="col-wdth-12 mt-10" id="mdp_' + filterRowId + '" ></div>';
    var hiddenFields = '<input type="hidden" name="dataParam[' + filterRowId + '].mdpDataType" id="mdpDataType_' + filterRowId + '" value="">';
    var childNodes = document.getElementById("FilterTableId").children.length;
    if (childNodes !== 0) {
        condtSelect = '<div class="col-wdth-12 mt-10" id="mdp_' + filterRowId + '" >'
                + '<select name="dataParam[' + filterRowId + '].mdpCondition" id="mdpCondition_' + filterRowId + '" class="form-control input-sm"><option value="AND">And</option><option value="OR">Or</option></select></div>';
    }
    filterHtml += condtSelect + columnSelect + operSelect + fieldText + hiddenFields;
    filterHtml += '<div class="col-wdth-10 mt-10"><button type="button" class="btn btn-greensea btn-sm" onclick="RemoveFilterRow(\'' + filterRowId + '\');"><i class="fa fa-remove"></i></button></div></div>';
    $("#FilterTableId").append(filterHtml);
    filterRowId++;
    return false;
}

function runParamReports(indx) {
    block('block_body');
    $("#reportView").val($("#ReportView_" + indx).val());
    $("#reportTitle").val($("#ReportTitle_" + indx).val());
    $("#reportTable").val($("#ReportTable_" + indx).val());
    $("#reportId").val($("#ReportId_" + indx).val());
    $("#reportName").val($("#ReportName_" + indx).val());
    $.ajax({
        type: "POST",
        url: APP_CONFIG.context + '/RunSavedReport.do',
        data: $("#ReportsParamForm").serialize(),
        async: true,
        success: function (result) {
            $('#reportparam_modal_dialog').modal('hide');
            setTimeout(function () {
                $('#block_body').html(result);
            }, 1000);
        },
        error: function (xhr, status, error) {
            alert("Error: " + error);
        },
        complete: function () {
            unblock('block_body');
        }
    });
}

function saveReportparams() {
    block('block_body');
    $.ajax({
        type: "POST",
        url: APP_CONFIG.context + '/saveReportParams.do',
        data: $("#ReportsParamForm").serialize(),
        success: function (result) {
            if (result.reportStatus != '500') {
                $('#reportparam_modal_dialog').modal('hide');
                setTimeout(function () {
                    $('#block_body').html(result);
                }, 1000);
            } else {
                $('#reportparam_modal_dialog').modal('show');
            }
        },
        complete: function () {
            unblock('block_body');
        },
        error: function (xhr, status, error) {
            alert("Error: " + error);
        }
    });
    
}
function showMsg(div, msg) {
    $.notify(msg, "custom");
}