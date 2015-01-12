utc-time
========

Simple UTC time primitive

This library is like Joda time, but much smaller (more than 100 times smaller than joda-time).

## How to use

See ``test/java/com/truward/time/UtcTimeTest.java``

```java
UtcTime time = UtcTime.now();     // creates immutable UTC time primitive initialized with System.currentTimeMills()
time = time.add(UtcTime.days(1)); // adds one day to the given time
Date date = time.toDate();        // converts time to java.util.Date
Calendar cal = time.toCalendar(); // converts time to java.util.Calendar - UTC time
```

## Maven

In order to include this library to your project add the following to your ``pom.xml``:

```xml
<dependency>
  <groupId>com.truward.time</groupId>
  <artifactId>utc-time</artifactId>
  <version>1.0.4</version>
</dependency>
```

## JDBC mappings

In order to use JDBC mappings, include ``utc-time-sql`` to your project:

```xml
<dependency>
  <groupId>com.truward.time</groupId>
  <artifactId>utc-time-sql</artifactId>
  <version>1.0.4</version>
</dependency>
```

Then, in order to convert UtcTime to and from result set use the following:

```java
// when inserting data
final UtcTime time = getUtcTimeFromSomewhere();
... time.asCalendar() ... // JDBC drivers understand java.util.Calendar instances

// when retrieving data
final ResultSet rs = ourResultSet;
final UtcTime time = UtcTimeSqlUtil.getUtcTime(rs, "timestampColumnName");
// or alternatively use getNullableUtcTime:
final UtcTime another = UtcTimeSqlUtil.getNullableUtcTime(rs, "timestampColumnName", UtcTime.days(1));
```
