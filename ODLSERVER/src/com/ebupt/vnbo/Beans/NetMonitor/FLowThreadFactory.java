package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.concurrent.ThreadFactory;

public class FLowThreadFactory implements ThreadFactory{
    private int counter;
    private String name;
    public FLowThreadFactory(){
    	counter=0;
    	this.name=name;
    	
    }
	@Override
	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		Thread t=new Thread(r,name+"_thread_"+counter);
		counter++;
		return t;
	}

}
