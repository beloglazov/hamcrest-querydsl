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

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

import com.mysema.query.support.ProjectableQuery;
import com.mysema.query.types.path.NumberPath;

public class IsQueryResultWithColumnMax<T extends Number & Comparable<T>, Q extends ProjectableQuery<?>>
	extends FeatureMatcher<Q, T> {

	private NumberPath<T> column;

	public IsQueryResultWithColumnMax(NumberPath<T> column, Matcher<? super T> sizeMatcher) {
		super(sizeMatcher, "query results with column '" + column + "' max of", "actual max");
		this.column = column;
	}

	@Override
	protected T featureValueOf(Q query) {
		return query.uniqueResult(column.max());
	}

	@Factory
	public static <T extends Number & Comparable<T>, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnMax(
		NumberPath<T> column, Matcher<? super T> maxMatcher) {
		return new IsQueryResultWithColumnMax<>(column, maxMatcher);
	}

	@Factory
	public static <T extends Number & Comparable<T>, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnMax(
		NumberPath<T> column, T max) {
		return IsQueryResultWithColumnMax.hasColumnMax(column, IsEqual.<T> equalTo(max));
	}

}
