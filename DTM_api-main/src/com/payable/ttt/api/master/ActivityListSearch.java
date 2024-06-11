package com.payable.ttt.api.master;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.hibernate.HibernateException;

import com.payable.ttt.config.SysConfigParam;
import com.payable.ttt.dto.CommonSearchRequest;
import com.payable.ttt.dto.master.ActivityDTO;
import com.payable.ttt.dto.master.CategoryDTO;
import com.payable.ttt.dto.master.ProjectDTO;

import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;
import com.payable.ttt.model.ORMActivity;
import com.payable.ttt.model.ORMCategory;
import com.payable.ttt.model.ORMProject;



/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-11-24
 */

@WebServlet("/list/activity")
public class ActivityListSearch extends MasterDataApiRead {

	private static final long serialVersionUID = 2211281242L;

	@Override
	protected String processMasterDataList(CommonSearchRequest csr, String userId, String ipAddress) throws TeamTimeTrackerException {

		log.info("*********** Inside _listActivity ***********");

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
		
		if(csr.getProjectId()==null || csr.getProjectId().isEmpty()) {
			log.error("Project Id cannot be null or empty: ");
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}
		
		if(csr.getCategoryId()==null || csr.getCategoryId().isEmpty()) {
			log.error("Category Id cannot be null or empty: ");
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}
		
		String hqlUserRoles =  " FROM ORMActivity tbl where (tbl.proId = '"+csr.getProjectId() +"' AND tbl.catId = '"+csr.getCategoryId() +"' )" + ((csr.getStatus() > CommonSearchRequest.ALL_STATUS) ? " AND tbl.status =" + csr.getStatus() : "") +" ORDER BY tbl.act_name ";
		
		System.out.println("=========> " + hqlUserRoles);
		
		// csr.getStatus()>CommonSearchRequest.DEFAULT_STATUS

		List<ORMActivity> listActivity= dbm_Activity.getRecords(hqlUserRoles, csr.getPageSize() * csr.getPageId(), csr.getPageSize());
		if (listActivity == null || listActivity.size() == 0) {
			log.error("No data found");
			throw new TeamTimeTrackerException(EnumException.NO_ACTIVITY);
		}

		long count = -1;

		if (csr.getPageId() == 0) {
				
			String hqlCount =  "SELECT count(*) FROM ORMActivity tbl where (tbl.proId = '"+csr.getProjectId()+"' AND tbl.catId = '"+csr.getCategoryId()+"' )" + ((csr.getStatus() > CommonSearchRequest.ALL_STATUS) ? " AND tbl.status =" + csr.getStatus() : "") ;
			
			try {
				count = dbm_Activity.getSingleResultL(hqlCount, null, null);
			} catch (HibernateException e) {
				log.error("(2211241242)HibernateException in dbm_Activity.getSingleResultL.  Err:" + e.toString());
				throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
			}
		}
		String seprator="";
		StringBuffer sbUserRoleList = new StringBuffer("{\"count\":" + count + ", \"arr\":[");
		for (ORMActivity ur : listActivity) {
			ActivityDTO curr = new ActivityDTO(ur);
			sbUserRoleList.append(seprator);
			sbUserRoleList.append(curr.getJson());
			seprator= ",";
		}
		sbUserRoleList.append("]}");
		return sbUserRoleList.toString();
	}

}
