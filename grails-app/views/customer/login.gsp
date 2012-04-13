<%--
  Created by IntelliJ IDEA.
  User: joshi088
  Date: 3/28/12
  Time: 12:07 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Login</title>
</head>
<body>
<div class="body">
    <h1>Login</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:form action="authenticate" controller="customer" method="POST">
        <p>Username: <g:textField name="email"/></p>
        <p>Password: <g:passwordField name="password"/></p>
        <g:submitButton name="Login" value="Login"/>
    </g:form>
</div>
</body>
</html>