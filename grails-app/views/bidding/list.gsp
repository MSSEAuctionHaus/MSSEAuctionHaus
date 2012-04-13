
<%@ page import="auctionhaus.Bidding" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bidding.label', default: 'Bidding')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-bidding" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-bidding" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'bidding.dateCreated.label', default: 'Date Created')}" />
					
						<th><g:message code="bidding.bidder.label" default="Bidder" /></th>
					
						<g:sortableColumn property="bidAmount" title="${message(code: 'bidding.bidAmount.label', default: 'Bid Amount')}" />
					
						<th><g:message code="bidding.listing.label" default="Listing" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${biddingInstanceList}" status="i" var="biddingInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${biddingInstance.id}">${fieldValue(bean: biddingInstance, field: "dateCreated")}</g:link></td>
					
						<td>${fieldValue(bean: biddingInstance, field: "bidder")}</td>
					
						<td>${fieldValue(bean: biddingInstance, field: "bidAmount")}</td>
					
						<td>${fieldValue(bean: biddingInstance, field: "listing")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${biddingInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
