package vtopo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebupt.vnbo.request.topology.TopologyRequest;
import com.ebupt.vnbo.request.vtopo.VtopoRequest;
import com.ebupt.vnbo.service.topology.TopologyService;
import com.ebupt.vnbo.service.vtopo.VtopoService;
import com.ebupt.vnbo.serviceImpl.topology.TopologyServiceImpl;
import com.ebupt.vnbo.serviceImpl.vtopo.VtopoServiceImpl;
import com.ebupt.vnbo.util.BaseUtil;

/**
 * Servlet implementation class VtopoManage
 */
public class VtopoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VtopoManage() {
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
	       JSONObject webRequest=BaseUtil.GetJson(bufferedReader);
	       VtopoService vtopoService=new VtopoServiceImpl();
	       VtopoRequest vtopoRequest=JSON.parseObject(webRequest.toJSONString(),VtopoRequest.class);
	       pr.write(vtopoService.resolve(vtopoRequest).toJSONString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}