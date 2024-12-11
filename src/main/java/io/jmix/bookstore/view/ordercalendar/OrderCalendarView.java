package io.jmix.bookstore.view.ordercalendar;

import com.vaadin.flow.router.Route;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.flowui.view.*;

@Route(value = "orderCalendar", layout = MainView.class)
@ViewController("bookstore_OrderCalendarView.view")
@ViewDescriptor("order-calendar-view.xml")
@DialogMode(width = "50em", height = "37.5em")
public class OrderCalendarView extends StandardView {

}
