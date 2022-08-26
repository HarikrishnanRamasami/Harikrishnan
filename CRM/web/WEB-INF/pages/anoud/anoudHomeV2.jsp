<%-- 
    Document   : anoudHomeV2
    Created on : 20 Oct, 2017, 3:28:35 PM
    Author     : thoufeak.rahman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
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
       /* width: 100% !important;*/
    }
</style>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-6 right-pad fullscreen-block">
            <div class="act-period">
                <div class="my-bord ">
                    <h3><s:text name="lbl.activities.for.period"/></h3>
                    <div class="board-icons">
                        <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="div_chart_activity" ></a>
                    </div>
                </div>   		  
                <div class="activ ities-per">
                    <ul class="period-list">
                        <li style="width:45%"><%--label>User </label--%> 
                            <s:select class="form-control" name="userId" id="sel_pie_userId" list="userList" listKey="key" listValue="value"/>
                        </li>
                        <li style="width:45%"><%--label>Range </label--%>
                            <!--s:select class="form-control" name="dateRange" id="sel_pie_dateRange" list="dateRangeList" listKey="key" listValue="value"/-->
                            <div id="date_range" class="form-control cal-dp-down">
                                <span><s:property value="dateRange.getDesc"/></span>
                                <s:hidden name="dateRange" id="sel_pie_dateRange"/>
                                <s:hidden name="dateRangeValue" id="hid_dateRangeValue"/>
                            </div>
                            <div class="clearfix"></div>
                        </li>
                        <li style="width:10%"><a class=" fa-2x btn_reload_chart_activity" href="#" id="btn_reload_chart_activity"><img src="plugins/innovate/images/ico_refresh_grey.svg" height="24" alt="Reload data" ></a></li>
                    </ul>
                </div>
                <div class="printer pull-right ">
                    <div id="div_chart_activity" style="height: 290px; width: 100%;"></div>
                </div>
            </div>
        </div>
        <div class="col-md-6  right-pad fullscreen-block">
            <div class="my-tasks">
                <div class="my-bord">
                    <h3><s:text name="lbl.my.tasks"/></h3>
                    <div class="board-icons">
                        <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="div_chart_task" ></a>
                    </div>
                </div>	
                <div class="dash-task">
                    <div class="body">
                        <div class="row clearfix top_search">
                            <div class="col-lg-12 col-md-12 ">
                                <div id="div_chart_task" style="height: 290px; width: 100%; margin-top: 30px;"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="clearfix"></div> 
<div class="container-fluid">
    <div class="row">
        <div class="col-md-6 right-pad fullscreen-block">
            <div class="act-period">
                <div class="my-bord">
                    <h3><s:text name="lbl.calllog"/></h3>
                    <div class="board-icons">
                        <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="div_chart_call_log" ></a>
                    </div>
                </div> 
                <div class="body">
                    <div class="printer pull-right">
                        <div id="div_chart_call_log" style="height: 290px; width: 100%; margin-top:25px;"></div>
                    </div>  	
                </div>
            </div>
        </div>
        <div class="col-md-6 right-pad fullscreen-block">
            <div class="dash-leader">
                <div class="my-bord">
                    <h3><s:text name="lbl.leader.board"/></h3>
                    <div class="board-icons">
                        <%--div class="dropdown" id="canvas-dropdown">
                            <a class="menudot" href="#" data-toggle="dropdown" class="btn-default dropdown-toggle" onclick="leaderBoardDateFilter('<s:property value="key" />')"> </a>
                            <ul class="dropdown-content1">
                                <s:iterator value="dateRangeList">
                                    <li><a href="javascript:void(0);" onclick="javascript:leaderBoardDateFilter('<s:property value="key" />');" data-range="<s:property value="key" />" class="date-range waves-effect waves-block"><s:property value="value" /></a></li>
                                </s:iterator>
                            </ul>
                        </div--%>
                        <a href="#" class="view-btn" id="btn_leader_board_more" style="width: 53px; height: 29px; <s:if test="leaderBoardList != null && leaderBoardList.size() <= 5">display: none;</s:if>"></a>
                            <a class="fullscreen fa fa-expand fa-2x" href="javascript:void(0);"></a>
                        </div>
                    </div>
                    <div class="call-details">
                        <table class="table table-striped table-bordered display dataTable dtr-inline" id="tbl_leader_board">
                            <thead>
                                <tr>
                                    <th><s:text name="lbl.name"/></th>
                                    <th><s:text name="lbl.attend.call"/></th>
                                    <th><s:text name="lbl.missed.call"/></th>
                                </tr>
                            </thead>
                            <tbody>
                            <s:if test="leaderBoardList != null && leaderBoardList.size() > 0">
                                <s:iterator value="leaderBoardList" status="row">
                                    <s:if test="#row.index < 5">
                                        <tr>
                                            <td><s:property value="key" /></td>
                                            <td><s:property value="value" /></td>
                                            <td><s:property value="info1" /></td>
                                        </tr>
                                    </s:if>
                                </s:iterator>
                            </s:if>
                            <s:else>
                                <tr><td colspan="3"><div class="no-data"></div></td></tr>
                            </s:else>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%--div class="col-md-8 right-pad">
            <div class="dash-leads">
                <div class="my-bord">
                    <h3>Leads</h3>
                    <div class="board-icons">
                        <a class="fullscreen" href="#"></a>
                    </div>
                </div>
                <div class="leads-tab">
                    <table class="table table-striped table-bordered display dataTable dtr-inline">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Mobile</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:if test="leadsList != null && leadsList.size() > 0">
                                <s:iterator value="leadsList" status="row">
                                    <s:if test="#row.index < 5">
                                        <tr>
                                            <td><s:property value="key" /></td>
                                            <td><s:property value="value" /></td>
                                            <td><s:property value="info1" /></td>
                                        </tr>
                                    </s:if>
                                </s:iterator>
                            </s:if>
                            <s:else>
                                <tr><td colspan="3"><div class="no-data"></div></td></tr>
                            </s:else>
                        </tbody>
                    </table>
                </div>
                <s:if test="leadsList != null && leadsList.size() > 5">
                    <a href="#" class="view-btn mbtn" id="btn_task_more"id="btn_leads_more">More</a>
                </s:if>
            </div>
        </div--%> 
    </div>
