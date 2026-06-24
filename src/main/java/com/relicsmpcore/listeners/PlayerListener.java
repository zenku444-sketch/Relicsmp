package com.relicsmpcore.listeners;

import com.relicsmpcore.RelicSMPCore;
import com.relicsmpcore.gui.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;

public class PlayerListener implements Listener {

    private final RelicSMPCore plugin;

    public PlayerListener(RelicSMPCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayerDataManager().loadPlayer(player.getUniqueId(), player.getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayerDataManager().savePlayerData();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        String title = view.getTitle();
        Player player = (Player) event.getWhoClicked();

        // Main GUI
        if (title.equals("§6RelicSMPCore")) {
            event.setCancelled(true);
            int slot = event.getRawSlot();

            if (slot == 11) { // Relics
                new RelicsGUI(plugin, player).open();
            } else if (slot == 13) { // Events
                new EventsGUI(plugin, player).open();
            } else if (slot == 15) { // Players
                new PlayersGUI(plugin, player).open();
            } else if (slot == 20) { // Settings
                new SettingsGUI(plugin, player).open();
            } else if (slot == 24) { // Reload
                if (player.hasPermission("relicsmpcore.command.reload")) {
                    plugin.getConfigManager().loadConfigurations();
                    plugin.getPlayerDataManager().loadPlayerData();
                    plugin.getRelicManager().loadRelics();
                    plugin.getEventManager().loadEvents();
                    player.sendMessage("§aRelicSMPCore reloaded!");
                }
            }
        }
        // Relics GUI
        else if (title.equals("§6Relics")) {
            event.setCancelled(true);
            if (event.getRawSlot() == 26) { // Back
                new MainGUI(plugin, player).open();
            }
        }
        // Events GUI
        else if (title.equals("§6Events")) {
            event.setCancelled(true);
            if (event.getRawSlot() == 26) { // Back
                new MainGUI(plugin, player).open();
            }
        }
        // Players GUI
        else if (title.equals("§6Players")) {
            event.setCancelled(true);
            if (event.getRawSlot() == 26) { // Back
                new MainGUI(plugin, player).open();
            }
        }
        // Settings GUI
        else if (title.equals("§6Settings")) {
            event.setCancelled(true);
            if (event.getRawSlot() == 26) { // Back
                new MainGUI(plugin, player).open();
            }
        }
    }
}
