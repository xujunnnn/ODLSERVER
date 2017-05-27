package com.ebupt.vnbo.classes.flow.match;

import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;

public class Ethernet_destination {
	private String address;
	@JSONField(name="mask")
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
	public void setMask(String mask) {
		Mask = mask;
	}
	@Override
	public boolean equals(Object obj){
		if(obj==this) 
			return true;
		if(obj==null)
			return false;
		if(this.getClass()!=obj.getClass())
			return false;
		
		Ethernet_destination other=(Ethernet_destination) obj;
		return(Objects.equals(other.address,this.address) && Objects.equals(other.address, this.address));
		
	}
	@Override   
	public int hashCode(){
		return Objects.hash(this.address,this.Mask);
	}
}
