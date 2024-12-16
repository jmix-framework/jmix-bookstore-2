package io.jmix.bookstore.view.ordercalendar;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.order.calendar.dto.OrderCalendarEventDto;
import io.jmix.bookstore.order.calendar.service.OrderCalendarEventService;
import io.jmix.bookstore.util.RandomUtils;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.component.data.CalendarEvent;
import io.jmix.fullcalendarflowui.component.data.ListCalendarDataProvider;
import io.jmix.fullcalendarflowui.component.data.SimpleCalendarEvent;
import io.jmix.fullcalendarflowui.component.event.DatesSetEvent;
import io.jmix.fullcalendarflowui.component.model.Display;
import io.jmix.fullcalendarflowui.component.model.option.FullCalendarOptions;
import io.jmix.fullcalendarflowui.kit.component.JmixFullCalendar;
import io.jmix.fullcalendarflowui.kit.component.model.CalendarDisplayModes;
import io.jmix.fullcalendarflowui.kit.component.model.option.CalendarOption;
import io.jmix.fullcalendarflowui.kit.component.model.option.JmixFullCalendarOptions;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Route(value = "orderCalendar", layout = MainView.class)
@ViewController("bookstore_OrderCalendarView.view")
@ViewDescriptor("order-calendar-view.xml")
@DialogMode(width = "50em", height = "37.5em")
public class OrderCalendarView extends StandardView {
    @ViewComponent
    private FullCalendar orderCalendar;
    @ViewComponent
    private H3 calendarTitle;
    @ViewComponent
    private JmixComboBox<CalendarDisplayModes> orderCalendarProjectionComboBox;
    @Autowired
    private UiComponents uiComponents;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private OrderCalendarEventService orderCalendarEventService;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {

        orderCalendar.addDataProvider(new ListCalendarDataProvider(getOrderCalendarEvents()));

        orderCalendarProjectionComboBox.setItems(List.of(
                CalendarDisplayModes.LIST_YEAR,
                CalendarDisplayModes.TIME_GRID_DAY,
                CalendarDisplayModes.TIME_GRID_WEEK,
                CalendarDisplayModes.DAY_GRID_MONTH,
                CalendarDisplayModes.MULTI_MONTH_YEAR));
        orderCalendarProjectionComboBox.setValue(CalendarDisplayModes.TIME_GRID_WEEK);
    }


    public List<CalendarEvent> getOrderCalendarEvents() {
        return orderCalendarEventService.loadAllOrderEvents()
                .stream()
                .map(this::mapEventToUi)
                .toList();
    }

    public CalendarEvent mapEventToUi(OrderCalendarEventDto orderCalendarEventDto) {
        int durationMinutes = RandomUtils.generateNormalDistributionDurationHalfHour();
        LocalDateTime startTime = orderCalendarEventDto.getStartDateTime()
                .atStartOfDay()
                .plusHours(new Random().nextInt(0, 24))
                .plusMinutes(new Random().nextBoolean() ? 30 : 0);

        SimpleCalendarEvent event = new SimpleCalendarEvent(UUID.randomUUID());
        event.setStartDateTime(startTime);
        event.setEndDateTime(startTime.plusMinutes(durationMinutes));
        event.setTitle(orderCalendarEventDto.getTitle());
        event.setDescription(orderCalendarEventDto.getDescription());

        return event;
    }


    @Subscribe("calendarNextAction")
    public void onCalendarNextAction(final ActionPerformedEvent event) {
        orderCalendar.navigateToNext();
    }

    @Subscribe("calendarPrevAction")
    public void onCalendarPrevAction(final ActionPerformedEvent event) {
        orderCalendar.navigateToPrevious();
    }


    @Subscribe("calendarTodayAction")
    public void onCalendarTodayAction(final ActionPerformedEvent event) {
        orderCalendar.navigateToToday();
    }

    @Install(to = "orderCalendarProjectionComboBox", subject = "itemLabelGenerator")
    private String getLabelByValue(CalendarDisplayModes mode) {
        return switch (mode) {
            case LIST_YEAR -> messageBundle.getMessage("calendarAgendaVisionAction.title");
            case TIME_GRID_DAY -> messageBundle.getMessage("calendarDayVisionAction.title");
            case TIME_GRID_WEEK -> messageBundle.getMessage("calendarWeekVisionAction.title");
            case DAY_GRID_MONTH -> messageBundle.getMessage("calendarMonthVisionAction.title");
            case MULTI_MONTH_YEAR -> messageBundle.getMessage("calendarYearVisionAction.title");
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }

    @Subscribe("orderCalendarProjectionComboBox")
    public void onOrderCalendarProjectionComboBoxComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixComboBox<CalendarDisplayModes>, CalendarDisplayModes> event) {
        orderCalendar.setCalendarDisplayMode(event.getValue());
    }
}
