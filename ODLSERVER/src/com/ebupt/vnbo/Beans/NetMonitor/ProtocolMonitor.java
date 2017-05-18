package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ebupt.vnbo.Beans.Exception.TableReadException;
import com.ebupt.vnbo.Beans.Flow.Flow;

/**
 * 
 * @author xu
 *
 */

public class ProtocolMonitor implements Runnable {
	private ArrayList<String> nodes=new ArrayList<>();
	private long interval;
	private Map<String, Long> Proto_Speed_Map=new HashMap<>();
	private Map<String, Long> Proto_byte_Map=new HashMap<>();
	
	public void run(){
		
		Map<String, Long> oldMap=new ConcurrentHashMap<>();
		for(String node:nodes){
			TableReader tableReader=new TableReader();
			tableReader.setNode(node).setTableid("0");
			Map<String, Flow> flowmap;
			try {
				flowmap = tableReader.read();
				for(String id:flowmap.keySet()){
					//System.out.println(oldMap.get(id));
					if(oldMap.get(id)==null){
						oldMap.put(id, (long) 0);
					}
					oldMap.put(id, oldMap.get(id)+flowmap.get(id).getFlow_Statistic().getByte_count());
				}	
			} catch (TableReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Long> NowMap=new ConcurrentHashMap<>();
		for(String node:nodes){
			TableReader tableReader=new TableReader();
			tableReader.setNode(node).setTableid("0");
			Map<String, Flow> flowmap;
			try {
				flowmap = tableReader.read();
			} catch (TableReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			for(String id:flowmap.keySet()){
				if(NowMap.get(id)==null){
					NowMap.put(id, (long) 0);
				}
				//System.out.println("Bytecount  "+NowMap.get(id)+flowmap.get(id).getFlow_Statistic().getByte_count());
				NowMap.put(id, NowMap.get(id)+flowmap.get(id).getFlow_Statistic().getByte_count());
			}
		
	}
		for(String id:NowMap.keySet()){
			long speed=(NowMap.get(id)-oldMap.get(id))/(interval/1000);
			Proto_Speed_Map.put(id, speed);
			Proto_byte_Map.put(id, NowMap.get(id));
		}
	

}

	public ArrayList<String> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<String> nodes) {
		this.nodes = nodes;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public Map<String, Long> getProto_Speed_Map() {
		return Proto_Speed_Map;
	}

	public void setProto_Speed_Map(Map<String, Long> proto_Speed_Map) {
		Proto_Speed_Map = proto_Speed_Map;
	}

	public Map<String, Long> getProto_byte_Map() {
		return Proto_byte_Map;
	}

	public void setProto_byte_Map(Map<String, Long> proto_byte_Map) {
		Proto_byte_Map = proto_byte_Map;
	}
}

