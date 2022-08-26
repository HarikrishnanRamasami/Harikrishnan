<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : leadDashboard
    Created on : Nov 9, 2017, 7:08:50 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 m-tb-3">
    <div class="col-md-2 nopad">
        <s:text name="lbl.leads.Lead.source"/>
    </div>
    <div class="col-md-4 nopad">
        <s:select name="leads.clSource" id="ds_clSource" list="sourceList" listKey="key" listValue="value" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="Source"/>
    </div>
    <div class="col-md-6">
        <h4><span id="div_status"></span> (<span id="div_start_dt"></span> <s:text name="lbl.to"/> <span id="div_end_dt"></span>)</h4>
    </div>
    <div class="col-md-12 nopad">
        <div class="elig-details">
            <ul class="total-prm">
                <li class="premium"><p><s:text name="lbl.leads.total.leads"/></p><h5 id="div_fig_1" class="formatted-number"></h5></li>
                <li class="claims"><p><s:text name="lbl.leads.opportunity"/></p><h5 id="div_fig_2" class="formatted-number"></h5></li>
                <li class="ratio"><p><s:text name="lbl.leads.materialized"/></p><h5 id="div_fig_3" class="formatted-number"></h5></li>
                <li class="source"><p><s:text name="lbl.leads.expected.income"/></p><h5 id="div_fig_4" class="formatted-decimal"></h5></li>
                <li class="client"><p><s:text name="lbl.leads.opp.income"/></p><h5 id="div_fig_5" class="formatted-decimal"></h5></li>
                <li class="client"><p><s:text name="lbl.leads.actual.income"/></p><h5 id="div_fig_6" class="formatted-decimal"></h5></li>
            </ul>
        </div>
    </div>
    <div class="col-md-6 nopad">
        <div class="prm-calums my-reminder">
            <div class="premium-graph">
                <div id="chart_pie" style="height: 300px; width: 100%;"></div>
            </div>
        </div>
    </div>
    <div class="col-md-6 right-pad">
        <div class="prm-calums my-reminder">
            <div class="premium-graph">
                <div id="chart_bar" style="height: 300px; width: 100%;"></div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    var chart_pie, chart_bar, chart_option_pie, chart_option_bar;

    $(document).ready(function () {
        chart_pie = echarts.init(document.getElementById('chart_pie'));
        chart_bar = echarts.init(document.getElementById('chart_bar'));
        
        $(".formatted-decimal").autoNumeric('init');
        $(".formatted-number").autoNumeric('init', {mDec: '0'});
        $("#ds_clSource").on("change", function () {
            loadSummary($(this).val());
        });
        
        chart_option_pie = {
            title: {
                text: "No data",
                show: true,
                zlevel: 20,
                x: '33%',
                y: '47%',
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
                    radius: ['45%', '65%'],
                    center: ['40%', '50%'],
                    data: [],
                    color: ['#44b8e2','#db9bc8','#8ad0f9','#fdc16c','#b1d653','#fa5840','#00a640','#cd5c5c','#ba55d3','#ffa500','#40e0d0','#ff69b4','#6495ed','#32cd32','#da70d6','#87cefa','#87cefa','#ff69b4','#c37cca','#8f95e7'],
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

        chart_option_bar = {
            title: {
                show: true,
                textStyle: {
                    color: '#666666',
                    fontSize: 16,
                    fontWeight: 'normal'
                },
                text: "No data",
                left: 'center',
                top: 'center'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow' // The default is line：'line' | 'shadow'
                }
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
                    name: 'Revenue',
                    type: 'bar',
                    barWidth: '50%',
                    data: []
                   }
            ],
            color: ['#82bd61','#db9bc8','#8ad0f9','#fdc16c','#b1d653','#fa5840','#00a640','#cd5c5c','#ba55d3','#ffa500','#40e0d0','#ff69b4','#6495ed','#32cd32','#da70d6','#87cefa','#87cefa','#ff69b4','#c37cca','#8f95e7']
        };
        chart_pie.setOption(chart_option_pie);
        chart_bar.setOption(chart_option_bar);
        window.onresize = function () {
            setTimeout(function () {
                chart_pie.resize();
                chart_bar.resize();
            }, 200);
        };
        
        $("#ds_clSource").trigger("change");
    });

    function loadSummary(source) {
        block('block_body');
        chart_pie.showLoading({
            text: 'Please wait!... '
        });
        chart_bar.showLoading({
            text: 'Please wait!... '
        });
        chart_pie.clear();
        chart_bar.clear();
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadLeadsDashBoardSummary.do",
            data: {"leads.clSource": source},
            async: true,
            success: function (result) {
                var PIE_STATUS = {"P": "Pending", "O": "Opportunity", "C": "Materialized"}
                if(result.dataBean && result.dataBean.SUMMARY) {
                    $("#div_fig_1").autoNumeric("set", result.dataBean.SUMMARY.count1); // Total Leads
                    $("#div_fig_2").autoNumeric("set", result.dataBean.SUMMARY.count2); // Opportunity
                    $("#div_fig_3").autoNumeric("set", result.dataBean.SUMMARY.data1); // Materialized
                    $("#div_fig_4").autoNumeric("set", result.dataBean.SUMMARY.data2); // Expected Income
                    $("#div_fig_5").autoNumeric("set", result.dataBean.SUMMARY.data3); // Opp. Income
                    $("#div_fig_6").autoNumeric("set", result.dataBean.SUMMARY.data4); // Actual Income
                    $("#div_status").text(result.dataBean.SUMMARY.count3 === 1 ? "Active" : "Inactive");
                    $("#div_start_dt").html(moment(result.dataBean.SUMMARY.date1, "YYYY-MM-DDTHH:mm:ss").format('Do MMMM YYYY').replace( /(\d)(st|nd|rd|th)/g, '$1<sup>$2</sup>' ));
                    $("#div_end_dt").html(moment(result.dataBean.SUMMARY.date2, "YYYY-MM-DDTHH:mm:ss").format('Do MMMM YYYY').replace( /(\d)(st|nd|rd|th)/g, '$1<sup>$2</sup>' ));
                } else {
                    for(var i=1; i<=6; i++) {
                        $("#div_fig_"+i).autoNumeric("set", 0);
                    }
                }
                chart_option_pie.legend.data = [];
                chart_option_pie.series[0].data = [];
                chart_option_bar.xAxis[0].data = [];
                chart_option_bar.series[0].data = [];
                let tot = 0;
                $.each(result.dataBean.PIE, function (i, o) {
                    chart_option_pie.legend.data.push(PIE_STATUS[o.key]);
                    chart_option_pie.series[0].data.push({"name": PIE_STATUS[o.key], "value": Number(o.value)});
                    tot += Number(o.value);
                });
                $.each(result.aaData, function (i, o) {
                    chart_option_bar.xAxis[0].data.push(o.data1);
                    chart_option_bar.series[0].data.push(o.number1);
                });
                //chart_option_pie.title.show = (result.dataBean && result.dataBean.PIE && result.dataBean.PIE.length > 0 ? false : true);
                chart_option_pie.title.text = (tot > 0 ? "Total: " + tot : "No data");
                chart_pie.setOption(chart_option_pie);
                chart_option_bar.title.show = (result.aaData && result.aaData.length > 0 ? false : true);
                chart_bar.setOption(chart_option_bar);
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                chart_pie.hideLoading();
                chart_bar.hideLoading();
                unblock('block_body');
            }
        });
    }
</script>