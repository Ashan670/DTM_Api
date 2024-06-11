package com.payable.mposlightportal.util;

import com.payable.ttt.util.IPAddressValidator;

import junit.framework.TestCase;

public class IPAddressValidatorTest extends TestCase {

	public void test_original() {

		String ip = "198.162.2.71";
		boolean result = IPAddressValidator.isValidIPAddress(ip);

		if (result == true) {
			assert (true);
		} else {
			assert (false);
		}
	}

	public void test_outOfValue() {

		String ip = "1232.453.647.134";
		boolean result = IPAddressValidator.isValidIPAddress(ip);
		if (result == false) {
			assert (true);
		} else {
			assert (false);
		}
	}

	public void test_lessLength() {

		String ip = "192.168.134";
		boolean result = IPAddressValidator.isValidIPAddress(ip);
		if (result == false) {
			assert (true);
		} else {
			assert (false);
		}
	}

	public void test_longLength() {

		String ip = "192.168.134.123.2";
		boolean result = IPAddressValidator.isValidIPAddress(ip);
		if (result == false) {
			assert (true);
		} else {
			assert (false);
		}
	}

	public void test_nullIp() {

		String ip = null;
		boolean result = IPAddressValidator.isValidIPAddress(ip);
		if (result == false) {
			assert (true);
		} else {
			assert (false);
		}
	}

	public void test_zeroLengthIp() {

		String ip = "";
		boolean result = IPAddressValidator.isValidIPAddress(ip);
		if (result == false) {
			assert (true);
		} else {
			assert (false);
		}
	}

	public void test_outOfValueEnd() {

		String ip = "192.168.2.732";
		boolean result = IPAddressValidator.isValidIPAddress(ip);
		System.out.println("Input : " + ip);
		System.out.println("Result original : " + result);
		if (result == false) {
			assert (true);
		} else {
			assert (false);
		}
	}

}
