package com.ebupt.vnbo.Beans.VTopo;

import java.util.HashSet;
import java.util.Objects;

import org.apache.ibatis.reflection.wrapper.BaseWrapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.Exception.Mac_MapFailException;
import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.Exception.VBridgeReadException;
import com.ebupt.vnbo.Beans.Exception.VbridgeFailExpection;
import com.ebupt.vnbo.Beans.Vtn.Allowed_Hosts;
import com.ebupt.vnbo.Beans.Vtn.OperationType;
import com.ebupt.vnbo.Beans.Vtn.UpDate_Mode;
import com.ebupt.vnbo.Beans.Vtn.Vbridge;
import com.ebupt.vnbo.Util.TopoUtil;

public class VGroup {
	private HashSet<String> host_names=new HashSet<String>();
	private String group_id;
	private String vtoponame;
	public VGroup(){}
	
	public String getVtoponame() {
		return vtoponame;
	}

	public VGroup setVtoponame(String vtoponame) {
		this.vtoponame = vtoponame;
		return this;
	}

	/**
	 * constructor
	 * @param host_names
	 */
	public VGroup(HashSet<String> host_names){
		this.host_names=host_names;
	}
	/**
	 * add a host to this group
	 * @param host_name
	 * @return
	 */
	public VGroup addHost(String host_name){
		this.host_names.add(host_name);
		return this;
	}
	public VGroup addGroup(VGroup group){
		this.host_names.addAll(group.getHost_names());
		return this;
	}
	
	public HashSet<String> getHost_names() {
		return host_names;
	}

	public VGroup setHost_names(HashSet<String> host_names) {
		this.host_names = host_names;
		return this;
	}

	public String getGroup_id() {
		return group_id;
	}

	public VGroup setGroup_id(String group_id) {
		this.group_id = group_id;
		return this;
	}
    /**
     * create the VGroup
     * @throws VbridgeFailExpection
     * @throws Mac_MapFailException
     * @throws TopoReadFailException 
     */
	public void create() throws VbridgeFailExpection, Mac_MapFailException, TopoReadFailException{
		Vbridge vbridge=new Vbridge();
		Allowed_Hosts allowed_Hosts=new Allowed_Hosts();
		for(String name:host_names){
			allowed_Hosts.addHost(TopoUtil.get_host_from_name(name).getMac()+"@"+"0");
			}
		vbridge.setTenant_name(vtoponame)
			   .setBridge_name(group_id)
			   .setUpdate_mode(UpDate_Mode.CREATE)
			   .setOperation(OperationType.SET)
			   .Set_Mac_Map(allowed_Hosts, null)
			   .send();
				
			   
	}
	/**
	 * 在原有的Vbridgei基础上进行增量设置
	 * @throws TopoReadFailException
	 * @throws VbridgeFailExpection
	 * @throws Mac_MapFailException
	 */
	public void updateAdd() throws TopoReadFailException, VbridgeFailExpection, Mac_MapFailException{
		Vbridge vbridge=new Vbridge();
		Allowed_Hosts allowed_Hosts=new Allowed_Hosts();
		for(String name:host_names){
			allowed_Hosts.addHost(TopoUtil.get_host_from_name(name).getMac()+"@"+"0");
			}
		//将macmap设置为ADD模式
		vbridge.getMac_Map().setOperation(OperationType.ADD);
		vbridge.setTenant_name(vtoponame)
					   .setBridge_name(group_id)
					   .Set_Mac_Map(allowed_Hosts, null)
					   .setOperation(OperationType.ADD)
					   .setUpdate_mode(UpDate_Mode.UPDATE)
					   .send();	
	}
	/**
	 * 在原有vbridge基础上进行删除操作
	 * @throws TopoReadFailException
	 * @throws VbridgeFailExpection
	 * @throws Mac_MapFailException
	 */
	public void updateRemove() throws TopoReadFailException, VbridgeFailExpection, Mac_MapFailException{
		Vbridge vbridge=new Vbridge();
		Allowed_Hosts allowed_Hosts=new Allowed_Hosts();
		for(String name:host_names){
			allowed_Hosts.addHost(TopoUtil.get_host_from_name(name).getMac()+"@"+"0");
			}
		vbridge.getMac_Map().setOperation(OperationType.REMOVE);
		vbridge.setTenant_name(vtoponame)
			           .setBridge_name(group_id)
			           .Set_Mac_Map(allowed_Hosts, null)
			           .setOperation(OperationType.REMOVE)
			           .setUpdate_mode(UpDate_Mode.UPDATE)
			           .send();
		
	}
	/**将Vgroup删除
	 * @throws VBridgeReadException 
	 * 
	 */
	public void remove() throws VBridgeReadException{
		Vbridge vbridge=new Vbridge();
		vbridge.setBridge_name(group_id)
					  .setTenant_name(vtoponame)
					  .delete();
		
	}
	
	
	public static void main(String args[]){
		VGroup vGroup=new VGroup().setGroup_id("g1");
		vGroup.addHost("host1")
			  .addHost("host2")
			  .addHost("host3");
		JSONObject json=(JSONObject)JSONObject.toJSON(vGroup);
		System.out.println(json.toJSONString());
		
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(this.host_names,this.group_id);
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return false;
		if(this==obj)
			return true;
		if(this.getClass()!=obj.getClass())
			return false;
		VGroup other=(VGroup) obj;
		return Objects.equals(this.host_names, other.getHost_names()) && Objects.equals(this.group_id, other.getGroup_id());
	}
}
