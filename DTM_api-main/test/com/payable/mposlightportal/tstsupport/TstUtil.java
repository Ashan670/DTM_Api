package com.payable.mposlightportal.tstsupport;

import java.security.SecureRandom;
import java.util.Random;

public class TstUtil {

	private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	private static final String NUMBER = "0123456789";

	private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
	private static SecureRandom random = new SecureRandom();

	public static String generateRandomString(int length) {
		if (length < 1)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {

			// 0-62 (exclusive), random returns 0-61
			int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
			char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
			sb.append(rndChar);

		}

		return sb.toString();

	}

	public static String generateRandomStringNumber(int length) {
		if (length < 1)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {

			int rndCharAt = random.nextInt(NUMBER.length());
			char rndChar = NUMBER.charAt(rndCharAt);
			sb.append(rndChar);

		}

		return sb.toString();

	}

	public static int generateRandomNumber(int limit) {
		if (limit < 1)
			throw new IllegalArgumentException();

		Random rand = new Random();
		int randInt = rand.nextInt(limit);

		return randInt;

	}

	public static int generateRandomStringNumberLength(int length) {
		if (length < 1)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {

			int rndCharAt = random.nextInt(NUMBER.length());
			char rndChar = NUMBER.charAt(rndCharAt);
			sb.append(rndChar);

		}

		int num = Integer.parseInt(sb.toString());

		return num;

	}
}
