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
 * Defines a generic interface for converting a object from one type to another.
 * 
 * @param <From>
 *            the converting from type.
 * @param <To>
 *            the converting to type.
 */
public interface Converter<From, To> {

	/**
	 * Convert a object from one type to another.
	 * 
	 * @param from
	 *            the object to convert from.
	 * @return the result of the conversion.
	 */
	To convert(From from);

}
