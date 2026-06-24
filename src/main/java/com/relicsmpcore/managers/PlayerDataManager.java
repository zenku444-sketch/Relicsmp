package com.relicsmpcore.managers;

import com.relicsmpcore.RelicSMPCore;
import com.relicsmpcore.models.PlayerData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PlayerDataManager {

    private final RelicSMPCore plugin;
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    private File playersFile;
    private FileConfiguration playersConfig;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PlayerDataManager(RelicSMPCore plugin) {
        this.plugin = plugin;
    }

    public void loadPlayerData() {
        playersFile = new File(plugin.getDataFolder(), "players.yml");
        if (!playersFile.exists()) {
            createDefaultPlayers();
        }
        playersConfig = YamlConfiguration.loadConfiguration(playersFile);
        loadAllPlayers();
        plugin.getLogger().info("Player data loaded successfully!");
    }

    private void createDefaultPlayers() {
        try {
            playersFile.createNewFile();
            FileConfiguration config = YamlConfiguration.loadConfiguration(playersFile);
            config.set("players", new HashMap<>());
            config.save(playersFile);
            plugin.getLogger().info("Default players file created!");
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to create players.yml: " + e.getMessage());
        }
    }

    private void loadAllPlayers() {
        playerDataMap.clear();
        if (playersConfig.contains("players")) {
            for (String uuidStr : playersConfig.getConfigurationSection("players").getKeys(false)) {
                try {
                    UUID uuid = UUID.fromString(uuidStr);
                    String path = "players." + uuidStr;

                    String playerName = playersConfig.getString(path + ".name", "Unknown");
                    String lastSeen = playersConfig.getString(path + ".last-seen", LocalDateTime.now().format(FORMATTER));
                    List<String> relics = playersConfig.getStringList(path + ".relics");
                    Map<String, Object> stats = playersConfig.getConfigurationSection(path + ".stats").getValues(false);

                    PlayerData playerData = new PlayerData(uuid, playerName);
                    playerData.setLastSeen(lastSeen);
                    playerDataMap.put(uuid, playerData);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Invalid UUID format: " + uuidStr);
                }
            }
        }
    }

    public void savePlayerData() {
        try {
            playersConfig.set("players", null);
            for (Map.Entry<UUID, PlayerData> entry : playerDataMap.entrySet()) {
                String path = "players." + entry.getKey();
                PlayerData data = entry.getValue();

                playersConfig.set(path + ".name", data.getPlayerName());
                playersConfig.set(path + ".last-seen", data.getLastSeen());
                playersConfig.set(path + ".relics", data.getRelics());
            }
            playersConfig.save(playersFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save players.yml: " + e.getMessage());
        }
    }

    public void loadPlayer(UUID uuid, String playerName) {
        if (!playerDataMap.containsKey(uuid)) {
            playerDataMap.put(uuid, new PlayerData(uuid, playerName));
        }
        PlayerData data = playerDataMap.get(uuid);
        data.setLastSeen(LocalDateTime.now().format(FORMATTER));
        savePlayerData();
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.get(uuid);
    }

    public List<PlayerData> getAllPlayers() {
        return new ArrayList<>(playerDataMap.values());
    }

    public void updatePlayerData(UUID uuid, PlayerData data) {
        playerDataMap.put(uuid, data);
        savePlayerData();
    }
}
