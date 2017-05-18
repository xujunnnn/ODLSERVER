package com.ebupt.vnbo.Beans.Vtn;

import com.alibaba.fastjson.annotation.JSONField;

public class Bridge_status {
	private State state;
	@JSONField(name="path-faults")
	private String path_faults;
}
