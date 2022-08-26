<%-- 
    Document   : campaignTemplateList
    Created on : 16 Sep, 2019, 1:22:07 PM
    Author     : syed.basha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 right-pad" id="block_body">
    <s:include value="/WEB-INF/pages/campaign/campaignHeader.jsp?stepNo=5"></s:include>
    <s:hidden name="mcCampId" id="mcCampId"/>
    <s:hidden name="camp.mcCampType" id="mcCampType"/>
    <s:hidden name="campaign.mcCampType"/>      
    <s:hidden name="campaign.mcStatus" id="mcStatus"/>
    <div class="dash-leads" style="border-top:0!important">
        <div class="my-bord bor2" id="block_template">
            <s:if test='"P".equals(campaign.mcStatus)'>
                <div class="col-sm-12 col-md-3 board-icons1 Rbtn">
                    <button type="button" class="btn btn-success waves-effect" style="width: 200px; margin-top: -8px"  data-dismiss="modal" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="<s:text name="lbl.campaign.create.campaign"/>" onclick="addTemplate('add')"><s:text name="btn.add.template"/></button>
                </div>
            </s:if>
            <table class="table table-striped table-bordered display dataTable dtr-inline" id="tbl_camp_temp" role="grid">
                <thead>
                    <tr>
                        <th class="text-center"><s:text name="lbl.id"/></th>
                        <th class="text-center"><s:text name="lbl.common.name"/></th>
                            <s:if test='"E".equals(campaign.mcCampType)'>
                            <th class="text-center"><s:text name="lbl.subject"/></th>
                            </s:if>
                            <s:else>
                            <th class="text-center"><s:text name="lbl.common.create.date"/></th>
                            </s:else>
                            <th class="text-center"><s:text name="lbl.action"/></th>
                    </tr>
                </thead>
                <tbody>
                </tbody> 
            </table>
        </div>
        <div id="block_camp_form">
        </div>
        <div class="" id="block_url" style="display: none;">
            <table class="table table-striped table-bordered display dataTable dtr-inline nowrap nolink" id="tbl_camp_url" role="grid">
                <thead>
                    <tr>
                        <th class="text-center"><s:text name="lbl.campaign.head.sub.urlkey"/></th>
                        <th class="text-center"><s:text name="lbl.campaign.head.sub.url"/></th>
                        <th class="text-center"><s:text name="lbl.campaign.head.sub.click.count"/></th>
                    </tr>
                </thead>
                <tbody>
                </tbody> 
            </table>
        </div>
        <div class="row b-t mt-10" id="div_btns">
            <div class="col-md-12 form-group center mt-30" >
                <button type="button" class="btn btn-primary leads-tab" onclick="goBackWard();"> <s:text name="btn.back"/> <i class="fa fa-arrow-circle-left"></i></button>
                <button type="button" class="btn btn-primary leads-tab" style="float: right;" onclick="proceedTemplate();"> <s:text name="btn.proceed"/> <i class="fa fa-arrow-circle-right"></i></button>
            </div>
        </div>
    </div>
