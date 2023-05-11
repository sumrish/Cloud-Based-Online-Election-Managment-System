<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="/WEB-INF/jsp/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evoting</title>
</head>
<body>
<h1>Provisional</h1>
<form:form action="provisional.do" method="POST" commandName="candidate">
<table border="1">
	<th>Select</th>
	<th>ccnic</th>
	<th>Candidate Name</th>
	<th>Party Name</th>
	<th>Symbol</th>
	<th>cid</th>
	<c:forEach items="${CandList1}" var="cand">
		<tr>
			<td><form:radiobutton path="ccnic" value="${cand.ccnic}"/></td>
			<td>${cand.ccnic}</td>
			<td>${cand.candidatename}</td>
			<td>${cand.partyname}</td>
			<td>${cand.symbol}</td>
			<td>${cand.cid}</td>
			
		</tr>
	</c:forEach>
</table>
<input type="submit" name="action" value="Submit">
</form:form>
</body>
</html>