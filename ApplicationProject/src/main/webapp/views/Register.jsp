<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form:form action="controller/regidter" method="POST" modelAttribute="user">

Id :<form:input path="id"/>

Name :<form:input path="name"/>
<form:errors path="name"></form:errors>

Password :<form:input path="password"/>


ConformPassword :<form:input path="conformpassword"/>

Address :<form:input path="address"/>

City :<form:input path="city"/>

Email :<form:input path="email"/>

PhoneNo :<form:input path="phoneno"/>

Image :<form:input type="file" path="image"/>

<input type="submit" value="Register">
</form:form>