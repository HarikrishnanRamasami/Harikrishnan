<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : campaignDetails
    Created on : Aug 17, 2017, 1:00:33 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="card">
    <div class="header">
        <div class="row clearfix">
            <div class="col-md-10">
                <h4 class="m-tb-6 d-inline-block"><s:property value="campaign.mcCampName"/></h4>&nbsp;<s:if test='"A".equals(campaign.mcCampStatus)'><span class="h6 font-bold text-warning"><s:text name="lbl.campaign.not.started"/></span></s:if><s:elseif test='"P".equals(campaign.mcCampStatus)'><span class="h6 font-bold text-primary"><s:text name="lbl.campaign.running"/></span></s:elseif><s:elseif test='"S".equals(campaign.mcCampStatus)'><span class="h6 font-bold text-danger">Stopped</span></s:elseif><s:elseif test='"C".equals(campaign.mcCampStatus)'><span class="h6 font-bold text-success">(<s:text name="lbl.common.closed"/>)</span></s:elseif>
                <p class="h6"><s:property value="campaign.mcCampDesc"/></p>
            </div>
            <div class="col-md-2 cstm_btn">
                <button type="button" id="btn_emails_back" class="btn btn-success waves-effect m-r-5 pull-right" data-toggle="tooltip" data-placement="top"  title="Go Back"><i class="fa fa-arrow-left"></i> <s:text name="btn.go.back"/></button>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-md-12">
                <div role="tabpanel" id="tabbedPanel" class="mb-0 mt-0">
                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs tabs-dark" role="tablist">
                        <li id="summary_tab" role="presentation" class="active">
                            <a href="#summaryTab"  aria-controls="all" role="tab" data-toggle="tab" data-placement="top" title="Summary" onclick="summary('summaryTab');"><s:text name="lbl.common.summary"/></a>
                        </li>
                        <li id="emails_tab" role="presentation">
                            <a href="#emailsTab" aria-controls="all" role="tab" data-toggle="tab" data-placement="top" title="Email" onclick="emails('emailsTab')"><s:text name="lbl.common.emails"/></a>
                        </li>
                        <%--<li id="conversation_tab" role="presentation">
                            <a href="#conversationTab" aria-controls="all" role="tab" data-toggle="tab" data-placement="top" tittle="Conversation" onclick="conversation('conversationTab')">Conversations</a>
                        </li>--%>
                        <li id="conversation_tab" role="presentation">
                            <a href="#templateTab" aria-controls="all" role="tab" data-toggle="tab" data-placement="top" tittle="Template"><s:text name="lbl.template"/></a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane active" id="summaryTab">
                            <div class="tile-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="body table-responsive">
                                            <table class="table table-striped table-bordered display dataTable dtr-inline">
                                                <tbody>
                                                    <%--<tr>
                                                        <td>Campaign Name</td>
                                                        <td><s:property value="campaign.mcCampName"/></td>
                                                    </tr>--%>
                                                    <tr>
                                                        <td><s:text name="lbl.campaign.campaign.id"/></td>
                                                        <td><s:property value="campaign.mcId"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td><s:text name="lbl.created.by"/></td>
                                                        <td><s:property value="campaign.mcCrUid"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td><s:text name="lbl.common.created.on"/></td>
                                                        <td><s:property value="campaign.mcCrDt"/></td>
                                                    </tr>
                                                    <%--<tr>
                                                        <td>Campaign Type</td>
                                                        <td><s:property value="campaign.mcBodyType"/></td>
                                                    </tr>--%>
                                                    <tr>
                                                        <td><s:text name="lbl.campaign.campaign.period"/></td>
                                                        <td><s:property value="campaign.mcScheduleOn"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td><s:text name="lbl.campaign.sender.email"/></td>
                                                        <td><s:property value="campaign.mcFrom"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td><s:text name="lbl.common.email.subject"/> </td>
                                                        <td><s:property value="campaign.mcSubject"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td><s:text name="lbl.campaign.exit.on"/></td>
                                                        <td><s:property value="campaign.mcExpireOn"/></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div id="chart_email_status" style="height: 300px; width:600px;"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="body"><div class="sub-header m-b-0"><h4> <s:text name="lbl.campaign.head.performance.on.delivery.emails"/> </h4></div></div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                   <div id="chart_email_summary_2" style="height: 400px; width: 600px;"></div>
