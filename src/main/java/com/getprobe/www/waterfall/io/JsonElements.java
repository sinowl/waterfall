package com.getprobe.www.scope.io;

public enum JsonElements {
    TYPE;
    
    public enum TYPE_VALUE{
        COMMAND_MESSAGE, RESULT_MESSAGE
    }
    
    // Command
    public enum Command {
        STARTING_ADDRESS, DESTINATION_ADDRESS, COMMAND;
    }

    // Result
    public enum Result {
        KEY, RESULT, ADDRESS_STACK;
    }
    
}
