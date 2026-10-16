<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <link rel="stylesheet" href="/css/createSchedule.css">
   <body>
<%@ include file="header.jsp" %>
<%@ include file="navBar.jsp" %>  

      <div align="center">
         <form:form id="createScheduleFormId" action="createSchedule" method="post" modelAttribute="schedule">
            <table class="table">
               <form:input type="hidden" path="candidate" value="${schedule.candidate}"/>
               <tr>
                  <td>InterviewLevel</td>
                  <td>${schedule.interviewLevel}</td>
               </tr>
               <tr>
                  <td>InterviewTypes</td>
                  <td>${schedule.interviewType}</td>
               </tr>
              <tr>
                  <td>Date</td>
                  <td>
                  	 <input type="date" name="shdate" />
                  </td>
               </tr>
               <tr>
                  <td>Time</td>
                  <td>
                  	 <input type="time" name="shtime" />
                  </td>
               </tr>
               <tr>
                  <td><input type="reset" value="Clear"></td>
                  <td><input type="submit" value="Submit"></td>
               </tr>
            </table>
         </form:form>
      </div>
   </body>
</html>