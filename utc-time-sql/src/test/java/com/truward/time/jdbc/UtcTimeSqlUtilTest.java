package com.truward.time.jdbc;

import com.truward.time.UtcTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for {@link com.truward.time.jdbc.UtcTimeSqlUtil}.
 *
 * @author Alexander Shabanov
 */
public final class UtcTimeSqlUtilTest {
  private Connection connection;

  @Before
  public void init() throws SQLException {
    org.h2.Driver.load();
    connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
    final Statement statement = connection.createStatement();
    try {
      statement.execute("CREATE TABLE test (\n" +
          " id INTEGER PRIMARY KEY,\n" +
          " ts1 TIMESTAMP,\n" +
          " ts2 TIMESTAMP NOT NULL,\n" +
          " dt1 DATE,\n" +
          " dt2 DATE NOT NULL,\n" +
          " ti1 TIME," +
          " ti2 TIME NOT NULL," +
          ")\n");
      statement.execute("INSERT INTO test (id, ts1, ts2, dt1, dt2, ti1, ti2)\n" +
          "VALUES (1, NULL, '2014-09-20 19:58:42.00000', NULL, '2014-09-20', NULL, '19:58:42')");
    } finally {
      statement.close();
    }
  }

  @After
  public void destroy() throws SQLException {
    connection.close();
  }

  @Test
  public void shouldGetTime() throws SQLException {
    final Statement statement = connection.createStatement();
    ResultSet rs = null;
    try {
      rs = statement.executeQuery("SELECT ts1, ts2, dt1, dt2, ti1, ti2 FROM test WHERE id=1");
      rs.next();

      // null getters
      assertNull("ts1 should be null", UtcTimeSqlUtil.getNullableUtcTime(rs, "ts1"));
      assertNull("ts1 should be null with null default", UtcTimeSqlUtil.getNullableUtcTime(rs, "ts1", null));
      assertEquals("ts1 should not be null with non-null default", UtcTime.days(1),
          UtcTimeSqlUtil.getNullableUtcTime(rs, "ts1", UtcTime.days(1)));
      assertEquals("ts1 should not be null with non-null default", UtcTime.days(1),
          UtcTimeSqlUtil.getUtcTime(rs, "ts1", UtcTime.days(1)));

      assertNull("dt1 should be null", UtcTimeSqlUtil.getNullableUtcTime(rs, "dt1"));

      assertNull("ti1 should be null", UtcTimeSqlUtil.getNullableUtcTime(rs, "ti1"));

      // non-null retrieval scenario
      final UtcTime ts2 = UtcTimeSqlUtil.getUtcTime(rs, "ts2");
      assertEquals("UtcTime(2014-09-20T19:58:42+0000)", ts2.toString());

      final UtcTime dt2 = UtcTimeSqlUtil.getUtcTime(rs, "dt2");
      assertEquals("UtcTime(2014-09-20T00:00:00+0000)", dt2.toString());

      final UtcTime ti2 = UtcTimeSqlUtil.getUtcTime(rs, "ti2");
      assertEquals("UtcTime(1970-01-01T19:58:42+0000)", ti2.toString());
    } finally {
      // Release resources
      if (rs != null) {
        rs.close();
      }
      statement.close();
    }
  }
}
