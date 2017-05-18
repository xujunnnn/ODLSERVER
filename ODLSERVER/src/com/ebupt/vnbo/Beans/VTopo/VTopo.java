package com.ebupt.vnbo.Beans.VTopo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.Exception.Mac_MapFailException;
import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;
import com.ebupt.vnbo.Beans.Exception.VBridgeReadException;
import com.ebupt.vnbo.Beans.Exception.VbridgeFailExpection;
import com.ebupt.vnbo.Beans.Exception.VtnFailExpection;
import com.ebupt.vnbo.Beans.Exception.VtnReadException;
import com.ebupt.vnbo.Beans.NetTopology.Host;
import com.ebupt.vnbo.Beans.Vtn.OperationType;
import com.ebupt.vnbo.Beans.Vtn.UpDate_Mode;
import com.ebupt.vnbo.Beans.Vtn.Vtn;
import com.ebupt.vnbo.Util.TopoUtil;
import com.ebupt.vnbo.Util.VTopoUtil;

/**
 * 
 * @author xu
 *
 */
public class VTopo {
	
	private HashSet<VGroup> vGroups=new HashSet<VGroup>();
	private HashSet<VLink> vLinks=new HashSet<VLink>();
	private String Vtopo_name;
	public HashSet<VGroup> getvGroups() {
		return vGroups;
	}
	public VTopo setvGroups(HashSet<VGroup> vGroups) {
		this.vGroups = vGroups;
		return this;
	}
	public HashSet<VLink> getvLinks() {
		return vLinks;
	}
	public VTopo setvLinks(HashSet<VLink> vLinks) {
		this.vLinks = vLinks;
		return this;
	}
	public String getVtopo_name() {
		return Vtopo_name;
	}
	public VTopo setVtopo_name(String vtopo_name) {
		Vtopo_name = vtopo_name;
		return this;
	}
	
	public VTopo addGroup(VGroup vGroup){
		this.vGroups.add(vGroup);
		return this;
	}
	
	public VTopo addLink(VLink vLink){
		this.vLinks.add(vLink);
		return this;
	}
	/**
	 * create the vtopo
	 * @throws VbridgeFailExpection
	 * @throws Mac_MapFailException
	 * @throws VtnFailExpection
	 * @throws TopoReadFailException 
	 * @throws VBridgeReadException 
	 */
	public void create() throws VbridgeFailExpection, Mac_MapFailException, VtnFailExpection, TopoReadFailException, VBridgeReadException{
		VTopoUtil.initVlan(Vtopo_name);
		Vtn vtn=new Vtn();
		vtn.setTenant_name(Vtopo_name)
		   .setHard_timeout("10000").setIdle_timeout("1000")
		   .setOperation(OperationType.SET)
		   .setUpdate_mode(UpDate_Mode.CREATE)
		   .Send();
		for(VGroup vGroup:vGroups){
			vGroup.setVtoponame(Vtopo_name).create();
		}
		for(VLink vLink:vLinks){
			vLink.setVtoponame(Vtopo_name).create();
		}
	}
	
	public void updateAdd() throws TopoReadFailException, VbridgeFailExpection, Mac_MapFailException, VBridgeReadException, VtnReadException{
		for(VGroup vGroup:vGroups){
			vGroup.updateAdd();
		}
		//读取VTopo中的所有VLink
		VTopoReader vTopoReader=new VTopoReader();
		VTopo vTopo=vTopoReader.Adapter(vTopoReader.read(Vtopo_name));
		for(VLink vLink:vTopo.getvLinks()){
			for(VGroup vGroup:vGroups){
				vLink.updateAdd(vGroup);
			}			
		}
		for(VLink vLink:vLinks){
			vLink.create();
		}
	}
	/**
	 * 移除vgroup和vlink
	 * @throws VBridgeReadException
	 */
	public void Remove() throws VBridgeReadException{
		for(VGroup vGroup:vGroups){
			vGroup.remove();
		}
		for(VLink vLink:vLinks){
			vLink.remove();
		}
	}
	/**
	 * 
	 * @throws VtnFailExpection
	 */
	public void delete() throws VtnFailExpection{
		Vtn vtn=new Vtn();
		vtn.setTenant_name(Vtopo_name);
		vtn.delete();
	}
	
	
	
	public static void main(String args[]) throws VtnFailExpection, TopoReadFailException{ 
		VTopo vTopo=new VTopo();
	
		vTopo.setVtopo_name("vtopo14");
		VTopoUtil.initVlan(vTopo.getVtopo_name());
		ArrayList<Host> hosts=new ArrayList<Host>(TopoUtil.get_hosts());
		VGroup vGroup=new VGroup().addHost(hosts.get(0).getHost_name()).addHost(hosts.get(1).getHost_name()).setGroup_id("g1").setVtoponame(vTopo.getVtopo_name());
		VGroup vGroup2=new VGroup().addHost(hosts.get(2).getHost_name()).addHost(hosts.get(3).getHost_name()).setGroup_id("g2").setVtoponame(vTopo.getVtopo_name());
		VGroup vGroup3=new VGroup().addHost(hosts.get(4).getHost_name()).addHost(hosts.get(5).getHost_name()).setGroup_id("g3").setVtoponame(vTopo.getVtopo_name());
		VLink vLink=new VLink();
		vLink.setGroupA(vGroup.getGroup_id()).setGroupB(vGroup2.getGroup_id()).setLink_id(vLink.getGroupA()+"_"+vLink.getGroupB()).setVtoponame(vTopo.getVtopo_name());
		VLink vLink2=new VLink();
		vLink2.setGroupA(vGroup.getGroup_id()).setGroupB(vGroup3.getGroup_id()).setLink_id(vLink2.getGroupA()+"_"+vLink.getGroupB()).setVtoponame(vTopo.getVtopo_name());
		vTopo.addGroup(vGroup).addGroup(vGroup2).addGroup(vGroup3).addLink(vLink).addLink(vLink2);
		JSONObject jsonObject=(JSONObject)JSONObject.toJSON(vTopo);
		System.out.println(jsonObject.toJSONString());
	}
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(vGroups,vLinks,Vtopo_name);
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
		VTopo other=(VTopo) obj;
			return Objects.deepEquals(this.vGroups, other.getvGroups())  &&
							Objects.deepEquals(this.vLinks, other.getvLinks()) && 
							Objects.equals(this.Vtopo_name, other.getVtopo_name());
	}
	
	

}
