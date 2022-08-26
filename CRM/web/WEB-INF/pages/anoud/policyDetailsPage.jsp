<%-- 
    Document   : policyDetailsPage
    Created on : 7 Mar, 2017, 4:13:00 PM
    Author     : palani.rajan
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<s:hidden name="customer.civilId"/>
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
<div class="card">
    <div class="modal-header">
        <h3 class="modal-title"><s:text name="lbl.policy.details"/></h3>
        <div style="float:right;" class="">
            <button class="btn btn-danger" data-dismiss="modal" id="btn_email_close" style="margin-top: -65px">&#10006; <s:text name="btn.close"/></button>
        </div>
    </div>
    <div class="body">
        <div class="row">
            <div class="col-md-12">
                <div class="modal-title"><h3 style="color: green;"><s:text name="lbl.active.policy"/></h3></div>
                <div class="table-responsive">
                    <table class="table table-striped table-bordered display dataTable dtr-inline" id="policy_tbl" style="width: 100%;">
                        <thead>
                            <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                <th class="text-center"><s:text name="lbl.policy.no"/></th>
                                <th class="text-center"><s:text name="lbl.insured.name"/></th>     
                                <th class="text-center"><s:text name="lbl.product"/></th> 
                                <th class="text-center"><s:text name="lbl.scheme"/></th> 
                                <th class="text-center"><s:text name="lbl.customer.name"/></th>                                              
                                <th class="text-center"><s:text name="lbl.start.date"/></th> 
                                <th class="text-center"><s:text name="lbl.end.date"/></th> 
                                <th class="text-center"><s:text name="lbl.business.source"/></th>
                                <th class="text-center"><s:text name="lbl.net.amount"/></th>
                                <th class="text-center"><s:text name="lbl.loss.ratio"/></th>
                                <th class="text-center"><s:text name="lbl.status"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody> 
                    </table>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="modal-title"><h3 style="color: red;"><s:text name="lbl.inactive.policy"/></h3></div>
                <div class="table-responsive">
                    <table class="table table-striped table-bordered display dataTable dtr-inline" id="policy_inActive_tbl">
                        <thead>
                            <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                <th class="text-center"><s:text name="lbl.policy.no"/></th>
                                <th class="text-center"><s:text name="lbl.insured.name"/></th>     
                                <th class="text-center"><s:text name="lbl.product"/></th> 
                                <th class="text-center"><s:text name="lbl.scheme"/></th> 
                                <th class="text-center"><s:text name="lbl.customer.name"/></th>                                              
                                <th class="text-center"><s:text name="lbl.start.date"/></th> 
                                <th class="text-center"><s:text name="lbl.end.date"/></th> 
                                <th class="text-center"><s:text name="lbl.business.source"/></th>
                                <th class="text-center"><s:text name="lbl.net.amount"/></th>
                                <th class="text-center"><s:text name="lbl.loss.ratio"/></th>
                                <th class="text-center"><s:text name="lbl.status"/></th>
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
<script type="text/javascript">
    function strtrunc(str, max, add) {
        add = add || '...';
        return (typeof str === 'string' && str.length > max ? str.substring(0, max) + add : str);
    }

    var policy_tbl;
    var activepolicy = [];
    var inactivepolicy = [];
    function loadpolicyDataList() {
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadCustomerPolicyDetailList.do?customer.civilId=<s:property value='customer.civilId'/>&company=<s:property value='company'/>",
            data: {},
            async: true,
            success: function (result) {
                aaDa = result.aaData;
                var list = result.aaData;
                if (list !== null && list.length > 0) {
                    for (var i = 0; i < list.length; i++) {
                        if (list[i].status === "Active") {
                            activepolicy.push(list[i]);
                        } else {
                            inactivepolicy.push(list[i]);
                        }
                    }
                    reloadDataTable('#policy_tbl', activepolicy);
                    reloadDataTable('#policy_inActive_tbl', inactivepolicy);
                }
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {

            }
        });
    }

    $(document).ready(function () {
        $('#policy_tbl').on('draw.dt', function () {
            $('[data-toggle="tooltip"]').tooltip();
        });

        policy_tbl = $("#policy_tbl").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            columns: [
                {data: "policyNo",
                    render: function (data, type, row, meta) {
                        if (data === undefined || data === null || data === '') {
                            return "";
                        } else {
                            return '<a href="javascript:viewPolicyDetails(\'' + data + '\');" title="View Policy">' + data + '</a>';
                        }
                    }},
                {data: "insuredName",
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
                {data: "businessSource"},
                {data: "netAmount", "className": "decimal"},
                {data: "lossRatio"},
                {data: "status"}
            ],
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function () {
                $("#policy_tbl tr td").css('cursor', 'default');
                //$('#datatable_search').html($('#policy_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
        });
        $('#policy_inActive_tbl').on('draw.dt', function () {
            $('[data-toggle="tooltip"]').tooltip();
        });
        $("#policy_inActive_tbl").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            columns: [
                {data: "policyNo",
                    render: function (data, type, row, meta) {
                        if (data === undefined || data === null || data === '') {
                            return "";
                        } else {
                            return '<a href="javascript:viewPolicyDetails(\'' + data + '\');" title="View Policy">' + data + '</a>';
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
                {data: "businessSource"},
                {data: "netAmount", "className": "decimal"},
                {data: "lossRatio"},
                {data: "status"}
            ],
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function () {
                $("#policy_inActive_tbl tr td").css('cursor', 'default');
                //$('#datatable_search1').html($('#policy_inActive_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
        });
        loadpolicyDataList();
    });

    function viewPolicyDetails(code) {
        window.open(APP_CONFIG.context + "/AnoudAppIntegration.do?flex1=Policy&flex2=POLICY_NO&flex3=" + code);
    }

    function reloadDataTable(dtId, jData) {
        var dt = $(dtId).DataTable();
        dt.clear();
        for (var i = 0; i < jData.length; i++) {
            dt.row.add(jData[i]);
        }
        dt.draw(false);
        // $(dtId).find('.decimal').autoNumeric('init');
        return false;
    }
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>