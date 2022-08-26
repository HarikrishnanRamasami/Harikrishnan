<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : task
    Created on : Mar 14, 2017, 11:55:52 AM
    Author     : ravindar.singh
--%>

<%@page import="qa.com.qic.common.util.ApplicationConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>
    <div class="dash-leads row" style="border-top:0!important">
        <%--div class="my-bord">
            <h3>Tasks</h3>
        </div--%>
        <div class="my-bord">
            <s:form class="form-inline" id="frm_search" name="frm_search" method="post" theme="simple">
                <div class="col-sm-12 col-md-12 Tmar">
                    <div class="row">
                        <div class="form-group padR">
                            <%--label>Status</label--%>
                            <s:select class="form-control" name="task.ctStatus" id="search_ctStatus" list="statusList" listKey="key" listValue="value" headerKey="ALL" headerValue="All"/>
                        </div>
                        <div class="form-group padR">
                            <%--label>User</label--%>
                            <s:select class="form-control" name="task.ctAssignedTo" id="search_ctAssignedTo" list="userList" listKey="key" listValue="value" />
                        </div>
                        <div class="form-group padR">
                            <%--label>Date</label--%>
                            <div id="task_range" class="form-control" style="background: #fff; cursor: pointer; padding: 6px 12px; border: 1px solid #ccc;">
                                <i class="fa fa-calendar"></i>&nbsp;
                                <span><s:text name="lbl.task.not.selected"/></span> <i class="fa fa-caret-down pull-right" style="margin-top: 5px;"></i>
                                <s:hidden name="task.ctAssignedDt" id="search_ctAssignedDt"/>
                            </div>
                        </div>
                        <div class="form-group padR">
                            <%--label>Category</label--%>
                            <s:select class="form-control" name="task.ctCatg" id="search_ctCatg" list="categoryList" listKey="key" listValue="value" headerKey="ALL" headerValue="All" />
                        </div>
                        <div class="form-group padR">
                            <%--label>Sub-Category</label--%>
                            <s:select class="form-control" name="task.ctSubCatg" id="search_ctSubCatg" list="#{}" listKey="key" listValue="value" headerKey="ALL" headerValue="All" />
                        </div> 
                        <div class="form-group padR">
                            <button type="button" id="search_task" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-search"></i> <s:text name="btn.search"/></button>
                        </div>
                    </div>
                </div>
                <s:if test='"Y".equals(enableForwardYn)'>
                    <div class="col-sm-12 col-md-12 Tmar" style="margin-bottom: 10px; margin-top:15px;">
                        <div class="row bor2" style="padding-top:7px !important;">
                            <div class="form-group padR assignAll">
                                <label class="text-black"><s:text name="lbl.task.assigned.to"/></label>
                                <s:select id="assignAll_ctAssignedTo" list="assignToList" listKey="key" listValue="value" cssClass="form-control" />
                            </div>
                            <div class="form-group padR assignAll">
                                <button type="button" id="btn_fwd_bulk_task" class="btn btn-primary tmargin cbtn" style="margin-top: 1px;"><i class="fa fa-fast-forward"></i> <s:text name="btn.forward"/></button>
                            </div>
                        </div>
                    </div>
                </s:if>
            </s:form>
        </div>
        <%--div class="col-sm-12 col-md-1 board-icons1 Rbtn"--%>
        <%--button class="btn btn-warning tmargin cbtn" onclick="UploadMyTaskFile();">Upload</button--%>
        <%--button class="btn btn-warning tmargin cbtn" id="btn_add_task" data-dismiss="modal" aria-hidden="true" style="width: 74px;">Add</button>
    </div--%>
    </div>
    <!--div class="row">
        <div class="col-md-9"></div>
        <div class="col-md-3">
            <div id="datatable_search" style="float: right;"></div>
        </div>
    </div-->
    <div class="row">
        <div class="col-md-12">
            <table id="task_tbl" class="table table-striped table-bordered display dataTable dtr-inline">
                <thead>
                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                        <!--th>Name</th-->
                        <!--th>Priority</th-->
                        <th style="text-align: center;"><input type="checkbox" id="ckbCheckdAll" name="select_all" class="checkBoxClass"/> <s:text name="Select"/>  </th>
                        <th><s:text name="lbl.id"/></th>
                        <th><s:text name="lbl.category"/></th>
                        <th style="width: 10%;"><s:text name="lbl.sub.category"/></th>
                        <th><s:text name="lbl.ref.no"/></th> 
                        <!--                    <th>Ref Id</th>  -->
                        <th><s:text name="lbl.mobile"/></th>
                        <!--th>Subject</th-->
                        <!--th>Message</th-->
                        <th style="width: 8%;" ><s:text name="lbl.due.on"/></th>
                        <!--                            <th style="width: 11%;">Remind Before</th>-->
                        <!--                             <th style="width: 10%;">Assigned To</th> -->
                        <!--                             <th class="no-sort">Status</th> -->
                        <th style="width: 8%;"><s:text name="lbl.task.assigned.date"/></th>
                        <th class="no-sort" style="text-align: center;"><s:text name="lbl.action"/></th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div id="task_add"></div>
