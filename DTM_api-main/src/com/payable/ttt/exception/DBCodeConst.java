package com.payable.ttt.exception;

/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-12-02
 */
public class DBCodeConst {

	/*
	 * while system use MySQL these code are fix if system change to difference DBMS
	 * this code should update related to the newe DBMS
	 */

	/*
	 * TODO use unique to block index in table column CREATE UNIQUE INDEX
	 * inx_CorpUserName ON tbl_corp_user_role(role_name);
	 * 
	 * add index name with prefix of inx_
	 * 
	 */
	public static final String INDEX_PREFIX = "inx_";

	public static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;
	public static final int INTEGRITY_CONSTRAINT_VIOLATION = 1052;
	// public static final String INSERTING_DATA_ERROR_CODE = "23000";
}
