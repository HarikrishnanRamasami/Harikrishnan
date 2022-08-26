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
     <div class="modal-header">
         <h3 class="modal-title"><s:text name="lbl.common.upload.file"/></h3>
                <div style="float:right;" class="btn-group">
                    <button type="button" class="close-btn btn lead-tab" id="btn_custome_close" data-dismiss="modal" aria-hidden="true" style="margin-top: -25px">&#10006<s:text name="btn.close"/></button>
                </div>
     </div>
    <div class="body">
        <div id="file_upload_list" class="alert alert-danger" style="display: none;"></div>  
        <div class="alert alert-danger" id="e_filter" style="display: none;"></div>
        <s:form id="uptFrm" name="uptFrm" theme="simple" method="post" autocomplete="off" enctype="multipart/form-data" action="UploadFileDetails.do" >
        
<!--             <div class="row"> -->
<!--                 <div class="col-md-3 required">Work Place</div> -->
<%--                 <div class="col-md-9 "><s:textfield cssClass="form-control" name="leads.clWorkPlace" id="clWorkPlace" /></div> --%>
<!--             </div>  -->
            <div class="row">
                <div id="uploadfile">                
                    <div class="col-md-6" id="div_file_upload">
                        <div data-provides="fileupload" class="fileupload fileupload-new mb-0">
                            <span class="btn btn-file  btn-greensea btn-sm" onclick="enable();"><i class="fa fa-folder-open-o"></i> <span class="fileupload-new"><s:text name="lbl.common.upload.file"/></span>
                                <span class="fileupload-exists"><s:text name="lbl.common.change"/></span>
                                <s:file name="attachment" onchange="openConfirm()" cssStyle="width: 350px;" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
                            </span> 
                            <span class="fileupload-preview"></span><a data-dismiss="fileupload" class="close fileupload-exists float-none" href="#"> &times; </a> 
                        </div>
                    </div> 
                    <div class="col-md-6 ">  
                        <%--a href="${pageContext.servletContext.contextPath}/Forms_Templates/TASKS_UPL_TEMPLATE.xlsx"--%>
                            <button type="button" class="btn btn-greensea btn-sm"  onclick="downlaodTaskRefFile();">
                                <i class="fa fa-download"></i>&nbsp;<s:text name="btn.ref.file.format"/>
                            </button>
                        <%--/a--%>
                    </div>                                    
                </div>
            </div>
        </s:form>       
    </div> 
</div> 

<div class="row" id="Dt_upd_task" style="display: none;">
	<div class="col-md-12  margin-bottom-10" style="left: 13px;   width: 98%;">
		<table
			class="table table-custom table-bordered table-responsive nowrap nolink"
			id="taskUpload_tbl" style="display: block;" role="grid">
			<thead>
				<tr>
                                    <th width="8%"><s:text name="lbl.common.module"/></th>
                                    <th width="8%"><s:text name="lbl.priority"/></th>
                                    <th width="8%"><s:text name="lbl.category"/></th>
                                    <th width="8%"><s:text name="lbl.sub.category"/></th>
                                    <th width="8%"><s:text name="lbl.common.refno"/></th>
                                    <th width="8%"><s:text name="lbl.subject"/></th>
                                    <th width="8%"><s:text name="lbl.message"/></th>
                                    <th width="8%"><s:text name="lbl.due.date"/></th>
                                    <th width="8%"><s:text name="lbl.mobile"/></th>
<!-- 					<th width="8%">Remind Before</th> -->
					<th width="8%"><s:text name="lbl.task.assigned.to"/></th>
