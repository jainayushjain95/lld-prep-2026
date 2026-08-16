package singleton;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager_V2 {
    private static ConfigurationManager_V2 instance;
    private Map<String, String> config;

    private ConfigurationManager_V2() {
        this.config = loadConfig();
    }

    public static synchronized ConfigurationManager_V2 getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager_V2();
        }
        return instance;
    }

    public String get(String key) {
        return config.get(key);
    }

    private Map<String, String> loadConfig() {
        return new HashMap<>();
    }

}

//Works but every single call to getInstance() acquires a lock —
// even after the instance is already created. In a high-traffic system, this is a bottleneck.
// Synchronization is only needed once — during the first creation.
// After that it's unnecessary overhead.
