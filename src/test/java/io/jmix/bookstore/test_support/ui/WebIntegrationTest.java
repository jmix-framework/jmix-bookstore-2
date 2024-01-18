//package io.jmix.bookstore.test_support.ui;
//
//
//
//import io.jmix.bookstore.JmixBookstoreApplication;
//import io.jmix.ui.Screens;
//import io.jmix.ui.testassist.UiTestAssistConfiguration;
//import io.jmix.ui.testassist.junit.UiTest;
//import org.junit.jupiter.api.BeforeEach;
//
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//
//@SpringBootTest
//@ActiveProfiles("integration-test")
//@UiTest(authenticatedUser = "admin", mainScreenId = "bookstore_MainScreen", screenBasePackages = "io.jmix.bookstore")
//@ContextConfiguration(classes = {JmixBookstoreApplication.class, UiTestAssistConfiguration.class})
//@AutoConfigureTestDatabase
//public abstract class WebIntegrationTest {
//
//
//    @BeforeEach
//    void removeAllScreens(Screens screens) {
//        screens.removeAll();
//    }
//}
