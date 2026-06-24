package com.relicsmpcore.managers;

import com.relicsmpcore.RelicSMPCore;
import com.relicsmpcore.models.WorldEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EventManager {

    private final RelicSMPCore plugin;
    private final List<WorldEvent> events = new ArrayList<>();
    private File eventsFile;
    private FileConfiguration eventsConfig;

    public EventManager(RelicSMPCore plugin) {
        this.plugin = plugin;
    }

    public void loadEvents() {
        eventsFile = new File(plugin.getDataFolder(), "events.yml");
        if (!eventsFile.exists()) {
            createDefaultEvents();
        }
        eventsConfig = YamlConfiguration.loadConfiguration(eventsFile);
        loadAllEvents();
        plugin.getLogger().info("Events loaded successfully!");
    }

    private void createDefaultEvents() {
        try {
            eventsFile.createNewFile();
            FileConfiguration config = YamlConfiguration.loadConfiguration(eventsFile);
            config.set("events", new ArrayList<>());
            config.save(eventsFile);
            plugin.getLogger().info("Default events file created!");
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to create events.yml: " + e.getMessage());
        }
    }

    private void loadAllEvents() {
        events.clear();
        if (eventsConfig.contains("events")) {
            List<Map<?, ?>> eventMaps = eventsConfig.getMapList("events");
            for (Map<?, ?> map : eventMaps) {
                WorldEvent event = WorldEvent.fromMap(map);
                if (event != null) {
                    events.add(event);
                }
            }
        }
    }

    public void saveEvents() {
        try {
            List<Map<String, Object>> eventMaps = new ArrayList<>();
            for (WorldEvent event : events) {
                eventMaps.add(event.toMap());
            }
            eventsConfig.set("events", eventMaps);
            eventsConfig.save(eventsFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save events.yml: " + e.getMessage());
        }
    }

    public List<WorldEvent> getAllEvents() {
        return new ArrayList<>(events);
    }

    public WorldEvent getEventById(String id) {
        return events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addEvent(WorldEvent event) {
        events.add(event);
        saveEvents();
    }

    public void removeEvent(String id) {
        events.removeIf(e -> e.getId().equals(id));
        saveEvents();
    }

    public void startEvent(String id) {
        WorldEvent event = getEventById(id);
        if (event != null) {
            event.start();
            saveEvents();
        }
    }

    public void stopEvent(String id) {
        WorldEvent event = getEventById(id);
        if (event != null) {
            event.stop();
            saveEvents();
        }
    }
}
