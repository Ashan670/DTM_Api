package com.payable.ttt.util;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MPOSUtils {

	private static Logger log = LogManager.getLogger(MPOSUtils.class);

	public static boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}

	public static byte intToByte(int i) {
		if (i > 65536) {
			throw new IllegalArgumentException("Invalid integer value : " + i);
		}
		String hexString = Integer.toHexString(i);
		int firstDigit = toDigit(hexString.charAt(0));
		int secondDigit = toDigit(hexString.charAt(1));
		return (byte) ((firstDigit << 4) + secondDigit);
	}

	private static int toDigit(char hexChar) {
		int digit = Character.digit(hexChar, 16);
		if (digit == -1) {
			throw new IllegalArgumentException("Invalid Hexadecimal Character: " + hexChar);
		}
		return digit;
	}

	public static boolean isValidUUID(String s) {

		if (s == null || s.length() == 0) {
			return false;
		}

		if (s.length() > 100) {
			return false;
		}

		return s.matches("^[a-zA-Z0-9\\-]*$");
	}

	public static int tidGenerator() {

		int min = 100;
		int max = 1000;

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public static Date generateDate(int year, String date, String time) throws Exception {

		if (date == null || date.length() <= 0 || date.length() > 4 || !(date.matches("[0-9]+"))) {
			return null;
		}

		if (time == null || time.length() <= 0 || time.length() > 6 || !(time.matches("[0-9]+"))) {
			return null;
		}

		String dateformat = "" + year + "-" + date.substring(0, 2) + "-" + date.substring(2, 4) + " "
				+ time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateformat);

	}

	// commented for Jey's request 2021-04-09
	/*
	 * public static boolean isValidAscii(String s){
	 * 
	 * log.info("checking isValidAscii in "+s);
	 * log.info("value "+Charset.forName("US-ASCII").newEncoder().canEncode(s));
	 * 
	 * return Charset.forName("US-ASCII").newEncoder().canEncode(s); }
	 */

	public static boolean isValidAscii(String s) {

		System.out.println("income string : " + s);
		log.info("checking isValidAscii in " + s);
		log.info("value " + Charset.forName("US-ASCII").newEncoder().canEncode(s));

		boolean res = Charset.forName("US-ASCII").newEncoder().canEncode(s);

		if (!res) {
			log.info("from validation 1");
			return false;
		}

		if (s.contains("\\u")) {
			log.info("from validation 2");
			return false;
		}

		return true;
	}

}
