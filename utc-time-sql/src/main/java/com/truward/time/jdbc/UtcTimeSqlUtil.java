package com.truward.time.jdbc;

import com.truward.time.UtcTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Utilities for jdbc conversions for {@link com.truward.time.UtcTime}.
 *
 * @author Alexander Shabanov
 */
public final class UtcTimeSqlUtil {
  /** Hidden. */
  private UtcTimeSqlUtil() {
  }

  /**
   * Tries to retrieve UTC time from the given {@link java.sql.ResultSet}.
   * Returns defaultTime if no the associated column has null value.
   *
   * @param rs Result set, must be in a state, where a value can be retrieved
   * @param columnName Column name, associated with timestamp value, can hold null value
   * @param defaultTime Default value, that should be used if returned timestamp is null
   * @return UtcTime instance or null if returned timestamp and defaultTime are null
   * @throws SQLException On SQL error
   */
  @Nullable
  public static UtcTime getNullableUtcTime(@Nonnull ResultSet rs,
                                           @Nonnull String columnName,
                                           @Nullable UtcTime defaultTime) throws SQLException {
    final Timestamp timestamp = rs.getTimestamp(columnName, UtcTime.newUtcCalendar());
    if (timestamp == null) {
      return defaultTime;
    }

    return UtcTime.valueOf(timestamp.getTime());
  }

  @Nullable
  public static UtcTime getNullableUtcTime(@Nonnull ResultSet rs, @Nonnull String columnName) throws SQLException {
    return getNullableUtcTime(rs, columnName, null);
  }

  /**
   * Retrieves UTC time from the given {@link java.sql.ResultSet}.
   * Throws {@link java.lang.IllegalStateException} if time is null.
   *
   * @param rs Result set, must be in a state, where a value can be retrieved
   * @param columnName Column name, associated with timestamp value, can hold null value
   * @param defaultTime Default value, that should be used if returned timestamp is null
   * @return UtcTime instance
   * @throws SQLException On SQL error
   * @throws java.lang.IllegalStateException If retrieved time is null and defaultTime is null
   */
  @Nonnull
  public static UtcTime getUtcTime(@Nonnull ResultSet rs,
                                   @Nonnull String columnName,
                                   @Nullable UtcTime defaultTime) throws SQLException {
    final UtcTime result = getNullableUtcTime(rs, columnName, defaultTime);
    if (result != null) {
      return result;
    }

    throw new IllegalStateException("Unable to retrieve time from the given resultSet, columnName=" + columnName);
  }

  @Nonnull
  public static UtcTime getUtcTime(@Nonnull ResultSet rs, @Nonnull String columnName) throws SQLException {
    return getUtcTime(rs, columnName, null);
  }
}