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
 * Common preconditions utility methods.
 */
public abstract class Preconditions {

	/**
	 * Checks that the boolean expression is true.
	 * 
	 * @param expression
	 *            a boolean expression to check.
	 * @throws IllegalArgumentException
	 *             if expression is false.
	 */
	public static void checkArgument(boolean expression) {
		if (!expression) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Checks that the boolean expression is true.
	 * 
	 * @param expression
	 *            a boolean expression to check.
	 * @param errorMessage
	 *            the exception message to use if the check fails.
	 * @throws IllegalArgumentException
	 *             if expression is false.
	 */
	public static void checkArgument(boolean expression, Object errorMessage) {
		if (!expression) {
			throw new IllegalArgumentException(String.valueOf(errorMessage));
		}
	}

	/**
	 * Checks that the object reference is not null.
	 * 
	 * @param reference
	 *            a object reference to check.
	 * @return if validated, the non-null reference.
	 * @throws NullPointerException
	 *             if object reference is null.
	 */
	public static <T> T checkNotNull(T reference) {
		if (reference == null) {
			throw new NullPointerException();
		}
		return reference;
	}

	/**
	 * Checks that the object reference is not null.
	 * 
	 * @param reference
	 *            a object reference to check.
	 * @param errorMessage
	 *            the exception message to use if the check fails.
	 * @return if validated, the non-null reference.
	 * @throws NullPointerException
	 *             if object reference is null.
	 */
	public static <T> T checkNotNull(T reference, Object errorMessage) {
		if (reference == null) {
			throw new NullPointerException(String.valueOf(errorMessage));
		}
		return reference;
	}

	/**
	 * Checks that the boolean expression is true.
	 * 
	 * @param expression
	 *            a boolean expression to check.
	 * @throws IllegalStateException
	 *             if expression is false.
	 */
	public static void checkState(boolean expression) {
		if (!expression) {
			throw new IllegalStateException();
		}
	}

	/**
	 * Checks that the boolean expression is true.
	 * 
	 * @param expression
	 *            a boolean expression to check.
	 * @param errorMessage
	 *            the exception message to use if the check fails.
	 * @throws IllegalStateException
	 *             if expression is false.
	 */
	public static void checkState(boolean expression, Object errorMessage) {
		if (!expression) {
			throw new IllegalStateException(String.valueOf(errorMessage));
		}
	}

}
