package com.ebupt.vnbo.classes.table;

import com.alibaba.fastjson.annotation.JSONField;

public class TableStatic {
	@JSONField(name="packets-matched")
	private String packets_matched;
	@JSONField(name="packets-looked-up")
	private String packets_looked_up;
	@JSONField(name="active-flows")
	private String active_flows;
	public String getPackets_matched() {
		return packets_matched;
	}
	public void setPackets_matched(String packets_matched) {
		this.packets_matched = packets_matched;
	}
	public String getPackets_looked_up() {
		return packets_looked_up;
	}
	public void setPackets_looked_up(String packets_looked_up) {
		this.packets_looked_up = packets_looked_up;
	}
	public String getActive_flows() {
		return active_flows;
	}
	public void setActive_flows(String active_flows) {
		this.active_flows = active_flows;
	}

}
