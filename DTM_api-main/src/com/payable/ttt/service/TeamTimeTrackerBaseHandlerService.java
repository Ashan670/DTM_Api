package com.payable.ttt.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;

import com.payable.ttt.acl.JWebToken;
import com.payable.ttt.dbManager.BaseDbManager;

import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;

import com.payable.ttt.model.ORMUser;
import com.payable.ttt.util.CryptoUtil;
import com.payable.ttt.util.MPOSUtils;
import com.payable.ttt.util.ProxyCommunicationUtil;

public class TeamTimeTrackerBaseHandlerService {

	private Logger log = LogManager.getLogger(TeamTimeTrackerBaseHandlerService.class);

	private Properties sysProperties;
	private int min_api_version_allowed;
	String whiteListedIps[] = null;

	public TeamTimeTrackerBaseHandlerService(Properties pro, int minApi) {
		super();
		sysProperties = pro;
		min_api_version_allowed = minApi;
		String strIPs = sysProperties.getProperty("portal_ip");
		if (strIPs != null) {
			strIPs = strIPs.trim();
			whiteListedIps = strIPs.split(",");
		}
	}

	public String readRawBody(HttpServletRequest request) throws ServletException, IOException, TeamTimeTrackerException {

		String sourceIp = "";
		int keyId = 0;
		boolean isProxyActive = false;

		boolean isEncryptionActive = isEncryptionActive(sysProperties);

		if (isEncryptionActive) {
			keyId = ProxyCommunicationUtil.getTrafficKeyId(request);
			isProxyActive = ProxyCommunicationUtil.isProxyActive(sysProperties);

			if (keyId == 0) {
				log.info("Encryption is enforced in the communication.");
				log.error("(2211222308)Communication is not encryptid");
				throw new TeamTimeTrackerException(EnumException.PROXY_ENCRYPTION_REQUIRED);
			}
		}

		if (isProxyActive) {
			sourceIp = ProxyCommunicationUtil.getSourceIp(request);
		} else {
			sourceIp = request.getRemoteAddr();
		}

		log.info("Source ip : " + sourceIp);

		if (whiteListedIps == null || whiteListedIps.length == 0) {
			log.error("(2211222307)there are no permited ip details in configuration file.(2211181310)");
			System.out.println("TeamTimeTrackerBaseHandlerService(2211181310)");

			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		}

		boolean isValidIp = false;

		for (int i = 0; i < whiteListedIps.length; i++) {
			if (sourceIp.equalsIgnoreCase(whiteListedIps[i].trim())) {
				isValidIp = true;
				break;
			}
		}

		if (!isValidIp) {
			log.error("(2211222306)Attempted to access webportal api from (2211181311) unauthorized ip : " + sourceIp);
			System.out.println("TeamTimeTrackerBaseHandlerService(2211181311)");
			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		}

		StringBuffer jb = new StringBuffer();
		String line = null;

		BufferedReader reader = request.getReader();

		while ((line = reader.readLine()) != null) {
			jb.append(line);
		}

		if (jb.toString().trim().length() == 0) {
			throw new TeamTimeTrackerException(Response.SC_BAD_REQUEST, "SC_BAD_REQUEST");
		}

		String strData = jb.toString().trim();

		String strCallerVersion = request.getHeader("api-client-version");
		int callerVersion = 0;

		if (strCallerVersion != null) {
			try {
				callerVersion = Integer.parseInt(strCallerVersion);
			} catch (Exception e) {
				log.error("(2211222305)Err in converting strCallerVersion : " + e.toString());
			}
		}

		log.info("Received request from api version : " + callerVersion);

		if (min_api_version_allowed > callerVersion) {
			log.error("(2211222304)Received request from old api client. callerVersion : " + callerVersion);
			throw new TeamTimeTrackerException(EnumException.INVALID_API_CLIENT);
		}

		String strContent = "";

		if (keyId > 0) {
			strContent = ProxyCommunicationUtil.decryptData(keyId, strData);
		} else {
			strContent = strData;
		}

		if (strContent.contains("\\u0027") || strContent.contains("'")) {
			log.error("(2211222303)Request contains possible sql injection.");

			if (sysProperties != null) {
				log.error("(2211222302)Portal Request contains possible sql injection. from IP:" + sourceIp + "  on  Request string: " + strContent);
			}

			throw new TeamTimeTrackerException(EnumException.REJECT_REQUEST);
		}

		boolean isValidAscii = MPOSUtils.isValidAscii(strData);
		if (!isValidAscii) {
			log.error("(2211222301)Request contains invalid ascii characters. request string : " + strContent);
			throw new TeamTimeTrackerException(EnumException.REJECT_REQUEST);
		}

		String strHash = request.getHeader("Content-Sig");

		if (strHash != null) {
			if (strHash.length() > 0) {
				String sha512 = CryptoUtil.generateSHA512(strContent);

				if (!sha512.equalsIgnoreCase(strHash)) {
					log.error("(2211222259)Received tampered request from ip : " + sourceIp);
					throw new TeamTimeTrackerException(EnumException.REJECT_REQUEST);
				}
			}
		}

		return strContent;

	}

