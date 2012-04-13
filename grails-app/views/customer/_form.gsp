<%@ page import="auctionhaus.Customer" %>



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

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'role', 'error')} required">
    <label for="role">
        <g:message code="customer.role.label" default="Role" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="role" from="${['author','admin']}"></g:select>

</div>


</div>

