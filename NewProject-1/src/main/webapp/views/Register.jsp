<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<form:form action="/mycontrol/register" method="POST" modelAttribute="user">

UserId :<form:input path="userid"/><br>

UserName :<form:input path="username"/><br>

UserCity :<form:input path="city"/><br>

UserAddress<form:input path="address"/><br>

Password :<form:input path="password"/><br>

ReEnterPassword :<form:input path="password2"/><br>

Email :<form:input path="email"/><br>

phone :<form:input path="phone"/><br>

UserFlag :<form:input path="flag"/><br>

UserImage :<form:input type="file" path="image"/><br>

<input type="submit" value="Register"/>

</form:form>