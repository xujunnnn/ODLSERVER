package com.ebupt.vnbo.Beans.NetMonitor;

import java.lang.Thread.State;
import java.util.Map;

import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Util.TopoUtil;


public class NetFlowMonitor {
	private volatile boolean  isactive=false;
	private NetFlowMonitorTask netFlowMonitorTask=new NetFlowMonitorTask();
	private  Thread thread=new Thread(netFlowMonitorTask);
	private String tableid;
	/**
	 * 开始监控
	 * @throws TopoReadFailException 
	 */
	public void begin() throws TopoReadFailException{
		isactive=true;
		netFlowMonitorTask.setIsactive(isactive);
		netFlowMonitorTask.setTableid(tableid);
		netFlowMonitorTask.setNodes(TopoUtil.get_access_node());
		if(thread.getState()==State.NEW)
		thread.start(); 
		else {
			thread.resume();
		}
	}
	/**
	 * 获取数据表
	 * @return
	 */
	public  Map<MonTag, NetStatic> getNetMap(){
		return netFlowMonitorTask.getNetMonitorMap();
		}
	/**
	 * 停止监控
	 */
	public  void stop(){
		isactive=false;
		netFlowMonitorTask.setIsactive(isactive);
		thread.suspend();
	}
	/**
	 * 查询工作状态
	 */
	public boolean getStatus(){
		return isactive;
	}
	public String getTableid() {
		return tableid;
	}
	public NetFlowMonitor setTableid(String tableid) {
		this.tableid = tableid;
		return this;
	}

}
