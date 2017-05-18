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

import org.apache.catalina.startup.Tool;

import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.NetTopology.HostTracker;
import com.ebupt.vnbo.Service.TopoService;
import com.ebupt.vnbo.Util.Util;

/**
 * Servlet implementation class TopoManage
 */
@WebServlet("/TopoManage")
public class TopoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopoManage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		   response.setContentType("application/json;charset=utf8");
		   request.setCharacterEncoding("utf-8");
		   PrintWriter pr=response.getWriter();
		   //read the input json
	       BufferedReader bufferedReader=new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"utf8"));
	       JSONObject topojson=Util.GetJson(bufferedReader);
	       String type=topojson.getString("Type");
	       TopoService topoService=new TopoService();
	       if("Host".equals(type)){
	    	   pr.write(topoService.getHost().toJSONString());
	       }
	       if("Switch".equals(type)){
	    	   pr.write(topoService.getSwitch().toJSONString());
	       }
	       if("Discovery".equals(type)){
	    	   String srcIp=topojson.getString("srcIp");
	    	   String startIp=topojson.getString("startIp");
	    	   String endIp=topojson.getString("endIp");
	    	  pr.write(topoService.HostTracker(srcIp, startIp, endIp).toJSONString()); 	   
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
