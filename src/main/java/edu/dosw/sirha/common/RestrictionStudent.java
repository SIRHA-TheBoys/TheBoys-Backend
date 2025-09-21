package edu.dosw.sirha.common;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class RestrictionStudent {
    public boolean checkDatesEnableBySystem(LocalDateTime currentDate, LocalDateTime startScheduleDate,
            LocalDateTime endScheduleDate) {
        return currentDate.isAfter(startScheduleDate) && currentDate.isBefore(endScheduleDate) ? true : false;
    }
}
