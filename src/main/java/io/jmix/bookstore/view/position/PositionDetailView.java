package io.jmix.bookstore.view.position;

import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import io.jmix.bookstore.employee.Position;

import io.jmix.bookstore.employee.PositionTranslation;
import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.MessageTools;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "positions/:id", layout = MainView.class)
@ViewController("bookstore_Position.detail")
@ViewDescriptor("position-detail-view.xml")
@EditedEntityContainer("positionDc")
public class PositionDetailView extends StandardDetailView<Position> {

    @Autowired
    private MessageTools messageTools;

    @Supply(to = "translationsDataGrid.locale", subject = "renderer")
    private Renderer<PositionTranslation> translationsDataGridLocaleRenderer() {
        return new TextRenderer<>(item ->
                messageTools.getLocaleDisplayName(item.getLocale()));
    }
}