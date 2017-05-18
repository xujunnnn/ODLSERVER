package com.ebupt.vnbo.Util;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import com.ebupt.vnbo.Beans.Exception.TopoReadFailException;

import sun.security.action.GetBooleanAction;

public class Test {
	public static void getpath(){
		System.out.println(System.getProperty("usr.dir"));
		System.out.println(Test.class.getClassLoader().getResource("com/ebupt/vnbo/Config/ODL.properties"));
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	System.out.println(System.getProperty("/Config/ODL.properties"));
		Test.getpath();
		System.out.println(Util.class.getClassLoader().getResource(""));
		try {
			FlowUtil.initFlowId();
		} catch (TopoReadFailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	SyFlowUtil.getFlowId("openflow:1", "0");
	}
	

}
