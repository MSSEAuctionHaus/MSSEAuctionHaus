import auctionhaus.Customer
import auctionhaus.Listing
import auctionhaus.Bidding
import groovy.time.*
import org.codehaus.groovy.runtime.TimeCategory


class BootStrap {

    def init = { servletContext ->
        def today = new Date()
        def futureDate = today + 100


        def a= new DatumDependentDuration(0, 0, 0, 0, 2, 0, 0)
        def b=  new Date()
        def twoMinExpired = a+b

        def twoWeeksFromNow = GregorianCalendar.getInstance()
        twoWeeksFromNow.add(Calendar.WEEK_OF_YEAR, 2)
        twoWeeksFromNow = twoWeeksFromNow.getTime()

        def seller = ((new Customer(email: "ujjwal77@gmail.com", password: "abcdef",role: "author")).save(flush: true))
        def seller1 = ((new Customer(email: "sabin8@gmail.com", password: "abcdef",role: "author")).save(flush: true))
        def bidder = ((new Customer(email: "kancharam@gmail.com", password: "abcdef")).save(flush: true))
        def bidder1 = ((new Customer(email: "gorkhali@gmail.com", password: "abcdef")).save(flush: true))
        def bidder2 = ((new Customer(email: "rajeshhamal@gmail.com", password: "abcdef")).save(flush: true))
        ((new Customer(email: "armpit@bradpitt.net", password: "abcdef")).save(flush: true))
        ((new Customer(email: "admin@gmail.com", password: "admin1",role: "admin")).save(flush: true))


        def listing = (new Listing(name: "TV", description:"It's TV", dateEnded: futureDate, priceStarted: 500, seller: seller)).save(flush: true)
        def listing1 = (new Listing(name: "PS3",description:"New", dateEnded: futureDate, priceStarted: 299, seller: seller)).save(flush: true)
        (new Listing(name: "Laptop", description:"Brand New", dateEnded: futureDate, priceStarted: 500, seller: seller1)).save(flush: true)
        (new Listing(name: "MW3", description:"No Scratch. Perfect Condition",dateEnded: futureDate, priceStarted: 50, seller: seller1)).save(flush: true)
        (new Listing(name: "MGS3", description:"No Scratch. Perfect Condition", dateEnded: futureDate, priceStarted: 50, dateCreated: today-5, seller: seller1)).save(flush: true)
        def listing3 = (new Listing(name: "Penny", dateEnded: futureDate, priceStarted: 50, dateCreated: today-9, seller: seller1)).save(flush: true)
        (new Listing(name: "Mike", dateEnded: new Date(), priceStarted: 50, seller: seller1)).save(flush: true)
        (new Listing(name: "NFL Mug", dateEnded: twoMinExpired, priceStarted: 50, seller: seller1)).save(flush: true)



        listing.addToBiddings(new Bidding(bidAmount: 600, bidder: bidder)).save(flush: true)
        listing.addToBiddings(new Bidding(bidAmount: 700, bidder: bidder1)).save(flush: true)
        listing.addToBiddings(new Bidding(bidAmount: 800, bidder: bidder1)).save(flush: true)

        listing1.addToBiddings(new Bidding(bidAmount: 300, bidder: bidder)).save(flush: true)
        listing1.addToBiddings(new Bidding(bidAmount: 350, bidder: bidder1)).save(flush: true)

        listing3.addToBiddings(new Bidding(bidAmount: 55, bidder: bidder2)).save(flush: true)
        listing3.addToBiddings(new Bidding(bidAmount: 60, bidder: bidder1)).save(flush: true)
        listing3.addToBiddings(new Bidding(bidAmount: 62, bidder: bidder2)).save(flush: true)
        listing3.addToBiddings(new Bidding(bidAmount: 62.8, bidder: bidder1)).save(flush: true)
        listing3.addToBiddings(new Bidding(bidAmount: 72.8, bidder: bidder1)).save(flush: true)


    }
    def destroy = {
    }
}
