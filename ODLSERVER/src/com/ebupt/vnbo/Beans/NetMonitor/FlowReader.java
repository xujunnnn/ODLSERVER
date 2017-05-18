package com.ebupt.vnbo.Beans.NetMonitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.Exception.FlowFailException;
import com.ebupt.vnbo.Beans.Flow.Flow;
import com.ebupt.vnbo.Util.HttpUtil;
import com.ebupt.vnbo.Util.Util;
import com.sun.xml.internal.ws.message.StringHeader;

public class FlowReader {
	private static String ODL_IP= Util.getODL_IP();
	private String tableid;
	private String node;
	public String getTableid() {
		return tableid;
	}
	public FlowReader setTableid(String tableid) {
		this.tableid = tableid;
		return this;
	}
	public String getNode() {
		return node;
	}
	public FlowReader setNode(String node) {
		this.node = node;
		return this;
	}
	public String getFlowid() {
		return flowid;
	}
	public FlowReader setFlowid(String flowid) {
		this.flowid = flowid;
		return this;
	}
	private String flowid;
	/**
	 * read the specified flow 
	 * @return
	 * @throws FlowFailException 
	 */
	public  Flow read() throws FlowFailException{
	  String url="http://"+ODL_IP+"/restconf/config/opendaylight-inventory:nodes/node/"+this.node+"/flow-node-inventory:table/"+this.tableid+"/flow/"+this.flowid;
	  System.out.println(url);
	  String []result=HttpUtil.Get_request(url);
	  String code=result[0];
	  if("404".equals(code))
		  throw new FlowFailException("can not read the flow ");
	  String s=result[1];
	  JSONObject jsonObject=JSONObject.parseObject(s); 
	  Flow flow=JSON.parseObject(jsonObject.getJSONArray("flow-node-inventory:flow").getJSONObject(0).toJSONString(), Flow.class);
	  return flow;
	}
	public static void main(String[] args){
		String url="http://10.108.125.125:8181/restconf/operational/opendaylight-inventory:nodes/node/openflow:2/flow-node-inventory:table/0/flow/13";
		for(int i=0;i<100;i++){
		System.out.print(HttpUtil.Get_request(url)[0]);
		}
		}

}
