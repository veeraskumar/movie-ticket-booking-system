package com.rvk.mtbs.util;

import java.security.SecureRandom;

public final class GenerateResetCode {

	private GenerateResetCode() {

	}

	private static final SecureRandom secureRandom = new SecureRandom();

	public static int generateResetCode() {
		return 100000 + secureRandom.nextInt(900000);
	}
}
