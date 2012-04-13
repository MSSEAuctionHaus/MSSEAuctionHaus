package auctionhaus

import org.springframework.dao.DataIntegrityViolationException

class CustomerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def customerService

    def beforeInterceptor = [action:this.&auth, except:["login", "logout","authenticate","register","saveRegistration"]]

    def auth() {
        if( !(session?.user?.role == "admin") ){
            flash.message = "You must have an administrator privilege to perform this task."
            redirect(action:"login")
            return false
        }
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def login={}

    def authenticate={
        Customer user = Customer.findByEmailAndPassword(params.email, params.password)
        def targetUri = params.targetUri ?: "/"

        if (user) {
            session.putValue("user",user)
            flash.message = "Hello ${user.email}"
            redirect(controller: "Listing", action: 'list')
        } else {
            flash.message = "Login Failed. Please check your username and/or password. If you are first time user, please Register"
            redirect(action: 'login')
        }
    }

    def logout ={
        flash.message = "Goodbye ${session.user.email}"
        session.invalidate()
        redirect(controller:"Listing", action:"list")

    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [customerInstanceList: Customer.list(params), customerInstanceTotal: Customer.count()]
    }

    
    def create() {
        [customerInstance: new Customer(params)]
    }

    def register() {}

    //SRV-4: Refactor your Controllers from the previous assignments to use the service methods created in this section (unit test)
    def saveRegistration() {
        def customerInstance = new Customer(params)
        if (params.password != params.confirm) {
            flash.message = "The passwords you entered do not match."
            render(view: "register")
        }
        else {
        try{
            customerService.createNewCustomer(customerInstance)
            flash.message = message(code: 'default.registered.message', args: [message(code: 'customer.label', default: 'Customer'), customerInstance.id])
            redirect(action: "list", controller: "listing")
        }catch(Exception e){
            render(view: "register", model: [customerInstance: customerInstance])
        }
    }

    }

    //SRV-4: Refactor your Controllers from the previous assignments to use the service methods created in this section (unit test)
    def save() {
        def customerInstance = new Customer(params)
        print params
        try{
            customerService.createNewCustomer(customerInstance)
            flash.message = message(code: 'default.created.message', args: [message(code: 'customer.label', default: 'Customer'), customerInstance.id])
            redirect(action: "show", id: customerInstance.id)
        }catch(Exception e){
            render(view: "create", model: [customerInstance: customerInstance])
        }
    }

    def show() {
        def customerInstance = Customer.get(params.id)
        if (!customerInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        [customerInstance: customerInstance]
    }

    def edit() {
        def customerInstance = Customer.get(params.id)
        if (!customerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        [customerInstance: customerInstance]
    }

    def update() {
        def customerInstance = Customer.get(params.id)
        if (!customerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (customerInstance.version > version) {
                customerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'customer.label', default: 'Customer')] as Object[],
                          "Another user has updated this Customer while you were editing")
                render(view: "edit", model: [customerInstance: customerInstance])
                return
            }
        }

        customerInstance.properties = params

        if (!customerInstance.save(flush: true)) {
            render(view: "edit", model: [customerInstance: customerInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'customer.label', default: 'Customer'), customerInstance.id])
        redirect(action: "show", id: customerInstance.id)
    }

    def delete() {
        def customerInstance = Customer.get(params.id)
        if (!customerInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        try {
            //C-4: An existing customer can only be deleted through the web interface if they have 0 listings and 0 bids.
            // The system will present an error message to the user if the delete cannot be performed (unit test of the
            // controller action that has this logic)
            if (((customerInstance?.biddings == null)||
                    (customerInstance?.biddings?.size() == 0)) && ((customerInstance?.listings?.size() == 0)
            ||customerInstance?.listings == null))
            {
            customerInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            }
            else
            {
                flash.message = message(code: 'default.not.deleted.message.custom', args: [message(code: 'customer.label', default: 'Customer'), params.id])
                redirect(action: "show", id: params.id)
            }
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
