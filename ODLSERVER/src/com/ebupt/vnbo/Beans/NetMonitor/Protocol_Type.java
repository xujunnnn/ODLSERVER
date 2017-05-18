package com.ebupt.vnbo.Beans.NetMonitor;

public enum Protocol_Type {
    TCP(6),ICMP(1),UDP(17),UNKNOW(0);
	private int value=-1;
    private Protocol_Type(int value) {
	// TODO Auto-generated constructor stub
    	this.value=value;
    }
    public int value(){
    	return this.value;
    }
    public static Protocol_Type Valueof(int value){
    	switch (value) {
		case 1:
			return ICMP;
		case 6:
			return TCP;
		case 17:
			return UDP;
		default:
			return UNKNOW;
			
		}
    }
    
}
