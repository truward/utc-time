package com.truward.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Represents immutable time holder unit.
 *
 * @author Alexander Shabanov
 */
public final class UtcTime {
  public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

  public static final long MILLIS_IN_DAY = 1000L * 60L * 60L * 24L;

  private long time;

  private UtcTime(long time) {
    this.time = time;
  }

  public static UtcTime valueOf(long time, boolean truncToDay) {
    return new UtcTime(truncToDay ? (time - time % MILLIS_IN_DAY) : time);
  }

  public static UtcTime valueOf(long time) {
    return valueOf(time, false);
  }

  public static UtcTime days(int count) {
    return valueOf(MILLIS_IN_DAY * count);
  }

  public static UtcTime now() {
    return valueOf(System.currentTimeMillis());
  }

  /**
   * @return Time in milliseconds
   */
  public final long getTime() {
    return time;
  }

  /**
   * Checks date precedence.
   *
   * @param another to compare
   * @return true if current date is after another, false otherwise
   */
  public final boolean after(UtcTime another) {
    return time > another.time;
  }


  /**
   * Checks date precedence.
   *
   * @param another to compare
   * @return true if current date is before another, false otherwise
   */
  public final boolean before(UtcTime another) {
    return time < another.time;
  }

  public final UtcTime add(UtcTime another) {
    return UtcTime.valueOf(getTime() + another.getTime());
  }

  public final UtcTime sub(UtcTime another) {
    return UtcTime.valueOf(getTime() - another.getTime());
  }

  /**
   * Checks date precedence.
   *
   * @param begin begin date
   * @param end   end date
   * @return true if current date is between begin and end, false otherwise
   */
  public final boolean between(UtcTime begin, UtcTime end) {
    return (time >= begin.time) && (time <= end.time);
  }

  public final Date asDate() {
    return new Date(getTime());
  }

  public final Calendar asCalendar() {
    Calendar calendar = Calendar.getInstance(UTC_TIME_ZONE);
    calendar.setTimeInMillis(getTime());
    return calendar;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UtcTime)) {
      return false;
    }

    UtcTime other = (UtcTime) o;
    return time == other.time;
  }

  @Override
  public final int hashCode() {
    return (int) (time ^ (time >>> 32));
  }

  @Override
  public final String toString() {
    final DateFormat format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss+00.00");
    format.setTimeZone(UTC_TIME_ZONE);
    return "UtcTime(" + format.format(asDate()) + ")";
  }
}
