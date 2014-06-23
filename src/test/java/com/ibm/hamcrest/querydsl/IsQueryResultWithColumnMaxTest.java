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

import static com.ibm.hamcrest.querydsl.Matchers.hasColumnMax;
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
public class IsQueryResultWithColumnMaxTest {

	Cat c = Fixture.c;
	CollQuery q;

	@Before
	public void setup() {
		q = Fixture.createQueryAllCats();
	}

	@Test
	public void passEqual() {
		assertThat(q, hasColumnMax($(c.getAge()), 7));
	}

	@Test
	public void failEqual() {
		try {
			assertThat(q, hasColumnMax($(c.getAge()), 5));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.age' max of <5>\n     but: actual max was <7>"));
			return;
		}
		fail();
	}

	@Test
	public void passEqualWithWhere() {
		assertThat(q.where($(c.getAge()).lt(5)), hasColumnMax($(c.getAge()), 4));
	}

	@Test
	public void passNotEqual() {
		assertThat(q, hasColumnMax($(c.getAge()), not(5)));
	}

	@Test
	public void failNotEqual() {
		try {
			assertThat(q, hasColumnMax($(c.getAge()), not(7)));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.age' max of not <7>\n     but: actual max was <7>"));
			return;
		}
		fail();
	}

	@Test
	public void passGreaterThan() {
		assertThat(q, hasColumnMax($(c.getAge()), greaterThan(6)));
	}

	@Test
	public void failGreaterThan() {
		try {
			assertThat(q, hasColumnMax($(c.getAge()), greaterThan(8)));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.age' max of a value greater than <8>\n     but: actual max <7> was less than <8>"));
			return;
		}
		fail();
	}

	@Test
	public void passLessThan() {
		assertThat(q, hasColumnMax($(c.getAge()), lessThan(8)));
	}

	@Test
	public void failLessThan() {
		try {
			assertThat(q, hasColumnMax($(c.getAge()), lessThan(6)));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.age' max of a value less than <6>\n     but: actual max <7> was greater than <6>"));
			return;
		}
		fail();
	}

}
