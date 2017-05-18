package com.ebupt.vnbo.Beans.NetMonitor;

public class Latency {
	private String srcnode;
	private String destnode;
	private long latency;
	
	public String getSrcnode() {
		return srcnode;
	}
	public void setSrcnode(String srcnode) {
		this.srcnode = srcnode;
	}
	public String getDestnode() {
		return destnode;
	}
	public void setDestnode(String destnode) {
		this.destnode = destnode;
	}
	public long getLatency() {
		return latency;
	}
	public void setLatency(long latency) {
		this.latency = latency;
	}


}
