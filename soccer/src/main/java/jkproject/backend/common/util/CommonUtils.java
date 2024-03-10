package jkproject.backend.common.util;

import java.security.SecureRandom;

public class CommonUtils {

	private static int TEMP_PASSWORD_LENGTH = 10;
	private static final String TEMP_PASSWORD_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final SecureRandom random = new SecureRandom();

	private CommonUtils() {
	}

	public static String createTempPassword() {
		StringBuilder tempPassword = new StringBuilder(TEMP_PASSWORD_LENGTH);

		for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++) {
			int index = random.nextInt(TEMP_PASSWORD_CHARSET.length());
			tempPassword.append(TEMP_PASSWORD_CHARSET.charAt(index));
		}

		return tempPassword.toString();
	}

}
