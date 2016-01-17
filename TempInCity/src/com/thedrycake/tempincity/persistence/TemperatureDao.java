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

package com.thedrycake.tempincity.persistence;

import android.content.ContentValues;
import android.content.Context;

import com.thedrycake.tempincity.provider.AppContract.TemperatureEntity;

public class TemperatureDao extends AbstractDao {

	public TemperatureDao(Context context) {
		super(DatabaseHelper.getInstance(context));
	}

	@Override
	protected String getTable() {
		return DatabaseHelper.Tables.TEMPERATURE;
	}

	@Override
	protected void setDefaultValuesForInsert(ContentValues values) {
		super.setDefaultValuesForInsert(values);
		long now = System.currentTimeMillis();
		putEmptyStringIfNotPresent(values, TemperatureEntity.Columns.CITY_NAME);
		putEmptyStringIfNotPresent(values,
				TemperatureEntity.Columns.COUNTRY_CODE);
		putEmptyStringIfNotPresent(values, TemperatureEntity.Columns.IMPL_TYPE);
		putEmptyStringIfNotPresent(values,
				TemperatureEntity.Columns.IMPL_TYPE_ARGS);
		putDefaultIfNotPresent(values, TemperatureEntity.Columns.TEMP, 0.0d);
		putDefaultIfNotPresent(values, TemperatureEntity.Columns.TEMP_DATE, now);
		putDefaultIfNotPresent(values, TemperatureEntity.Columns.CREATE_DATE,
				now);
		putDefaultIfNotPresent(values,
				TemperatureEntity.Columns.MODIFICATION_DATE, now);
	}

	@Override
	protected void setDefaultValuesForUpdate(ContentValues values) {
		super.setDefaultValuesForUpdate(values);
		long now = System.currentTimeMillis();
		putDefaultIfNotPresent(values,
				TemperatureEntity.Columns.MODIFICATION_DATE, now);
	}

}
