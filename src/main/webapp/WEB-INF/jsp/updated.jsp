
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <html>
        <head>
        <title>View Users</title>
    <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Age</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${user}" var="user">
        <tr>
            <td>${user.name}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>

