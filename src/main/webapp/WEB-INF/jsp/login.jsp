<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ include file="/WEB-INF/jsp/includes.jsp"%>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   
<title>test</title>
		 <link href="<c:url value="/resources/css/back1.css" />" rel="stylesheet">

		 <link href="<c:url value="/resources/css/component.css" />" rel="stylesheet">
		 <link href="<c:url value="/resources/css/button.css" />" rel="stylesheet">
         <script src="<c:url value="/resources/js/modernizr.custom.js" />"></script>
<style>
body {
padding-top:12%;padding-bottom: 2%;
    background-color: whitesmoke;
}
</style>
</head>
<body>


<div class="user-id-div">
<span>
<h1>LOGIN!</h1>

<form:form method="POST" action="login.do" commandName="admin1" >
<table>
		<tr>
			<td>Username</td>
			<td><form:input path="username" /></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><form:input type="password" path="password" name="password" /></td>
		</tr>
		
		<tr>
			<td colspan="2">
				
				<input id="DemoGradient" type="submit" name="action" value="Search" />
			</td>
		</tr>
	</table>
</form:form>


<p>${loginFailed}</p>
</span>
</div>
</body>
</html>