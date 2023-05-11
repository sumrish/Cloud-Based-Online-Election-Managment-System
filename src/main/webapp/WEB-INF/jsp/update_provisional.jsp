<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@ include file="/WEB-INF/jsp/includes.jsp"%>
       <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evoting</title>
 <link href="<c:url value="/resources/css/back1.css" />" rel="stylesheet">
 		 <link href="<c:url value="/resources/css/table.css" />" rel="stylesheet">
 
		 <link href="<c:url value="/resources/css/component.css" />" rel="stylesheet">
		 <link href="<c:url value="/resources/css/button.css" />" rel="stylesheet">
         <script src="<c:url value="/resources/js/modernizr.custom.js" />"></script>
<style>
body {
padding-top:3%;padding-bottom: 2%;
    background-color: whitesmoke;
}
</style>
</head>
<body>
<c:choose>
    <c:when test="${adminusername=='no'}">
        You have to login to view this page.
        <a href="http://localhost:8080/FinalFYP/login">Login</a>
    </c:when>
    <c:otherwise>
 <div class="cand-id-div"> 
    
<h1>Update Provisional Candidate</h1>
<form:form action="update_provisional.do" method="POST" commandName="candidate" enctype="multipart/form-data">
	<pre>
CNIC            <form:input path="ccnic" />
 	
Candidate Name  <form:input  path="candidatename" />

Party Name      <form:input  path="partyname" />
		
     Symbol          <form:input  type="file" path="symbol" name="symbol" />
		
Constituency                   <form:select path="cid" items="${constList}" />
		
     Image           <form:input type="file" path="pic" name="pic" />
		
		
 <input id="DemoGradient" type="submit" name="action" value="Submit" />
			
</pre>

</form:form>

</div>
<table id="t01">

	<th>CNIC</th>
	<th>Name</th>
	<th>Party Name</th>
	<th>Symbol</th>
	<th>Cid</th>
	<th>Image</th>
	<c:forEach items="${ProCandidatesList}" var="cand">
		<tr>   
		    <td>${cand.ccnic}</td>
			<td>${cand.candidatename}</td>
			<td>${cand.partyname}</td>
			<td><img src="/FinalFYP/myImage/ProvisionalSymbolDisplay?ccnic=${cand.ccnic}" style="width:150px;height:100px;"/></td>
			<td>${cand.cid}</td>
			<td><img src="/FinalFYP/myImage/ProvisionalImageDisplay?ccnic=${cand.ccnic}" style="width:150px;height:100px;"/></td>
		
		</tr>
	</c:forEach>
	
	
</table>

<h3>${updated}</h3>
 </c:otherwise>
</c:choose>
</body>
      <%@ include file="/WEB-INF/jsp/back.jsp"%>

</html>