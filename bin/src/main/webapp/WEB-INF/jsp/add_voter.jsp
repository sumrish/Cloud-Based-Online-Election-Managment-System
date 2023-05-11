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
    <c:when test="${adminusername=='no'}">
        You have to login to view this page.
        <a href="http://localhost:8080/FinalFYP/login">Login</a>
    </c:when>
    <c:otherwise>
    <form:form action="add_voter.do" method="POST" commandName="voter" enctype="multipart/form-data">

<table>
		<tr>
			<td>CNIC</td>
			<td><form:input path="cnic" /></td>
		</tr>
		<tr>
			<td>First Name</td>
			<td><form:input  path="firstname" /></td>
		</tr>
		<tr>
			<td>Last Name</td>
			<td><form:input  path="lastname" /></td>
		</tr>
		<tr>
			<td>Gender</td>
			<td>
			<form:radiobutton path="gender" value="Male"/>Male 
			<form:radiobutton path="gender" value="Female"/>Female</td>
		</tr>
		<tr>
			<td>City</td>
			<td><form:input  path="city" /></td>
		</tr>
		<tr>
			<td>Address</td>
			<td><form:input  path="address" /></td>
		</tr>
		<tr>
			<td>Vote number</td>
			<td><form:input  path="voteno" /></td>
		</tr>
		<tr>
			<td>Constituency </td>
			<td><form:select path="cid" items="${constList1}" /></td>
		</tr>
		<tr>
			<td>Polling Station </td>
			<td><form:select path="polling" items="${polling}" /></td>
		</tr>
		
		
		
		
		<tr>
			<td>Image</td>
			<td><form:input type="file" path="pic" name="pic" /></td>
		</tr>
		<tr>
			<td colspan="2">
				
				<input type="submit" name="action" value="Submit" />
			</td>
		</tr>
	</table>

</form:form>
    </c:otherwise>
</c:choose>
<h1>${vt}</h1>
</body>
</html>