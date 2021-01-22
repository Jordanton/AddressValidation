package com.itafin.addressvalidation.util;

public class StringUtils {

	public static boolean isEmpty(String str, boolean trimFirst) {
		// either it's null, simply empty or just contains blanks - in which
		// case trim to see if it's
		// all white spaces.
		return str == null || str.length() == 0 || (trimFirst && str.trim().length() == 0);
	}

	public static String fixNull(String str) {
		return (str == null ? "" : str);
	}
	
	public static String nullToZero(String str) {
      return (str == null ? "0" : str);
  }

	public static boolean isBlank(String str) {
		return isEmpty(str, true);
	}

}
