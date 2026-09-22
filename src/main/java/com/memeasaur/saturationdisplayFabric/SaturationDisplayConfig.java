package com.memeasaur.saturationdisplayFabric;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Persistent client options. Kept dependency-free so the mod works on a minimal Fabric install. */
public final class SaturationDisplayConfig {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("saturationdisplay.properties");
    public static boolean enabled = true;
    public static boolean showNumber = true;
    public static boolean coloredNumber = false;
    public static boolean outlineHungerBar = false;

    private SaturationDisplayConfig() { }

    public static void load() {
        Properties properties = new Properties();
        if (Files.isRegularFile(FILE)) {
            try (InputStream input = Files.newInputStream(FILE)) {
                properties.load(input);
            } catch (IOException ignored) {
                return;
            }
        }
        enabled = value(properties, "enabled", enabled);
        showNumber = value(properties, "showNumber", showNumber);
        coloredNumber = value(properties, "coloredNumber", coloredNumber);
        outlineHungerBar = value(properties, "outlineHungerBar", outlineHungerBar);
    }

    public static void save() {
        Properties properties = new Properties();
        properties.setProperty("enabled", Boolean.toString(enabled));
        properties.setProperty("showNumber", Boolean.toString(showNumber));
        properties.setProperty("coloredNumber", Boolean.toString(coloredNumber));
        properties.setProperty("outlineHungerBar", Boolean.toString(outlineHungerBar));
        try {
            Files.createDirectories(FILE.getParent());
            try (OutputStream output = Files.newOutputStream(FILE)) {
                properties.store(output, "Saturation Display options");
            }
        } catch (IOException ignored) { }
    }

    private static boolean value(Properties properties, String key, boolean fallback) {
        return Boolean.parseBoolean(properties.getProperty(key, Boolean.toString(fallback)));
    }
}
