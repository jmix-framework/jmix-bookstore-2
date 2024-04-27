package io.jmix.bookstore.test_support.ui;

import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.testassist.UiTestUtils;
import io.jmix.flowui.view.DialogWindow;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.View;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewInteractions {

    private ViewNavigators viewNavigators;
    private View<?> parentView;
    private DialogWindows dialogWindows;

    public ViewInteractions(ViewNavigators viewNavigators) {
        this.viewNavigators = viewNavigators;
    }

    public ViewInteractions(View<?> parentView, DialogWindows dialogWindows) {
        this.parentView = parentView;
        this.dialogWindows = dialogWindows;
    }

    public static ViewInteractions forDialog(DialogWindows dialogWindows) {
        return new ViewInteractions(UiTestUtils.getCurrentView(), dialogWindows);
    }

    public static ViewInteractions forNavigation(ViewNavigators viewNavigators) {
        return new ViewInteractions(viewNavigators);
    }

    @SuppressWarnings("unchecked")
    public <T> T findOpenView(Class<T> viewClass) {
        View<?> currentView = UiTestUtils.getCurrentView();

        assertThat(currentView)
                .isInstanceOf(viewClass);

        return (T) currentView;
    }

    @SuppressWarnings("unchecked")
    public <T extends StandardView> T navigate(Class<T> viewClass) {
        viewNavigators.view(viewClass).navigate();
        View<?> currentView = UiTestUtils.getCurrentView();
        assertThat(currentView).isInstanceOf(viewClass);
        return (T) currentView;
    }

    public <S extends StandardDetailView<E>, E> S openDetailForCreation(Class<S> viewClass, Class<E> entityClass) {
        DialogWindow<S> dialogWindow = dialogWindows.detail(parentView, entityClass)
                .withViewClass(viewClass)
                .newEntity()
                .build();
        return dialogWindow.getView();
    }

    public <S extends StandardDetailView<E>, E> S openDetailForEditing(Class<S> viewClass, Class<E> entityClass, E entity) {
        DialogWindow<S> dialogWindow = dialogWindows.detail(parentView, entityClass)
                .withViewClass(viewClass)
                .editEntity(entity)
                .build();
        return dialogWindow.getView();
    }
}
