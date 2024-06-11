package com.payable.ttt.api.daily;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.hibernate.HibernateException;

import com.payable.ttt.api.master.MasterDataApiRead;
import com.payable.ttt.config.SysConfigParam;
import com.payable.ttt.dbManager.BaseDbManager;
import com.payable.ttt.dto.CommonSearchRequest;
import com.payable.ttt.dto.daily.ReportBasicDetailDTO;
import com.payable.ttt.dto.daily.ReportBasicProjectDetailDTO;
import com.payable.ttt.dto.master.ProjectDTO;

import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;

import com.payable.ttt.model.ORMProject;
import com.payable.ttt.model.ORMRecentTask;
import com.payable.ttt.model.ORMReportBasickDetail;
import com.payable.ttt.model.ORMTask;
import com.payable.ttt.model.ORMUser;
import com.payable.ttt.util.DateUtil;

/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-11-24
 */

@WebServlet("/report/basicProjectDetails")
public class ProjectBasicDetailSummary extends ReadDataAPI {

	private static final long serialVersionUID = 2211281242L;

	@Override
	protected String processReportDataFetch(CommonSearchRequest csr, String userId, String ipAddress)
			throws TeamTimeTrackerException, IOException {

		log.info("*********** Inside _basicProjectDetails ***********");

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

		String hqltaskDetail = "FROM ORMUser where id = '" + userId + "'";

		System.out.println("hqltaskDetail" + hqltaskDetail);
		List<ORMUser> taskDetail = null;
		taskDetail = dbm_User.getRecords(hqltaskDetail);

		if (taskDetail == null || taskDetail.size() == 0) {
			log.error("No data found");
			throw new TeamTimeTrackerException(EnumException.NO_RECORD_FOUND);
		}

		String roleId = taskDetail.get(0).getUserRoleId();

		if (!roleId.equalsIgnoreCase(SysConfigParam.PM_ROLE_ID)) {
			log.error("(2211241153)Invalid User : " + csr.getPageId());
			throw new TeamTimeTrackerException(EnumException.ACCESS_DENIED);
		}

//		String whereDate = "";
//		String whereUser = "";
//		String whereProject = "";
//		String whereCat = "";
//		String whereAct = "";
		String hql = "";

		if (csr.getStDate() != null && (csr.getEnDate() != null)) {
			hql = " startTimeEnterd BETWEEN :stInterval AND :enInterval ";
		}

		if (csr.getStaffUserId() != null && csr.getStaffUserId().length() > 5) {
			hql = hql + "AND user_id ='" + csr.getStaffUserId() + "' ";
		}

		if (csr.getProjectId() != null && csr.getProjectId().length() > 5) {
			hql = hql + "AND pro_id ='" + csr.getProjectId() + "' ";
		}

		if (csr.getCategoryId() != null && csr.getCategoryId().length() > 5) {
			hql = hql + "AND cat_id ='" + csr.getCategoryId() + "' ";
		}

		if (csr.getActivityId() != null && csr.getActivityId().length() > 5) {
			hql = hql + "AND act_id ='" + csr.getActivityId() + "' ";
		}
		
		if (csr.getTaskTypeId() != null && csr.getTaskTypeId().length() > 5) {
			hql = hql + "AND tasktype_id ='" + csr.getTaskTypeId() + "' ";
		}
		
		

		String finalhql = hql;

		/*
		 * String hqlUserRoles
		 * ="SELECT   user_id,    pro_id,    pro_name,    cat_id,    cat_name,    act_id,    act_name,"
		 * + "     firstName,    lastName,    email,    user_group_id," +
		 * "    group_name,    user_role_id,    role_name" +
		 * "    ,sum(TIMESTAMPDIFF(MINUTE, startTimeEnterd, endTimeEnterd)) as durationInMinute"
		 * + "    FROM ORMReportBasickDetail " // + "    where "+ finalhql+"" +
		 * "    group by " +
		 * "    user_id,    pro_id,    pro_name,    cat_id,    cat_name,    act_id,    act_name,"
		 * + "    firstName,    lastName,    email,    user_group_id," +
		 * "    group_name,    user_role_id,    role_name" + "    order by email";
		 */
		String hqlUserRoles = "SELECT   d.user_id,    d.pro_id,    d.pro_name,    d.cat_id,    d.cat_name,    d.act_id,    d.act_name,"
				+ "     d.firstName,    d.lastName,    d.email,    d.user_group_id,"
				+ "    d.group_name,    d.user_role_id,    d.role_name,  d.tasktype_id, d.tasktype_name, "
				+ " SUM(d.enterd_duration_seconds) AS totalMinutes "

				+ "    FROM ORMReportBasickDetail d" + "    where " + finalhql + ""
				+ "    and endTimeEnterd is not null" + "    group by "
				+ "    d.user_id,    d.pro_id,    d.pro_name,    d.cat_id,    d.cat_name,    d.act_id,    d.act_name,"
				+ "    d.firstName,    d.lastName,    d.email,    d.user_group_id,"
				+ "    d.group_name,    d.user_role_id,    d.role_name,  d.tasktype_id, d.tasktype_name "
				+ "    order by  d.tasktype_name, d.pro_name, d.cat_name,d.act_name";
	

		System.out.println("sql 0001 " + hqlUserRoles);

		List<Object[]> listData = null;
		if (csr.getStDate() == null) {
			listData = dbm_RreportBasic.getGenaricRecords(hqlUserRoles, csr.getPageSize() * csr.getPageId(),
					csr.getPageSize());
		} else {
			try {

				listData = dbm_RreportBasic.getGenaricRecords(hqlUserRoles, csr.getPageSize() * csr.getPageId(),
						csr.getPageSize(), DateUtil.getDateSOD(csr.getStDate()), DateUtil.getDateEOD(csr.getEnDate()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (listData == null || listData.size() == 0) {
			log.error("No data found");
			throw new TeamTimeTrackerException(EnumException.NO_TASK_FOUND_FOR_THE_DAY);
		}

		long count = -1;
		long totDuration = -1;

		String formatedTotalDuration = "";
		if (csr.getPageId() == 0) {

			String hqlCount = "SELECT COUNT(*) as count " + "    FROM ORMReportBasickDetail d" + "    where " + finalhql
					+ "" + "    and endTimeEnterd is not null" + "    group by "
					+ "    d.user_id,    d.pro_id,    d.pro_name,    d.cat_id,    d.cat_name,    d.act_id,    d.act_name,"
					+ "    d.firstName,    d.lastName,    d.email,    d.user_group_id,"
					+ "    d.group_name,    d.user_role_id,    d.role_name ,  d.tasktype_id, d.tasktype_name" + "    order by d.email";

			System.out.println("sql 002 " + hqlCount);
			try {
				if (csr.getStDate() == null) {
					count = dbm_RreportBasic.getSingleResultMutipleL(hqlCount, null, null);
				} else {
					count = dbm_RreportBasic.getSingleResultMutipleL(hqlCount, DateUtil.getDateSOD(csr.getStDate()),
							DateUtil.getDateEOD(csr.getEnDate()));
				}

			} catch (HibernateException e) {
				log.error("(2211241242)HibernateException in dbm_userRole.getSingleResultL.  Err:" + e.toString());
				throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
			}

			String hqlTotDuration = "SELECT  " + " SUM(d.enterd_duration_seconds) AS totalMinutes "
					+ "    FROM ORMReportBasickDetail d" + "    where " + finalhql + ""
					+ "    and endTimeEnterd is not null";

			System.out.println("sql 003 " + hqlTotDuration);
			try {
				if (csr.getStDate() == null) {
					totDuration = dbm_RreportBasic.getSingleResultL(hqlTotDuration, null, null);
				} else {
					totDuration = dbm_RreportBasic.getSingleResultL(hqlTotDuration,
							DateUtil.getDateSOD(csr.getStDate()), DateUtil.getDateEOD(csr.getEnDate()));
				}

			} catch (HibernateException e) {
				log.error("(2211241242)HibernateException in dbm_userRole.getSingleResultL.  Err:" + e.toString());
				throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
			}

		}
		ReportBasicProjectDetailDTO.setTotalDurationInMillis(0);
		String seprator = "";
		StringBuffer sbUserRoleList = new StringBuffer("{\"count\":" + count + ", \"arr\":[");
		// int duInMinutes = 0;
		int duInSeconds = 0;

		for (Object[] obj : listData) {

			ReportBasicProjectDetailDTO curr = new ReportBasicProjectDetailDTO();

			String currentUserId = String.valueOf(obj[0]);
			String currentProId = String.valueOf(obj[1]);
			String currentProName = String.valueOf(obj[2]);
			String currentCatId = String.valueOf(obj[3]);
			String currentCatName = String.valueOf(obj[4]);
			String currentActId = String.valueOf(obj[5]);
			String currentActName = String.valueOf(obj[6]);
			String currentfName = String.valueOf(obj[7]);
			String currentlName = String.valueOf(obj[8]);
			String currentTaskTypeId =  String.valueOf(obj[14]);
			String currentTaskTypeName =  String.valueOf(obj[15]);
			
			// String durationInMinutesString = String.valueOf(obj[14]);
			String durationInSecondsString = String.valueOf(obj[16]);

			duInSeconds = Integer.parseInt(durationInSecondsString);
			Duration duration = Duration.ofSeconds(duInSeconds);
			long hours = duration.toHours();
			long minutes = duration.toMinutes() % 60;
			// long seconds = duration.toSeconds() % 60;
			long seconds = (duration.toMillis() / 1000) % 60;

			String formattedDuration = String.format("%02d:%02d:%02d", hours, minutes, seconds);
			System.out.println(formattedDuration); // Output: 00:42:00

			curr.setUser_id(currentUserId);
			curr.setPro_id(currentProId);
			curr.setPro_name(currentProName);
			curr.setCat_id(currentCatId);
			curr.setCat_name(currentCatName);
			curr.setAct_id(currentActId);
			curr.setAct_name(currentActName);
			curr.setStaffName(currentfName + " " + currentlName);
			curr.setDuration(formattedDuration);
			curr.setTask_type_id(currentTaskTypeId);
			curr.setTask_type_name(currentTaskTypeName);
			

			sbUserRoleList.append(seprator);
			sbUserRoleList.append(curr.getJson());
			seprator = ",";
			// durationInMinutes = durationInMinutes + duInMinutes;
		}

		String formattedTotWorking = "-1";
		if (totDuration > 0) {
			Duration duration = Duration.ofSeconds(totDuration);
			long hours = duration.toHours();
			long minutes = duration.toMinutes() % 60;
			long seconds = (duration.toMillis() / 1000) % 60;

			formattedTotWorking = String.format("%02d:%02d:%02d", hours, minutes, seconds);

		}
		formatedTotalDuration = formattedTotWorking;

		sbUserRoleList.append("],\r\n \"totalWorkHr\": \"" + formatedTotalDuration + "\"\r\n  " + " }");
		return sbUserRoleList.toString();
	}

}
