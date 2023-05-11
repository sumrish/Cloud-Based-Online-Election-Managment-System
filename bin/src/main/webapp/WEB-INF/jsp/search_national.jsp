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
<h1>Search from National Candidates</h1>
<form:form action="search_national.do" method="POST" commandName="candidate">
		<form:select path="candidatename" items="${listToSearch}" />
			<br>
		<br>
		<form:input type="text" path="partyname"/>
		

		
		<input type="submit" name="action" value="Search">
</form:form>

<br><br>
<h3>${select}</h3>
<br><br>
<table border="1">

	<th>CNIC</th>
	<th>Name</th>
	<th>Party Name</th>
	<th>Symbol</th>
	<th>Constituency</th>
	<th>Image</th>
	<c:forEach items="${NatSearch}" var="candidate">
		<tr>   
		    <td>${candidate.ccnic}</td>
			<td>${candidate.candidatename}</td>
			<td>${candidate.partyname}</td>
			<td>${candidate.symbol}</td>
			<td>${candidate.cid}</td>
			<td><img src="/FinalFYP/myImage/NationalImageDisplay?ccnic=${candidate.ccnic}"/></td>
			
			
			
		</tr>
	</c:forEach>
	
	
</table>
 </c:otherwise>
</c:choose>
</body>
</html>