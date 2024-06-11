package com.payable.ttt.exception;

public enum EnumException {

	INTERNAL_ERROR(100, "internal error"), PROCESS_BLOCKED(101, "couldn't complete the request"),
	PIN_ENCRYPTION_NOT_AVAILABLE(102, "Pin encryption is not available"),
	INVALID_SERVER_CONFIG(103, "Invalid server configuration."),
	INVALID_PROXY_SERVER_CONFIG(104, "Invalid proxy server configuration."),
	PROXY_ENCRYPTION_REQUIRED(105, "Request is not encrypted for proxy"),
	// Added By Sandun
	LOGIN_BLOCKED(106, "You have been blocked, please contact the Administrator"),
	// Added by Anjana 2022/03/22
	USER_DEACTIVATED(107, "Your account have been deactivated"),
	NO_CORPUSER_ROLE_PERMISSION(121003, "There are no Corp User role permission listed."),

	LOGIN_USER_SAME_AS_CORPUSER(121014, "Login Users can not edit or change the status of their records."),
	INVALID_USER_AGENT(10000, "user agent is not valid"), INVALID_AUTHENTICATION(10001, "Invalid authentication"),

	USER_NAME_EXIST(10002, "Username already exist"), MOBILE_NO_EXIST(10003, "Mobile number already exist"),
	SIM_ID_EXIST(10004, "Sim Id already exist"), DEVICE_ID_EXIST(10005, "Device Id already exist"),
	CR_ID_EXIST(10006, "Card Reader already exist"), EMAIL_ID_EXIST(10007, "Email Id already exist"),

	INVALID_VERIFICATION_CODE(10008, "Invalid verification code"),
	ACCESS_DENIED(10009, "user is blocked to access the system"), 
	INVALID_REQUEST(10010, "Invalid requset"),
	
	INVALID_BANKRECORD(10011, "Invalid Bank record"), BANK_ACCESS_BLOCKED(10012, "Merchant bank account is denied"),
	INVALID_RECORD(10013, "Invalid record"), UNAUTHORIZED_CARD_READER(10014, "Unauthorized card reader"),
	INVALID_EMV_DATA(10015, "Invalid EMV data"), INVALID_PASSWORD(10016, "Authentication failed. Invalid password."),
	STATUS_WAITING4VERIFICATION(10017, "user is waiting for the verification"),
	STATUS_WAITING4ACTIVATION(10018, "user is waiting for the activation"),
	INVALID_ANDROID_APK(10019, "App is out of date.please update the apk"), REJECT_REQUEST(10020, "Request rejected"),
	INVALID_CARD_READER(10021, "Invalid card reader."),
	INVALID_IOS_APP(10022, "App is out of date.please update the APP"),
	INVALID_EMV_TERMINAL_CONFIG(10023, "Invalid EMV Terminal config"),
	INVALID_CORPORATE_ID(10024, "Invalid corporate id"), 
	INVALID_BRANCH_ID(10025, "Invalid branch id/details"),
	LARGE_PAGE_SIZE(10026, "Received request with large page size"),
	INVALID_SOURCE_AGENT_TYPE(10027, "Invalid source agent type"), INVALID_SOURCE_AGENT(10028, "Invalid source agent"),
	INVALID_MERCHANT_TYPE(10029, "Invalid merchant type"), INVALID_PERMISSION(10030, "Invalid permission"),
	INVALID_PROFILE(10031, "Invalid merchant profile"), INVALID_SDK_CLIENT(10032, "Invalid sdk client"),
	NO_MASTER_PERMISSION(121001, "There are no master permission listed."),
	BANK_PROFILE_EXIST(121002, "Bank Profile already exist!"),
	CORPUSER_EMAIL_EXISTS(121011, "Email already exist in the system."), NO_RECORD_FOUND(30001, "No record found"),
	NO_CORPUSER(121015, "There are no Corp users listed."),
	//NO_CORPUSER_ROLE(121005, "There are no Corp User role listed."),
	NO_PROJECT(121006, "There are no Project listed."),
	NO_TASK_TYPE(121007, "There are Task Type listed."),
	NO_USER_ROLE(121005, "There are no User role listed."),

	INVALID_LICENSE(110001, "Invalid license."), INVALID_API_CLIENT(110002, "Invalid client version."),
	DEACTIVATED_API(110003, "API is deactivated."),
	NO_CORPORATE(40004, "There are no corporate listed."),
	//CORP_ID_NOT_EXISTS(300000, "Corp id  is not exists with user."),
	CORP_BRANCH_NOT_EXISTS(300001, "Corp id / Branch is not exists with user."),
	INVALID_DATE_RANGE(300002,"Date range should be less than or equal to 31 days"),
	
