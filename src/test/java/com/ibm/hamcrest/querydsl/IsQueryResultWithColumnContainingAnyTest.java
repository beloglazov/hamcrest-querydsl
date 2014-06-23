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

import static com.ibm.hamcrest.querydsl.Matchers.hasColumnContainingAny;
import static com.mysema.query.alias.Alias.$;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ibm.hamcrest.querydsl.Fixture.Cat;
import com.mysema.query.collections.CollQuery;

@RunWith(JUnit4.class)
public class IsQueryResultWithColumnContainingAnyTest {

	Cat c = Fixture.c;
	CollQuery q;

	@Before
	public void setup() {
		q = Fixture.createQueryAllCats();
	}

	@Test
	public void passContainsIterableOne() {
		assertThat(q, hasColumnContainingAny($(c.getName()), Arrays.asList("Oreo")));
	}

	@Test
	public void failContainsIterableOne() {
		try {
		assertThat(q, hasColumnContainingAny($(c.getName()), Arrays.asList("Schrodinger's")));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.name' containing [Schrodinger's]\n     but: actual did not contain any"));
			return;
		}
		fail();
	}

	@Test
	public void passContainsIterableMany() {
		assertThat(q, hasColumnContainingAny($(c.getName()), Arrays.asList("Oreo", "Kitty", "Smokey")));
		assertThat(q, hasColumnContainingAny($(c.getName()), Arrays.asList("Oreo", "Schrodinger's")));
	}

	@Test
	public void failContainsIterableMany() {
		try {
		assertThat(q, hasColumnContainingAny($(c.getName()), Arrays.asList("Nonexistent", "Schrodinger's")));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.name' containing [Nonexistent, Schrodinger's]\n     but: actual did not contain any"));
			return;
		}
		fail();
	}

	@Test
	public void passContainsIterableManyWithWhere() {
		assertThat(q.where($(c.getAge()).lt(4)), hasColumnContainingAny($(c.getName()), Arrays.asList("Oreo", "Kitty", "Smokey")));
	}

	@Test
	public void failContainsIterableManyWithWhere() {
		try {
		assertThat(q.where($(c.getAge()).lt(4)), hasColumnContainingAny($(c.getName()), Arrays.asList("Kitty", "Lucky")));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.name' containing [Kitty, Lucky]\n     but: actual did not contain any"));
			return;
		}
		fail();
	}

	@Test
	public void passContainsVarArgOne() {
		assertThat(q, hasColumnContainingAny($(c.getName()), "Oreo"));
	}

	@Test
	public void failContainsVarArgOne() {
		try {
		assertThat(q, hasColumnContainingAny($(c.getName()), "Schrodinger's"));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.name' containing [Schrodinger's]\n     but: actual did not contain any"));
			return;
		}
		fail();
	}

	@Test
	public void passContainsVarArgMany() {
		assertThat(q, hasColumnContainingAny($(c.getName()), "Oreo", "Kitty", "Smokey"));
		assertThat(q, hasColumnContainingAny($(c.getName()), "Oreo", "Schrodinger's"));
	}

	@Test
	public void failContainsVarArgMany() {
		try {
		assertThat(q, hasColumnContainingAny($(c.getName()), "Nonexistent", "Schrodinger's"));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.name' containing [Nonexistent, Schrodinger's]\n     but: actual did not contain any"));
			return;
		}
		fail();
	}

	@Test
	public void passContainsVarArgManyWithWhere() {
		assertThat(q.where($(c.getAge()).lt(4)), hasColumnContainingAny($(c.getName()), "Oreo", "Kitty", "Smokey"));
	}

	@Test
	public void failContainsVarArgManyWithWhere() {
		try {
		assertThat(q.where($(c.getAge()).lt(4)), hasColumnContainingAny($(c.getName()), "Kitty", "Lucky"));
		} catch (AssertionError e) {
			assertThat(
				e.getMessage(),
				is("\nExpected: query results with column 'cat.name' containing [Kitty, Lucky]\n     but: actual did not contain any"));
			return;
		}
		fail();
	}

}
