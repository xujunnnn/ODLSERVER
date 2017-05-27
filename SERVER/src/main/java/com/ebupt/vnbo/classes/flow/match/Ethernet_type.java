package com.ebupt.vnbo.classes.flow.match;

import java.util.Objects;

public class Ethernet_type {
	
		private String type;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			
			return Objects.hash(type);
		}

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			if(obj==this) 
				return true;
			if(obj==null)
				return false;
			if(this.getClass()!=obj.getClass())
				return false;
			Ethernet_type other=(Ethernet_type) obj;
				return Objects.equals(this.type, other.getType());
					
		}
	
}
