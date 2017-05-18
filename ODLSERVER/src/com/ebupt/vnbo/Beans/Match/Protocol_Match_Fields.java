package com.ebupt.vnbo.Beans.Match;

import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;

public class Protocol_Match_Fields {
	@JSONField(name="mpls-label")
	private String mpls_label;
	@JSONField(name="mpls-tc")
	private String mpls_tc;
	@JSONField(name="mpls-bos")
	private String mpls_bos;
	public String getMpls_label() {
		return mpls_label;
	}
	public void setMpls_label(String mpls_label) {
		this.mpls_label = mpls_label;
	}
	public String getMpls_tc() {
		return mpls_tc;
	}
	public void setMpls_tc(String mpls_tc) {
		this.mpls_tc = mpls_tc;
	}
	public String getMpls_bos() {
		return mpls_bos;
	}
	public void setMpls_bos(String mpls_bos) {
		this.mpls_bos = mpls_bos;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(mpls_bos,mpls_label,mpls_tc);
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
		Protocol_Match_Fields other=(Protocol_Match_Fields) obj;
			return Objects.equals(this.mpls_bos, other.getMpls_bos()) && Objects.equals(this.mpls_label,other.getMpls_label())
					&& Objects.equals(this.mpls_tc, other.mpls_tc);
	}

}
