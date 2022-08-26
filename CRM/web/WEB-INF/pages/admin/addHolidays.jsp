<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<% pageContext.setAttribute("currentYear", java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)); %>
<div id="block_body">
    <div class="col-md-12 right-pad" id="block_body">
        <div class="dash-leads">
            <div class="my-bord">
                <h3><s:text name="lbl.holidays"/></h3>
            </div>
            <div class="col-sm-12">
                <div class="col-md-8">
                    <form class="form-inline" id="frm_hol_search" name="frm_hol_search" method="post">
                        <div class="col-sm-12 col-md-12">
                            <div class="row bor2">
                                <div class="form-group padR">
                                    <label><s:text name="lbl.year"/></label>
                                    <s:select name="crmHolidays.holidaysYear" id="year" class="form-control" list="holidayYearList" listKey="key" listValue="value" cssClass="form-control"/>
                                </div>
                                <div class="form-group padR">
                                    <button type="button" id="search_holidays" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-4 board-icons1 Rbtn">
                    <button type="button" class="btn btn-danger waves-effect" style="width: 80px; margin-top: -8px" onclick="openAddDialog();" class="btn btn-primary"><i class="fa fa-plus"></i> <s:text name="btn.add"/></button>
                </div>
            </div>
            <div class="my-bord bor2">
                <div class="row">
                    <div class="col-md-12">
                        <table id="msg_tbl" class="table table-striped table-bordered display dataTable dtr-inline">
                            <thead>
                                <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                    <th style="width: 10%;"><s:text name="lbl.type"/></th>
                                    <th><s:text name="lbl.name"/></th>
                                    <th><s:text name="lbl.from.date"/></th>
                                    <th><s:text name="lbl.to.date"/></th>
                                    <th><s:text name="lbl.action"/></th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="msg_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
                <div class="modal-dialog  modal-mm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title"><s:text name="lbl.add.update.holiday.form"/></h4> 
                            <div style="margin-top: -31px; float: right;">
                                <button class="save-btn btn btn-primary" onclick="saveForm()" id="btn_upd_task">&#10004;<s:text name="btn.save"/></button>
                                <button class="close-btn btn" data-dismiss="modal" id="btn_task_close">&#10006; <s:text name="btn.close"/></button>
                            </div>
                        </div>
                        <div class="modal-body" style="margin-top: 20px;">
                            <div id="msg_frm" class="alert alert-danger" style="display: none;"></div>
                            <s:form id="frm_hol_msg" name="frm_hol_msg" method="post" theme="simple">   
                                <s:hidden name="crmHolidays.holidaysId" id="holidaysId"/>      
                                <s:hidden name="flex1" id="flex1"/> 
                                <div class="row" style="margin-top:10px;"> 
                                    <div class=" form-group col-md-1">
                                        <label><s:text name="lbl.type"/><span>*</span></label>
                                    </div>
                                    <div class="col-md-5 form-group "> 
                                        <s:select name="crmHolidays.holidaysType" id="holidaysType" style="width:295px;" list="#{'H':'Holiday', 'W':'Weekend'}" required="true" onChange="loadHolidayType()"/>
                                    </div>
                                    <div class="form-group col-md-1">
                                        <label><s:text name="lbl.year"/><span>*</span></label>
                                    </div>
                                    <div class="col-md-5 form-group"> 
                                        <s:select name="crmHolidays.holidaysYear" type="number" id="holidaysYear" style="width:295px;" required="true" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.year')}"  list="yearsList" listKey="key" listValue="value" cssClass="form-control" disabled='"edit".equals(flex1)' />
                                    </div>
                                </div>                                               
                                <div class="row" style="margin-top:10px;"> 
                                    <div class="form-group col-md-1">
                                        <label><s:text name="lbl.name"/><span>*</span></label>
                                    </div>
                                    <div class="col-md-5 form-group"> 
                                        <s:textfield name="crmHolidays.name" id="names" style="width:675px;" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.name')}" required="true"/>
                                    </div>
                                </div>
                                <div class="row" style="margin-top:10px;" id="holidayDt"> 
                                    <div class="form-group col-md-1">
                                        <label><s:text name="lbl.from"/><span>*</span></label>
                                    </div>
                                    <div class="col-md-5 form-group"> 
                                        <s:textfield name="crmHolidays.fromDt" id="fromDt" cssClass="form-control datepicker calicon" maxlength="10" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.from.date')}" required="true"/>
                                    </div>
                                    <div class="form-group col-md-1">
                                        <label>To<span>*</span></label>
                                    </div>
                                    <div class="col-md-5 form-group"> 
                                        <s:textfield name="crmHolidays.toDt" id="toDt" cssClass="form-control datepicker calicon" maxlength="10" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.to.date')}" required="true"/>
                                    </div>
                                </div>
                                <div class="row" style="margin-top:10px;" id="weekendDt"> 
                                    <div class="form-group col-md-1">
                                        <label><s:text name="lbl.from"/><span>*</span></label>
                                    </div>
                                    <div class="col-md-5 form-group"> 
                                        <s:select name="crmHolidays.fromDt" id="weekFromDt" style="width:295px;" list="#{'1':'Sunday', '2':'Monday','3':'Tuesday', '4':'Wednesday','5':'Thursday', '6':'Friday','7':'Saturday'}" required="true"/>
                                    </div>
                                    <div class="form-group col-md-1">
                                        <label><s:text name="lbl.to"/></label>
                                    </div>
                                    <div class="col-md-5 form-group">                                    
                                        <s:select name="crmHolidays.toDt" id="weekToDt" style="width:295px;" list="#{'1':'Sunday', '2':'Monday','3':'Tuesday', '4':'Wednesday','5':'Thursday', '6':'Friday','7':'Saturday'}" headerKey="" headerValue="--Select--" />
                                    </div>
                                </div>
                            </s:form>
                        </div>
                    </div>
                </div>
            </div>               
        </div>
    </div>
