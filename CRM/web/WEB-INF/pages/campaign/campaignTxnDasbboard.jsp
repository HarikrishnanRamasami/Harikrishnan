<%-- 
    Document   : campaignTxnDasbboard
    Created on : 21 Oct, 2019, 11:27:57 AM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/campaignDashboard.js"></script>
<style type="text/css">
    .home-card {
        min-height: 370px;
    }
    .no-data:after {
        content: "No data available";
        color: rgba(30, 127, 210, 0.62);
    }
    .list-group {
        word-break:break-all;
    }
    #tbl_reminder_filter.dataTables_filter input {
        width: 100% !important;
    }
</style>
<s:hidden name="mcCampId" id="mcCampId"/>
<ul class="act-name current" id="tab_camp_dash">
    <div class="board-icons1" style="margin-top: -60px;">
        <button id="btnRptRsltBack" type="button" class="btn btn-warning tmargin cbtn" onclick="backCampaign();">
            <s:text name="btn.back="/>
        </button>
    </div>
    <div class="col-md-12 right-pad fullscreen-block">
        <div class="dash-leader">
            <div class="my-bord">
                <h3><s:text name="lbl.campaign.head.campaign.performance"/></h3>
            </div>
            <div class="activities-per"></div>
            <div class="col-md-12">
                <div class="wrapper">
                    <div class="camp-graph">
                        <div class="container-fluid">
                            <div class="row no-gutters"> 

                                <!-- col 01 --->
                                <div class="col-md-2 col-lg-2 col-sm-6">
                                    <div class="panel panel-primary">
                                        <div class="panel-heading">
                                            <h3 class="panel-title">Sent</h3>
                                        </div>
                                        <div class="panel-body">
                                            <div id="div_txn_dash_sent" class="cp-graphs-settings"></div>
                                        </div>
                                    </div>
                                </div>
                                <!-- --> 
                                <!-- col 02 --->
                                <div class="col-md-2 col-lg-2 col-sm-6">
                                    <div class="panel panel-primary">
                                        <div class="panel-heading">
                                            <h3 class="panel-title"><s:text name="lbl.common.head.bounced"/></h3>
                                        </div>
                                        <div class="panel-body">
                                            <div id="div_txn_dash_bounced" class="cp-graphs-settings"></div>
                                        </div>
                                    </div>
                                </div>

                                <!-- col 03 --->
                                <div class="col-md-2 col-lg-2 col-sm-6">
                                    <div class="panel panel-primary">
                                        <div class="panel-heading">
                                            <h3 class="panel-title"><s:text name="lbl.common.head.opens"/></h3>
                                        </div>
                                        <div class="panel-body">
                                            <div id="div_txn_dash_opened" class="cp-graphs-settings"></div>
                                        </div>
                                    </div>
                                </div>

                                <!-- col 04 --->
                                <div class="col-md-2 col-lg-2 col-sm-6">
                                    <div class="panel panel-primary">
                                        <div class="panel-heading">
                                            <h3 class="panel-title"><s:text name="lbl.common.head.clicks"/></h3>
                                        </div>
                                        <div class="panel-body">
                                            <div id="div_txn_dash_clicked" class="cp-graphs-settings"></div>
                                        </div>
                                    </div>
                                </div>

                                <!-- col 05 --->
                                <div class="col-md-2 col-lg-2 col-sm-6">
                                    <div class="panel panel-primary">
                                        <div class="panel-heading">
                                            <h3 class="panel-title"><s:text name="lbl.common.head.convertions"/></h3>
                                        </div>
                                        <div class="panel-body">
                                            <div id="div_txn_dash_converted" class="cp-graphs-settings"></div>
                                        </div>
                                    </div>
                                </div>

                                <!-- col 06 --->
                                <div class="col-md-2 col-lg-2 col-sm-6">
                                    <div class="panel panel-primary">
                                        <div class="panel-heading">
                                            <h3 class="panel-title"><s:text name="lbl.common.head.unsubscriptions"/></h3>
                                        </div>
                                        <div class="panel-body">
                                            <div id="div_txn_dash_unsubscribed" class="cp-graphs-settings"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12" >
                <div id="div_campaign_txn_graph" style="height: 250px; width: 100%; margin-top: 30px;"></div>
            </div>
        </div>
    </div>
