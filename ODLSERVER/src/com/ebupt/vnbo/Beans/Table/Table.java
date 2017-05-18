package com.ebupt.vnbo.Beans.Table;

import java.util.ArrayList;

import com.ebupt.vnbo.Beans.Flow.Flow;
/**
 * 
 * @author xu
 *
 */
public class Table {
	private ArrayList<Flow> flow=new ArrayList<Flow>();
    private String id;
	public ArrayList<Flow> getFlow() {
		return flow;
	}

	public void setFlow(ArrayList<Flow> flow) {
		this.flow = flow;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
