package com.ebupt.vnbo.Beans.Match;

import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;
import com.sun.org.apache.regexp.internal.recompile;

public class Ethernet_source {
	private String address;
	@JSONField(name="Mask")
	private String Mask;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMask() {
		return Mask;
	}
	public void setMask(String Mask) {
		Mask = Mask;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==this) 
			return true;
		if(obj==null)
			return false;
		if(this.getClass()!=obj.getClass())
			return false;
		
		Ethernet_source other=(Ethernet_source) obj;
		return(Objects.equals(other.address,this.address) && Objects.equals(other.address, this.address));
		
	}
	@Override
	public int hashCode(){
		return Objects.hash(this.address,this.Mask);
	}
}
