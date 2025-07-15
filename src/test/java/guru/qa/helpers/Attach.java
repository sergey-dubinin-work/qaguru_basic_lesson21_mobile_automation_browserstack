package guru.qa.helpers;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.WebDriverRunner.driver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Attach {

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message){
        return message;
    }

    @Attachment(value = "Page Source", type = "text/xml")
    public static byte[] pageSource(){
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] screenshotAs(String attachName){
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Autoplay video", type = "text/html", fileExtension = ".html")
    public static String addAutoplayVideo(String sessionId) {

        return loadTemplate("templatesHTML/autoplayVideo.html").replace(
                "{{video_url}}",
                BrowserStack.getVideoUrl(sessionId).toString()
        );

    }

    public static String loadTemplate(String templatePath) {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(templatePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Template file not found: " + templatePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load template: " + templatePath, e);
        }
    }

    public static String getSessionId(){
        return driver().getSessionId().toString();
    }

}
