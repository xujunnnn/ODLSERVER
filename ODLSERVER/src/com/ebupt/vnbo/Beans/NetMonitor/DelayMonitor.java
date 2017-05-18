package com.ebupt.vnbo.Beans.NetMonitor;

import org.apache.tomcat.util.buf.StringCache;

import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.Exception.DelayMonitorException;
import com.ebupt.vnbo.Util.HttpUtil;
import com.ebupt.vnbo.Util.Util;

public class DelayMonitor {
	private static String ODL_IP=Util.getODL_IP();
	public JSONObject getGlobalLatency() throws DelayMonitorException{
		String url="http://"+ODL_IP+"/restconf/operations/topology-lldp-discovery-impl:getGlobalLatency";
		String[] result=HttpUtil.Post_request(url);
		String responsecode=result[0];
		String responsebody=result[1];
		if("200".equals(responsecode) && "201".equals(responsecode))
				throw new DelayMonitorException("delay monitor eror");
		JSONObject resultjson=JSONObject.parseObject(responsebody);
		Latency_list latency_list=JSONObject.parseObject(resultjson.getJSONObject("output").toJSONString(), Latency_list.class);
		/**
		for(Latency latency:latency_list.getLatency_list()){
			String []srcinfo=latency.getSrcnode().split(":");
			String srcnode=srcinfo[0]+":"+srcinfo[1];
			String []destinfo=latency.getDestnode().split(":");
			String destnode=destinfo[0]+":"+destinfo[1];
			latency.setLatency(latency.getLatency()-(getSwitchLatency(srcnode)+getSwitchLatency(destnode))/2);
		}
		**/
		return JSONObject.parseObject(JSONObject.toJSONString(latency_list));
		}
	
	
	public long getSwitchLatency(String node){
		long latency=0;
	/*	
		http://10.108.125.125:8181/restconf/operations/sal-latency:echo-latency
		{
		    "input": {
		        "node": "/opendaylight-inventory:nodes/opendaylight-inventory:node[opendaylight-inventory:id='openflow:1']"
		    }
		}
		*/
		String url="http://"+ODL_IP+"/restconf/operations/sal-latency:echo-latency";
		String nodestring="/opendaylight-inventory:nodes/opendaylight-inventory:node[opendaylight-inventory:id='"+node+"']";
	   //构建请求JSON数据
	   JSONObject request=new JSONObject();
	   JSONObject nodejson=new JSONObject();
	   nodejson.put("node", nodestring);
	    request.put("input", nodejson);
		String [] response=HttpUtil.Post_request(url, request);
		String responsecode=response[0];
		String responsebody=response[1];
		JSONObject result=JSONObject.parseObject(responsebody);
		latency=result.getJSONObject("output").getLong("latency");
		return latency;
	}
		
	public static void main(String []args){
		DelayMonitor delayMonitor=new DelayMonitor();
		try {
			System.out.println(delayMonitor.getGlobalLatency());
		} catch (DelayMonitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
