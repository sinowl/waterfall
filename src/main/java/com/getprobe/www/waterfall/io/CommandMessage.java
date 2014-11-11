package com.getprobe.www.waterfall.io;

import org.vertx.java.core.json.JsonObject;

public class CommandMessage implements Message{
    private String startAddress;
    private String destAddress;
    private String command;
    
    /**
     * Constructor
     * */
    @Deprecated
    public CommandMessage(String destAddress, String command){
        this(null, destAddress, command);
    }
    
    /**
     * Constructor
     * */
    public CommandMessage(String startAddress, String destAddress, String command) {
        this.startAddress = startAddress;
        this.destAddress = destAddress;
        this.command = command;
    }
    
    /***/
    public String getStartAddress(){
        return this.startAddress;
    }
    
    /***/
    public String getDestAddress(){
        return this.destAddress;
    }
    
    /***/
    public String getCommand(){
        return this.command;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        
        json.putString(JsonElements.TYPE.name(), 
                JsonElements.TYPE_VALUE.COMMAND_MESSAGE.name());
        json.putString(JsonElements.Command.STARTING_ADDRESS.name(), getStartAddress());
        json.putString(JsonElements.Command.DESTINATION_ADDRESS.name(), getDestAddress());
        json.putString(JsonElements.Command.COMMAND.name(), getCommand());
        
        return json;
    }
}
