package com.ebupt.vnbo.Beans.Meter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.Beans.Exception.MeterFailException;
import com.ebupt.vnbo.Util.HttpUtil;
import com.ebupt.vnbo.Util.Util;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallerImpl;

public class Meter {
	private static String ODL_IP=Util.getODL_IP();
	@JSONField(name="meter-id")
	private String meter_id;
	@JSONField(name="meter-name")
	private String meter_name;
	@JSONField(name="container-name")
	private String container_name;
	private String flags="meter-kbps";
	@JSONField(name="meter-band-headers")
	private Meter_Band_Headers meter_band_headers;
	public String getMeter_id() {
		return meter_id;
	}
	public Meter setMeter_id(String meter_id) {
		this.meter_id = meter_id;
		return this;
	}
	public String getMeter_name() {
		return meter_name;
	}
	public Meter setMeter_name(String meter_name) {
		this.meter_name = meter_name;
		return this;
	}
	public String getContainer_name() {
		return container_name;
	}
	public Meter setContainer_name(String container_name) {
		this.container_name = container_name;
		return this;
	}
	public String getFlags() {
		return flags;
	}
	public Meter setFlags(String flags) {
		this.flags = flags;
		return this;
	}
	public Meter_Band_Headers getMeter_band_headers() {
		return meter_band_headers;
	}
	public Meter setMeter_band_headers(Meter_Band_Headers meter_band_headers) {
		this.meter_band_headers = meter_band_headers;
		return this;
	}
	/**
	 * set drop-rate for the meter
	 * @param droprate
	 * @return
	 */
	public Meter Set_drop_rate(String droprate){
		Meter_Band_Header meter_Band_Header=new Meter_Band_Header().setBand_burst_size("100").setBand_id(meter_id).setBand_rate("100").setDrop_burst_size("100").setDrop_rate(droprate);
		meter_Band_Header.Set_Type("ofpmbt-drop");
		Meter_Band_Headers meter_Band_Headers=new Meter_Band_Headers();
		meter_Band_Headers.addMeter_Band_Header(meter_Band_Header);
		this.meter_band_headers=meter_Band_Headers;
		return this;
	}
	/**
	 * send the meter to the specified node
	 * @param node
	 * @return s[0]=code s[1]=description
	 */
	public void Send(String node) throws MeterFailException{
		String url="http://"+ODL_IP+"/restconf/config/opendaylight-inventory:nodes/node/"+node+"/flow-node-inventory:meter/"+meter_id;
	    JSONArray jsonArray=new JSONArray();
	    jsonArray.add(JSONObject.parse(JSON.toJSONString(this)));
	    JSONObject jsonObject=new JSONObject();   
	    jsonObject.put("flow-node-inventory:meter", jsonArray);
		String responsecode=HttpUtil.Put_request(url, jsonObject)[0];
		if(!"200".equals(responsecode) && !"201".equals(responsecode))
			throw new MeterFailException("meter"+this.getMeter_id()+"sended to "+node+"fail");
	
	}
	/**
	 * delete the meter from the specified node
	 * @param node
	 * @throws MeterFailException
	 */
	public void Delete(String node) throws MeterFailException{
		String url="http://"+ODL_IP+"/restconf/config/opendaylight-inventory:nodes/node/"+node+"/flow-node-inventory:meter/"+meter_id;
		String responsecode=HttpUtil.Delete_request(url)[0];
		if(!"201".equals(responsecode) && !"200".equals(responsecode) && !"404".equals(responsecode))
			throw new MeterFailException("meter "+this.getMeter_id()+" delete failed");
		/*
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String getcode=request.Get_request(url)[0];
		if(!"404".equals(getcode)){
			throw new MeterFailException("meter "+this.getMeter_id()+" delete failed");
		}
		*/
		
	}


}
