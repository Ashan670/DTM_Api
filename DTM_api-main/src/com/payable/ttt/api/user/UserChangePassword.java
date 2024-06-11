package com.payable.ttt.api.user;

import java.io.IOException;

import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;


import com.payable.ttt.dto.CommonSearchRequest;
import com.payable.ttt.exception.EnumException;
import com.payable.ttt.model.ORMUser;
import com.payable.ttt.model.ORMUserPasswordHistory;
import com.payable.ttt.util.CryptoUtil;
import com.payable.ttt.exception.TeamTimeTrackerException;

/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-12-21
 */

@WebServlet("/user/changePassword")
public class UserChangePassword extends UserApiRead{

	//TODO suba request to remove user id from the request and use token user
	
	
	private static final long serialVersionUID = 2211240847L;

	@Override
	protected String processUser(CommonSearchRequest csr, String userId, String ipAddress) throws TeamTimeTrackerException {



		if (!csr.getPasswordNew().equals(csr.getPasswordConfirm())) {
			log.error("(2212210844)confirm password not match");
			throw new TeamTimeTrackerException(EnumException.CONFIRM_PASSWORD_NOT_MATCH);
		}

		if (csr.getPasswordNew().equals(csr.getPasswordCurrent())) {
			log.error("(2212210847)new password cannot be equal to old password");
			throw new TeamTimeTrackerException(EnumException.NEW_PASSWORD_EQUAL_OLD_PASSWORD);
		}

		
		ORMUser User = null;
		try {
			User = dbm_User.get(ORMUser.class, userId);
		} catch (IOException e) {
			log.error("(2212210956)IOException err : " + e.toString());
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		}

		if (User == null) {
			log.error("(2212210958)Couldn't find User for ID : " + userId);
			throw new TeamTimeTrackerException(EnumException.NO_CORPUSER);
		}

		String oldPasswordToTest = CryptoUtil.generateSHA512(csr.getPasswordCurrent() + User.getSalt());

		if (!User.getPassword().equals(oldPasswordToTest)) {
			log.error("(2212210959)Entered Current Password Incorrect : " + userId);
			throw new TeamTimeTrackerException(EnumException.CURRENT_PASSWORD_INCORRECT);
		}

		String newPasswordEncripted = CryptoUtil.generateSHA512(csr.getPasswordNew() + User.getSalt());

		String strPwdHistryQuery = "FROM ORMUserPasswordHistory tbl WHERE tbl.userId = '" + userId + "' " + " ORDER BY tbl.recordedTs DESC";
		List<ORMUserPasswordHistory> oldPasswordList = dbm_UserPasswordHistory.getRecords(strPwdHistryQuery, 0, ORMUserPasswordHistory.NO_OF_OLD_PASSWORD_CHECK_FOR_CHANGE);
		boolean isRecentlyUsed = oldPasswordList.stream().anyMatch(op -> op.getPwd().equals(newPasswordEncripted));
		if (isRecentlyUsed) {
			log.error("(221221151)Password Recently Used ");
			throw new TeamTimeTrackerException(EnumException.PASSWORD_RECENTLY_USED);
		}

		Date dayStamp = new Date();
		ORMUserPasswordHistory newLog = new ORMUserPasswordHistory(userId, oldPasswordToTest, User.getIsDefaultPassword(), dayStamp);
		User.setPassword(newPasswordEncripted);
		User.setUpdatedAt(dayStamp);
		User.setUpdatedBy(userId);
		User.setAuth(0);
		User.setExpiry(0);
		User.setIsDefaultPassword(ORMUser.IS_DEFAULT_PASSWORD_NO);

		try {
			// TODO need to use new DB hear because transaction management is not happen
			// when we write like this
			// amila
			dbm_User.update(User);
			dbm_UserPasswordHistory.uuidInsert(newLog);
		} catch (IOException e) {
			log.error("(2212211210)DB updation err : " + csr.getStatus());
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		}

		/*
		//TODO we have to fully test this way to get 
		 * value of Transaction Management 
		 * above method lost transaction management feature totally
		DB db = new DB(hibernateFactory);
		db.init();
		try {
			db.update(copUser);
			db.write(newLog);
			db.commitClose();
		} catch (HibernateException e) {
			db.rollback();
		} finally {
			db.finallyClose();
		}
		*/
		

		String sbRsp = "{\"id\":\"" + User.getId() + "\",\"status\":true}";
		return sbRsp;

		// return copUser.getJson();
	}

}

//StringBuffer jsonUserRole=new StringBuffer(userRole.getJson());
//jsonUserRole.append("[");
//for(ORMCorpUserRolePermission p:permissionls) {
//	jsonUserRole.append(p.getJson());
//	jsonUserRole.append(",");
//}
//jsonUserRole.append("]");
//
//return jsonUserRole.toString();
