package com.getprobe.www.scope.phase.worker;

import com.getprobe.www.scope.io.CommandMessage;
import com.getprobe.www.scope.io.ResultMessage;
import com.getprobe.www.scope.phase.process.Process;

public interface WorkerInterface {
    
    // Status
    /**
     * 
     * */
    public void stop();
    
    public void initialize();
    
    public void receive(CommandMessage command);
    
    // Logic
    public void receive(ResultMessage<Number> result);
    
    public void launch(Process process);
    
}
