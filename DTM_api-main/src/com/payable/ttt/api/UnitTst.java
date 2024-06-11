package com.payable.ttt.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class UnitTst
 */
@WebServlet("/UnitTst")
public class UnitTst extends HttpServlet {
	private static final long serialVersionUID = 20240415L;

	private static final String releaseDate = "15th April 2024";
	private static final String releaseDescription = "Sprint04 - Add Bug Id";
	private static final int releaseCounter = 23;
	private static final String version = "0.0.6";
	private static final String dbschema = "dbpttimetracker";
	private static final String config = "config_payableteamtimetracker.properties";

	private static final Logger logger = LogManager.getLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UnitTst() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Inside Unit test Component for PayableTeamTimeTrackerApi, Received request with IP : "
				+ request.getRemoteAddr());

		PrintWriter out = response.getWriter();

		out.println("*********************************************** ");
		out.println("Roll out date :" + releaseDate + "");
		out.println("Roll out counter :" + releaseCounter + "");
		out.println("Version :" + version + "");
		out.println("Release description :" + releaseDescription + "");
		out.println("DB schema :" + dbschema + "");
		out.println("Config changes :" + config + "");
		out.println("Server IP :" + _ip2() + "");
		out.println("Src ip : " + request.getRemoteAddr());
		out.println("forward ip : " + request.getHeader("X-Forwarded-For"));
		out.println("forward protocol : " + request.getHeader("X-Forwarded-Proto"));
		out.println("forward port : " + request.getHeader("X-Forwarded-Port"));
		out.println(" ");
		out.println("*********************************************** ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	private static String _ip2() {

		try {
			Enumeration<NetworkInterface> net = NetworkInterface.getNetworkInterfaces();
			while (net.hasMoreElements()) {
				NetworkInterface networkInterface = net.nextElement();
				Enumeration<InetAddress> add = networkInterface.getInetAddresses();
				while (add.hasMoreElements()) {
					InetAddress a = add.nextElement();
					if (!a.isLoopbackAddress() && !a.getHostAddress().contains(":")) {

						return a.getHostAddress();
					}
				}
			}
		} catch (Exception e) {
			return e.toString();
		}

		return "-";
	}

}
