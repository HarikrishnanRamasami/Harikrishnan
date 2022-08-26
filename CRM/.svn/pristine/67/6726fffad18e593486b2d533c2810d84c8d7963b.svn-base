<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : missedCall
    Created on : May 25, 2017, 9:20:08 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="qa.com.qic.common.util.ApplicationConstants"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style>
    .bor3 {
        margin-left: 19px;
        margin-top: 10px;
        margin-right: 14px;
    }
</style>
<div class="col-md-12 right-pad">
    <div class="dash-leads" style="border-top:0!important">
        <div class="my-bord">
            <h3><s:text name="lbl.call.details"/></h3>
        </div>
        <div class="">
            <div class="row">
                <s:if test='!"N".equals(flex1)'>
                    <div class="row">
                        <div class="col-md-6" style="margin-left: -40px;">
                            <div id="div_chart_call_log" style="height: 200px; width: 100%;"></div>
                        </div>
                        <div class="col-md-6">
                            <%--div>
                                <s:textfield name="callLog.cclCallDt" id="clDateCallWait" cssClass="form-control input-sm calicon" cssStyle="width: 290px;" placeholder="DD/MM/YYYY" title="Date" readonly="true"/>
                            </div--%>
                            <table id="tbl_call_wait" class="table table-striped table-bordered display dataTable dtr-inline table-striped table-bordered display dataTable dtr-inline" width="100%">
                                <thead>
                                    <tr>
                                        <th style="text-align: center;"><s:text name="lbl.type"/></th>
                                        <s:iterator value="yearList">
                                            <th style="text-align: center;"><s:property/></th>
                                        </s:iterator>
                                        <th style="text-align: center;"><s:text name="lbl.total.calls"/></th>
                                        <th style="text-align: center;"><s:text name="lbl.sla"/> (%)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </s:if>
            </div>
        </div>
        <div class="my-bord bor2">
            <div class="">
                <div class="col-sm-12 col-md-12">
                    <s:form class="form-inline pt-5" id="frm_search_callog" name="frm_search_callog" method="post" theme="simple">
                        <input type="hidden" id="hid_prev_clDate"/>
                        <%--callLog.cclFlex3 used for call wait time--%>
                        <s:hidden name="callLog.cclFlex3"/>
                        <div class="form-group padR">
                            <label for="clDate"><s:text name="lbl.date"/></label>
                            <s:textfield name="callLog.cclCallDt" id="clDate" class="form-control input-sm calicon " placeholder="DD/MM/YYYY" title="%{getText('lbl.date')}" readonly="true"/>
                        </div>
                        <div class="form-group padR">
                            <label for="cclCrmType"><s:text name="lbl.call.type"/></label>
                            <s:select class="form-control input-sm" name="callLog.cclCrmType" id="cclCrmType" list="keyValueList" listKey="key" listValue="value" onchange="enableDetails();"/>
                        </div>
                        <div class="form-group padR">
                            <label for="userCallId"><s:text name="lbl.user"/></label>
                            <s:select id="userCallId" class="form-control input-sm" name="callLog.cclCrUid" list="userList" listKey="key" listValue="value" cssStyle="width: 121px;"/>
                        </div>
                        <div class="form-group padR" id="showbox">
                            <input type="checkbox" name="callLog.cclNotAnswered" value="1" id="md_checkbox_36" class="filled-in chk-col-deep-orange" title="%{getText('lbl.missed.call.not.answered')}">
                            <label for="md_checkbox_36"><i class="material-icons-sharp" title="<s:text name="lbl.not.answered"/>" style="color: #dd4343;vertical-align: bottom;">phone_disabled</i></label>                                
                        </div>
                        <s:if test='"N".equals(flex1)'>
                            <div class="form-group padR">
                                <b style="color: #FF0000;"><s:text name="lbl.or"/></b>
                            </div>
                            <div class="form-group padR">
                                <label for="pwd"><s:text name="lbl.call.no"/></label>
                                <s:textfield id="cclCallNo" class="form-control input-sm" name="callLog.cclCallNo" cssStyle="width: 121px;" maxlength="10"/>
                            </div>
                        </s:if>
                        <div class="form-group">
                            <button type="button" id="search_callog" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
                        </div> 
                    </s:form>
                </div>
                <div class="row">
                    <div class="col-md-9">
                        <div id="dt_help_lbl" style="margin-top: 19px; padding: 8px; font-weight: bold;"><s:text name="lbl.civil.id"/> :-&nbsp;
                            <i class="fa fa-address-book-o fa-lg" style="color: #418bca;"></i>-<s:text name="lbl.not.available"/>,&nbsp;
                            <i class="fa fa-address-book-o fa-lg" style="color: #ca4141;"></i>-<s:text name="lbl.duplicate"/>,&nbsp;
                            <i class="fa fa-address-book-o fa-lg" style="color: #41ca45;"></i>-<s:text name="lbl.available"/>&nbsp;
                        </div>
                        <div style="padding: 8px; font-weight: bold;"><s:text name="lbl.call.type"/> :-&nbsp;
                            <i class="material-icons" style="color: #FF0000;">call_end</i>-<s:text name="lbl.call.type"/>,&nbsp;
                            <i class="material-icons" style="color: #00FF00;">call_received</i>-<s:text name="lbl.inbound"/>,&nbsp;
                            <i class="material-icons" style="color: #FF0000;">call_missed_outgoing</i>-<s:text name="lbl.outbound.missed"/>,&nbsp;
                            <i class="material-icons" style="color: #00FF00;">call_made</i>-<s:text name="lbl.outbound"/>,&nbsp;
                            <i class="material-icons" style="color: #FF0000;">call_missed</i>-<s:text name="lbl.missed"/>,&nbsp;
                            <i class="material-icons" style="color: #0000FF;">settings_phone</i>-<s:text name="lbl.forwarded"/>,&nbsp;
                            <i class="material-icons" style="color: #418bca;">record_voice_over</i>-<s:text name="lbl.callback"/>&nbsp;
                        </div>
                    </div>
                    <%--div class="col-md-3">
                        <div id="datatable_search1" style="float: right; margin-top: 10px;"></div>
                    </div--%>
                </div>
                <table id="tbl_call_log" class="table table-striped table-bordered display dataTable dtr-inline">
                    <thead>
                        <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                            <th class="text-center"><s:text name="lbl.call.type"/></th>
                            <th class="text-center"><s:text name="lbl.agent.name"/></th>
                            <th class="text-center"><s:text name="lbl.call.no"/></th>
                            <th class="text-center"><s:text name="lbl.call.type"/></th>
                            <th class="text-center"><s:text name="lbl.call.no"/></th>
                            <th class="text-center"><s:text name="lbl.duration"/></th>
                            <th class="text-center"><s:text name="lbl.dateAndTime"/></th>
                            <th class="text-center"><s:text name="lbl.issue.type"/></th>
                            <th class="text-center"><s:text name="lbl.callfor"/></th>
                            <th class="text-center"><s:text name="lbl.wait.time"/></th>
                            <th class="text-center"><s:text name="lbl.action"/></th>
                        </tr>
                    </thead>
                    <tbody>	   
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<style> 
    i.fa-pause-circle {
        -webkit-animation: playing 3s infinite; /* Safari and Chrome */
        animation-name: playing;
        animation-duration: 3s;
        animation-iteration-count: infinite;
        border-radius: 50px;
        color: #05b82a;
    }

    /* Safari 4.0 - 8.0 */
    @-webkit-keyframes playing {
        from {background-color: green;}
        to {background-color: lightgreen;}
    }

    /* Standard syntax */
    @keyframes playing {
        from {background-color: green;}
        to {background-color: lightgreen;}
    }
