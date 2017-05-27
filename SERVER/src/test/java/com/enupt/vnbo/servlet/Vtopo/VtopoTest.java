package com.enupt.vnbo.servlet.Vtopo;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.ebupt.vnbo.classes.enums.OperationType;
import com.ebupt.vnbo.classes.vtopo.VTopo;
import com.ebupt.vnbo.request.vtopo.VtopoRequest;

public class VtopoTest {
	public static void main(String []args){
		VTopo vTopo=new VTopo();
		VtopoRequest vtopoRequest=new VtopoRequest();
		vtopoRequest.setOperationType(OperationType.QUERRYALL);
		System.out.println(JSON.toJSONString(vtopoRequest));
		VTopo vTopo2=new VTopo().setVtopo_name("v1");
		ArrayList<VTopo> vTopos=new ArrayList<>();
		vTopos.add(new VTopo().setVtopo_name("v1"));
		vtopoRequest.setOperationType(OperationType.QUERRY);
		vtopoRequest.setvTopos(vTopos);
		System.out.println(JSON.toJSONString(vtopoRequest));
		
	}

}
