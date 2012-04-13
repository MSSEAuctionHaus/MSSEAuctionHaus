package auctionhaus

import static org.junit.Assert.*
import org.junit.*

class BiddingIntegrationTests {
    def today = new Date()
    def futureDate = today + 100
    def seller
    def bidder
    def listing

    @Before
    void setUp() {
        seller = ((new Customer(email: "seller@gmail.com", password: "abcdef")).save(flush: true))
        bidder = new Customer(email: "bidder@gmail.com", password: "abcdef").save(flush: true)
        listing = (new Listing(name: "SomethingNew", dateEnded: futureDate, priceStarted: 1.50, seller: seller)).save(flush: true)
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testBiddingIsSavedWhenListingIsSaved() {
//B-6: When a Listing is saved, any new Bids added for the listing must be saved (integration test)
        //Bidding is unorder collections. bidding is added sequentially
        def listing1 = (new Listing(name: "SomethingNew", dateEnded: futureDate, priceStarted: 1.50, seller: seller)).save(flush: true)
        listing1.addToBiddings(new Bidding(bidAmount: 2.00, bidder: bidder)).save(flush: true)
        listing1.addToBiddings(new Bidding(bidAmount: 2.50, bidder: bidder)).save(flush: true)
        listing1.addToBiddings(new Bidding(bidAmount: 3.00, bidder: bidder)).save(flush: true)
        listing1.addToBiddings(new Bidding(bidAmount: 4.00, bidder: bidder)).save(flush: true)
        listing1.addToBiddings(new Bidding(bidAmount: 5.00, bidder: bidder)).save(flush: true)
        listing1.addToBiddings(new Bidding(bidAmount: 6.00, bidder: bidder)).save(flush: true)
        assert 6 == Bidding.where {listing == listing1}.count()
    }

    @Test
    void testBidAmountIsFiftyCentHigherThanPreviousBid() {
        //B-5: The Bid amount must be at least .50 higher than the previous Bid for the same listing (integration test)
        new Bidding(bidAmount: 2.00, bidder: bidder, listing: listing).save(flush: true)
        listing.refresh()
        assert (new Bidding(bidAmount: listing.winningBidPrice + 0.50, bidder: bidder, listing: listing).validate())
        assert !(new Bidding(bidAmount: listing.winningBidPrice + 0.49, bidder: bidder, listing: listing).validate())
        assert (new Bidding(bidAmount: listing.winningBidPrice + 0.51, bidder: bidder, listing: listing).validate())
    }

    //UI-1The implementation of the lookup of these results must be done with a Named Query. (Integration Test)
    public testBidsAboutListingNamedQuery() {
        def listing2 = (new Listing(name: "SomethingNew", dateEnded: futureDate, priceStarted: 1.50, seller: seller)).save(flush: true)
        listing2.addToBiddings(new Bidding(bidAmount: 2.00, bidder: bidder)).save(flush: true)
        listing2.addToBiddings(new Bidding(bidAmount: 2.50, bidder: bidder)).save(flush: true)
        def biddings = Bidding.bidsAboutListing(listing2).list()
        assert 2 == biddings.size()

    }


}
