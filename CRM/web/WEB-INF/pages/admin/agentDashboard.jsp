<%-- 
    Document   : agentDashboard
    Created on : 23 Nov, 2017, 5:57:18 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="">
    <div class="my-bord bor2" style="padding-top:7px !important;">               
        <s:form class="form-inline" id="frm_agent" name="frm_agent" method="post" theme="simple">
            <div class="form-group padR">
                <label for="email"><s:text name="lbl.agent.name"/></label>
                <s:select name="userId" id="sel_pie_userId" list="userList" listKey="key" listValue="value" headerKey="" headerValue="All" cssClass="form-control show-tick"/>
            </div>
            <div class="form-group padR">
                <label for="email"><s:text name="lbl.month"/></label>
                <s:select name="month" id="sel_pie_month" list="monthRangeList" listKey="key" listValue="value" cssClass="form-control show-tick"/>
            </div>
            <div class="form-group padR">
                <label for="email"><s:text name="lbl.year"/></label>
                <s:select class="form-control" name="yearList" id="sel_pie_year" list="yearList" />
            </div>
            <%--div class="form-group padR">
                <div id="date_range" class="form-control" style="background: #fff; cursor: pointer; padding: 6px 12px; border: 1px solid #ccc;">
                    <i class="fa fa-calendar"></i>&nbsp;
                    <span>Not Selected</span> <i class="fa fa-caret-down pull-right" style="margin-top: 5px;"></i>
                    <s:hidden id="sel_pie_month"/>
                    <s:hidden id="sel_pie_year"/>
                </div>
            </div--%>
            <div class="form-group padR">
                <button type="button" id="agent_search" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
            </div>
        </s:form>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6 fullscreen-block">
                <div class="prm-calums my-reminder">
                    <div class="my-bord">
                        <h3><s:text name="lbl.inbound.and.outBound.calls"/></h3>
                        <div class="board-icons">
                            <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="chart_call_summary" data-fs-height-on="350px" data-fs-height-off="350px"></a>
                        </div>
                    </div>
                    <div class="premium-graph">
                        <div class="body">
                            <div class="row clearfix top_search">
                                <div class="col-lg-12 col-md-12 ">
                                    <div id="chart_call_summary" style="height: 350px; width: 100%;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 fullscreen-block">
                <div class="prm-calums my-reminder">
                    <div class="my-bord">
                        <h3><s:text name="lbl.missed.calls"/></h3>
                        <div class="board-icons">
                            <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="chart_missedcall_summary" data-fs-height-on="350px" data-fs-height-off="350px"></a>
                        </div>
                    </div>
                    <div class="premium-graph">
                        <div class="body">
                            <div class="row clearfix top_search">
                                <div class="col-lg-12 col-md-12 ">
                                    <div id="chart_missedcall_summary" style="height: 350px; width: 100%;" ></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6 fullscreen-block">
                <div class="prm-calums my-reminder">
                    <div class="my-bord">
                        <h3><s:text name="lbl.total.revenue"/></h3>
                        <div class="board-icons">
                            <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="chart_Revenue_summary" ></a>
                        </div>
                    </div>
                    <div class="premium-graph">
                        <div class="body">
                            <div class="row clearfix top_search">
                                <div class="col-lg-12 col-md-12 ">
                                    <div id="chart_Revenue_summary" style="height: 350px; width: 100%;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="prm-calums my-reminder">
                    <div class="my-bord">
                        <h3><s:text name="lbl.quotation.followups"/></h3>
                        <div class="pull-right" style="padding: 5px 10px 0px 0px;">
                            <button class="btn btn-info" title="Excel" onclick="downloadPaymentLinkReport('Q');"><i class="fa fa-download"></i> <s:text name="btn.excel"/></button>
                        </div>
                    </div>
                    <div class="premium-graph">
                        <div id="chart_quote_summary" style="height: 350px; width: 100%;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <div class="prm-calums my-reminder">
                    <div class="my-bord">
                        <h3><s:text name="lbl.renewal.followups"/></h3>
                        <div class="pull-right" style="padding: 5px 10px 0px 0px;">
                            <button class="btn btn-info" title="Excel" onclick="downloadPaymentLinkReport('R');"><i class="fa fa-download"></i> <s:text name="btn.excel"/></button>
                        </div>
                    </div>
                    <div class="premium-graph">
                        <div id="chart_renewal_summary" style="height: 300px; width: 100%;"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="prm-calums my-reminder">
                    <div class="my-bord">
                        <h3><s:text name="lbl.payment.links"/></h3>
                        <div class="pull-right" style="padding: 5px 10px 0px 0px;">
                            <button class="btn btn-info" title="Excel" onclick="downloadPaymentLinkReport('P');"><i class="fa fa-download"></i> <s:text name="btn.excel"/></button>
                        </div>
                    </div>
                    <div class="premium-graph">
                        <div id="chart_paylink_summary" style="height: 300px; width: 100%;"></div>
                    </div>
                </div>
            </div> 
        </div>
    </div>
    <div class="clearfix"></div>
    <br /> 
