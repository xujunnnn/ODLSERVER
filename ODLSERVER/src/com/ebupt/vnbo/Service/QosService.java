package com.ebupt.vnbo.Service;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.Exception.FlowFailException;
import com.ebupt.vnbo.Beans.Exception.MeterFailException;
import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.Qos.QosPolicy;
import com.ebupt.vnbo.Dao.QosPolicyDao;
/**
 * 
 * @author xu
 *
 */
public class QosService {
	/**
	 * create a qos policy and insert it into mysql
	 * @param qos
	 * @return
	 * @throws IOException 
	 */
	public JSONObject addQos(QosPolicy qos) {
		JSONObject result=new JSONObject();
		QosPolicyDao qosPolicyDao=new QosPolicyDao();
		boolean contain;
		//test if the qospolicy is in the table
		try {
			contain = qosPolicyDao.contain(qos);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.put("Status", -1);
			result.put("description", "failed to check the QosPolicy  qosid= "+qos.getQos_id());
			return result;		
		}
		if(!contain){
		// apply the qos
		try {
			qos.apply();
		} catch (MeterFailException | FlowFailException | TopoReadFailException e) {
			// TODO Auto-generated catch block
			result.put("Status", -1);
			result.put("description", "failed to creat QosPolicy  qosid= "+qos.getQos_id());
			e.printStackTrace();
			return result;			
		}
		//insert the qos	
		try {
			qosPolicyDao.insertQosPolicy(qos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result.put("Status", -1);
			result.put("description", "successed to creat QosPolicy but failed to save  qosid= "+qos.getQos_id());
			e.printStackTrace();
			return result;
		}
		result.put("Status", 0);
		result.put("description", "QosPolicy created success qosid= "+qos.getQos_id());
		return result;
	  }
		else{
			result.put("Status", -1);
			result.put("description", "QosPolicy "+qos.getQos_id()+" existed");
			return result;
		}
	}
	/**
	 * modify a existed qospolicy
	 * @param qosPolicy
	 * @return
	 */
	public JSONObject modifyQos(QosPolicy qosPolicy){
		QosPolicyDao qosPolicyDao=new QosPolicyDao();
		JSONObject result=new JSONObject();
		boolean contain=false;
		try {
			contain = qosPolicyDao.contain(qosPolicy);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.put("Status", -1);
			result.put("description", "failed to check the QosPolicy  qosid= "+qosPolicy.getQos_id());
			return result;	
		}
		if(!contain){
			result.put("Status", -1);
			result.put("description", "QosPolicy "+qosPolicy.getQos_id()+" can not be finded");
			return result;
		}
		else {
			try {
				qosPolicy.apply();
			} catch (MeterFailException | FlowFailException | TopoReadFailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.put("Status", -1);
				result.put("description", "QosPolicy "+qosPolicy.getQos_id()+" failed to be created");
				return result;
			}
			try {
				qosPolicyDao.updateQosPolicy(qosPolicy);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.put("Status", -1);
				result.put("description", "QosPolicy "+qosPolicy.getQos_id()+" failed to be modified");
				return result;
			}
		}
		result.put("Status", 0);
		result.put("description", "QosPolicy "+qosPolicy.getQos_id()+" modefied success");
		return result;
		
	}
	
	/**
	 * querry all the qospolicy
	 * @return
	 */
	public JSONObject querryQos(){
		QosPolicyDao qosPolicyDao=new QosPolicyDao();
		JSONObject result=new JSONObject();
		try {
			List<QosPolicy> qosPolicies=qosPolicyDao.getQosPolicy();
			result.put("status", 0);
			result.put("description", "querry success");
			result.put("QosPolicys", JSON.toJSON(qosPolicies));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result.put("status", -1);
			result.put("description", "failed to querry");
			e.printStackTrace();
			return result;
		}
		return result;
	}
	/**
	 * delete the specified QosPolicy
	 * @param qosPolicy
	 * @return
	 */
	public JSONObject deleteQos(QosPolicy qosPolicy){
		QosPolicyDao qosPolicyDao=new QosPolicyDao();
		JSONObject result=new JSONObject();
		try {
			qosPolicy.delete();
		} catch (FlowFailException | MeterFailException | TopoReadFailException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.put("Status", -1);
			result.put("description", "failed to delete qospolicy");
			return result;
		}
		try {
			qosPolicyDao.deleteQosPolicy(qosPolicy);
			result.put("Status", 0);
			result.put("description", "delete success "+qosPolicy.getQos_id());
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result.put("Status", -1);
			result.put("description", "failed to delete qospolicy");
			
			e.printStackTrace();
			return result;
		}
		
	}

}
