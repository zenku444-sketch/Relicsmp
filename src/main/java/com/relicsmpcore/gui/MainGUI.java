package com.relicsmpcore.gui;

import com.relicsmpcore.RelicSMPCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class MainGUI {

    private final RelicSMPCore plugin;
    private final Player player;
    private Inventory inventory;

    public MainGUI(RelicSMPCore plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 27, "§6RelicSMPCore");
        setupInventory();
    }

    private void setupInventory() {
        // Fill borders with gray stained glass
        ItemStack filler = createItem(Material.GRAY_STAINED_GLASS_PANE, "§7");

        // Fill top and bottom rows
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, filler);
            inventory.setItem(i + 18, filler);
        }

        // Fill left and right columns
        for (int i = 9; i < 18; i++) {
            if (i % 9 == 0 || i % 9 == 8) {
                inventory.setItem(i, filler);
            }
        }

        // Relics button (slot 11)
        inventory.setItem(11, createItem(Material.GOLDEN_AXE, "§6Relics", "§7View and manage your relics"));

        // Events button (slot 13)
        inventory.setItem(13, createItem(Material.DIAMOND, "§6Events", "§7World events and activities"));

        // Players button (slot 15)
        inventory.setItem(15, createItem(Material.PLAYER_HEAD, "§6Players", "§7View player statistics"));

        // Settings button (slot 20)
        inventory.setItem(20, createItem(Material.REDSTONE, "§6Settings", "§7Plugin configuration"));

        // Reload button (slot 24)
        inventory.setItem(24, createItem(Material.REPEATING_COMMAND_BLOCK, "§6Reload", "§7Reload plugin data"));
    }

    private ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(java.util.Arrays.asList(lore));
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
