<%-- 
    Document   : campaignTxn
    Created on : 23 Sep, 2019, 10:14:27 AM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    .wrapper panel-body{
        min-height: 226px;
    }
</style>
<div class="col-md-12 right-pad" id="block_body">
    <s:hidden name="mcCampId" id="mcCampId"/>
    <div class="dash-leads" style="border-top:0!important">
        <div class="my-bord">
            <h3><s:text name="lbl.campaign.head.campaign.transaction"/></h3>
        </div>
        <div class="col-sm-12">
            <div class="col-md-8">
                <form class="form-inline" id="frm_txn_search" name="frm_txn_search" method="post">
                    <div class="col-sm-12 col-md-12">
                        <div class="row bor2">
                             <div class="form-group padR">
                                <label><s:text name="lbl.year"/></label>
                                <s:textfield type="number" name="year" id="year" class="form-control numbers" maxlength="4" data-toggle="tooltip"  data-placement="top" title="%{getText('lbl.campaign.enter.campaign.name')}"/></div> 
                            <div class="form-group padR">
                                <label><s:text name="lbl.month"/></label>
                                <s:select name="month" id="month" class="form-control" list="monthRangeList" listKey="key" listValue="value" cssClass="form-control"/>
                            </div>
                            <div class="form-group padR">
                                <button type="button" id="search_campaign_txn" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-4 board-icons1 Rbtn">
                <button type="button" class="btn btn-danger waves-effect" style="width: 80px; margin-top: -8px" id="btn_txn_close" data-toggle="tooltip" data-placement="top" title="Close"><i class="fa fa-close"></i> <s:text name="btn.close"/></button>
            </div>
        </div>
        <div class="my-bord bor2">
            <table  class="table table-striped table-bordered display dataTable dtr-inline" id="tbl_camp_txn">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                        <th class="text-center" ><s:text name="lbl.start.date"/></th>
                        <th class="text-center"><s:text name="lbl.end.date"/></th>
                        <th class="text-center"><s:text name="lbl.status"/></th>
                        <th class="text-center"><s:text name="lbl.campaign.head.sub.flow.action"/></th>
                        <th class="text-center"><s:text name="lbl.campaign.head.sub.ab.yn="/></th>
                        <th class="text-center"><s:text name="lbl.campaign.head.sub.data.count"/></th>
                        <th class="text-center" style="text-align: center"><s:text name="lbl.common.head.sub.actions"/></th>
                    </tr>
                </thead>
                <tbody>	   
                </tbody>
            </table>
        </div>
    </div>
    <div class="row clearfix col-md-12" id="div_perform">
        <div class="col-md-12">
            <div class="wrapper">
                <div class="cmp_txn">
                    <div class="container-fluid">
                        <div class="row no-gutters"> 

                            <!-- col 01 --->
                            <div class="col-md-3 col-lg-3 col-sm-6">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><s:text name="lbl.common.sent"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div id="div_txn_sent" class="cp-graphs-settings"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-3 col-lg-3 col-sm-6">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><s:text name="lbl.common.head.opens"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div id="div_txn_opened" class="cp-graphs-settings"></div>
                                    </div>
                                </div>
                            </div>

                            <!-- col 04 --->
                            <div class="col-md-3 col-lg-3 col-sm-6">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><s:text name="lbl.common.head.clicks"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div id="div_txn_clicked" class="cp-graphs-settings"></div><br>
                                        <button class="btn btn-primary waves-effect" onclick="viewClickedData()"><s:text name="btn.view"/></button>&nbsp&nbsp;  
                                    </div>

                                </div>
                            </div>

                            <!-- col 05 --->
                            <div class="col-md-3 col-lg-3 col-sm-6">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><s:text name="lbl.common.head.convertions"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div id="div_txn_converted" class="cp-graphs-settings"></div><br>
                                        <button class="btn btn-primary waves-effect" onclick="viewConvertedData()"><s:text name="btn.view"/></button>&nbsp&nbsp;  
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <div id="div_data_txn">
        <div class="dash-leads" style="border-top:0!important">
            <div class="my-bord">
                <h3><s:text name="lbl.campaign.head.data.view"/></h3>
            </div>
            <s:form class="form-inline" id="frm_search_txn" name="frm_search" method="post" theme="simple">
                <s:hidden name="campaignTxnData.mctdTxnId" id="txnId"/>
                <s:hidden name="campaignTxnData.mctdCampId" id="mctdCampId"/>
                <div class="col-sm-12 col-md-12 bor2 Tmar" style="margin-bottom: 10px;">
                    <div class="row bor2">
                        <div class="form-group padR">
                            <label for="clDate">Action</label>
                            <s:select id="mctdAction" name="campaignTxnData.mctdAction" list="campActionList"  listKey="key" listValue="value"  data-toggle="tooltip" data-placement="top" headerKey="" headerValue="--select--"  cssClass="form-control"/>
                        </div> 
                        <div class="form-group padR">
                            <button type="button" id="search_camp_txn_data" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>

                        </div>
                        <div class="col-sm-12 col-md-4 board-icons1 Rbtn">
                            <button type="button" class="btn btn-primary tmargin cbtn" style="width: 80px; margin-top: -8px" onclick="downloadExcel();" data-toggle="tooltip" data-placement="top" title="Close"><i class="fa fa-download"></i> <s:text name="btn.excel"/></button></div>
                    </div>
                </div>
            </s:form>
            <div class="my-bord bor2">
                <div id="datamap_error_div" class="alert alert-danger mb-10" style="display:none"></div>
                <table class="table table-striped table-bordered display dataTable dtr-inline" id="tbl_camp_txn_data" width="100%">
                    <thead>
                        <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                            <th style="width: 12%;" class="text-center"><s:text name="lbl.campaign.head.sub.contact.id"/></th>
                            <th style="width: 12%;" class="text-center"><s:text name="lbl.ref.no"/></th>
                            <th style="width: 12%;" class="text-center"><s:text name="lbl.common.sent"/></th>
                            <th style="width: 12%;" class="text-center"><s:text name="lbl.common.bounced"/></th>
                            <th style="width: 12%;" class="text-center"><s:text name="lbl.common.replied"/></th>
                            <th style="width: 12%;" class="text-center"><s:text name="lbl.common.clicked"/></th>
                            <th style="width: 12%;" class="text-center"><s:text name="lbl.common.converted"/></th>
                        </tr>
                    </thead>
                    <tbody>	   
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div id="div_data_convert">
        <div class="dash-leads" style="border-top:0!important">
            <div class="my-bord">
                <h3><s:text name="lbl.campaign.head.converted.view"/></h3>
            </div>
            <div class="my-bord bor2">
                <div id="datamap_error_div" class="alert alert-danger mb-10" style="display:none"></div>
                <table class="table table-striped table-bordered display dataTable dtr-inline nowrap nolink" id="tbl_camp_txn_convert" role="grid">
                    <thead>
                        <tr>
                            <th class="text-center"><s:text name="lbl.campaign.head.sub.contact.id"/></th>
                            <th class="text-center"><s:text name="lbl.ref.no"/></th>
                            <th class="text-center"><s:text name="lbl.date"/></th>
                            <th class="text-center"><s:text name="lbl.value"/></th>
                        </tr>
                    </thead>
                    <tbody>	   
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div id="div_data_clicked">
        <div class="dash-leads" style="border-top:0!important">
            <div class="my-bord">
                <h3><s:text name="lbl.campaign.head.clicked.view"/></h3>
            </div>
            <div class="my-bord bor2">
                <div id="datamap_error_div" class="alert alert-danger mb-10" style="display:none"></div>
                <table id="tbl_camp_txn_click" class="table table-striped table-bordered display dataTable dtr-inline">
                    <thead>
                        <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                            <th style="width: 10%;" class="text-center"><s:text name="lbl.campaign.head.sub.contact.id"/></th>
                            <th style="width: 10%;" class="text-center"><s:text name="lbl.common.head.sub.upper.url"/></th>
                            <th style="width: 10%;" class="text-center"><s:text name="lbl.date"/></th>
                        </tr>
                    </thead>
                    <tbody>	   
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<s:form name="txn_excel" id="txn_excel" action=""></s:form>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#div_data_txn").hide();
            $("#div_perform").hide();
            $("#div_data_convert").hide();
            $("#div_data_clicked").hide();
            $("#tbl_camp_txn").DataTable({
                paging: true,
                searching: true,
                ordering: true,
                info: true,
                "destroy": true,
                "lengthChange": false,
                "pageLength": 10,
                "responsive": true,
                autoWidth: false,
                "processing": true,
                "ajax": {
                    "url": APP_CONFIG.context + "/camp/loadCampaignTxn.do",
                    "type": "POST",
                    "data": function (d) {
                        return $.extend({}, d, {'month': $('#frm_txn_search #month').val(), 'year': $('#frm_txn_search #year').val(), 'mcCampId': $("#mcCampId").val()});
                    },
                    "dataType": "json",
                    "dataSrc": "aaData"
                },
                columns: [
                    {data: "mctStartDate",
                        render: function (data, type, row, meta) {
                            if (data !== "" && data !== null) {
                                var result = moment(data).format('DD/MM/YYYY HH:mm');
                                return result;
                            } else {
                                return "";
                            }
                        }
                    },
                    {data: "mctEndDate",
                        render: function (data, type, row, meta) {
                            if (data !== "" && data !== null) {
                                var result = moment(data).format('DD/MM/YYYY HH:mm');
                                return result;
                            } else {
                                return "";
                            }
                        }
                    },
                    {data: "mctStatusDesc"},
                    {data: "mctFlowAction",
                        render: function (data, type, row, meta) {
                            if (data !== "" && data !== null) {
                                return data;
                            } else {
                                return "Completed";
                            }
                        }
                    },
                    {data: "mctAbYn",
                        render: function (data) {
                            if (data === '1') {
                                return "Yes";
                            } else if (data === '0') {
                                return "No";
                            } else {
                                return "";
                            }
                        }
                    },
                    {data: "mctDataCount"},
                    {data: "mctTxnId",
                        render: function (data, type, row, meta) {
                            if (row.mctTxnStatus === "W") {
                                return  '<center><i class="fa fa-stop-circle action-icon" style="color:red" onclick="javascript: stopTxn(\'' + row.mctTxnId + '\');" title="Stop"></i>&nbsp;&nbsp' +
                                        '<i class="fa fa-eye action-icon" style="color:#3399cc" onclick="javascript: viewData(\'' + row.mctTxnId + '\');" title="View Data"></i>&nbsp;&nbsp' +
                                        '<i class="fa fa-line-chart action-icon" style="color:#3399cc" onclick="javascript: viewPerformance(\'' + row.mctTxnId + '\');" title="Performance"></i>';
                            } else {
                                return  '<center><i class="fa fa-eye action-icon" style="color:#3399cc" onclick="javascript: viewData(\'' + row.mctTxnId + '\');" title="View Data"></i>&nbsp;&nbsp' +
                                        '<i class="fa fa-line-chart action-icon" style="color:#3399cc" onclick="javascript: viewPerformance(\'' + row.mctTxnId + '\');" title="Performance"></i>';
                            }

                        },
                    },
                ],
                columnDefs: [
                    {targets: 2, orderable: false}
                ],
                language: {
                    searchPlaceholder: "Search..."
                },
                dom: '<"clear">lfltipT',
                tableTools: {
                    "sRowSelect": "single",
                    "aButtons": []
                },
                initComplete: function () {
                    $("#tbl_camp_txn tr td").css('cursor', 'default');
                    // $('#datatable_search').html($('#insured_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
                    unblock('block_body');
                }
            });

            $("#btn_txn_close").on("click", function () {
                $('#block_camp').show();
                $('#block_camp_txn').empty().hide();
            });

            $("#search_camp_txn_data").on("click", function () {
                block('block_body');
                $.ajax({
                    type: "POST",
                    url: APP_CONFIG.context + "/camp/loadCampaignTxnData.do",
                    data: $('#frm_search_txn').serialize(),
                    success: function (result) {
                        reloadDataTable("#tbl_camp_txn_data", result.aaData);
                    },
                    error: function (xhr, status, error) {
                        alert("Error: " + error);
                    },
                    complete: function () {
                        unblock('block_body');
                    }
                });
            });
            
            $("#search_campaign_txn").on("click", function() {
                $("#tbl_camp_txn").DataTable().ajax.reload();
            });
        });
        
        function downloadExcel() {
            document.txn_excel.action = APP_CONFIG.context + "/camp/downloadCampaignData.do?campaignTxnData.mctdCampId=" + $("#mctdCampId").val() + "&campaignTxnData.mctdTxnId=" + $("#txnId").val() + "&campaignTxnData.mctdAction=" + $("#mctdAction").val();
            document.txn_excel.submit();
        }


        function viewData(txnId) {
            $("#div_data_convert").hide();
            $("#div_data_clicked").hide();
            $("#div_data_txn").show();
            $("#txnId").val(txnId);
            $("#mctdCampId").val($("#mcCampId").val());
            var view_data_url = APP_CONFIG.context + "/camp/loadCampaignTxnData.do?campaignTxnData.mctdCampId=" + $("#mcCampId").val() + "&campaignTxnData.mctdTxnId=" + txnId;
            $("#tbl_camp_txn_data").DataTable({
                "destroy": true,
                "lengthChange": false,
                "pageLength": 10,
                "responsive": true,
                autoWidth: false,
                "processing": true,
                "ajax": {
                    "url": view_data_url,
                    "type": "POST",
                    "contentType": "application/json",
                    "dataSrc": "aaData"
                },
                columns: [
                    {data: "mctdContactId"},
                    {data: "mctdContactRef"},
                    {data: "mctdSentYn",
                        render: function (data, type, row, meta) {
                            if (data === '1') {
                                return "Yes";
                            } else {
                                return "No"
                            }
                        }},
                    {data: "mctdBouncedYn",
                        render: function (data, type, row, meta) {
                            if (data === '1') {
                                return "Yes";
                            } else {
                                return "No"
                            }
                        }},
                    {data: "mctdRepliedYn",
                        render: function (data, type, row, meta) {
                            if (data === '1') {
                                return "Yes";
                            } else {
                                return "No"
                            }
                        }},
                    {data: "mctdClickedYn",
                        render: function (data, type, row, meta) {
                            if (data === '1') {
                                return "Yes";
                            } else {
                                return "No"
                            }
                        }},
                    {data: "mctdConvertedYn",
                        render: function (data, type, row, meta) {
                            if (data === '1') {
                                return "Yes";
                            } else {
                                return "No"
                            }
                        }},
                ],
                columnDefs: [
                    {targets: 2, orderable: false}
                ],
                language: {
                    searchPlaceholder: "Search..."
                },
                dom: '<"clear">lfrtipT',
                tableTools: {
                    "sRowSelect": "single",
                    "aButtons": []
                },
                initComplete: function () {
                    $("#tbl_camp_txn_data tr td").css('cursor', 'default');
                }
            });
            $("#div_perform").hide();
        }

        function viewPerformance(txnId) {
            $("#div_data_txn").hide();
            $("#div_data_convert").hide();
            $("#div_data_clicked").hide()
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/loadCampaignTxnCount.do",
                data: {"mcCampId": "${mcCampId}", "campaignTxn.mctTxnId": txnId},
                success: function (result) {
                    console.log(result);
                    performanceChart(result);
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
            $("#div_perform").show();
        }
        function performanceChart(result) {
            var Totalratio = [], txn_sent = [], txn_clicked = [], txn_opened = [], txn_openedRatio = [], txn_uniqueOpened = [], txn_uniqueOpenRatio = [], txn_clickedRatio = [], txn_uniqueClicked = [], txn_uniqueClickedRatio = [], txn_convertedAmt = [], txn_converted = [], txn_convertedRatio = [];
            $.each(result.aaData, function (i, o) {
                console.log(result.aaData);
                txn_sent.push(Number(o.sent === null ? "0" : o.sent));
                txn_opened.push(Number(o.opened === null ? "0" : o.opened));
                txn_openedRatio.push(Number(o.openedRatio === null ? "0" : o.openedRatio));
                txn_uniqueOpened.push(Number(o.uniqueOpened === null ? "0" : o.uniqueOpened));
                txn_uniqueOpenRatio.push(Number(o.uniqueOpenRatio === null ? "0" : o.uniqueOpenRatio));
                txn_clicked.push(Number(o.clicked === null ? "0" : o.clicked));
                txn_clickedRatio.push(Number(o.clickedRatio === null ? "0" : o.clickedRatio));
                txn_uniqueClicked.push(Number(o.uniqueClicked === null ? "0" : o.uniqueClicked));
                txn_uniqueClickedRatio.push(Number(o.uniqueClickedRatio === null ? "0" : o.uniqueClickedRatio));
                txn_converted.push(Number(o.converted === null ? "0" : o.converted));
                txn_convertedRatio.push(Number(o.convertedRatio === null ? "0" : o.convertedRatio));
                txn_convertedAmt.push(Number(o.convertedAmt === null ? "0" : o.convertedAmt));
                Totalratio.push(Number(100));
            });
            var campaign_txn_sent = echarts.init(document.getElementById('div_txn_sent'));
            var campaign_txn_opened = echarts.init(document.getElementById('div_txn_opened'));
            var campaign_txn_clicked = echarts.init(document.getElementById('div_txn_clicked'));
            var campaign_txn_converted = echarts.init(document.getElementById('div_txn_converted'));
            var a = {normal: {label: {show: !0, position: "center",
                        formatter: "{b}\n", textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}},
                    labelLine: {show: !1}}},
            l = {normal: {color: "#cccccc", label: {show: !0, position: "center", textStyle: {baseline: "middle"}},
                    labelLine: {show: !1}}}, n = [45, 60];
            var campaign_txn_sent_options = {
                title: [
                    {
                        text: '',
                        x: 'center',
                        y: '80%',
                        textStyle: {
                            fontWeight: 'normal',
                            fontSize: '13',
                            color: '#484848',
                            lineHeight: 'normal'

                        },
                        subtextStyle: {
                            fontWeight: 'normal',
                            fontSize: '13',
                            color: '#484848',
                            lineHeight: 'normal'

                        }
                    }
                ],
                legend: {x: "center", y: "56%",
                    data: ["sent"]},
                color: ["#4caf50"],
                series: [
                    {
                        name: 'bounced',
                        type: "pie",
                        x: '20%',
                        y: '78%',
                        radius: n,
                        itemStyle: {normal: {label: {textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}, formatter: function (e) {
                                        return"\n\n" + txn_sent;
                                    }}}},
                        data: [{name: "100%", value: '', itemStyle: l},
                            {name: Totalratio + "%", value: txn_sent, itemStyle: a}]
                    }
                ]
            };
            campaign_txn_sent.setOption(campaign_txn_sent_options);

            var campaign_txn_opened_options = {
                title: [
                    {
                        text: 'Unique Opens:' + txn_uniqueOpened,
                        subtext: 'Unique Ratio: ' + txn_uniqueOpenRatio + "%",
                        x: 'center',
                        y: '80%',
                        textStyle: {
                            fontWeight: 'normal',
                            fontSize: '13',
                            color: '#484848',
                            lineHeight: 'normal'

                        },
                        subtextStyle: {
                            fontWeight: 'normal',
                            fontSize: '13',
                            color: '#484848',
                            lineHeight: 'normal'

                        }
                    }
                ],
                legend: {x: "center", y: "56%",
                    data: [""]},
                color: ["#0b40a2"],
                series: [
                    {
                        name: 'opened',
                        type: "pie",
                        x: '7%',
                        y: '78%',
                        radius: n,
                        itemStyle: {normal: {label: {textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}, formatter: function (e) {
                                        return"\n\n" + txn_opened;
                                    }}}},
                        data: [{name: "", value: txn_sent, itemStyle: l},
                            {name: txn_openedRatio + "%", value: txn_opened, itemStyle: a}]
                    },
                ]
            };
            campaign_txn_opened.setOption(campaign_txn_opened_options);
            var campaign_txn_clicked_options = {
                title: [
                    {
                        text: 'Unique Clicks: ' + txn_uniqueClicked,
                        subtext: 'Unique Ratio: ' + txn_uniqueClickedRatio + "%",
                        x: 'center',
                        y: '80%',
                        textStyle: {
                            fontWeight: 'normal',
                            fontSize: '13',
                            color: '#484848',
                            lineHeight: 'normal'

                        },
                        subtextStyle: {
                            fontWeight: 'normal',
                            fontSize: '13',
                            color: '#484848',
                            lineHeight: 'normal'

                        }
                    }
                ],
                legend: {x: "center", y: "56%",
                    data: [""]},
                color: ["#009688"],
                series: [
                    {
                        name: 'clicked',
                        type: "pie",
                        x: '7%',
                        y: '78%',
                        radius: n,
                        itemStyle: {normal: {label: {textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}, formatter: function (e) {
                                        return"\n\n" + txn_clicked;
                                    }}}},
                        data: [{name: "", value: txn_sent, itemStyle: l},
                            {name: txn_clickedRatio + "%", value: txn_clicked, itemStyle: a}]
                    },
                ]
            };
            campaign_txn_clicked.setOption(campaign_txn_clicked_options);
            var campaign_txn_converted_options = {
                title: [
                    {
                        x: 'center',
                        y: '80%',
                        textStyle: {
                            fontWeight: 'normal',
                            fontSize: '13',
                            color: '#484848',
                            lineHeight: 'normal'

                        },
                        subtextStyle: {
                            fontWeight: 'normal',
                            fontSize: '13',
                            color: '#484848',
                            lineHeight: 'normal'

                        }
                    }
                ],
                legend: {x: "center", y: "56%",
                    data: [""]},
                color: ["#ff9800"],
                series: [
                    {
                        name: 'converted',
                        type: "pie",
                        x: '70%',
                        y: '78%',
                        radius: n,
                        color: ["#ff9800"],
                        itemStyle: {normal: {label: {textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}, formatter: function (e) {
                                        return"\n\n" + txn_converted;
                                    }}}},
                        data: [{name: "", value: txn_sent, itemStyle: l},
                            {name: txn_convertedRatio + "%", value: txn_converted, itemStyle: a}]
                    },
                ]
            };
            campaign_txn_converted.setOption(campaign_txn_converted_options);
            window.onresize = function () {
                setTimeout(function () {
                    campaign_txn_sent.resize();
                    campaign_txn_opened.resize();
                    campaign_txn_clicked.resize();
                    campaign_txn_converted.resize();
                }, 200);
            }

            setTimeout(function () {
                campaign_txn_sent.resize();
                campaign_txn_opened.resize();
                campaign_txn_clicked.resize();
                campaign_txn_converted.resize();
            }, 1000);
            $("#div_perform").show();
        }

        function stopTxn(txnId) {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/updateCampaignTxn.do",
                data: {"mcCampId": "${mcCampId}", "campaignTxn.mctTxnId": txnId},
                success: function (result) {
                    if (result.messageType === "S") {
                        $.notify(result.message, "custom");
                        reloadDt("tbl_camp_txn");
                    } else {
                        $.notify(result.message, "custom");
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        }

        function viewConvertedData() {
            block('block_body');
            $("#div_perform").show();
            $("#div_data_convert").show();
            $("#div_data_clicked").hide();
            var converted_url = APP_CONFIG.context + "/camp/loadTxnConvertedData.do?mcCampId=" + $("#mcCampId").val() + "&campaignTxn.mctTxnId=" + $("#txnId").val();
            $("#tbl_camp_txn_convert").DataTable({
                paging: true,
                searching: true,
                ordering: true,
                info: true,
                "destroy": true,
                "lengthChange": false,
                "pageLength": 10,
                "responsive": true,
                autoWidth: false,
                "processing": true,
                "ajax": {
                    "url": converted_url,
                    "type": "POST",
                    "contentType": "application/json",
                    "dataSrc": "aaData"
                },
                columns: [
                    {data: "mctdContactId"},
                    {data: "mctdContactRef"},
                    {data: "mctdConvertedDt",
                        render: function (data, type, row, meta) {
                            if (data !== "" && data !== null) {
                                var result = moment(data).format('DD/MM/YYYY');
                                return result;
                            } else {
                                return "";
                            }
                        }

                    },
                    {data: "mctdConvertedValue"},
                ],
                columnDefs: [
                    {targets: 2, orderable: false}
                ],
                language: {
                    searchPlaceholder: "Search..."
                },
                dom: '<"clear">lfrtipT',
                tableTools: {
                    "sRowSelect": "single",
                    "aButtons": []
                },
                initComplete: function () {
                }
            });
            unblock('block_body');
        }

        function viewClickedData() {
            block('block_body');
            $("#div_perform").show();
            $("#div_data_convert").hide();
            $("#div_data_clicked").show();
            var url = APP_CONFIG.context + "/camp/loadTxnClickedData.do?mcCampId=" + $("#mcCampId").val();
            $("#tbl_camp_txn_click").DataTable({
                paging: true,
                searching: true,
                ordering: true,
                info: true,
                "destroy": true,
                "lengthChange": false,
                "pageLength": 10,
                "responsive": true,
                autoWidth: false,
                "processing": true,
                "ajax": {
                    "url": url,
                    "type": "POST",
                    "contentType": "application/json",
                    "dataSrc": "aaData"
                },
                columns: [
                    {data: "mctlDataId"},
                    {data: "mctlCrDt",
                        render: function (data, type, row, meta) {
                            if (data !== "" && data !== null) {
                                var result = moment(data).format('DD/MM/YYYY HH:mm:ss');
                                return result;
                            }
                            else {
                                return "";
                            }
                        }
                    },
                    {data: "mctlUrl"},
                ],
                columnDefs: [
                    {targets: 2, orderable: false}
                ],
                language: {
                    searchPlaceholder: "Search..."
                },
                dom: '<"clear">lfltipT',
                tableTools: {
                    "sRowSelect": "single",
                    "aButtons": []
                },
                initComplete: function () {
                    $("#tbl_camp_txn_click tr td").css('cursor', 'default');
                    // $('#datatable_search').html($('#insured_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
                    unblock('block_body');
                }
            });
            unblock('block_body');
        }
</script>

