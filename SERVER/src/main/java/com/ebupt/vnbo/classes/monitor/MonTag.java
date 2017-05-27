package com.ebupt.vnbo.classes.monitor;

import java.util.Objects;

import com.ebupt.vnbo.classes.enums.Protocol_Type;

/**
 * 该类描述了流量监控的标签
 * 分四项，协议类型，源mac，目的mac，入端口
 * @author xu
 *
 */
public class MonTag {
	private String node;
	private Protocol_Type protocol_Type;
	private String srcmac;
	private String destmac;
	private String inport;
	private String srcip;
	private String destip;
	private long timestamp;
	public String getSrcip() {
		return srcip;
	}
	public MonTag setSrcip(String srcip) {
		this.srcip = srcip;
		return this;
	}
	public String getDestip() {
		return destip;
	}
	public MonTag setDestip(String destip) {
		this.destip = destip;
		return this;
	}
	public Protocol_Type getProtocol_Type() {
		return protocol_Type;
	}
	public MonTag setProtocol_Type(Protocol_Type protocol_Type) {
		this.protocol_Type = protocol_Type;
		return this;
	}
	public String getSrcmac() {
		return srcmac;
	}
	public MonTag setSrcmac(String srcmac) {
		this.srcmac = srcmac;
		return this;
	}
	public String getDestmac() {
		return destmac;
	}
	public MonTag setDestmac(String destmac) {
		this.destmac = destmac;
		return this;
	}
	public String getInport() {
		return inport;
	}
	public MonTag setInport(String inport) {
		this.inport = inport;
		return this;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this==obj)
			return true;
		if(obj==null)
			return false;
	    if(this.getClass()!=obj.getClass())
	    	return false;
	    MonTag other=(MonTag) obj;
	    return Objects.equals(this.protocol_Type, other.getProtocol_Type())
	    	&& Objects.equals(this.srcmac, other.getSrcmac())
	    	&& Objects.equals(this.destmac, other.getDestmac())
	    	&& Objects.equals(this.inport,other.getInport())
	    	&& Objects.equals(this.node, other.getNode());
		
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(protocol_Type,srcmac,destmac,inport,node);
	}
	public String getNode() {
		return node;
	}
	public MonTag setNode(String node) {
		this.node = node;
		return this;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Montag "+" srcmac= "+srcmac+" descmac "+destmac+" ipport= "+inport+" protocol_Type= "+protocol_Type+" node ="+node;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public MonTag setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}
	

	
	
	
}
