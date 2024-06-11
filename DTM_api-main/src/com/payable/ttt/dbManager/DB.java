package com.payable.ttt.dbManager;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-11-29
 */
public class DB {

	protected static Logger log;
	protected SessionFactory factory;

	Session session;
	Transaction trans;

	public DB(SessionFactory sf) {
		factory = sf;
		log = LogManager.getLogger(this.getClass());
		init();
	}

	public void init() {
		log.info("inside db init");
		session = factory.openSession();
		trans = session.beginTransaction();
	}

	public Object write(Object obj) {
		return session.save(obj);
	}

	public void update(Object obj) {
		session.update(obj);
	}

//	public void edit(Object obj) {
////		 session.saveOrUpdate(obj);
////		 .addEntity(User.class)
////	        .uniqueResult();
//		// return session.update(obj) .addEntity(User.class).uniqueResult();
//	}

	public <E> List<E> read(String hql) {
		@SuppressWarnings("unchecked")
		List<E> list = session.createQuery(hql).list();
		return list;
	}

	public <E> List<E> read(String hql, int start, int size) {
		Query<?> query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(size);
		@SuppressWarnings("unchecked")
		List<E> list = (List<E>) query.list();
		return list;
	}

	public <T> T read(String entity, String id) {
		@SuppressWarnings("unchecked")
		T t = ((T) session.get(entity, id));
		return t;
	}

	public void commit() {
		trans.commit();
	}

	public void rollback() {
		trans.rollback();
	}

	public void commitClose() {
		trans.commit();
		session.close();
	}

	public void rollbackClose() {
		trans.rollback();
		session.close();
	}

	public void finallyClose() {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}

}
