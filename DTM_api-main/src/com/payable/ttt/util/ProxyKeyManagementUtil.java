package com.payable.ttt.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class ProxyKeyManagementUtil {

	private static Cipher cipher_proxy_enc;
	private static Cipher cipher_proxy_dec;

	private static Key generateKey() throws UnsupportedEncodingException {

		String keyString = "175345a789abcdef";
		byte[] keyValue = null;
		keyValue = keyString.getBytes("UTF-8");

		Key key = new SecretKeySpec(keyValue, "AES");

		return key;

	}

	private static void _loadProxyCipher_enc() throws UnsupportedEncodingException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

		cipher_proxy_enc = Cipher.getInstance("AES");
		cipher_proxy_enc.init(Cipher.ENCRYPT_MODE, generateKey());

	}

	private static void _loadProxyCipher_dec() throws UnsupportedEncodingException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

		cipher_proxy_dec = Cipher.getInstance("AES");
		cipher_proxy_dec.init(Cipher.DECRYPT_MODE, generateKey());

	}

	public static Cipher getProxyCipher_enc_mode() throws InvalidKeyException, UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {

		if (cipher_proxy_enc == null) {
			_loadProxyCipher_enc();
		}

		return cipher_proxy_enc;

	}

	public static Cipher getProxyCipher_dec_mode() throws InvalidKeyException, UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {

		if (cipher_proxy_dec == null) {
			_loadProxyCipher_dec();
		}

		return cipher_proxy_dec;

	}

}
