<!DOCTYPE html>
<%@page import="com.example.demo.DbCon"%>
<%@page import="java.sql.ResultSet"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>IceCreame Shop</title>
<style type="text/css">
body{
background-color: #4158D0;
background-image: linear-gradient(43deg, #4158D0 0%, #63c850 46%, #ff70ff 100%);


text-align: center;
color: black;
}
h1,span{
color: black;
}
.sub{
 background-color: green;
 color: white;
}
</style>
</head>
<body bgcolor="orange">
<h1>IceCreame Ibaco</h1>
<%! ResultSet rs ;
    DbCon db ;
    public void jspInit() {
        db = new DbCon();
    }
%>
<form action="/con/shop" method="post">
<span><input type="hidden" name="shop" value="Invoice">
<% rs=db.checkTable("ibaco"); 
while(rs.next()){ %>
<input type="checkbox" name=<%=rs.getString(1) %> value=<%=rs.getString(2) %> >
	<%=rs.getString(1) %>
	<img alt="Image not found......" src=/con/shopimg?shopname=ibaco&name=<%=rs.getString(1) %> width="100px" height="100px">
	 <% } %></span>
    <input class="sub" type="submit" value="Next">
</form>

</body>
</html>