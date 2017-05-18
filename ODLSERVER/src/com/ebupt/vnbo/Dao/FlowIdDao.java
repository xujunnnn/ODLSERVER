package com.ebupt.vnbo.Dao;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ebupt.vnbo.Beans.Flow.Flow;
import com.ebupt.vnbo.Beans.Flow.FlowId;
import com.ebupt.vnbo.Util.DBUtil;

/**
 * db operation about Flowid
 * @author xu
 *
 */

public class FlowIdDao {
	/**
	 * get flowid from the database
	 * @param node
	 * @param table
	 * @return
	 */
	public FlowId getFlowId(String node,String table){
		SqlSession session=null;
		FlowId flowId=new FlowId();
		flowId.setNode(node).setTableid(table);
		try {
			session=DBUtil.getSqlSession();		
			flowId=session.selectOne("FlowId.querryFlowId",flowId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(session!=null)
				session.close();
		}
		return flowId;
	}
	/**
	 * 
	 * @param flowId
	 */
	public void insertFlowid(FlowId flowId){
		SqlSession session=null;
		try {
			session=DBUtil.getSqlSession();		
			session.insert("FlowId.insertFlowId",flowId);
			session.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(session!=null)
				session.close();
		}
		
	}
	/**
	 * 
	 * @param flowId
	 */
	public void updateFlowid(FlowId flowId){
		SqlSession session=null;
		try {
			session=DBUtil.getSqlSession();		
			session.update("FlowId.updateFlowId",flowId);
			session.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(session!=null)
				session.close();
		}
	}
}