</div>

<script type="text/javascript">
    var chart_paylink_summary, chart_renewal_summary, chart_quote_summary, chart_call_summary, chart_missedcall_summary, chart_Revenue_summary;
    var data = "";
    var quoteSum, renewSum, paySum;
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
            if ($(this).data("fs-id") === "chart_call_summary") {
                setTimeout(function () {
                    chart_call_summary.resize();
                }, 100);
            } else if ($(this).data("fs-id") === "chart_missedcall_summary") {
                setTimeout(function () {
                    chart_missedcall_summary.resize();
                }, 100);
            } else if ($(this).data("fs-id") === "chart_Revenue_summary") {
                setTimeout(function () {
                    chart_Revenue_summary.resize();
                }, 100);
            }
        });

        $('#date_range').daterangepicker({
            startDate: moment(),
            endDate: moment(),
            locale: {
                format: 'MM/YYYY'
            }
        }, function (start, end, label) {
            $('#date_range span').html(start.format('MM/YYYY') + ' - ' + end.format('MM/YYYY'));
            $('#sel_pie_month').val(end.format('YYYYMM'));
            $('#sel_pie_year').val(start.format('YYYYMM'));
        });

        $("#agent_search").on("click", function () {
            var pieData = [], legendData = [];
            chart_call_summary = echarts.init(document.getElementById('chart_call_summary'));
            chart_call_summary_option = {
                title: {
                    text: "No data",
                    show: true,
                    zlevel: 20,
                    x: 'center',
                    y: '55%',
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
                legend: {
                    show: false,
                    orient: 'vertical',
                    x: 'left',
                    data: legendData
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: false},
                        dataView: {show: false, readOnly: false},
                        magicType: {show: false, type: ['pie', 'funnel'], option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 1548
                                }
                            }},
                        restore: {show: false},
                        saveAsImage: {show: false},
                    }
                },
                calculable: false,
                series: [
                    {
                        type: 'pie',
                        avoidLabelOverlap: true,
                        center: ["50%", "60%"],
                        radius: ["35%", "55%"],
                        itemStyle: 'labelFromatter',
                        data: pieData,
                        color: ['#44b8e2', '#db9bc8', '#8ad0f9', '#fdc16c', '#00a640', '#fa5840', '#00a640', '#cd5c5c', '#ba55d3', '#ffa500', '#40e0d0', '#ff69b4', '#6495ed', '#32cd32', '#da70d6', '#87cefa', '#87cefa', '#ff69b4', '#c37cca', '#8f95e7']
                    }
                ]
            };
            chart_call_summary.setOption(chart_call_summary_option);
            window.onresize = function () {
                setTimeout(function () {
                    chart_call_summary.resize();
                }, 200);
            }
            callSummaryFilter($("#sel_pie_userId").val(), $("#sel_pie_month").val(), $("#sel_pie_year").val());

            var pieData = [], legendData = [];
            chart_missedcall_summary = echarts.init(document.getElementById('chart_missedcall_summary'));
            chart_missedcall_summary_option = {
                title: {
                    text: "No data",
                    show: true,
                    zlevel: 20,
                    x: 'center',
                    y: '55%',
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
                legend: {
                    show: false,
                    orient: 'vertical',
                    x: 'left',
                    data: legendData
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: false},
                        dataView: {show: false, readOnly: false},
                        magicType: {show: false, type: ['pie', 'funnel'], option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 1548
                                }
                            }},
                        restore: {show: false},
                        saveAsImage: {show: false}
                    }
                },
                calculable: false,
                series: [
                    {
                        type: 'pie',
                        avoidLabelOverlap: true,
                        center: ["50%", "60%"],
                        radius: ["35%", "55%"],
                        itemStyle: 'labelFromatter',
                        data: pieData,
                        color: ['#00a640', '#fa5840', '#028bc5', '#fdc16c', '#00a640', '#fa5840', '#00a640', '#cd5c5c', '#ba55d3', '#ffa500', '#40e0d0', '#ff69b4', '#6495ed', '#32cd32', '#da70d6', '#87cefa', '#87cefa', '#ff69b4', '#c37cca', '#8f95e7']
                    }
                ]
            };
            chart_missedcall_summary.setOption(chart_missedcall_summary_option);
            window.onresize = function () {
                setTimeout(function () {
                    chart_missedcall_summary.resize();
                }, 200);
            }
            loadMissedcallSummary($("#sel_pie_userId").val(), $("#sel_pie_month").val(), $("#sel_pie_year").val());

            // Revenu Summary
            var pieData = [], legendData = [];
            chart_Revenue_summary = echarts.init(document.getElementById('chart_Revenue_summary'));
            chart_Revenue_summary_option = {
                title: {
                    text: "No data",
                    show: true,
                    zlevel: 20,
                    x: 'center',
                    y: '45%',
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
                legend: {
                    show: false,
                    orient: 'vertical',
                    x: 'left',
                    data: legendData
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: false},
                        dataView: {show: false, readOnly: false},
                        magicType: {show: false, type: ['pie', 'funnel'], option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 1548
                                }
                            }},
                        restore: {show: false},
                        saveAsImage: {show: false}
                    }
                },
                calculable: false,
                series: [
                    {
                        type: 'pie',
                        avoidLabelOverlap: true,
                        center: ["50%", "50%"],
                        radius: ["35%", "55%"],
                        itemStyle: 'labelFromatter',
                        data: pieData,
                        color: ['#ef1071', '#7449ac', '#438d8e', '#fdc16c', '#00a640', '#fa5840', '#00a640', '#cd5c5c', '#ba55d3', '#ffa500', '#40e0d0', '#ff69b4', '#6495ed', '#32cd32', '#da70d6', '#87cefa', '#87cefa', '#ff69b4', '#c37cca', '#8f95e7']
                    }
                ]
            };
            chart_Revenue_summary.setOption(chart_Revenue_summary_option);
            window.onresize = function () {
                setTimeout(function () {
                    chart_Revenue_summary.resize();
                }, 200);
            };

            //Quote Summary
            chart_quote_summary = echarts.init(document.getElementById('chart_quote_summary'));

            option_quote_summary = {
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
                        type: 'shadow' // The default is line：'line' | 'shadow'
                    }
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: legendData
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
                        name: 'Alocation Count',
                        type: 'bar',
                        barWidth: '30%',
                        data: []
                    },
                    {
                        name: 'Policy Count',
                        type: 'bar',
                        barWidth: '30%',
                        data: [],
                        color: ['#4caf50', '#79ba56', '#8ad0f9', '#fdc16c', '#00a640', '#fa5840', '#00a640', '#cd5c5c', '#ba55d3', '#ffa500', '#40e0d0', '#ff69b4', '#6495ed', '#32cd32', '#da70d6', '#87cefa', '#87cefa', '#ff69b4', '#c37cca', '#8f95e7']
                    }
                ]
            };


            chart_quote_summary.setOption(option_quote_summary);
            window.onresize = function () {
                setTimeout(function () {
                    chart_quote_summary.resize();

                }, 200);
            };
            chart_renewal_summary = echarts.init(document.getElementById('chart_renewal_summary'));


            option_renewal_summary = {
                title: {
                    text: "",
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
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: legendData
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
                        name: 'Alocation Count',
                        type: 'bar',
                        barWidth: '30%',
                        data: []
                    },
                    {
                        name: 'Policy Count',
                        type: 'bar',
                        barWidth: '30%',
                        data: [],
                        color: ['#d29d1e', '#4caf50', '#8ad0f9', '#fdc16c', '#00a640', '#fa5840', '#00a640', '#cd5c5c', '#ba55d3', '#ffa500', '#40e0d0', '#ff69b4', '#6495ed', '#32cd32', '#da70d6', '#87cefa', '#87cefa', '#ff69b4', '#c37cca', '#8f95e7']
                    }
                ]
            };

            chart_renewal_summary.setOption(option_renewal_summary);
            window.onresize = function () {
                setTimeout(function () {
                    chart_renewal_summary.resize();

                }, 200);
            };

            chart_paylink_summary = echarts.init(document.getElementById('chart_paylink_summary'));

            option_paylink_summary = {
                title: {
                    // text : "Payment Links Followups",
                    show: true,
                    textStyle: {
                        color: 'grey',
                        fontSize: 20
                    },
                    left: 'center',
                    top: 'top'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow' // The default is line：'line' | 'shadow'
                    }
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: legendData
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
                        name: 'Link Count',
                        type: 'bar',
                        barWidth: '30%',
                        data: [],
                        color: ['#a00669', '#db9bc8', '#8ad0f9', '#fdc16c', '#00a640', '#fa5840', '#00a640', '#cd5c5c', '#ba55d3', '#ffa500', '#40e0d0', '#ff69b4', '#6495ed', '#32cd32', '#da70d6', '#87cefa', '#87cefa', '#ff69b4', '#c37cca', '#8f95e7']
                    },
                    {
                        name: 'Policy Count',
                        type: 'bar',
                        barWidth: '30%',
                        data: []
                    }
                ]
            };


            chart_paylink_summary.setOption(option_paylink_summary);
            window.onresize = function () {
                setTimeout(function () {
                    chart_paylink_summary.resize();

                }, 200);
            };
            callSummary($("#sel_pie_userId").val(), $("#sel_pie_month").val(), $("#sel_pie_year").val());
        });
        $("#agent_search").trigger("click");
    });
    function callSummaryFilter(agentId, month, year) {
        chart_call_summary.showLoading({
            text: 'Please wait!... '
        });
        chart_call_summary.clear();
        $.getJSON(APP_CONFIG.context + "/loadAgentDashBoardcallSummary.do", {"agentId": agentId, "month": month, "year": year}, function () {
        }).done(function (result) {
            chart_call_summary_option.legend.data = [];
            chart_call_summary_option.legend.data.push();
            chart_call_summary_option.series[0].data = [];
            let tot = 0;
            $.each(result.aaData, function (i, o) {
                chart_call_summary_option.legend.data.push(o.key);
                chart_call_summary_option.series[0].data.push({"name": o.key, "value": (Number(o.value) + Number(o.info))});
                tot += Number(o.value);
            });
            chart_call_summary_option.title.text = (tot > 0 ? "Total: " + tot : "No data");
            chart_call_summary.setOption(chart_call_summary_option);
        }).fail(function () {

        }).always(function () {
            chart_call_summary.hideLoading();
        });
    }

    //Missed Call Summary
    function loadMissedcallSummary(agentId, month, year) {
        chart_missedcall_summary.showLoading({
            text: 'Please wait!... '
        });
        chart_missedcall_summary.clear();
        $.getJSON(APP_CONFIG.context + "/loadAgentDashBoardcallSummary.do", {"agentId": agentId, "month": month, "year": year}, function () {
        }).done(function (result) {
            chart_missedcall_summary_option.legend.data = [];
            chart_missedcall_summary_option.legend.data.push();
            chart_missedcall_summary_option.series[0].data = [];
            let tot = 0;
            $.each(result.aaData, function (i, o) {
                chart_missedcall_summary_option.legend.data.push(o.key);
                chart_missedcall_summary_option.series[0].data.push({"name": o.key, "value": (Number(o.info1))});
                tot += Number(o.info1);
            });
            chart_missedcall_summary_option.title.text = (tot > 0 ? "Total: " + tot : "No data");
            chart_missedcall_summary.setOption(chart_missedcall_summary_option);

        }).fail(function () {

        }).always(function () {
            chart_missedcall_summary.hideLoading();
        });
    }

    function callSummary(agentId, month, year) {
        //console.log("Summary => type: "+type);
        chart_quote_summary.showLoading({
            text: 'Please wait!... '
        });
        chart_quote_summary.clear();
        quoteSum = 0;

        chart_renewal_summary.showLoading({
            text: 'Please wait!... '
        });
        chart_renewal_summary.clear();
        renewSum = 0;

        chart_paylink_summary.showLoading({
            text: 'Please wait!... '
        });
        chart_paylink_summary.clear();
        paySum = 0;

        chart_Revenue_summary.showLoading({
            text: 'Please wait!... '
        });

        $.when(
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadAgentDashBoardQuoteSummary.do",
            data: {"agentId": agentId, "month": month, "year": year},
            success: function (result) {
                //console.log("Summary Data: "+JSON.stringify(result))
                option_quote_summary.legend.data = [];
                option_quote_summary.series[0].data = [];
                option_quote_summary.series[1].data = [];
                option_quote_summary.legend.data.push("Alocation Count");
                option_quote_summary.legend.data.push("Policy Count");
                let tot = 0;
                $.each(result.aaData, function (i, o) {
                    option_quote_summary.xAxis[0].data.push(o.key);
                    option_quote_summary.series[0].data.push(o.value);
                    option_quote_summary.series[1].data.push(o.info);
                    quoteSum += Number(o.info1);
                    tot += Number(o.value);
                });
                option_quote_summary.title.text = (tot > 0 ? "Total: " + tot : "No data");
                option_quote_summary.title.show = (result.aaData && result.aaData.length > 0 ? false : true);
                chart_quote_summary.setOption(option_quote_summary);
            },
            complete: function () {
                chart_quote_summary.hideLoading();
            }
        }),
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadAgentDashBoardRenewalSummary.do",
            data: {"agentId": agentId, "month": month, "year": year},
            success: function (result) {
                //console.log("Summary Data 2: "+JSON.stringify(result))
                option_renewal_summary.legend.data = [];
                option_renewal_summary.series[0].data = [];
                option_renewal_summary.series[1].data = [];
                option_renewal_summary.legend.data.push("Alocation Count");
                option_renewal_summary.legend.data.push("Policy Count");
                $.each(result.aaData, function (i, o) {
                    option_renewal_summary.xAxis[0].data.push(o.key);
                    option_renewal_summary.series[0].data.push(o.value);
                    option_renewal_summary.series[1].data.push(o.info);
                    renewSum += Number(o.info1);
                });
                //option_renewal_summary.title.show = (result.aaData && result.aaData.length > 0 ? false : true);
                chart_renewal_summary.setOption(option_renewal_summary);
            },
            complete: function () {
                chart_renewal_summary.hideLoading();
            }
        }),
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadAgentDashBoardPayLinkSummary.do",
            data: {"agentId": agentId, "month": month, "year": year},
            success: function (result) {
                //console.log("Summary Data 1: "+JSON.stringify(result))
                option_paylink_summary.legend.data = [];
                option_paylink_summary.series[0].data = [];
                option_paylink_summary.series[1].data = [];
                option_paylink_summary.legend.data.push("Link Count");
                option_paylink_summary.legend.data.push("Policy Count");
                $.each(result.aaData, function (i, o) {
                    option_paylink_summary.xAxis[0].data.push(o.key);
                    option_paylink_summary.series[0].data.push(o.value);
                    option_paylink_summary.series[1].data.push(o.info);
                    paySum += Number(o.info1);

                });
                option_paylink_summary.title.show = (result.aaData && result.aaData.length > 0 ? false : true);
                chart_paylink_summary.setOption(option_paylink_summary);
            },
            complete: function () {
                chart_paylink_summary.hideLoading();
            }
        })
        ).then(function () {
            console.log("paySum: " + paySum + ", quoteSum: " + quoteSum + ", renewSum: " + renewSum);
            chart_Revenue_summary.clear();
            chart_Revenue_summary_option.legend.data = [];
            chart_Revenue_summary_option.legend.data.push();
            chart_Revenue_summary_option.series[0].data = [];

            if(paySum > 0) {
                chart_Revenue_summary_option.legend.data.push("Payment Links");
                chart_Revenue_summary_option.series[0].data.push({"name": "Payment Links", "value": paySum});
            }
            if(quoteSum > 0) {
                chart_Revenue_summary_option.legend.data.push("Quatation Followups");
                chart_Revenue_summary_option.series[0].data.push({"name": "Quatation Followups", "value": quoteSum});
            }
            if(renewSum > 0) {
                chart_Revenue_summary_option.legend.data.push("Renewal Followups");
                chart_Revenue_summary_option.series[0].data.push({"name": "Renewal Followups", "value": renewSum});
            }
            let tot = paySum + quoteSum + renewSum;
            chart_Revenue_summary_option.title.text = (tot > 0 ? "Total: " + tot : "No data");
            chart_Revenue_summary.setOption(chart_Revenue_summary_option);
            chart_Revenue_summary.hideLoading();
        });
    }

    function downloadPaymentLinkReport(type) {
        var a, b, c, l, h;
        if (type === "Q") {
            a = option_quote_summary.xAxis[0].data;
            b = option_quote_summary.series[0].data;
            c = option_quote_summary.series[1].data;
            l = option_quote_summary.legend.data;
            h = "Quotation Followups Report";
        } else if (type === "R") {
            a = option_renewal_summary.xAxis[0].data;
            b = option_renewal_summary.series[0].data;
            c = option_renewal_summary.series[1].data;
            d = "Records not Available";
            l = option_renewal_summary.legend.data;
            h = "Renewal Followups Report";
        } else if (type === "P") {
            a = option_paylink_summary.xAxis[0].data;
            b = option_paylink_summary.series[0].data;
            c = option_paylink_summary.series[1].data;
            l = option_paylink_summary.legend.data;
            h = "Payment Link Report";
        }
        var d = [];
        for (var i = 0; i < a.length; i++) {
            d[i] = JSON.parse("{\"User Name\": \"" + a[i] + "\", \"" + l[0] + "\": \"" + b[i] + "\", \"" + l[1] + "\": \"" + c[i] + "\"}");
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
</script>
