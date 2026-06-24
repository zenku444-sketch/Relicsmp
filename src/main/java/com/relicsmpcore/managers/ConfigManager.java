package com.relicsmpcore.managers;

import com.relicsmpcore.RelicSMPCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final RelicSMPCore plugin;
    private FileConfiguration mainConfig;
    private File configFile;

    public ConfigManager(RelicSMPCore plugin) {
        this.plugin = plugin;
    }

    public void loadConfigurations() {
        // Create data folder if it doesn't exist
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        // Load main config
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            createDefaultConfig();
        }
        mainConfig = YamlConfiguration.loadConfiguration(configFile);
        plugin.getLogger().info("Configuration loaded successfully!");
    }

    private void createDefaultConfig() {
        try {
            configFile.createNewFile();
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

            // Set default values
            config.set("plugin.name", "RelicSMPCore");
            config.set("plugin.version", "1.0.0");
            config.set("settings.auto-save-interval", 300);
            config.set("settings.debug-mode", false);

            config.save(configFile);
            plugin.getLogger().info("Default configuration created!");
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to create config.yml: " + e.getMessage());
        }
    }

    public FileConfiguration getConfig() {
        return mainConfig;
    }

    public void saveConfig() {
        try {
            mainConfig.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save config.yml: " + e.getMessage());
        }
    }

    public int getAutoSaveInterval() {
        return mainConfig.getInt("settings.auto-save-interval", 300);
    }

    public boolean isDebugMode() {
        return mainConfig.getBoolean("settings.debug-mode", false);
    }
}
