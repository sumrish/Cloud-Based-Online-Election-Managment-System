<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ include file="/WEB-INF/jsp/includes.jsp"%>
      <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evoting</title>
</head>
<body>
<c:choose>
    <c:when test="${adminusername=='no' || homePage=='presiding' homePage=='electcommission'}">
        You have to login to view this page.
        <a href="http://localhost:8080/FinalFYP/login">Login</a>
    </c:when>
    <c:otherwise>
<h1>Returning Home Page</h1>

<a href="http://localhost:8080/FinalFYP/add_national">Add National Candidate</a><br>
<a href="http://localhost:8080/FinalFYP/add_provisional">Add Provisional Candidate</a><br><br>
<a href="http://localhost:8080/FinalFYP/update_national">Update National Candidate</a><br>
<a href="http://localhost:8080/FinalFYP/update_provisional">Update Provisional Candidate</a><br><br>
<a href="http://localhost:8080/FinalFYP/search_national">Search From National Candidates</a><br>
<a href="http://localhost:8080/FinalFYP/search_provisional">Search From Provisional Candidates</a><br><br>
<a href="http://localhost:8080/FinalFYP/delete_national">Delete from National Candidates</a><br>
<a href="http://localhost:8080/FinalFYP/delete_provisional">Delete from Provisional Candidates</a><br><br>
<a href="http://localhost:8080/FinalFYP/logout">Logout</a><br><br>


   </c:otherwise>
</c:choose>
</body>
</html>