package com.ebupt.vnbo.Beans.Qos;

import java.util.HashSet;
import javax.print.attribute.ResolutionSyntax;
import javax.print.attribute.standard.NumberOfDocuments;

import com.alibaba.fastjson.JSON;
import com.ebupt.vnbo.Beans.Action.Action;
import com.ebupt.vnbo.Beans.Exception.FlowFailException;
import com.ebupt.vnbo.Beans.Exception.MeterFailException;
import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.Flow.Flow;
import com.ebupt.vnbo.Beans.Instruction.Apply_Actions;
import com.ebupt.vnbo.Beans.Instruction.Instruction;
import com.ebupt.vnbo.Beans.Instruction.Instructions;
import com.ebupt.vnbo.Beans.Match.Match;
import com.ebupt.vnbo.Beans.Meter.Meter;
import com.ebupt.vnbo.Beans.NetMonitor.Protocol_Type;
import com.ebupt.vnbo.Beans.NetTopology.Host;
import com.ebupt.vnbo.Beans.NetTopology.Node;
import com.ebupt.vnbo.Util.TopoUtil;
import com.sun.org.apache.regexp.internal.REUtil;
/**
 * 
 * @author xu
 *
 */
public class QosPolicy {
	private String qos_id;
	private String drop_rate;
	private String queue_id;
	private String Srchost;
	private String Desthost;
	private Protocol_Type ip_Protocol;
	private String Tcp_srcPort;
	private String Tcp_destPort;
	private String Udp_srcPort;
	private String Udp_destPort;
	public String getTcp_srcPort() {
		return Tcp_srcPort;
	}
	public QosPolicy setTcp_srcPort(String tcp_srcPort) {
		Tcp_srcPort = tcp_srcPort;
		return this;
	}
	public String getTcp_destPort() {
		return Tcp_destPort;
	}
	public QosPolicy setTcp_destPort(String tcp_destPort) {
		Tcp_destPort = tcp_destPort;
		return this;
	}
	public String getUdp_srcPort() {
		return Udp_srcPort;
	}
	public QosPolicy setUdp_srcPort(String udp_srcPort) {
		Udp_srcPort = udp_srcPort;
		return this;
	}
	
	private String flowid;
	public String getSrchost() {
		return Srchost;
	}
	public QosPolicy setSrchost(String Srchost) {
		this.Srchost = Srchost;
		return this;
	}
	public String getFlowid() {
		return flowid;
	}
	public QosPolicy setFlowid(String flowid) {
		this.flowid = flowid;
		return this;
	}
	public String getDesthost() {
		return Desthost;
	}
	public QosPolicy setDesthost(String Desthost) {
		this.Desthost = Desthost;
		return this;
	}

