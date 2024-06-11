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
import com.payable.ttt.dto.daily.ReportBasicDetailDTO;
import com.payable.ttt.dto.master.ProjectDTO;

import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;

import com.payable.ttt.model.ORMProject;
import com.payable.ttt.model.ORMRecentTask;
import com.payable.ttt.model.ORMReportBasickDetail;
import com.payable.ttt.model.ORMTask;
import com.payable.ttt.util.DateUtil;



/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-11-24
 */

@WebServlet("/report/basicDetail")
public class ReportBasicDetail extends ReadDataAPI {

	private static final long serialVersionUID = 2211281242L;

	@Override
	protected String processReportDataFetch(CommonSearchRequest csr, String userId, String ipAddress) throws TeamTimeTrackerException {

		log.info("*********** Inside _listProject ***********");

		if (csr.getPageSize() <= 0) {
			log.error("(2211241154)Invalid page size : " + csr.getPageSize());
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}

		if (csr.getPageSize() > SysConfigParam.MAX_PAGE_SIZE) {
			log.error("(2211241155)Received a request with large page size. Requested page size = " + csr.getPageSize());
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}

		if (csr.getPageId() < 0) {
			log.error("(2211241152)Invalid page Id : " + csr.getPageId());
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}
	
		String whereDate="";
		String whereUser="";
		if(csr.getStDate()!=null && (csr.getEnDate()!=null)) {
			whereDate = " startTimeEnterd BETWEEN :stInterval AND :enInterval ";
		}
		
		if(csr.getStaffUserId()!=null && csr.getStaffUserId().length()>5) {
			whereUser = " user_id ='" + csr.getStaffUserId() +  "' ";
		}
		
		
		String finalWhere="";
		if(whereDate.length()> 5 && whereUser.length()>5) {
			finalWhere = " where " + whereDate + " AND " + whereUser ;
		}else if(whereDate.length()> 5){
			finalWhere = " where " + whereDate;
		}else if(whereUser.length()>5) {
			finalWhere = " where " + whereUser;
		}
		
		if (csr.getStatus() > 0) {
			finalWhere = finalWhere + " AND is_active = "  + csr.getStatus();
		}
		
		if(csr.getTaskTypeId()!=null && csr.getTaskTypeId().length()>5) {
			finalWhere = finalWhere + " AND tasktype_id = '"  + csr.getTaskTypeId() + "' "  ;
		}
		

		String hqlUserRoles = " FROM ORMReportBasickDetail tbl "+ finalWhere +"  ORDER BY startTimeEnterd DESC ";
		List<ORMReportBasickDetail> listUserRole = null;
		if(csr.getStDate()==null) {
			listUserRole = dbm_RreportBasic.getRecords(hqlUserRoles, csr.getPageSize() * csr.getPageId(), csr.getPageSize());
		}else {
			try {
				listUserRole = dbm_RreportBasic.getRecords(hqlUserRoles, csr.getPageSize() * csr.getPageId(), csr.getPageSize(),DateUtil.getDateSOD(csr.getStDate()),DateUtil.getDateEOD(csr.getEnDate()));
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
		boolean activeTaskFound=false;
		boolean onHoldTaskFound = false;
		String formatedTotalDuration = "";
		if (csr.getPageId() == 0) {
			String hqlCount = "SELECT count(*) FROM ORMReportBasickDetail tbl  " + finalWhere ;
			
			try {
				if(csr.getStDate()==null) {
					count = dbm_RreportBasic.getSingleResultL(hqlCount, null, null);
				}else {
					count = dbm_RreportBasic.getSingleResultL(hqlCount, DateUtil.getDateSOD(csr.getStDate()),DateUtil.getDateEOD(csr.getEnDate()));
				}
				
			} catch (HibernateException e) {
				log.error("(2211241242)HibernateException in dbm_userRole.getSingleResultL.  Err:" + e.toString());
				throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
			}
			
//			String hqlUserTask = " FROM ORMRecentTask tbl where user_id ='" + userId + "' ";
//			// + " ORDER BY is_active, startTimeEnterd DESC ";

			List<ORMReportBasickDetail> tasks = null;
			try {
				tasks = dbm_RreportBasic.getRecords(hqlUserRoles, 0, 1000, DateUtil.getDateSOD(csr.getStDate()),
						DateUtil.getDateEOD(csr.getEnDate()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Duration fulltotalDuration = Duration.ZERO;
			for (ORMReportBasickDetail task : tasks) {
				if (task.getIs_active() == ORMTask.STATUS_ACTIVATED)
					activeTaskFound = true;
				if (task.getIs_active() == ORMTask.STATUS_ON_HOLD)
					onHoldTaskFound = true;
				Duration durationFull = null;
				if (task.getEndTimeEnterd() == null) {
					Date now = new Date();
					durationFull = Duration.between(task.getStartTimeEnterd().toInstant(), now.toInstant());
				} else {
					durationFull = Duration.between(task.getStartTimeEnterd().toInstant(),
							task.getEndTimeEnterd().toInstant());
				}
				fulltotalDuration = fulltotalDuration.plus(durationFull);
			}
			
			formatedTotalDuration = DateUtil.formatDuration(fulltotalDuration);
		}
		
		ReportBasicDetailDTO.setTotalDurationInMillis(0);

		String seprator="";
		StringBuffer sbUserRoleList = new StringBuffer("{\"count\":" + count + ", \"arr\":[");
		for (ORMReportBasickDetail ur : listUserRole) {
			
			ReportBasicDetailDTO curr = new ReportBasicDetailDTO(ur);
			sbUserRoleList.append(seprator);
			sbUserRoleList.append(curr.getJson());
			seprator= ",";
		}
		
		if(formatedTotalDuration.equals("")){
			formatedTotalDuration="-1";
		}
		
		String currentTaskStatus = ORMTask.STATUS_DEACTIVATED + "";
		if (onHoldTaskFound)
			currentTaskStatus = ORMTask.STATUS_ON_HOLD + "";
		if (activeTaskFound)
			currentTaskStatus = ORMTask.STATUS_ACTIVATED + "";
		if(csr.getPageId()>0) currentTaskStatus ="-1";
		if(csr.getStaffUserId()==null || csr.getStaffUserId().length()<5) currentTaskStatus ="-1";
		
		sbUserRoleList.append("],\r\n \"totalWorkHr\": \""+ formatedTotalDuration +"\"\r\n ,\"currentTaskStatus\": "+ currentTaskStatus +" }");
		return sbUserRoleList.toString();
	}

}



//Duration totalDuration = Duration.ZERO;
//for (TaskTime taskTime : taskTimes) {
//    LocalDateTime startDateTime = taskTime.getStartDateTime();
//    LocalDateTime endDateTime = taskTime.getEndDateTime();
//    Duration duration = Duration.between(startDateTime, endDateTime);
//    totalDuration = totalDuration.plus(duration);
//}