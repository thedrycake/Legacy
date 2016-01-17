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

package com.thedrycake.tempincity;

import android.content.Context;
import android.content.Intent;

public abstract class Broadcasts {

	public static void sendCurrentCityChanged(Context context) {
		if (context != null) {
			context.sendBroadcast(new Intent(
					Constants.ACTION_CURRENT_CITY_CHANGED));
		}
	}

	public static void sendTempChanged(Context context) {
		if (context != null) {
			context.sendBroadcast(new Intent(Constants.ACTION_TEMP_CHANGED));
		}
	}

	public static void sendWidgetSettingsChanged(Context context) {
		if (context != null) {
			context.sendBroadcast(new Intent(
					Constants.ACTION_WIDGET_SETTINGS_CHANGED));
		}
	}

}
