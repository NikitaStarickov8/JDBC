<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add User</title>
</head>
<body>
<c:if test="${addUserSuccessful}">
    <div>Successfully added User with Name: ${savedUser.name}</div>
</c:if>

<form:form action="/user/addUser" method="post" modelAttribute="user">
    <form:label path="name">Name: </form:label> <form:input type="text" path="name"/>
    <form:label path="age">Age: </form:label> <form:input type="text" path="age"/>
    <form:label path="email">Email: </form:label> <form:input path="email"/>
    <input type="submit" value="submit"/>
</form:form>
</body>
</html>