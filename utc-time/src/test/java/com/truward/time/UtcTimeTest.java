package com.truward.time;

import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public final class UtcTimeTest {

  @Test
  public void shouldCalculateDays() {
    assertEquals(TimeUnit.DAYS.toMillis(1), UtcTime.days(1).getTime());
    assertEquals(TimeUnit.DAYS.toMillis(2), UtcTime.days(2).getTime());
  }

  @Test
  public void shouldAdd() {
    assertEquals(2, UtcTime.valueOf(1).add(UtcTime.valueOf(1)).getTime());
  }

  @Test
  public void shouldSub() {
    assertEquals(0, UtcTime.valueOf(1).sub(UtcTime.valueOf(1)).getTime());
  }

  @Test
  public void shouldGetNow() {
    assertTrue("Too big difference in currentTime() calculation",
        Math.abs(System.currentTimeMillis() - UtcTime.now().getTime()) < 5000L);
  }

  @Test
  public void shouldGetAfter() {
    assertTrue(UtcTime.days(2).after(UtcTime.days(1)));
    assertFalse(UtcTime.days(1).after(UtcTime.days(2)));
    assertFalse(UtcTime.days(2).after(UtcTime.days(2)));
  }

  @Test
  public void shouldGetBefore() {
    assertTrue(UtcTime.days(1).before(UtcTime.days(2)));
    assertFalse(UtcTime.days(2).before(UtcTime.days(2)));
    assertFalse(UtcTime.days(2).before(UtcTime.days(1)));
  }

  @Test
  public void shouldGetBetween() {
    assertTrue(UtcTime.days(2).between(UtcTime.days(1), UtcTime.days(3)));
    assertTrue(UtcTime.days(1).between(UtcTime.days(1), UtcTime.days(3)));
    assertTrue(UtcTime.days(3).between(UtcTime.days(1), UtcTime.days(3)));

    assertFalse(UtcTime.days(4).between(UtcTime.days(1), UtcTime.days(3)));
    assertFalse(UtcTime.days(0).between(UtcTime.days(1), UtcTime.days(3)));
  }

  @Test
  public void shouldGetCalendar() {
    final Calendar calendar = UtcTime.days(1).asCalendar();
    assertEquals(TimeUnit.DAYS.toMillis(1L), calendar.getTime().getTime());
  }

  @Test
  public void shouldCalculateToString() {
    final UtcTime now = UtcTime.now();
    final String value = now.toString();
    assertNotNull(value);
  }

  @Test
  public void shouldCloneTimeZone() {
    final TimeZone z1 = UtcTime.newUtcTimeZone();
    assertEquals(z1, UtcTime.newUtcTimeZone());
    assertFalse("Clone should make a new copy", z1 == UtcTime.newUtcTimeZone());
  }
}
