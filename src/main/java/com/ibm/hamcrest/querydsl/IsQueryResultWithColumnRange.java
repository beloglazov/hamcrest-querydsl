/**
 * Copyright IBM Corporation 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package com.ibm.hamcrest.querydsl;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.mysema.query.support.ProjectableQuery;
import com.mysema.query.types.path.NumberPath;

public class IsQueryResultWithColumnRange<T extends Number & Comparable<T>, Q extends ProjectableQuery<?>>
	extends TypeSafeMatcher<Q> {

	private NumberPath<T> column;
	private T min;
	private T max;
	private T actualMin;
	private T actualMax;

	public IsQueryResultWithColumnRange(NumberPath<T> column, T min, T max) {
		super();
		this.column = column;
		this.min = min;
		this.max = max;
	}

	@Override
	protected boolean matchesSafely(Q query) {
		actualMin = query.uniqueResult(column.min());
		actualMax = query.uniqueResult(column.max());
		return min.compareTo(actualMin) <= 0 && max.compareTo(actualMax) >= 0;
	}

	@Override
	public void describeMismatchSafely(Q query, Description mismatchDescription) {
		mismatchDescription.appendText("actual range was [" + actualMin + ", " + actualMax + "]");
	};

	@Override
	public void describeTo(Description description) {
		description.appendText(
			"query results with column '" + column + "' range of [" + min + ", " + max + "]");
	}

	@Factory
	public static <T extends Number & Comparable<T>, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnRange(
		NumberPath<T> column, T min, T max) {
		return new IsQueryResultWithColumnRange<>(column, min, max);
	}

}
