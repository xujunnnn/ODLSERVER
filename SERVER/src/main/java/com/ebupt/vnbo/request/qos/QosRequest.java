package com.ebupt.vnbo.request.qos;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.ebupt.vnbo.classes.enums.OperationType;
import com.ebupt.vnbo.classes.qos.QosEntry;
import com.ebupt.vnbo.request.Request;

public class QosRequest implements Request {
	private ArrayList<QosEntry> qosEntries;
	private OperationType operationType;
	private QosEntry qosEntry;
	public ArrayList<QosEntry> getQosEntries() {
		return qosEntries;
	}
	public void setQosEntries(ArrayList<QosEntry> qosEntries) {
		this.qosEntries = qosEntries;
	}
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	public QosEntry getQosEntry() {
		return qosEntry;
	}
	public void setQosEntry(QosEntry qosEntry) {
		this.qosEntry = qosEntry;
	}
	public static void main(String []args){
		QosRequest qosRequest=new QosRequest();
		qosRequest.setOperationType(OperationType.QUERRYALL);
		System.out.println(JSON.toJSONString(qosRequest));
		QosEntry qosEntry=new QosEntry();
		qosRequest.setQosEntry(qosEntry);
		
	}
}
