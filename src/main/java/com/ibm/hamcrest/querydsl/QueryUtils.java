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

import com.mysema.query.QueryMetadata;
import com.mysema.query.collections.CollQuery;
import com.mysema.query.jdo.AbstractJDOQuery;
import com.mysema.query.jpa.JPAQueryBase;
import com.mysema.query.sql.AbstractSQLQuery;
import com.mysema.query.sql.ProjectableSQLQuery;
import com.mysema.query.support.ProjectableQuery;

public class QueryUtils {

	public static <Q extends ProjectableQuery<?>> QueryMetadata getMetadata(Q query) {
		QueryMetadata metadata;
		if (query instanceof CollQuery) {
			metadata = ((CollQuery) query).getMetadata();
		} else if (query instanceof AbstractSQLQuery) {
			metadata = ((AbstractSQLQuery<?>) query).getMetadata();
		} else if (query instanceof ProjectableSQLQuery) {
			metadata = ((ProjectableSQLQuery<?>) query).getMetadata();
		} else if (query instanceof JPAQueryBase) {
			metadata = ((JPAQueryBase<?>) query).getMetadata();
		} else if (query instanceof AbstractJDOQuery) {
			metadata = ((AbstractJDOQuery<?>) query).getMetadata();
		} else {
			throw new RuntimeException("Unsupported query type: " + query.getClass());
		}
		return metadata;
	}

}
