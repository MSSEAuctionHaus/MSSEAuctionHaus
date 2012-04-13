package auctionhaus

import grails.converters.deep.JSON
import grails.plugin.mail.MailService

import org.apache.activemq.ActiveMQConnectionFactory



class ListingNotificationService {
    boolean transactional = false
    def jmsService
    def mailService
    def messageSource
    static exposes = ['jms']
    String listingEndedQueue = "queue.listingended";


    def listingEndedJMS(){
        println("Get all expired listing")
        def listings = Listing.findAllByDateEndedLessThan(new Date())
        listings.each {sendListingEndedJMS(it)}
    }

    def sendListingEndedJMS(Listing listing) {

        println("sendListingEndedJMS for ${listing}")

        def messageString = (listing as JSON).toString()
        //print messageString.toString()

        jmsService.send(listingEndedQueue, messageString)
        //jmsService.send(service:"queue",messageString.toString(),"standard",null)
        println "completed sendListingJMS for: " + listing.description
        listing.notificationSent=true

    }

    def listingEndedEmail(Listing listing) {

        if (listing?.dateEnded <= new Date()) {
            try {
                println("Listing ended call for ${listing}")
                sendListingEndedEmailToWinner(listing)
                sendListingEndedEmailToSeller(listing)
            } catch (e) {
                println "exception: ${e}"
            }
        }
    }

     //C-2: When a listing completes, send a confirmation email to the winner and the lister with a link
     // to the listing information and the final price. (integration test for extra credit)
    def  sendListingEndedEmailToWinner(Listing listing){
        println("sendListingEndedEmailToWinner for ${listing}")
        def subjecttxt = "Congratulations!! You own the listing for ${listing}"

        def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()
        def lnktxt = g.createLink(controller: 'listing', action: 'show',id: listing.id, absolute: 'true')
        //def manageSubscriptions = g.createLink(controller: 'comm', action: 'index', absolute: 'true')
        def bodytxt = "You won the bid with final price: ${listing.winningBidPrice}. Click link: ${lnktxt} to see details of the listing"


      sendMail{
         to  listing.winner.email
         subject subjecttxt
         body bodytxt
        }
        println("complete sendListingEndedEmailToWinner for ${listing}")
    }

    //C-2: When a listing completes, send a confirmation email to the winner and the lister with a link to
    // the listing information and the final price. (integration test for extra credit)
    def  sendListingEndedEmailToSeller(Listing listing){
        println("sendListingEndedEmailToSeller for ${listing}")
        def subjecttxt = "Congratulations!! You sold the listing for ${listing}"

        def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()
        def lnktxt = g.createLink(controller: 'listing', action: 'show',id: listing.id, absolute: 'true')
        //def manageSubscriptions = g.createLink(controller: 'comm', action: 'index', absolute: 'true')
        def bodytxt = "You sold the listing with final price: ${listing.winningBidPrice}. Click link: ${lnktxt} to see details of the listing"


        sendMail{
            to  listing.seller.email
            subject subjecttxt
            body bodytxt
        }
        println("complete sendListingEndedEmailToWinner for ${listing}")
    }



}
