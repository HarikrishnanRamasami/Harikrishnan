<%-- 
    Document   : logActivityPage
    Created on : 10 Mar, 2017, 11:27:44 AM
    Author     : palani.rajan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@taglib prefix="s" uri="/struts-tags" %>
<style>
.list-group {
    word-break: break-all;
}
/*#log_activity_search input {
    margin-bottom: 10px !important;
    width: 182px!important;
    height: 35px!important;
    border-radius: 5px!important;
    background-color: rgba(0, 0, 0, 0.08)!important;
    background-image: url('../images/search1.png')!important;
    background-position: 95% 10px!important;
    background-repeat: no-repeat!important;
    padding: 12px 40px 12px 9px!important;
    border-color:transparent!important;
    box-shadow:5px 5px 5px transparent!important;
    color: rgba(151, 142, 142, 0.65)!important;
    margin-right: 10px;
}*/
</style>
<div class="modal-header">
    <h3 class="modal-title"><s:text name="lbl.activities"/></h3>
    <div style="float:right;" class="btn-group">
        <button class="btn btn-danger" data-dismiss="modal" id="btn_email_close" style="margin-top: -40px">&#10006; <s:text name="btn.close"/></button>
    </div>
</div> 
<div class="body">
    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped table-bordered display dataTable dtr-inline" id="log_activity_tbl" role="grid">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">                            
                        <th class="text-center"><s:text name="lbl.name"/></th>
                        <th class="text-center"><s:text name="lbl.date"/></th>     
                        <th class="text-center"><s:text name="lbl.message"/></th>
                        <th class="text-center"><s:text name="lbl.remarks"/></th>
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
          console.log("'m here in doc...");
           $('[data-toggle="tooltip"]').tooltip();
        var url = APP_CONFIG.context +"/loadLogActivityDetails.do?customer.civilId=<s:property value='customer.civilId'/>&company=<s:property value='company'/>";
        log_activity_tbl = $("#log_activity_tbl").DataTable({
            paging: true,
            searching: true,
            responsive:true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 10,
            destory: true,
            "ajax": {
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "info2",'width': '200px'},
                {data: "info3",'width': '150px'},
                {data: "value"},
                {data: "info1", render: function (data, type, row, meta) {
                    return '<span class ="list-group">'+data+'</span>';
                }
            }

            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function () {
                $("#log_activity_tbl tr td").css('cursor', 'default');
               // $('#log_activity_search').html($('#log_activity_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
            
        });
    });
    
       </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>
