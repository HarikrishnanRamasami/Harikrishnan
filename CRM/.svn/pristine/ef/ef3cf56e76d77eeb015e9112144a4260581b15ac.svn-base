<%-- 
    Document   : campaignImages
    Created on : Aug 30, 2017, 1:04:36 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="card">
    <div class="header">
        <h4 class="m-tb-6 d-inline-block"><s:text name="lbl.campaign.images"/></h4>
        <div class="pull-right cstm_btn">
            <button type="button" class="btn btn-success waves-effect" id="btn_add_camp" onclick="showForm('add');"><s:text name="btn.add.campaign.image"/></button>
        </div>
    </div>
    <div class="body">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-custom table-bordered table-responsive nowrap nolink" id="tbl_camp_mail" style="display: block;" role="grid">
                    <thead>
                        <tr>
                            <th width="8%"><s:text name="lbl.images"/></th>
                            <th width="8%"><s:text name="lbl.description"/></th>
                            <th width="8%"><s:text name="lbl.created.by"/></th>
                            <th width="8%"><s:text name="lbl.created.on"/></th>
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
    var camp_mail = {"campaignimages.mciDesc": "", "operation": ""};
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        var url = APP_CONFIG.context + "/admin/loadCampaignImagesEntryData.do";
        tbl_camp_mail = $("#tbl_camp_mail").DataTable({
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
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "mciId", width: "20%",
                    render: function (data, type, row, meta) {
                        return '<button type="image" class="fa fa-address-card  col-blue" \n\
                    onclick="javascript:viewImageInfo(\'' + row.mciId + '\',\'' + row.mciExt + '\',\'' + row.mciMime + '\');">\n\
                   recent_actors</i></button>';
                    }
                },
                {data: "mciDesc"},
                {data: "mciCrUid"},
                {data: "mciCrDt"}
            ],
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
        });
    });
    function showForm() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/admin/openCampaignImagesEntryForm.do",
            data: data,
            success: function (result) {
                $('#plugin_modal_dialog .modal-dialog').removeClass("modal-mg modal-sm").addClass("modal-lg");
                $('#plugin_modal_dialog .modal-content').empty().html(result);
                $('#plugin_modal_dialog').modal('show');
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');

            }
        });
    }
    function viewImageInfo(mciId, mciExt, mciMime) {
        var compcode = {"company": "<s:property value="#session.userInfo.getCompanyCode()" />"};
        var LeftPosition = (screen.width) ? (screen.width - 500) / 2 : 100;
        var TopPosition = (screen.height) ? (screen.height - 300) / 2 : 100;
        var urlString = "<%=request.getContextPath()%>/ShowImage?s=" + mciId + "." + mciExt + "&c=" + compcode + "&t=" + mciMime;
        var popup = window.open(urlString, '_newPage', 'width=500px' + ',height=300px' + ',left=' + LeftPosition + ',top=' + TopPosition);
        popup.focus();
    }
</script>
