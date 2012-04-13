package auctionhaus



import grails.test.mixin.*
import org.junit.*
import grails.test.mixin.domain.DomainClassUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CustomerService)
@TestMixin(DomainClassUnitTestMixin)
@Mock(Customer)
class CustomerServiceTests {

    //SRV-1: Create a Grails service method that supports creating a new customer (unit test)
    void testaddNewCustomer() {
        def customer = new Customer(email: "ujjwal77@gmail.com", password: "abcdef")
        int id = service.createNewCustomer(customer)
        assert id == 1
        def customer1 = new Customer(email: "ujjwal76@gmail.com", password: "abcdef")
        int id1 = service.createNewCustomer(customer1)
        assert id1 == 2
    }
}
