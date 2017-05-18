package com.ebupt.vnbo.Beans.Flow;

public class FlowId {
	private int id;
	public int getId() {
		return id;
	}
	public FlowId setId(int id) {
		this.id = id;
		return this;
	}
	public int getFlowid() {
		return flowid;
	}
	public FlowId setFlowid(int flowid) {
		this.flowid = flowid;
		return this;
	}
	public String getTableid() {
		return tableid;
	}
	public FlowId setTableid(String tableid) {
		this.tableid = tableid;
		return this;
	}
	public String getNode() {
		return node;
	}
	public FlowId setNode(String node) {
		this.node = node;
		return this;
	}
	private int flowid;
	private String tableid;
	private String node;
}
