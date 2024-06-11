package com.payable.mposlightportal.util;

import com.payable.ttt.util.MPOSUtils;

import junit.framework.TestCase;

public class MPOSUtilsTest extends TestCase {

	public void test_tc1_validUUID() {
		String data = "ae5ac47a-8900-11e9-9e56-2fd7a48deb33";

		boolean res = MPOSUtils.isValidUUID(data);
		if (res == true) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	public void test_tc2_invalidCharacters() {
		String data = "ae5ac@3a-8900-11e9-9e56-2fd7a48deb33";

		boolean res = MPOSUtils.isValidUUID(data);
		if (res == true) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	public void test_tc3_invalidCharacters() {
		String data = "ae5ac47a-8900-11e9-9e56-2fd7a48de#$%";

		boolean res = MPOSUtils.isValidUUID(data);
		if (res == true) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	public void test_tc4_longLength() {
		String data = "ae5ac---a-8900-11e9-9e56-2fd7a48debae5ac47a-8900-11e9-9e56-2fd7a48deb33ae5ac47a-8900-11e9-9e56-2fd7a48deb33ae5ac47a-8900-11e9-9e56-2fd7a48deb33";

		boolean res = MPOSUtils.isValidUUID(data);
		if (res == true) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	public void test_tc5_nullInput() {
		String data = null;

		boolean res = MPOSUtils.isValidUUID(data);
		if (res == true) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	public void test_tc6_lengthZero() {
		String data = null;

		boolean res = MPOSUtils.isValidUUID(data);
		if (res == true) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

}
