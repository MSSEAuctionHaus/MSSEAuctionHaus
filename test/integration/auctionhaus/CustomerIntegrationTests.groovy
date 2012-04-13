package auctionhaus

import static org.junit.Assert.*
import org.junit.*

class CustomerIntegrationTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testEmailIsUnique() {
        //C-2: Email address must be a unique field (integration test)
        def customer = new Customer(email:"abc@gmail.com", password:"abcdef")
        customer.save(flush:true)
        assert 0 == customer.errors.errorCount

        def customer1 = new Customer(email:"abc@gmail.com", password:"abcdeaf")
        customer1.save(flush:true)
        assert 1 == customer1.errors.errorCount

        def customer2 = new Customer(email:"abcd@gmail.com", password:"abcdeaf")
        customer2.save(flush:true)
        assert 0 == customer2.errors.errorCount


    }


}
