<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="obj" class="bean.Initialize"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PPS Web application</title>
</head>
<body>
out.println("Database Initialized");
session.setAttribute("session","TRUE");
</body>
</html>