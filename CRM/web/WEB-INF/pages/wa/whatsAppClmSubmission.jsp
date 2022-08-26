<%-- 
    Document   : whatsAppClmSubmission
    Created on : 17 Feb, 2021, 10:17:49 AM
    Author     : sivaiah.savadam
--%>

<%@page import="qa.com.qic.common.util.ApplicationConstants, qa.com.qic.crm.restapi.resources.services.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    .home-card {
        min-height: 370px;
    }
    .no-data:after {
        content: "No data available";
        color: rgba(30, 127, 210, 0.62);
    }
</style>
<%
    WhatsAppProperty prop = new WhatsAppProperties((String) session.getAttribute(ApplicationConstants.SESSION_NAME_COMPANY_CODE)).getProp();
%>
<script>
    var WA_CONFIG = {
        context: "<%=request.getContextPath()%>",
        notifyBaseUrl: "<%=prop.getNotifyBaseUrl()%>",
        countryCode: "<%=prop.getCountryCode()%>",
        loggedInUserId: "<s:property value="#session.USER_INFO.userId" />",
        loggedInUserName: "<s:property value="#session.USER_INFO.userName" />"
    };
</script>
<div class="modal-header">
    <h4 class="modal-title"><s:text name="lbl.whatsapp.head.claim.submission"/></h4>
</div>
<div class="body" style="padding: 7px;">
    <div class="modal-body">
        <div class="row clearfix">
            <div class="col-lg-6 col-md-6">
                <div class="input-group">
                    <span class="input-group-addon" id="title_hdng"><s:text name="lbl.whatsapp.search.member"/></span>
                    <div class="form-line">
                        <select id="sel_search_member_by" class="form-control show-tick" onchange="setAddlTextField(this.value);">
                            <option value="MEM_ID"><s:text name="lbl.whatsapp.option.member.id"/></option>
                            <option value="CIVIL_ID"><s:text name="lbl.civil.id"/></option>
                            <option value="Mobile_No"><s:text name="lbl.mobile.no"/></option>
                            <option value="EMP_ID"><s:text name="lbl.employee.no"/></option>
                            <option value="MEM_NAME"><s:text name="lbl.member.name"/></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <div class="input-group">
                    <div class="form-line">
                        <div style="display:none" id="div_policy_no">
                            <div class="form-line" style="width: 50%;">
                                <input type="text" class="form-control" id="txt_member_policyNo" placeholder="<s:text name="lbl.policy.no"/>" style="width: 100%;margin-left: -1px;"> 
                                <input type="text" class="form-control" id="txt_member_val" placeholder="<s:text name="lbl.member"/>" style="width: 100%;margin-top: -28px;margin-left: 161px;">
                            </div>
                        </div>
                        <div id="div_member_name">
                            <input type="text" class="form-control" id="txt_search_member_val" placeholder="<s:text name="lbl.member"/>">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table id="doc_tbl" class="table table-striped table-bordered display dataTable dtr-inline">
                    <thead>
                        <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                            <th><s:text name="lbl.common.doc.name"/></th>
                            <th class="no-sort" style="text-align: center;"><s:text name="lbl.action"/></th> 
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12"
                 style="margin-top: 23px">
                <button class="save-btn btn btn-primary" id="btn_create_preapproval"
                        onclick="callSubmitClaim();"><s:text name="btn.submit"/></button>
                <button class="close-btn btn" data-dismiss="modal"
                        id="btn_approval_close"><s:text name="btn.close"/></button>
            </div>
        </div>
        <div class="row" id="div_doc" style=" margin-top:5px;display:none;">
            <div class="col-md-12" style="height:300px;">
                <iframe id="iframe_doc" src="" width="100%" height="100%" frameborder="0" style="border:0"></iframe>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        doc_tbl = $("#doc_tbl").DataTable({
            paging: true,
            searching: false,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            processing: false,
            serverSide: false,
            "ajax": {
                "url": APP_CONFIG.context + "/loadWhatsDocData.do?txn.wtTxnId="+'${txn.wtTxnId}'+"&txn.wtMobileNo="+'${txn.wtMobileNo}',
                "type": "POST",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "wlFilePath",
                    render: function (data, type, row, meta) {
                        return '<span style="color:#0066FF;text-decoration:underline; cursor: pointer;" onclick="viewDoc(\'' + row.wlFilePath + '\')">' + data + '</span>';
                    }
                },
                {data: "wlLogId", "className": "center",
                    render: function (data, type, row, meta) {
                        return '<input type="checkbox" class="checkbox mr-5" style="height:20px;width:20px" id="cbox_'+ row.wlLogId +'"/>';
                    }
                }
            ],
            dom: 'T<"clear">lfrtip',
            initComplete: function () {
                $("#doc_tbl tr td").css('cursor', 'default');
            },
            tableTools: {
                "sRowSelect": "single",
                "aButtons": [],
            }
        });
    });
    function setAddlTextField(value){
        if(value !== null && value !=='' && value ==='MEM_NAME'){
            $("#div_policy_no").show();
            $("#div_member_name").hide();
            $("#txt_member_policyNo").val('');
            $("#txt_search_member_val").val('').attr("placeholder","Member Name");
        }else{
            $("#div_policy_no").hide();
            $("#div_member_name").show();
            $("#txt_search_member_val").val('').removeAttr("placeholder");
        }

    }
    function callSubmitClaim(){
        var docId = '';
        var data = doc_tbl.rows().data();
        $.each(data, function (index, value) {
            if(doc_tbl.$("#cbox_"+value.wlLogId).is(':checked')){
                docId  += value.wlLogId + ",";
            }
        });
        docId = docId.slice(0, -1);
        var searchBy, searchValue;
        searchBy = $("#sel_search_member_by option:selected").val();
        
        if(searchBy != null && searchBy !='' && searchBy=='MEM_NAME'){
            searchValue = $("#txt_member_val").val();
        }else{
            searchValue = $("#txt_search_member_val").val();
        }
        if (searchBy === "0") {
            alert("Please select search category");
        } else if ($.trim(searchValue) === "") {
            alert("Please enter value for search");
        } else {
            let url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=WhatsAppMemberClaim&flex2=" + searchBy + "&flex3=" + searchValue + "&details=" + docId;
            if(searchBy != null && searchBy !='' && searchBy=='MEM_NAME'){
                var policyNo=$("#txt_member_policyNo").val();
                url = url + "&flex4=" + policyNo;
            }
            var win = window.open(url, '_blank');
        }
    }
    
    function viewDoc(filePath){
        $("#iframe_doc").attr("src", "<%=ApplicationConstants.WHATSAPP_QLM_DOC_URL%>"+filePath);
        $("#div_doc").show();
    }
</script>