//package io.jmix.bookstore.test_support.ui;
//
//import io.jmix.core.DataManager;
//import io.jmix.ui.Screens;
//import io.jmix.ui.screen.Screen;
//import io.jmix.ui.screen.StandardEditor;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class ScreenInteractions {
//
//    private final Screens screens;
//    private final DataManager dataManager;
//
//    public ScreenInteractions(Screens screens, DataManager dataManager) {
//        this.screens = screens;
//        this.dataManager = dataManager;
//    }
//
//    public ScreenInteractions(Screens screens) {
//        this.screens = screens;
//        this.dataManager = null;
//    }
//
//    public static ScreenInteractions forEditor(Screens screens, DataManager dataManager) {
//        return new ScreenInteractions(screens, dataManager);
//    }
//
//    public static ScreenInteractions forBrowse(Screens screens) {
//        return forScreen(screens);
//    }
//
//    public static ScreenInteractions forScreen(Screens screens) {
//        return new ScreenInteractions(screens);
//    }
//
//    public <T> T findOpenScreen(Class<T> screenClass) {
//        Screen screen = screens.getOpenedScreens()
//                .getActiveScreens().stream()
//                .filter(it -> screenClass.isAssignableFrom(it.getClass()))
//                .findFirst()
//                .orElseThrow();
//
//        assertThat(screen)
//                .isInstanceOf(screenClass);
//
//        return (T) screen;
//    }
//
//    public <T extends Screen> T open(Class<T> screenClass) {
//        T screen = screens.create(screenClass);
//        screen.show();
//        return screen;
//    }
//
//    public <S extends StandardEditor<E>, E> S openEditorForCreation(Class<S> screenClass, Class<E> entityClass) {
//        return openEditorForEditing(screenClass, entityClass, dataManager.create(entityClass));
//    }
//
//    public <S extends StandardEditor<E>, E> S openEditorForEditing(Class<S> screenClass, Class<E> entityClass, E entity) {
//        S screen = screens.create(screenClass);
//
//        screen.setEntityToEdit(entity);
//        screen.show();
//        return screen;
//    }
//}
