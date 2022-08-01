<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form action="/form/register" method="POST" modelAttribute="userObj">

UserName :<form:input path="uname"/>

<form:errors path="uname"></form:errors><br>

Password :<form:input path="upass"/>

Email :<form:input path="email"/>

phone :<form:input path="phone"/>

<input type="submit" value="Enter"/>

</form:form>