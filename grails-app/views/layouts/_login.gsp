<div id="login" class="loginBox">
    <g:form action="login" controller="customer" method="POST">
        <p>Username: <g:textField name="email"/></p>
        <p>Password: <g:passwordField name="password"/></p>
        <g:submitButton name="Login" value="Login"/>
    </g:form>

</div>