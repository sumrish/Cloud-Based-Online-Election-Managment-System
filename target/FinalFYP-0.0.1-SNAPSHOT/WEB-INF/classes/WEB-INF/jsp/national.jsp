<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ include file="/WEB-INF/jsp/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evoting</title>
		 <link href="<c:url value="/resources/css/back1.css" />" rel="stylesheet">
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

<span>

<div class="cand-id-div"> 
<h1>National Candidates</h1>
<form:form action="national.do" method="POST" commandName="candidate">

	<c:forEach items="${CandList}" var="cand">
		<div class="cand-div"> 
			<form:radiobutton path="ccnic" value="${cand.ccnic}"/>
			<img src="/FinalFYP/myImage/NationalSymbolDisplay?ccnic=${cand.ccnic}" style="width:150px;height:100px;"/>
			<p>
			${cand.candidatename}
		</div>
		<p>
		<br>
	</c:forEach>

<input id="DemoGradient" type="submit" name="action" value="Submit">

</form:form>
</div>
</body>
</html>