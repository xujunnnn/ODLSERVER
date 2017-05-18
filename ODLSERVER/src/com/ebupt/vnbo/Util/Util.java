package com.ebupt.vnbo.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.catalina.valves.rewrite.Substitution.RewriteRuleBackReferenceElement;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.regexp.internal.recompile;
import com.sun.org.apache.xpath.internal.axes.NodeSequence;
public class Util {
 
	//鑾峰彇Odl_Ip
	public static String getODL_IP(){
		Properties properties=new Properties();
		Reader inReader;
		try {
			//System.out.println(System.getProperty("com/ebupt/vnbo/Config/ODL.properties"));
			InputStream inputStream=Util.class.getClassLoader().getResourceAsStream("com/ebupt/vnbo/Config/ODL.properties");				
			properties.load(inputStream);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ODL_IP=properties.getProperty("ODL_IP");
		return ODL_IP;
	}

	//璇诲彇json瀛楃涓�
	public static JSONObject GetJson(BufferedReader bufferedReader){
		StringBuffer sb=new StringBuffer();
		String s;
		try {
			while((s=bufferedReader.readLine())!=null){
				sb.append(s);		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObject=JSONObject.parseObject(sb.toString());
		return jsonObject;
	}
	
	
	//
	
	//鏇存敼vlanid
	
		

	public static Connection connect_to_sql(String database){
		Connection connection=null;
		Properties properties=new Properties();
		Reader inReader;
		try {
			InputStream inputStream=Util.class.getClassLoader().getResourceAsStream("com/ebupt/vnbo/Config/ODL.properties");			
			properties.load(inputStream);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String username=properties.getProperty("username");
		//System.out.println(username);
		String password=properties.getProperty("password");
		//System.out.println(password);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database+"?autoReconnect=true&useSSL=false",username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	public static void close_connection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*判断qostask是否合法
 * 
 * 
 */

}
