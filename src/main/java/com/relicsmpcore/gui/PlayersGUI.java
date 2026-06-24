package com.relicsmpcore.gui;

import com.relicsmpcore.RelicSMPCore;
import com.relicsmpcore.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class PlayersGUI {

    private final RelicSMPCore plugin;
    private final Player player;
    private Inventory inventory;

    public PlayersGUI(RelicSMPCore plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 27, "§6Players");
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

        // Get all player data
        List<PlayerData> allPlayers = plugin.getPlayerDataManager().getAllPlayers();

        // Display players
        int slot = 10;
        for (PlayerData playerData : allPlayers) {
            if (slot >= 17) break;
            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
            if (skullMeta != null) {
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerData.getUuid()));
                skullMeta.setDisplayName("§6" + Bukkit.getOfflinePlayer(playerData.getUuid()).getName());
                skullMeta.setLore(java.util.Arrays.asList(
                    "§7Relics: §f" + playerData.getRelics().size(),
                    "§7Last Seen: §f" + playerData.getLastSeen()
                ));
                playerHead.setItemMeta(skullMeta);
            }
            inventory.setItem(slot, playerHead);
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
