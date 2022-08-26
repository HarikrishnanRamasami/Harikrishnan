<%-- 
    Document   : activities
    Created on : Apr 4, 2017, 7:10:04 AM
    Author     : sivaiah.savadam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %> 
<style>
.list-group{
 word-break:break-all;
}
.red-tooltip + .tooltip > .tooltip-inner {word-break:break-all;}
</style>
<div class="col-md-12 right-pad">
    <div class="dash-leads" style="border-top:0!important">
        <div class="my-bord">
            <h3><s:text name="lbl.activities"/></h3>
        </div>
        <div class="my-bord bor2">
            <div class="col-md-9 Tmar" style="margin-bottom: 10px;">               
                <s:form class="form-inline" id="frm_activity_search" name="frm_activity_search" method="post" theme="simple">
                    <div class="form-group padR">
                        <label><s:text name="lbl.common.range"/></label>
                        <s:select name="activity.dateRange" id="clStatus" list="dateRangeList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="form-control bor"/>
                    </div>
                    <div class="form-group padR">
                        <label><s:text name="lbl.user"/></label>
                        <s:select name="activity.userId" list="userList" listKey="key" listValue="value" cssClass="form-control bor" />
                    </div>
                    <div class="form-group padR">
                        <button type="button" id="search_activities" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
                    </div>
                </s:form>
            </div>
            <table id="activity_tbl" class="table table-striped table-bordered display dataTable dtr-inline">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                        <th width="8%"><s:text name="lbl.common.activity.id"/></th>
                        <th width="8%"><s:text name="lbl.type"/></th>
                        <th width="8%"><s:text name="lbl.civil.id"/></th>     
                        <th width="8%"><s:text name="lbl.common.refno"/></th>                                              
                        <th width="8%" class="list-group"><s:text name="lbl.message"/></th> 
                        <th width="8%"><s:text name="lbl.common.cr.user"/></th> 
                        <th width="8%"><s:text name="lbl.common.crdate"/></th>
                    </tr>
                </thead>
                <tbody>	   
                </tbody>
            </table>
        </div>
    </div>
</div>
<s:action namespace="/" name="openMissedCallPage" executeResult="true" >
    <s:param name="flex1">N</s:param>
</s:action>
<div id="lead_add"></div>
<script type="text/javascript">
function strtrunc(str, max, add) {
    add = add || '...';
    return (typeof str === 'string' && str.length > max ? str.substring(0, max) + add : str);
};

    $(document).ready(function () {
    	 $('#activity_tbl').on('draw.dt', function () {
             $('[data-toggle="tooltip"]').tooltip();
         });

         $('[data-toggle="tooltip"]').tooltip();
        var url = APP_CONFIG.context + "/loadActivitiesEntryData.do?" + $('#frm_activity_search').serialize();
        activity_tbl = $("#activity_tbl").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            processing: true,
            serverSide: false,
            "ajax": {
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "ctId"},
                {data: "ctModuleDesc"},
                {data: "ctRefId"},
                {data: "ctRefNo"},
                {data: "ctMessage", 
                    //'targets': 1,
                    "render": function (data, type, row, meta) {
                        if (data === null) {
                            data = "";
                        }
                        if (type === 'display') {
                            data1 = strtrunc(data, 10);
                        }
                        return '<span data-toggle="tooltip" class="red-tooltip" title="' + data + '">' + data1 + '</span>';

                    }
                },
                {data: "ctCrUid"},
                {data: "ctCrDt",
                    render: function (data, type, row, meta) {
                        if (data !== "" && data !== null) {
                            var result = moment(data, "YYYY-MM-DDTHH:mm:ss").format('DD/MM/YYYY');
                            return result;
                        } else {
                            return "";
                        }
                    }
                }

            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function () {
                $("#activity_tbl tr td").css('cursor', 'default');
                //$('#datatable_search').html($('#activity_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
        });

        $("#search_activities").on("click", function () {
            $("#activity_tbl").reloadDT(APP_CONFIG.context + "/loadActivitiesEntryData.do?" + $('#frm_activity_search').serialize());
        });
    });
</script>
