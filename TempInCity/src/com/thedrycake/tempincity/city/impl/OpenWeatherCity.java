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

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.thedrycake.tempincity.http.HttpClient;
import com.thedrycake.tempincity.util.StringUtils;

public class OpenWeatherCity extends AbstractCity {

	private static final Locale LOCALE = Locale.UK;
	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");

	private final int mCityId;
	private final String mName;
	private final String mCountryCode;

	public OpenWeatherCity(String name, int cityId) {
		this(name, cityId, "SE");
	}

	public OpenWeatherCity(String name, int cityId, String countryCode) {
		mName = name;
		mCityId = cityId;
		mCountryCode = countryCode;
	}

	public String getName() {
		return mName;
	}

	public String getCountryCode() {
		return mCountryCode;
	}

	public URL getUserFriendlyWebAddress() throws MalformedURLException {
		return new URL("http://openweathermap.org/city/" + mCityId);
	}

	@Override
	protected void refreshInternal() throws Exception {
		String response = HttpClient
				.get("http://api.openweathermap.org/data/2.5/weather?id="
						+ mCityId
						+ "&units=metric&mode=xml&APPID=979760d918307df42c1dc0413a86325b");

		if (StringUtils.isNullOrEmpty(response)) {
			return;
		}

		XPath xpath = XPathFactory.newInstance().newXPath();
		Node rootNode = (Node) xpath.evaluate("/", new InputSource(
				new StringReader(response)), XPathConstants.NODE);

		mTemperatureInCelsius = (Double) xpath.evaluate(
				"/current/temperature/@value", rootNode, XPathConstants.NUMBER);

		String dateTime = (String) xpath.evaluate("/current/lastupdate/@value",
				rootNode, XPathConstants.STRING);
		if (!StringUtils.isNullOrEmpty(dateTime)) {
			Calendar calendar = Calendar.getInstance(TIME_ZONE, LOCALE);
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss", LOCALE);
			dateTimeFormat.setTimeZone(TIME_ZONE);
			calendar.setTime(dateTimeFormat.parse(dateTime));
			mTemperatureDate = calendar.getTime();
		}
	}

}
