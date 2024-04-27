package io.jmix.bookstore.test_support.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.provider.DataProvider;
import io.jmix.core.metamodel.datatype.EnumClass;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.data.items.ContainerDataProvider;
import io.jmix.flowui.util.OperationResult;
import io.jmix.flowui.view.StandardDetailView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FormInteractions {

    private final StandardDetailView detailView;

    public FormInteractions(StandardDetailView detailView) {
        this.detailView = detailView;
    }

    public static FormInteractions of(StandardDetailView editor) {
        return new FormInteractions(editor);
    }

    @Nullable
    TypedTextField<String> textField(String componentId) {
        return (TypedTextField<String>) getComponent(componentId);
    }

    @Nullable
    HtmlContainer htmlContainerField(String componentId) {
        return (HtmlContainer) getComponent(componentId);
    }

    @Nullable
    TypedTextField<Number> numberField(String componentId) {
        return (TypedTextField<Number>) getComponent(componentId);
    }

    ComboBox<EnumClass<String>> comboBoxField(String componentId) {
        return (ComboBox<EnumClass<String>>) getComponent(componentId);
    }

    <T> EntityComboBox<T> entityComboBoxField(String componentId, Class<T> entityClass) {
        return (EntityComboBox<T>) getComponent(componentId);
    }

    @Nullable
    private Component getComponent(String componentId) {
        return UiComponentUtils.findComponent(detailView, componentId).orElse(null);
    }

    @Nullable
    Button button(String buttonId) {
        return (Button) getComponent(buttonId);
    }

    public void setTextFieldValue(String componentId, String value) {
        textField(componentId).setTypedValue(value);
    }
    public void setNumberFieldValue(String componentId, Number value) {
        numberField(componentId).setTypedValue(value);
    }

    public OperationResult saveForm() {
        return detailView.closeWithSave();
    }

    public void setEnumFieldValue(String componentId, EnumClass<String> value) {
        comboBoxField(componentId).setValue(value);
    }

    public <T> List<T> getEntityComboBoxValues(String componentId, Class<T> entityClass) {
        EntityComboBox<T> entityComboBox = entityComboBoxField(componentId, entityClass);
        return getEntityComboBoxValues(entityComboBox);
    }

    @NotNull
    private <T> List<T> getEntityComboBoxValues(EntityComboBox<T> entityComboBox) {
        DataProvider<T, ?> dataProvider = entityComboBox.getDataProvider();
        if (dataProvider instanceof ContainerDataProvider containerDataProvider) {
            return containerDataProvider.getContainer().getItems();
        } else {
            throw new UnsupportedOperationException("Unsupported DataProvider: " + dataProvider);
        }
    }

    public <T> void setEntityComboBoxFieldValue(String componentId, T entity, Class<T> entityClass) {
        EntityComboBox<T> comboBox = entityComboBoxField(componentId, entityClass);
        T entityFromComboBox = getEntityComboBoxValues(comboBox).stream()
                .filter(t -> t.equals(entity))
                .findFirst()
                .orElseThrow();
        comboBox.setValue(entityFromComboBox);
    }

    public boolean isVisible(String componentId) {
        return UiComponentUtils.isComponentVisible(UiComponentUtils.getComponent(detailView, componentId));
    }

    public void click(String componentId) {
        button(componentId).click();
    }
}
