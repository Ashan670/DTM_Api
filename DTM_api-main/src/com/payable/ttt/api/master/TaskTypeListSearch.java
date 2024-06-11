package com.payable.ttt.api.master;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.hibernate.HibernateException;

import com.payable.ttt.config.SysConfigParam;
import com.payable.ttt.dto.CommonSearchRequest;
import com.payable.ttt.dto.master.ProjectDTO;
import com.payable.ttt.dto.master.TaskTypeDTO;
import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;
import com.payable.ttt.model.ORMTaskType;
//import com.payable.ttt.model.ORMProject;

/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2024-02-26
 */

@WebServlet("/list/tasktype")
public class TaskTypeListSearch extends MasterDataApiRead {

	private static final long serialVersionUID = 2211281242L;

	@Override
	protected String processMasterDataList(CommonSearchRequest csr, String userId, String ipAddress)
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

		String searchTest = csr.getSearchText();
		String hqlUserRoles;
		if (searchTest == null || searchTest.isEmpty()) {
			hqlUserRoles = " FROM ORMTaskType tbl"
					+ ((csr.getStatus() > CommonSearchRequest.ALL_STATUS) ? " where status =" + csr.getStatus() : "")
					+ " ORDER BY tt_name ";
		} else {
			hqlUserRoles = " FROM ORMTaskType tbl WHERE tt_name LIKE '%" + searchTest + "%'"
					+ ((csr.getStatus() > CommonSearchRequest.ALL_STATUS) ? " AND status =" + csr.getStatus() : "")
					+ " ORDER BY tt_name";
		}

		List<ORMTaskType> listUserRole = dbm_TaskType.getRecords(hqlUserRoles, csr.getPageSize() * csr.getPageId(),
				csr.getPageSize());
		if (listUserRole == null || listUserRole.size() == 0) {
			log.error("No data found");
			throw new TeamTimeTrackerException(EnumException.NO_TASK_TYPE);
		}

		long count = -1;

		if (csr.getPageId() == 0) {

			String hqlCount;
			if (searchTest == null || searchTest.isEmpty()) {

				hqlCount = "SELECT count(*) FROM ORMTaskType tbl "
						+ ((csr.getStatus() > CommonSearchRequest.ALL_STATUS) ? " where status =" + csr.getStatus()
								: "");
			} else {
				// Run the query with LIKE clause when searchTest is not null or empty
				hqlCount = "SELECT count(*) FROM ORMTaskType tbl WHERE tt_name LIKE '%" + searchTest + "%'"
						+ ((csr.getStatus() > CommonSearchRequest.ALL_STATUS) ? " AND status =" + csr.getStatus() : "");
			}
			try {
				count = dbm_Project.getSingleResultL(hqlCount, null, null);
			} catch (HibernateException e) {
				log.error("(2211241242)HibernateException in dbm_userRole.getSingleResultL.  Err:" + e.toString());
				throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
			}
		}
		String seprator = "";
		StringBuffer sbUserRoleList = new StringBuffer("{\"count\":" + count + ", \"arr\":[");
		for (ORMTaskType ur : listUserRole) {
			TaskTypeDTO curr = new TaskTypeDTO(ur);
			sbUserRoleList.append(seprator);
			sbUserRoleList.append(curr.getJson());
			seprator = ",";
		}
		sbUserRoleList.append("]}");
		return sbUserRoleList.toString();
	}

}
