package com.payable.ttt.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.properties.PropertiesConfiguration;
import org.apache.logging.log4j.core.config.properties.PropertiesConfigurationFactory;

@WebListener
public class MPDServletConList implements ServletContextListener {

	private final Logger logger = LogManager.getLogger(this.getClass());

	public MPDServletConList() {

	}

	public void contextInitialized(ServletContextEvent config) {

		String log4jLocation;

		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			log4jLocation = "e:/payable/ttt/config/log4j_payableteamtimetracker_api.properties";
		} else {
			log4jLocation = "/payable/ttt/config/log4j_payableteamtimetracker_api.properties";
		}

		System.out.println("log4j property file: " + log4jLocation);

		File logProperty = new File(log4jLocation);

		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(logProperty));
			PropertiesConfigurationFactory factory = new PropertiesConfigurationFactory();
			LoggerContext context = (LoggerContext) LogManager.getContext(false);
			PropertiesConfiguration configuration = factory.getConfiguration(context, source);
			configuration.start();

			context.start(configuration);
			logger.info("Configured Log4j2");
		} catch (IOException e) {

		}
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {

	}

}
