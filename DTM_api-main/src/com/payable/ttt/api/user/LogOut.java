package com.payable.ttt.api.user;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.hibernate.HibernateException;

import com.fasterxml.uuid.Generators;
import com.payable.ttt.dbManager.BaseDbManager;
import com.payable.ttt.dto.CommonSearchRequest;
import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;
//import com.payable.ttt.model.ORMCorpUser;
//import com.payable.ttt.model.ORMCorpUserLoginHistory;
import com.payable.ttt.model.ORMUser;
import com.payable.ttt.model.ORMUserLoginHistory;
import com.payable.ttt.util.MPOSUtils;

@WebServlet("/user/logout")
public class LogOut extends UserApiRead {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2212161555L;

	//private BaseDbManager<ORMTerminal> dbm_terminal;
	
	private BaseDbManager<ORMUserLoginHistory> dbm_corp_user_log;

	public LogOut() {
		super();
		//dbm_terminal = new BaseDbManager<ORMTerminal>(hibernateFactory);
		dbm_corp_user_log = new BaseDbManager<ORMUserLoginHistory>(hibernateFactory);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//dbm_terminal = new BaseDbManager<ORMTerminal>(hibernateFactory);
		dbm_corp_user_log = new BaseDbManager<ORMUserLoginHistory>(hibernateFactory);
	}

	
	@Override
	protected String processUser(CommonSearchRequest csr, String userId, String ipAddress) throws TeamTimeTrackerException {
		int tid = MPOSUtils.tidGenerator();
		log.info("TID:" + tid + "  " + "Inside function performLogout");

		if (csr.getSrcIp() == null) {
			log.error("(2212170312)TID:" + tid + "  " + "SrcIp object is null.");
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}

		//ORMTerminal loggedInUser = null;
		ORMUser corpUser = null;

		try {
			
			corpUser = dbm_User.get(ORMUser.class, userId);
		
			
		} catch (IOException e) {
			log.error("(2212170313)IOException err : " + e.toString());
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		}

		if (corpUser == null) {
			log.error("(2212170314)Couldn't find Corp User object for ID : " + userId);
			_logFailureAttemptswithOutTerminalUser(userId, csr.getSrcIp(), ORMUserLoginHistory.STATUS_TERMINAL_NOT_EXISTS);
			throw new TeamTimeTrackerException(EnumException.NO_RECORD_FOUND);
		}

		log.info("(2212170324)Inside function performLogout " + corpUser.getEmail() );
		
		long currentTs = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

		long auth = 0;

		try {
			log.info("(2212170315)Terminal Record is updated with auth details for " + userId);
			corpUser.setAuth(auth);
			corpUser.setLastLoggedTs(currentTs);
			corpUser.setExpiry(0);
			dbm_User.update(corpUser);
		} catch (IOException e) {
			log.error("(2212170316)IOException err : " + e.toString());
			_logAttemptswithTerminalUser(corpUser, csr.getSrcIp(), ORMUserLoginHistory.STATUS_FAILED_LOGOUT);
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		}
		String sbRsp = "{\"status\":true}";
		return sbRsp;
	}

	private void _logFailureAttemptswithOutTerminalUser(String id, String ip, int status) throws TeamTimeTrackerException {

		log.info("(2212170317)Executing function : _logFailureAttemptswithOutTerminalUser");
		ORMUserLoginHistory logUser = new ORMUserLoginHistory();

		UUID uuid = Generators.timeBasedGenerator().generate();
		logUser.setId(uuid.toString());
	
		logUser.setUsername("-");
		logUser.setSrcIp(ip);
		logUser.setStatus(status);
		logUser.setErrorWeight(1);
		logUser.setLogtime(new Date());

		try {
			String rs = dbm_corp_user_log.uuidInsert(logUser);
		} catch (HibernateException he) {

			log.error("(2212170318)Error in saving ORMUserLoginHistory object." + he.toString());
			throw new TeamTimeTrackerException(EnumException.PROCESS_BLOCKED);

		} catch (IOException e) {

			log.error("(2212170319)IOException err : " + e.toString());
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		}
		log.info("(2212170320)Log Out attempt is recorded in Merchant logHistory for " + id);
	}

	private void _logAttemptswithTerminalUser(ORMUser u, String ip, int status) throws TeamTimeTrackerException {

		log.info("(2212170321)Executing function : _logAttemptswithTerminalUser");
		ORMUserLoginHistory logUser = new ORMUserLoginHistory();

		UUID uuid = Generators.timeBasedGenerator().generate();
		logUser.setId(uuid.toString());
		logUser.setUsername(u.getEmail());
		logUser.setSrcIp(ip);
		logUser.setStatus(status);
		logUser.setErrorWeight(0);
		logUser.setLogtime(new Date());
		// Removed by Nadeeshya on 2021-08-27
		// logUser.setDeviceId(u.getDeviceId());
		// logUser.setSimId(u.getSimId());

		try {
			String rs = dbm_corp_user_log.uuidInsert(logUser);
		} catch (HibernateException he) {

			log.error("(2212170322)Error in saving ORMUserLoginHistory object." + he.toString());
			throw new TeamTimeTrackerException(EnumException.PROCESS_BLOCKED);

		} catch (IOException e) {

			log.error("IOException err : " + e.toString());
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		}
		log.info("Log Out attempt is recorded in Merchant logHistory for " + u.getEmail());
	}

//	@Override
//	protected String processUser(CommonSearchRequest csr, String userId, String ipAddress) throws TeamTimeTrackerException {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
