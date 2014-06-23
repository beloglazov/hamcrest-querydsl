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

import static com.mysema.query.alias.Alias.$;
import static com.mysema.query.alias.Alias.alias;
import static com.mysema.query.collections.CollQueryFactory.from;

import java.util.Arrays;
import java.util.List;

import com.mysema.query.collections.CollQuery;

public class Fixture {

	static final Cat c = alias(Cat.class, "cat");

	public static List<Cat> createCats() {
		return Arrays.asList(
			new Cat("Kitty", 5),
			new Cat("Smokey", 2),
			new Cat("Princess", 4),
			new Cat("Oreo", 3),
			new Cat("Lucky", 7));
	}

	public static CollQuery createQueryAllCats() {
		return from($(c), createCats());
	}

	public static class Cat {

		private String name;
		private int age;

		public Cat() {
		}

		public Cat(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

	}

}
