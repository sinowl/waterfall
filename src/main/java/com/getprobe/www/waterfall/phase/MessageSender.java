package com.getprobe.www.waterfall.phase;

public interface MessageSender <T>{
    
    /**
     * @return <code> true</code> if sended successfully
     * */
    public boolean send(String address, T message);
    
}
