package singleton;

import java.util.HashMap;
import java.util.Map;

// Usage:
//ConfigurationManager_V4.INSTANCE.get("db.url");

public enum ConfigurationManager_V4 {
    INSTANCE;
    private Map<String, String> config;

    ConfigurationManager_V4() {
        this.config = loadConfig();
    }

    private Map<String, String> loadConfig() {
        return new HashMap<>();
    }

    public String get(String key) {
        return config.get(key);
    }
}

/*
Why is it a Singleton?
Java guarantees each enum constant is instantiated exactly once per JVM — at class loading time.
INSTANCE is the only constant here, so exactly one ConfigurationManager object ever exists.
This is a JVM-level guarantee, not something you implement.

Why is it thread-safe?
Enum constants are initialized during class loading.
Java class loading is thread-safe by the JVM spec.
No synchronized, no volatile needed — the JVM handles it.

When to use over DCL?
When you want the simplest possible code
When you don't need to extend a class (enums can't extend classes)

When NOT to use:
When your Singleton needs to extend a class
*/