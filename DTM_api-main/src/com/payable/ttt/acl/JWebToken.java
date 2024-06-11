package com.payable.ttt.acl;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.UUID;
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;

public class JWebToken {

	private static final int VERSION = 1;

	private static final String SECRET_KEY = "payablelight_corp#hajhy@123#"; // @TODO Add Signature here
	private static final String ISSUER = "payablesl1";
	private static final String JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
	private JSONObject payload = new JSONObject();
	private String signature;
	private String encodedHeader;

	private JWebToken() {
		encodedHeader = encode(new JSONObject(JWT_HEADER));
	}

	public JWebToken(String userId, int role, long token, String ip, long expires) {
		this();
		payload.put("version", VERSION);
		payload.put("user", userId);
		payload.put("role", role);
		payload.put("token", token);
		payload.put("ip", ip);
		payload.put("ts", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
		payload.put("exp", expires);
		payload.put("issuer", ISSUER);
		payload.put("token_id", UUID.randomUUID().toString());
		System.out.println("current ts : " + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
		signature = hmacSha256(encodedHeader + "." + encode(payload), SECRET_KEY);

	}

	public JWebToken(String token) throws NoSuchAlgorithmException {
		this();
		String[] parts = token.split("\\.");
		System.out.println("Parts length : " + parts.length);
		if (parts.length != 3) {
			throw new IllegalArgumentException("Invalid Token format");
		}

		System.out.println("header : " + decode(parts[0]));
		System.out.println("body : " + decode(parts[1]));
		System.out.println("signature : " + decode(parts[2]));

		if (!encodedHeader.equals(parts[0])) {
			System.out.println("Header is not matching. incoming header : " + decode(parts[0]));
			throw new NoSuchAlgorithmException("JWT Header is Incorrect: " + parts[0]);
		}

		payload = new JSONObject(decode(parts[1]));
		if (payload.isEmpty()) {
			throw new JSONException("Payload is Empty: ");
		}

		signature = parts[2];
	}

	public String toString() {
		return encodedHeader + "." + encode(payload) + "." + signature;
	}

	public String getUserId() {
		if (payload == null) {
			return null;
		}

		return payload.getString("user");
	}

	public int getRoleId() {
		if (payload == null) {
			return 0;
		}

		return payload.getInt("role");
	}

	public int getVersion() {
		if (payload == null) {
			return 0;
		}

		return payload.getInt("version");
	}

	public long getToken() {
		if (payload == null) {
			return 0;
		}

		return payload.getLong("token");
	}

	public boolean isValid() {

		long l1 = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

		if (payload.getLong("exp") <= l1) {
			System.out.println("token is expired.");
			System.out.println("exp time in jwt : " + payload.getLong("exp"));
			System.out.println("Current time : " + l1);
			return false;
		}

		if (!signature.equals(hmacSha256(encodedHeader + "." + encode(payload), SECRET_KEY))) {
			System.out.println("Signaure didn't match");
			return false;
		}

		return true;
	}

	private static String encode(JSONObject obj) {
		return encode(obj.toString().getBytes(StandardCharsets.UTF_8));
	}

	private static String encode(byte[] bytes) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	private static String decode(String encodedString) {
		return new String(Base64.getUrlDecoder().decode(encodedString));
	}

	private String hmacSha256(String data, String secret) {
		try {

			// MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = secret.getBytes(StandardCharsets.UTF_8);// digest.digest(secret.getBytes(StandardCharsets.UTF_8));

			Mac sha256Hmac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
			sha256Hmac.init(secretKey);

			byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return encode(signedBytes);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException Err : " + e.toString());
			e.printStackTrace();

		} catch (InvalidKeyException e) {
			System.out.println("NoSuchAlgorithmException Err : " + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String args[]) {
		String str = "eyJraWQiOiIwM1IxbGlzQlFQYU45dnlud1dIZGs3YjRHTksyaWhqbFhPdzJIa2lTRHdnPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI1OTZlY2UxNS1jNTUzLTQzNTYtYTYzYS00OTdmNjU1YmQxNzAiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMi5hbWF6b25hd3MuY29tXC91cy1lYXN0LTJfUWhyZ0Z6N25SIiwicGhvbmVfbnVtYmVyX3ZlcmlmaWVkIjp0cnVlLCJjb2duaXRvOnVzZXJuYW1lIjoidGVzdCIsImF1ZCI6IjM3b2cwczBucWJncWhrcmdnbnBiMG1ua2szIiwiZXZlbnRfaWQiOiI0ZjA4MWM2Yy0xMzY4LTQxYTItOTg3Ny04MTVlMDMxYzk4ZmYiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTU4OTU5ODYyNywicGhvbmVfbnVtYmVyIjoiKzExMjM0NTY3ODkiLCJleHAiOjE1ODk2MDIyMjcsImlhdCI6MTU4OTU5ODYyNywiZW1haWwiOiJ0ZXN0QHRlc3QuY29tIn0.Ow4UNx80ef9OJ9Iv6KBk7mWElLUomwYJvThmWhcCBvnlIm_w2GjxsBjvtKApnr8jQu4lDP9flyxuLiEIkWhcg7_0qh4XQaTI8nrHC42Aw4O3DPHvguylcA7YfFI14UAzHDk5V3qeTrvVEGolRVJ4MB-FJDqb8PAYMyD0d5Am0EgUf99mCP4IFg7Fg5ckszdpfOyCBbVQxeOIEfwwrzHa_DYk77Nm_rrdymAfqvaN5wx9aF0uGhlP7ujBh_fcJcN9G7xMwowdRx2WKDInvXa0RfYJEqsZi-miImWUWaEvkLpF7MZJnSPTvrEnRCsVxIq8wb2GXju4h3fkEydxPa-rmQ";

		System.out.println("Program is running ... ");
		try {
			JWebToken jk = new JWebToken(str);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error : err : " + e.toString());
			e.printStackTrace();
		}

	}

}
