<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View schedules</title>
 <link rel="stylesheet" type="text/css" href="/css/viewSchedules.css">
</head>
<body>
<%@ include file="header.jsp" %>
<%@ include file="navBar.jsp" %> 
	<div>
        <table id="contentTable" class = "table" align="center" cellpadding = "10">
            <tr>
                <th>S.No.</th>
                <th>Candidate name</th>
                <th>Round</th>
                <th>Interview Type</th>
                <th>Date</th>
                <th>Time</th>
                <th>Status</th>
                <th>Interviewer</th>
                <th>More</th>
            </tr>
            <c:set var="sNumber" value="1" scope="page"/>
            <c:forEach var="schedule" items="${schedules}">
	                <tr>
	                    <td>${sNumber}</td>
	                    <td>${schedule.candidate.name}</td>
	                    <td>${schedule.round}</td>
	                    <td>${schedule.interviewType}</td>
	                    <td>${schedule.date}</td>         
	                    <td>${schedule.time}</td>
	                    <td>${schedule.status}</td>
                    	<c:choose>
		                    <c:when test="${schedule.interviewer != null}">               
			                    <td>${schedule.interviewer.name}</td>
		                    </c:when>
		                    <c:otherwise>               
			                    <td>Not assigned</td>
		                    </c:otherwise>
	                    </c:choose>               
	                    <td><a href="getScheduleWithInterviewers?scheduleId=${schedule.id}">Click here</a></td>
	                </tr>
                <c:set var="sNumber" value="${sNumber+1}" scope="page"/>
            </c:forEach>
        </table>
      </div>
</body>
</html>