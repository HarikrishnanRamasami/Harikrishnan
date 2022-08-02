<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="lightyellow"><center>
<hr>
<h1>Shoes Shop</h1></center>
<hr>
<form action="purchase.do" method="post">
<input type="hidden" name="action" value="purchase">
<input type="hidden" name="nextshop" value="Watches">
<b>
<input type="radio" name="Slip-Ons" value="1,000">Slip-Ons<br>
<input type="radio" name="Monk" value="1,200">Monk<br>
<input type="radio" name="Loafers" value="2,000">Loafers<br>
</b>
<hr>
<input type="submit" value="Nextshop">
</form>

</body>
</html>