	// Added by Amila Kanishka
	NO_PROFILE(301051212, "There are no profile listed."),
	DUPLICATE_RECORD_FOUNDE(211291402, "Record already found in database"),
	CORPUSER_ROLE_NAME_EXIST(211121002, "Role Name already exist in the system"),
	STATUS_INVALID(112021038, "The status value is incorrect"),
	
	INTEGRITY_CONSTRAINT_VIOLATION(212021638, "Data integrity constraint violated"),
	LOGGEDIN_USER_CANNOT_CHANGE_SAME_ACCOUNT(212051202, "User cannot change own account"),
	USER_ROLE_RELATED_TO_CURRENT_USER(212051347, "User role related to current user"),

	LOGGEDIN_USER_CANNOT_CHANGE_OTHER_ACCOUNT(212051202, "User cannot change other account password"),
	CONFIRM_PASSWORD_NOT_MATCH(212210824, "Confirm password not match"),
	NEW_PASSWORD_EQUAL_OLD_PASSWORD(212210825, "New password equal to old password"),
	PASSWORD_RECENTLY_USED(212211154, "Password Recently used"),
	CURRENT_PASSWORD_INCORRECT(212211217, "Current Password incorrect"),
	LOGIN_USER_CANNOT_RESET_OWN_PASSWORD(212221329, "User cannot reset own password"),
	
	PASSWORD_LENGTH_INVALID(121012, "The user password can not be less than 7 and greater than 100."),
	COROP_USER_ROLE_NOT_EXISTS(121008, "Role is not exists with id."),
	CORPBANK_NOT_EXISTS(121004, "Corp Bank is not exists with id."),
	OLD_CORPUSER_SAME_AS_NEW_CORPUSER(121007, "Old value and New value Cannot be same"),
	NO_CORPUSER_BANK_PROFILES(121003, "There are no Corp User Bank Profiles listed."),
	CORPUSER_NOT_EXISTS(121013, "Corp User is not exists with id."),
	INVALID_EMAIL_LENGTH(121009, "The user's email address cannot be less than one or more than 40."),
	INVALID_EMAIL(121010, "Please enter a valid email address"),
	
	INVALID_DATA(230119025, "Please enter a valid data"),
	INVALID_MERCHANT_ID(121012, "Invalid Merchant id"),
	
	SUPER_USER_CANOT_CREATE_NON_SUPER(302061753, "Suer User cannot create by Non Super User"),
	SUPER_USER_CANOT_EDIT_NON_SUPER(302071459, "Suer User cannot edit by Non Super User"),
	BANK_DETAIL_NOT_FOUND(302061821, "Bank detail not found"),
	SUPER_USER_MUST_BE_IN_SUPER_USER_ROLE(302061843, "Super User must be in Super User Role"),
	
	SUER_USER_ROLE_CANNOT_EDIT(302071454, "Super User Role cannot edit"),
	USER_CANNOT_BE_IN_SUPER_USER_ROLE(302061843, "User Cannot be in Super User Role"),
	
	SELECT_ONE_PERMISSION(302150842, "Please select at least 1 permission."),
	
	START_END_TASK_CANNOT_PROCESS_AT_ONCE(302150842, "Start and End Task Cannot process at once"),
	
	
	ACTIVE_TASK_FIND(302202001, "On Going Task is already exist. Please pause or complete the current on going task"),
	
	NO_ACTIVE_TASK_FIND(302202001, "No Active Task Found"),
	NO_ON_HOLD_TASK_FIND(302202001, "No Paused Task Found"),
	INVALIED_DATE_FORMAT(302202159, "Invalid Date Format"),
	NO_TASK_FOUND_FOR_THE_DAY(302232159, "No task found for the day"),
	
	
	INVALID_TASK_STOP_TIME(302281412, "Invalid Task Stop Date and Time"),
	INVALID_TASK_PAUSE_TIME(302281436, "Invalid Task Pause Date and Time"),
	INVALID_TASK_RESTART_TIME(302281436, "Invalid Task Resume Date and Time"),
	
	INVALID_TASK_DURATION(302281437, "Duration is greater than 24 hours"),

	
	//CONFIG_PROPERTY_MISSING(302062019, "Property missing in config file"),
	//SUPER_USER_CANNOT_CONVERT_IN_EIDT(302071648, "Super user cannot convert at edit"),
	
	// Added By Madhushika
	FAILED_CACHE_CLEAR(121000, "Can not connect with Core war"),
	
	// Added By Anjana
	NO_CATEGORY(302202160, "There are no Category listed."),
	NO_ACTIVITY(302202161, "There are no Activity listed."),
	NO_STAFF(302202162, "There are no staff listed."),
    NO_USER_GROUP(121016, "There are no User groups listed."); // Add this line

	
	
	
	private int m_code;
	private String m_description;

	EnumException(int code, String des) {
		m_code = code;
		m_description = des;
	}

	public int getCode() {
		return m_code;
	}

	public String getDescription() {
		return m_description;
	}

}
