package com.ebupt.vnbo.Beans.Vtn;

import java.util.ArrayList;
import java.util.HashSet;

import com.alibaba.fastjson.annotation.JSONField;

public class VtnRead {
	private String name;
	@JSONField(name="vtenant-config")
	private Vtenant_config vtenant_config;
	@JSONField(name="vbridge")	
	private HashSet<VbridgeRead> vbridgeReads=new HashSet<>();
	
	
	public String getName() {
		return name;
	}
	public VtnRead setName(String name) {
		this.name = name;
		return this;
	}
	public Vtenant_config getVtenant_config() {
		return vtenant_config;
	}
	public VtnRead setVtenant_config(Vtenant_config vtenant_config) {
		this.vtenant_config = vtenant_config;
		return this;
	}
	public HashSet<VbridgeRead> getVbridgeReads() {
		return vbridgeReads;
	}
	public VtnRead setVbridgeReads(HashSet<VbridgeRead> vbridgeReads) {
		this.vbridgeReads = vbridgeReads;
		return this;
	}
	
}
