package com.ebupt.vnbo.Beans.NetMonitor;

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
	
	
	/**
	 * add 
	 * @param oldnetStatic
	 * @return
	 */
	public NetStatic add(NetStatic oldnetStatic){
		
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
		return Objects.hash(bytecount,bytespeed,packetcount,packetspeed);
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
	    return
	    	Objects.equals(packetcount, other.getPacketcount()) &&
	    	Objects.equals(packetspeed, other.getPacketspeed()) &&
	    	Objects.equals(bytecount, other.getBytecount()) &&
	    	Objects.equals(packetcount, other.getPacketcount());
	    		
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
