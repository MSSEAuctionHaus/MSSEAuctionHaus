package auctionhaus



import org.junit.*
import grails.test.mixin.*

@TestFor(ListingController)
@Mock([Listing,Customer, Bidding])
class ListingControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
     // params["name"] = 'Coke'
       // params["priceStarted"] = 1.50
        //params["name"] = 'someValidName'
        //params["name"] = 'someValidName'
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/listing/list" == response.redirectedUrl
    }

    //L-7: The detail page for the listing allows a new bid to be placed (unit test of the controller
    // action that handles this requirement)
    void testAddBids()
    {
        def today = new Date()
        def futureDate = today + 100

        def seller = ((new Customer(email: "ujjwal76@gmail.com", password: "abcdef"))).save(flush:true)
        assert 0 == seller.errors.errorCount
        def listing = (new Listing(name: "Coke", description:"It's coke", dateEnded: futureDate, priceStarted: 10, seller: seller)).save(flush: true)
        assert listing.errors.errorCount == 0
        def bidder = ((new Customer(email: "ujjwal77@gmail.com", password: "abcdef"))).save(flush:true)
        assert 0 == bidder.errors.errorCount

        controller.listingService = new ListingService()

        params['listing.id'] = listing.id
        params.bidAmount = 50
        session.user = bidder
       controller.addBids()
        assert 1 == listing.biddings.size()
        assert controller.flash.message == "default.successful.bid.submitted"

        response.reset()

        params['listing.id'] = listing.id
        params.bidAmount = 60
        controller.addBids()
        assert 2 == listing.biddings.size()
        assert controller.flash.message == "default.successful.bid.submitted"

    }
    //L-8: Validation errors will be displayed on the listing detail page if an added bid does not pass
    // validation (unit test of the controller action that handles this requirement)
    void testInvalidAddBids()
    {

        def today = new Date()
        def futureDate = today + 100

        def seller = ((new Customer(email: "ujjwal76@gmail.com", password: "abcdef"))).save(flush:true)
        assert 0 == seller.errors.errorCount
        def listing = (new Listing(name: "Coke", description:"It's coke", dateEnded: futureDate, priceStarted: 10, seller: seller)).save(flush: true)
        assert listing.errors.errorCount == 0
        def bidder = ((new Customer(email: "ujjwal77@gmail.com", password: "abcdef"))).save(flush:true)
        assert 0 == bidder.errors.errorCount

        controller.listingService = new ListingService()

       params['listing.id'] = listing.id
        params.bidAmount = 60
        session.user = bidder
        controller.addBids()
        assert 1 == listing.biddings.size()
        assert controller.flash.message == "default.successful.bid.submitted"

        response.reset()

        params['listing.id'] = listing.id
        params.bidAmount = 55
        controller.addBids()
        assert 1 == listing.biddings.size()
        assert flash.message != null
        print controller.flash.message
        assert controller.flash.message == "default.unsuccessful.bid.submitted"

        response.reset()

        params['listing.id'] = listing.id
        params.bidAmount = 50
        controller.addBids()
        assert 1 == listing.biddings.size()
        assert flash.message != null
        assert controller.flash.message == "default.unsuccessful.bid.submitted"

    }

    //UI-4: The action of placing a new bid will display a
    //message to the user indicating that the bid was successful (Controller Unit Test)
    void testFlashAddBidSuccessful() {
        def today = new Date()
        def futureDate = today + 100

        def seller = ((new Customer(email: "ujjwal76@gmail.com", password: "abcdef"))).save(flush:true)
        assert 0 == seller.errors.errorCount
        def listing = (new Listing(name: "Coke", description:"It's coke", dateEnded: futureDate, priceStarted: 10, seller: seller)).save(flush: true)
        assert listing.errors.errorCount == 0
        def bidder = ((new Customer(email: "ujjwal77@gmail.com", password: "abcdef"))).save(flush:true)
        assert 0 == bidder.errors.errorCount

        controller.listingService = new ListingService()

        params['listing.id'] = listing.id
        params.bidAmount = 60
        session.user = bidder
        //mockSession.user = bidder
        controller.addBids()
        def bids = Bidding.findAllByListing(listing)
        assert bids.size() == 1
        assert controller.flash.message == "default.successful.bid.submitted"
    }

    //UI-5: An error message will be displayed if placing a new bid is unsuccessful
    // (for instance if the new bid amount does not pass validation requirements) (Controller Unit Test)
    void testFlashAddBidFail() {
        def today = new Date()
        def futureDate = today + 100

        def seller = ((new Customer(email: "ujjwal76@gmail.com", password: "abcdef"))).save(flush:true)
        assert 0 == seller.errors.errorCount
        def listing = (new Listing(name: "Coke", description:"It's coke", dateEnded: futureDate, priceStarted: 10, seller: seller)).save(flush: true)
        assert listing.errors.errorCount == 0
        def bidder = ((new Customer(email: "ujjwal77@gmail.com", password: "abcdef"))).save(flush:true)
        assert 0 == bidder.errors.errorCount

        controller.listingService = new ListingService()

        params['listing.id'] = listing.id
        params.bidAmount = 60
        session.user = bidder
        //mockSession.user = bidder
        controller.addBids()
        def bids = Bidding.findAllByListing(listing)
        assert bids.size() == 1
        assert controller.flash.message == "default.successful.bid.submitted"

        response.reset()

        params['listing.id'] = listing.id
        params.bidAmount = 55
        controller.addBids()
        assert 1 == listing.biddings.size()
        assert controller.flash.message == "default.unsuccessful.bid.submitted"
    }


   /* void testList() {

        def model = controller.list()

        assert model.listingInstanceList.size() == 0
        assert model.listingInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.listingInstance != null
    }*/

  /*  void testSave() {
        controller.save()

        assert model.listingInstance != null
        assert view == '/listing/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/listing/show/1'
        assert controller.flash.message != null
        assert Listing.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/listing/list'


        populateValidParams(params)
        def listing = new Listing(params)

        assert listing.save() != null

        params.id = listing.id

        def model = controller.show()

        assert model.listingInstance == listing
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/listing/list'


        populateValidParams(params)
        def listing = new Listing(params)

        assert listing.save() != null

        params.id = listing.id

        def model = controller.edit()

        assert model.listingInstance == listing
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/listing/list'

        response.reset()


        populateValidParams(params)
        def listing = new Listing(params)

        assert listing.save() != null

        // test invalid parameters in update
        params.id = listing.id


        controller.update()

        assert view == "/listing/edit"
        assert model.listingInstance != null

        listing.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/listing/show/$listing.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        listing.clearErrors()

        populateValidParams(params)
        params.id = listing.id
        params.version = -1
        controller.update()

        assert view == "/listing/edit"
        assert model.listingInstance != null
        assert model.listingInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/listing/list'

        response.reset()

        populateValidParams(params)
        def listing = new Listing(params)

        assert listing.save() != null
        assert Listing.count() == 1

        params.id = listing.id

        controller.delete()

        assert Listing.count() == 0
        assert Listing.get(listing.id) == null
        assert response.redirectedUrl == '/listing/list'
    }*/



}
