<%-- 
    Document   : logout
    Created on : 4 Oct, 2017, 1:13:36 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<%
    String url = request.getContextPath() + "/login.do?src=lg";
%>
<html>
    <head>
        <meta http-equiv="refresh" content="0;url=<%=url %>">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:text name="lbl.common.annoud.crm"/></title>
    </head>
</html>