	public String generateResponse(HttpServletRequest request, String data) throws TeamTimeTrackerException {

		int keyId = ProxyCommunicationUtil.getTrafficKeyId(request);

		if (keyId > 0) {
			return ProxyCommunicationUtil.encryptData(keyId, data);
		} else {
			return data;
		}

	}

	public boolean isEncryptionActive(Properties p) throws TeamTimeTrackerException {

		if (p == null) {
			log.error("(2211222258)Function isEncryptionActive : property is null");
			throw new TeamTimeTrackerException(EnumException.INVALID_SERVER_CONFIG);
		}

		String str = p.getProperty("encryption_active");

		if (str != null && str.trim().length() != 0 && str.trim().equalsIgnoreCase("1")) {
			return true;
		}

		return false;
	}

	public void validateConfig(Properties pro) throws TeamTimeTrackerException {

		String db_user = sysProperties.getProperty("db_user");

		if (db_user == null || db_user.trim().length() <= 0) {
			throw new TeamTimeTrackerException(EnumException.INVALID_SERVER_CONFIG + " [db_user]");
		}

		String db_pwd = sysProperties.getProperty("db_pwd");

		if (db_pwd == null || db_pwd.trim().length() <= 0) {
			throw new TeamTimeTrackerException(EnumException.INVALID_SERVER_CONFIG + " [db_pwd]");
		}

		String db_url = sysProperties.getProperty("db_url");

		if (db_url == null || db_url.trim().length() <= 0) {
			throw new TeamTimeTrackerException(EnumException.INVALID_SERVER_CONFIG + " [db_url]");
		}

		String bank_id = sysProperties.getProperty("bank_id");
		System.out.println("bank id bank id " + bank_id);
		if (bank_id == null || bank_id.trim().length() <= 0) {
			throw new TeamTimeTrackerException(EnumException.INVALID_SERVER_CONFIG + " [bank_id]");
		}

		String encryption_active = sysProperties.getProperty("encryption_active");

		if (encryption_active == null || encryption_active.trim().length() <= 0) {
			throw new TeamTimeTrackerException(EnumException.INVALID_SERVER_CONFIG + " [encryption_active]");
		}

	}

