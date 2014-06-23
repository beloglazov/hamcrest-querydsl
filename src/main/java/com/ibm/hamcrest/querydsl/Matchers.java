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
import org.hamcrest.Matcher;
import com.mysema.query.support.ProjectableQuery;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.path.NumberPath;

public class Matchers {

	@Factory
	public static <T, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnContainingAll(
		SimpleExpression<T> column, Iterable<T> items) {
		return IsQueryResultWithColumnContainingAll.hasColumnContainingAll(column, items);
	}

	@SafeVarargs
	@Factory
	public static <T, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnContainingAll(
		SimpleExpression<T> column, T... items) {
		return IsQueryResultWithColumnContainingAll.hasColumnContainingAll(column, items);
	}

	@Factory
	public static <T, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnContainingAny(
		SimpleExpression<T> column, Iterable<T> items) {
		return IsQueryResultWithColumnContainingAny.hasColumnContainingAny(column, items);
	}

	@SafeVarargs
	@Factory
	public static <T, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnContainingAny(
		SimpleExpression<T> column, T... items) {
		return IsQueryResultWithColumnContainingAny.hasColumnContainingAny(column, items);
	}

	@Factory
	public static <T extends Number & Comparable<T>, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnMax(
		NumberPath<T> column, Matcher<? super T> maxMatcher) {
		return IsQueryResultWithColumnMax.hasColumnMax(column, maxMatcher);
	}

	@Factory
	public static <T extends Number & Comparable<T>, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnMax(
		NumberPath<T> column, T max) {
		return IsQueryResultWithColumnMax.hasColumnMax(column, max);
	}

	@Factory
	public static <T extends Number & Comparable<T>, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnMin(
		NumberPath<T> column, Matcher<? super T> minMatcher) {
		return IsQueryResultWithColumnMin.hasColumnMin(column, minMatcher);
	}

	@Factory
	public static <T extends Number & Comparable<T>, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnMin(
		NumberPath<T> column, T min) {
		return IsQueryResultWithColumnMin.hasColumnMin(column, min);
	}

	@Factory
	public static <T extends Number & Comparable<T>, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnRange(
		NumberPath<T> column, T min, T max) {
		return IsQueryResultWithColumnRange.hasColumnRange(column, min, max);
	}

	@Factory
	public static <Q extends ProjectableQuery<?>> Matcher<? super Q> hasResultSize(
		Matcher<? super Integer> sizeMatcher) {
		return IsQueryResultWithSize.hasResultSize(sizeMatcher);
	}

	@Factory
	public static <Q extends ProjectableQuery<?>> Matcher<? super Q> hasResultSize(int size) {
		return IsQueryResultWithSize.hasResultSize(size);
	}

}
