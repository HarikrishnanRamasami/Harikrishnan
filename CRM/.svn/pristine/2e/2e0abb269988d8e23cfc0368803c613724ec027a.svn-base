<%-- 
    Document   : insuredDetailsPage
    Created on : 5 Apr, 2017, 10:43:49 AM
    Author     : palani.rajan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 right-pad" id="block_body">
    <div class="dash-leads" style="border-top:0!important">
        <div class="my-bord">
            <h3><s:text name="lbl.contacts"/></h3>
        </div>
        <div class=" bor2">
            <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@APP_TYPE_QLM.equals(#session.USER_INFO.appType)}'>
                <div class="col-sm-12 col-md-9 Tmar">               
                    <s:form class="form-inline" id="frm_search" name="frm_search" method="post" theme="simple">
                        <div class="form-group padR">
                            <label for="email"><s:text name="lbl.search.by.customer.type"/></label>
                            <s:select  name="customer.source" list="customerTypeList" listKey="key" listValue="value" cssClass="form-control" id="source" onchange="customerSearch();"/>
                        </div>
                    </s:form>
                </div>
            </s:if>
            <s:if test='%{#session.USER_INFO.userAdminYn <= 1}'>
            <div class="col-sm-12 col-md-3 board-icons1 Rbtn">
                <button class="btn btn-warning tmargin cbtn" onclick="openForm();"><s:text name="btn.add.contact"/></button>
            </div>
            </s:if>
            <!--div><button class="btn btn-warning lead-tab" style="width: 100px;float: right; margin-top: -6px" onclick="openForm();">Add Contact</button></div-->
            <table id="insured_tbl" class="table table table-striped table-bordered display">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                        <th class="text-center"><s:text name="lbl.insured.name"/></th>
                        <th class="text-center"><s:text name="lbl.civil.id"/></th>
                        <th class="text-center"><s:text name="lbl.mobile.no"/></th>
                        <th class="text-center"><s:text name="lbl.alternative.no"/></th>
                        <th class="text-center"><s:text name="lbl.email.id"/></th>
                        <th class="text-center"><s:text name="lbl.nationality"/></th>
                        <th class="text-center <s:if test='%{#session.USER_INFO.userAdminYn > 1}'>never</s:if>"><s:text name="lbl.action"/></th>
                    </tr>
                    </tr>
                </thead>
                <tbody>	   
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        block('block_body');
        var insured_tbl = $("#insured_tbl").DataTable({
            //paging: true,
            searching: true,
            ordering: true,
            info: false,
            //  "destroy": true,
            "lengthChange": false,
            "pageLength": 20,
            "responsive": true,
            autoWidth: false,
            "serverSide": true,
            "processing": true,
            deferRender: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadInsuredDetailList.do",
                "datatype": "json",
                data: function(d) {
                    return $.extend({}, d, {flex1: "${search}", "customer.source": $("#source option:selected").val()});
                }
            },
            columns: [
                {data: "name"},
                {data: "civilId"},
                {data: "mobileNo"},
                {data: "mobileNo1",
                    render: function (data, type, row, meta) {
                        return (row.mobileNo1 ? row.mobileNo1 + (row.mobileNo2 ? ', ' : '') : '') + (row.mobileNo1 ? row.mobileNo2 : '');
                    }
                },
                {data: "emailId"},
                {data: "nationality"},
                {data: "name",
                    render: function (data, type, row, meta) {
                        return  '<i class="fa fa-pencil" title="Edit" style="color: #418bca;" onclick="viewInsured(\'' + row.civilId + '\');"></i>' +
                                '&nbsp;<i class="fa fa-address-card" title="Customer 360 view" style="color: #418bca;" onclick="window.location.href = APP_CONFIG.context + \'/customer360.do?company=<s:property value="#session.USER_INFO.companyCode" />&civilid=' + row.civilId + '&search=view\';"></i>';
                        //'&nbsp;<i class="fa fa-address-card"  title="Customer 360 view" style="color: #418bca;" onclick="viewDetails(\'' + row.civilId + '\',\'' + row.name + '\')"></i>';
                    }
                },
            ],
            dom: '<"clear">lfltipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function () {
                $("#insured_tbl tr td").css('cursor', 'default');
               // $('#datatable_search').html($('#insured_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
                unblock('block_body');
            },
            "fnRowCallback": function(nRow, data, row, iDisplayIndex, iDisplayIndexFull) {
                if (data.status === 'B') {
                    $(nRow).attr('style', 'color:red !important; text-decoration: line-through; text-decoration-style: double');
                }
            }
        });

    });
    
    function customerSearch() {
        $("#insured_tbl").DataTable().ajax.reload();
    }
    
    function viewDetails(civilId, name) {
        var data = {"company": "<s:property value="#session.USER_INFO.companyCode" />", "civilid": civilId, search: "view"};
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/customer360.do",
            data: data,
            success: function (result) {
                $("#tab_cust_info").tabs().add(name, result);
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function viewInsured(civilId) {
        var data = {"company": "<s:property value="#session.USER_INFO.companyCode"/>", "customer.civilId": civilId};
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openCustomerEntryForm.do?operation=edit",
            data: data,
            success: function (result) {
                //reloadDataTable('#task_tbl', result.aaData)
                //$('#task_add').html(result);
                //$('#plugin_modal_dialog').modals().mm(result);
                $('#popup_custom').html(result);
                //$('#plugin_modal_dialog').modals().mm(result);
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
    function openForm() {
        var data = {"company": "<s:property value="#session.USER_INFO.companyCode"/>"};
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openCustomerEntryForm.do?operation=add",
            data: data,
            success: function (result) {
                $('#popup_custom').html(result);
                //$('#plugin_modal_dialog').modals().mm(result);
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
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>
