<%-- 
    Document   : bulkSMSEmailTemplate
    Created on : 28 Apr, 2017, 1:03:00 PM
    Author     : palani.rajan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<s:form id="BulkSMSEmail" theme="simple" name="smsemailtemplateform" method="post" autocomplete="off">
      <div id="msg_email_temp" class="alert alert-danger" style="display: none;"></div>
    <%--Edit SMS_EMAIL TEMPLATE --%>
    <s:iterator  value='SMSEmailTemplateList' status="curStr">
        <table class="table table-striped"  align="center" cellSpacing="0" cellPadding="0">
            <tr>
                <th align="left" colspan="4"><s:text name="lbl.template"/> </th>
            </tr>
            <tr>
                <td>
                    <s:label value="Template Code"></s:label><span class="required">*</span>
                </td>
                <td>
                    <s:textfield  name="template_code" readonly="true"  cssClass="form-controlReadOnly" maxlength="12"></s:textfield>
                </td>
                <td>
                    <s:label value="Description"></s:label><span class="required">*</span>
                </td>
                <td>
                    <s:textfield  name="description" id="descriptionId" cssClass="form-control" cssStyle="width:210px" maxlength="240"></s:textfield>
                </td>
            </tr>
            <tr>
                <td><s:label value="Email To"></s:label></td>
                <td colspan="3">
                    <s:textfield cssStyle="width:608px" name="emailto" maxlength="240" cssClass="form-control"></s:textfield>
                </td>
            </tr>
            <tr>
                <td>
                    <s:label value="Email From"></s:label><span class="required">*</span>
                </td>
                <td colspan="3">
                    <s:textfield cssStyle="width:608px" name="emailfrom" id="emailfromId" maxlength="240" cssClass="form-control"></s:textfield>
                </td>
            </tr>
            <tr>
                <td><s:label value="Cc"></s:label>
                </td>
                <td colspan="3">
                    <s:textfield cssStyle="width:608px;" name="emailcc"  maxlength="240" cssClass="form-control"></s:textfield>
                </td>
            </tr>
            <tr>
                <td><s:label value="Email Subject"></s:label></td>
                <td colspan="3">
                    <s:textfield cssStyle="width:608px;" name="emailsubject"  maxlength="240" cssClass="form-control"></s:textfield>
                </td>
            </tr>
            <tr>
                <td valign="top"><s:label value="Email Body"></s:label></td>
                <td colspan="3">
                    <%--s:url value="/" var="cPath"></s:url>
                    <sjr:ckeditor
                        label="Your Text"
                        id="letterEditor"
                        name="emailbody"
                        width="750"
                        height="400"
                        toolbar="MyToolbar"
                        customConfig="%{cPath}/javascript/ckeditor.config.js"/--%>
                    <s:textarea cssStyle="width:608px;height:150px" name="emailbody" maxlength="4000" cssClass="form-control"></s:textarea>
                </td>
            </tr>
            <tr>
                <td></td>
                <td colspan="3">
                    <input type="button" style="float:right" class="buttonStyleDashNoSize" value="VIEW" onclick="OpenHTMLBody('BulkSMSEmail_emailbody');">
                </td>
            </tr>
            <tr>
                <td valign="top"><s:label value="SMS Text"></s:label></td>
                <td colspan="3">
                    <s:textarea  cssStyle="width:608px;height:50px" name="smstext" maxlength="2000" cssClass="form-control"></s:textarea>
                </td>
            </tr>
        </table>
        <table  align="center" width="90%">
            <tr>
                <td width="50%"  align="right"><s:submit cssClass="buttonStyleDash" value="UPDATE"  action="updatesmsemailtemplate"></s:submit></td>
            <td><s:submit value="CLOSE" cssClass="buttonStyleDash"  action="CancelSMSEmailTemplate"></s:submit></td>
            </tr>
        </table>
    </s:iterator>

    <s:if test="%{SMSEmailTemplateList == null}">
        <table class="table table-striped"  align="center" cellSpacing="0" cellPadding="0">
            <tr>
                <th align="left" colspan="4"><s:text name="lbl.template"/> </th>
            </tr>
            <tr>
                <td width="25%"><s:label value="Template Code"></s:label><span class="required">*</span></td>
                <td width="30%">
                    <s:if test='duplicateflag!="Yes"'>
                        <s:textfield  name="bulkSMSEmail.template_code" cssClass="form-control" maxlength="12"></s:textfield>
                    </s:if>
                    <s:else>
                        <s:textfield readonly="true"   name="bulkSMSEmail.template_code" cssClass="form-control" maxlength="12"></s:textfield>
                    </s:else>
                </td>
                <td width="15%"><s:label value="Description"></s:label><span class="required">*</span></td>
                <td width="30%">
                    <s:textfield  name="bulkSMSEmail.description" maxlength="240"   cssClass="form-control"></s:textfield>
                </td>
            </tr>
            <tr>
                <td><s:label value="Email To"></s:label></td>
                <td colspan="3">
                    <s:textfield cssStyle="width:608px" name="bulkSMSEmail.emailto" maxlength="240" cssClass="form-control"></s:textfield>
                </td>
            </tr>
            <tr>
                <td><s:label value="Email From"></s:label><span class="required">*</span></td>
                <td colspan="3">
                    <s:textfield cssStyle="width:608px" name="bulkSMSEmail.emailfrom"  maxlength="240" cssClass="form-control"></s:textfield>
                </td>
            </tr>
            <tr>
                <td><s:label value="Cc"></s:label></td>
                <td colspan="3">
                    <s:textfield cssStyle="width:608px;" name="bulkSMSEmail.emailcc"  maxlength="240" cssClass="form-control"></s:textfield>
                </td>
            </tr>
            <tr>
                <td><s:label value="Email Subject"></s:label></td>
                <td colspan="3">
                    <s:textfield cssStyle="width:608px;" name="bulkSMSEmail.emailsubject" maxlength="240" cssClass="form-control"></s:textfield>
                </td>
            </tr>
            <tr>
                <td valign="top"><s:label value="Email Body"></s:label></td>
                <td colspan="3">
                    <%--s:url value="/" var="cPath"></s:url>
                    <sjr:ckeditor
                       label="Your Text"
                       id="letterEditor"
                       name="emailbody"
                       width="750"
                       height="400"
                       toolbar="MyToolbar"
                       customConfig="%{cPath}/javascript/ckeditor.config.js"/--%>
                    <s:textarea cssStyle="width:608px;height:150px" name="bulkSMSEmail.emailbody" maxlength="4000" cssClass="form-control"></s:textarea>
                    <input type="button" style="float:right" class="btn btn-warning" value="VIEW" onclick="OpenHTMLBody('BulkSMSEmail_emailbody');">
                </td>
            </tr>
            <tr>
                <td valign="top"><s:label value="SMS Text"></s:label></td>
                <td colspan="3">
                    <s:textarea cssStyle="width:608px;height:50px" name="bulkSMSEmail.smstext" maxlength="2000" cssClass="form-control"></s:textarea>
                </td>
            </tr>
            </table>
                
                <table  align="center" width="90%" style="padding-bottom:20px">
                    <tr>
                        <td width="50%"  align="right"><button type="button" class="save-btn btn btn-primary" id="btn_sms_update" style="float: right;" onclick="updateTemplate();">&#10004;<s:text name="btn.update"/></button> </td>
                <td  width="50%" style="padding-left:26px;"> <button type="button" class="close-btn btn" id="btn_sms_close" data-dismiss="modal" aria-hidden="true">&#10006;<s:text name="btn.close"/></button></td>
            </tr>
        </table>
    </s:if>
</s:form>

<script type="text/javascript">
    function updateTemplate(){
     block('block_body');
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/updatesmsemailtemplate.do",
            data: $("#BulkSMSEmail").serialize(),
            success: function (result) {
                if(result.messageType==='E'){
                    $("#msg_email_temp").empty().html(result.message).show();
                }else{
                      $('#plugin_modal_dialog').modal('hide');
                }
            },
            error: function (xhr, status, error) {
                alert(error);
            },
            complete: function () {
                unblock('block_body');
            }
        });
  }
    </script>