	public String getUdp_destPort() {
		return Udp_destPort;
	}
	public QosPolicy setUdp_destPort(String udp_destPort) {
		Udp_destPort = udp_destPort;
		return this;
	}
	public String getQos_id() {
		return qos_id;
	}
	public QosPolicy setQos_id(String qos_id) {
		this.qos_id = qos_id;
		return this;
	}
	public String getDrop_rate() {
		return drop_rate;
	}
	public QosPolicy setDrop_rate(String drop_rate) {
		this.drop_rate = drop_rate;
		return this;
	}
	public String getQueue_id() {
		return queue_id;
	}
	public QosPolicy setQueue_id(String queue_id) {
		this.queue_id = queue_id;
		return this;
	}	
	public Protocol_Type getIp_Protocol() {
		return ip_Protocol;
	}
	public QosPolicy setIp_Protocol(Protocol_Type ip_Protocol) {
		this.ip_Protocol = ip_Protocol;
		return this;
	}
	/**
	 * apply the qospolicy
	 * @throws MeterFailException
	 * @throws FlowFailException
	 * @throws TopoReadFailException 
	 */
	public void apply() throws MeterFailException, FlowFailException, TopoReadFailException{
		//if the match contains host information,add the meter and flow
		//to the access node 
		HashSet<String> nodes=new HashSet<>();
		Match match=new Match();
		if(this.Srchost!=null && this.Desthost!=null){
			Host Srchost=TopoUtil.get_host_from_name(this.Srchost);
			Host Desthost=TopoUtil.get_host_from_name(this.Desthost);
			match.Set_Mac_Match(Srchost.getMac(), Desthost.getMac(), "2048");
		    nodes.add(Srchost.getAccess_node());
		    nodes.add(Desthost.getAccess_node());
		}
		else if(this.Srchost==null && this.Desthost!=null){
			Host Desthost=TopoUtil.get_host_from_name(this.Desthost);
			match.Set_Mac_Match(null, Desthost.getMac(), "2048");
			nodes.add(Desthost.getAccess_node());
		}
		else if(this.Srchost!=null && this.Desthost==null){
			Host Srchost=TopoUtil.get_host_from_name(this.Srchost);
			match.Set_Mac_Match(Srchost.getMac(), null, "2048");
			nodes.add(Srchost.getAccess_node());
		}
		else{
			match.Set_Mac_Match( null, null, "2048");
			for(Node n:TopoUtil.get_access_node()){
				nodes.add(n.getNode_id());
			}
		}
		
		if(this.ip_Protocol!=null){
			match.Set_Ip_Match(String.valueOf(ip_Protocol.value()), null, null, null);
		}
		
		if(this.Tcp_srcPort!=null){
			match.Set_Ip_Match(String.valueOf(Protocol_Type.TCP.value()), null, null, null);
			match.setTcp_source_port(this.Tcp_srcPort);
		}
		if(this.Tcp_destPort!=null){
			match.Set_Ip_Match(String.valueOf(Protocol_Type.TCP.value()), null, null, null);
			match.setTcp_destination_port(this.Tcp_destPort);
		}
		if(this.Udp_srcPort!=null){
			match.Set_Ip_Match(String.valueOf(Protocol_Type.UDP.value()), null, null, null);
			match.setUdp_source_port(this.Udp_srcPort);			
		}
		if(this.Udp_destPort!=null){
			match.Set_Ip_Match(String.valueOf(Protocol_Type.UDP.value()), null, null, null);
			match.setUdp_destination_port(this.Udp_destPort);			
		}
		for(String node:nodes){
			Flow flow=new Flow();
			Instructions instructions=new Instructions();
			int Inorder=0;
			//Qos中包含限速信息
			if(drop_rate!=null){
				Meter meter=new Meter();
				meter.setMeter_id(qos_id)
				.setMeter_name("qos"+this.getQos_id())
				.Set_drop_rate(drop_rate)
				.setContainer_name(meter.getMeter_name())
				.Send(node);
				Instruction instruction2=new Instruction();
				instruction2.Set_Meter(meter.getMeter_id()).setOrder(String.valueOf(Inorder++));
				instructions.addInstruction(instruction2);
			}
			if(queue_id!=null){
				Instruction queueinstruction=new Instruction();
				Apply_Actions actions=new Apply_Actions();
				Action action=new Action().Set_Queue_Id(queue_id);
				actions.addAction(action);
				queueinstruction.setApply_Actions(actions)
												  .setOrder(String.valueOf(Inorder++));
				instructions.addInstruction(queueinstruction);
			}
			//go_to_table 3 instruction
		Instruction instruction=new Instruction();	
		instruction.Set_Go_To_Table_Id("3").setOrder(String.valueOf(Inorder++));
		instructions.addInstruction(instruction);		
			flow.setId(this.qos_id)
				.setFlow_name("qosflow"+this.qos_id)
				.setCookie(this.qos_id)
				.setTable_id("0")
				.setPriority("210")
				.setHard_timeout("0")
				.setIdle_timeout("0")
				.setMatch(match)
				.setInstructions(instructions)
				.Send(node);
		}	
	
	  }
	
	public void delete() throws FlowFailException, MeterFailException, TopoReadFailException{
		HashSet<String> nodes=new HashSet<>();
		if(this.Srchost!=null && this.Desthost!=null){
			Host Srchost=TopoUtil.get_host_from_name(this.Srchost);
			Host Desthost=TopoUtil.get_host_from_name(this.Desthost);
		    nodes.add(Srchost.getAccess_node());
		    nodes.add(Desthost.getAccess_node());
		}
		else if(this.Srchost==null && this.Desthost!=null){
			Host Desthost=TopoUtil.get_host_from_name(this.Desthost);
			nodes.add(Desthost.getAccess_node());
		}
		else if(this.Srchost!=null && this.Desthost==null){
			Host Srchost=TopoUtil.get_host_from_name(this.Srchost);
			nodes.add(Srchost.getAccess_node());
		}
		else{
			for(Node n:TopoUtil.get_access_node()){
				nodes.add(n.getNode_id());
			}
		}
		
		for(String node:nodes){
		Meter meter=new Meter();
		meter.setMeter_id(qos_id).Delete(node);
		Flow flow=new Flow();
			flow.setId(this.qos_id).setTable_id("0").Delete(node);		
		}	
	
		
	}
	public static void main(String[] args){
		QosPolicy qosPolicy=new QosPolicy();
		qosPolicy.setSrchost("host:c2:bb:1f:c7:8c:54").setDesthost("host:9e:73:ae:0a:ba:4f").setIp_Protocol(Protocol_Type.UDP).setDrop_rate("200").setQos_id("26")
		.setUdp_destPort("120");
		System.out.println(JSON.toJSON(qosPolicy));
	}
}
