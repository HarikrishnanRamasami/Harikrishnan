<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : appAutoLogin
    Created on : Mar 21, 2018, 10:16:06 AM
    Author     : ravindar.singh
--%>
<%@page errorPage="/WEB-INF/pages/uam/500.jsp" import="java.util.Map, java.util.HashMap, java.util.Iterator" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="Robots" content="NOINDEX, NOFOLLOW">
        <meta name="Cache-Control" content="max-age=0"></meta>
        <meta name="Cache-Control" content="no-cache"></meta>
        <meta http-equiv="author" content="Ravindar Singh T"/>
        <title></title>
    </head>
    <body>
        <form name="app" method="post" target="_parent" autocomplete="off">
            <%
                HashMap params = (HashMap)request.getAttribute("LOGIN_PARAM");
                if(params == null || params.isEmpty()) throw new Exception("Auto login parameter list is empty");
                Iterator paramsIterator = paramsIterator = params.entrySet().iterator();
                while (paramsIterator.hasNext()) {
                    Map.Entry param = (Map.Entry) paramsIterator.next();
                    out.print("<input type=\"hidden\" id=\"" + param.getKey() + "\" name=\"" + param.getKey() + "\" value=\"" + param.getValue() + "\"/>\n");
                }
            %>
        </form>
        <script>
            document.app.action="${url}";
            document.app.submit();
        </script>
    </body>
</html>
