package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.Map;

import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Util.NetMonitorUtil;

public class NetFlowMonitorTest {
	public static void main(String []args){
		NetFlowMonitor netFlowMonitor=new NetFlowMonitor();
		netFlowMonitor.setTableid("5");
		try {
			netFlowMonitor.begin();
		} catch (TopoReadFailException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i=0;i<200;i++){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Map<MonTag, NetStatic>rawmap=netFlowMonitor.getNetMap();
		Map<MonTag, NetStatic>map=NetMonitorUtil.aggregate(netFlowMonitor.getNetMap(),Tag.srcMac);
			//System.out.println(map.keySet().size());
		//	for(MonTag monTag:map.keySet()){
				//System.out.println("hash"+monTag.hashCode());
			//	System.out.println(monTag.getInport()+"<><><>"+monTag.getSrcmac()+"<><><><>"+monTag.getDestmac()+"<><><><><>"+map.get(monTag).getBytecount());
		//	System.out.println(monTag.getInport()+"<><><>"+monTag.getProtocol_Type()+"<><><><>"+map.get(monTag).getBytespeed());
		//	}
		for(MonTag monTag:map.keySet()){
				//System.out.println("hash"+monTag.hashCode());
			 System.out.println(monTag.getSrcmac()+"<><><><>"+map.get(monTag).getBytespeed());
			//System.out.println(monTag.getProtocol_Type()+"<><><><>"+map.get(monTag).getBytespeed());
		}
			
		}
	}
}

