<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="lite blue"><center>
<hr>
<h1>Watches Shop</h1></center>
<hr>

<form action="purchase.do" method="post">
<input type="hidden" name="action" value="purchase">
<input type="hidden" name="nextshop" value="Cricketbag">
<input type="radio" name="Rolex" value="1,221,160">Rolex<br>
<input type="radio" name="Armin Strom<" value="70,500">Armin Strom<br>
<input type="radio" name="Seiko" value="50,0000">Seiko<br>


<hr>
<input type="submit" value="submit">
</form>

</body>
</html>