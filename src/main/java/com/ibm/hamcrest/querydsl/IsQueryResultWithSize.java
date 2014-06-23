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

import java.math.BigDecimal;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

import com.mysema.query.support.ProjectableQuery;

public class IsQueryResultWithSize<Q extends ProjectableQuery<?>> extends
	FeatureMatcher<Q, Integer> {

	public IsQueryResultWithSize(Matcher<? super Integer> sizeMatcher) {
		super(sizeMatcher, "query results with size of", "actual size");
	}

	@Override
	protected Integer featureValueOf(Q query) {
		return new BigDecimal(query.count()).intValueExact();
	}

	@Factory
	public static <Q extends ProjectableQuery<?>> Matcher<? super Q> hasResultSize(
		Matcher<? super Integer> sizeMatcher) {
		return new IsQueryResultWithSize<Q>(sizeMatcher);
	}

	@Factory
	public static <Q extends ProjectableQuery<?>> Matcher<? super Q> hasResultSize(int size) {
		return IsQueryResultWithSize.hasResultSize(IsEqual.<Integer> equalTo(size));
	}

}
