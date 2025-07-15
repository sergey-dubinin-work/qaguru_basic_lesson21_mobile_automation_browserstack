package guru.qa;

import com.codeborne.selenide.Configuration;
import guru.qa.helpers.Attach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import guru.qa.drivers.BrowserStackMobileDriver;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        Configuration.browser = BrowserStackMobileDriver.class.getName();
        Configuration.browserSize = null;
    }

    @BeforeEach
    void setUp() {
        open();
    }

    @AfterEach
    void tearDown() {
        String sessionId = Attach.getSessionId();

        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();

        closeWebDriver();

        Attach.addAutoplayVideo(sessionId);
    }
}
