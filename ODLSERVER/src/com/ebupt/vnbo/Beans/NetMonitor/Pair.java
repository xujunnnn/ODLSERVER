/*
 * Copyright (c) 2015 NEC Corporation. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.ebupt.vnbo.Beans.NetMonitor;

import java.util.Objects;

public class Pair<T,O> {
	private T left;
	private O right;
	public T getLeft() {
		return left;
	}
	public void setLeft(T left) {
		this.left = left;
	}
	public O getRight() {
		return right;
	}
	public void setRight(O right) {
		this.right = right;
	}
	public Pair(){
		left=null;
		right=null;
	}
	public Pair(T left,O right){
		this.left=left;
		this.right=right;
		
	}
	@Override
	public boolean equals(Object obj){
		if(obj==this) 
			return true;
		if(obj==null)
			return false;
		if(obj.getClass()!=this.getClass())
			return false;
		Pair<T, O> other=(Pair<T,O>) obj;
		return (Objects.equals(this.left, other.left) && Objects.equals(this.right, other.right));
	}
	
	@Override
	public int hashCode(){
		final int prime=31;
		int result=1;
		result=prime*result+((this.getLeft()==null) ? 0:this.getLeft().hashCode());
		result=prime*result+((this.getRight()==null)? 0:this.getRight().hashCode());
		return result;
	}

}
