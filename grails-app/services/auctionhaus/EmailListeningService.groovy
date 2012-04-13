package auctionhaus

import grails.plugin.jms.Queue
import groovy.json.JsonSlurper

class EmailListeningService {

    static exposes = ['jms']

    @Queue
    def onMessage(message) {
        //TBD Listen to message and parse
        def title = message
        def slurper = new JsonSlurper()
        def result = slurper.parseText(message)
def description = result."description".text()
        def messagek = description
        return messagek.toString();

        //Thread.sleep(10*1000);
    }
}
