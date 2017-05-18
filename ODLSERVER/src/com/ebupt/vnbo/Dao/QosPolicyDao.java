package com.ebupt.vnbo.Dao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

import com.ebupt.vnbo.Beans.Qos.QosPolicy;
import com.ebupt.vnbo.Util.DBUtil;

	/**
	 * db operation about QosPolicy
	 * @author xu
	 *
	 */

public class QosPolicyDao{
	//querry all the enable qospolicies
	public List<QosPolicy> getQosPolicy() throws IOException{
		
		SqlSession session=null;
		List <QosPolicy> qosPolicies=new ArrayList<>();
			
			session=DBUtil.getSqlSession();		
			qosPolicies=session.selectList("QosPolicy.querryQosPolicy");

			// TODO Auto-generated catch block
	
	
			if(session!=null)
				session.close();

		return qosPolicies;
	}
	/**
	 * insert a new qospolicy into the database;
	 * @param qos
	 * @throws IOException
	 */
	public void insertQosPolicy(QosPolicy qos) throws IOException{
		SqlSession session=null;
		
			session=DBUtil.getSqlSession();		
			session.insert("QosPolicy.insertQosPolicy",qos);
			session.commit();
	
			// TODO Auto-generated catch block
	
	
			if(session!=null)
				session.close();
	
	}
	/**
	 * update the specified qospolicy
	 * @param qos
	 * @throws IOException
	 */
	public void updateQosPolicy(QosPolicy qos) throws IOException{
		SqlSession session=null;
			session=DBUtil.getSqlSession();		
			session.update("QosPolicy.updateQosPolicy",qos);
			session.commit();
			// TODO Auto-generated catch block	
			if(session!=null)
				session.close();	
	}
	/**
	 * delete qos from the table
	 * @param qos
	 * @throws IOException
	 */
	public void deleteQosPolicy(QosPolicy qos) throws IOException{
		SqlSession session=null;
			session=DBUtil.getSqlSession();		
			session.delete("QosPolicy.deleteQosPolicy",qos);
			session.commit();	
			// TODO Auto-generated catch block	
			if(session!=null)
				session.close();
		
	}
	/**
	 * test with the qospolicy is in the table
	 * @param qos
	 * @return
	 * @throws IOException
	 */
	public boolean contain(QosPolicy qos) throws IOException{
		boolean result=false;
		SqlSession session=null;
	    session=DBUtil.getSqlSession();
	    QosPolicy qosPolicy=session.selectOne("QosPolicy.querryQosPolicyWithId",qos);
		if(qosPolicy!=null){
			result=true;
		}
		return result;
	}
	public static void main(String []args) throws IOException{
		QosPolicyDao qosPolicyDao=new QosPolicyDao();
		List<QosPolicy> qosPolicies=new ArrayList<>();
        qosPolicies=qosPolicyDao.getQosPolicy();
		for(QosPolicy qosPolicy:qosPolicies){
			System.out.println(qosPolicy.getDrop_rate());
			//qosPolicy.setQos_id("2");
			//qosPolicy.setDrop_rate("10000");
			//qosPolicyDao.insertQosPolicy(qosPolicy);
			qosPolicy.setQos_id("8");
			qosPolicyDao.contain(qosPolicy);
		}
	}
}

	



