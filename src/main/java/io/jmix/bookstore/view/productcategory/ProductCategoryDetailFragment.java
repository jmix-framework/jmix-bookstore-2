package io.jmix.bookstore.view.productcategory;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.sidepanellayout.SidePanelLayout;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewValidation;
import org.springframework.beans.factory.annotation.Autowired;

@FragmentDescriptor("product-category-detail-fragment.xml")
public class ProductCategoryDetailFragment extends Fragment<VerticalLayout> {

    @ViewComponent
    private InstanceContainer<ProductCategory> productCategoryDc;

    @Autowired
    private ViewValidation viewValidation;

    @Autowired
    private DataManager dataManager;

    private SidePanelLayout sidePanelLayout;

    public ProductCategoryDetailFragment withSidePanelLayout(SidePanelLayout sidePanelLayout) {
        this.sidePanelLayout = sidePanelLayout;
        return this;
    }

    public ProductCategoryDetailFragment withNewItem() {
        productCategoryDc.setItem(dataManager.create(ProductCategory.class));
        return this;
    }

    public ProductCategoryDetailFragment withEditedItem(ProductCategory productCategory) {
        productCategoryDc.setItem(dataManager.load(Id.of(productCategory)).one());
        return this;
    }

    public ProductCategoryDetailFragment withSaveListener(ComponentEventListener<SaveEvent> listener) {
        addListener(SaveEvent.class, listener);
        return this;
    }

    public void focusFirstField() {
        UiComponentUtils.getComponents(getContent()).stream()
                .filter(c -> c instanceof TextField)
                .findFirst()
                .ifPresent(c -> ((TextField) c).focus());
    }

    public ValidationErrors validate() {
        return viewValidation.validateUiComponents(getContent());
    }

    @Subscribe(id = "saveButton", subject = "clickListener")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        ValidationErrors errors = validate();
        if (errors.isEmpty()) {
            ProductCategory saved = dataManager.save(productCategoryDc.getItem());

            fireEvent(new SaveEvent(this, event.isFromClient(), saved));
            sidePanelLayout.closeSidePanel();
        } else {
            viewValidation.showValidationErrors(errors);
        }
    }

    @Subscribe(id = "cancelButton", subject = "clickListener")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        sidePanelLayout.closeSidePanel();
    }

    public static class SaveEvent extends ComponentEvent<ProductCategoryDetailFragment> {

        protected ProductCategory item;

        public SaveEvent(ProductCategoryDetailFragment source,
                         boolean fromClient,
                         ProductCategory item) {
            super(source, fromClient);

            this.item = item;
        }

        public ProductCategory getItem() {
            return item;
        }
    }
}