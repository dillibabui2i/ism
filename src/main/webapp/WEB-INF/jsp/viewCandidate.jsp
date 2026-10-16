<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
<title>View Candidate</title>
 <link rel="stylesheet" type="text/css" href="/css/viewCandidate.css">
  <link rel="stylesheet" type="text/css" href="/css/popUp.css">
</head>
<body onload="currentStatus('${status}');">
<%@ include file="header.jsp" %>  
<%@ include file="navBar.jsp" %>
    <div>
        <table class="table">
            <tr>
                <td>Name:</td>
                <td>${candidate.name}</td>
            </tr>
            <tr>
                <td>Position:</td>
                <td>${candidate.position}</td>
            </tr>
            <tr>
                <td>Department:</td>
                <td>${candidate.department}</td>
            </tr>
            <tr>
                <td>Status:</td>
                <td>${candidate.status}</td>
            </tr>
            <tr>
                <th colspan="4">Interview Details</th>
            </tr>
            <tr> 
                <th>SI.No</th>
                <th>Round</th>
                <th>Type</th>
                <th>Result</th>
                <th>Comment</th>
            </tr>
            <c:set var="siNo" value="1" scope="page"/>
            <c:forEach var="schedule" items="${candidate.schedules}">
                <tr>
                    <td>${siNo}</td>
                    <td>${schedule.round}</td>
                    <td>${schedule.interviewType}</td>
                    <td>${schedule.status}</td>
                    <td>${schedule.rescheduleComment}${schedule.cancellationComment}${schedule.interviewFeedback}</td>
                </tr>
                <c:set var="siNo" value="${siNo + 1}" scope="page"/>
            </c:forEach>
        </table>
    </div>
    <div id="pass">
      <div class="modal-content">
          <div id="created">Created Successfully</div><br>
          <div id="updated">Updated Successfully</div><br>
          <span class="close">&times;</span>
	  </div>
    </div>
    <script type="text/javascript">
    function currentStatus(status) {
        if (status === "created") {
            var modal = document.getElementById("pass");
            var created = document.getElementById("created");
            var span = document.getElementsByClassName("close")[0];
            modal.style.display = "block";
            created.style.display = "block"
            span.onclick = function() {
                modal.style.display = "none";
                created.style.display = "none"; 
             }
        } else if (status === "updated") {
            var modal = document.getElementById("pass");
            var updated = document.getElementById("updated");
            var span = document.getElementsByClassName("close")[0];
            modal.style.display = "block";
            updated.style.display = "block"; 
            span.onclick = function() {
                modal.style.display = "none";
                updated.style.display = "none"; 
            }
        }
    }
    </script>
</body>
</html>