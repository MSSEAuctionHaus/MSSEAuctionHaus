package auctionhaus



class ListingEndedMonitorJob {
    def ListingNotificationService
    static triggers = {
      simple repeatInterval: 5000l // execute job once in 5 seconds
    }

    def execute() {
        //check if any listing is expired and post message to JMS
      //listingNotificationService.listingEndedJMS();
    }
}
