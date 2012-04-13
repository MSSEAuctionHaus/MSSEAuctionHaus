<div class="dialog" id="addBids">
    <div class="message" style="display:none;">
        <g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}"/>

        <g:hasErrors bean="${biddingInstance}">
            <div class="errors">
                <g:renderErrors bean="${biddingInstance}" as="list"/>
            </div>
        </g:hasErrors>
    </div>

    <!--UI-2: The action of placing a new bid on a listing will happen asynchronously, without leaving the page-->
    <g:formRemote name="addBids" method="post" url="[action:'addBids']"
                  update="[success:'bidslisting', failure:'addBids']"
                  onSuccess="\$('div.message').show().text('Bid submitted succesfully!');"
                  onFailure="\$('div.message').show()">
        <g:hiddenField name="listing.id" value="${listingInstance?.id}"/>
        <div class="dialog">
            <table>
                <tbody>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="bidAmount"><g:message code="bidding.bidAmount" default="Enter your bid amount: "/>:</label>
                        <g:textField name="bidAmount" value="${fieldValue(bean:biddingInstance, field: 'bidAmount')}"/>
                       <g:submitButton name="bid" class="save"  value="${message(code: 'bidding', default: 'Bid')}" />

                    </td>
                </tr>
                </tbody>
            </table>
        </div>



    </g:formRemote>
</div>