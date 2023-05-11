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
<h1>Delete from National Candidate.</h1>
<form:form action="delete_provisional.do" method="POST" commandName="candidate">
<table border="1">
	<th>Select</th>
	<th>CNIC</th>
	<th>Name</th>
	<th>Party Name</th>
	<th>Symbol</th>
	<th>Cid</th>
	<c:forEach items="${ProcandidatesList}" var="candidate">
		<tr>   
		     <td><form:checkbox path="ccnic" value="${candidate.ccnic}"/> </td>
			<td>${candidate.ccnic}</td>
			<td>${candidate.candidatename}</td>
			<td>${candidate.partyname}</td>
			<td>${candidate.symbol}</td>
			<td>${candidate.cid}</td>
			<td><img src="/FinalFYP/myImage/ProvisionalImageDisplay?ccnic=${candidate.ccnic}"/></td>
			
			
			
			
		</tr>
	</c:forEach>
	
	
</table>

<input type="submit" name="action"  value="Submit">
</form:form>
<h3>${done}</h3>
</c:otherwise>
</c:choose>
</body>
</html>