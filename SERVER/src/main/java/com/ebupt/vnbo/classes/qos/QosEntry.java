package com.ebupt.vnbo.classes.qos;

import java.util.HashSet;

import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.classes.abstracts.Config;
import com.ebupt.vnbo.classes.abstracts.Operational;
import com.ebupt.vnbo.classes.enums.Protocol_Type;
import com.ebupt.vnbo.classes.exception.ConfigException;
import com.ebupt.vnbo.classes.exception.OperationalException;
import com.ebupt.vnbo.classes.flow.FlowEntry;
import com.ebupt.vnbo.classes.flow.action.Action;
import com.ebupt.vnbo.classes.flow.instruction.Apply_Actions;
import com.ebupt.vnbo.classes.flow.instruction.Instruction;
import com.ebupt.vnbo.classes.flow.instruction.Instructions;
import com.ebupt.vnbo.classes.flow.match.Match;
import com.ebupt.vnbo.classes.meter.MeterEntry;
import com.ebupt.vnbo.classes.topology.Host;
import com.ebupt.vnbo.classes.topology.Node;
import com.ebupt.vnbo.service.topology.TopologyService;
import com.ebupt.vnbo.serviceImpl.topology.TopologyServiceImpl;
public class QosEntry implements Config,Operational {
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
	
	public String getQos_id() {
		return qos_id;
	}
	public QosEntry setQos_id(String qos_id) {
		this.qos_id = qos_id;
		return this;
	}
	public String getDrop_rate() {
		return drop_rate;
	}
	public QosEntry setDrop_rate(String drop_rate) {
		this.drop_rate = drop_rate;
		return this;
	}
	public String getQueue_id() {
		return queue_id;
	}
	public QosEntry setQueue_id(String queue_id) {
		this.queue_id = queue_id;
		return this;
	}
	public String getSrchost() {
		return Srchost;
	}
	public QosEntry setSrchost(String srchost) {
		Srchost = srchost;
		return this;
	}
	public String getDesthost() {
		return Desthost;
	}
	public QosEntry setDesthost(String desthost) {
		Desthost = desthost;
		return this;
	}
	public Protocol_Type getIp_Protocol() {
		return ip_Protocol;
	}
	public QosEntry setIp_Protocol(Protocol_Type ip_Protocol) {
		this.ip_Protocol = ip_Protocol;
		return this;
	}
	public String getTcp_srcPort() {
		return Tcp_srcPort;
	}
	public QosEntry setTcp_srcPort(String tcp_srcPort) {
		Tcp_srcPort = tcp_srcPort;
		return this;
	}
	public String getTcp_destPort() {
		return Tcp_destPort;
	}
	public QosEntry setTcp_destPort(String tcp_destPort) {
		Tcp_destPort = tcp_destPort;
		return this;
	}
	public String getUdp_srcPort() {
		return Udp_srcPort;
	}
	public QosEntry setUdp_srcPort(String udp_srcPort) {
		Udp_srcPort = udp_srcPort;
		return this;
	}
	public String getUdp_destPort() {
		return Udp_destPort;
	}
	public QosEntry setUdp_destPort(String udp_destPort) {
		Udp_destPort = udp_destPort;
		return this;
	}
	
	
	/**
	 * 获取节点
	 * @return
	 * @throws TopoReadFailException
	 */
	@JSONField(deserialize=true)
	private HashSet<String> getNodes() throws OperationalException{
		
		TopologyService topoService=new TopologyServiceImpl();
		HashSet<String> nodes=new HashSet<String>();
		if(this.Srchost!=null && this.Desthost!=null){
			Host Srchost=topoService.get_host_from_name(this.Srchost);
			Host Desthost=topoService.get_host_from_name(this.Desthost);
		    nodes.add(Srchost.getAccess_node());
		    nodes.add(Desthost.getAccess_node());
		}
		else if(this.Srchost==null && this.Desthost!=null){
			Host Desthost=topoService.get_host_from_name(this.Desthost);
			nodes.add(Desthost.getAccess_node());
		}
		else if(this.Srchost!=null && this.Desthost==null){
			Host Srchost=topoService.get_host_from_name(this.Srchost);
			nodes.add(Srchost.getAccess_node());
		}
		else{
			for(Node n:topoService.get_access_node()){
				nodes.add(n.getNode_id());
			}
		}
		return nodes;
		
	}
	
	
	public Operational read(String node) throws OperationalException {
		// TODO Auto-generated method stub
		return null;
	}

	public void send(String Node) throws ConfigException, OperationalException{
		// TODO Auto-generated method stub
		TopologyService topoService=new TopologyServiceImpl();
		HashSet<String> nodes=new HashSet<String>();
		Match match=new Match();
		if(this.Srchost!=null && this.Desthost!=null){
			Host Srchost=topoService.get_host_from_name(this.Srchost);
			Host Desthost=topoService.get_host_from_name(this.Desthost);
			match.Set_Mac_Match(Srchost.getMac(), Desthost.getMac(), "2048");
		}
		else if(this.Srchost==null && this.Desthost!=null){
			Host Desthost=topoService.get_host_from_name(this.Desthost);
			match.Set_Mac_Match(null, Desthost.getMac(), "2048");
		}
		else if(this.Srchost!=null && this.Desthost==null){
			Host Srchost=topoService.get_host_from_name(this.Srchost);
			match.Set_Mac_Match(Srchost.getMac(), null, "2048");

		}
		else{
			match.Set_Mac_Match( null, null, "2048");
		}
		for(String node:this.getNodes()){
			FlowEntry flow=new FlowEntry();
			Instructions instructions=new Instructions();
			int Inorder=0;
			//Qos中包含限速信息
			if(drop_rate!=null){
				MeterEntry meter=new MeterEntry();
				meter.setMeter_id(qos_id)
				.setMeter_name("qos"+this.getQos_id())
				.Set_drop_rate(drop_rate)
				.setContainer_name(meter.getMeter_name())
				.send(node);
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
			instruction.Set_Go_To_Table_Id(MONFLOWTABLE).setOrder(String.valueOf(Inorder++));
			instructions.addInstruction(instruction);		
				flow.setId(this.qos_id)
					.setFlow_name("qosflow"+this.qos_id)
					.setCookie(this.qos_id)
					.setTable_id(QOSFLOWTABLE)
					.setPriority(MIDPRIORITY)
					.setHard_timeout(HARD_TIME_OUT)
					.setIdle_timeout(IDLE_TIME_OUT)
					.setMatch(match)
					.setInstructions(instructions)
					.send(node);
			}	
		
	}

	public void remove(String Node) throws ConfigException, OperationalException {
		// TODO Auto-generated method stub
		for(String node:this.getNodes()){
			MeterEntry meter=new MeterEntry();
			meter.setMeter_id(qos_id).remove(node);
			FlowEntry flow=new FlowEntry();
				flow.setId(this.qos_id).setTable_id(QOSFLOWTABLE).remove(node);;		
			}	
		
	}

}
