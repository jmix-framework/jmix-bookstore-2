package io.jmix.bookstore.view.order;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.view.Subscribe;

@FragmentDescriptor("delivered-order-list-fragment.xml")
public class DeliveredOrderListFragment extends Fragment<VerticalLayout> {

    public void load() {
        getFragmentData().loadAll();
    }
}