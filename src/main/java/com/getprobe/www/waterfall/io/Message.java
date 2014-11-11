package com.getprobe.www.waterfall.io;

import org.vertx.java.core.json.JsonObject;

public interface Message {
    
    /**
     * @return <code>null</code> if is outsourced message
     * */
    public String getStartAddress();
    
    /**
     * @return <code>null</code> if is all-passing message
     * */
    public String getDestAddress();

    /**
     * @return convert message to json object type
     * */
    public JsonObject toJson();
    
}
