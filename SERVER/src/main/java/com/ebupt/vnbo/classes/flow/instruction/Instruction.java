package com.ebupt.vnbo.classes.flow.instruction;

import java.util.ArrayList;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
public class Instruction {
	private String order;
	@JSONField(name="apply-actions")
	private Apply_Actions apply_Actions;
	@JSONField(name="go-to-table")
	private Go_To_Table go_To_Table;
	@JSONField(name="meter")
	private Meter_Case meter_Case;
	public Meter_Case getMeter_Case() {
		return meter_Case;
	}
	public void setMeter_Case(Meter_Case meter_Case) {
		this.meter_Case = meter_Case;
	}
	public String getOrder() {
		return order;
	}
	public Instruction setOrder(String order) {
		this.order = order;
		return this;
	}

	public Go_To_Table getGo_To_Table() {
		return go_To_Table;
	}
	public Instruction setGo_To_Table(Go_To_Table go_To_Table) {
		this.go_To_Table = go_To_Table;
		return this;
	}

	public Instruction Set_Go_To_Table_Id(String table_id){
		Go_To_Table go_To_Table=new Go_To_Table();
		go_To_Table.setTable_id(table_id);
	    this.setGo_To_Table(go_To_Table);
		return this;
	}
	public Instruction Set_Meter(String meter){
		Meter_Case meter_case=new Meter_Case();
		meter_case.setMeter(meter);
		this.setMeter_Case(meter_case);
		return this;
	}

	public Apply_Actions getApply_Actions() {
		return apply_Actions;
	}
	public Instruction setApply_Actions(Apply_Actions apply_Actions) {
		this.apply_Actions = apply_Actions;
		return this;
	}
	public static void main(String []args){
		Instruction instruction=new Instruction();
		Meter_Case meter_Case=new Meter_Case().setMeter("11");
		instruction.setMeter_Case(meter_Case);
		System.out.println(JSON.toJSON(instruction));
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(order,apply_Actions,go_To_Table,meter_Case);
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
	    Instruction other=(Instruction) obj;
	    	return Objects.equals(this.order, other.getOrder()) && Objects.equals(this.apply_Actions, other.getApply_Actions())
	    	&& Objects.equals(this.go_To_Table, other.getGo_To_Table()) && Objects.equals(this.meter_Case, other.getMeter_Case());
	}
    
}
	

