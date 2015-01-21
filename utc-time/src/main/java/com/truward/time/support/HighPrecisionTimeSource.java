package com.truward.time.support;

import com.truward.time.TimeSource;

/**
 * Implementation of {@link com.truward.time.TimeSource} that uses {@link System#nanoTime()} in order
 * to retrieve current time. Note, that the returned value is measured in nanoseconds.
 *
 * @author Alexander Shabanov
 */
public final class HighPrecisionTimeSource implements TimeSource {
  public static final TimeSource INSTANCE = new HighPrecisionTimeSource();

  HighPrecisionTimeSource() {
  }

  @Override
  public long currentTime() {
    return System.nanoTime() / 1000L;
  }
}
