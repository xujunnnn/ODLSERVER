package com.ebupt.vnbo.classes.abstracts;

import com.ebupt.vnbo.classes.exception.ConfigException;
import com.ebupt.vnbo.classes.exception.ODL_IO_Exception;
import com.ebupt.vnbo.util.BaseUtil;

public interface  Config {
	public String QOSFLOWTABLE="0";
	public String VTNFLOWTABLE="5";
	public String MONFLOWTABLE="3";
	public String LOWPRIORITY="200";
	public String MIDPRIORITY="210";
	public String HIGHPRIORITY="220";
	public String IDLE_TIME_OUT="0";
	public String HARD_TIME_OUT="0";
	public String VTN_FLOW_IDLE="3600";
	public String VTN_FLOW_HARD="0";
	public static final String ConfigUrl=BaseUtil.getODL_IP()+"/restconf/config";
	public static final String VtnConfigUrl=BaseUtil.getODL_IP()+"/restconf/operations";
	/**
	 * 发送配置信息
	 * @param node
	 * @throws ConfigException
	 */
	public  void send(String node) throws ODL_IO_Exception;
	/**
	 * 删除配置信息
	 * @param node
	 * @throws ConfigException
	 */
	public  void remove(String node) throws ODL_IO_Exception;
}