</style>
<div class="modal fade" id="modal_call_voice" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <div class="row">
                    <div class="col-md-12">
                        <audio id="play_call_voice" data-url="" data-token=""></audio>
                        <div class="col-md-8" id="block_play_call_voice">
                            <i id="_voice_stop" class="fa fa-stop-circle fa-3x hand text-danger" title="Stop"></i>
                            &nbsp;&nbsp;
                            <i id="_voice_control" class="fa fa-play-circle fa-3x text-success hand start" title="Play / Pause"></i>
                            &nbsp;&nbsp;
                            <i id="_voice_download" class="fa fa-download fa-3x text-info hand" title="Download"></i>
                            <div id="_voice_duration"></div>
                        </div>
                        <div class="col-md-4">
                            <button class="close-btn btn" style="float: right;" data-dismiss="modal">&#10006; Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //var chart_call_log = document.getElementById('div_chart_call_log');
    var call_dt_changed = false;
    $(document).ready(function () {
        $("#modal_call_voice").on('hide.bs.modal', function () {
            document.getElementById('play_call_voice').load();
            $('#block_play_call_voice #_voice_control').removeClass('fa-pause-circle fa-spin fa-spinner text-warning').addClass('fa-play-circle text-success start');
            //$("#play_call_voice").empty();
        });
        
        $('#play_call_voice').on('play', function() {
            $('#block_play_call_voice #_voice_control').removeClass('fa-play-circle text-success').addClass('fa-spin fa-spinner text-warning');
        }).on('playing', function() {
            $('#block_play_call_voice #_voice_control').removeClass('fa-spin fa-spinner').addClass('fa-pause-circle text-warning');
            /*let ap = document.getElementById('play_call_voice');
            $('#_voice_duration').html(ap.duration);
            console.log('duration: '+ap.duration);*/
        }).on('ended pause', function() {
            $('#block_play_call_voice #_voice_control').removeClass('fa-pause-circle text-warning').addClass('fa-play-circle text-success');
        });
        
        $('#block_play_call_voice').on('click', '#_voice_stop', function() {
            let ap = document.getElementById('play_call_voice');
            ap.currentTime = 0;
            ap.pause();
        }).on('click', '#_voice_download', function() {
            $.fileDownload("https://www.devapi.anoudapps.com/crm/api/ameyo/voice", 
            {"data": {"token": $('#play_call_voice').data('token')}});
            //{"command": "playVoiceLog", "data": '{"crtObjectId":"' + $('#play_call_voice').data('token') + '","targetFormat":"wav"}'}
        }).on('click', '#_voice_control', function() {
            //console.log('click');
            let ap = document.getElementById('play_call_voice');
            if($(this).hasClass('fa-play-circle')) {
                if($(this).hasClass('start')) {
                    ap.src = $('#play_call_voice').data('url');
                    $(this).removeClass('start');
                }
                ap.play();
            } else if($(this).hasClass('fa-pause-circle')) {
                ap.pause();
            }
        });
        //$("#clDate").datepicker({format: 'dd/mm/yyyy', orientation: 'top auto'});
        $('#clDate, #clDateCallWait').daterangepicker({
            timePicker: true,
            startDate: moment().startOf('day'),
            endDate: moment().endOf('day'),
            locale: {
                format: 'DD/MM/YYYY hh:mm A'
            }
        });
    <s:if test='!"N".equals(flex1)'>
        var pieData = [], legendData = [];
        /*$.each(result.aaData, function (i, o) {
         pieData.push({"name": o.key, "value": Number(o.value)});
         legendData.push(o.key);
         });*/
        chart_call_log = echarts.init(document.getElementById('div_chart_call_log'));
        chart_call_log_option = {
            title: {
                text: "",
                show: true,
                zlevel: 20,
                x: '40%',
                y: '50%',
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
                  //  saveAsImage: {show: true},
                }
            },
            calculable: false,
            series: [
                {
                    type: 'pie',
                    radius: ['45%', '60%'],
                    center: ['50%', '60%'],
                    data: pieData,
                    color: ['#44b8e2','#db9bc8','#8ad0f9','#fdc16c','#b1d653','#fa5840','#00a640','#cd5c5c','#ba55d3','#ffa500','#40e0d0','#ff69b4','#6495ed','#32cd32','#da70d6','#87cefa','#87cefa','#ff69b4','#c37cca','#8f95e7']
                }
            ]
        };
        chart_call_log.setOption(chart_call_log_option);
        window.onresize = function () {
            setTimeout(function () {
                chart_call_log.resize();
            }, 200);
        };
        activitiesFilter();
        
        $("#tbl_call_wait").DataTable({
            paging: false,
            searching: false,
            ordering: false,
            info: false,
            lengthChange: false,
            autoWidth: false,
            responsive: true,
            destory: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadCallLogWaitTimeSummary.do",
                "data": function (d) {
                    return $.extend({}, d, {'callLog.cclFlex3': '${callLog.cclFlex3}', 'callLog.cclCallDt': $('#clDate').val(), 'callLog.cclCrUid': $("#frm_search_callog #userCallId").val()});
                },
                "type": "POST",
                "datatype": "json"
            },
            columns: [
                {data: "data1"},
                {data: "integer1"},
                {data: "integer2"},
                {data: "integer3"},
                {data: "integer4"},
                {data: "integer5"},
                {data: "integer6"},
                {data: "count1"},
                {data: "count2", 
                    render: function(data, type, row, meta) {
                        data = (((row.integer2 + row.integer3 + row.integer4 + row.integer5 + row.integer6) / row.count1) * 100).toFixed(2);
                        return '<b style="color: #FF0000;">'+data+'</b>';
                    }
                }
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
                    title: 'Call wait for ' + $('#clDate').val() + ' period', 
                    filename: 'callwait_' + Math.floor(Math.random() * Math.floor(99999999999999999)),
                    exportOptions: {columns: [':visible']}
                }
            ]
        });
        
        $('#clDateCallWait').on("change", function () {
            call_dt_changed = true;
            $("#tbl_call_wait").DataTable().ajax.reload();
        });
        
        $('#clDate, #userCallId').on("change", function () {
            call_dt_changed = true;
        });
    </s:if>

        $("#tbl_call_log").DataTable({
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
                "url": APP_CONFIG.context + "/loadMissedCallData.do",
                "data": function (d) {
                    return $.extend({}, d, $("#frm_search_callog").serializeObject());
                },
                "type": "POST",
                "datatype": "json"
            },
            columns: [
                {data: "cclExtId", className: "never"},
                {data: "cclCrUid"},
                {data: "cclCallNoDesc", className: "never",
                    render: function (data, type, row, meta) {
                        return row.cclCallNo;
                    }
                },
                {data: "cclCrmTypeDesc", className: "never"},
                {data: "cclCallNo",
                    render: function (data, type, row, meta) {
                        if('filter' === type || 'sort' === type) {
                            return data;
                        }
                        if (data !== "" && data !== null) {
                            let call = "";
                            if($("#cclCrmType").val() === "" || $.trim($("#cclCallNo").val()) !== "") {
                                if(row.cclCrUid === "<%=ApplicationConstants.ABANDONED_CALL_USER_ID%>") {
                                    call = '<i class="material-icons" style="color: #FF0000;">call_end</i>';
                                } else if(row.cclCrmType === "004") { // Inbound Call
                                    call = '<i class="material-icons" style="color: #00FF00;">call_received</i>';
                                } else if(row.cclCrmType === "005") { // Outbound Call
                                    if(row.cclDurationDesc === "00:00:00") {
                                        call = '<i class="material-icons" style="color: #FF0000;">call_missed_outgoing</i>';
                                    } else {
                                        call = '<i class="material-icons" style="color: #00FF00;">call_made</i>';
                                    }
                                } else if(row.cclCrmType === "008") { // Missed Call
                                    call = '<i class="material-icons" style="color: #FF0000;">call_missed</i>';
                                } else if(row.cclCrmType === "011") { // Forwarded Call
                                    call = '<i class="material-icons" style="color: #0000FF;">settings_phone</i>';
                                }
                                /*else if(row.cclType === "I") {
                                    call = '<i class="material-icons" style="color: #00FF00;">call_received</i>';
                                } else if(row.cclType === "O") {
                                    if(row.cclDurationDesc === "00:00:00") {
                                        call = '<i class="material-icons" style="color: #FF0000;">call_missed_outgoing</i>';
                                    } else {
                                        call = '<i class="material-icons" style="color: #00FF00;">call_made</i>';
                                    }
                                } else if(row.cclType === "M") {
                                    call = '<i class="material-icons" style="color: #FF0000;">call_missed</i>';
                                }*/
                                call += '&nbsp;';
                            }
                            return call + data + '&nbsp;<i class="fa fa-phone-square hand" style="color: #3fde3f" onclick="callCustomer(\'' + data + '\');"></i>';
                        } else {
                            return data;
                        }
                    }
                },
                {data: "cclDurationDesc"},
                {data: "cclCallDt",
                    render:function(data, type, row, meta) {
                        if(type === "sort") {
                            data = moment(data, "DD/MM/YYYY hh:mm:ss A").format('YYYYMMDDHHmmss');
                        }
                        return data;
                    }
                },
                {data: "cclCallCodeDesc"},
                {data: "cclFlex2", className: "<s:if test='%{"N".equals(#session.USER_INFO.ameyoChannel)}'>never</s:if>"},
                {data: "cclFlex3", className: "<s:if test='%{"N".equals(#session.USER_INFO.ameyoChannel)}'>never</s:if>"},
                {data: "cclExtId",
                    render: function (data, type, row, meta) {
                        var btn = '<center><i class="fa fa-eye hand" title="View" style="color: #418bca;" onclick="viewCallDetails(\'' + row.cclId + '\', \'view\');"></i>';
                        if (row.cclCallRefId) {
                            btn += '&nbsp;<i class="material-icons hand" title="Response call" style="color: #418bca;" onclick="viewCallDetails(\'' + row.cclCallRefId + '\', \'missed_view\');">record_voice_over</i>';
                        }
                        if (row.cclCivilId === null) {
                            btn += '&nbsp;<i class="fa fa-address-card hand" title="Customer 360 view" style="color: #418bca;" onclick="openCustomerPopup(APP_CONFIG.context + \'/customer360.do?company=<s:property value="#session.USER_INFO.companyCode" />&mobile=' + row.cclCallNo + '&search=plugin\');"></i>';
                        }
                        else if (row.cclCivilId === "<%=ApplicationConstants.MULTIPLE_CIVIL_ID_EXIST%>") {
                            btn += '&nbsp;<i class="fa fa-address-card hand" title="Customer 360 view" style="color: #ca4141;" onclick="window.location.href=\'' + APP_CONFIG.context + '/openInsuredDetailPage.do?search=' + row.cclCallNo + '\';"></i>';
                        } else {
                            btn += '&nbsp;<i class="fa fa-address-card hand" title="Customer 360 view" style="color: #41ca45;" onclick="openCustomerPopup(APP_CONFIG.context + \'/customer360.do?company=<s:property value="#session.USER_INFO.companyCode" />&mobile=' + row.cclCallNo + '&search=plugin\');"></i>';
                        }
                        <s:if test='%{!"N".equals(#session.USER_INFO.ameyoChannel)}'>
                        if (row.cclFilePath !== "" && row.cclFilePath !== null && row.cclDurationDesc !== "00:00:00" && row.cclDurationDesc !== "") {
                            btn += '&nbsp; <span><i class="fa fa-headphones hand" title="Voice Logs" style="color: #418bca" onclick="downloadCall(\'' + row.cclFilePath + '\');"></i></span>';
                        }
                        </s:if>
                        if(row.cclFbLanguage !== null) {
                            btn += '&nbsp;<i class="fa fa-commenting hand" title="Feedback" style="color: #418bca;" onclick="openCallFeedBack(\'' + row.cclId + '\', \'feedback\');"></i>';
                        }
                        btn += '</center>'
                        return  btn;
                    }
                }
            ],
            columnDefs: [
                {targets: 7, orderable: false}
            ],
            language: {
                searchPlaceholder: "Search..."
            },
            dom: 'lBftrpi',
            tableTools: {
                "sRowSelect": "single"
            },
            "buttons": [
                {
                    extend: 'excel', 
                    text: 'Download', 
                    className: 'btn btn-primary',
                    style: 'margin-right: 4px;',
                    messageTop: function () {
                        let dt = $('#clDate').val();
                        return 'Call log for ' + dt + ' period';
                    }, 
                    filename: 'calllog_' + Math.floor(Math.random() * Math.floor(99999999999999999)),
                    exportOptions: {columns: [1, 2, 3, 5, 6, 7, 8, 9]}//columns: [ 0, ':visible' ]
                }
            ],
            initComplete: function () {
                $("#tbl_call_log tr td").css('cursor', 'default');
               // $('#datatable_search1').html($('#tbl_call_log_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            }
        });
        
        $("#search_callog").on("click", function () {
            <s:if test='!"N".equals(flex1)'>
            activitiesFilter();
            if(call_dt_changed) {
                call_dt_changed = false;
                $("#tbl_call_wait").DataTable().ajax.reload();
            }
            </s:if>
            $("#tbl_call_log").DataTable().ajax.reload();
        });
    });
    
    function openCallFeedBack(id){
        block('block_body');
        $.ajax({
            type: "POST",
            data: {"callLog.cclId": id},
            url: APP_CONFIG.context + "/openCallFeedBack.do",
            success: function (result) {
                $('#popup_custom').html(result);
                $('.popup-wrap').addClass('popup-open');
                $('#overlay').show();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function activitiesFilter() {

        /*if ($("#hid_prev_clDate").val() === $("#clDate").val()) {
         return false;
         } else {*/
        $("#hid_prev_clDate").val($("#clDate").val());
        //}
        chart_call_log.showLoading({
            text: 'Please wait!... '
        });
        chart_call_log.clear();
        $.getJSON(APP_CONFIG.context + "/loadCallLogSummaryData.do", {"callLog.cclCallDt": $("#frm_search_callog #clDate").val(), "callLog.cclCrUid": $("#frm_search_callog #userCallId").val(), "callLog.cclType": $("#frm_search_callog #cclCrmType").val()}, function () {
        }).done(function (result) {
            chart_call_log_option.legend.data = [];
            chart_call_log_option.series[0].data = [];
            $.each(result.aaData, function (i, o) {
                chart_call_log_option.legend.data.push(o.cclCrmTypeDesc);
                chart_call_log_option.series[0].data.push({"name": o.cclCrmTypeDesc + " (" + Number(o.cclCallRefId) + ")", "value": Number(o.cclCallRefId)});
                if (o.cclCrmType === '008') {
                    chart_call_log_option.legend.data.push(o.cclCrmTypeDesc + " - Answered");
                    chart_call_log_option.series[0].data.push({"name": o.cclCrmTypeDesc + " - Answered (" + Number(o.cclNotAnswered) + ")", "value": Number(o.cclNotAnswered)});
                }
            });
            chart_call_log.setOption(chart_call_log_option);
        }).fail(function () {

        }).always(function () {
            chart_call_log.hideLoading();
        });
    }

    function enableDetails() {
        var res = $("#cclCrmType").val();
        if (res === "008" || res === "AC")
            $("#showbox").show();
        else
            $("#showbox").hide();
        if (res === "AC") {
            $('#userCallId').val("");
            $('#userCallId').prop('disabled', true);
        } else {
            $('#userCallId').prop('disabled', false);
        }
    }

    /**
     * 
     * @param {type} id
     * @param {type} mode view or missed_view
     * @returns {undefined}
     */
    function viewCallDetails(id, mode) {
        block('block_body');
        $.ajax({
            type: "POST",
            data: {"callLog.cclId": id, "operation": mode},
            url: APP_CONFIG.context + "/openMissedCallDetails.do",
            success: function (result) {
//                $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm").addClass("modal-mm");
//                $('#plugin_modal_dialog .modal-content').empty().html(result);
//                $('#plugin_modal_dialog').modal('show');
                $('#popup_custom').html(result);
                $('.popup-wrap').addClass('popup-open');
                $('#overlay').show();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function downloadCall(id) {
        /*var call = '<video controls="" name="media" style="margin-top:-115px;">' +
                '<source src="' + APP_CONFIG.ameyo.url + 'ameyowebaccess/command?command=playVoiceLog&amp;data={%22crtObjectId%22:%22' + id + '%22,%22targetFormat%22:%22wav%22}" type="audio/mp3">' +
                '</video>';
        console.log(call);
        $("#play_call_voice").empty().html(call);*/
        $('#play_call_voice').data('url', APP_CONFIG.ameyo.url + 'ameyowebaccess/command?command=playVoiceLog&data={%22crtObjectId%22:%22' + id + '%22,%22targetFormat%22:%22wav%22}');
        $('#play_call_voice').data('token', id);
        $("#modal_call_voice").modal("show");
    }
    
    function callCustomer(mobile) {
        <s:if test='%{!"N".equals(#session.USER_INFO.ameyoChannel) && #session.USER_INFO.agentType == 1}'>
            doDial(mobile);
        </s:if>
        <s:else>
        var data = {"customer.mobileNo": mobile};
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/callCustomer.do",
            data: data,
            async: true,
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify(mobile + " calling...", "custom");
                } else {
                    $.notify("CTI not configured", "custom");
                }
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
            }
        });
        </s:else>
    }
</script>
