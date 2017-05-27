package com.ebupt.vnbo.classes.monitor;

import com.ebupt.vnbo.classes.exception.ODL_IO_Exception;
import com.ebupt.vnbo.util.InfluxDBUtil;

public class LatencyMonitorTask implements Runnable{
	private Latency_list latency_list=new Latency_list();
	private static final String LATENCYMEASUREMENT="latency_load";
	private static boolean isactive;
	public static boolean isIsactive() {
		return isactive;
	}

	public LatencyMonitorTask setIsactive(boolean isactive) {
		LatencyMonitorTask.isactive = isactive;
		return this;
	}

	private long interval;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long timestamp=System.currentTimeMillis();	
		while(isactive){
			long time1=System.currentTimeMillis();	
	
			try {
				latency_list=latency_list.read(null);
			} catch (ODL_IO_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long time2=System.currentTimeMillis();
			interval=3000-(time2-time1);
			InfluxDBUtil.putLatency(timestamp, LATENCYMEASUREMENT, latency_list);
			timestamp+=3000;
			System.out.println("latency monitoring");
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void main(String []args){
		LatencyMonitorTask latencyMonitorTask=new LatencyMonitorTask();
		latencyMonitorTask.setIsactive(true);
		Thread thread=new Thread(latencyMonitorTask);
		thread.start();
	}

}
