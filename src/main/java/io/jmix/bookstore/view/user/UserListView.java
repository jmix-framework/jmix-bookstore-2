package io.jmix.bookstore.view.user;

import com.vaadin.flow.router.Route;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.flowui.view.*;

@Route(value = "users", layout = MainView.class)
@ViewController("bkst_User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersTable")
@DialogMode(width = "50em", height = "37.5em")
public class UserListView extends StandardListView<User> {
}