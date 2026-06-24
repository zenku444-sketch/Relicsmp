package com.relicsmpcore.events;

import java.time.LocalDateTime;
import java.util.List;

public abstract class EventFramework {

    protected String id;
    protected String name;
    protected String description;
    protected boolean running;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected List<String> rewards;

    public EventFramework(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.running = false;
        this.rewards = new java.util.ArrayList<>();
    }

    public abstract void onEventStart();

    public abstract void onEventStop();

    public abstract void onEventTick();

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
        onEventStart();
    }

    public void stop() {
        this.running = false;
        this.endTime = LocalDateTime.now();
        onEventStop();
    }

    public void tick() {
        if (running) {
            onEventTick();
        }
    }

    public List<String> getRewards() {
        return new java.util.ArrayList<>(rewards);
    }

    public void addReward(String reward) {
        rewards.add(reward);
    }
}
