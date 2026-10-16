<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link rel="stylesheet" type="text/css" href="/css/createCandidate.css">
<title>View schedule</title>
</head>
<body>

</body>
<%@ include file="header.jsp" %>
<%@ include file="navBar.jsp" %> 
    <div>
        <table class="table" id="scheduleForm">
            <tr>
                <td>Candidate Name:</td>
                <td>${schedule.candidate.name}</td>
            </tr>
            <tr>
                <td>Interview Type:</td>
                <td>${schedule.interviewType}</td>
            </tr>
            <tr>
                <td>Interview Round:</td>
                <td>${schedule.round}</td>
            </tr>
            <tr>
                <td>Date:</td>
                <td>${schedule.date}</td>
            </tr>
            <tr>
                <td>Status:</td>
                <td>${schedule.status}</td>
            </tr>
            <tr>
               	<c:choose>
	                <c:when test="${schedule.interviewer != null}">   
                		<td>Interviewer:</td>            
	                	<td>${schedule.interviewer.name}</td>
	                </c:when>
	                <c:otherwise>               
                		<td>Select Interviewer:</td>            
	                	<td>
					        <table class="assigntable">
					                <caption>Interviewers</caption>
					        <c:forEach var="interviewer" items="${interviewers}">
					              <tr>
					                <form action="assignInterviewer" method="post">
					                <td>
					                	${interviewer.name}
				                	</td>
				                	<td>
					                	<input type="hidden" name="interviewerId" value="${interviewer.id}">
					                	<input type="hidden" name="scheduleId" value="${schedule.id}">
					                	<input type="submit" value="Assign">
					               	</td>
					               	</form>
					            </tr>
					        </c:forEach>
					        </table>
        				</td>
	                </c:otherwise>
                </c:choose> 
            </tr>
	        <c:if test="${schedule.status == 'Declined'}">
	        	<c:forEach var="scheduleRejectionTrack" items="${schedule.scheduleRejectionTracks}">
	        	</c:forEach>
	        	<tr>
	        		<td>Declined By:</td>
	        		<td>${scheduleRejectionTrack.employee.name}</td>
	        	</tr>
	        	<tr>
	        		<td>Reason:</td>
	        		<td>${scheduleRejectionTrack.comment}</td>
	        	</tr>
	        </c:if>
	            <tr id="comment" style="display:none">
	    	<form:form action="cancelSchedule" method="post" modelAttribute="schedule">
	                <td>Comment:</td>
	                <td><form:input path="cancellationComment" required="true"/></td>
	            </tr>
	            <tr>
	                <td>
	                	<form:input type="hidden" path="id" value="${schedule.id}"/>
	                		<c:choose>
				                <c:when test="${schedule.status != 'Rescheduled' && schedule.status != 'Cancelled'
				                && schedule.status != 'Selected' && schedule.status != 'Rejected'}">
					                	<input id="confirm" type="submit" value="Confirm" style="display:none"/>
					                	<input id="reset" type="reset" onclick="getCommentBox(this.value)" value="Cancel" style="display:none">
			                	</c:when>
			                	<c:otherwise>${schedule.cancellationComment}${schedule.rescheduleComment}</c:otherwise>
                			</c:choose>
	               		 </td>
       			</form:form>
	          		  </tr>
	        <c:if test="${schedule.status == 'Selected' || schedule.status == 'Rejected'}">
	        	<tr>
	        		<td>Feedback:</td>
	        		<td>${schedule.interviewFeedback}</td>
	        	</tr>
	        </c:if>
	        <tr><td>
        <input type="button" id="rescheduleButton" onclick="getCommentBox(this.value)" value="Reschedule">
        <input type="button" id="cancelButton" onclick="getCommentBox(this.value)" value="Cancel Schedule">
        	</td></tr>
        </table>
        <table class="table" id="rescheduleForm" style="display:none">
	    	<form:form action="reschedule" method="post" modelAttribute="newSchedule">
            <tr>
                <td>Candidate Name:</td>
                <td>${schedule.candidate.name}</td>
            </tr>
            <tr>
                <td>Interview Type</td>
                <td><form:input value="${schedule.interviewType}" path="interviewType" readonly="true"/></td>
            </tr>
            <tr>
                <td>Interview Level:</td>
                <td><form:input value="${schedule.interviewLevel}" path="interviewLevel" readonly="true"/></td>
            </tr>
            <tr>
                <td>Date:</td>
                <td><input type="date" name="shdate"/></td>
            </tr>
            <tr>
                <td>Time:</td>
                <td ><input type="time" name="shtime"/></td>
            </tr>
            <tr>
                <td>Comment:</td>
                <td><input name="comment" required/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="hidden" name="scheduleId" value="${schedule.id}"/>
                	<input value="${schedule.candidate.id}" name="candidateId" type="hidden"/>
                	<input type="submit" value="Confirm"/>
       				<input type="reset" onclick="getCommentBox(this.value)" value="Cancel">
                	</td>
            </tr>
	        </form:form>
        </table>
    </div>
   </body>
      <script>
         function getCommentBox(value) {
              if(value === "Reschedule") {
                  document.getElementById("rescheduleForm").style.display="block";
                  document.getElementById("scheduleForm").style.display="none";
                  document.getElementById("rescheduleButton").style.display="none";
                  document.getElementById("cancelButton").style.display="none";
                  document.getElementById("comment").style.display="none";
                  
              } else if(value === "Cancel Schedule") {
                  document.getElementById("rescheduleForm").style.display="none";
                  document.getElementById("scheduleForm").style.display="block";
                  document.getElementById("rescheduleButton").style.display="none";
                  document.getElementById("cancelButton").style.display="none";
                  document.getElementById("comment").style.display="block";
                  document.getElementById("confirm").style.display="block";
                  document.getElementById("reset").style.display="block";
                  
              } else if(value === "Cancel") {
                  document.getElementById("rescheduleForm").style.display="none";
                  document.getElementById("scheduleForm").style.display="block";
                  document.getElementById("rescheduleButton").style.display="block";
                  document.getElementById("cancelButton").style.display="block";
                  document.getElementById("comment").style.display="none";
                  document.getElementById("confirm").style.display="none";
                  document.getElementById("reset").style.display="none";
                  
              }
         }
      </script>
</html>