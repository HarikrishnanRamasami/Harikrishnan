<%-- 
    Document   : bulkSMSEmailDetails
    Created on : 28 Apr, 2017, 12:48:37 PM
    Author     : palani.rajan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
 <input type="hidden"  id="rowId"/>
<div class="row">
    <div class="col-md-12">
        <table class="table table-striped table-bordered display dataTable dtr-inline" id="cust_SMS_tbl" style="display: block;" role="grid">
            <thead>
                <tr>
                    <th width="8%"><s:text name="lbl.select"/></th>
                    <th width="8%"><s:text name="lbl.mobile.or.email"/></th>
                    <th width="8%"><s:text name="lbl.col1"/></th>     
                    <th width="8%"><s:text name="lbl.col2"/></th>     
                    <th width="8%"><s:text name="lbl.col3"/></th>     
                    <th width="8%"><s:text name="lbl.col4"/></th>     
                    <th width="8%"><s:text name="lbl.col5"/></th>     
                    <th width="8%"><s:text name="lbl.col6"/></th>     
                    <th width="8%"><s:text name="lbl.col7"/></th> 
                    <th width="8%"><s:text name="lbl.col8"/></th>     
                    <th width="8%"><s:text name="lbl.col9"/></th>     
                    <th width="8%"><s:text name="lbl.col10"/></th>  
                </tr>
            </thead>
            <tbody>              
            </tbody> 
        </table>
    </div>
</div>
<div class="row">
    <div class="col-md-12 text-center">
        <input type="button" name="Preview" value="Personalized Preview Template" onclick="preview();" class="btn btn-warning" /> 
        <input type="button" name="sendEmail" value="Send Campaign" onclick="sendBulkSMSEmail();" class="btn btn-warning" /> 
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var cust_SMS_tbl = $("#cust_SMS_tbl").DataTable({
            searching: true,
            ordering: true,
            info: false,
            "lengthChange": false,
            "pageLength": 10,
            "responsive": true,
            autoWidth: false,  
            "serverSide": true,
            "processing": true,
            deferRender: true,
            "ajax": {
                "url":"<%=request.getContextPath()%>/loadExtractDataList.do?sendOption=${sendOption}&customize=${customize}&includePol=${includePol}&operation=${operation}",
                 "datatype": "json",
            },
            columns: [
                {data: "rowId",
                    render: function (data, type, row, meta) {
                        return  '<input type="radio" name="rowId" id="rowid_'+row.smsEmailHeading+'" class="with-gap radio-col-blue" onclick="return selectRowId(\'' + row.rowId + '\')" /> <label for="rowid_'+row.smsEmailHeading+'"></label>';
                    }
                },
                {data: "smsEmailHeading"},
                {data: "col1"},
                {data: "col2"},
                {data: "col3"},
                {data: "col4"},
                 {data: "col5"},
                {data: "col6"},
                {data: "col7"},
                {data: "col8"},
                {data: "col9"},
                {data: "col10"},
            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            initComplete: function () {
                $("#cust_SMS_tbl tr td").css('cursor', 'default');
               // $('#ins_search').html($('#insured_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
        });
        

    });

</script>