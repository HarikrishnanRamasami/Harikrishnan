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


/*var AGGREGATE_FUNCTIONS = ["MIN", "MAX", "SUM", "COUNT", "AVG"];
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
}*/

$(document).ready(function () {
});


function RemoveFilterRow(rowId) {
    $("#frm_del_filter_param #mcfpSno").val(rowId);
    $("#del_filter_param").modal("show");
}

function deleteFilterParam(rowId) {
    var p = document.getElementById("FilterRow_" + rowId);
    var x = p.previousElementSibling;
    var y = p.nextElementSibling;
    $("#FilterRow_" + rowId).remove();
    if (x === null && y !== null) {
        var id = y.attributes["id"].value.split("_");
        var nextRow = id[1];
        $("#mcfp_" + nextRow).html("");
    }
    return false;
}

function DataTypeChange(obj, indx) {
    var value = $('#' + obj).val();
    var id = obj.substr(obj.indexOf('_') + 1);
    //var code=value.substr(value.indexOf('_')+1);
    var dataType = $("#" + obj).find("option:selected").attr("title");
    $("#mcfpDataType_" + indx).val(dataType);
    $("#mcfpOperator_" + indx + " option[value='bp']").remove();
    /* var selVal = $("#mcfpOperator_" + indx).val();
     if( ("D" == dataType || "T" == dataType) && !$("#mcfpOperator_" + indx + " option[value='bp']").length )
     $("#mcfpOperator_" + indx).prepend('<option value="bp" selected>By Period</option>').val("bp"); */
    /* else
     $("#mcfpOperator_" + indx).val(selVal); */
    OperatorTypeChange($("#mcfpOperator_" + indx).val(), indx);
    inputTypeChange(value, id);
    return false;
}

