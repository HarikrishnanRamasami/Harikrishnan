<%-- 
    Document   : fileUpload
    Created on : 24 Jan, 2018, 11:52:00 AM
    Author     : sutharsan.g
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    .fileupload a {
        color: #9B0052 !important;
        opacity: 0.6 !important;
    }
    .fileupload a:hover {
        opacity: 0.9 !important;
    }
</style>
<div class="tile-body">
    <div class="popup-title" style="padding: 9px 3px;">
        <h3><s:text name="lbl.attachments"/></h3>
    </div>
    <div class="panel panel-primary">
        <div class="panel-body pl-0" style="padding: 0px;">
            <div class="row">
                <div class="col-md-12">
                    <div id="div_upload_parent">
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="col-md-6 mb-10">
                                <span class="btn btn-sm btn-file btn-greensea  btn-sm dmsFile"><i
                                        class="fa fa-folder-open-o"></i>
                                        <span class="fileupload-new"><s:text name="lbl.browse.file"/></span><span class="fileupload-exists"><s:text name="lbl.change"/></span> <input
                                        type="file" size="20" id="multi" name="attachments" /> </span> 
                                <span  class="fileupload-preview"></span>
                                <a href="#" class="close fileupload-exists float-none" data-dismiss="fileupload"><i class="fa fa-custom fa-trash-o"></i></a> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="div_upload" style="display: none;">
                <div class="row">
                    <div class="col-md-12">
                        <div class="fileupload fileupload-new" data-provides="fileupload">   
                            <div class="col-md-6 mb-10">            
                                <span class="btn btn-sm btn-file btn-greensea  btn-sm dmsFile"><i class="fa fa-folder-open-o"></i> <span class="fileupload-new"><s:text name="lbl.browse.file"/></span><span class="fileupload-exists"><s:text name="lbl.change"/></span>
                                    <input type="file" id="single" size="20" name="attachments" >
                                </span><span class="fileupload-preview"></span> <a href="#" class="close fileupload-exists float-none" data-dismiss="fileupload"> <i class="fa fa-custom fa-trash-o"></i> </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <s:set var="formId"><s:property value="%{#parameters['formId']}"/></s:set>
    <s:set var="count"><s:property value="%{#parameters['count']}"/></s:set>
</div>
<script type="text/javascript">
    function addMoreUploads(formId) {
        var count_file = 0;
        $("#" + formId + " .fileupload-preview").each(function (index) {
            if ($(this).parent().find("#file_upload").length <= 0 && $(this).html() == "") {
                count_file = count_file + 1;
            }
        });
        if (count_file <= 1)
            var time = moment().format('HH_mm_ss');
        $("#" + formId + "  #div_upload_parent input[name=attachments]").attr('id', time);
        $("#" + formId + "  #div_upload_parent").append($("#" + formId + "  #div_upload").html());
    }
    
    $(document).ready(function () {
        var countsize = ${attr.count};
        var count = 0;
        for (count = 0; count < countsize; count++) {
            addMoreUploads('${attr.formId}');
        }
    });

</script>



