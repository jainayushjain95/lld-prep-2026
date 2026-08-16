package singleton;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager_V3 {
    private static volatile ConfigurationManager_V3 instance;
    private Map<String, String> config;

    private ConfigurationManager_V3() {
        this.config = loadConfig();
    }

    public static ConfigurationManager_V3 getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager_V3.class) {
                if (instance == null) {
                    instance = new ConfigurationManager_V3();
                }
            }
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

//Double-Checked Locking + volatile (Production Standard)
/*
Why two checks?

First check (outside lock): fast path. If instance exists, return immediately. No locking overhead.
        Lock: only one thread creates the instance.
Second check (inside lock): another thread may have created the instance between the first check and acquiring the lock. Check again.

Why volatile?
Without volatile, the JVM can reorder instructions.
instance = new ConfigurationManager() is actually three steps:

Allocate memory
Call constructor (initialise fields)
Assign reference to instance

JVM can reorder to: allocate → assign reference → call constructor.
Another thread sees a non-null but partially constructed object.
Reads garbage. volatile prevents reordering.
*/