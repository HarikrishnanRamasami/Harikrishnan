<%-- 
    Document   : claimDetailsPage
    Created on : 7 Mar, 2017, 4:31:50 PM
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
        <h3 class="modal-title"><s:text name="lbl.claim.details"/></h3>
        <div style="float:right;" class="btn-group">
            <button class="btn btn-danger" data-dismiss="modal" id="btn_email_close" style="margin-top: -45px">&#10006; <s:text name="btn.close"/></button>
        </div>
    </div>
    <div class="body">
        <div class="row">
            <div class="col-md-12">
                <div class="modal-title"><h3 style="color: green;"><s:text name="lbl.active.claim"/></h3></div>
                <div class="table-responsive">
                    <table class="table table-striped table-bordered display dataTable dtr-inline" id="claim_tbl" role="grid">
                        <thead>
                            <tr>
                                <th class="text-center"><s:text name="lbl.ref.no"/></th>
                                <th class="text-center"><s:text name="lbl.claim.no"/></th>     
                                <th class="text-center"><s:text name="lbl.policy.no"/></th> 
                                <th class="text-center"><s:text name="lbl.int.date"/></th>                                           
                                <th class="text-center"><s:text name="lbl.Garage.Name"/></th> 
                                <th class="text-center"><s:text name="lbl.claim.status"/></th>    
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
                <div class="modal-title"><h3 style="color: red;"><s:text name="lbl.closed.claim"/></h3></div>
                <div class="table table-striped table-bordered display dataTable dtr-inline">
                    <table class="table table-striped table-bordered display dataTable dtr-inline" id="claim_Closed_tbl" role="grid">
                        <thead>
                            <tr>
                                <th class="text-center"><s:text name="lbl.ref.no"/></th>
                                <th class="text-center"><s:text name="lbl.claim.no"/></th>     
                                <th class="text-center"><s:text name="lbl.policy.no"/></th> 
                                <th class="text-center"><s:text name="lbl.int.date"/></th>                                           
                                <th class="text-center"><s:text name="lbl.Garage.Name"/></th> 
                                <th class="text-center"><s:text name="lbl.claim.status"/></th>    
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
    var claim_tbl;
    var activeClaims = [];
    var inactiveClaims = [];
    function loadClaimDataList() {
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadCustomerClaimDetailList.do?customer.civilId=<s:property value='customer.civilId'/>&company=<s:property value='company'/>",
            data: {},
            async: true,
            success: function (result) {
                var list = result.aaData;
                if (list !== null && list.length > 0) {
                    for (var i = 0; i < list.length; i++) {
                        if (list[i].claimStatus === "Active") {
                            activeClaims.push(list[i]);
                        } else {
                            inactiveClaims.push(list[i]);
                        }
                    }
                    reloadDataTable('#claim_tbl', activeClaims);
                    reloadDataTable('#claim_Closed_tbl', inactiveClaims);
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
        console.log("asfafaf");
        claim_tbl = $("#claim_tbl").DataTable({
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
                {data: "onlineRef"},
                {data: "claimNo",
                    render: function (data, type, row, meta) {
                        if (data === undefined || data === null || data === '') {
                            return "";
                        } else {
                            return '<a href="javascript:viewClaimDetails(\'' + data + '\');" title="View Claim">' + data + '</a>';
                        }
                    }},
                {data: "policyNo"},
                {data: "intimatedDate"},
                {data: "garageName"},
                {data: "status"},
            ],
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function () {
                $("#claim_tbl tr td").css('cursor', 'default');
                //$('#datatable_search').html($('#claim_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
            "drawCallback": function (settings) {
                //  $('#claim_tbl').DataTable().$('.decimal').autoNumeric('init');
            }
        });


        $("#claim_Closed_tbl").DataTable({
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
                {data: "onlineRef"},
                {data: "claimNo"},
                {data: "policyNo"},
                {data: "intimatedDate"},
                {data: "garageName"},
                {data: "status"},
            ],
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function () {
                $("#claim_Closed_tbl tr td").css('cursor', 'default');
                //$('#datatable_search1').html($('#claim_Closed_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
            "drawCallback": function (settings) {
                //  $('#claim_tbl').DataTable().$('.decimal').autoNumeric('init');
            }
        });


        loadClaimDataList();

    });

    function viewClaimDetails(code) {
        window.open(APP_CONFIG.context + "/AnoudAppIntegration.do?flex1=Claim&flex2=CLM_NO&flex3=" + code);
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