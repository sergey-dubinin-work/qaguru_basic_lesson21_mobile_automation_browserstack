package guru.qa.tests;

import com.codeborne.selenide.CollectionCondition;
import guru.qa.TestBase;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class BrowserStackSelenideTests extends TestBase {

    @Test
    void androidSelenideSampleTest() {
        step("Skip 'Choose language' step", () ->
                $(AppiumBy.id("org.wikipedia.alpha:id/fragment_onboarding_skip_button"))
                        .click()

        );

        sleep(5000);

        step("Click 'Search'", () ->
                $(AppiumBy.accessibilityId("Search Wikipedia"))
                        .click()
        );

        step("Input search text", () ->
                $(AppiumBy.id("org.wikipedia.alpha:id/search_src_text"))
                        .sendKeys("BrowserStack")
        );

        sleep(5000);

        step("Check search results", () ->
                $$(AppiumBy.className("android.widget.TextView"))
                        .shouldHave(CollectionCondition.sizeGreaterThan(0))
        );


    }

}
