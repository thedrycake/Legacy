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

import java.util.Date;

import android.util.Log;

import com.thedrycake.tempincity.city.City;

abstract class AbstractCity implements City {

	protected Double mTemperatureInCelsius;
	protected Date mTemperatureDate;

	public Double getTemperatureInCelsius() {
		return mTemperatureInCelsius;
	}

	public Date getTemperatureDate() {
		return mTemperatureDate;
	}

	public void refresh() {
		mTemperatureInCelsius = null;
		mTemperatureDate = null;
		try {
			refreshInternal();
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), "Error updating " + getName()
					+ " current temperature.", e);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((getCountryCode() == null) ? 0 : getCountryCode().hashCode());
		result = prime * result
				+ ((getName() == null) ? 0 : getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AbstractCity)) {
			return false;
		}
		AbstractCity other = (AbstractCity) obj;
		if (getCountryCode() == null) {
			if (other.getCountryCode() != null) {
				return false;
			}
		} else if (!getCountryCode().equals(other.getCountryCode())) {
			return false;
		}
		if (getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!getName().equals(other.getName())) {
			return false;
		}
		return true;
	}

	protected abstract void refreshInternal() throws Exception;

}
