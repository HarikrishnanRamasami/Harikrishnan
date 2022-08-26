<%-- 
    Document   : callFeedBack
    Created on : Dec 26, 2019, 3:49:23 PM
    Author     : soumya.gaur
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<a href="#" class="close-link">&#10006;</a>
<div class="task-form">
    <div class="popup-title">
        <h3><s:text name="lbl.call.feedBack"/></h3>
        <div style="float:right;" class="">
            <button class="close-btn btn" data-dismiss="modal">&#10006;<s:text name="btn.close"/></button>
        </div>
    </div>
    <table border="0" style="margin: 10px; width: 96%;">
        <tbody>
            <s:iterator value="feedBackList" status="parent" >
                <tr>
                    <td><b><label style="color: #bb3232;"><s:property value="#parent.index+1"/>. &nbsp;<s:property value="question"/></label></b></td>
                </tr>
                <tr>
                    <s:iterator value="options" status="row" var="opt">
                        <td style="height: 40px;">
                            <s:if test="callLogMap[#parent.index + 1] == (#row.index + 1)">
                                <i class="fa fa-check-circle-o" aria-hidden="true" style="color: green;"></i>
                            </s:if>
                            <s:else>
                                <i class="fa fa-circle-o" aria-hidden="true" style="color: #aaa;"></i>
                            </s:else>
                            <label><s:property value="value"/></label> 
                        </td>
                    </s:iterator>  
                </tr>
            </s:iterator>
        </tbody>
    </table>
</div>
<script type="text/javascript">
    $(function () {
        $('.close-link').click(function () {
            $('.popup-wrap').removeClass('popup-open');
            $('#overlay').hide();
        });
        $('.close-btn').click(function () {
            $('.popup-wrap').removeClass('popup-open');
            $('#overlay').hide();
        });
    });
</script>


