package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.ArrayList;

import com.alibaba.fastjson.annotation.JSONField;

public class Latency_list {
	@JSONField(name="latency-list")
	private ArrayList<Latency> latency_list=new ArrayList<>();

	public ArrayList<Latency> getLatency_list() {
		return latency_list;
	}

	public void setLatency_list(ArrayList<Latency> latency_list) {
		this.latency_list = latency_list;
	}

}
