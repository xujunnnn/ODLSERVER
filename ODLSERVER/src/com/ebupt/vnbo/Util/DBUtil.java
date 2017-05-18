package com.ebupt.vnbo.Util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBUtil {
	/**
	 * get sql session
	 * @return
	 * @throws IOException
	 */
 public static SqlSession getSqlSession() throws IOException{
		//read the configure file
		Reader reader=Resources.getResourceAsReader("com/ebupt/vnbo/Config/Configuration.xml");
		SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(reader);
		SqlSession session=sessionFactory.openSession();
		return session;
	}

}
