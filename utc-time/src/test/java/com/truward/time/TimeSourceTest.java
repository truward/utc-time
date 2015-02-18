package com.truward.time;

import com.truward.time.support.HighPrecisionTimeSource;
import com.truward.time.support.StandardTimeSource;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link com.truward.time.TimeSource} implementations.
 *
 * @author Alexander Shabanov
 */
public final class TimeSourceTest {

  @Test
  public void shouldCalcTimeWithInsignificantDifferenceForStandardTimeSource() {
    testInsignificantDifferenceOfSubsequentCurrentTimeCalls(StandardTimeSource.INSTANCE);
  }

  @Test
  public void shouldCalcTimeWithInsignificantDifferenceForHighPrecisionTimeSource() {
    testInsignificantDifferenceOfSubsequentCurrentTimeCalls(HighPrecisionTimeSource.INSTANCE);
  }

  @Test
  public void shouldReturnMilliseconds() {
    final TimeSource ts = StandardTimeSource.INSTANCE;
    final long millis = ts.currentTime();
    assertEquals(millis, TimeUnit.MILLISECONDS.convert(millis, ts.getTimeUnit()));
  }

  @Test
  public void shouldReturnNanoseconds() {
    final TimeSource ts = HighPrecisionTimeSource.INSTANCE;
    final long nanos = ts.currentTime();
    assertEquals(nanos, TimeUnit.NANOSECONDS.convert(nanos, ts.getTimeUnit()));
  }

  //
  // Private
  //

  private void testInsignificantDifferenceOfSubsequentCurrentTimeCalls(TimeSource timeSource) {
    final long time1 = timeSource.currentTime();
    final long time2 = timeSource.currentTime();
    final long delta = timeSource.getTimeUnit().convert(100L, TimeUnit.MILLISECONDS);

    // difference between current time should be less than 100 msec (based on empirical evidence)
    assertTrue("Time retrieved from different time sources should differ insignificantly",
        Math.abs(time1 - time2) < delta);
  }
}
