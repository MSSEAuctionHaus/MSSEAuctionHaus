<g:if test="${bidInstanceList}">
    <table>
        <thead>
            <td valign="top" class="name"><g:message code="listing.minBidPrice" default="Minimum Bid Price"/>:</td>
            <td valign="top" class="value" id="minBidPrice">$<g:formatNumber number='${minBidPrice}' format='#,##0.00'/></td>

        <tr>
            <th><g:message code="bidding.bidder" default="Bidder"/></th>
            <th><g:message code="bid.dateCreated" default="Date Created"/></th>
            <th><g:message code="bid.amount" default="Amount"/></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${bidInstanceList}" status="i" var="bidInstance">
            <tr>
                <td>${bidInstance.bidder.getCustomerName()}</td>
                <td><g:formatDate date="${bidInstance.dateCreated}"/></td>
                <td>$<g:formatNumber number="${fieldValue(bean: bidInstance, field: 'bidAmount')}" currencyCode="USD" format="#,##0.00"/></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</g:if>