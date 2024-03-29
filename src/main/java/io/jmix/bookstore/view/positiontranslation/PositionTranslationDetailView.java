package io.jmix.bookstore.view.positiontranslation;

import io.jmix.bookstore.employee.PositionTranslation;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.MessageTools;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Route(value = "positionTranslations/:id", layout = MainView.class)
@ViewController("bookstore_PositionTranslation.detail")
@ViewDescriptor("position-translation-detail-view.xml")
@EditedEntityContainer("positionTranslationDc")
public class PositionTranslationDetailView extends StandardDetailView<PositionTranslation> {

    @ViewComponent
    private JmixComboBox<Locale> localeField;

    @Autowired
    private MessageTools messageTools;

    @Subscribe
    public void onInit(final InitEvent event) {
        Map<Locale, String> swappedMap = messageTools.getAvailableLocalesMap().entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getValue(), entry -> entry.getKey()));
        ComponentUtils.setItemsMap(localeField, swappedMap);
    }
}