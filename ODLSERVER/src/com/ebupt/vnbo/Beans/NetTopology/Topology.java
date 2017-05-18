package com.ebupt.vnbo.Beans.NetTopology;

import java.util.HashSet;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Util.HttpUtil;
import com.ebupt.vnbo.Util.Util;

/**
 * 
 * @author xu
 *
 */
public class Topology {
	private static final String ODL_IP=Util.getODL_IP();
	@JSONField(name="topology-id")
	private String topology_id;
	@JSONField(name="node")
	public HashSet<Node> nodes=new HashSet<>();
	@JSONField(name="link")
	public HashSet<Link> links=new HashSet<>();
	
	
	public String getTopology_id() {
	
		return topology_id;
	}
	public void setTopology_id(String topology_id) {
		this.topology_id = topology_id;
	}
	public HashSet<Node> getNodes() {
		return nodes;
	}
	public void setNodes(HashSet<Node> nodes) {
		
		this.nodes = nodes;
	}
	public HashSet<Link> getLinks() {
		return links;
	}
	public void setLinks(HashSet<Link> links) {
		this.links = links;
	}
	/**
	 * get the topology information
	 * @return
	 * @throws TopoReadFailException 
	 */
	public Topology update() throws TopoReadFailException{
		String url="http://"+ODL_IP+"/restconf/operational/network-topology:network-topology";
		String []result=HttpUtil.Get_request(url);
		String responsecode=result[0];
		String json=result[1];
		if(!"200".equals(responsecode) && !"201".equals(responsecode))
			throw new TopoReadFailException("failed to read the topologu from the controller");
		//System.out.println(json);
		JSONObject jsonObject=JSONObject.parseObject(json);
		JSONObject topojson=jsonObject.getJSONObject("network-topology").getJSONArray("topology").getJSONObject(0);
		Topology topology=JSONObject.parseObject(topojson.toJSONString(), Topology.class);
		this.nodes=topology.nodes;
		this.links=topology.links;
		return this;
	}
	/**
	 * get the access switch 
	 * @return
	 */
	/*
	public HashSet<Node> get_access_node(){
		HashSet<Node> Accessnodes=new HashSet<>();
		HashSet<String> AccessStringnodes=new HashSet<String>();
		Topology topology=this.update();
		HashSet<Node> nodes=topology.getNodes();
		for(Node node:nodes){
			if(node.getNode_id().startsWith("host")){
				String node_connector=node.getHost_tracker_service_attachment_points().get(0).getTp_id();
				String []nodeinfo=node_connector.split(":");
				String nodeid=nodeinfo[0]+":"+nodeinfo[1];
				
				
				if(!AccessStringnodes.contains(nodeid)){
					
					AccessStringnodes.add(nodeid);
					
				}
			}
			
		}
		for(String nodeid:AccessStringnodes){
			Node node2=new Node();
			node2.setNode_id(nodeid);
			Accessnodes.add(node2);
			
		}
		return Accessnodes;
	}
	/**
	 * get inner links
	 * @return
	 */
	/*
	public HashSet<Link> get_inner_link(){
		HashSet<Link> inner_links=new HashSet<>();
		Topology topology=this.update();
		for(Link link:topology.getLinks()){
				if((link.getSource().getSource_node().startsWith("openflow")) && (link.getDestination().getDest_node().startsWith("openflow"))){
				inner_links.add(link);
			}
		}
		return inner_links;
	}
	/**
	 * get node which is a openflow switch
	 * @return
	 */
	/*
	public HashSet<Node> get_switch(){
		HashSet<Node> switches=new HashSet<>();
		Topology topology=this.update();
		for(Node node:topology.getNodes()){
			if(node.getNode_id().startsWith("openflow")){
				switches.add(node);
			}
		}
		return switches;
	}
	/**
	 * get ports linked with host
	 * @return
	 */
	/*
	public HashSet<Termination_point> get_ports_to_host(){
		HashSet<Termination_point>  ports_to_host=new HashSet<>();
		HashSet<Termination_point> ports_to_switch=this.get_ports_to_switch();
		for(Node node:this.getNodes()){
			for(Termination_point termination_point:node.getTermination_points()){
				if(!ports_to_switch.contains(termination_point) && !termination_point.getTp_id().endsWith("LOCAL")){
					System.out.println(termination_point.getTp_id());
					ports_to_host.add(termination_point);
				}
			}
		}
		return ports_to_host;
	}
	/**
	 * get ports linked with the switch
	 * @return
	 */
	/*
	public HashSet<Termination_point> get_ports_to_switch(){
		HashSet<Termination_point> ports_to_switch=new HashSet<>();
		for(Link link:this.get_inner_link()){
			Termination_point termination_point=new Termination_point();
			termination_point.setTp_id(link.getSource().getSource_tp());
		    ports_to_switch.add(termination_point);
		    Termination_point termination_point2=new Termination_point();
		    termination_point2.setTp_id(link.getDestination().getDest_node());
		}
		return ports_to_switch;
	}
	/**
	 * get ports links with the host of the specified node
	 * @param thenode
	 * @return
	 */
	/*
	public HashSet<Termination_point> get_ports_to_host(Node thenode){
		HashSet<Termination_point>  ports_to_host=new HashSet<>();
		HashSet<Termination_point> ports_to_switch=this.get_ports_to_switch();
		for(Node node:this.getNodes()){
			if(node.getNode_id().equals(thenode.getNode_id())){
			for(Termination_point termination_point:node.getTermination_points()){
				if(!ports_to_switch.contains(termination_point) && !termination_point.getTp_id().endsWith("LOCAL")){
					System.out.println(termination_point.getTp_id());
					ports_to_host.add(termination_point);
				}
			}
		  }
		}
		return ports_to_host;
		
	}
	*/
}
