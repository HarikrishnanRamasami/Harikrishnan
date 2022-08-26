<%-- 
    Document   : TaskV2
    Created on : 4 Nov, 2017, 1:25:23 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-6 fullscreen-block">
            <div class="act-period">
                <div class="my-bord">
                    <h3><s:text name="lbl.task.summary.by"/></h3>
                    <div class="board-icons">
                        <s:select id="sel_type_task_summary" list="#{'TECHNICIAN':'Assigned To', 'CATEGORY':'Task Category', 'PRIORITY':'Priority'}" cssClass="form-control input-sm" cssStyle="display: inline; width: auto; background-color: #FFF; color: #000;"/>
                        <a class="fullscreen fa fa-expand fa-2x" href="#"></a>
                    </div>
                </div>
                <div class="activities-per">
                    <table id="tbl_task_summary" class="table table table-striped table-bordered display dataTable dtr-inline" cellpadding='0' border="0">
                        <thead>
                            <tr>
                                <th><s:text name="lbl.name"/></th>
                                <th style="text-align: center;"><s:text name="lbl.open"/></th>
                                <th style="text-align: center;"><s:text name="lbl.onhold"/></th>
                                <th style="text-align: center;"><s:text name="lbl.sla.violated"/></th>
                                <th style="text-align: center;"><s:text name="lbl.due.today"/></th>
                            </tr>                            
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-6 fullscreen-block">
            <div class="my-reminder">
                <div class="my-bord">
                    <h3><s:text name="lbl.open.tasks.by"/></h3>
                    <div class="board-icons">
                        <s:select id="sel_type_task_open" list="#{'TECHNICIAN':'Assigned To', 'CATEGORY':'Task Category', 'PRIORITY':'Priority' }" cssClass="form-control input-sm" cssStyle="display: inline; width: auto; background-color: #FFF; color: #000;"/>
                        <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="chart_row_1_col_2" data-fs-height-on="400px" data-fs-height-off="350px"></a>
                    </div>
                </div>
                <div class="dash-task">
                    <div class="body">
                        <div class="row clearfix">
                            <div class="col-lg-12 col-md-12 fs-zoom">
                                <div id="chart_row_1_col_2" style="height: 350px; width: 100%; padding-left: 0px !important; padding-right: 0px !important; background:red !important;"></div> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6 fullscreen-block">
            <div class="my-tasks">
                <div class="my-bord">
                    <h3><s:text name="lbl.sla.violated.requests"/></h3>
                    <div class="board-icons">
                        <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="chart_row_2_col_1" data-fs-height-on="400px" data-fs-height-off="350px"></a>
                    </div>
                </div>	
                <div class="dash-task">
                    <div class="body">
                        <div class="col-md-12" style="position: absolute;">
                            <div class="row">
                                <div class="col-md-6"><s:text name="lbl.open"/>: <s:property value="dataBean.count1" /></div>
                            </div>
                            <div class="row">
                                <div class="col-md-6"><s:text name="lbl.sla.violated"/>: <s:property value="dataBean.count2" /></div>
                            </div>
                        </div>
                        <div class="call-details">
                            <div class="body">
                                <div class="row clearfix">
                                    <div class="col-lg-12 col-md-12 fs-zoom">
                                        <div id="chart_row_2_col_1" style="height: 350px; width: 100%; padding: 0px !important;"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6 fullscreen-block">
            <div class="my-tasks">
                <div class="my-bord">
                    <h3><s:text name="lbl.task.by"/></h3>
                    <div class="board-icons">
                        <s:select id="sel_type_task_by_period" list="#{'TECHNICIAN':'Assigned To', 'CATEGORY':'Task Category', 'PRIORITY':'Priority' }" cssClass="form-control input-sm" cssStyle="display: inline; width: auto;"/>
                        <s:select name="dateRange" id="sel_dr_task_by_period" list="dateRangeList" listKey="key" listValue="value" cssClass="form-control input-sm" cssStyle="display: inline; width: auto;"/>
                        <a class="fullscreen fa fa-expand fa-2x" href="javascript:void(0);" data-fs-id="chart_row_2_col_2" data-fs-height-on="350px" data-fs-height-off="300px"></a>
                    </div>
                </div>
                <div class="call-details">
                    <div class="body">
                        <div class="row clearfix fs-zoom">
                            <div class="col-lg-12 col-md-12">
                                <div id="chart_row_2_col_2" style="height: 300px; width: 100%; padding: 0px !important;"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 fullscreen-block">
            <div class="my-tasks">
                <div class="my-bord">
                    <h3><s:text name="lbl.task.category"/></h3>
                    <div class="board-icons">
                        <s:select name="dateRange" id="sel_dr_task_by_catg" list="dateRangeList" listKey="key" listValue="value" cssClass="form-control input-sm" cssStyle="display: inline; width: auto;"/>
                        <a class="fullscreen fa fa-expand fa-2x" href="javascript:void(0);" data-fs-id="chart_row_2_col_2" data-fs-height-on="350px" data-fs-height-off="300px"></a>
                    </div>
                </div>
                <div class="dash-task">
                    <div class="body">
                        <table id="tbl_task_catg_sla" class="table table table-striped table-bordered display dataTable dtr-inline" cellpadding='0' border="0">
                            <thead>
                                <tr>
                                    <th style="text-align: center;"><s:text name="lbl.category"/></th>
                                    <th style="text-align: center;"><s:text name="lbl.assigned"/></th>
                                    <th style="text-align: center;"><s:text name="lbl.approached"/></th>
                                    <th style="text-align: center;"><s:text name="lbl.sla.violated"/></th>
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
</div>
<script type="text/javascript">

    var chart_row_1_col_2, chart_row_2_col_1, chart_row_2_col_2;

    $(document).ready(function () {
        $('.fullscreen').click(function () {
            $(this).parents('.fullscreen-block').toggleClass('panel-fullscreen');
            if ($(this).hasClass("fa-expand")) {
                $(this).removeClass("fa-expand").addClass("fa-compress");
                $("#" + $(this).data("fs-id")).css({height: $(this).data("fs-height-on")});
            } else {
                $(this).removeClass("fa-compress").addClass("fa-expand");
                $("#" + $(this).data("fs-id")).css({height: $(this).data("fs-height-off")});
            }
            if ($(this).data("fs-id") === "chart_row_1_col_2") {
                setTimeout(function () {
                    chart_row_1_col_2.resize();
                }, 100);
            } else if ($(this).data("fs-id") === "chart_row_2_col_1") {
                setTimeout(function () {
                    chart_row_2_col_1.resize();
                }, 100);
            } else if ($(this).data("fs-id") === "chart_row_2_col_2") {
                setTimeout(function () {
                    chart_row_2_col_2.resize();
                }, 100);
            }
        });
        $('[data-toggle="tooltip"]').tooltip();
        tbl_task_summary = $("#tbl_task_summary").DataTable({
            paging: true,
            searching: false,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 7,
            responsive: true,
            destory: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadTaskDashBoardSummary.do",
                "data": function (d) {
                    return $.extend({}, d, {"type": $("#sel_type_task_summary").val()});
                },
                "type": "POST",
                "datatype": "json"
            },
            columns: [
                {data: "data1"},
                {data: "count1"},
                {data: "count2"},
                {data: "count3"},
                {data: "count4"}],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "none",
                "aButtons": []
            },
            order: [[0, "asc"]]
        });

        tbl_task_catg_sla = $("#tbl_task_catg_sla").DataTable({
            paging: true,
            searching: false,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadTaskDashBoardSlaSummaryByPeriod.do",
                "data": function (d) {
                    return $.extend({}, d, {"dateRange": $("#sel_dr_task_by_catg").val()});
                },
                "type": "POST",
                "datatype": "json"
            },
            columns: [
                {data: "data1"},
                {data: "count1"},
                {data: "count2"},
                {data: "count3"}
            ],
            dom: 'lBftrpi',
            tableTools: {
                "sRowSelect": "none"
            },
            "buttons": [
                {
                    extend: 'excel',
                    text: 'Download',
                    className: 'btn btn-primary',
                    style: 'margin-right: 4px;',
                    title: 'Task category wise SLA',
                    filename: 'task_catg_sla_' + Math.floor(Math.random() * Math.floor(99999999999999999)),
                    exportOptions: {columns: [':visible']}
                }
            ],
            order: [[0, "asc"]]
        });

        $('.acti-off-heads a').click(function () {
            var tab_id = $(this).attr('data-tab');

            $('.acti-off-heads a').removeClass('active');
            $('.act-name').removeClass('current');

            $(this).addClass('active');
            $("#" + tab_id).addClass('current');
        });

        chart_row_1_col_2 = echarts.init(document.getElementById('chart_row_1_col_2'));
        chart_row_2_col_1 = echarts.init(document.getElementById('chart_row_2_col_1'));
        chart_row_2_col_2 = echarts.init(document.getElementById('chart_row_2_col_2'));

        option_row_1_col_2 = {
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
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            toolbox: {
                feature: {
                    restore: {},
                    saveAsImage: {}
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
                    radius: ['45%', '60%'],
                    center: ['45%', '55%'],
                    data: [],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ],
            color: ['#44b8e2', '#db9bc8', '#8ad0f9', '#fdc16c', '#b1d653', '#fa5840', '#00a640', '#cd5c5c', '#ba55d3', '#ffa500', '#40e0d0', '#ff69b4', '#6495ed', '#32cd32', '#da70d6', '#87cefa', '#87cefa', '#ff69b4', '#c37cca', '#8f95e7'],
            textStyle: {
                fontWeight: 'normal',
                fontSize: '11'
            }
        };

        var slaVal = Number("${dataBean.count1}") === 0 ? 0 : Math.round(Number("${dataBean.count2}") / Number("${dataBean.count1}") * 100)
        option_row_2_col_1 = {
            tooltip: {
                formatter: "{a} <br/>{b} : {c}%"
            },
            toolbox: {
                feature: {
                    restore: {},
                    saveAsImage: {}
                }
            },
            series: [
                {
                    name: 'SLA',
                    type: 'gauge',
                    detail: {formatter: '{value}%'},
                    data: [{value: slaVal, name: 'Violated'}]
                }
            ]
        };


        const option_row_2_col_2_label = ['Closed', 'Pending', 'SLA Violated']
        option_row_2_col_2 = {
            title: {
                show: true,
                textStyle: {
                    color: 'grey',
                    fontSize: 20
                },
                text: "No data available",
                left: 'center',
                top: 'center'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow' //'line' | 'shadow'
                }
            },
            legend: {
                data: ['0', '1', '2'],
                selected: {
                    '2': false
                },
                formatter: function (name) {
                    return option_row_2_col_2_label[Number(name)];
                }
            },
            toolbox: {
                feature: {
                    restore: {},
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'value'
            },
            yAxis: {
                type: 'category',
                data: []
            },
            series: [
                {
                    name: '0',
                    type: 'bar',
                    stack: 'S1',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    data: []
                },
                {
                    name: '1',
                    type: 'bar',
                    stack: 'S1',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    data: []
                },
                {
                    name: '2',
                    type: 'bar',
                    stack: 'S1',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    data: []
                }
            ]
        };

        chart_row_1_col_2.setOption(option_row_1_col_2);
        chart_row_2_col_1.setOption(option_row_2_col_1);
        chart_row_2_col_2.setOption(option_row_2_col_2);
        window.onresize = function () {
            setTimeout(function () {
                chart_row_1_col_2.resize();
                chart_row_2_col_1.resize();
                chart_row_2_col_2.resize();
            }, 200);
        };

        $("#sel_type_task_summary").on("change", function () {
            tbl_task_summary.ajax.reload();
        });

        $("#sel_dr_task_by_catg").on("change", function () {
            tbl_task_catg_sla.ajax.reload();
        });

        $("#sel_type_task_open").on("change", function () {
            loadTaskSummary($("#sel_type_task_open").val());
        });

        $("#sel_type_task_by_period, #sel_dr_task_by_period").on("change", function () {
            loadTaskSummaryByPeriod($("#sel_type_task_by_period").val(), $("#sel_dr_task_by_period").val());
        });

        $("#sel_type_task_by_period, #sel_type_task_open").trigger("change");
    });

    function loadTaskSummary(type) {
        //console.log("Summary => type: "+type);
        chart_row_1_col_2.showLoading({
            text: 'Please wait!... '
        });
        chart_row_1_col_2.clear();
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadTaskDashBoardSummary.do",
            data: {"type": type},
            success: function (result) {
                //console.log("Summary Data: "+JSON.stringify(result))
                option_row_1_col_2.legend.data = [];
                option_row_1_col_2.series[0].data = [];
                let tot = 0;
                $.each(result.aaData, function (i, o) {
                    option_row_1_col_2.legend.data.push(o.data1);
                    option_row_1_col_2.series[0].data.push({"name": o.data1, "value": (Number(o.count1) + Number(o.count2))});
                    tot += (Number(o.count1) + Number(o.count2));
                });
                option_row_1_col_2.title.text = (tot > 0 ? "Total: " + tot : "No data");
                //option_row_1_col_2.title.show = (result.aaData && result.aaData.length > 0 ? false : true);
                chart_row_1_col_2.setOption(option_row_1_col_2);
            },
            complete: function () {
                chart_row_1_col_2.hideLoading();
            }
        });
    }

    function loadTaskSummaryByPeriod(type, dateRange) {
        //console.log("ByPeriod => type: "+type+", dateRange: "+dateRange);
        chart_row_2_col_2.showLoading({
            text: 'Please wait!... '
        });
        chart_row_2_col_2.clear();
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadTaskDashBoardSummaryByPeriod.do",
            data: {"type": type, "dateRange": dateRange},
            success: function (result) {
                //console.log("ByPeriod Data: "+JSON.stringify(result))
                option_row_2_col_2.yAxis.data = [];
                for (var i = 0; i < 3; i++) {
                    option_row_2_col_2.series[i].data = [];
                }
                $.each(result.aaData, function (i, o) {
                    option_row_2_col_2.yAxis.data.push(o.data1 + " (" + o.count1 + ")");
                    option_row_2_col_2.series[0].data.push(Number(o.count2));
                    option_row_2_col_2.series[1].data.push(Number(o.count4));
                    option_row_2_col_2.series[2].data.push(Number(o.count3));
                });
                option_row_2_col_2.title.show = (result.aaData && result.aaData.length > 0 ? false : true);
                chart_row_2_col_2.setOption(option_row_2_col_2);
            },
            complete: function () {
                chart_row_2_col_2.hideLoading();
            }
        });
    }

    chart_row_2_col_2.on('legendSelectChanged', renderLegendSelect);
    function renderLegendSelect(param) {
        if (param.name === "2") {
            if (param.selected["2"]) {
                param.selected["0"] = false;
                param.selected["1"] = false;
            } else {
                param.selected["0"] = true;
                param.selected["1"] = true;
            }
        } else if (param.name === "0" || param.name === "1") {
            param.selected["2"] = false;
        }
        option_row_2_col_2.legend.selected = param.selected;
        chart_row_2_col_2.setOption(option_row_2_col_2);
    }
</script>
