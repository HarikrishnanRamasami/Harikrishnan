<%-- 
    Document   : campaignDataMappings
    Created on : 18 Sep, 2019, 10:28:38 AM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="col-md-12 right-pad" id="block_body">
    <s:include value="/WEB-INF/pages/campaign/campaignHeader.jsp?stepNo=3"></s:include>
    <s:hidden name="mcCampId" id="mcCampId"/>
    <div class="dash-leads" style="border-top:0!important">
        <!--div class="my-bord">
            <h3>Data Mappings</h3>
        </div-->
        <div class="my-bord bor">
            <s:hidden name="campaign.mcStatus" id="mcStatus"/>
            <div id="datamap_error_div" class="alert alert-danger mb-10" style="display:none"></div>
            <table id="tbl_data_map" class="table table-striped table-bordered display dataTable dtr-inline">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                        <th style="width: 10%;" class="text-center"><s:text name="lbl.common.head.column"/></th>
                        <th style="width: 10%;" class="text-center"><s:text name="lbl.campaign.data.type"/></th>
                        <th style="width: 10%;" class="text-center"><s:text name="lbl.common.name"/></th>
                        <th style="width: 10%;" class="text-center"><s:text name="lbl.common.sample"/></th>
                    </tr>
                </thead>
                <tbody>	   
                </tbody>
            </table>
        </div>
        <div class="row b-t mt-10" id="div_btns">
            <div class="col-md-12 form-group center mt-30">
                <button type="button" class="btn btn-primary leads-tab" onclick="goBackWard();"> <s:text name="btn.back"/> <i class="fa fa-arrow-circle-left"></i></button>
                <button type="button" class="btn btn-primary leads-tab" style="float: right" onclick="updateDataMappings();"> <s:text name="btn.proceed"/> <i class="fa fa-arrow-circle-right"></i></button>
            </div>
        </div>
    </div>
