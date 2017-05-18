package com.ebupt.vnbo.Beans.Instruction;

import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;

public class Meter_Case{
	@JSONField(name="meter-id")
	private String meter_id;

	public String getMeter_id() {
		return meter_id;
	}
	@JSONField(name="meter-id")
	public Meter_Case setMeter(String meter_id) {
		this.meter_id = meter_id;
		return this;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(meter_id);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this==obj)
			return true;
		if(obj==null)
			return false;
	    if(this.getClass()!=obj.getClass())
	    	return false;
	    Meter_Case other=(Meter_Case) obj;
	    	return Objects.equals(this.meter_id, other.getMeter_id());
	}
}
	