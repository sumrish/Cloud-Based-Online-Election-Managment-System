<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="/WEB-INF/jsp/includes.jsp"%>
      <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>E-Voting</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   
<title>test</title>
		 <link href="<c:url value="/resources/css/back1.css" />" rel="stylesheet">

		 <link href="<c:url value="/resources/css/component.css" />" rel="stylesheet">
		 <link href="<c:url value="/resources/css/button.css" />" rel="stylesheet">
         <script src="<c:url value="/resources/js/modernizr.custom.js" />"></script>
<style>
body {
padding-top:2%;padding-bottom: 2%;
    background-color: whitesmoke;
}
</style>
</head>
<body>
<div class="user-id-div">
<span>
	<h1>Place your finger on device and get yourself verified.</h1>
	<img src="http://ecx.images-amazon.com/images/I/31nEHEcB2VL.jpg"  style="width:304px;height:200px;">
	<br>
	</br>
	<form:form action="verify_voter.do" method="POST" commandName="voter" >
	<input id="DemoGradient" type="submit" name="action" value="Submit" />
	</form:form>
	
<h3>${failed}</h3>
</span>
</div>
</body>
</html>