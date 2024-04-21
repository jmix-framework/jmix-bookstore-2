package io.jmix.bookstore.view.supplierorder;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.Outcome;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@ProcessForm(outcomes = {
        @Outcome(id = SupplierOrderApprovalForm.YES_OUTCOME),
        @Outcome(id = SupplierOrderApprovalForm.NO_OUTCOME)
})
@Route(value = "supplier-order-approval-form", layout = MainView.class)
@ViewController("bookstore_SupplierOrderApprovalForm")
@ViewDescriptor("supplier-order-approval-form.xml")
@DialogMode(width = "60em", resizable = true)
public class SupplierOrderApprovalForm extends StandardView {

    static final String YES_OUTCOME = "Yes";
    static final String NO_OUTCOME = "No";

    @Autowired
    private ProcessFormContext processFormContext;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private Notifications notifications;

    @ViewComponent
    DataContext dataContext;
    @ViewComponent
    private InstanceContainer<SupplierOrder> supplierOrderDc;
    @ViewComponent
    private JmixTextArea changesRequiredCommentTextArea;

    @ProcessVariable(name = "supplierOrder")
    private SupplierOrder supplierOrder;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        supplierOrderDc.setItem(dataContext.merge(supplierOrder));
    }

    @Subscribe(id = "yesOutcomeBtn", subject = "clickListener")
    protected void onYesOutcomeBtnClick(ClickEvent<JmixButton> event) {
        dataContext.save();
        processFormContext.taskCompletion()
                .withOutcome(YES_OUTCOME)
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe(id = "noOutcomeBtn", subject = "clickListener")
    protected void onNoOutcomeBtnClick(ClickEvent<JmixButton> event) {
        String changesRequiredComment = changesRequiredCommentTextArea.getValue();
        if (!StringUtils.hasText(changesRequiredComment)) {
            notifications.create("Please provide comment")
                    .withType(Notifications.Type.WARNING)
                    .show();
            return;
        }

        dataContext.save();
        processFormContext.taskCompletion()
                .withOutcome(NO_OUTCOME)
                .saveInjectedProcessVariables()
                .addProcessVariable("changesRequiredComment", changesRequiredComment)
                .addProcessVariable("approver", currentAuthentication.getUser())
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe(id = "cancelBtn", subject = "clickListener")
    public void onCancelBtnClick(final ClickEvent<JmixButton> event) {
        closeWithDefaultAction();
    }
}