package com.ebupt.vnbo.Beans.Qos;

import java.util.HashSet;

import com.alibaba.fastjson.JSON;
import com.ebupt.vnbo.Beans.Exception.FlowFailException;
import com.ebupt.vnbo.Beans.Exception.MeterFailException;
import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.Exception.VBridgeReadException;
import com.ebupt.vnbo.Beans.Flow.FlowId;
import com.ebupt.vnbo.Beans.NetMonitor.Protocol_Type;
import com.ebupt.vnbo.Beans.NetTopology.Host;
import com.ebupt.vnbo.Beans.VTopo.VGroup;
import com.ebupt.vnbo.Beans.VTopo.VLink;
import com.ebupt.vnbo.Beans.Vtn.Hostmc;
import com.ebupt.vnbo.Beans.Vtn.Mac_Map_Config;
import com.ebupt.vnbo.Util.FlowUtil;
import com.ebupt.vnbo.Util.TopoUtil;
import com.ebupt.vnbo.Util.VTNUtil;
/**
 * GroupQosPolicy is a special QosPolicy which you can apply it on your VirtulNetWork
 * for example,you can apply it on a set of vgroups or a set of vlinks
 * @author xu
 *
 */
public class GroupQosPolicy {
	private String Groupqos_id;
	private String drop_rate;
	private String queue_id;
	private Protocol_Type ip_Protocol;
	private String Tcp_srcPort;
	private String Tcp_destPort;
	private String Udp_srcPort;
	private String Udp_destPort;
	private HashSet<VGroup> vGroups=new HashSet<>();
	private HashSet<VLink> vLinks=new HashSet<>();
	
	public String getDrop_rate() {
		return drop_rate;
	}
	public void setDrop_rate(String drop_rate) {
		this.drop_rate = drop_rate;
	}
	public String getQueue_id() {
		return queue_id;
	}
	public void setQueue_id(String queue_id) {
		this.queue_id = queue_id;
	}
	public Protocol_Type getIp_Protocol() {
		return ip_Protocol;
	}
	public void setIp_Protocol(Protocol_Type ip_Protocol) {
		this.ip_Protocol = ip_Protocol;
	}
	public String getTcp_srcPort() {
		return Tcp_srcPort;
	}
	public void setTcp_srcPort(String tcp_srcPort) {
		Tcp_srcPort = tcp_srcPort;
	}
	public String getTcp_destPort() {
		return Tcp_destPort;
	}
	public void setTcp_destPort(String tcp_destPort) {
		Tcp_destPort = tcp_destPort;
	}
	public String getUdp_srcPort() {
		return Udp_srcPort;
	}
	public void setUdp_srcPort(String udp_srcPort) {
		Udp_srcPort = udp_srcPort;
	}
	public String getUdp_destPort() {
		return Udp_destPort;
	}
	public void setUdp_destPort(String udp_destPort) {
		Udp_destPort = udp_destPort;
	}
	public String getGroupqos_id() {
		return Groupqos_id;
	}
	public void setGroupqos_id(String groupqos_id) {
		Groupqos_id = groupqos_id;
	}

	
	
	public HashSet<VGroup> getvGroups() {
		return vGroups;
	}
	public void setvGroups(HashSet<VGroup> vGroups) {
		this.vGroups = vGroups;
	}
	public HashSet<VLink> getvLinks() {
		return vLinks;
	}
	public void setvLinks(HashSet<VLink> vLinks) {
		this.vLinks = vLinks;
	}
	public void apply() throws VBridgeReadException, TopoReadFailException, MeterFailException, FlowFailException{
		QosPolicy qosPolicy=new QosPolicy();
		qosPolicy.setDrop_rate(this.drop_rate)
				 .setQueue_id(this.queue_id)
				 .setIp_Protocol(this.ip_Protocol)
				 .setTcp_srcPort(this.Tcp_srcPort)
				 .setTcp_destPort(this.Tcp_destPort)
				 .setUdp_srcPort(this.Udp_srcPort)
				 .setUdp_destPort(this.Udp_destPort);
		if(vGroups!=null){
			for(VGroup vGroup:vGroups){
				Mac_Map_Config mac_Map_Config=VTNUtil.readMac_Map_Config(vGroup.getVtoponame(), vGroup.getGroup_id());
				for(Hostmc hostmc:mac_Map_Config.getAllowedHosts().getVlan_host_desc_list()){
					for(Hostmc hostmc2:mac_Map_Config.getAllowedHosts().getVlan_host_desc_list()){
						if(!hostmc.equals(hostmc2)){
							String srchost=hostmc.getHostName();
							String desthost=hostmc2.getHostName();
							Host srcHost=TopoUtil.get_host_from_name(srchost);
							qosPolicy.setSrchost(srchost)
									 .setDesthost(desthost)
									 .setQos_id(FlowUtil.getFlowId(srcHost.getAccess_node(), "0"));
							System.out.println(JSON.toJSON(qosPolicy));
							//qosPolicy.apply();
						}
					}
				}
			}
			
			if(vLinks!=null){
				for(VLink vLink:vLinks){
					Mac_Map_Config mac_Map_ConfigA=VTNUtil.readMac_Map_Config(vLink.getVtoponame(), vLink.getGroupA());
					Mac_Map_Config mac_Map_ConfigB=VTNUtil.readMac_Map_Config(vLink.getVtoponame(), vLink.getGroupB());
					for(Hostmc hostmcA:mac_Map_ConfigA.getAllowedHosts().getVlan_host_desc_list()){
						for(Hostmc hostmcB:mac_Map_ConfigB.getAllowedHosts().getVlan_host_desc_list()){
							 
						}
					}
					
				}
			
			}
		}
		
	}
	//
	public static void main(String []args) throws VBridgeReadException, TopoReadFailException, MeterFailException, FlowFailException{
		GroupQosPolicy groupQosPolicy=new GroupQosPolicy();
		groupQosPolicy.setDrop_rate("100");
		groupQosPolicy.setIp_Protocol(Protocol_Type.TCP);
		groupQosPolicy.setGroupqos_id("1");
		HashSet<VGroup> vGroups=new HashSet<>();
		VGroup vGroup1=new VGroup().setGroup_id("g1").setVtoponame("vtopo10");
		VGroup vGroup2=new VGroup().setGroup_id("g2").setVtoponame("vtopo10");
		vGroups.add(vGroup1);
		vGroups.add(vGroup2);
		groupQosPolicy.setvGroups(vGroups);
		groupQosPolicy.apply();
	}

}
