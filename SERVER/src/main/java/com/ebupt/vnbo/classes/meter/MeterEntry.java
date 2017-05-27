package com.ebupt.vnbo.classes.meter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.classes.abstracts.Config;
import com.ebupt.vnbo.classes.abstracts.Operational;
import com.ebupt.vnbo.classes.exception.ConfigException;
import com.ebupt.vnbo.classes.exception.OperationalException;
import com.ebupt.vnbo.classes.meter.band_header.Meter_Band_Header;
import com.ebupt.vnbo.classes.meter.band_header.Meter_Band_Headers;
import com.ebupt.vnbo.util.HttpUtil;
public class MeterEntry implements Config,Operational {
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
	public MeterEntry setMeter_id(String meter_id) {
		this.meter_id = meter_id;
		return this;
	}
	public String getMeter_name() {
		return meter_name;
	}
	public MeterEntry setMeter_name(String meter_name) {
		this.meter_name = meter_name;
		return this;
	}
	public String getContainer_name() {
		return container_name;
	}
	public MeterEntry setContainer_name(String container_name) {
		this.container_name = container_name;
		return this;
	}
	public String getFlags() {
		return flags;
	}
	public MeterEntry setFlags(String flags) {
		this.flags = flags;
		return this;
	}
	public Meter_Band_Headers getMeter_band_headers() {
		return meter_band_headers;
	}
	public MeterEntry setMeter_band_headers(Meter_Band_Headers meter_band_headers) {
		this.meter_band_headers = meter_band_headers;
		return this;
	}
	/**
	 * set drop-rate for the meter
	 * @param droprate
	 * @return
	 */
	public MeterEntry Set_drop_rate(String droprate){
		Meter_Band_Header meter_Band_Header=new Meter_Band_Header().setBand_burst_size("100").setBand_id(meter_id).setBand_rate("100").setDrop_burst_size("100").setDrop_rate(droprate);
		meter_Band_Header.Set_Type("ofpmbt-drop");
		Meter_Band_Headers meter_Band_Headers=new Meter_Band_Headers();
		meter_Band_Headers.addMeter_Band_Header(meter_Band_Header);
		this.meter_band_headers=meter_Band_Headers;
		return this;
	}

	public Operational read(String node) throws OperationalException {
		// TODO Auto-generated method stub
		String url="http://"+OperationalUrl+"/opendaylight-inventory:nodes/node/"+node+"/flow-node-inventory:meter/"+meter_id;
		String responsecode=HttpUtil.Delete_request(url)[0];
		if(!"201".equals(responsecode) && !"200".equals(responsecode) )
			throw new OperationalException("meter "+this.getMeter_id()+" read failed");
		return null;

	}
	public void remove(String node) throws ConfigException {
		// TODO Auto-generated method stub
		String url="http://"+ConfigUrl+"/opendaylight-inventory:nodes/node/"+node+"/flow-node-inventory:meter/"+meter_id;
		String responsecode=HttpUtil.Delete_request(url)[0];
		if(!"201".equals(responsecode) && !"200".equals(responsecode) && !"404".equals(responsecode))
			throw new ConfigException("meter "+this.getMeter_id()+" delete failed");
		
	}
	public void send(String node) throws ConfigException {
		// TODO Auto-generated method stub
		String url="http://"+ConfigUrl+"/opendaylight-inventory:nodes/node/"+node+"/flow-node-inventory:meter/"+meter_id;
	    JSONArray jsonArray=new JSONArray();
	    jsonArray.add(JSONObject.parse(JSON.toJSONString(this)));
	    JSONObject jsonObject=new JSONObject();   
	    jsonObject.put("flow-node-inventory:meter", jsonArray);
		String responsecode=HttpUtil.Put_request(url, jsonObject)[0];
		if(!"200".equals(responsecode) && !"201".equals(responsecode))
			throw new ConfigException("meter"+this.getMeter_id()+"sended to "+node+"fail");
		
	}


}
