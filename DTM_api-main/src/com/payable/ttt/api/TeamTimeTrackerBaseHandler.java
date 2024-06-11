package com.payable.ttt.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.apache.catalina.connector.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payable.ttt.dbManager.BaseDbManager;
import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;

import com.payable.ttt.model.ORMUser;
import com.payable.ttt.service.TeamTimeTrackerBaseHandlerService;
import com.payable.ttt.util.HibernateUtil;

public abstract class TeamTimeTrackerBaseHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected static final String ERROR_HEADER_MSG = "pttt-expMsg";
	protected static final String ERROR_HEADER_CODE = "pttt-expCode";

	protected Logger log;
	protected static SessionFactory hibernateFactory;
	protected static Gson gson;

	protected Properties sysProperties;
	protected BaseDbManager<ORMUser> db_user;
	protected static Validator validator;
	protected static int bankId;

	protected TeamTimeTrackerBaseHandlerService baseHandlerService;

	private String arr_client_ip[] = null;

	private void _loadPropertyConfig() throws FileNotFoundException, IOException {
		sysProperties = new Properties();
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			FileInputStream fin = new FileInputStream(
					"e:/payable/ttt/config/config_payableteamtimetracker.properties");
			sysProperties.load(fin);
			fin.close();
		} else {
			FileInputStream fin = new FileInputStream(
					"/payable/ttt/config/config_payableteamtimetracker.properties");
			sysProperties.load(fin);
			fin.close();
		}
		
	}

	private void _setLogger() {
		log = LogManager.getLogger(this.getClass());
	}

	public TeamTimeTrackerBaseHandler() {
		try {
			gson = new GsonBuilder().create();
			_setLogger();
			_loadPropertyConfig();
			hibernateFactory = HibernateUtil.getSessionFactory(sysProperties);
			validator = HibernateUtil.getValidationFactory();
			baseHandlerService = new TeamTimeTrackerBaseHandlerService(sysProperties, 0);
			db_user = new BaseDbManager<ORMUser>(hibernateFactory);

		} catch (Exception e) {
			System.out.println("Error in init TeamTimeTrackerBaseHandler constructor");
			System.out.println(e.toString());
		}
	}

	public void init(ServletConfig config) throws ServletException {
		_setLogger();

		try {
			gson = new GsonBuilder().create();
			_loadPropertyConfig();
			db_user = new BaseDbManager<ORMUser>(hibernateFactory);
			hibernateFactory = HibernateUtil.getSessionFactory(sysProperties);
			validator = HibernateUtil.getValidationFactory();

			baseHandlerService = new TeamTimeTrackerBaseHandlerService(sysProperties, 0);
		} catch (Exception e) {
			throw new ServletException("Error in init TeamTimeTrackerBaseHandler", e);
		}

		String strIPs = sysProperties.getProperty("portal_ip");

		if (strIPs != null) {
			strIPs = strIPs.trim();
			arr_client_ip = strIPs.split(",");
		} else {
			log.error("(2211222218)there are no permited ip details in configuration file.");
		}

		super.init(config);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		warnLogWithSignature(request, "** Received GET Request **");

		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		infoLogWithSignature(request, "Received post Request");

		try {
			_validateBasicHeader(request, response);

			String strData = baseHandlerService.readRawBody(request);

			if (strData != null) {
				log.info(strData);
			} else {
				log.info("Str data is null");
			}

			baseHandlerService.validateConfig(sysProperties);

			bankId = Integer.parseInt(sysProperties.getProperty("bank_id"));
			
			ORMUser user = baseHandlerService.validateToken(request, db_user);

			String strRes = processRequest(request, response, user, strData);

			String strResponse = baseHandlerService.generateResponse(request, strRes);

			PrintWriter out = response.getWriter();

			if (strResponse != null) {
				out.println(strResponse);
			}

			infoLogWithSignature(request, "end of processing post Request");

			if (strRes != null) {
				// log.info(strRes);
				log.info("Responding to the reqest with result data.");
			} else {
				log.info("strRes is null");
			}

		} catch (TeamTimeTrackerException e) {
			errorLogWithSignature(request, "DoPost mpos Exp:" + e.toString() + " Error code:" + e.getErrCode());
			response.setHeader(ERROR_HEADER_MSG, e.getMessage());
			response.setHeader(ERROR_HEADER_CODE, Integer.toString(e.getErrCode()));
			response.sendError(Response.SC_BAD_REQUEST, "Invalid Request");
		} finally {
			response.setHeader("Cache-Control",
					"no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
		}

	}

	private void _validateBasicHeader(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, TeamTimeTrackerException {

		String userAgent = request.getHeader("User-Agent");

		if (this.isNullOrEmptyString(userAgent)) {
			System.out.println("TeamTimeTrackerBaseHandler(2211181210)");
			throw new TeamTimeTrackerException(EnumException.INVALID_USER_AGENT);
		}

		if (!userAgent.equalsIgnoreCase("Team-Time-Tracker-Client")) {
			System.out.println("TeamTimeTrackerBaseHandler(2211181211)");
			throw new TeamTimeTrackerException(EnumException.INVALID_USER_AGENT);
		}

		String auth = request.getHeader("Portal-Auth");

		if (this.isNullOrEmptyString(auth)) {
			System.out.println("TeamTimeTrackerBaseHandler(202211181213)");
			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		}

		if (!auth.equalsIgnoreCase("452cd39d4766b21a4dbc031ad3b68320")) {
			System.out.println("TeamTimeTrackerBaseHandler(202211181212)");
			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		}

		if (arr_client_ip == null || arr_client_ip.length == 0) {
			log.error("(2211222219)arr_client_ip is null. there are no permited ip details in configuration file.");
			System.out.println("TeamTimeTrackerBaseHandler(202211181214)");
			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		}

		String requestIp = request.getRemoteAddr();

		boolean isValidIp = false;

		for (int i = 0; i < arr_client_ip.length; i++) {
			if (requestIp.equalsIgnoreCase(arr_client_ip[i].trim())) {
				isValidIp = true;
				break;
			}
		}

		if (!isValidIp) {
			log.error("(2211222220)Attempted to access PayableTeamTimeTrackerApiLogin api from unauthorized  ip : " + requestIp);
			System.out.println("TeamTimeTrackerBaseHandler(2022111812115)");
			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		}

	}

	public boolean isNullOrEmptyString(String str) {
		if (str == null) {
			return true;
		}

		if (str.length() == 0) {
			return true;
		}

		return false;
	}

	abstract protected String processRequest(HttpServletRequest request, HttpServletResponse response, ORMUser user,
			String strData) throws ServletException, IOException, TeamTimeTrackerException;

	protected void errorLogWithSignature(HttpServletRequest request, String strlog) {
		log.error("(2211222221)IP:" + request.getRemoteAddr() + " - " + strlog);
	}

	protected void infoLogWithSignature(HttpServletRequest request, String strlog) {
		log.info("IP:" + request.getRemoteAddr() + " - " + strlog);
	}

	protected void warnLogWithSignature(HttpServletRequest request, String strlog) {
		log.warn("IP:" + request.getRemoteAddr() + " - " + strlog);
	}

}
