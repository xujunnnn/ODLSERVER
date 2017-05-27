package com.ebupt.vnbo.dao.qos;

import java.io.IOException;
import java.util.List;

import com.ebupt.vnbo.classes.qos.QosEntry;



public interface QosEntryDao {
	/**
	 * 向数据库中插入qos
	 * @param qosEntry
	 * @throws IOException
	 */
	public void insert(QosEntry qosEntry) throws IOException;
	/**
	 * 从数据库中删除qos
	 * @param qosEntry
	 * @throws IOException
	 */
	public void delete(QosEntry qosEntry) throws IOException;
	/**
	 * 从数据库中查询指定的qos
	 * @param qosEntry
	 * @throws IOException
	 */
	public QosEntry querry(QosEntry qosEntry) throws IOException;
	/**
	 * 检查数据库中是否已经存在该qos
	 * @param qosEntry
	 * @return
	 * @throws IOException
	 */
	public boolean contains(QosEntry qosEntry) throws IOException;
	/**
	 * 查询数据库中的所有qos
	 * @return
	 * @throws IOException
	 */
	public List<QosEntry> querryAll() throws IOException;
	

}
