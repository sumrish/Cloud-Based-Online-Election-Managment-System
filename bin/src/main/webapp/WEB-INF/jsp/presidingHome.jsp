<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evoting</title>
</head>
<body>
<c:choose>
    <c:when test="${adminusername=='no' || homePage=='returning' || homePage=='electcommission'}">
        You have to login to view this page.
        <a href="http://localhost:8080/FinalFYP/login">Login</a>
    </c:when>
    <c:otherwise>
<h1>Presiding Home Page</h1>

<a href="http://localhost:8080/FinalFYP/logout">Logout</a><br><br>

</c:otherwise>
</c:choose>
</body>
</html>