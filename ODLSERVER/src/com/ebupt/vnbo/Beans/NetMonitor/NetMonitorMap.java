package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NetMonitorMap {

	private Map<MonTag, NetStatic> netmonitormap=new ConcurrentHashMap<>();
	
	public NetMonitorMap put(MonTag monTag,NetStatic netStatic){
		this.netmonitormap.put(monTag, netStatic);
		return this;
	}
	
	public NetStatic get(MonTag monTag){
		return this.netmonitormap.get(monTag);
	}
	
	public Map<MonTag, NetStatic> getNetmonitormap() {
		return netmonitormap;
	}

	public void setNetmonitormap(Map<MonTag, NetStatic> netmonitormap) {
		this.netmonitormap = netmonitormap;
	}
	
	
	/**
	 * 对数据按标签进行聚合
	 * @param tag
	 * @return
	 */
	public Map<MonTag, NetStatic> aggregate(String tag){
		Map<MonTag, NetStatic> aggnetmap=new HashMap<>();
		if(tag=="protocol_Type"){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(MonTag monTag:netmonitormap.keySet()){
				MonTag temp=new MonTag().setProtocol_Type(monTag.getProtocol_Type());
				if(aggnetmap.get(temp)!=null){
					aggnetmap.put(temp,aggnetmap.get(temp).add(netmonitormap.get(monTag)));					
				}
				else{
					aggnetmap.put(temp, netmonitormap.get(monTag));
				}
			}
			
		}
		return aggnetmap;
	}

}
