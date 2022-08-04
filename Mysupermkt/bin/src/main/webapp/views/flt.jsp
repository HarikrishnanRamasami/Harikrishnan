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
<style type="text/css">
body{
background-color: #0093E9;
background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%);

text-align: center;
color: black;
}
h1,span{
color: black;
}
.sub{
 background-color: white;
}
</style>
</head>
<body>
<h1>FlightShop</h1>
<%! ResultSet rs ;
    DbCon db ;
    public void jspInit() {
        db = new DbCon();
    }
%>
<form action="/con/shop" method="post">
<input type="hidden" name="shop" value="fruit">
<% rs=db.checkTable("flightshop"); 
while(rs.next()){ %>
<span><input type="checkbox" name=<%=rs.getString(1) %> value=<%=rs.getString(2) %> >
	<%=rs.getString(1) %>
	<img alt="Image not found......" src=/con/shopimg?shopname=flightshop&name=<%=rs.getString(1) %> width="100px" height="100px">
	 <% } %></span>
    <input class="sub" type="submit" value="Next">
</form>
</body>
</html>