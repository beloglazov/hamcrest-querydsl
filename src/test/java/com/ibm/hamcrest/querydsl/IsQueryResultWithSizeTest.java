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

import static com.ibm.hamcrest.querydsl.Matchers.hasResultSize;
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
public class IsQueryResultWithSizeTest {

	Cat c = Fixture.c;
	CollQuery q;

	@Before
	public void setup() {
		q = Fixture.createQueryAllCats();
	}

	@Test
	public void passEqual() {
		assertThat(q, hasResultSize(5));
	}

	@Test
	public void failEqual() {
		try {
			assertThat(q, hasResultSize(4));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with size of <4>\n     but: actual size was <5>"));
			return;
		}
		fail();
	}

	@Test
	public void passEqualWithWhere() {
		assertThat(q.where($(c.getAge()).gt(3)), hasResultSize(3));
	}

	@Test
	public void passNotEqual() {
		assertThat(q, hasResultSize(not(4)));
	}

	@Test
	public void failNotEqual() {
		try {
			assertThat(q, hasResultSize(not(5)));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with size of not <5>\n     but: actual size was <5>"));
			return;
		}
		fail();
	}

	@Test
	public void passGreaterThan() {
		assertThat(q, hasResultSize(greaterThan(4)));
	}

	@Test
	public void failGreaterThan() {
		try {
			assertThat(q, hasResultSize(greaterThan(6)));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with size of a value greater than <6>\n     but: actual size <5> was less than <6>"));
			return;
		}
		fail();
	}

	@Test
	public void passLessThan() {
		assertThat(q, hasResultSize(lessThan(6)));
	}

	@Test
	public void failLessThan() {
		try {
			assertThat(q, hasResultSize(lessThan(4)));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with size of a value less than <4>\n     but: actual size <5> was greater than <4>"));
			return;
		}
		fail();
	}

}
