<%@ page import="auctionhaus.Listing" %>



<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="listing.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="63" required="" value="${listingInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="listing.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="255" value="${listingInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'dateEnded', 'error')} required">
	<label for="dateEnded">
		<g:message code="listing.dateEnded.label" default="Date Ended" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateEnded" precision="day"  value="${listingInstance?.dateEnded}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'priceStarted', 'error')} required">
	<label for="priceStarted">
		<g:message code="listing.priceStarted.label" default="Price Started" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="priceStarted" required="" value="${fieldValue(bean: listingInstance, field: 'priceStarted')}"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'seller', 'error')} required">--}%
	%{--<label for="seller">--}%
		%{--<g:message code="listing.seller.label" default="Seller" />--}%
		%{--<span class="required-indicator">*</span>--}%
	%{--</label>--}%
	%{--<g:select id="seller" name="seller.id" from="${auctionhaus.Customer.list()}" optionKey="id" required="" value="${listingInstance?.seller?.id}" class="many-to-one"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'winner', 'error')} ">--}%
	%{--<label for="winner">--}%
		%{--<g:message code="listing.winner.label" default="Winner" />--}%
		%{----}%
	%{--</label>--}%
	%{--<g:select id="winner" name="winner.id" from="${auctionhaus.Customer.list()}" optionKey="id" value="${listingInstance?.winner?.id}" class="many-to-one" noSelection="['null': '']"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'biddings', 'error')} ">--}%
	%{--<label for="biddings">--}%
		%{--<g:message code="listing.biddings.label" default="Biddings" />--}%
		%{----}%
	%{--</label>--}%

%{--<ul class="one-to-many">--}%
%{--<g:each in="${listingInstance?.biddings?}" var="b">--}%
    %{--<li><g:link controller="bidding" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>--}%
%{--</g:each>--}%
%{--<li class="add">--}%
%{--<g:link controller="bidding" action="create" params="['listing.id': listingInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'bidding.label', default: 'Bidding')])}</g:link>--}%
%{--</li>--}%
%{--</ul>--}%

</div>

