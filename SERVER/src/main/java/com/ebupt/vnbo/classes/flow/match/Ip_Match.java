package com.ebupt.vnbo.classes.flow.match;

import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;

public class Ip_Match {
	@JSONField(name="ip-protocol")
private String ip_protocol;
	@JSONField(name="ip-dscp")
private String ip_dscp;
	@JSONField(name="ip-ecn")
private String ip_ecn;
	@JSONField(name="ip-proto")
private String ip_proto;


public String getIp_protocol() {
		return ip_protocol;
	}

	public void setIp_protocol(String ip_protocol) {
		this.ip_protocol = ip_protocol;
	}

	public String getIp_dscp() {
		return ip_dscp;
	}

	public void setIp_dscp(String ip_dscp) {
		this.ip_dscp = ip_dscp;
	}

	public String getIp_ecn() {
		return ip_ecn;
	}

	public void setIp_ecn(String ip_ecn) {
		this.ip_ecn = ip_ecn;
	}

	public String getIp_proto() {
		return ip_proto;
	}

	public void setIp_proto(String ip_proto) {
		this.ip_proto = ip_proto;
	}

public Ip_Match(){}

/**
 * 
 * @param protocol
 * @param dscp
 * @param ecn
 * @param proto
 */
public Ip_Match(String protocol,String dscp,String ecn,String proto){
	if(protocol!=null)
		this.ip_protocol=protocol;
	
	if(proto!=null)
		this.ip_proto=proto;
	if(dscp!=null)
		this.ip_dscp=dscp;
	if(ecn!=null)
		this.ip_ecn=ecn;
}

@Override
public int hashCode() {
	// TODO Auto-generated method stub
	return Objects.hash(ip_dscp,ip_ecn,ip_proto,ip_protocol);
}

@Override
public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	if(obj==this) 
		return true;
	if(obj==null)
		return false;
	if(this.getClass()!=obj.getClass())
		return false;
	Ip_Match other=(Ip_Match) obj;
		return Objects.deepEquals(this.ip_dscp, other.getIp_dscp()) && Objects.deepEquals(this.ip_ecn, other.getIp_ecn())
				&& Objects.deepEquals(this.ip_proto, other.getIp_proto()) && Objects.deepEquals(this.ip_protocol, other.getIp_protocol());
}
}
