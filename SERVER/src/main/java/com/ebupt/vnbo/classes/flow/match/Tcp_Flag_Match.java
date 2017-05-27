package com.ebupt.vnbo.classes.flow.match;

import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;

public class Tcp_Flag_Match {
	@JSONField(name="tcp-flag")
	private String tcp_flag;
	public String getTcp_flag() {
		return tcp_flag;
	}
	public void setTcp_flag(String tcp_flag) {
		this.tcp_flag = tcp_flag;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(tcp_flag);
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
		Tcp_Flag_Match other=(Tcp_Flag_Match) obj;
			return Objects.equals(this.tcp_flag, other.tcp_flag);
	}
	
}
