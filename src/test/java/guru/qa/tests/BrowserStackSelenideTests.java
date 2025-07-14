package guru.qa.tests;

import com.codeborne.selenide.CollectionCondition;
import guru.qa.TestBase;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class BrowserStackSelenideTests extends TestBase {

    @Test
    void androidSelenideSampleTest() {
        $(AppiumBy.id("org.wikipedia.alpha:id/fragment_onboarding_skip_button"))
                .click();

        sleep(5000);

        $(AppiumBy.accessibilityId("Search Wikipedia"))
                .click();

        $(AppiumBy.id("org.wikipedia.alpha:id/search_src_text"))
                .sendKeys("BrowserStack");

        sleep(5000);

        $$(AppiumBy.className("android.widget.TextView"))
                .shouldHave(CollectionCondition.sizeGreaterThan(0));
    }

}
