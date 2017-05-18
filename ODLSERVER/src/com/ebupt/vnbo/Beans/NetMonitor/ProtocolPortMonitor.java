package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.Map;

import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Util.TopoUtil;



/**
 * 
 * @author xu
 *
 */
public class ProtocolPortMonitor {
		private static boolean isactive=false;
		
		private static ProtocolPortMonitorTask protocolPortMonitorTask=new ProtocolPortMonitorTask();
		private static Thread thread=new Thread(protocolPortMonitorTask); 
		/**
		 * start the monitor
		 * @throws TopoReadFailException 
		 */
		public static void begin() throws TopoReadFailException{
			protocolPortMonitorTask.setNodes(TopoUtil.get_access_node());
			isactive=true;
			if(isactive=true){	
			thread.start();
			}		
		}
		/**
		 * get the dataMap
		 * @return
		 */
		public static Map<Pair<String, Protocol_Type>, Pair<Long, Long>> getNetSpeedMap() {
			return protocolPortMonitorTask.getNetSpeedMap();
		}
		/**
		 * get the totalbyteMap
		 * @return
		 */
		public static Map<Pair<String, Protocol_Type>, Pair<Long, Long>> getNetByteMap(){
			return protocolPortMonitorTask.getNetByteMap();
		}
		
		/**
		 * stop the monitor
		 */
		public static void stop(){
			isactive=false;
			thread.interrupt();
		}
		/**
		 * get the running states
		 * @return
		 */
		public boolean getStats(){
			return isactive;
		}



}
