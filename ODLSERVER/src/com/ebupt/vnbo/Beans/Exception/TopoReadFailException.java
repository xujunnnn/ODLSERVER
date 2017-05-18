package com.ebupt.vnbo.Beans.Exception;

public class TopoReadFailException extends Exception {
	public TopoReadFailException(){};
	
	public TopoReadFailException(String gripe){
		super(gripe);
	}
}
