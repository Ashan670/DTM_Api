package com.payable.ttt.exception;

public class TeamTimeTrackerException extends Exception {

	private static final long serialVersionUID = 1L;

	private String strException;
	private int errCode = -1;

	private int iaState = 0;
	private String iaMessage = null;

	public TeamTimeTrackerException(int code, String msg) {
		strException = msg;
		errCode = code;
	}

	public TeamTimeTrackerException(String msg) {
		strException = msg;
		errCode = -2;
	}

	public TeamTimeTrackerException(EnumException enu) {
		strException = enu.getDescription();
		errCode = enu.getCode();
	}

	public void setExpMessage(String str) {
		strException = str;
	}

	public int getErrCode() {
		return errCode;
	}

	public String toString() {

		if (strException != null)
			return strException;

		return super.toString();

	}

	public String getMessage() {
		if (strException != null)
			return strException;

		return super.toString();
	}

	public int getIaState() {
		return iaState;
	}

	public void setIaState(int iaState) {
		this.iaState = iaState;
	}

	public String getIaMessage() {
		return iaMessage;
	}

	public void setIaMessage(String iaMessage) {
		this.iaMessage = iaMessage;
	}

}
