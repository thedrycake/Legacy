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

/**
 * An generic @{link android.os.AsyncTask} result holder.
 * 
 * @param <T>
 *            the type of the result.
 */
public class AsyncTaskResult<T> {

	private final T mResult;
	private final Exception mError;

	/**
	 * Create a new {@code AsyncTaskResult}.
	 */
	public AsyncTaskResult() {
		this(null, null);
	}

	/**
	 * Create a new {@code AsyncTaskResult}.
	 * 
	 * @param result
	 *            the result.
	 */
	public AsyncTaskResult(T result) {
		this(result, null);
	}

	/**
	 * Create a new {@code AsyncTaskResult}.
	 * 
	 * @param error
	 *            the error.
	 */
	public AsyncTaskResult(Exception error) {
		this(null, error);
	}

	/**
	 * Create a new {@code AsyncTaskResult}.
	 * 
	 * @param result
	 *            the result.
	 * @param error
	 *            the error.
	 */
	protected AsyncTaskResult(T result, Exception error) {
		mResult = result;
		mError = error;
	}

	/**
	 * Checks if there is any result.
	 * 
	 * @return if there is any result.
	 */
	public boolean hasResult() {
		return mResult != null;
	}

	/**
	 * Returns the result.
	 * 
	 * @return the result.
	 */
	public T getResult() {
		return mResult;
	}

	/**
	 * Checks if there is any error.
	 * 
	 * @return if there is any error.
	 */
	public boolean hasError() {
		return mError != null;
	}

	/**
	 * Returns the error.
	 * 
	 * @return the error.
	 */
	public Exception getError() {
		return mError;
	}

	/**
	 * Returns the the result from the result holder.
	 * 
	 * @param result
	 *            the result holder.
	 * @return the result or null.
	 */
	public static <T> T getResult(AsyncTaskResult<T> result) {
		return result != null ? result.getResult() : null;
	}

	/**
	 * Returns the the error from the result holder.
	 * 
	 * @param result
	 *            the result holder.
	 * @return the error or null.
	 */
	public static <T> Exception getError(AsyncTaskResult<T> result) {
		return result != null ? result.getError() : null;
	}

}
