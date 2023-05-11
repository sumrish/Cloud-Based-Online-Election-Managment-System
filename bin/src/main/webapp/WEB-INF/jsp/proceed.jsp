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
<h1> Please cast your vote for both national level and provisional level.</h1>
<form:form action="voting.do" method="POST" commandName="voter">
	<table>
		
		<tr>
			<td colspan="2">
				<input type="submit" name="action" value="National" />
				<input type="submit" name="action" value="Provisional" />
				<p>${done }</p>
				<br>
				<p>${notcomplete}</p> 
		
			</td>
		</tr>
	</table>
</form:form>
<br>
<form:form action="votecast.do" method="POST">
<input type="submit" name="action" value="Submit">
</form:form> 

</body>
</html>