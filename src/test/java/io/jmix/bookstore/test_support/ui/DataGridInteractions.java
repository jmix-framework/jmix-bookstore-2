package io.jmix.bookstore.test_support.ui;

import io.jmix.flowui.action.list.EditAction;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.Action;
import io.jmix.flowui.view.StandardView;

import java.util.ArrayList;
import java.util.List;

public class DataGridInteractions<E> {

    private final DataGrid<E> dataGrid;

    public DataGridInteractions(DataGrid<E> dataGrid) {
        this.dataGrid = dataGrid;
    }

    public static <E> DataGridInteractions<E> of(DataGrid<E> table, Class<E> entityClass) {
        return new DataGridInteractions<>(table);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <E, V extends StandardView> DataGridInteractions<E> of(V view, Class<E> entityClass, String componentId) {
        return DataGridInteractions.of(
                (DataGrid) UiComponentUtils.getComponent(view, componentId),
                entityClass
        );
    }

//    @Nullable
//    Button button(String buttonId) {
//        return Optional.ofNullable((Button) dataGrid.getButtonsPanel().getComponent(buttonId)).orElseThrow();
//    }

    Action action(String actionId) {
        Action action = dataGrid.getAction(actionId);
        if (action == null)
            throw new IllegalArgumentException("Action '%s' not found".formatted(EditAction.ID));
        return action;
    }

    public E firstItem() {
        return dataGrid.getItems().getItems().iterator().next();
    }

    public void selectFirst() {
        dataGrid.getSelectionModel().select(firstItem());
    }

    public void edit(E entity) {
        dataGrid.getSelectionModel().select(entity);
        actionPerform("edit");
    }

    public void actionPerform(String actionId) {
        action(actionId).actionPerform(null);
    }

//    public void click(String buttonId) {
//        button(buttonId).click();
//    }

    public void create() {
        actionPerform("create");
    }

    public List<E> items() {
        return new ArrayList<>(dataGrid.getItems().getItems());
    }

    public void select(E entity) {
        dataGrid.getSelectionModel().select(entity);
    }
}
