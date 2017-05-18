package com.ebupt.vnbo.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.HashSet;
import java.util.Set;

import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.NetTopology.Host;
import com.ebupt.vnbo.Beans.NetTopology.Link;
import com.ebupt.vnbo.Beans.NetTopology.Node;
import com.ebupt.vnbo.Beans.NetTopology.Termination_point;
import com.ebupt.vnbo.Beans.NetTopology.Topology;

public class TopoUtil {
	private static Topology topology=new Topology();
/**
 * get the access switch
 * @return
 * @throws TopoReadFailException
 */
	public static HashSet<Node> get_access_node() throws TopoReadFailException{
			topology.update();
			HashSet<Node> Accessnodes=new HashSet<>();
			HashSet<String> AccessStringnodes=new HashSet<String>();
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
		 * @throws TopoReadFailException 
		 */
		public static HashSet<Link> get_inner_link() throws TopoReadFailException{
			topology.update();
			HashSet<Link> inner_links=new HashSet<>();
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
		 * @throws TopoReadFailException 
		 */
		public static HashSet<Node> get_switch() throws TopoReadFailException{
			topology.update();
			HashSet<Node> switches=new HashSet<>();
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
		 * @throws TopoReadFailException 
		 */
		public static HashSet<Termination_point> get_ports_to_host() throws TopoReadFailException{
			
			HashSet<Termination_point>  ports_to_host=new HashSet<>();
			HashSet<Host> hosts=TopoUtil.get_hosts();
			for(Host host:hosts){
				Termination_point termination_point=new Termination_point();
				termination_point.setTp_id(host.getAccess_port());
				ports_to_host.add(termination_point);
			}
			return ports_to_host;
		}
		/**
		 * get ports linked with the switch
		 * @return
		 * @throws TopoReadFailException 
		 */
		public static HashSet<Termination_point> get_ports_to_switch() throws TopoReadFailException{
			HashSet<Termination_point> ports_to_switch=new HashSet<>();
			for(Link link:TopoUtil.get_inner_link()){
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
		 * @throws TopoReadFailException 
		 */
		public static HashSet<Termination_point> get_ports_to_host(Node thenode) throws TopoReadFailException{
			HashSet<Termination_point>  ports_to_host=new HashSet<>();
			HashSet<Host> hosts=TopoUtil.get_hosts(thenode);
			for(Host host:hosts){
				Termination_point termination_point=new Termination_point();
				termination_point.setTp_id(host.getAccess_port());
				ports_to_host.add(termination_point);
			}
			return ports_to_host;
			
			
		}
		/**
		 * get  all hosts
		 * @return
		 * @throws TopoReadFailException 
		 */
	    public static HashSet<Host> get_hosts() throws TopoReadFailException{
	    	topology.update();
	    	HashSet<Host> hosts=new HashSet<Host>();
	    	for(Node node:topology.getNodes()){
	    		if(node.getNode_id().startsWith("host")){
	    			Host host=new Host();
	    			String port=node.getHost_tracker_service_attachment_points().get(0).getTp_id();
	    			String []portinfo=port.split(":");
	    			String nodename=portinfo[0]+":"+portinfo[1];
	    			host.setMac(node.getHost_tracker_service_addresses().get(0).getMac())
	    				.setIp(node.getHost_tracker_service_addresses().get(0).getIp())
	    				.setAccess_node(nodename)
	    				.setAccess_port(port)
	    				.setHost_name(node.getNode_id());
	    			hosts.add(host);
	    		}
	    	}
	    	return hosts;
	    }
	    /**
	     * get host connected to this node
	     * @param node
	     * @return
	     * @throws TopoReadFailException 
	     */
	    public static HashSet<Host> get_hosts(Node thenode) throws TopoReadFailException{
	    	topology.update();
	    	HashSet<Host> hosts=new HashSet<Host>();
	    	for(Node node:topology.getNodes()){
	    		if(node.getNode_id().startsWith("host")){
	    			Host host=new Host();
	    			String port=node.getHost_tracker_service_attachment_points().get(0).getTp_id();
	    			String []portinfo=port.split(":");
	    			String nodename=portinfo[0]+":"+portinfo[1];
	    			host.setMac(node.getHost_tracker_service_addresses().get(0).getMac())
	    				.setIp(node.getHost_tracker_service_addresses().get(0).getIp())
	    				.setAccess_node(nodename)
	    				.setAccess_port(port)
	    				.setHost_name(node.getNode_id());
	    			if(thenode.getNode_id().equals(nodename))
	    			hosts.add(host);
	    		}
	    	}
	    	return hosts;
	    }
	    /**
	     * get host with the hostname
	     * @param hostA
	     * @return
	     * @throws TopoReadFailException 
	     */
	  public static Host get_host_from_name(String hostA) throws TopoReadFailException{
		  topology.update();
	    	Host host=new Host();
	    	for(Node node:topology.getNodes()){
	    		if(node.getNode_id().equals(hostA)){
	    			String port=node.getHost_tracker_service_attachment_points().get(0).getTp_id();
	    			String []portinfo=port.split(":");
	    			String nodename=portinfo[0]+":"+portinfo[1];
	    			host.setMac(node.getHost_tracker_service_addresses().get(0).getMac())
	    				.setIp(node.getHost_tracker_service_addresses().get(0).getIp())
	    				.setAccess_node(nodename)
	    				.setAccess_port(port)
	    				.setHost_name(node.getNode_id());
	    			break;	    		
	    		}
	    	}
	    	return host;
	  }
	  /**
	   * 将byte[]转换为int
	   * @param bytes
	   * @return
	   */
		public static int BytesToInt(byte[] bytes){
			return ByteBuffer.wrap(bytes).getInt();
		}
		/**
		 * 将int 转化为Byte[]
		 * @param i
		 * @return
		 */
		public static byte[] IntToBytes(int i){
			byte[] bytes=new byte[4];
			 bytes[3]=(byte)(i & 0xff);
			 bytes[2]=(byte)((i>>8) & 0xff);
			 bytes[1]=(byte)((i>>16) & 0xff);
			 bytes[0]=(byte)((i>>24) & 0xff);
			 return bytes;
		}
		/**
		 * 获取指定范围的iP
		 *
		 * @param starts
		 * @param ends
		 * @return
		 * @throws UnknownHostException
		 */
		public static HashSet<String> getIpInRange(String starts,String ends) throws UnknownHostException{
				HashSet< String> ipList=new HashSet<>();		
				InetAddress start=InetAddress.getByName(starts);
				InetAddress end=InetAddress.getByName(ends);
				int srcAdd=BytesToInt(start.getAddress());
				int destAdd=BytesToInt(end.getAddress());
				for(int i=srcAdd;i<destAdd;i++){
				InetAddress inetAddress=InetAddress.getByAddress(IntToBytes(i));
				ipList.add(inetAddress.getHostAddress());			
				}
				return ipList;
		}
}
