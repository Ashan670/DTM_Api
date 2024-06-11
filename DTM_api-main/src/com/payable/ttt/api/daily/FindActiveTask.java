package com.payable.ttt.api.daily;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;

import org.hibernate.HibernateException;

import com.fasterxml.uuid.Generators;
import com.payable.ttt.config.SysConfigParam;
import com.payable.ttt.dbManager.DB;
import com.payable.ttt.dto.CommonSearchRequest;
import com.payable.ttt.dto.MailSendElem;
import com.payable.ttt.dto.daily.DailyTaskDTO;

import com.payable.ttt.exception.EnumException;

import com.payable.ttt.exception.TeamTimeTrackerException;
//import com.payable.ttt.formats.WelcomeEmailComposerV1;
import com.payable.ttt.model.ORMTask;
import com.payable.ttt.model.ORMTaskEnd;
import com.payable.ttt.model.ORMTaskStart;
import com.payable.ttt.model.ORMUser;
//import com.payable.ttt.util.CheckSumKeyManagementUtil;
import com.payable.ttt.util.CryptoUtil;
import com.payable.ttt.util.DateUtil;
//import com.payable.ttt.util.MailHelperV2;
//import com.payable.ttt.util.PasswordGenerator;
//import com.payable.ttt.util.StringValidationsUtil;

@WebServlet("/task/findActiveTask")
public class FindActiveTask extends DailyTasks {

	private static final long serialVersionUID = 221212101112L;

	@Override
	protected String processDailyTaskUpdate(DailyTaskDTO dt, ORMUser user, String ipAddress) throws TeamTimeTrackerException {

		boolean isActiveTaskExists = dbm_Task.isExists(ORMTask.class, "user_id", user.getId(), "is_active", 1);
		ORMTask task=null;
		ORMTaskStart taskTime=null;
		if(isActiveTaskExists) {
//			DB dbReadTask = new DB(hibernateFactory);
//			dbReadTask.init();
			db.init();
			String hqlTask="From ORMTask where is_active="+ ORMTask.STATUS_ACTIVATED +" and user_id = '" + user.getId() + "'";
			List<ORMTask> foundActive =db.read(hqlTask);
			task =foundActive.get(0);
	
//			DB dbReadTime = new DB(hibernateFactory);
//			dbReadTime.init();
			String hqlTaskTime="From ORMTaskStart where is_active="+ ORMTask.STATUS_ACTIVATED +" and taskId='"+ task.getId() +"'";
			List<ORMTaskStart> foundActiveTime =db.read(hqlTaskTime);
			taskTime=foundActiveTime.get(0);
			db.finallyClose();
			
		}
		String sbRsp ="";
		if(isActiveTaskExists) {
			sbRsp = "{\"id\":\"" + task.getId() + "\"";
			sbRsp = sbRsp + ",\r\n\"StartAt\":\"" + taskTime.getSystem_time() + "\"";
			sbRsp = sbRsp + ",\r\n\"status\":true}";
		}else {	
			sbRsp = sbRsp + "{\"status\":false}";
		}
		
		
		
		return sbRsp;

	}

}