<!-- 					<th width="8%">Status</th> -->
                                        <th width="8%"><s:text name="lbl.task.head.recordstatus"/></th> 
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>
<div id="upload_div" style="display: none;">      
    <div class="alert alert-danger" id="msg_delete" style="display: none;"></div>
    <div id="exl_error" style="display: none;  margin-left: 10px;">
        <s:text name="lbl.task.err.wrong.val.passed"/>
    </div>     
    <div align="center" class="padding-vertical-10"  style="height: 95px;">
        <button id="btn_common_rev_save" type="button" class="save-btn btn btn-primary" onclick="uploadTasksDetails()">
            <i class="fa fa-check"></i>&nbsp;<s:text name="btn.confirm"/>
        </button>
        <button type="button" class="close-btn btn" data-dismiss="modal" aria-hidden="true">
            <i class="fa fa-times-circle"></i> &nbsp;<s:text name="btn.close"/>
        </button>
    </div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		var addOptions = {
			success : uploadCallback, // post-submit callback
			url : APP_CONFIG.context + "/loadTaskEntryUploadData.do",
			type : "POST" // 'get' or 'post', override for form's 'method' attribute
		};
		$('#uptFrm').ajaxForm(addOptions);
		
		
		var taskUpload_tbl = $("#taskUpload_tbl").DataTable({
					paging : true,
					searching : true,
					ordering : true,
					info : true,
					lengthChange : false,
					autoWidth : false,
					pageLength : 5,
					responsive : true,
					destory : true,
					columns : [
                                                    {data : "ctModuleDesc",
                                                        render : function(data, type, row, meta) {

                                                        if (data !== "" && data !== null) {
                                                                return data;
                                                        }else {
                                                        return '<font  color="red">'+"Error"+'</font>';
                                                        }
                                                    }
                                                    },
                                                    {data : "ctPriorityDesc",
                                                        render : function(data, type, row, meta) {
                                                            if (data !== "" && data !== null) {
                                                                    return data;
                                                            }else {
                                                                   return '<font  color="red">'+"Error"+'</font>';
                                                        }
                                                    }
                                                    },
                                                    {data : "ctCatgDesc",
                                                        render : function(data, type, row, meta) {
								if (data !== "" && data !== null) {
									return data;
                                                                }else{
                                                                    return '<font  color="red">'+"Error"+'</font>';
								}
							}
							},
							{data : "ctSubCatgDesc",
								render : function(data, type, row, meta) {
								if (data !== "" && data !== null) {
									return data;
								} else {
							               return '<font  color="red">'+"Error"+'</font>';
								}
							}
							},
							{data : "ctRefNo"},
							{data : "ctSubject"},
							{data : "ctMessage"},
							{data : "ctDueDate"},
                                                        {data :"ctFlex01",
								render : function(data, type, row, meta) {
								if (data !== "Error") {
									return data;
								} else {
							                return '<font  color="red">'+"Error"+'</font>';
								}
							}},
							{data : "ctAssignedToDesc",
								render : function(data, type, row, meta) {
								if (data !== "" && data !== null) {
									return data;
								} else {
							                return '<font  color="red">'+"Error"+'</font>';
								}
							}
							},
// 							
							{data : "ctFlex02",
								render : function(data, type, row, meta) {
								if (data !== "" && data !== null && data === 'OK') {
									$("#hid_flex02").val(data);
									 var data = '<center><i class="fa fa-check" title="View" style="color: #008000;" "></i>';
									return  data; 
									}else{
										$("#hid_flex02").val(data);
										 var data = '<center><i class="fa fa-close" title="View" style="color: #FF0000;" "></i>';
										 return data;
									}
							}
							}

							],
					dom : '<"clear">lfrtipT',
					tableTools : {
						"sRowSelect" : "single",
						"aButtons" : []
					},
					initComplete : function() {
						$("#taskUpload_tbl tr td").css('cursor',
								'default');
						
					},
				});
	});

	function uploadTasksDetails() {
		$('#uptFrm').ajaxFormUnbind();
		var addOptions = {
				success : uploadConfCallback, // post-submit callback
				url : APP_CONFIG.context + "/saveUploadTaskEntryForm.do",
				type : "POST" // 'get' or 'post', override for form's 'method' attribute
			};
		
			$('#uptFrm').ajaxForm(addOptions);
			$('#uptFrm').submit();
		return false;
	}
	function uploadConfCallback(result, statusText, xhr, $form) {
		if (result.messageType === "S") {
			$("#file_upload_list").removeClass("alert-danger").addClass("alert-success").html("Passed records  Upload Successfully").show();
		} else {
			$("#file_upload_list").removeClass("alert-success").addClass("alert-danger").html(result.message).show();
		}
                
                $("#btn_common_rev_save").hide();
		  reloadDt("lead_tbl");
		  
	}

	function uploadCallback(result, statusText, xhr, $form) {
		//unblockDiv("uploadfile");
                 unblock('block_body');
		console.log("test  :: "+JSON.stringify(result.messageType));
		  
		$("#Dt_upd_task").show();
                $("#btn_common_rev_save").show();
		 if(result.messageType !== "OK" && result.message === "OK"){
			 $("#exl_error").html(" <p><b><p style=color:red>Error:</p>Wrong values passed</b></p>  Please Reload the XL sheet without Error (or)<br> <p><b>Are you sure want to assign the passed task, click confirm</b></p>  ").show();
			
 		 }else if(result.messageType === "OK"){
			 $("#exl_error").html("  Are you sure want to confirm the tasks ").show();
			 }else if(result.messageType === "Error"){
				 $("#exl_error").html(" <p><b><p style=color:red>Error:</p>Wrong values passed</b></p>  Please Reload the XL sheet without Error ").show();
			 }
                         
                        if(result.message !== "OK"){
                           $("#btn_common_rev_save").hide();
                        }
	  reloadDataTable("#taskUpload_tbl", result.aaData);
	  $('#upload_div').show();

	}
	function openConfirm() {
		if ($("#div_file_upload input[name=attachment]").val() !== ""
				&& $("#div_file_upload input[name=attachment]").val().match(
						/\.(xls[mx]?)$/)) {
			//blockDiv("uploadfile");
                          block('block_body');
			$("#uptFrm").submit();
		}
		
		
	}
	function enable() {
		//$("#btn_custome_close").attr("disabled", false);
		
	}
        function downlaodTaskRefFile() {
            document.uptFrm.action =  APP_CONFIG.context + "/DownloadTaskRefFile.do";
            document.uptFrm.submit();
        }
</script>