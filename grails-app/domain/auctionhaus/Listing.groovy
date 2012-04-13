package auctionhaus

class Listing {
    String name
    Date dateEnded
    BigDecimal priceStarted
    String description
    Customer seller
    Date dateCreated
    Boolean notificationSent=false
    public static final BigDecimal minBidIncrement = 0.5

    static hasMany = [biddings: Bidding]
    static belongsTo = [seller: Customer]

    static transients = ['winningBidPrice','minBidPrice','winner']

    String toString(){
        return name
    }


    static constraints = {
        // L1 and L6 name cannot be blank and needs to be less than 64 characters
        name(nullable:false, blank:false, size: 1..63)
        //L4 description can be blank but cannot be more than 256 characters
        description(nullable:true, blank: true, size:1..255)
        //L1 and L5 end date cannot be null. It needs to be a date sometime in the future.
        dateEnded(nullable:false,validator:{it > new Date()}  )
        //L1 starting Price cannot be null. Probably should not be negative
        dateCreated()
        priceStarted(nullable: false, validator: {it > 0.00})
        //L3 Listing is required to have a seller.
        seller (nullable: false)

    }

    BigDecimal getWinningBidPrice(){
        def output
        if (biddings != null)
        {
            output = Bidding.createCriteria().get(){
            eq ("listing",this)
            projections { max "bidAmount"}
        }

        if (output)
        {
           return output
        }
        else
        {
            return priceStarted
        }
    }
}
    boolean isExpired() {
        dateEnded <= new Date()
    }

    BigDecimal getMinBidPrice() {
        if (biddings) {
            getWinningBidPrice() + 0.50
        } else {
            priceStarted
        }
    }

    Bidding getWinningBid() {
        Bidding.findByListingAndBidAmount(this, getWinningBidPrice())
    }

//    Customer getWinner() {
//        def bidData =    (dateEnded.time <= new Date().time) ? Bidding.findAllByListing(this):null
//        return bidData[-1].bidder
//    }

    Customer getWinner() {
       (dateEnded.time <= new Date().time) ? getWinningBid()?.bidder : null

    }
}