<div class="modal fade" id="task_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog  modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><s:text name="lbl.task.close.form"/></h4>
                <div style="margin-top: -31px; float: right;">
                    <button class="save-btn btn btn-primary" id="btn_upd_task">&#10004;<s:text name="btn.save"/></button>
                    <button class="close-btn btn" data-dismiss="modal" id="btn_task_close">&#10006; <s:text name="btn.close"/></button>
                </div>
            </div>
            <div class="body" style="margin-top: 40px;">
                <div id="msg_closetask" class="alert alert-danger" style="display: none;"></div>
                <s:form id="frm_task_close" name="frm_task_close" method="post" theme="simple">
                    <s:include value="taskClose.jsp"/>
                </s:form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="taskfwd_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog  modal-mm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><s:text name="lbl.task.head.task.form="/></h4>
                <div style="margin-top: -31px; float: right;">
                    <button class="save-btn btn btn-primary" id="btn_fwd_task">&#10004;<s:text name="btn.save"/></button>
                    <button class="close-btn btn" data-dismiss="modal" id="btn_task_close">&#10006; <s:text name="btn.close"/></button>
                </div>
            </div>
            <div class="body" style="margin-top: 40px;">
                <div id="msg_fwdtask" class="alert alert-danger" style="display: none;"></div>
                <s:form id="frm_task_fwd" name="frm_task_fwd" method="" theme="simple">
                    <s:hidden name="company" id="company" />
                    <s:hidden name="task.ctId" id="ctId_fwd_id"/>
                    <div class="form-fields clearfix">
                        <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                            <select name="task.ctStatus" id="ctStatus" class="textbox" data-toggle="tooltip" data-placement="top" title="<s:text name="lbl.status"/>">
                                <s:iterator value="statusList" var="row">
                                    <s:if test='#row.key != "C"'>
                                        <option value="<s:property value="#row.key"/>"><s:property value="#row.value"/></option>
                                    </s:if>
                                </s:iterator>
                            </select>
                            <label class="textboxlabel"><s:text name="lbl.status"/></label>
                        </div>
                        <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                            <s:select name="task.ctPriority" id="ctPriority" list="priorityList" listKey="key" listValue="value" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.priority')}"/>
                            <label class="textboxlabel"><s:text name="lbl.priority"/></label>
                        </div>
                        <s:if test='"Y".equals(enableForwardYn)'>
                            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                                <s:select name="task.ctAssignedTo" id="ctAssignedTo" list="assignToList" listKey="key" listValue="value" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.task.assigned.to')}"/>
                                <label class="textboxlabel"><s:text name="lbl.task.assigned.to"/></label>
                            </div>
                        </s:if>
                        <div class="form-group col-sm-6 col-md-6 col-xs-12 spac taskhold">
                            <s:textfield name="task.ctDueDate" id="ctDueDate" cssClass="textbox calicon" title="%{getText('lbl.due.date')}" placeholder="DD/MM/YYYY HH:mm"/>
                            <label class="textboxlabel"><s:text name="lbl.due.date"/><span>*</span></label>
                        </div>
                        <div class="form-group col-sm-6 col-md-6 col-xs-12 spac taskhold">
                            <s:select name="task.ctRemindBefore" id="ctRemindBefore" cssClass="textbox" list="hoursRangeList" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.task.remind.before.hrs')}"/>
                            <label class="textboxlabel"><s:text name="lbl.task.remind.before.hrs"/><span>*</span></label>
                        </div>
                    </div>
                    <div class="form-fields clearfix">
                        <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                            <s:textarea name="task.ctCloseRemarks" id="ctCloseRemarks" maxLength="2000" title="%{getText('lbl.please.enter.message')}" class="textbox"/>
                            <label class="textboxlabel"><s:text name="lbl.remarks"/><span>*</span></label>
                        </div>
                    </div>
                    <div class="form-group col-sm-6 col-md-6 col-xs-12 spac taskhold">
                        <s:select name="task.ctReason" id="ctReason" list="reasonList" listKey="key" listValue="value" cssClass="textbox" data-toggle="tooltip" data-placement="top" headerKey="" headerValue="Select Reason" title="%{getText('lbl.common.reason')}"/>
                        <label class="textboxlabel"><s:text name="lbl.common.reason"/><span>*</span></label>
                    </div>
                    <div class="form-fields clearfix">                             
                        <div class="col-md-12 form-group">
                            <s:action name="loadFileUpload" executeResult="true" ignoreContextParams="true">
                                <s:param name="formId">frm_task_fwd</s:param>
                                <s:param name="count">2</s:param>
                            </s:action> 
                        </div>
                    </div>
                </s:form>
                <script type="text/javascript">
                    $(document).ready(function() {
                        $('#frm_task_fwd #ctStatus').on('change', function() {
                            if ($(this).val() === 'H') {
                                $('.taskhold').show();
                            } else {
                                $('.taskhold').hide();
                            }
                        });
                    });
                </script>
            </div>
        </div>
    </div>
