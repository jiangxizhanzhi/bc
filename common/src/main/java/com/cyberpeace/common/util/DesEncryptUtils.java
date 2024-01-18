package com.cyberpeace.common.util;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DesEncryptUtils {

	private static final String key = "B13H089Y";

	private static final String ALGORITHM = "DES";

	private static final String MODE = "DES/ECB/PKCS5Padding";

	public static String encrypt(String plaintext) {
		byte[] encryptedBytes = new byte[0];
		try {
			SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
			Cipher cipher = Cipher.getInstance(MODE);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			log.error("Unsupport charset! catch error:", e);
			throw new BusinessException(ErrorCode.UNSUPPORT_CHASET);
		}

		return bytesToHex(encryptedBytes);
	}

	public static String decrypt(String encryptedText) {
		byte[] decryptedBytes = new byte[0];
		try {
			Cipher cipher = Cipher.getInstance(MODE);
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			decryptedBytes = cipher.doFinal(hexToBytes(encryptedText));
		} catch (Exception e) {
			log.error("Unsupport charset! catch error:", e);
			throw new BusinessException(ErrorCode.UNSUPPORT_CHASET);
		}

		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(String.format("%02X", b));
		}
		return result.toString().toLowerCase();
	}

	private static byte[] hexToBytes(String hex) {
		int len = hex.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
					+ Character.digit(hex.charAt(i + 1), 16));
		}
		return data;
	}

}
