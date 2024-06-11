/*
 * package com.payable.ttt.api.daily;
 * 
 * import java.io.IOException; import java.time.Duration; import java.util.Date;
 * import java.util.List;
 * 
 * import javax.servlet.annotation.WebServlet;
 * 
 * import org.hibernate.HibernateException;
 * 
 * import com.payable.ttt.api.master.MasterDataApiRead; import
 * com.payable.ttt.config.SysConfigParam; import
 * com.payable.ttt.dbManager.BaseDbManager; import
 * com.payable.ttt.dto.CommonSearchRequest; import
 * com.payable.ttt.dto.daily.ReportBasicDetailDTO; import
 * com.payable.ttt.dto.daily.ReportBasicProjectDetailDTO; import
 * com.payable.ttt.dto.master.ProjectDTO;
 * 
 * import com.payable.ttt.exception.EnumException; import
 * com.payable.ttt.exception.TeamTimeTrackerException;
 * 
 * import com.payable.ttt.model.ORMProject; import
 * com.payable.ttt.model.ORMRecentTask; import
 * com.payable.ttt.model.ORMReportBasickDetail; import
 * com.payable.ttt.model.ORMTask; import com.payable.ttt.util.DateUtil;
 * 
 * 
 * @author Amila Giragama
 * 
 * @version 1.0
 * 
 * @since 2022-11-24
 * 
 * 
 * @WebServlet("/") public class ReportProjectBasicDetail extends ReadDataAPI {
 * 
 * private static final long serialVersionUID = 2211281242L;
 * 
 * @Override protected String processCorpUserRole(CommonSearchRequest csr,
 * String userId, String ipAddress) throws TeamTimeTrackerException {
 * 
 * log.info("*********** Inside _basicProjectDetails ***********");
 * 
 * if (csr.getPageSize() <= 0) { log.error("(2211241154)Invalid page size : " +
 * csr.getPageSize()); throw new
 * TeamTimeTrackerException(EnumException.INVALID_REQUEST); }
 * 
 * if (csr.getPageSize() > SysConfigParam.MAX_PAGE_SIZE) { log.error(
 * "(2211241155)Received a request with large page size. Requested page size = "
 * + csr.getPageSize()); throw new
 * TeamTimeTrackerException(EnumException.INVALID_REQUEST); }
 * 
 * if (csr.getPageId() < 0) { log.error("(2211241152)Invalid page Id : " +
 * csr.getPageId()); throw new
 * TeamTimeTrackerException(EnumException.INVALID_REQUEST); }
 * 
 * String whereDate = ""; String whereUser = ""; String whereProject = "";
 * String whereCat = ""; String whereAct = ""; String hql = "";
 * 
 * if (csr.getStDate() != null && (csr.getEnDate() != null)) { hql =
 * " startTimeEnterd BETWEEN :stInterval AND :enInterval "; }
 * 
 * if (csr.getStaffUserId() != null && csr.getStaffUserId().length() > 5) { hql
 * = hql +"AND user_id ='" + csr.getStaffUserId() + "' "; }
 * 
 * if (csr.getProjectId() != null && csr.getProjectId().length() > 5) { hql =
 * hql + "AND pro_id ='" + csr.getProjectId() + "' "; }
 * 
 * if (csr.getCategoryId() != null && csr.getCategoryId().length() > 5) { hql =
 * hql + "AND cat_id ='" + csr.getCategoryId() + "' "; }
 * 
 * if (csr.getActivityId() != null && csr.getActivityId().length() > 5) { hql =
 * hql + "AND act_id ='" + csr.getActivityId() + "' "; }
 * 
 * String finalhql = hql;
 * 
 * 
 * String hqlUserRoles = " FROM ORMReportBasickDetail tbl Where" + finalhql +
 * "  ORDER BY pro_id,cat_id,act_id,email";
 * 
 * System.out.println("hqlUserRoles" + hqlUserRoles);
 * 
 * List<ORMReportBasickDetail> listUserRole = null;
 * 
 * if (csr.getStDate() == null) { listUserRole =
 * dbm_RreportBasic.getRecords(hqlUserRoles, csr.getPageSize() *
 * csr.getPageId(), csr.getPageSize());
 * 
 * System.out.println("listUserRole" + listUserRole.size());
 * 
 * } else { try { listUserRole = dbm_RreportBasic.getRecords(hqlUserRoles,
 * csr.getPageSize() * csr.getPageId(), csr.getPageSize(),
 * DateUtil.getDateSOD(csr.getStDate()), DateUtil.getDateEOD(csr.getEnDate()));
 * System.out.println("listUserRole" + listUserRole.size());
 * 
 * } catch (IOException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } }
 * 
 * if (listUserRole == null || listUserRole.size() == 0) {
 * log.error("No data found"); throw new
 * TeamTimeTrackerException(EnumException.NO_TASK_FOUND_FOR_THE_DAY); }
 * 
 * long count = 0; boolean activeTaskFound = false; boolean onHoldTaskFound =
 * false; String formatedTotalDuration = ""; if (csr.getPageId() == 0) {
 * 
 * String hqlCount = " FROM ORMReportBasickDetail tbl Where" + finalhql +
 * "  ORDER BY pro_id,cat_id,act_id,email";
 * 
 * System.out.println("hqlCount"+hqlCount);
 * 
 * List<ORMReportBasickDetail> listUserTaskCount = null;
 * 
 * if (csr.getStDate() == null) { listUserTaskCount =
 * dbm_RreportBasic.getRecords(hqlCount, csr.getPageSize() * csr.getPageId(),
 * csr.getPageSize());
 * 
 * System.out.println("listUserTaskCount" + listUserTaskCount.size());
 * 
 * } else { try { listUserTaskCount = dbm_RreportBasic.getRecords(hqlCount,
 * csr.getPageSize() * csr.getPageId(), csr.getPageSize(),
 * DateUtil.getDateSOD(csr.getStDate()), DateUtil.getDateEOD(csr.getEnDate()));
 * 
 * } catch (IOException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } }
 * 
 * 
 * 
 * boolean isSameLineCount = false; String currentProCount = ""; String
 * currentCatCount = ""; String currentActCount = ""; String currentStffCount =
 * ""; String currentStffNameCount = "";
 * 
 * long totolCount = 0; boolean isInitCount = true; String
 * enterd_start_time_count; String enterd_end_time_count; String
 * durationTotCount = null; long countList = 0;
 * 
 * for (ORMReportBasickDetail tkCount : listUserTaskCount) {
 * 
 * if (isInitCount || (currentProCount.equalsIgnoreCase(tkCount.getPro_id()) &&
 * currentCatCount.equalsIgnoreCase(tkCount.getCat_id()) &&
 * currentActCount.equalsIgnoreCase(tkCount.getAct_id()) &&
 * currentStffCount.equalsIgnoreCase(tkCount.getUser_id()) )) {
 * 
 * // ReportBasicProjectDetailDTO basicProject = new
 * ReportBasicProjectDetailDTO();
 * 
 * Date est = null;
 * 
 * est = tkCount.getStartTimeEnterd();
 * 
 * enterd_start_time_count = DateUtil.getDateToDisplayString(est);
 * enterd_end_time_count = "";
 * 
 * Date eet = null; if (tkCount.getEndTimeEnterd() == null) {
 * enterd_end_time_count = ""; } else {
 * 
 * eet = tkCount.getEndTimeEnterd(); enterd_end_time_count =
 * DateUtil.getDateToDisplayString(eet); }
 * 
 * if (eet == null) eet = new Date();
 * 
 * long durationInMillis = eet.getTime() - est.getTime();
 * 
 * totolCount = totolCount + durationInMillis;
 * 
 * 
 * int hours = (int) (totolCount / (1000 * 60 * 60)); int minutes = (int)
 * (totolCount / (1000 * 60)) % 60; int seconds = (int) (totolCount/ 1000) % 60;
 * durationTotCount = String.format("%02d:%02d:%02d", hours, minutes, seconds);
 * 
 * isInitCount = false;
 * 
 * } else {
 * 
 * 
 * count = count +1; System.out.println("count1 "+count);
 * 
 * Date est = null; est = tkCount.getStartTimeEnterd();
 * 
 * enterd_start_time_count = DateUtil.getDateToDisplayString(est);
 * enterd_end_time_count = "";
 * 
 * Date eet = null; if (tkCount.getEndTimeEnterd() == null) {
 * enterd_end_time_count = ""; } else {
 * 
 * eet = tkCount.getEndTimeEnterd(); enterd_end_time_count =
 * DateUtil.getDateToDisplayString(eet); }
 * 
 * if (eet == null) eet = new Date();
 * 
 * long durationInMillis = eet.getTime() - est.getTime();
 * 
 * totolCount = durationInMillis;
 * 
 * }
 * 
 * currentProCount = tkCount.getPro_id(); currentCatCount = tkCount.getCat_id();
 * currentActCount = tkCount.getAct_id(); currentStffCount =
 * tkCount.getUser_id();
 * 
 * }
 * 
 * 
 * //System.out.println("count2 "+count); count = count+1;
 * System.out.println("count 2"+count);
 * 
 * 
 * List<ORMReportBasickDetail> tasks = null; try { tasks =
 * dbm_RreportBasic.getRecords(hqlUserRoles, 0, 1000,
 * DateUtil.getDateSOD(csr.getStDate()), DateUtil.getDateEOD(csr.getEnDate()));
 * } catch (IOException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } Duration fulltotalDuration = Duration.ZERO; for
 * (ORMReportBasickDetail task : tasks) { if (task.getIs_active() ==
 * ORMTask.STATUS_ACTIVATED) activeTaskFound = true; if (task.getIs_active() ==
 * ORMTask.STATUS_ON_HOLD) onHoldTaskFound = true; Duration durationFull = null;
 * if (task.getEndTimeEnterd() == null) { Date now = new Date(); durationFull =
 * Duration.between(task.getStartTimeEnterd().toInstant(), now.toInstant()); }
 * else { durationFull = Duration.between(task.getStartTimeEnterd().toInstant(),
 * task.getEndTimeEnterd().toInstant()); } fulltotalDuration =
 * fulltotalDuration.plus(durationFull); } formatedTotalDuration =
 * DateUtil.formatDuration(fulltotalDuration);
 * 
 * 
 * } ReportBasicProjectDetailDTO.setTotalDurationInMillis(0);
 * 
 * String seprator = ""; StringBuffer sbReportBasicProjectDetail = new
 * StringBuffer("{\"count\":" + count + ", \"arr\":[");
 * 
 * boolean isSameLine = false; String currentPro = ""; String currentCat = "";
 * String currentAct = ""; String currentStff = ""; String currentStffName = "";
 * 
 * long totol = 0; boolean isInit = true; String enterd_start_time; String
 * enterd_end_time; String durationTot = null;
 * 
 * for (ORMReportBasickDetail ur : listUserRole) {
 * 
 * if (isInit || (currentPro.equalsIgnoreCase(ur.getPro_id()) &&
 * currentCat.equalsIgnoreCase(ur.getCat_id()) &&
 * currentAct.equalsIgnoreCase(ur.getAct_id()) &&
 * currentStff.equalsIgnoreCase(ur.getUser_id()) )) {
 * 
 * // ReportBasicProjectDetailDTO basicProject = new
 * ReportBasicProjectDetailDTO();
 * 
 * Date est = null;
 * 
 * est = ur.getStartTimeEnterd();
 * 
 * enterd_start_time = DateUtil.getDateToDisplayString(est); enterd_end_time =
 * "";
 * 
 * Date eet = null; if (ur.getEndTimeEnterd() == null) { enterd_end_time = ""; }
 * else {
 * 
 * eet = ur.getEndTimeEnterd(); enterd_end_time =
 * DateUtil.getDateToDisplayString(eet); }
 * 
 * if (eet == null) eet = new Date();
 * 
 * long durationInMillis = eet.getTime() - est.getTime();
 * 
 * totol = totol + durationInMillis;
 * 
 * 
 * int hours = (int) (totol / (1000 * 60 * 60)); int minutes = (int) (totol /
 * (1000 * 60)) % 60; int seconds = (int) (totol / 1000) % 60; durationTot =
 * String.format("%02d:%02d:%02d", hours, minutes, seconds);
 * 
 * isInit = false; } else {
 * 
 * ReportBasicProjectDetailDTO basicProjectD = new
 * ReportBasicProjectDetailDTO(ur);
 * 
 * basicProjectD.setPro_id(currentPro); basicProjectD.setCat_id(currentCat);
 * basicProjectD.setAct_id(currentAct); basicProjectD.setUser_id(currentStff);
 * basicProjectD.setDuration(durationTot);
 * basicProjectD.setStaffName(ur.getFirstName()+" "+ur.getLastName());
 * sbReportBasicProjectDetail.append(seprator);
 * sbReportBasicProjectDetail.append(basicProjectD.getJson()); seprator = ",";
 * 
 * Date est = null; est = ur.getStartTimeEnterd();
 * 
 * enterd_start_time = DateUtil.getDateToDisplayString(est); enterd_end_time =
 * "";
 * 
 * Date eet = null; if (ur.getEndTimeEnterd() == null) { enterd_end_time = ""; }
 * else {
 * 
 * eet = ur.getEndTimeEnterd(); enterd_end_time =
 * DateUtil.getDateToDisplayString(eet); }
 * 
 * if (eet == null) eet = new Date();
 * 
 * long durationInMillis = eet.getTime() - est.getTime();
 * 
 * totol = durationInMillis;
 * 
 * }
 * 
 * currentPro = ur.getPro_id(); currentCat = ur.getCat_id(); currentAct =
 * ur.getAct_id(); currentStff = ur.getUser_id(); currentStffName =
 * ur.getFirstName()+" "+ur.getLastName();
 * 
 * }
 * 
 * // ReportBasicDetailDTO curr = new ReportBasicDetailDTO(ur);
 * ReportBasicProjectDetailDTO curr = new ReportBasicProjectDetailDTO();
 * curr.setUser_id(currentStff); curr.setPro_id(currentPro);
 * curr.setCat_id(currentCat); curr.setAct_id(currentAct);
 * curr.setStaffName(currentStffName);
 * 
 * String duration; int hours = (int) (totol / (1000 * 60 * 60)); int minutes =
 * (int) (totol / (1000 * 60)) % 60; int seconds = (int) (totol / 1000) % 60;
 * duration = String.format("%02d:%02d:%02d", hours, minutes, seconds);
 * curr.setDuration(duration); sbReportBasicProjectDetail.append(seprator);
 * sbReportBasicProjectDetail.append(curr.getJson()); seprator = ",";
 * 
 * 
 * 
 * sbReportBasicProjectDetail.append("],\r\n \"totalWorkHr\": \"" +
 * formatedTotalDuration + "\"\r\n , " + " }"); return
 * sbReportBasicProjectDetail.toString(); }
 * 
 * }
 */