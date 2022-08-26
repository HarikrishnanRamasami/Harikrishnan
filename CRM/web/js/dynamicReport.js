var AGGREGATE_FUNCTIONS = ["MIN", "MAX", "SUM", "COUNT", "AVG"];
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
        $("#urp_" + nextRow).html("");
    }
    return false;
}

function DataTypeChange(obj, indx) {
    var value = $('#' + obj).val();
    var id = obj.substr(obj.indexOf('_') + 1);
    //var code=value.substr(value.indexOf('_')+1);
    var dataType = $("#" + obj).find("option:selected").attr("title");
    $("#urpDataType_" + indx).val(dataType);
    $("#urpOperator_" + indx + " option[value='bp']").remove();
    /* var selVal = $("#urpOperator_" + indx).val();
     if( ("D" == dataType || "T" == dataType) && !$("#urpOperator_" + indx + " option[value='bp']").length )
     $("#urpOperator_" + indx).prepend('<option value="bp" selected>By Period</option>').val("bp"); */
    /* else
     $("#urpOperator_" + indx).val(selVal); */
    OperatorTypeChange($("#urpOperator_" + indx).val(), indx);
    inputTypeChange(value, id);
    return false;
}

function OperatorTypeChange(val, indx) {
    var targetEle = "";
    if ($("#urpValuesText_" + indx).is(':visible'))
    {
        targetEle = "#urpValuesText_" + indx;
        //$('#urpValuesSelect_'+indx).html('');
    }
    else
    {
        targetEle = "#urpValuesSelect_" + indx;
        //$('#urpValuesText_'+indx).html('');
    }
    if ("btw" == val || "nbtw" == val) {
        var fields = '<div class="col-wdth-50"><input type="text" name="repFilter[' + indx + '].urpValueFm" id="urpValueFm_' + indx + '" class="form-control" /></div>'
                + '<div class="col-wdth-50"><input type="text" name="repFilter[' + indx + '].urpValueTo" id="urpValueTo_' + indx + '" class="form-control" /></div>';
        $(targetEle).html(fields);
    } else if ("bp" == val) {
        var fields = '<select name="repFilter[' + indx + '].urpValueFm" id="urpValueFm_' + indx + '" class="form-control input-sm">';
        $("#filterDateListId option").each(function (i) {
            fields += '<option value="' + $(this).val() + '" ' + (i == 0 ? 'selected' : '') + '>' + $(this).text() + '</option>';
        });
        fields += '</select>';
        $(targetEle).html(fields);
    } else {
        var fields = '<input type="text" name="repFilter[' + indx + '].urpValueFm" id="urpValueFm_' + indx + '" class="form-control" />';
        $(targetEle).html(fields);
    }
    fieldTypeChange(indx);
    return false;
}
function fieldTypeChange(indx) {
    var dataType = $("#urpDataType_" + indx).val();
    var oper = $("#urpOperator_" + indx).val();
    if (oper != "bp") {
        var valTo = $("#urpValueTo_" + indx).length > 0 ? true : false;
        $("#urpValueFm_" + indx).val("");
        $("#urpValueFm_" + indx).removeClass("decimal");
        if ($("#urpValueFm_" + indx).hasClass("hasDatepicker"))
            $("#urpValueFm_" + indx).datepicker("destroy");
        if (valTo) {
            $("#urpValueTo_" + indx).val("");
            if ($("#urpValueTo_" + indx).hasClass("hasDatepicker"))
                $("#urpValueTo_" + indx).datepicker("destroy");
            $("#urpValueTo_" + indx).removeClass("decimal");
        }
        if (dataType == "D" || dataType == "T") {
            $("#urpValueFm_" + indx).datepicker({
                format: 'dd/mm/yyyy' + (dataType == "T" ? " 00:00" : ""),
                changeMonth: true,
                changeYear: true,
                onSelect: function (selectedDate) {
                    if (valTo)
                        $("#urpValueTo_" + indx).datepicker("option", "minDate", new Date(selectedDate.substring(3, 6) + selectedDate.substring(0, 3) + selectedDate.substring(6, 10)));
                }}
            );
            if (valTo) {
                $("#urpValueTo_" + indx).datepicker({
                    format: 'dd/mm/yyyy' + (dataType == "T" ? " 23:59" : ""),
                    changeMonth: true,
                    changeYear: true,
                    onSelect: function (selectedDate) {
                        $("#urpValueFm_" + indx).datepicker("option", "maxDate", new Date(selectedDate.substring(3, 6) + selectedDate.substring(0, 3) + selectedDate.substring(6, 10)));
                    }
                });
            }
        } else if (dataType == "N") {
            $("#urpValueFm_" + indx).addClass("decimal");
            if (valTo) {
                $("#urpValueTo_" + indx).addClass("decimal");
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
                var divId = 'urpValuesText_' + obj;
                //console.log("obj: "+obj);
                console.log("result.filterColumns: " + JSON.stringify(result.filterColumns));
                if (result.filterColumns != '') {
                    $(result.filterColumns).each(function (i, v) {
                        //console.log("v: "+v);
                        //console.log("v.key"+v.key);
                        if (v.key === '1') {
                            $('#urpValuesSelect_' + obj).css("display", "none");
                            $('#urpValueSelect_' + obj).css("display", "none");
                            $('#urpValuesText_' + obj).css("display", "block");
                            $('#urpValuesHidden_' + obj).css("display", "none");
                            $('#urpValueHidden_' + obj).css("display", "block");
                            $('#urpValueText_' + obj).attr("name", "urpValueFm_desc");
                            var id = 'urpValueText_' + obj;
                            var hidden = 'urpValueHidden_' + obj;
                            var aa = {};
                            aa[hidden] = "desc";
                            //setAccountAutoCompleteForFilter(id  , 'ACCOUNT_TYPE', {"searchCriteria": "BOTH", 'url':value}, {'searchAccountType': 'searchAccountType'}, {"id": 'desc'}, '');
                            var autoCompleteType = "";
                            if (filterValue == "Main Account")
                                autoCompleteType = "MAIN_ACCOUNT";
                            else if (filterValue == "Sub Account")
                                autoCompleteType = "SUB_ACCOUNT";
                            //setAccountAutoCompleteForFilter(id, autoCompleteType, {"searchCriteria": "BOTH", 'qry': value}, {}, aa, '');
                            setAccountAutoComplete('urpValueFm_' + obj, autoCompleteType, {"searchCriteria": "BOTH", "searchAccountType": ''}, {}, {}, '');
                            if ($('#urpOperator_' + obj).val() == 'btw' || $('#urpOperator_' + obj).val() == 'nbtw')
                            {
                                setAccountAutoComplete('urpValueTo_' + obj, autoCompleteType, {"searchCriteria": "BOTH", "searchAccountType": ''}, {}, {}, '');
                            }
                            //$('#urpValuesSelect_'+obj).html('');
                        } else if (v.key === '0') {
                            var id1 = 'urpValuesSelect_' + obj;
                            $('#urpValuesSelect_' + obj).css("display", "block");
                            $('#urpValuesText_' + obj).css("display", "none");
                            //$('#urpValueText_'+obj).css("display","none");
                            $('#urpValuesHidden_' + obj).css("display", "none");
                            $('#urpValueHidden_' + obj).css("display", "none");
                            $('#urpValueText_' + obj).attr("disabled", "disabled");
                            $('#urpValueHidden_' + obj).attr("disabled", "disabled");
                            var value = v.value;
                            loadDropDown(id1, value, divId, obj);//aaa
                            //$('#urpValuesText_'+obj).html('');
                        }
                    });
                } else {
                    $('#urpValuesSelect_' + obj).css("display", "none");
                    $('#urpValueSelect_' + obj).css("display", "none");
                    $('#urpValuesHidden_' + obj).css("display", "none");
                    $('#urpValueHidden_' + obj).css("display", "none");
                    $('#urpValueText_' + obj).css("display", "block");
                    $('#urpValuesText_' + obj).css("display", "block");
                    $('#urpValueText_' + obj).val('');
                    $('#urpValueText_' + obj).removeAttr("disabled");
                    $('#urpValueSelect_' + obj).val('');
                    $('#urpValueHidden_' + obj).attr("disabled", "disabled");
                    //$('#urpValueTxt_'+obj).attr("name", "repFilter['" + obj + "].urpValueFm");
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
    var fmOpts = '<select name="repFilter[' + obj + '].urpValueFm" id="urpSelect_' + obj + '" class="form-control input-sm"><option value="">-- Select --</option>';
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
                if ($('#urpOperator_' + obj).val() == 'btw' || $('#urpOperator_' + obj).val() == 'nbtw')
                {
                    var toOpts = '<select name="repFilter[' + obj + '].urpValueTo" class="form-control input-sm"><option value="">-- Select --</option>' + opts;
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
    $('[id^="urpColumn_"]').each(function (i, v) {
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

    var filterHtml = '<div id="FilterRow_' + filterRowId + '" class="col-wdth-100">';
    var columnSelect = '<div class="col-wdth-30 mt-10"><select name="repFilter[' + filterRowId + '].urpColumn" id="urpColumn_' + filterRowId + '" class="form-control input-sm" onchange="DataTypeChange(this.id, \'' + filterRowId + '\');">';
    $("#filterColumnsListId option").each(function (i) {
        columnSelect += '<option value="' + $(this).val() + '" title="' + $(this).attr('title') + '">' + $(this).text() + '</option>';
    });
    columnSelect += '</select></div>';
    var operSelect = '<div class="col-wdth-18 mt-10"><select name="repFilter[' + filterRowId + '].urpOperator" id="urpOperator_' + filterRowId + '" class="form-control input-sm" onchange="DataTypeChange(\'urpColumn_' + filterRowId + '\', \'' + filterRowId + '\');">';
    $("#filterCondtListId option").each(function (i) {
        operSelect += '<option value="' + $(this).val() + '">' + $(this).text() + '</option>';
    });
    operSelect += '</select></div>';
    var fieldText = '<div id="urpValuesText_' + filterRowId + '" class="col-wdth-30 mt-10 h-15">' +
            '<input type="text" name="repFilter[' + filterRowId + '].urpValueFm" id="urpValueText_' + filterRowId + '" class="form-control" /></div>';
    fieldText += '<div id="urpValuesHidden_' + filterRowId + '" class="col-wdth-30 mt-10" style="display:none;">' +
            '<input type="hidden" name="repFilter[' + filterRowId + '].urpValueFm" id="urpValueHidden_' + filterRowId + '" class="form-control"></div>';
    fieldText += '<div id="urpValuesSelect_' + filterRowId + '" class="col-wdth-30 mt-10" style="display:none;">' +
            '<select name="repFilter[' + filterRowId + '].urpValueFm" id="urpValueSelect_' + filterRowId + '" class="form-control input-sm" ></select></div>';

    var condtSelect = '<div class="col-wdth-12 mt-10" id="urp_' + filterRowId + '" ></div>';
    var hiddenFields = '<input type="hidden" name="repFilter[' + filterRowId + '].urpDataType" id="urpDataType_' + filterRowId + '" value="">';
    var childNodes = document.getElementById("FilterTableId").children.length;
    if (childNodes !== 0) {
        condtSelect = '<div class="col-wdth-12 mt-10" id="urp_' + filterRowId + '" >'
                + '<select name="repFilter[' + filterRowId + '].urpCondition" id="urpCondition_' + filterRowId + '" class="form-control input-sm"><option value="AND">And</option><option value="OR">Or</option></select></div>';
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
            if(result.reportStatus != '500'){
            $('#reportparam_modal_dialog').modal('hide');
            setTimeout(function () {
                $('#block_body').html(result);
            }, 1000);
            }else{
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