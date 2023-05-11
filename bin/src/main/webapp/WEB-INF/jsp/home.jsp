<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ include file="/WEB-INF/jsp/includes.jsp"%>
  <%@ include file="/WEB-INF/jsp/headerfile.jsp"%>
  
<!DOCTYPE html >
<html lang="en" class="no-js">
<head>

	
		
		<title>Evoting</title>
		
	
		
		
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

		<div id="about">
This is an E-voting website. Tremors is a neurological condition; in which your hands, head or legs shake unintentionally. Until now; people with such condition found it hard to get an assessment. We at Trequant know how a patient feels about their condition, and our aim is to make them independent. Trequants Tremor Quantifying device not only helps in effective self assessment but also keeps the doctors and families updated with the reports. We have designed a device that draws no attention to it and definitely does not look like a medical aid. Our device looks like a conventional wrist-watch; this protects the patients privacy and saves them from intriguing questions by onlookers. The device has the ability to be synced with modern smart phones/laptops via Bluetooth. A dedicated application works on tracking and analyzing the tremor patterns. The data is then saved on cloud and ready to be shared with doctors and family members. Let's take back control.


</div>


	<div id="help">
We will provide you help regarding vote casting. 
Tremors is a neurological condition; in which your hands, head or legs shake unintentionally. Until now; people with such condition found it hard to get an assessment. We at Trequant know how a patient feels about their condition, and our aim is to make them independent. Trequants Tremor Quantifying device not only helps in effective self assessment but also keeps the doctors and families updated with the reports. We have designed a device that draws no attention to it and definitely does not look like a medical aid. Our device looks like a conventional wrist-watch; this protects the patients privacy and saves them from intriguing questions by onlookers. The device has the ability to be synced with modern smart phones/laptops via Bluetooth. A dedicated application works on tracking and analyzing the tremor patterns. The data is then saved on cloud and ready to be shared with doctors and family members. Let's take back control.Tremors is a neurological condition; in which your hands, head or legs shake unintentionally. Until now; people with such condition found it hard to get an assessment. We at Trequant know how a patient feels about their condition, and our aim is to make them independent. Trequants Tremor Quantifying device not only helps in effective self assessment but also keeps the doctors and families updated with the reports. We have designed a device that draws no attention to it and definitely does not look like a medical aid. Our device looks like a conventional wrist-watch; this protects the patients privacy and saves them from intriguing questions by onlookers. The device has the ability to be synced with modern smart phones/laptops via Bluetooth. A dedicated application works on tracking and analyzing the tremor patterns. The data is then saved on cloud and ready to be shared with doctors and family members. Let's take back control.

</div>
	<div id="contact">
Contact the team Tremors is a neurological condition; in which your hands, head or legs shake unintentionally. Until now; people with such condition found it hard to get an assessment. We at Trequant know how a patient feels about their condition, and our aim is to make them independent. Trequants Tremor Quantifying device not only helps in effective self assessment but also keeps the doctors and families updated with the reports. We have designed a device that draws no attention to it and definitely does not look like a medical aid. Our device looks like a conventional wrist-watch; this protects the patients privacy and saves them from intriguing questions by onlookers. The device has the ability to be synced with modern smart phones/laptops via Bluetooth. A dedicated application works on tracking and analyzing the tremor patterns. The data is then saved on cloud and ready to be shared with doctors and family members. Let's take back control.


</div>
<h1>Verify Yourself!</h1>

<form:form action="processing" method="POST" modelAttribute="voter">
	<table>
		<tr>
			<td>CNIC</td>
			<td><form:input path="cnic" /></td>
		</tr>
		
		<tr>
			<td colspan="2">
				
				<input type="submit" name="action" value="Search" />
			</td>
		</tr>
	</table>
</form:form>
<h1>${error}</h1>

<br> <br>
<a href="http://localhost:8080/FinalFYP/login">Login</a>
</body>
</html>