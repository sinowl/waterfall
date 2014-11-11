package com.getprobe.www.waterfall.phase.master;

public interface WorkerFactory {
    
    /**
     * @return <code>true</code> if run successfully
     * */
    public boolean createWorker(String address);

    /***/
    public String createWorker();
}
