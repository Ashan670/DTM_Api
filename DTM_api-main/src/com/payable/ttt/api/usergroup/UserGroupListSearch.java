package com.payable.ttt.api.usergroup;

import com.payable.ttt.config.SysConfigParam;
import com.payable.ttt.dto.CommonSearchRequest;
import com.payable.ttt.dto.usergroup.UserGroupDTO;
import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;
import com.payable.ttt.model.ORMUserGroup;

import javax.servlet.annotation.WebServlet;

import org.hibernate.HibernateException;

import java.util.List;

@WebServlet("/usergroup/listSearch")
public class UserGroupListSearch extends UserGroupApiRead {

    private static final long serialVersionUID = 2211281242L;

    @Override
    protected String processUserGroup(CommonSearchRequest csr, String userId, String ipAddress) throws TeamTimeTrackerException {

        log.info("*********** Inside _listUserGroup ***********");

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

        String hqlUserGroups = "FROM ORMUserGroup tbl" + ((csr.getStatus() > CommonSearchRequest.ALL_STATUS) ? " where status =" + csr.getStatus() : "");

        List<ORMUserGroup> listUserGroup = dbm_CorpUserGroup.getRecords(hqlUserGroups, csr.getPageSize() * csr.getPageId(), csr.getPageSize());
        if (listUserGroup == null || listUserGroup.size() == 0) {
            log.error("No data found");
            throw new TeamTimeTrackerException(EnumException.NO_USER_GROUP);
        }

        long count = -1;

        if (csr.getPageId() == 0) {
            String hqlCount = "SELECT count(*) FROM ORMUserGroup tbl " + ((csr.getStatus() > CommonSearchRequest.ALL_STATUS) ? " where status =" + csr.getStatus() : "");
            
            try {
                count = dbm_CorpUserGroup.getSingleResultL(hqlCount, null, null);
            } catch (HibernateException e) {
                log.error("(2211241242)HibernateException in dbm_userGroup.getSingleResultL.  Err:" + e.toString());
                throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
            }
        }
        String separator = "";
        StringBuffer sbUserGroupList = new StringBuffer("{\"count\":" + count + ", \"arr\":[");
        for (ORMUserGroup ug : listUserGroup) {
            UserGroupDTO curr = new UserGroupDTO(ug);
            sbUserGroupList.append(separator);
            sbUserGroupList.append(curr.getJson());
            separator = ",";
        }
        sbUserGroupList.append("]}");
        return sbUserGroupList.toString();
    }
}
