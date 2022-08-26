<%-- 
    Document   : taskFileUpload
    Created on : Aug 22, 2017, 2:48:21 PM
    Author     : mathiyalagan.a
--%>

<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    .fileupload a {
        color: #9B0052 !important;
        opacity: 0.6 !important;
    }
    .fileupload a:hover {
        opacity: 0.9 !important;
    }
    .mem_dms_btn{
        background-color: #ac0636;
        color: #fff !important;
        display: inline-block;
        font-family: roboto;
        font-size: 14px;
        font-weight: 400;
        margin-right: 10px;
        min-width: 120px;
        padding:6px;
        text-align: center;
        text-transform: uppercase;
    }
</style>
<div class="card">
    <div class="header">
        <h4 class="m-tb-3 d-inline-block"> <s:text name="lbl.common.upload.file"/></h4>
        <div class="pull-right cstm_btn">
            <button type="button" class="btn btn-success waves-effect" id="btn_custome_close" onclick="uploadTaskDetails();"><i class="fa fa-upload"></i> <s:text name="btn.upload"/></button>
            <button type="button" class="btn btn-danger waves-effect" id="btn_custome_close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-close"></i> <s:text name="btn.close"/></button>
        </div>
    </div>
    <div class="body">
        <div id="file_upload_list" class="alert alert-danger" style="display: none;"></div>  
        <div class="alert alert-danger" id="e_filter" style="display: none;"></div>
        <s:form id="uptFrm" name="uptFrm" theme="simple" method="post" autocomplete="off" enctype="multipart/form-data" action="UploadFileDetails.do" >
            <div class="row">
                <div class="col-md-3 required"><s:text name="lbl.work.place"/></div>
                <div class="col-md-9 "><s:textfield cssClass="form-control" name="leads.clWorkPlace" id="clWorkPlace" /></div>
            </div> 
            <div class="row">
                <div id="uploadfile">                
                    <div class="col-md-6" id="div_file_upload">
                        <div data-provides="fileupload" class="fileupload fileupload-new mb-0">
                            <span class="btn btn-file  btn-greensea btn-sm"><i class="fa fa-folder-open-o"></i> <span class="fileupload-new"><s:text name="lbl.common.upload.file"/></span>
                                <span class="fileupload-exists"><s:text name="lbl.common.change"/></span>
                                <s:file name="attachment" cssStyle="width: 350px;" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
                            </span> 
                            <span class="fileupload-preview"></span><a data-dismiss="fileupload" class="close fileupload-exists float-none" href="#"> &times; </a> 
                        </div>
                    </div> 
                    <div class="col-md-6 ">  
                        <a href="${pageContext.servletContext.contextPath}/Forms_Templates/LEADS_UPL_TEMPLATE.xls">
                            <button type="button" class="btn btn-greensea btn-sm" >
                                <i class="fa fa-download"></i>&nbsp;<s:text name="btn.ref.file.format"/>
                            </button>
                        </a>
                    </div>                                    
                </div>
            </div>
        </s:form>       
    </div> 
</div> 
<script type="text/javascript">
    $(document).ready(function () {
        var addOptions = {
            success: uploadCallback, // post-submit callback
            url: APP_CONFIG.context + "/saveUploadLeadsEntryForm.do",
            type: "POST"    // 'get' or 'post', override for form's 'method' attribute
        };
        $('#uptFrm').ajaxForm(addOptions);
    });

    function uploadTaskDetails() {

        if ($('#clWorkPlace').val() === "")
        {
            alert('Please enter work place');
        }
        if ($("#div_file_upload input[name=attachment]").val() !== "" && $("#div_file_upload input[name=attachment]").val().match(/\.(xls[mx]?)$/))
        {
            blockDiv("uploadfile");
            $("#uptFrm").submit();
        }
        return false;
    }

    function uploadCallback(result, statusText, xhr, $form) {
        unblockDiv("uploadfile");
        if (result.messageType === "S") {
            $("#file_upload_list").removeClass("alert-danger").addClass("alert-success").html("File Upload Successfully").show();
        } else {
            $("#file_upload_list").removeClass("alert-success").addClass("alert-danger").html(result.message).show();
        }
    }
</script>