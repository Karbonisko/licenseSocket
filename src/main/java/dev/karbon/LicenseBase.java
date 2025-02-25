package dev.karbon;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;


public class LicenseBase {
    private static final Yaml yaml = new Yaml();
    private static InputStream inputStream = null;
    private static Map<String, Object> generalConfig = null;
    public static void init(String path) throws IOException {
        inputStream = Files.newInputStream(Path.of(path));
        generalConfig = yaml.load(inputStream);

    }

    public static void init() {
        inputStream = Main.class.getClassLoader().getResourceAsStream("config.yaml");
    }

    public static List<String> getLicenses() {
        return (List<String>) generalConfig.get("licenses");
    }
}
