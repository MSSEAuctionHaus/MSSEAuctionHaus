
<%@ page import="auctionhaus.Listing" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'listing.label', default: 'Listing')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-listing" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
<g:if test="${session?.user}">
    <li><g:link class="create" action="create"><g:message code="default.new.mylisting.label" args="[entityName]" />
                </g:link></li>
    </g:if>
                <g:hiddenField name="role" value="${session?.user?.role}" />
<g:if test="${session?.user?.role.equals('admin')}">
    <li><g:link class="create" action="create" controller="customer"><g:message code="default.new1.label" default="Create Customer" /></g:link></li>
    <li><g:link class="list" action="list" controller="customer"><g:message code="default.new1.label" default="Customer List" /></g:link></li>
    <li><g:link class="list" action="list" controller="bidding"><g:message code="default.new1.label" default="Bidding List" /></g:link></li>
    </g:if>
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
					   <!--M-5: The name is visible for each listing
                           M-6: The number of bids is visible for each listing
                           M-7: The starting price is visible for each listing
                           M-8: The end date/time is visible for each listing-->
						<g:sortableColumn property="name" title="${message(code: 'listing.name.label', default: 'Name')}" />
                        <g:sortableColumn  property= "NumberOfBids" title="Number of Bids" />
						<g:sortableColumn property="dateEnded" title="${message(code: 'listing.dateEnded.label', default: 'Date Ended')}" />
						<g:sortableColumn property="priceStarted" title="${message(code: 'listing.priceStarted.label', default: 'Price Started')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${listingInstanceList}" status="i" var="listingInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${listingInstance.id}">${fieldValue(bean: listingInstance, field: "name")}</g:link></td>
                        <td>${listingInstance?.biddings.size()}</td>
						<td><g:formatDate date="${listingInstance.dateEnded}" /></td>
						<td>${fieldValue(bean: listingInstance, field: "priceStarted")}</td>
					</tr>
				</g:each>
				</tbody>
			</table>



            <!--M-3: If more than 5 listings exist, pagination links will be available to let the user page through the listings-->
			<div class="pagination">
            <g:paginate total="${listingInstanceTotal}" />
        </div>
		</div>
	</body>
</html>
