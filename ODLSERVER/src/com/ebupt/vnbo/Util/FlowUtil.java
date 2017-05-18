package com.ebupt.vnbo.Util;

import java.util.HashSet;

import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.Flow.FlowId;
import com.ebupt.vnbo.Beans.NetTopology.Node;
import com.ebupt.vnbo.Beans.NetTopology.Switch;
import com.ebupt.vnbo.Dao.FlowIdDao;

public class FlowUtil {
	/**
	 * get flow id
	 * @param Node
	 * @param Table
	 * @return
	 */
	public static String  getFlowId(String Node,String Table){
		FlowId flowid;
		FlowIdDao flowIdDao=new FlowIdDao();
		//get flow id
		flowid=flowIdDao.getFlowId(Node, Table);
		//update id+1
		flowid.setFlowid(flowid.getFlowid()+1);
		flowIdDao.updateFlowid(flowid);
		
		return String.valueOf(flowid.getFlowid());
	}
	/**
	 * 
	 * @throws TopoReadFailException
	 */
	public static void initFlowId() throws TopoReadFailException{
		HashSet<Node> switchs=TopoUtil.get_switch();
		for(Node node:switchs){
			FlowIdDao flowIdDao=new FlowIdDao();
			FlowId flowId=new FlowId();
			flowId.setNode(node.getNode_id()).setTableid("0").setFlowid(1);
			flowIdDao.insertFlowid(flowId);
		}
		
	}

}
