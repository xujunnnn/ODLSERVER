package com.ebupt.vnbo.Beans.Instruction;

import java.util.ArrayList;
import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.Beans.Action.Action;

public class Apply_Actions{
	@JSONField(name="action")
	private ArrayList<Action> actions=new ArrayList<Action>();

	public ArrayList<Action> getActions() {
		return actions;
	}
    public Apply_Actions(){}
	public void setAction(ArrayList<Action> action) {
		this.actions = action;
	}
	public Apply_Actions addAction(Action action){
		this.actions.add(action);
		return this;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(actions);
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
	    Apply_Actions other=(Apply_Actions) obj;
	    	return Objects.deepEquals(this.actions, other.getActions());
	}
	


	
}