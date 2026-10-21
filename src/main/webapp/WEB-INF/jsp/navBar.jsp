<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="/css/recruiterMenu.css">
<div class="navbar">
    <a href="${pageContext.request.contextPath}/viewCandidates">Recruiter</a>
    <a href="${pageContext.request.contextPath}/viewSchedules">Manager</a>
    <a href="${pageContext.request.contextPath}/newSchedules?id=1">Employee</a>
    <a href="${pageContext.request.contextPath}/addCandidate">Create Candidate</a>
    <a href="${pageContext.request.contextPath}/viewCandidates">View Candidates</a>
    <a href="${pageContext.request.contextPath}/viewSchedules">View Schedules</a>
    <a href="${pageContext.request.contextPath}/uploadResumeForm">Upload Resume</a>
    <% if (request.isUserInRole("USER")) { %>
    <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
    <form class="logout-form" method="post" action="${pageContext.request.contextPath}/user/logout">
        <button type="submit">Logout</button>
    </form>
    <% } %>
</div>