</div>
<div class="clearfix"></div>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-6 right-pad">
            <div class="my-reminder">
                <div class="my-bord">
                    <h3><s:text name="lbl.my.task.reminder"/></h3>
                    <div class="board-icons">
                        <a class="fullscreen" href="#"></a>
                    </div>
                </div>
                <div class="reminder-alert">
                    <table class="table table-striped table-bordered display dataTable dtr-inline" id="tbl_reminder">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th><s:text name="lbl.reminder"/></th>
                                <th><s:text name="lbl.due.on"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
              <!--  <a href="#" class="view-btn mbtn" id="btn_reminder_more" <s:if test="reminderList != null && reminderList.size() <= 5">style="display: none;"</s:if>>More</a> -->
                </div>
            </div>
            <div class="col-md-6 right-pad">
                <div class="clearfix"></div>
                <div class="dash-leads-sec">
                    <h3><s:text name="lbl.recent.activities"/><span class="pull-right"></span></h3>
                    <div class="leads-tab">
                        <table class="table table-striped table-bordered display dataTable dtr-inline" id="tbl_activity">
                            <thead>
                                <tr>
                                    <th class="col-md-3"><s:text name="lbl.type"/></th>
                                    <th class="col-md-7"><s:text name="lbl.activities"/></th>
                                    <th class="col-md-2"><s:text name="lbl.time"/></th>
                                </tr>
                            </thead>
                            <tbody>
                            <s:if test="recentActivityList != null && recentActivityList.size() > 0">
                                <s:iterator value="recentActivityList" status="row">
                                    <s:if test="#row.index < 5">
                                        <tr>
                                            <td><s:property value="key"/></td>
                                            <td class="list-group"><s:property value="value"/></td>
                                            <td class="timeago"><s:property value="info1"/></td>
                                        </tr>
                                    </s:if>
                                </s:iterator>
                            </s:if>
                            <s:else>
                                <tr><td colspan="3"><div class="no-data"></div></td></tr>
                            </s:else>
                        </tbody>
                    </table>
                </div>
                <s:if test="recentActivityList != null && recentActivityList.size() > 5">
                    <a href="#" class="view-btn mbtn" id="btn_activity_more"><s:text name="lbl.more"/></a>
                </s:if>
            </div>
        </div>     
    </div>
</div>
<div class="clearfix"></div>
<br /><br /><br />

