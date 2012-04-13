<%--
  Created by IntelliJ IDEA.
  User: joshi088
  Date: 4/6/12
  Time: 1:30 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="auctionhaus.Customer" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
<a href="#Register" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>
<div id="create-customer" class="content scaffold-create" role="main">
    <h1><g:message code="default.register.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${customerInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${customerInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="saveRegistration" >
        <fieldset class="form">
            <div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'email', 'error')} required">
                <label for="email">
                    <g:message code="customer.email.label" default="Email" />
                    <span class="required-indicator">*</span>
                </label>
                <g:field type="email" name="email" required="" value="${customerInstance?.email}"/>
            </div>

            <div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'password', 'error')} required">
                <label for="password">
                    <g:message code="customer.password.label" default="Password" />
                    <span class="required-indicator">*</span>
                </label>
                <g:passwordField name="password" maxlength="8" required="" value="${customerInstance?.password}"/>
            </div>

            <div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'password', 'error')} required">
                <label for="password">
                    <g:message code="customer.password.label" default="Confirm Password" />
                    <span class="required-indicator">*</span>
                </label>
                <g:passwordField name="confirm" maxlength="8" required=""/>
            </div>

        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="create" class="save" value="${message(code: 'default.button.register.label', default: 'Register')}" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
