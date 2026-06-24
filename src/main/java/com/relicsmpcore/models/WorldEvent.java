package com.relicsmpcore.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WorldEvent {

    private final String id;
    private final String name;
    private final String description;
    private boolean running;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final List<String> rewards;
    private final LocalDateTime createdAt;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public WorldEvent(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.running = false;
        this.rewards = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRunning() {
        return running;
    }

    public void start() {
        this.running = true;
        this.startTime = LocalDateTime.now();
    }

    public void stop() {
        this.running = false;
        this.endTime = LocalDateTime.now();
    }

    public void schedule(LocalDateTime scheduledTime) {
        this.startTime = scheduledTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<String> getRewards() {
        return new ArrayList<>(rewards);
    }

    public void addReward(String reward) {
        rewards.add(reward);
    }

    public void removeReward(String reward) {
        rewards.remove(reward);
    }

    public void broadcast(String message) {
        // Broadcast logic will be implemented here
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("description", description);
        map.put("running", running);
        map.put("start-time", startTime != null ? startTime.format(FORMATTER) : null);
        map.put("end-time", endTime != null ? endTime.format(FORMATTER) : null);
        map.put("rewards", rewards);
        map.put("created-at", createdAt.format(FORMATTER));
        return map;
    }

    public static WorldEvent fromMap(Map<?, ?> map) {
        try {
            String id = (String) map.get("id");
            String name = (String) map.get("name");
            String description = (String) map.get("description");

            WorldEvent event = new WorldEvent(id, name, description);
            event.running = (Boolean) map.get("running");
            event.rewards.addAll((List<String>) map.get("rewards"));

            if (map.get("start-time") != null) {
                event.startTime = LocalDateTime.parse((String) map.get("start-time"), FORMATTER);
            }
            if (map.get("end-time") != null) {
                event.endTime = LocalDateTime.parse((String) map.get("end-time"), FORMATTER);
            }

            return event;
        } catch (Exception e) {
            return null;
        }
    }
}
