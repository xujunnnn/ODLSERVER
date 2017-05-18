package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ProtoMonTest {
	public static void main(String [] args) throws InterruptedException{
		ProtocolMonitor protocolMonitor=new ProtocolMonitor();
		ArrayList<String> nodes=new ArrayList<>();
		nodes.add("openflow:1");
		nodes.add("openflow:2");
		protocolMonitor.setNodes(nodes);
		protocolMonitor.setInterval(3000);
		Thread thread=new Thread(protocolMonitor);
		ScheduledThreadPoolExecutor executor=(ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
		ThreadFactory flowFactory=new FLowThreadFactory();
		
		 executor.scheduleAtFixedRate(protocolMonitor, 0, 3, TimeUnit.SECONDS);	 	
		
		for(int i=0;i<100;i++){
		for(String s:protocolMonitor.getProto_Speed_Map().keySet()){
			if(protocolMonitor.getProto_Speed_Map().get(s)!=null)
			System.out.println(s+"   "+protocolMonitor.getProto_Speed_Map().get(s));
		}
		Thread.sleep(3000);
		}
	}

}
