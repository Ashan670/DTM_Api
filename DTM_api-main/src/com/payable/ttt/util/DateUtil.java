package com.payable.ttt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;

//import com.payable.ipg.exception.InvalidDataException;

public class DateUtil {

	/*
	 * public static Date getStringToDate(String dateStr) throws
	 * InvalidDataException {
	 * 
	 * SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	 * 
	 * Date date = null; try { date = dateFormat.parse(dateStr); } catch
	 * (ParseException e) { e.printStackTrace(); throw new
	 * InvalidDataException("Invalid Date Format - " + dateStr); }
	 * 
	 * return date; }
	 */

	public static String getDateToString(Date date) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		String dateStr = dateFormat.format(date);

		return dateStr;
	}

	public static Date getStringToDateForTest(String dateStr) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = new Date();
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return date;
	}

	/*
	 * public static Date getTrnxDateStringToDate(String dateStr) throws
	 * InvalidDataException {
	 * 
	 * Date date = null; try { SimpleDateFormat inputDateFormat = new
	 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	 * inputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); date =
	 * inputDateFormat.parse(dateStr);
	 * 
	 * } catch (Exception ex) { ex.printStackTrace(); } return date; }
	 */
	public static String getTrnxStartDateTimestamp(Date date) {

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date startDate = cal.getTime();

		SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyy-MM-dd");

		String dateStr = dateFormatIn.format(startDate);
		String isoStartDate = dateStr.concat("T18:30:00.000Z");
		return isoStartDate;
	}

	public static String getTrnxEndDateTimestamp(Date date) {

		SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyy-MM-dd");

		String dateStr = dateFormatIn.format(date);
		String isoEndDate = dateStr.concat("T18:29:59.000Z");

		return isoEndDate;
	}

	public static boolean isDateInCurrentMonth(Date reqDate) {
		boolean validMonth = false;
		// Create 2 instances of Calendar
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		// set the given date in one of the instance and current date in the other
		cal1.setTime(reqDate);
		cal2.setTime(getCurrentDateTime());
		System.out.println("reqDate : " + reqDate + " " + "/actualDateTime(Asia/Kolkata) : " + cal2.getTime());
		// now compare the dates using methods on Calendar
		if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
			if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
				validMonth = true;
			}
		}
		System.out.println("Curent month invoice " + validMonth + " / ReqDate : " + reqDate + " / ActualDateTime(Asia/Kolkata) : " + cal2.getTime());
		return validMonth;
	}

	public static Date getCurrentDateTime() {

		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
		long gmtTime = cal.getTime().getTime();

		long timezoneAlteredTime = gmtTime + TimeZone.getTimeZone("Asia/Kolkata").getRawOffset();
		cal.setTimeInMillis(timezoneAlteredTime);

		Date serverDate = new Date();
		Date actualDateTime = cal.getTime();

		System.out.println("serverDate : " + serverDate + " " + "/actualDateTime : " + actualDateTime);

		return actualDateTime;
	}

	
	/**
	 * @auther AmilaKanishka
	 * @param date
	 * @return end of the day value of the given date
	 */
	public static Date getDateEOD(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 900);
		Date eDate = calendar.getTime();
		return eDate;
	}
	public static Date getDateSOD(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    Date sDate = calendar.getTime();
	    return sDate;
	}
	
	public static Date toDate12h(String date) throws ParseException {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.parse(date);
	}

	public static Date toDate(String date) throws ParseException {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(date);
	}

	
//	public static String getDateToDisplayString(Date date) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
//		return dateFormat.format(date);
//	}
	
	public static String getDateToDisplayString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	public static String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		long seconds =  duration.getSeconds() % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
	
}
