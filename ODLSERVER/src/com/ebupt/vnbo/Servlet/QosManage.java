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
import com.ebupt.vnbo.Beans.Vtn.OperationType;
import com.ebupt.vnbo.Service.QosService;
import com.ebupt.vnbo.Util.Util;
import com.ebupt.vnbo.Beans.Qos.QosPolicy;
/**
 * Servlet implementation class QosManage
 */
@WebServlet("/QosManage")
public class QosManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QosManage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//set servlet character
		   response.setContentType("application/json;charset=utf8");
		   request.setCharacterEncoding("utf-8");
		   PrintWriter pr=response.getWriter();
		   //read the input json
	       BufferedReader bufferedReader=new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"utf8"));
	       JSONObject qosjson=Util.GetJson(bufferedReader);
	       OperationType operationType=OperationType.valueOf(qosjson.getString("Operation"));
	      
	       QosService qosService=new QosService();
	       if(operationType==OperationType.ADD){
	       QosPolicy qosPolicy=JSON.parseObject(qosjson.getJSONObject("QosPolicy").toJSONString(), QosPolicy.class);
 	       JSONObject result=qosService.addQos(qosPolicy);
	       pr.write(result.toJSONString());
	       }
	       if(operationType==OperationType.REMOVE){
	    	   QosPolicy qosPolicy=JSON.parseObject(qosjson.getJSONObject("QosPolicy").toJSONString(), QosPolicy.class);
	    	   JSONObject result=qosService.deleteQos(qosPolicy);
	    	   pr.write(result.toJSONString());
	       }
	       if(operationType==OperationType.SET){
	    	   QosPolicy qosPolicy=JSON.parseObject(qosjson.getJSONObject("QosPolicy").toJSONString(), QosPolicy.class);
	    	   JSONObject result=qosService.modifyQos(qosPolicy);
	    	   pr.write(result.toJSONString());   	   
	       }
	       if(operationType==OperationType.QUERRY){
	    	   JSONObject result=qosService.querryQos();
	    	   pr.write(result.toJSONString());   	   
	       }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