function OperatorTypeChange(val, indx) {
    var targetEle = "";
    if ($("#mcfpValuesText_" + indx).is(':visible'))
    {
        targetEle = "#mcfpValuesText_" + indx;
        //$('#mcfpValuesSelect_'+indx).html('');
    }
    else
    {
        targetEle = "#mcfpValuesSelect_" + indx;
        //$('#mcfpValuesText_'+indx).html('');
    }
    if ("BETWEEN" == val || "NOT BETWEEN" == val) {
        var fields = '<div class="col-wdth-50"><input type="text" name="campaignFilterParm[' + indx + '].mcfpValueFm" id="mcfpValueFm_' + indx + '" class="form-control" /></div>'
                + '<div class="col-wdth-50"><input type="text" name="campaignFilterParm[' + indx + '].mcfpValueTo" id="mcfpValueTo_' + indx + '" class="form-control" /></div>';
        $(targetEle).html(fields);
    } else if ("bp" == val) {
        var fields = '<select name="campaignFilterParm[' + indx + '].mcfpValueFm" id="mcfpValueFm_' + indx + '" class="form-control input-sm">';
        $("#filterDateListId option").each(function (i) {
            fields += '<option value="' + $(this).val() + '" ' + (i == 0 ? 'selected' : '') + '>' + $(this).text() + '</option>';
        });
        fields += '</select>';
        $(targetEle).html(fields);
    } else {
        var fields = '<input type="text" name="campaignFilterParm[' + indx + '].mcfpValueFm" id="mcfpValueFm_' + indx + '" class="form-control" />';
        $(targetEle).html(fields);
    }
    fieldTypeChange(indx);
    return false;
}
function fieldTypeChange(indx) {
    var dataType = $("#mcfpDataType_" + indx).val();
    var oper = $("#mcfpOperator_" + indx).val();
    if (oper != "bp") {
        var valTo = $("#mcfpValueTo_" + indx).length > 0 ? true : false;
        $("#mcfpValueFm_" + indx).val("");
        $("#mcfpValueFm_" + indx).removeClass("decimal");
        if ($("#mcfpValueFm_" + indx).hasClass("hasDatepicker"))
            $("#mcfpValueFm_" + indx).datepicker("destroy");
        if (valTo) {
            $("#mcfpValueTo_" + indx).val("");
            if ($("#mcfpValueTo_" + indx).hasClass("hasDatepicker"))
                $("#mcfpValueTo_" + indx).datepicker("destroy");
            $("#mcfpValueTo_" + indx).removeClass("decimal");
        }
        if (dataType == "D" || dataType == "T") {
            $("#mcfpValueFm_" + indx).datepicker({
                format: 'dd/mm/yyyy' + (dataType == "T" ? " 00:00" : ""),
                changeMonth: true,
                changeYear: true,
                onSelect: function (selectedDate) {
                    if (valTo)
                        $("#mcfpValueTo_" + indx).datepicker("option", "minDate", new Date(selectedDate.substring(3, 6) + selectedDate.substring(0, 3) + selectedDate.substring(6, 10)));
                }}
            );
            if (valTo) {
                $("#mcfpValueTo_" + indx).datepicker({
                    format: 'dd/mm/yyyy' + (dataType == "T" ? " 23:59" : ""),
                    changeMonth: true,
                    changeYear: true,
                    onSelect: function (selectedDate) {
                        $("#mcfpValueFm_" + indx).datepicker("option", "maxDate", new Date(selectedDate.substring(3, 6) + selectedDate.substring(0, 3) + selectedDate.substring(6, 10)));
                    }
                });
            }
        } else if (dataType == "N") {
            $("#mcfpValueFm_" + indx).addClass("decimal");
            if (valTo) {
                $("#mcfpValueTo_" + indx).addClass("decimal");
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
                var divId = 'mcfpValuesText_' + obj;
                //console.log("obj: "+obj);
                console.log("result.filterColumns: " + JSON.stringify(result.filterColumns));
                if (result.filterColumns != '') {
                    $(result.filterColumns).each(function (i, v) {
                        //console.log("v: "+v);
                        //console.log("v.key"+v.key);
                        if (v.key === '1') {
                            $('#mcfpValuesSelect_' + obj).css("display", "none");
                            $('#mcfpValueSelect_' + obj).css("display", "none");
                            $('#mcfpValuesText_' + obj).css("display", "block");
                            $('#mcfpValuesHidden_' + obj).css("display", "none");
                            $('#mcfpValueHidden_' + obj).css("display", "block");
                            $('#mcfpValueText_' + obj).attr("name", "mcfpValueFm_desc");
                            var id = 'mcfpValueText_' + obj;
                            var hidden = 'mcfpValueHidden_' + obj;
                            var aa = {};
                            aa[hidden] = "desc";
                            //setAccountAutoCompleteForFilter(id  , 'ACCOUNT_TYPE', {"searchCriteria": "BOTH", 'url':value}, {'searchAccountType': 'searchAccountType'}, {"id": 'desc'}, '');
                            var autoCompleteType = "";
                            if (filterValue == "Main Account")
                                autoCompleteType = "MAIN_ACCOUNT";
                            else if (filterValue == "Sub Account")
                                autoCompleteType = "SUB_ACCOUNT";
                            //setAccountAutoCompleteForFilter(id, autoCompleteType, {"searchCriteria": "BOTH", 'qry': value}, {}, aa, '');
                            setAccountAutoComplete('mcfpValueFm_' + obj, autoCompleteType, {"searchCriteria": "BOTH", "searchAccountType": ''}, {}, {}, '');
                            if ($('#mcfpOperator_' + obj).val() == 'BETWEEN' || $('#mcfpOperator_' + obj).val() == 'NOT BETWEEN')
                            {
                                setAccountAutoComplete('mcfpValueTo_' + obj, autoCompleteType, {"searchCriteria": "BOTH", "searchAccountType": ''}, {}, {}, '');
                            }
                            //$('#mcfpValuesSelect_'+obj).html('');
                        } else if (v.key === '0') {
                            var id1 = 'mcfpValuesSelect_' + obj;
                            $('#mcfpValuesSelect_' + obj).css("display", "block");
                            $('#mcfpValuesText_' + obj).css("display", "none");
                            //$('#mcfpValueText_'+obj).css("display","none");
                            $('#mcfpValuesHidden_' + obj).css("display", "none");
                            $('#mcfpValueHidden_' + obj).css("display", "none");
                            $('#mcfpValueText_' + obj).attr("disabled", "disabled");
                            $('#mcfpValueHidden_' + obj).attr("disabled", "disabled");
                            var value = v.value;
                            loadDropDown(id1, value, divId, obj);//aaa
                            //$('#mcfpValuesText_'+obj).html('');
                        }
                    });
                } else {
                    $('#mcfpValuesSelect_' + obj).css("display", "none");
                    $('#mcfpValueSelect_' + obj).css("display", "none");
                    $('#mcfpValuesHidden_' + obj).css("display", "none");
                    $('#mcfpValueHidden_' + obj).css("display", "none");
                    $('#mcfpValueText_' + obj).css("display", "block");
                    $('#mcfpValuesText_' + obj).css("display", "block");
                    $('#mcfpValueText_' + obj).val('');
                    $('#mcfpValueText_' + obj).removeAttr("disabled");
                    $('#mcfpValueSelect_' + obj).val('');
                    $('#mcfpValueHidden_' + obj).attr("disabled", "disabled");
                    //$('#mcfpValueTxt_'+obj).attr("name", "campaignFilterParm['" + obj + "].mcfpValueFm");
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
    var fmOpts = '<select name="campaignFilterParm[' + obj + '].mcfpValueFm" id="mcfpSelect_' + obj + '" class="form-control input-sm"><option value="">-- Select --</option>';
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
                if ($('#mcfpOperator_' + obj).val() == 'BETWEEN' || $('#mcfpOperator_' + obj).val() == 'NOT BETWEEN')
                {
                    var toOpts = '<select name="campaignFilterParm[' + obj + '].mcfpValueTo" class="form-control input-sm"><option value="">-- Select --</option>' + opts;
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
    $('[id^="mcfpDataCol_"]').each(function (i, v) {
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
    var columnSelect = '<div class="col-wdth-30 mt-10"><select name="campaignFilterParm[' + filterRowId + '].mcfpDataCol" id="mcfpDataCol_' + filterRowId + '" class="form-control input-sm" onchange="DataTypeChange(this.id, \'' + filterRowId + '\');">';
    $("#filterColumnsListId option").each(function (i) {
        columnSelect += '<option value="' + $(this).val() + '" title="' + $(this).attr('title') + '">' + $(this).text() + '</option>';
    });
    columnSelect += '</select></div>';
    var operSelect = '<div class="col-wdth-18 mt-10"><select name="campaignFilterParm[' + filterRowId + '].mcfpOperator" id="mcfpOperator_' + filterRowId + '" class="form-control input-sm" onchange="DataTypeChange(\'mcfpDataCol_' + filterRowId + '\', \'' + filterRowId + '\');">';
    $("#filterCondtListId option").each(function (i) {
        operSelect += '<option value="' + $(this).val() + '">' + $(this).text() + '</option>';
    });
    operSelect += '</select></div>';
    var fieldText = '<div id="mcfpValuesText_' + filterRowId + '" class="col-wdth-30 mt-10 h-15">' +
            '<input type="text" name="campaignFilterParm[' + filterRowId + '].mcfpValueFm" id="mcfpValueText_' + filterRowId + '" class="form-control" /></div>';
    fieldText += '<div id="mcfpValuesHidden_' + filterRowId + '" class="col-wdth-30 mt-10" style="display:none;">' +
            '<input type="hidden" name="campaignFilterParm[' + filterRowId + '].mcfpValueFm" id="mcfpValueHidden_' + filterRowId + '" class="form-control"></div>';
    fieldText += '<div id="mcfpValuesSelect_' + filterRowId + '" class="col-wdth-30 mt-10" style="display:none;">' +
            '<select name="campaignFilterParm[' + filterRowId + '].mcfpValueFm" id="mcfpValueSelect_' + filterRowId + '" class="form-control input-sm" ></select></div>';

    var condtSelect = '<div class="col-wdth-12 mt-10" id="mcfp_' + filterRowId + '" ></div>';
    var hiddenFields = '<input type="hidden" name="campaignFilterParm[' + filterRowId + '].mcfpDataType" id="mcfpDataType_' + filterRowId + '" value="">';
    var childNodes = document.getElementById("FilterTableId").children.length;
    if (childNodes !== 0) {
        condtSelect = '<div class="col-wdth-12 mt-10" id="mcfp_' + filterRowId + '" >'
                + '<select name="campaignFilterParm[' + filterRowId + '].mcfpCondition" id="mcfpCondition_' + filterRowId + '" class="form-control input-sm"><option value="AND">And</option><option value="OR">Or</option></select></div>';
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