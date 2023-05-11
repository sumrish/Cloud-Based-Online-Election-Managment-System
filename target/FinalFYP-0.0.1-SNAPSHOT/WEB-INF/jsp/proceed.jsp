<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ include file="/WEB-INF/jsp/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evoting</title>
		 <link href="<c:url value="/resources/css/back1.css" />" rel="stylesheet">
		 <link href="<c:url value="/resources/css/BoldButton.css" />" rel="stylesheet">

		 <link href="<c:url value="/resources/css/component.css" />" rel="stylesheet">
		 <link href="<c:url value="/resources/css/button.css" />" rel="stylesheet">
         <script src="<c:url value="/resources/js/modernizr.custom.js" />"></script>
<style>


body {
padding-top:7%;padding-bottom: 2%;
    background-color: whitesmoke;
}
</style>

</head>

<body>
<div class="user-id-div">
<span>
<h1> Please cast your vote for both national level and provisional level.</h1>
<form:form action="voting.do" method="POST" commandName="voter">
	<table>
		
		<tr>
			<td colspan="2">
			<br>
			<span class="wrapper">
				<input  id="DemoGrad" type="submit" name="action" value="National" />
			</span>
			<br>
			<p>
			<span>
				<input id="DemoGrad" type="submit" name="action" value="Provisional" />
			</span>
				<p>${done}</p>
				
				<p>${notcomplete}</p> 
		
			</td>
		</tr>
	</table>
</form:form>
<br>
<form:form action="votecast.do" method="POST">
<input id="DemoGradient" type="submit" name="action" value="Submit">
</form:form> 
</span>
</div>
</body>
</html>