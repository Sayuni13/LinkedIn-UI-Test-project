package base;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Properties;
public class Config {
    private static final Properties values = new Properties();
    static {
        Path file = Path.of("config.properties");
        if (Files.exists(file)) {
            try (InputStream in = Files.newInputStream(file)) { values.load(in); }
            catch (Exception e) { throw new IllegalStateException("Cannot read config.properties", e); }
        }
    }
    public static String get(String key, String fallback) { return values.getProperty(key, fallback); }
    public static int seconds() { return Integer.parseInt(get("wait.seconds", "30")); }
    public static String credential(String key) {
        String value = System.getenv("LINKEDIN_" + key.toUpperCase());
        if (value == null || value.isBlank()) value = get(key, "");
        if (value.isBlank() || value.startsWith("YOUR_"))
            throw new IllegalStateException("Set " + key + " in config.properties first.");
        return value;
    }
}
