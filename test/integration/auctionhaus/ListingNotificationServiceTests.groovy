package auctionhaus

import static org.junit.Assert.*
import org.junit.*
import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import grails.converters.deep.JSON
import com.icegreen.greenmail.util.*
import groovy.json.JsonSlurper


@TestFor(ListingNotificationService)
@Mock([Listing,Bidding,Customer])
class ListingNotificationServiceTests {
    def jmsService
    def mailService
    def greenMail
    String listingEndedQueue = "queue.listingended";



    void testListingEndedSendJMS() {

        def seller = new Customer(email: "ujjwal55@gmail.com", password: "abcdef").save(flush:true)

        //Ended Listing
        def listing1 = (new Listing(name: "Cup", description:"cup", dateEnded: new Date()-1,
                priceStarted: 10, seller: seller,notificationSent: false))
        listing1.addToBiddings(new Bidding(bidAmount: 15, bidder: seller)).save(flush: true, validate: false)
        listing1.save(validate: false, flush: true)

        //Ended Listing
        def listing2 = (new Listing(name: "Mug", description:"mug", dateEnded: new Date()-1,
                priceStarted: 12, seller: seller,notificationSent: false))
        listing2.addToBiddings(new Bidding(bidAmount: 20, bidder: seller)).save(flush: true, validate: false)
        listing2.save(validate: false, flush: true)

        //Not Ended Listing
        def listing3 = (new Listing(name: "Glass", description:"glass", dateEnded: new Date()+1,
                priceStarted: 2, seller: seller,notificationSent: false))
        listing3.addToBiddings(new Bidding(bidAmount: 3, bidder: seller)).save(flush: true, validate: false)
        listing3.save(validate: false, flush: true)


        def srv = new ListingNotificationService()
        srv.jmsService = jmsService
        srv.listingEndedJMS()

        assert true == listing1.notificationSent
        assert true == listing2.notificationSent
        assert false == listing3.notificationSent
    }

    //Functional test for email. Run this and check if winner and seller get the email with formatted text
    void testListingEndedSendEmail() {

        def seller = new Customer(email: "joshi088@umn.edu", password: "abcdef").save(flush:true)
        def winner1 =  new Customer(email: "ujjwal77@gmail.com", password: "abcdef").save(flush:true, validate: false)

        def listing1 = (new Listing(name: "Orange", description:"cup", dateEnded: new Date()-1,
                priceStarted: 10, seller: seller,notificationSent: false))
        listing1.addToBiddings(new Bidding(bidAmount: 15, bidder: seller)).save(flush: true, validate: false)
        listing1.addToBiddings(new Bidding(bidAmount: 16, bidder: seller)).save(flush: true, validate: false)
        listing1.addToBiddings(new Bidding(bidAmount: 17, bidder: winner1)).save(flush: true, validate: false)
        listing1.save(validate: false, flush: true)
        def srv = new ListingNotificationService()
        srv.mailService =mailService
        srv.listingEndedEmail(listing1)

    }

    //Extra Credit using Green Mail
    void testListingEndedSendEmail_usingGreenMail() {

        def seller = new Customer(email: "joshi088@umn.edu", password: "abcdef").save(flush:true)
        def winner1 =  new Customer(email: "ujjwal77@gmail.com", password: "abcdef").save(flush:true, validate: false)

        def listing1 = (new Listing(name: "Orange", description:"cup", dateEnded: new Date()-1,
                priceStarted: 10, seller: seller,notificationSent: false))
        listing1.addToBiddings(new Bidding(bidAmount: 15, bidder: seller)).save(flush: true, validate: false)
        listing1.addToBiddings(new Bidding(bidAmount: 16, bidder: seller)).save(flush: true, validate: false)
        listing1.addToBiddings(new Bidding(bidAmount: 17, bidder: winner1)).save(flush: true, validate: false)
        listing1.save(validate: false, flush: true)
        def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()
        def subjecttxt = "Congratulations!! You own the listing for ${listing1}"
        def lnktxt = g.createLink(controller: 'listing', action: 'show',id: listing1.id, absolute: 'true')
        //def manageSubscriptions = g.createLink(controller: 'comm', action: 'index', absolute: 'true')
        def bodytxt = "You won the bid with final price: ${listing1.winningBidPrice}. Click link: ${lnktxt} to see details of the listing"

        def srv = new ListingNotificationService()
        srv.mailService =mailService
        srv.listingEndedEmail(listing1)

        Map mail = [message:bodytxt, from:'msseauctionhaus@gmail.com',to:'ujjwal77@gmail.com', subject:subjecttxt]
        def receivedMsg = greenMail.getReceivedMessages()[0]

        //two message sent, one for lister other for winner
        assertEquals(2, greenMail.getReceivedMessages().length)

        assertEquals(mail.message.toString(), GreenMailUtil.getBody(receivedMsg))
        assertEquals(mail.subject.toString(),receivedMsg.subject)

    }

    void tearDown() {
        greenMail.deleteAllMessages()
    }



}
