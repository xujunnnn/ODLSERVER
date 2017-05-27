package com.ebupt.vnbo.classes.vtn;

import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.classes.enums.State;

public class Bridge_status {
	private State state;
	@JSONField(name="path-faults")
	private String path_faults;
}
