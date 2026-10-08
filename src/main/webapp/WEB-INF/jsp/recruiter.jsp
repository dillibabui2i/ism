<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="header.jsp" %>  
    <form action="schedulesByStatus" method="get">
        <select name="status">
            <c:forEach var="status" items="${scheduleStatus}">
            	<option value="${status}">${status}</option>
            </c:forEach>
        </select>
        <input class="save" type="submit" value="view schedules"/>
    </form><br>
    <form action="addCandidate" method="post">
        <input class="save" type="submit" value="create candidate"/>
    </form><br>
    <form action="candidatesByStatus" method="post">
        <select name="status">
            <c:forEach var="status" items="${candidateStatus}">
            	<option value="${status}">${status}</option>
            </c:forEach>
        </select>
        <input class="save" type="submit" value="view candidates"/>
    </form>
</body>
</html>