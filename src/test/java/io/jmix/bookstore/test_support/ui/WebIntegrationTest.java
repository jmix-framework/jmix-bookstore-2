package io.jmix.bookstore.test_support.ui;


import io.jmix.bookstore.JmixBookstoreApplication;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("integration-test")
@UiTest
@ContextConfiguration(classes = {JmixBookstoreApplication.class, FlowuiTestAssistConfiguration.class})
@AutoConfigureTestDatabase
public abstract class WebIntegrationTest {
}
