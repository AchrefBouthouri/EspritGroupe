package com.pidev.esprit.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Reminder {

    private static final Logger log = LoggerFactory.getLogger(Reminder.class);

    private static final LocalTime BREAKFAST_TIME = LocalTime.of(8, 0);
    private static final LocalTime LUNCH_TIME = LocalTime.of(12, 30);
    private static final LocalTime DINNER_TIME = LocalTime.of(19, 0);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(fixedDelay = 30000) // runs every 30 seconds
    public void remindNextMeal() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime nextMealTime = LocalTime.from(getNextMealTime(now.toLocalTime()));
        Duration timeUntilNextMeal = Duration.between(now.toLocalTime(), nextMealTime);

        String message = String.format("%d hours %d minutes %d seconds remaining until %s",
                timeUntilNextMeal.toHoursPart(),
                timeUntilNextMeal.toMinutesPart(),
                timeUntilNextMeal.toSecondsPart(),
                getTimeDescription(nextMealTime));
        log.info(message);
    }

    private LocalDateTime getNextMealTime(LocalTime currentTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate currentDate = now.toLocalDate();
        LocalDateTime nextMealDateTime = LocalDateTime.of(currentDate, BREAKFAST_TIME);

        if (currentTime.isAfter(DINNER_TIME)) {
            // Next meal is tomorrow's breakfast
            nextMealDateTime = nextMealDateTime.plusDays(1);
        } else if (currentTime.isAfter(LUNCH_TIME)) {
            nextMealDateTime = LocalDateTime.of(currentDate, DINNER_TIME);
        } else if (currentTime.isAfter(BREAKFAST_TIME)) {
            nextMealDateTime = LocalDateTime.of(currentDate, LUNCH_TIME);
        }

        return nextMealDateTime;
    }

    private String getTimeDescription(LocalTime time) {
        if (time.equals(BREAKFAST_TIME)) {
            return "breakfast";
        } else if (time.equals(LUNCH_TIME)) {
            return "lunch";
        } else {
            return "dinner";
        }
    }
}
