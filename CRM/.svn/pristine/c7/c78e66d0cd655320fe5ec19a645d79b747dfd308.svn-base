<%-- 
    Document   : DmsFiles
    Created on : 2 Feb, 2018, 4:56:49 PM
    Author     : sutharsan.g
--%>

<%@page import="qa.com.qic.common.util.ApplicationConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dash-leads" style="border-top:0!important">
    <div class="my-bord">
        <h3><s:text name="lbl.task.dms.files"/></h3>
    </div>
    <table id="dmsfiles_tbl" class="table table-striped table-bordered display dataTable dtr-inline">
        <thead>
            <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                <th style="width: 10%;"><s:text name="lbl.trans.no"/></th>
                <th style="width: 10%;"><s:text name="lbl.file.name"/></th>
                <th style="width: 10%;"><s:text name="lbl.doc.id"/></th>
                <th style="width: 10%;"><s:text name="lbl.action"/></th>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="docInfoList" status="row">
                <tr>
                    <td><s:property value="cdiDocId" /></td>
                    <td><s:property value="cdiDocName" /></td>
                    <td><s:property value="cdiLink" /></td>
                    <td style="text-align: center;"><i class="fa fa-download" title="Download" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="downloadDms('<s:property value="cdiLink" />', '<s:property value="cdiDocName" />');"></i>
                    </td>
                </tr>
            </s:iterator>
        </tbody>
    </table>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        dmsfiles_tbl = $("#dmsfiles_tbl").DataTable({
            paging: true,
            searching: false,
            ordering: true,
            info: false,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function () {
                $("#dmsfiles_tbl tr td").css('cursor', 'default');
                //$('#datatable_search').html($('#dmsfiles_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            },
        });
    });