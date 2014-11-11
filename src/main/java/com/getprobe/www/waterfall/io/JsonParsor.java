package com.getprobe.www.waterfall.io;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.vertx.java.core.json.JsonObject;


public class JsonParsor {
    
    public JsonParsor() {
        // TODO Auto-generated constructor stub
    }
    
    // Parse to json
    //  ResultMessage
    public static ResultMessage<Number> parseResultFromJson(String jsonString) throws IOException{

        return parseResultFromJson(new JsonObject(jsonString));
    }
    
    public static ResultMessage<Number> parseResultFromJson(JsonObject jsonObject) 
            throws IOException{
        
        String key = jsonObject.getString(JsonElements.Result.KEY.name());
        Number result = jsonObject.getNumber(JsonElements.Result.RESULT.name());
        Deque<String> addressStack;
        
        List<String> addressList = new ArrayList<String>();
        for(Object address : jsonObject.getArray(JsonElements.Result.ADDRESS_STACK.name()) ){
            addressList.add(address.toString());
        }
        addressStack = new ArrayDeque<String>(addressList);
        
        if(key == null ){
            throw new IOException();
        }else if(result == null){
            throw new IOException();
        }
        
        return new ResultMessage<Number>(key, result, addressStack);
    }
    
    //  CommandMessage
    /**
     * @throws IOException 
     * */
    public static CommandMessage parseCommandFromJson(String jsonString) throws IOException{
        
        return parseCommandFromJson(new JsonObject(jsonString));
    }
    
    public static CommandMessage parseCommandFromJson(JsonObject jsonObject) throws IOException{
        String startAddress = jsonObject.getString(JsonElements.Command.STARTING_ADDRESS.name());
        String destAddress = jsonObject.getString(JsonElements.Command.DESTINATION_ADDRESS.name());
        String command = jsonObject.getString(JsonElements.Command.COMMAND.name());
        
        return new CommandMessage(startAddress, destAddress, command);
    }
}
