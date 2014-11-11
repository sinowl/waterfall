package com.getprobe.www.waterfall.vertx;

import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.json.JsonObject;

import com.getprobe.www.waterfall.phase.MessageSender;
import com.getprobe.www.waterfall.io.Message;

public class MessageSenderVertx <T extends Message> implements MessageSender<T> {
    private EventBus eventBus;
    
    public MessageSenderVertx(EventBus eventBus) {
        this.eventBus = eventBus;
    }
    
    public boolean send(String address, T message) {
        JsonObject messageJson = message.toJson();
        
        getEventBus().send(address, messageJson);
        
        //TODO receive ack and return true
        
        return true;
    }
    
    private EventBus getEventBus(){
        return this.eventBus;
    }
    
    
}
