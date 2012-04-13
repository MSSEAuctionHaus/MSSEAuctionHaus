<%--
  Created by IntelliJ IDEA.
  User: ujjwal
  Date: 3/28/12
  Time: 9:14 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'mylisting.label', default: 'My Listing')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-listing" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.mylisting.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="list-listing" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <g:sortableColumn property="name" title="${message(code: 'listing.name.label', default: 'Name')}" />
            <g:sortableColumn  property= "NumberOfBids" title="Number of Bids" />
            <g:sortableColumn property="dateEnded" title="${message(code: 'listing.dateEnded.label', default: 'Date Ended')}" />
            <g:sortableColumn property="priceStarted" title="${message(code: 'listing.priceStarted.label', default: 'Price Started')}" />
        </tr>
        </thead>
        <tbody>
        <g:each in="${listingInstanceList}" status="i" var="listingInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td valign="top" class="value">${fieldValue(bean: listingInstance, field: "name")}</td>
                <td>${listingInstance?.biddings.size()}</td>
                <td><g:formatDate date="${listingInstance.dateEnded}" /></td>
                <td>${fieldValue(bean: listingInstance, field: "priceStarted")}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${listingInstanceTotal}" />
    </div>

</div>
</body>
</html>
