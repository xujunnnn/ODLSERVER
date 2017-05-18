package com.ebupt.vnbo.Beans.Vtn;

import com.mysql.jdbc.StringUtils;

public class Hostmc {
		private String host;
		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}
		public String getHostName(){
			StringBuffer sb=new StringBuffer();
			sb.append("host").append(":").append(host.split("@")[0]);
			return sb.toString();
		}
	
}
