<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body><center>
<h1>Mobile Shop</h1>
<form action="purchase.do" method="post">
<input type="hidden" name="action" value="purchase">
<input type="hidden" name="nextshop" value="Shoes">
<input type="radio" name="iphone-12" value="1,30,000">iphone-12
<input type="radio" name="vivo" value="15,000">vivo
<input type="radio" name="realme" value="18,000">realme
<input type="submit" value="Nextshop">
</form>
</center>
</body>
</html>