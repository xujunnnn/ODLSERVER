package com.ebupt.vnbo.classes.table;

import java.util.HashSet;

import org.apache.http.util.VersionInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.classes.abstracts.Operational;
import com.ebupt.vnbo.classes.exception.ODL_IO_Exception;
import com.ebupt.vnbo.classes.flow.FlowEntry;
import com.ebupt.vnbo.util.HttpUtil;

public class TableRead implements Operational{
	@JSONField(name="id")
	private String tableid;
	private HashSet<FlowEntry> flow;
	@JSONField(name="opendaylight-flow-table-statistics:flow-table-statistics")
	private TableStatic tableStatic;
	
	public String getTableid() {
		return tableid;
	}


	public void setTableid(String tableid) {
		this.tableid = tableid;
	}


	public HashSet<FlowEntry> getFlow() {
		return flow;
	}


	public void setFlow(HashSet<FlowEntry> flow) {
		this.flow = flow;
	}


	public TableStatic getTableStatic() {
		return tableStatic;
	}


	public void setTableStatic(TableStatic tableStatic) {
		this.tableStatic = tableStatic;
	}
	@Override
	public TableRead read(String node) throws ODL_IO_Exception {
		// TODO Auto-generated method stub
		String url="http://"+OperationalUrl+"/opendaylight-inventory:nodes/node/"+node+"/flow-node-inventory:table/"+tableid;
		String s=HttpUtil.Get_request(url)[1];
	    JSONObject flowtable=JSONObject.parseObject(s);
//		System.out.println(s);
		JSONArray jsonArray=flowtable.getJSONArray("flow-node-inventory:table");
		JSONObject tablejson=jsonArray.getJSONObject(0);
		TableRead tableRead=JSON.toJavaObject(tablejson, TableRead.class);
		return tableRead;
	}
	public static void main(String []args){
		TableRead tableRead=new TableRead();
		tableRead.setTableid("0");
		try {
			tableRead.read("openflow:1");
		} catch (ODL_IO_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
