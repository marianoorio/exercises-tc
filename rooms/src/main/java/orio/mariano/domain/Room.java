package orio.mariano.domain;

import lombok.Getter;
import lombok.Setter;
import orio.mariano.util.SpotValidationUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@Setter
@Getter
public class Room {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private Integer id;
    private String name;
    private Integer capacity;
    private Map<LocalDate, List<Event>> events;

    public Room(Integer id, String name, Integer capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.events = new HashMap<>();
    }

    public Boolean isSpotAvailable(Event event) {

        if (SpotValidationUtil.isValidDurationRange.test(event)) {
            return false;
        }

        if (SpotValidationUtil.finalizationExceedsEndOfDay.test(event)) {
            return false;
        }

        if (SpotValidationUtil.isTheDayFullAvailable.test(events, event)) {
            return true;
        }

        return findSpot(events.get(event.getDate()), event);
    }

    private Boolean findSpot(List<Event> roomEvents, Event event) {

        roomEvents.sort(Comparator.comparing(Event::getStartTime));

        Event firstEvent = roomEvents.get(0);

        if (SpotValidationUtil.isBeforeEvent.test(event, firstEvent)) {
            return true;
        }

        Iterator<Event> iterator = roomEvents.iterator();
        Event previousEvent = iterator.next();

        while (iterator.hasNext()) {

            Event currentEvent = iterator.next();

            if (SpotValidationUtil.isAfterEvent.test(event, previousEvent) && SpotValidationUtil.isBeforeEvent.test(event, currentEvent)) {
                return true;
            }

            previousEvent = currentEvent;
        }

        return SpotValidationUtil.isAfterEvent.test(event, previousEvent);
    }
}
