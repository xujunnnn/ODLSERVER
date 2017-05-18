package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ebupt.vnbo.Beans.Vtn.Mac_Map;

public class Test {
	public static void main(String []args){
		Map<MonTag, NetStatic> map=new ConcurrentHashMap<>();
		MonTag monTag=new MonTag().setInport("openflow:1:1").setProtocol_Type(Protocol_Type.TCP);
		NetStatic netStatic=new NetStatic().setBytecount(100);
		map.put(monTag, netStatic);
		MonTag monTag1=new MonTag().setInport("openflow:1:1").setProtocol_Type(Protocol_Type.TCP);
		map.put(monTag1, netStatic);
	}

}
