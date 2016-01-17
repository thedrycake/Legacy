package com.thedrycake.tempincity.util;

public abstract class IntegerUtils {

	public static boolean isInteger(String s) {
		if (!StringUtils.isNullOrEmpty(s)) {
			try {
				Integer.parseInt(s);
				return true;
			} catch (NumberFormatException e) {
			}
		}
		return false;
	}

	public static int toInt(long l) {
		if (l > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		} else if (l < Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		} else {
			return Long.valueOf(l).intValue();
		}
	}

	public static Integer toInteger(Long l) {
		if (l == null) {
			return null;
		} else if (l > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		} else if (l < Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		} else {
			return l.intValue();
		}
	}

}
