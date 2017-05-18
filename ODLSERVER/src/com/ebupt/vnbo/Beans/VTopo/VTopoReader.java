package com.ebupt.vnbo.Beans.VTopo;

import java.util.HashSet;
import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.Exception.VtnReadException;
import com.ebupt.vnbo.Beans.Vtn.Hostmc;
import com.ebupt.vnbo.Beans.Vtn.VbridgeRead;
import com.ebupt.vnbo.Beans.Vtn.VtnRead;
import com.ebupt.vnbo.Util.HttpUtil;
import com.ebupt.vnbo.Util.Util;

/**
 * 该类拥有读取vtopo信息
 * @author xu
 *
 */
public class VTopoReader {
	private static String ODL_IP=Util.getODL_IP();
	/**
	 * 读取所有VTopo信息
	 * @return
	 * @throws VtnReadException 
	 */
	public VTopoRead read() throws VtnReadException{
		String url="http://"+ODL_IP+"/restconf/operational/vtn:vtns";
		String response[]=HttpUtil.Get_request(url);
		String responsecode=response[0];
		if(!"200".equals(responsecode)  &&  !"201".equals(responsecode))
				throw new VtnReadException("failed to read the VTopo");
		String responsebody=response[1];
		JSONObject resultjson=JSONObject.parseObject(responsebody);
		VTopoRead vTopoRead=JSONObject.parseObject(resultjson.getJSONObject("vtns").toJSONString(), VTopoRead.class);
		 return vTopoRead;
		
	}
	/**
	 * 读取指定的Vtopo信息
	 * @param vtoponame
	 * @return
	 * @throws VtnReadException
	 */
	public VtnRead read(String vtoponame) throws VtnReadException{
		String url="http://"+ODL_IP+"/restconf/operational/vtn:vtns/vtn/"+vtoponame;
		String response[]=HttpUtil.Get_request(url);
		String responsecode=response[0];
		if(!"200".equals(responsecode)  &&  !"201".equals(responsecode))
				throw new VtnReadException("failed to read the VTopo");
		String responsebody=response[1];
		JSONObject resultjson=JSONObject.parseObject(responsebody);
		VtnRead vtnRead=JSONObject.parseObject(resultjson.getJSONArray("vtn").getJSONObject(0).toJSONString(), VtnRead.class);
		return vtnRead;
	
		
	}
/**
 * 
 * @param vTopoRead
 * @return
 */
	public HashSet< VTopo> Adapter(VTopoRead vTopoRead){
		HashSet<VTopo> vTopos=new HashSet<>();
		for(VtnRead vtnRead:vTopoRead.getVtnReads()){
			VTopo vTopo=new VTopo().setVtopo_name(vtnRead.getName());
			for(VbridgeRead vbridgeRead:vtnRead.getVbridgeReads()){
				if(!vbridgeRead.getName().contains("_")){
				VGroup vGroup=new VGroup().setGroup_id(vbridgeRead.getName());
				HashSet<String> host_names=new HashSet<>();
				if(vbridgeRead.getMac_Map()!=null){
				for(Hostmc hostmc:vbridgeRead.getMac_Map().getMac_Map_Config().getAllowedHosts().getVlan_host_desc_list()){
						host_names.add(hostmc.getHostName());
				}
				}
				vGroup.setHost_names(host_names);
				vTopo.addGroup(vGroup);
				}
				else{
					VLink vLink=new VLink().setLink_id(vbridgeRead.getName());
					String linkname=vbridgeRead.getName();
					String groupA=linkname.split("_")[0];
					String groupB=linkname.split("_")[1];
					int vlanid=Integer.parseInt(linkname.split("_")[2]);
					vLink.setGroupA(groupA).setGroupB(groupB).setVlanid(vlanid);
					vTopo.addLink(vLink);
				}
			}
			vTopos.add(vTopo);
		}
		return vTopos;
	}
	/**
	 * 将vtnread转化为Vtopo
	 * @param vtnRead
	 * @return
	 */
	public VTopo Adapter(VtnRead vtnRead){
		VTopo vTopo=new VTopo().setVtopo_name(vtnRead.getName());
		for(VbridgeRead vbridgeRead:vtnRead.getVbridgeReads()){
			if(!vbridgeRead.getName().contains("_")){
			VGroup vGroup=new VGroup().setGroup_id(vbridgeRead.getName());
			HashSet<String> host_names=new HashSet<>();
			if(vbridgeRead.getMac_Map()!=null){
			for(Hostmc hostmc:vbridgeRead.getMac_Map().getMac_Map_Config().getAllowedHosts().getVlan_host_desc_list()){
					host_names.add(hostmc.getHostName());
			}
			}
			vGroup.setHost_names(host_names);
			vTopo.addGroup(vGroup);
			}
			else{
				VLink vLink=new VLink().setLink_id(vbridgeRead.getName());
				String linkname=vbridgeRead.getName();
				String groupA=linkname.split("_")[0];
				String groupB=linkname.split("_")[1];
				int vlanid=Integer.parseInt(linkname.split("_")[2]);
				vLink.setGroupA(groupA).setGroupB(groupB).setVlanid(vlanid);
				vTopo.addLink(vLink);
			}
		}
			return vTopo;

	}
	public static void main(String []args) throws VtnReadException{
		VTopoReader vTopoReader=new VTopoReader();
		System.out.println(JSONObject.toJSONString(vTopoReader.Adapter(vTopoReader.read("vn3"))));      
	}
}
