package com.ebupt.vnbo.Beans.Meter;

import java.util.Objects;

import javax.print.attribute.standard.MediaSize.Other;

import com.alibaba.fastjson.annotation.JSONField;

public class Meter_Band_Header {
	@JSONField(name="band-id")
	private String band_id;
	@JSONField(name="band-rate")
	private String band_rate;
	@JSONField(name="band-burst-size")
	private String band_burst_size;
	@JSONField(name="drop-rate")
	private String drop_rate;
	@JSONField(name="drop-burst-size")
	private String drop_burst_size;
	@JSONField(name="dscp-remark-burst-size")
	private String dscp_remark_burst_size;
	@JSONField(name="dscp-remark-rate")
	private String dscp_remark_rate;
	@JSONField(name="meter-band-types")
	private Meter_Band_Types meter_Band_Types;
	public String getDscp_remark_burst_size() {
		return dscp_remark_burst_size;
	}
	public Meter_Band_Header setDscp_remark_burst_size(String dscp_remark_burst_size) {
		this.dscp_remark_burst_size = dscp_remark_burst_size;
		return this;
	}
	public String getDscp_remark_rate() {
		return dscp_remark_rate;
	}
	public Meter_Band_Header setDscp_remark_rate(String dscp_remark_rate) {
		this.dscp_remark_rate = dscp_remark_rate;
		return this;
	}
	public String getPrec_level() {
		return prec_level;
	}
	public Meter_Band_Header setPrec_level(String prec_level) {
		this.prec_level = prec_level;
		return this;
	}
	private String prec_level;
	public String getBand_id() {
		return band_id;
	}
	public Meter_Band_Header setBand_id(String band_id) {
		this.band_id = band_id;
		return this;
	}
	public String getBand_rate() {
		return band_rate;
		
	}
	public Meter_Band_Header setBand_rate(String band_rate) {
		this.band_rate = band_rate;
		return this;
	}
	public String getBand_burst_size() {
		return band_burst_size;
		
	}
	public Meter_Band_Header setBand_burst_size(String band_burst_size) {
		this.band_burst_size = band_burst_size;
		return this;
	}
	public String getDrop_rate() {
		return drop_rate;
		
	}
	public Meter_Band_Header setDrop_rate(String drop_rate) {
		this.drop_rate = drop_rate;
		return this;
	}
	public String getDrop_burst_size() {
		return drop_burst_size;
	}
	public Meter_Band_Header setDrop_burst_size(String drop_burst_size) {
		this.drop_burst_size = drop_burst_size;
		return this;
	}
	public Meter_Band_Types getMeter_Band_Types() {
		return meter_Band_Types;
	}
	public Meter_Band_Header setMeter_Band_Types(Meter_Band_Types meter_Band_Types) {
		this.meter_Band_Types = meter_Band_Types;
        return this;
	}
	public Meter_Band_Header Set_Type(String type){
		Meter_Band_Types meter_Band_Types=new Meter_Band_Types();
		meter_Band_Types.setFlags(type);
		this.meter_Band_Types=meter_Band_Types;getClass();
		return this; 
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(band_id,band_rate,band_burst_size,drop_rate,drop_burst_size,dscp_remark_burst_size,dscp_remark_rate,meter_Band_Types);
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj==this) 
			return true;
		if(obj==null)
			return false;
		if(this.getClass()!=obj.getClass())
			return false;
		Meter_Band_Header other=(Meter_Band_Header) obj;
			return Objects.equals(this.band_id, other.getBand_id()) && Objects.equals(this.band_rate, other.band_rate)
			&& Objects.equals(this.band_burst_size, other.band_burst_size) && Objects.equals(this.drop_rate, other.drop_rate)
			&& Objects.equals(this.drop_burst_size, other.drop_burst_size) && Objects.equals(this.dscp_remark_burst_size, other.dscp_remark_burst_size)
			&& Objects.equals(this.dscp_remark_burst_size, other.getDscp_remark_burst_size()) && Objects.equals(this.dscp_remark_rate, other.dscp_remark_rate)
			&& Objects.equals(this.meter_Band_Types,other.getMeter_Band_Types());
	}
	
	

}
class Meter_Band_Types{
	private String flags;
	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(flags);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj==this) 
			return true;
		if(obj==null)
			return false;
		if(this.getClass()!=obj.getClass())
			return false;
		Meter_Band_Types other=(Meter_Band_Types) obj;
			return Objects.equals(this.flags,other.getFlags());
	}
	
}
