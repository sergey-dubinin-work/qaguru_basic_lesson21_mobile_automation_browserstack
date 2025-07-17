package guru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import guru.qa.helpers.Attach;
import guru.qa.helpers.BrowserStack;
import guru.qa.helpers.Driver;
import guru.qa.helpers.models.SessionDetails;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import guru.qa.drivers.BrowserStackMobileDriver;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.browser = BrowserStackMobileDriver.class.getName();
        Configuration.browserSize = null;
    }

    @BeforeEach
    void setUp() {
        open();
    }

    @AfterEach
    void tearDown() {
        String sessionId = Driver.getSessionId();

        SessionDetails sessionDetails = BrowserStack.getSessionDetails(sessionId);

        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();

        closeWebDriver();

        Attach.autoplayVideo(sessionDetails.getVideoUrl());
        Attach.url("Logs", sessionDetails.getLogsUrl());
        Attach.url("Appium Logs", sessionDetails.getAppiumLogsUrl());
        Attach.url("Device Logs", sessionDetails.getDeviceLogsUrl());

    }

}
