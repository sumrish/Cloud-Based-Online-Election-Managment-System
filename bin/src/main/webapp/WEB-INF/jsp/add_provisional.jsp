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
<h1>Add New Provisional Candidate</h1>
<h3>Please ensure that you write correct cnic. Cnic can't be changed later.</h3>

<form:form action="add_provisional.do" method="POST" commandName="candidate" enctype="multipart/form-data">

<table>
		<tr>
			<td>CNIC</td>
			<td><form:input path="ccnic" /></td>
		</tr>
		<tr>
			<td>Candidate Name</td>
			<td><form:input  path="candidatename" /></td>
		</tr>
		<tr>
			<td>Party Name</td>
			<td><form:input  path="partyname" /></td>
		</tr>
		<tr>
			<td>Symbol</td>
			<td><form:input  path="symbol" /></td>
		</tr>
		<tr>
			<td>Constituency</td>
			<td><form:select path="cid" items="${constList1}" /></td>
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
<h3>${added}</h3>
</body>
</html>