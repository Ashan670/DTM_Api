package com.payable.ttt.dbManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
//import org.hibernate.Query;
import org.hibernate.query.Query;

import com.payable.ttt.dto.BaseModel;
import com.payable.ttt.exception.DBCodeConst;
import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;

/**
 * Transaction management is very poor in this class. when inserting multiple
 * records this will make missing record @ deprecated As of <product> <version>,
 * because ... use {@link com.payable.ttt.dbManager.DB} instead. @
 * Deprecated // remove spece in @ Deprecated Deprecated
 */

public class BaseDbManager<T> {

	protected static Logger log;

	protected SessionFactory factory;


	
	public BaseDbManager(SessionFactory f) {
		factory = f;
		log = LogManager.getLogger(this.getClass());
	}

	public String uuidInsert(T obj) throws HibernateException, IOException, TeamTimeTrackerException {
		log.info("inside uuidInsert(T obj)");
		Session session = factory.openSession();
		Transaction tx = null;

		String dbId = null;

		try {
			tx = session.beginTransaction();
			dbId = (String) session.save(obj);
			tx.commit();
		} catch (HibernateException e) {
			log.error("Received exception in last insert tx.");
			e.printStackTrace();

			if (tx != null) {
				try {
					log.info("Rolling back the last insert.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}
			}
			throw e;

			/*
			 * 
			 * modified by Amila Giragama 20221129
			 */
		} catch (PersistenceException e) {
			log.error("2211291113Received exception in last insert tx.");
			e.printStackTrace();

			if (tx != null) {
				try {
					log.info("Rolling back the last insert.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}
			}
			SQLIntegrityConstraintViolationException sql_violation_exception = (SQLIntegrityConstraintViolationException) e
					.getCause().getCause();
			String errMsg = sql_violation_exception.getMessage();
			int inxIn = errMsg.indexOf(DBCodeConst.INDEX_PREFIX);
			if (inxIn > 0) {
				String colName = errMsg.substring(inxIn + DBCodeConst.INDEX_PREFIX.length());
				if (sql_violation_exception.getErrorCode() == DBCodeConst.DUPLICATE_ENTRY_ERROR_CODE) {
					log.error("(2212031023)Error update duplicate data in ,'" + colName + "," + errMsg);
					throw new TeamTimeTrackerException(EnumException.DUPLICATE_RECORD_FOUNDE );
					//+ sql_violation_exception.getMessage()  + colName + " : " + errMsg
				} else if (sql_violation_exception.getErrorCode() == DBCodeConst.INTEGRITY_CONSTRAINT_VIOLATION) {
					log.error("(2212031025)Error update constraint violation data in ,"
							+ sql_violation_exception.getMessage());
					throw new TeamTimeTrackerException(EnumException.INTEGRITY_CONSTRAINT_VIOLATION + errMsg);
				}
			}
			throw e;
		} catch (ConstraintViolationException e) {
			log.error("2211291114Received exception in last insert tx.");
			e.printStackTrace();

			if (tx != null) {
				try {
					log.info("Rolling back the last insert.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}
			}
			throw e;

		} finally {

			try {
				if (session != null) {
					if (session.isOpen()) {
						session.close();
					}
				}
			} catch (HibernateException e2) {
				log.info("Error in closing session. " + e2.toString());
			}
		}

		return dbId.toString();

	}

	public long insert(T obj) throws HibernateException, IOException {
		log.info("inside insert(T obj)");
		long res = 0;
		Session session = factory.openSession();
		Long dbId = null;
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			dbId = (Long) session.save(obj);
			tx.commit();
		} catch (HibernateException e) {
			log.error("Received exception in last insert tx.");

			if (tx != null) {
				try {
					log.info("Rolling back the last insert.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}
			}

			try {
				if (session != null) {
					if (session.isOpen()) {
						session.close();
					}
				}
			} catch (HibernateException e2) {
				log.info("Error in closing session. " + e2.toString());
			}

			throw e;
		}

		res = dbId.longValue();

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return res;
	}

	public long getSingleResultMutipleL(String hSql, Date stDate, Date enDate) throws HibernateException {
		
		log.info("(2212091626)inside getSingleResultMutipleL(String hSql)");
		
		Long l = null;
		
		Session session = null;
		
		try {
			
			session = factory.openSession();
			Transaction tx = session.beginTransaction();
			
			Query query = session.createQuery(hSql);
			
			if (stDate != null) {
				query.setParameter("stInterval", stDate);
			}
			
			if (enDate != null) {
				query.setParameter("enInterval", enDate);
			}
			
			l = (long) query.list().size();
			
			tx.commit();
			
			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}
		
		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
			
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}
		
		if (l != null) {
			return l;
		}
		
		return 0;
		
	}
	public long getSingleResultL(String hSql, Date stDate, Date enDate) throws HibernateException {

		log.info("(2212091625)inside getSingleResultL(String hSql)");

		Long l = null;

		Session session = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hSql);

			if (stDate != null) {
				query.setParameter("stInterval", stDate);
			}

			if (enDate != null) {
				query.setParameter("enInterval", enDate);
			}

			l = (Long) query.uniqueResult();

			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}

		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		if (l != null) {
			return l.longValue();
		}

		return 0;

	}

	public double getSingleResultinDouble(String hSql, Date stDate, Date enDate)
			throws HibernateException, IOException {
		log.info("inside getSingleResultinDouble(String hSql)");

		Session session = null;

		BigDecimal d = null;

		try {
			session = factory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hSql);

			if (stDate != null) {
				query.setParameter("stInterval", stDate);
			}

			if (enDate != null) {
				query.setParameter("enInterval", enDate);
			}

			d = (BigDecimal) query.uniqueResult();

			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}

		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		if (d != null) {
			return d.doubleValue();
		}

		return 0;

	}

	@SuppressWarnings("unchecked")
	public List<T> getRecords(String hSql) {

		Session session = null;

		log.info("inside List<T> getRecords(String hSql)");

		List<T> records = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();
			records = session.createQuery(hSql).list();
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("Error in tx. " + e.toString());
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}

		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return records;

	}

	// added on 8th november 2016

	// added on 7th october 2016

	public List<Object[]> getGenaricRecords(String hSql, int start, int size, Date stDate, Date enDate)
			throws IOException {

		log.info("List<Object[]> getGenaricRecords(String hSql, int start, int size , stDate , enDate)");

		List<Object[]> records = null;
		Session session = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hSql);
			query.setFirstResult(start);
			query.setMaxResults(size);

			if (stDate != null) {
				query.setParameter("stInterval", stDate);
			}

			if (enDate != null) {
				query.setParameter("enInterval", enDate);
			}

			System.out.println("SQL=====> " + query.unwrap(org.hibernate.Query.class).getQueryString());
			
			records = query.list();
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Err: " + e.toString());
		} catch (Exception e) {
			log.error("Err: " + e.toString());
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}

			}

		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return records;

	}

	/*
	 * @author Amila Giragama
	 * 
	 * @since 2022-11-20
	 */
	public List<Object[]> getGenaricRecords(String hSql, int start, int size) throws IOException {

		log.info("List<Object[]> getGenaricRecords(String hSql, int start, int size , stDate , enDate)");

		List<Object[]> records = null;
		Session session = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hSql);
			query.setFirstResult(start);
			query.setMaxResults(size);

			records = query.list();
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Err: " + e.toString());
		} catch (Exception e) {
			log.error("Err: " + e.toString());
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}

			}

		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return records;

	}

	public List<Object[]> getGenaricRecords(String hSql) throws IOException {

		log.info("List<Object[]> getGenaricRecords(String hSql)");

		List<Object[]> records = null;
		Session session = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();
			records = session.createQuery(hSql).list();
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}

			}

		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return records;
	}

	public List<T> getRecords(String hSql, int start, int size, Date stDate, Date enDate) throws IOException {

		log.info("List<T> getRecords(String hSql, int start, int size , stDate , enDate)");

		List<T> records = null;
		Session session = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hSql);

			query.setFirstResult(start);
			query.setMaxResults(size);

			if (stDate != null) {
				query.setParameter("stInterval", stDate);
			}

			if (enDate != null) {
				query.setParameter("enInterval", enDate);
			}

			records = query.list();
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("Error in closing session. " + e.toString());
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}

			}

		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return records;
	}

	public List<T> getRecords(String hSql, int start, int size) {
		log.info("List<T> getRecords(String hSql, int start, int size)");

		List<T> records = null;
		Session session = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hSql);

			query.setFirstResult(start);
			query.setMaxResults(size);

			// List<T> records = query.list() ;
			records = query.list();
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}

			}

		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return records;
	}

	/*
	 * @author Amila Giragama
	 * 
	 * @since 2022-11-21 full text search not supportd by HQL
	 */
	public List<T> getRecordsBySQL(String hSql, int start, int size) {
		log.info("List<T> getRecords(String hSql, int start, int size)");

		List<T> records = null;
		Session session = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createSQLQuery(hSql);

			query.setFirstResult(start);
			query.setMaxResults(size);

			// List<T> records = query.list() ;
			records = query.list();
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}

			}

		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return records;
	}

	public T get(String entity, Long id) throws IOException {

		log.info("T get(String entity, Long id)");

		Session session = null;

		T obj = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();
			obj = (T) session.get(entity, id);
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}

			}

		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return obj;
	}

	public T get(String entity, String id) throws IOException {

		log.info("T get(String entity, Long id)");

		Session session = null;

		T obj = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();
			obj = (T) session.get(entity, id);
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}

			}

		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return obj;
	}

	public void update(T obj) throws IOException, TeamTimeTrackerException {
		log.info("update(T obj)");

		Session session = null;

		try {
			session = factory.openSession();
		} catch (HibernateException e) {
			log.error("Error in creating session. " + e.toString());
			return;
		}

		if (session == null) {
			log.error("Session is null.");
			return;
		}

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(obj);
			tx.commit();

		} catch (HibernateException e) {
			log.error("Received exception in last update tx. err : " + e.toString());
			if (tx != null) {
				try {
					log.info("Rolling back the last update.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}
			}

			/*
			 * 
			 * modified by Amila Giragama 20221201
			 */

			throw e;

		} catch (PersistenceException e) {
			System.out.println("persist catch ---------");
			log.error("2212011113Received exception in last insert tx.");
			e.printStackTrace();

			if (tx != null) {
				try {
					log.info("Rolling back the last insert.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}
			}

			SQLIntegrityConstraintViolationException sql_violation_exception = (SQLIntegrityConstraintViolationException) e
					.getCause().getCause();
			String errMsg = sql_violation_exception.getMessage();
			int inxIn = errMsg.indexOf(DBCodeConst.INDEX_PREFIX);
			if (inxIn > 0) {
				String colName = errMsg.substring(inxIn + DBCodeConst.INDEX_PREFIX.length());
				if (sql_violation_exception.getErrorCode() == DBCodeConst.DUPLICATE_ENTRY_ERROR_CODE) {
					log.error("(2212021537)Error update duplicate data in ,'" + colName + "," + errMsg);
					throw new TeamTimeTrackerException(
							EnumException.DUPLICATE_RECORD_FOUNDE + sql_violation_exception.getMessage());
				} else if (sql_violation_exception.getErrorCode() == DBCodeConst.INTEGRITY_CONSTRAINT_VIOLATION) {
					log.error("(2212021539)Error update constraint violation data in ,"
							+ sql_violation_exception.getMessage());
					throw new TeamTimeTrackerException(EnumException.INTEGRITY_CONSTRAINT_VIOLATION + errMsg);
				}
			}
			throw e;
		} catch (ConstraintViolationException e) {
			log.error("2212011114Received exception in last insert tx.");
			e.printStackTrace();

			if (tx != null) {
				try {
					log.info("Rolling back the last insert.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}
			}
			throw e;

		} finally {

			try {
				if (session != null) {
					if (session.isOpen()) {
						session.close();
					}
				}
			} catch (HibernateException e2) {
				log.info("Error in closing session. " + e2.toString());
			}
		}

		// session.close();

	}

	public void updateWithUniqueFields(T obj) throws HibernateException, IOException, TeamTimeTrackerException {
		log.info("updateWithUniqueFields(T obj)");

		Session session = null;

		try {
			session = factory.openSession();
		} catch (HibernateException e) {
			log.error("Error in creating session. " + e.toString());
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		}

		if (session == null) {
			log.error("Session is null.");
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		}

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(obj);
			tx.commit();
		} catch (HibernateException e) {
			log.error("Received exception in last update tx.");
			if (tx != null) {
				try {
					log.info("Rolling back the last update.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}

			}

			try {
				if (session != null) {
					if (session.isOpen()) {
						session.close();
					}

				}
			} catch (HibernateException e2) {
				log.info("Error in closing session. " + e2.toString());
			}

			throw e;

		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (HibernateException e2) {
			log.error("Error in closing session. " + e2.toString());
		}
	}

	public int update(String sql) throws IOException {
		log.info("update(String sql)");

		Session session = null;
		int res = -1;

		try {
			session = factory.openSession();
		} catch (HibernateException e) {
			log.error("Error in creating session. " + e.toString());
			return res;
		}

		if (session == null) {
			log.error("Session is null.");
			return res;
		}

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			res = query.executeUpdate();
			tx.commit();

		} catch (HibernateException e) {
			log.error("Received exception in last update tx.");
			if (tx != null) {
				try {
					log.info("Rolling back the last update.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}

			}
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (HibernateException e2) {
			log.error("Error in closing session. " + e2.toString());
		}

		return res;
	}

	public int update(String sql, String[] param, Date[] date) throws IOException {
		log.info("inside update(String sql, String[] param, Date[] date)");

		Session session = null;
		int res = -1;

		try {
			session = factory.openSession();
		} catch (HibernateException e) {
			log.error("Error in creating session. " + e.toString());
			return res;
		}

		if (session == null) {
			log.error("Session is null.");
			return res;
		}

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);

			if (param != null && date != null) {
				if (param.length == date.length) {
					for (int i = 0; i < param.length; i++) {
						query.setParameter(param[i], date[i]);
					}
				}
			}

			res = query.executeUpdate();
			tx.commit();

		} catch (HibernateException e) {
			log.error("Received exception in last update tx.");
			if (tx != null) {
				try {
					log.info("Rolling back the last update.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}

			}
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (HibernateException e2) {
			log.error("Error in closing session. " + e2.toString());
		}

		return res;

	}

	public void delete(T obj) throws IOException {
		log.info("delete(T obj)");

		Session session = null;

		try {
			session = factory.openSession();
		} catch (HibernateException e) {
			log.error("Error in creating session. " + e.toString());
			return;
		}

		if (session == null) {
			log.error("Session is null.");
			return;
		}

		// Session session = factory.openSession();

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			log.error("Received exception in last delete tx.");
			if (tx != null) {
				try {
					log.info("Rolling back the last delete.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}

			}
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (HibernateException e2) {
			log.error("Error in closing session. " + e2.toString());
		}

	}

	public void deleteWithId(String entity, Long id) throws IOException {
		log.info("deleteWithId(String entity, Long id)");

		Session session = null;

		try {
			session = factory.openSession();
		} catch (HibernateException e) {
			log.error("Error in creating session. " + e.toString());
			return;
		}

		if (session == null) {
			log.error("Session is null.");
			return;
		}

		// Session session = factory.openSession();

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			T obj = (T) session.get(entity, id);
			session.delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			log.error("Received exception in last update tx.");
			if (tx != null) {
				try {
					log.info("Rolling back the last update.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}

			}
		}

		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (HibernateException e2) {
			log.error("Error in closing session. " + e2.toString());
		}
	}

	
	/*
	 * @added Amila Giragama
	 * 
	 * @since 2022-12-08
	 */
//	public boolean isExists(Class clazz, String findInColumn, Object findValue) {
//		
//		System.out.println( "isExists  " +  findInColumn + " :: " + findValue + " :: " + clazz);
//		
//	    return factory.openSession().createCriteria(clazz)
//	            .add(Restrictions.eq(findInColumn, findValue))
//	            .uniqueResult() != null;
//
//	}

//	public boolean isExists(Class clazz, String findInColumn, Object findValue) {
//		System.out.println("isExists  " + findInColumn + " :: " + findValue + " :: " + clazz);
//		return factory.openSession().createCriteria(clazz).add(Restrictions.eq(findInColumn, findValue))
//				.setProjection(Projections.property(findInColumn)).uniqueResult() != null;
//
//	}
	
	public boolean isExists(Class clazz, String findInColumn, Object findValue) {
		System.out.println("isExists  " + findInColumn + " :: " + findValue + " :: " + clazz);
		Session session = factory.openSession();
		try {
		    return session.createCriteria(clazz)
		        .add(Restrictions.eq(findInColumn, findValue))
		        .setProjection(Projections.property(findInColumn))
		        .uniqueResult() != null;
		} finally {
		    session.close();
		}
	}

	public boolean isExists(Class clazz, String idKey1, Object idValue1, String idKey2, Object idValue2) {
		System.out.println("isExists TwoRestriction " + idKey1 + " :: " + idValue1 + " :: " + clazz);
		Session session = factory.openSession();
		try {
		    return session.createCriteria(clazz)
		        .add(Restrictions.eq(idKey1, idValue1))
		        .add(Restrictions.eq(idKey2, idValue2))
		        .setProjection(Projections.property(idKey1))
		        .uniqueResult() != null;
		} finally {
		    session.close();
		}
	}

//	public boolean isExistsActive(Class clazz, String findInColumn, Object findValue) {
//		System.out.println("isExistsActive " + findInColumn + " :: " + findValue + " :: " + clazz);
//		return factory.openSession().createCriteria(clazz).add(Restrictions.eq(findInColumn, findValue))
//				.add(Restrictions.eq("status", 1)).setProjection(Projections.property(findInColumn))
//				.uniqueResult() != null;
//	}
//	
//	
	public boolean isExistsActive(Class clazz, String findInColumn, Object findValue) {
		System.out.println("isExistsActive " + findInColumn + " :: " + findValue + " :: " + clazz);
		Session session = factory.openSession();
		boolean exists=false;
		try {
		 exists = session.createCriteria(clazz)
		        .add(Restrictions.eq(findInColumn, findValue))
		        .add(Restrictions.eq("status", 1))
		        .setProjection(Projections.property(findInColumn))
		        .uniqueResult() != null;
		}finally {
		session.close();
		}
		return exists;
	}
	
	

//	public boolean isExistsExceptThis(Class clazz, String findInColumn, Object valueFind, String exceptInColumn,
//			Object exceptInValue) {
//
//		System.out.println("in_isExistsExceptThis " + findInColumn + " :: " + valueFind + " :: " + clazz);
//		return factory.openSession().createCriteria(clazz).add(Restrictions.eq(findInColumn, valueFind))
//				.add(Restrictions.not(Restrictions.like(exceptInColumn, "%" + exceptInValue + "%")))
//				.setProjection(Projections.property(findInColumn)).uniqueResult() != null;
//	}
	
	
	public boolean isExistsExceptThis(Class clazz, String findInColumn, Object valueFind, String exceptInColumn,
			Object exceptInValue) {

		System.out.println("in_isExistsExceptThis " + findInColumn + " :: " + valueFind + " :: " + clazz);
		Session session = factory.openSession();
		try {
		    return session.createCriteria(clazz)
		        .add(Restrictions.eq(findInColumn, valueFind))
		        .add(Restrictions.not(Restrictions.like(exceptInColumn, "%" + exceptInValue + "%")))
		        .setProjection(Projections.property(findInColumn))
		        .uniqueResult() != null;
		} finally {
		    session.close();
		}
	}
	
	

	public String InsertOrUpdate(T obj) throws HibernateException, IOException, TeamTimeTrackerException {
		log.info("inside InsertOrUpdate(T obj)");
		Session session = factory.openSession();
		Transaction tx = null;

		String dbId = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(obj);
			tx.commit();
		} catch (HibernateException e) {
			log.error("Received exception in last insert tx.");
			e.printStackTrace();

			if (tx != null) {
				try {
					log.info("Rolling back the last insert.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}
			}
			throw e;

			/*
			 * 
			 * modified by Amila Giragama 20221129
			 */
		} catch (PersistenceException e) {
			log.error("2211291113Received exception in last insert tx.");
			e.printStackTrace();

			if (tx != null) {
				try {
					log.info("Rolling back the last insert.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}
			}
			SQLIntegrityConstraintViolationException sql_violation_exception = (SQLIntegrityConstraintViolationException) e
					.getCause().getCause();
			String errMsg = sql_violation_exception.getMessage();
			int inxIn = errMsg.indexOf(DBCodeConst.INDEX_PREFIX);
			if (inxIn > 0) {
				String colName = errMsg.substring(inxIn + DBCodeConst.INDEX_PREFIX.length());
				if (sql_violation_exception.getErrorCode() == DBCodeConst.DUPLICATE_ENTRY_ERROR_CODE) {
					log.error("(2212031023)Error update duplicate data in ,'" + colName + "," + errMsg);
					throw new TeamTimeTrackerException(
							EnumException.DUPLICATE_RECORD_FOUNDE + sql_violation_exception.getMessage());
				} else if (sql_violation_exception.getErrorCode() == DBCodeConst.INTEGRITY_CONSTRAINT_VIOLATION) {
					log.error("(2212031025)Error update constraint violation data in ,"
							+ sql_violation_exception.getMessage());
					throw new TeamTimeTrackerException(EnumException.INTEGRITY_CONSTRAINT_VIOLATION + errMsg);
				}
			}
			throw e;
		} catch (ConstraintViolationException e) {
			log.error("2211291114Received exception in last insert tx.");
			e.printStackTrace();

			if (tx != null) {
				try {
					log.info("Rolling back the last insert.");
					tx.rollback();
				} catch (HibernateException e1) {
					log.error("Error in roll back.");
				}
			}
			throw e;

		} finally {

			try {
				if (session != null) {
					if (session.isOpen()) {
						session.close();
					}
				}
			} catch (HibernateException e2) {
				log.info("Error in closing session. " + e2.toString());
			}
		}

		// String sbRsp = "{\"id\":\"" + orm.getId() + "\",\"status\":true}";
		String sbRsp = "{\"status\":true}";

		return sbRsp;

	}

	
	
	public T get(Class<T> class1, String string) throws IOException {

		log.info("T get(String entity, Long id)");

		Session session = null;

		T obj = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();
			obj = (T) session.get(class1, string);
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}
		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return obj;
	}

	
	public T getWithSub(Class<T> class1, String string) throws IOException {

		log.info("T get(String entity, Long id)");

		Session session = null;

		T obj = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();
			obj = (T) session.get(class1, string);
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}
		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		return obj;
	}

	
	public void initialize(BaseModel bm) throws IOException {
		log.info("T get(String entity, Long id)");

		Session session = null;

		//T obj = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();
			//obj = (T) session.get(class1, string);
			Hibernate.initialize(bm);
			
			
			//session.
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}
		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		//return obj;
	}
	
	public void initialize(List<T> bm) throws IOException {
		log.info("T get(String entity, Long id)");

		Session session = null;

		 //List<T> obj = null;

		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();
			//obj = (T) session.get(class1, string);
			Hibernate.initialize(bm);
			
		
			//session.
			tx.commit();

			// session.close();
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}
		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (HibernateException e) {
			log.error("Error in closing session. " + e.toString());
		}

		//return obj;
	}
}
