package com.ebupt.vnbo.Beans.Vtn;

import java.util.HashSet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.Beans.Exception.Mac_MapFailException;
import com.ebupt.vnbo.Util.HttpUtil;
import com.ebupt.vnbo.Util.Util;

public class Mac_Map {
	private static String ODL_IP=Util.getODL_IP();
	
	@JSONField(name="tenant-name")
	private String tenant_name;
	@JSONField(name="bridge-name")
	private String bridge_name;
	@JSONField(name="allowed-hosts")
	private HashSet<String> allowed_hosts;
	@JSONField(name="denied-hosts")
	private HashSet<String> denyed_Hosts;
	@JSONField(name="mac-map-config")
	private Mac_Map_Config mac_Map_Config;
	private OperationType operation;
	
	public Mac_Map_Config getMac_Map_Config() {
		return mac_Map_Config;
	}
	public void setMac_Map_Config(Mac_Map_Config mac_Map_Config) {
		this.mac_Map_Config = mac_Map_Config;
	}
	public HashSet<String> getAllowed_hosts() {
		return allowed_hosts;
	}
	public Mac_Map setAllowed_hosts(HashSet<String> allowed_hosts) {
		this.allowed_hosts = allowed_hosts;
		return this;
	}
	public HashSet<String> getDenyed_Hosts() {
		return denyed_Hosts;
	}
	public Mac_Map setDenyed_Hosts(HashSet<String> denyed_Hosts) {
		this.denyed_Hosts = denyed_Hosts;
		return this;
	}
	public String getTenant_name() {
		return tenant_name;
	}
	public Mac_Map setTenant_name(String tenant_name) {
		this.tenant_name = tenant_name;
		return this;
	}
	public String getBridge_name() {
		return bridge_name;
	}
	public Mac_Map setBridge_name(String bridge_name) {
		this.bridge_name = bridge_name;
		return this;
	}
	public OperationType getOperation() {
		return operation;
	}
	public Mac_Map setOperation(OperationType operation) {
		this.operation = operation;
		return this;
	}
	
	
	public void send() throws Mac_MapFailException{
		String url2="http://"+ODL_IP+"/restconf/operations/vtn-mac-map:set-mac-map";
		JSONObject jsonObject2=new JSONObject();
		jsonObject2.put("input", JSONObject.parseObject(JSON.toJSONString(this)));
		System.out.println(url2+jsonObject2);
		String responsecode=HttpUtil.Post_request(url2,jsonObject2)[0];
		if(!"200".equals(responsecode) && !"201".equals(responsecode))
			throw new Mac_MapFailException("vbridge"+this.getBridge_name()+"mac_map created failed");
	}

}