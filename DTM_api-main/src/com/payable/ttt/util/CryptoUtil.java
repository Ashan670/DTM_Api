package com.payable.ttt.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CryptoUtil {
	// Commented by Madhushika
	// private static Logger log = Logger.getLogger(CryptoUtil.class);
	private static Logger log = LogManager.getLogger(CryptoUtil.class);

	private static SecureRandom random;

	static {
		random = new SecureRandom();
	}

	public static long generateLongToken() {
		return new BigInteger(64, random).longValue();
	}

	public static String generateMD5(String s) throws NoSuchAlgorithmException {

		MessageDigest digest = null;

		digest = MessageDigest.getInstance("MD5");

		digest.reset();
		byte[] data = digest.digest(s.getBytes());
		return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));

	}

	public static String generateSHA1(String s) {

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			log.error("(2211222256)" + e.toString());
			return "";
		}

		digest.reset();
		byte[] data = digest.digest(s.getBytes());
		return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));

	}

	public static String generateSHA512(String s) {

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			log.error("(2211222255)" + e.toString());
			return "";
		}

		digest.reset();
		byte[] data = digest.digest(s.getBytes());
		return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));

	}

	public static String generateRandomSalt() {

		return generateSHA1(Long.toString(generateLongToken()));
	}

	public static String getCurrentKSN(String data, String ksn) {

		String counter = data.substring(9, 14);
		return ksn + counter;
	}

	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	public static String hexToASCII(String hexValue) {
		StringBuilder output = new StringBuilder("");
		for (int i = 0; i < hexValue.length(); i += 2) {
			String str = hexValue.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}

	public static String asciiToHex(String asciiValue) {
		char[] chars = asciiValue.toCharArray();
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}
		return hex.toString();
	}

	/*
	 * public static int parseHexStr2Int(String hexStr){
	 * 
	 * return 0 ; }
	 */

	public static boolean isSha512(String hexString) {

		if (hexString.length() != 128) {
			return false;
		}

		if (hexString.matches("-?[0-9a-fA-F]+")) {
			return true;
		}

		return false;
	}

}