</div>
<div>
    <s:form name="camp_temp" id="camp_temp"/>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        })
        var url = APP_CONFIG.context + "/camp/loadTemplateData.do?mcCampId=" + $("#mcCampId").val();
        var table = $("#tbl_camp_temp").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            "destroy": true,
            "lengthChange": false,
            "pageLength": 5,
            "responsive": true,
            autoWidth: false,
            "processing": true,
            "ajax": {
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "mctTemplateId"},
                {data: "mctName"},
                {data: "mctSubject",
                    render: function (data, type, row, meta) {
                        if (row.mctType === "E") {
                            return row.mctSubject;
                        } else {
                            return moment(row.mctCrDt).format('DD/MM/YYYY');
                        }
                    }
                },
                {data: "mctTemplateId",
                    render: function (data, type, row, meta) {
                        if (row.mctType === "E") {
                           return '<i class="fa fa-eye action-icon" title="Preview" onclick="previewTemplate(\'' + row.mctTemplateId + '\');"></i>&nbsp;&nbsp'
                           + '<i class="fa fa-link action-icon" title="URl\'s" onclick="loadUrlDT(\'' + row.mctTemplateId + '\');"></i>&nbsp;&nbsp'
                        <s:if test='"P".equals(campaign.mcStatus)'>
                            + '<i class="fa fa-edit action-icon" title="Edit" onclick="editTemplateForm(\'edit\',\'' + row.mctTemplateId + '\',\'' + row.mctType + '\');"></i>&nbsp;&nbsp'
                                    + '<i class="fa fa-envelope action-icon" title="Test Mail" onclick="sendEmailTemplate(\'' + row.mctTemplateId + '\');"></i>';
                        </s:if>
                        } else {
                            return '<i class="fa fa-eye action-icon" title="Preview" onclick="previewTemplate(\'' + row.mctTemplateId + '\');"></i>&nbsp;&nbsp'
                        <s:if test='"P".equals(campaign.mcStatus)'>
                            + '<i class="fa fa-edit action-icon" title="Edit" onclick="editTemplateForm(\'edit\',\'' + row.mctTemplateId + '\',\'' + row.mctType + '\');"></i>&nbsp;&nbsp'
                                    + '<i class="fa fa-envelope action-icon" title="Test SMS" onclick="sendEmailTemplate(\'' + row.mctTemplateId + '\');"></i>';
                        </s:if>
                        }

                    }
                }
            ],
            columnDefs: [
                {targets: 3, orderable: false}
            ],
            language: {
                searchPlaceholder: "Search..."
            },
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            }
        });

        $('#tbl_camp_temp tbody').on('click', 'tr', function () {
            var templateId = table.row(this).data().mctTemplateId;
            loadUrlDT(templateId);
        });
    });

    function addTemplate(oper) {
        $("#block_url").hide();
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/openCampaignTempForm.do?oper=" + oper,
            data: {"mcCampId": "${mcCampId}"},
            success: function (result) {
                $('#block_template').hide();
                $('#block_camp_form').empty().html(result).show();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function editTemplateForm(oper, code, type) {
        $("#block_url").hide();
        var data = {"mcCampId": "${mcCampId}", "companyCode": "<s:property value="#session.USER_INFO.companyCode"/>", "campTemplate.mctTemplateId": code, "campTemplate.mctType": type};
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/openCampaignTempForm.do?oper=" + oper,
            data: data,
            success: function (result) {
                $('#block_template').hide();
                $('#block_camp_form').empty().html(result).show();
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }

    function previewTemplate(value) {
        var url = "${pageContext.request.contextPath}" + "/camp/PreviewTemplate.do";
        var data = {"campTemplate.mctTemplateId": value, "mcCampId": "${mcCampId}"};
        block('block_body');
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            async: true,
            dataType: 'json',
            complete: function () {
                unblock('block_body');
            },
            success: function (result) {
                if (result !== null) {
                    var myWindow = window.open("");
                    myWindow.document.title = result.messageType;
                    myWindow.document.body.innerHTML = result.message;
                }
            },
            error: function (error) {
                var divId = "OnlineAddlInfoErrorDiv";
                $('#' + divId).html(error);
                $('#' + divId).show();
                $('#' + divId).delay(5000).fadeOut('slow');
            }
        });
    }

    function loadUrlDT(tempId) {
        $("#block_url").show();
        var url = APP_CONFIG.context + "/camp/loadTemplateUrl.do?campTemplate.mctTemplateId=" + tempId;
        $("#tbl_camp_url").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            "destroy": true,
            "lengthChange": false,
            "pageLength": 5,
            "responsive": true,
            autoWidth: false,
            "processing": true,
            "ajax": {
                "url": url,
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "mtuUrlKey"},
                {data: "mtuUrl"},
                {data: "mtuClickedCount"}
            ],
            columnDefs: [
                {targets: 2, orderable: false}
            ],
            language: {
                searchPlaceholder: "Search..."
            },
            dom: '<"clear">lfrtipT',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            }
        });
    }

    function sendEmailTemplate(tempId) {
        var url = APP_CONFIG.context + "/camp/sendEmailTemplate.do";
        var data = {"campTemplate.mctTemplateId": tempId, "mcCampId": "${mcCampId}"};
        block('block_body');
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            async: true,
            dataType: 'json',
            complete: function () {
                unblock('block_body');
            },
            success: function (result) {
                if (result !== null) {
                    $.notify(result.message, "custom");
                }
            },
            error: function (error) {
                var divId = "OnlineAddlInfoErrorDiv";
                $('#' + divId).html(error);
                $('#' + divId).show();
                $('#' + divId).delay(5000).fadeOut('slow');
            }
        });
    }

    function proceedTemplate() {
        if ($('#mcStatus').val() !== 'P') {
            openCampaignStep(6);
        } else {
            $("#camp_temp").attr("action", "<%=request.getContextPath()%>/camp/openCampaignFilter.do?mcCampId=" + $("#mcCampId").val());
            $("#camp_temp").submit()
        }
    }

    function goBackWard() {
        if ($('#mcStatus').val() !== 'P') {
            openCampaignStep(4);
        } else {
            $("#camp_temp").attr("action", "<%=request.getContextPath()%>/camp/loadForms.do?mcCampId=" + $("#mcCampId").val());
            $("#camp_temp").submit()
        }
    }
</script>