</ul>
<s:form name="camp_das" id="camp_das"/>
<script type="text/javascript">
    var campaign_dash_sent, campaign_sent, campaign_bounced, campaign_opened, campaign_clicked, campaign_converted, campaign_unsubscribed, campaign_dash_graph;

    $(document).ready(function () {
        
    campaign_sent = echarts.init(document.getElementById('div_txn_dash_sent'));
    campaign_opened = echarts.init(document.getElementById('div_txn_dash_opened'));
    campaign_bounced = echarts.init(document.getElementById('div_txn_dash_bounced'));
    campaign_clicked = echarts.init(document.getElementById('div_txn_dash_clicked'));
    campaign_converted = echarts.init(document.getElementById('div_txn_dash_converted'));
    campaign_unsubscribed = echarts.init(document.getElementById('div_txn_dash_unsubscribed'));
    campaign_dash_graph = echarts.init(document.getElementById('div_campaign_txn_graph'));

        loadCampaignBouncedCount($("#mcCampId").val());
        setTimeout(function () {
            loadCampaignGraph($("#mcCampId").val());
        }, 500);

        var campaign_dash_graph = echarts.init(document.getElementById('div_campaign_txn_graph'));

        campaign_dash_graph_option = {
            title: {
                show: true,
                textStyle: {
                    color: 'grey',
                    fontSize: 20
                },
                left: 'center',
                top: 'top'
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
                data: ['opened', 'clicked', 'c']
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
                    axisLabel: {
                        formatter: function (value) {
                            return echarts.format.formatTime('DD-MM-YYYY', value);
                        }
                    },
                    data: [],
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisLabel: {
                        formatter: function (value) {
                            return echarts.format.formatTime('DD-MM-YYYY', value);
                        }
                    },
                }
            ],
            series: [
                {
                    name: 'opened',
                    type: 'line',
                    barWidth: '30%',
                    data: []
                },
                {
                    name: 'clicked',
                    type: 'line',
                    barWidth: '30%',
                    data: []
                },
                {
                    name: 'converted',
                    type: 'line',
                    barWidth: '30%',
                    data: []
                }
            ]
        };


        campaign_dash_graph.setOption(campaign_dash_graph_option);
        window.onresize = function () {
            setTimeout(function () {
                campaign_dash_graph.resize();

            }, 200);
        };
    });

    function openCampaignPage() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/openCampaignPage.do",
            data: {},
            success: function (result) {
                $("#camp_details").html(result).show();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }
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
            for (i = 0; i < keys.length; i++) {
                if (label === DATE_RANGES_LIST[keys[i]]) {
                    $('#sel_pie_dateRange').val(keys[i]);
                    break;
                }
            }
        });
    }

    function loadCampaignGraph(mcCampId) {
        /* campaign_graph.showLoading({
         text: 'Please wait!... '
         });*/
        //campaign_type.clear();
        $.getJSON(APP_CONFIG.context + "/camp/loadCampaignGraphData.do", {"mcCampId": mcCampId}, function () {
        }).done(function (result) {
            loadGraphChart(result)
        }).fail(function () {

        }).always(function () {
            //campaign_graph.hideLoading();
        });
    }



    function loadGraphChart(result) {
        var xAxisData = [], openedCnt = [], clickedCnt = [], convertedCnt = [];
        $.each(result.aaData, function (i, o) {
            xAxisData.push(o.key);
            openedCnt.push(Number(o.value));
            clickedCnt.push(Number(o.info));
            convertedCnt.push(Number(o.info1));

        });
        var campaign_graph = echarts.init(document.getElementById('div_campaign_txn_graph'));
        var campaign_graph_options = {
            title: {
                text: ''/*,
                 subtext: ''*/
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                orient: 'horizontal',
                x: 'left',
                y: 'top',
                fontSize: 100,
                data: [
                    'opened', 'clicked', 'converted'
                ],
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: false},
                    dataView: {show: false, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: false},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            //grid: {y: 70, y2: 30, x2: 20},
            xAxis: [
                {
                    type: 'category',
                    axisLine: {show: false},
                    axisTick: {show: false},
                    axisLabel: {show: true},
                    splitArea: {show: false},
                    splitLine: {show: false},
                    data: xAxisData
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    //splitArea : {show : true},
                    axisLabel: {formatter: '{value}'}
                }
            ],
            series: [
                {
                    name: 'opened',
                    type: 'line',
                    itemStyle: {normal: {color: '#4A66A0', label: {show: true, formatter: function (p) {
                                    return p.value > 0 ? (p.value + '\n') : '';
                                }}}},
                    data: openedCnt
                },
                {
                    name: 'clicked',
                    type: 'line',
                    itemStyle: {normal: {color: '#F44336', label: {show: true, formatter: function (p) {
                                    return p.value > 0 ? (p.value + '\n') : '';
                                }}}},
                    data: clickedCnt
                },
                {
                    name: 'converted',
                    type: 'line',
                    itemStyle: {normal: {color: '#25e457', label: {show: true, formatter: function (p) {
                                    return p.value > 0 ? (p.value + '\n') : '';
                                }}}},
                    data: convertedCnt
                }
            ]
        };
        campaign_graph.setOption(campaign_graph_options);
        window.onresize = function () {
            setTimeout(function () {
                campaign_graph.resize();
            }, 200);
        }

        setTimeout(function () {
            campaign_graph.resize();
        }, 1000);
    }

    function backCampaign() {
        $('#block_camp').show();
        $('#block_txn_dashboard').empty().hide();
    }

</script>
