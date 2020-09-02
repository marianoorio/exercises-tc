package orio.mariano.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class Event {

    private Integer eventId;
    private LocalDate date;
    private LocalTime startTime;
    private Duration duration;

    public LocalTime getEndTime() {
        return this.startTime.plusMinutes(duration.toMinutes());
    }
}
