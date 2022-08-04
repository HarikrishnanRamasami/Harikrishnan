<%@page import="java.sql.ResultSet"%>
<%@page import="com.example.demo.DbCon"%>
<%@page import="java.io.OutputStream"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Flight Shop</title>
</head>
<body bgcolor="light blue">
<h1>Mobile Shop</h1>
<%! ResultSet rs ;
    DbCon db ;
    public void jspInit() {
        db = new DbCon();
    }
%>
<form action="/shopping/shop" method="post">
<input type="hidden" name="shop" value="shoe">
<% rs=db.checkTable("mobile"); 
while(rs.next()){ %>
<span><input type="checkbox" name=<%=rs.getString(1) %> value=<%=rs.getString(2) %> >
	<%=rs.getString(1) %>
	<img alt="Image not found......" src=/shopping/imgtake?shopname=mobile&name=<%=rs.getString(1) %> width="100px" height="100px">
	 <% } %></span>
    <input class="sub" type="submit" value="Next">
</form>
</body>
</html>