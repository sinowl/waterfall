package com.getprobe.www.scope.io;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

/**
 * 
 * */

public class ResultMessage <T extends Number> implements Message{
    private String key;
    private T result; 
    private Deque<String> addressStack ;
    
    /**
     * 
     * */
    public ResultMessage() {
       this.key = null;
       this.result = null;
       this.addressStack = new ArrayDeque<String>();
    }
    
    /**
     * 
     * */
    public ResultMessage(String key){
        this();
        setKey(key);
    }
    
    /**
     * 
     * */
    public ResultMessage(String key, T result){
        setKey(key);
        setResult(result);
    }
    
    public ResultMessage(String key, T result, Deque<String> addressStack){
        this.key = key;
        this.result = result;
        this.addressStack = addressStack;
    }
    
//    public ResultMessage(JsonObject json){
//        //TODO
//        this(,);
//    }
    
    /**
     * 
     * */
    private void setKey(String key){
        this.key = key;
    }
    
    /**
     * 
     * */
    public String getKey(){
        return this.key;
    }
    
    /***/
    private void setResult(T result){
        this.result = result;
    }
    
    /***/
    public T getResult(){
        return this.result;
    }
    
    private Deque<String> getAddressStack(){
        return this.addressStack;
    }
    
    /***/
    public boolean setAddress(String address){
        getAddressStack().addFirst(address);
        return true; //TODO
    }
    
    /***/
    public String getRecentAddress(){
        return getAddressStack().peekFirst();
    }
    
    /**
     * @return 
     * */
    public List<String> getAddress(){
        return new ArrayList<String>(getAddressStack());
    }

    public String getStartAddress() {
        return null;
    }

    public String getDestAddress() {
        return null;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        
        json.putString(JsonElements.TYPE.name(), 
                JsonElements.TYPE_VALUE.RESULT_MESSAGE.name());
        // Key
        json.putString(JsonElements.Result.KEY.name(), getKey());
        // Result
        json.putNumber(JsonElements.Result.RESULT.name(), getResult());
        // AddressStack
        List<String> addressList = new ArrayList<String>(
                getAddressStack());
        JsonArray jsonAddress = new JsonArray();
        for(String address : addressList){
            jsonAddress.addString(address);
        }
        json.putArray(JsonElements.Result.ADDRESS_STACK.name(), jsonAddress);
        
        return json;
    }
    
}
