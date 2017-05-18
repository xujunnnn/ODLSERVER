package com.ebupt.vnbo.Beans.Match;

import java.util.Objects;
import com.alibaba.fastjson.annotation.JSONField;

public class Icmpv4_Match {
	@JSONField(name="icmpv4-type")
	private String icmpv4_type;
	@JSONField(name="icmpv4-code")
	private String icmpv4_code;
	public String getIcmpv4_type() {
	return icmpv4_type;
}
	public void setIcmpv4_type(String icmpv4_type) {
	this.icmpv4_type = icmpv4_type;
}
	public String getIcmpv4_code() {
	return icmpv4_code;
}
	public void setIcmpv4_code(String icmpv4_code) {
	this.icmpv4_code = icmpv4_code;
}
	public Icmpv4_Match(String icmpv4_type,String icmpv4_code){
		if (icmpv4_code!=null)
		this.icmpv4_code=icmpv4_code;
		if(icmpv4_type!=null)
		this.icmpv4_type=icmpv4_type;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(this.icmpv4_code,icmpv4_type);
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
		Icmpv4_Match other=(Icmpv4_Match) obj;
			return Objects.equals(this.icmpv4_code,other.getIcmpv4_code())&& Objects.equals(this.icmpv4_type, other.getIcmpv4_type());
	}

}
