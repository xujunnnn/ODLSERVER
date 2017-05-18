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
import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.Beans.NetMonitor.MonOperation;
import com.ebupt.vnbo.Beans.NetMonitor.Tag;
import com.ebupt.vnbo.Service.NetMonService;
import com.ebupt.vnbo.Util.Util;

/**
 * Servlet implementation class MonitorManage
 */
@WebServlet(description = "监控网络", urlPatterns = { "/MonitorManage" })
public class MonitorManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonitorManage() {
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
		   BufferedReader bufferedReader=new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"utf8"));
	       JSONObject monjson=Util.GetJson(bufferedReader);
	       //接受输入的分类标签
	       MonOperation monOperation=MonOperation.valueOf(monjson.getString("MonOperation"));
	       NetMonService netMonService=new NetMonService();
	       //判断是否是查询请求
	       if(monOperation==MonOperation.Querry){
	       Tag tag=Tag.valueOf(monjson.getString("Tag"));
	       //获取数据并输出
	       if(tag==Tag.Delay){
	    	   pr.println(netMonService.GetGlobalLatency());
	       }
	       else
	       pr.println(netMonService.GetNetData(tag));	 
	       }
	       //start monitor
	       if(monOperation==MonOperation.Start){
	    	   pr.println(netMonService.StartMonitor());
	       }
	       //stop monitor
	       if(monOperation==MonOperation.Stop){
	    	  pr.println(netMonService.StopMonitor());
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
