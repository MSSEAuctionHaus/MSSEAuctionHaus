package auctionhaus

class Bidding {
    BigDecimal bidAmount
    Date dateCreated

    //B-1: Bids have the following required fields: amount and date/time of bid (unit test)
    //B-2: Bids are required to be for a Listing (unit test)
    //B-3: Bids are required to have a bidder (Customer) (unit test)
    static belongsTo = [bidder: Customer, listing: Listing]

//    String toString(){
//        return bidAmount
//    }

//    static mapping = {
//        listing cascade: 'refresh'
//    }

    static namedQueries = {
        bidsAboutListing { listingid ->
            eq 'listing', listingid
        }
    }

    static constraints = {

        //Bidding dateCreated shall be less than listing dateEnded
        dateCreated(validator:
                {val, obj ->
                    if (obj.listing) {
                        val <= obj.listing.dateEnded
                    }
                }
        )

        //Bidders can not be sellers. That would be cheating
        bidder(Validator:
                {val, obj ->
                    if (obj.listing)
                        obj.listing.seller == !val
                })

        /*B-2: Bids are required to be for a Listing (unit test). Instance of Bid can not have Nullable listings and bidders*/
        //B-3: Bids are required to have a bidder (Customer) (unit test)

        bidAmount(nullable: false)

        //B-5: The Bid amount must be at least .50 higher than the previous Bid for the same listing (integration test)
        //The custom validator needs to make sure it's only validating the last bid that was added.
        bidAmount(validator:
                {val, obj ->
                    if (obj.listing)
                    {
                        if (obj.listing.biddings && obj.id == null) {
                            if (obj.listing.biddings.size() >= 1)
                            {
                            return val >= (obj.listing.winningBidPrice + 0.5)
                            }
                        }
                        else {
                          return val >= (obj.listing.priceStarted + 0.5)
                        }
                    }
                }
        )


    }
}
