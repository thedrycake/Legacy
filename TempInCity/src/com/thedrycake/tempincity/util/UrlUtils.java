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

import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

/**
 * Common URL utility methods.
 */
public abstract class UrlUtils {

	/**
	 * Create a new {@link java.net.URI} from a URL String without throwing any
	 * exceptions.
	 * 
	 * @param url
	 *            the URL String.
	 * @return the new URL.
	 */
	public static URL createUrlQuietly(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			Log.w(UrlUtils.class.getSimpleName(), "Error creating URL: " + url,
					e);
		}
		return null;
	}

}
