# hamcrest-querydsl

This library provides [Hamcrest](https://github.com/hamcrest/JavaHamcrest/)
matchers for [Querydsl](https://github.com/querydsl/querydsl/). In particular,
the following matchers for query results are included:

- `hasResultSize`
- `hasColumnRange`
- `hasColumnMax`
- `hasColumnMin`
- `hasColumnContainingAll`
- `hasColumnContainingAny`


## Installation

Just add the following dependency to you `pom.xml`:

```xml
<dependency>
    <groupId>com.ibm</groupId>
    <artifactId>hamcrest-querydsl</artifactId>
    <version>1.0</version>
    <scope>test</scope>
</dependency>
```


## Usage

To use the matchers, add the following static import to your test class:

```Java
import static com.ibm.hamcrest.querydsl.Matchers.*;
```


## Examples

The examples are given using the collections back-end (querydsl-collections),
but the matchers work seamlessly for the SQL, JPA, and JDO back-ends.

Assume that you have a collection of cats with names and ages:

```Java
List<Cat> cats = Arrays.asList(
        new Cat("Kitty", 5),
        new Cat("Smokey", 2),
        new Cat("Princess", 4),
        new Cat("Oreo", 3),
        new Cat("Lucky", 7));
```

Then, a query to select all the cats is:

```Java
Cat c = alias(Cat.class), "cat"); // required for the collections back-end
CollQuery q = from($(c), cats);
```


### hasResultSize

Matches the size of the query results:

```Java
assertThat(q, hasResultSize(5));
```

It can be combined with other matchers:

```Java
assertThat(q, hasResultSize(greaterThan(1)));
assertThat(q, hasResultSize(lessThan(10)));
assertThat(q, hasResultSize(not(0)));
```

The query can have arbitrary conditions:

```Java
assertThat(q.where($(c.getAge()).gt(3)), hasResultSize(3));
```

In case of an assertion error (e.g., `assertThat(q, hasResultSize(4));`), a meaningful
message is printed:

```
Expected: query results with size of <4>
but: actual size was <5>
```


### hasColumnRange

Matches the range of a column in the query results:

```Java
assertThat(q, hasColumnRange($(c.getAge()), 2, 7));
```


### hasColumnMax

Matches the maximum value of a column in the query results:

```Java
assertThat(q, hasColumnMax($(c.getAge()), 7));
assertThat(q, hasColumnMax($(c.getAge()), greaterThan(6)));
assertThat(q.where($(c.getAge()).lt(5)), hasColumnMax($(c.getAge()), 4));
```


### hasColumnMin

Matches the minimum value of a column in the query results:

```Java
assertThat(q, hasColumnMin($(c.getAge()), 2));
assertThat(q, hasColumnMin($(c.getAge()), greaterThan(1)));
assertThat(q.where($(c.getAge()).gt(3)), hasColumnMin($(c.getAge()), 4));
```


### hasColumnContainingAll

Checks whether a column in the query results contains all of the listed values:

```Java
assertThat(q, hasColumnContainingAll($(c.getName()), "Oreo"));
assertThat(q, hasColumnContainingAll($(c.getName()), "Oreo", "Kitty", "Smokey"));
assertThat(q.where($(c.getAge()).lt(6)), hasColumnContainingAll($(c.getName()), "Oreo", "Kitty", "Smokey"));
```

In addition to varargs, an Iterable of the required values can be passed:

```Java
assertThat(q, hasColumnContainingAll($(c.getName()), Arrays.asList("Oreo")));
assertThat(q, hasColumnContainingAll($(c.getName()), Arrays.asList("Oreo", "Kitty", "Smokey")));
assertThat(q.where($(c.getAge()).lt(6)), hasColumnContainingAll($(c.getName()), Arrays.asList("Oreo", "Kitty", "Smokey")));
```


### hasColumnContainingAny


Checks whether a column in the query results contains any of the listed values:

```Java
assertThat(q, hasColumnContainingAny($(c.getName()), "Oreo"));
assertThat(q, hasColumnContainingAny($(c.getName()), "Oreo", "Kitty", "Smokey"));
assertThat(q, hasColumnContainingAny($(c.getName()), "Oreo", "Schrodinger's"));
assertThat(q.where($(c.getAge()).lt(4)), hasColumnContainingAny($(c.getName()), "Oreo", "Kitty", "Smokey"));
```

Similarly to hasColumnContainingAll, this matcher supports Iterables:

```Java
assertThat(q, hasColumnContainingAny($(c.getName()), Arrays.asList("Oreo")));
assertThat(q, hasColumnContainingAny($(c.getName()), Arrays.asList("Oreo", "Kitty", "Smokey")));
assertThat(q, hasColumnContainingAny($(c.getName()), Arrays.asList("Oreo", "Schrodinger's")));
assertThat(q.where($(c.getAge()).lt(4)), hasColumnContainingAny($(c.getName()), Arrays.asList("Oreo", "Kitty", "Smokey")));
```

Suggestions for new matchers are welcome!


## Author

The project has been developed by [Anton Beloglazov](http://beloglazov.info/).

For more open-source projects from IBM, head over to (http://ibm.github.io).


## Copyright and license

© Copyright IBM Corporation 2014. Distributed under the [Apache 2.0 license](LICENSE).
