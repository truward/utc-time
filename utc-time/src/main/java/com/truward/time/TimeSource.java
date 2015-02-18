package com.truward.time;

import java.util.concurrent.TimeUnit;

/**
 * Abstraction over the different ways to retrieve current time for <strong>measuring time lapses</strong>.
 *
 * <p>
 * This abstraction might be used as a replacement for {@link System#currentTimeMillis()} or
 * {@link System#nanoTime()} in an application, that uses DI-alike pattern for managing dependencies.
 * </p>
 * <p>
 * Using this interface would make it trivial to mock time source for particular service and thus would allow
 * the developer to use deterministic ways of retrieving time in tests.
 * </p>
 * <p>
 * Also this abstraction would allow to easily switch from fast but imprecise way of fetching time to slow but
 * precise one (<code>currentTimeMillis</code> vs <code>nanoTime</code>).
 * </p>
 *
 * @author Alexander Shabanov
 */
public interface TimeSource {

  /**
   * Returns current time based on timer abstraction (e.g. it might be unix time in milliseconds).
   * It is up to the abstraction to decide
   *
   * @return Current time.
   */
  long currentTime();

  /**
   * @return Time unit, used by the underlying timer.
   */
  TimeUnit getTimeUnit();
}
