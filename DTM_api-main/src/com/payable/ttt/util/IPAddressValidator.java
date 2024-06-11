package com.payable.ttt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPAddressValidator {

	public static boolean isValidIPAddress(String ip) {
		if (ip == null || ip.length() == 0) {
			return false;
		}

		String IPV4_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
				+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

		Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);

		Matcher matcher = IPV4_PATTERN.matcher(ip);

		return matcher.matches();
	}

}
