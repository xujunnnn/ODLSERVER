package com.ebupt.vnbo.Beans.Exception;

public class MeterFailException extends Exception {
	public MeterFailException(){};
	
	public MeterFailException(String gripe){
		super(gripe);
	}

}
