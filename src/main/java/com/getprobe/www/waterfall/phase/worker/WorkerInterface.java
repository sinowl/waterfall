package com.getprobe.www.waterfall.phase.worker;

import com.getprobe.www.waterfall.io.CommandMessage;
import com.getprobe.www.waterfall.io.ResultMessage;
import com.getprobe.www.waterfall.phase.process.Process;

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
