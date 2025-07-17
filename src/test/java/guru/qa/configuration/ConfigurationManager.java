package guru.qa.configuration;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {

    public static BrowserStackConfiguration getBrowserStackConfig(){
        return ConfigCache.getOrCreate(BrowserStackConfiguration.class);
    }

}
