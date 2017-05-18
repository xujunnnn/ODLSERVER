package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.HashSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.ebupt.vnbo.Beans.Flow.Flow;
import com.ebupt.vnbo.Beans.Match.Ethernet_destination;
import com.ebupt.vnbo.Beans.Match.Ethernet_source;
import com.ebupt.vnbo.Beans.NetTopology.Node;
/**
 * 
 * @author xu
 *
 */
public class HostMonitorTask implements Runnable{
	private Map<Triple<String, Ethernet_source, Ethernet_destination>, Pair<Long, Long>> HostByteMap=new ConcurrentHashMap<>();
	private Map<Triple<String, Ethernet_source, Ethernet_destination>, Pair<Long, Long>> HostSpeedMap=new ConcurrentHashMap<>();
	  private HashSet<Node> nodes=new HashSet<Node>();
	public Map<Triple<String, Ethernet_source, Ethernet_destination>,Pair<Long, Long>> getHostByteMap() {
		return HostByteMap;
	}



	public void setHostByteMap(
			Map<Triple<String, Ethernet_source, Ethernet_destination>, Pair<Long, Long>> hostByteMap) {
		HostByteMap = hostByteMap;
	}



	public Map<Triple<String, Ethernet_source, Ethernet_destination>, Pair<Long, Long>> getHostSpeedMap() {
		return HostSpeedMap;
	}



	public void setHostSpeedMap(
			Map<Triple<String, Ethernet_source, Ethernet_destination>, Pair<Long, Long>> hostSpeedMap) {
		HostSpeedMap = hostSpeedMap;
	}



	private long interval=3000;
	

	
	


	public HashSet<Node> getNodes() {
		return nodes;
	}



	public void setNodes(HashSet<Node> nodes) {
		this.nodes = nodes;
	}



	public long getInterval() {
		return interval;
	}



	public void setInterval(long interval) {
		this.interval = interval;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
	//	 Map<Pair<Ethernet_source, Ethernet_destination>, Long> oldByteMap=new ConcurrentHashMap<>();
	//	 Map<Pair<Ethernet_source, Ethernet_destination>, Long> newByteMap=new ConcurrentHashMap<>();
		// TODO Auto-generated method stub
		while(!Thread.interrupted()){
			
		for(Node node:nodes){

			TableReader tableReader=new TableReader();
			tableReader.setNode(node.getNode_id());
			tableReader.setTableid("5");
			for(String id:tableReader.read().keySet()){
				
				Flow flow=tableReader.read().get(id);
				if(flow.getMatch()!=null && flow.getMatch().getEthernet_Match()!=null && flow.getMatch().getIn_port()!=null){
					String in_port=flow.getMatch().getIn_port();
					Triple<String, Ethernet_source, Ethernet_destination> triple=new Triple<String, Ethernet_source, Ethernet_destination>();
					triple.setLeft(in_port);
				if(flow.getMatch().getEthernet_Match().getEthernet_source()!=null){
					Ethernet_source source=flow.getMatch().getEthernet_Match().getEthernet_source();
					triple.setMid(source);
				}
				if(flow.getMatch().getEthernet_Match().getEthernet_destination()!=null){
					Ethernet_destination destination=flow.getMatch().getEthernet_Match().getEthernet_destination();
					triple.setRight(destination);
				}
				long nowbyte=flow.getFlow_Statistic().getByte_count();
				long nowpkt=flow.getFlow_Statistic().getPacket_count();
				Pair<Long, Long> sumpair=new Pair<Long, Long>();
				sumpair.setLeft(nowpkt);
				sumpair.setRight(nowbyte);
				if(HostByteMap.get(triple)!=null){
				
					long oldbyte=HostByteMap.get(triple).getRight();
					long oldpkt=HostByteMap.get(triple).getLeft();
					if(nowbyte < oldbyte){
						nowbyte=oldbyte+nowbyte;
					}
					long bytespeed=(nowbyte-oldbyte)/(interval/1000);
					long pktspeed=(nowpkt-oldpkt)/(interval/1000);
					Pair<Long, Long> speedpair=new Pair<Long, Long>();
					speedpair.setLeft(pktspeed);
					speedpair.setRight(bytespeed);
					this.HostSpeedMap.put(triple, speedpair);
				}
				this.HostByteMap.put(triple,sumpair);
				
				}
			}
			
		}
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		for(Node node:nodes){

			TableReader tableReader=new TableReader();
			tableReader.setNode(node.getNode_id());
			tableReader.setTableid("5");
			for(String id:tableReader.read().keySet()){
				Flow flow=tableReader.read().get(id);
				if(flow.getMatch()!=null && flow.getMatch().getEthernet_Match()!=null){
				Pair<Ethernet_source, Ethernet_destination> pair=new Pair<Ethernet_source, Ethernet_destination>();
				if(flow.getMatch().getEthernet_Match().getEthernet_source()!=null){
					Ethernet_source source=flow.getMatch().getEthernet_Match().getEthernet_source();
					pair.setLeft(source);
				}
				if(flow.getMatch().getEthernet_Match().getEthernet_destination()!=null){
					Ethernet_destination destination=flow.getMatch().getEthernet_Match().getEthernet_destination();
					pair.setRight(destination);
				}
				long oldbyte=oldByteMap.get(pair);
				
				long newbyte=flow.getFlow_Statistic().getByte_count();
				long speed=(newbyte-oldbyte)/(interval/1000);
				newByteMap.put(pair, newbyte);
				this.HostByteMap.put(pair, newbyte);
				this.HostSpeedMap.put(pair, speed);
			}
			}
			
		}
		*/
	  }
	}
	
	
}
