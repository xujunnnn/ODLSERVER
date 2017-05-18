package com.ebupt.vnbo.Beans.Vtn;

import java.util.HashSet;

import com.alibaba.fastjson.annotation.JSONField;

public class Allowed_Hosts {
	private HashSet<String> allowed_hosts=new HashSet<String>();
    public Allowed_Hosts(){}
    public Allowed_Hosts(HashSet<String> allowed_hosts){
    	this.allowed_hosts=allowed_hosts;
    }
	public HashSet<String> getAllowed_hosts() {
		return allowed_hosts;
	}

	public Allowed_Hosts setAllowed_hosts(HashSet<String> allowed_hosts) {
		this.allowed_hosts = allowed_hosts;
		return this;
	}
	public Allowed_Hosts addHost(String host){
		this.allowed_hosts.add(host);
		return this;
		
	}
	public Allowed_Hosts addHosts(HashSet<String> hosts){
		this.allowed_hosts.addAll(hosts);
		return this;
	}

}
