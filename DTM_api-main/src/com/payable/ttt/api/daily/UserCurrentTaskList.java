package com.payable.ttt.api.daily;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.hibernate.HibernateException;

import com.payable.ttt.api.master.MasterDataApiRead;
import com.payable.ttt.config.SysConfigParam;
import com.payable.ttt.dbManager.BaseDbManager;
import com.payable.ttt.dto.CommonSearchRequest;
import com.payable.ttt.dto.daily.DailyTaskDTO;
import com.payable.ttt.dto.master.ProjectDTO;

import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;

import com.payable.ttt.model.ORMProject;
import com.payable.ttt.model.ORMRecentTask;
import com.payable.ttt.model.ORMTask;
import com.payable.ttt.util.DateUtil;

/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-11-24
 */

@WebServlet("/list/currentTask")
public class UserCurrentTaskList extends ReadDataAPI {

	private static final long serialVersionUID = 2211281242L;

	@Override
	protected String processReportDataFetch(CommonSearchRequest csr, String userId, String ipAddress)
			throws TeamTimeTrackerException {

		log.info("*********** Inside _listProject ***********");

		if (csr.getPageSize() <= 0) {
			log.error("(2211241154)Invalid page size : " + csr.getPageSize());
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}

		if (csr.getPageSize() > SysConfigParam.MAX_PAGE_SIZE) {
			log.error(
					"(2211241155)Received a request with large page size. Requested page size = " + csr.getPageSize());
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}

		if (csr.getPageId() < 0) {
			log.error("(2211241152)Invalid page Id : " + csr.getPageId());
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}
		Date reportDate = new Date();
		String where = "";
		if (csr.getStDate() != null) {
			reportDate = csr.getStDate();
			where = " AND startTimeEnterd BETWEEN :stInterval AND :enInterval  ";
			// where = " AND ((startTimeEnterd BETWEEN :stInterval AND :enInterval) OR
			// (endTimeSystem BETWEEN :stInterval AND :enInterval)) ";
		}

		String hqlUserRoles = " FROM ORMRecentTask tbl where user_id ='" + userId + "' " + where
				+ "  ORDER BY is_active, startTimeEnterd DESC ";
		List<ORMRecentTask> listUserRole = null;
		if (csr.getStDate() == null) {
			listUserRole = dbm_RecentTask.getRecords(hqlUserRoles, csr.getPageSize() * csr.getPageId(),	csr.getPageSize());
		} else {
			try {
				listUserRole = dbm_RecentTask.getRecords(hqlUserRoles, csr.getPageSize() * csr.getPageId(),
						csr.getPageSize(), DateUtil.getDateSOD(reportDate), DateUtil.getDateEOD(reportDate));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (listUserRole == null || listUserRole.size() == 0) {
			log.error("No data found");
			throw new TeamTimeTrackerException(EnumException.NO_TASK_FOUND_FOR_THE_DAY);
		}

		long count = -1;
		//long duration = -1;
		String formatedTotalDuration = "";
		boolean activeTaskFound = false;
		boolean onHoldTaskFound = false;
		if (csr.getPageId() == 0) {
			String hqlCount = "SELECT count(*) FROM ORMRecentTask tbl  where user_id ='" + userId + "' " + where;

			try {
				if (csr.getStDate() == null) {
					count = dbm_RecentTask.getSingleResultL(hqlCount, null, null);
				} else {
					count = dbm_RecentTask.getSingleResultL(hqlCount, DateUtil.getDateSOD(reportDate),
							DateUtil.getDateEOD(reportDate));
				}
			} catch (HibernateException e) {
				log.error("(2211241242)HibernateException in dbm_userRole.getSingleResultL.  Err:" + e.toString());
				throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
			}


//this is use for calculate total duration with out pagination
			List<ORMRecentTask> tasks = null;
			try {
				tasks = dbm_RecentTask.getRecords(hqlUserRoles, 0, 1000, DateUtil.getDateSOD(reportDate),
						DateUtil.getDateEOD(reportDate));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Duration fulltotalDuration = Duration.ZERO;
			for (ORMRecentTask task : tasks) {
				if (task.getIs_active() == ORMTask.STATUS_ACTIVATED)
					activeTaskFound = true;
				if (task.getIs_active() == ORMTask.STATUS_ON_HOLD)
					onHoldTaskFound = true;
				Duration durationFull = null;
				if (task.getEndTimeEnterd() == null) {
					Date now = new Date();
					durationFull = Duration.between(task.getStartTimeEnterd().toInstant(), now.toInstant());
				} else {
					durationFull = Duration.between(task.getStartTimeEnterd().toInstant(),task.getEndTimeEnterd().toInstant());
				}
				fulltotalDuration = fulltotalDuration.plus(durationFull);
			}
			formatedTotalDuration = DateUtil.formatDuration(fulltotalDuration);
		}

		DailyTaskDTO.setTotalDurationInMillis(0);
		
		String seprator = "";
		StringBuffer sbUserRoleList = new StringBuffer("{\"count\":" + count + ", \"arr\":[");
		for (ORMRecentTask ur : listUserRole) {
			DailyTaskDTO curr = new DailyTaskDTO(ur);
			sbUserRoleList.append(seprator);
			sbUserRoleList.append(curr.getJson());
			seprator = ",";
		}

		String currentTaskStatus = ORMTask.STATUS_DEACTIVATED + "";
		if (onHoldTaskFound)
			currentTaskStatus = ORMTask.STATUS_ON_HOLD + "";
		if (activeTaskFound)
			currentTaskStatus = ORMTask.STATUS_ACTIVATED + "";
		if(csr.getPageId()>0) {
			currentTaskStatus ="-1";
			formatedTotalDuration ="-1";
		}
//		sbUserRoleList.append("],\r\n \"totalWorkHr\": \"" + DailyTaskDTO.totalDuration()
//				+ "\"\r\n ,\"currentTaskStatus\": " + currentTaskStatus + " }");
		sbUserRoleList.append("],\r\n \"totalWorkHr\": \"" + formatedTotalDuration + "\"\r\n ,\"currentTaskStatus\": "
				+ currentTaskStatus + " }");

		return sbUserRoleList.toString();
	}

//	public static String totalDuration(long totalDurationInMillis) {
//		int totalHours = (int) (totalDurationInMillis / (1000 * 60 * 60));
//		int totalMinutes = (int) (totalDurationInMillis / (1000 * 60)) % 60;
//		int totalSeconds = (int) (totalDurationInMillis / 1000) % 60;
//		return String.format("%02d:%02d:%02d", totalHours, totalMinutes, totalSeconds);
//	}



}

//Duration totalDuration = Duration.ZERO;
//for (TaskTime taskTime : taskTimes) {
//    LocalDateTime startDateTime = taskTime.getStartDateTime();
//    LocalDateTime endDateTime = taskTime.getEndDateTime();
//    Duration duration = Duration.between(startDateTime, endDateTime);
//    totalDuration = totalDuration.plus(duration);
//}