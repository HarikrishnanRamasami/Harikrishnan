<%-- 
    Document   : mailCampaignPage
    Created on : 19 Sep, 2017, 12:03:52 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="card">
    <div class="header">
        <h4 class="m-tb-6 d-inline-block"><s:text name="lbl.campaign.mail"/> </h4>
    </div>
    <s:hidden name="customer.emailId" id="customer.emailId"/>
    <div class="body">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-custom table-bordered table-responsive nowrap nolink" id="tbl_mail" role="grid">
                    <thead>
                        <tr>
                            <th width="8%"><s:text name="lbl.campaign.name"/></th>
                            <th width="8%"><s:text name="lbl.description"/></th>
                            <th width="8%"><s:text name="lbl.visited"/></th>
                            <th width="8%"><s:text name="lbl.opened"/></th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody> 
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
            $(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
            var url = APP_CONFIG.context + "/loadCampByEmailFigure.do?customer.emailId=<s:property value='customer.emailId'/>&company=<s:property value='company'/>";
            tbl_mail = $("#tbl_mail").DataTable({
    paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 4,
            responsive: true,
            destory: true,
            "ajax": {
            "url": url,
                    "type": "POST",
                    "contentType": "application/json",
                    "dataSrc": "aaData"
            },
            columns: [
           
            {data: "key"},
            {data: "value"},
            {data: "info"},
            {data: "info1"}

            ],
            dom: '<"clear">lfrtipT',
            tableTools: {
            "sRowSelect": "single",
                    "aButtons": []
            }
    });
    });
          
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>
