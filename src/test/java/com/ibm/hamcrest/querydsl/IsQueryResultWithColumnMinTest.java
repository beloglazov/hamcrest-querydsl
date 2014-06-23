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

import static com.ibm.hamcrest.querydsl.Matchers.hasColumnMin;
import static com.mysema.query.alias.Alias.$;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ibm.hamcrest.querydsl.Fixture.Cat;
import com.mysema.query.collections.CollQuery;

@RunWith(JUnit4.class)
public class IsQueryResultWithColumnMinTest {

	Cat c = Fixture.c;
	CollQuery q;

	@Before
	public void setup() {
		q = Fixture.createQueryAllCats();
	}

	@Test
	public void passEqual() {
		assertThat(q, hasColumnMin($(c.getAge()), 2));
	}

	@Test
	public void failEqual() {
		try {
			assertThat(q, hasColumnMin($(c.getAge()), 7));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.age' min of <7>\n     but: actual min was <2>"));
			return;
		}
		fail();
	}

	@Test
	public void passEqualWithWhere() {
		assertThat(q.where($(c.getAge()).gt(3)), hasColumnMin($(c.getAge()), 4));
	}

	@Test
	public void passNotEqual() {
		assertThat(q, hasColumnMin($(c.getAge()), not(7)));
	}

	@Test
	public void failNotEqual() {
		try {
			assertThat(q, hasColumnMin($(c.getAge()), not(2)));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.age' min of not <2>\n     but: actual min was <2>"));
			return;
		}
		fail();
	}

	@Test
	public void passGreaterThan() {
		assertThat(q, hasColumnMin($(c.getAge()), greaterThan(1)));
	}

	@Test
	public void failGreaterThan() {
		try {
			assertThat(q, hasColumnMin($(c.getAge()), greaterThan(6)));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.age' min of a value greater than <6>\n     but: actual min <2> was less than <6>"));
			return;
		}
		fail();
	}

	@Test
	public void passLessThan() {
		assertThat(q, hasColumnMin($(c.getAge()), lessThan(6)));
	}

	@Test
	public void failLessThan() {
		try {
			assertThat(q, hasColumnMin($(c.getAge()), lessThan(1)));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.age' min of a value less than <1>\n     but: actual min <2> was greater than <1>"));
			return;
		}
		fail();
	}

}
