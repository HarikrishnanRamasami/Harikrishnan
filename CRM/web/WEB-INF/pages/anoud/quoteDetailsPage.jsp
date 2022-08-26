<%-- 
    Document   : quoteDetailsPage
    Created on : 7 Mar, 2017, 3:22:46 PM
    Author     : palani.rajan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style>
    .dataTable a {
        text-decoration: underline;
    }
    .table1>tbody>tr>td {
        font-family: ProximaNova-Regular;
        font-size: 14px;
        text-align: left;
        color: #333333;
    }
</style>
<s:hidden name="customer.civilId"/>
<div class="card">
    <div class="modal-header">
        <h3 class="modal-title"><s:text name="lbl.quote.details"/></h3>
        <div style="float:right;" class="btn-group">
            <button class="btn btn-danger" data-dismiss="modal" id="btn_email_close" style="margin-top: -45px">&#10006; <s:text name="btn.close"/></button>
        </div>
    </div> 
    <div class="body">
        <div class="row">
            <div class="col-md-12">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered display dataTable dtr-inline" id="quote_tbl" role="grid">
                        <thead>
                            <tr>
                                <th class="text-center"><s:text name="lbl.quote.no"/></th>
                                <th class="text-center"><s:text name="lbl.insured.name"/></th>     
                                <th class="text-center"><s:text name="lbl.product"/></th> 
                                <th class="text-center"><s:text name="lbl.scheme"/></th> 
                                <th class="text-center"><s:text name="lbl.customer.name"/></th>                                              
                                <th class="text-center"><s:text name="lbl.start.date"/></th> 
                                <th class="text-center"><s:text name="lbl.end.date"/></th> 
                                <th class="text-center"><s:text name="lbl.net.amount"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody> 
                    </table>
                    <div class="table-responsive">
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            function strtrunc(str, max, add) {
                add = add || '...';
                return (typeof str === 'string' && str.length > max ? str.substring(0, max) + add : str);
            }
            ;
            var quote_tbl;
            $(document).ready(function () {
                $('#quote_tbl').on('draw.dt', function () {
                    $('[data-toggle="tooltip"]').tooltip();
                });
                quote_tbl = $("#quote_tbl").DataTable({
                    paging: true,
                    searching: true,
                    ordering: true,
                    info: true,
                    lengthChange: false,
                    autoWidth: false,
                    pageLength: 5,
                    responsive: true,
                    destory: true,
                    "ajax": {
                        "url": APP_CONFIG.context + "/loadCustomerQuoteDetailList.do?customer.civilId=<s:property value='customer.civilId'/>&company=<s:property value='company'/>",
                        "type": "POST",
                        "contentType": "application/json",
                        "dataSrc": "aaData"
                    },
                    columns: [
                        {data: "policyNo",
                            render: function (data, type, row, meta) {
                                if (data === undefined || data === null || data === '') {
                                    return "";
                                } else {
                                    return '<a href="javascript:viewQuoteDetails(\'' + data + '\');" title="View Quote">' + data + '</a>';
                                }
                            }},
                        {data: "insuredName", "width": "18%",
                            'targets': 1,
                            "render": function (data, type, row, meta) {
                                if (type === 'display') {
                                    data1 = strtrunc(data, 25);

                                }
                                return '<span data-toggle="tooltip" title="' + data + '">' + data1 + '</span>';

                            }},
                        {data: "product"},
                        {data: "scheme"},
                        {data: "customerName"},
                        {data: "fromDate"},
                        {data: "toDate"},
                        {data: "netAmount", "className": "decimal"}
                    ],
                    dom: '<"clear">lfrtipT',
                    tableTools: {
                        "sRowSelect": "single",
                        "aButtons": []
                    },
                    initComplete: function () {
                        $("#quote_tbl tr td").css('cursor', 'default');
                        //$('#datatable_search').html($('#quote_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
                    },
                    "drawCallback": function (settings) {
                        //  $('#quote_tbl').DataTable().$('.decimal').autoNumeric('init');
                    }
                });

            });
            function viewQuoteDetails(code) {
                window.open(APP_CONFIG.context + "/AnoudAppIntegration.do?flex1=Quote&flex2=QUOT_NO&flex3=" + code);
            }
        </script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>