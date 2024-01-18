//package io.jmix.bookstore.test_support.ui;
//
//import io.jmix.core.metamodel.datatype.impl.EnumClass;
//import io.jmix.ui.component.*;
//import io.jmix.ui.screen.StandardEditor;
//import io.jmix.ui.util.OperationResult;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class FormInteractions {
//
//    private final StandardEditor editor;
//
//    public FormInteractions(StandardEditor editor) {
//        this.editor = editor;
//    }
//
//    public static FormInteractions of(StandardEditor editor) {
//        return new FormInteractions(editor);
//    }
//
//    @Nullable
//    TextField<String> textField(String componentId) {
//        return (TextField<String>) getComponent(componentId);
//    }
//    @Nullable
//    Label<String> labelField(String componentId) {
//        return (Label<String>) getComponent(componentId);
//    }
//    @Nullable
//    TextField<Number> numberField(String componentId) {
//        return (TextField<Number>) getComponent(componentId);
//    }
//    @Nullable
//    CurrencyField<Number> currencyField(String componentId) {
//        return (CurrencyField<Number>) getComponent(componentId);
//    }
//
//    ComboBox<EnumClass<String>> comboBoxField(String componentId) {
//        return (ComboBox<EnumClass<String>>) getComponent(componentId);
//    }
//
//    <T> ComboBox<T> entityComboBoxField(String componentId, Class<T> entityClass) {
//        return (ComboBox<T>) getComponent(componentId);
//    }
//
//    @Nullable
//    private Component getComponent(String componentId) {
//        return editor.getWindow().getComponent(componentId);
//    }
//
//    private <T> EntitySuggestionField<T> entitySuggestionField(String componentId, Class<T> entityClass) {
//        return (EntitySuggestionField<T>) getComponent(componentId);
//    }
//
//    @Nullable
//    Button button(String buttonId) {
//        return (Button) getComponent(buttonId);
//    }
//
//    public void setTextFieldValue(String componentId, String value) {
//        textField(componentId).setValue(value);
//    }
//    public void setNumberFieldValue(String componentId, Number value) {
//        numberField(componentId).setValue(value);
//    }
//    public void setCurrencyFieldValue(String componentId, BigDecimal value) {
//        currencyField(componentId).setValue(value);
//    }
//
//    public OperationResult saveForm() {
//        return editor.closeWithCommit();
//    }
//
//    public void setEnumFieldValue(String componentId, EnumClass<String> value) {
//        comboBoxField(componentId).setValue(value);
//    }
//
//    public <T> List<T> getEntityComboBoxValues(String componentId, Class<T> entityClass) {
//        return entityComboBoxField(componentId, entityClass).getOptions().getOptions().collect(Collectors.toList());
//    }
//
//    public <T> void setEntityComboBoxFieldValue(String componentId, T entity, Class<T> entityClass) {
//        ComboBox<T> comboBox = entityComboBoxField(componentId, entityClass);
//
//        T entityFromComboBox = comboBox.getOptions().getOptions().filter(t -> t.equals(entity)).findFirst().orElseThrow();
//        comboBox.setValue(entityFromComboBox);
//    }
//    public <T> void setEntitySuggestionFieldValue(String componentId, T entity, Class<T> entityClass) {
//        EntitySuggestionField<T> entitySuggestionField = entitySuggestionField(componentId, entityClass);
//
//        entitySuggestionField.setValueFromUser(entity);
//    }
//
//    @NotNull
//    public <T> List<T> getSuggestions(String searchString, String componentId, Class<T> entityClass) {
//        EntitySuggestionField<T> entitySuggestionField = entitySuggestionField(componentId, entityClass);
//
//        return entitySuggestionField.getSearchExecutor()
//                .search(searchString, Map.of());
//    }
//
//    public boolean isVisible(String componentId) {
//        return getComponent(componentId).isVisibleRecursive();
//    }
//
//    public String getLabelValue(String componentId) {
//        return labelField(componentId).getRawValue();
//    }
//
//    public void click(String componentId) {
//        button(componentId).click();
//    }
//}
