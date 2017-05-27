package com.enupt.vnbo.servlet.qos;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.ebupt.vnbo.classes.enums.OperationType;
import com.ebupt.vnbo.classes.enums.Protocol_Type;
import com.ebupt.vnbo.classes.qos.QosEntry;
import com.ebupt.vnbo.request.qos.QosRequest;

public class QosRequestTest {
	public static void main(String []args){
		QosRequest qosRequest=new QosRequest();
		qosRequest.setOperationType(OperationType.ADD);
		qosRequest.setQosEntry(new QosEntry().setQos_id("74")
				.setDrop_rate("400")
				.setIp_Protocol(Protocol_Type.ICMP)
				);
		System.out.println(JSON.toJSONString(qosRequest));
		ArrayList<QosEntry> qosEntries=new ArrayList<>();
		qosEntries.add(new QosEntry().setQos_id("26"));
		qosEntries.add(new QosEntry().setQos_id("27"));
		qosRequest.setOperationType(OperationType.QUERRY);
		qosRequest.setQosEntries(qosEntries);
		System.out.println(JSON.toJSONString(qosRequest));
		qosRequest.setOperationType(OperationType.REMOVE);
		System.out.println(JSON.toJSONString(qosRequest));
	}

}
