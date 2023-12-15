package com.cyberpeace.auth.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES工具类
 */
@Slf4j public class AESUtil {

	/**
	 * 获取AES编码规则
	 *
	 * @return
	 */
	public static String genEncodeRule() {
		StringBuilder chars = new StringBuilder(
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			int num = Integer.parseInt(
					String.valueOf(Math.round(Math.floor(Math.random() * chars.length()))));
			result.append(chars.charAt(num));
		}
		return result.toString();
	}

	/**
	 * AES加密
	 * @param content
	 * @return
	 */
	public static String encrypt(String content) throws Exception {
		byte[] bytes = "CYBER.peace#2389".getBytes("UTF-8");
		SecretKeySpec keySpec = new SecretKeySpec(bytes, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		byte[] bytes1 = cipher.doFinal(content.getBytes("UTF-8"));
		String result = Base64.getEncoder().encodeToString(bytes1);
		return result;
	}

	public static String decrypt(String content) throws Exception {
		byte[] decode = Base64.getDecoder().decode(content);
		SecretKeySpec keySpec = new SecretKeySpec("CYBER.peace#2389".getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] bytes = cipher.doFinal(decode);
		return new String(bytes, "UTF-8");
	}
}
