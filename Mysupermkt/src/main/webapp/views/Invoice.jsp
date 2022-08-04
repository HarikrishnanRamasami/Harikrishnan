
<!DOCTYPE html>
<%@page import="java.util.Enumeration"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Invoice</title>
<style type="text/css">
body {
	background-image:
		url("https://th.bing.com/th/id/OIP.n_n6YYKF91mBsb8s0s6tfAHaEo?pid=ImgDet&rs=1");
	color: black;
	text-align: center;
	background-color: #D9AFD9;
	background-image: linear-gradient(0deg, #D9AFD9 0%, #97D9E1 100%);
	text-align: center;
}

a {
	text-decoration: none;
}

table {
	text-align: center;
	align-content: center;
}
</style>
</head>
<body>
	<h1>Invoice</h1>
	<center>
		<table border="1">
			<thead>
				<tr>
					<th>Name</th>
					<th>Value</th>
				</tr>
			</thead>
			<%
			long c = 0;
			Enumeration<String> em = session.getAttributeNames();
			while (em.hasMoreElements()) {
				String a = em.nextElement().toString();
				String b = session.getAttribute(a).toString();
				if(a.equals("shop") || a.equals("name") || a.equals("pass")){}
				 else {
					c = c + Long.parseLong(b);
			%>
			<tbody>
				<tr>
					<td><%=a%></td>
					<td><%=b%></td>
				</tr>
			</tbody>
			<%
			}
			}
			%>
			<tfoot>
				<tr>
					<td>Total</td>
					<td><%=c%></td>
				</tr>
			</tfoot>
		</table>
	</center>
	<a href="../index.html">Home</a>
</body>
</html>