package com.ebupt.vnbo.Beans.NetTopology;

import java.util.ArrayList;

import com.alibaba.fastjson.annotation.JSONField;

public class Node {
	@JSONField(name="node-id")
	private String node_id;
	@JSONField(name="host-tracker-service:attachment-points")
    private ArrayList<Host_tracker_service_attachment_points> host_tracker_service_attachment_points=new ArrayList<>();
	@JSONField(name="termination-point")
	private ArrayList<Termination_point> termination_points=new ArrayList<>();
	@JSONField(name="host-tracker-service:addresses")
	private ArrayList<Host_tracker_service_address> host_tracker_service_addresses=new ArrayList<>();
	public String getNode_id() {
		return node_id;
	}
	public ArrayList<Termination_point> getTermination_points() {
		return termination_points;
	}
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
	public void setTermination_points(ArrayList<Termination_point> termination_points) {
		this.termination_points = termination_points;
	}
	public ArrayList<Host_tracker_service_attachment_points> getHost_tracker_service_attachment_points() {
		return host_tracker_service_attachment_points;
	}
	public void setHost_tracker_service_attachment_points(ArrayList<Host_tracker_service_attachment_points> host_tracker_service_attachment_points) {
		this.host_tracker_service_attachment_points = host_tracker_service_attachment_points;
	}
	public ArrayList<Host_tracker_service_address> getHost_tracker_service_addresses() {
		return host_tracker_service_addresses;
	}
	public void setHost_tracker_service_addresses(ArrayList<Host_tracker_service_address> host_tracker_service_addresses) {
		this.host_tracker_service_addresses = host_tracker_service_addresses;
	}
	
}
