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

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * A {@link android.os.ResultReceiver} that can be detached. Useful for when
 * sending callbacks from a {@link android.app.Service} to a
 * {@link android.app.Activity}. The Activity may or may not be active/present
 * at this time.
 */
public class DetachableResultReceiver extends ResultReceiver {

	/**
	 * Generic interface for receiving a callback result from someone.
	 */
	public interface Receiver {

		/**
		 * Called when the result is received.
		 * 
		 * @param resultCode
		 *            arbitrary result code delivered by the sender, as defined
		 *            by the sender.
		 * @param resultData
		 *            any additional data provided by the sender.
		 */
		public void onReceiveResult(int resultCode, Bundle resultData);

	}

	private static final String LOG_TAG = DetachableResultReceiver.class
			.getSimpleName();

	private Receiver mReceiver = null;

	/**
	 * Create a new {@code DetachableResultReceiver} to receive results.
	 */
	public DetachableResultReceiver() {
		this(null);
	}

	/**
	 * Create a new {@code DetachableResultReceiver} to receive results.
	 * 
	 * @param handler
	 *            the Handler. Method @{code onReceiveResult} will be called
	 *            from the thread running handler if given, or from an arbitrary
	 *            thread if null.
	 */
	public DetachableResultReceiver(Handler handler) {
		super(handler);
	}

	/**
	 * Detach the receiver.
	 */
	public void clearReceiver() {
		mReceiver = null;
	}

	/**
	 * Attach an receiver.
	 * 
	 * @param receiver
	 *            the receiver to attach.
	 */
	public void setReceiver(Receiver receiver) {
		mReceiver = receiver;
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if (mReceiver != null) {
			mReceiver.onReceiveResult(resultCode, resultData);
		} else {
			Log.d(LOG_TAG, String.format(Locale.US,
					"Dropping result: {resultCode:%s, resultData:%s}",
					resultCode, resultData != null ? resultData.toString()
							: resultData));
		}
	}

}
