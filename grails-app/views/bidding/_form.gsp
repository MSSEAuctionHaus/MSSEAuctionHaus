<%@ page import="auctionhaus.Bidding" %>



<div class="fieldcontain ${hasErrors(bean: biddingInstance, field: 'bidder', 'error')} required">
	<label for="bidder">
		<g:message code="bidding.bidder.label" default="Bidder" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="bidder" name="bidder.id" from="${auctionhaus.Customer.list()}" optionKey="id" required="" value="${biddingInstance?.bidder?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: biddingInstance, field: 'bidAmount', 'error')} required">
	<label for="bidAmount">
		<g:message code="bidding.bidAmount.label" default="Bid Amount" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="bidAmount" required="" value="${fieldValue(bean: biddingInstance, field: 'bidAmount')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: biddingInstance, field: 'listing', 'error')} required">
	<label for="listing">
		<g:message code="bidding.listing.label" default="Listing" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="listing" name="listing.id" from="${auctionhaus.Listing.list()}" optionKey="id" required="" value="${biddingInstance?.listing?.id}" class="many-to-one"/>
</div>

