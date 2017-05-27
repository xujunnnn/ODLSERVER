package com.ebupt.vnbo.classes.flow.instruction;

import java.util.ArrayList;
import java.util.Objects;

public class Instructions {
	
	private ArrayList<Instruction> instruction=new ArrayList<Instruction>();

	public ArrayList<Instruction> getInstruction() {
		return instruction;
	}

	public void setInstruction(ArrayList<Instruction> instruction) {
		this.instruction = instruction;
	}
	public Instructions(ArrayList<Instruction> instructions){
		this.instruction=instructions;
	}
	public Instructions addInstruction(Instruction instruction){
		this.instruction.add(Integer.parseInt(instruction.getOrder()), instruction);
		return this;
	}
	/**
	 * 
	 */
	public Instructions(){
		
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(instruction);
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
	    Instructions other=(Instructions) obj;
	    	return Objects.deepEquals(this.instruction, other.getInstruction());
	}
	
	
}
