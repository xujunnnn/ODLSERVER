package com.ebupt.vnbo.classes.abstracts;

import com.ebupt.vnbo.classes.exception.ODL_IO_Exception;
import com.ebupt.vnbo.classes.exception.OperationalException;
import com.ebupt.vnbo.util.BaseUtil;

public interface Operational {
	public static final String OperationalUrl=BaseUtil.getODL_IP()+"/restconf/operational";
	public static final String VtnOperationUrl=BaseUtil.getODL_IP()+"/restconf/operations";
	/**
	 * 读取配置信息
	 * @param node
	 * @return
	 * @throws OperationalException
	 */
	public abstract Operational read(String node) throws ODL_IO_Exception;

}
