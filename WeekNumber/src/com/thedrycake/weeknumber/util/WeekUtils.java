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

import java.util.Calendar;

public abstract class WeekUtils {

	/**
	 * Returns the current week number according to ISO 8601 standard.
	 * 
	 * @return the current week number according to ISO 8601 standard.
	 */
	public static int getWeekNumber() {
		return getWeekNumber(Calendar.MONDAY, 4);
	}

	/**
	 * Returns the current week number.
	 * 
	 * @param firstDayOfWeek
	 *            the first day of the week. Use the {@link Calendar} constants
	 *            {@link Calendar#MONDAY}, {@link Calendar#TUESDAY}, etc.
	 * @param minimalDaysInFirstWeek
	 *            the minimal days required in the first week of the year (1-7).
	 * @return the current week number.
	 */
	public static int getWeekNumber(int firstDayOfWeek,
			int minimalDaysInFirstWeek) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(firstDayOfWeek);
		calendar.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
		// Workaround to refresh the calendar.
		calendar.setTimeInMillis(System.currentTimeMillis() + 1L);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		return weekOfYear;
	}

}
