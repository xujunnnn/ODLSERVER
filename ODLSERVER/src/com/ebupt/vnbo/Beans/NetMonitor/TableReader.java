package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.Exception.TableReadException;
import com.ebupt.vnbo.Beans.Flow.Flow;
import com.ebupt.vnbo.Beans.Table.Table;
import com.ebupt.vnbo.Util.HttpUtil;
import com.ebupt.vnbo.Util.Util;




public class TableReader {
	private static final String ODL_IP=Util.getODL_IP();
	private String node;
	private String tableid;
	public String getNode() {
		return node;
	}
	public TableReader setNode(String node) {
		this.node = node;
		return this;
	}
	public String getTableid() {
		return tableid;
	}
	public TableReader setTableid(String tableid) {
		this.tableid = tableid;
		return this;
	}
	/**
	 * 读取flowTable 并把table中的流表放入Map
	 * @return
	 * @throws TableReadException 
	 */
	public Map<String, Flow> read() throws TableReadException{
    Map<String, Flow> flowmap=new HashMap<String, Flow>();
	
	String url="http://"+ODL_IP+"/restconf/operational/opendaylight-inventory:nodes/node/"+this.node+"/flow-node-inventory:table/"+tableid;
	String[] result=HttpUtil.Get_request(url);
	String responsecode=result[0];
	String responsebody=result[1];
	if(!"200".equals(responsecode) && !"201".equals(responsecode))
		throw new TableReadException(url +"read fail");
	
    JSONObject flowtable=JSONObject.parseObject(responsebody);
//	System.out.println(s);
	JSONArray jsonArray=flowtable.getJSONArray("flow-node-inventory:table");
	JSONObject tablejson=jsonArray.getJSONObject(0);
	Table table=JSONObject.parseObject(tablejson.toJSONString(), Table.class);
    for(Flow flow:table.getFlow()){
    	flowmap.put(flow.getId(), flow);
    }
    return flowmap;

}
}