<!--                                 <div id="chart1" style="height: 400px; width: 600px;"></div>-->
                                </div>
<!--                                <div class="col-md-6">
                                    <div id="chart_email_summary" style="height: 250px; width: 650px;"></div>
                                </div>-->
                            </div>
                        </div>
                        <div role="tabpanel" class="tab-pane" id="emailsTab">
                            <div class="tile-body" id="div_emails">
                                <div class="row form-inline">
                                    <div class="col-md-1">
                                        <s:text name="lbl.status"/>
                                    </div>
                                    <div class="col-md-2">
                                        <s:select name="mailTxn.mtStatus" id="selMailTxnFilter" list="statusEmailList" listKey="key" listValue="value"  data-toggle="tooltip" data-placement="top" title="Status" cssClass="form-control" />
                                    </div>
                                    <div class="col-md-1">
                                        <a href="javascript:void(0);" style="padding-top: 3px;" data-toggle="tooltip" data-placement="top" title="Search" id="search_email"><i class="fa fa-search"></i></a>
                                    </div>
                                </div>
                                <table class="table table-striped table-bordered display dataTable dtr-inline" id="tbl_email_txn">
                                    <thead> 
                                        <tr>
                                            <th class="text-center"><s:text name="lbl.customer.name"/></th>
                                            <th class="text-center"><s:text name="lbl.common.email.address"/></th>  
                                            <th class="text-center"><s:text name="lbl.opened"/></th>
                                            <th class="text-center"><s:text name="lbl.visited"/></th>
                                        </tr>
                                    </thead>    
                                    <tbody> 
                                    </tbody> 
                                </table>
                            </div>
                        </div>
                        <%--div role="tabpanel" class="tab-pane" id="conversationTab">
                        </div--%>
                        <div role="tabpanel" class="tab-pane" id="templateTab">
                            <s:property value="campaign.mcCampBody" escapeHtml="false"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>

