package com.relicsmpcore.commands;

import com.relicsmpcore.RelicSMPCore;
import com.relicsmpcore.gui.MainGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RelicSMPCoreCommand implements CommandExecutor {

    private final RelicSMPCore plugin;

    public RelicSMPCoreCommand(RelicSMPCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be executed by players!");
            return true;
        }

        if (!player.hasPermission("relicsmpcore.command.main")) {
            player.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        if (args.length == 0) {
            new MainGUI(plugin, player).open();
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("relicsmpcore.command.reload")) {
                player.sendMessage("§cYou don't have permission to reload the plugin!");
                return true;
            }

            plugin.getConfigManager().loadConfigurations();
            plugin.getPlayerDataManager().loadPlayerData();
            plugin.getRelicManager().loadRelics();
            plugin.getEventManager().loadEvents();

            player.sendMessage("§aRelicSMPCore has been reloaded!");
            return true;
        }

        player.sendMessage("§cUnknown subcommand: " + args[0]);
        return false;
    }
}
