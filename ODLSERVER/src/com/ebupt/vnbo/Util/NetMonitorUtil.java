package com.ebupt.vnbo.Util;

import java.util.HashMap;
import java.util.Map;

import com.ebupt.vnbo.Beans.NetMonitor.MonTag;
import com.ebupt.vnbo.Beans.NetMonitor.NetStatic;
import com.ebupt.vnbo.Beans.NetMonitor.Tag;

public class NetMonitorUtil {

	/**
	 * 对给定的数据进行标签整合
	 * @param netmonitormap
	 * @param tag
	 * @return
	 */
	public static Map<MonTag, NetStatic> aggregate(Map<MonTag, NetStatic> netmonitormap,Tag tag){
	
		Map<MonTag, NetStatic> aggnetmap=new HashMap<>();
		if(tag==Tag.protocol_Type){
			for(MonTag monTag:netmonitormap.keySet()){
				MonTag temp=new MonTag().setProtocol_Type(monTag.getProtocol_Type());
				if(aggnetmap.get(temp)!=null){	
					aggnetmap.put(temp,aggnetmap.get(temp).add(netmonitormap.get(monTag)));
				}
				else{
					NetStatic tempstatic;
					try {
						if(netmonitormap.get(monTag)!=null){
						tempstatic = (NetStatic)netmonitormap.get(monTag).clone();
						aggnetmap.put(temp,tempstatic);
						}
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
			}			
		}
		
 		if(tag==Tag.srcMac){
			for(MonTag monTag:netmonitormap.keySet()){
				MonTag temp=new MonTag().setSrcmac(monTag.getSrcmac());
				if(aggnetmap.get(temp)!=null){	
					aggnetmap.put(temp,aggnetmap.get(temp).add(netmonitormap.get(monTag)));
				}
				else{
					NetStatic tempstatic;
					try {
						if(netmonitormap.get(monTag)!=null){
						tempstatic = (NetStatic)netmonitormap.get(monTag).clone();
						aggnetmap.put(temp,tempstatic);
						}
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
			}			
		}
		
		if(tag==Tag.destMac){
			for(MonTag monTag:netmonitormap.keySet()){
				MonTag temp=new MonTag().setDestmac(monTag.getDestmac());
				if(aggnetmap.get(temp)!=null){	
					aggnetmap.put(temp,aggnetmap.get(temp).add(netmonitormap.get(monTag)));
				}
				else{
					NetStatic tempstatic;
					try {
						if(netmonitormap.get(monTag)!=null){
						tempstatic = (NetStatic)netmonitormap.get(monTag).clone();
						aggnetmap.put(temp,tempstatic);
						}
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
			}			
		}
		return aggnetmap;
	}

}