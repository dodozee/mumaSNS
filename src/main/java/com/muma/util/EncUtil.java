package com.muma.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class EncUtil {

	public static String generateMD5(String message) {
		return hashString(message, "MD5", "UTF-8");

	}

	public static String generateSHA256(String message) {
		return hashString(message, "SHA-256", "UTF-8");

	}

	public static String generateSHA512(String message) {
		return hashString(message, "SHA-512", "UTF-8");

	}

	private static String hashString(String message, String algorithm, String charest) {

		try {

			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] hashedBytes = digest.digest(message.getBytes(charest));
			return convertByteArrayToHexString(hashedBytes);

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {

			return "";

		}

	}

	private static String convertByteArrayToHexString(byte[] arrayBytes) {

		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		return stringBuffer.toString();

	}
}
