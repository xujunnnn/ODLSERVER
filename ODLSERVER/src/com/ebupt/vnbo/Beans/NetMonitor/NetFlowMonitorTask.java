package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ebupt.vnbo.Beans.Exception.TableReadException;
import com.ebupt.vnbo.Beans.Flow.Flow;
import com.ebupt.vnbo.Beans.Match.Ethernet_destination;
import com.ebupt.vnbo.Beans.Match.Ethernet_source;
import com.ebupt.vnbo.Beans.NetTopology.Node;

public class NetFlowMonitorTask implements Runnable{
	private Map<MonTag, NetStatic> netMonitorMap=new ConcurrentHashMap();
	private volatile boolean isactive;
	//private NetMonitorMap netMonitorMap=new NetMonitorMap();
	private HashSet<Node> nodes=new HashSet<Node>();
	private long interval=3000;
	private String tableid;

	public boolean isIsactive() {
		return isactive;
	}



	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
		
	}



	public HashSet<Node> getNodes() {
		return nodes;
	}



	public NetFlowMonitorTask setNodes(HashSet<Node> nodes) {
		this.nodes = nodes;
		return this;
	}



	public long getInterval() {
		return interval;
	}



	public NetFlowMonitorTask setInterval(long interval) {
		this.interval = interval;
		return this;
	}
	



	@Override
	public void run() {
		// TODO Auto-generated method stub
	//	 Map<Pair<Ethernet_source, Ethernet_destination>, Long> oldByteMap=new ConcurrentHashMap<>();
	//	 Map<Pair<Ethernet_source, Ethernet_destination>, Long> newByteMap=new ConcurrentHashMap<>();
		// TODO Auto-generated method stub			
		while(isactive){
		//读取每个接入交换机的table?
		long time1=System.currentTimeMillis();	
		for(Node node:nodes){
			TableReader tableReader=new TableReader();
			tableReader.setNode(node.getNode_id());
			tableReader.setTableid(tableid);
			//读取每条流表的static信息
			if(tableid=="5"){
			try {
				for(String id:tableReader.read().keySet()){
					//读取table中的每条流表
					Flow flow=tableReader.read().get(id);
					//是否存在inport 匹配
					MonTag monTag=new MonTag();
					if(flow.getMatch()!=null && flow.getMatch().getEthernet_Match()!=null && flow.getMatch().getIn_port()!=null){
						String in_port=flow.getMatch().getIn_port();
						//将监控标签加入该port
						
						monTag.setInport(in_port);
						//是否存在源mac
					if(flow.getMatch().getEthernet_Match().getEthernet_source()!=null){
						Ethernet_source source=flow.getMatch().getEthernet_Match().getEthernet_source();
						monTag.setSrcmac(source.getAddress());
					}
					//是否存在目的mac
					if(flow.getMatch().getEthernet_Match().getEthernet_destination()!=null){
						Ethernet_destination destination=flow.getMatch().getEthernet_Match().getEthernet_destination();
						monTag.setDestmac(destination.getAddress());
					}
					//获取当前流表的数据
					long nowbyte=flow.getFlow_Statistic().getByte_count();
					long nowpkt=flow.getFlow_Statistic().getPacket_count();
					NetStatic netStatic=new NetStatic();
					//设置监控统计项
					netStatic.setBytecount(nowbyte)
							 .setPacketcount(nowpkt);
					//判断是否是第一次读取
					if(netMonitorMap.get(monTag)!=null){
					
						long oldbyte=netMonitorMap.get(monTag).getBytecount();
						long oldpkt=netMonitorMap.get(monTag).getPacketcount();
						if(nowbyte < oldbyte){
							nowbyte=oldbyte+nowbyte;
						}
						//计算速度
						long bytespeed=(nowbyte-oldbyte)/(3000/1000);
						long pktspeed=(nowpkt-oldpkt)/(3000/1000);
						netStatic.setPacketspeed(pktspeed).setBytespeed(bytespeed);
						
					}
					this.netMonitorMap.put(monTag, netStatic);
					
					}
				}
			} catch (TableReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
			else if(tableid=="3") {
			
			//	monTag.setNode(node.getNode_id());
				
				try {
					for(String id:tableReader.read().keySet()){
						MonTag monTag=new MonTag();
						monTag.setNode(node.getNode_id());
						NetStatic netStatic=new NetStatic();
						Flow flow=tableReader.read().get(id);
						//流表是否存在inport匹配域
						if(flow.getMatch().getIn_port()!=null){
							String inport=flow.getMatch().getIn_port();	
							monTag.setInport(inport);
						}
						//流表是否存在protocol域
						if(flow.getMatch().getIp_Match()!=null && flow.getMatch().getIp_Match().getIp_protocol()!=null){
							String ipProtocol=flow.getMatch().getIp_Match().getIp_protocol();
							Protocol_Type protocol_Type=Protocol_Type.Valueof(Integer.parseInt(ipProtocol));
							monTag.setProtocol_Type(protocol_Type);
						}
						else {
							monTag.setProtocol_Type(Protocol_Type.UNKNOW);
						}
						//获取当前流表的数据
						long nowbyte=flow.getFlow_Statistic().getByte_count();
						long nowpkt=flow.getFlow_Statistic().getPacket_count();
						netStatic.setBytecount(nowbyte)
						         .setPacketcount(nowpkt);
						//判断是否是第一次读取
						if(netMonitorMap.get(monTag)!=null){
						
							long oldbyte=netMonitorMap.get(monTag).getBytecount();
							long oldpkt=netMonitorMap.get(monTag).getPacketcount();
							if(nowbyte < oldbyte){
								nowbyte=oldbyte+nowbyte;
							}
							//计算速度
							long bytespeed=(nowbyte-oldbyte)/(3000/1000);
							long pktspeed=(nowpkt-oldpkt)/(3000/1000);
							netStatic.setPacketspeed(pktspeed).setBytespeed(bytespeed);
							
						}
						//System.out.println("MonTag "+monTag.getInport()+"<<<>>>"+monTag.getProtocol_Type());
						long bytespeed=netStatic.getBytespeed();
					
						this.netMonitorMap.put(monTag, netStatic);
						//for(MonTag monTag1:netMonitorMap.keySet()){
						//	System.out.println("MonTag "+monTag1.getInport()+"  "+monTag1.getProtocol_Type());
					//	}
					}
				} catch (NumberFormatException | TableReadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		long time2=System.currentTimeMillis();
		interval=3000-(time2-time1);
		System.out.println("Monitoring");
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	}





	public Map<MonTag, NetStatic> getNetMonitorMap() {
		return netMonitorMap;
	}



	public void setNetMonitorMap(Map<MonTag, NetStatic> netMonitorMap) {
		this.netMonitorMap = netMonitorMap;
	}



	public String getTableid() {
		return tableid;
	}



	public NetFlowMonitorTask setTableid(String tableid) {
		this.tableid = tableid;
		return this;
	}
}
