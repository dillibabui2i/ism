<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload Resume</title>
<link rel="stylesheet" type="text/css" href="/css/uploadResume.css">
</head>
<body>
<%@ include file="header.jsp" %>
<%@ include file="recruiterMenu.jsp" %>
  <table class="table">
    <tr><td colspan="2">Upload Resume</td></tr>
    <tr>
      <td>Candidate Id:</td>
      <td><input id="candidateId" type="number" required="required"/></td>
    </tr>
    <tr>
      <td>Resume:</td>
      <td><input id="resume" name="resume" type="file" required="required"/></td>
    </tr>
    <tr>
      <td colspan="2">
        <input id="submit" type="button" value="Submit" onclick="uploadResume()"/>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <span id="successMessage"></span>
        <span id="errorMessage"></span>
      </td>
    </tr>
  </table>
  <script type="text/javascript">
    function uploadResume() {
        var candidateId = document.getElementById('candidateId').value;
        var resume = document.getElementById('resume').files[0];
        var successMessage = document.getElementById('successMessage');
        var errorMessage = document.getElementById('errorMessage');
        successMessage.style.display = 'none';
        errorMessage.style.display = 'none';
        successMessage.innerHTML = '';
        errorMessage.innerHTML = '';
        if (!candidateId || !resume) {
            return false;
        }
        var formData = new FormData();
        formData.append('candidateId', candidateId);
        formData.append('resume', resume);
        var httpRequest = new XMLHttpRequest();
        httpRequest.open('POST', 'uploadResume');
        httpRequest.responseType = 'json';
        httpRequest.onreadystatechange = function() {
            if (httpRequest.readyState === XMLHttpRequest.DONE) {
                var body = httpRequest.response;
                if (httpRequest.status === 200) {
                    successMessage.innerHTML = 'Resume uploaded successfully.';
                    successMessage.style.display = 'inline';
                } else {
                    errorMessage.innerHTML = body && body.message ? body.message : 'Failed to upload resume.';
                    errorMessage.style.display = 'inline';
                }
            }
        };
        httpRequest.send(formData);
    }
  </script>
</body>
</html>
