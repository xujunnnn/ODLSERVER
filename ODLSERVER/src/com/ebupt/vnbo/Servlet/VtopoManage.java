package com.ebupt.vnbo.Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.VTopo.VTopo;
import com.ebupt.vnbo.Beans.Vtn.OperationType;
import com.ebupt.vnbo.Service.VTopoService;
import com.ebupt.vnbo.Util.Util;

/**
 * Servlet implementation class VtopoManage
 */
@WebServlet("/VtopoManage")
public class VtopoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		   response.setContentType("application/json;charset=utf8");
		   request.setCharacterEncoding("utf-8");
		   JSONObject result=new JSONObject();
		   PrintWriter pr=response.getWriter();
		   //read the input json
	       BufferedReader bufferedReader=new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"utf8"));
	       JSONObject qosjson=Util.GetJson(bufferedReader);
	       //get the operationType
	       OperationType operationType=OperationType.valueOf(qosjson.getString("Operation"));
	      //get the vtopo from the input
	       VTopo vTopo=JSON.parseObject(qosjson.getJSONObject("VTopo").toJSONString(), VTopo.class);
	       VTopoService vTopoService=new VTopoService();
	       // judge the operationType
	       if(operationType==OperationType.ADD){
 	        result=vTopoService.addVTopo(vTopo);
	
	       }
	       if(operationType==OperationType.REMOVE){
	    	 result=vTopoService.removeVTopo(vTopo);
	    	 
	       }
	       if(operationType==OperationType.SET){
	    	//   JSONObject result=vTopoService.;
	       	   
	       }
	       if(operationType==OperationType.QUERRY){
	    //	   JSONObject result=qosService.querryQos();
	    //	   pr.write(result.toJSONString());   	   
	       }
	       pr.write(result.toJSONString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
