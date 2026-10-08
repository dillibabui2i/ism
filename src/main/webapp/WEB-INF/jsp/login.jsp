<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Login</title>
</head>
<body>
    <div>
        <h2 align="center">Admin Login</h2>
    </div>
    <c:if test="${param.error != null}">
        <p style="color:red;">Invalid username or password.</p>
    </c:if>
    <form name="form" action="login" method="post">
        <table>
            <tr>
                <td>Username:</td>
                <td><input type="text" name="username" required="required"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" required="required"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Login"/></td>
            </tr>
        </table>
    </form>
</body>
</html>
