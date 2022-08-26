<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : taskClose
    Created on : Feb 13, 2019, 10:56:24 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<s:hidden name="company" id="company" />
<s:hidden name="task.ctId" id="ctId_close_id"/>
<s:hidden name="task.crmTasksLog.ctlId" id="ctId_log_id"/>
<div class="form-fields clearfix">
    <%--div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
        <s:select name="task.ctStatus" id="ctStat" list="statusList" listKey="key" listValue="value" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="Status"/>
        <label class="textboxlabel">Status</label>
    </div--%>
    <s:hidden name="task.ctStatus" id="ctStat"></s:hidden>
    <s:hidden name="task.ctCloseYn" id="ctCloseYn"></s:hidden>
        <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
        <s:select name="task.ctCloseCode" id="ctCloseCode" list="statusRemarksList" listKey="key" listValue="value"  listTitle="info1" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.task.status.close.remarks')}"/>
        <label class="textboxlabel"><s:text name="lbl.task.close.type"/><span>*</span></label>
    </div>
</div>
<div class="form-fields clearfix">
    <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
        <s:textarea name="task.ctCloseRemarks" id="ctCloseRemarks" title="%{getText('lbl.please.enter.message')}" maxLength="2000" cssClass="textbox"/>
        <label class="textboxlabel"><s:text name="lbl.remarks"/><span>*</span></label>
    </div>
</div>