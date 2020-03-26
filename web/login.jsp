<%--
  Created by IntelliJ IDEA.
  User: kenazee
  Date: 3/19/20
  Time: 8:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Welcome, please login</h1>

    <form action="process" method="post">
        login: <input type="text" name="login_name" width="30"/>
        password: <input type="password" name="password" width="10"/>
        <input type="submit" value="Login"/>
    </form>

    <p style="color:red;">${errorMessage}</p>

</body>
</html>
