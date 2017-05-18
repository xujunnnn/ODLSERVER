package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ebupt.vnbo.Beans.Flow.Flow;
import com.ebupt.vnbo.Beans.NetTopology.Node;



public class ProtocolPortMonitorTask implements Runnable {
	private Map<Pair<String, Protocol_Type>, Pair<Long, Long>> NetByteMap=new ConcurrentHashMap<>();
	private Map<Pair<String,Protocol_Type>, Pair<Long, Long>> NetSpeedMap=new ConcurrentHashMap<>();
	private HashSet<Node> nodes=new HashSet<>();
	private static long interval=3000;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!Thread.interrupted()){
			
		for(Node node:this.getNodes()){

			TableReader tableReader=new TableReader();
			tableReader.setNode(node.getNode_id());
			tableReader.setTableid("0");
			for(String id:tableReader.read().keySet()){
				Flow flow=tableReader.read().get(id);
				if(flow.getMatch().getIn_port()!=null){
					String in_port=flow.getMatch().getIn_port();
				
					if(flow.getMatch().getIp_Match()!=null){
						String proto_id=flow.getMatch().getIp_Match().getIp_protocol();
						Protocol_Type protocol_Type=Protocol_Type.Valueof(Integer.parseInt(proto_id));
						Pair<String, Protocol_Type> pair=new Pair<>();
						pair.setLeft(in_port);
						pair.setRight(protocol_Type);
						long bytecount=flow.getFlow_Statistic().getByte_count();
						long pktcount=flow.getFlow_Statistic().getPacket_count();
						if(NetByteMap.get(pair)!=null){
						long oldpkt=NetByteMap.get(pair).getLeft();	
						long oldbyte=NetByteMap.get(pair).getRight();						
						long bytespeed=(bytecount-oldbyte)/(interval/1000);
						long pktspeed=(pktcount-oldpkt)/(interval/1000);
						Pair<Long, Long> speedpair=new Pair<Long, Long>();
						speedpair.setLeft(pktspeed);
						speedpair.setRight(bytespeed);
						NetSpeedMap.put(pair,speedpair);
						}
						Pair<Long, Long> sumpair=new Pair<Long, Long>();
						sumpair.setLeft(pktcount);
						sumpair.setRight(bytecount);
						NetByteMap.put(pair,sumpair);
						
					}
				}
			}
			
		}
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	  }
		
	}
	
	


	public Map<Pair<String, Protocol_Type>, Pair<Long, Long>> getNetByteMap() {
		return NetByteMap;
	}




	public void setNetByteMap(
			Map<Pair<String, Protocol_Type>, Pair<Long, Long>> netByteMap) {
		NetByteMap = netByteMap;
	}




	public Map<Pair<String, Protocol_Type>, Pair<Long, Long>> getNetSpeedMap() {
		return NetSpeedMap;
	}




	public void setNetSpeedMap(
			Map<Pair<String, Protocol_Type>, Pair<Long, Long>> netSpeedMap) {
		NetSpeedMap = netSpeedMap;
	}




	public HashSet<Node> getNodes() {
		return nodes;
	}


	public void setNodes(HashSet<Node> nodes) {
		this.nodes = nodes;
	}


	public static long getInterval() {
		return interval;
	}

	
}
