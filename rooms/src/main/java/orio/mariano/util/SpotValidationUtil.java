package orio.mariano.util;

import orio.mariano.domain.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class SpotValidationUtil {

    private static final Integer MIN_TIME_MINUTES = 15;
    private static final Integer MAX_TIME_MINUTES = 210;

    public static final Predicate<Event> isValidDurationRange = event -> event.getDuration().toMinutes() < MIN_TIME_MINUTES
        || event.getDuration().toMinutes() > MAX_TIME_MINUTES;

    public static final Predicate<Event> finalizationExceedsEndOfDay = event -> {
        LocalDateTime endDate = LocalDateTime.of(event.getDate(), event.getStartTime()).plusMinutes(event.getDuration().toMinutes());
        return  endDate.toLocalDate().isEqual(event.getDate().plusDays(1)) && !endDate.toLocalTime().equals(LocalTime.of(0, 0));
    };

    public static final BiPredicate<Map<LocalDate, List<Event>>, Event> isTheDayFullAvailable = (events, event) -> !events.containsKey(event.getDate())
        || events.get(event.getDate()).isEmpty();

    public static final BiPredicate<Event, Event> isBeforeEvent = (toValidate, current) -> toValidate.getEndTime().isBefore(current.getStartTime())
        || toValidate.getEndTime().equals(current.getStartTime());

    public static final BiPredicate<Event, Event> isAfterEvent = (toValidate, current) -> toValidate.getStartTime().isAfter(current.getEndTime())
        || toValidate.getStartTime().equals(current.getEndTime());


    private SpotValidationUtil() {
        // Nothing to do
    }
}
