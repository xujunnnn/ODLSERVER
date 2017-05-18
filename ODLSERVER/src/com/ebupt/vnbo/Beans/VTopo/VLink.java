package com.ebupt.vnbo.Beans.VTopo;

import java.util.HashSet;
import java.util.Objects;

import javax.crypto.Mac;

import com.alibaba.fastjson.annotation.JSONField;
import com.ebupt.vnbo.Beans.Exception.Mac_MapFailException;
import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.Exception.VBridgeReadException;
import com.ebupt.vnbo.Beans.Exception.VbridgeFailExpection;
import com.ebupt.vnbo.Beans.Vtn.Allowed_Hosts;
import com.ebupt.vnbo.Beans.Vtn.Hostmc;
import com.ebupt.vnbo.Beans.Vtn.Mac_Map_Config;
import com.ebupt.vnbo.Beans.Vtn.OperationType;
import com.ebupt.vnbo.Beans.Vtn.UpDate_Mode;
import com.ebupt.vnbo.Beans.Vtn.Vbridge;
import com.ebupt.vnbo.Util.TopoUtil;
import com.ebupt.vnbo.Util.VTNUtil;
import com.ebupt.vnbo.Util.VTopoUtil;
/**
 * 
 * @author xu
 *
 */
public class VLink {
	private String link_id;
	private String groupA;
	private String groupB;
	@JSONField(deserialize=true)
	private int vlanid;
	
	public int getVlanid() {
		return vlanid;
	}
	public VLink setVlanid(int vlanid) {
		this.vlanid = vlanid;
		return this;
	}
	private String vtoponame;
	public String getLink_id() {
		return link_id;
	}
	public String getGroupA() {
		return groupA;
	}
	public VLink setGroupA(String groupA) {
		this.groupA = groupA;
		return this;
	}
	public String getGroupB() {
		return groupB;
	}
	public VLink setGroupB(String groupB) {
		this.groupB = groupB;
		return this;
	}
	public VLink setLink_id(String link_id) {
		this.link_id = link_id;
		return this;
	}	
	public String getVtoponame() {
		return vtoponame;
	}
	public VLink setVtoponame(String vtoponame) {
		this.vtoponame = vtoponame;
		return this;
	}
	/**
	 * 
	 * @throws VbridgeFailExpection
	 * @throws Mac_MapFailException
	 * @throws VBridgeReadException
	 */
	public  void create() throws VbridgeFailExpection, Mac_MapFailException, VBridgeReadException{
		Allowed_Hosts allowed_Hosts=new Allowed_Hosts();
		//读取vgropu中的主机列表
		Mac_Map_Config mac_Map_Config=VTNUtil.readMac_Map_Config(vtoponame,groupA);
		 this.vlanid=VTopoUtil.get_Vlan(vtoponame);
		for(Hostmc host:mac_Map_Config.getAllowedHosts().getVlan_host_desc_list()){
			allowed_Hosts.addHost(host.getHost().split("@")[0]+"@"+vlanid);
		}
		//读取vgropu中的主机列表
		mac_Map_Config=VTNUtil.readMac_Map_Config(vtoponame,groupB);
		for(Hostmc host:mac_Map_Config.getAllowedHosts().getVlan_host_desc_list()){
			allowed_Hosts.addHost(host.getHost().split("@")[0]+"@"+vlanid);
		}
		//linkid改为groupname+vlanid
		link_id=link_id+"_"+vlanid;
		Vbridge vbridge=new Vbridge();
		vbridge.setTenant_name(vtoponame)
			   .setUpdate_mode(UpDate_Mode.CREATE)
			   .setOperation(OperationType.SET)
			   .setBridge_name(link_id)
			   .Set_Mac_Map(allowed_Hosts,null)
			   .send();
		
	}
	/**
	 * 在原有的Vlink基础上进行增量设置
	 * @throws TopoReadFailException
	 * @throws VbridgeFailExpection
	 * @throws Mac_MapFailException
	 */
	public void updateAdd(VGroup vGroup) throws TopoReadFailException, VbridgeFailExpection, Mac_MapFailException{
		Vbridge vbridge=new Vbridge();
		Allowed_Hosts allowed_Hosts=new Allowed_Hosts();
		for(String name:vGroup.getHost_names()){
			allowed_Hosts.addHost(TopoUtil.get_host_from_name(name).getMac()+"@"+this.vlanid);
			}
		//linkid改为groupname+vlanid
		link_id=link_id+"_"+vlanid;
		//将macmap设置为ADD模式
		vbridge.getMac_Map().setOperation(OperationType.ADD);
		vbridge.setTenant_name(vtoponame)
					   .setBridge_name(link_id)
					   .Set_Mac_Map(allowed_Hosts, null)
					   .setOperation(OperationType.ADD)
					   .setUpdate_mode(UpDate_Mode.UPDATE)
					   .send();	
	}
	/**
	 * 在原有VLink基础上进行remove操作
	 * @param vGroup
	 * @throws TopoReadFailException
	 * @throws VbridgeFailExpection
	 * @throws Mac_MapFailException
	 */
	public void updateRemove(VGroup vGroup) throws TopoReadFailException, VbridgeFailExpection, Mac_MapFailException{
		Vbridge vbridge=new Vbridge();
		Allowed_Hosts allowed_Hosts=new Allowed_Hosts();
		for(String name:vGroup.getHost_names()){
			allowed_Hosts.addHost(TopoUtil.get_host_from_name(name).getMac()+"@"+this.vlanid);
			}
		//linkid改为groupname+vlanid
		link_id=link_id+"_"+vlanid;
		//将macmap设置为ADD模式
		vbridge.getMac_Map().setOperation(OperationType.REMOVE);
		vbridge.setTenant_name(vtoponame)
					   .setBridge_name(link_id)
					   .Set_Mac_Map(allowed_Hosts, null)
					   .setOperation(OperationType.REMOVE)
					   .setUpdate_mode(UpDate_Mode.UPDATE)
					   .send();	
	}
	/**
	 * 删除VLink
	 * @throws VBridgeReadException
	 */
	public void remove() throws VBridgeReadException{
		Vbridge vbridge=new Vbridge();
		//linkid改为groupname+vlanid
		link_id=link_id+"_"+vlanid;
		vbridge.setBridge_name(link_id).setTenant_name(vtoponame).delete();
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(link_id,groupA,groupB,vtoponame);
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
		VLink other=(VLink) obj;
			return Objects.equals(this.link_id,other.getLink_id()) &&
					        Objects.equals(this.groupA, other.getGroupA()) &&
					        Objects.equals(this.groupB, other.getGroupB()) &&
					        Objects.equals(this.vtoponame,other.getVtoponame());
	}
	
}
