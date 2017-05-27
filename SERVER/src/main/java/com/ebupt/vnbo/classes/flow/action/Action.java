package com.ebupt.vnbo.classes.flow.action;

import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class Action {
	private String order;
	@JSONField(name="output-action")
	private OutPut_Action outPut_Action;
	@JSONField(name="set-queue-action")
	private Set_Queue_Action set_Queue_Action;
	public OutPut_Action getOutPut_Action() {
		return outPut_Action;
	}

	public void setOutPut_Action(OutPut_Action outPut_Action) {
		this.outPut_Action = outPut_Action;
	}

	public Set_Queue_Action getSet_Queue_Action() {
		return set_Queue_Action;
	}

	public void setSet_Queue_Action(Set_Queue_Action set_Queue_Action) {
		this.set_Queue_Action = set_Queue_Action;
	}
    

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	public Action Set_Out_Put_Connector(String node_connector){
		OutPut_Action outPut_Action=new OutPut_Action();
		outPut_Action.setMax_length("65535");
		outPut_Action.setOutput_node_connector(node_connector);
		this.outPut_Action=outPut_Action;
		return this;
	}
	public Action Set_Queue_Id(String queue_id){
		Set_Queue_Action set_Queue_Action=new Set_Queue_Action();
		set_Queue_Action.setQueue(queue_id);
		this.set_Queue_Action=set_Queue_Action;
		return this;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(order,outPut_Action,set_Queue_Action);
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
	    Action other=(Action) obj;
	    	return Objects.equals(this.order, other.getOrder()) &&
	    			Objects.equals(this.outPut_Action, other.getOutPut_Action()) &&
	    			Objects.equals(this.set_Queue_Action, other.getSet_Queue_Action());
	}
	
	
	
	
}


/**
 * 
 * @author xu
 *
 */
class OutPut_Action{
	@JSONField(name="output-node-connector")
	private String output_node_connector;
	@JSONField(name="max-length")
	private String max_length;
	public String getOutput_node_connector() {
		return output_node_connector;
	}
	public void setOutput_node_connector(String output_node_connector) {
		this.output_node_connector = output_node_connector;
	}
	public String getMax_length() {
		return max_length;
	}
	public void setMax_length(String max_length) {
		this.max_length = max_length;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(output_node_connector,max_length);
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
	    OutPut_Action other=(OutPut_Action) obj;
	    	return Objects.equals(this.output_node_connector, other.output_node_connector) &&
	    			Objects.equals(this.max_length, other.getMax_length());
	}
	
}


/**
 * 
 * @author xu
 *
 */
class Set_Queue_Action{
	@JSONField(name="queue-id")
	private String queue;
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getQueue_id() {
		return queue_id;
	}
	public void setQueue_id(String queue_id) {
		this.queue_id = queue_id;
	}
	private String queue_id;
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(queue,queue_id);
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
	    Set_Queue_Action other=(Set_Queue_Action) obj;
	    	return Objects.equals(this.queue_id, other.getQueue_id()) && Objects.equals(this.queue,other.getQueue());
	}
	
}
