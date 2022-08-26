/* 
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

$(document).ready(function () {
    
});

function setupDateRange() {
    $('#date_range').daterangepicker({
        startDate: moment(),
        endDate: moment(),
        ranges: DATE_RANGES,
        locale: {
            format: 'DD/MM/YYYY'
        }
    }, function (start, end, label) {
        /*var days = moment().diff(start, 'days');
         alert(days);*/
        if (label === 'Custom Range') {
            $('#date_range span').html(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
        } else {
            $('#date_range span').html(label);
        }
        if (label === 'Custom Range') {
            $('#sel_pie_dateRange').val('CUSTOM');
            $('#hid_dateRangeValue').val(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
        }
        var keys = Object.keys(DATE_RANGES_LIST);
        for(i = 0; i < keys.length; i++) {
            if(label === DATE_RANGES_LIST[keys[i]]) {
                $('#sel_pie_dateRange').val(keys[i]);
                break;
            }
        }
    });
}