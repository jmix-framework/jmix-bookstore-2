package io.jmix.bookstore.view.statistics;


import com.vaadin.flow.router.Route;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.fake.OrderFakeGenerator;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "statistics", layout = MainView.class)
@ViewController("bookstore_Statistics.view") // todo name
@ViewDescriptor("statistics-view.xml")
@DialogMode(width = "50em", height = "37.5em")
public class StatisticsView extends StandardView {
    @ViewComponent
    private CollectionContainer<Order> ordersDc;
    @Autowired
    private OrderFakeGenerator orderFakeGenerator;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        ordersDc.getMutableItems().addAll(orderFakeGenerator.generateRandomOrdersWithData(10000));
    }



}
