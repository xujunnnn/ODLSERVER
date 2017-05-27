package com.ebupt.vnbo.classes.monitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.influxdb.InfluxDB;

import com.ebupt.vnbo.classes.enums.Protocol_Type;
import com.ebupt.vnbo.classes.exception.ODL_IO_Exception;
import com.ebupt.vnbo.classes.exception.OperationalException;
import com.ebupt.vnbo.classes.flow.FlowEntry;
import com.ebupt.vnbo.classes.flow.match.Ethernet_destination;
import com.ebupt.vnbo.classes.flow.match.Ethernet_source;
import com.ebupt.vnbo.classes.table.TableRead;
import com.ebupt.vnbo.classes.topology.Node;
import com.ebupt.vnbo.serviceImpl.topology.TopologyServiceImpl;
import com.ebupt.vnbo.util.InfluxDBUtil;

public class IpMonitorTask implements Runnable{
	private static final String IPTABLE="5";
	private static final String IPMEASUREMENT="ip_load";
	private volatile boolean isactive;
	//private NetMonitorMap netMonitorMap=new NetMonitorMap();
	private HashSet<Node> nodes=new HashSet<Node>();
	private long interval=3000;
	private String tableid;
	public boolean isIsactive() {
		return isactive;
	}
	public IpMonitorTask setIsactive(boolean isactive) {
		this.isactive = isactive;
		return this;
	}
	public HashSet<Node> getNodes() {
		return nodes;
	}
	public IpMonitorTask setNodes(HashSet<Node> nodes) {
		this.nodes = nodes;
		return this;
	}
	public long getInterval() {
		return interval;
	}
	public IpMonitorTask setInterval(long interval) {
		this.interval = interval;
		return this;
	}
	public String getTableid() {
		return tableid;
	}
	public IpMonitorTask setTableid(String tableid) {
		this.tableid = tableid;
		return this;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub			
		long time1=System.currentTimeMillis();	

		while(true){
	
			Map<MonTag, NetStatic> netMonitorMap=new HashMap<>();
		//读取每个接入交换机的table?
			long time=System.currentTimeMillis();			
			long timestamp=System.currentTimeMillis();	
		for(Node node:nodes){
			TableRead tableRead=new TableRead();
			tableRead.setTableid(IPTABLE);
			//读取每条流表的static信息
			try {
				for(FlowEntry flowEntry:tableRead.read(node.getNode_id()).getFlow()){
					//读取table中的每条流表
					//是否存在inport 匹配
					MonTag monTag=new MonTag().setTimestamp(time1);
					if(flowEntry.getMatch()!=null && flowEntry.getMatch().getEthernet_Match()!=null && flowEntry.getMatch().getIn_port()!=null){
						String in_port=flowEntry.getMatch().getIn_port();
						//将监控标签加入该port		
						monTag.setInport(in_port);
					}
					else{
						continue;
					}
						//是否存在源mac
					if(flowEntry.getMatch()!=null && flowEntry.getMatch().getEthernet_Match()!=null&&flowEntry.getMatch().getEthernet_Match().getEthernet_source()!=null){
						Ethernet_source source=flowEntry.getMatch().getEthernet_Match().getEthernet_source();
						monTag.setSrcmac(source.getAddress());
					}
					//是否存在目的mac
					if(flowEntry.getMatch()!=null && flowEntry.getMatch().getEthernet_Match()!=null && flowEntry.getMatch().getEthernet_Match().getEthernet_destination()!=null){
						Ethernet_destination destination=flowEntry.getMatch().getEthernet_Match().getEthernet_destination();
						monTag.setDestmac(destination.getAddress());
					}
					//是否存在协议字段
					if(flowEntry.getMatch().getIp_Match()!=null && flowEntry.getMatch().getIp_Match().getIp_protocol()!=null){
						String ipProtocol=flowEntry.getMatch().getIp_Match().getIp_protocol();
						Protocol_Type protocol_Type=Protocol_Type.Valueof(Integer.parseInt(ipProtocol));
						monTag.setProtocol_Type(protocol_Type);
					}
					NetStatic netStatic=new NetStatic();
					//获取当前流表的数据
					long nowbyte=flowEntry.getFlow_Statistic().getByte_count();
					long nowpkt=flowEntry.getFlow_Statistic().getPacket_count();
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
					//设置监控统计项			
					netStatic.setBytecount(nowbyte).setPacketcount(nowpkt);
					//将数据存入缓存
					netMonitorMap.put(monTag, netStatic);	
					}
			} catch (NumberFormatException | ODL_IO_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		  }
		InfluxDBUtil.put(timestamp,IPMEASUREMENT,netMonitorMap);
		long time2=System.currentTimeMillis();
		interval=3000-(time2-time);
		System.out.println("Monitoring");
		timestamp+=3000;
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
		}
	
	public static void main(String []args){
		Thread thread;
		try {
			thread = new Thread(new IpMonitorTask().setIsactive(true).setNodes(new TopologyServiceImpl().get_access_node()));
			thread.start();
		} catch (OperationalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}

		
		

		


