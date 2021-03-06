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
 * The {@code Builder} interface is designed to mark a class as a builder object
 * in the builder design pattern.
 * 
 * @param <T>
 *            the type of object that the builder will construct.
 */
public interface Builder<T> {

	/**
	 * Returns a reference to the object being constructed by the builder.
	 * 
	 * @return the object constructed by the builder.
	 */
	T build();

}
