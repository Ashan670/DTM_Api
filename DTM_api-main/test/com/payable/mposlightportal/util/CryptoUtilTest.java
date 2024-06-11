package com.payable.mposlightportal.util;

import com.payable.ttt.util.CryptoUtil;

import junit.framework.TestCase;

public class CryptoUtilTest extends TestCase {

	public void test_tc_isSha512_correct_input() {
		assertTrue(CryptoUtil.isSha512(
				"895CE8EA3075F0ED7BAE164C3CC23DF6C4F7DE704161E7643E2CB123FF06CFD5D8A7C67CFE7E38AACE1A22CE83F1F55E738D25F464D79F6546C09C40CEFDF3A2"));
	}

	public void test_tc_isSha512_wrong_input_set1() {
		// replaced one character with ;
		assertTrue(!CryptoUtil.isSha512(
				"895CE8EA3075F0ED7BAE;64C3CC23DF6C4F7DE704161E7643E2CB123FF06CFD5D8A7C67CFE7E38AACE1A22CE83F1F55E738D25F464D79F6546C09C40CEFDF3A2"));

	}

	public void test_tc_isSha512_wrong_input_set2() {
		assertTrue(!CryptoUtil.isSha512("abc123456"));

	}

}
