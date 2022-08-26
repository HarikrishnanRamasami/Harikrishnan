<%-- 
    Document   : campaignDashboard
    Created on : 9 Oct, 2019, 11:35:06 AM
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
<s:hidden name="oper" id="oper"/>
<s:hidden name="campaignId" id="campaignId"/>
<ul class="act-name current" id="tab_camp_dash">
    <div class="col-md-4 right-pad fullscreen-block">
        <div class="act-period">
            <div class="my-bord ">
                <h3><s:text name="lbl.campaign.head.active.campaigns"/></h3>
                <div class="board-icons">
                    <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="div_chart_activity" data-fs-height-on="350px" data-fs-height-off="250px"></a>
                    <a class="refresh fa fa-refresh fa-2x" href="#" id="btn_reload_chart_activity"></a>
                </div>
            </div>   		  
            <div class="activities-per">
            </div>
            <div class="printer pull-right fs-zoom">
                <div id="div_active_campaigns" style="height: 250px; width: 100%;"></div>
            </div>
        </div>
    </div>
    <div class="col-md-8 right-pad fullscreen-block">
        <div class="act-period">
            <div class="my-bord">
                <h3><s:text name="lbl.campaign.head.transactions.period"/></h3>
                <div class="board-icons">
                    <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="div_chart_activity" data-fs-height-on="350px" data-fs-height-off="250px"></a>
                    <a class="refresh fa fa-refresh fa-2x" href="#" id="btn_reload_transactions"></a>
                </div>
                <div id="date_range" class="custom-date-range">
                    <i class="fa fa-calendar"></i>&nbsp;
                    <span><s:property value="dateRange.getDesc"/></span> <i class="fa fa-caret-down pull-right" ></i>
                    <s:hidden name="dateRange" id="sel_pie_dateRange"/>
                    <s:hidden name="dateRangeValue" id="hid_dateRangeValue"/>
                </div>
            </div>
            <div class="activities-per" style="margin-top: 10px;">

            </div>                                
            <div class="col-md-6">
                <div id="div_campaign_status" style="height: 250px; width: 100%;"></div>
            </div>
            <div class="col-md-6">
                <div id="div_campaign_type" style="height: 250px; width: 100%;"></div>
            </div>
        </div>
    </div>
    <div class="board-icons1" style="margin-top: -80px;">
        <button id="btnRptRsltBack" type="button" class="btn btn-warning tmargin cbtn" onclick="backCampaign();">
            <s:text name="btn.back"/>
        </button>
    </div>
    <div class="col-md-12 right-pad fullscreen-block">
        <div class="dash-leader">
            <div class="my-bord">
                <h3><s:text name="lbl.campaign.head.campaign.performance"/></h3>
                <div class="board-icons">
                    <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="div_chart_activity" data-fs-height-on="350px" data-fs-height-off="250px"></a>
                    <a class="refresh fa fa-refresh fa-2x" href="#" id="btn_reload_performance"></a>
                </div>
                <s:if test='!"txn".equals(oper)'>
                    <div >
                        <s:select class="form-control custom-date-range" name="campId" id="sel_pie_campId" list="campaignList" listKey="key" listValue="value" />
                    </div>
                </s:if>
            </div>
            <div class="activ ities-per"></div>
            <div class="col-md-12">
                <div class="wrapper">
                    <div class="camp-graph">
                        <div class="container-fluid">
                            <div class="row no-gutters"> 

                                <!-- col 01 --->
                                <div class="col-md-2 col-lg-2 col-sm-6">
                                    <div class="panel panel-primary">
                                        <div class="panel-heading">
                                            <h3 class="panel-title"><s:text name="lbl.common.head.sent"/></h3>
                                        </div>
                                        <div class="panel-body">
                                            <div id="div_sent" class="cp-graphs-settings"></div>
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
                                            <div id="div_bounced" class="cp-graphs-settings"></div>
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
                                            <div id="div_opened" class="cp-graphs-settings"></div>
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
                                            <div id="div_clicked" class="cp-graphs-settings"></div>
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
                                            <div id="div_converted" class="cp-graphs-settings"></div>
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
                                            <div id="div_unsubscribed" class="cp-graphs-settings"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12" >
                <div id="div_campaign_graph" style="height: 250px; width: 100%; margin-top: 30px;"></div>
            </div>
        </div>
    </div>
