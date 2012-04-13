package auctionhaus

import grails.validation.ValidationException
import javax.persistence.PersistenceException

class CustomerService {

   //SRV-1: Create a Grails service method that supports creating a new customer (unit test)
    def createNewCustomer(Customer customer) {
        if (!customer.validate()) {
            throw new ValidationException("Customer is not valid",customer.errors)
        }   else if(!customer.save(flush: true)){
            throw new PersistenceException()
        }
        return customer.id
    }
}


