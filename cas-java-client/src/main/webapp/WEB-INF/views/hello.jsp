<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>HelloWorld page</title>
</head>
<body>
	Greeting : ${message}
	This is a welcome page.
	
	

	<a href="<spring:eval expression="@applicationProperties.getProperty('cas.client.url')"/>/j_spring_cas_security_logout">退出</a>
	
</body>
</html>