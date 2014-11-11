package com.getprobe.www.waterfall.phase.master;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.getprobe.www.waterfall.io.CommandMessage;
import com.getprobe.www.waterfall.phase.MessageSender;
import com.getprobe.www.waterfall.util.KeyGenerator;

public class Master implements MasterInterface{
    private final String PREFIX_WORKER_ADDRESS = "scope.worker.";
    // Command
    private final String KILL = "KILL";
    
    private String phaseName;
    private String address;
    private WorkerFactory workerFactory;
    private MessageSender<CommandMessage> sender;
    private Set<String> workerAddresses;
    
    // Constructor
    public Master(String address,
            WorkerFactory factory,
            MessageSender<CommandMessage> sender){
        this(null, address, factory, sender);
    }
    
    public Master(String phaseName,
            String address, 
            WorkerFactory factory,
            MessageSender<CommandMessage> sender) {
        this.phaseName = phaseName;
        this.address = address;
        this.workerAddresses = new HashSet<String>();
    }

    // THIS OBJECT STATUS
    public String getPhaseName(){
        if(this.phaseName.length() > 0){
            return this.phaseName; 
        }else{
            return "";
        }
    }
    
    public String getAddress(){
        return this.address;
    }
    
    public void stop(){
        // release resources
        //TODO
    }
    
    // MANAGE WORKERS
    /**
     * @return <code>null</code> when occures failure
     * */
    public String createWorker() {
        String address;
        int numberBits = 130;
        int radix = 32;
        
        while(!workerAddresses.contains(address 
                = PREFIX_WORKER_ADDRESS 
                + getPhaseName() 
                + KeyGenerator.generateString(numberBits, radix) )){
            workerAddresses.add(address);
        }
                
        if(this.workerFactory.createWorker(address)){
            return address;
        }else{
            // remove 
            workerAddresses.remove(address);
            return null;
        }
    }
    
    public boolean askAlive(String address) {
        // TODO Auto-generated method stub
        return false;
    }
    
    public Set<String> stopWorkers() {
        Set<String> addressErrorOccured = new HashSet<String>();
        
        for(String address : getWorkerAddresses()){
            if(!stopWorker(address)){
                addressErrorOccured.add(address);
            }
        }
        
        return addressErrorOccured;
    }

    public boolean stopWorker(String address) {
        CommandMessage message = new CommandMessage(getAddress(), 
                address, 
                KILL);
        
        return this.sender.send(address, message);
    }
    
    public Set<String> getWorkerAddresses() {
        return this.workerAddresses;
    }

    public void receive(CommandMessage message) {
        //TODO
    }

    public Map<String, String> updateWorkerAddresses() {
        final String KEY_MISSING = "missing";
        final String KEY_UPDATING = "updating";
        
        Map<String, String> updatedAddress = new HashMap<String, String>();
        
        // for, KEY_MISSING
        for(String address: getWorkerAddresses()){
            if(!askAlive(address)){
                updatedAddress.put(KEY_MISSING, address);
            }
        }
        
        // TODO for, KEY_UPDATING
        
        return updatedAddress;
    }
    
    
    // NETWORK
    
}
