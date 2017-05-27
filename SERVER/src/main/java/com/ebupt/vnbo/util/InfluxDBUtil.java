package com.ebupt.vnbo.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import com.alibaba.fastjson.JSON;
import com.ebupt.vnbo.classes.monitor.Latency;
import com.ebupt.vnbo.classes.monitor.Latency_list;
import com.ebupt.vnbo.classes.monitor.MonTag;
import com.ebupt.vnbo.classes.monitor.NetStatic;
public class InfluxDBUtil {
	private static final String DBurl="http://127.0.0.1:8086";
	private static final String dbname="Flow_Static";
	//时间转换
	public static String TimeConvert(String time){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'");
		try {
			Date date=simpleDateFormat.parse(time);
			String formattime=simpleDateFormat2.format(date);
			return formattime;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将数据存入
	 * @param srcip
	 * @param destip
	 * @param protocol_Type
	 * @param timestamp
	 * @param val
	 */
	public static void put(long timestamp,String measurement,Map<MonTag, NetStatic> netMap){
		
		InfluxDB influxDB = InfluxDBFactory.connect(DBurl);
		BatchPoints batchPoints = BatchPoints
				.database("Flow_Static")
				.retentionPolicy("autogen")
				.consistency(ConsistencyLevel.ALL)
				.build();
		//根据存储的数据设计数据库标签
	
		for(MonTag monTag:netMap.keySet()){			
		NetStatic netStatic=netMap.get(monTag);
		Map<String, String> tags=new HashMap<>();
		if("protocol_load".equals(measurement)){
			tags.put("InPort",monTag.getInport());
			tags.put("Protocol", monTag.getProtocol_Type().toString());
		}
		if("ip_load".equals(measurement)){
			tags.put("InPort", monTag.getInport());
			tags.put("SrcMac", monTag.getSrcmac());
			tags.put("DestMac", monTag.getDestmac());
		}
		Point point= Point.measurement(measurement)
				.time(timestamp,TimeUnit.MILLISECONDS)
				.addField("byte", netStatic.getBytecount())
				.addField("packet",netStatic.getPacketcount())
				.addField("bytespeed", netStatic.getBytespeed())
				.addField("packetspeed", netStatic.getPacketspeed())
				.tag(tags)
				.build();
		batchPoints.point(point);
		}
		influxDB.write(batchPoints);	
		influxDB.close();
	}
	
	public static void putLatency( long timestamp,String measurement,Latency_list latency_list){
		InfluxDB influxDB = InfluxDBFactory.connect(DBurl);
		BatchPoints batchPoints = BatchPoints
				.database("Flow_Static")
				.retentionPolicy("autogen")
				.consistency(ConsistencyLevel.ALL)
				.build();
		
		for(Latency latency:latency_list.getLatency_list()){
			Map<String, String> tags=new HashMap<>();
			tags.put("SrcPort", latency.getSrcnode());
			tags.put("DestPort", latency.getDestnode());
			Point point= Point.measurement(measurement)
					.time(timestamp,TimeUnit.MILLISECONDS)
					.addField("latency", latency.getLatency())
					.tag(tags)
					.build();
			batchPoints.point(point);		
		}
		influxDB.write(batchPoints);	
		influxDB.close();
	}
	
	/**
	 * 
	 * @param sql
	 * @return
	 */
	public static JSON get(String sql){
		InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086");
		Query query = new Query(sql,dbname);
		QueryResult queryResult=influxDB.query(query);
		Result result=queryResult.getResults().get(0);
		JSON resultjson=(JSON)JSON.toJSON(result);
		System.out.println(resultjson);
		return resultjson;

	}
	public static void main(String []args){		
	get("");
	System.out.print(TimeConvert("2017-11-2"));
	}

}