<script type="text/javascript">
    var chart_activity, chart_activity_option, chart_task, chart_task_option, chart_call_log, chart_call_log_option;
    var DATE_RANGES = {};
    var DATE_RANGES_LIST = {};
    
    $(document).ready(function() {
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
        $('.fullscreen').click(function() {
            $(this).parents('.fullscreen-block').toggleClass('panel-fullscreen');
            if ($(this).hasClass("fa-expand")) {
                $(this).removeClass("fa-expand").addClass("fa-compress");
                $("#" + $(this).data("fs-id")).css({height: $(this).data("fs-height-on")});
            } else {
                $(this).removeClass("fa-compress").addClass("fa-expand");
                $("#" + $(this).data("fs-id")).css({height: $(this).data("fs-height-off")});
            }
            if ($(this).data("fs-id") === "div_chart_activity") {
                setTimeout(function() {
                    chart_activity.resize();
                }, 100);
            } else if ($(this).data("fs-id") === "div_chart_task") {
                setTimeout(function() {
                    chart_task.resize();
                }, 100);
            } else if ($(this).data("fs-id") === "div_chart_call_log") {
                setTimeout(function() {
                    chart_call_log.resize();
                }, 100);
            }
        });
        $("#opportunities").hide();
        $("#revenue").hide();
        var pieData = [], legendData = [];
        chart_activity = echarts.init(document.getElementById('div_chart_activity'));
        chart_task = echarts.init(document.getElementById('div_chart_task'));
        chart_call_log = echarts.init(document.getElementById('div_chart_call_log'));
        chart_activity_option = {
            title: {
                text: "No data",
                show: true,
                zlevel: 50,
                x: 'center',
                y: '45%',
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
                //x: 'left',
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
                            width: '10%',
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
                    center: ["50%", "50%"],
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
                        lineStyle:{
                            color: '#dbdbdb'
                        }
                    }
                }
              }
            ],
             textStyle: {
                fontWeight: 'normal',
                fontSize: '10'
            }
        };
        
        chart_task_option = {
            title: {
                text: "No data ",
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
                orient: 'vertical',
                //x: 'left',
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
                    center: ["50%", "50%"],
                    radius: ["45%", "65%"],
                    itemStyle : 'labelFromatter',
                    data: pieData,
                    color: ['#44b8e2','#db9bc8','#8ad0f9','#fdc16c','#b1d653','#fa5840','#00a640','#cd5c5c','#ba55d3','#ffa500','#40e0d0','#ff69b4','#6495ed','#32cd32','#da70d6','#87cefa','#87cefa','#ff69b4','#c37cca','#8f95e7'],
                    label: {
                        normal:{
                            formatter: "{b}\n{d}%"
                           
                        }
                    },
                    labelLine: {
                        textStyle: {
                            fontSize: '50',
                            fontWeight: 'normal'
                           
                        },
                        normal:{
                            show: true,
                            length: 8,
                            lineStyle:{
                                color: '#dbdbdb'
                            }
                        }
                    }
                }
            ],
            textStyle: {
                fontWeight: 'normal',
                fontSize: '10'
            }
        };
        
        chart_call_log_option = {
            title: {
                text: "No data ",
                show: true,
                zlevel: 20,
                x: 'center',
                y: '40%',
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
                //x: 'left',
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
                    center: ["50%", "45%"],
                    radius: ["45%", "65%"],
                    itemStyle : 'labelFromatter',
                    data: pieData,
                    color: ['#44b8e2','#db9bc8','#8ad0f9','#fdc16c','#b1d653','#fa5840','#00a640','#cd5c5c','#ba55d3','#ffa500','#40e0d0','#ff69b4','#6495ed','#32cd32','#da70d6','#87cefa','#87cefa','#ff69b4','#c37cca','#8f95e7'],
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
                        lineStyle:{
                            color: '#dbdbdb'
                        }
                    }
                }
              }
            ],
            textStyle: {
                fontWeight: 'normal',
                fontSize: '10'
            }
        };
        chart_activity.setOption(chart_activity_option);
        chart_task.setOption(chart_task_option);
        chart_call_log.setOption(chart_call_log_option);
        window.onresize = function() {
            setTimeout(function() {
                chart_activity.resize();
                chart_task.resize();
                chart_call_log.resize();
            }, 200);
        };
        $("#btn_task_more").on("click", function() {
            window.location.href = "<%=request.getContextPath()%>/openTaskEntryPage.do";
        });
        $("#btn_activity_more").on("click", function() {
            window.location.href = "<%=request.getContextPath()%>/openActivitiesEntryPage.do";
        });
        $("#btn_opportunity_more").on("click", function() {
            window.location.href = "<%=request.getContextPath()%>/openOpportunitiesEntryPage.do";
        });
        $("#btn_leads_more").on("click", function() {
            window.location.href = "<%=request.getContextPath()%>/openLeadsEntryPage.do";
        });
        $("#btn_reload_chart_activity").on("click", function() {
            var userId = $("#sel_pie_userId").val();
            var dateRange = $("#sel_pie_dateRange").val();
            var dateRangeValue = $("#hid_dateRangeValue").val();
            activitiesFilter(userId, dateRange, dateRangeValue);
            myTaskFilter(userId, dateRange, dateRangeValue);
            calllogFilter(userId, dateRange, dateRangeValue);
            leaderBoardDateFilter("", dateRange, dateRangeValue);
            myRemaindersFilter(userId, "");
            myRecentActivitiesFilter(userId);
            opportunityFilter(userId, "");
            revenueFilter("", dateRange, dateRangeValue);
            leadsDateFilter("", dateRange, dateRangeValue);
        });
        $(".date-range").on("click", function() {
            $(this).parent().parent().data("range", $(this).data("range"));
        });
        $(".status-by").on("click", function() {
            $(this).parent().parent().data("status", $(this).data("status"));
        });
        $("#btn_leader_board_more").on("click", function() {
            leaderBoardDateFilter("ALL", $("#sel_pie_dateRange").val(), $("#hid_dateRangeValue").val());
        });
        $("#btn_reminder_more").on("click", function() {
            myRemaindersFilter($("#sel_pie_userId").val(), 'ALL');
        });
        
        $("#btn_reload_chart_activity").trigger("click");
    });
    function activitiesFilter(userId, dateRange, dateRangeValue) {
        chart_activity.showLoading({
            text: 'Please wait!... '
        });
        chart_activity.clear();
        $.getJSON(APP_CONFIG.context + "/loadActivityChartData.do", {"userId": userId, "dateRange": dateRange, "dateRangeValue": dateRangeValue}, function() {
        }).done(function(result) {
            chart_activity_option.legend.data = [];
            chart_activity_option.series[0].data = [];
            let tot = 0;
            $.each(result.aaData, function(i, o) {
                chart_activity_option.legend.data.push(o.key);
                chart_activity_option.series[0].data.push({"name": o.key, "value": Number(o.value)});
                tot += Number(o.value);
            });
            chart_activity_option.title.text = (tot > 0 ? "Total: " + tot : "No data");
            chart_activity.setOption(chart_activity_option);
        }).fail(function() {

        }).always(function() {
            chart_activity.hideLoading();
        });
    }
    function calllogFilter(userId, dateRange, dateRangeValue) {
        chart_call_log.showLoading({
            text: 'Please wait!... '
        });
        chart_call_log.clear();
        $.getJSON(APP_CONFIG.context + "/loadCallLogDetails.do", {"userId": userId, "dateRange": dateRange, "dateRangeValue": dateRangeValue}, function() {
        }).done(function(result) {
            chart_call_log_option.legend.data = [];
            chart_call_log_option.series[0].data = [];
            let tot = 0;
            $.each(result.aaData, function(i, o) {
                chart_call_log_option.legend.data.push(o.cclCrmTypeDesc);
                chart_call_log_option.series[0].data.push({"name": o.cclCrmTypeDesc, "value": Number(o.cclCallRefId)});
                if (o.cclCrmType === '008') {
                    chart_call_log_option.legend.data.push(o.cclCrmTypeDesc + " - Answered");
                    chart_call_log_option.series[0].data.push({"name": o.cclCrmTypeDesc + " - Answered", "value": Number(o.cclNotAnswered)});
                }
                tot += Number(o.cclCallRefId);
                //chart_call_log_option.legend.data.push(o.cclCrmTypeDesc);
                //chart_call_log_option.series[0].data.push({"name": o.cclCrmTypeDesc , "value": Number(o.cclNotAnswered),"value": Number(o.cclNotAnswered)});
            });
            chart_call_log_option.title.text = (tot > 0 ? "Total: " + tot : "No data");
            chart_call_log.setOption(chart_call_log_option);
        }).fail(function() {

        }).always(function() {
            chart_call_log.hideLoading();
        });
    }
    function myTaskFilter(userId, dateRange, dateRangeValue) {
        chart_task.showLoading({
            text: 'Please wait!... '
        });
        chart_task.clear();
        $.getJSON(APP_CONFIG.context + "/loadMyTaskFilterData.do", {"userId": userId, "dateRange": dateRange, "dateRangeValue": dateRangeValue}, function() {
        }).done(function(result) {
            chart_task_option.legend.data = [];
            chart_task_option.series[0].data = [];
            let tot = 0;
            $.each(result.aaData, function(i, o) {
                chart_task_option.legend.data.push(o.key);
                chart_task_option.series[0].data.push({"name": o.key, "value": Number(o.value)});
                tot += Number(o.value);
            });
            chart_task_option.title.text = (tot > 0 ? "Total: " + tot : "No data");
            chart_task.setOption(chart_task_option);
        }).fail(function() {

        }).always(function() {
            chart_task.hideLoading();
        });
    }

    function myRemaindersFilter(userId, fetch) {
        $.ajax({
            type: "POST",
            data: {"userId": userId, "flex1": fetch},
            url: APP_CONFIG.context + "/loadReminderFilterData.do",
            success: function(result) {
                reloadDataTable("#tbl_reminder", result.aaData);
                /* var data = "";
                 $.each(result.aaData, function(i, o) {
                 if (i > 5)
                 return false;
                 data += ("<tr><td><a href=\"javascript:viewTasks('" + o.key + "');\">" + o.key + "</a></td><td>" + o.value + "</td><td data-timeago=\"" + o.info1 + "\">" + o.info1 + "</td></tr>");
                 });
                 if (result.aaData.length > 5) {
                 $("#btn_reminder_more").show();
                 } else {
                 $("#btn_reminder_more").hide();
                 }
                 if (data === "") {
                 data = "<tr><td colspan=\"3\"><div class=\"no-data\"></div></td></tr>";
                 }
                 if (fetch === "ALL") {
                 viewMore("tbl_reminder", data, "My Task Reminders");
                 } else {
                 $("#tbl_reminder > tbody").html(data);
                 }
                 TIMEAGO.init();
                 delete data;*/
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
            }
        });
        return false;
    }

    function myRecentActivitiesFilter(userId) {
        $.ajax({
            type: "POST",
            data: {"userId": userId},
            url: APP_CONFIG.context + "/loadMyRecentFilterData.do",
            success: function(result) {
                var data = "";
                $.each(result.aaData, function(i, o) {
                    data += ("<tr><td>" + o.key + "</td><td>" + o.value + "</td><td>" + o.info1 + "</td></tr>");
                });
                if (data === "") {
                    data = "<tr><td colspan=\"3\"><div class=\"no-data\"></div></td></tr>";
                }
                $("#tbl_activity > tbody").html(data);
                delete data;
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
            }
        });
        return false;
    }

    function opportunityFilter(userId, fetch) {
        fetch = fetch ? fetch : "";
        $.ajax({
            type: "POST",
            data: {"userId": userId, "flex1": fetch},
            url: APP_CONFIG.context + "/loadOpportunityFilterData.do",
            success: function(result) {
                var data = "";
                $.each(result.aaData, function(i, o) {
                    data += ("<tr><td>" + o.key + "</td><td>" + o.value + "</td><td>" + o.info1 + "</td><td>" + o.info2 + "</td></tr>");
                });
                if (data === "") {
                    data = "<tr><td colspan=\"3\"><div class=\"no-data\"></div></td></tr>";
                }
                if (fetch === "ALL") {
                    viewMore("tbl_opportunity", data, "Opportunity");
                } else {
                    $("#tbl_opportunity > tbody").html(data);
                }
                delete data;
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
            }
        });
    }

    function revenueFilter(fetch, dateRange, dateRangeValue) {
        fetch = fetch ? fetch : "";
        $.ajax({
            type: "POST",
            data: {"userId": $("#sel_pie_userId").val(), "dateRange": dateRange, "dateRangeValue": dateRangeValue, "flex1": fetch},
            url: APP_CONFIG.context + "/loadRevenueFilterData.do",
            success: function(result) {
                var data = "";
                $.each(result.aaData, function(i, o) {
                    data += ("<tr><td>" + o.key + "</td><td>" + o.value + "</td></tr>");
                });
                if (data === "") {
                    data = "<tr><td colspan=\"2\"><div class=\"no-data\"></div></td></tr>";
                }
                if (fetch === "ALL") {
                    viewMore("tbl_revenue", data, "Revenue");
                } else {
                    $("#tbl_revenue > tbody").html(data);
                }
                delete data;
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
            }
        });
    }

    function leaderBoardDateFilter(fetch, dateRange, dateRangeValue) {
        fetch = fetch ? fetch : "";
        block('block_body');
        $.ajax({
            type: "POST",
            data: {"dateRange": dateRange, "flex1": fetch, "dateRangeValue": dateRangeValue},
            url: APP_CONFIG.context + "/loadLeaderBoardFilterData.do",
            success: function(result) {
                var data = "";
                $.each(result.aaData, function(i, o) {
                    if (i >= 5 && fetch !== "ALL") {
                        return false;
                    }
                    data += ("<tr><td>" + o.key + "</td><td>" + o.value + "</td><td>" + o.info1 + "</td></tr>");
                });
                if (data === "") {
                    data = "<tr><td colspan=\"3\"><div class=\"no-data\"></div></td></tr>";
                }
                if (fetch === "ALL") {
                    viewMore("tbl_leader_board", data, "Leader Board");
                } else {
                    $("#tbl_leader_board > tbody").html(data);
                    if (result.aaData.length > 5) {
                        $("#btn_leader_board_more").show();
                    } else {
                        $("#btn_leader_board_more").hide();
                    }
                }
                delete data;
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
                unblock('block_body');
            }
        });
    }

    function leadsDateFilter(fetch, dateRange, dateRangeValue) {
        fetch = fetch ? fetch : "";
        $.ajax({
            type: "POST",
            data: {"userId": $("#sel_pie_userId").val(), "dateRange": dateRange, "dateRangeValue": dateRangeValue, "flex1": fetch},
            url: APP_CONFIG.context + "/loadLeadsFilterData.do",
            success: function(result) {
                var data = "";
                $.each(result.aaData, function(i, o) {
                    data += ("<tr><td>" + o.key + "</td><td>" + o.value + "</td><td>" + o.info1 + "</td></tr>");
                });
                if (data === "") {
                    data = "<tr><td colspan=\"3\"><div class=\"no-data\"></div></td></tr>";
                }
                if (fetch === "ALL") {
                    viewMore("tbl_leads", data, "Leads");
                } else {
                    $("#tbl_leads > tbody").html(data);
                }
                delete data;
                        },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
            }
        });
    }

    function viewMore(tblId, data, title) {
        $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm").addClass("modal-mm");
        var clone_tbl = $("#" + tblId).clone();
        $(clone_tbl).find("tbody").append(data);
        $('#plugin_modal_dialog .modal-content').empty().html($(clone_tbl));
        $('#plugin_modal_dialog .modal-content').html('<div class="card">' +
                '<div class="modal-header">' +
                '<h4 class="m-tb-6 d-inline-block">' + title + '</h4>' +
                '<div class="pull-right cstm_btn">' +
                '<button type="button" class="close-btn btn" data-dismiss="modal" aria-hidden="true" style="margin-left:6%;margin-top: -19px;"><i class="fa fa-close"></i> Close</button>' +
                '</div>' +
                '</div>' +
                '<div class="body">' +
                '<div class="row">' +
                '<div class="col-md-12">' +
                $('#plugin_modal_dialog .modal-content').html() +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>');
        $('#plugin_modal_dialog').modal('show');
        delete clone_tbl;
    }

    $(document).ready(function() {
        block('tbl_reminder');
        tbl_reminder = $("#tbl_reminder").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            processing: true,
            serverSide: false,
            /*"ajax": {
                url: APP_CONFIG.context + "/loadReminderFilterData.do?userId=${userId}",
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },*/
            columns: [
                {data: "key",
                    render: function(data, type, row, meta) {
                        if (row.key !== "" && row.key !== null) {
                            return '<a href="javascript:viewTasks(' + row.key + ');">' + row.key + '</a>';
                        }
                    }
                },
                {data: "value"},
                {data: "info1",
                    render: function(data, type, row, meta) {
                        return '<span data-timeago="'+data+'"></span>';
                    }
                }
            ],
            dom: '<"clear">lfltipT',
            tableTools: {
                "sRowSelect": "none",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function() {
                $("#tbl_reminder tr td").css('cursor', 'default');
                unblock('tbl_reminder');
            },
            "drawCallback": function( settings  ) {
                 TIMEAGO.init();    
            }
        });
    });
</script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/dashboard.js"></script>