/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.guzz.transaction;

import java.io.Serializable;
import java.util.Map;

import javax.transaction.Transaction;

import org.guzz.exception.DaoException;
import org.guzz.jdbc.JDBCTemplate;
import org.guzz.jdbc.ObjectBatcher;
import org.guzz.jdbc.SQLBatcher;
import org.guzz.orm.sql.BindedCompiledSQL;
import org.guzz.orm.sql.CompiledSQL;

/**
 * 
 * 用于数据库写的事务对象，可以进行最简单的数据库读取操作。
 * 
 *
 * @author liukaixuan(liukaixuan@gmail.com)
 */
public interface WriteTranSession {
		
	public JDBCTemplate createJDBCTemplate(Class domainClass) ;
	
	/**按照领域对象的名称或者类名称，获取JDBCTemplate*/
	public JDBCTemplate createJDBCTemplate(String businessName) ;
	
	public JDBCTemplate createJDBCTemplateByDbGroup(String dbGroup) ;
	
	/**
	 * 
	 * create a batcher to perform jdbc batch operations based on the {@link CompiledSQL}.
	 * 
	 * <br>the {@link WriteTranSession} from which the batcher is created, 
	 * share the same {@link java.sql.Connection} and {@link Transaction} with the batcher.
	 * <br>
	 * The resources of the batcher will be released once the {@link WriteTranSession} is closed.
	 * 
	 * @param CompiledSQL sql
	 * @exception DaoException may raise database exception
	 */
	public SQLBatcher createCompiledSQLBatcher(CompiledSQL sql) ;
	
	/**
	 *
	 * create a batcher to perform operations on pojo objects.
	 *
	 * <br>the {@link WriteTranSession} from which the batcher is created, 
	 * share the same {@link java.sql.Connection} and {@link Transaction} with the batcher.
	 * <br>
	 * The resources of the batcher will be released once the {@link WriteTranSession} is closed.
	 * 
	 * @param Class domainClass
	 * @exception DaoException may raise database exception
	 */
	public ObjectBatcher createObjectBatcher(Class domainClass) ;
	
	public Object findObjectByPK(String businessName, Serializable pk) ;
	
	public Object findObjectByPK(Class domainClass, Serializable pk) ;
	
	public Object findObjectByPK(Class domainClass, int pk) ;
		
	//insert
	public Serializable insert(Object domainObject) ;
	
	public boolean update(Object domainObject) ;
	
	public boolean delete(Object domainObject) ;
	
	
	//通用的更新查询。
	public int executeUpdate(String id, Map params) ;
	
	public int executeUpdate(BindedCompiledSQL bsql) ;
	
	public void commit() ;
	
	public void rollback() ;
	
	/**关闭事务，忽略一切错误。*/
	public void close() ;
	
	public void commitAndClose() ;
	
	public void rollbackAndClose() ;

}
