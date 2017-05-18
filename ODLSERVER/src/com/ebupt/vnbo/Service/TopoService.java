package com.ebupt.vnbo.Service;

import java.net.UnknownHostException;
import java.util.HashSet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.Exception.HostTrackeException;
import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.NetTopology.Host;
import com.ebupt.vnbo.Beans.NetTopology.Node;
import com.ebupt.vnbo.Beans.NetTopology.Switch;
import com.ebupt.vnbo.Util.TopoUtil;
import com.ebupt.vnbo.Beans.NetTopology.HostTracker;
public class TopoService {
	/**
	 * get hosts
	 * @return
	 */
	public JSONObject getHost(){
		JSONObject result=new JSONObject();
		try {
			HashSet<Host> hosts=TopoUtil.get_hosts();
			result.put("status", 0);
			result.put("description", "get success");
			result.put("result",JSONArray.toJSON(hosts));
		} catch (TopoReadFailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", -1);
			result.put("description", "failed to read topology");			
		}
		finally {
			return result;
		}
		
	}
	
	public JSONObject getSwitch(){
		JSONObject result=new JSONObject();
		try {
			HashSet<Node> nodes=TopoUtil.get_switch();
			HashSet<Switch> switchs=new HashSet<>();
			for(Node node:nodes){
				Switch switch1=new Switch();
				switch1.setNode_id(node.getNode_id());
				switch1.setPorts(new HashSet<>(node.getTermination_points()));
				switchs.add(switch1);
			}
			result.put("status", 0);
			result.put("description","get success");
			result.put("result",JSONArray.toJSON(switchs));
		} catch (TopoReadFailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", -1);
			result.put("description", "failed to read topology");			
		}
		finally {
			return result;
		}
	}
	
	public JSONObject HostTracker(String srcIp,String startIp,String endIp){
		HostTracker hostTracker=new HostTracker();
		JSONObject result=new JSONObject();
		try {
			hostTracker.setSrcIp(srcIp).setIp_list(TopoUtil.getIpInRange(startIp, endIp));
			hostTracker.dicovery();
			result.put("status", 0);
			result.put("description","success");
			return result;
		} catch (UnknownHostException | HostTrackeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", -1);
			result.put("description","fail");
			return result;
		}
		
	}
	
	public   static void main(String []args) {
		TopoService topoService=new TopoService();
		System.out.println(topoService.getSwitch().toJSONString());
	}

}
