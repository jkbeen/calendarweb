<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	2007135058 Junkil Been Assignment #4  
</h1>



<br>  calendarUsers is </br> 
<div>
<table border="1">
<tr>
<th>ID</th><th>Email</th><th>Password</th><th>Name</th>
</tr>
<c:forEach var="user" items="${UserList}">
<tr>
<td>${user.id}</td><td>${user.email}</td><td>${user.password}</td><td>${user.name}</td>
</tr> 
</c:forEach>
</table> 
</div>


<br>  events is </br>
<div>
<table border="1">
<tr>
<th>ID</th><th>Summary</th><th>Description</th>
</tr>
<c:forEach var="event" items="${EventList}">
<tr>
<td>${event.id}</td><td>${event.summary}</td><td>${event.description}</td>
</tr> 
</c:forEach>
</table> 
</div>

<br>  eventAttentees is</br>
<div>
<table border="1">
<tr>
<th>ID</th><th>Attendee</th>
</tr>
<c:forEach var="eventAttendee" items="${EventAttendeeList}">
<tr>
<td>${eventAttendee.id}</td><td>${eventAttendee.attendee}</td>
</tr> 
</c:forEach>
</table> 
</div>

</body>
</html>
