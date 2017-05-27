package com.ebupt.vnbo.classes.flow.flow_static;

import com.alibaba.fastjson.annotation.JSONField;

public class Flow_Statistic {
	@JSONField(serialize=false)
	private String flowid;
	public String getFlowid() {
		return flowid;
	}
	public Flow_Statistic setFlowid(String flowid) {
		this.flowid = flowid;
		return this;
	}
	@JSONField(name="packet-count")
	private long packet_count;
	@JSONField(name="byte-count")
	private long byte_count;
	private Duration duration;
	public long getPacket_count() {
		return packet_count;
	}
	public void setPacket_count(long packet_count) {
		this.packet_count = packet_count;
	}
	public long getByte_count() {
		return byte_count;
	}
	public void setByte_count(long byte_count) {
		this.byte_count = byte_count;
	}
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

}

