package auctionhaus

class AuthTagLib {
    def loginControl = {
        if(session.user){
            out << "Hello ${session.user.getCustomerName()} "
            out << """[${link(action:"logout", controller:"customer"){"Logout"}}]"""
            out << """[${link(action:"mylisting", controller:"listing"){"My Listing"}}]"""
        } else {
            out << """[${link(action:"login", controller:"customer"){"Login"}}]"""
            out << """[${link(action:"register", controller:"customer"){"Register"}}]"""
        }
    }
}
