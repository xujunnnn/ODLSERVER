package com.ebupt.vnbo.Beans.VTopo;

import java.util.HashSet;

import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.Beans.Vtn.VtnRead;


public class VTopoRead {
	@JSONField(name="vtn")
      private HashSet<VtnRead> vtnReads=new HashSet<>();

	public HashSet<VtnRead> getVtnReads() {
		return vtnReads;
	}

	public VTopoRead setVtnReads(HashSet<VtnRead> vtnReads) {
		this.vtnReads = vtnReads;
		return this;
	}
}
