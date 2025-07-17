package guru.qa.helpers;

import io.qameta.allure.Attachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.isNull;

public class Attach {

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String asText(String attachName, String message){
        return message;
    }

    @Attachment(value = "Page Source", type = "text/xml")
    public static byte[] pageSource(){
        return Driver.getPageSource();
    }

    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] screenshotAs(String attachName){
        return Driver.getScreenshot();
    }

    @Attachment(value = "Autoplay video", type = "text/html", fileExtension = ".html")
    public static String autoplayVideo(String videoUrl) {

        if (!isNull(videoUrl)){
            return loadTemplate("templatesHTML/autoplayVideo.html").replace(
                    "{{video_url}}",
                    videoUrl
            );
        }

        return loadTemplate("templatesHTML/noVideo.html");
    }

    @Attachment(value = "{attachName}", type = "text/html", fileExtension = ".html")
    public static String url(String attachName, String url){

        return loadTemplate("templatesHTML/simpleLink.html")
                .replace("{{link_description}}", attachName)
                .replace("{{link_url}}", url);

    }

    private static String loadTemplate(String templatePath) {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(templatePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Template file not found: " + templatePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load template: " + templatePath, e);
        }
    }

}
