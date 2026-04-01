package io.jmix.bookstore.view.quartz;

import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.quartzflowui.view.jobs.JobModelListView;

@ViewController(id = "quartz_JobModel.list")
@ViewDescriptor(path = "bookstore-job-model-list-view.xml")
public class BookstoreJobModelListView extends JobModelListView {
}