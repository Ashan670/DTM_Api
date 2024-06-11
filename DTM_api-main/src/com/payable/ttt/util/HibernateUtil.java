package com.payable.ttt.util;

import java.util.Properties;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import com.payable.ttt.model.ORMUserLoginHistory;
import com.payable.ttt.model.ORMUserPasswordHistory;
import com.payable.ttt.model.ORMActivity;

import com.payable.ttt.model.ORMCategory;
import com.payable.ttt.model.ORMProject;
import com.payable.ttt.model.ORMRecentTask;
import com.payable.ttt.model.ORMReportBasickDetail;
import com.payable.ttt.model.ORMTask;
import com.payable.ttt.model.ORMTaskEnd;
import com.payable.ttt.model.ORMTaskStart;
import com.payable.ttt.model.ORMTaskType;
import com.payable.ttt.model.ORMUser;
import com.payable.ttt.model.ORMUserGroup;
import com.payable.ttt.model.ORMUserRole;



public class HibernateUtil {

	// private static Logger log = Logger.getLogger(HibernateUtil.class);

	private static SessionFactory sessionFactory = null;
	private static Validator validator = null;

	private static void _configHibernate(Properties sysProperties) {

		Configuration configuration = new Configuration();
		configuration.configure();

		configuration.setProperty("hibernate.connection.username", sysProperties.getProperty("db_user"));
		configuration.setProperty("hibernate.connection.password", sysProperties.getProperty("db_pwd"));
		configuration.setProperty("hibernate.connection.url", sysProperties.getProperty("db_url"));
		configuration.setProperty("hibernate.connection.driver_class", sysProperties.getProperty("driver_class"));
		
		//configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		configuration.setProperty("hibernate.dialect", "com.payable.util.mysqldialect.CustomMySQLDialect");
		
		/// added on 13 september 2017

		configuration.setProperty("hibernate.c3p0.idle_test_period", sysProperties.getProperty("db_pool_idle_test_period"));
		configuration.setProperty("hibernate.c3p0.timeout", sysProperties.getProperty("db_pool_timeout"));
		
		if(sysProperties.getProperty("developer_mode").equalsIgnoreCase("true")  ) {
			System.out.println("ACTIVATE DEVELOPER MODE");
			configuration.setProperty("spring.jpa.properties.hibernate.generate_statistics", sysProperties.getProperty("hibernate_statistics"));
			configuration.setProperty("logging.level.org.hibernate.stat", sysProperties.getProperty("hibernate_stat"));
			configuration.setProperty("logging.level.org.hibernate.SQL", sysProperties.getProperty("hibernate_sql"));
			configuration.setProperty("show_sql", "true");
			
			
		//	configuration.setProperty("spring.jpa.properties.hibernate.session.events.log.LOG_QUERIES_SLOWER_THAN_MS", sysProperties.getProperty("log_queries_slower_than_ms"));
		}


		configuration.addAnnotatedClass(ORMUserLoginHistory.class);
		configuration.addAnnotatedClass(ORMUserPasswordHistory.class);
		configuration.addAnnotatedClass(ORMUser.class);
		configuration.addAnnotatedClass(ORMUserRole.class);
		configuration.addAnnotatedClass(ORMTaskType.class);
		configuration.addAnnotatedClass(ORMProject.class);
		
		configuration.addAnnotatedClass(ORMTask.class);
		configuration.addAnnotatedClass(ORMTaskStart.class);
		configuration.addAnnotatedClass(ORMTaskEnd.class);
		configuration.addAnnotatedClass(ORMCategory.class);
		configuration.addAnnotatedClass(ORMActivity.class);
		configuration.addAnnotatedClass(ORMUserGroup.class);
		configuration.addAnnotatedClass(ORMRecentTask.class);
		configuration.addAnnotatedClass(ORMReportBasickDetail.class);
		
		
		// TODO add ORM to this

		StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
		sb.applySettings(configuration.getProperties());

		StandardServiceRegistry standardServiceRegistry = sb.build();

		sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);

	}

	public static SessionFactory getSessionFactory(Properties sysProperties) {

		if (sessionFactory == null) {
			_configHibernate(sysProperties);
		}

		return sessionFactory;
	}

	public static Validator getValidationFactory() {

		if (validator == null) {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			validator = factory.getValidator();
		}

		return validator;
	}

}
