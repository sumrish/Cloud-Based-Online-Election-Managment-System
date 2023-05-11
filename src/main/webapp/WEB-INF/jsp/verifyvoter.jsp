<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ include file="/WEB-INF/jsp/includes.jsp"%>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   
<title>test</title>
		 <link href="<c:url value="/resources/css/back.css" />" rel="stylesheet">
		 <link href="<c:url value="/resources/css/component.css" />" rel="stylesheet">
		 <link href="<c:url value="/resources/css/button.css" />" rel="stylesheet">
         <script src="<c:url value="/resources/js/modernizr.custom.js" />"></script>


</head>
<body>


<div class="user-id-div">
<span>
<form:form action="processing" method="POST" modelAttribute="voter">
	<table>
		<tr>
			<td>CNIC</td>
			<td><form:input path="cnic" /></td>
		</tr>
		
		<tr>
			<td colspan="2">
				
				<input id="DemoGradient" type="submit" name="action" value="Search" />
			</td>
		</tr>
	</table>
</form:form>
</span>
</div>
<h1>${error}</h1>

</body>
</html>