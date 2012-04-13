<%@ page import="auctionhaus.Listing" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'listing.label', default: 'Listing')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <g:javascript library='jquery' plugin='jquery'/>
        <!--UI-1: The listing detail page will asynchronously load and display a list of the last 10 bids placed
        on the item showing the user, date/time, and amount. The implementation of the lookup of these results
         must be done with a Named Query. (Integration Test)-->
        <g:javascript>
             $(document).ready(function() {
             $('#bidsSpinner').show();
                setInterval("updateListing();", 2000)
                    <g:remoteFunction action="getBids" update="bidslisting" id="${fieldValue(bean: listingInstance, field: 'id')}"
                                      params="[max:10,order:asc]"/>
            });

              function updateListing() {
                    $.ajax({
                            url:"${createLink(controller:'listing', action:'getMostRecentBidPrice', id:listingInstance.id)}",
                            success:function(data){
                                $('#minimumBidPrice').html(data);
                    }
                });
             }
        </g:javascript>
	</head>
	<body>

		<a href="#show-listing" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <g:if test="${session?.user}">
                    <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </g:if>
			</ul>
		</div>

<div class="body">
<g:if test="${!listingInstance.isExpired()}">
    <h1><g:message code="listing.show" default="Show Listing"/></h1>
    <div class="dialog">
        <table>
            <tbody>

            <div id="minimumBidPrice" style="color:#00FF00"> </div>

            %{--<tr class="prop">--}%
                %{--<td valign="top" class="name"><g:message code="listing.id" default="Id"/>:</td>--}%

                %{--<td valign="top" class="value">${fieldValue(bean: listingInstance, field: "id")}</td>--}%

            %{--</tr>--}%

            <tr class="prop">
                <td valign="top" class="name"><g:message code="listing.name" default="Name"/>:</td>

                <td valign="top" class="value">${fieldValue(bean: listingInstance, field: "name")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="listing.dateEnded" default="End Date And Time"/>:</td>

                <td valign="top" class="value"><g:formatDate date="${listingInstance?.dateEnded}"/></td>

            </tr>


            %{--<tr class="prop">--}%
                %{--<td valign="top" class="name"><g:message code="listing.minBidPrice" default="Minimum Bid Price"/>:</td>--}%
                %{--<td valign="top" class="value" id="minBidPrice">$<g:formatNumber number="${fieldValue(bean: listingInstance, field: 'minBidPrice')}" currencyCode="USD" format="#,##0.00"/></td>--}%
            %{--</tr>--}%


            <tr class="prop">
                <td valign="top" class="name"><g:message code="listing.seller" default="Seller"/>:</td>

                <td valign="top" class="value">${listingInstance?.seller?.email?.split('@')[0].encodeAsHTML()}</td>

            </tr>

    <g:if test="${listingInstance?.description}">
            <tr class="prop">
                <td valign="top" class="name"><g:message code="listing.description" default="Description"/>:</td>

                <td valign="top" class="value">${fieldValue(bean: listingInstance, field: "description")}</td>

            </tr>
    </g:if>

            <g:if test="${listingInstance?.priceStarted}">
                <tr class="prop">
                    <td valign="top" class="name"><g:message code="listing.priceStarted" default="Price Started"/>:</td>

                    <td valign="top" class="value">${fieldValue(bean: listingInstance, field: "priceStarted")}</td>

                </tr>
            </g:if>

            <g:if test="${listingInstance?.winner}">
                <tr class="prop">
                    <td valign="top" class="name"><g:message code="listing.winner" default="Winner"/>:</td>

                    <td valign="top" class="value"><g:link controller="customer" action="show" id="${listingInstance?.winner?.id}">${listingInstance?.winner?.email.split('@').encodeAsHTML()}</g:link></td>

                </tr>
            </g:if>
            <g:else>
                <tr class="prop">
                    <td valign="top" class="name"><g:message code="listing.winningBid" default="Most Recent Bids"/>:</td>
                    <td valign="top" style="text-align: left;" class="value" id="bidslisting">
                        <div id="bidsSpinner" class="bidsSpinner">
                            <img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="${message(code: 'spinner.alt', default: 'Loading...')}"/>
                        </div>
                    </td>
                </tr>
            </g:else>

            </tbody>
        </table>
    </div>


    
                <!--L-7: The detail page for the listing allows a new bid to be placed (unit test of the controller action that handles this requirement)-->
    <g:if test="${session?.user}">
    <g:if test="${listingInstance.seller.toString()!= session?.user.toString()}">
        <g:render template="addBids"/>
    </g:if>
    </g:if>

</g:if>
    <g:else>
        <h1><g:message code="listing.expired" default="This listing is expired!"/></h1>
    </g:else>
             <g:if test="${listingInstance.seller.toString()== session?.user.toString()}">
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${listingInstance?.id}" />
                    <g:hiddenField name="user" value="${session.user}" />
                    <g:hiddenField name="seller" value="${listingInstance.seller}" />
                <g:link class="edit" action="edit" id="${listingInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
			</g:form>
             </g:if>

    %{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%

		</div>
	</body>
</html>
