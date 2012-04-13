package auctionhaus



import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerController)
@Mock([Customer,Listing])
class CustomerControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
        params["email"] = 'new@gmail.com'
        params["password"] = 'abcdefd'
    }

   /* void testIndex() {
        controller.index()
        assert "/customer/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.customerInstanceList.size() == 0
        assert model.customerInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.customerInstance != null
    }

    void testSave() {
        controller.save()

        assert model.customerInstance != null
        assert view == '/customer/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/customer/show/1'
        assert controller.flash.message != null
        assert Customer.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/customer/list'


        populateValidParams(params)
        def customer = new Customer(params)

        assert customer.save() != null

        params.id = customer.id

        def model = controller.show()

        assert model.customerInstance == customer
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/customer/list'


        populateValidParams(params)
        def customer = new Customer(params)

        assert customer.save() != null

        params.id = customer.id

        def model = controller.edit()

        assert model.customerInstance == customer
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/customer/list'

        response.reset()


        populateValidParams(params)
        def customer = new Customer(params)

        assert customer.save() != null

        // test invalid parameters in update
        params.id = customer.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/customer/edit"
        assert model.customerInstance != null

        customer.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/customer/show/$customer.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        customer.clearErrors()

        populateValidParams(params)
        params.id = customer.id
        params.version = -1
        controller.update()

        assert view == "/customer/edit"
        assert model.customerInstance != null
        assert model.customerInstance.errors.getFieldError('version')
        assert flash.message != null
    }*/

    void testDelete() {
        def today = new Date()
        def futureDate = today + 100

        //first method to test not found exception
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customer/list'


        response.reset()

        //second method happy path   to test if delete is working
        populateValidParams(params)
        def customer = new Customer(params)
        assert customer.save() != null
        assert Customer.count() == 1

        params.id = customer.id
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customer/list'
        response.reset()

        //C-4: An existing customer can only be deleted through the web interface if they have 0 listings and 0 bids.
        // The system will present an error message to the user if the delete cannot be performed
        // (unit test of the controller action that has this logic)
        populateValidParams(params)
        def seller = new Customer(params)
        seller.save()
        def bidder = new Customer(email: "ad@gmail.com",password: "abcdefd").save()
        def listing = new Listing(name: "TV", dateEnded: futureDate, priceStarted: 1.50, seller: seller).save(flush: true)
        listing.addToBiddings(new Bidding(bidAmount: 2.00, bidder: bidder))
        listing.save(flush: true)

        assert Listing.count() == 1
        assert listing.biddings.size() == 1
        params.id = bidder.id
        controller.delete()
        assert flash.message != null
    }
}
