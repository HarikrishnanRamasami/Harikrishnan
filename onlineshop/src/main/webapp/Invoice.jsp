
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Invoice</title>
</head>
<body bgcolor="aqua">
<%
Enumeration em=session.getAttributeNames();
while(em.hasMoreElements()){
	String a=em.nextElement().toString();
	String b=session.getAttribute(a).toString();
	out.println("<h1>"+a+":"+b+"<h1>");
}
%>
</body>
</html>