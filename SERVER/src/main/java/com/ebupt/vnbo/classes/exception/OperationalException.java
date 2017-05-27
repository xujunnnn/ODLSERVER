package com.ebupt.vnbo.classes.exception;

public class OperationalException extends ODL_IO_Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OperationalException(){}
	public OperationalException(String info){
		super(info);
	}

}
