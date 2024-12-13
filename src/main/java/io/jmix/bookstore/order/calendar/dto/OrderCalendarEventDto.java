package io.jmix.bookstore.order.calendar.dto;

import java.time.LocalDate;

public class OrderCalendarEventDto {
    private Object eventId;
    private Long orderNumber;
    private LocalDate startDateTime;
    private LocalDate endDateTime;
    private String title;
    private String description;


    public Object getEventId() {
        return eventId;
    }

    public void setEventId(Object eventId) {
        this.eventId = eventId;
    }

    public LocalDate getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDate startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDate getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDate endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }
}
