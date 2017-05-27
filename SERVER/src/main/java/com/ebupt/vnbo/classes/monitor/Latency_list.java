package com.ebupt.vnbo.classes.monitor;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.classes.abstracts.Operational;
import com.ebupt.vnbo.classes.exception.ODL_IO_Exception;
import com.ebupt.vnbo.classes.exception.OperationalException;
import com.ebupt.vnbo.util.HttpUtil;

public class Latency_list implements Operational{
	@JSONField(name="latency-list")
	private ArrayList<Latency> latency_list=new ArrayList<>();

	public ArrayList<Latency> getLatency_list() {
		return latency_list;
	}

	public void setLatency_list(ArrayList<Latency> latency_list) {
		this.latency_list = latency_list;
	}

	@Override
	public Latency_list read(String node) throws ODL_IO_Exception {
		// TODO Auto-generated method stub
		String url="http://"+VtnOperationUrl+"/topology-lldp-discovery-impl:getGlobalLatency";
		String[] result=HttpUtil.Post_request(url);
		String responsecode=result[0];
		String responsebody=result[1];
		if(!"201".equals(responsecode) && !"200".equals(responsecode))
			throw new OperationalException("Latency read fail");
		JSONObject resultjson=JSONObject.parseObject(responsebody);
		Latency_list latency_list=JSONObject.parseObject(resultjson.getJSONObject("output").toJSONString(), Latency_list.class);
		return latency_list;
	}

}
