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
import com.payable.ttt.dbManager.DB;
import com.payable.ttt.dto.daily.DailyTaskDTO;
import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;
import com.payable.ttt.model.ORMTask;
import com.payable.ttt.model.ORMTaskEnd;
import com.payable.ttt.model.ORMTaskStart;
import com.payable.ttt.model.ORMUser;

/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-12-05
 */

public abstract class DailyTasks extends TeamTimeTrackerBaseHandler {
	private static final long serialVersionUID = 2211240845L;

	protected BaseDbManager<ORMTask> dbm_Task;
//	protected BaseDbManager<ORMTaskStart> dbm_TaskStart;
//	protected BaseDbManager<ORMTaskEnd> dbm_TaskEnd;

	DB db = new DB(hibernateFactory);
	

	public DailyTasks() {
		super();
		dbm_Task = new BaseDbManager<ORMTask>(hibernateFactory);
		db = new DB(hibernateFactory);
		
//		dbm_TaskStart = new BaseDbManager<ORMTaskStart>(hibernateFactory);
//		dbm_TaskEnd = new BaseDbManager<ORMTaskEnd>(hibernateFactory);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dbm_Task = new BaseDbManager<ORMTask>(hibernateFactory);
		db = new DB(hibernateFactory);
		db.init();
//		dbm_TaskStart = new BaseDbManager<ORMTaskStart>(hibernateFactory);
//		dbm_TaskEnd = new BaseDbManager<ORMTaskEnd>(hibernateFactory);
	}

	@Override
	protected String processRequest(HttpServletRequest request, HttpServletResponse response, ORMUser user, String strData) throws ServletException, IOException, TeamTimeTrackerException {

		String ipAddress = request.getRemoteAddr();

		// CommonSearchRequest csr = new CommonSearchRequest();

		DailyTaskDTO dto = null;

		try {
			dto = gson.fromJson(strData, DailyTaskDTO.class);
		} catch (Exception e) {
			log.error("Cannot create DailyTask(2212051407) json. Err:" + e.toString());
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}

		if (dto == null) {
			log.error("DailyTask(2212051411) object is null.");
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}
		if (dto.getId() != null && (dto.getId().equalsIgnoreCase(user.getId()))) {

		} else {
			Set<ConstraintViolation<DailyTaskDTO>> validationErrors = validator.validate(dto);
			if (!validationErrors.isEmpty()) {
				for (ConstraintViolation<DailyTaskDTO> error : validationErrors) {
					TeamTimeTrackerException ex = new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
					ex.setExpMessage(error.getMessage());
					log.error("(2212051401)" + error.getMessage());
					throw ex;
				}
			}
		}

		return processDailyTaskUpdate(dto, user, ipAddress);
	}

	protected abstract String processDailyTaskUpdate(DailyTaskDTO dto, ORMUser user, String ipAddress) throws TeamTimeTrackerException;

	
}
