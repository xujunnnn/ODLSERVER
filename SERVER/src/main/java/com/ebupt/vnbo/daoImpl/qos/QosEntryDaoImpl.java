package com.ebupt.vnbo.daoImpl.qos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ebupt.vnbo.classes.enums.Protocol_Type;
import com.ebupt.vnbo.classes.qos.QosEntry;
import com.ebupt.vnbo.dao.qos.QosEntryDao;
import com.ebupt.vnbo.util.DBUtil;

public class QosEntryDaoImpl implements QosEntryDao {
	public static void main(String []args){
		QosEntryDaoImpl qosDaoImpl=new QosEntryDaoImpl();
		QosEntry qosEntry=new QosEntry().setQos_id("1").setDrop_rate("100").setIp_Protocol(Protocol_Type.ICMP);
		try {
			qosDaoImpl.insert(qosEntry);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void insert(QosEntry qosEntry) throws IOException {
		// TODO Auto-generated method stub
	SqlSession session=null;
		
		session=DBUtil.getSqlSession();		
		session.insert("QosEntry.insertQosEntry",qosEntry);
		session.commit();
		
	}

	@Override
	public void delete(QosEntry qosEntry) throws IOException {
		// TODO Auto-generated method stub
		SqlSession session=null;
		session=DBUtil.getSqlSession();		
		session.delete("QosEntry.deleteQosEntry",qosEntry);
		session.commit();	
		// TODO Auto-generated catch block	
		if(session!=null)
			session.close();
		
	}

	@Override
	public QosEntry querry(QosEntry qosEntry) throws IOException {
		// TODO Auto-generated method stub
		SqlSession session=null;
	    session=DBUtil.getSqlSession();
	    QosEntry qosEntry2=session.selectOne("QosEntry.querryQosEntryWithId",qosEntry);
	    return qosEntry2;
		
	}

	@Override
	public boolean contains(QosEntry qosEntry) throws IOException {
		// TODO Auto-generated method stub
		boolean result=false;
		SqlSession session=null;
	    session=DBUtil.getSqlSession();
	    QosEntry qosEntry2=session.selectOne("QosEntry.querryQosEntryWithId",qosEntry);
		if(qosEntry2!=null){
			result=true;
		}
		return result;
	}

	@Override
	public List<QosEntry> querryAll() throws IOException {
		// TODO Auto-generated method stub
		SqlSession session=null;
		List <QosEntry> qosEntries=new ArrayList<>();
			
			session=DBUtil.getSqlSession();		
			qosEntries=session.selectList("QosEntry.querryQosEntry");

			// TODO Auto-generated catch block
	
	
			if(session!=null)
				session.close();

		return qosEntries;
	}
	/**
	public void insert(QosEntry qosEntry) throws IOException{
		// TODO Auto-generated method stub
		SqlSession session=null;
		
		session=DBUtil.getSqlSession();		
		session.insert("QosPolicy.insertQosPolicy",qosEntry);
		session.commit();

		// TODO Auto-generated catch block


		if(session!=null)
			session.close();
		
	}

	@Override
	public void delete(QosEntry qosEntry) throws IOException {
		// TODO Auto-generated method stub
		SqlSession session=null;
		session=DBUtil.getSqlSession();		
		session.delete("QosPolicy.deleteQosPolicy",qosEntry);
		session.commit();	
		// TODO Auto-generated catch block	
		if(session!=null)
			session.close();	
	}

	@Override
	public void querry(QosEntry qosEntry) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(QosEntry qosEntry) throws IOException {
		// TODO Auto-generated method stub
		boolean result=false;
		SqlSession session=null;
	    session=DBUtil.getSqlSession();
	    QosEntry qosPolicy=session.selectOne("QosPolicy.querryQosPolicyWithId",qosEntry);
		if(qosPolicy!=null){
			result=true;
		}
		return result;
	}

	@Override
	public List<QosEntry> querry() throws IOException {
		// TODO Auto-generated method stub
		SqlSession session=null;
		List <QosEntry> qosEntries=new ArrayList<>();
			
			session=DBUtil.getSqlSession();		
			qosEntries=session.selectList("QosPolicy.querryQosPolicy");

			// TODO Auto-generated catch block
	
	
			if(session!=null)
				session.close();

		return qosEntries;
	}

**/
}
