package auctionhaus

import org.springframework.dao.DataIntegrityViolationException

class BiddingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def beforeInterceptor = [action:this.&auth]

    def index() {
        redirect(action: "list", params: params)
    }

    def auth() {
        if( !(session?.user?.role == "admin") ){
            flash.message = "You must have an administrator privilege to perform this task."
            redirect(action:"login",controller:"customer")
            return false
        }
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [biddingInstanceList: Bidding.list(params), biddingInstanceTotal: Bidding.count()]
    }

    def create() {
        [biddingInstance: new Bidding(params)]
    }

    def save() {
        def biddingInstance = new Bidding(params)
        if (!biddingInstance.save(flush: true)) {
            render(view: "create", model: [biddingInstance: biddingInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'bidding.label', default: 'Bidding'), biddingInstance.id])
        redirect(action: "show", id: biddingInstance.id)
    }

    def show() {
        def biddingInstance = Bidding.get(params.id)
        if (!biddingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'bidding.label', default: 'Bidding'), params.id])
            redirect(action: "list")
            return
        }

        [biddingInstance: biddingInstance]
    }

    def edit() {
        def biddingInstance = Bidding.get(params.id)
        if (!biddingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bidding.label', default: 'Bidding'), params.id])
            redirect(action: "list")
            return
        }

        [biddingInstance: biddingInstance]
    }

    def update() {
        def biddingInstance = Bidding.get(params.id)
        if (!biddingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bidding.label', default: 'Bidding'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (biddingInstance.version > version) {
                biddingInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'bidding.label', default: 'Bidding')] as Object[],
                          "Another user has updated this Bidding while you were editing")
                render(view: "edit", model: [biddingInstance: biddingInstance])
                return
            }
        }

        biddingInstance.properties = params

        if (!biddingInstance.save(flush: true)) {
            render(view: "edit", model: [biddingInstance: biddingInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'bidding.label', default: 'Bidding'), biddingInstance.id])
        redirect(action: "show", id: biddingInstance.id)
    }

    def delete() {
        def biddingInstance = Bidding.get(params.id)
        if (!biddingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'bidding.label', default: 'Bidding'), params.id])
            redirect(action: "list")
            return
        }

        try {
            biddingInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'bidding.label', default: 'Bidding'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bidding.label', default: 'Bidding'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
