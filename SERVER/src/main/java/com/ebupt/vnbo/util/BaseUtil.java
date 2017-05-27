package com.ebupt.vnbo.util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.regexp.internal.recompile;
import com.sun.org.apache.xpath.internal.axes.NodeSequence;
public class BaseUtil {
	//读取ODL控制器Ip地址
	public static String getODL_IP(){
		Properties properties=new Properties();
		Reader inReader;
		try {
			//System.out.println(System.getProperty("com/ebupt/vnbo/Config/ODL.properties"));
			InputStream inputStream=BaseUtil.class.getClassLoader().getResourceAsStream("config/ODL.properties");				
			properties.load(inputStream);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ODL_IP=properties.getProperty("ODL_IP");
		return ODL_IP;
	}

	//获取输入的json对象
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
	  /**
	   * 将byte[]转换为int
	   * @param bytes
	   * @return
	   */
		public static int BytesToInt(byte[] bytes){
			return ByteBuffer.wrap(bytes).getInt();
		}
		/**
		 * 将int 转化为Byte[]
		 * @param i
		 * @return
		 */
		public static byte[] IntToBytes(int i){
			byte[] bytes=new byte[4];
			 bytes[3]=(byte)(i & 0xff);
			 bytes[2]=(byte)((i>>8) & 0xff);
			 bytes[1]=(byte)((i>>16) & 0xff);
			 bytes[0]=(byte)((i>>24) & 0xff);
			 return bytes;
		}
		/**
		 * 获取指定范围的iP
		 *
		 * @param starts
		 * @param ends
		 * @return
		 * @throws UnknownHostException
		 */
		public static HashSet<String> getIpInRange(String starts,String ends) throws UnknownHostException{
				HashSet< String> ipList=new HashSet<String>();		
				InetAddress start=InetAddress.getByName(starts);
				InetAddress end=InetAddress.getByName(ends);
				int srcAdd=BytesToInt(start.getAddress());
				int destAdd=BytesToInt(end.getAddress());
				for(int i=srcAdd;i<destAdd;i++){
				InetAddress inetAddress=InetAddress.getByAddress(IntToBytes(i));
				ipList.add(inetAddress.getHostAddress());			
				}
				return ipList;
		}
}
