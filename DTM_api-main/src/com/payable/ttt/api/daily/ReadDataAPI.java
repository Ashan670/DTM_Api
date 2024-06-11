package com.payable.ttt.api.daily;

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
import com.payable.ttt.model.ORMActivity;

import com.payable.ttt.model.ORMCategory;
import com.payable.ttt.model.ORMProject;
import com.payable.ttt.model.ORMRecentTask;
import com.payable.ttt.model.ORMReportBasickDetail;
import com.payable.ttt.model.ORMUser;
import com.payable.ttt.model.ORMUserRole;

/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-11-24
 */

public abstract class ReadDataAPI extends TeamTimeTrackerBaseHandler {
	private static final long serialVersionUID = 2211240845L;

//	protected BaseDbManager<ORMUser> dbm_CorpUser;
//	protected BaseDbManager<ORMUserRole> dbm_CorpUserRole;

	
	protected BaseDbManager<ORMRecentTask> dbm_RecentTask;
	protected BaseDbManager<ORMReportBasickDetail> dbm_RreportBasic;
	protected BaseDbManager<ORMUser> dbm_User;
	
//	protected BaseDbManager<ORMAllTaskDetail> dbm_AllTask;
	
	public ReadDataAPI() {
		super();
		dbm_RecentTask = new BaseDbManager<ORMRecentTask>(hibernateFactory);
		dbm_RreportBasic = new BaseDbManager<ORMReportBasickDetail>(hibernateFactory);
		dbm_User = new BaseDbManager<ORMUser>(hibernateFactory);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dbm_RecentTask = new BaseDbManager<ORMRecentTask>(hibernateFactory);
		dbm_RreportBasic = new BaseDbManager<ORMReportBasickDetail>(hibernateFactory);
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

		return processReportDataFetch(csr, userId, ipAddress);
	}

	protected abstract String processReportDataFetch(CommonSearchRequest csr, String userId, String ipAddress)
			throws TeamTimeTrackerException, IOException;

}
