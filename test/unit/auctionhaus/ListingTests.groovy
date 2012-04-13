package auctionhaus

import grails.test.mixin.*
import org.junit.*
import org.apache.commons.io.filefilter.FalseFileFilter

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Listing)
class ListingTests {

    def listing
    Customer customer = new Customer(email: 'ujjwal@joshi.com', password: 'testPassword', dateCreated: new Date())

    protected void setUp() {
        super.setUp()
    }

    void testNameIsRequired() {
        listing = new Listing(dateEnded: new Date() + 5, priceStarted: 40.99, description: 'Cannon Camera', seller: customer)
        listing.validate()
        assert 1 == listing.errors.fieldErrorCount
        assert 'nullable' == listing.errors['name'].code
    }

    void testNameIsNotBlank() {
        listing = new Listing(name: '', dateEnded: new Date() + 5, priceStarted: 40.99, description: 'Cannon Camera', seller: customer)
        listing.validate()
        assert 1 == listing.errors.fieldErrorCount
        assert 'blank' == listing.errors['name'].code
    }

    void testLengthOfNameTooLong() {
        listing = new Listing(name: 'sdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdadasdasdasdasb', dateEnded: new Date() + 5, priceStarted: 40.99, description: 'Cannon Camera', seller: customer)
        listing.validate()
        assert 1 == listing.errors.fieldErrorCount
        assert 'size.toobig' == listing.errors['name'].code
    }

    void testLengthOfNameOK() {
        listing = new Listing(name: 'sdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdadasdasdasda', dateEnded: new Date() + 5, priceStarted: 40.99, description: 'Cannon Camera', seller: customer)
        listing.validate()
        assert 0 == listing.errors.fieldErrorCount
        listing.name = 'asdadasdas'
        listing.validate()
        assert 0 == listing.errors.fieldErrorCount
        listing.name = 'a'
        listing.validate()
        assert 0 == listing.errors.fieldErrorCount

    }
    
    

    void testDateNotNull() {
        listing = new Listing(name: 'Cannon', priceStarted: 40.99, description: 'Cannon Camera', seller: customer)
        listing.validate()
        assert 1 == listing.errors.fieldErrorCount
        assert 'nullable' == listing.errors['dateEnded'].code
    }

    void testDateIsInFuture() {
        listing = new Listing(name: 'Cannon', dateEnded: new Date() - 2, priceStarted: 40.99, description: 'Cannon Camera', seller: customer)
        listing.validate()
        assert 1 == listing.errors.fieldErrorCount
        assert 'validator.invalid' == listing.errors['dateEnded'].code
    }

    void testPriceStartedIsRequired() {
        listing = new Listing(name: 'Cannon', dateEnded: new Date() + 2, description: 'Cannon Camera', seller: customer)
        listing.validate()
        assert 1 == listing.errors.fieldErrorCount
        assert 'nullable' == listing.errors['priceStarted'].code
    }

    void testPriceStartedIsValid() {
        listing = new Listing(name: 'Cannon', dateEnded: new Date() + 2, priceStarted: -0.99, description: 'Cannon Camera', seller: customer)
        listing.validate()
        assert 1 == listing.errors.fieldErrorCount
        assert 'validator.invalid' == listing.errors['priceStarted'].code
    }

    void testSellerIsRequired() {
        listing = new Listing(name: 'Cannon', dateEnded: new Date() + 2, priceStarted: 0.99, description: 'Cannon Camera')
        listing.validate()
        assert 1 == listing.errors.fieldErrorCount
        assert 'nullable' == listing.errors['seller'].code
    }
                                    //howa
    void testLengthOfDescriptionTooLong() {
        listing = new Listing(name: 'Cannon', dateEnded: new Date() + 2, priceStarted: 0.99, description: 'qsdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdadasdasdasdasdasdasdasdasdasdasdasdasdasasdasdasdasdasdasdasdasdadasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdadsdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdadasdasdasdas', seller: customer)
        listing.validate()
        assert 1 == listing.errors.fieldErrorCount
        assert 'size.toobig' == listing.errors['description'].code
        listing.description = 'qsdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdadasdasdasdasdasdasdasdasdasdasdasdasdasasdasdasdasdasdasdasdasdadasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdadsdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdadasdasdasdasooi'
        assert 1 == listing.errors.fieldErrorCount
        assert 'size.toobig' == listing.errors['description'].code
    }

    void testLengthOfDescriptionOK() {
        listing = new Listing(name: 'Cannon', dateEnded: new Date() + 2, priceStarted: 0.99, description: '', seller: customer)
        listing.validate()
        assert 0 == listing.errors.fieldErrorCount
        listing.description = 'qsdasdasdasdasdasdasdasdasdasdasdasd'
        assert 0 == listing.errors.fieldErrorCount
        listing.description = 'd'
        assert 0 == listing.errors.fieldErrorCount
        listing.description = ''
        assert 0 == listing.errors.fieldErrorCount

    }

}

