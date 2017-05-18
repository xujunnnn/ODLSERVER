package com.ebupt.vnbo.Util;


import java.util.HashSet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.Exception.VBridgeReadException;
import com.ebupt.vnbo.Beans.Exception.VtnReadException;
import com.ebupt.vnbo.Beans.Vtn.Mac_Map_Config;
import com.ebupt.vnbo.Beans.Vtn.Vbridge;
import com.ebupt.vnbo.Beans.Vtn.Vtn;
public class VTNUtil {
	private static String ODL_IP=Util.getODL_IP();
	/**
	 * read the infomation of the given vtn
	 * @param Tenant_name
	 * @return
	 * @throws VtnReadException 
	 */
	public static Vtn readVtn(String Tenant_name) throws VtnReadException{
	
		String url="http://"+ODL_IP+"/restconf/operational/vtn:vtns/vtn/"+Tenant_name;
		String []result=HttpUtil.Get_request(url);
		String jsondata=result[1];
		String responsecode=result[0];
		if(!"200".equals(responsecode) && ! "201".equals(responsecode))
			throw new VtnReadException("vtn"+Tenant_name+"read failed");
		JSONObject jsonObject=JSONObject.parseObject(jsondata);
		JSONObject vtnJsonObject=jsonObject.getJSONArray("vtn").getJSONObject(0);
		
		Vtn vtn=JSONObject.parseObject(vtnJsonObject.toJSONString(), Vtn.class);
		return vtn;
	}
	/**
	 * 
	 * @param Tenant_name
	 * @param Vbridge
	 * @return
	 * @throws VBridgeReadException 
	 */
	public static Vbridge readVbridge(String Tenant_name,String Vbridge) throws VBridgeReadException{
		String url="http://"+ODL_IP+"/restconf/operational/vtn:vtns/vtn/"+Tenant_name+"/vbridge/"+Vbridge;
		String []result=HttpUtil.Get_request(url);
		String jsondata=result[1];
		String responsecode=result[0];
		if(!"200".equals(responsecode) && ! "201".equals(responsecode))
			throw new VBridgeReadException("vbridge"+Vbridge+"read failed");
		JSONObject vbridgejson=JSONObject.parseObject(jsondata).getJSONArray("vbridge").getJSONObject(0);
		Vbridge vbridge=JSONObject.parseObject(vbridgejson.toJSONString(), Vbridge.class);
		return vbridge;
	}
	/**
	 * get mac map host of the bridge
	 * @param Tenant
	 * @param vbridge
	 * @return Mac_Map_Config
	 * @throws VBridgeReadException 
	 */
	public static Mac_Map_Config readMac_Map_Config(String Tenant,String vbridge) throws VBridgeReadException{
		String url="http://"+ODL_IP+"/restconf/operational/vtn:vtns/vtn/"+Tenant+"/vbridge/"+vbridge+"/mac-map/mac-map-config";
		String []result=HttpUtil.Get_request(url);
		String jsondata=result[1];
		System.out.println(jsondata);
		String responsecode=result[0];
		if(!"200".equals(responsecode) && ! "201".equals(responsecode))
				throw new VBridgeReadException("failed to read the mac_map VTn/Vbridge "+Tenant+"/"+vbridge);
		JSONObject js=JSONObject.parseObject(jsondata).getJSONObject("mac-map-config");
		System.out.println(js);
		Mac_Map_Config mac_Mapped_Config=JSON.parseObject(js.toJSONString(), Mac_Map_Config.class);
		return mac_Mapped_Config;
			

	}

}