</div>
<s:include value="taskView.jsp"/>
<script type="text/javascript">
    var numberOfMonths;
    function strtrunc(str, max, add) {
        add = add || '...';
        return (typeof str === 'string' && str.length > max ? str.substring(0, max) + add : str);
    }

    $(document).ready(function() {
        $('#task_tbl').on('draw.dt', function() {
            $('[data-toggle="tooltip"]').tooltip({html: true});
        });
        $('#frm_task_close #ctCloseRemarks').maxlength();
        $('#frm_task_fwd #ctCloseRemarks').maxlength();
        var start = moment("21/07/1982", "DD/MM/YYYY");
        var end = moment("21/07/1982", "DD/MM/YYYY");
        $('#task_range').daterangepicker({
            startDate: start,
            endDate: end,
            ranges: {
                'Today': [moment(), moment()],
                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
                'Clear': [moment("21/07/1982", "DD/MM/YYYY"), moment("21/07/1982", "DD/MM/YYYY")]
            },
            locale: {
                format: 'DD/MM/YYYY'
            }
        }, function(start, end, label) {
            if (label === 'Clear') {
                $('#task_range span').html('Not Selected');
                $('#search_ctAssignedDt').val('');
            } else {
                if (label === 'Custom Range') {
                    numberOfMonths = end.diff(start, 'days');
                    $('#task_range span').html(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
                } else {
                    $('#task_range span').html(label);
                }
                $('#search_ctAssignedDt').val(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
            }
        });
        $('#frm_task_fwd #ctDueDate').bootstrapMaterialDatePicker({format: 'DD/MM/YYYY HH:mm', minDate: new Date(), nowButton: true});
        task_tbl = $("#task_tbl").DataTable({
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
                "url": APP_CONFIG.context + "/loadTaskEntryData.do",
                "type": "POST",
                "data": function (d) {
                    return $.extend({}, d, $('#frm_search').serializeObject());
                },
                "dataSrc": "aaData"
            },
            columns: [
                {data: "ctId",
                    render: function(data, type, row, meta) {
                        if (type === "sort") {
                            return data;
                        }
                        if (row.ctId !== "" && row.ctId !== null && row.ctStatus === "P") {
                            data = '<input type="checkbox" class "checkBoxClass" value="' + row.ctId + '" >&nbsp;<a href="javascript:openTaskLog(' + row.ctId + ', \'' + row.ctStatus + '\');">' + data + '</a>&nbsp;';
                        }
                        if (row.ctSlaViolated === "1") {
                            data += '<span><i class="fa fa-flag" title="SLA Violated" style="color: #ef1515"></i></span>&nbsp;';
                        }
                        if (row.ctFlex05 !== "" && row.ctFlex05 !== null) {
                            data += '<span><i class="fa fa-paperclip" title="Attachments" style="color: #080808"></i></span>&nbsp;';
                        }
                        if (row.ctStatus === "P") {
                            data += '<i class="fa fa-hourglass-half" style="color:green" title="Pending" aria-hidden="true"></i>';
                        } else if (row.ctStatus === "H") {
                            data += '<i class="fa fa-hourglass-start" style="color: #cccc03" title="On Hold" aria-hidden="true"></i>';
                        } else if (row.ctStatus === "C") {
                            data += '<i class="fa fa-hourglass-end" style="color:red" title="Closed" aria-hidden="true"></i>';
                        }
                        return data;
                    }
                },
                {data:"ctId"},
                {data: "ctCatgDesc",
                    render: function(data, type, row, meta) {
                        if (type === "sort") {
                            return data;
                        }
                        if (data === null) {
                            data = "";
                        }
                        if (row.ctPriority !== "" && row.ctPriority !== null && row.ctPriority === 'C') {
                            return '<div class="task-critical priority" title="Critical" data-toggle="tooltip" data-placement="top">&nbsp;</div>&nbsp;' + data;
                        } else if (row.ctPriority !== "" && row.ctPriority !== null && row.ctPriority === 'H') {
                            return '<div class="task-high priority" title="High" data-toggle="tooltip" data-placement="top">&nbsp;</div>&nbsp;' + data;
                        } else if (row.ctPriority !== "" && row.ctPriority !== null && row.ctPriority === 'N') {
                            return '<div class="task-normal priority" title="Normal" data-toggle="tooltip" data-placement="top">&nbsp;</div>&nbsp;' + data;
                        }
                        else if (row.ctPriority !== "" && row.ctPriority !== null && row.ctPriority === 'L') {
                            return '<div class="task-low priority" title="Low" data-toggle="tooltip" data-placement="top">&nbsp;</div>&nbsp;' + data;
                        }
                    }
                },
                {data: "ctSubCatgDesc", "width": "18%"},
                //  {data: "ctAffSource", "width": "18%"},
                {data: "ctRefNo"},
                //  {data:  "ctRefId"},
                {data: "ctFlex01", "width": "5%",
                    render: function(data, type, row, meta) {
                        if (type === "sort") {
                            return data;
                        }
                        if (data !== "" && data !== null) {
                            return  '<center>' + data + '<span class="contact-icons" style= "color: #3fde3f" onclick="calltask(\'' + row.ctFlex01 + '\',\'' + row.ctId + '\');"><i class="fa fa-phone-square hand" id ="btn_call"></i></span>';
                        } else {
                            return data;
                        }
                    }
                },
                /*{data: "ctSubject", "width": "18%",
                 'targets': 1,
                 "render": function (data, type, row, meta) {
                 if (data === null) {
                 data = "";
                 }
                 if (type === 'display') {
                 data = strtrunc(data, 25);
                 }
                 return '<span style="text-align: left !important;" title="' + String(row.ctMessage).replace(/\r\n|\n|\r/g, '<br/>') + '" data-toggle="tooltip">' + data + '</span>';
                 
                 }
                 },*/
                // {data: "ctMessage"},
                {data: "ctDueDate",
                    render: function(data, type, row, meta) {
                        if (data && type === "sort") {
                            data = moment(data, "DD/MM/YYYY HH:mm").format('YYYYMMDDHHmm');
                        } else {
                            data = moment(data, "DD/MM/YYYY HH:mm").format('DD/MM/YYYY HH:mm');
                        }
                        return data;
                    }
                },
                {data: "ctAssignedDt",
                    render: function(data, type, row, meta) {
                        if (type === "sort") {
                            data = moment(data, "DD/MM/YYYY HH:mm").format('YYYYMMDDHHmm');
                        } else if (data != null) {
                            data = moment(data, "DD/MM/YYYY HH:mm").format('DD/MM/YYYY HH:mm');
                            data += '&nbsp;<span><i class="fa fa-info" title="' + row.ctAssignedTo + '" style="cursor:pointer;color: #418bca;"></i></span>';
                        }
                        return data;
                    }
                },
                {data: "ctStatus",
                    render: function(data, type, row, meta) {
                        return '<center>' +
                                ((row.ctStatus !== "" && row.ctStatus !== null && row.ctStatus === 'C') ? '' :
                                        //'<i class="fa fa-pencil" title="Modify" data-toggle="tooltip" style="cursor:pointer;color:#418bca;" onclick="modifyTask(\'' + row.ctId + '\');"></i>' )+
                                        '<i class="fa fa-eye" title="View Message" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="viewTasks(' + row.ctId + ');"></i>&nbsp;&nbsp' +
                                        '<i class="fa fa-forward" title="Forward" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="forwardTask(\'' + row.ctId + '\');"></i>') +
                                //'<i class="fa fa-eye" title="View" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="viewTask(\'' + row.ctId + '\');"></i>') +
                                        ((row.ctStatus !== "" && row.ctStatus !== null && row.ctStatus === 'P' || row.ctStatus === 'H') ? '' :
                                                '&nbsp;&nbsp;<i class="fa fa-eye" title="View" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="viewTask(\'' + row.ctId + '\');"></i>') +
                                        ((row.ctStatus !== "" && row.ctStatus !== null && row.ctStatus === 'C') ? '' :
                                                '&nbsp;&nbsp;<i class="fa fa-close" title="Close" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="closeTask(\'' + row.ctId + '\');"></i>') +
                                        //'&nbsp;&nbsp;<i class="fa fa-comments" title="Logs" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="openTaskLog(' + row.ctId + ', \''+ row.ctStatus + '\');"></i>' +
                                                ((row.ctRefId === null || row.ctRefId === '' || row.ctRefId === 'undefined') ? '' :
                                                        '&nbsp;&nbsp;<i class="fa fa-address-card" title="View customer information" style="cursor:pointer;color: #418bca;" data-toggle="tooltip" \n\
                                onclick="openCustomerPopup(APP_CONFIG.context + \'/customer360.do?company=<s:property value="#session.USER_INFO.companyCode" />&civilid=' + row.ctRefId + '&search=plugin\');"></i>') +
                                                '</center>';
                    }
                }
            ],
            columnDefs: [
             {targets: 0, orderable: false}
            ],
            dom: 'lBftrpi',
            tableTools: {
                "sRowSelect": "single",
            },
            buttons: [
                {
                    extend: 'excel',
                    text: '<i class="fa fa-file-excel-o"></i> Download',
                    className: 'btn btn-primary',
                    title: (($('#search_ctStatus').val() === 'P' ? 'Pending' : $('#search_ctStatus').val() === 'C' ? 'Closed' : 'On Hold') + ' Task'),
                    filename: 'task_' + ($('#search_ctStatus').val() === 'P' ? 'pending' : $('#search_ctStatus').val() === 'C' ? 'closed' : 'hold') + '_' + $("#search_ctAssignedTo").val(),
                    exportOptions: {columns: [0, 1, 2, 3, 4, 5, 6]}//columns: [ 0, ':visible' ]
                },
                {
                    text: '<i class="fa fa-plus"></i> Add',
                    className: 'btn btn-warning',
                    action: function(e, dt, node, config) {
                        block('block_body');
                        $.ajax({
                            type: "POST",
                            url: APP_CONFIG.context + "/openTaskEntryForm.do?operation=add",
                            data: {},
                            success: function(result) {
                                $('#popup_custom').html(result);
                                $('.popup-wrap').addClass('popup-open');
                                $('#overlay').show();
                            },
                            error: function(xhr, status, error) {
                                alert("Error: " + error);
                            },
                            complete: function() {
                                unblock('block_body');
                            }
                        });
                    }
                }
                //{extend: 'colvis', className: 'btn btn-primary'}
            ],
            initComplete: function() {
                $("#task_tbl tr td").css('cursor', 'default');
                //$('#datatable_search').html($('#task_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
                $('#task_tbl_filter label input').attr('placeholder', 'Search..').removeAttr('type');
                $("#search_ctStatus").data('dt-uid', $("#search_ctAssignedTo").val());
            },
            "fnRowCallback": function(nRow, data, row, iDisplayIndex, iDisplayIndexFull) {
                if (data.ctReadYn === '0') {
                    $(nRow).attr('style', 'font-weight:bold! important;');
                }
            }
        });

        $('thead input[name="select_all"]', task_tbl.table().container()).on('click', function(e){
            var table = $('#task_tbl').DataTable();
            var p = table.rows({ page: 'current' }).nodes();
            if(this.checked){
                table.$('input[type="checkbox"]', p).prop('checked', this.checked);
            }else {
                table.$('input[type="checkbox"]', p).removeProp('checked');
            }
        });
    });

</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/task.js"></script>
<script type="text/javascript">
    function openForm(mode, type, id) {
        var data = {"task.ctId": id, "operation": mode};
        data["operation"] = mode;
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/openTaskEntryForm.do",
            data: data,
            success: function(result) {
                $('#overlay').hide();
                $('#popup_custom').html(result);
                $('.popup-wrap').addClass('popup-open');
                $('#overlay').show();
                $("#frm_task #block_task_attach").load(APP_CONFIG.context + "/LoadDmsDocs.do?docInfoBean.cdiTransId=" + id + "&docInfoBean.cdiDocType=CRM_TASKS&companyCode=${company}");
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
                unblock('block_body');
            }
        });
    }
    function viewCustomer(ctRefId) {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/customer360.do?company=<s:property value="#session.USER_INFO.companyCode" />&search=view&civilid=" + ctRefId,
            data: {"flex1": "view"},
            success: function(result) {
                $('#plugin_modal_dialog').modals().mm(result);
            },
            error: function(xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function() {
                unblock('block_body');
            }
        });
    }


    function viewAttachment(id, filename) {
        var urlString = "<%=request.getContextPath()%>/ShowDmsImageServlet?isThumbNail=N&docName=" + filename + "&module=<s:property value="#session.USER_INFO.companyCode" />&project=<%= ApplicationConstants.DMS_PROJECT_MED%>&para1=EMAIL-TASK&para2=" + id;
        var LeftPosition = (screen.width) ? (screen.width - 500) / 2 : 100;
        var TopPosition = (screen.height) ? (screen.height - 300) / 2 : 100;
        var popup = window.open(urlString, '_newPage', 'width=1000px' + ',height=1000px' + ',left=' + LeftPosition + ',top=' + TopPosition);
        popup.focus();
    }
    function downloadAttachment(id, fileName) {
        var urlString = "<%=request.getContextPath()%>/ShowDmsImageServlet?disposition=attachment&isThumbNail=N&docName=" + fileName + "&module=<s:property value="#session.USER_INFO.companyCode" />&project=<%= ApplicationConstants.DMS_PROJECT_MED%>&para1=EMAIL-TASK&para2=" + id;
        $("#frm_search").prop("action", urlString);
        $("#frm_search").submit();
    }
    function downloadDms(id, filename) {
        var urlString = APP_CONFIG.context + "/downloadDmsFile.do?docInfoBean.cdiLink=" + id + "&docInfoBean.cdiDocType=CRM_TASKS&companyCode=${company}&docInfoBean.cdiDocName=" + filename;
        $("#frm_search").prop("action", urlString);
        $("#frm_search").submit();
    }

</script>