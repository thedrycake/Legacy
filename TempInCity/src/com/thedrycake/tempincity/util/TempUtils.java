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

package com.thedrycake.tempincity.util;

import java.util.Locale;

import com.thedrycake.tempincity.provider.AppContract.TemperatureEntity.TemperatureUnit;

public abstract class TempUtils {

	public static String convertAndFormat(double tempInCelsius,
			TemperatureUnit tempUnit) {
		double temp;
		switch (tempUnit) {
		case FAHRENHEIT:
			temp = celsiusToFahrenheit(tempInCelsius);
			break;
		case KELVIN:
			temp = celsiusToKelvin(tempInCelsius);
			break;
		default:
			temp = tempInCelsius;
			break;
		}
		return format(temp, tempUnit);
	}

	public static String format(double temp, TemperatureUnit tempUnit) {
		return format(temp, tempUnit.getName());
	}

	public static String format(double temp, String tempUnit) {
		Locale defaultLocale = Locale.getDefault();
		return String.format(defaultLocale, "%.1f °%s", temp, tempUnit);
	}

	public static double celsiusToFahrenheit(double celsius) {
		return ((9.0 / 5.0) * celsius) + 32.0;
	}

	public static double fahrenheitToCelsius(double fahrenheit) {
		return (5.0 / 9.0) * (fahrenheit - 32.0);
	}

	public static double celsiusToKelvin(double celsius) {
		return celsius + 273.15;
	}

	public static double kelvinToCelsius(double kelvin) {
		return kelvin - 273.15;
	}

}
