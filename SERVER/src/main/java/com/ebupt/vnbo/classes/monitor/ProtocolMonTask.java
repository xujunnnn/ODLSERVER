package com.ebupt.vnbo.classes.monitor;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



import com.ebupt.vnbo.classes.enums.Protocol_Type;
import com.ebupt.vnbo.classes.exception.ODL_IO_Exception;
import com.ebupt.vnbo.classes.exception.OperationalException;
import com.ebupt.vnbo.classes.flow.FlowEntry;
import com.ebupt.vnbo.classes.table.TableRead;
import com.ebupt.vnbo.classes.topology.Node;
import com.ebupt.vnbo.serviceImpl.topology.TopologyServiceImpl;
import com.ebupt.vnbo.util.InfluxDBUtil;
public class ProtocolMonTask implements Runnable {
	private static final String PROTOCOLTABLE="3";
	private static final String measurement="protocol_load";
	private Map<MonTag, NetStatic> netMonitorMap=new ConcurrentHashMap();
	private volatile boolean isactive;
	//private NetMonitorMap netMonitorMap=new NetMonitorMap();
	private HashSet<Node> nodes=new HashSet<Node>();
	private long interval=3000;
	private String tableid;
	public Map<MonTag, NetStatic> getNetMonitorMap() {
		return netMonitorMap;
	}
	public void setNetMonitorMap(Map<MonTag, NetStatic> netMonitorMap) {
		this.netMonitorMap = netMonitorMap;
	}
	public boolean isIsactive() {
		return isactive;
	}
	public ProtocolMonTask setIsactive(boolean isactive) {
		this.isactive = isactive;
		return this;
	}
	public HashSet<Node> getNodes() {
		return nodes;
	}
	public ProtocolMonTask setNodes(HashSet<Node> nodes) {
		this.nodes = nodes;
		return this;
	}
	public long getInterval() {
		return interval;
	}
	public void setInterval(long interval) {
		this.interval = interval;
	}
	public String getTableid() {
		return tableid;
	}
	public void setTableid(String tableid) {
		this.tableid = tableid;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

		while(isactive){
			//读取每个接入交换机的table?
			long time1=System.currentTimeMillis();	
			long timestamp=System.currentTimeMillis();	
			for(Node node:nodes){
			   //设置tableReader
				TableRead tableRead=new TableRead();		
				tableRead.setTableid(PROTOCOLTABLE);
				//读取table中的所有流表
				try {
					tableRead=tableRead.read(node.getNode_id());
				} catch (ODL_IO_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(FlowEntry flowEntry:tableRead.getFlow()){
					MonTag monTag=new MonTag();
					//解析流表的匹配域
					String inport;
					//存在Inport
					if((inport=flowEntry.getMatch().getIn_port())!=null){
						monTag.setInport(inport);
					}
					//存在Protocol
					if(flowEntry.getMatch().getIp_Match()!=null && flowEntry.getMatch().getIp_Match().getIp_protocol()!=null){
						String ipProtocol=flowEntry.getMatch().getIp_Match().getIp_protocol();
						Protocol_Type protocol_Type=Protocol_Type.Valueof(Integer.parseInt(ipProtocol));
						monTag.setProtocol_Type(protocol_Type);
					}
					//将协议设置为unknown
					else {
						monTag.setInport("no");
						monTag.setProtocol_Type(Protocol_Type.UNKNOW);
					}
					long nowbyte=flowEntry.getFlow_Statistic().getByte_count();
					long nowpkt=flowEntry.getFlow_Statistic().getPacket_count();
					NetStatic netStatic=new NetStatic();
					netStatic.setBytecount(nowbyte).setPacketcount(nowpkt);	
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
					else {
						//将速度设置为0
						netStatic.setPacketspeed(0).setBytespeed(0);
					}
					//存入缓存
					this.netMonitorMap.put(monTag, netStatic);
				}
					
			}
			//将本次统计的数据置入数据库
			InfluxDBUtil.put(timestamp,measurement, netMonitorMap);
			timestamp=timestamp+3000;
			long time2=System.currentTimeMillis();
			//计算时延
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
	public static void main(String []args){
		Thread thread;
		try {
			thread = new Thread(new ProtocolMonTask().setIsactive(true).setNodes(new TopologyServiceImpl().get_access_node()));
			thread.start();
		} catch (OperationalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