</ul>
<s:form name="camp_das" id="camp_das"/>
<script type="text/javascript">
    var active_campaigns, campaign_type, campaign_sent, campaign_bounced, campaign_opened, campaign_clicked, campaign_converted, campaign_unsubscribed, campaign_graph;
    var DATE_RANGES = {};
    var DATE_RANGES_LIST = {};
    $(document).ready(function () {
        $(function () {
            $('.acti-off-heads a').click(function () {
                var tab_id = $(this).attr('data-tab');

                $('.acti-off-heads a').removeClass('active');
                $('.act-name').removeClass('current');

                $(this).addClass('active');
                $("#" + tab_id).addClass('current');
            });

            $('#sidebar').slimScroll({
                height: '100%',
                width: '100%',
            });
            $('.leads-tab').click(function () {
                $('.popup-wrap').addClass('popup-open');
                $('#overlay').show();

            });
            $('.close-link').click(function () {
                $('.popup-wrap').removeClass('popup-open');
                $('#overlay').hide();
            });
            $('.close-btn').click(function () {
                $('.popup-wrap').removeClass('popup-open');
                $('#overlay').hide();
            });
        });
        $('.fullscreen').click(function () {
            $(this).parents('.fullscreen-block').toggleClass('panel-fullscreen');
            if ($(this).hasClass("fa-expand")) {
                $(this).removeClass("fa-expand").addClass("fa-compress");
                $("#" + $(this).data("fs-id")).css({height: $(this).data("fs-height-on")});
            } else {
                $(this).removeClass("fa-compress").addClass("fa-expand");
                $("#" + $(this).data("fs-id")).css({height: $(this).data("fs-height-off")});
            }
            if ($(this).data("fs-id") === "active_campaigns") {
                setTimeout(function () {
                    active_campaigns.resize();
                }, 100);
            } else if ($(this).data("fs-id") === "campaign_status") {
                setTimeout(function () {
                    campaign_status.resize();
                }, 100);
            } else if ($(this).data("fs-id") === "campaign_graph") {
                setTimeout(function () {
                    campaign_graph.resize();
                }, 100);
            }
        });
    <s:iterator value="dateRangeList">
        Object.assign(DATE_RANGES_LIST, {'<s:property value="key"/>': '<s:property value="value"/>'});
    </s:iterator>
        //Object.assign(DATE_RANGES, DATE_RANGES_LIST);
        var keys = Object.keys(DATE_RANGES_LIST);
        for (i = 0; i < keys.length; i++) {
            if ('CUSTOM' !== keys[i]) {
                DATE_RANGES[DATE_RANGES_LIST[keys[i]]] = [moment().subtract(i, 'days'), moment().subtract(i, 'days')];
            }
        }

        //DATE_RANGES = DATE_RANGES_LIST.reduce((json, o, i) => { console.log(i); json[o[Object.keys(o)[0]]] = [moment().subtract(i, 'days'), moment().subtract(i, 'days')]; return json; }, {});
        setupDateRange();
        var pieData = [], legendData = [];
        active_campaigns = echarts.init(document.getElementById('div_active_campaigns'));
        campaign_status = echarts.init(document.getElementById('div_campaign_status'));
        campaign_type = echarts.init(document.getElementById('div_campaign_type'));
        active_campaigns_option = {
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
            legend: {
                orient: 'vertical',
                y: '17%',
                left: 'right',
                data: legendData,
                show: false
            },
            toolbox: {
                show: true,
                fontSize: '5',
                feature: {
                    mark: {show: true},
                    dataView: {show: false, readOnly: false},
                    magicType: {show: true, type: ['pie', 'funnel'], option: {
                        funnel: {
                            x: '25%',
                            width: '60%',
                            funnelAlign: 'right',
                            max: 1700
                        },
                        textStyle: {
                            fontWeight: 'normal',
                            color: '#666666',
                            fontSize: '12'
                        }
                    }},
                    restore: {show: false},
                    saveAsImage: {show: false, x:'5%'}
                },
                labelLine: {
                    normal: {
                        show: true
                    }
                }
            },
            calculable: true,
            series: [
                {
                    type: 'pie',
                    avoidLabelOverlap: true,
                    center: ["47%", "55%"],
                    radius: ["45%", "65%"],
                    itemStyle : 'labelFromatter',
                    data: pieData,
                    color: ['#44b8e2','#db9bc8','#8ad0f9','#fdc16c','#00a640','#fa5840','#00a640','#cd5c5c','#ba55d3','#ffa500','#40e0d0','#ff69b4','#6495ed','#32cd32','#da70d6','#87cefa','#87cefa','#ff69b4','#c37cca','#8f95e7'],
                    label: {
                        normal:{
                            formatter: "{b}\n{d}%"
                        }
                    },
                    labelLine: {
                        textStyle: {
                        fontSize: '100',
                        fontWeight: 'bold'
                    },
                    normal: {
                        show: true,
                        length: 15,
                        lineStyle: {
                            color: '#dbdbdb'
                        }
                    }
                }
              }
            ],
            textStyle: {
                fontWeight: 'normal',
                fontSize: '11'
            }
        };
        active_campaigns.setOption(active_campaigns_option);
        //campaign_status_option = active_campaigns_option;
        campaign_status_option = {
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
            legend: {
                orient: 'vertical',
                y: '17%',
                left: 'right',
                data: legendData,
                show: false
            },
            toolbox: {
                show: true,
                fontSize: '5',
                feature: {
                    mark: {show: true},
                    dataView: {show: false, readOnly: false},
                    magicType: {show: true, type: ['pie', 'funnel'], option: {
                        funnel: {
                            x: '25%',
                            width: '60%',
                            funnelAlign: 'right',
                            max: 1700
                        },
                        textStyle: {
                            fontWeight: 'normal',
                            color: '#666666',
                            fontSize: '12'
                        }
                    }},
                    restore: {show: false},
                    saveAsImage: {show: false, x:'5%'}
                },
                labelLine: {
                    normal: {
                        show: true
                    }
                }
            },
            calculable: true,
            series: [
                {
                    type: 'pie',
                    avoidLabelOverlap: true,
                    center: ["47%", "55%"],
                    radius: ["45%", "65%"],
                    itemStyle : 'labelFromatter',
                    data: pieData,
                    color: ['#44b8e2','#db9bc8','#8ad0f9','#fdc16c','#00a640','#fa5840','#00a640','#cd5c5c','#ba55d3','#ffa500','#40e0d0','#ff69b4','#6495ed','#32cd32','#da70d6','#87cefa','#87cefa','#ff69b4','#c37cca','#8f95e7'],
                    label: {
                        normal:{
                            formatter: "{b}\n{d}%"
                        }
                    },
                    labelLine: {
                        textStyle: {
                            fontSize: '100',
                            fontWeight: 'bold'
                        },
                        normal:{
                            show: true,
                            length: 15,
                            lineStyle: {
                                color: '#dbdbdb'
                            }
                        }
                    }
                }
            ],
            textStyle: {
                fontWeight: 'normal',
                fontSize: '11'
            }
        };
        campaign_status.setOption(campaign_status_option);
        //campaign_type_option = active_campaigns_option;
         campaign_type_option = {
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
            legend: {
                orient: 'vertical',
                 y: '17%',
                left: 'right',
                data: legendData,
                show: false
            },
            toolbox: {
                show: true,
                fontSize: '5',
                feature: {
                    mark: {show: true},
                    dataView: {show: false, readOnly: false},
                    magicType: {show: true, type: ['pie', 'funnel'], option: {
                        funnel: {
                            x: '25%',
                            width: '60%',
                            funnelAlign: 'right',
                            max: 1700
                        },
                        textStyle: {
                            fontWeight: 'normal',
                            color: '#666666',
                            fontSize: '12'
                        }
                    }},
                    restore: {show: false},
                    saveAsImage: {show: false, x:'5%'}
                },
                labelLine: {
                    normal: {
                        show: true
                    }
                }
            },
            calculable: true,
            series: [
                {
                    type: 'pie',
                     //radius: '75%',
                    //radius : ['40%', '50%'],
                    avoidLabelOverlap: true,
                    center: ["47%", "55%"],
                    radius: ["45%", "65%"],
                    itemStyle : 'labelFromatter',
                    data: pieData,
                    color: ['#44b8e2','#db9bc8','#8ad0f9','#fdc16c','#00a640','#fa5840','#00a640','#cd5c5c','#ba55d3','#ffa500','#40e0d0','#ff69b4','#6495ed','#32cd32','#da70d6','#87cefa','#87cefa','#ff69b4','#c37cca','#8f95e7'],
                    label: {
                        normal:{
                            formatter: "{b}\n{d}%"
                        }
                    },
                    labelLine: {
                        textStyle: {
                            fontSize: '100',
                            fontWeight: 'bold'
                        },
                        normal:{
                            show: true,
                            length: 15,
                            lineStyle: {
                                color: '#dbdbdb'
                            }           
                        }
                    }
                }
            ],
            textStyle: {
                fontWeight: 'normal',
                fontSize: '11'
            }
        };
        campaign_type.setOption(campaign_type_option);
        window.onresize = function () {
            setTimeout(function () {
                active_campaigns.resize();
                campaign_status.resize();
                campaign_type.resize();
            }, 200);
        }
        activeCampaigns();
        setTimeout(function () {
            loadCampaignStatusChart($("#sel_pie_dateRange").val());
        }, 500);
        setTimeout(function () {
            loadCampaignTypeChart($("#sel_pie_dateRange").val());
        }, 500);
        setTimeout(function () {
            loadCampaignGraph($("#sel_pie_campId").val());
        }, 500);
        setTimeout(function () {
            //loadCampaignDataCount($("#sel_pie_campId").val());
        }, 500);
        setTimeout(function () {
            loadCampaignBouncedCount($("#sel_pie_campId").val());
        }, 500);

        $("#btn_reload_transactions").on("click", function () {
            var dateRange = $("#sel_pie_dateRange").val();
            loadCampaignStatusChart(dateRange);
            loadCampaignTypeChart(dateRange);
        });
        $("#btn_reload_performance").on("click", function () {
            var campId = $("#sel_pie_campId").val();
            loadCampaignGraph(campId);
            //loadCampaignDataCount(campId);
            loadCampaignBouncedCount(campId);
        });
        var campaign_graph = echarts.init(document.getElementById('div_campaign_graph'));
        
        campaign_graph_option = {
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


        campaign_graph.setOption(campaign_graph_option);
        window.onresize = function () {
            setTimeout(function () {
                campaign_graph.resize();

            }, 200);
        };
    });

    function activeCampaigns() {
        active_campaigns.showLoading({
            text: 'Please wait!... '
        });
        active_campaigns.clear();
        $.getJSON(APP_CONFIG.context + "/camp/loadActiveCampaignChart.do", function () {
        }).done(function (result) {
            active_campaigns_option.legend.data = [];
            active_campaigns_option.series[0].data = [];
             let tot = 0;
            $.each(result.aaData, function (i, o) {
                active_campaigns_option.legend.data = [];
                active_campaigns_option.legend.data.push();
                active_campaigns_option.series[0].data = [];
                active_campaigns_option.legend.data.push("Email");
                active_campaigns_option.series[0].data.push({"name": "Email", "value": o.key, itemStyle: {normal: {color: '#b1d653'}}});
                active_campaigns_option.legend.data.push("Sms");
                active_campaigns_option.series[0].data.push({"name": "Sms", "value": o.value, itemStyle: {normal: {color: '#44b8e2'}}});
                active_campaigns_option.legend.data.push("Push");
                active_campaigns_option.series[0].data.push({"name": "Push", "value": o.info, itemStyle: {normal: {color: '#db9bc8'}}});
                 tot += Number(o.key);
            });
            active_campaigns_option.title.text = (tot > 0 ? "Total: " + tot : "");
            active_campaigns.setOption(active_campaigns_option);
        }).fail(function () {

        }).always(function () {
            active_campaigns.hideLoading();
        });
    }

    function loadCampaignStatusChart(dateRange) {
        campaign_status.showLoading({
            text: 'Please wait!... '
        });
        campaign_status.clear();
        $.getJSON(APP_CONFIG.context + "/camp/loadCampaignStatusChart.do", {"dateRange": dateRange}, function () {
        }).done(function (result) {
            console.log(result);
            campaign_status_option.legend.data = [];
            campaign_status_option.series[0].data = [];
             let tot = 0;
            $.each(result.aaData, function (i, o) {
                campaign_status_option.legend.data = [];
                campaign_status_option.legend.data.push();
                campaign_status_option.series[0].data = [];
                campaign_status_option.legend.data.push("Running");
                campaign_status_option.series[0].data.push({"name": "Running", "value": o.key, itemStyle: {normal: {color: '#566c7b'}}});
                campaign_status_option.legend.data.push("Completed");
                campaign_status_option.series[0].data.push({"name": "Completed", "value": o.value, itemStyle: {normal: {color: '#009688'}}});
                campaign_status_option.legend.data.push("NoData");
                campaign_status_option.series[0].data.push({"name": "NoData", "value": o.info, itemStyle: {normal: {color: '#ff9800'}}});
                campaign_status_option.legend.data.push("Waiting");
                campaign_status_option.series[0].data.push({"name": "Waiting", "value": o.info1, itemStyle: {normal: {color: '#ff5722'}}});
                 tot += Number(o.value);
            });
             campaign_status_option.title.text = (tot > 0 ? "Total: " + tot : "No data");
            campaign_status.setOption(campaign_status_option);
        }).fail(function () {

        }).always(function () {
            campaign_status.hideLoading();
        });
    }
    function loadCampaignTypeChart(dateRange) {
        campaign_type.showLoading({
            text: 'Please wait!... '
        });
        campaign_type.clear();
        $.getJSON(APP_CONFIG.context + "/camp/loadCampaignTypeChart.do", {"dateRange": dateRange}, function () {
        }).done(function (result) {
            campaign_type_option.legend.data = [];
            campaign_type_option.series[0].data = [];
             let tot = 0;
            $.each(result.aaData, function (i, o) {
                campaign_type_option.legend.data = [];
                campaign_type_option.legend.data.push();
                campaign_type_option.series[0].data = [];
                campaign_type_option.legend.data.push("EMAILS");
                campaign_type_option.series[0].data.push({"name": "EMAILS", "value": o.key, itemStyle: {normal: {color: '#009688'}}});
                campaign_type_option.legend.data.push("SMS");
                campaign_type_option.series[0].data.push({"name": "SMS", "value": o.value, itemStyle: {normal: {color: '#61a0a8'}}});
                campaign_type_option.legend.data.push("PUSH");
                campaign_type_option.series[0].data.push({"name": "PUSH", "value": o.info, itemStyle: {normal: {color: '#d48265'}}});
                  tot += Number(o.key);
            });
             campaign_type_option.title.text = (tot > 0 ? "Total: " + tot : "No data");
            campaign_type.setOption(campaign_type_option);
        }).fail(function () {

        }).always(function () {
            campaign_type.hideLoading();
        });
    }
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
            xAxisData.push(o.key === null ? "0" : o.key);
            openedCnt.push(Number(o.value === null ? "0" : o.value));
            clickedCnt.push(Number(o.info === null ? "0" : o.info));
            convertedCnt.push(Number(o.info1 === null ? "0" : o.info1));

        });
        var campaign_graph = echarts.init(document.getElementById('div_campaign_graph'));
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


    campaign_sent = echarts.init(document.getElementById('div_sent'));
    campaign_opened = echarts.init(document.getElementById('div_opened'));
    campaign_bounced = echarts.init(document.getElementById('div_bounced'));
    campaign_clicked = echarts.init(document.getElementById('div_clicked'));
    campaign_converted = echarts.init(document.getElementById('div_converted'));
    campaign_unsubscribed = echarts.init(document.getElementById('div_unsubscribed'));



    function backCampaign() {
        $("#camp_das").attr("action", "<%=request.getContextPath()%>/camp/openCampaignPage.do?campaignTab=campaign");
        $("#camp_das").submit()
    }

</script>
