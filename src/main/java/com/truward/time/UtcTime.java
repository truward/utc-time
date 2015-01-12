package com.truward.time;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
  /**
   * NB: this value is mutable, it should not be made public and it should not be modified in this class.
   */
  private static TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

  public static final long MILLIS_IN_DAY = 1000L * 60L * 60L * 24L;

  private long time;

  private UtcTime(long time) {
    this.time = time;
  }

  /**
   * Retrieves UTC time zone. This is not a static member, because the returned value is mutable/\
   *
   * @return New instance of UTC time zone
   */
  @Nonnull
  public static TimeZone newUtcTimeZone() {
    return (TimeZone) UTC_TIME_ZONE.clone();
  }

  /**
   * Returns new instance of {@link java.util.Calendar} class in UTC time zone.
   *
   * @return New calendar instance
   */
  @Nonnull
  public static Calendar newUtcCalendar() {
    final Calendar calendar = Calendar.getInstance(UTC_TIME_ZONE);
    calendar.setTimeZone(newUtcTimeZone());
    return calendar;
  }

  @Nonnull
  public static UtcTime valueOf(long time, boolean truncToDay) {
    return new UtcTime(truncToDay ? (time - time % MILLIS_IN_DAY) : time);
  }

  @Nonnull
  public static UtcTime valueOf(long time) {
    return valueOf(time, false);
  }

  @Nonnull
  public static UtcTime days(int count) {
    return valueOf(MILLIS_IN_DAY * count);
  }

  @Nonnull
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
  public final boolean after(@Nonnull UtcTime another) {
    return time > another.time;
  }


  /**
   * Checks date precedence.
   *
   * @param another to compare
   * @return true if current date is before another, false otherwise
   */
  public final boolean before(@Nonnull UtcTime another) {
    return time < another.time;
  }

  @Nonnull
  public final UtcTime add(@Nonnull UtcTime another) {
    return UtcTime.valueOf(getTime() + another.getTime());
  }

  @Nonnull
  public final UtcTime sub(@Nonnull UtcTime another) {
    return UtcTime.valueOf(getTime() - another.getTime());
  }

  /**
   * Checks date precedence.
   *
   * @param begin begin date
   * @param end   end date
   * @return true if current date is between begin and end, false otherwise
   */
  public final boolean between(@Nonnull UtcTime begin, @Nonnull UtcTime end) {
    return (time >= begin.time) && (time <= end.time);
  }

  @Nonnull
  public final Date asDate() {
    return new Date(getTime());
  }

  @Nonnull
  public final Calendar asCalendar() {
    final Calendar calendar = newUtcCalendar();
    calendar.setTimeInMillis(getTime());
    return calendar;
  }

  @Override
  public final boolean equals(@Nullable Object o) {
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

  @Nonnull
  @Override
  public final String toString() {
    final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    format.setTimeZone(UTC_TIME_ZONE);
    return "UtcTime(" + format.format(asDate()) + ")";
  }
}