	public ORMUser validateToken(HttpServletRequest request, BaseDbManager<ORMUser> db_user) throws TeamTimeTrackerException {

		String token = request.getHeader("gttoken");

		if (token == null) {
			log.error("token is null. Declining the request (2211181312)");
			System.out.println("TeamTimeTrackerBaseHandlerService(2211181312)");

			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		}

		JWebToken jwt;
		try {
			jwt = new JWebToken(token);

			boolean resToken = jwt.isValid();

			if (!resToken) {
				log.error("Invalid token. Declining the request (2211181314)");
				System.out.println("TeamTimeTrackerBaseHandlerService(2211181314)");

				throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
			}

		} catch (NoSuchAlgorithmException e) {
			log.error("Received NoSuchAlgorithmException. Declining the request (2211181315)");
			System.out.println("TeamTimeTrackerBaseHandlerService(2211181315)");

			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		} catch (JSONException e) {
			log.error("Received JSONException. Declining the request (2211181316)");
			System.out.println("TeamTimeTrackerBaseHandlerService(2211181316)");
			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		} catch (Exception e) {
			log.error("Received Exception. Declining the request (2211181317)");
			System.out.println("TeamTimeTrackerBaseHandlerService(2211181317)");
			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		}

		log.info("Token received with User Id " + jwt.getUserId());

		ORMUser u = null;

		try {
			//u = db_user.get("com.payable.mposlightportal.model.ORMUser", jwt.getUserId());
			System.out.println("=======>"+jwt.getUserId());
			
			u = db_user.get(ORMUser.class , jwt.getUserId());
		} catch (IOException e) {
			log.error("(2211222257)IOException err : " + e.toString());
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		}

		if (u == null) {
			log.error("coudn't find the user record for the received token. Declining the request (2211181318)");
			System.out.println("TeamTimeTrackerBaseHandlerService(2211181318)");
			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		}

		if (jwt.getToken() != u.getAuth()) {
			log.error("token values are not matching. Declining the request (2211181319)");
			System.out.println("TeamTimeTrackerBaseHandlerService(2211181319)");
			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		}

		if (u.getStatus() != ORMUser.STATUS_ACTIVATED) {
			log.error("user is not active. Declining the request (2211181320)");
			System.out.println("TeamTimeTrackerBaseHandlerService(2211181320)");
			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
		}

		/*
		 * added by Amila Giragama 20221123
		 */
//		String bank_id_Received = request.getHeader("bank_id");
//		if (bank_id_Received == null) {
//			log.error("Header Bank id is null");
//			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
//		}
//		String bank_id_Setupped = sysProperties.getProperty("bank_id");
//		if (!(bank_id_Received.equals(bank_id_Setupped))) {
//			log.error("Invalid Bank Id");
//			throw new TeamTimeTrackerException(EnumException.INVALID_AUTHENTICATION);
//		}

		log.info("API Request received with valid token from User Id " + jwt.getUserId());
		return u;
	}

	/* Added by Sandun 20221219 */
//	public List<CorpBankProfileElem> getBankProfiles(String userId,int isSuperUser, BaseDbManager<ORMBankProfile> db_CorpBankProfile) throws MPOSLightException {
//
//		List<ORMBankProfile> ls = null;
//
//		if (userId == null || (userId.isEmpty())) {
//			log.error("User id null or empty");
//			throw new MPOSLightException(EnumException.INVALID_AUTHENTICATION);
//		}
//		String bankId = sysProperties.getProperty("bank_id");
//
//		String bankProfileQuery = "FROM ORMBankProfile tbl WHERE tbl.bankId =" + bankId + " AND tbl.corpUser.id = '" + userId + "' " + " AND tbl.status = " + ORMBankProfile.STATUS_ACTIVE;
//
//		log.info("bankProfileQuery " + bankProfileQuery);
//		ls = db_CorpBankProfile.getRecords(bankProfileQuery);
//
//		if (ls == null || ls.size() == 0) {
//			log.error("No Bank Profile Found ");
//			throw new MPOSLightException(EnumException.INVALID_AUTHENTICATION);
//		}
//
//		List<CorpBankProfileElem> profiles = new ArrayList<CorpBankProfileElem>();
//
//		for (ORMBankProfile p : ls) {
//			CorpBankProfileElem proElem = new CorpBankProfileElem();
//			proElem.setCorporateId(p.getCorporateId());
//			proElem.setBranchId(p.getBranchId() + "");
//			proElem.setQrType(p.getQrType());
//			proElem.setSuperUser(isSuperUser==ORMUser.SUPER_USER?true:false);
//			profiles.add(proElem);
//			//System.out.println( "(202212271144)=====>" + p);
//			
//		}
//		;
//
//		return profiles;
//	}

}
