package com.ebupt.vnbo.Beans.Flow;

import com.ebupt.vnbo.Beans.Exception.FlowFailException;
import com.ebupt.vnbo.Beans.Instruction.Instruction;
import com.ebupt.vnbo.Beans.Instruction.Instructions;
import com.ebupt.vnbo.Beans.Match.Match;
import com.ebupt.vnbo.Beans.NetMonitor.Flow_Statistic;
import com.ebupt.vnbo.Util.HttpUtil;
import com.ebupt.vnbo.Util.Util;

import java.util.ArrayList;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;




/**
 * @author xu
 *
 */
/**
 * @author xu
 *
 */
public class Flow {
	private static String ODL_IP=Util.getODL_IP();
	@JSONField(name="flow-name")
	private String flow_name;
	private String  priority;
	@JSONField(name="idle-timeout")
	private String  idle_timeout;
	@JSONField(name="hard-timeout")
	private String  hard_timeout;
	@JSONField(name="opendaylight-flow-statistics:flow-statistics")
	private Flow_Statistic flow_Statistic;
	private String table_id;
	private String cookie;
	private String id;
	private Instructions instructions;
    private Match match;
    public Flow_Statistic getFlow_Statistic() {
		return flow_Statistic;
	}
	public void setFlow_Statistic(Flow_Statistic flow_Statistic) {
		this.flow_Statistic = flow_Statistic;
	}
	public String getFlow_name() {
		return flow_name;
	}
	public Flow setFlow_name(String flow_name) {
		this.flow_name = flow_name;
		return this;
	}
	public String getPriority() {
		return priority;
	}
	public Flow setPriority(String priority) {
		this.priority = priority;
		return this;
	}
	public String getIdle_timeout() {
		return idle_timeout;
	}
	public Flow setIdle_timeout(String idle_timeout) {
		this.idle_timeout = idle_timeout;
		return this;
	}
	public String getHard_timeout() {
		return hard_timeout;
	}
	public Flow setHard_timeout(String hard_timeout) {
		this.hard_timeout = hard_timeout;
		return this;
	}
	public String getTable_id() {
		return table_id;
	}
	public Flow setTable_id(String table_id) {
		this.table_id = table_id;
		return this;
	}
	public String getId() {
		return id;
	}
	public Flow setId(String id) {
		this.id = id;
		return this;
	}
	public Instructions getInstructions() {
		return instructions;
	}
	public Flow setInstructions(Instructions instructions) {
		this.instructions = instructions;
		return this;
	}
	public Match getMatch() {
		return match;
	}
	public Flow setMatch(Match match) {
		this.match = match;
		return this;
	}
	public String getCookie() {
		return cookie;
	}
	public Flow setCookie(String cookie) {
		this.cookie = cookie;
		return this;
	}
	/**
	 * send a flow to the node
	 * @param node the specific openflow switch
	 * @return s s[0]=code s[1]=information
	 */
	public void Send(String node) throws FlowFailException{
       	String url="http://"+ODL_IP+"/restconf/config/opendaylight-inventory:nodes/node/"+node+"/flow-node-inventory:table/"+this.getTable_id()+"/flow/"+this.getId();
		System.out.println(url);
       	JSONArray jsonArray=new JSONArray();
		//make the right json
		jsonArray.add(JSON.parseObject(JSON.toJSONString(this)));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("flow", jsonArray);
		System.out.println(jsonObject);
		String []result=HttpUtil.Put_request(url, jsonObject);
		String responsecode=result[0];
		if(!"200".equals(responsecode) && ! "201".equals(responsecode))
			throw new FlowFailException("flow"+this.getId()+"sended to"+node+"failed");			
	}
	/**
	 * delete the flow from the node
	 * @param node
	 * @throws FlowFailException
	 */
	public void Delete(String node) throws FlowFailException{
		String url="http://"+ODL_IP+"/restconf/config/opendaylight-inventory:nodes/node/"+node+"/flow-node-inventory:table/"+this.getTable_id()+"/flow/"+this.getId();
		String responsecode=HttpUtil.Delete_request(url)[0];
		if(!"200".equals(responsecode) && !"201".equals(responsecode) && !"404".equals(responsecode))
			throw new FlowFailException("flow "+this.getId()+" delete from "+node+" failed");			
		
		/*
		System.out.println(url);
		String operurl="http://"+ODL_IP+"/restconf/operational/opendaylight-inventory:nodes/node/"+node+"/flow-node-inventory:table/"+this.getTable_id()+"/flow/"+this.getId();
		System.out.println(operurl);
		String responsecode=request.Delete_request(url)[0];
		String []result=request.Get_request(operurl);
		String getcode=result[0];
		String flowjson=result[1];
		Flow flow=JSON.parseObject(flowjson, Flow.class);		
		if(!"404".equals(getcode))
			throw new FlowFailException("flow "+this.getId()+" delete from "+node+" failed");			
	*/
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(flow_name,id,instructions,match,idle_timeout,hard_timeout,table_id);
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
	    Flow other=(Flow) obj;
	    return Objects.equals(this.id, other.getId()) && Objects.equals(this.getMatch(), other.getMatch()) 
	    		&& Objects.equals(this.getInstructions(),other.getInstructions()); 
 		
	}
	
}
