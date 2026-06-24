package com.relicsmpcore.models;

import org.bukkit.Material;

import java.util.*;

public class Relic {

    private final UUID id;
    private final String name;
    private final String description;
    private final Material material;
    private final int cooldown;
    private UUID holderUuid;
    private final List<UUID> ownershipHistory;
    private long lastUsed;

    public Relic(UUID id, String name, String description, Material material, int cooldown, UUID holderUuid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.material = material;
        this.cooldown = cooldown;
        this.holderUuid = holderUuid;
        this.ownershipHistory = new ArrayList<>();
        this.lastUsed = 0;

        if (holderUuid != null) {
            this.ownershipHistory.add(holderUuid);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Material getMaterial() {
        return material;
    }

    public int getCooldown() {
        return cooldown;
    }

    public UUID getHolderUuid() {
        return holderUuid;
    }

    public void setHolderUuid(UUID uuid) {
        if (uuid != null && !ownershipHistory.contains(uuid)) {
            ownershipHistory.add(uuid);
        }
        this.holderUuid = uuid;
    }

    public List<UUID> getOwnershipHistory() {
        return new ArrayList<>(ownershipHistory);
    }

    public long getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(long time) {
        this.lastUsed = time;
    }

    public boolean isOnCooldown() {
        return (System.currentTimeMillis() - lastUsed) < (cooldown * 1000);
    }

    public long getRemainingCooldown() {
        long remaining = cooldown * 1000 - (System.currentTimeMillis() - lastUsed);
        return Math.max(0, remaining / 1000);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id.toString());
        map.put("name", name);
        map.put("description", description);
        map.put("material", material.name());
        map.put("cooldown", cooldown);
        map.put("holder", holderUuid != null ? holderUuid.toString() : null);
        map.put("last-used", lastUsed);
        map.put("ownership-history", ownershipHistory.stream().map(UUID::toString).toList());
        return map;
    }

    public static Relic fromMap(Map<?, ?> map) {
        try {
            UUID id = UUID.fromString((String) map.get("id"));
            String name = (String) map.get("name");
            String description = (String) map.get("description");
            Material material = Material.matchMaterial((String) map.get("material"));
            int cooldown = ((Number) map.get("cooldown")).intValue();
            UUID holder = map.get("holder") != null ? UUID.fromString((String) map.get("holder")) : null;

            if (material == null) {
                material = Material.PAPER;
            }

            return new Relic(id, name, description, material, cooldown, holder);
        } catch (Exception e) {
            return null;
        }
    }
}