</div>
<div id="form_sample">
    <s:form name="frm_map_dat" id="frm_map_dat"></s:form>
    </div>
    <script type="text/javascript">
        $(document).ready(function () {
            block('block_body');
            var tbl_data_map = $("#tbl_data_map").DataTable({
                paging: true,
                searching: false,
                responsive: true,
                ordering: false,
                info: true,
                lengthChange: false,
                autoWidth: false,
                pageLength: 10,
                destory: true,
                "ajax": {
                    "url": APP_CONFIG.context + "/camp/loadDataMappings.do?mcCampId=" + $("#mcCampId").val(),
                    "type": "POST",
                    "contentType": "application/json",
                    "dataSrc": "dataMap"
                },
                columns: [
                    {data: "mcdmDataCol",
                        render: function (data, type, row, meta) {
                            var idx = meta.row;
                          //  var res = '<input type="text" readonly id="mcdmDataCol_' + idx + '" name="dataMap[' + idx + '].mcdmDataColName" value="' + getValue(row.mcdmDataCol) + '" class="form-control" style="border:none;">';
                            var res = '<input type="hidden" readonly id="mcdmDataCol_' + idx + '" name="dataMap[' + idx + '].mcdmDataCol" value="' + getValue(row.mcdmDataCol) + '" class="form-control" style="border:none;">'+row.mcdmDataCol.substr(4);;
                            return res;
                        }
                    },
                    {data: "mcdmDataType",
                        render: function (data, type, row, meta) {
                            var idx = meta.row;
                            var res = '<select id="sel_dataType_' + idx + '" name="dataMap[' + idx + '].mcdmDataType" class="p-0 input-sm col-md-6">';
                            res = res + '<option value="">--Select--</option>';
                            for (var i = 0; i < row.dataTypeList.length; i++) {
                                if (row.dataTypeList[i].key == row.mcdmDataType) {
                                    res = res + dataType + '<option  value="' + row.dataTypeList[i].key + '" selected="selected">' + row.dataTypeList[i].value + '</option>';
                                } else {
                                    res = res + dataType + '<option  value="' + row.dataTypeList[i].key + '">' + row.dataTypeList[i].value + '</option>';
                                }
                            }
                            res = res + '</select>';
                            if (idx === 0) {
                                var dataType = '<option value="">--Select--</option>';
                                for (var i = 0; i < row.dataTypeList.length; i++) {
                                    if (row.dataTypeList[i].key == row.mcdmDataType) {
                                        dataType = dataType + '<option  value="' + row.dataTypeList[i].key + '" selected="selected">' + row.dataTypeList[i].value + '</option>';
                                    } else {
                                        dataType = dataType + '<option  value="' + row.dataTypeList[i].key + '">' + row.dataTypeList[i].value + '</option>';
                                    }
                                }
                                dataType = dataType + '</select>';
                                $('#select_dataType').html(dataType).select({dropdownAutoWidth: true});
                            }
                            return res;
                        }

                    },
                    {data: "mcdmDataName",
                        render: function (data, type, row, meta) {
                            var idx = meta.row;
                            var res = '<input type="text" id="mcdmDataName_' + idx + '" name="dataMap[' + idx + '].mcdmDataName" value="' + getValue(row.mcdmDataName) + '" class="form-control" '+ ($("#mcStatus").val() !== "P"?"readonly":"") +' style="margin-left:24px;">';
                            return res;
                        }
                    },
                    {data: "mcdmSampleData",
                        render: function (data, type, row, meta) {
                            var idx = meta.row;
                            var res = '<input type="text" id="mcdmSampleData_' + idx + '" name="dataMap[' + idx + '].mcdmSampleData" value="' + getValue(row.mcdmSampleData) + '" class="form-control" '+ ($("#mcStatus").val() !== "P"?"readonly":"") +' style="margin-left:24px;">';
                            return res;
                        }
                    },
                ],
                dom: '<"clear">lfltipT',
                tableTools: {
                    "sRowSelect": "single",
                    "aButtons": []
                },
                initComplete: function () {
                    $("#tbl_data_map tr td").css('cursor', 'default');
                    unblock('block_body');
                }, "drawCallback": function (settings) {
                    $("#tbl_data_map").DataTable().rows().every(function (rowIdx, tableLoop, rowLoop) {
                        var row = this.data();
                        var index = this.index();
                        if (getValue(row.mcdmDataType) !== '') {
                            $('#sel_dataType_' + index).select("val", row.mcdmDataType);
                        }

                    });
                },
            });

        });

        function getValue(val) {
            if (val === null || null === 'undefined') {
                return "";
            }
            return val;
        }

        function updateDataMappings() {
            
            if ($('#mcStatus').val() !== 'P')
            {
                openCampaignStep(4);
            } else {
                block('block_body');
                var isDataType = true;
                $('#tbl_data_map').DataTable().$('select[name$="mcdmDataType"]').each(function () {
                    if ($(this).val() === '')
                    {
                        isDataType = false;
                        return false;
                    }
                });
                if (!isDataType) {
                    $.notify("Select the Data Type Fields", "custom");
                    unblock('block_body');
                    return false;
                }
                var form_serialize = $('#tbl_data_map').DataTable().$('input[type=hidden], input[type=text], select').filter(function () {
                    return true;
                }).serialize();
                $.ajax({
                    type: "POST",
                    data: form_serialize,
                    url: '${pageContext.request.contextPath}/camp/updateDataMappings.do?mcCampId=' + $("#mcCampId").val(),
                    success: function (result) {
                        if (result.messageType === "S") {
                            $.notify(result.message, "custom");
                            $.notify("Data Filter Updated Successfully", "custom");
                            $("#frm_map_dat").attr("action", "<%=request.getContextPath()%>/camp/loadForms.do?mcCampId=" + $("#mcCampId").val());
                            $("#frm_map_dat").submit();
                        } else {
                            $.notify(result.message, "custom");
                        }
                    },
                    complete: function () {
                        unblock('block_body');
                    },
                    error: function (xhr, status, error) {
                        displayAlert('E', error);
                    }
                });
                return false;
            }

        }

        function goBackWard() {
            if ($('#mcStatus').val() !== 'P')
            {
                openCampaignStep(2);
            } else {
                $("#frm_map_dat").attr("action", "<%=request.getContextPath()%>/camp/loadDataParamFilter.do?mcCampId=" + $("#mcCampId").val());
                $("#frm_map_dat").submit()
            }


        }
</script>