<script type="text/javascript">
    var chart_email_summary_option, chart_email_summary_2_option, chart_email_status_option;
    var chart_email_summary, chart_email_summary_2, chart_email_status;
    $(document).ready(function() {
        $('[data-toggle="tooltip"]').tooltip();
        <%--chart_email_summary = echarts.init(document.getElementById('chart_email_summary'));
        chart_email_summary_2 = echarts.init(document.getElementById('chart_email_summary_2'));--%>
        chart_email_status = echarts.init(document.getElementById('chart_email_status'));
        
        <%--chart_email_summary_option = {
            title: {
                text: 'Customer intraction'
            },
            angleAxis: {
            },
            radiusAxis: {
                type: 'category',
                data: ['Open', 'Visit', 'Total'],
                z: 5
            },
            polar: {
            },
            series: [],
            legend: {
                show: true,
                orient: 'vertical',
                x: 'right',
                data: ['Open', 'Visit', 'Total']
            }
        };
        chart_email_summary.setOption(chart_email_summary_option);

        chart_email_summary_2_option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                left: '1%',
                right: '1%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'value'
            },
            yAxis: {
                type: 'category',
                data: ['Opened', 'Clicked', 'No Action']
            },
            series: [
                {
                    type: 'bar',
                    itemStyle: {
                        normal: {
                            color: '#277EAB',
                            shadowBlur: 5
                        }
                    },
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
        chart_email_summary_2.setOption(chart_email_summary_2_option);--%>

        chart_email_status_option = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: ["Not Send", "Delivered", "Un-Delivered"]
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
                    saveAsImage: {show: true},
                }
            },
            calculable: false,
            series: [
                {
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data: []
                }
            ]
        };
        chart_email_status.setOption(chart_email_status_option);
        window.onresize = function() {
            setTimeout(function() {
                chart_email_status.resize();
            }, 200);
        };

        $("#div_emails").show();
        $("#tbl_email_txn").DataTable({
            paging: true,
            searching: true,
            autoWidth: false,
            ordering: true,
            info: false,
            lengthChange: false,
            pageLength: 5,
            responsive: true,
            "destroy": true,
            "ajax": {
                "url": APP_CONFIG.context + "/camp/loadEmailSmsTxnData.do",
                "data": function(d) {
                    return $.extend({}, d, {"mailTxn.mtMcId": "${campaign.mcId}", "mailTxn.mtStatus": $('#selMailTxnFilter').val()});
                },
                "type": "POST",
                "datatype": "json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "mtTo"},
                {data: "mtTo"},
                {data: "mtOpened"},
                {data: "mtVisisted"}
            ],
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function() {
                $("#tbl_email_txn tr td").css('cursor', 'default');
            }
        });

        $("#search_email").on("click", function() {
            $("#tbl_email_txn").DataTable().ajax.reload();
        });

        $("#btn_emails_back").on("click", function() {
            $('#block_camp').show();
            $('#block_camp_report').empty().hide();
        });

        viewEmailCharts();
    });

    function summary() {

    }

    function emails() {

    }

    function viewEmailCharts() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/loadEmailSmsSummaryData.do",
            data: {"campaign.mcId": "${campaign.mcId}"},
            success: function(result) {
                <%--chart_email_summary_option.series = [];
                chart_email_status_option.series[0].data = [];--%>
                //console.log(result.aaData);
                if (result.aaData && result.aaData.length > 0) {
                    var data = result.aaData[0];
                    <%--chart_email_summary_option.series.push({
                        type: 'bar',
                        data: [data.intData2, 0, 0],
                        coordinateSystem: 'polar',
                        name: 'Open',
                        stack: 'a'
                    });
                    chart_email_summary_option.series.push({
                        type: 'bar',
                        data: [0, data.intData3, 0],
                        coordinateSystem: 'polar',
                        name: 'Visit',
                        stack: 'b'
                    });
                    chart_email_summary_option.series.push({
                        type: 'bar',
                        data: [0, 0, data.intData1],
                        coordinateSystem: 'polar',
                        name: 'Total',
                        stack: 'c'
                    });
                    chart_email_summary.setOption(chart_email_summary_option);

                    chart_email_summary_2_option.series[0].data = [data.intData2, data.intData3, data.intData1];
                    chart_email_summary_2.setOption(chart_email_summary_2_option);--%>

                    chart_email_status_option.series[0].data.push({"name": "Not Send", "value": data.intData4});
                    chart_email_status_option.series[0].data.push({"name": "Delivered", "value": data.intData5});
                    chart_email_status_option.series[0].data.push({"name": "Un-Delivered", "value": (data.intData6 + data.intData7)});
                    //chart_email_status_option.series[0].data.push({"name": "Bounced", "value": data.intData7});
                    chart_email_status.setOption(chart_email_status_option);
                    var dataz = [{
                        type: 'bar',
                        status: ['N', 'C', 'O', 'S'],
                        x: [(data.intData1 - Math.max(data.intData2, data.intData3)), data.intData3, data.intData2, data.intData1],
                        y: ['No Action', 'Clicked', 'Opened', 'Delivered'],
                        orientation: 'h'
                    }];
            
                    var myPlot =  Plotly.newPlot('chart_email_summary_2', dataz);
                    document.getElementById("chart_email_summary_2").on('plotly_click', function(data) {
                        var dataArray = data.points[0].data['status'];
                
                        var pn='',tn='';
                        for(var i=0; i < data.points.length; i++){
                            pn = data.points[i].pointNumber;
                            tn = data.points[i].curveNumber;
                        };
                        //console.log(pn+":"+tn);.
                        if(pn >= 0) {
                            $("#summary_tab").removeAttr("class");
                            $("#summaryTab").attr("class","tab-pane");
                            $("#emails_tab").attr("class","active");
                            $("#emailsTab").attr("class","tab-pane active");
                            $("#selMailTxnFilter").val(dataArray[pn]);
                            $("#tbl_email_txn").DataTable().ajax.reload();
                        }
                    });
                }
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
                unblock('block_body');
            }
        });
    }
</script>