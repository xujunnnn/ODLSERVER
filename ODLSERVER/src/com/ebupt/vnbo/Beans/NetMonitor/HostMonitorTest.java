package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.Map;

import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.Match.Ethernet_destination;
import com.ebupt.vnbo.Beans.Match.Ethernet_source;
public class HostMonitorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			HostMonitor.begin();
		} catch (TopoReadFailException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i=0;i<1000;i++){
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<Triple<String, Ethernet_source, Ethernet_destination>, Pair<Long, Long>> map=HostMonitor.getLocalMap();
		for(Triple<String, Ethernet_source, Ethernet_destination> t:map.keySet()){
			System.out.println("port<><><><>"+t.getLeft()+"<><><><>"+t.getMid().getAddress()+"<><><><>"+t.getRight().getAddress()+"speed = "+map.get(t).getRight());
		}
	  }

	}

}
