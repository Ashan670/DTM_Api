package com.payable.ttt.api.userrole;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.hibernate.HibernateException;

import com.payable.ttt.config.SysConfigParam;
import com.payable.ttt.dto.CommonSearchRequest;
import com.payable.ttt.dto.userrole.UserRoleDTO;
import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;
import com.payable.ttt.model.ORMUserRole;


/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-11-24
 */

@WebServlet("/userrole/listSearch")
public class UserRoleListSearch extends UserRoleApiRead {

	private static final long serialVersionUID = 2211281242L;

	@Override
	protected String processCorpUserRole(CommonSearchRequest csr, String userId, String ipAddress) throws TeamTimeTrackerException {

		log.info("*********** Inside _listCorpUserRole ***********");

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

		String hqlUserRoles = "FROM ORMUserRole tbl" + ((csr.getStatus() > CommonSearchRequest.ALL_STATUS) ? " where status =" + csr.getStatus() : "") + " ORDER BY tbl.createdAt DESC";

		// csr.getStatus()>CommonSearchRequest.DEFAULT_STATUS

		List<ORMUserRole> listUserRole = dbm_CorpUserRole.getRecords(hqlUserRoles, csr.getPageSize() * csr.getPageId(), csr.getPageSize());
		if (listUserRole == null || listUserRole.size() == 0) {
			log.error("No data found");
			throw new TeamTimeTrackerException(EnumException.NO_USER_ROLE);
		}

		long count = -1;

		if (csr.getPageId() == 0) {
			String hqlCount = "SELECT count(*) FROM ORMUserRole tbl " + ((csr.getStatus() > CommonSearchRequest.ALL_STATUS) ? " where status =" + csr.getStatus() : "");
			
			try {
				count = dbm_CorpUserRole.getSingleResultL(hqlCount, null, null);
			} catch (HibernateException e) {
				log.error("(2211241242)HibernateException in dbm_userRole.getSingleResultL.  Err:" + e.toString());
				throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
			}
		}
		String seprator="";
		StringBuffer sbUserRoleList = new StringBuffer("{\"count\":" + count + ", \"arr\":[");
		for (ORMUserRole ur : listUserRole) {
			UserRoleDTO curr = new UserRoleDTO(ur);
			sbUserRoleList.append(seprator);
			sbUserRoleList.append(curr.getJson());
			seprator= ",";
		}
		sbUserRoleList.append("]}");
		return sbUserRoleList.toString();
	}

}
