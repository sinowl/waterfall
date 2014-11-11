package com.getprobe.www.waterfall.vertx.verticle;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import com.getprobe.www.waterfall.io.CommandMessage;
import com.getprobe.www.waterfall.phase.master.Master;
import com.getprobe.www.waterfall.phase.master.WorkerFactory;
import com.getprobe.www.waterfall.util.JsonMessageElements;
import com.getprobe.www.waterfall.vertx.JsonConfigElements;
import com.getprobe.www.waterfall.vertx.MessageSenderVertx;

public class MasterVerticle extends Verticle{
//    private final String PREFIX_MASTER_ADDRESS = "scope.master.";
    private final String JAVA_SURFIX = ".java";
    
    private String phaseName;
    
    public void start(){
        String phaseName;
        String address;
//        String address = PREFIX_MASTER_ADDRESS ;

        EventBus eventBus = vertx.eventBus();
        
        // A. Get config
        JsonObject config = container.config();
        if( config != null){            
            // phase name
            phaseName = setPhaseName(
                    config.getString(JsonConfigElements.MASTER.PHASE_NAME.name()) );
            //address
            address = config.getString(JsonConfigElements.MASTER.ADDRESS.name());
        }else{
            phaseName = null;
            address = null;
        }
        
        Master master = new Master(phaseName, 
                address, 
                new WorkerFactoryVertx(), 
                new MessageSenderVertx<CommandMessage>(eventBus));
        
        // B. Network config
        if(address != null){
            // register event bus
            eventBus.registerHandler(address, new MasterHandler(master));
        }
        
    }
    
    private String setPhaseName(String phaseName){
        this.phaseName = phaseName;
        return phaseName;
    }
    
    private String getPhaseName(){
        return phaseName;
    }
    
    private class MasterHandler implements Handler<Message<JsonObject>>{
        private Master master;
        
        public MasterHandler(Master master){
            this.master = master;
        }
        
        public void handle(Message<JsonObject> json) {
            String command = json.body().getString(JsonMessageElements.COMMAND.name());
            
                // kill
            if( command == JsonMessageElements.COMMAND_VALUE.KILL.name()){
                // stop master object
                master.stop();
                // stop masterverticle
                vertx.stop();
            }
            
            // TODO
            
        }
        
    }
    
    public class WorkerFactoryVertx implements WorkerFactory {
        
        public boolean createWorker(String address) {
            
            // Set config
            JsonObject config = new JsonObject();
            // address
            config.putString(JsonConfigElements.WORKER.PHASE_NAME.name(), 
                    getPhaseName());
            // phase name
            config.putString(JsonConfigElements.WORKER.ADDRESS.name(), 
                    address);
            
            // Deploy 
            String workerVerticleName = WorkerVerticle.class.getName();
            container.deployWorkerVerticle(workerVerticleName + JAVA_SURFIX, config);

            //TODO receive ack
            return true;
        }
        
        public String createWorker() {
            String address = null;
            
            createWorker(address);
            return null;
        }
        
    }
    
}
