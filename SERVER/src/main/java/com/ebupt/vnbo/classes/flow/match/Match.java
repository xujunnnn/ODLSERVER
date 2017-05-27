package com.ebupt.vnbo.classes.flow.match;

import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class Match {

	@JSONField(name="in-port")
	private String in_port;
	@JSONField(name="in-phy-port")
	private String in_phy_port;
	@JSONField(name="ipv4-source")
	private String ipv4_source;
	@JSONField(name="ipv4-destination")
	private String ipv4_destination;
	@JSONField(name="tcp-source-port")
	private String tcp_source_port;
	@JSONField(name="tcp-destination-port")
	private String tcp_destination_port;
	@JSONField(name="ethernet-match")
	private Ethernet_Match ethernet_Match;
	@JSONField(name="icmpv4-match")
	private Icmpv4_Match icmpv4_Match;
	@JSONField(name="ip-match")
	private Ip_Match ip_Match;
	@JSONField(name="protocol-match-fields")
	private Protocol_Match_Fields protocol_Match_Fields;
	@JSONField(name="tcp-flag-match")
	private Tcp_Flag_Match tcp_Flag_Match;
	@JSONField(name="vlan-match")
	private Vlan_Match vlan_Match;
	@JSONField(name="udp-source-port")
	private String udp_source_port;
	@JSONField(name="udp-destination-port")
	private String udp_destination_port;
	public Ethernet_Match getEthernet_Match() {
		return ethernet_Match;
	}
	public void setEthernet_Match(Ethernet_Match ethernet_Match) {
		this.ethernet_Match = ethernet_Match;
	}
	public Icmpv4_Match getIcmpv4_Match() {
		return icmpv4_Match;
	}
	public void setIcmpv4_Match(Icmpv4_Match icmpv4_Match) {
		this.icmpv4_Match = icmpv4_Match;
	}
	public Ip_Match getIp_Match() {
		return ip_Match;
	}
	public void setIp_Match(Ip_Match ip_Match) {
		this.ip_Match = ip_Match;
	}
	public Protocol_Match_Fields getProtocol_Match_Fields() {
		return protocol_Match_Fields;
	}
	public void setProtocol_Match_Fields(Protocol_Match_Fields protocol_Match_Fields) {
		this.protocol_Match_Fields = protocol_Match_Fields;
	}
	public Tcp_Flag_Match getTcp_Flag_Match() {
		return tcp_Flag_Match;
	}
	public void setTcp_Flag_Match(Tcp_Flag_Match tcp_Flag_Match) {
		this.tcp_Flag_Match = tcp_Flag_Match;
	}
	public Vlan_Match getVlan_Match() {
		return vlan_Match;
	}
	public void setVlan_Match(Vlan_Match vlan_Match) {
		this.vlan_Match = vlan_Match;
	}
	public String getIn_port() {
		return in_port;
	}
	public Match setIn_port(String in_port) {
		this.in_port = in_port;
		return this;
	}
	public String getIn_phy_port() {
		return in_phy_port;
	}
	public Match setIn_phy_port(String in_phy_port) {
		this.in_phy_port = in_phy_port;
		return this;
	}
	public String getIpv4_source() {
		return ipv4_source;
	}
	public Match setIpv4_source(String ipv4_source) {
		this.ipv4_source = ipv4_source;
		return this;
	}
	public String getIpv4_destination() {
		return ipv4_destination;
	}
	public Match setIpv4_destination(String ipv4_destination) {
		this.ipv4_destination = ipv4_destination;
		return this;
	}
	public String getTcp_source_port() {
		return tcp_source_port;
	}
	public void setTcp_source_port(String tcp_source_port) {
		this.tcp_source_port = tcp_source_port;
	}
	public String getTcp_destination_port() {
		return tcp_destination_port;
	}
	public void setTcp_destination_port(String tcp_destination_port) {
		this.tcp_destination_port = tcp_destination_port;
	}
	/**
	 * set mac_match with the specified macaddress
	 * @param src source macaddress
	 * @param dest destination macaddress
	 * @param etherType default 2048
	 * @return
	 */
	public Match Set_Mac_Match(String src,String dest,String etherType){
	    Ethernet_Match ethernet_Match=new Ethernet_Match(src,dest,etherType);
	    this.setEthernet_Match(ethernet_Match);
		return this;
		
	}
	/**
	 * 
	 * @param icmpv4_type
	 * @param icmpv4_code
	 * @return
	 */
	public Match Set_Icmpv4_Match(String icmpv4_type,String icmpv4_code){
		Icmpv4_Match icmpv4_Match=new Icmpv4_Match(icmpv4_type, icmpv4_code);
		return this;
	}
	/**
	 * Set Vlan match 
	 * @param vlanpcp the pcp 
	 * @param vlanid the specified vlanid to match
	 * @return Match this match
	 */
	public Match Set_Vlan_Match(String vlanpcp,String vlanid){
		Vlan_Match vlan_Match=new Vlan_Match(vlanpcp, vlanid);
		this.setVlan_Match(vlan_Match);
		return this;
	}
	/**
	 * 
	 * @param protocol
	 * @param dscp
	 * @param ecn
	 * @param proto
	 * @return Match
	 */
    public Match Set_Ip_Match(String protocol,String dscp,String ecn,String proto){
    	Ip_Match ip_Match=new Ip_Match(protocol, dscp, ecn, proto);
    	this.ip_Match=ip_Match;
    	return this;
    }
    
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(in_phy_port,in_port,ipv4_source,ipv4_destination,tcp_source_port,tcp_destination_port,ethernet_Match,icmpv4_Match,ip_Match,protocol_Match_Fields,
				tcp_Flag_Match,vlan_Match,udp_source_port,udp_destination_port);
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj==this) 
			return true;
		if(obj==null)
			return false;
		if(this.getClass()!=obj.getClass())
			return false;
		Match other=(Match)obj;
			return Objects.equals(this.in_phy_port,other.getIn_phy_port()) && Objects.equals(this.in_port, other.getIn_port())
					&& Objects.equals(this.ipv4_source,other.getIpv4_source()) && Objects.equals(ipv4_destination, other.getIpv4_destination())
					&& Objects.equals(this.tcp_source_port, other.getTcp_source_port()) && Objects.equals(this.tcp_destination_port,other.getTcp_destination_port())
					&& Objects.equals(this.ethernet_Match, other.getEthernet_Match()) && Objects.equals(this.icmpv4_Match,other.getIcmpv4_Match())
					&& Objects.equals(this.ip_Match, other.getIp_Match()) && Objects.equals(this.protocol_Match_Fields, other.getProtocol_Match_Fields())
					&& Objects.equals(this.tcp_Flag_Match, other.getTcp_Flag_Match()) &&  Objects.equals(this.vlan_Match, other.getVlan_Match())
					&& Objects.equals(this.udp_destination_port, other.getUdp_destination_port()) && Objects.equals(this.udp_source_port,other.getUdp_source_port());
	}
	
	
	
	public static void main(String []args){
		Match match=new Match();
		match.setIn_phy_port("openflow:1");
		match.setIn_port("of1");
		match.setTcp_destination_port("68");
		match.setTcp_source_port("67");
		String json=JSON.toJSONString(match);
		System.out.println(json);
	}
	public String getUdp_source_port() {
		return udp_source_port;
	}
	public Match setUdp_source_port(String udp_source_port) {
		this.udp_source_port = udp_source_port;
		return this;
	}
	public String getUdp_destination_port() {
		return udp_destination_port;
	}
	public Match setUdp_destination_port(String udp_destination_port) {
		this.udp_destination_port = udp_destination_port;
		return this;
	}

}
