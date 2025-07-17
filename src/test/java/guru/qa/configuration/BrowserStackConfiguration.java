package guru.qa.configuration;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:browserstack.properties"
})
public interface BrowserStackConfiguration extends Config {

    @Key("browserstack.url")
    String browserstackUrl();

    @Key("browserstack.userName")
    String userName();

    @Key("browserstack.accessKey")
    String accessKey();
}
