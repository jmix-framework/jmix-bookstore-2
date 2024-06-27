package io.jmix.bookstore.view.supplierorder;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.Outcome;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@ProcessForm(outcomes = {
        @Outcome(id = SupplierOrderReviewForm.YES_OUTCOME),
        @Outcome(id = SupplierOrderReviewForm.NO_OUTCOME)
})
@Route(value = "supplier-order-review-form", layout = MainView.class)
@ViewController("bookstore_SupplierOrderReviewForm")
@ViewDescriptor("supplier-order-review-form.xml")
@DialogMode(width = "60em", resizable = true)
public class SupplierOrderReviewForm extends StandardView {

    static final String YES_OUTCOME = "Yes";
    static final String NO_OUTCOME = "No";

    @Autowired
    private ProcessFormContext processFormContext;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @ViewComponent
    DataContext dataContext;
    @ViewComponent
    private InstanceContainer<SupplierOrder> supplierOrderDc;
    @ViewComponent
    private H4 changesRequiredCommentLabel;

    @ProcessVariable(name = "supplierOrder")
    private SupplierOrder supplierOrder;

    @ProcessVariable(name = "changesRequiredComment")
    private String changesRequiredComment;

    @ProcessVariable(name = "approver")
    private User approver;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        supplierOrderDc.setItem(dataContext.merge(supplierOrder));

        if (StringUtils.hasText(changesRequiredComment)) {
            String message = messageBundle.formatMessage("changesRequiredComment", approver.getDisplayName(), changesRequiredComment);
            changesRequiredCommentLabel.setText(message);
            changesRequiredCommentLabel.setVisible(true);
        }
    }

    @Subscribe(id = "yesOutcomeBtn", subject = "clickListener")
    protected void onYesOutcomeBtnClick(ClickEvent<JmixButton> event) {
        dataContext.save();
        processFormContext.taskCompletion()
                .withOutcome(YES_OUTCOME)
                .saveInjectedProcessVariables()
                .addProcessVariable("reviewedBy", currentAuthentication.getUser())
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe(id = "noOutcomeBtn", subject = "clickListener")
    protected void onNoOutcomeBtnClick(ClickEvent<JmixButton> event) {
        dataContext.save();
        processFormContext.taskCompletion()
                .withOutcome(NO_OUTCOME)
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe(id = "cancelBtn", subject = "clickListener")
    public void onCancelBtnClick(final ClickEvent<JmixButton> event) {
        closeWithDefaultAction();
    }
}