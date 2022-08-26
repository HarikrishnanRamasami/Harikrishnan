<%-- 
    Document   : WhatsAppTransactionHistory
    Created on : May 19, 2021, 17:51:55 PM
    Author     : Mahadhir
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
</style>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/whatsapp.css">
<div class="col-md-12 right-pad">
    <div class="dash-leads" style="border-top:0!important">        
        <div class="my-bord bor2" style="height: 97px; box-shadow: 1px 3px 2px #dedede;">
            <s:form class="form-inline" id="frm_whatsapp_txn_hist_search" name="frm_whatsapp_txn_hist_search" method="post" theme="simple">
                <div class="col-md-12 Tmar" style="margin-bottom: 10px;"> 
                    <div class="row">
                        <div class="form-group padR">
                            <label><s:text name="lbl.from"/></label>
                            <s:textfield name="txnHist.whFromDate" id="datepicker_from" cssClass="form-control calicon" style="background: #fff; cursor: pointer; padding: 6px 12px; border: 1px solid #ccc;"/>
                        </div>
                        <div class="form-group padR">
                            <label><s:text name="lbl.to"/></label>
                            <s:textfield name="txnHist.whToDate" id="datepicker_to" cssClass="form-control calicon" style="background: #fff; cursor: pointer; padding: 6px 12px; border: 1px solid #ccc;"/>
                        </div>                        
                        <div class="form-group padR">
                            <label for="pwd"><s:text name="lbl.call.no"/></label>
                            <s:textfield name="txnHist.whMobileNo" id="whMobileNo" class="form-control input-sm" cssStyle="width: 121px;" maxlength="20"/>
                        </div>
                        <div class="form-group padR">
                            <label for="pwd"><s:text name="lbl.user"/></label>
                            <s:select class="form-control" name="txnHist.whAssignedTo" id="search_txn_AssignedTo" list="userList" listKey="key" listValue="value" /> 
                        </div>
                        <div class="form-group padR">
                            <button type="button" id="search_txn_history" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>                            
                        </div>
                    </div>
                </div>
                <div class="col-md-12 Tmar">
                    <hr style="margin: 0px 0px !important; padding: 3px 0px;" />
                </div>
                <div class="col-md-12 Tmar" style="margin-bottom: 10px;">
                    <div class="row">
                        <div class="form-group padR">
                            <label class="text-black"><s:text name="lbl.whatsapp.search.chat"/></label>
                            <s:textfield name="txnHist.whName" id="txn_hist_message" class="form-control input-sm"  cssStyle="width: 334px;"/>
                        </div>
                        <div class="form-group padR">
                            <s:radio name="txnHist.whFlex01" list="#{'I': 'Inbound','O' :'Outbound'}" id="whatsapp_dash_summary_checkbox" cssStyle="margin: 0px 1px;" />
                        </div>
                        <div class="form-group padR">
                            <button type="button" id="search_txn_history_msg" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
                            &nbsp;<i style="font-size: 12px;font-weight: bold">(<s:text name="lbl.whatsapp.past.four.month"/>)</i>
                        </div>
                    </div>
                </div>
            </s:form>
            <table id="txn_history_tbl" class="table table-striped table-bordered display dataTable dtr-inline">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                        <th><s:text name="lbl.common.name"/></th>
                        <th><s:text name="lbl.contact"/></th>
                        <th><s:text name="lbl.date"/></th>
                        <th><s:text name="lbl.username"/></th>
                        <th><s:text name="lbl.common.closed.dt"/></th>
                        <th class="no-sort" style="text-align: center;"><s:text name="lbl.action"/></th> 
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="popup-box chat-popup" id="qnimate_txn_hist" data-mobile="" data-lastLoad="0">    
    <div id="frame">
        <div class="content">
            <div class="contact-profile">
                <div class="chat-header text-center"><h4><span id="chat_num"></span><span id="chat_contact"></span><span class="pull-right"> <a data-widget="remove" id="closeChat_whatsapp_hist" class="" type="button" style="border-radius: 0; text-white"><i class="glyphicon glyphicon-remove text-white"></i></a></span></h4>   </div>
            </div>
            <div class="messages">
                <ul id="print_msgs"> </ul> 
                <div class="modal fade summary-page-two" id="summary-page-two" data-keyboard="false" data-backdrop="false" >
                    <div class="lab-modal-body ">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"><s:text name="btn.close"/></span></button>
                        <div class="clearfix"></div> 
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $(function () {
            $("#datepicker_from").datetimepicker({format: 'DD/MM/YYYY'});
        });
        $(function () {
            $("#datepicker_to").datetimepicker({format: 'DD/MM/YYYY'});
        });
        $('input:radio[id=whatsapp_dash_summary_checkboxI]').prop('checked', true);
        var url = APP_CONFIG.context + "/loadWhatsAppHistoryData.do?" + $('#frm_whatsapp_txn_hist_search').serialize();
        var txn_history_tbl = $("#txn_history_tbl").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 10,
            responsive: true,
            destory: true,
            processing: false,
            serverSide: false,
            "ajax": {
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "whName", className: "never"},
                {data: "whMobileNo", className: "col-md-5",
                    render: function (data, type, row, meta) {
                        if (row.whName !== null)
                            return row.whName + ' - ' + row.whMobileNo;
                        else
                            return row.whMobileNo;
                    }
                },
                {data: "whDate", className: "col-md-2",
                    render: function (data, type, row, meta) {
                        if (type === "sort") {
                            data = moment(data).format('YYYYMMDDHHmm');
                        } else {
                            data = moment(data).format('DD/MM/YYYY HH:mm');
                        }
                        return data;
                    }
                },
                {data: "whAssignedTo", className: "col-md-2"},
                {data: "whCloseDt", className: "col-md-2",
                    render: function (data, type, row, meta) {
                        if (type === "sort") {
                            data = moment(data).format('YYYYMMDDHHmm');
                        } else {
                            data = moment(data).format('DD/MM/YYYY HH:mm');
                        }
                        return data;
                    }
                },
                {data: "",
                    render: function (data, type, row, meta) {
                        return '<center><i class="fa fa-comments-o hand" title="View Message" style="color: #418bca;" onclick="viewChatMessage(\'' + row.whMobileNo + '\',\'' + row.whTxnId + '\',\'' + encodeURIComponent(row.whName) + '\');"></i></center>';
                    }
                }
            ],
            dom: 'T<"clear">lfrtip',
            "order": [
                [2, "desc"]
            ],
            initComplete: function () {
                $("#txn_history_tbl tr td").css('cursor', 'default');
            },
            tableTools: {
                "sRowSelect": "single",
                "aButtons": [],
            }
        });

        $("#search_txn_history").on("click", function () {
            if (($('#datepicker_from').val() != '' && $('#datepicker_to').val() == '') || ($('#datepicker_to').val() != '' && $('#datepicker_from').val() == '')) {
                alert('Please Select Valid date');
                return false;
            }
            $("#txn_history_tbl").reloadDT(APP_CONFIG.context + "/loadWhatsAppHistoryData.do?" + $('#frm_whatsapp_txn_hist_search').serialize());
        });

        $("#search_txn_history_msg").on("click", function () {
            if (($('#txn_hist_message').val() == '')) {
                alert('Please enter message text');
                return false;
            }

            if ($('#txn_hist_message').val().length >= 5) {
                $("#txn_history_tbl").reloadDT(APP_CONFIG.context + "/loadWhatsAppHistoryDataByMessage.do?" + $('#frm_whatsapp_txn_hist_search').serialize());
            } else {
                alert('Please enter minimum 5 charecters');
                return false;
            }
        });
        $("#closeChat_whatsapp_hist").click(function () {
            $('#qnimate_txn_hist').removeClass('popup-box-on');
        });
    });

    function viewChatMessage(mobileNo, txnId, whName) {
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadWhatsAppLog.do",
            data: {"txn.wtTxnId": txnId, "txn.wtMobileNo": mobileNo},
            success: function (result) {
                $('#modal_dialog').modal('hide');
                $("#qnimate_txn_hist #print_msgs").empty();
                $.each(result.aaData, function (i, v) {
                    processMessage(v);
                });
                $('#qnimate_txn_hist #chat_num').html(mobileNo);
                if (whName !== 'null' && whName !== '') {
                    $('#qnimate_txn_hist #chat_contact').html(' - ' + fixedEncodeURIComponent(whName));
                }
                $('#qnimate_txn_hist').addClass('popup-box-on');
                scrollDown();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
            }
        });
    }
</script>