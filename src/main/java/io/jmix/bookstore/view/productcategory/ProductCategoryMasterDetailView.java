package io.jmix.bookstore.view.productcategory;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.core.AccessManager;
import io.jmix.core.EntityStates;
import io.jmix.core.entity.EntityValues;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.flowui.Fragments;
import io.jmix.flowui.UiComponentProperties;
import io.jmix.flowui.UiViewProperties;
import io.jmix.flowui.accesscontext.UiEntityAttributeContext;
import io.jmix.flowui.action.SecuredBaseAction;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.sidepanellayout.SidePanelLayout;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.data.EntityValueSource;
import io.jmix.flowui.data.SupportsValueSource;
import io.jmix.flowui.kit.action.Action;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.sidepanellayout.SidePanelAfterOpenEvent;
import io.jmix.flowui.kit.component.sidepanellayout.SidePanelCloseEvent;
import io.jmix.flowui.kit.component.sidepanellayout.SidePanelPosition;
import io.jmix.flowui.model.*;
import io.jmix.flowui.util.OperationResult;
import io.jmix.flowui.util.UnknownOperationResult;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import static io.jmix.flowui.component.delegate.AbstractFieldDelegate.PROPERTY_INVALID;

@Route(value = "product-categories", layout = MainView.class)
@ViewController(id = "bookstore_ProductCategory.md")
@ViewDescriptor(path = "product-category-master-detail-view.xml")
@LookupComponent("productCategoriesDataGrid")
@DialogMode(width = "64em")
public class ProductCategoryMasterDetailView extends StandardListView<ProductCategory> {

    @ViewComponent
    private SidePanelLayout sidePanelLayout;
    @ViewComponent
    private VerticalLayout sidePanelContent;
    @ViewComponent
    private DataGrid<ProductCategory> productCategoriesDataGrid;
    @ViewComponent
    private JmixSelect<SidePanelPosition> positionSelect;

    @ViewComponent
    private CollectionLoader<ProductCategory> productCategoriesDl;
    @ViewComponent
    private CollectionContainer<ProductCategory> productCategoriesDc;

    @Autowired
    private Fragments fragments;

    @Subscribe
    public void onInit(final InitEvent event) {
        positionSelect.setItems(SidePanelPosition.values());
        positionSelect.setValue(sidePanelLayout.getSidePanelPosition());
        positionSelect.addValueChangeListener(e -> sidePanelLayout.setSidePanelPosition(e.getValue()));
        positionSelect.setItemLabelGenerator(Enum::name);
    }

    @Subscribe("productCategoriesDataGrid.editAction")
    public void onProductCategoriesDataGridEditAction(final ActionPerformedEvent event) {
        if (sidePanelLayout.isSidePanelOpened()) {
            return;
        }

        ProductCategory item = productCategoriesDataGrid.getSingleSelectedItem();

        ProductCategoryDetailFragment editorFragment = createEditorFragment()
                .withEditedItem(item)
                .withSaveListener(saveEvent -> productCategoriesDc.replaceItem(saveEvent.getItem()));

        sidePanelContent.add(editorFragment);
        sidePanelLayout.openSidePanel();
    }

    @Subscribe("productCategoriesDataGrid.createAction")
    public void onProductCategoriesDataGridCreateAction(final ActionPerformedEvent event) {
        if (sidePanelLayout.isSidePanelOpened()) {
            return;
        }

        ProductCategoryDetailFragment editorFragment = createEditorFragment()
                .withNewItem()
                .withSaveListener(saveEvent -> productCategoriesDl.load());

        sidePanelContent.add(editorFragment);
        sidePanelLayout.openSidePanel();
    }

    @Subscribe(id = "sidePanelLayout", subject = "addSidePanelAfterOpenListener")
    public void onSidePanelLayoutAfterOpened(SidePanelAfterOpenEvent event) {
        ProductCategoryDetailFragment fragment = (ProductCategoryDetailFragment) sidePanelContent.getComponentAt(0);
        fragment.focusFirstField();
    }

    @Subscribe(id = "sidePanelLayout", subject = "addSidePanelCloseListener")
    public void onSidePanelLayoutClosed(SidePanelCloseEvent event) {
        sidePanelContent.removeAll();
    }

    private ProductCategoryDetailFragment createEditorFragment() {
        return fragments.create(this, ProductCategoryDetailFragment.class)
                .withSidePanelLayout(sidePanelLayout);
    }

    @Subscribe("overlayCheckbox")
    public void onOverlayCheckboxComponentValueChange(final ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        sidePanelLayout.setSidePanelOverlay(event.getValue());
    }

    @Subscribe("modalCheckbox")
    public void onModalCheckboxComponentValueChange(final ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        sidePanelLayout.setModal(event.getValue());
    }
}