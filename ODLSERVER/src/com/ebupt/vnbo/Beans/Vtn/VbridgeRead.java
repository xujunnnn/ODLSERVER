package com.ebupt.vnbo.Beans.Vtn;

import com.alibaba.fastjson.annotation.JSONField;

public class VbridgeRead {
	private String name;
	@JSONField(name="bridge-status")
	private Bridge_status bridge_status;
	@JSONField(name="mac-map")
	private Mac_Map mac_Map;
	public String getName() {
		return name;
	}
	public VbridgeRead setName(String name) {
		this.name = name;
		return this;
	}
	public Bridge_status getBridge_status() {
		return bridge_status;
	}
	public VbridgeRead setBridge_status(Bridge_status bridge_status) {
		this.bridge_status = bridge_status;
		return this;
	}
	public Mac_Map getMac_Map() {
		return mac_Map;
	}
	public VbridgeRead setMac_Map(Mac_Map mac_Map) {
		this.mac_Map = mac_Map;
		return this;
	}


}
