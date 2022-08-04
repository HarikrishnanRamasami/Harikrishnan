<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>window.alert("Please Enter Valid Deatails")</script>
<style>
body{
background-color: #ff8087;
background-image: linear-gradient(45deg, #ff8587 0%, #9d7dff 100%);

}
.sub{
 background-color: green;
}
</style>
<center><h1>Welcome to Register Page</h1>
<form:form action="/con/forms" method="POST" modelAttribute="user">
	<label>UserName</label><br><form:input type="text" path="name"/><br>
	<form:errors style="color:red;" path="name"></form:errors><br>
	<label>PassWord</label><br><form:input type="text" path="pass"/><br>
	<label>Re-Enter PassWord</label><br><input type="password" name="pass1" required="required"/><br>
	<form:errors style="color:red;" path="pass"></form:errors><br>
	<label>Age</label><br><form:input type="number" path="age"/><br>
	<form:errors style="color:red;" path="age"></form:errors><br>
	<label>Phone</label><br><form:input path="mno"/><br>
	<form:errors style="color:red;" path="mno"></form:errors><br>
	<label>Address</label><br><form:input type="text" path="address"/><br>
	<form:errors style="color:red;" path="address"></form:errors><br>
	<label>City</label><br><form:input type="text" path="city"/><br>
	<form:errors style="color:red;" path="city"></form:errors><br>
	<label>Image</label><br><form:input type="file" path="img"/><br>
	<form:errors style="color:red;" path="img"></form:errors><br>
	<input class="sub" type="submit" value="click...">
</form:form></center>