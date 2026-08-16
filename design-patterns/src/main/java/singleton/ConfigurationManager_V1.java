package singleton;

import java.util.*;

public class ConfigurationManager_V1 {
    private static ConfigurationManager_V1 instance;
    private Map<String, String> config;

    private ConfigurationManager_V1() {
        this.config = loadConfig();
    }

    // BROKEN — race condition here
    public static ConfigurationManager_V1 getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager_V1();
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

//Two threads hit getInstance() simultaneously. Both see instance == null.
// Both create a new instance. Two instances exist. Singleton broken.

