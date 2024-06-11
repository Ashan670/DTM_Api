package com.payable.ttt.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

//import org.apache.catalina.connector.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.payable.ttt.exception.EnumException;
import com.payable.ttt.exception.TeamTimeTrackerException;

public class ProxyCommunicationUtil {

	// private static Logger log = Logger.getLogger(ProxyCommunicationUtil.class);
	private static Logger log = LogManager.getLogger(ProxyCommunicationUtil.class);

	public static boolean isProxyActive(Properties p) throws TeamTimeTrackerException {

		if (p == null) {
			log.error("(2211222244)Function isProxyActive : property is null");
			throw new TeamTimeTrackerException(EnumException.INVALID_SERVER_CONFIG);
		}

		String str = p.getProperty("proxy_active");

		if (str != null && str.trim().length() != 0 && str.trim().equalsIgnoreCase("1")) {
			return true;
		}

		return false;
	}

	public static boolean isEnforceEncryption(Properties p) throws TeamTimeTrackerException {
		if (p == null) {
			log.error("(2211222243)Function isProxyActive : property is null");
			throw new TeamTimeTrackerException(EnumException.INVALID_SERVER_CONFIG);
		}

		String str = p.getProperty("proxy_enforce_encryption");

		if (str != null && str.trim().length() != 0 && str.trim().equalsIgnoreCase("1")) {
			return true;
		}

		return false;
	}

	public static boolean isEnforceSignatureOnRequest(Properties p) throws TeamTimeTrackerException {

		if (p == null) {
			log.error("(2211222242)Function isEnforceSignatureOnRequest : property is null");
			throw new TeamTimeTrackerException(EnumException.INVALID_SERVER_CONFIG);
		}

		String str = p.getProperty("proxy_enforce_signature");

		if (str != null && str.trim().length() != 0 && str.trim().equalsIgnoreCase("1")) {
			return true;
		}

		return false;

	}

	public static int getTrafficKeyId(HttpServletRequest request) throws TeamTimeTrackerException {

		String version = request.getHeader("proxy-km-version");

		if (version == null) {
			return 0;
		}

		int numVersion = 0;

		try {
			numVersion = Integer.parseInt(version);
		} catch (NumberFormatException e) {
			log.info("(2211222241)Error in getTrafficKeyVersion. Received version id : " + version);
			log.error("(2211222240)Err : " + e.toString());
			// throw new MPOSException(Response.SC_BAD_REQUEST, "SC_BAD_REQUEST");
			throw new TeamTimeTrackerException(HttpServletResponse.SC_BAD_REQUEST, "SC_BAD_REQUEST");
		}

		return numVersion;
	}

	public static String getSourceIp(HttpServletRequest request) throws TeamTimeTrackerException {

		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null) {
			log.error("(2211222239)Received invalid ip in X-Forwarded-For header is null");
			throw new TeamTimeTrackerException(EnumException.INVALID_PROXY_SERVER_CONFIG);
		}

		ip = ip.trim();

		if (IPAddressValidator.isValidIPAddress(ip)) {
			return ip;
		}

		log.error("(2211222238)Received invalid ip in X-Forwarded-For header. header value : " + ip);
		throw new TeamTimeTrackerException(EnumException.INVALID_PROXY_SERVER_CONFIG);
	}

	public static String decryptData(int keyVersion, String data) throws TeamTimeTrackerException {

		if (keyVersion == 1) {
			return performDecryptionV1(data);
		}

		return "";
	}

	public static String encryptData(int keyVersion, String data) throws TeamTimeTrackerException {

		if (keyVersion == 1) {
			return performEncryptionV1(data);
		}

		return "";
	}

	private static String performEncryptionV1(String data) throws TeamTimeTrackerException {

		if (data == null) {
			log.error("(2211222237)performDecryptionV1 received data is null");
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}

		try {
			Cipher c = ProxyKeyManagementUtil.getProxyCipher_enc_mode();
			byte[] b = c.doFinal(data.getBytes());
			return DatatypeConverter.printBase64Binary(b);
		} catch (InvalidKeyException e) {
			log.error("(2211222236)InvalidKeyException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		} catch (UnsupportedEncodingException e) {
			log.error("(2211222235)UnsupportedEncodingException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		} catch (NoSuchAlgorithmException e) {
			log.error("(2211222234)NoSuchAlgorithmException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		} catch (NoSuchPaddingException e) {
			log.error("(2211222233)NoSuchPaddingException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		} catch (InvalidKeySpecException e) {
			log.error("(2211222232)NoSuchPaddingException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		} catch (IllegalBlockSizeException e) {
			log.error("(2211222231)IllegalBlockSizeException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		} catch (BadPaddingException e) {
			log.error("(2211222230)BadPaddingException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}
	}

	private static String performDecryptionV1(String data) throws TeamTimeTrackerException {

		if (data == null) {
			log.error("(2211222229)performDecryptionV1 received data is null");
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}

		try {
			Cipher c = ProxyKeyManagementUtil.getProxyCipher_dec_mode();
			byte[] decVal = c.doFinal(DatatypeConverter.parseBase64Binary(data));

			if (decVal != null) {
				return new String(decVal);
			}

			return null;

		} catch (InvalidKeyException e) {
			log.error("(2211222228)InvalidKeyException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		} catch (UnsupportedEncodingException e) {
			log.error("(2211222227)UnsupportedEncodingException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		} catch (NoSuchAlgorithmException e) {
			log.error("(2211222226)NoSuchAlgorithmException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		} catch (NoSuchPaddingException e) {
			log.error("(2211222225)NoSuchPaddingException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		} catch (InvalidKeySpecException e) {
			log.error("(2211222224)NoSuchPaddingException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INTERNAL_ERROR);
		} catch (IllegalBlockSizeException e) {
			log.error("(2211222223)IllegalBlockSizeException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		} catch (BadPaddingException e) {
			log.error("(2211222222)BadPaddingException. err : " + e.toString());
			e.printStackTrace();
			throw new TeamTimeTrackerException(EnumException.INVALID_REQUEST);
		}
	}

}
