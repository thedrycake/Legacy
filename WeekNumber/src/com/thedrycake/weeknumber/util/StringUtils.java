/* 
 * Copyright (C) 2013 The Drycake Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thedrycake.weeknumber.util;

/**
 * Common String utility methods.
 */
public abstract class StringUtils {

	/**
	 * The empty String <code>""</code>.
	 */
	public static final String EMPTY = "";

	/**
	 * The value of <code>System.getProperty("line.separator")</code>.
	 */
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	/**
	 * Check if the {@link java.lang.CharSequence} is {@code null} or empty
	 * (length() == 0).
	 * 
	 * @param s
	 *            the CharSequence to check.
	 * @return returns true if the CharSequence is {@code null} or empty
	 *         (length() == 0).
	 */
	public static boolean isNullOrEmpty(CharSequence s) {
		return s == null || s.length() == 0;
	}

	/**
	 * Remove beginning prefix of a String.
	 * 
	 * @param s
	 *            the String.
	 * @param prefix
	 *            the prefix to remove.
	 * @return a new String without the beginning prefix.
	 */
	public static String removeBeginningPrefix(String s, String prefix) {
		if (s != null && prefix != null && s.startsWith(prefix)) {
			return s.substring(prefix.length());
		} else {
			return s;
		}
	}

}
