package io.jmix.bookstore.view.statistics;


import com.vaadin.flow.router.Route;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.flowui.view.*;

@Route(value = "statistics", layout = MainView.class)
@ViewController("bookstore_Statistics.view") // todo name
@ViewDescriptor("statistics-view.xml")
@DialogMode(width = "50em", height = "37.5em")
public class StatisticsView extends StandardView {



}
