package com.relicsmpcore.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PlayerData {

    private final UUID uuid;
    private final String playerName;
    private final List<String> relics;
    private String lastSeen;
    private final Map<String, Integer> statistics;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PlayerData(UUID uuid, String playerName) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.relics = new ArrayList<>();
        this.lastSeen = LocalDateTime.now().format(FORMATTER);
        this.statistics = new HashMap<>();
        initializeStatistics();
    }

    private void initializeStatistics() {
        statistics.put("relics-collected", 0);
        statistics.put("relics-used", 0);
        statistics.put("events-participated", 0);
        statistics.put("total-playtime", 0);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<String> getRelics() {
        return new ArrayList<>(relics);
    }

    public void addRelic(String relicId) {
        if (!relics.contains(relicId)) {
            relics.add(relicId);
            incrementStat("relics-collected");
        }
    }

    public void removeRelic(String relicId) {
        relics.remove(relicId);
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Map<String, Integer> getStatistics() {
        return new HashMap<>(statistics);
    }

    public int getStatistic(String key) {
        return statistics.getOrDefault(key, 0);
    }

    public void incrementStat(String key) {
        statistics.put(key, statistics.getOrDefault(key, 0) + 1);
    }

    public void setStat(String key, int value) {
        statistics.put(key, value);
    }
}
