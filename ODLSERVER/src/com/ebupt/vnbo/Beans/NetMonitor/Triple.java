package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.Objects;

public class Triple<T,O,X> {
	private T t;
	private O O;
	private X x;
	
	public Triple(){};
	
	public Triple (T left,O Mid,X Right){
		this.t=left;
		this.O=Mid;
		this.x=Right;
	}
	
	
	public T getLeft(){
		return t;
	}
	public O getMid(){
		return O;
	}
	public X getRight(){
		return x;
	}
	public Triple<T, O, X> setLeft(T left){
		this.t=left;
		return this;
	}
	
	public Triple<T, O, X> setMid(O Mid){
		this.O=Mid;
		return this;
	}
	
	public Triple<T, O, X> setRight(X right){
		this.x=right;
		return this;
	}
    @Override
	public boolean equals(Object obj){
		if(this==obj)
			return true;
		if(obj==null)
			return false;
		if(this.getClass()!=obj.getClass())
			return false;
		
	    Triple<T, O, X> other=(Triple<T,O,X>) obj;
	    return (Objects.equals(this.t, other.t)) && (Objects.equals(this.O, other.O)) && (Objects.equals(this.x, other.x));
	}
	@Override
	public int hashCode(){
		return Objects.hash(this.t,this.O,this.x);
	}
}
