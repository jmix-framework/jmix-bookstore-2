package io.jmix.bookstore.view.quartz;

import com.vaadin.flow.router.Route;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.quartzflowui.view.jobs.JobModelListView;

@Route(value = "quartz/jobmodels", layout = MainView.class)
@ViewController(id = "quartz_JobModel.list")
@ViewDescriptor(path = "bookstore-job-model-list-view.xml")
public class BookstoreJobModelListView extends JobModelListView {
}