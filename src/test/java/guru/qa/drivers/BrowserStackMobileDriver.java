package guru.qa.drivers;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BrowserStackMobileDriver implements WebDriverProvider {

    public static Map<String, Object> browserStackYamlMap;
    public static final String USER_DIR = "user.dir";

    public BrowserStackMobileDriver() {
        File file = new File(getUserDir() + "/browserstack.yml");
        this.browserStackYamlMap = convertYamlFileToMap(file, new HashMap<>());
    }

    @Override
    public WebDriver createDriver(Capabilities capabilities) {

        String userName = System.getenv("BROWSERSTACK_USERNAME") != null
                ? System.getenv("BROWSERSTACK_USERNAME")
                : (String) browserStackYamlMap.get("userName");

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY") != null
                ? System.getenv("BROWSERSTACK_ACCESS_KEY")
                : (String) browserStackYamlMap.get("accessKey");

        // Получаем первое устройство из platforms
        List<Map<String, Object>> platforms = (List<Map<String, Object>>) browserStackYamlMap.get("platforms");
        Map<String, Object> device = platforms.get(0);

        // bstack:options - общие опции BrowserStack
        Map<String, Object> bstackOptions = new HashMap<>();

        MutableCapabilities caps = (MutableCapabilities) capabilities;

        bstackOptions.put("userName", userName);
        bstackOptions.put("accessKey", accessKey);

        caps.setCapability("app", browserStackYamlMap.get("app"));
//        caps.setCapability("browserstack.user", userName);
//        caps.setCapability("browserstack.key", accessKey);
        caps.setCapability("app", browserStackYamlMap.get("app"));

        bstackOptions.put("deviceName", device.get("deviceName"));
        bstackOptions.put("platformVersion", device.get("platformVersion"));
        bstackOptions.put("platformName", device.get("platformName"));

        bstackOptions.put("buildName", browserStackYamlMap.get("buildName"));
        bstackOptions.put("projectName", browserStackYamlMap.get("projectName"));
        bstackOptions.put("debug", browserStackYamlMap.get("debug"));
        bstackOptions.put("networkLogs", browserStackYamlMap.get("networkLogs"));
        bstackOptions.put("local", browserStackYamlMap.getOrDefault("browserstackLocal", false));
        bstackOptions.put("source", "junit5:appium-sample-sdk:v1.1");
        bstackOptions.put("buildIdentifier", browserStackYamlMap.get("buildIdentifier"));

        caps.setCapability("bstack:options", bstackOptions);

        return new AndroidDriver(getBrowserStackUrl(), capabilities);
    }

    private static URL getBrowserStackUrl(){
        try {
            return new URL("https://hub.browserstack.com/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getUserDir() {
        return System.getProperty(USER_DIR);
    }

    private Map<String, Object> convertYamlFileToMap(File yamlFile, Map<String, Object> map) {
        try {
            InputStream inputStream = Files.newInputStream(yamlFile.toPath());
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);
            map.putAll(config);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Malformed browserstack.yml file - %s.", e));
        }
        return map;
    }

}
