package com.ebupt.vnbo.classes.monitor;

import java.util.Objects;

/**
 * 该类描述流量监控的监控项
 * uploadpacket 上传包总数
 * downloadpacket 下载包总数
 * upbyte 上传比特数
 * downbyte 下载比特数
 * uppacketspeed 上传速度 包/秒
 * downpacketspeed 下载速度 包/秒
 * upbytespeed 上传速度 比特/秒
 * downbytespeed 下载速度 比特/秒
 * bytecount 当前流表匹配 比特
 * packetcount 当前流表匹配的包数
 * bytespeed 当前流表匹配速度 比特/秒
 * packetspeed 当前流表匹配包数 包/秒
 * @author xu
 *
 */
public class NetStatic implements Cloneable{
	private long uploadpacket;
	private long downloadpacket;
	private long upbyte;
	private long downbyte;
	private long uppacketspeed;
	private long downpacketspeed;
	private long upbytespeed;
	private long downbytespeed;
	private long packetcount;
	private long bytecount;
	private long packetspeed;
	private long bytespeed;
	public long getPacketcount() {
		return packetcount;
	}
	public NetStatic setPacketcount(long packetcount) {
		this.packetcount = packetcount;
		return this;
	}
	public long getBytecount() {
		return bytecount;
	}
	public NetStatic setBytecount(long bytecount) {
		this.bytecount = bytecount;
		return this;
	}
	public long getPacketspeed() {
		return packetspeed;
	}
	public NetStatic setPacketspeed(long packetspeed) {
		this.packetspeed = packetspeed;
		return this;
	}
	public long getBytespeed() {
		return bytespeed;
	}
	public NetStatic setBytespeed(long bytespeed) {
		this.bytespeed = bytespeed;
		return this;
	}
	public long getUploadpacket() {
		return uploadpacket;
	}
	public NetStatic setUploadpacket(long uploadpacket) {
		this.uploadpacket = uploadpacket;
		return this;
	}
	public long getDownloadpacket() {
		return downloadpacket;
	}
	public NetStatic setDownloadpacket(long downloadpacket) {
		this.downloadpacket = downloadpacket;
		return this;
	}
	public long getUpbyte() {
		return upbyte;
	}
	public NetStatic setUpbyte(long upbyte) {
		this.upbyte = upbyte;
		return this;
	}
	public long getDownbyte() {
		return downbyte;
	}
	public NetStatic setDownbyte(long downbyte) {
		this.downbyte = downbyte;
		return this;
	}
	public long getUppacketspeed() {
		return uppacketspeed;
	}
	public NetStatic setUppacketspeed(long uppacketspeed) {
		this.uppacketspeed = uppacketspeed;
		return this;
	}
	public long getDownpacketspeed() {
		return downpacketspeed;
	}
	public NetStatic setDownpacketspeed(long downpacketspeed) {
		this.downpacketspeed = downpacketspeed;
		return this;
	}
	public long getUpbytespeed() {
		return upbytespeed;
	}
	public NetStatic setUpbytespeed(long upbytespeed) {
		this.upbytespeed = upbytespeed;
		return this;
	}
	public long getDownbytespeed() {
		return downbytespeed;
	}
	public NetStatic setDownbytespeed(long downbytespeed) {
		this.downbytespeed = downbytespeed;
		return this;
	}
	
	/**
	 * add 
	 * @param oldnetStatic
	 * @return
	 */
	public NetStatic add(NetStatic oldnetStatic){
		this.uploadpacket=oldnetStatic.getUploadpacket()+this.uploadpacket;
		this.downloadpacket=oldnetStatic.getDownloadpacket()+this.downloadpacket;
		this.upbyte=oldnetStatic.getUpbyte()+this.upbyte;
		this.downbyte=oldnetStatic.getDownbyte()+this.downbyte;
		this.uppacketspeed=oldnetStatic.getUpbytespeed()+this.uppacketspeed;
		this.downpacketspeed=oldnetStatic.getDownpacketspeed()+this.downpacketspeed;
		this.upbytespeed=oldnetStatic.getUpbytespeed()+this.upbytespeed;
		this.downbytespeed=oldnetStatic.getDownbytespeed()+this.downbytespeed;
		this.packetcount=oldnetStatic.getPacketcount()+this.packetcount;
		this.bytecount=oldnetStatic.getBytecount()+this.bytecount;
		this.packetcount=oldnetStatic.getPacketcount()+this.packetcount;
		this.bytespeed=oldnetStatic.getBytespeed()+this.bytespeed;
		this.packetspeed=oldnetStatic.getPacketspeed()+this.packetspeed;
		return this;
	}
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(uploadpacket,downloadpacket,upbyte,downbyte,uppacketspeed,downpacketspeed,upbytespeed,downbytespeed);
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
	    NetStatic other=(NetStatic) obj;
	    return Objects.equals(this.uploadpacket, other.getUploadpacket()) && Objects.equals(this.downloadpacket, other.getDownloadpacket())
	    		&& Objects.equals(this.upbyte, other.getUpbyte()) && Objects.equals(this.downbyte,other.getDownbyte())
	    		&& Objects.equals(this.uppacketspeed, other.getUppacketspeed()) && Objects.equals(this.downpacketspeed, other.getDownpacketspeed())
	    		&& Objects.equals(this.upbytespeed, other.getUpbytespeed()) && Objects.equals(this.downbytespeed, other.getDownbytespeed());
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "bytespeed "+bytespeed; 
	}
	 
	@Override
	public Object clone() throws CloneNotSupportedException  {
		// TODO Auto-generated method stub
		return super.clone();
	}


}
