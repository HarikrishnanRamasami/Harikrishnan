<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : unAuthroizedAccess
    Created on : Nov 13, 2017, 10:10:52 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div style="min-height: 500px;" align="center">
    <table align="center" width="60%" style="margin: 100px;">
        <tr>
            <td align="center">
                <h1><s:text name="lbl.common.head.access"/> <font style="color: red;"><s:text name="lbl.common.head.restricted"/></font>!!!</h1>
            </td>
        </tr>
        <tr>
            <td align="center">
                <h3><s:text name="lbl.common.head.authorized.to.access"/></h3>
            </td>
        </tr>
        <tr>	
            <td align="center">                     
                <s:text name="lbl.common.redirected.home.screen"/> <span id="time"><s:text name="lbl.common.five"/></span> <s:text name="lbl.common.seconds"/>.
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    var sec = 5;
    var timerId = setInterval("UpdateTimer()", 1000);
    function UpdateTimer(){
        sec = sec - 1;
        document.getElementById("time").innerHTML = sec;
        if(sec === 0){
            window.clearInterval(timerId); 
        }
    }
    setInterval("PageRedirection()", 5000);
    function PageRedirection(){
        window.location.href = "<%=request.getContextPath() %>/home.do";
    }
</script>
