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
		 <link href="<c:url value="/resources/css/component.css" />" rel="stylesheet">
		 <link href="<c:url value="/resources/css/button.css" />" rel="stylesheet">
         <script src="<c:url value="/resources/js/modernizr.custom.js" />"></script>
<style>
body {
padding-top:1%;padding-bottom: 1%;
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
  
  <h1> Voter Data </h1>
    <form:form action="add_voter.do" method="POST" commandName="voter" enctype="multipart/form-data">

<pre>
CNIC            <form:input path="cnic" />
 			
First Name      <form:input  path="firstname" />
		
Last Name       <form:input  path="lastname" />

Gender                    <form:radiobutton path="gender" value="Male"/>Male 
			        <form:radiobutton path="gender" value="Female"/>Female
		
City            <form:input  path="city" />

Address         <form:input  path="address" />

Vote number     <form:input  path="voteno" />

Constituency                  <form:select path="cid" items="${constList1}" />

Polling Station <form:select path="polling" items="${polling}"/>
	
    Image            <form:input type="file" path="pic" name="pic" />

          
          
   <input id="DemoGradient" type="submit" name="action" value="Submit" />
</pre>	
</form:form>
     <%@ include file="/WEB-INF/jsp/back-e.jsp"%>
    </c:otherwise>
</c:choose>
<h1>${vt}</h1>
</body>
</html>
