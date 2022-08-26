var chart_row_1_col_1, chart_row_1_col_2, chart_row_2, chart_row_3;
var option_row_1_col_1, option_row_1_col_2, option_row_2, option_row_3;

$(document).ready(function () {
    chart_row_1_col_1 = echarts.init(document.getElementById('chart_row_1_col_1'));
    chart_row_1_col_2 = echarts.init(document.getElementById('chart_row_1_col_2'));
    chart_row_2 = echarts.init(document.getElementById('chart_row_2'));
    chart_row_3 = echarts.init(document.getElementById('chart_row_3'));
    
    window.onresize = function () {
        setTimeout(function () {
            chart_row_1_col_1.resize();
            chart_row_1_col_2.resize();
            chart_row_2.resize();
            chart_row_3.resize();
        }, 200);
    };
});

/* Outgoing messages */
option_row_1_col_1 = {
    title: {
        text: "No data",
        show: true,
        zlevel: 20,
        x: '35%',
        y: '52%',
        textStyle: {
            fontWeight: 'normal',
            color: '#666666',
            fontSize: '16'
        }
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: ({d}%)"
    },
    toolbox: {
        show: true,
        feature: {
            saveAsImage: {show: true}
        }
    },
    legend: {
        show: false,
        type: 'scroll',
        orient: 'vertical',
        right: 10,
        top: 20,
        bottom: 20,
        data: []
    },
    series: [
        {
            name: 'Total',
            type: 'pie',
            center: ["47%", "55%"],
            radius: ["45%", "65%"],
            data: [],
            color: ['#44b8e2', '#db9bc8', '#8ad0f9', '#fdc16c', '#b1d653', '#fa5840', '#00a640', '#cd5c5c', '#ba55d3', '#ffa500', '#40e0d0', '#ff69b4', '#6495ed', '#32cd32', '#da70d6', '#87cefa', '#87cefa', '#ff69b4', '#c37cca', '#8f95e7'],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};

/* PIE CHART OPEN, COMPLETED (BY USER), COMPLETED (BY CLOSE CODE) */
option_row_1_col_2 = {
    title: {
        text: "No data",
        show: true,
        zlevel: 20,
        x: '35%',
        y: '52%',
        textStyle: {
            fontWeight: 'normal',
            color: '#666666',
            fontSize: '16'
        }
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: ({d}%)"
    },
    toolbox: {
        show: true,
        feature: {
            saveAsImage: {show: true}
        }
    },
    legend: {
        show: false,
        type: 'scroll',
        orient: 'vertical',
        right: 10,
        top: 20,
        bottom: 20,
        data: []
    },
    series: [
        {
            name: 'Total',
            type: 'pie',
            center: ["47%", "55%"],
            radius: ["45%", "65%"],
            data: [],
            color: ['#44b8e2', '#db9bc8', '#8ad0f9', '#fdc16c', '#b1d653', '#fa5840', '#00a640', '#cd5c5c', '#ba55d3', '#ffa500', '#40e0d0', '#ff69b4', '#6495ed', '#32cd32', '#da70d6', '#87cefa', '#87cefa', '#ff69b4', '#c37cca', '#8f95e7'],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
/* Yearly report by inbound and outbound */
option_row_2 = {
    title: {
        text: "No data",
        show: true,
        zlevel: 20,
        x: '35%',
        y: '50%',
        formatter: "value",
        textStyle: {
            fontWeight: 'normal',
            color: '#666666',
            fontSize: '16'
        }
    },
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    toolbox: {
        show: true,
        feature: {
            saveAsImage: {show: true}
        }
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        data: []
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: [],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: 'Inbound Count',
            type: 'bar',
            barWidth: '30%',
            data: []
        },
        {
            name: 'Outbound Count',
            type: 'bar',
            barWidth: '30%',
            data: [],
            color: ['#4caf50', '#79ba56', '#8ad0f9', '#fdc16c', '#00a640', '#fa5840', '#00a640', '#cd5c5c', '#ba55d3', '#ffa500', '#40e0d0', '#ff69b4', '#6495ed', '#32cd32', '#da70d6', '#87cefa', '#87cefa', '#ff69b4', '#c37cca', '#8f95e7']
        }
    ]
};

/* Yearly report by close code */
var option_row_3_label = {
    show: true,
    position: 'insideBottom',
    distance: 15,
    align: 'left',
    verticalAlign: 'middle',
    rotate: 90,
    formatter: '{c}  {name|{a}}',
    fontSize: 16,
    rich: {
        name: {
        }
    }
};

option_row_3 = {
    title: {
        text: "No data",
        show: true,
        zlevel: 20,
        x: '35%',
        y: '50%',
        formatter: "value",
        textStyle: {
            fontWeight: 'normal',
            color: '#666666',
            fontSize: '16'
        }
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow' // The default is line：'line' | 'shadow'
        }
    },
    toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature: {
            mark: {show: true},
            dataView: {show: false, readOnly: false},
            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    legend: {
        data: []
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            axisTick: {show: false},
            data: []
        }
    ],
    yAxis: [
        {
            type: 'value',
            scale : true
        }
    ],
    series: []
};

function downloadWhatsAppPieChartReport(type) {
    var a, b, c, l, h;
    if (type === "O") {
        b = option_row_1_col_1.series[0].data;
        l = option_row_1_col_1.legend.data;
        h = "Outbound Messages";
    } else if (type === "C") {
        b = option_row_1_col_2.series[0].data;
        l = option_row_1_col_2.legend.data;
        if ($("input[name='whatsappDashSummaryCheckbox']:checked").val() === 'O') {
            h = "Open Report";
        } else if ($("input[name='whatsappDashSummaryCheckbox']:checked").val() === 'CU') {
            h = "Completed By User";
        } else if ($("input[name='whatsappDashSummaryCheckbox']:checked").val() === 'CC') {
            h = "Completed By Close Code";
        }
    }
    var d = [];
    for (var i = 0; i < b.length; i++) {
        d[i] = JSON.parse("{\"User Name\": \"" + (b[i].name === 'Auto Responce' ? b[i].name : b[i].name.substr(b[i].name, b[i].name.indexOf(' '))) + "\", \"Total\": \"" + b[i].value + "\"}");
    }
    JSONToCSVConvertor(d, h, true);
}

function downloadWhatsAppBarChartReport(type) {
    var a, b, c, l, h;
    if (type === "I") {
        a = option_row_2.xAxis[0].data;
        b = option_row_2.series[0].data;
        c = option_row_2.series[1].data;
        l = option_row_2.legend.data;
        h = "Inbound And Outbound Messages";
    }
    var d = [];
    for (var i = 0; i < a.length; i++) {
        d[i] = JSON.parse("{\"Month\": \"" + a[i] + "\", \"" + l[0] + "\": \"" + b[i] + "\", \"" + l[1] + "\": \"" + c[i] + "\"}");
    }
    JSONToCSVConvertor(d, h, true);
}

function JSONToCSVConvertor(d, ReportTitle, ShowLabel) {
    var arrData = typeof d != 'object' ? JSON.parse(d) : d;
    var CSV = '';
    CSV += ReportTitle + '\r\n\n';
    if (ShowLabel) {
        var row = "";
        for (var index in arrData[0]) {
            row += index + ',';
        }
        row = row.slice(0, -1);
        CSV += row + '\r';
    }
    if (arrData.length > 0) {
        for (var i = 0; i < arrData.length; i++) {
            var row = "";
            for (var index in arrData[i]) {
                row += '"' + arrData[i][index] + '",';
            }
            row.slice(0, row.length - 1);
            CSV += row + '\r';
        }
    } else {
        row = "Report data not available";
        row.slice(0, row.length - 1);
        CSV += row + '\r';
    }
    if (CSV == '') {
        alert("Invalid data");
        return;
    }
    var fileName = "Report_";
    //this will remove the blank-spaces from the title and replace it with an underscore
    fileName += ReportTitle.replace(/ /g, "_");
    var uri = 'data:text/csv;charset=utf-8,' + escape(CSV);
    var link = document.createElement("a");
    link.href = uri;
    link.style = "visibility:hidden";
    link.download = fileName + ".csv";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}