<%-- 
    Document   : WhatsAppDash
    Created on : May 1, 2019, 10:46:33 AM
    Author     : soumya.gaur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script src="<%=request.getContextPath()%>/js/whatsapp-dashboard.js"></script>
<style>
    .taskleft{
        padding-left: 3%;
    }
</style>
<div class="row">
    <div class="col-md-12">
        <s:text name="lbl.whatsapp.transaction.for"/> <s:select name="dateRange" id="sel_by_period" list="dateRangeList" listKey="key" listValue="value" cssClass="form-control input-sm" cssStyle="display: inline; width: auto;"/>
    </div>
    <div class="col-md-6 fullscreen-block">
        <div class="my-reminder">
            <div class="my-bord">
                <h3><s:text name="lbl.whatsapp.head.outbound.msg"/> </h3>
                <div class="board-icons">     
                    <a class="fa fa-file-excel-o fa-2x" title="Download" style="margin-top: -10px;cursor:pointer" onclick="downloadWhatsAppPieChartReport('O');"></a>            
                    <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="chart_row_1_col_1" data-fs-height-on="400px" data-fs-height-off="350px"></a>
                </div>
            </div>
            <div class="dash-task">
                <div class="body">
                    <div class=" clearfix">
                        <div class="col-lg-12 col-md-12 fs-zoom">   
                            <div id="chart_row_1_col_1" style="height: 350px; width: 100%; padding-left: 0px !important; padding-right: 0px !important;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6 fullscreen-block">
        <div class="my-reminder">
            <div class="my-bord">        
                <h3> <s:radio name="whatsappDashSummaryCheckbox" list="#{'O': 'Open','CU' :'Completed (by User)','CC':'Completed (by close Code)'}" id="whatsapp_dash_summary_checkbox"/></h3>        
                <div class="board-icons">
                    <a class="fa fa-file-excel-o fa-2x" style="cursor:pointer;" title="Download" onclick="downloadWhatsAppPieChartReport('C');"></a>                     
                    <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="chart_row_1_col_2" data-fs-height-on="400px" data-fs-height-off="350px"></a>
                </div>
            </div>
            <div class="dash-task">
                <div class="body">
                    <div class=" clearfix">
                        <div class="col-lg-12 col-md-12 fs-zoom">   
                            <div id="chart_row_1_col_2" style="height: 350px; width: 100%; padding-left: 0px !important; padding-right: 0px !important;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="col-md-12 right-pad fullscreen-block">
    <div class="my-reminder">
        <div class="my-bord">
            <h3><s:text name="lbl.whatsapp.head.inbound.and.outbound.msg"/></h3>
            <div class="board-icons">  
                <a class="fa fa-file-excel-o fa-2x" title="Download" style="margin-top: -10px;cursor:pointer" onclick="downloadWhatsAppBarChartReport('I');"></a>              
                <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="chart_row_2" data-fs-height-on="400px" data-fs-height-off="350px"></a>
            </div>
        </div>
        <div class="dash-task">
            <div class="body">
                <div class=" clearfix">
                    <div class="col-lg-12 col-md-12 fs-zoom">
                        <div class="row">
                            <div class="col-md-8 col-md-offset-2">
                                <div id="chart_row_2" style="height: 350px; width: 100%;"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="col-md-12 right-pad fullscreen-block">
    <div class="my-reminder">
        <div class="my-bord">
            <h3><s:text name="lbl.whatsapp.head.inbound.and.outbound.msg"/></h3>
            <div class="board-icons">  
                <%--a class="fa fa-file-excel-o fa-2x" title="Download" style="margin-top: -10px;cursor:pointer" onclick="downloadWhatsAppBarChartReport('I');"></a--%>
                <a class="fullscreen fa fa-expand fa-2x" href="#" data-fs-id="chart_row_3" data-fs-height-on="400px" data-fs-height-off="350px"></a>
            </div>
        </div>
        <div class="dash-task">
            <div class="body">
                <div class=" clearfix">
                    <div class="col-lg-12 col-md-12 fs-zoom">
                        <div class="row">
                            <div class="col-md-8 col-md-offset-2">
                                <div id="chart_row_3" style="height: 350px; width: 100%;"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
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
            if ($(this).data("fs-id") === "chart_row_1_col_1") {
                setTimeout(function () {
                    chart_row_1_col_1.resize();
                }, 100);
            }
            if ($(this).data("fs-id") === "chart_row_1_col_2") {
                setTimeout(function () {
                    chart_row_1_col_2.resize();
                }, 100);
            }
            if ($(this).data("fs-id") === "chart_row_2") {
                setTimeout(function () {
                    chart_row_2.resize();
                }, 100);
            }
            if ($(this).data("fs-id") === "chart_row_3") {
                setTimeout(function () {
                    chart_row_3.resize();
                }, 100);
            }
        });

        loadWhatsAppSummary($("#sel_by_period").val());
        $("input[name=whatsappDashSummaryCheckbox][value='O']").prop('checked', true);// Default make it as open
        loadWhatsAppOpenAndCompletedRequest($("#sel_by_period").val());

        $("#sel_by_period").on("change", function () {
            loadWhatsAppSummary($("#sel_by_period").val());
            loadWhatsAppOpenAndCompletedRequest($("#sel_by_period").val());
            loadChartMonthlyWiseData($("#sel_by_period").val());
        });

        $('input[type=radio][name=whatsappDashSummaryCheckbox]').on('change', function () {
            loadWhatsAppOpenAndCompletedRequest($("#sel_by_period").val());
        });
        
        loadChartMonthlyWiseDataByCloseCode();
    });


    function numberFormat(value) {
        return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    function loadWhatsAppSummary(dateRange) {
        chart_row_1_col_1.showLoading({
            text: 'Please wait!... '
        });
        chart_row_1_col_1.clear();
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadWhatsAppDashSummaryByPeriod.do",
            data: {"dateRange": dateRange},
            success: function (result) {
                option_row_1_col_1.legend.data = [];
                option_row_1_col_1.series[0].data = [];
                let tot = 0;
                $.each(result.aaData, function (i, o) {
                    option_row_1_col_1.legend.data.push(o.key);
                    option_row_1_col_1.series[0].data.push({"name": (o.key == '' || o.key == null) ? 'Auto Responce' : o.key + " (" + numberFormat(o.value) + ")", "value": Number(o.value)});
                    tot += Number(o.value);
                });
                option_row_1_col_1.title.text = (tot > 0 ? "Total: " + numberFormat(tot) : "No data");
                chart_row_1_col_1.setOption(option_row_1_col_1);
            },
            complete: function () {
                chart_row_1_col_1.hideLoading();
            }
        });
    }

    /**
     * 
     * @param {type} dateRange
     * @param {type} actionType - O, CU, CC
     * @returns {undefined}
     */
    function loadWhatsAppOpenAndCompletedRequest(dateRange) {
        chart_row_1_col_2.showLoading({
            text: 'Please wait!... '
        });
        chart_row_1_col_2.clear();
        let actionType = $("input[name='whatsappDashSummaryCheckbox']:checked").val();
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadWhatsAppDashOpenSummaryByPeriod.do",
            data: {"dateRange": dateRange, "fetch": actionType},
            success: function (result) {
                option_row_1_col_2.legend.data = [];
                option_row_1_col_2.series[0].data = [];
                let tot = 0;
                $.each(result.aaData, function (i, o) {
                    option_row_1_col_2.legend.data.push(o.key);
                    option_row_1_col_2.series[0].data.push({"name": (o.key === null || o.key === '') ? (actionType === 'O' ? 'Auto Responce' : '-') : o.key + " (" + numberFormat(o.value) + ")", "value": Number(o.value)});
                    tot += Number(o.value);
                });
                option_row_1_col_2.title.text = (tot > 0 ? "Total: " + numberFormat(tot) : "No data");
                chart_row_1_col_2.setOption(option_row_1_col_2);
            },
            complete: function () {
                chart_row_1_col_2.hideLoading();
            }
        });
    }

    function loadChartMonthlyWiseData(dateRange) {
        chart_row_2.showLoading({
            text: 'Please wait!... '
        });
        chart_row_2.clear();
        quoteSum = 0;
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadWhatsAppDashMonthlySummaryByPeriod.do",
            data: {"dateRange": dateRange},
            success: function (result) {
                option_row_2.legend.data = [];
                option_row_2.xAxis[0].data = [];
                option_row_2.series[0].data = [];
                option_row_2.series[1].data = [];
                option_row_2.legend.data.push("Inbound Count");
                option_row_2.legend.data.push("Outbound Count");
                let tot = 0;
                $.each(result.aaData, function (i, o) {
                    option_row_2.xAxis[0].data.push(o.key);
                    option_row_2.series[0].data.push(o.value);
                    option_row_2.series[1].data.push(o.info);
                    quoteSum += Number(o.info1);
                    tot += Number(o.value);
                });
                option_row_2.title.text = (tot > 0 ? "Total: " + tot : "No data");
                option_row_2.title.show = (result.aaData && result.aaData.length > 0 ? false : true);
                chart_row_2.setOption(option_row_2);
            },
            complete: function () {
                chart_row_2.hideLoading();
            }
        });
    }

    function loadChartMonthlyWiseDataByCloseCode() {
        chart_row_3.showLoading({
            text: 'Please wait!... '
        });
        chart_row_3.clear();
        quoteSum = 0;
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadWhatsAppDashMonthlySummaryByCloseCode.do",
            data: {},
            success: function (result) {
                option_row_3.legend.data = [];
                option_row_3.xAxis[0].data = [];
                option_row_3.series = [];

                $.each(result.chartData.MON_YEAR, function (i, o) {
                    option_row_3.xAxis[0].data.push(i);
                });

                var j = 0;
                $.each(result.chartData.DATA, function (i, o) {
                    option_row_3.legend.data.push(i);
                    let ser = {
                        name: i,
                        type: 'bar',
                        barGap: 0,
                        label: option_row_3_label,
                        emphasis: {
                            focus: 'series'
                        },
                        data: o
                    };
                    option_row_3.series.push(ser);
                    j++;
                });
                option_row_3.title.text = (j > 0 ? "" : "No data");
                chart_row_3.setOption(option_row_3, true);
            },
            complete: function () {
                chart_row_3.hideLoading();
            }
        });
    }
</script>