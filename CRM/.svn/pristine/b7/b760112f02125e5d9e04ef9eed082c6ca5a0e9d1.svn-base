<%-- 
    Document   : forms
    Created on : Sep 23, 2019, 9:25:28 AM
    Author     : soumya.gaur
--%>

<%@page import="qa.com.qic.common.vo.UserInfo"%>
<%@page import="qa.com.qic.common.util.ApplicationConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 right-pad" id="block_body">
    <s:include value="/WEB-INF/pages/campaign/campaignHeader.jsp?stepNo=4"></s:include>
    <s:hidden name="mcCampId" id="mcCampId"/>
    <s:hidden name="campaign.mcStatus" id="mcStatus"/>
    <div class="dash-leads" style="border-top:0!important" >
        <div class="my-bord bor2" id="camp_list">
            <s:if test='"P".equals(campaign.mcStatus)'>
                <div class="col-sm-12 col-md-3 board-icons1 Rbtn">
                    <button type="button" class="btn btn-success waves-effect" style="margin-top: -9px"  data-dismiss="modal" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="Create Form" onclick="addForm('add')"><s:text name="btn.add.form"/></button>
                </div>
            </s:if>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped table-bordered display dataTable dtr-inline" id="tbl_form_view" role="grid">
                        <thead>
                            <tr>  
                                <th class="text-center"><s:text name="lbl.common.name"/></th>
                                <th class="text-center"><s:text name="lbl.common.title"/></th>
                                <th class="text-center"><s:text name="lbl.common.validity"/></th>
                                <th class="text-center"><s:text name="lbl.common.head.sub.actions"/></th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody> 
                        </table>
                    </div>
                </div>
            </div>
            <div></div>
            <div id="camp_form">
            </div>
            <div>&nbsp;</div>
            <div class="my-bord bor2" id="form_config" style="display: none;">
                <div>&nbsp;&nbsp;&nbsp;&nbsp;
                    <b><span id="formTitle"></span></b>
                </div>
                <div class="row" id="form_list">
                    <div class="col-md-12">
                        <table class="table table-custom table-bordered table-striped table-responsive nowrap nolink" id="tbl_form_config" role="grid">
                            <thead>
                                <tr>
                                    <th class="text-center"><s:text name="lbl.common.head.column"/></th>
                                    <th class="text-center"><s:text name="lbl.common.nam"/></th>
                                    <th class="text-center"><s:text name="lbl.campaign.data.type"/></th>
                                    <th class="text-center"><s:text name="lbl.common.field.type"/></th>
                                    <th class="text-center"><s:text name="lbl.common.head.sub.actions"/></th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody> 
                        </table>
                    </div>
                </div>
                <div id="form_field">
                </div>
            </div>
            <div class="row b-t mt-10" id="div_btns">
                <div class="col-md-12 form-group center mt-30">
                    <button type="button" class="btn btn-primary leads-tab" onclick="goBackWard();"> <s:text name="btn.back"/> <i class="fa fa-arrow-circle-left"></i></button>
                    <button type="button" class="btn btn-primary leads-tab" style="float: right;"onclick="openTemplate();"> <s:text name="btn.proceed"/> <i class="fa fa-arrow-circle-right"></i></button> 
                </div>
            </div>
            <div id="form_sample">
                <s:form name="frm_map_dat" id="frm_map_dat"></s:form>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(document).ready(function () {
            var url = APP_CONFIG.context + "/camp/loadFormData.do?mcCampId=" + $('#mcCampId').val();
            var table = $("#tbl_form_view").DataTable({
                paging: true,
                searching: false,
                ordering: true,
                info: true,
                "destroy": true,
                "lengthChange": false,
                "pageLength": 10,
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
                    {data: "mcfName"},
                    {data: "mcfFormTitle"},
                    {data: "mcfValidity",
                        render: function (data, type, row, meta) {
                            if (data !== "" && data !== null) {
                                var result = moment(data).format('DD/MM/YYYY');
                                return result;
                            } else {
                                return "";
                            }
                        }
                    },
                    {data: "",
                        render: function (data, type, row, meta) {
                            if($("#mcStatus").val() === "P") {
                            return '<i class="fa fa-pencil action-icon" title="Edit" onclick="editForm(\'edit\',\'' + row.mcfFormId + '\');"></i>&nbsp;&nbsp;' +
                                    '<i class="fa fa-clipboard action-icon" title="Copy URL" onclick="copyLink(' + row.mcfFormId + ');"></i>&nbsp;&nbsp;' +
                                    '<i class="fa fa-eye action-icon" title="Preview" onclick="previewForm(' + row.mcfFormId + ');"></i>&nbsp;&nbsp;'+
                                    '<i class="fa fa-list alt action-icon" onclick="loadConfig(\'' + row.mcfFormId + '\', \'' + row.mcfFormTitle + '\');" style="color: #418bca;" title="Config"></i>&nbsp;&nbsp';
                            // '<right><i class="fa fa-link" data-toggle="tooltip" data-placement="top" title="Url" style="color: #418bca;" onclick="loadConfig(\'' + row.mcfFormId + '\', \'' + row.mcfFormTitle + '\');"></i>&nbsp;&nbsp;' +
                            // '<a href="#" title="View"  style="cursor:pointer;color: #418bca;" onclick="viewConfig(\'' + row.mcfFormId + '\');" >View</a>';
                        } else {
                            return '<i class="fa fa-list alt action-icon" onclick="loadConfig(\'' + row.mcfFormId + '\', \'' + row.mcfFormTitle + '\');" style="color: #418bca;" title="Config"></i>&nbsp;&nbsp';
                        }
                            
                        }
                    }
                ],
                dom: '<"clear">lfrtipT',
                tableTools: {
                    "sRowSelect": "single",
                    "aButtons": []
                },
                initComplete: function () {
                    $("#tbl_form_view tr td").css('cursor', 'default');
                }
            });

            $('#tbl_form_view tbody').on('click', 'tr', function () {
                var formId = table.row(this).data().mcfFormId;
                var title = table.row(this).data().mcfFormTitle;
                loadConfig(formId, title);
            });


            $('#camp_form').hide();

        });
        function addForm(oper)
        {

            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/AddCampaignForm.do",
                data: {"oper": oper, "mcCampId": $('#mcCampId').val()},
                success: function (result) {
                    $('#camp_list').hide();
                    $('#form_config').hide();
                    $('#camp_form').empty().html(result).show();
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        }

        function editForm(oper, formId) {

            var data = {"mcCampId": "${mcCampId}", "campForm.mcfFormId": formId};
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/AddCampaignForm.do?oper=" + oper,
                data: data,
                success: function (result) {
                    $('#camp_list').hide();
                    $('#form_config').hide();
                    $('#camp_form').empty().html(result).show();

                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        }

        function loadConfig(formId, title) {
            $('#formTitle').html(title + ' ' + 'Configurations');
            //   $('#mcfFormId').val(formId);
            //$("#form_config").style.display = 'block';
            // $('#camp_list').hide();
            $('#form_config').css('display', 'block');
            var url = APP_CONFIG.context + "/camp/loadFormFields.do?formField.mcffFormId=" + formId;
            $("#tbl_form_config").DataTable({
                paging: true,
                searching: false,
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
                    {data: "mcffColNo"},
                    {data: "mcffFieldName"},
                    {data: "mcffDataType"},
                    {data: "mcffFieldType"},
                    {data: "",
                        render: function (data, type, row, meta) {
                            if($("#mcStatus").val() === "P") {
                            return '<left><i class="fa fa-pencil action-icon" title="Edit" onclick="editConfig(\'' + row.mcffFormId + '\', \'' + row.mcffColNo + '\', \'edit\');"></i>&nbsp;&nbsp;'
                            +'<i class="fa fa-trash action-icon" title="Delete" onclick="deleteConfig(\'' + row.mcffFormId + '\', \'' + row.mcffColNo + '\', \'delete\');"></i>&nbsp;&nbsp;';
                        }else{
                            return '<left><i class="fa fa-eye action-icon" title="view" onclick="editConfig(\'' + row.mcffFormId + '\', \'' + row.mcffColNo + '\', \'view\',);"></i>&nbsp;&nbsp;';
                            }
                        }
                    }
                ],
                dom: '<"clear">lfrtipT',
                tableTools: {
                    "sRowSelect": "single",
                    "aButtons": []
                },
                initComplete: function () {
                    $("#tbl_form_config tr td").css('cursor', 'default');
                }
            });
        }

        function editConfig(formId, colNo, oper) {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/editFormFields.do?oper="+oper,
                data: {"formField.mcffFormId": formId, "formField.mcffColNo": colNo},
                success: function (result) {
                    $('#form_list').hide();
                    $('#form_field').empty().html(result).show();
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        }
        
        function deleteConfig(formId, colNo, oper) {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/deleteFormFields.do?",
                data: {"formField.mcffFormId": formId, "formField.mcffColNo": colNo},
                success: function (result) {
                   if (result.messageType === "S") {
                        $.notify("Config Updated successfully", "custom");
                        reloadDt("tbl_form_config");
                   }else {
                    $.notify(result.message, "custom");
                }
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        }

        function viewConfig(formId) {
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/camp/viewConfig.do",
                data: {"campForm.mcfFormId": formId},
                success: function (result) {
                    $('#camp_list').hide();
                    $('#form_config').hide();
                    $('#camp_form').empty().html(result).show();
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        }
        function openTemplate()
        {
            if ($('#mcStatus').val() !== 'P')
            {
                openCampaignStep(5);
            } else {
                $("#frm_map_dat").attr("action", "<%=request.getContextPath()%>/camp/loadCampaignTemplateData.do?mcCampId=" + $("#mcCampId").val());
                $("#frm_map_dat").submit()
            }

        }

        function goBackWard() {
            if ($('#mcStatus').val() !== 'P')
            {
                openCampaignStep(3);
            } else {
                $("#frm_map_dat").attr("action", "<%=request.getContextPath()%>/camp/openDataMappings.do?mcCampId=" + $("#mcCampId").val());
                $("#frm_map_dat").submit()
            }

        }

        function copyLink(id) {
            var formUrl = "<%=ApplicationConstants.getCampaignBaseDomain(((UserInfo) session.getAttribute(ApplicationConstants.SESSION_NAME_USER_INFO)).getCompanyCode())%>";
            var url = formUrl + '/form/' + id + '/@TOKEN@';
            var temp = $('<input>').val(url).appendTo('body').select();
            document.execCommand('copy');
            $(temp).remove();
            alert('Below URL copied to clipboard\n' + formUrl + '/form/' + id + '/@TOKEN@');
        }

        function previewForm(value) {
            var formUrl = "<%=ApplicationConstants.getCampaignBaseDomain(((UserInfo) session.getAttribute(ApplicationConstants.SESSION_NAME_USER_INFO)).getCompanyCode())%>";
            var url = formUrl + '/form/' + value + '/view';
            var myWindow = window.open(url, "");
        }
</script>
