package com.ebupt.vnbo.Beans.Flow;

import java.util.HashSet;

import com.ebupt.vnbo.Beans.Exception.FlowFailException;
import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.Instruction.Instruction;
import com.ebupt.vnbo.Beans.Instruction.Instructions;
import com.ebupt.vnbo.Beans.Match.Match;
import com.ebupt.vnbo.Beans.NetMonitor.Protocol_Type;
import com.ebupt.vnbo.Beans.NetTopology.Node;
import com.ebupt.vnbo.Beans.NetTopology.Termination_point;
import com.ebupt.vnbo.Beans.NetTopology.Topology;
import com.ebupt.vnbo.Util.HttpUtil;
import com.ebupt.vnbo.Util.TopoUtil;
import com.ebupt.vnbo.Util.Util;

import java.util.HashSet;

public class Initialize {
    private static String ODL_IP=Util.getODL_IP(); 
    private HashSet<Node> nodes=new HashSet<Node>();
    private HashSet<Protocol_Type> protocol_Types=new HashSet<>();
    public HashSet<Protocol_Type> getProtocol_Types() {
		return protocol_Types;
	}


	public void setProtocol_Types(HashSet<Protocol_Type> protocol_Types2) {
		this.protocol_Types = protocol_Types2;
	}



	private static int id=1;
    
    public String getid(){
    	String flowid=String.valueOf(id);
    	id++;
    	return flowid;
    }
    
    
    /**
     * 
     * @return
     */
    public Initialize addProtocol(Protocol_Type protocol_Type){
    	this.protocol_Types.add(protocol_Type);
    	return this;
    }
    
    public HashSet<Node> getNodes() {
		return nodes;
	}
	public Initialize setNodes(HashSet<Node> nodes) {
		this.nodes = nodes;
		return this;
	}
	public Initialize addNode(Node node){
		this.nodes.add(node);
		return this;
	}
	/**
	 * delete the table from the node
	 * @param node
	 * @param tableid
	 * @return
	 */
	public  String[] delete_Table(String node,String tableid){
		String url="http://"+ODL_IP+"/restconf/config/opendaylight-inventory:nodes/node/"+node+"/flow-node-inventory:table/"+tableid;
		
		System.out.println(url);
		return HttpUtil.Delete_request(url);
		
	}
	/**
	 * send initflow to the node
	 * @param node
	 * @throws FlowFailException 
	 */
    public void initFlow(Node node) throws FlowFailException{
    	Flow flow=new Flow();
    	Match match=new Match();
    	Instructions instructions=new Instructions();
    	Instruction instruction=new Instruction().Set_Go_To_Table_Id("3").setOrder("0");
    	instructions.addInstruction(instruction);
    	/*
    	 * send flow gototable 3
    	 */
    	flow.setId("0")
    		.setFlow_name("initflow"+flow.getId())
    		.setCookie(flow.getId())
    		.setHard_timeout("0")
    		.setIdle_timeout("0")
    		.setPriority("200")
    		.setTable_id("0")
    		.setMatch(match)
    		.setInstructions(instructions)
    		.Send(node.getNode_id());
    	Instructions instructions2=new Instructions();
    	Instruction instruction2=new Instruction().Set_Go_To_Table_Id("5").setOrder("0");
    	instructions2.addInstruction(instruction2);
    	/*
    	 * send flow gototable 5
    	 */
    	flow.setId("0")
    		.setFlow_name("initflow"+flow.getId())
    		.setCookie(flow.getId())
    		.setInstructions(instructions2)
    		.setTable_id("3")
    		.setHard_timeout("0")
    		.setIdle_timeout("0")
    		.setPriority("200")
    		.setMatch(match)
    		.Send(node.getNode_id());
    	
    	/*
    	  for(Protocol_Type p:protocol_Types){
    		System.out.println(p);
    	for(Termination_point t:topology.get_ports_to_host(node)){
    		
    			System.out.println(p);
    		Flow flow2=new Flow();
    		System.out.println("protocol-type      "+String.valueOf(p.value()));
    		Match match2=new Match().Set_Ip_Match(String.valueOf(p.value()), null, null,null).setIn_phy_port(t.getTp_id());
    		
    		flow2.setId(getid())
    			 .setFlow_name("initflow"+flow.getId())
    			 .setCookie(flow.getId())
    			 .setIdle_timeout("0")
    			 .setHard_timeout("0")
    			 .setPriority("210")
    			 .setTable_id("0")
    			 .setInstructions(instructions)
    			 .setMatch(match2)
    			 .Send(node.getNode_id());
    			 
    		}		 
    	}
    	
    		*/
    	
    		
    }
   	
    public void initMonitorFlow(Node node) throws FlowFailException, TopoReadFailException{
    	Instructions instructions=new Instructions();
    	Instruction instruction=new Instruction().Set_Go_To_Table_Id("5").setOrder("0");
    	instructions.addInstruction(instruction);
    	HashSet<Termination_point> termination_points=TopoUtil.get_ports_to_host(node);
    	for(Termination_point t:termination_points){
    		System.out.println(t.getTp_id());
    		for(Protocol_Type p:protocol_Types){
    			Flow flow2=new Flow();
        		Match match2=new Match().Set_Ip_Match(String.valueOf(p.value()), null, null,null)
        								.setIn_port(t.getTp_id())
        								.Set_Mac_Match(null, null, "2048");
        		flow2.setId(getid())
        			 .setFlow_name("initflow"+flow2.getId())
        			 .setCookie(flow2.getId())
        			 .setIdle_timeout("0")
        			 .setHard_timeout("0")
        			 .setPriority("210")
        			 .setTable_id("3")
        			 .setInstructions(instructions)
        			 .setMatch(match2)
        			 .Send(node.getNode_id());
    		}
    	}
    }
   
    
    /**
     * Init the switch
     * @throws FlowFailException 
     * @throws TopoReadFailException 
     */
	public void init() throws FlowFailException, TopoReadFailException {
		// TODO Auto-generated method stub
		Topology topology=new Topology().update();
		for(Node n:TopoUtil.get_switch()){
			System.out.println(n.getNode_id());
			//delete_Table(n.getNode_id(), "0");
			//delete_Table(n.getNode_id(), "3");
			
			initFlow(n);
		}
		for(Node n:TopoUtil.get_access_node()){
			initMonitorFlow(n);
			
		}
	}
	

}