</div>
<script type="text/javascript">
    function openAddDialog() {
    	var currYear = ${currentYear};
        $('#frm_hol_msg #holidaysId').val("");
        $('#frm_hol_msg #flex1').val("");
        $('#frm_hol_msg #names').val("");
        $('#frm_hol_msg #fromDt').val("");
        $('#frm_hol_msg #toDt').val("");
        $('#frm_hol_msg #weekFromDt').val("");
        $('#frm_hol_msg #weekToDt').val("");
        $('#frm_hol_msg #holidaysType').val("H");
        $('#frm_hol_msg #holidaysYear').val(currYear); 
        $('#frm_hol_msg #holidaysType').prop('disabled', false);
        $('#frm_hol_msg #holidaysYear').prop('disabled', false);
        $('#holidayDt').show();
        $('#weekendDt').hide();
        $("#msg_frm").css("display","none");
        $("#msg_modal_dialog").modal("show"); 
    }
    
    function saveForm() {
        block('block_body');
        var holId = $('#frm_hol_msg #holidaysId').val();
        var flex1 = $('#frm_hol_msg #flex1').val();      
        var name = $('#frm_hol_msg #names').val();
        var holidaysType = $('#frm_hol_msg #holidaysType').val();
        var holidaysYear = $('#frm_hol_msg #holidaysYear').val();   
        var currYear = ${currentYear};
        if(holidaysType === 'W'){
        	if($('#frm_hol_msg #weekFromDt').val() === null || $('#frm_hol_msg #weekFromDt').val() === ''){
            }else{
       		 var fromDt = $('#frm_hol_msg #weekFromDt').val() + "/01/" + currYear;
       		 if($('#frm_hol_msg #weekToDt').val() === null || $('#frm_hol_msg #weekToDt').val() === ''){
       			$('#frm_hol_msg #weekToDt').val("");
       		 }else{
           		 var toDt = $('#frm_hol_msg #weekToDt').val() + "/01/" + currYear; 
       		 }
            }
        } else if(holidaysType === 'H') {
        	 var fromDt = $('#frm_hol_msg #fromDt').val();
        	 var toDt = $('#frm_hol_msg #toDt').val();
        }
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/saveHolidayForm.do",
       
            data: {"crmHolidays.holidaysId": holId, "flex1": flex1, "crmHolidays.name": name, "crmHolidays.holidaysType": holidaysType, "crmHolidays.holidaysYear": holidaysYear, "crmHolidays.fromDt": fromDt, "crmHolidays.toDt": toDt},
            success: function (result) {
                if (result.message === "") {
                	  $("#msg_modal_dialog").modal("hide");
	                  $("#search_holidays").trigger('click');
	                  $("#msg_frm").html(result.message).hide();
                } else {
                	$("#msg_frm").html(result.message).show();
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
      
    function loadHolidayType(){
    	var holidayType = $('#frm_hol_msg #holidaysType').val();
        if (holidayType !== "") {
        	 if (holidayType === 'H') {
        		 $('#frm_hol_msg #weekFromDt').val("");
             	$('#frm_hol_msg #weekToDt').val("");
                $('#weekendDt').hide();
                $('#holidayDt').show();
            } else if (holidayType === 'W'){
            	$('#frm_hol_msg #fromDt').val("");
            	$('#frm_hol_msg #toDt').val("");
                $('#holidayDt').hide();
                $('#weekendDt').show();
            } 
        }
    }
    
    $(document).ready(function () {
    	$(".datepicker").datetimepicker({format: 'DD/MM/YYYY'});
        $('#msg_tbl').on('draw.dt', function () {
            $('[data-toggle="tooltip"]').tooltip({html: true});
        });
        
        $("#search_holidays").on("click", function () {
            $('#msg_tbl').DataTable().ajax.url(APP_CONFIG.context + "/loadHolidaysData.do?" + $('#frm_hol_search').serialize()).load();       
        });
         var currYear = ${currentYear};
         var url = APP_CONFIG.context + "/loadHolidaysData.do?crmHolidays.holidaysYear=" + currYear;       
         $('#frm_hol_search #year').val(currYear); 
         msg_tbl = $("#msg_tbl").DataTable({
            paging: true,
            searching: true,
            responsive: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 10,
            destory: true,
            "ajax": {
                "url": url,
                "type": "POST",
                "dataSrc": "aaData"
            },
            columns: [
            	{data: "holidaysType",
                    render: function (data, type, row, meta) {
                        if (row.holidaysType === 'H') {
                            return "Holiday";
                        } else if (row.holidaysType === 'W') {
                            return "Weekend";
                        }         
                    }
                },
                {data: "name"},
                {data: "fromDt",
                    render: function (data, type, row, meta) {
                        if (row.fromDt === null || row.fromDt === '')
                            return   "";
                        else{
                          if (row.holidaysType === 'H') {
                            return   moment(new Date(row.fromDt)).format("DD/MM/YYYY");
                          } else if(row.holidaysType === 'W'){
                        	  let day = moment(new Date(row.fromDt)).format("DD");
                        	  switch (day) {
							case '01':
								return "Sunday";
							case '02':
								return "Monday";
							case '03':
								return "Tuesday";
							case '04':
								return "Wednesday";
							case '05':
								return "Thursday";
							case '06':
								return "Friday";
							case '07':
								return "Saturday";
							
							default:
								break;
							}
                           }
                        }
                    }
                },
                {data: "toDt",
                    render: function (data, type, row, meta) {
                        if (row.toDt === null || row.toDt === '')
                            return   "";
                        else{
                        	if (row.holidaysType === 'H') {
                                return   moment(new Date(row.toDt)).format("DD/MM/YYYY");
                              } else if(row.holidaysType === 'W'){
                            	  let day = moment(new Date(row.toDt)).format("DD");
                            	  switch (day) {
    							case '01':
    								return "Sunday";
    							case '02':
    								return "Monday";
    							case '03':
    								return "Tuesday";
    							case '04':
    								return "Wednesday";
    							case '05':
    								return "Thursday";
    							case '06':
    								return "Friday";
    							case '07':
    								return "Saturday";
    							
    							default:
    								break;
    							}
                             }
                        }
                    }
                },
                {data: "name",
                    render: function (data, type, row, meta) {
                    	var d1 = Date.parse(row.toDt);
                    	var d2 = Date.parse(row.currDate);
                    	var res = "";
                    	if (row.holidaysType === 'H' && (d1 > d2 || d1 === d2)) {
                    		res = res + '<div id="qnimate"><span id="chat_contact"></span><i class="fa fa-pencil" title="Edit" style="color: #418bca;" onclick="editContactName(\'' + row.holidaysId + '\',\'' + row.name + '\',\'' + moment(new Date(row.fromDt)).format("DD/MM/YYYY") + '\',\'' + moment(new Date(row.toDt)).format("DD/MM/YYYY") + '\',\'' + row.holidaysType + '\',\'' + row.holidaysYear + '\');"></i>&nbsp;&nbsp;';
                    	} else{
	                      if(row.toDt === null || row.toDt === NaN || row.toDt === '' && (d1 > d2 || d1 === d2)){
	                    	  res = res + '<div id="qnimate"><span id="chat_contact"></span><i class="fa fa-pencil" title="Edit" style="color: #418bca;" onclick="editContactName(\'' + row.holidaysId + '\',\'' + row.name + '\',\'' + moment(new Date(row.fromDt)).format("DD/MM/YYYY") + '\',\'' + "" + '\',\'' + row.holidaysType + '\',\'' + row.holidaysYear + '\');"></i>&nbsp;&nbsp;';
	                      } else if(d1 > d2){
	                    	  res = res + '<div id="qnimate"><span id="chat_contact"></span><i class="fa fa-pencil" title="Edit" style="color: #418bca;" onclick="editContactName(\'' + row.holidaysId + '\',\'' + row.name + '\',\'' + moment(new Date(row.fromDt)).format("DD/MM/YYYY") + '\',\'' + moment(new Date(row.toDt)).format("DD/MM/YYYY") + '\',\'' + row.holidaysType + '\',\'' + row.holidaysYear + '\');"></i>&nbsp;&nbsp;</div>';
	                      } 
                     	}
                    	res = res +'<i class="fa fa-trash" title="Delete" style="color: #418bca;" onclick="deleteRecord(\'' + row.holidaysId + '\');"></i></div>';
                    	return res;
                   }
                  }
             ],
            tableTools: {
                "sRowSelect": "single",
            },
            initComplete: function () {
                $("#msg_tbl tr td").css('cursor', 'default');
            },
        });        
    });
    
    function editContactName(id,name,fromDt,toDt,holidaysType,holidaysYear) {
        $("#msg_modal_dialog").modal("show");
        $("#msg_frm").css("display","none");
        var id = id;
        var name = name;     
        var holidaysType = holidaysType;
        var holidaysYear = holidaysYear;
        var operation = "edit";
        var fromDt = fromDt;
        var toDt = toDt;
    
        $('#frm_hol_msg #holidaysId').val(id);
        $('#frm_hol_msg #flex1').val(operation);
        $('#frm_hol_msg #names').val(name);
        if(holidaysType === 'W'){
        	var fromDtSplit = fromDt.split("/")[0];
        	var weekFromDt = fromDtSplit.substring(1);
        	var ToDtSplit = toDt.split("/")[0];
        	var weekToDt = ToDtSplit.substring(1);
        	$('#frm_hol_msg #weekFromDt').val(weekFromDt);
            $('#frm_hol_msg #weekToDt').val(weekToDt);           
            $('#weekendDt').show();
            $('#holidayDt').hide();
        } else if(holidaysType === 'H'){
            $('#frm_hol_msg #fromDt').val(fromDt);
            $('#frm_hol_msg #toDt').val(toDt);
            $('#weekendDt').hide();
            $('#holidayDt').show();
        }
        $('#frm_hol_msg #holidaysType').val(holidaysType);
        $('#frm_hol_msg #holidaysYear').val(holidaysYear);
            $('#frm_hol_msg #holidaysType').prop('disabled', true);
            $('#frm_hol_msg #holidaysYear').prop('disabled', true);
    }   
    
    function deleteRecord(id) {
        block('block_body'); 
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/deleteHoliday.do",
       
            data: {"crmHolidays.holidaysId": id, "flex1": "delete"},
            success: function (result) {
                if (result.message !== null)
                {
               	     alert("Error: " + result.message);
                } else {
                	$("#search_holidays").trigger('click');
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
</script>