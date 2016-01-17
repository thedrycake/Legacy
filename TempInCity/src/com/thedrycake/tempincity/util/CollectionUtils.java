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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Common collection utility methods.
 */
public abstract class CollectionUtils {

	/**
	 * Converts the elements in a Collection to another type and returns a List
	 * containing the converted elements.
	 * 
	 * @param fromCollection
	 *            the Collection.
	 * @param converter
	 *            a converter that converts each element from one type to
	 *            another type.
	 * @return a List containing the converted elements.
	 */
	public static <To, From> List<To> convert(Collection<From> fromCollection,
			Converter<From, To> converter) {
		return convert(fromCollection, converter, false);
	}

	/**
	 * Converts the elements in a Collection to another type and returns a List
	 * containing the converted elements.
	 * 
	 * @param fromCollection
	 *            the Collection.
	 * @param converter
	 *            a converter that converts each element from one type to
	 *            another type.
	 * @param excludeNullValues
	 *            if null values should be excluded from the resulting List.
	 * @return a List containing the converted elements.
	 */
	public static <To, From> List<To> convert(Collection<From> fromCollection,
			Converter<From, To> converter, boolean excludeNullValues) {
		List<To> toList = null;
		if (fromCollection != null) {
			toList = new ArrayList<To>(fromCollection.size());
			convert(fromCollection, converter, toList, excludeNullValues);
		}
		return toList;
	}

	/**
	 * Converts the elements in a Iterable to another type, and add the
	 * converted elements to a Collection.
	 * 
	 * @param fromIterable
	 * @param converter
	 *            a converter that converts each element from one type to
	 *            another type.
	 * @param toCollection
	 *            a Collection to add the converted elements to.
	 */
	public static <To, From> void convert(Iterable<From> fromIterable,
			Converter<From, To> converter, Collection<To> toCollection) {
		convert(fromIterable, converter, toCollection, false);
	}

	/**
	 * Converts the elements in a Iterable to another type, and add the
	 * converted elements to a Collection.
	 * 
	 * @param fromIterable
	 * @param converter
	 *            a converter that converts each element from one type to
	 *            another type.
	 * @param toCollection
	 *            a Collection to add the converted elements to.
	 * @param excludeNullValues
	 *            if null values should be excluded from the resulting
	 *            Collection.
	 */
	public static <To, From> void convert(Iterable<From> fromIterable,
			Converter<From, To> converter, Collection<To> toCollection,
			boolean excludeNullValues) {
		if (fromIterable != null && converter != null && toCollection != null) {
			for (From from : fromIterable) {
				To to = converter.convert(from);
				if (to != null || !excludeNullValues) {
					toCollection.add(to);
				}
			}
		}
	}

}
