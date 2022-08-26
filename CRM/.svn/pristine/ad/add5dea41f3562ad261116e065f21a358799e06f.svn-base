<%-- 
    Document   : campaignImageUploadForm
    Created on : Aug 30, 2017, 1:04:56 PM
    Author     : sutharsan.g
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
        <h4 class="m-tb-3 d-inline-block"> <s:text name="lbl.upload.image"/></h4>
        <div class="pull-right cstm_btn">
            <button type="button" class="btn btn-success waves-effect" id="btn_upl" onclick="uploadCampaignImage();"><i class="fa fa-upload"></i> <s:text name="lbl.upload"/></button>
            <button type="button" class="btn btn-danger waves-effect" id="btn_custome_close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-close"></i> <s:text name="btn.close"/></button>
        </div>
    </div>
    <div class="body">
        <div id="file_upload_list" class="alert alert-danger" style="display: none;"></div>  
        <div class="alert alert-danger" id="e_filter" style="display: none;"></div>
        <s:form id="uplFrm" name="uplFrm" theme="simple" method="post" autocomplete="off" enctype="multipart/form-data" action="/admin/uploadCampaignImagesEntryForm.do">
            <div class="row">
                <div class="col-md-3 required"><s:text name="lbl.description"/></div>
                <div class="col-md-9 "><s:textfield cssClass="form-control" name="campaignImages.mciDesc" id="mciDesc" /></div>
            </div> 
            <div class="row">
                <div id="uploadfile">                
                    <div class="col-md-6" id="div_file_upload">
                        <div data-provides="fileupload" class="fileupload fileupload-new mb-0">
                            <span class="btn btn-file  btn-greensea btn-sm"><i class="fa fa-folder-open-o"></i> <span class="fileupload-new"><s:text name="lbl.upload.image"/></span>
                                <span class="fileupload-exists"><s:text name="lbl.change"/></span>
                                <s:file name="attachments" cssStyle="width: 350px;"/>
                            </span> 
                            <span class="fileupload-preview"></span><a data-dismiss="fileupload" name="attachments" class="close fileupload-exists float-none" href="#"> &times; </a> 
                        </div>
                    </div>                                   
                </div>
            </div>
        </s:form>       
    </div> 
</div> 

<script type="text/javascript">
    $(document).ready(function () {
        var addOptions = {
            success: uploadCallback,
            url: APP_CONFIG.context + "/admin/uploadCampaignImagesEntryForm.do",
            type: "POST",
            allowedTypes: "jpg,png,gif",
        };
        $('#uplFrm').ajaxForm(addOptions);
    });

    function uploadCampaignImage() {
        if ($('#mciDesc').val() === "")
        {
            alert('Please enter Description');
            return false;
        }
        var path = $('[name="attachments"]').val();
        if (!path || path === "") {
            alert("Please select file");
            return false;
        }
        if ($("#div_file_upload input[name=attachments]").val() !== "")
        {
            blockDiv("uploadfile");
            $("#uplFrm").submit();
        }
        return false;
    }
    function uploadCallback(result, statusText, xhr, $form) {
        unblockDiv("uploadfile");
        if (result.messageType === "S") {
            $("#file_upload_list").removeClass("alert-danger").addClass("alert-success").html("File Upload Successfully").show();
            $("#tbl_camp_mail").DataTable().ajax.url(getMailCampTableUrl()).load();

        } else {
            $("#file_upload_list").removeClass("alert-success").addClass("alert-danger").html(result.message).show();
        }
    }
    function getMailCampTableUrl() {
        return APP_CONFIG.context + "/admin/loadCampaignImagesEntryData.do";
    }

</script>