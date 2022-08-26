<%-- 
    Document   : feedbackQuestion
    Created on : 3 Apr, 2017, 11:16:29 AM
    Author     : palani.rajan
--%>
<%@page import="qa.com.qic.anoud.vo.KeyValue"%>
<%@page import="qa.com.qic.anoud.vo.FeedBackInfoBO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<s:set var="suggestYN"><s:property value="%{#parameters['suggestYN']==null?'Y':#parameters['suggestYN']}"/></s:set>
<div class="col-md-12" id="fbMsgModalBody">
    <div id="feedbackFrmMsg" style="display:<s:if test="hasActionErrors()">block</s:if><s:else>none</s:else>;" class="alert alert-warning">
        <s:iterator value="actionErrors">
            <ul><li><s:property escapeHtml="true"/></li></ul>
        </s:iterator>
    </div>
    <s:form name="feedbackFrm" id="feedbackFrm" action="submitFeedback">
        <s:hidden name="formId" id="formId"/>
        <s:hidden name="custId"/>
        <s:hidden name="emailId"/>
        <s:hidden name="customer.civilId"/>
        <s:hidden name="company"/>
        <s:hidden name="userName"/>
        <s:hidden name="mobileNo"/>
        <div class="row">                            
            <div class="col-md-12">
                <%
                    List<FeedBackInfoBO> feedback = (ArrayList<FeedBackInfoBO>) request.getAttribute("feedbackList");
                    if (feedback != null && !feedback.isEmpty()) {
                        int i=0;
                                    for (FeedBackInfoBO feedbackDetail : feedback) {
                                   %>
                <div class="col-md-12 margin-top-20">
                    <b><%=feedbackDetail.getId()%>. <%=feedbackDetail.getQuestion()%></b>
                    <input type="hidden" name="questions" value="<%=feedbackDetail.getId()%>"/>
                </div>
                <div class="col-md-12 clearfix margin-top-5" style="background-color: #f9f9f9;">
                    <% for (KeyValue keyValueBO : feedbackDetail.getOptions()) {
                     i++;%>
                    <div class="col-md-2" style="width: 19.666667%;" id="feedbk">
                        
                        <p><img alt="" src='${pageContext.request.contextPath}/images/online/<%=keyValueBO.getInfo1()%>' align="top" height="26" width="26"></p>
                        <span class="">
                            <input name="q_opt_<%=feedbackDetail.getId()%>" value="<%=keyValueBO.getKey()%>" type="radio" id="q_opt_<%=feedbackDetail.getId() + i %>" class="with-gap radio-col-red" />
                            <label for="q_opt_<%=feedbackDetail.getId()+i%>"></label>
                        </span>
                        <p><%=keyValueBO.getValue()%></p>
                        
                    </div>
                    <% } %>
                </div>
                <%
                        }
                    }
                %>
                <s:if test='#suggestYN == "Y"'>
                    <div class="col-md-12 no-padding margin-top-20">
                        <b><s:text name="Your Suggestions" /></b>&nbsp;&nbsp;(<s:text name="lbl.max.char"/> <font id="suggest_len_left"></font>)
                        <s:textarea name="suggestions" id="suggestions" cssClass="form-control" maxlength="2000" />
                       <div class="help-info"><span id="textarea_feedback"></span></div>
                    </div>
                </s:if>
            </s:form>
        </div>
        </div>
    <div class="form-group col-md-12">
        <input type="button" class="save-btn btn btn-primary" id="fb_submit_btn" style="float: right" onclick="ajaxSubmitFeedback();"
               value="Submit"/>
</div>
</div>
<script type="text/javascript">
 $(document).ready(function () {
           var text_max = 2000;
        $('#textarea_feedback').html(text_max + ' characters remaining');

        $('#suggestions').keyup(function () {
            var text_length = $('#suggestions').val().length;
            var text_remaining = text_max - text_length;

            $('#textarea_feedback').html(text_remaining + ' characters remaining');
        });
        });
</script>
