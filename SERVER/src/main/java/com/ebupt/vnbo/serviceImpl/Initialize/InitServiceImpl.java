package com.ebupt.vnbo.serviceImpl.Initialize;
import java.util.HashSet;

import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.classes.enums.OperationType;
import com.ebupt.vnbo.classes.enums.Protocol_Type;
import com.ebupt.vnbo.classes.enums.Tag;
import com.ebupt.vnbo.classes.exception.ConfigException;
import com.ebupt.vnbo.classes.exception.OperationalException;
import com.ebupt.vnbo.classes.flow.FlowEntry;
import com.ebupt.vnbo.classes.flow.instruction.Instruction;
import com.ebupt.vnbo.classes.flow.instruction.Instructions;
import com.ebupt.vnbo.classes.flow.match.Match;
import com.ebupt.vnbo.classes.monitor.IpMonitorTask;
import com.ebupt.vnbo.classes.monitor.LatencyMonitorTask;
import com.ebupt.vnbo.classes.monitor.ProtocolMonTask;
import com.ebupt.vnbo.classes.topology.Node;
import com.ebupt.vnbo.classes.topology.Termination_point;
import com.ebupt.vnbo.request.Request;
import com.ebupt.vnbo.request.initialize.InitRequest;
import com.ebupt.vnbo.service.initialize.InitService;
import com.ebupt.vnbo.service.topology.TopologyService;
import com.ebupt.vnbo.serviceImpl.topology.TopologyServiceImpl;
import com.ebupt.vnbo.util.FlowUtil;
public class InitServiceImpl implements InitService{
	private static Thread PROTOCOL_MON_THREAD;
	private static Thread IP_MON_THREAD;
	private static Thread LATENCY_MON_THREAD;
	
	@Override
	public JSONObject resolve(Request request) {
		// TODO Auto-generated method stub
		JSONObject result=new JSONObject();
		InitRequest initRequest=(InitRequest) request;
		if(initRequest.getOperationType()==OperationType.INIT){
			if(initRequest.getTag()==Tag.BASE)
				return initBaseFlow();
			if(initRequest.getTag()==Tag.MONITOR)
				return initMonitor();
			result.put("status", -1);
			result.put("description", "error tag");		
			return result;
		}
		result.put("status", -1);
		result.put("description", "error operationType ");		
		return result;
		
	}



	@Override
	public JSONObject initBaseFlow()  {
		// TODO Auto-generated method stub
		JSONObject result=new JSONObject();
		TopologyService topologyService=new TopologyServiceImpl();
		try {
			for(Node node:topologyService.get_switch()){
			FlowEntry flow=new FlowEntry();
			Match match=new Match();
			Instructions instructions=new Instructions();
			Instruction instruction=new Instruction().Set_Go_To_Table_Id(MONFLOWTABLE).setOrder("0");
			instructions.addInstruction(instruction);
			/*
			 * send flow gototable 3
			 */
			flow.setId("0")
				.setFlow_name("initflow"+flow.getId())
				.setCookie(flow.getId())
				.setHard_timeout("0")
				.setIdle_timeout("0")
				.setPriority(LOWPRIORITY)
				.setTable_id(QOSFLOWTABLE)
				.setMatch(match)
				.setInstructions(instructions)
				.send(node.getNode_id());
			Instructions instructions2=new Instructions();
			Instruction instruction2=new Instruction().Set_Go_To_Table_Id(VTNFLOWTABLE).setOrder("0");
			instructions2.addInstruction(instruction2);
			/*
			 * send flow gototable 5
			 */
			flow.setId("0")
				.setFlow_name("initflow"+flow.getId())
				.setCookie(flow.getId())
				.setInstructions(instructions2)
				.setTable_id(MONFLOWTABLE)
				.setHard_timeout("0")
				.setIdle_timeout("0")
				.setPriority(LOWPRIORITY)
				.setMatch(match)
				.send(node.getNode_id());
			}
			//初始化flowidMap
			FlowUtil.initMap();
			result.put("Status", 0);
			result.put("description", "success to init baseFlow");
		} catch (OperationalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("Status", -1);
			result.put("description", "init error, can not read the openflow topology");
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("Status", -1);
			result.put("description", "init error, failed to send the initflows");
		}
		finally{
			return result;
		}
		
	}
	
	
	
	@Override
	public JSONObject initMonitor(){
		// TODO Auto-generated method stub
		JSONObject result=new JSONObject();
		HashSet<Protocol_Type> protocol_Types=new HashSet<>();
		protocol_Types.add(Protocol_Type.ICMP);
		protocol_Types.add(Protocol_Type.TCP);
		protocol_Types.add(Protocol_Type.UDP);
		TopologyService topologyService=new TopologyServiceImpl();
		try {
			for(Node node:topologyService.get_switch()){
			Instructions instructions=new Instructions();
			Instruction instruction=new Instruction().Set_Go_To_Table_Id(VTNFLOWTABLE).setOrder("0");
			instructions.addInstruction(instruction);
			//获取与主机相连接的端口
			HashSet<Termination_point> termination_points=topologyService.get_ports_to_host(node);
			for(Termination_point t:termination_points){
				System.out.println(t.getTp_id());
				for(Protocol_Type p:protocol_Types){
					FlowEntry flow2=new FlowEntry();
					Match match2=new Match().Set_Ip_Match(String.valueOf(p.value()), null, null,null)
											.setIn_port(t.getTp_id())
											.Set_Mac_Match(null, null, "2048");
					flow2.setId(Long.toString(FlowUtil.getFlowId(node)))
						 .setFlow_name("initflow"+flow2.getId())
						 .setCookie(flow2.getId())
						 .setIdle_timeout("0")
						 .setHard_timeout("0")
						 .setPriority(MIDPRIORITY)
						 .setTable_id(MONFLOWTABLE)
						 .setInstructions(instructions)
						 .setMatch(match2)
						 .send(node.getNode_id());
				}
			}
 }
			/**开启监控进程*/
			ProtocolMonTask protocolMonTask=new ProtocolMonTask().setIsactive(true);
			PROTOCOL_MON_THREAD=new Thread(protocolMonTask);
			PROTOCOL_MON_THREAD.start();
			IpMonitorTask ipMonitorTask=new IpMonitorTask().setIsactive(true);
			IP_MON_THREAD=new Thread(ipMonitorTask);
			IP_MON_THREAD.start();
			LatencyMonitorTask latencyMonitorTask=new LatencyMonitorTask().setIsactive(true);
			LATENCY_MON_THREAD=new Thread(latencyMonitorTask);
			LATENCY_MON_THREAD.start();
			
			result.put("Status", 0);
			result.put("description", "success to init baseFlow");
		} catch (OperationalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("Status", -1);
			result.put("description", "init error, can not read the openflow topology");
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("Status", -1);
			result.put("description", "init error, failed to send the Monflows");
		}
		finally{
			return result;
		}
	}
	public static void main(String []args){
		InitServiceImpl impl=new InitServiceImpl();
		impl.initBaseFlow();
		impl.initMonitor();
	}



}
