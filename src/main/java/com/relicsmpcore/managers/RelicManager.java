package com.relicsmpcore.managers;

import com.relicsmpcore.RelicSMPCore;
import com.relicsmpcore.models.Relic;
import com.relicsmpcore.relics.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RelicManager {

    private final RelicSMPCore plugin;
    private final Map<UUID, List<Relic>> playerRelics = new HashMap<>();
    private File relicsFile;
    private FileConfiguration relicsConfig;

    public RelicManager(RelicSMPCore plugin) {
        this.plugin = plugin;
    }

    public void loadRelics() {
        relicsFile = new File(plugin.getDataFolder(), "relics.yml");
        if (!relicsFile.exists()) {
            createDefaultRelics();
        }
        relicsConfig = YamlConfiguration.loadConfiguration(relicsFile);
        loadPlayerRelics();
        plugin.getLogger().info("Relics loaded successfully!");
    }

    private void createDefaultRelics() {
        try {
            relicsFile.createNewFile();
            FileConfiguration config = YamlConfiguration.loadConfiguration(relicsFile);

            // Create default relics
            Map<String, Object> windRelic = new HashMap<>();
            windRelic.put("name", "Relic of Winds");
            windRelic.put("description", "Grants the power of flight");
            windRelic.put("material", "FEATHER");
            windRelic.put("cooldown", 30);
            config.set("relics.relic_of_winds", windRelic);

            Map<String, Object> enderRelic = new HashMap<>();
            enderRelic.put("name", "Relic of Ender");
            enderRelic.put("description", "Enables teleportation across distances");
            enderRelic.put("material", "ENDER_PEARL");
            enderRelic.put("cooldown", 45);
            config.set("relics.relic_of_ender", enderRelic);

            Map<String, Object> titanRelic = new HashMap<>();
            titanRelic.put("name", "Relic of Titan");
            titanRelic.put("description", "Increases strength and durability");
            titanRelic.put("material", "IRON_BLOCK");
            titanRelic.put("cooldown", 60);
            config.set("relics.relic_of_titan", titanRelic);

            Map<String, Object> hunterRelic = new HashMap<>();
            hunterRelic.put("name", "Relic of Hunter");
            hunterRelic.put("description", "Enhances tracking and precision");
            hunterRelic.put("material", "BOW");
            hunterRelic.put("cooldown", 40);
            config.set("relics.relic_of_hunter", hunterRelic);

            Map<String, Object> fortuneRelic = new HashMap<>();
            fortuneRelic.put("name", "Relic of Fortune");
            fortuneRelic.put("description", "Increases luck and rare drops");
            fortuneRelic.put("material", "GOLD_BLOCK");
            fortuneRelic.put("cooldown", 50);
            config.set("relics.relic_of_fortune", fortuneRelic);

            config.save(relicsFile);
            plugin.getLogger().info("Default relics created!");
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to create relics.yml: " + e.getMessage());
        }
    }

    private void loadPlayerRelics() {
        playerRelics.clear();
        if (relicsConfig.contains("player-relics")) {
            for (String uuidStr : relicsConfig.getConfigurationSection("player-relics").getKeys(false)) {
                UUID uuid = UUID.fromString(uuidStr);
                List<Map<?, ?>> relicMaps = relicsConfig.getMapList("player-relics." + uuidStr);
                List<Relic> relics = new ArrayList<>();
                for (Map<?, ?> map : relicMaps) {
                    Relic relic = Relic.fromMap(map);
                    if (relic != null) {
                        relics.add(relic);
                    }
                }
                playerRelics.put(uuid, relics);
            }
        }
    }

    public void saveRelics() {
        try {
            relicsConfig.set("player-relics", null);
            for (Map.Entry<UUID, List<Relic>> entry : playerRelics.entrySet()) {
                List<Map<String, Object>> relicMaps = new ArrayList<>();
                for (Relic relic : entry.getValue()) {
                    relicMaps.add(relic.toMap());
                }
                relicsConfig.set("player-relics." + entry.getKey(), relicMaps);
            }
            relicsConfig.save(relicsFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save relics.yml: " + e.getMessage());
        }
    }

    public List<Relic> getPlayerRelics(UUID uuid) {
        return playerRelics.getOrDefault(uuid, new ArrayList<>());
    }

    public void addRelicToPlayer(UUID uuid, Relic relic) {
        playerRelics.computeIfAbsent(uuid, k -> new ArrayList<>()).add(relic);
        saveRelics();
    }

    public void removeRelicFromPlayer(UUID uuid, Relic relic) {
        playerRelics.computeIfPresent(uuid, (k, v) -> {
            v.remove(relic);
            return v.isEmpty() ? null : v;
        });
        saveRelics();
    }

    public Relic getRelicById(String id) {
        String path = "relics." + id;
        if (!relicsConfig.contains(path)) {
            return null;
        }

        String name = relicsConfig.getString(path + ".name", "Unknown");
        String description = relicsConfig.getString(path + ".description", "");
        Material material = Material.matchMaterial(relicsConfig.getString(path + ".material", "PAPER"));
        int cooldown = relicsConfig.getInt(path + ".cooldown", 30);

        if (material == null) {
            material = Material.PAPER;
        }

        return new Relic(UUID.randomUUID(), name, description, material, cooldown, null);
    }
}
