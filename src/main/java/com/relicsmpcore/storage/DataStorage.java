package com.relicsmpcore.storage;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class DataStorage {

    protected File file;
    protected YamlConfiguration config;

    public DataStorage(File file) {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() throws IOException {
        config.save(file);
    }

    public void load() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }

    public abstract void initialize();
}
