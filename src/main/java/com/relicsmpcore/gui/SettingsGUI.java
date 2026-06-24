package com.relicsmpcore.gui;

import com.relicsmpcore.RelicSMPCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsGUI {

    private final RelicSMPCore plugin;
    private final Player player;
    private Inventory inventory;

    public SettingsGUI(RelicSMPCore plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 27, "§6Settings");
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

        if (player.hasPermission("relicsmpcore.admin")) {
            // Admin settings
            inventory.setItem(11, createItem(Material.COMMAND_BLOCK, "§6Admin Panel", "§7Access admin controls"));
            inventory.setItem(13, createItem(Material.REDSTONE, "§6Reload Config", "§7Reload plugin configuration"));
            inventory.setItem(15, createItem(Material.CHEST, "§6Data Backup", "§7Backup player data"));
        } else {
            // Regular player settings
            inventory.setItem(13, createItem(Material.BOOK, "§6Help", "§7View help information"));
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
