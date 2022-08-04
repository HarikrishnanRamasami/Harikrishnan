<!DOCTYPE html>
<%@page import="com.example.demo.DbCon"%>
<%@page import="java.sql.ResultSet"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>IceCreame Shop</title>
</head>
<body bgcolor="light yellow">
<h1>Shoe Shop</h1>
<%! ResultSet rs ;
    DbCon db ;
    public void jspInit() {
        db = new DbCon();
    }
%>
<form action="/shopping/shop" method="post">
<span><input type="hidden" name="shop" value="Invoice">
<% rs=db.checkTable("shoe"); 
while(rs.next()){ %>
<input type="checkbox" name=<%=rs.getString(1) %> value=<%=rs.getString(2) %> >
	<%=rs.getString(1) %>
	<img alt="Image not found......" src=/shopping/imgtake?shopname=shoe&name=<%=rs.getString(1) %> width="100px" height="100px">
	 <% } %></span>
    <input class="sub" type="submit" value="Next">
</form>

</body>
</html>