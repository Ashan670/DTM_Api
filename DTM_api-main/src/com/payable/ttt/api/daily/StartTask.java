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
import com.payable.ttt.model.ORMTaskStart;
import com.payable.ttt.model.ORMUser;
//import com.payable.ttt.util.CheckSumKeyManagementUtil;
//import com.payable.ttt.util.CryptoUtil;
import com.payable.ttt.util.DateUtil;
//import com.payable.ttt.util.MailHelperV2;
//import com.payable.ttt.util.PasswordGenerator;
//import com.payable.ttt.util.StringValidationsUtil;

@WebServlet("/task/start")
public class StartTask extends DailyTasks {

	private static final long serialVersionUID = 221212101112L;

	@Override
	protected String processDailyTaskUpdate(DailyTaskDTO dt, ORMUser user, String ipAddress) throws TeamTimeTrackerException {
		boolean isActiveTaskExists = dbm_Task.isExists(ORMTask.class, "user_id", user.getId(), "is_active", 1);
		if (isActiveTaskExists) {
			log.error("(2302201951)Active Task Found for the user = " + user.getId());
			throw new TeamTimeTrackerException(EnumException.ACTIVE_TASK_FIND);
		}
		
		Date enterdDateTime = null;
		try {
			enterdDateTime = DateUtil.toDate(dt.getEnterd_start_time());
		} catch (ParseException e1) {
			e1.printStackTrace();
			log.error("(2302202159)Date format incorrect = " + dt.getEnterd_start_time());
			throw new TeamTimeTrackerException(EnumException.INVALIED_DATE_FORMAT);
		} 
		System.out.println( " ========> " + dt.getEnterd_start_time()  + " =====> " + enterdDateTime);

		ORMTask task = new ORMTask();
		task.setAct_id(dt.getAct_id());
		task.setCat_id(dt.getCat_id());
		task.setTasktype_id(dt.getTasktype_id());
		task.setPro_id(dt.getPro_id());
		task.setUser_id(user.getId()); // USER ID 
		task.setTask_detail(dt.getTask_detail());
		task.setIs_active(1);
		task.setInit_ts(new Date());
		task.setBug_id(dt.getBug_id());
		

		if(enterdDateTime==null) new Date();
		ORMTaskStart taskStart = new ORMTaskStart();
		taskStart.setEnterd_time(enterdDateTime);
		taskStart.setIs_active(1);
		taskStart.setSystem_time(new Date());
		taskStart.setTask(task);

		
		db.init();
		
		try {
			// db.update(copUser);
			db.write(task);
			db.write(taskStart);
			db.commitClose();
		} catch (HibernateException e) {
			db.rollbackClose();
		} finally {
			//db.finallyClose();
		}

		if(task.getId()==null) {
			String sbRsp = "{\"id\":\"" + task.getId() + "\"";
			sbRsp = sbRsp + ",\r\n\"status\":false}";
			return sbRsp;
		}else {
			String sbRsp = "{\"id\":\"" + task.getId() + "\"";
			sbRsp = sbRsp + ",\r\n\"status\":true}";
			return sbRsp;
		}
	}

}
