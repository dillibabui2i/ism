<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
 <link rel="stylesheet" type="text/css" href="/css/viewCandidates.css">
</head>
<body>
<%@ include file="header.jsp" %>  
<%@ include file="navBar.jsp" %>
        <div>
          <table align="center">
          <tr>
            <form action="searchByName" method="post">
              <td><input type="text" name="name" required/></td>
              <td><input type="submit" value=&#128269;></td>
            </form>
            <form action="searchByStatus" method="post">
              <td><select name="result">
                    <c:forEach var="result" items="${pagenationInfo.results}" >
                      <option  value="${result}">${result}</option>                  
                    </c:forEach>
                  </select>
              </td>
              <td><input type="submit"  value=&#128269;></td>
            </form>
          </tr>
          </table>
        </div>
      <div>
        <table id="contentTable" class = "table" align="center" cellpadding = "10">
            <tr>
                <th>Name</th>
                <th>Position</th>
                <th>Department</th>
                <th>Experience</th>
                <th>Status</th>
                <th>Schedule</th>
                <th>Update</th>
            </tr>
            <c:forEach var="candidate" items="${pagenationInfo.candidates}">
                <tr>
                    <td class = "td"><a href="viewProgress?id=${candidate.id}">${candidate.name}</a></td>
                    <td class = "td">${candidate.position}</td>
                    <td class = "td">${candidate.department}</td>
                    <td class = "td">${candidate.experience}</td>    
                    <td class = "td">${candidate.status}</td>
                    <c:if test="${candidate.status == 'Cleared'}">                 
                      <td class = "td" > 
                        <button class = "schedule" 
                            onclick="location.href='scheduleForm?candidateId=${candidate.id}';">&#x1F4C5;
                        </button></td>
                    </c:if>  
                    <c:if test="${candidate.status == 'New'}">                 
                      <td class = "td" > 
                        <button class = "schedule" 
                            onclick="location.href='scheduleForm?candidateId=${candidate.id}';">&#x1F4C5;
                        </button></td>
                    </c:if>      
                    <c:if test="${candidate.status != 'Cleared'}">     
                      <c:if test="${candidate.status != 'New'}">                 
                        <td class = "td" > 
                        </td>
                      </c:if>              
                    </c:if>            
                    <td class = "td">
                      <button class = "editButton"
                       onclick="location.href='viewCandidateForUpdate?candidateId=${candidate.id}';">&#x1F58B;</button>      
                    </td>
                </tr>
            </c:forEach>
          </table>  
        </div>
        <div align="center">
          <div class = "cards">
            <button value =1  id = "back" 
               onclick = "pagenation('${pagenationInfo.status}', this.value, '-1', ${pagenationInfo.lastPageNo});"
               class = "btn">&#10096;</button>
          </div>
            <c:forEach var="page" items="${pagenationInfo.pages}">
            <div class = "cards">
             <button class = "btn" onclick = "pagenation('${pagenationInfo.status}', ${page}, 'page', ${pagenationInfo.lastPageNo});">
                 ${page}</button>          
            </div> 
           </c:forEach>
         <div class = "cards">
           <button value =1 id = "next" class = "btn"  
               onclick = "pagenation('${pagenationInfo.status}', this.value, '1', ${pagenationInfo.lastPageNo});">
           &#10097;</button>
        </div>
      </div> 
      <script type="text/javascript">
      function pagenation(status, page, choice, lastpage) {
          httpRequest = new XMLHttpRequest();
          if (!httpRequest) {
             console.log('Unable to create XMLHTTP instance');
             return false;
          }
          var pageno = page;
          document.getElementById('back').value = pageno;
          document.getElementById('next').value = pageno; 
          if (pageno === 1) {
              document.getElementById('back').style.display = 'none';
          } else {
              document.getElementById('back').style.display = '';
          }
          if (pageno === lastpage) {
              document.getElementById('next').style.display = 'none';
          } else {
              document.getElementById('next').style.display = '';
          }
          if (choice === '1' && pageno < lastpage) {
              pageno = page * 1 + 1;
              document.getElementById('next').value = pageno;
          } else if (choice === '-1' && pageno > 1) {
              pageno = page - 1;
              document.getElementById('back').value = pageno;
          }
          //var status = document.getElementById('result').value;
          httpRequest.open('GET', 'viewAllCandidates?pageNo='+pageno+"&result="+status);
          httpRequest.responseType = 'json';
          httpRequest.send();
          httpRequest.onreadystatechange = function() {
          if (httpRequest.readyState === XMLHttpRequest.DONE) {
              if (httpRequest.status === 200) {
                  var j =0;
                  var array = httpRequest.response;                     
                  for (var i=1; i<= array.length; i++) {
                      var row = document.getElementById('contentTable').rows; 
                      var column = row[i].cells;
                      var name = array[j].candidateName;
                      var candidateId = array[j].candidateId;
                      var view = name.link("viewProgress?id=" + candidateId);
                      column[0].innerHTML = view;
                      column[1].innerHTML = array[j].position;
                      column[2].innerHTML = array[j].department;
                      column[3].innerHTML = array[j].experience; 
                      column[4].innerHTML = array[j].status
                      column[5].style.display =''; 
                      column[6].style.display =''; 
                      column[5].innerHTML =''; 
                      if(('New' == array[j].status) || ('Cleared' == array[j].status)) { 
                          var scheduleBtn = document.createElement("BUTTON");
                          scheduleBtn.id ='dbtn';
                          scheduleBtn.innerHTML = "&#x1F4C5";
                          scheduleBtn.setAttribute("class", "schedule");
                          scheduleBtn.setAttribute("onclick", "scheduleCandidate("+candidateId+");");
                          column[5].appendChild(scheduleBtn);
                      }
                      column[6].innerHTML =''; 
                      var editBtn = document.createElement("BUTTON");
                      editBtn.id ='ebtn';
                      editBtn.innerHTML = "&#x1F58B;";
                      editBtn.setAttribute("class", "editButton");
                      editBtn.setAttribute("onclick", "onEdit("+candidateId+");");
                      column[6].appendChild(editBtn); 
                      j = j + 1;
                   }
                   for (var i=array.length+1 ; i<=5; i++) {
                      var row = document.getElementById('contentTable').rows; 
                      var column = row[i].cells;
                      column[0].innerHTML = "";
                      column[1].innerHTML = "";
                      column[2].innerHTML = "";
                      column[3].innerHTML = "";
                      column[4].innerHTML = "";
                      column[5].style.display ='none'; 
                      column[6].style.display ='none'; 
                   }   
              } else {
                   console.log('Something went wrong..!!');
              }
           }
       }
  }
  function scheduleCandidate(id) {
	  location.href="scheduleForm?candidateId="+id;
  }
  function onEdit(id) {
	  location.href="viewCandidateForUpdate?candidateId="+id;
  }
  </script>
</body>
</html>