package com.ebupt.vnbo.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类是网络监控的service 类
 * @author xu
 *
 */
import com.alibaba.fastjson.*;
import com.ebupt.vnbo.Beans.Exception.DelayMonitorException;
import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.NetMonitor.DelayMonitor;
import com.ebupt.vnbo.Beans.NetMonitor.MonTag;
import com.ebupt.vnbo.Beans.NetMonitor.NetFlowMonitor;
import com.ebupt.vnbo.Beans.NetMonitor.NetStatic;
import com.ebupt.vnbo.Beans.NetMonitor.Tag;
import com.ebupt.vnbo.Util.NetMonitorUtil;
public class NetMonService {
	//监控不同协议的网络流量
	private static NetFlowMonitor protoMon=new NetFlowMonitor().setTableid("3");
	//按mac地址进行监控
	private static NetFlowMonitor macMon=new NetFlowMonitor().setTableid("5"); 
	//监控网络时延
	private static DelayMonitor delayMonitor=new DelayMonitor();
 /**
  * 开始监控
  * @return
  */
	public static JSONObject StartMonitor(){
		JSONObject result=new JSONObject();
	 try {
		 //开启监控进程
		protoMon.begin();
		macMon.begin();
		result.put("Status", 0);
		result.put("description", "begin to monitor");
		return result;
		
	} catch (TopoReadFailException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		result.put("Status", -1);
		result.put("description", "can not start");
		return result;
	}
 }
/**
 * 获取各种协议的数据流量
 * @return
 */
	public static JSONObject GetNetData(Tag tag){
		JSONObject result=new JSONObject();
		Map<MonTag, NetStatic> netDataMap=new HashMap<>() ;
		if(tag==Tag.protocol_Type)
			netDataMap=NetMonitorUtil.aggregate(protoMon.getNetMap(),tag );
		else if(tag==Tag.srcMac)
			netDataMap=NetMonitorUtil.aggregate(macMon.getNetMap(),tag );
		else if (tag==Tag.destMac) 
			netDataMap=NetMonitorUtil.aggregate(macMon.getNetMap(),tag );
		else
			netDataMap=macMon.getNetMap();
			
		
	   JSONArray dataArray=new JSONArray();
	   for(MonTag monTag:netDataMap.keySet()){
		   JSONObject jsonObject=new JSONObject();
		   if(monTag.getProtocol_Type()!=null)
			   			jsonObject.put(monTag.getProtocol_Type().toString(), JSONObject.toJSON(netDataMap.get(monTag)));
		   if(monTag.getSrcmac()!=null)
			   			jsonObject.put(monTag.getSrcmac(), JSONObject.toJSON(netDataMap.get(monTag)));
		   if(monTag.getDestmac()!=null)
			   			jsonObject.put(monTag.getDestmac(), JSONObject.toJSON(netDataMap.get(monTag)));
		   dataArray.add(jsonObject);
 }
	   result.put("status", 0);
	   result.put("description", "querry success");
	   result.put("result", dataArray);
	   return result;
	}
	/**
	 * 停止监控进程
	 * @return
	 */
	public static JSONObject StopMonitor(){
		JSONObject result=new JSONObject();
		//停止监控进程
		protoMon.stop();
		macMon.stop();
		result.put("Status", 0);
		result.put("description", "stop to monitor");
		return result;
	}
	/**
	 * 计算网络时延
	 * @return
	 */
	public static JSONObject GetGlobalLatency(){
		JSONObject result=new JSONObject();
		 try {
			JSONObject data=delayMonitor.getGlobalLatency();
			result.put("status",0);
			result.put("description", "querry success");
			result.put("result", data);
			return result;
		} catch (DelayMonitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status",-1);
			result.put("description", "querry failed");
			
			return result;
		}
	}

}
