<%-- 
    Document   : customerNotFound
    Created on : 3 Feb, 2018, 6:31:52 PM
    Author     : ravindar.singh
--%>

<%@page import="qa.com.qic.common.util.ApplicationConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%--div class="row">
    <div class="crm-dashes" style="padding-right: 50px; padding-bottom: 50px;">
        <div class="dash-leads">
            <div class="col-md-12 col-sm-12 col-xs-12 nopad">
                <div style="min-height: 200px;" align="center">
                    <table align="center" width="60%" style="margin: 142px;">
                        <tr>
                            <td align="center">
                                <h1><font style="color: red;">Customer details not found.</font></h1>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div--%>
<style type="text/css">
    /*-------------------------------------------------------*/

    .call-app-wraper {padding:10px; margin:0px; }
    .i-call-section {/*background:#f9f9f9;*/ padding:0px; margin:0px; display: inline-block; width: 100%;}
    .xs-customer {padding:0px; margin:0px; /*border-left: solid 3px #ccc;*/}
    .call-app-wraper h2 {font-size: 22px;}
     .call-app-wraper h2.text-effect {line-height: 1.2em;}
    .call-app-wraper h3 {font-size: 22px;}

    .box-shaddow
    {
        position: relative;
    }
    .box-shaddow:before, .box-shaddow:after
    {
        z-index: -1;
        position: absolute;
        content: "";
        bottom: 15px;
        left: 10px;
        width: 50%;
        top: 80%;
        max-width:300px;
        background: #777;
        -webkit-box-shadow: 0 15px 10px #777;
        -moz-box-shadow: 0 15px 10px #777;
        box-shadow: 0 15px 10px #777;
        -webkit-transform: rotate(-3deg);
        -moz-transform: rotate(-3deg);
        -o-transform: rotate(-3deg);
        -ms-transform: rotate(-3deg);
        transform: rotate(-3deg);
    }
    .box-shaddow:after
    {
        -webkit-transform: rotate(3deg);
        -moz-transform: rotate(3deg);
        -o-transform: rotate(3deg);
        -ms-transform: rotate(3deg);
        transform: rotate(3deg);
        right: 10px;
        left: auto;
    }


    .bg-box-gold {
        min-height: 335px;
        border: 4px solid #fff;
        /* margin: 100px; */
        padding: 10px 20px;
        overflow: hidden;
        background-image: -moz-linear-gradient(top, #f4f4f4, #e6edf2);
        background-image: -webkit-gradient(linear,left top,left bottom,color-stop(0, #f4f4f4),color-stop(1, #e6edf2));
        filter: progid:DXImageTransform.Microsoft.gradient(startColorStr='#f4f4f4', EndColorStr='#e6edf2');
        -ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorStr='#f4f4f4', EndColorStr='#e6edf2')";
        -moz-box-shadow: 0 0 2px rgba(0, 0, 0, 0.35), 0 85px 180px 0 #fff, 0 12px 8px -5px rgba(0, 0, 0, 0.85);
        -webkit-box-shadow: 0 0 2px rgba(0, 0, 0, 0.35), 0 85px 810px -68px #fff, 0 12px 8px -5px rgba(0, 0, 0, 0.65);
        box-shadow: 0 0 2px rgba(0, 0, 0, 0.35), 0 85px 180px 0 #fff, 0 12px 8px -5px rgba(0, 0, 0, 0.85);
    }

    .text-effect{
        color: rgba(0,188,213,0.1);
    }
    .text-effect {
        /* the shimmer magic */
        background: -webkit-gradient(linear,left top,right top,from(#222),to(#222),color-stop(.5,#fff));
        background: -moz-gradient(linear,left top,right top,from(#222),to(#222),color-stop(.5,#fff));
        background: gradient(linear,left top,right top,from(#222),to(#222),color-stop(.5,#fff));
        -webkit-background-size: 125px 100%;
        -moz-background-size: 125px 100%;
        background-size: 125px 100%;
        -webkit-background-clip: text;
        -moz-background-clip: text;
        background-clip: text;
        -webkit-animation-name: text-effect;
        -moz-animation-name: text-effect;
        -webkit-animation-name: text-effect;
        animation-name: text-effect;
        -webkit-animation-duration: 2s;
        -moz-animation-duration: 2s;
        -webkit-animation-duration: 2s;
        animation-duration: 2s;
        -webkit-animation-iteration-count: infinite;
        -moz-animation-iteration-count: infinite;
        -webkit-animation-iteration-count: infinite;
        animation-iteration-count: infinite;
        background-repeat: no-repeat;
        background-position: 0 0;
        background-color: #222;

    }

    @-moz-keyframes text-effect {
        0% {
            background-position: top left;
        }

        100% {
            background-position: top right;
        }
    }

    @-webkit-keyframes text-effect {
        0% {
            background-position: top left;
        }

        100% {
            background-position: top right;
        }
    }

    @-o-keyframes text-effect {
        0% {
            background-position: top left;
        }

        100% {
            background-position: top right;
        }
    }

    @-ms-keyframes text-effect {
        0% {
            background-position: top left;
        }

        100% {
            background-position: top right;
        }
    }

    @keyframes text-effect {
        0% {
            background-position: top left;
        }

        100% {
            background-position: top right;
        }
    }

    .blink_me {
        padding: 2px;

        -webkit-animation-name: blinker;
        -webkit-animation-duration: 1s;
        -webkit-animation-timing-function: steps(1, start);
        -webkit-animation-iteration-count: infinite;

        -moz-animation-name: blinker;
        -moz-animation-duration: 1s;
        -moz-animation-timing-function: steps(1, start);
        -moz-animation-iteration-count: infinite;

        animation-name: blinker;
        animation-duration: 1s;
        animation-timing-function: steps(1, start);
        animation-iteration-count: infinite;
    }

    @-moz-keyframes blinker {  
        0% { color: none; }
        50% { color: #57a100 }
        100% { color: none; }
    }

    @-webkit-keyframes blinker {  
        0% { color: none; }
        50% { color: #57a100 }
        100% { color: none; }
    }

    @keyframes blinker {  
        0% { color: none; }
        50% { color: #57a100 }
        100% { color: none; }
    }


    .inln-blk {display:inline-block;}

    .material-icons.md-18 { font-size: 18px; }
    .material-icons.md-24 { font-size: 24px; }
    .material-icons.md-36 { font-size: 36px; }
    .material-icons.md-48 { font-size: 48px; }

    .red-color {color:#ff0003;}
    .green-color {color:#27c700} 

    .row-eq-height {
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
    }


    @media (max-width: 767px) {
        .row-eq-height {
            display: -webkit-box;
            display: -webkit-inline-flex;
            display: -ms-flexbox;
            display: block;
        }
    }

    .i-call-section .pagination {margin:0px;}
    .i-call-section .v-align {vertical-align: middle; line-height: 2.5em;}

    .right-sec input[type='text'] {

        color: #333;
        width: 100%;
        box-sizing: border-box;
        letter-spacing: 1px;
        border: 1px solid #ccc;
        padding: 5px 14px 7px;
        transition: 0.4s;
    }

    .link-bttn{
        /* width:180px;*/
        display: inline-block;
        height: auto;
        padding:8px 12px;
        color:#fff;
        background:#8BC3A3;
        border:none;
        border-radius:3px;
        outline: none;
        -webkit-transition: all 0.3s;
        -moz-transition: all 0.3s;
        transition: all 0.3s;
        margin:auto;
        box-shadow: 0px 1px 4px rgba(0,0,0, 0.10);
        -moz-box-shadow: 0px 1px 4px rgba(0,0,0, 0.10);
        -webkit-box-shadow: 0px 1px 4px rgba(0,0,0, 0.10);
    }

    .link-bttn:hover{
        background:#111;
        color: white;
        border:none;
    }

    .link-bttn:active{
        opacity: 0.9;
    }
    .red-str {font-size:22px; color:#B20003; padding:0px 5px; vertical-align: top; display: inline-block;}
    .call-app-wraper .hdng-one {font-size:18px; margin-top:0px; margin-bottom: 0px;}
    .call-app-wraper .hdng-two {font-size:18px;}
    .call-app-wraper .hdng-three {font-size:20px;}
    /*------------------------------------------------------------*/
</style>

<div class="container">  
    <div class="call-app-wraper">
        <h2 class="text-effect text-center"> <s:text name="lbl.incoming.call"/> <br /><span class="blink_me"><s:property value="mobile"/></span></h2>  
        <br />
        <div class="">
        </div>
        <div class="row row-eq-height">
            <!-- Left section starts --->
            <div class="col-md-8  col-sm-12 col-xs-12">
                <div class="i-call-section">
                    <div class="panel panel-default panel-table">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col col-xs-6">
                                    <h3 class="hdng-one"><s:text name="lbl.call.log.past.thirty.days"/></h3>
                                </div>
                                <div class="col col-xs-6 text-right"></div>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive11">
                                <table id="tbl_call_log" class="table table-striped table-bordered display dataTable dtr-inline" width="100%">
                                    <thead>
                                        <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                            <th class="text-center"><s:text name="lbl.agent.name"/></th>
                                            <th class="text-center"><s:text name="lbl.type"/></th>
                                            <th class="text-center"><s:text name="lbl.duration"/></th>
                                            <th class="text-center"><s:text name="lbl.dateAndTime"/></th>
                                            <th class="text-center"><s:text name="lbl.issue.type"/></th>
                                            <th class="text-center"><s:text name="lbl.callfor"/></th>
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
            <!-- Left section ends --->
            <!-- right section starts here -->
            <div class="col-md-4 col-sm-12 col-xs-12">
                <div class="xs-customer box-shaddow11 bg-box-gold" style="min-height: 275px;">
                    <h3><s:text name="lbl.existing.customer"/> </h3><br />
                    <div class="right-sec">
                        <div class="input-group">
                            <input type="text" id="txt_customer_search" class="form-control" required="true" title="" placeholder="<s:text name="lbl.Mobile.or.civil.id"/>"/>
                            <s:hidden name="civilId" id="civilId" />
                            <s:hidden name="refName" id="refName" />
                            <span class="input-group-addon" style="border: 0;">
                                <button type="button" id="btn_view_customer" class="btn btn-primary disabled" disabled="true"><i class="fa fa-address-card"></i></button>
                            </span>
                        </div>
                        <br /><br />
                        <div id="block_replace_no"></div>
                        <br />
                        <center><button type="button" id="btn_link_number" class="btn btn-success disabled" disabled="true"><s:text name="btn.link.number"/></button></center>
                        <br /><br />
                        <p><span class="red-str">*</span><s:text name="lbl.transfer.entire.call.history"/>.</p>
                    </div>
                </div>
            </div>
            <!-- right section starts here -->
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        let range = moment().subtract(30, 'days').format('DD/MM/YYYY hh:mm:ss A') + " - " + moment().format('DD/MM/YYYY hh:mm:ss A');
        console.log('Loading call log range from ' + range);
        $("#tbl_call_log").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            pageLength: 10,
            responsive: true,
            processing: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadMissedCallData.do",
                "data": function (d) {
                    return $.extend({}, d, {"callLog.cclCallNo": "${mobile}", "callLog.cclCallDt": range});
                },
                "type": "POST",
                "datatype": "json"
            },
            columns: [
                {data: "cclCrUid"},
                {data: "cclCallNo",
                    render: function (data, type, row, meta) {
                        if ('filter' === type || 'sort' === type) {
                            return data;
                        }
                        let call = "";
                        if (row.cclCrUid === "<%=ApplicationConstants.ABANDONED_CALL_USER_ID%>") {
                            call = '<i class="material-icons" style="color: #FF0000;">call_end</i>';
                        } else if (row.cclCrmType === "004") { // Inbound Call
                            call = '<i class="material-icons" style="color: #00FF00;">call_received</i>';
                        } else if (row.cclCrmType === "005") { // Outbound Call
                            if (row.cclDurationDesc === "00:00:00") {
                                call = '<i class="material-icons" style="color: #FF0000;">call_missed_outgoing</i>';
                            } else {
                                call = '<i class="material-icons" style="color: #00FF00;">call_made</i>';
                            }
                        } else if (row.cclCrmType === "008") { // Missed Call
                            call = '<i class="material-icons" style="color: #FF0000;">call_missed</i>';
                        } else if (row.cclCrmType === "011") { // Forwarded Call
                            call = '<i class="material-icons" style="color: #0000FF;">settings_phone</i>';
                        }
                        call += '&nbsp;';
                        return call;
                    }
                },
                {data: "cclDurationDesc"},
                {data: "cclCallDt",
                    render: function (data, type, row, meta) {
                        if (type === "sort") {
                            data = moment(data, "DD/MM/YYYY hh:mm:ss A").format('YYYYMMDDHHmmss');
                        }
                        return data;
                    }
                },
                {data: "cclCallCodeDesc"},
                {data: "cclFlex2", className: "<s:if test='%{"N".equals(#session.USER_INFO.ameyoChannel)}'>never</s:if>"}
            ],
            order: [3, 'desc'],
            columnDefs: [
                {targets: 1, orderable: false}
            ],
            language: {
                search: "_INPUT_",
                searchPlaceholder: "Search..."
            },
            tableTools: {
                "sRowSelect": "single"
            },
            initComplete: function () {
                $("#tbl_call_log tr td").css('cursor', 'default');
            }
        });

        $('#btn_link_number').on('click', function () {
            block('block_body');
            let d = {"company": "<s:property value="#session.USER_INFO.companyCode"/>", "callLog.cclCivilId": $("#civilId").val(), "callLog.cclRefName": $("#refName").val(), "callLog.cclCallNo": "${mobile}"};
            if($("#replaceWith").length > 0) {
                d["callLog.cclType"] = $("#replaceWith").val();
            }
            console.log('Link number data: ', d);
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + '/linkCustomerNewNumber.do',
                data: d,
                success: function (result) {
                    console.log(result);
                    if (result.messageType === "S") {
                        $('#btn_link_number, #btn_view_customer').prop('disabled', true);
                        $('#btn_link_number, #btn_view_customer').removeClass('active').addClass('disabled');
                        $.notify("${mobile} is linked to civill id " + $("#civilId").val(), "success");
                        setTimeout(function() {
                            window.location.href = APP_CONFIG.context + '/customer360.do?company=<s:property value="#session.USER_INFO.companyCode" />&search=plugin&civilid=' + $("#civilId").val();
                        }, 500);
                    } else {
                        $.notify(result.message, "error");
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        });

        $('#btn_view_customer').on('click', function () {
            block('block_body');
            $.ajax({
                type: 'POST',
                url: APP_CONFIG.context + '/customer360.do',
                data: {"company": "<s:property value="#session.USER_INFO.companyCode"/>", "civilid": $("#civilId").val(), "search": "modal"},
                success: function (result) {
                    $('#plugin_modal_dialog').modals().mx(result);
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        });

        var has_valid_values = false, has_values_modified = false;
        $('#txt_customer_search').autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: APP_CONFIG.context + '/loadInsuredDetailList.do',
                    data: {
                        flex1: request.term,
                        start: 0,
                        length: 10
                    },
                    success: function (data) {
                        var rows = [];
                        if (data.aaData === null) {
                            has_valid_values = false;
                        } else {
                            $.each(data.aaData, function (i, v) {
                                rows.push({label: v.name + '-' + v.mobileNo, value: v.civilId, key: v.name, mobileNo: v.mobileNo, mobileNo1: v.mobileNo1, mobileNo2: v.mobileNo2});
                            });
                        }
                        response(rows);
                    },
                    error: function (xhr, status, error) {
                        displayAlert('E', error);
                    },
                    complete: function () {
                        unblock();
                    }
                });
            },
            select: function (event, ui) {
                has_valid_values = true;
                $('#civilId').val(ui.item.value);
                $('#refName').val(ui.item.key);
                $(this).val(ui.item.key + '-' + ui.item.value);
                $('#btn_link_number, #btn_view_customer').prop('disabled', false);
                $('#btn_link_number, #btn_view_customer').removeClass('disabled').addClass('active');
                console.log('mobileNo: ', ui.item.mobileNo, 'mobileNo1: ', ui.item.mobileNo1, 'mobileNo2: ', ui.item.mobileNo2);
                if(ui.item.mobileNo && ui.item.mobileNo1 && ui.item.mobileNo2) {
                    let opt = 'Replace this ';
                    opt += '<select id="replaceWith" class="form-control">' +
                    '<option value="MOBILE_NO">Mobile 1 - ' + ui.item.mobileNo + '</option>' +
                    '<option value="MOBILE_NO_1">Mobile 2 - ' + ui.item.mobileNo1 + '</option>' +
                    '<option value="MOBILE_NO_2" selected>Mobile 3 - ' + ui.item.mobileNo2 + '</option>' +
                    '</select>';
                    console.log(opt);
                    $('#block_replace_no').html(opt);
                }
                event.preventDefault();
            },
            change: function (event, ui) {
                has_values_modified = true;
            },
            open: function () {
                $(this).removeClass('ui-corner-all ui-corner-top');
            },
            close: function () {
                $(this).removeClass('ui-corner-top ui-corner-all');
            },
            minLength: 3,
            autoFocus: true
        }).on('blur', function (event) {
            if (!has_valid_values && has_values_modified) {
                $(this).val('');
                $('#civilId').val('');
                $('#refName').val('');
                $('#block_replace_no').html('');
                $('#btn_link_number, #btn_view_customer').prop('disabled', true);
                $('#btn_link_number, #btn_view_customer').removeClass('active').addClass('disabled');
            }
            has_valid_values = false;
            has_values_modified = false;
        });
    });
</script>