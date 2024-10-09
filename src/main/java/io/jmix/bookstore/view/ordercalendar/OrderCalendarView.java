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
        updateCalendarOptions();

        orderCalendar.addDataProvider(new ListCalendarDataProvider(getOrderCalendarEvents()));

        orderCalendarProjectionComboBox.setItems(List.of(
                CalendarDisplayModes.LIST_YEAR,
                CalendarDisplayModes.TIME_GRID_DAY,
                CalendarDisplayModes.TIME_GRID_WEEK,
                CalendarDisplayModes.DAY_GRID_MONTH,
                CalendarDisplayModes.MULTI_MONTH_YEAR));
        orderCalendarProjectionComboBox.setValue(CalendarDisplayModes.TIME_GRID_WEEK);
    }

    @Subscribe("orderCalendar")
    public void onOrderCalendarDatesSet(final DatesSetEvent e) {

        LocalDate startDate = e.getStartDate();
        LocalDate endDate = e.getEndDate();

        DateTimeFormatter monthDayFormatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH);

        String formattedDate;
        if (startDate.getYear() == endDate.getYear()) {
            formattedDate = MessageFormat.format("{0} - {1}, {2}",
                    startDate.format(monthDayFormatter),
                    endDate.format(monthDayFormatter),
                    endDate.format(yearFormatter));
        } else {
            formattedDate = MessageFormat.format("{0}, {1} - {2}, {3}",
                    startDate.format(monthDayFormatter),
                    startDate.format(yearFormatter),
                    endDate.format(monthDayFormatter),
                    endDate.format(yearFormatter));
        }
        calendarTitle.setText(formattedDate);
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
        event.setAllDay(false);
        event.setStartDateTime(startTime);
        event.setEndDateTime(startTime.plusMinutes(durationMinutes));
        event.setTitle(orderCalendarEventDto.getTitle());
        event.setDescription(orderCalendarEventDto.getDescription());
        event.setInteractive(true);
        event.setClassNames("shipping-event");
        event.setStartEditable(true);
        event.setDurationEditable(true);
        event.setDisplay(Display.BLOCK);
        event.setOverlap(true);
        event.setBackgroundColor("var(--event-fill-body)"); // Зеленый фон
        event.setBorderColor("var(--event-border-color)");
        event.setTextColor("var(--secondary-base-color)");

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

    @Supply(to = "orderCalendarProjectionComboBox", subject = "renderer")
    private Renderer<CalendarDisplayModes> orderCalendarProjectionComboBoxRenderer() {
        return new ComponentRenderer<>(e -> {
            String label = getLabelByValue(e);
            Icon icon = switch (e) {
                case CalendarDisplayModes.LIST_YEAR -> VaadinIcon.LINES_LIST.create();
                case CalendarDisplayModes.TIME_GRID_DAY -> VaadinIcon.GRID_V.create();
                case CalendarDisplayModes.TIME_GRID_WEEK -> VaadinIcon.GRID_H.create();
                case CalendarDisplayModes.DAY_GRID_MONTH -> VaadinIcon.GRID_BIG_O.create();
                case CalendarDisplayModes.MULTI_MONTH_YEAR -> VaadinIcon.GRID_SMALL_O.create();
                default -> throw new IllegalStateException("Unexpected value: " + e);
            };

            HorizontalLayout contentBox = uiComponents.create(HorizontalLayout.class);
            contentBox.setPadding(false);

            contentBox.add(icon);
            contentBox.add(label);

            return contentBox;
        });
    }

    @Install(to = "orderCalendarProjectionComboBox", subject = "itemLabelGenerator")
    private String orderCalendarProjectionComboBoxItemLabelGenerator(final CalendarDisplayModes mode) {
        return getLabelByValue(mode);
    }

    private String getLabelByValue(CalendarDisplayModes mode) {
        return switch (mode) {
            case CalendarDisplayModes.LIST_YEAR -> messageBundle.getMessage("calendarAgendaVisionAction.title");
            case CalendarDisplayModes.TIME_GRID_DAY -> messageBundle.getMessage("calendarDayVisionAction.title");
            case CalendarDisplayModes.TIME_GRID_WEEK -> messageBundle.getMessage("calendarWeekVisionAction.title");
            case CalendarDisplayModes.DAY_GRID_MONTH -> messageBundle.getMessage("calendarMonthVisionAction.title");
            case CalendarDisplayModes.MULTI_MONTH_YEAR -> messageBundle.getMessage("calendarYearVisionAction.title");
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }

    @Subscribe("orderCalendarProjectionComboBox")
    public void onOrderCalendarProjectionComboBoxComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixComboBox<CalendarDisplayModes>, CalendarDisplayModes> event) {
        orderCalendar.setCalendarDisplayMode(event.getValue());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void updateCalendarOptions() {
        try {
            Field optionsField = JmixFullCalendar.class.getDeclaredField("options");

            // Открытие доступа к защищенному полю
            optionsField.setAccessible(true);

            FullCalendarOptions options = (FullCalendarOptions) optionsField.get(orderCalendar);

            Field optionsMapField = JmixFullCalendarOptions.class.getDeclaredField("optionsMap");
            optionsMapField.setAccessible(true);

            Map optionMap = (Map) optionsMapField.get(options);
            optionMap.put("slotLabelFormat", new CalendarOption("slotLabelFormat") {
                @SuppressWarnings("DataFlowIssue")
                @Nullable
                @Override
                protected Object getValueToSerialize() {
                    return Map.of("hour", "numeric", "hour12", true);
                }

                @Override
                protected String getName() {
                    return super.getName();
                }

                @Override
                protected boolean isDirty() {
                    return true;
                }
            });

            optionMap.put("dayHeaderFormat", new CalendarOption("dayHeaderFormat") {
                @SuppressWarnings("DataFlowIssue")
                @Nullable
                @Override
                protected Object getValueToSerialize() {
                    return Map.of("weekday", "long", "day", "2-digit");
                }

                @Override
                protected String getName() {
                    return super.getName();
                }

                @Override
                protected boolean isDirty() {
                    return true;
                }
            });


            Method performUpdateOptions = JmixFullCalendar.class.getDeclaredMethod("performUpdateOptions", Boolean.TYPE);

            performUpdateOptions.setAccessible(true);
            performUpdateOptions.invoke(orderCalendar, true);
        } catch (Exception e) {
            throw new RuntimeException("UI caller exception", e);
        }
    }
}
