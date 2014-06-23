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

import static com.ibm.hamcrest.querydsl.Matchers.hasColumnRange;
import static com.mysema.query.alias.Alias.$;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ibm.hamcrest.querydsl.Fixture.Cat;
import com.mysema.query.collections.CollQuery;

@RunWith(JUnit4.class)
public class IsQueryResultWithColumnRangeTest {

	Cat c = Fixture.c;
	CollQuery q;

	@Before
	public void setup() {
		q = Fixture.createQueryAllCats();
	}

	@Test
	public void passCorrectRange() {
		assertThat(q, hasColumnRange($(c.getAge()), 2, 7));
		assertThat(q, hasColumnRange($(c.getAge()), 1, 8));
	}

	@Test
	public void passCorrectRangeWithWhere() {
		assertThat(q.where($(c.getAge()).between(3, 4)), hasColumnRange($(c.getAge()), 3, 4));
	}

	@Test
	public void failWrongLowerBound() {
		try {
			assertThat(q, hasColumnRange($(c.getAge()), 3, 7));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.age' range of [3, 7]\n     but: actual range was [2, 7]"));
			return;
		}
		fail();
	}

	@Test
	public void failWrongUpperBound() {
		try {
			assertThat(q, hasColumnRange($(c.getAge()), 2, 6));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.age' range of [2, 6]\n     but: actual range was [2, 7]"));
			return;
		}
		fail();
	}

}
