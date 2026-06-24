package com.relicsmpcore;

import com.relicsmpcore.commands.RelicSMPCoreCommand;
import com.relicsmpcore.listeners.PlayerListener;
import com.relicsmpcore.managers.ConfigManager;
import com.relicsmpcore.managers.PlayerDataManager;
import com.relicsmpcore.managers.RelicManager;
import com.relicsmpcore.managers.EventManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RelicSMPCore extends JavaPlugin {

    private static RelicSMPCore instance;
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;
    private RelicManager relicManager;
    private EventManager eventManager;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("================================================");
        getLogger().info("RelicSMPCore v" + getDescription().getVersion() + " is enabling...");
        getLogger().info("================================================");

        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.playerDataManager = new PlayerDataManager(this);
        this.relicManager = new RelicManager(this);
        this.eventManager = new EventManager(this);

        // Load configurations
        configManager.loadConfigurations();
        playerDataManager.loadPlayerData();
        relicManager.loadRelics();

        // Register commands
        getCommand("relicsmpcore").setExecutor(new RelicSMPCoreCommand(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        getLogger().info("================================================");
        getLogger().info("RelicSMPCore successfully enabled!");
        getLogger().info("================================================");
    }

    @Override
    public void onDisable() {
        getLogger().info("================================================");
        getLogger().info("RelicSMPCore is disabling...");
        getLogger().info("================================================");

        // Save all data
        if (playerDataManager != null) playerDataManager.savePlayerData();
        if (relicManager != null) relicManager.saveRelics();
        if (eventManager != null) eventManager.saveEvents();

        getLogger().info("================================================");
        getLogger().info("RelicSMPCore successfully disabled!");
        getLogger().info("================================================");
    }

    public static RelicSMPCore getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public RelicManager getRelicManager() {
        return relicManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
