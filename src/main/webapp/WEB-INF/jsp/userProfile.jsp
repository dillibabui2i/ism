<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile</title>
</head>
<body>
<%@ include file="navBar.jsp" %>
    <div>
        <h2 align="center">Profile</h2>
    </div>
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>
    <c:if test="${emailUpdated == true}">
        <p>Email updated.</p>
    </c:if>
    <c:if test="${passwordUpdated == true}">
        <p>Password updated.</p>
    </c:if>
    <table>
        <tr>
            <td>Username:</td>
            <td>${user.username}</td>
        </tr>
    </table>
    <form name="updateEmailForm" action="user/profile/email" method="post">
        <table>
            <tr>
                <td>Email:</td>
                <td><input type="text" name="email" value="${user.email}" required="required"
                    pattern="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$"
                    title="Enter a valid email address."/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Update email"/></td>
            </tr>
        </table>
    </form>
    <form name="changePasswordForm" action="user/profile/password" method="post">
        <table>
            <tr>
                <td>Current password:</td>
                <td><input type="password" name="currentPassword" required="required"/></td>
            </tr>
            <tr>
                <td>New password:</td>
                <td><input type="password" name="newPassword" required="required" minlength="8"
                    pattern="^(?=.*[A-Za-z])(?=.*\d).{8,}$"
                    title="At least 8 characters, including at least one letter and one digit."/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Change password"/></td>
            </tr>
        </table>
    </form>
</body>
</html>
