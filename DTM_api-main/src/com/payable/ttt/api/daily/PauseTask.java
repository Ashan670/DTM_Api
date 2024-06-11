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
//i/mport com.payable.ttt.util.StringValidationsUtil;

@WebServlet("/task/pause")
public class PauseTask extends DailyTasks {

	private static final long serialVersionUID = 221212101112L;

	@Override
	protected String processDailyTaskUpdate(DailyTaskDTO dt, ORMUser user, String ipAddress) throws TeamTimeTrackerException {

		boolean isActiveTaskExists = dbm_Task.isExists(ORMTask.class, "user_id", user.getId(), "is_active", 1);

		if (!isActiveTaskExists) {
			log.error("(2302201951)No Active Task Found for the user = " + user.getId());
			throw new TeamTimeTrackerException(EnumException.NO_ACTIVE_TASK_FIND);
		}

		System.out.println("=======> " + dt.getEnterd_end_time());
		Date enterdDateTime = null;
		if (dt.getEnterd_end_time() != null && (dt.getEnterd_end_time().length() > 10)) {
			try {
				enterdDateTime = DateUtil.toDate(dt.getEnterd_end_time());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		db.init();
//		DB dbReadTask = new DB(hibernateFactory);
//		dbReadTask.init();
		String hqlTask="From ORMTask where is_active="+ ORMTask.STATUS_ACTIVATED +" and user_id = '" + user.getId() + "'";
		List<ORMTask> foundActive =db.read(hqlTask);
		ORMTask task =foundActive.get(0);

		
//		DB dbReadTime = new DB(hibernateFactory);
//		dbReadTime.init();
		String hqlTaskTime="From ORMTaskStart where is_active="+ ORMTask.STATUS_ACTIVATED +" and taskId='"+ task.getId() +"'";
		List<ORMTaskStart> foundActiveTime =db.read(hqlTaskTime);
		ORMTaskStart taskTime=foundActiveTime.get(0);
		task.setIs_active(ORMTask.STATUS_ON_HOLD);
		taskTime.setIs_active(ORMTask.STATUS_DEACTIVATED);
		ORMTaskEnd taskEnd=new ORMTaskEnd();
		taskEnd.setId(taskTime.getId());
		taskEnd.setIs_active(ORMTask.STATUS_ON_HOLD);
		Date nowEnd=new Date();
		taskEnd.setSystem_time(nowEnd);
		taskEnd.setTask(task);
		if(enterdDateTime==null)
			taskEnd.setEnterd_time(nowEnd);
		else
			taskEnd.setEnterd_time(enterdDateTime);

		if(taskTime.getEnterd_time().after(taskEnd.getEnterd_time())){
			log.error("(2302281420) = Invalid Task Pause Date and Time " );
			throw new TeamTimeTrackerException(EnumException.INVALID_TASK_PAUSE_TIME);
		}
		
		long durationInMillis = taskEnd.getEnterd_time().getTime() - taskTime.getEnterd_time().getTime();
		long durationInHours = durationInMillis / (1000 * 60 * 60); // Convert milliseconds to hours

		if (durationInHours > 24) {
		    log.error("(2302281406) = Invalid Task Duration: Duration is greater than 24 hours");
		    throw new TeamTimeTrackerException(EnumException.INVALID_TASK_DURATION);
		}
		
		
//		DB db = new DB(hibernateFactory);
//		db.init();
		try {
			db.update(task);
			db.update(taskTime);
			db.write(taskEnd);
			db.commitClose();
		} catch (HibernateException e) {
			db.rollbackClose();
		} finally {
//			db.finallyClose();
//			dbReadTime.finallyClose();
//			dbReadTask.finallyClose();
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
