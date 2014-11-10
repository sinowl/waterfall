package com.getprobe.www.scope.phase.worker;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.getprobe.www.scope.io.CommandMessage;
import com.getprobe.www.scope.io.ResultMessage;
import com.getprobe.www.scope.phase.process.Process;

public class Worker implements WorkerInterface{
    private static final String SURFIX_PROCESS_CLASS = "Process";
    
    private String address;
    private Process process;
    
    // Constructor
    public Worker(String address, String phaseName) throws IOException {
        this(address, getProcess(phaseName));
    }
    
    public Worker(String address, Process process){
        this.address = address;
        this.process = process;
    }

    // Status
    public String getAddress(){
        return this.address;
    }
    
    public void stop() {
        // TODO Auto-generated method stub
        
    }

    public void initialize() {
        // TODO Auto-generated method stub
        
    }

    public void receive(CommandMessage commandMessage) {
        String command = commandMessage.getCommand();
        //TODO
    }

    // Logic 
    public void receive(ResultMessage<Number> result) {
        String key = result.getKey();
        Number value = result.getResult();
        
        Process process;
        if( (process = getProcess()) != null ){
            process.run(key, value);
        }
    }

    public void launch(Process process) {
        this.process = process;
    }    
    
    public void launch(String phaseName) throws IOException{
        launch(getProcess(phaseName));
    }
    
    public Process getProcess(){
        return this.process;
    }
    
    /**
     * @throws IOException does not exist
     * */
    public static Process getProcess(String phaseName) throws IOException{
        try {
            return getProcess(phaseName, null);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public static Process getProcess(String phaseName, String jsonConf) throws IOException, 
    NoSuchMethodException, SecurityException, 
    InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Class<?> processClass;
        try {
            processClass = Class.forName(SURFIX_PROCESS_CLASS + phaseName);
            
        } catch (ClassNotFoundException e) {
            throw new IOException();
        }
        
        Constructor<?> processConstructor = processClass.getConstructor(String.class);
        
        return (Process) processConstructor.newInstance(jsonConf);
    }
    
    
}
