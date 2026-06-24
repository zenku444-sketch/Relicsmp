package com.relicsmpcore.gui;

import com.relicsmpcore.RelicSMPCore;
import com.relicsmpcore.models.WorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EventsGUI {

    private final RelicSMPCore plugin;
    private final Player player;
    private Inventory inventory;

    public EventsGUI(RelicSMPCore plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 27, "§6Events");
        setupInventory();
    }

    private void setupInventory() {
        ItemStack filler = createItem(Material.GRAY_STAINED_GLASS_PANE, "§7");

        // Fill borders
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, filler);
            inventory.setItem(i + 18, filler);
        }
        for (int i = 9; i < 18; i++) {
            if (i % 9 == 0 || i % 9 == 8) {
                inventory.setItem(i, filler);
            }
        }

        // Get all events
        List<WorldEvent> events = plugin.getEventManager().getAllEvents();

        // Display events
        int slot = 10;
        for (WorldEvent event : events) {
            if (slot >= 17) break;
            ItemStack eventItem = new ItemStack(Material.BOOK);
            ItemMeta meta = eventItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("§6" + event.getName());
                String status = event.isRunning() ? "§aRunning" : "§cStopped";
                meta.setLore(java.util.Arrays.asList(
                    "§7" + event.getDescription(),
                    status
                ));
                eventItem.setItemMeta(meta);
            }
            inventory.setItem(slot, eventItem);
            slot++;
        }

        // Back button
        inventory.setItem(26, createItem(Material.ARROW, "§cBack"));
    }

    private ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            if (lore.length > 0) {
                meta.setLore(java.util.Arrays.asList(lore));
            }
            meta.setEnchantmentGlintOverride(true);
            item.setItemMeta(meta);
        }
        return item;
    }

    public void open() {
        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
