package com.payable.ttt.api.user;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;


import com.payable.ttt.api.TeamTimeTrackerBaseHandler;
import com.payable.ttt.dbManager.BaseDbManager;
import com.payable.ttt.dto.CommonSearchRequest;
import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;

import com.payable.ttt.model.ORMUser;
import com.payable.ttt.model.ORMUserPasswordHistory;
import com.payable.ttt.model.ORMUserRole;

/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-11-24
 */

public abstract class UserApiRead extends TeamTimeTrackerBaseHandler {
	private static final long serialVersionUID = 2211240845L;

	protected BaseDbManager<ORMUser> dbm_User;
	protected BaseDbManager<ORMUserPasswordHistory> dbm_UserPasswordHistory;


	public UserApiRead() {
		super();
		dbm_UserPasswordHistory = new BaseDbManager<ORMUserPasswordHistory>(hibernateFactory);
		dbm_User = new BaseDbManager<ORMUser>(hibernateFactory);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dbm_UserPasswordHistory = new BaseDbManager<ORMUserPasswordHistory>(hibernateFactory);
		dbm_User = new BaseDbManager<ORMUser>(hibernateFactory);
	}

	@Override
	protected String processRequest(HttpServletRequest request, HttpServletResponse response, ORMUser user,
			String strData) throws ServletException, IOException, TeamTimeTrackerException {
		String userId = user.getId();
		String ipAddress = request.getRemoteAddr();

		CommonSearchRequest csr = new CommonSearchRequest();

		try {
			csr = gson.fromJson(strData, CommonSearchRequest.class);
		} catch (Exception e) {
			log.error("Cannot create CommonSearchRequest(2211240910) json. Err:" + e.toString());
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}

		if (csr == null) {
			log.error("CommonSearchRequest(2211240911) object is null.");
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}

		Set<ConstraintViolation<CommonSearchRequest>> validationErrors = validator.validate(csr);
		if (!validationErrors.isEmpty()) {
			for (ConstraintViolation<CommonSearchRequest> error : validationErrors) {
				TeamTimeTrackerException ex = new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
				ex.setExpMessage(error.getMessage());
				log.error("(2211242201)" + error.getMessage());
				throw ex;
			}
		}

		return processUser(csr, userId, ipAddress);
	}

	protected abstract String processUser(CommonSearchRequest csr, String userId, String ipAddress)
			throws TeamTimeTrackerException;

}
