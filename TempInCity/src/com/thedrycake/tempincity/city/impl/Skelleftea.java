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

package com.thedrycake.tempincity.city.impl;

import java.io.BufferedReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thedrycake.tempincity.http.HttpClient;
import com.thedrycake.tempincity.util.StringUtils;

public class Skelleftea extends AbstractCity {

	private static final String TEMP_SOURCE = "http://balderskolan.se/vader/international.htm";
	private static final Locale LOCALE = new Locale("sv", "SE");
	private static final TimeZone TIME_ZONE = TimeZone
			.getTimeZone("Europe/Stockholm");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			"HH:mm", LOCALE);

	public String getCountryCode() {
		return "SE";
	}

	public String getName() {
		return "Skellefteå";
	}

	public URL getUserFriendlyWebAddress() throws MalformedURLException {
		return new URL(TEMP_SOURCE);
	}

	@Override
	protected void refreshInternal() throws Exception {
		String response = HttpClient.get(TEMP_SOURCE);
		if (StringUtils.isNullOrEmpty(response)) {
			return;
		}
		BufferedReader bufferedReader = new BufferedReader(new StringReader(
				response));
		String line;
		int row = 0;
		while ((line = bufferedReader.readLine()) != null) {
			row++;
			if (row == 56) {
				Matcher matcher = Pattern.compile("(\\d+:\\d+)").matcher(line);
				if (matcher.find()) {
					String timeString = matcher.group(1).trim();
					Calendar time = Calendar.getInstance(TIME_ZONE, LOCALE);
					Calendar calendar = Calendar.getInstance(TIME_ZONE, LOCALE);
					time.setTime(TIME_FORMAT.parse(timeString));
					calendar.set(Calendar.HOUR_OF_DAY,
							time.get(Calendar.HOUR_OF_DAY));
					calendar.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
					mTemperatureDate = calendar.getTime();
				}
			} else if (row == 69) {
				Matcher matcher = Pattern.compile("([-]?\\d+\\.\\d+)").matcher(line);
				if (matcher.find()) {
					mTemperatureInCelsius = Double.parseDouble(matcher.group(1).trim());
				}
				break;
			}
		}
	}

}
