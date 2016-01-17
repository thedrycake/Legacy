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

package com.thedrycake.tempincity.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.thedrycake.tempincity.Constants;
import com.thedrycake.tempincity.util.IntegerUtils;
import com.thedrycake.tempincity.util.IoUtils;

public abstract class HttpClient {

	public static final long DEFAULT_CONNECT_TIMEOUT = Constants.TWENTY_SECONDS;
	public static final long DEFAULT_READ_TIMEOUT = Constants.ONE_MINUTE;
	private static final String LOG_TAG = HttpClient.class.getSimpleName();

	public static String get(String url) throws IOException {
		return get(new URL(url));
	}

	public static String get(URL url) throws IOException {
		return get(url, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
	}

	public static String get(URL url, long connectTimeout, long readTimeout)
			throws IOException {
		String response = null;
		HttpURLConnection connection = null;
		InputStream input = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setInstanceFollowRedirects(false);
			connection.setConnectTimeout(IntegerUtils.toInt(connectTimeout));
			connection.setReadTimeout(IntegerUtils.toInt(readTimeout));
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.connect();
			int responseCode = connection.getResponseCode();
			Log.d(LOG_TAG, "The response code is: " + responseCode);
			if (responseCode == 200) {
				input = connection.getInputStream();
				response = IoUtils.toString(input);
			}
		} finally {
			IoUtils.closeQuietly(input);
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}

}
