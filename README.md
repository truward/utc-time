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
  <version>1.0.2</version>
</dependency>
```
