package com.ebupt.vnbo.Beans.NetTopology;


import java.util.HashSet;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.Beans.Exception.HostTrackeException;
import com.ebupt.vnbo.Util.HttpUtil;
import com.ebupt.vnbo.Util.Util;
/**
 * 
 * @author xu
 *
 */
public class HostTracker {
	private static final String ODL_IP=Util.getODL_IP();
	@JSONField(name="SrcIp")
	private String SrcIp;
	@JSONField(name="ip-list")
	private HashSet<String> ip_list=new HashSet<>();
	@JSONField(name="SrcIp")
	public String getSrcIp() {
		return SrcIp;
	}
	@JSONField(name="SrcIp")
	public HostTracker setSrcIp(String SrcIp) {
		this.SrcIp = SrcIp;
		return this;
	}
	public HashSet<String> getIp_list() {
		return ip_list;
	}
	public HostTracker setIp_list(HashSet<String> ip_list) {
		this.ip_list = ip_list;
		return this;
	}
	
	public void dicovery() throws HostTrackeException{
		String url="http://"+ODL_IP+"/restconf/operations/arp-handler-impl:ActiveArp";
		JSONObject jsondata=new JSONObject();
		jsondata.put("input", JSONObject.toJSON(this));
		String []result=HttpUtil.Post_request(url, jsondata);
		String responsecode=result[0];
		if(!"200".equals(responsecode) && !"201".equals(responsecode)){
			throw new HostTrackeException("failed to send ArpPacket");
		}
	}
	
	
}
