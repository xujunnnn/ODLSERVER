package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.HashMap;
import java.util.Map;

import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;

public class ProtocolPortTest {
	public static void main(String []args){
		try {
			ProtocolPortMonitor.begin();
		} catch (TopoReadFailException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i=0;i<100;i++){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Map<Pair<String, Protocol_Type>, Pair<Long, Long>> map=ProtocolPortMonitor.getNetSpeedMap();
		
		for(Pair<String, Protocol_Type> pair:map.keySet()){
			System.out.println(pair.getLeft()+"<><><><><>"+pair.getRight()+"<><>pktspeed= "+map.get(pair).getLeft()+"<><>bytespeed ="+map.get(pair).getRight());
		}
	}
}

}
