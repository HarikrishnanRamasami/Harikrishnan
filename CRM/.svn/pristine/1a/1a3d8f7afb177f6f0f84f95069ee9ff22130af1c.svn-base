<%-- 
    Document   : bulkSMSEmailInfo
    Created on : 27 Apr, 2017, 12:24:27 PM
    Author     : palani.rajan
--%>

<%@page import="java.util.List"%>
<%@page import="qa.com.qic.anoud.vo.KeyValue"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page errorPage="/jsp/common/ErrorMsg.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery-ui.js"></script>
 
<div class="">
    <div class="col-md-12 right-pad">
        <div class="dash-leads" style="border-top:0!important">
            <div class="my-bord">
                <h3><s:text name="lbl.bulk.sms.email"/></h3>
            </div>
            <div class="my-bord1">
                <div id="message" class="alert alert-danger" style="display: none;">
                    &nbsp;<s:actionerror /><s:fielderror /><s:property value="errorMsg" />&nbsp;
                </div>
            </div>
            <s:form name="bulkSMSEmailInfo" id="bulkSMSEmailInfo" theme="simple" action="%{#request.contextPath}/uploadExcel.do" method="post" 
                    autocomplete="off" enctype="multipart/form-data">            
                <s:hidden name="operation" id="hid_operation" />            
                <s:hidden id="preview_content"/>
                <%
                    List<KeyValue> customizeList = (List<KeyValue>) request.getAttribute("customizeList");
                %>
                <div class="form-fields clearfix pt-10">                    
                    <div class="container-fluid">
                        <div class="row">
                            <div class=" col-sm-6 col-md-6 col-xs-12">  
                                <h4 class=""><s:text name="lbl.send.option"/></h4>
                                <div class="form-line newRadioStyle">
                                    <!-- ----->
                                    <div class="switch">
                                        <s:radio name="sendOption" class="radio" list="sendOptionList"/>                            
                                    </div></div>
                                <!--                    </div>
                                                    <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">-->
                            </div>                        
                            <div class="col-sm-6 col-md-6 col-xs-12 ">
                                <div class="form-line">
                                    <h4 class=""><s:text name="lbl.to"/>:</h4>
                                    <span class="">  <s:select name="sendTo" list="sendToList" id="sendTo" onchange="setList(this.value)" cssClass="form-control"/></span>
                                </div>
                            </div>
                        </div>  
                    </div>  
                    <!-- -->
                </div>
                <div class="form-fields clearfix">
                    <br />
                    <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                        <span class=""><s:text name="lbl.file.path"/></span>
                        <div class="form-line">
                            <a class=' file-upload-wrapper' href='javascript:;'>                                
                                <s:file  name="excelFile" style="position:absolute; z-index:2;"/>
                                <!-- <label class="custom-file-label" for="customFile">Choose file</label>-->
                            </a>
                        </div>
                    </div>
                    <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                        <a class="btn btn-warning" href="<%=request.getContextPath()%>/Forms_Templates/MAIL_UPL_TEMPLATE.xls" target="_blank" style="padding: 8px 5px; text-decoration: none;margin-top: 15px;"><s:text name="lbl.sample.excel"/></a>
                    </div>
                </div>
                <div class="form-fields clearfix" id="CustomizedTextDivId">
                    <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                        <span class=""><s:text name="lbl.file.customize"/></span>
                        <div class="form-group">
                            <% for (KeyValue custObj : customizeList) {%>
                            <div class="form-group col-sm-12">
                                <input type="checkbox" name="customize" value="<%=custObj.getKey()%>" id="customize<%=custObj.getKey()%>" class="form-control" />
                                <label for="customize<%=custObj.getKey()%>"><%=custObj.getValue()%></label><br>
                            </div>
                            <%}%>
                        </div>
                    </div>
                    <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                        <div id="includePolId" style="text-align: center" ><input type="checkbox" id="includePol" name="includePol" class="form-control"> <label for="includePol" style="color: red"><s:text name="lbl.include.policy.details"/></label>  </div>  
                    </div>                    
                </div>
                <div class="form-fields clearfix">
                    <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                        <span class=""><s:text name="lbl.template"/></span>
                        <div class="form-line">
                            <s:select name="template" list="templateList" id="templateId" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                        <input type="button" name="addEdit" value="Add/Edit Template" class="btn btn-warning" style="margin-top: 15px;"onclick="editBulkSMSEMailInfo($('#templateId').val());"/> 
                        <input type="button" name="extractData" value="Extract Data" style="margin-top: 15px;" onclick="ExtractData(this.form);" class="btn btn-warning" /> 
                    </div>
                </div>
            </s:form>
            <div id="bulksmsDetail">
                <s:include value="WEB_INF/pages/bulksms/bulkSMSEmailDetails.jsp" />
            </div>
            <div id="sendEmail"></div>
            <div id="TransRemarksWindowId" style="display: none;">
                <s:include value="/pages/bulksms/bulkSMSEmailTemplate.jsp" />
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {

        if ($("#hid_operation").val() === 'upload') {
            $.ajax({
                type: "POST",
                url: "<%=request.getContextPath()%>/loadExtractDataPage.do?operation=upload",
                data: {},
                async: true,
                success: function (result) {
                    $("#bulksmsDetail").empty().html(result);
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                }
            });
        }
        $("#plugin_modal_dialog").on('shown.bs.modal', function () {
            $(this).find("input:visible:enabled:first").focus();
        });

        if ('${sendTo}' === "C") {
            $("#CustomizedTextDivId").show();
            $("#ExcelFileDivId").hide();
            $("#includePolId").show();
        } else {
            $("#CustomizedTextDivId").hide();
            $("#ExcelFileDivId").show();
            $("#includePolId").hide();
        }

        $(document).on('click', '.liClik', function () {
            var code = $(this).attr('value');
            //CKEDITOR.instances['letterEditor'].insertText(code);		
        });
    });

    function setList(val) {
        $("#message").html("");
        $("#bulksmsDetail").html("");
        if (val == "C") {
            $("#CustomizedTextDivId").show();
            $("#ExcelFileDivId").hide();
            $("#includePolId").show();
        } else {
            $("#CustomizedTextDivId").hide();
            $("#ExcelFileDivId").show();
            $("#includePolId").hide();
        }
    }

    function editBulkSMSEMailInfo(id) {
        var url = "${pageContext.request.contextPath}" + "/loadTempCodeSummary.do";
        var data = {template_code: id};
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            complete: function () {
            },
            success: function (result) {
                $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm modal-md");
                $('#plugin_modal_dialog .modal-dialog').addClass("modal-lg");
                $('#plugin_modal_dialog .modal-content').empty().html(result);
                $('#plugin_modal_dialog').modal('show');
            },
            error: function (error) {
                var divId = "OnlineAddlInfoErrorDiv";
                $('#' + divId).html(error);
                $('#' + divId).show();
                $('#' + divId).delay(5000).fadeOut('slow');
            }
        });
    }
    function openRemarksDialog() {
        var template = $("#templateId").val();
        /*while ($("#BulkSMSEmail_emailbody").val().match(/&lt/g, /&gt/g)) {
         $("#BulkSMSEmail_emailbody").val($("#BulkSMSEmail_emailbody").val().replace(/&lt;/g, '<').replace(/&gt;/g, '>'));
         }*/

        var addEdit;
        if (template != '')
            addEdit = "U";
        else
            addEdit = "I";

        $('#plugin_modal_dialog .modal-content').empty().html($("TransRemarksWindowId").html());
        $('#plugin_modal_dialog').modal('show');
        /*   $('#TransRemarksWindowId').dialog({
         autoOpen: false,
         resizable: true,
         draggable: true,
         closeOnEscape: false,
         width: 800,
         height: 620,
         modal: true,
         buttons: {
         "Save": function () {
         if ($("#BulkSMSEmail_template_code").val() === "") {
         alert("Enter Template Code");
         return false;
         }
         else if ($("#BulkSMSEmail_description").val() === "") {
         alert("Enter Description");
         return false;
         } else if ($("#BulkSMSEmail_emailfrom").val() === "") {
         alert("Enter Email From");
         return false;
         }
         var tempCode = $("#BulkSMSEmail_template_code").val();
         var desc = $("#BulkSMSEmail_description").val();
         var eFrom = $("#BulkSMSEmail_emailfrom").val();
         var emailto = $("#BulkSMSEmail_emailto").val();
         
         var emailcc = $("#BulkSMSEmail_emailcc").val();
         var emailsubject = $("#BulkSMSEmail_emailsubject").val();                        
         var emailbody =$("#BulkSMSEmail_emailbody").val();                              
         var smstext =$("#BulkSMSEmail_smstext").val();
         
         $("#modal_operation").val($("#hid_operation").val());
         $("#modal_sendOption").val($('input[name="sendOption"]:checked', "#bulkSMSEmailInfo").val());
         $("#modal_sendTo").val($("#sendTo").val());
         $("#modal_template").val($("#templateId").val());
         
         $("#modal_template_code").val(tempCode);
         $("#modal_description").val(desc);
         $("#modal_emailfrom").val(eFrom);
         $("#modal_emailto").val(emailto);
         $("#modal_emailcc").val(emailcc);
         $("#modal_emailsubject").val(emailsubject);
         $("#modal_emailbody").val(emailbody);
         $("#modal_smstext").val(smstext);
         $("#modal_addEdit").val(addEdit);
         $("#SmsEmailTamplateForm").attr("action", "<%=request.getContextPath()%>/SaveBulkSMSEmailtemp.do");
         $("#SmsEmailTamplateForm").submit();
         },
         "Close": function () {
         $(this).dialog("close");
         }
         }
         });
         $("#TransRemarksWindowId").dialog("open");
         $('.ui-dialog-titlebar').hide();
         $('.ui-dialog-buttonpane :button').addClass("btn-primary")*/
    }


    function ExtractData(form) {
        form = document.bulkSMSEmailInfo;
        var sendOption;
        if (document.bulkSMSEmailInfo.sendOption[0].checked) {
            sendOption = document.bulkSMSEmailInfo.sendOption[0].value;
        } else {
            sendOption = document.bulkSMSEmailInfo.sendOption[1].value;
        }
        var extractType = $("#sendTo").val();
        var customizeData = ",";
        var flag = false;
        var includePol;
        if (form.includePol.checked) {
            includePol = 1;
        } else {
            includePol = 0;
        }

        if (extractType === "C") {
            for (var i = 0; i < form.customize.length; i++) {
                if (form.customize[i].checked) {
                    customizeData = customizeData + form.customize[i].value + ",";
                    flag = true;
                }
            }
            if (flag) {
                $("#message").html('');
                block('block_body');
                $.ajax({
                    type: "POST",
                    url: "<%=request.getContextPath()%>/loadExtractDataPage.do?sendOption=" + sendOption + "&customize=" + customizeData + "&includePol=" + includePol,
                    data: {},
                    async: true,
                    success: function (result) {
                        $("#bulksmsDetail").empty().html(result);
                    },
                    error: function (xhr, status, error) {
                        alert("Error: " + error);
                    },
                    complete: function () {
                        unblock('block_body');
                    }
                });
            } else {
                $("#message").html("Select Customize Type");
                $("#bulksmsDetail").html("");
                // document.getElementById('loadingImg').innerHTML = "";
                return false;
            }
        } else {
            validateExcelFile(form);
            return false;
        }
    }

    function getMappedContent(url, id) {
        var xmlhttp;
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        } else {// code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.open("GET", url, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById(id).innerHTML = xmlhttp.responseText;
                document.getElementById('loadingImg').innerHTML = "";
                if (id == "bulksmsDetail") {
                    $("#bulksmsDetail").on('click', 'span.pagelinks>a, table#recordsList>thead>tr>th>a', function () {
                        $.get($(this).attr('href'), function (responseText, status, xmlHttpRequest) {
                            $('#displayTagDiv').html($('#displayTagDiv', responseText).html());
                        });
                        return false;
                    });
                } else if (id == "message") {
                    getMappedContent("<%=request.getContextPath()%>/DeleteExtractData.do", "bulksmsDetail");
                }
            }
        };
        xmlhttp.send(null);
    }

    function validateExcelFile(form) {
        var errorcode = "";
        var formObjName;
        var formObjValue = "";
        for (i = 0; i < form.elements.length; i++) {
            formObjName = form.elements[i].name;
            formObjValue = (form.elements[i].value).replace(/^\s\s*/, '').replace(/\s\s*$/, '');
            if (form.elements[i].type == "select-one") {
                if (formObjValue == '') {
                    /* if (formObjName == 'product') {
                     errorcode = '100040';
                     } else if (formObjName == 'scheme') {
                     errorcode = '100041';
                     } else if (formObjName == 'calcType') {
                     errorcode = '100044';
                     }*/
                }
            } else if (form.elements[i].type == "file") {
                if (formObjValue == '') {
                    if (formObjName == 'excelFile') {
                        errorcode = '100045';
                    }
                } else if ((formObjValue.toString()).indexOf(".xls") == -1 && (formObjValue.toString()).indexOf(".XLS") == -1) {
                    errorcode = '100043';
                }
            }
        }
        if (errorcode != "") {
            getMappedContent("<%=request.getContextPath()%>/GetErrorMessage.do?errorCode=" + errorcode + "&summa=" + Math.random(), 'message');
            return false;
        }
        form.operation.value = "upload";
        form.submit();
        return false;
    }

    function sendBulkSMSEmail() {
        var sendOption;
        if (document.bulkSMSEmailInfo.sendOption[0].checked) {
            sendOption = document.bulkSMSEmailInfo.sendOption[0].value;
        } else {
            sendOption = document.bulkSMSEmailInfo.sendOption[1].value;
        }

        var template = $("#templateId").val();
        if (template == '') {
            alert("Select Template");
            return false;
        }
        // document.getElementById('bulksmsDetail').innerHTML = '<img src="${pageContext.request.contextPath}/images/sendingEmail.gif"/>';
        blockDiv('bulksmsDetail');
        var url = "${pageContext.request.contextPath}" + "/SendBulkSMSEmail.do?template=" + template + "&sendOption=" + sendOption;
        var result = null;
        $.ajax({
            type: "POST",
            url: url,
            //  data: data,
            async: true,
            dataType: 'json',
            complete: function () {
                if (result != null) {
                    if (result.respMsg == "S")
                        if (sendOption == "E")
                            document.getElementById('bulksmsDetail').innerHTML = 'Email Send Successfully.';
                    if (sendOption == "S")
                        document.getElementById('bulksmsDetail').innerHTML = 'SMS Send Successfully.';
                }
                else {
                    document.getElementById('bulksmsDetail').innerHTML = 'Process Excution Failed';
                }
            }
            ,
            success: function (response) {
                result = response;
                unblockDiv('bulksmsDetail');
            },
            error: function (error) {
            }
        });

    }

    function preview() {
        var url = "${pageContext.request.contextPath}" + "/PreviewSMSEmail.do";
        var sendOption;
        if (document.bulkSMSEmailInfo.sendOption[0].checked) {
            sendOption = document.bulkSMSEmailInfo.sendOption[0].value;
        } else {
            sendOption = document.bulkSMSEmailInfo.sendOption[1].value;
        }
        var template = $("#templateId").val();
        if (document.getElementById('rowId').value == '') {
            alert('Select a Record ');
            return false;
        } else if (template == '') {
            alert("Select Template ");
            return false;
        }
        var rowId = document.getElementById('rowId').value;
        var data = {rowId: rowId, sendOption: sendOption, template: template};

        $.ajax({
            type: "POST",
            url: url,
            data: data,
            async: true,
            dataType: 'json',
            complete: function () {

            },
            success: function (result) {
                if (result != null) {
                    $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm modal-md");
                    $('#plugin_modal_dialog .modal-dialog').addClass("modal-lg");
                    $('#plugin_modal_dialog .modal-content').empty().html(result.respMsg);
                    $('#plugin_modal_dialog').modal('show');
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

    function selectRowId(rowId) {
        document.getElementById("rowId").value = rowId;
    }

    var popUpWin = null;
    function OpenHTMLBody(id) {
        var leftPosition = (screen.width) ? (screen.width - 600) / 2 : 100;
        var topPosition = (screen.height) ? (screen.height - 500) / 2 : 100;
        if (popUpWin == undefined || popUpWin.closed)
            popUpWin = window.open('', '', 'scrollbars=1, width=700,height=500,left=' + leftPosition + ',top=' + topPosition);
        var html = $("#" + id).val();
        // $(popUpWin.document.body).html(html);
        popUpWin.focus();
    }
</script>

<script>
$(document).on('change', ':file', function() {
    var input = $(this),
        numFiles = input.get(0).files ? input.get(0).files.length : 1,
        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    input.trigger('fileselect', [numFiles, label]);
});
</script>
 