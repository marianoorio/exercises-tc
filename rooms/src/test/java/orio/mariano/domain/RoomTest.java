package orio.mariano.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public class RoomTest {

    @Test
    public void testIsSpotAvailableWhenDataRangeIsInValid() {
        Event event = new Event();
        event.setEventId(1);
        event.setDate(LocalDate.parse("2020-08-31"));
        event.setStartTime(LocalTime.parse("12:30"));
        event.setDuration(Duration.ofMinutes(14L));

        Room room = new Room(1, "testRoom", 15);

        assertFalse(room.isSpotAvailable(event));

        event.setDuration(Duration.ofMinutes(211L));
        assertFalse(room.isSpotAvailable(event));
    }

    @Test
    public void testIsSpotAvailableWhenEndTimeIsInValid() {
        Event event = new Event();
        event.setEventId(1);
        event.setDate(LocalDate.parse("2020-08-31"));
        event.setStartTime(LocalTime.parse("23:30"));
        event.setDuration(Duration.ofMinutes(60L));

        Room room = new Room(1, "testRoom", 15);

        assertFalse(room.isSpotAvailable(event));

        event.setDuration(Duration.ofMinutes(29L));
        assertTrue(room.isSpotAvailable(event));

        event.setDuration(Duration.ofMinutes(30L));
        assertTrue(room.isSpotAvailable(event));

        event.setDuration(Duration.ofMinutes(31L));
        assertFalse(room.isSpotAvailable(event));
    }

    @Test
    public void testIsSpotAvailableWhenDataRangeIsValidAndDateIsNotRegistered() {
        Event event = new Event();
        event.setEventId(1);
        event.setDate(LocalDate.parse("2020-08-31"));
        event.setStartTime(LocalTime.parse("12:30"));
        event.setDuration(Duration.ofMinutes(60L));

        Room room = new Room(1, "testRoom", 15);

        assertTrue(room.isSpotAvailable(event));
    }

    @Test
    public void testIsSpotAvailableWhenDataRangeIsValidAndDateIsRegistered() {
        Event event = new Event();
        event.setEventId(1);
        event.setDate(LocalDate.parse("2020-08-31"));
        event.setStartTime(LocalTime.parse("12:30"));
        event.setDuration(Duration.ofMinutes(60L));

        Event event2 = new Event();
        event2.setEventId(2);
        event2.setDate(LocalDate.parse("2020-08-31"));
        event2.setStartTime(LocalTime.parse("10:00"));
        event2.setDuration(Duration.ofMinutes(30L));

        Event event3 = new Event();
        event3.setEventId(3);
        event3.setDate(LocalDate.parse("2020-08-31"));
        event3.setStartTime(LocalTime.parse("14:00"));
        event3.setDuration(Duration.ofMinutes(120L));

        Room room = new Room(1, "testRoom", 15);
        room.getEvents().put(event.getDate(), Arrays.asList(event, event2, event3));

        Event eventToValidate = new Event();
        eventToValidate.setEventId(4);
        eventToValidate.setDate(LocalDate.parse("2020-08-31"));
        eventToValidate.setStartTime(LocalTime.parse("09:00"));
        eventToValidate.setDuration(Duration.ofMinutes(60L));

        assertTrue(room.isSpotAvailable(eventToValidate));

        // Event between two other events
        eventToValidate.setStartTime(LocalTime.parse("09:00"));
        eventToValidate.setDuration(Duration.ofMinutes(75L));

        assertFalse(room.isSpotAvailable(eventToValidate));

        eventToValidate.setStartTime(LocalTime.parse("10:30"));
        eventToValidate.setDuration(Duration.ofMinutes(90L));

        assertTrue(room.isSpotAvailable(eventToValidate));

        // Event at the end of the day
        eventToValidate.setStartTime(LocalTime.parse("16:15"));
        eventToValidate.setDuration(Duration.ofMinutes(30L));

        assertTrue(room.isSpotAvailable(eventToValidate));

        eventToValidate.setStartTime(LocalTime.parse("16:00"));
        eventToValidate.setDuration(Duration.ofMinutes(30L));

        assertTrue(room.isSpotAvailable(eventToValidate));
    }
}