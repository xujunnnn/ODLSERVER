package com.ebupt.vnbo.Service;

import java.util.HashSet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.Exception.Mac_MapFailException;
import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.Exception.VBridgeReadException;
import com.ebupt.vnbo.Beans.Exception.VbridgeFailExpection;
import com.ebupt.vnbo.Beans.Exception.VtnFailExpection;
import com.ebupt.vnbo.Beans.Exception.VtnReadException;
import com.ebupt.vnbo.Beans.VTopo.VTopo;
import com.ebupt.vnbo.Beans.VTopo.VTopoReader;
/**
 * 
 * @author xu
 *
 */
public class VTopoService {
	
	
	public JSONObject querryVTopo(){
		JSONObject result=new JSONObject();	
		VTopoReader vTopoReader=new VTopoReader();
		try {
			HashSet<VTopo> vTopos=vTopoReader.Adapter(vTopoReader.read());
			result.put("status", 0);
			result.put("description", "read success");
			JSONObject vtopoJSon=new JSONObject();
			vtopoJSon.put("VTopos", JSONArray.toJSON(vTopos));
			result.put("result",vtopoJSon);
			return result;
		} catch (VtnReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", -1);
			result.put("description", "read failed");
			return result;
		}
			
	}
	
	/**
	 * create the vtopo
	 * @param vTopo
	 * @return
	 */
	public JSONObject addVTopo(VTopo vTopo){
		JSONObject result=new JSONObject();
		try {
			vTopo.create();
			result.put("Status", 0);
			result.put("description", "success to creat vtopo "+vTopo.getVtopo_name());
		} catch (VbridgeFailExpection | Mac_MapFailException | VtnFailExpection | TopoReadFailException | VBridgeReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("Status", -1);
			result.put("description", "faied to creat vtopo "+vTopo.getVtopo_name());
			return result;
		}
		return result;
	}
	/**
	 * delete the specified topo
	 * @param vTopo
	 * @return
	 */
	public JSONObject removeVTopo(VTopo vTopo){
		JSONObject result=new JSONObject();
		try {
			vTopo.delete();
			result.put("Status", 0);
			result.put("description", "success to remove vtopo "+vTopo.getVtopo_name());
			
		} catch (VtnFailExpection e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("Status", -1);
			result.put("description", "faied to delete vtopo "+vTopo.getVtopo_name());
			return result;
		}
		return result;
		
	}

}
