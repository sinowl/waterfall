package com.getprobe.www.scope.vertx.verticle;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import com.getprobe.www.scope.io.JsonElements;
import com.getprobe.www.scope.io.JsonParsor;
import com.getprobe.www.scope.io.ResultMessage;
import com.getprobe.www.scope.phase.worker.Worker;
import com.getprobe.www.scope.vertx.JsonConfigElements;

public class WorkerVerticle extends Verticle{
    
    public void start(){
        
        EventBus eventBus = vertx.eventBus(); 
    
        // Get Configuration
        JsonObject confJson = container.config();
        //PHASE_NAME 
        final String phaseName = confJson.getString(JsonConfigElements.WORKER.PHASE_NAME.name());
        //ADDRESS
        final String address = confJson.getString(JsonConfigElements.WORKER.ADDRESS.name());
        
        
        // Launch
        Worker worker = null;
        try {
            worker = new Worker(address, phaseName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ;
        }
        
        // Network Configuration
        if(worker.getAddress() != null){
            eventBus.registerHandler(worker.getAddress(),
                    new WorkerHandler(worker));
        }
        
    }
    
    private class WorkerHandler implements Handler<Message<JsonObject>>{
        private Worker worker;
        
        public WorkerHandler(Worker worker){
            this.worker = worker;
        }
        
        public void handle(Message<JsonObject> message) {
            JsonObject messageJson = message.body();
            
            //check the message type e.g. command, result
            String type = messageJson.getString(JsonElements.TYPE.name());
            if( type == JsonElements.TYPE_VALUE.COMMAND_MESSAGE.name()){
                // command message
                try {
                    worker.receive(JsonParsor.parseResultFromJson(messageJson));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else if(type == JsonElements.TYPE_VALUE.RESULT_MESSAGE.name()){
                // result message
                try {
                    worker.receive(JsonParsor.parseResultFromJson(messageJson));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                try {
                    throw new IOException("Invalid message type");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            //TODO parse systemitic messages e.g. ack
            
        }
        
    } 
    
}
