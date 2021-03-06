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

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.mysema.query.QueryMetadata;
import com.mysema.query.support.ProjectableQuery;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.SimpleExpression;

public class IsQueryResultWithColumnContainingAny<T, Q extends ProjectableQuery<?>> extends
	TypeSafeMatcher<Q> {

	private final SimpleExpression<T> column;
	private final Iterable<T> items;

	public IsQueryResultWithColumnContainingAny(SimpleExpression<T> column, Iterable<T> items) {
		super();
		this.items = items;
		this.column = column;
	}

	@Override
	public boolean matchesSafely(Q query) {
		Predicate baseWhere = QueryUtils.getMetadata(query).clone().getWhere();

		for (T item : items) {
			if (query.where(column.eq(item)).exists()) {
				return true;
			}
			QueryMetadata metadata = QueryUtils.getMetadata(query);
			metadata.clearWhere();
			metadata.addWhere(baseWhere);
		}

		return false;
	}

	@Override
	public void describeMismatchSafely(Q query, Description mismatchDescription) {
		mismatchDescription.appendText("actual did not contain any");
	};

	@Override
	public void describeTo(Description description) {
		description.appendText(
			"query results with column '" + column + "' containing " + items);
	}

	@Factory
	public static <T, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnContainingAny(
		SimpleExpression<T> column, Iterable<T> items) {
		return new IsQueryResultWithColumnContainingAny<>(column, items);
	}

	@SafeVarargs
	@Factory
	public static <T, Q extends ProjectableQuery<?>> Matcher<? super Q> hasColumnContainingAny(
		SimpleExpression<T> column, T... items) {
		return new IsQueryResultWithColumnContainingAny<>(column, Arrays.asList(items));
	}

}
