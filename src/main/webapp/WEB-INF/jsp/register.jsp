<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
    <div>
        <h2 align="center">Register</h2>
    </div>
    <c:choose>
        <c:when test="${registrationSuccess == true}">
            <p>Registration successful.</p>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty error}">
                <p style="color:red;">${error}</p>
            </c:if>
            <form name="form" action="register" method="post">
                <table>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="username" value="${user.username}" required="required"/></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="password" required="required"/></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" value="Register"/></td>
                    </tr>
                </table>
            </form>
        </c:otherwise>
    </c:choose>
</body>
</html>
