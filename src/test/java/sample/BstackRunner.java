package sample;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.MutableCapabilities;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BstackRunner {
    public AndroidDriver driver;

    public static String userName, accessKey;
    public static Map<String, Object> browserStackYamlMap;
    public static final String USER_DIR = "user.dir";

    public BstackRunner() {
        File file = new File(getUserDir() + "/browserstack.yml");
        this.browserStackYamlMap = convertYamlFileToMap(file, new HashMap<>());
    }

    @BeforeEach
    public void setUp() throws Exception {
        userName = System.getenv("BROWSERSTACK_USERNAME") != null
                ? System.getenv("BROWSERSTACK_USERNAME")
                : (String) browserStackYamlMap.get("userName");

        accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY") != null
                ? System.getenv("BROWSERSTACK_ACCESS_KEY")
                : (String) browserStackYamlMap.get("accessKey");

        MutableCapabilities capabilities = new MutableCapabilities();

        // Получаем первое устройство из platforms
        List<Map<String, Object>> platforms = (List<Map<String, Object>>) browserStackYamlMap.get("platforms");
        Map<String, Object> device = platforms.get(0);

        // bstack:options - общие опции BrowserStack
        Map<String, Object> bstackOptions = new HashMap<>();

        // Устанавливаем app отдельно в capabilities (корневой уровень)
        capabilities.setCapability("app", browserStackYamlMap.get("app"));

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

        capabilities.setCapability("bstack:options", bstackOptions);

        String url = String.format("https://%s:%s@hub.browserstack.com/wd/hub", userName , accessKey);

        driver = new AndroidDriver(new URL(url), capabilities);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null){

            driver.quit();
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
