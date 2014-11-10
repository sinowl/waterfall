package com.getprobe.www.scope.phase.master;

import java.util.Map;
import java.util.Set;

import com.getprobe.www.scope.io.CommandMessage;

public interface MasterInterface{
    
    // Monitor Resources
    
    
    // Manage Workers
    /**
     * @return address of a worker created soon
     * */
    public String createWorker();
    
    /**
     * 
     * */
    public boolean askAlive(String address);
    
    /**
     * Stop and clear all workers.
     * @return address set of workers which throws exceptions
     * */
    public Set<String> stopWorkers();
    
    /**
     * Stop a specific worker.
     * */
    public boolean stopWorker(String address);
        
    /***/
    public Set<String> getWorkerAddresses();
    
    /**
     * @return key: <code>missing</code>, <code>updating</code>
     * */
    public Map<String, String> updateWorkerAddresses();
    
    //Network
    /**
     * Receive message
     * */
    public void receive(CommandMessage message);
    
    //TODO methods for networking/coordinating slave workers
    
}
