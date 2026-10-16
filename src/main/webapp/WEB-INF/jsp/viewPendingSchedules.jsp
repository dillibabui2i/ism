<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
 <link rel="stylesheet" type="text/css" href="/css/viewPendingSchedules.css">
   <link rel="stylesheet" type="text/css" href="/css/popUp.css">
   <link rel="stylesheet" type="text/css" href="/css/recruiterMenu.css">
</head>
<body>
<%@ include file="header.jsp" %>
<%@ include file="navBar.jsp" %>   
        <div class="navbar">
            <a href="newSchedules?id=${employee.id}">New Schedules</a>
            <a href="pendingSchedules?id=${employee.id}">Pending Schedules</a>
        </div>
      <form action="searchByName" method="post">
          <table align="center"><tr>
              <td><input type="text" name="name"/></td>
              <td><input type="submit" value=&#128269;></td></tr>
          </table>
      </form>
      
      <div>
        <table id="contentTable" class = "table" align="center" cellpadding = "10">
            <tr>
                <th>Name</th>
                <th>Position</th>
                <th>Department</th>
                <th>Experience</th>
                <th>Status</th>
                <th>Selected</th>
                <th>Reject</th>
            </tr>
            <c:forEach var="schedule" items="${employee.schedules}">
                <tr>
                    <td class = "td"><a href="viewProgress?id=${schedule.candidate.id}">${schedule.candidate.name}</a></td>
                    <td class = "td">${schedule.candidate.position}</td>
                    <td class = "td">${schedule.candidate.department}</td>
                    <td class = "td">${schedule.candidate.experience}</td>    
                    <td class = "td">${schedule.candidate.status}</td>                 
                    <td class = "td" > 
                      <button class = "select" 
                          onclick="getComment('selected','${schedule.id}');">&#10004;
                      </button></td>           
                    <td class = "td">
                      <button class = "reject"
                          onclick="getComment('rejected', '${schedule.id}');">&times;
                      </button></td>
                </tr>
                <div id="pass">
                    <div class="modal-content">
                        <div>Comment</div>
                            <input type="text" id="feedback" name="feedback" /><br>
                            <input type="submit" id="saveComment" value="submit"/>
                        </form>
	                </div>
                </div>
            </c:forEach>
        </table>  
      </div>
    <script type="text/javascript">
    function getComment(result , id) {
    	if (result === "selected") {
            var modal = document.getElementById("pass");
            var feedback = document.getElementById("feedback");
            var submit = document.getElementById("saveComment");
            modal.style.display = "block";
            feedback.style.display = "block"
            submit.style.display = "block"
            submit.onclick = function() {
                modal.style.display = "none";
                submit.style.display = "none";
                var feedback = document.getElementById("feedback").value;
                location.href = "interviewResult?id="+id+"&result=selected&feedback="+feedback;
             }
    	} else if (result === "rejected") {
            var modal = document.getElementById("pass");
            var feedback = document.getElementById("feedback");
            var submit = document.getElementById("saveComment");
            modal.style.display = "block";
            feedback.style.display = "block"; 
            submit.style.display = "block"
            submit.onclick = function() {
                modal.style.display = "none";
                submit.style.display = "none"; 
                var feedback = document.getElementById("feedback").value;
                location.href = "interviewResult?id="+id+"&result=rejected&feedback="+feedback;
            }
        }
        window.onclick = function(event) {
             if (event.target == modal) {
                 modal.style.display = "none";
                 feedback.style.display = "none";
                 submit.style.display = "none";
             }
         }
    }
    </script>
</body>
</html>