<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ include file="/WEB-INF/jsp/includes.jsp"%>
       <%@ include file="/WEB-INF/jsp/headerfile.jsp"%>
       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>E-Voting</title>
         <link href="<c:url value="/resources/css/back2.css" />" rel="stylesheet">
		 <link href="<c:url value="/resources/css/component.css" />" rel="stylesheet">
         <script src="<c:url value="/resources/js/modernizr.custom.js" />"></script>

<!--  <script src="<c:url value="/resources/js/check.js" />"></script>-->

</head>
<body>

<pre>

<h2 class="font-style">
  The website is capable of providing the users a means of getting themselves registered. 
  Once they get registered they are eligible of casting a vote. 
  The users who wish to cast a vote enter his CNIC.
  If the user has not already casted a vote, he is not allowed to go further. 
  The system checks from the database if the flag has been set. 
  The flag is stored in an encrypted form. 
  If the user has not casted a vote, he is asked to verify himself via a fingerprint. 
  This verification is to ensure that no one castes a vote on the behalf of some other person. 
  Once verified, the user is asked to vote for both the National and Provisional Assembly. 
  The user may now cast a vote after which he may done with his job. 
  His vote is saved in the database.
  </h2>
</pre>

</body>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>

</html>