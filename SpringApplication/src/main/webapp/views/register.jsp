<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form action="/mcr/reg" method="post" modelAttribute="userobj">
		
		UserName:<form:input type="text" path="username" /><br>
		<form:errors color="red" path="username"></form:errors><br>
		Password:<input type="password" name="password1"/><br>	
		Confirm Password:<input type="password" name="password2"/><br>	
		Email:<form:input type="email" path="email"/><br>
		City:<form:input type="text" path="city"/><br>
		Address:<form:input type="text" path="address"/><br>
		Phone:<form:input path="phone"/><br>		
		Image:<form:input type="file" path="img"/><br>	
	
		<right><input type="Submit" value="click"><br></right>
</form:form>