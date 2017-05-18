package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.Match.Ethernet_destination;
import com.ebupt.vnbo.Beans.Match.Ethernet_source;
import com.ebupt.vnbo.Beans.NetTopology.Termination_point;
import com.ebupt.vnbo.Beans.NetTopology.Topology;
import com.ebupt.vnbo.Util.TopoUtil;

/**
 * 
 * @author xu
 *
 */
public class HostMonitor {
	private static boolean isactive=false;
	private static HashSet<Termination_point> ports;
	private static HostMonitorTask hostMonitorTask=new HostMonitorTask();
	private static Thread thread=new Thread(hostMonitorTask); 
	public static void begin() throws TopoReadFailException{
		isactive=true;
		if(isactive=true){
		Topology topology=new Topology().update();
	    ports=TopoUtil.get_ports_to_switch();
		hostMonitorTask.setNodes(TopoUtil.get_access_node());
		thread.start();
		}		
	}
	/**
	 * get the dataMap
	 * @return
	 */
	public static Map<Triple<String, Ethernet_source, Ethernet_destination>, Pair<Long, Long>> getHostSpeedMap() {
		return hostMonitorTask.getHostSpeedMap();
	}
	/**
	 * get local switch data
	 * @return
	 */
	public static Map<Triple<String, Ethernet_source, Ethernet_destination>, Pair<Long,Long>> getLocalMap(){
		Map<Triple<String, Ethernet_source, Ethernet_destination>, Pair<Long,Long>> allDataMap=hostMonitorTask.getHostSpeedMap();
		Map<Triple<String, Ethernet_source, Ethernet_destination>, Pair<Long,Long>> LocalDataMap=new HashMap<Triple<String,Ethernet_source,Ethernet_destination>, Pair<Long,Long>>();
		
		HashSet<String> portnames=new HashSet<String>();
		for(Termination_point port:ports){
			portnames.add(port.getTp_id());
		}
		
		for(Triple<String, Ethernet_source, Ethernet_destination> t:allDataMap.keySet()){						
			if(!portnames.contains(t.getLeft())){
				LocalDataMap.put(t, allDataMap.get(t));
				
			}
		}
		return LocalDataMap;
		
	}
	
	public static void stop(){
		isactive=false;
		thread.interrupt();
	}
	
	public boolean getStats(){
		return isactive;
	}
}
